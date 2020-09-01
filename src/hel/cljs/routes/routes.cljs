(ns hel.cljs.routes.routes
  (:require [secretary.core :as secretary :refer-macros [defroute] :include-macros true]
            [ajax.core :refer [GET POST]]
            [re-frame.core :as rf]))

(defroute "/" []
          #_(GET "/hello"
               {:params          {:email "test@test.com"
                                  :user  "Marcin"}
                :handler         (fn [req]
                                   (rf/dispatch [:routes/home req]))
                :error-handler   (fn [req]
                                   (println "Error!" (str req)))
                :response-format :json
                :keywords?       true})
          (POST "/login"
               {:body {:user "admin"
                       :body true
                       :password  "secret"}
                :handler         (fn [req]
                                   (println "handler" (str req)))
                :error-handler   (fn [req]
                                   (println "error" (str req)))
                :response-format :json
                :keywords?       true}))

(defroute "/about" []
          (println "/about"))