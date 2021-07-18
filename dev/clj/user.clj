(ns user
  (:require
    [clojure.pprint :as pp]
    [integrant.repl :as ig-repl]
    [integrant.core :as ig]
    [integrant.repl.state :as state]
    [muuntaja.core :as muuntaja]
    [malli.core :as malli]
    [reitit.core :as reitit]
    [reitit.ring :as rr]
    [reitit.ring.coercion :as rrc]
    [reitit.ring.middleware.muuntaja :as rrmm]
    [reitit.ring.middleware.parameters :as rrmp]
    [reitit.coercion.malli :as rcm]
    [torneoencasa.api.routes :as tcr]
    [next.jdbc :as jdbc]
    [next.jdbc.result-set :as rs]
    [next.jdbc.sql :as sql]
    [torneoencasa.api.db.users :as tcdb])
(:gen-class))

(ig-repl/set-prep!
  ;;zero arg function returning ig config
  (fn [] (-> "resources/config.edn" slurp ig/read-string)))

(def go ig-repl/go)
(def halt ig-repl/halt)
(def reset ig-repl/reset)
(def reset-all ig-repl/reset-all)

(defn app []
  (-> state/system :torneoencasa/app))

(defn db []
  (-> state/system :db/config))

(comment
  (go)
  (halt)
  (reset)

  ;;;;;;;;;;;;;;;;
  (def db-spec {:dbtype "h2:mem" :dbname "torneoencasa"})
  (def ds (jdbc/with-options (jdbc/get-datasource db-spec) tcdb/ds-opts))
  (tcdb/get-users ds)
  (tcdb/get-user-by-username ds "batman")

  (def h (tcr/build-handler ds))
  (def auth {:uri            "/api/auth"
             :request-method :post
             :body-params    {:username "batman"
                              :password "namtab"}})
  (h auth)
  (->> (h auth) muuntaja/decode-response-body)

  (require '[malli.core :as malli])
  (malli/validate [:map [:message string?]] {:message "hello"})

  (def match (r/match-by-path (api-router dbspec) "/api/auth"))

  (rc/compile-request-coercers)
  (rc/coerce! match)
  ,)
