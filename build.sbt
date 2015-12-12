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
  "com.lihaoyi" %%% "utest" % "0.3.0" % "test"
)

testFrameworks += new TestFramework("utest.runner.Framework")

