(ns torneoencasa.nav.core
  (:require
    [re-frame.core :as rf]
    [torneoencasa.components.core :as c]
    [torneoencasa.nav.events :as nav-events]))

(def nav-items
  [{:id :home
    :name "Home"
    :href "#home"
    :authorized #{:teacher :student}
    :dispatch #(rf/dispatch [::nav-events/set-active-nav :home])}
   {:id :sign-in
    :name "Sign in"
    :href "#sign-in"
    :authorized #{:anonymous}
    :dispatch #(rf/dispatch [::nav-events/set-active-nav :sign-in])}
   {:id :sign-up
    :name "Sign up"
    :href "#sign-up"
    :authorized #{:anonymous}
    :dispatch #(rf/dispatch [::nav-events/set-active-nav :sign-up])}
   {:id :classroom
    :name "Classboard"
    :href "#classboard"
    :authorized #{:student}
    :dispatch #(rf/dispatch [::nav-events/set-active-nav :classboard])}
   {:id :profile
    :name "Profile"
    :href "#profile"
    :authorized #{:teacher :student}
    :dispatch #(rf/dispatch [::nav-events/set-active-nav :profile])}
   {:id :tabs
    :name "Tabs"
    :href "#tabs"
    :authorized #{:teacher}
    :dispatch #(rf/dispatch [::nav-events/set-active-nav :tabs])}
   {:id :card
    :name "Card"
    :href "#card"
    :authorized #{:teacher}
    :dispatch #(rf/dispatch [::nav-events/set-active-nav :card])}
   {:id :panel
    :name "Panel"
    :href "#panel"
    :authorized #{:teacher}
    :dispatch #(rf/dispatch [::nav-events/set-active-nav :panel])}
   {:id :box
    :name "Box"
    :href "#box"
    :authorized #{:teacher}
    :dispatch #(rf/dispatch [::nav-events/set-active-nav :box])}
   {:id :tiles
    :name "Tiles"
    :href "#tiles"
    :authorized #{:teacher}
    :dispatch #(rf/dispatch [::nav-events/set-active-nav :tiles])}
   {:id :media-object
    :name "Media Object"
    :href "#media-object"
    :authorized #{:teacher}
    :dispatch #(rf/dispatch [::nav-events/set-active-nav :media-object])}])

(defn nav [active-page user]
  (let [user-roles (:roles user)
        items (filter #(some (:authorized %) user-roles) nav-items)]
    [:nav.navbar
      [:div.navbar-brand
        (for [item items]
          ^{:key item}
          [c/nav-item item active-page])]]))
