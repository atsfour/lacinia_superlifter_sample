(ns lacinia-superlifter-sample.resolver
  (:require
    [lacinia-superlifter-sample.repository :as repository]
    [superlifter.api :refer [def-superfetcher]]
    [superlifter.core :refer [enqueue!]]
    [superlifter.lacinia :refer [with-superlifter]]))


(def simple-resolver-map
  {:Query/persons (fn [_ _ _] (repository/list-persons))
   :Person/friends (fn [_ _ {:keys [id]}] (repository/fetch-friends-by-id id))})


(def joined-resolver-map
  {:Query/persons (fn [_ _ _] (repository/list-persons-with-friends))})


(def-superfetcher FetchFriends [id]
  (fn [many _]
    (let [result (repository/list-friends-by-ids (map :id many))]
      (map #(get result (:id %)) many))))


(def superlifter-resolver-map
  {:Query/persons (fn [_ _ _] (repository/list-persons))
   :Person/friends (fn [context _ {:keys [id]}]
                     (with-superlifter context
                       (enqueue! (-> context :request :superlifter) :friends (->FetchFriends id))))})
