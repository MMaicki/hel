(ns hel.cljs.client.components.header
  (:require [material-ui :as mui]
            [reagent.core :as r]))

;; PASSING DOM ELEMENTS REF https://github.com/reagent-project/reagent/blob/master/doc/FAQ/UsingRefs.md
(defn header []
  ; (println "MUI" (keys (js->clj mui {:keywordize-keys true})))
  (let [r-is-open? (r/atom false)
        !ref       (atom nil)]
    (fn []
      (let [is-open? @r-is-open?
            ref      @!ref
            on-close #(reset! r-is-open? (not is-open?))]
        [:header {:ref (fn [el]
                         (reset! !ref el))}
         (when ref
           [:> mui/Menu {:open      is-open?
                         :anchor-el ref}
            [:> mui/MenuItem {:on-click on-close} "Home"]])
         [:nav {}
          [:div {}
           [:> mui/Button {:on-click on-close} "Open Menu"]
           [:a {:href "/about"}
            "About"]
           [:a {:href "/"}
            "Home"]
           [:a {:href "/login"}
            "Login"]]]]))))

