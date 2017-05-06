package rxscalajs.subscription

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal


@js.native @JSGlobal
class InnerSubscriber[T, R] protected() extends Subscriber[R] {
  def this(parent: OuterSubscriber[T, R], outerValue: T, outerIndex: Double) = this()


}


