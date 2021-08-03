(ns torneoencasa.api.schemas)

(def error
  [:map
   [:error-id keyword?]])

(def creds
  [:map
   [:username string?]
   [:password string?]])

(def user
  [:map
   [:firstname string?]
   [:lastname string?]
   [:email string?]
   [:code string?]
   [:username string?]
   [:password {:optional true} string?]
   [:role string?]])

(def users
  [:set [:map
         [:id uuid?]
         [:firstname string?]
         [:lastname string?]
         [:email string?]
         [:code string?]
         [:username string?]
         [:role string?]
         [:created inst?]]])
