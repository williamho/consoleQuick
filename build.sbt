name := "consoleQuick"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.7"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

scalacOptions ++= Seq("-feature","-deprecation", "-unchecked", "-Xlint")
scalacOptions in Test ++= Seq("-Yrangepos")

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2-core" % "3.7" % "test",
  "org.specs2" %% "specs2-mock" % "3.7" % "test",
  "org.typelevel" %% "cats" % "0.4.1"
)

// Commands to run on `sbt console` or `sbt consoleQuick`
initialCommands := """
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent._
  import scala.concurrent.duration._

  implicit val timeout = 10.seconds

  // Utility for evaluating futures
  // e.g., Future.successful("Hello").await
  implicit class FutureWithAwait[T](f: Future[T]) {
    def await() = Await.result(f, 10.seconds)
  }
"""

