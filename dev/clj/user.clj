(ns clj.user
  (:require [ring.util.http-response :as response]
            [muuntaja.core :as m]
            [malli.core :as malli]
            [reitit.ring :as rr]
            [reitit.ring.coercion :as rrc]
            [reitit.ring.middleware.muuntaja :as rrmm]
            [reitit.ring.middleware.parameters :as rrmp]
            [reitit.coercion.malli :as rcm]
            [ring.middleware.cors :as cors])
  (:gen-class))

(defn wrap [handler id]
  (fn [req]
    (let [response (handler req)]
      (assoc-in response [:headers (str "W-" id)] "check"))))

(defn wrap-with-cors
  [handler domain-pattern]
  (cors/wrap-cors handler
                  :access-control-allow-origin domain-pattern
                  :access-control-allow-headers #{:accept :content-type}
                  :access-control-allow-methods #{:get :put :post :delete}))

(defn ping-handler [req]
  {:status 200
   :body   "pong"})

(def handler
  (rr/ring-handler
    (rr/router
      [["/api"
        ["/ping" {:name ::ping
                  :get  {:handler ping-handler}}]]]
      {:data {:muuntaja   m/instance
              :coercion   rcm/coercion
              :middleware [rrmp/parameters-middleware
                           rrmm/format-negotiate-middleware
                           rrmm/format-request-middleware
                           rrmm/format-response-middleware
                           rrc/coerce-exceptions-middleware
                           rrc/coerce-request-middleware
                           rrc/coerce-response-middleware
                           [wrap-with-cors #"http://localhost.*"]
                           ;;[wrap-with-cors #".*"]
                           [wrap "TOP"]]}})))

(require '[ring.adapter.jetty :as jetty])
(defonce srvr (jetty/run-jetty #'handler {:port 3000, :join? false}))
(.stop srvr)
(.start srvr)

(def pf {:uri            "/api/ping"
         :request-method :options
         :headers        {"origin" "http://localhost:8280"}})
(handler pf)

(def preflight {:uri            "/api/ping"
                :request-method :options
                :headers        {"origin"                         "http://localhost:8280"
                                 "access-control-request-method"  "POST"
                                 "access-control-request-headers" "Accept, Content-Type"}})
(handler preflight)

(def ping {:uri            "/api/ping"
           :request-method :get
           :headers        {"origin"                         "http://localhost:8280"
                            "access-control-request-method"  "head"
                            "access-control-request-headers" "Accept, Content-Type"}})
(handler ping)

(def pxng {:uri            "/api/ping"
           :request-method :get
           :headers        {"origin"                         "http://some.edu"
                            "access-control-request-method"  "head"
                            "access-control-request-headers" "Accept, Content-Type"}})
(handler pxng)

(def response {:status 200, :body "ok"})
(def app (rr/ring-handler
           (rr/router
             [["/get" {:get  (constantly response)
                       :post (constantly response)}]
              ["/options" {:options (constantly response)}]
              ["/any" (constantly response)]])))

(= response (app {:request-method :get, :uri "/get"}))
(= {:status 200, :body "", :headers {"Allow" "GET,POST,OPTIONS"}}
   (app {:request-method :options, :uri "/get"}))

(= response (app {:request-method :get, :uri "/any"}))
(= response (app {:request-method :options, :uri "/any"}))
(= response (app {:request-method :options, :uri "/options"}))

