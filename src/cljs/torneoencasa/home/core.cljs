(ns torneoencasa.home.core
  (:require
   [re-frame.core :as rf]
   [torneoencasa.users.events :as users-events]
   [torneoencasa.components.core :as c]
   [torneoencasa.i18n :refer [app-tr]]
   [torneoencasa.nav.subs :as nav-subs]
   [torneoencasa.subs :as common-subs]))

(defn main [user]
  [:div.columns
   [:div.column.is-half.is-offset-one-quarter
    [:div
     [:h1.has-text-centered (str (app-tr :welcome) " " user)]]
    [:div.box
     [:div.icon-text
      [:span.icon.has-text-info
       [:i.fas.fa-info-circle]]
      [:span (app-tr :information)]]
     [:p (app-tr :messages/download-file-here)]
     [:div.field.is-grouped
      [c/button (app-tr :download)
       {:on-click #(rf/dispatch [::users-events/download-file])}]]]]])

(defn home []
  (let [user (rf/subscribe [::common-subs/user])
        active-page (rf/subscribe [::nav-subs/active-page])]
    [:div.columns
     [:div.column.is-two-third
      [main @user]]]))
