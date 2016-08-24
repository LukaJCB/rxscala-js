package rxscalajs

import rxscalajs.facade.SubjectFacade

import scala.scalajs.js

/**
  * A Subject is an Observable and an Observer at the same time.
  *
  */
class Subject[T] protected(inner: SubjectFacade[T]) extends Observable[T](inner) with Observer[T] {

    def next(value: T): Unit = inner.next(value)
    def error(err: js.Any): Unit = inner.error(err)
    def complete(): Unit = inner.complete()
    def asObservable(): Observable[T] = this
    def unsubscribe(): Unit = inner.unsubscribe()


}
/**
  * Subject that, once an `Observer` has subscribed, emits all subsequently observed items to the
  * subscriber.
  * <p>
  * <img width="640" height="405" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/S.PublishSubject.png" alt="" />
  * <p>
 *
  * @example
{{{
  val subject = Subject[String]()
  // observer1 will receive all onNext and onCompleted events
  subject.subscribe(observer1)
  subject.onNext("one")
  subject.onNext("two")
  // observer2 will only receive "three" and onCompleted
  subject.subscribe(observer2)
  subject.onNext("three")
  subject.onCompleted()
  }}}
  */
object Subject {
  /**
    * Creates and returns a new `Subject`.
    *
    * @return the new `Subject`
    */
  def apply[T](): Subject[T] = new Subject(new SubjectFacade())
}

