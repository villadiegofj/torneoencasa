(ns torneoencasa.routes
  (:require [reitit.core :as reitit]))

(def router
  (reitit/router
    [["/" ::home]
     ["/classboard/:id" ::classboard]]))

(defn url-for [url & opts]
  (reitit/match-by-name router url opts))

