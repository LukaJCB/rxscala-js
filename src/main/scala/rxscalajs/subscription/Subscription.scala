package rxscalajs.subscription

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName


@js.native
trait AnonymousSubscription extends js.Object {
  /**
    * Call this method to stop receiving notifications on the Observer that was registered when
    * this Subscription was received.
    */
  def unsubscribe(): Unit = js.native
}


@js.native
/**
  * Subscriptions are returned from all `Observable.subscribe` methods to allow unsubscribing.
  *
  * This interface is the equivalent of `IDisposable` in the .NET Rx implementation.
  */
class Subscription protected () extends AnonymousSubscription {
  def add(teardown: AnonymousSubscription ): Subscription = js.native
  def remove(sub: Subscription): Unit = js.native
  def this(unsubscribe: js.Function0[Unit] = js.native) = this()

  val closed: Boolean = js.native

  @JSName("closed")
  val isUnsubscribed: Boolean = js.native
}

@js.native
@JSName("Rx.Subscription")
object Subscription extends js.Object {
  var EMPTY: Subscription = js.native
}



