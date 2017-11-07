package rxscalajs

import cats._

class CombineObservable[A](val value: Observable[A]) extends AnyVal
object CombineObservable {
  implicit def combineObservableApplicative: Applicative[CombineObservable] = new Applicative[CombineObservable] {
    def pure[A](x: A): CombineObservable[A] = new CombineObservable(Observable.just(x))

    def ap[A, B](ff: CombineObservable[(A) => B])(fa: CombineObservable[A]) = new CombineObservable(
      ff.value.combineLatestWith(fa.value)((f, a) => f(a))
    )

    override def map[A, B](fa: CombineObservable[A])(f: A => B): CombineObservable[B] =
      new CombineObservable(fa.value.map(f))

    override def product[A, B](fa: CombineObservable[A], fb: CombineObservable[B]): CombineObservable[(A, B)] =
      new CombineObservable(fa.value.combineLatest(fb.value))
  }
}
