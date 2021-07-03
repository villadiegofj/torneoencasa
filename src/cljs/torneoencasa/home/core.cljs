(ns torneoencasa.home.core
  (:require
   [re-frame.core :as rf]
   [torneoencasa.subs :as common-subs]
   [torneoencasa.nav.subs :as nav-subs]
   [torneoencasa.routes :refer [url-for]]))

(defn panel []
  [:nav {:class "panel"}
   [:p {:class "panel-heading"}]
   [:div {:class "panel-block"}
    [:p {:class "control has-icons-left"}]]
   [:p {:class "panel-tabs"}
    [:a "All"]
    [:a {:class "is-active"} "Assignments"]
    [:a "Resources"]
    [:a "Notifications"]]
   [:a {:class "panel-block"}
    [:span {:class "panel-icon"}
     [:i {:class "fas fa-book", :aria-hidden "true"}]] "An infomercial about your favorite toy"]
   [:a {:class "panel-block"}
    [:span {:class "panel-icon"}
     [:i {:class "fas fa-book", :aria-hidden "true"}]] "A 280 word essay about independence day"]
   [:a {:class "panel-block"}
    [:span {:class "panel-icon"}
     [:i {:class "fas fa-code-branch", :aria-hidden "true"}]] "Summary of audio story about the colored fish"]
   [:a {:class "panel-block"}
    [:span {:class "panel-icon"}
     [:i {:class "fas fa-code-branch", :aria-hidden "true"}]]]])

(defn text []
  [:article {:class "media"}
    [:figure {:class "media-left"}
      [:p {:class "image is-64x64"}
        [:img {:src "https://bulma.io/images/placeholders/128x128.png"}]]]
    [:div {:class "media-content"}
      [:div {:class "field"}
        [:p {:class "control"}
          [:textarea {:class "textarea", :placeholder "Once upon a time..."}]]]
        [:nav {:class "level"}
          [:div {:class "level-left"}
            [:div {:class "level-item"}
              [:a {:class "button is-warning"} "Save Draft"]]
           [:div {:class "level-item"}
            [:a {:class "button is-info"} "Submit"]]]
          [:div {:class "level-right"}
            [:div {:class "level-item"}
              [:a {:class "button is-danger"} "Delete"]]]]]])

(defn home []
  (let [user (rf/subscribe [::common-subs/user])
        active-page (rf/subscribe [::nav-subs/active-page])]
    [:div.columns
     [:div.column.is-two-third
      [panel]]]))
