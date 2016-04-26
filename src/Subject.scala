
import scala.scalajs.js
import js.annotation._
import js.|

package rxscalajs {

@js.native
class Subject[T] protected () extends Observable[T] with Observer[T] with ISubscription {
  def this(destination: Observer[T] = ???, source: Observable[T] = ???) = this()
  var destination: Observer[T] = js.native
  var source: Observable[T] = js.native
  var observers: js.Array[Observer[T]] = js.native
  var isUnsubscribed: Boolean = js.native
  var isStopped: Boolean = js.native
  var hasErrored: Boolean = js.native
  var errorValue: js.Any = js.native
  var dispatching: Boolean = js.native
  var hasCompleted: Boolean = js.native
  def lift[T, R](operator: Operator[T, R]): Observable[T] = js.native
  def add(subscription: TeardownLogic): Subscription = js.native
  def remove(subscription: Subscription): Unit = js.native
  def unsubscribe(): Unit = js.native
  def _subscribe(subscriber: Subscriber[T]): TeardownLogic = js.native
  def _unsubscribe(): Unit = js.native
  def next(value: T): Unit = js.native
  def error(err: js.Any = ???): Unit = js.native
  def complete(): Unit = js.native
  def asObservable(): Observable[T] = js.native
  def _next(value: T): Unit = js.native
  def _finalNext(value: T): Unit = js.native
  def _error(err: js.Any): Unit = js.native
  def _finalError(err: js.Any): Unit = js.native
  def _complete(): Unit = js.native
  def _finalComplete(): Unit = js.native
  def throwIfUnsubscribed(): js.Dynamic = js.native
}

@js.native
object Subject extends js.Object {
  var create: js.Function = js.native
}

}
