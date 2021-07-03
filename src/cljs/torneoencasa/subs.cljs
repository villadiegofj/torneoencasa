(ns torneoencasa.subs
  (:require
   [re-frame.core :as rf]))

(rf/reg-sub
  ::user
  (fn [db _]
    (:user db)))

(rf/reg-sub
  ::errors
  (fn [db _]
    (:errors db)))

