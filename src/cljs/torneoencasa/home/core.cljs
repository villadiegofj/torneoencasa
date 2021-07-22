(ns torneoencasa.home.core
  (:require
   [re-frame.core :as rf]
   [torneoencasa.i18n :refer [app-tr]]
   [torneoencasa.nav.subs :as nav-subs]
   [torneoencasa.subs :as common-subs]))

(defn main [user]
  [:div.columns
   [:div.column.is-half.is-offset-one-quarter
    [:div
     [:h1.has-text-centered (str (app-tr :welcome) " " user)]]]])

(defn home []
  (let [user (rf/subscribe [::common-subs/user])
        active-page (rf/subscribe [::nav-subs/active-page])]
    [:div.columns
     [:div.column.is-two-third
      [main user]]]))
