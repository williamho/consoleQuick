name := "consoleQuick"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.7"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

scalacOptions ++= Seq("-feature","-deprecation", "-unchecked", "-Xlint")
scalacOptions in Test ++= Seq("-Yrangepos")

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.2.5",
  "com.typesafe" % "config" % "1.3.0",
  "com.typesafe.play" %% "play-json" % "2.4.3",
  "org.specs2" %% "specs2-core" % "3.0" % "test",
  "org.specs2" %% "specs2-mock" % "3.0" % "test"
)

// Commands to run on `sbt console` or `sbt consoleQuick`
initialCommands := """
  import com.typesafe.config._
  import play.api.libs.json._
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent._
  import scala.concurrent.duration._

  import shapeless._
  import shapeless.ops.function._
  import shapeless.ops.hlist._
  import shapeless.ops.record._
  import shapeless.record._
  import shapeless.syntax.singleton._
  import shapeless.syntax.std._
  import shapeless.syntax.std.function._
  import shapeless.syntax.std.traversable._

  implicit val timeout = 10.seconds

  // Utility for evaluating futures
  // e.g., Future.successful("Hello").await
  implicit class FutureWithAwait[T](f: Future[T]) {
    def await() = Await.result(f, 10.seconds)
  }
"""

