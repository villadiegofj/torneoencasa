(ns torneoencasa.errors
  (:require
    [torneoencasa.i18n :refer [app-tr]]))

(defn lookup-error [code]
  (let [m {:e401 (app-tr :errors/e401)
           :e403 (app-tr :errors/e403)
           :e404 (app-tr :errors/e404)
           :e900 (app-tr :errors/e900)}
        message (m code)]
    (if (nil? message)
      (app-tr :errors/error-found)
      message)))
