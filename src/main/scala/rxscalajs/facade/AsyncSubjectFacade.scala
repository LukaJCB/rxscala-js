package rxscalajs.facade
import rxscalajs.subscription.{Subscriber, AnonymousSubscription}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName


@js.native
@JSGlobal("Rx.AsyncSubject")
class AsyncSubjectFacade[T] extends SubjectFacade[T] {
  var value: T = js.native
  var hasNext: Boolean = js.native
  def _subscribe(subscriber: Subscriber[js.Any]): AnonymousSubscription = js.native
  def _next(value: T): Unit = js.native
  def _complete(): Unit = js.native
}
