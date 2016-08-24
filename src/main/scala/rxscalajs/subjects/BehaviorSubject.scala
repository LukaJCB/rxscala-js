package rxscalajs.subjects

import rxscalajs.Subject
import rxscalajs.facade.BehaviorSubjectFacade

/**
  * Created by Luka on 21.05.2016.
  */
class BehaviorSubject[T] protected(inner: BehaviorSubjectFacade[T]) extends Subject[T](inner)


object BehaviorSubject {
  def apply[T](defaultValue: T): BehaviorSubject[T] = new BehaviorSubject(new BehaviorSubjectFacade(defaultValue))
}