(ns torneoencasa.views
  (:require
   [re-frame.core :as rf]
   [torneoencasa.nav.subs :as nav-subs]
   [torneoencasa.auth.subs :as auth-subs]
   [torneoencasa.auth.core :refer [sign-in sign-up]]
   [torneoencasa.nav.core :refer [nav]]
   [torneoencasa.home.core :refer [home]]))

(defn pages [page-name]
  (case page-name
    :home [home]
    :sign-in [sign-in]
    :sign-up [sign-up]
    [sign-in]))

(defn errors-list [errors]
  [:ul.error-messages
   (for [[key [val]] errors]
     ^{:key key} [:li (str (name key) " " val)])])

(defn header []
  [:section.hero.is-info.is-bold.is-small
   [:div.hero-body
    [:div.container
     [:h1.title "Torneo en Casa"]
     [:h2.subtitle "Todos somos una pieza, y en este rompecabezas todas las piezas son importantes"]]]])

(defn footer []
  [:footer
   [:div.container
    [:div.notification.has-text-centered
    [:p "Made by Oraqus"]]]])

(defn main-panel []
  (let [active-page (rf/subscribe [::nav-subs/active-page])
        user (rf/subscribe [::auth-subs/current-user])]
    [:div
     [header]
     [nav @active-page @user]
     [pages @active-page]
     [footer]]))
