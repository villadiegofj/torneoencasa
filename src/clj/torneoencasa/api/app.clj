(ns torneoencasa.api.app
  (:require [ring.adapter.jetty :as jetty]
            [ring.util.response :refer [resource-response]]
            [torneoencasa.api.routes :as routes])
  (:gen-class))

(defn -main [& args]
  (let [host "0.0.0.0"
        port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    (println "listening on " host ":" port)
    (jetty/run-jetty routes/app-routes {:host host :port port})))
