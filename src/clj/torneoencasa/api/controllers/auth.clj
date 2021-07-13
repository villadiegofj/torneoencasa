(ns torneoencasa.api.controllers.auth
  (:require
    [ring.util.http-response :as response]
    [torneoencasa.db.core :as db])
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
          [:active-page [:enum :login :home]]]]
   [:user [:map
           [:id uuid?]
           [:username string?]
           [:firstname string?]
           [:lastname string?]
           [:email string?]
           [:password string?]
           [:role string?]]]
   [:items [:set [:map
                  [:id string?]
                  [:name string?]
                  [:events vector?]]]]])

(def clean-profile
  {:auth  true
   :errors {}
   :nav   {:active-page :login}
   :user  {:id        (java.util.UUID/randomUUID)
           :username  ""
           :firstname ""
           :lastname  ""
           :email     ""
           :password  ""
           :role      ""}
   :items #{{:id "" :name "" :events []}}})

(defn handler [reqs]
  (let [{:keys [username]} (-> reqs :parameters :body)
        ds (-> reqs :reitit.core/match :data :db :datasource)
        [user] (db/get-user-by-username ds username)]
    (response/ok
      (assoc-in clean-profile [:user] user))))
