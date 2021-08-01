(ns torneoencasa.auth.subsevents
  (:require
    [ajax.core :as ajax]
    [day8.re-frame.http-fx] ;; not used but causes :http-xhrio to self-register
    [re-frame.core :as rf]
    [torneoencasa.errors :refer [lookup-error]]
    [torneoencasa.subsevents :as events]
    [torneoencasa.i18n :refer [app-tr]]
    [torneoencasa.nav.subsevents :as nav-events]))

;; events
(rf/reg-event-db
  ::api-request-error
  (fn [db [_ result]]
    (let [code (-> result
                   :response
                   :error-id
                   (keyword))]
    (assoc db :errors {code (lookup-error code)}))))

(rf/reg-event-fx
 ::sign-in
 (fn [{:keys [db]} [_ {:keys [username password]}]]
   {:db (-> db (update-in [:errors] dissoc :message))
    :http-xhrio {:method          :post
                 :uri             (events/endpoint "auth")
                 :params          {:username username
                                   :password password}
                 :format          (ajax/json-request-format)
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success      [::api-sign-in-success]
                 :on-failure      [::api-request-error]}}))

(rf/reg-event-fx
  ::api-sign-in-success
  (fn [{:keys [db]} [_ result]]
    {:db         result
     :dispatch-n [[::nav-events/set-active-nav :home]]}))

;; subscriptions
(rf/reg-sub
  ::current-auth
  (fn [db _]
    (:auth db)))

(rf/reg-sub
  ::current-user
  (fn [db _]
    (:user db)))
