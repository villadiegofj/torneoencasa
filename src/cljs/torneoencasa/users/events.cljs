(ns torneoencasa.users.events
  (:require
   [re-frame.core :as rf]
   [day8.re-frame.http-fx] ;; not used but causes :http-xhrio to self-register
   [torneoencasa.events :as events]
   [torneoencasa.i18n :refer [app-tr]]))

(rf/reg-event-fx
 ::download-file
 (fn [{:keys [db]} [_ _]]
   {:db (-> db (update-in [:errors] dissoc :message))
    :http-xhrio {:method          :get
                 :uri             (events/endpoint "users/report")
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
  ::download-file-ok
  (fn [_ [_ result]]
    (download-file! result "text/csv" "users.csv")
    {}))

(rf/reg-event-fx
  ::download-file-error
  (fn [{:keys [db]} [_ result]]
    {:db (assoc db :errors {:eXXX (str "no pude conseguir el archivo: " result)})}))

