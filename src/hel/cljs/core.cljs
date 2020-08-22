(ns hel.cljs.core
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            [clojure.string :as str]
            [hel.cljs.client.layout :refer [layout]]))

(defn app []
  (fn []
    [layout
     [:header {:key :header}
      [:nav {}
       [:div {} "Nav"]]]

     [:div {:id :container
            :key :container}
      [:h2 {} "Container Div"]]

     [:footer {:key :footer}
      [:div {} "Footer"]]]))

(defn ^:export run []
  (println "APP INIT!")
  (rdom/render [app] (js/document.getElementById "app")))

(enable-console-print!)

(run)
