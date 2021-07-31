(ns torneoencasa.users.core
  (:require
    [reagent.core :as r]
    [re-frame.core :as rf]
    [torneoencasa.auth.events :as auth-events]
    [torneoencasa.components.core :as c]
    [torneoencasa.i18n :refer [app-tr]]
    [torneoencasa.subs :as common-subs]))

(defn sign-up []
  (let [initial-values {:firstname "" :lastname "" :email "" :code ""
                        :username "" :password "" :roles #{:participant}}
        values (r/atom initial-values)]
    (fn []
      [:div.columns
       [:div.column.is-one-third.is-offset-one-third
        [:div
         (if-let [errors (rf/subscribe [::common-subs/errors])]
           [:div.message.is-danger (:message @errors)])]
        [:div
         [c/input-field (app-tr :name) "fa-user" {:type      "text"
                                                  :value     (:firstname @values)
                                                  :on-change #(swap! values assoc :firstname (.. % -target -value))}]
         [c/input-field (app-tr :lastname) "fa-user" {:type      "text"
                                                      :value     (:lastname @values)
                                                      :on-change #(swap! values assoc :lastname (.. % -target -value))}]
         [c/input-field (app-tr :email) "fa-envelope" {:type      "text"
                                                       :value     (:email @values)
                                                       :on-change #(swap! values assoc :email (.. % -target -value))}]
         [c/input-field (app-tr :optional-code) "fa-lock" {:type      "text"
                                                           :value     (:code @values)
                                                           :on-change #(swap! values assoc :code (.. % -target -value))}]
         [:hr]
         [c/input-field (app-tr :username) "fa-user" {:type      "text"
                                                      :value     (:username @values)
                                                      :on-change #(swap! values assoc :username (.. % -target -value))}]
         [c/input-field (app-tr :password) "fa-lock" {:type      "password"
                                                      :value     (:password @values)
                                                      :on-change #(swap! values assoc :password (.. % -target -value))}]
         [:hr]
         [:div.field.is-grouped
          [c/button (app-tr :signup) {:on-click #(rf/dispatch [::auth-events/sign-up @values])}]]]]])))
