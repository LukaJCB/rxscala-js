package rxscalajs
import scala.scalajs.js
import js.annotation._
import js.|
import rxscalajs.facade.SubjectFacade

@js.native
class SubjectSubscription protected () extends Subscription {
  def this(subject: SubjectFacade[js.Any], observer: Observer[js.Any]) = this()
  var subject: SubjectFacade[js.Any] = js.native
  var observer: Observer[js.Any] = js.native
}


