
import scala.scalajs.js
import js.annotation._
import js.|

package rxscalajs {

@js.native
class BehaviorSubjectFacade[T] protected() extends SubjectFacade[T] {
  def this(_value: T) = this()
  def getValue(): T = js.native
  var value: T = js.native
}

}
