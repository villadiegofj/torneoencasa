(ns torneoencasa.api.middleware.core
  (:require
    [ring.middleware.cors :as cors]
    [ring.middleware.defaults :refer [site-defaults wrap-defaults]]))

(defn wrap-with-cors
  [handler domain-pattern]
  (cors/wrap-cors handler
             :access-control-allow-origin domain-pattern
             :access-control-allow-credentials "true"
             :access-control-allow-headers #{:accept :content-type}
             :access-control-allow-methods #{:get :put :post :delete}))

(def wrap-db
  {:name ::db
   :compile (fn [{:keys [db]} _]
              (fn [handler]
                (fn [req]
                  (handler (assoc req :db (:datasource db))))))})

;;(def wrap-enforce-roles
;;  {:name ::enforce-roles
;;   :compile (fn [{:keys [roles]} opts]
;;              (let [authorized roles]
;;                (if (seq roles)
;;                  (fn [handler]
;;                    (fn [req]
;;                      (handler (assoc req :xxx (str "contents:" (keys req)))))))))})
