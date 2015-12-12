package com.example

import scala.scalajs.js.JSApp
import org.scalajs.dom
import org.scalajs.dom.{ document, html }

object TutorialApp extends JSApp {
  def main(): Unit = {
    val input = document.createElement("input").asInstanceOf[html.Input]
    document.body.appendChild(input)

    val button = document.createElement("button")
    button.innerHTML = "Add"
    button.addEventListener("click", { e: dom.Event =>
      if (input.value.nonEmpty) {
        addDiv(document.body, input.value)
        input.value = ""
      }
    })

    document.body.appendChild(button)
  }

  def addDiv(parent: dom.Element, text: String): Unit = {
    val d = document.createElement("div")
    d.innerHTML = text
    parent.appendChild(d)
  }
}

