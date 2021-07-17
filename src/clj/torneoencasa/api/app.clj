(ns torneoencasa.api.app
  (:require
    [environ.core :refer [env]]
    [integrant.core :as ig]
    [next.jdbc :as jdbc]
    [ring.adapter.jetty :as jetty]
    [ring.util.response :refer [resource-response]]
    [torneoencasa.api.routes :as routes]
    [torneoencasa.db.core :as db])
  (:gen-class))

(defmethod ig/prep-key :server/http
  [_ config]
  (if-let [env-port (env :port)]
    (merge config {:port (Integer/parseInt env-port)})
    config))

(defmethod ig/init-key :server/http
  [_ {:keys [handler port]}]
  (println (str "[*] listening on: " port))
  (jetty/run-jetty handler {:port port :join? false}))

(defmethod ig/halt-key! :server/http
  [_ jetty]
  (.stop jetty))

(defmethod ig/init-key :torneoencasa/app
  [_ ds]
  (println "[*] started app")
  (routes/handler ds))

(defmethod ig/init-key :db/config
  [_ db-spec]
  (println "[*] db connection")
  (let [ds (-> db-spec
               (jdbc/get-datasource)
               (jdbc/with-options db/ds-opts))]
    (db/populate ds)
    ds))

(defn -main
  [config-file]
  (let [config (-> config-file slurp ig/read-string)]
    (-> config ig/prep ig/init)))