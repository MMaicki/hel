(ns hel.cljs.re-frame.routes
  (:require [re-frame.core :as rf]))

(rf/reg-event-fx :routes/home
                 (fn [{:keys [db]} [_ data]] ; _ - event name
                   {:db (assoc-in db [:current-user] data)}))

