package com.example

import japgolly.scalajs.react._
import scala.scalajs.js.JSApp
import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom.document

object TutorialApp extends JSApp {
  case class State(
    text: String = "Placeholder",
    items: List[String] = List.empty)

  val Echoer = ReactComponentB[Unit]("Echo")
    .initialState(new State)
    .renderBackend[Backend]
    .buildU

  class Backend($: BackendScope[Unit, State]) {
    def render(s: State) = <.div(
      <.form(
        <.input(
          ^.onChange ==> handleChange,
          ^.value := s.text
        ),
        <.button(
          "Submit",
          ^.onClick ==> handleSubmit
        )
      ),
      <.ul(
        s.items.map(<.li(_))
      )
    )

    def handleSubmit(e: ReactEventI): Callback = {
      e.preventDefaultCB >>
      $.modState { s =>
        if (s.text.nonEmpty) {
          val newItems: List[String] = s.items :+ s.text
          State("", newItems)
        } else s
      }
    }

    def handleChange(e: ReactEventI) = {
      $.modState(s => s.copy(text = e.target.value))
    }
  }

  def main(): Unit = {
    val holder = document.querySelector("#content")
    ReactDOM.render(Echoer(), holder)
  }
}

