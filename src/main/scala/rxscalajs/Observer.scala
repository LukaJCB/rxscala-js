package rxscalajs

import scala.scalajs.js

/**
  * Provides a mechanism for receiving push-based notifications.
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
