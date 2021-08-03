(ns torneoencasa.api.controllers.users
  (:require
    [buddy.hashers :as buddy-hashers]
    [java-time :as t]
    [ring.util.http-response :as response]
    [torneoencasa.api.db.users :as users-model]
    [torneoencasa.api.formats :as formats])
  (:import
    [java.util UUID])
  (:gen-class))

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

(defn report [request]
  (let [response-format (formats/mime->keyword (get-in request [:headers "accept"] "text/plain"))
        disposition     (str "attachment; filename=\"users." (name response-format) "\"")
        users           (users-model/get-users (:db request))]
    (-> (formats/create-representation users response-format)
        (response/ok)
        (merge {:headers {"Content-Type" (formats/content-type response-format)
                          "Content-Disposition" disposition}}))))

