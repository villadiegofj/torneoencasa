(ns torneoencasa.views
  (:require
   [re-frame.core :as rf]
   [torneoencasa.auth.subs :as auth-subs]
   [torneoencasa.auth.core :refer [sign-in]]
   [torneoencasa.components.core :refer [modal]]
   [torneoencasa.home.core :refer [home]]
   [torneoencasa.nav.core :refer [nav]]
   [torneoencasa.nav.subs :as nav-subs]
   [torneoencasa.users.core :refer [sign-up]]))

(defn pages [page-name]
  (case page-name
    :home [home]
    :sign-in [sign-in]
    :sign-up [sign-up]
    [sign-in]))

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
    [:p [:a {:href "http://www.oraqus.cl"} "Made by Oraqus"]]]]])

(defn main-panel []
  (let [active-page (rf/subscribe [::nav-subs/active-page])
        user (rf/subscribe [::auth-subs/current-user])]
    [:div
     [header]
     [nav @active-page @user]
     [pages @active-page]
     #_[footer]]))
