
import scala.scalajs.js
import js.annotation._
import js.|

package rxscalajs {

@js.native
class AsyncSubject[T] extends Subject[T] {
  var value: T = js.native
  var hasNext: Boolean = js.native
  def _subscribe(subscriber: Subscriber[js.Any]): TeardownLogic = js.native
  def _next(value: T): Unit = js.native
  def _complete(): Unit = js.native
}

}
