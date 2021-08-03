(ns torneoencasa.api.controllers.auth
  (:require
    [buddy.hashers :as hashers]
    [ring.util.http-response :as response]
    [torneoencasa.api.db.users :as users-model])
  (:gen-class))

(defn check-credentials [request]
  (let [db (:db request)
        {:keys [username password]} (-> request :parameters :body)
        record (users-model/get-user-by-username db username)]
    (if-not (seq record)
      (response/not-found {:error-id :e404})
      (let [[user] record
            authorized? (hashers/check password (:password user))]
        (if authorized?
          (response/ok (dissoc user :password))
          (response/unauthorized {:error-id :e401}))))))
