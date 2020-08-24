(ns hel.cljs.app
  (:require [hel.cljs.client.layout :refer [layout]]
            [ajax.core :refer [GET POST]]
            [re-frame.core :as rf]
            [hel.cljs.client.components.header :refer [header]]))

(defn app []
  (let [external-launches (GET "https://api.spacexdata.com/v3/launches"
                               {:handler         (fn [req]
                                                   (rf/dispatch [:routes/external-api req]))
                                :error-handler   (fn [req]
                                                   (println "Error getting SpaceX API" (str req)))
                                :response-format :json
                                :keywords?       true})
        rf-launches       (rf/subscribe [:external/launches])]
    (fn []
      (let [launches @rf-launches]
        [layout
         [header {:key :header}]
         [:div {:id  :container
                :key :container}
          [:h2 {} "Container Div"]
          [:div {}
           (if launches
             (map (fn [launch] [:div {:key (:mission_name launch)}
                                (:mission_name launch)]) launches)
             "LOADING")]]

         [:footer {:key :footer}
          [:div {} "Footer"]]]))))