package rxscalajs

/**
  * Created by Luka on 21.05.2016.
  */
class AsyncSubject[T] protected(inner: AsyncSubjectFacade[T]) extends Subject[T](inner)

object AsyncSubject {
  def apply[T](): AsyncSubject[T] = new AsyncSubject(new AsyncSubjectFacade())
}