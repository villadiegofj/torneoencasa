(ns torneoencasa.profile.core
  (:require
   [re-frame.core :as rf]))

(defn profile []
  [:section.hero
   [:div.hero-body
    [:container
     [:h1.title "Profile"]
     [:h2.subtitle "hero subtitle"]]]])
