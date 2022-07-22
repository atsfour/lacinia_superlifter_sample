(ns lacinia-superlifter-sample.repository
  (:require
    [clojure.tools.logging :as log]))


(def ^:const ^:private persons
  [{:id 1
    :name "夜神 月"}
   {:id 2
    :name "L"}
   {:id 3
    :name "夜神 総一郎"}
   {:id 4
    :name "弥 海砂"}
   {:id 5
    :name "ニア"}])


(def ^:const ^:private person-friendships
  [{:person_id 1 :friend_id 2}
   {:person_id 1 :friend_id 4}
   {:person_id 2 :friend_id 1}
   {:person_id 2 :friend_id 3}
   {:person_id 2 :friend_id 5}
   {:person_id 3 :friend_id 1}
   {:person_id 3 :friend_id 2}
   {:person_id 4 :friend_id 1}
   {:person_id 4 :friend_id 2}
   {:person_id 4 :friend_id 3}
   {:person_id 5 :friend_id 2}])


(def ^:private person-map
  (-> (group-by :id persons)
      (update-vals first)))


(def ^:private friendship-map
  (-> (group-by :person_id person-friendships)
      (update-vals #(map (fn [{:keys [friend_id]}] (get person-map friend_id)) %))))


(defn- db-access-log
  [name args]
  (log/info (str "Repository accessed. name: " name " args: " args)))


(defn list-persons
  []
  (db-access-log "list-persons" [])
  persons)


(defn fetch-friends-by-id
  [id]
  (db-access-log "fetch-friends-by-id" [id])
  (get friendship-map id))


(defn list-persons-with-friends
  []
  (db-access-log "list-persons-with-friends" [])
  (map #(assoc % :friends (get friendship-map (:id %))) persons))
