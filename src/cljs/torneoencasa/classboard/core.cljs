(ns torneoencasa.classboard.core
  (:require
   [re-frame.core :as rf]))

(defn box []
  [:div {:class "box"}
   [:article {:class "media"}
    [:div {:class "media-left"}
     [:figure {:class "image is-64x64"}
      [:img {:src "https://bulma.io/images/placeholders/128x128.png", :alt "Image"}]]]
    [:div {:class "media-content"}
     [:div {:class "content"}
      [:p
       [:strong "Reading"]
       [:small "infant"]
       [:small "3m"]
       [:br] "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean efficitur sit amet massa fringilla egestas. Nullam condimentum luctus turpis."]]
     [:nav {:class "level is-mobile"}
      [:div {:class "level-left"}
       [:a {:class "level-item", :aria-label "reply"}
        [:span {:class "icon is-small"}
         [:i {:class "fas fa-reply", :aria-hidden "true"}]]]
       [:a {:class "level-item", :aria-label "retweet"}
        [:span {:class "icon is-small"}
         [:i {:class "fas fa-retweet", :aria-hidden "true"}]]]
       [:a {:class "level-item", :aria-label "like"}
        [:span {:class "icon is-small"}
         [:i {:class "fas fa-heart", :aria-hidden "true"}]]]]]]]])

(defn classboard []
  [:div
  [:section.hero
   [:div.hero-body
    [:container
     [:h1.title "Classboard"]
     [:h2.subtitle "hero subtitle"]]]]
   [box]
   [box]
   [box]])
