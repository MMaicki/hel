(ns hel.cljs.routes.routes
  (:require [secretary.core :as secretary :refer-macros [defroute] :include-macros true]))

(defroute "/" []
          (println "/"))

(defroute "/about" []
          (println "/about"))