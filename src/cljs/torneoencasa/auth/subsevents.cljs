(ns torneoencasa.auth.subsevents
  (:require
    [ajax.core :as ajax]
    [day8.re-frame.http-fx] ;; not used but causes :http-xhrio to self-register
    [re-frame.core :as rf]
    [torneoencasa.subsevents :as common-se]
    [torneoencasa.i18n :refer [app-tr]]
    [torneoencasa.nav.subsevents :as nav-se]
    [torneoencasa.users.subsevents :as users-se]))

;; events
(rf/reg-event-fx
 ::sign-in
 (fn [{:keys [db]} [_ {:keys [username password]}]]
   {:db (-> db (update-in [:errors] dissoc :message))
    :http-xhrio {:method          :post
                 :uri             (common-se/endpoint "auth")
                 :params          {:username username
                                   :password password}
                 :format          (ajax/json-request-format)
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success      [::api-sign-in-success]
                 :on-failure      [::common-se/api-request-error]}}))

(rf/reg-event-fx
  ::api-sign-in-success
  (fn [_ [_ result]]
    {:db         result
     :dispatch-n [[::users-se/set-user result]
                  [::nav-se/set-active-nav :home]]}))

;; subscriptions
(rf/reg-sub
  ::current-user
  (fn [db _]
    (:user db)))
