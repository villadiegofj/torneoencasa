(ns torneoencasa.home.core
  (:require
    [reagent.core :as r]
    [re-frame.core :as rf]
    [torneoencasa.components.core :as c]
    [torneoencasa.i18n :refer [app-tr]]
    [torneoencasa.subsevents :as common-se]
    [torneoencasa.users.subsevents :as users-se]))

#_(defn- main [user]
  [:div.columns
   [:div.column.is-half.is-offset-one-quarter
    (if-let [errors (rf/subscribe [::common-se/errors])]
      [:div.message.is-danger
       [:div (c/list-errors @errors)]])
    [:div
     [:h1.has-text-centered (str (app-tr :welcome) " " (:firstname user))]]
    [:div.box
     [:div.icon-text
      [:span.icon.has-text-info
       [:i.fas.fa-info-circle]]
      [:span (app-tr :information)]]
     [:p (app-tr :messages/download-file-here)]
     [:div.field.is-grouped
      [c/button
       {:label    (app-tr :download)
        :on-click #(rf/dispatch [::users-se/download-file])}]]]]])

(def items [{:id          1
             :description "Lorem ipsum dolor sit amet"
             :link        "/img/some/route/to/one.jpg"
             :type        "Image"
             :created-at  "2021-12-31 13:29:00"}
            {:id          32
             :description "Lorem ipsum dolor sit amet"
             :link        "/img/some/route/to/one.jpg"
             :type        "Image"
             :created-at  "2021-12-31 13:29:00"}
            {:id          42
             :description "Lorem ipsum dolor sit amet"
             :link        "/img/some/route/to/one.jpg"
             :type        "Image"
             :created-at  "2021-12-31 13:29:00"}
            {:id          44
             :description "Lorem ipsum dolor sit amet"
             :link        "/img/some/route/to/one.jpg"
             :type        "Image"
             :created-at  "2021-12-31 13:29:00"}
            {:id          47
             :description "Lorem ipsum dolor sit amet"
             :link        "/img/some/route/to/one.jpg"
             :type        "Image"
             :created-at  "2021-12-31 13:29:00"}
            {:id          54
             :description "Lorem ipsum dolor sit amet"
             :link        "/img/some/route/to/one.jpg"
             :type        "Image"
             :created-at  "2021-12-31 13:29:00"}])

(defn media-table [items]
  [:table.table.is-fullwidth.is-striped
   [:thead
    [:tr
     [:th (app-tr :file)]
     [:th (app-tr :type)]
     [:th (app-tr :date)]
     [:th (app-tr :actions)]]]
   [:tfoot
    [:tr
     [:th (app-tr :file)]
     [:th (app-tr :type)]
     [:th (app-tr :date)]
     [:th (app-tr :actions)]]]
   [:tbody
    (for [{:keys [id description link type created-at]} items]
      [:tr
       [:td
        [:a
         {:title description,
          :href  link}
         description]]
       [:td type]
       [:td created-at]
       [:td [:button.button.is-danger.is-outlined
             {:on-click (str "#" id)}
             [:span.icon.is-small [:i.fas.fa-trash-alt]] ]]])]])

(defn home []
  (let [user (rf/subscribe [::users-se/current-user])
        initial-values {:upload ""}
        values (r/atom initial-values)]
    (fn []
      [:<>
       [:div.columns
        [:div.column.is-one-third.is-offset-one-third
         (if-let [errors (rf/subscribe [::common-se/errors])]
           [:div.message.is-danger
            [:div (c/list-errors @errors)]])]]
       [:div.columns
        [:div.column.is-one-third.is-offset-one-third
         [:h1.has-text-centered (str (app-tr :welcome) " " (:firstname user))]]]

       [:div.columns
        [:div.column.is-one-third.is-offset-one-third
         [:div
          [c/field
           {:id         :upload
            :label      (app-tr :upload)
            :type       "text"
            :icon-class "fa-upload"
            :values     values}]
          [:div.field.is-grouped
           [c/button {:label       (app-tr :ok)
                      :css-classes "is-fullwidth"
                      :on-click    ""}]]]]]
       [:hr]
       #_[:div.columns
        [:div.column.is-10.is-offset-1
         [c/tabs [{:label      (app-tr :pictures)
                   :icon-class "fa-image"
                   :on-click   ""}
                  {:label      (app-tr :audios)
                   :icon-class "fa-music"
                   :on-click   ""}
                  {:label      (app-tr :videos)
                   :icon-class "fa-film"}
                  {:label      (app-tr :documents)
                   :icon-class "fa-file-alt"
                   :on-click   ""}]]]]
       [:div.columns
        [:div.column.is-9.is-offset-1
         [media-table items]]]])))

