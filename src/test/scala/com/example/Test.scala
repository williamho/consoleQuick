package com.example

import utest._
import org.scalajs.dom
import org.scalajs.dom.{ document, html }

object Test extends TestSuite {
  def tests = TestSuite {
    'Hello {
      val div = document.createElement("div")
      div.classList.add("some-class")
      div.innerHTML = "Hello"

      document.body.appendChild(div)
      assert(document.querySelector(".some-class").innerHTML == "Hello")
    }
  }
}

