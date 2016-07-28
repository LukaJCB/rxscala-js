package rxscalajs.facade
import rxscalajs.{AnonymousSubscription, Subscriber}

import scala.scalajs.js



@js.native
class AsyncSubjectFacade[T] extends SubjectFacade[T] {
  var value: T = js.native
  var hasNext: Boolean = js.native
  def _subscribe(subscriber: Subscriber[js.Any]): AnonymousSubscription = js.native
  def _next(value: T): Unit = js.native
  def _complete(): Unit = js.native
}
