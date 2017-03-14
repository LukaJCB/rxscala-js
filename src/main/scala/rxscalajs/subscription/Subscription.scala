package rxscalajs.subscription

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName
import scala.scalajs.js.|


@js.native
trait AnonymousSubscription extends js.Object {
  /**
    * Call this method to stop receiving notifications on the Observer that was registered when
    * this Subscription was received.
    */
  def unsubscribe(): Unit = js.native
}


@js.native @JSGlobal
/**
  * Subscriptions are returned from all `Observable.subscribe` methods to allow unsubscribing.
  *
  * This interface is the equivalent of `IDisposable` in the .NET Rx implementation.
  */
class Subscription protected () extends AnonymousSubscription {
  def add(teardown: Subscription | js.Function0[Unit]): Subscription = js.native
  def remove(sub: Subscription): Unit = js.native
  def this(unsubscribe: js.Function0[Unit] = js.native) = this()

  def closed: Boolean = js.native

  @JSName("closed")
  def isUnsubscribed: Boolean = js.native
}

@js.native
@JSGlobal("Rx.Subscription")
object Subscription extends js.Object {
  var EMPTY: Subscription = js.native
}



