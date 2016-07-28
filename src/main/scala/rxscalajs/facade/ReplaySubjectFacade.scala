package rxscalajs.facade
import rxscalajs.Scheduler

import scala.scalajs.js



@js.native
class ReplaySubjectFacade[T] protected() extends SubjectFacade[T] {
  def this(bufferSize: Double = ???, windowTime: Double = ???, scheduler: Scheduler = ???) = this()
}
