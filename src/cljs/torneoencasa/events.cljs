(ns torneoencasa.events
  (:require
   [re-frame.core :as rf]
   [torneoencasa.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced]]))

(rf/reg-event-db
 ::initialize-db
 (fn [db _]
   db/default-db))

(rf/reg-event-fx
 ::printlog
 (fn [coeffects event]
   (let [data (second event)
         db (:db coeffects)]
     (js/console.log "data: " data ", db: " db)
     {:db db})))
