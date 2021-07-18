(ns torneoencasa.api.controllers.auth
  (:require
    [buddy.hashers :as buddy-hashers]
    [ring.util.http-response :as response]
    [torneoencasa.api.db.users :as users-model])
  (:import
    [java.util UUID])
  (:gen-class))

(def creds-schema
  [:map
   [:username string?]
   [:password string?]])

(def profile-schema
  [:map
   [:auth boolean?]
   [:errors {:optional true} map?]
   [:nav [:map
          [:active-page [:enum :sign-in :home]]]]
   [:user [:map
           [:id uuid?]
           [:username string?]
           [:firstname string?]
           [:lastname string?]
           [:email string?]
           [:code string?]
           [:password string?]
           [:role string?]]]
   [:items [:set [:map
                  [:id string?]
                  [:name string?]
                  [:events vector?]]]]])

(def clean-profile
  {:auth  true
   :errors {}
   :nav   {:active-page :sign-in}
   :user  {:id        (UUID/randomUUID)
           :firstname ""
           :lastname  ""
           :email     ""
           :code      ""
           :username  ""
           :password  ""
           :role      ""}
   :items #{{:id "" :name "" :events []}}})

(defn check-credentials [request]
  (let [db (:db request)
        {:keys [username password]} (-> request :parameters :body)
        record (users-model/get-user-by-username db username)]
    (if-not (seq record)
      (response/not-found {:error   404
                           :message (str "not found:" username)})
      (let [[user] record
            authorized? (buddy-hashers/check password (:password user))]
        (if authorized?
          (response/ok (assoc-in clean-profile [:user] user))
          (response/unauthorized {:error   401
                                  :message (str "unauthorized access for:" username)}))))))
