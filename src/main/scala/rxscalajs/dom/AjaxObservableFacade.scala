
import scala.scalajs.js
import js.annotation._
import js.|
import org.scalajs.dom._

package rxscalajs {

  import rxscalajs.facade.ObservableFacade

  @js.native
trait AjaxRequest extends js.Object {
  var url: String = js.native
  var body: js.Any = js.native
  var user: String = js.native
  var async: Boolean = js.native
  var method: String = js.native
  var headers: Object = js.native
  var timeout: Double = js.native
  var password: String = js.native
  var hasContent: Boolean = js.native
  var crossDomain: Boolean = js.native
  var createXHR: js.Function0[XMLHttpRequest] = js.native
  var progressSubscriber: Subscriber[js.Any] = js.native
  var resultSelector: js.Function = js.native
  var responseType: String = js.native
}

@js.native
trait
AjaxCreationMethod extends js.Object {
  def apply[T](urlOrRequest: String | AjaxRequest): ObservableFacade[T] = js.native
  def get[T](url: String, resultSelector: js.Function1[AjaxResponse, T] = ???, headers: Object = ???): ObservableFacade[T] = js.native
  def post[T](url: String, body: js.Any = ???, headers: Object = ???): ObservableFacade[T] = js.native
  def put[T](url: String, body: js.Any = ???, headers: Object = ???): ObservableFacade[T] = js.native
  def delete[T](url: String, headers: Object = ???): ObservableFacade[T] = js.native
  def getJSON[T, R](url: String, resultSelector: js.Function1[T, R] = ???, headers: Object = ???): ObservableFacade[R] = js.native
}

@js.native
class AjaxObservableFacade[T] protected() extends ObservableFacade[T] {
  def this(urlOrRequest: String | AjaxRequest) = this()
}

@js.native
object AjaxObservableFacade extends js.Object {
  var create: AjaxCreationMethod = js.native
}

@js.native
class AjaxSubscriber[T] protected () extends Subscriber[Event] {
  def this(destination: Subscriber[T], request: AjaxRequest) = this()
  var request: AjaxRequest = js.native
}

@js.native
class AjaxResponse protected () extends js.Object {
  def this(originalEvent: Event, xhr: XMLHttpRequest, request: AjaxRequest) = this()
  var originalEvent: Event = js.native
  var xhr: XMLHttpRequest = js.native
  var request: AjaxRequest = js.native
  var status: Double = js.native
  var response: js.Any = js.native
  var responseText: String = js.native
  var responseType: String = js.native
}

@js.native
class Error protected() extends js.Object


@js.native
class AjaxError protected () extends Error {
  def this(message: String, xhr: XMLHttpRequest, request: AjaxRequest) = this()
  var xhr: XMLHttpRequest = js.native
  var request: AjaxRequest = js.native
  var status: Double = js.native
}

@js.native
class AjaxTimeoutError protected () extends AjaxError {
  def this(xhr: XMLHttpRequest, request: AjaxRequest) = this()
}

@js.native
object Rxscalajs extends js.GlobalScope {
  def ajaxGet[T](url: String, resultSelector: js.Function1[AjaxResponse, T] = ???, headers: Object = ???): AjaxObservableFacade[T] = js.native
  def ajaxPost[T](url: String, body: js.Any = ???, headers: Object = ???): ObservableFacade[T] = js.native
  def ajaxDelete[T](url: String, headers: Object = ???): ObservableFacade[T] = js.native
  def ajaxPut[T](url: String, body: js.Any = ???, headers: Object = ???): ObservableFacade[T] = js.native
  def ajaxGetJSON[T, R](url: String, resultSelector: js.Function1[T, R] = ???, headers: Object = ???): ObservableFacade[R] = js.native
}

}
