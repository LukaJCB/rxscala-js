
import scala.scalajs.js
import js.annotation._
import js.|

package rxscalajs {

@js.native
class SubjectSubscription protected () extends Subscription {
  def this(subject: Subject[js.Any], observer: Observer[js.Any]) = this()
  var subject: Subject[js.Any] = js.native
  var observer: Observer[js.Any] = js.native
  var isUnsubscribed: Boolean = js.native
  def unsubscribe(): Unit = js.native
}

}
