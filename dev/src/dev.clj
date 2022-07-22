(ns dev
  "開発用名前空間"
  (:require
    [clojure.java.io :as io]
    [clojure.repl :refer :all]
    [duct.core :as duct]
    [integrant.repl]))


(duct/load-hierarchy)


(defn read-config
  "`config.edn` の設定を読み込み、マップデータとして返す。"
  []
  (duct/read-config (io/resource "lacinia_superlifter_sample/config.edn")))


(defn reset
  "ソースコードの変更を検出してリロードし、システムを起動または再起動する。"
  []
  (let [result (integrant.repl/reset)]
    result))


(def profiles
  [:duct.profile/dev :duct.profile/local])


(integrant.repl/set-prep! #(duct/prep-config (read-config) profiles))
