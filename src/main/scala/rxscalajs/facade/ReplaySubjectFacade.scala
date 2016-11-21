package rxscalajs.facade
import rxscalajs.Scheduler

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName


@js.native
@JSName("Rx.ReplaySubject")
class ReplaySubjectFacade[T] protected() extends SubjectFacade[T] {
  def this(bufferSize: Int = ???, windowTime: Int = ???, scheduler: Scheduler = ???) = this()
}
