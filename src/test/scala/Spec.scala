import org.specs2.mutable._

import com.typesafe.config._
import play.api.libs.json._

import shapeless._
import shapeless.ops.function._
import shapeless.syntax.std._
import shapeless.syntax.std.function._
import shapeless.syntax.std.traversable._
import shapeless.record._
import shapeless.ops.record._
import shapeless.syntax.singleton._

class Spec extends Specification {

  "applyL" >> {
    def sum3(a: Int, b: Int, c: Int): Int = a + b + c

    def applyL[L <: HList, F, R](l: L, f: F)(implicit f2p: FnToProduct.Aux[F, L => R]) = {
      f.toProduct(l)
    }

    applyL(1 :: 2 :: 3 :: HNil, sum3 _) must_== 6
  }

  "something" >> {
    case class MyClass(a: Int, b: Int, c: Int)

    val gen = LabelledGeneric[MyClass]

    val h = ('a ->> 1 :: 'b ->> 2 :: 'c ->>3 :: HNil)

    val updated = h.updatedElem('a ->> 100).updatedElem('b ->> 5)

    gen.from(updated) must_== MyClass(100, 5, 3)

    updated.values.toList must_== List(100, 5, 3)
  }

  "some other thing" >> {
    import shapeless._
    import shapeless.syntax.singleton._
    import shapeless.ops.hlist._

    class PartialConstructor[C, Default <: HList, Repr <: HList]
    (default: Default)
    (implicit lgen: LabelledGeneric.Aux[C, Repr]) {
      def apply[Args <: HList, Full <: HList]
      (args: Args)
      (implicit prepend: Prepend.Aux[Default, Args, Full],
        align: Align[Full, Repr]): C =
          lgen.from(align(default ++ args))
    }

    class Reshaper[C]() {
      def apply[Default <: HList, Repr <: HList]
      (default: Default)
      (implicit lgen: LabelledGeneric.Aux[C, Repr]) =
        new PartialConstructor[C, Default, Repr](default)
    }

    def reshape[C] = new Reshaper[C]

    case class MyClass(a: Double, b: String, c: Int)

    val default = 'b ->> "Hello" :: HNil

    val r = reshape[MyClass](default)

    r('a ->> 123.4 :: 'c ->> 534 :: HNil) must_== MyClass(123.4, "Hello", 534)
  }
}

