(ns torneoencasa.core
  (:require
   [reagent.dom :as rdom]
   [re-frame.core :as rf]
   [torneoencasa.subsevents :as common-se]
   [torneoencasa.views :as views]
   [torneoencasa.config :as config]))

(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (rf/clear-subscription-cache!)
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [views/main-panel] root-el)))

(defn init []
  (rf/dispatch-sync [::common-se/initialize-db])
  (dev-setup)
  (mount-root))
