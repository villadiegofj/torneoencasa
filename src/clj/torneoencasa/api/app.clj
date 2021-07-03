(ns torneoencasa.api.app
  (:require [immutant.web :as web]
            [ring.util.response :refer [resource-response]]
            [reitit.ring :as reitit-ring]
            [torneoencasa.api.routes :as routes])
  (:gen-class))

(defn -main [& args]
  (let [host "0.0.0.0"
        port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    (println "listening on " host ":" port)
    (web/run routes/app-routes {:host host :port port})))
