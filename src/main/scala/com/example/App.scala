package com.example

import japgolly.scalajs.react._
import scala.scalajs.js.JSApp
import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom.document

object TutorialApp extends JSApp {
  val HelloMessage = ReactComponentB[String]("HelloMessage")
    .render($ => <.div("Hello ", $.props))
    .build

  def main(): Unit = {
    ReactDOM.render(HelloMessage("World!"), document.querySelector("#content"))
  }
}

