import org.specs2.mutable._
import org.specs2.mock.Mockito

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

class Spec extends Specification with Mockito {
  "Hello world" >> {
    1 must_== 1
  }
}

