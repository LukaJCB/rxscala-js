package rxscalajs.facades

import rxscalajs.AnonymousSubscription

import scala.scalajs.js

/**
* Created by Luka on 18.05.2016.
*/
@js.native
trait Subscribable[T] extends js.Object {
  def subscribe(onNext: js.Function1[T, Unit], error: js.Function1[js.Any, Unit] = ???, complete: js.Function0[Unit] = ???): AnonymousSubscription = js.native
}
