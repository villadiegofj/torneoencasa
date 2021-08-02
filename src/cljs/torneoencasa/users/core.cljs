(ns torneoencasa.users.core
  (:require
    [reagent.core :as r]
    [re-frame.core :as rf]
    [torneoencasa.components.core :as c]
    [torneoencasa.i18n :refer [app-tr]]
    [torneoencasa.subsevents :as common-se]
    [torneoencasa.users.subsevents :as users-se]))

(defn sign-up []
  (let [initial-values {:firstname "" :lastname "" :email "" :code ""
                        :username  "" :password "" :role "participant"}
        values (r/atom initial-values)]
    (fn []
      [:div.columns
       [:div.column.is-one-third.is-offset-one-third
        [:div
         (if-let [errors (rf/subscribe [::common-se/errors])]
           [:div.message.is-danger (:message @errors)])]
        [:div
         [c/field
          {:id :firstname
           :label (app-tr :name)
           :type      "text"
           :icon-class "fa-id-card"
           :values values}]
         [c/field
          {:id :lastname
           :label (app-tr :lastname)
           :type      "text"
           :icon-class "fa-signature"
           :values values}]
         [c/field
          {:id :email
           :label (app-tr :email)
           :type      "text"
           :icon-class "fa-envelope"
           :values values}]
         [c/field
          {:id :code
           :label (app-tr :pass-code)
           :type      "text"
           :icon-class "fa-ticket-alt"
           :help-text (app-tr :optional)
           :values values}]
         [:hr]
         [c/field
          {:id :username
           :label (app-tr :username)
           :type      "text"
           :icon-class "fa-user"
           :values values}]
         [c/field
          {:id :password
           :label (app-tr :password)
           :type      "password"
           :icon-class "fa-lock"
           :values values}]
         [:hr]
         [:div.field.is-grouped
          [c/button {:label (app-tr :signup)
                     :css-classes "is-fullwidth"
                     :on-click #(rf/dispatch [::users-se/sign-up @values])}]]]]])))
