import org.specs2.mock.Mockito
import org.specs2.mutable._
import org.specs2.concurrent.ExecutionEnv
import org.specs2.specification.ExecutionEnvironment
import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class Spec extends Specification {
  // Define algebra
  sealed trait CalculatorA[A]
  case class Reset(value: Int) extends CalculatorA[Int]
  case class Add(value: Int) extends CalculatorA[Int]
  case class Subtract(value: Int) extends CalculatorA[Int]
  case class Multiply(value: Int) extends CalculatorA[Int]
  case class Divide(value: Int) extends CalculatorA[Int]

  // Wrap it with Free
  import cats.free.Free

  type Calculator[A] = Free[CalculatorA, A]

  // Lift the algebra into the Free
  import cats.free.Free.liftF

  def reset(value: Int = 0): Calculator[Int] =
    liftF[CalculatorA, Int](Reset(value))
  def add(value: Int): Calculator[Int] =
    liftF[CalculatorA, Int](Add(value))
  def subtract(value: Int): Calculator[Int] =
    liftF[CalculatorA, Int](Subtract(value))
  def multiply(value: Int): Calculator[Int] =
    liftF[CalculatorA, Int](Multiply(value))
  def divide(value: Int): Calculator[Int] =
    liftF[CalculatorA, Int](Divide(value))

  // Define a compiler
  import cats.{Id, ~>}

  def idCompiler = new (CalculatorA ~> Id) {
    @volatile var value: Int = 0

    def apply[A](fa: CalculatorA[A]): Id[A] = {
      fa match {
        case Reset(v) =>
          value = v
          value
        case Add(v) =>
          value = value + v
          value
        case Subtract(v) =>
          value = value - v
          value
        case Multiply(v) =>
          value = value * v
          value
        case Divide(v) =>
          value = value / v // Might throw an exception!
          value
      }
    }
  }

  // Define a program
  def program: Calculator[Int] = for {
    _ <- reset(5) // 5
    _ <- multiply(2) // 10
    _ <- divide(5) // 2
    _ <- subtract(3) // -1
    r <- add(10) // 9
  } yield r


  // Run the program
  "Id" >> {
    val result: Int = program.foldMap(idCompiler)

    result must_== 9
  }

  // Run the program with a different compiler
  "Future" >> { implicit ee: ExecutionEnv =>
    import cats.std.future._

    def futureCompiler = new (CalculatorA ~> Future) {
      val underlying: (CalculatorA ~> Id) = idCompiler

      def apply[A](fa: CalculatorA[A]): Future[A] =
        Future.successful(underlying(fa))
    }

    val result: Future[Int] = program.foldMap(futureCompiler)

    result must be_==(9).await
  }

  "List" >> {
    import cats.std.list._

    def listCompiler = new (CalculatorA ~> List) {
      @volatile var results: List[Any] = List.empty

      val underlying: (CalculatorA ~> Id) = idCompiler

      def apply[A](fa: CalculatorA[A]): List[A] = {
        val result = underlying(fa)
        results = result :: results
        println(results)
        List(result)
      }
    }

    val result: List[Int] = program.foldMap(listCompiler)

    result must_== List(9)
  }
}

