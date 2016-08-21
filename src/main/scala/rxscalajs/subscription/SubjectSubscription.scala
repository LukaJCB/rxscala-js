package rxscalajs.subscription

import rxscalajs.facade.SubjectFacade

import scala.scalajs.js

@js.native
class SubjectSubscription protected () extends Subscription {
  def this(subject: SubjectFacade[js.Any], observer: Observer[js.Any]) = this()
  var subject: SubjectFacade[js.Any] = js.native
  var observer: Observer[js.Any] = js.native
}


