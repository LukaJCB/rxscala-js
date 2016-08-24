package rxscalajs

import rxscalajs.subscription.Subscription

import scala.scalajs.js
import js.annotation._
import js.|



@js.native
/**
  * Represents an object that schedules units of work.
  */
trait Scheduler extends js.Object {
  /**
    * @return the scheduler's notion of current absolute time in milliseconds.
    */
  def now(): Double = js.native
  /**
    * Schedules an Action for execution.
    *
    * @param work the Action to schedule
    * @return a subscription to be able to unsubscribe the action (unschedule it if not executed)
    */
  def schedule[T](work: js.Function1[T, Subscription | Unit], delay: Double = ???, state: T = ???): Subscription = js.native
  def flush(): Unit = js.native
}


