(ns torneoencasa.nav.core
  (:require
    [re-frame.core :as rf]
    [torneoencasa.components.core :as c]
    [torneoencasa.i18n :refer [app-tr]]
    [torneoencasa.nav.subsevents :as nav-se]))

(def nav-items
  [{:id :home
    :name (app-tr :home)
    :href "#home"
    :authorized #{:admin :participant :invited}
    :dispatch #(rf/dispatch [::nav-se/set-active-nav :home])}
   #_{:id :sign-in
    :name "Sign in"
    :href "#sign-in"
    :authorized #{:admin :participant :invited :visitor}
    :dispatch #(rf/dispatch [::nav-se/set-active-nav :sign-in])}
   #_{:id :sign-up
    :name "Sign up"
    :href "#sign-up"
    :authorized #{:admin :participant :invited :visitor}
    :dispatch #(rf/dispatch [::nav-se/set-active-nav :sign-up])}])

(defn nav [active-page user]
  (let [user-roles (:roles user)
        items (filter #(some (:authorized %) user-roles) nav-items)]
    [:nav.navbar
      [:div.navbar-brand
        (for [item items]
          ^{:key item}
          [c/nav-item item active-page])]]))
