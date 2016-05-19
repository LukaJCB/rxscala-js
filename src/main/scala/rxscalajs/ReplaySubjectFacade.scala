
import scala.scalajs.js
import js.annotation._
import js.|

package rxscalajs {

@js.native
class ReplaySubjectFacade[T] protected() extends SubjectFacade[T] {
  def this(bufferSize: Double = ???, windowTime: Double = ???, scheduler: Scheduler = ???) = this()
}

}
