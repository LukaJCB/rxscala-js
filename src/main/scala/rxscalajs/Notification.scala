
import scala.scalajs.js
import js.annotation._
import js.|

package rxscalajs {

@js.native
class Notification[T] protected () extends js.Object {
  def this(kind: String, value: T = ???, exception: js.Any = ???) = this()
  var kind: String = js.native
  var value: T = js.native
  var exception: js.Any = js.native
  var hasValue: Boolean = js.native
  def observe(observer: Observer[T]): js.Dynamic = js.native
  def `do`(next: js.Function1[T, Unit], error: js.Function1[js.Any, Unit] = ???, complete: js.Function0[Unit] = ???): js.Dynamic = js.native
  def accept(nextOrObserver: Observer[T] | js.Function1[T, Unit], error: js.Function1[js.Any, Unit] = ???, complete: js.Function0[Unit] = ???): js.Dynamic = js.native
  def toObservable(): Observable[T] = js.native
}

@js.native
object Notification extends js.Object {
  def createNext[T](value: T): Notification[T] = js.native
  def createError[T](err: js.Any = js.native): Notification[T] = js.native
  def createComplete(): Notification[js.Any] = js.native
}

}
