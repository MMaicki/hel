(ns hel.cljs.client.components.login
  (:require [material-ui :as mui]
            [ajax.core :refer [POST]]
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
           [:> mui/TextField {:required  true
                              :value     login
                              :on-change #(reset! r-login (.. % -target -value))
                              :label     "Username"
                              :variant   :outlined}]
           [:> mui/TextField {:required  true
                              :value     password
                              :on-change #(reset! r-password (.. % -target -value))
                              :label     "Password"
                              :variant   :outlined}]
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
            "Submit"]]]]))))