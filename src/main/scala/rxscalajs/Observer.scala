
import scala.scalajs.js
import js.annotation._
import js.|

package rxscalajs {

@js.native
trait NextObserver[T] extends js.Object {
  var isUnsubscribed: Boolean = js.native
  var complete: js.Function0[Unit] = js.native
}

@js.native
trait ErrorObserver[T] extends js.Object {
  var isUnsubscribed: Boolean = js.native
  var complete: js.Function0[Unit] = js.native
}

@js.native
trait CompletionObserver[T] extends js.Object {
  var isUnsubscribed: Boolean = js.native
  var complete: js.Function0[Unit] = js.native
}

trait Observer[T]



}
