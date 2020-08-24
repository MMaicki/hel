(ns hel.db.database
  (:require [hel.handler :refer [app]]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.reload :refer [wrap-reload]]))

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

#_(defn init-db! [& args]
  (jdbc/with-db-connection [conn {:datasource @datasource}]
                           (let [rows (jdbc/query conn "SELECT 1")]
                             (println "DB INIT!" rows)))
  (jdbc/db-do-commands "postgresql://localhost:5432/hel?user=postgres&password=nxCXwqKhfdw4o2"
                       (jdbc/create-table-ddl :testing [[:id "BIGINT" "UNIQUE" "NOT NULL"]
                                                        [:applied "TIMESTAMP" "" ""]
                                                        [:description "VARCHAR(1024)" "" ""]]))
  (close-datasource @datasource))
