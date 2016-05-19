
import scala.scalajs.js
import js.annotation._
import js.|

package rxscalajs {

@js.native
trait AnonymousSubscription extends js.Object {
  def unsubscribe(): Unit = js.native
}


@js.native
trait ISubscription extends AnonymousSubscription {
  def add(teardown: AnonymousSubscription ): Subscription = js.native
  def remove(sub: Subscription): Unit = js.native
}

@js.native
class Subscription protected () extends ISubscription {
  def this(unsubscribe: js.Function0[Unit] = js.native) = this()
}

@js.native
object Subscription extends js.Object {
  var EMPTY: Subscription = js.native
}

@js.native
class UnsubscriptionError protected () extends Error {
  def this(errors: js.Array[js.Any]) = this()
}

}
