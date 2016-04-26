
import scala.scalajs.js
import js.annotation._
import js.|

package rxscalajs {

@js.native
class ReplaySubject[T] protected () extends Subject[T] {
  def this(bufferSize: Double = ???, windowTime: Double = ???, scheduler: Scheduler = ???) = this()
}

}
