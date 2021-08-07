(ns torneoencasa.api.formats
  (:require
    [clojure.data.csv :as csv]
    [clojure.java.io :as io]
    [clojure.pprint :refer [pprint]]
    [muuntaja.core :as m]
    [ring.util.io :as ring-io])
  (:gen-class))

(def content-negotiation
  (m/create))

(defn piped-csv-output [data-seq]
  (let [headers     (map name (keys (first data-seq)))
        rows        (map vals data-seq)
        stream-csv  (fn [out] (csv/write-csv out (cons headers rows))
                              (.flush out))]
    (ring-io/piped-input-stream #(stream-csv (io/make-writer % {})))))

(defn csv-output [data-seq]
  (let [header (map name (keys (first data-seq)))
        out    (java.io.StringWriter.)
        write  #(csv/write-csv out (list %))]
    (write header)
    (dorun (map (comp write vals) data-seq))
    (.toString out)))

(defn create-representation
  "Returns a representation of the data based on the response format"
  [data response-format]
  (case response-format
    :csv   (csv-output data)
    :plain data
    :json  (into #{} data)))

(defn content-type [k]
  (case k
    :csv  "text/csv"
    :plain "text/plain"
    :json "application/json"))

(defn mime->keyword [mime]
  (when mime
    (-> mime keyword name keyword)))
