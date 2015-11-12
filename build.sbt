name := "consoleQuick"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.7"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

scalacOptions ++= Seq("-feature","-deprecation", "-unchecked", "-Xlint")
scalacOptions in Test ++= Seq("-Yrangepos")

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.2.5",
  "com.typesafe" % "config" % "1.3.0",
  "com.typesafe.play" % "play-json_2.11" % "2.4.3",
  "org.specs2" %% "specs2-core" % "3.0" % "test"
)

// Commands to run on `sbt console` or `sbt consoleQuick`
initialCommands := """
  import com.typesafe.config._
  import play.api.libs.json._
"""

