
import scala.scalajs.js
import js._

package rxscalajs {

  import scala.scalajs.js.annotation.JSName

  @js.native
  trait Subscribable[T] extends js.Object {
    def subscribe(onNext: js.Function1[T, Unit], error: js.Function1[js.Any, Unit] = ???, complete: js.Function0[Unit] = ???): AnonymousSubscription = js.native
  }

  @js.native
  class GroupedObservable[K,T] protected() extends Observable[T] {
    def this(key: K, groupSubject: Subject[T], refCountSubscription: Subscription) = this()
  }
  @js.native
  class TimeInterval[T] protected() extends js.Object {
    def this(value: T,interval: Int) = this()
  }

  @js.native
  class Timestamp[T] protected() extends js.Object {
    def this(value: T,interval: Int) = this()
  }

  @js.native
  class ErrorObservable protected() extends Observable[js.Any] {
    def this(error: js.Any,scheduler: Scheduler = ???) = this()
  }


  @js.native
  @JSName("Rx.Observable")
  class Observable[T] protected() extends Subscribable[T] {
    def this(subscribe: js.Function = js.native) = this()

    var source: Observable[js.Any] = js.native
    var operator: Operator[js.Any, T] = js.native



    def audit[T2](durationSelector:  js.Function1[T,Subscribable[T2]]): Observable[T] = js.native
    def auditTime(delay: Int, scheduler: Scheduler = ???): Observable[T] = js.native
    def buffer[T2](closingNotifier: Observable[T2]): Observable[js.Array[T]] = js.native
    def bufferCount(bufferSize: Int, startBufferEvery: Int = ???): Observable[js.Array[T]] = js.native
    def bufferTime(bufferTimeSpan: Int, bufferCreationInterval: Int = ???, scheduler: Scheduler = ???): Observable[js.Array[T]] = js.native
    def bufferToggle[T2,O](openings: Subscribable[O], closingSelector:  js.Function1[O, Subscribable[T2]]): Observable[js.Array[T]] = js.native
    def bufferWhen[T2](closingSelector: js.Function0[Observable[T2]]): Observable[js.Array[T]] = js.native
    def cache(bufferSize: Int = ???, windowTime: Int = ???, scheduler: Scheduler = ???): Observable[T] = js.native
    def `catch`[T2,R](selector: js.Function2[T2, Observable[T],Observable[R]]): Observable[R] = js.native
    def combineAll[T2,R](project:  js.Function1[js.Array[T2],R]= ???): Observable[R] = js.native


    def combineLatest[T2, R](v2: Observable[T2], project:  js.Function2[T,T2,R] = ???): Observable[R] = js.native


    @JSName("concat")
    def ++[T2, R]( that: Observable[T2], scheduler: Scheduler = ???): Observable[R] = js.native
    def concat[T2, R](that: Observable[T2], scheduler: Scheduler = ???): Observable[R] = js.native

    def concatAll(): T = js.native

    def concatMap[I, R](project: js.Function2[T,Int,Observable[I]], resultSelector: js.Function4[T, I, Int, Int, R] = ???): Observable[R] = js.native


    def concatMapTo[I, R](innerObservable: Observable[I], resultSelector: js.Function4[T, I, Int, Int, R] = ???): Observable[R] = js.native


    def count(predicate: js.Function3[T, Int, Observable[T],Boolean]  = ???): Observable[Int] = js.native

    def debounce(durationSelector:  js.Function1[T, Subscribable[Int]]): Observable[T] = js.native
    def debounceTime(dueTime: Int, scheduler: Scheduler = ???): Observable[T] = js.native


    def defaultIfEmpty[R](defaultValue: R): Observable[R] = js.native
    def delay(delay: Int | Date, scheduler: Scheduler = ???): Observable[T] = js.native
    def delayWhen[U,I](delayDurationSelector:  js.Function1[T, Observable[U]], subscriptionDelay: Observable[I] = ???): Observable[T] = js.native
    def dematerialize[T2](): Observable[T2] = js.native
    def distinct[T2](compare: js.Function2[T,  T, Boolean] = ???, flushes: Observable[T2] = ???): Observable[T] = js.native
    def distinctKey[T2](key: String, compare: js.Function2[T,  T,Boolean] = ???, flushes: Observable[T2] = ???): Observable[T] = js.native
    def distinctUntilChanged[K](compare: js.Function2[K,  K,Boolean] = ???, keySelector: js.Function1[T,K] = ???): Observable[T] = js.native
    def distinctUntilKeyChanged(key: String, compare: js.Function2[ T,  T, Boolean] = ???): Observable[T] = js.native
    def `do`[T2](nextOrObserver: (Observer[T] | js.Function1[T ,Unit]) = ???, error: js.Function1[T2 ,Unit] = ???, complete: js.Function0[Unit] = ???): Observable[T] = js.native
    def elementAt(index: Int, defaultValue: T = ???): Observable[T] = js.native
    def every[T2](predicate: js.Function3[T,  Int,  Observable[T],Boolean], thisArg: T2 = ???): Observable[Boolean] = js.native
    def exhaust(): Observable[T] = js.native
    def exhaustMap[I, R](project: js.Function2[T, Int, Observable[R]], resultSelector: js.Function4[T, I, Int, Int, R] = ???): Observable[R] = js.native
    def expand[R](project: js.Function2[ T, Int, Observable[R]], concurrent: Int = ???, scheduler: Scheduler = ???): Observable[R] = js.native
    def filter[T2](predicate: js.Function2[ T,  Int, Boolean], thisArg: T2 = ???): Observable[T] = js.native
    def _finally(finallySelector: js.Function0[Unit]): Observable[T] = js.native
    def find[T2](predicate: js.Function3[T,  Int,  Observable[T],Boolean], thisArg: T2 = ???): Observable[T] = js.native
    def findIndex[T2](predicate: js.Function3[T,  Int,  Observable[T],Boolean], thisArg: T2 = ???): Observable[Int] = js.native
    def first[ R](predicate: js.Function3[T,  Int,  Observable[T],Boolean] = ???, resultSelector: js.Function2[T,Int,R] = ???, defaultValue: R = ???): Observable[T | R] = js.native
    def groupBy[K, R,T2](keySelector: js.Function1[T,K], elementSelector:  js.Function1[T,R]= ???, durationSelector:  js.Function1[GroupedObservable[K, R],Observable[T2]] = ???): Observable[GroupedObservable[K, R]] = js.native
    def ignoreElements(): Observable[T] = js.native
    def isEmpty(): Observable[Boolean] = js.native
    def last[ R](predicate:  js.Function3[T,  Int,  Observable[T],Boolean] = ???, resultSelector: js.Function2[T,Int,R] = ???, defaultValue: R = ???): Observable[T | R] = js.native
    def let[ R](func:  js.Function1[Observable[T],Observable[R]]): Observable[R] = js.native
    def map[T2, R](project: js.Function2[T,Int,R], thisArg: T2 = ???): Observable[R] = js.native
    def mapTo[ R](value: R): Observable[R] = js.native
    def materialize(): Observable[Notification[T]] = js.native
    def max(comparer: js.Function2[T,T,T] = ???): Observable[T] = js.native
    def merge[R >: T](that: Observable[R],concurrent: Int = ???, scheduler: Scheduler = ???): Observable[R] = js.native

    def mergeAll(concurrent: Int = ???): T = js.native
    def mergeMap[I, R](project: js.Function2[T, Int,Observable[I]], resultSelector: js.Function4[T, I, Int, Int, R] = ???, concurrent: Int = ???): Observable[R] = js.native
    def mergeMapTo[I, R](innerObservable: Observable[I], resultSelector: js.Function4[T, I, Int, Int, R] = ???, concurrent: Int = ???): Observable[R] = js.native
    def mergeScan[ R](project: js.Function2[R,T,Observable[R]], seed: R, concurrent: Int = ???): Observable[R] = js.native
    def min(comparer: js.Function2[T,T,T] = ???): Observable[T] = js.native
    def multicast(subjectOrSubjectFactory: Subject[T] | js.Function0[Subject[T]]): ConnectableObservable[T] = js.native

    def observeOn(scheduler: Scheduler, delay: Int = ???): Observable[T] = js.native

    def pairwise(): Observable[js.Array[T]] = js.native
    def partition[T2](predicate: js.Function1[T,Boolean], thisArg: T2 = ???): js.Array[Observable[T]] = js.native
    def pluck[R](properties: String*): Observable[R] = js.native
    def publish(): ConnectableObservable[T] = js.native

    def publishBehavior(value: T): ConnectableObservable[T] = js.native

    def publishLast(): ConnectableObservable[T] = js.native
    def publishReplay(bufferSize: Int = ???, windowTime: Int = ???, scheduler: Scheduler = ???): ConnectableObservable[T] = js.native

    def race(observables: (Observable[T] | js.Array[Observable[T]])*): Observable[T] = js.native
    def reduce[R](project: js.Function2[R,T,R],seed: R = ???): Observable[R] = js.native

    def repeat(count: Int = ???): Observable[T] = js.native

    def retry(count: Int = ???): Observable[T] = js.native
    def retryWhen[T2,T3](notifier: js.Function1[Observable[T2], Observable[T3]]): Observable[T] = js.native
    def sample[I](notifier: Observable[I]): Observable[T] = js.native

    def sampleTime(delay: Int, scheduler: Scheduler = ???): Observable[T] = js.native
    def scan[R](accumulator: js.Function2[R, T, R],seed: R = ???): Observable[R] = js.native
    def share(): Observable[T] = js.native
    def single(predicate: js.Function3[T, Int, Observable[T],Boolean] = ???): Observable[T] = js.native

    @JSName("skip")
    def drop(total: Int): Observable[T] = js.native

    def skip(total: Int): Observable[T] = js.native
    def skipUntil[T2](notifier: Observable[T2]): Observable[T] = js.native
    def skipWhile(predicate: js.Function2[T,Int,Boolean]): Observable[T] = js.native

    @JSName("startWith")
    def +:[U >: T](elem: U, scheduler: Scheduler = ???): Observable[U] = js.native

    def startWith[U >: T](v1: U, scheduler: Scheduler = ???): Observable[U] = js.native
    def subscribeOn(scheduler: Scheduler, delay: Int = ???): Observable[T] = js.native
    def switch(): T = js.native
    def switchMap[I, R](project: js.Function2[T, Int,Observable[I]], resultSelector: js.Function4[T, I, Int, Int, R] = ???): Observable[R] = js.native
    def switchMapTo[ I, R](innerObservable: Observable[I], resultSelector: js.Function4[T, I, Int, Int, R] = ???): Observable[R] = js.native
    def take(total: Int): Observable[T] = js.native
    def takeLast(total: Int): Observable[T] = js.native
    def takeUntil[T2](notifier: Observable[T2]): Observable[T] = js.native
    def takeWhile(predicate: js.Function2[T,Int,Boolean]): Observable[T] = js.native
    def throttle(durationSelector:  js.Function1[T, Subscribable[Int]]): Observable[T] = js.native
    def throttleTime(delay: Int, scheduler: Scheduler = ???): Observable[T] = js.native
    def timeInterval(scheduler: Scheduler = ???): Observable[TimeInterval[T]] = js.native
    def timeout[T2](due: Int | Date, errorToSend: T2 = ???, scheduler: Scheduler = ???): Observable[T] = js.native
    def timeoutWith[ R](due: Int | Date, withObservable: Observable[R], scheduler: Scheduler = ???): Observable[T | R] = js.native
    def timestamp(scheduler: Scheduler = ???): Observable[Timestamp[T]] = js.native
    def toArray(): Observable[js.Array[T]] = js.native
    def window[I](windowBoundaries: Observable[I]): Observable[Observable[T]] = js.native
    def windowCount(windowSize: Int, startWindowEvery: Int = ???): Observable[Observable[T]] = js.native
    def windowTime(windowTimeSpan: Int, windowCreationInterval: Int = ???, scheduler: Scheduler = ???): Observable[Observable[T]] = js.native
    def windowToggle[T2,O](openings: Observable[O], closingSelector:  js.Function1[O, Observable[T2]]): Observable[Observable[T]] = js.native
    def windowWhen[T2](closingSelector: js.Function0[Observable[T2]]): Observable[Observable[T]] = js.native

    def withLatestFrom[T2, R](v2: Observable[T2],project: js.Function2[T, T2, R] = ???): Observable[R] = js.native

    def zip[T2, R](v2: Observable[T2], project: js.Function2[T,T2,R] = ???): Observable[R] = js.native

    def zipAll[T2,R](project: (js.Function1[js.Array[T2],R]) = ???): Observable[R] = js.native

    
    def lift[R](operator: Operator[T, R]): Observable[R] = js.native


    def forEach(next: js.Function1[T, Unit], PromiseCtor: Promise.type = js.native): Promise[Unit] = js.native

  }

  @js.native
  @JSName("Rx.Observable")
  object Observable extends js.Object {
    def ajax[T2](request: String | js.Object): Observable[T2]  = js.native

    def bindCallback[T,T2](callbackFunc: js.Function, selector: js.Function, scheduler: Scheduler): js.Function1[T2, Observable[T]]  = js.native

    def bindNodeCallback[T,T2](callbackFunc: js.Function, selector: js.Function, scheduler: Scheduler): js.Function1[T2, Observable[T]]  = js.native

    import js.JSConverters._

    def combineLatest[T,R] (sources: Seq[Observable[T]],combineFunction: collection.mutable.Seq[T] ⇒ R = ???): Observable[R] = _combineLatest(sources.toJSArray)(combineFunction)

    @JSName("combineLatest")
    private def _combineLatest[T, R](sources: js.Array[Observable[T]])(combineFunction: collection.mutable.Seq[T] ⇒ R = ???): Observable[R] = js.native



    def concat[T, R](observables: Seq[Observable[T]], scheduler: Scheduler = ???): Observable[R] = _concat(observables.toJSArray,scheduler)
    @JSName("concat")
    private def _concat[T, R](observables: js.Array[Observable[T]], scheduler: Scheduler = ???): Observable[R] = js.native

    def concatMap[T,T2, I, R](project: js.Function2[T,Int, Observable[I]], resultSelector: js.Function4[T, I, Int, Int, R] = ???): T2 = js.native

    def interval(period: Int = 0, scheduler: Scheduler = ???): Observable[Int] = js.native

    def merge[T, R](observables: Seq[Observable[T]], scheduler: Scheduler = ???): Observable[T] = _merge(observables.toJSArray, scheduler)

    @JSName("merge")
    private def _merge[T, R](observables: js.Array[Observable[T]], scheduler: Scheduler = ???): Observable[R] = js.native

    def of[T](elements: T*): Observable[T] = js.native
    def race[T](observables: (Observable[T] | Array[Observable[T]])*): Observable[T] = js.native

    def range(start: Int = 0, count: Int = 0, scheduler: Scheduler = ???): Observable[Int] = js.native
    def timer(initialDelay: Int = 0, period: Int = 1000, scheduler: Scheduler = ???):  Observable[Int] = js.native


    def zip[T,R](observables: Seq[Observable[T]], project: js.Function1[js.Array[T], R] = ??? ): Observable[R] = _zip(observables.toJSArray, project)

    @JSName("zip")
    def _zip[T,R](observables: js.Array[Observable[T]], project: js.Function1[js.Array[T], R] = ??? ): Observable[R] = js.native


    var create: js.Function = js.native
  }

}
