(ns torneoencasa.auth.events
  (:require
   [clojure.string :as str]
   [re-frame.core :as rf]
   [day8.re-frame.http-fx] ;; not used but causes :http-xhrio to self-register
   [ajax.core :as ajax]
   [torneoencasa.nav.events :as nav-events]))

(def api-url "http://localhost:8080/api")

(defn endpoint
  "Concat any params to api-url separated by /"
  [& params]
  (str/join "/" (concat [api-url] params))) 

(rf/reg-event-fx
 ::sign-up
 ;;[cofx event]
 (fn [{:keys [db]} [_ {:keys [id firstname lastname password roles]}]]
   {:db (-> db
     (assoc :auth true)
     (assoc-in [:nav :active-page] :home)
     (assoc-in [:user :id] id)
     (assoc-in [:user :firstname] firstname)
     (assoc-in [:user :lastname] lastname)
     (assoc-in [:user :password] password)
     (assoc-in [:user :roles] roles)
     (update-in [:errors] dissoc :message))}))

(rf/reg-event-fx
  ::api-sign-in-success
  (fn [{:keys [db]} [_ result]]
    {:db         result
     :dispatch-n [[::nav-events/set-active-nav :home]]}))

(rf/reg-event-db
  ::api-request-error
  (fn [db [_ result]]
    (assoc-in db [:errors :message] result)))

(rf/reg-event-fx
 ::sign-in
 (fn [{:keys [db]} [_ data]]
   {:db (-> db
            (assoc :auth true)
            (assoc-in [:nav :active-page] :home)
            (update-in [:errors] dissoc :message))
    :http-xhrio {:method          :post
                 :uri             (endpoint "auth")
                 :params          {:id (:id data)
                                   :password (:password data)}
                 :format          (ajax/json-request-format)
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success      [::api-sign-in-success]
                 :on-failure      [::api-request-error :sign-in]}}))

