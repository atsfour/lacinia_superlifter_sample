(ns lacinia-superlifter-sample.resolver
  (:require
    [clojure.tools.logging :as log]
    [lacinia-superlifter-sample.repository :as repository]))


(def simple-resolver-map
  {:Query/persons (fn [_ _ _] (repository/list-persons))
   :Person/friends (fn [_ _ {:keys [id]}] (repository/fetch-friends-by-id id))})

(def joined-resolver-map
  {:Query/persons (fn [_ _ _] (repository/list-persons-with-friends))})
