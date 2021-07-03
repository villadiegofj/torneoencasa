(ns torneoencasa.events
  (:require
   [re-frame.core :as rf]
   [day8.re-frame.tracing :refer-macros [fn-traced]]))

(def default-db
  {:auth false
   :errors {}
   :nav {:active-page :sign-in}
   :user {:id ""
          :firstname ""
          :lastname ""
          :roles #{:anonymous}
          :password ""}
   :items #{}})

(rf/reg-event-db
 ::initialize-db
 (fn [db _]
   default-db))

(rf/reg-event-fx
 ::printlog
 (fn [coeffects event]
   (let [data (second event)
         db (:db coeffects)]
     {:db db})))
