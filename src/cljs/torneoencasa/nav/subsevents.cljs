(ns torneoencasa.nav.subsevents
  (:require
   [re-frame.core :as rf]
   [day8.re-frame.tracing :refer-macros [fn-traced]]))

;;;;;;;;;;;;;;;;;;;;;;;;
;; events
;;;;;;;;;;;;;;;;;;;;;;;;

(rf/reg-event-fx
 ::set-active-nav
 (fn [{:keys [db]} [_ data]]
   (let [arg data]
     {:db (assoc-in db [:nav :active-page] arg)})))

;;;;;;;;;;;;;;;;;;;;;;;;
;; subscriptions
;;;;;;;;;;;;;;;;;;;;;;;;

(rf/reg-sub
  ::active-page
  (fn [db _]
    (:active-page (:nav db))))
