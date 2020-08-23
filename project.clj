(defproject hel "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :main hel.server
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/clojurescript "1.10.773"]
                 [ring "1.8.1"]
                 [compojure "1.6.1"]
                 [hiccup "1.0.5"]
                 [reagent "1.0.0-alpha2"]
                 [re-frame "1.0.0"]
                 [clj-commons/secretary "1.2.4"]
                 [venantius/accountant "0.2.5"]
                 [cljs-ajax "0.8.1"]
                 [lein-figwheel "0.5.20"]
                 [ring/ring-json "0.5.0"]
                 [ring/ring-defaults "0.3.2"]
                 [org.clojure/java.jdbc "0.6.1"]
                 [org.postgresql/postgresql "9.4-1201-jdbc41"] ]
  :cljsbuild {:builds [{:id           "app"
                        :source-paths ["src"]
                        :figwheel     {:on-jsload     "on-js-reload"
                                       :websocket-url "ws://localhost:3449/figwheel-ws"}
                        :compiler     {:main       hel.cljs.core
                                       :asset-path "js/out"
                                       :output-to  "resources/public/js/compiled/app.js"
                                       :output-dir "resources/public/js/out"}}]}
  :figwheel {:css-dirs         ["resources/public/css"]
             :http-server-root "public"
             :server-port      3449
             :ring-handler     hel.handler/app}
  :plugins [[lein-ring "0.12.5"]
            [lein-figwheel "0.5.20"]]
  :ring {:handler hel.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]
                        [re-frisk "1.3.4"]]}})
