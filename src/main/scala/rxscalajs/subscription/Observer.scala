package rxscalajs.subscription

import scala.scalajs.js



@js.native
trait NextObserver[T] extends js.Object {
  var isUnsubscribed: Boolean = js.native
  /**
    * Provides the Observer with new data.
    *
    * The [[rxscalajs.Observable]] calls this closure 0 or more times.
    *
    * The [[rxscalajs.Observable]] will not call this method again after it calls either `onCompleted` or `onError`.
    */
  def next(): Unit = js.native
}

@js.native
trait ErrorObserver[T] extends js.Object {
  var isUnsubscribed: Boolean = js.native
  /**
    * Notifies the Observer that the [[rxscalajs.Observable]] has experienced an error condition.
    *
    * If the [[rxscalajs.Observable]] calls this method, it will not thereafter call `onNext` or `onCompleted`.
    */
  def error(): Unit = js.native
}

@js.native
trait CompletionObserver[T] extends js.Object {
  var isUnsubscribed: Boolean = js.native
  /**
    * Notifies the Observer that the [[rxscalajs.Observable]] has finished sending push-based notifications.
    *
    * The [[rxscalajs.Observable]] will not call this method if it calls `onError`.
    */
  def complete(): Unit = js.native
}
/**
Provides a mechanism for receiving push-based notifications.
  *
  * After an Observer calls an [[rxscalajs.Observable]]'s `subscribe` method, the Observable
  * calls the Observer's `onNext` method to provide notifications. A well-behaved Observable will
  * call an Observer's `onCompleted` or `onError` methods exactly once.
  */
trait Observer[T]


