
import scala.scalajs.js
import js.annotation._
import js.|

package rxscalajs {

  @js.native
  class InnerSubscriber[T, R] protected() extends Subscriber[R] {
    def this(parent: OuterSubscriber[T, R], outerValue: T, outerIndex: Double) = this()


  }


}