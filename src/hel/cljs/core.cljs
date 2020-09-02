(ns hel.cljs.core
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            [clojure.string :as str]
            [hel.cljs.routes.routes]
            [hel.cljs.re-frame.root]
            [re-frame.core :as rf]
           [re-frisk.core :as re-frisk] ; re frisk is dev dependency it's not getting compiled in production
            [accountant.core :as accountant]
            [hel.cljs.app :refer [app]]
            [figwheel.client :as figwheel :include-macros true]
            [secretary.core :as secretary]))

(defn fighweel-reload []
  (println "fighweel reload"))

(defn ^:export run []
  (println "APP INIT!")
  (re-frisk/enable) ; re frisk is dev dependency it's not getting compiled in production
  (rf/dispatch-sync [:config/init])
  (accountant/configure-navigation!
    {:nav-handler       (fn [path]
                          (secretary/dispatch! path))
     :path-exists?      (fn [path]
                          (secretary/locate-route path))
     :reload-same-path? false})
  (rdom/render [app] (js/document.getElementById "app")))

;; START FRONT-END APPLICATION
(enable-console-print!)
(figwheel/watch-and-reload ;; only for dev env
  :websocket-url "ws://localhost:3449/figwheel-ws"
  :jsload-callback fighweel-reload)
(run)
