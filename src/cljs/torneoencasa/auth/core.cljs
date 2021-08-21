(ns torneoencasa.auth.core
  (:require
    [reagent.core :as r]
    [re-frame.core :as rf]
    [torneoencasa.auth.subsevents :as auth-se]
    [torneoencasa.components.core :as c]
    [torneoencasa.i18n :refer [app-tr]]
    [torneoencasa.nav.subsevents :as nav-se]
    [torneoencasa.subsevents :as common-se]))

(defn sign-in []
  (let [initial-values {:username "" :password ""}
        values (r/atom initial-values)]
    (fn []
      [:div.columns
       [:div.column.is-one-third.is-offset-one-third
        (if-let [errors (rf/subscribe [::common-se/errors])]
          [:div.message.is-danger
           [:div (c/list-errors @errors)]])
        [:div
         [c/field
          {:id         :username
           :label      (app-tr :username)
           :type       "text"
           :icon-class "fa-user"
           :values     values}]
         [c/field
          {:id         :password
           :label      (app-tr :password)
           :type       "password"
           :icon-class "fa-lock"
           :values     values}]
         [:hr]
         [:div.field.is-grouped
          [c/button {:label       (app-tr :ok)
                     :css-classes "is-fullwidth"
                     :on-click    #(rf/dispatch [::auth-se/sign-in @values])}]]
         [:div
          [:hr]
          [:p.has-text-centered (str (app-tr :messages/no-account?) " ")
           [c/link {:text     (app-tr :messages/sign-up!)
                    :on-click #(rf/dispatch [::nav-se/set-active-nav :sign-up])}]]]]]])))

