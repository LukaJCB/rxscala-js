
import scala.scalajs.js
import js.annotation._
import js.|

package rxscalajs {

@js.native
class ConnectableObservable[T] protected () extends Observable[T] {
  def this(source: Observable[T], subjectFactory: js.Function0[Subject[T]]) = this()
  def connect(): Subscription = js.native
  def refCount(): Observable[T] = js.native
}

}
