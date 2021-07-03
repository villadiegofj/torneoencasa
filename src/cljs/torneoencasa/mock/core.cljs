(ns torneoencasa.mock.core
  (:require
   [re-frame.core :as rf]
   [torneoencasa.subs :as common-subs]
   [torneoencasa.routes :refer [url-for]]))

(defn tabs []
  [:div {:class "tabs is-toggle is-toggle-rounded"}
   [:ul
    [:li {:class "is-active"}
     [:a
      [:span {:class "icon is-small"}
       [:i {:class "fas fa-image"}]]
      [:span "Pictures"]]]
    [:li
     [:a
      [:span {:class "icon is-small"}
       [:i {:class "fas fa-music"}]]
      [:span "Music"]]]
    [:li
     [:a
      [:span {:class "icon is-small"}
       [:i {:class "fas fa-film"}]]
      [:span "Videos"]]]
    [:li
     [:a
      [:span {:class "icon is-small"}
       [:i {:class "fas fa-file-alt"}]]
      [:span "Documents"]]]]])

(defn card []
  [:div {:class "card"}
   [:div {:class "card-image"}
    [:figure {:class "image is-4by3"}
     [:img {:src "https://bulma.io/images/placeholders/1280x960.png", :alt "Placeholder image"}]]]
   [:div {:class "card-content"}
    [:div {:class "media"}
     [:div {:class "media-left"}
      [:figure {:class "image is-48x48"}
       [:img {:src "https://bulma.io/images/placeholders/96x96.png", :alt "Placeholder image"}]]]
     [:div {:class "media-content"}
      [:p {:class "title is-4"} "John Smith"]
      [:p {:class "subtitle is-6"} "@johnsmith"]]]
    [:div {:class "content"} "Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n      Phasellus nec iaculis mauris. "
     [:a "@bulmaio"] "."
     [:a {:href "#"} "#css"]
     [:a {:href "#"} "#responsive"]
     [:br]
     [:time {:datetime "2016-1-1"} "11:09 PM - 1 Jan 2016"]]]])

(defn panel []
  [:nav {:class "panel"}
   [:p {:class "panel-heading"}]
   [:div {:class "panel-block"}
    [:p {:class "control has-icons-left"}
     [:input {:class "input", :type "text", :placeholder "Search"}]
     [:span {:class "icon is-left"}
      [:i {:class "fas fa-search", :aria-hidden "true"}]]]]
   [:p {:class "panel-tabs"}
    [:a {:class "is-active"} "All"]
    [:a "Public"]
    [:a "Private"]
    [:a "Sources"]
    [:a "Forks"]]
   [:a {:class "panel-block is-active"}
    [:span {:class "panel-icon"}
     [:i {:class "fas fa-book", :aria-hidden "true"}]]]
   [:a {:class "panel-block"}
    [:span {:class "panel-icon"}
     [:i {:class "fas fa-book", :aria-hidden "true"}]]]
   [:a {:class "panel-block"}
    [:span {:class "panel-icon"}
     [:i {:class "fas fa-book", :aria-hidden "true"}]] "minireset.css"]
   [:a {:class "panel-block"}
    [:span {:class "panel-icon"}
     [:i {:class "fas fa-book", :aria-hidden "true"}]] "jgthms.github.io"]
   [:a {:class "panel-block"}
    [:span {:class "panel-icon"}
     [:i {:class "fas fa-code-branch", :aria-hidden "true"}]] "daniellowtw/infboard"]
   [:a {:class "panel-block"}
    [:span {:class "panel-icon"}
     [:i {:class "fas fa-code-branch", :aria-hidden "true"}]]]
   [:label {:class "panel-block"}
    [:input {:type "checkbox"}] "remember me"]
   [:div {:class "panel-block"}
    [:button {:class "button is-link is-outlined is-fullwidth"} "Reset all filters"]]])

