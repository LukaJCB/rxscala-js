package rxscalajs
import scala.scalajs.js
import js.annotation._
import js.|



@js.native
/**
* An extension of the [[Observer]] trait which adds subscription handling
* (unsubscribe, isUnsubscribed, and `add` methods) and backpressure handling
* (onStart and request methods).
*
* After a [[Subscriber]] calls an [[Observable]]'s `subscribe` method, the
* [[Observable]] calls the [[Subscriber]]'s onNext method to emit items. A well-behaved
* [[Observable]] will call a [[Subscriber]]'s onCompleted method exactly once or the [[Subscriber]]'s
* onError method exactly once.
*
*
*/
class Subscriber[T]  () extends Subscription {
  def this(destinationOrNext: Observer[js.Any] | js.Function1[T, Unit] = ???, error: js.Function1[js.Any, Unit] = ???, complete: js.Function0[Unit] = ???) = this()

  def next(value: T = ???): Unit = js.native
  def error(err: js.Any = ???): Unit = js.native
}

@js.native
object Subscriber extends js.Object {
  def create[T](next: js.Function1[T, Unit] = ???, error: js.Function1[js.Any, Unit] = ???, complete: js.Function0[Unit] = ???): Subscriber[T] = js.native
}


