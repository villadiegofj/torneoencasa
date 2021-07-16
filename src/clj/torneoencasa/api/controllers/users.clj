(ns torneoencasa.api.controllers.users
  (:require
    [buddy.hashers :as buddy-hashers]
    [java-time :as t]
    [ring.util.http-response :as response]
    [torneoencasa.db.core :as db])
  (:gen-class))

(def user-schema
  [:map
   [:firstname string?]
   [:lastname string?]
   [:email string?]
   [:code string?]
   [:username string?]
   [:password string?]
   [:role string?]])

(def users-schema
  [:set [:map
         [:id uuid?]
         [:firstname string?]
         [:lastname string?]
         [:email string?]
         [:code string?]
         [:username string?]
         [:password string?]
         [:role string?]]])

(defn handler [reqs]
  (let [db (-> reqs :reitit.core/match :data :db :datasource)]
    (response/ok (into #{} (db/get-users db)))))

(defn add [reqs]
  (let [data (-> reqs :parameters :body)
        db (-> reqs :reitit.core/match :data :db :datasource)
        id (java.util.UUID/randomUUID)
        encrypted (-> data :password (buddy-hashers/encrypt))
        user (-> data
                 (assoc :id id :password encrypted :created (t/instant)))
        _ (db/save-user db user)]
    (println "encrypted:" encrypted "user" user)
    (response/created (str "/api/users/" id) {:id id})))