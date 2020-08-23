(ns hel.cljs.routes.routes
  (:require [secretary.core :as secretary :refer-macros [defroute] :include-macros true]
            [re-frame.core :as rf]))

(defroute "/" []
          (rf/dispatch [:routes/home]))

(defroute "/about" []
          (println "/about"))