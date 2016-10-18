package rxscalajs.subjects

import rxscalajs.Subject
import rxscalajs.facade.BehaviorSubjectFacade


class BehaviorSubject[T] protected(inner: BehaviorSubjectFacade[T]) extends Subject[T](inner)


object BehaviorSubject {
  def apply[T](defaultValue: T): BehaviorSubject[T] = new BehaviorSubject(new BehaviorSubjectFacade(defaultValue))
}