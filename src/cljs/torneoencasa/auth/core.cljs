(ns torneoencasa.auth.core
  (:require
    [reagent.core :as r]
    [re-frame.core :as rf]
    [torneoencasa.subs :as common-subs]
    [torneoencasa.auth.events :as auth-events]
    [torneoencasa.components.core :as c]))

(defn sign-in []
  (let [initial-values {:name "" :password ""}
        values (r/atom initial-values)]
    (fn []
    [:div.columns
     [:div.column.is-half.is-offset-one-quarter
      [:div
        (if-let [errors (rf/subscribe [::common-subs/errors])]
          [:div.message.is-danger (:message @errors)])]
     [:div
      [:h2.has-text-centered "Sign in"]
      [:hr]
      [c/input-field "User" "fa-user" {:type "text"
                                       :value (:id @values)
                                       :on-change #(swap! values assoc :id (.. % -target -value))}]
      [c/input-field "Password" "fa-lock" {:type "password"
                                           :value (:password @values)
                                           :on-change #(swap! values assoc :password (.. % -target -value))}]
      [:hr]
      [:div.field.is-grouped
       [c/button "Login" {:on-click #(rf/dispatch [::auth-events/sign-in @values])}]]]]])))

(defn sign-up []
  (let [initial-values {:id "" :firstname "" :lastname "" :password "" :roles #{:candidate}}
        values (r/atom initial-values)]
    (fn []
      [:div.columns
       [:div.column.is-half.is-offset-one-quarter
        [:div
         (if-let [errors (rf/subscribe [::common-subs/errors])]
           [:div.message.is-danger (:message @errors)])]
        [:div
         [:h2.has-text-centered "Sign up"]
         [:hr]
         [c/input-field "User Id" "fa-user" {:type "text"
                                          :value (:id @values)
                                          :on-change #(swap! values assoc :id (.. % -target -value))}]
         [c/input-field "First Name" "fa-user" {:type "text"
                                        :value (:firstname @values)
                                        :on-change #(swap! values assoc :firstname (.. % -target -value))}]
         [c/input-field "Last Name" "fa-user" {:type "text"
                                        :value (:lastname @values)
                                        :on-change #(swap! values assoc :lastname (.. % -target -value))}]
         [c/input-field "Password" "fa-lock" {:type "password"
                                              :value (:password @values)
                                              :on-change #(swap! values assoc :password (.. % -target -value))}]
         [:hr]
         [:div.field.is-grouped
          [c/button "Sign up" {:on-click #(rf/dispatch [::auth-events/sign-up @values])}]]]]])))
