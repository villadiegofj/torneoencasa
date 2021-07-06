(ns torneoencasa.db.core
  (:require [clojure.spec.alpha :as s]
            [clojure.java.io :as io]
            [clojure.edn :as edn])
 (:gen-class))

(s/def ::roles #{:admin :regular :anonymous})
(s/def ::id string?)
(s/def ::password string?)
(s/def ::auth-request (s/keys :req-un [::id ::password]))

(defn read-users [] (->> "dev/resources/users.edn"
     io/resource
     slurp
     edn/read-string))

(defn retrieve [id password]
  (let [users (read-users)
        filters [#(-> % :user :id (= id))
                 #(-> % :user :password (= password))]]
  (into {} (filter (apply every-pred filters) users))))
