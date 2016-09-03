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

@js.native
@JSName("Rx.Scheduler")
/**
  * Represents an object that schedules units of work.
  */
object Scheduler extends js.Object{
  /**
    * Schedules on a queue in the current event frame (trampoline scheduler).
    * Use this for iteration operations.
    */
  val queue: Scheduler = js.native
  /**
    * Schedules on the micro task queue, which uses the fastest transport mechanism available,
    * either Node.js'`process.nextTick()` or Web Worker MessageChannel or setTimeout or others.
    * Use this for asynchronous conversions.
    */
  val asap: Scheduler = js.native
  /**
    * Schedules work with `setInterval`. Use this for time-based operations.
    */
  val async: Scheduler = js.native
}


