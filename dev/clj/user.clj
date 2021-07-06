(ns clj.user
  (:require
            [integrant.repl :as ig-repl]
            [integrant.core :as ig]
            [integrant.repl.state :as state]
            [ring.util.http-response :as response]
            [muuntaja.core :as m]
            [malli.core :as malli]
            [reitit.ring :as rr]
            [reitit.ring.coercion :as rrc]
            [reitit.ring.middleware.muuntaja :as rrmm]
            [reitit.ring.middleware.parameters :as rrmp]
            [reitit.coercion.malli :as rcm]
            [ring.middleware.cors :as cors])
  (:gen-class))

(ig-repl/set-prep!
  ;;zero arg function returning ig config
  (fn [] (-> "resources/config.edn" slurp ig/read-string)))

(def go ig-repl/go)
(def halt ig-repl/halt)
(def reset ig-repl/reset)
(def reset-all ig-repl/reset-all)

(defn app [] (-> state/system :torneoencasa/app))

(comment
  (go)
  (halt)
  (reset)

  ;;;;;;;;;;;;;;;;
  (app {:request-method :get
        :uri            "/swagger.json"})
  (app {:request-method :post
        :uri            "/api/auth"
        :query-params {:id "batman"
                       :password "namtab"}})
  (defn wrap-with-cors
    [handler domain-pattern]
    (cors/wrap-cors handler
                    :access-control-allow-origin domain-pattern
                    :access-control-allow-headers #{:accept :content-type}
                    :access-control-allow-methods #{:get :put :post :delete}))

  (def routes [["/api" {:get  (constantly response)
                        :post (constantly response)}]
               ["/options" {:options (constantly response)}]
               ["/any" (constantly response)]])

  (def router-options
    {:data {:muuntaja muuntaja/instance
            :coercion rc-malli/coercion
            :middleware [rrmp/parameters-middleware
                         rrmm/format-negotiate-middleware
                         rrmm/format-response-middleware
                         rrmm/format-request-middleware
                         [middleware/wrap-with-cors accepted-origin]
                         rrc/coerce-request-middleware
                         rrc/coerce-exceptions-middleware
                         rrc/coerce-response-middleware]}
     :exception reitit.dev.pretty/exception})

  (def handler (rr/ring-handler
                 (rr/router
                   routes
                   router-options)))

  (= {:status 200
      :body ""
      :headers {"Allow" "GET,POST,OPTIONS"}}
     (handler {:request-method :options
               :uri "/api"}))
  (= response (handler {:request-method :get, :uri "/any"}))
  (= response (handler {:request-method :options, :uri "/any"}))
  (= response (handler {:request-method :options, :uri "/options"}))

  (def preflight {:uri            "/api"
                  :request-method :options
                  :headers        {"origin"                         "http://localhost:8280"
                                   "access-control-request-method"  "POST"
                                   "access-control-request-headers" "Accept, Content-Type"}})
  (handler preflight)
  ,)
