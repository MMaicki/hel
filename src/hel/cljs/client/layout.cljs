(ns hel.cljs.client.layout)

(defn layout [& children]
  (fn [& children]
    [:<>
     children
     [:div {} "--- layout base ---"]]))