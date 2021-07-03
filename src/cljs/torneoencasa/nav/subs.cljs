(ns torneoencasa.nav.subs
  (:require
   [re-frame.core :as rf]))

(rf/reg-sub
 ::active-page
 (fn [db _]
   (:active-page (:nav db))))
