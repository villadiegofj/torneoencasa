(ns torneoencasa.api.routes
  (:require [ring.util.http-response :as response]
            [muuntaja.core :as muuntaja]
            [reitit.coercion :as rc]
            [reitit.dev.pretty]
            [reitit.ring :as rr]
            [reitit.ring.coercion :as rrc]
            [reitit.ring.middleware.muuntaja :as rrm-muuntaja]
            [reitit.ring.middleware.parameters :as rrm-params]
            [reitit.coercion.malli :as rc-malli]
            [torneoencasa.api.controllers.auth :as auth]
            [torneoencasa.api.controllers.users :as users]
            [torneoencasa.api.middleware.core :as middleware])
 (:gen-class))

(defn home-page [_]
  (-> (response/resource-response "index.html" {:root "public"})
      (response/content-type "text/html")))

(def api-routes
  [["/" {:name ::home
         :get  home-page}]
   ["/api"
    ["/auth" {:name ::auth
              :post {:parameters {:body auth/creds-schema}
                     :responses  {200 {:body auth/profile-schema}}
                     :handler    auth/handler}}]
    ["/users" {:name      ::users
               :responses {200 {:body users/users-schema}}
               :get       {:handler users/handler}}]]])

(def accepted-origin #".*")

(defn router-options
  [ds]
  {:data {:db ds
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

(defn api-router [ds]
  (rr/router
    api-routes (router-options ds)))

(defn build-handler
  [ds]
  (rr/ring-handler
    (api-router ds)
    (rr/routes (rr/create-resource-handler {:path "/"})
               (middleware/wrap-with-webjars "/webjars")
               (rr/redirect-trailing-slash-handler {:method :strip})
               (rr/create-default-handler))))
