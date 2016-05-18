package rxscalajs.facades

import rxscalajs.Scheduler

import scala.scalajs.js

/**
* Created by Luka on 18.05.2016.
*/
@js.native
class ErrorObservableFacade protected() extends ObservableFacade[js.Any] {
  def this(error: js.Any,scheduler: Scheduler = ???) = this()
}
