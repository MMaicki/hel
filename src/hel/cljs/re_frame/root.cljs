(ns hel.cljs.re-frame.root
  (:require [hel.cljs.re-frame.routes]
            [re-frame.core :as rf]))

(rf/reg-event-fx :config/init (fn [{:keys [db event] :as cofx} event-name]
                                {:db {:init {:date      (.now js/Date)
                                             :initiator (str event-name)}}}))
