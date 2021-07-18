(ns torneoencasa.api.controllers.users
  (:require
    [buddy.hashers :as buddy-hashers]
    [java-time :as t]
    [ring.util.http-response :as response]
    [torneoencasa.api.db.users :as users-model])
  (:import
    [java.util UUID])
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

(def endpoint "/api/users/")

(defn fetch-all [request]
  (let [db (:db request)]
    (response/ok (into #{} (users-model/get-users db)))))

(defn add! [request]
    (let [db (:db request)
          data (-> request :parameters :body)
          id (UUID/randomUUID)
          encrypted (-> data :password (buddy-hashers/encrypt))
          user (-> data
                   (assoc :id id :password encrypted :created (t/instant)))
          _ (users-model/save-user db user)]
      (response/created (str endpoint id) {:id id})))