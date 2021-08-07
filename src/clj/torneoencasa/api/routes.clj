(ns torneoencasa.api.routes
  (:require [ring.util.http-response :as response]
            [reitit.dev.pretty :as dev]
            [reitit.ring :as ring]
            [reitit.ring.coercion :as coercion]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [reitit.ring.middleware.parameters :as params]
            [reitit.coercion.malli :as malli]
            [torneoencasa.api.controllers.auth :as auth]
            [torneoencasa.api.controllers.users :as users]
            [torneoencasa.api.formats :as formats]
            [torneoencasa.api.middleware.core :as middleware]
            [torneoencasa.api.schemas :as schema])
 (:gen-class))

(defn home-page [_]
  (-> (response/resource-response "index.html" {:root "public"})
      (response/content-type "text/html")))

(def all-routes
  [["/" {:name ::home
         :get  home-page}]
   ["/api"
    ["/auth" {:name ::auth
              :post {:handler    auth/check-credentials
                     :parameters {:body schema/creds}
                     :responses  {200 {:body schema/user}
                                  401 {:body schema/error}
                                  404 {:body schema/error}}}}]
    ["/users" {:middleware [[middleware/wrap-enforce-roles]]}
     ["" {:name ::users
          :get  {:handler   users/fetch-all
                 :responses {200 {:body schema/users}}}
          :post {:handler    users/add!
                 :parameters {:body schema/user}
                 :responses  {201 {:body [:map [:id uuid?]]}}}}]
     ["/report" {:name  ::users-report
                 :allowed #{:admin}
                 :get   {:handler users/report}}]]]])

(def accepted-origin #".*")

(defn router-options [ds]
  "router options affecting all routes"
  {:data {:db ds
          :muuntaja formats/content-negotiation
          :coercion malli/coercion
          :middleware [params/parameters-middleware
                       muuntaja/format-negotiate-middleware
                       muuntaja/format-response-middleware
                       muuntaja/format-request-middleware
                       [middleware/wrap-with-cors accepted-origin]
                       coercion/coerce-request-middleware
                       coercion/coerce-exceptions-middleware
                       coercion/coerce-response-middleware
                       middleware/wrap-db]}
   :exception dev/exception})

(defn app-router [ds]
  (ring/router
    all-routes (router-options ds)))

(defn handler
  [ds]
  (ring/ring-handler
    (app-router ds)
    (ring/routes (ring/create-resource-handler {:path "/"})
                 (ring/redirect-trailing-slash-handler {:method :strip})
                 (ring/create-default-handler
                   {:not-found          (constantly {:status 404 :body {:error 404 :message "resource not found"}})
                    :method-not-allowed (constantly {:status 405 :body {:error 405 :message "method not allowed"}})
                    :not-acceptable     (constantly {:status 406 :body {:error 406 :message "not acceptable"}})}))))
