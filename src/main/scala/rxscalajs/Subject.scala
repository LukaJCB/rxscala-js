
import scala.scalajs.js
import js.annotation._
import js.|

package rxscalajs {

@js.native
class Subject[T] protected () extends Observable[T] with Observer[T] with ISubscription {
  def this(destination: Observer[T] = ???, source: Observable[T] = ???) = this()
  var destination: Observer[T] = js.native
  var observers: js.Array[Observer[T]] = js.native
  var isStopped: Boolean = js.native
  var hasErrored: Boolean = js.native
  var errorValue: js.Any = js.native
  var dispatching: Boolean = js.native
  var hasCompleted: Boolean = js.native
  def next(value: T): Unit = js.native
  def error(err: js.Any = ???): Unit = js.native
  def asObservable(): Observable[T] = js.native
  def throwIfUnsubscribed(): js.Dynamic = js.native
}

@js.native
object Subject extends js.Object {
  var create: js.Function = js.native
}

}
