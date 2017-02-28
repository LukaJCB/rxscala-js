package rxscalajs.dom

import scala.scalajs.js


final case class Request(url: String,
                         data: String = "",
                         timeout: Int = 0,
                         headers: Map[String, String] = Map.empty,
                         crossDomain: Boolean = false,
                         responseType: String = "")

final case class Response(body: String, status: Int, responseType: String)

object Ajax {
  import scala.scalajs.js.JSConverters._
  def toJsRequest(request: Request): AjaxRequest = {
    js.Dynamic.literal(
      url = request.url,
      body = request.data,
      timeout = request.timeout,
      headers = request.headers.toJSDictionary,
      crossDomain = request.crossDomain,
      responseType = request.responseType
    ).asInstanceOf[AjaxRequest]
  }

  def fromJsResponse(response: AjaxResponse): Response = {
    Response(
      response.responseText,
      response.status.toInt,
      response.responseType
    )
  }
}