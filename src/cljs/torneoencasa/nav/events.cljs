(ns torneoencasa.nav.events
  (:require
   [re-frame.core :as rf]
   [day8.re-frame.tracing :refer-macros [fn-traced]]))

(rf/reg-event-fx
 ::set-active-nav
 (fn [{:keys [db]} [_ data]]
   (let [arg data]
     {:db (assoc-in db [:nav :active-page] arg)})))