(defn box []
  [:div {:class "box"}
   [:article {:class "media"}
    [:div {:class "media-left"}
     [:figure {:class "image is-64x64"}
      [:img {:src "https://bulma.io/images/placeholders/128x128.png", :alt "Image"}]]]
    [:div {:class "media-content"}
     [:div {:class "content"}
      [:p
       [:strong "John Smith"]
       [:small "@johnsmith"]
       [:small "31m"]
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

(defn media-object []
  [:article {:class "media"}
   [:figure {:class "media-left"}
    [:p {:class "image is-64x64"}
     [:img {:src "https://bulma.io/images/placeholders/128x128.png"}]]]
   [:div {:class "media-content"}
    [:div {:class "content"}
     [:p
      [:strong "Barbara Middleton"]
      [:br] "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis porta eros lacus, nec ultricies elit blandit non. Suspendisse pellentesque mauris sit amet dolor blandit rutrum. Nunc in tempus turpis."
      [:br]
      [:small
       [:a "Like"] " · "
       [:a "Reply"] " · 3 hrs"]]]
    [:article {:class "media"}
     [:figure {:class "media-left"}
      [:p {:class "image is-48x48"}
       [:img {:src "https://bulma.io/images/placeholders/96x96.png"}]]]
     [:div {:class "media-content"}
      [:div {:class "content"}
       [:p
        [:strong "Sean Brown"]
        [:br] "Donec sollicitudin urna eget eros malesuada sagittis. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Aliquam blandit nisl a nulla sagittis, a lobortis leo feugiat."
        [:br]
        [:small
         [:a "Like"] " · "
         [:a "Reply"] " · 2 hrs"]]]
      [:article {:class "media"} "Vivamus quis semper metus, non tincidunt dolor. Vivamus in mi eu lorem cursus ullamcorper sit amet nec massa."]
      [:article {:class "media"} "Morbi vitae diam et purus tincidunt porttitor vel vitae augue. Praesent malesuada metus sed pharetra euismod. Cras tellus odio, tincidunt iaculis diam non, porta aliquet tortor."]]]
    [:article {:class "media"}
     [:figure {:class "media-left"}
      [:p {:class "image is-48x48"}
       [:img {:src "https://bulma.io/images/placeholders/96x96.png"}]]]
     [:div {:class "media-content"}
      [:div {:class "content"}
       [:p
        [:strong "Kayli Eunice "]
        [:br] "Sed convallis scelerisque mauris, non pulvinar nunc mattis vel. Maecenas varius felis sit amet magna vestibulum euismod malesuada cursus libero. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Phasellus lacinia non nisl id feugiat."
        [:br]
        [:small
         [:a "Like"] " · "
         [:a "Reply"] " · 2 hrs"]]]]]]]
  [:article {:class "media"}
   [:figure {:class "media-left"}
    [:p {:class "image is-64x64"}
     [:img {:src "https://bulma.io/images/placeholders/128x128.png"}]]]
   [:div {:class "media-content"}
    [:div {:class "field"}
     [:p {:class "control"}
      [:textarea {:class "textarea", :placeholder "Add a comment..."}]]]
    [:div {:class "field"}
     [:p {:class "control"}
      [:button {:class "button"} "Post comment"]]]]])

(defn tiles []
  [:div {:class "tile is-ancestor"}
   [:div {:class "tile is-vertical is-8"}
    [:div {:class "tile"}
     [:div {:class "tile is-parent is-vertical"}
      [:article {:class "tile is-child notification is-primary"}
       [:p {:class "title"} "Vertical..."]
       [:p {:class "subtitle"} "Top tile"]]
      [:article {:class "tile is-child notification is-warning"}
       [:p {:class "title"} "...tiles"]
       [:p {:class "subtitle"} "Bottom tile"]]]
     [:div {:class "tile is-parent"}
      [:article {:class "tile is-child notification is-info"}
       [:p {:class "title"} "Middle tile"]
       [:p {:class "subtitle"} "With an image"]
       [:figure {:class "image is-4by3"}
        [:img {:src "https://bulma.io/images/placeholders/640x480.png"}]]]]]
    [:div {:class "tile is-parent"}
     [:article {:class "tile is-child notification is-danger"}
      [:p {:class "title"} "Wide tile"]
      [:p {:class "subtitle"} "Aligned with the right tile"]
      [:div {:class "content"}  "<!-- Content -->"]]]]
   [:div {:class "tile is-parent"}
    [:article {:class "tile is-child notification is-success"}
     [:div {:class "content"}
      [:p {:class "title"} "Tall tile"]
      [:p {:class "subtitle"} "With even more content"]
      [:div {:class "content"}  "<!-- Content -->"]]]]])
