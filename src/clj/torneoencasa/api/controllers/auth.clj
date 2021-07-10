(ns torneoencasa.api.controllers.auth
  (:require [ring.util.http-response :as response]
            [torneoencasa.db.core :as db])
  (:gen-class))

(def creds-schema
  [:map [:id string?]
   [:password string?]])

(def profile-schema
  [:map
   [:auth boolean?]
   [:errors {:optional true} map?]
   [:nav [:map
          [:active-page [:enum :login :home]]]]
   [:user [:map
           [:id string?]
           [:firstname string?]
           [:lastname string?]
           [:roles [:set keyword?]]
           [:password string?]]]
   [:items [:set [:map
                  [:id string?]
                  [:name string?]
                  [:events vector?]]]]])

(defn handler [reqs]
  (println reqs))

#_(defn handler [{{{:keys [id password]} :body} :parameters}]
  (response/ok (db/retrieve id password)))

