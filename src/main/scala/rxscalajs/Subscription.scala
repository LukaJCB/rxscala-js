package rxscalajs
import scala.scalajs.js
import js.annotation._
import js.|



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
}

@js.native
object Subscription extends js.Object {
  var EMPTY: Subscription = js.native
}



