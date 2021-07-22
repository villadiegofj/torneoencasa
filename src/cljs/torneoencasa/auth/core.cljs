(ns torneoencasa.auth.core
  (:require
    [reagent.core :as r]
    [re-frame.core :as rf]
    [torneoencasa.auth.events :as auth-events]
    [torneoencasa.components.core :as c]
    [torneoencasa.i18n :refer [app-tr]]
    [torneoencasa.nav.events :as nav-events]
    [torneoencasa.subs :as common-subs]))

(defn sign-in []
  (let [initial-values {:username "" :password ""}
        values (r/atom initial-values)]
    (fn []
      [:div.columns
       [:div.column.is-one-third.is-offset-one-third
        (if-let [errors (rf/subscribe [::common-subs/errors])]
          [:div.message.is-danger
           [:div (c/list-errors @errors)]])
        [:div
         [c/input-field (app-tr :username) "fa-user" {:type      "text"
                                             :value     (:username @values)
                                             :on-change #(swap! values assoc :username (.. % -target -value))}]
         [c/input-field (app-tr :password) "fa-lock" {:type      "password"
                                           :value     (:password @values)
                                           :on-change #(swap! values assoc :password (.. % -target -value))}]
         [:hr]
         [:div.field.is-grouped
          [c/button (app-tr :ok) {:on-click #(rf/dispatch [::auth-events/sign-in @values])}]]
         [:div
          [:hr]
          [:p.has-text-centered (str (app-tr :messages/no-account?) " ")
           [:a {:on-click #(rf/dispatch [::nav-events/set-active-nav :sign-up])} (app-tr :messages/sign-up!)]]]]]])))
