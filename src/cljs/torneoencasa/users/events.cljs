(ns torneoencasa.users.events
  (:require
   [clojure.string :as str]
   [re-frame.core :as rf]
   [day8.re-frame.http-fx] ;; not used but causes :http-xhrio to self-register
   [ajax.core :as ajax]
   [torneoencasa.i18n :refer [app-tr]]
   [torneoencasa.nav.events :as nav-events]))

(rf/reg-event-fx
 ::download-file
 (fn [{:keys [db]} [_ _]]
   {:db (-> db (update-in [:errors] dissoc :message))
    :http-xhrio {:method          :get
                 :uri             "/api/users/report"
                 :response-format {:description "file-download"
                                   :content-type "text/csv"
                                   :type :blob
                                   :read ajax.protocols/-body}
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
  ::download-file-ok-orig
  (fn [_ [_ result]]
    (download-file! result "text/csv" "users.csv")
    {}))

(rf/reg-event-fx
  ::download-file-ok
  (fn [{:keys [db]} [_ result]]
    (js/console.log "all good while retrieving file")
    {:db (assoc db :download-file-result result)}))

(rf/reg-event-fx
  ::download-file-error
  (fn [{:keys [db]} [_ result]]
    (js/console.log "error found while retrieving file")
    {:db (assoc db :errors {:eXXX (str  "no pude conseguir el archivo" result)})}))

