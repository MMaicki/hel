(ns hel.cljs.app
  (:require [hel.cljs.client.layout :refer [layout]]
            [ajax.core :refer [GET POST]]
            [re-frame.core :as rf]
            [hel.cljs.client.components.header :refer [header]]
            [hel.cljs.client.components.login :refer [login]]))

(defn app []
  (let [external-launches (GET "https://api.spacexdata.com/v3/launches"
                               {:handler         (fn [req]
                                                   (rf/dispatch [:routes/external-api req]))
                                :error-handler   (fn [req]
                                                   (println "Error getting SpaceX API" (str req)))
                                :response-format :json
                                :keywords?       true})
        rf-launches       (rf/subscribe [:external/launches])
        rf-page           (rf/subscribe [:routes/get-page])]
    (fn []
      (let [launches @rf-launches
            page @rf-page]
        [layout
         [header {:key :header}]
         [:div {:id  :container
                :key :container}
          [:h2 {} "Container Div"]
          (when (= page :login)
            [login])]
         [:footer {:key :footer}
          [:div {} "Footer"]]]))))