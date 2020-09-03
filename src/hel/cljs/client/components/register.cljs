(ns hel.cljs.client.components.register
  (:require [material-ui :as mui]
            [ajax.core :refer [POST]]
            [reagent.core :as r]))

(defn register []
  (let [r-login    (r/atom "")
        r-password (r/atom "")]
    (fn []
      (let [login    @r-login
            password @r-password]
        [:div {:id :login}
         [:> mui/Card {}
          [:> mui/CardHeader {:title     "Register"
                              :subheader "Register to Awesome App"}]
          [:> mui/CardContent {}
           [:> mui/Grid {:container true
                         :spacing 2
                         :align-items :center
                         :justify :center
                         :direction :column}
            [:> mui/Grid {:item true}
             [:> mui/TextField {:required  true
                                :value     login
                                :on-change #(reset! r-login (.. % -target -value))
                                :label     "User"
                                :variant   :outlined}]]
            [:> mui/Grid {:item true}
             [:> mui/TextField {:required  true
                                :value     password
                                :on-change #(reset! r-password (.. % -target -value))
                                :label     "Password"
                                :variant   :outlined}]]
            [:> mui/Grid {:item true}
             [:> mui/Button {:variant  :contained
                             :color    :primary
                             :on-click #(POST "/register"
                                              {:params          {:user     login
                                                                 :password password}
                                               :format          :json
                                               :handler         (fn [req]
                                                                  (println "handler" (str req)))
                                               :error-handler   (fn [req]
                                                                  (println "error" (str req)))
                                               :response-format :json
                                               :keywords?       true})}
              "Register"]]]]]]))))