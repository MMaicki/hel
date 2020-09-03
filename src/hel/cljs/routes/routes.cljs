(ns hel.cljs.routes.routes
  (:require [secretary.core :as secretary :refer-macros [defroute] :include-macros true]
            [ajax.core :refer [GET POST]]
            [re-frame.core :as rf]))

(defroute "/" []
          (rf/dispatch [:routes/set-page :/]))

(defroute "/about" []
          (rf/dispatch [:routes/set-page :about]))

(defroute "/login" []
          (rf/dispatch [:routes/set-page :login]))