(ns torneoencasa.api.controllers.users
  (:require
    [ring.util.http-response :as response]
    [torneoencasa.db.core :as db])
  (:gen-class))

(def users-schema
  [:set [:map
         [:id uuid?]
         [:username string?]
         [:firstname string?]
         [:lastname string?]
         [:email string?]
         [:password string?]
         [:role string?]]])

(defn handler [reqs]
  (let [db (-> reqs :reitit.core/match :data :db :datasource)]
    (response/ok (into #{} (db/get-users db)))))