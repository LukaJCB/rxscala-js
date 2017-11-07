package rxscalajs

import cats.Eq
import cats.tests.CatsSuite
import org.scalacheck.{Arbitrary, Gen}
import cats.laws.discipline._
import cats.kernel.laws.discipline._

class LawTests extends CatsSuite {

  implicit def arbObservable[A: Arbitrary]: Arbitrary[Observable[A]] =
    Arbitrary(Gen.oneOf((for {
        e <- Arbitrary.arbitrary[A]
        e2 <- Arbitrary.arbitrary[A]
      } yield Observable.of(e, e2)
    ), (for {
      e <- Arbitrary.arbitrary[A]
    } yield Observable.of(e))))

  implicit def eqObservable[A: Eq]: Eq[Observable[A]] = new Eq[Observable[A]] {
    def eqv(x: Observable[A], y: Observable[A]) = {
      var a: Seq[A] = Seq()
      var b: Seq[A] = Seq()
      x.toSeq.subscribe(xs => a = xs)
      y.toSeq.subscribe(ys => b = ys)

      a.toList === b.toList
    }
  }

  implicit def eqCombineObservable[A: Eq]: Eq[CombineObservable[A]] = Eq.by(_.value)
  implicit def arbCombineObservable[A: Arbitrary]: Arbitrary[CombineObservable[A]] =
    Arbitrary(arbObservable[A].arbitrary.map(a => new CombineObservable(a)))

  checkAll("Observable.MonadLaws", MonadTests[Observable].monad[Int, Int, String])
  checkAll("Observable.MonoidKLaws", MonoidKTests[Observable].monoidK[Int])
  checkAll("CombineObservable.ApplicativeLaws", ApplicativeTests[CombineObservable].apply[Int, Int, String])
  checkAll("Observable.ParallelLaws", ParallelTests[Observable, CombineObservable].parallel[Int, String])
}
