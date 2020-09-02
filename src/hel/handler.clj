(ns hel.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.util.response :refer [response file-response]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file :refer [wrap-file]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.not-modified :refer [wrap-not-modified]]
            [hiccup.page :refer [html5 include-css]]
            [hiccup.core :refer [html]]
            [hel.db.db :refer [pg-db]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [clojure.java.jdbc :as jdbc]))

(defn wrap-current-user-id [handler]
  (fn [request]
    (let [user-id (-> request :session :user-id)]
      (handler (assoc request :user-id user-id)))))

(defn root-render [req]
  (html5
    [:head {}
     [:meta {:charset "utf-8"}]
     ; [:meta {:http-equiv "Content-Security-Policy" :content "default-src 'self'"}]
     [:meta {:name "application-name" :content "HEL APP"}]
     [:meta {:name "theme-color" :content "#dedede"}]
     [:meta {:name "robots" :content "index,follow"}]
     [:title "HEL app"]
     [:meta {:name :viewport :content "width=device-width, initial-scale=1"}]
     [:link {:type "text/css", :href "css/style.css", :rel "stylesheet"}]]
    [:body {}
     [:div#app]
     [:script {:type "text/javascript"
               :src  "/js/compiled/app.js"}]
     [:script {:type "text/javascript"
               :src  "/templates/test.js"}]]))

(defn login-authenticate
  [req]
  (let [username (:username req)
        password (:password req)
        body     (:body req)]
    (println body "\n" req)
    (response {:message "LOGIN"
               :body    (str body)})))

(defroutes app-routes
           (GET "/" req (root-render req))
           (GET "/about" req (root-render req))
           (wrap-json-response
             (GET "/hello" request
               (response {:name    "Marcin"
                          :surname "Maicki"})))

           (POST "/login" req (login-authenticate req))
           (GET "/login" req (root-render req))

           #_(GET "/public/js/compiled/app.js" req
             (file-response "app.js" {:root "/public/js/compiled/"}))
           #_(GET "/templates/test.js" req
             (file-response "test.js" {:root "/templates/"}))
           #_(GET "/favicon.ico" req
             (file-response "favicon.ico" {:root "resources/public/"}))

           (context "/api" []
             (wrap-json-response
               (GET "/" request
                 (response {:id :test
                            :data (jdbc/query pg-db "select * from users;")})))
             (wrap-json-response
               (GET "/users" request
                 (jdbc/insert-multi! pg-db :users
                                  [{:id 1 :first_name "Marcin" :last_name "Maicki" :email "test@test.com" :admin true :is_active false :pass "1234567"}
                                   {:id 2 :first_name "xD" :last_name "xD" :email "test@test2.com" :admin false :is_active true :pass "1234567"}])
                 (response {:id :test
                            :data (jdbc/query pg-db "select * from users;")}))))
           (GET "/foobar" [x y :as {u :uri rm :request-method}] ; http://localhost:3000/foobar?x=foo&y=bar&z=baz&w=qux
             (str "'x' is \"" x "\"\n"
                  "'y' is \"" y "\"\n"
                  "The request URI was \"" u "\"\n"
                  "The request method was \"" rm "\""))
           (wrap-current-user-id
             (GET "/user/:user-id" [user-id message]        ; ?message=xd
               (str "The current user ID is: " user-id " " message)))
           (route/not-found "Not Found"))

(def app
  (-> app-routes
      (wrap-defaults api-defaults)
      (wrap-resource "public")
      wrap-content-type
      wrap-json-response
      (wrap-json-body {:keywords? true :bigdecimals? true})
      wrap-not-modified))
