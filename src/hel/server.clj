(ns hel.server
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [hikari-cp.core :refer :all]
            [ring.middleware.reload :refer [wrap-reload]]
            [hel.handler :refer [app]]
            [com.stuartsierra.component :as component])
  (:gen-class))

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

(defrecord WebServer [http-server]
  component/Lifecycle
  (start [this]
    (assoc this :http-server (http-server)))
  (stop [this]
    (assoc this :http-server nil)
    this))

(defn web-server
  "Returns a new instance of the web server component which
  creates its handler dynamically."
  [http-server]
  (map->WebServer {:http-server http-server}))

(defrecord Database [host port connection]
  component/Lifecycle

  (start [component]
    (println ";; Starting database" host port)
    (let [conn @datasource]
      (println "CONNECTION" conn)
      (assoc component :connection conn)))

  (stop [component]
    (println ";; Stopping database")
    (close-datasource @datasource)
    (assoc component :connection nil)))

(defn postgree-db [host port]
  (map->Database {:host host :port port}))

(defn web-server-system [config-options]
  (let [{:keys [host port http-server]} config-options]
    (component/system-map
      :config-options config-options
      :database (postgree-db host port)
      :app (component/system-using
             (web-server http-server)
             [])))) ; this is place for whole app dependency

; PRODUCTION SERVER
; #(run-jetty app {:port 3000})

; DEVELOPEMENT SERVER FUNCTION
; #(run-jetty (wrap-reload #'app)
;           {:port  3000 :join? false})

(def system (web-server-system {:host        "localhost"
                                :port        5432
                                :http-server #(run-jetty (wrap-reload #'app)
                                                         {:port  3449
                                                          :join? false})}))

(defn -main []
  ; PRODUCTION BUILD
  ; (component/start system)

  ; DEVELOPEMENT BUILD
  (alter-var-root #'system component/start)
  )