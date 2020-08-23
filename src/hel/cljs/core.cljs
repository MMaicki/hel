(ns hel.cljs.core
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            [clojure.string :as str]
            [hel.cljs.routes.routes]
            [accountant.core :as accountant]
            [hel.cljs.client.layout :refer [layout]]
            [secretary.core :as secretary]))

(defn app []
  (fn []
    [layout
     [:header {:key :header}
      [:nav {}
       [:div {}
        [:a {:href "/about"}
         "About"]
        [:a {:href "/"}
         "Home"]]]]

     [:div {:id :container
            :key :container}
      [:h2 {} "Container Div"]]

     [:footer {:key :footer}
      [:div {} "Footer"]]]))

(defn ^:export run []
  (println "APP INIT!")
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
