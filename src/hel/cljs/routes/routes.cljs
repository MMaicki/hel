(ns hel.cljs.routes.routes
  (:require [secretary.core :as secretary :refer-macros [defroute] :include-macros true]
            [ajax.core :refer [GET POST]]
            [re-frame.core :as rf]))

(defroute "/" []
          (GET "/hello"
               {:params          {:email "test@test.com"
                                  :user  "Marcin"}
                :handler         (fn [req]
                                   (rf/dispatch [:routes/home req]))
                :error-handler   (fn [req]
                                   (println "Error!" (str req)))
                :response-format :json
                :keywords?       true})
          (GET "https://api.spacexdata.com/v3/launches"
               {:handler         (fn [req]
                                   (rf/dispatch [:routes/external-api req]))
                :error-handler   (fn [req]
                                   (println "Error getting SpaceX API" (str req)))
                :response-format :json
                :keywords?       true}))

(defroute "/about" []
          (println "/about"))