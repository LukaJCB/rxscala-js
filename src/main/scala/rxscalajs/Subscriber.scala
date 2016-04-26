
import scala.scalajs.js
import js.annotation._
import js.|

package rxscalajs {

@js.native
class Subscriber[T]  () extends Subscription with Observer[T] {
  def this(destinationOrNext: Observer[js.Any] | js.Function1[T, Unit] = ???, error: js.Function1[js.Any, Unit] = ???, complete: js.Function0[Unit] = ???) = this()
  var syncErrorValue: js.Any = js.native
  var syncErrorThrown: Boolean = js.native
  var syncErrorThrowable: Boolean = js.native
  var isStopped: Boolean = js.native
  var destination: Observer[js.Any] = js.native
  def next(value: T = ???): Unit = js.native
  def error(err: js.Any = ???): Unit = js.native
}

@js.native
object Subscriber extends js.Object {
  def create[T](next: js.Function1[T, Unit] = ???, error: js.Function1[js.Any, Unit] = ???, complete: js.Function0[Unit] = ???): Subscriber[T] = js.native
}

}
