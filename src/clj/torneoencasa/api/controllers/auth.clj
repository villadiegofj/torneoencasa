(ns torneoencasa.api.controllers.auth
  (:require
    [clojure.pprint :as p]
    [ring.util.http-response :as response]
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
  (let [params (-> reqs :parameters :body)
        db (-> reqs :reitit.core/match :data :db)]
    (response/ok (db/get-user-by-id db params))))

#_(defn handler [{{{:keys [id password]} :body} :parameters}]
  (response/ok (db/retrieve id password)))

