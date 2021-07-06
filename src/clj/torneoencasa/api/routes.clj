(ns torneoencasa.api.routes
  (:require [ring.util.http-response :as response]
            [muuntaja.core :as muuntaja]
            [malli.core :as malli]
            [reitit.core :as reitit]
            [reitit.dev.pretty]
            [reitit.ring :as rr]
            [reitit.ring.coercion :as rrc]
            [reitit.ring.middleware.muuntaja :as rrm-muuntaja]
            [reitit.ring.middleware.parameters :as rrm-params]
            [reitit.coercion.malli :as rc-malli]
            [torneoencasa.db.core :as db]
            [torneoencasa.api.middleware.core :as middleware])
 (:gen-class))

(def profile-schema
 [:map
  [:auth boolean?]
  [:errors {:optional true} map?]
  [:nav [:map
         [:active-page [:enum :login :home]]]]
  [:user [:map
          [:id string?]
          [:firstname string?]
          [:lastname string?]
          [:roles [:set keyword?]]
          [:password string?]]]
  [:items [:set [:map
                 [:id string?]
                 [:name string?]
                 [:events vector?]]]]])

(defn auth-handler [{{{:keys [id password]} :body} :parameters}]
    (response/ok (db/retrieve id password)))

(defn home-page [request]
  (-> (response/resource-response "index.html" {:root "public"})
      (response/content-type "text/html")))

(def api-routes
  [["/" {:name ::home
        :get home-page}]
   ["/api"
     ["/auth" {:name ::auth
               :parameters {:body [:map [:id string?]
                                        [:password string?]]}
               :responses {200 {:body profile-schema}}
               :post {:handler auth-handler}}]]])

(def accepted-origin #".*")

(defn router-options
  [db-config]
  {:data {:db db-config
          :muuntaja muuntaja/instance
          :coercion rc-malli/coercion
          :middleware [rrm-params/parameters-middleware
                       rrm-muuntaja/format-negotiate-middleware
                       rrm-muuntaja/format-response-middleware
                       rrm-muuntaja/format-request-middleware
                       [middleware/wrap-with-cors accepted-origin]
                       rrc/coerce-request-middleware
                       rrc/coerce-exceptions-middleware
                       rrc/coerce-response-middleware]}
   :exception reitit.dev.pretty/exception})

(defn router [env]
    (rr/router
      api-routes (router-options env)))

(defn build-handler
  [env]
  (rr/ring-handler
    (router env)
    (rr/routes (rr/create-resource-handler {:path "/"})
               (middleware/wrap-with-webjars "/webjars")
               (rr/create-default-handler))))

#_(defn routes
  [env]
  (ring/ring-handler
    (ring/router
      [swagger-docs
       ["/api"
        (math/routes env)
        (strings/routes env)]]
      router-config)
    (ring/routes
      (swagger-ui/create-swagger-ui-handler {:path "/"}))))
