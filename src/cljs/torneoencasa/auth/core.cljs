(ns torneoencasa.auth.core
  (:require
    [reagent.core :as r]
    [re-frame.core :as rf]
    [torneoencasa.subs :as common-subs]
    [torneoencasa.auth.events :as auth-events]
    [torneoencasa.nav.events :as nav-events]
    [torneoencasa.components.core :as c]))

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
         [c/input-field "Usuario" "fa-user" {:type      "text"
                                             :value     (:username @values)
                                             :on-change #(swap! values assoc :username (.. % -target -value))}]
         [c/input-field "Clave" "fa-lock" {:type      "password"
                                           :value     (:password @values)
                                           :on-change #(swap! values assoc :password (.. % -target -value))}]
         [:hr]
         [:div.field.is-grouped
          [c/button "Ok" {:on-click #(rf/dispatch [::auth-events/sign-in @values])}]]
         [:div
          [:hr]
          [:p.has-text-centered "No tienes cuenta? "
           [:a {:on-click #(rf/dispatch [::nav-events/set-active-nav :sign-up])} "Inscríbete aquí"]]]]]])))
