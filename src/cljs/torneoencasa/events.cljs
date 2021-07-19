(ns torneoencasa.events
  (:require
   [day8.re-frame.tracing :refer-macros [fn-traced]]
   [re-frame.core :as rf]
   [torneoencasa.users.core :as users]))

(rf/reg-event-db
 ::initialize-db
 (fn [_ _]:w
   users/default-db))

(rf/reg-event-fx
 ::printlog
 (fn [coeffects event]
   (let [db (:db coeffects)]
     {:db db})))
