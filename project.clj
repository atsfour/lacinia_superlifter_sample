(defproject lacinia-superlifter-sample "0.1.0-SNAPSHOT"
  :description "Lacinia Superlifter Sample"
  :url ""
  :license {:name "Eclipse Public License 2.0"
            :url "http://www.eclipse.org/legal/epl-v20.html"}
  :dependencies [[com.walmartlabs/lacinia-pedestal "1.1"]
                 [duct.module.pedestal "2.1.4"]
                 [duct/core "0.8.0"]
                 [io.aviso/logging "1.0"]
                 [io.aviso/pretty "1.1.1"]
                 [org.clojure/clojure "1.11.1"]
                 [superlifter "0.1.3"]]
  :plugins [[duct/lein-duct "0.12.3"]]
  :middleware [lein-duct.plugin/middleware]
  :source-paths   ["src/clj"]
  :test-paths     ["test/clj"]
  :resource-paths ["resources" "target/resources"]
  :prep-tasks     ["javac" "compile" ["run" ":duct/compiler"]]
  :profiles
  {:repl {:prep-tasks   ^:replace ["javac" "compile"]
          :repl-options {:init-ns user}}
   :dev  [:project/dev :profiles/dev]
   :project/dev {:source-paths   ["dev/src"]
                 :resource-paths ["dev/resources"]
                 :dependencies   [[integrant/repl "0.3.2"]]
                 :plugins [[lein-ancient "0.7.0"]]}
   :project/test {}
   :profiles/dev {}
   :profiles/test {}})
