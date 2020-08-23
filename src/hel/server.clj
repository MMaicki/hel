(ns hel.server
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.reload :refer [wrap-reload]]
            [hel.handler :refer [app]]
            [clojure.java.jdbc :as j]))

(defn -main []
  (run-jetty
    (wrap-reload #'app)
    {:port  3000
     :join? false}))
