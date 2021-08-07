(ns torneoencasa.users.subsevents
  (:require
    [ajax.core :as ajax]
    [day8.re-frame.http-fx]                                 ;; not used but causes :http-xhrio to self-register
    [re-frame.core :as rf]
    [torneoencasa.errors :refer [lookup-error]]
    [torneoencasa.nav.subsevents :as nav-se]
    [torneoencasa.subsevents :as common-se]
    [torneoencasa.i18n :refer [app-tr]]))

;; events
(rf/reg-event-db
  ::set-user
  (fn [db [_ data]]
    (assoc db :user data)))

(rf/reg-event-fx
  ::sign-up
  ;;[cofx event]
  (fn [{:keys [db]} [_ user]]
    {:db         (-> db
                     (assoc :auth false)
                     (assoc-in [:user] user)
                     (assoc-in [:nav :active-page] :sign-in)
                     (assoc-in [:errors] nil))
     :http-xhrio {:method          :post
                  :uri             (common-se/endpoint "users")
                  :params          user
                  :format          (ajax/json-request-format)
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success      [::api-sign-up-success]
                  :on-failure      [::common-se/api-request-error :sign-up]}}))

(rf/reg-event-fx
  ::api-sign-up-success
  (fn [{:keys [db]} [_ result]]
    {:db         result
     :dispatch-n [[::nav-se/set-active-nav :sign-in]]}))

(rf/reg-event-fx
  ::download-file
  (fn [{:keys [db]} [_ _]]
    {:db         (-> db (update-in [:errors] dissoc :message))
     :http-xhrio {:method          :get
                  :uri             (common-se/endpoint "users/report")
                  :response-format {:description  "file-download"
                                    :content-type "text/csv"
                                    :type         :blob
                                    :read         ajax.protocols/-body}
                  :on-success      [::download-file-ok]
                  :on-failure      [::download-file-error]}}))

(defn download-file!
  [data content-type file-name]
  (let [data-blob (js/Blob. #js [data] #js {:type content-type})
        link (js/document.createElement "a")]
    (set! (.-href link) (js/URL.createObjectURL data-blob))
    (.setAttribute link "download" file-name)
    (js/document.body.appendChild link)
    (.click link)
    (js/document.body.removeChild link)))

(rf/reg-event-fx
  ::download-file-ok
  (fn [_ [_ result]]
    (download-file! result "text/csv" "users.csv")
    {}))

(rf/reg-event-fx
  ::download-file-error
  (fn [{:keys [db]} [_ result]]
    {:db (assoc db :errors :e900 (lookup-error :e900))}))


