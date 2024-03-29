(ns user
  (:require
    [clojure.pprint :as pp]
    [integrant.repl :as ig-repl]
    [integrant.core :as ig]
    [integrant.repl.state :as state]
    [muuntaja.core :as muuntaja]
    [buddy.hashers :as buddy-hashers]
    [malli.core :as malli]
    [reitit.core :as reitit]
    [reitit.ring :as rr]
    [reitit.ring.coercion :as rrc]
    [reitit.ring.middleware.muuntaja :as rrmm]
    [reitit.ring.middleware.parameters :as rrmp]
    [reitit.coercion.malli :as rcm]
    [shadow.cljs.devtools.server :as shadow-server]
    [shadow.cljs.devtools.api :as shadow]
    [torneoencasa.api.routes :as tcr]
    [torneoencasa.api.controllers.auth :as auth]
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
  (shadow-server/start!)
  (shadow/watch :torneoencasa)
  (shadow/compile :torneoencasa)
  (shadow/release :torneoencasa)
  (shadow/browser-repl)
  (shadow/node-repl)
  ;; shadow-cljs cljs-repl foo
  (shadow/repl :torneoencasa)
  ;; Once you are in a CLJS REPL you can use
  :repl/quit
  ;; or
  :cljs/quit
  (shadow-server/stop!)
  ;;;;;;;;;;;;;;;;
  (def db-spec {:dbtype "h2:mem" :dbname "torneoencasa"})
  (def ds (jdbc/with-options (jdbc/get-datasource db-spec) tcdb/ds-opts))
  (tcdb/get-users ds)
  (tcdb/get-user-by-username ds "barran")
  (sql/find-by-keys ds :users {:username "batman"})
  (sql/find-by-keys ds :users :all {:columns [:username :firstname :lastname :role :email :code :created]})

  (def h (tcr/handler {:datasource (jdbc/with-options
                                     (jdbc/get-datasource
                                       {:dbtype "h2:mem" :dbname "torneoencasa"}) tcdb/ds-opts)}))
  (->> (h {:uri "/api/users"
           :request-method :get})
       muuntaja/decode-response-body)

  (->> (h {:uri "/api/users/report"
           ;:headers {"accept" "application/json"}
           :headers {"accept" "text/csv"}
           :roles "admin"
           :request-method :get}) )

  (require '[malli.core :as malli])
  (malli/validate [:map [:message string?]] {:message "hello"})

  (def match (reitit/match-by-path
               (tcr/api-router db-spec) "/api/users/report"))

  (rc/compile-request-coercers)
  (rc/coerce! match)
  ,)
