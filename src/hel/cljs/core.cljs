(ns hel.cljs.core
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            [clojure.string :as str]
            [hel.cljs.routes.routes]
            [hel.cljs.re-frame.root]
            [re-frame.core :as rf]
            [re-frisk.core :as re-frisk]
            [accountant.core :as accountant]
            [hel.cljs.app :refer [app]]
            [secretary.core :as secretary]))

(defn ^:export run []
  (println "APP INIT!")
  (re-frisk/enable)
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
(run)
