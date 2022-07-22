(ns lacinia-superlifter-sample.graphql
  (:require
    [clojure.java.io :as io]
    [com.walmartlabs.lacinia.parser.schema :as parser.schema]
    [com.walmartlabs.lacinia.pedestal2 :as lacinia.pedestal2]
    [com.walmartlabs.lacinia.schema :as schema]
    [com.walmartlabs.lacinia.util :as util]
    [integrant.core :as ig]
    [io.pedestal.http :as http]
    [lacinia-superlifter-sample.resolver :as resolver]))


(defmethod ig/init-key ::schema
  [_ {:keys [path]}]
  (-> (io/resource path)
      slurp
      (parser.schema/parse-schema)
      (util/inject-resolvers resolver/joined-resolver-map)))


(defn routes
  [interceptors {:keys [api-path ide-path asset-path]
                 :as options}]
  (into #{[api-path :post interceptors
           :route-name ::graphql-api]
          [ide-path :get (lacinia.pedestal2/graphiql-ide-handler options)
           :route-name ::graphiql-ide]}
        (lacinia.pedestal2/graphiql-asset-routes asset-path)))


(defmethod ig/init-key ::service
  [_ {:keys [schema options]}]
  (let [compiled-schema (schema/compile schema)
        interceptors (lacinia.pedestal2/default-interceptors compiled-schema (:app-context options))]
    (lacinia.pedestal2/enable-graphiql
      {:env (:env options)
       ::http/routes (routes interceptors options)
       ::http/allowed-origins (constantly true)
       ::http/container-options {}})))
