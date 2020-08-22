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
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defn wrap-current-user-id [handler]
  (fn [request]
    (let [user-id (-> request :session :user-id)]
      (handler (assoc request :user-id user-id)))))

(defn root-render [req]
  (html [:html {}
          [:head {}
           [:link {:type "text/css", :href "css/style.css", :rel "stylesheet"}]]
          [:body {}
           [:div#app]
           [:p {} (str req)]
           [:script {:type "text/javascript"
                     :src  "/resources/public/js/compiled/app.js"}]
           [:script {:type "text/javascript"
                     :src  "/resources/templates/test.js"}]]]))

(defroutes app-routes
           (GET "/" req (root-render req))

           (GET "/resources/templates/test.js" req
             (file-response "test.js" {:root "resources/templates/"}))

           (context "/api" []
             (wrap-json-response
               (GET "/" request
                 (response {:name "Marcin"
                            :request (str request)})))
             (GET "/users" request
               (html5 [:h1 {} "hiccup"])))
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
      (wrap-defaults site-defaults)
      (wrap-resource "public")
      wrap-content-type
      wrap-not-modified))
