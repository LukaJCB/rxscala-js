package rxscalajs.subjects


import rxscalajs.Subject
import rxscalajs.facade.AsyncSubjectFacade


class AsyncSubject[T] protected(inner: AsyncSubjectFacade[T]) extends Subject[T](inner)

object AsyncSubject {
  def apply[T](): AsyncSubject[T] = new AsyncSubject(new AsyncSubjectFacade())
}