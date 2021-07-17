(ns torneoencasa.api.controllers.auth
  (:require
    [ring.util.http-response :as response]
    [torneoencasa.db.core :as db])
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

(defn check-credentials [db]
  (fn [request]
    (let [{:keys [username]} (-> request :parameters :body)
          [user] (db/get-user-by-username db username)]
      (response/ok
        (assoc-in clean-profile [:user] user)))))
