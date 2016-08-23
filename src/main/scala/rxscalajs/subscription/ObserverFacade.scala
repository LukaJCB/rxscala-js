package rxscalajs.subscription

import scala.scalajs.js



@js.native
trait NextObserver[T] extends js.Object {
  /**
    * Provides the Observer with new data.
    *
    * The [[rxscalajs.Observable]] calls this closure 0 or more times.
    *
    * The [[rxscalajs.Observable]] will not call this method again after it calls either `onCompleted` or `onError`.
    */
  def next(t: T): Unit = js.native
}

@js.native
trait ErrorObserver[T] extends js.Object {
  /**
    * Notifies the Observer that the [[rxscalajs.Observable]] has experienced an error condition.
    *
    * If the [[rxscalajs.Observable]] calls this method, it will not thereafter call `onNext` or `onCompleted`.
    */
  def error(err :js.Any): Unit = js.native
}

@js.native
trait CompletionObserver[T] extends js.Object {
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
@js.native
trait ObserverFacade[T] extends NextObserver[T] with ErrorObserver[T] with CompletionObserver[T]

/**
Provides a mechanism for receiving push-based notifications.
  *
  * After an Observer calls an [[rxscalajs.Observable]]'s `subscribe` method, the Observable
  * calls the Observer's `onNext` method to provide notifications. A well-behaved Observable will
  * call an Observer's `onCompleted` or `onError` methods exactly once.
  */
trait Observer[T] {
  /**
    * Provides the Observer with new data.
    *
    * The [[rxscalajs.Observable]] calls this closure 0 or more times.
    *
    * The [[rxscalajs.Observable]] will not call this method again after it calls either `completed` or `error`.
    */
  def next(t: T): Unit
  /**
    * Notifies the Observer that the [[rxscalajs.Observable]] has experienced an error condition.
    *
    * If the [[rxscalajs.Observable]] calls this method, it will not thereafter call `next` or `completed`.
    */
  def error(err: js.Any): Unit
  /**
    * Notifies the Observer that the [[rxscalajs.Observable]] has finished sending push-based notifications.
    *
    * The [[rxscalajs.Observable]] will not call this method if it calls `error`.
    */
  def complete(): Unit
}
