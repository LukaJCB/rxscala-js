package rxscalajs.facade
import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport


@js.native
@JSImport("rxjs/Rx", "BehaviorSubject", globalFallback = "Rx.BehaviorSubject")
class BehaviorSubjectFacade[T] protected() extends SubjectFacade[T] {
  def this(_value: T) = this()
  def getValue(): T = js.native
  var value: T = js.native
}
