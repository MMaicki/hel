(ns hel.cljs.re-frame.root
  (:require [hel.cljs.re-frame.routes]
            [hel.cljs.re-frame.external]
            [re-frame.core :as rf]
            [clojure.string :as str]))

(rf/reg-event-fx :config/init (fn [{:keys [db event] :as cofx} event-name]
                                (let [path (.. js/window -location -pathname)]
                                  {:db {:page {:current (keyword (if (or
                                                                       (= path "/")
                                                                       path)
                                                                   path
                                                                   (str/replace path #"/" "")))}
                                        :init {:date      (.now js/Date)
                                               :initiator (str event-name)}}})))
