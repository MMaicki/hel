(ns hel.cljs.re-frame.external
  (:require [re-frame.core :as rf]))

(rf/reg-sub :external/launches (fn [db _]
                                 (:launches db)))