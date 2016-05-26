package rxscalajs

import scala.scalajs.js

/**
  * Created by Luka on 19.05.2016.
  */
class Subject[T] protected(inner: SubjectFacade[T]) extends Observable[T](inner) with Observer[T] {

    def next(value: T): Unit = inner.next(value)
    def error(err: js.Any): Unit = inner.error(err)
    def asObservable(): Observable[T] = this
    def unsubscribe(): Unit = inner.unsubscribe()


}

object Subject {
  def apply[T](): Subject[T] = new Subject(new SubjectFacade())
}

