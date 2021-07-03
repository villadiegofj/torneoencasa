(ns torneoencasa.auth.subs
  (:require
   [re-frame.core :as rf]))

(rf/reg-sub
 ::current-auth
 (fn [db _]
   (:auth db)))

(rf/reg-sub
 ::current-user
 (fn [db _]
   (:user db)))
