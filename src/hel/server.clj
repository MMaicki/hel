(ns hel.server
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [hikari-cp.core :refer :all]
            [ring.middleware.reload :refer [wrap-reload]]
            [hel.handler :refer [app]]
            [com.stuartsierra.component :as component]))

(def datasource-options {:auto-commit        true
                         :read-only          false
                         :connection-timeout 30000
                         :validation-timeout 5000
                         :idle-timeout       600000
                         :max-lifetime       1800000
                         :minimum-idle       10
                         :maximum-pool-size  10
                         :pool-name          "db-pool"
                         :adapter            "postgresql"
                         :username           "postgres"
                         :password           "nxCXwqKhfdw4o2"
                         :database-name      "hel"
                         :server-name        "localhost"
                         :port-number        5432
                         :register-mbeans    false})

(defonce datasource
         (delay (make-datasource datasource-options)))

(defrecord WebServer [http-server app-component]
  component/Lifecycle
  (start [this]
    (println "WEBSERVER:" this)
    (assoc this :http-server (run-jetty
                               (wrap-reload #'app)
                               {:port  3000
                                :join? false})))
  (stop [this]
    (assoc this :http-server nil)
    this))

(defn web-server
  "Returns a new instance of the web server component which
  creates its handler dynamically."
  []
  (component/using (map->WebServer {})
                   [:database])) ; webserver gets his db dependency declared here

(defrecord Database [host port connection]
  component/Lifecycle

  (start [component]
    (println ";; Starting database")
    (let [conn (map->Database datasource-options)]
      (println "CONNECTION" conn)
      (assoc component :connection conn)))

  (stop [component]
    (println ";; Stopping database")
    (close-datasource @datasource)
    (assoc component :connection nil)))

(defn postgree-db [host port]
  (map->Database {:host host :port port}))

(defn example-system [config-options]
  (let [{:keys [host port]} config-options]
    (component/system-map
      :config-options config-options
      :database (postgree-db "localhost" 5432)
      :app (component/system-using
             (web-server)
             [])))) ; this is place for whole app dependency

(def system (example-system {:host "localhost" :port 5432}))

(defn -main []
  (alter-var-root #'system component/start))
