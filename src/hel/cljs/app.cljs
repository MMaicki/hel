(ns hel.cljs.app
  (:require [hel.cljs.client.layout :refer [layout]]
            [ajax.core :refer [GET POST]]
            [re-frame.core :as rf]
            [material-ui :as mui]
            [hel.cljs.client.components.header :refer [header]]
            [hel.cljs.client.components.login :refer [login]]
            [hel.cljs.client.components.register :refer [register]]))

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
            page     @rf-page]
        [layout
         [header {:key :header}]
         [:> mui/Container {:max-width false
                            :style {:margin-top "100px"}}
          [:> mui/Grid {:container   true
                        :justify     :center
                        :align-items :center}
           (when (= page :login)
             [:> mui/Grid {:container true
                           :direction :column
                           :item      true
                           :spacing   4
                           :xs        12
                           :sm        10
                           :md        6}
              [:> mui/Grid {:item true}
               [login]]
              [:> mui/Grid {:item true}
               [register]]])]]
         [:footer {:key :footer}
          [:div {} "Footer"]]]))))