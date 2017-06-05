package rxscalajs.subscription

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport


@js.native
@JSImport("rxjs/Rx", "InnerSubscription", globalFallback = "Rx.InnerSubscription")
class InnerSubscriber[T, R] protected() extends Subscriber[R] {
  def this(parent: OuterSubscriber[T, R], outerValue: T, outerIndex: Double) = this()
}


