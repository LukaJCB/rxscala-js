package rxscalajs.facade
import rxscalajs.subscription.{AnonymousSubscription, Subscriber}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport


@js.native
@JSImport("rxjs/Rx", "AsyncSubject", globalFallback = "Rx.AsyncSubject")
class AsyncSubjectFacade[T] extends SubjectFacade[T] {
  var value: T = js.native
  var hasNext: Boolean = js.native
  def _subscribe(subscriber: Subscriber[js.Any]): AnonymousSubscription = js.native
  def _next(value: T): Unit = js.native
  def _complete(): Unit = js.native
}
