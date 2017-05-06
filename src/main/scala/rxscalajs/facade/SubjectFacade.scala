package rxscalajs.facade
import rxscalajs.subscription.{AnonymousSubscription, ObserverFacade}

import scala.scalajs.js
import scala.scalajs.js.annotation._



@js.native
@JSGlobal("Rx.Subject")
class SubjectFacade[T] protected() extends ObservableFacade[T] with AnonymousSubscription with ObserverFacade[T] {
  def this(destination: ObserverFacade[T] = ???, source: ObservableFacade[T] = ???) = this()

  override def next(value: T): Unit = js.native
  override def error(err: js.Any = ???): Unit = js.native
  override def complete(): Unit = js.native
  def asObservable(): ObservableFacade[T] = js.native
  def throwIfUnsubscribed(): js.Dynamic = js.native
}

@js.native @JSGlobal
object SubjectFacade extends js.Object {
  var create: js.Function = js.native
}
