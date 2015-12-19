name := "example"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-feature","-deprecation", "-unchecked", "-Xlint")

enablePlugins(ScalaJSPlugin)

scalaJSStage in Global := FastOptStage

skip in packageJSDependencies := false

persistLauncher in Compile := true

persistLauncher in Test := false

jsDependencies in Test += RuntimeDOM

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.8.2",
  "com.lihaoyi" %%% "utest" % "0.3.0" % "test",
  "com.github.japgolly.scalajs-react" %%% "core" % "0.10.2"
)

jsDependencies ++= Seq(
  "org.webjars.bower" % "react" % "0.14.3"
    /        "react-with-addons.js"
    minified "react-with-addons.min.js"
    commonJSName "React",

  "org.webjars.bower" % "react" % "0.14.3"
    /         "react-dom.js"
    minified  "react-dom.min.js"
    dependsOn "react-with-addons.js"
    commonJSName "ReactDOM")

testFrameworks += new TestFramework("utest.runner.Framework")

