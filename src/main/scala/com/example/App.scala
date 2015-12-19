package com.example

import japgolly.scalajs.react._
import scala.scalajs.js.JSApp
import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom.document

object TutorialApp extends JSApp {
  case class State(
    text: String,
    items: List[String])

  val Echoer = ReactComponentB[Unit]("Echo")
    .initialState(State("", List("Some", "Items", "Here")))
    .renderBackend[Backend]
    .buildU

  class Backend($: BackendScope[Unit, State]) {
    def render(s: State) = <.div(
      <.form(
        <.input(
          ^.value := s.text,
          ^.placeholder := "TODO",
          ^.onChange ==> handleChange
        ),
        <.button(
          "Submit",
          ^.onClick ==> handleSubmit
        )
      ),
      <.ul(
        s.items.zipWithIndex.map { case (item: String, index: Int) =>
          <.li(
            "[",
            <.a("x", ^.onClick --> handleRemove(index)),
            "] ",
            item
          )
        }
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

    def handleRemove(i: Int): Callback = {
      $.modState { s =>
        val newItems: List[String] = s.items.take(i) ++ s.items.drop(i+1)
        s.copy(items = newItems)
      }
    }
  }

  def main(): Unit = {
    val holder = document.querySelector("#content")
    ReactDOM.render(Echoer(), holder)
  }
}

