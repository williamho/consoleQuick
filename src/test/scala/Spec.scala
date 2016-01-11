import org.specs2.mutable._
import org.specs2.mock.Mockito

class Spec extends Specification with Mockito {
  class Timer {
    // Want to mock this method to just return `body`
    def time(body: => String): String = {
      val start = System.currentTimeMillis()
      val result = body
      val end = System.currentTimeMillis()
      println(s"${end - start}ms")

      result
    }

    def thisShouldBeCalledOnce(): String = "hello"
  }

  class MyClass(timer: Timer) {
    def doSomething(): String = {
      //println("inside method `doSomething`")

      timer.time {
        //println("inside method `doSomething` in `timer.time`")
        timer.thisShouldBeCalledOnce()
      }
    }
  }

  "timer.thisShouldBeCalledOnce should be called once" >> {
    val timer = mock[Timer]
    timer.thisShouldBeCalledOnce returns "hi"

    timer.time(anyString) answers { (params: Any, mock: Any) =>
      //println("inside `answers`, before params match")
      params match {
        case Array(f: Function0[String] @unchecked) => f()
      }
    }

    val c = new MyClass(timer)
    c.doSomething()
    there was one(timer).thisShouldBeCalledOnce() // Is actually called 3 times
  }
}

