package rxscalajs

import rxscalajs.subscription.ObserverFacade

import scala.scalajs.js
import js.annotation._
import js.|



  import rxscalajs.facade.ObservableFacade

@js.native
@JSName("Rx.Notification")
/**
  * Emitted by Observables returned by [[rxscalajs.Observable.materialize]].
  */
class Notification[T] protected () extends js.Object {
  def this(kind: String, value: T = ???, exception: js.Any = ???) = this()
  var kind: String = js.native
  var value: T = js.native
  var exception: js.Any = js.native
  var hasValue: Boolean = js.native
  def observe(observer: ObserverFacade[T]): js.Dynamic = js.native
  def `do`(next: js.Function1[T, Unit], error: js.Function1[js.Any, Unit] = ???, complete: js.Function0[Unit] = ???): js.Dynamic = js.native
  /**
    * Invokes the function corresponding to the notification.
    *
    * @param onNext
    *               The function to invoke for an [[rxscalajs.Notification]] notification.
    * @param onError
    *               The function to invoke for an [[rxscalajs.Notification]] notification.
    * @param onCompleted
    *               The function to invoke for an [[rxscalajs.Notification]] notification.
    */
  def accept(onNext: ObserverFacade[T] | js.Function1[T, Unit], onError: js.Function1[js.Any, Unit] = ???, onCompleted: js.Function0[Unit] = ???): js.Dynamic = js.native
  def toObservable(): ObservableFacade[T] = js.native
}

@js.native
@JSName("Rx.Notification")
object Notification extends js.Object {
  def createNext[T](value: T): Notification[T] = js.native
  def createError[T](err: js.Any = js.native): Notification[T] = js.native
  def createComplete(): Notification[js.Any] = js.native
}


