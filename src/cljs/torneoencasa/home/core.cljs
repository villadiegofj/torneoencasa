(ns torneoencasa.home.core
  (:require
    [re-frame.core :as rf]
    [torneoencasa.components.core :as c]
    [torneoencasa.i18n :refer [app-tr]]
    [torneoencasa.subsevents :as common-se]
    [torneoencasa.users.subsevents :as users-se]))

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
      [c/button
       {:label    (app-tr :download)
        :on-click #(rf/dispatch [::users-se/download-file])}]]]]])

(defn home []
  (let [user (rf/subscribe [::common-se/user])]
    [:div.columns
     [:div.column.is-two-third
      [main @user]]]))
