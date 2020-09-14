(ns hel.cljs.client.components.login
  (:require [material-ui :as mui]
            [ajax.core :refer [POST]]
            ["@material-ui/icons" :as icons]
            [reagent.core :as r]))

(defn login []
  (let [r-login    (r/atom "")
        r-password (r/atom "")]
    (fn []
      (let [login    @r-login
            password @r-password]
        [:div {:id :login}
         [:> mui/Card {}
          [:> mui/CardHeader {:title     "Login"
                              :subheader "Login to Awesome App"}]
          [:> mui/CardContent {}
           [:> mui/Grid {:container true
                         :spacing 2
                         :align-items :center
                         :justify :center
                         :direction :column}
            [:> mui/Grid {:item true}
             [:> mui/TextField {:required  true
                                :value     login
                                :InputProps {:end-adornment (r/as-element [:> mui/Button {}
                                                                           [:> icons/Add {}]])}
                                :on-change #(reset! r-login (.. % -target -value))
                                :placeholder "Sample"
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
                             :on-click #(POST "/login"
                                              {:params          {:user     login
                                                                 :token    "test"
                                                                 :body     true
                                                                 :password password}
                                               :format          :json
                                               :handler         (fn [req]
                                                                  (println "handler" (str req)))
                                               :error-handler   (fn [req]
                                                                  (println "error" (str req)))
                                               :response-format :json
                                               :keywords?       true})}
              "Submit"]]]]]]))))