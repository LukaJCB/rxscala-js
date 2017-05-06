package rxscalajs.subscription

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal


@js.native @JSGlobal
class OuterSubscriber[T, R] extends Subscriber[T] {
  def notifyNext(outerValue: T, innerValue: R, outerIndex: Double, innerIndex: Double, innerSub: InnerSubscriber[T, R]): Unit = js.native
  def notifyError(error: js.Any, innerSub: InnerSubscriber[T, R]): Unit = js.native
  def notifyComplete(innerSub: InnerSubscriber[T, R]): Unit = js.native
}


