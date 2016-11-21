package rxscalajs.subjects

import rxscalajs.{Scheduler, Subject}
import rxscalajs.facade.ReplaySubjectFacade

import scala.concurrent.duration.FiniteDuration


class ReplaySubject[T] protected(inner: ReplaySubjectFacade[T]) extends Subject[T](inner)


object ReplaySubject {
  def apply[T](): ReplaySubject[T] = new ReplaySubject(new ReplaySubjectFacade())
  def withSize[T](bufferSize: Int): ReplaySubject[T] = new ReplaySubject(new ReplaySubjectFacade(bufferSize))
  def withTime[T](time: FiniteDuration, scheduler: Scheduler): ReplaySubject[T] = new ReplaySubject(new ReplaySubjectFacade(windowTime = time.toMillis.toInt, scheduler = scheduler))
  def withTimeAndSize[T](time: FiniteDuration, size: Int, scheduler: Scheduler): ReplaySubject[T] = new ReplaySubject(new ReplaySubjectFacade(size, time.toMillis.toInt,scheduler))
}

