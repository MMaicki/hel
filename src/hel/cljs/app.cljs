(ns hel.cljs.app
  (:require [hel.cljs.client.layout :refer [layout]]))

(defn app []
  (fn []
    [layout
     [:header {:key :header}
      [:nav {}
       [:div {}
        [:a {:href "/about"}
         "About"]
        [:a {:href "/"}
         "Home"]]]]

     [:div {:id :container
            :key :container}
      [:h2 {} "Container Div"]]

     [:footer {:key :footer}
      [:div {} "Footer"]]]))