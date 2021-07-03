(ns torneoencasa.api.middleware.formats
  (:require
    [cognitect.transit :as transit]
    [muuntaja.core :as m]))

#_(def formatter
  (m/create
    (-> m/default-options
        ;; set Transit readers & writers
        (update-in [:formats "application/transit+json"]
                   merge {:decoder-opts {:handlers transit/readers}
                          :encoder-opts {:handlers transit/writers}}))))
(def formatter
  (m/create))

