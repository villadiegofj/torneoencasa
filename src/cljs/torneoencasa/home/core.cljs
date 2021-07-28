(ns torneoencasa.home.core
  (:require
   [re-frame.core :as rf]
   [torneoencasa.components.core :as c]
   [torneoencasa.i18n :refer [app-tr]]
   [torneoencasa.nav.subs :as nav-subs]
   [torneoencasa.subs :as common-subs]))

(defn main [user]
  [:div.columns
   [:div.column.is-half.is-offset-one-quarter
    [:div
     [:h1.has-text-centered (str (app-tr :welcome) " " user)]]
         [:div.field.is-grouped
          [c/button (app-tr :download) {:on-click #(rf/dispatch [::auth-events/sign-in @values])}]]
    ]])

(defn home []
  (let [user (rf/subscribe [::common-subs/user])
        active-page (rf/subscribe [::nav-subs/active-page])]
    [:div.columns
     [:div.column.is-two-third
      [main @user]]]))
