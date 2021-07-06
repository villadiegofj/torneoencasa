(ns torneoencasa.api.app
  (:require
    [environ.core :refer [env]]
    [integrant.core :as ig]
    [reitit.ring :as ring]
    [ring.adapter.jetty :as jetty]
    [ring.util.response :refer [resource-response]]
    [torneoencasa.api.routes :as routes])
  (:gen-class))

(defn app
  [ctx]
  (routes/build-handler ctx))

(defmethod ig/prep-key :server/http
  [_ config]
  (if-let [env-port (env :port)]
    (merge config {:port (Integer/parseInt env-port)})
    config))

(defmethod ig/init-key :server/http
  [_ {:keys [handler port]}]
  (println (str "[*] listening on: " port))
  (jetty/run-jetty handler {:port port :join? false}))

(defmethod ig/init-key :torneoencasa/app
  [_ config]
  (println "[*] started app: ")
  (app config))

#_(defmethod ig/init-key :db/config [_ db-spec]
  (let [conn (jdbc/get-datasource db-spec)]
    (populate conn (:dbtype db-spec))
    conn))

(defmethod ig/init-key :db/config [_ db-spec]
  [_ db-spec]
  (println "[*] db connection: " db-spec)
  db-spec)

(defmethod ig/halt-key! :server/http
  [_ jetty]
  (.stop jetty))

(defn -main
  [config-file]
  (let [config (-> config-file slurp ig/read-string)]
    (-> config ig/prep ig/init)))

(comment
  ((app {:dbtype "mydbtype"
         :dbname "mydbname"}) {:request-method :post
                        :uri "/api/auth"
                        :form-params {:id "batman"
                                      :password "namtab"}})
  ,)