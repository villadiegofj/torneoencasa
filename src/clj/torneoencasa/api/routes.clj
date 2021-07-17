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

(defn api-routes [ds]
  (let [db (:datasource ds)]
    [["/" {:name ::home
           :get  home-page}]
     ["/api"
      ["/auth" {:name ::auth
                :post {:handler    (auth/check-credentials db)
                       :parameters {:body auth/creds-schema}
                       :responses  {200 {:body auth/profile-schema}}}}]
      ["/users" {:name ::users
                 :get  {:handler   (users/fetch-all db)
                        :responses {200 {:body users/users-schema}}}
                 :post {:handler    (users/add! db)
                        :parameters {:body users/user-schema}
                        :responses  {201 {:body [:map [:id uuid?]]}}}}]]]))

(def accepted-origin #".*")

(def router-options
  {:data {:muuntaja muuntaja/instance
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
    (api-routes ds) router-options))

(defn handler
  [ds]
  (rr/ring-handler
    (api-router ds)
    (rr/routes (rr/create-resource-handler {:path "/"})
               (middleware/wrap-with-webjars "/webjars")
               (rr/redirect-trailing-slash-handler {:method :strip})
               (rr/create-default-handler))))
