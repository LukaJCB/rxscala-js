package rxscalajs.scheduler

import rxscalajs.subscription.Subscription

import scala.scalajs.js
import js.annotation._
import js.|



  import rxscalajs.Scheduler

  @js.native
trait Action[T] extends Subscription {
  var work: js.Function1[T, Unit] | Subscription = js.native
  var state: T = js.native
  var delay: Double = js.native
  def schedule(state: T = ???, delay: Double = ???): Unit = js.native
  def execute(): Unit = js.native
  var scheduler: Scheduler = js.native
  var error: js.Any = js.native
}


