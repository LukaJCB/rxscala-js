package rxscalajs.subscription

import rxscalajs.facade.SubjectFacade
import scala.scalajs.js.annotation.JSGlobal
import scala.scalajs.js

@js.native @JSGlobal
class SubjectSubscription protected () extends Subscription {
  def this(subject: SubjectFacade[js.Any], observer: ObserverFacade[js.Any]) = this()
  var subject: SubjectFacade[js.Any] = js.native
  var observer: ObserverFacade[js.Any] = js.native
}


