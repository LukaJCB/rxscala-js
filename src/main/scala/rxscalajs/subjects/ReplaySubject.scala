package rxscalajs.subjects

import rxscalajs.{Subject, Scheduler}
import rxscalajs.facade.ReplaySubjectFacade


class ReplaySubject[T] protected(inner: ReplaySubjectFacade[T]) extends Subject[T](inner)


object ReplaySubject {
  def apply[T](): ReplaySubject[T] = new ReplaySubject(new ReplaySubjectFacade())
  def withSize[T](bufferSize: Int): ReplaySubject[T] = new ReplaySubject(new ReplaySubjectFacade(bufferSize.toDouble))
  def withTime[T](time: Int, scheduler: Scheduler): ReplaySubject[T] = new ReplaySubject(new ReplaySubjectFacade(windowTime = time.toDouble, scheduler = scheduler))
  def withTimeAndSize[T](time: Int, size: Int, scheduler: Scheduler): ReplaySubject[T] = new ReplaySubject(new ReplaySubjectFacade(size.toDouble,time.toDouble,scheduler))
}

