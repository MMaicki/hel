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
                 [lein-figwheel "0.5.20"]
                 [ring/ring-json "0.5.0"]
                 [ring/ring-defaults "0.3.2"]]
  :cljsbuild {:builds [{:id           "app"
                        :source-paths ["src"]
                        :figwheel     true
                        :compiler     {:main       hel.cljs.core
                                       :asset-path "js/out"
                                       :output-to  "resources/public/js/compiled/app.js"
                                       :output-dir "resources/public/js/out"}}]}
  :figwheel {:css-dirs ["resources/public/css"]}            ;; watch and update CSS}
  :plugins [[lein-ring "0.12.5"]
            [lein-figwheel "0.5.20"]]
  :ring {:handler hel.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})
