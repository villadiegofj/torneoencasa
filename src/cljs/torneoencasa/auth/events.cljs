(ns torneoencasa.auth.events
  (:require
   [clojure.string :as str]
   [re-frame.core :as rf]
   [day8.re-frame.http-fx] ;; not used but causes :http-xhrio to self-register
   [ajax.core :as ajax]
   [torneoencasa.nav.events :as nav-events]))

(def api-url "/api")

(defn endpoint
  "Concat any params to api-url separated by /"
  [& params]
  (str/join "/" (concat [api-url] params))) 

(defn lookup-error [code]
  (let [m {:e401 "unauthorized access"
           :e403 "you don't have access"
           :e404 "resource not found"}
        message (m code)]
    (if (nil? message)
      "error found"
      message)))

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
                 :uri             (endpoint "auth")
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

(rf/reg-event-fx
  ::sign-up
  ;;[cofx event]
  (fn [{:keys [db]} [_ user]]
    {:db (-> db
             (assoc :auth false)
             (assoc-in [:user] user)
             (assoc-in [:nav :active-page] :sign-in)
             (assoc-in [:errors] nil))
     :http-xhrio {:method          :post
                  :uri             (endpoint "users")
                  :params          user
                  :format          (ajax/json-request-format)
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success      [::api-sign-up-success]
                  :on-failure      [::api-request-error :sign-up]}}))

(rf/reg-event-fx
  ::api-sign-up-success
  (fn [{:keys [db]} [_ result]]
    {:db         result
     :dispatch-n [[::nav-events/set-active-nav :sign-in]]}))

