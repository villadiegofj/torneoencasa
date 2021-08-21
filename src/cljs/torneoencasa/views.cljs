(ns torneoencasa.views
  (:require
    [re-frame.core :as rf]
    [torneoencasa.auth.core :refer [sign-in]]
    [torneoencasa.home.core :refer [home]]
    [torneoencasa.i18n :refer [app-tr]]
    [torneoencasa.nav.core :refer [nav]]
    [torneoencasa.nav.subsevents :as nav-se]
    [torneoencasa.users.core :refer [sign-up]]
    [torneoencasa.users.subsevents :as users-se]))

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
     [:h1.title (app-tr :app-title)]
     [:h2.subtitle (app-tr :app-subtitle)]]]])

(defn footer []
  [:footer
    [:div.notification.has-text-centered
     [:p [:a {:href "http://www.oraqus.cl"} (app-tr :made-by)]]]])

(defn main-panel []
  (let [active-page (rf/subscribe [::nav-se/active-page])
        user (rf/subscribe [::users-se/current-user])]
    [:div
     [header]
     [nav @active-page @user]
     [pages @active-page]
     [footer]]))
