name := "consoleQuick"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-feature","-deprecation", "-unchecked", "-Xlint")

libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.3.0",
  "com.typesafe.play" % "play-json_2.11" % "2.4.3"
)

// Commands to run on `sbt console` or `sbt consoleQuick`
initialCommands := """
  import com.typesafe.config._
  import play.api.libs.json._
"""

