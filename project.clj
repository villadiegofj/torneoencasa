(defproject torneoencasa "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/clojurescript "1.10.773" :scope "provided"
                  :exclusions [com.google.javascript/closure-compiler-unshaded
                               r
                               org.clojure/google-closure-library
                               org.clojure/google-closure-library-third-party]]
                 [buddy "2.0.0"]
                 [clojure.java-time "0.3.2"]
                 [com.cognitect/transit-clj "1.0.324"]
                 [com.github.seancorfield/next.jdbc "1.2.674"]
                 [com.h2database/h2 "1.4.200"]
                 [day8.re-frame/http-fx "0.2.1"]
                 [day8.re-frame/tracing "0.6.0"]
                 [environ "1.2.0"]
                 [integrant "0.8.0"]
                 [metosin/malli "0.2.0"]
                 [metosin/muuntaja "0.6.7"]
                 [metosin/reitit "0.5.9"]
                 [metosin/ring-http-response "0.9.1"]
                 [reagent "0.10.0"]
                 [re-frame "1.1.2"]
                 [ring/ring-jetty-adapter "1.9.3"]
                 [ring/ring-defaults "0.2.1"]
                 [ring-cors "0.1.13"]
                 [ring-webjars "0.2.0"]
                 [thheller/shadow-cljs "2.11.8" :scope "provided"]
                 [org.webjars.npm/bulma "0.9.1"]
                 [org.webjars/webjars-locator "0.40"]
                 [org.webjars.npm/material-icons "0.3.1"]]

  :min-lein-version "2.9.0"
  :plugins [[lein-shadow "0.2.0"]]
  :source-paths ["src/clj" "src/cljs"]
  :target-path "target/%s"
  :clean-targets ^{:protect false} ["resources/public/js" "target"]
  :main torneoencasa.api.app
  :aot [torneoencasa.api.app]
  :shadow-cljs {:nrepl  {:port 8777}
                :builds {:torneoencasa {:target     :browser
                                    :output-dir "resources/public/js"
                                    :asset-path "/js"
                                    :modules    {:main {:init-fn  torneoencasa.core/init
                                                        :preloads [devtools.preload
                                                                   day8.re-frame-10x.preload]}}
                                    :dev        {:compiler-options
                                                  {:closure-defines {re-frame.trace.trace-enabled? true
                                                                     day8.re-frame.tracing.trace-enabled? true}}}
                                    :release    {:build-options
                                                 {:ns-aliases
                                                  {day8.re-frame.tracing day8.re-frame.tracing-stubs}}}
                                    :devtools   {:http-root "resources/public"
                                                 :http-port 8280}}}}
  :npm-deps [[react "16.13.0"]
             [react-dom "16.13.0"] ]
  :npm-dev-deps [[xmlhttprequest "1.8.0"]]
  :prep-tasks []
  :profiles {:uberjar {:source-paths   ["src/clj" "src/cljs"]
                       :omit-source    true
                       :prep-tasks     ["compile" ["shadow" "release" "torneoencasa"]]
                       :main           torneoencasa.api.app
                       :aot            [torneoencasa.api.app]
                       :uberjar-name   "torneoencasa.jar"
                       :resource-paths ["resources"]}
             :dev     {:dependencies [[binaryage/devtools "1.0.2"]
                                      [day8.re-frame/re-frame-10x "0.7.0"]
                                      [ring/ring-mock "0.4.0"]
                                      [integrant/repl "0.3.2"]]
                       :source-paths ["dev/clj" "dev/cljs"]
                       :resource-paths ["dev/resources"]}
             :prod    {}})
