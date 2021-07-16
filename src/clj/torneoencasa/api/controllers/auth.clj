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
   :user  {:id        (java.util.UUID/randomUUID)
           :firstname ""
           :lastname  ""
           :email     ""
           :code      ""
           :username  ""
           :password  ""
           :role      ""}
   :items #{{:id "" :name "" :events []}}})

(defn handler [reqs]
  (let [{:keys [username]} (-> reqs :parameters :body)
        ds (-> reqs :reitit.core/match :data :db :datasource)
        [user] (db/get-user-by-username ds username)]
    (response/ok
      (assoc-in clean-profile [:user] user))))
