package rxscalajs

import scala.scalajs.js

/**
  * Created by Luka on 19.05.2016.
  */
class Subject[T] protected(inner: SubjectFacade[T]) extends Observable[T] with Observer[T] with ISubscription {

    def next(value: T): Unit = inner.next(value)
    def error(err: js.Any): Unit = inner.error(err)
    def asObservable(): Observable[T] = new Observable[T](inner.asObservable())


}

object Subject {
  def apply[T](): Subject[T] = new Subject(new SubjectFacade())
}

