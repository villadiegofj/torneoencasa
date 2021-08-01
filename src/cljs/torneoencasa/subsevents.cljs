(ns torneoencasa.subsevents
  (:require
   [clojure.string :as str]
   [day8.re-frame.tracing :refer-macros [fn-traced]]
   [re-frame.core :as rf]
   [torneoencasa.errors :refer [lookup-error]]))

(def api-url "/api")

(defn endpoint
  "Concat any params to api-url separated by /"
  [& params]
  (str/join "/" (concat [api-url] params)))

(def default-db
  {:auth false
   :errors nil
   :nav {:active-page :sign-in}
   :user {:id ""
          :firstname ""
          :lastname ""
          :email ""
          :code ""
          :username ""
          :password ""
          :roles #{:visitor}}
   :items #{}})

;; events
(rf/reg-event-db
 ::initialize-db
 (fn [_ _]
   default-db))

(rf/reg-event-db
  ::api-request-error
  (fn [db [_ result]]
    (let [code (-> result
                   :response
                   :error-id
                   (keyword))]
      (assoc db :errors {code (lookup-error code)}))))

;; subscriptions
(rf/reg-sub
  ::user
  (fn [db _]
    (:user db)))

(rf/reg-sub
  ::errors
  (fn [db _]
    (:errors db)))

