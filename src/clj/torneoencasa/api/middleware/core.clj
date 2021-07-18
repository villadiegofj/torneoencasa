(ns torneoencasa.api.middleware.core
  (:require
    [torneoencasa.api.middleware.formats :as formats]
    [muuntaja.middleware :as m]
    [ring.middleware.cors :as cors]
    [ring.middleware.webjars :as webjars]
    [ring.middleware.defaults :refer [site-defaults wrap-defaults]]))

(defn wrap-with-cors
  [handler domain-pattern]
  (cors/wrap-cors handler
             :access-control-allow-origin domain-pattern
             :access-control-allow-credentials "true"
             :access-control-allow-headers #{:accept :content-type}
             :access-control-allow-methods #{:get :put :post :delete}))

(defn wrap-with-webjars
  ([handler]
   (wrap-with-webjars handler "/webjars"))
  ([handler prefix]
   (webjars/wrap-webjars handler prefix)))

(def wrap-db
  {:name ::db
   :compile (fn [{:keys [db]} _]
              (fn [handler]
                (fn [req]
                  (handler (assoc req :db (:datasource db))))))})