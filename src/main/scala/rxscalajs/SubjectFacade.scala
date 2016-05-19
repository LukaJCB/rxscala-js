
import scala.scalajs.js
import js.annotation._
import js.|

package rxscalajs {

@js.native
  @JSName("Rx.Subject")
class SubjectFacade[T] protected() extends ObservableFacade[T] with Observer[T] with ISubscription {
  def this(destination: Observer[T] = ???, source: ObservableFacade[T] = ???) = this()

  def next(value: T): Unit = js.native
  def error(err: js.Any = ???): Unit = js.native
  def asObservable(): ObservableFacade[T] = js.native
  def throwIfUnsubscribed(): js.Dynamic = js.native
}

@js.native
object SubjectFacade extends js.Object {
  var create: js.Function = js.native
}

}
