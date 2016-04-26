
import scala.scalajs.js
import js.annotation._
import js.|

package rxscalajs {

@js.native
class InnerSubscriber[T, R] protected () extends Subscriber[R] {
  def this(parent: OuterSubscriber[T, R], outerValue: T, outerIndex: Double) = this()
  var parent: js.Any = js.native
  var outerValue: js.Any = js.native
  var outerIndex: js.Any = js.native
  var index: js.Any = js.native

}
