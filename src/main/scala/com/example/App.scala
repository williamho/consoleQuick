package com.example

import japgolly.scalajs.react._
import scala.scalajs.js.JSApp
import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom.document

object TutorialApp extends JSApp {
  case class State(text: String)

  val Echoer = ReactComponentB[Unit]("Echo")
    .initialState(State("Initial value"))
    .renderBackend[Backend]
    .buildU

  class Backend($: BackendScope[Unit, State]) {
    def render(s: State) = {
      <.div(
        <.input(
          ^.onChange ==> handleChange,
          ^.value := s.text
        ),
        <.div(s.text.toUpperCase)
      )
    }

    def handleChange(e: ReactEventI) = {
      val newState = State(e.target.value)
      $.setState(newState)
    }
  }

  def main(): Unit = {
    val holder = document.querySelector("#content")
    ReactDOM.render(Echoer(), holder)
  }
}

