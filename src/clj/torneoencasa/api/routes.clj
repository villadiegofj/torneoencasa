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

(def error-schema
  [:map
   [:error-id keyword?]])

(def api-routes
  [["/" {:name ::home
         :get  home-page}]
   ["/api"
    ["/auth" {:name ::auth
              :post {:handler    auth/check-credentials
                     :parameters {:body auth/creds-schema}
                     :responses  {200 {:body users/user-schema}
                                  401 {:body error-schema}
                                  404 {:body error-schema}}}}]
    ["/users" {:name ::users
               :get  {:handler   users/fetch-all
                      :responses {200 {:body users/users-schema}}}
               :post {:handler    users/add!
                      :parameters {:body users/user-schema}
                      :responses  {201 {:body [:map [:id uuid?]]}}}}]]])

(def accepted-origin #".*")

(defn router-options [ds]
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
                       rrc/coerce-response-middleware
                       middleware/wrap-db]}
   :exception reitit.dev.pretty/exception})

(defn api-router [ds]
  (rr/router
    api-routes (router-options ds)))

(defn handler
  [ds]
  (rr/ring-handler
    (api-router ds)
    (rr/routes (rr/create-resource-handler {:path "/"})
               (middleware/wrap-with-webjars "/webjars")
               (rr/redirect-trailing-slash-handler {:method :strip})
               (rr/create-default-handler
                 {:not-found (constantly {:status 404 :body {:error 404 :message "resource not found"}})}))))
