
import scala.scalajs.js
import js._

package rxscalajs {

  @js.native
  trait Subscribable[T] extends js.Object {
    def subscribe(observer: Observer[T]): AnonymousSubscription = js.native
  }

  @js.native
  class Observable[T] protected() extends Subscribable[T] {
    def this(subscribe: js.Function = js.native) = this()

    var source: Observable[js.Any] = js.native
    var operator: Operator[js.Any, T] = js.native

    def audit(durationSelector:  T => Subscribable[js.Any]): Observable[T] = js.native
    def auditTime(delay: Int, scheduler: Scheduler = ???): Observable[T] = js.native
    def buffer(closingNotifier: Observable[js.Any]): Observable[js.Array[T]] = js.native
    def bufferCount(bufferSize: Int, startBufferEvery: Int = ???): Observable[js.Array[T]] = js.native
    def bufferTime(bufferTimeSpan: Int, bufferCreationInterval: Int = ???, scheduler: Scheduler = ???): Observable[js.Array[T]] = js.native
    def bufferToggle[T, O](openings: Subscribable[O], closingSelector:  O => Subscribable[js.Any]): Observable[js.Array[T]] = js.native
    def bufferWhen(closingSelector: () => Observable[js.Any]): Observable[js.Array[T]] = js.native
    def cache(bufferSize: Int = ???, windowTime: Int = ???, scheduler: Scheduler = ???): Observable[T] = js.native
    def `catch`[T, R](selector: (js.Any, Observable[T]) => Observable[R]): Observable[R] = js.native
    def combineAll[R](project:  (js.Any*) => R = ???): Observable[R] = js.native

    def combineLatest[T, R](observables: (Observable[js.Any] | js.Array[Observable[js.Any]] | (js.Any*) => R)*): Observable[R] = js.native

    def combineLatest[R](project: T => R): Observable[R] = js.native
    def combineLatest[T2, R](v2: Observable[T2], project:  (T , T2) => R): Observable[R] = js.native
    def combineLatest[T2, T3, R](v2: Observable[T2], v3: Observable[T3], project: ( T,  T2,  T3) => R): Observable[R] = js.native
    def combineLatest[T2, T3, T4, R](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], project: (T, T2,  T3, T4) => R): Observable[R] = js.native
    def combineLatest [T2, T3, T4, T5, R](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], project: ( T,  T2,  T3,  T4,  T5) => R): Observable[R] = js.native
    def combineLatest[T2, T3, T4, T5, T6, R](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], v6: Observable[T6], project: ( T,  T2, T3,  T4,  T5,  T6) => R): Observable[R] = js.native
    def combineLatest[T2](v2: Observable[T2]): Observable[js.Array[T | T2]] = js.native
    def combineLatest [T2, T3](v2: Observable[T2], v3: Observable[T3]): Observable[js.Array[T | T2 | T3]] = js.native
    def combineLatest [T2, T3, T4](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4]): Observable[js.Array[T | T2 | T3 | T4]] = js.native
    def combineLatest [T2, T3, T4, T5](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5]): Observable[js.Array[T | T2 | T3 | T4 | T5]] = js.native
    def combineLatest[T2, T3, T4, T5, T6](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], v6: Observable[T6]): Observable[js.Array[T | T2 | T3 | T4 | T5 | T6]] = js.native
    def combineLatest [R](observables: (Observable[js.Any] | ((js.Any*) => R))*): Observable[R] = js.native
    def combineLatest[R](array: js.Array[Observable[js.Any]]): Observable[R] = js.native
    def combineLatest [R](array: js.Array[Observable[js.Any]], project: (js.Any*) => R): Observable[R] = js.native



    def combineLatest(scheduler: Scheduler = ???): Observable[T] = js.native
    def combineLatest[T2](v2: Observable[T2], scheduler: Scheduler = ???): Observable[T | T2] = js.native
    def combineLatest [T2, T3](v2: Observable[T2], v3: Observable[T3], scheduler: Scheduler = ???): Observable[T | T2 | T3] = js.native
    def combineLatest [T2, T3, T4](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], scheduler: Scheduler = ???): Observable[T | T2 | T3 | T4] = js.native
    def combineLatest [T2, T3, T4, T5](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], scheduler: Scheduler = ???): Observable[T | T2 | T3 | T4 | T5] = js.native
    def combineLatest[T2, T3, T4, T5, T6](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], v6: Observable[T6], scheduler: Scheduler = ???): Observable[T | T2 | T3 | T4 | T5 | T6] = js.native
    def combineLatest(observables: (Observable[T] | Scheduler)*): Observable[T] = js.native
    def combineLatest[R](observables: (Observable[js.Any] | Scheduler)*): Observable[R] = js.native


    def concatAll(): T = js.native
    def concatMap[T, I, R](project: ( T,  Int) => Observable[I], resultSelector: (T,  I,  Int,  Int) => R = ???): js.Any = js.native

    def combineLatest[R](project: (T, Int) => Observable[R]): Observable[R] = js.native
    def combineLatest[I, R](project: ( T,  Int) => Observable[I], resultSelector: ( T, I, Int,  Int) => R): Observable[R] = js.native

    def concatMapTo[T, I, R](innerObservable: Observable[I], resultSelector: (T, I,  Int,  Int) => R = ???): Observable[R] = js.native

    def combineLatest[R](observable: Observable[R]): Observable[R] = js.native
    def combineLatest[I, R](observable: Observable[I], resultSelector: (T,  I,  Int,  Int) => R): Observable[R] = js.native

    def count(predicate: ( T,  Int,  Observable[T]) => Boolean  = ???): Observable[Int] = js.native

    def debounce(durationSelector:  T => Subscribable[Int]): Observable[T] = js.native
    def debounceTime(dueTime: Int, scheduler: Scheduler = ???): Observable[T] = js.native

    def defaultIfEmpty[T, R](defaultValue: R = ???): Observable[T | R] = js.native
    def defaultIfEmpty(defaultValue: T = ???): Observable[T] = js.native
    def delay(delay: Int | Date, scheduler: Scheduler = ???): Observable[T] = js.native
    def delayWhen(delayDurationSelector:  T => Observable[js.Any], subscriptionDelay: Observable[js.Any] = ???): Observable[T] = js.native
    def dematerialize(): Observable[js.Any] = js.native
    def distinct(compare: ( T,  T) => Boolean = ???, flushes: Observable[js.Any] = ???): Observable[T] = js.native
    def distinctKey(key: String, compare: ( T,  T) => Boolean = ???, flushes: Observable[js.Any] = ???): Observable[T] = js.native
    def distinctKey[T,K](key: String, compare: ( K,  K) => Boolean, flushes: Observable[js.Any] = ???): Observable[T] = js.native
    def distinctKey(key: String): Observable[T] = js.native
    def distinctUntilChanged[ K](compare: ( K,  K) => Boolean = ???, keySelector: ( T) => K = ???): Observable[T] = js.native
    def distinctUntilChanged[ K](compare: ( K,  K) => Boolean = ???, keySelector: ( T) => K = ???): Observable[T] = js.native
    def distinctUntilKeyChanged(key: String, compare: ( T,  T) => Boolean = ???): Observable[T] = js.native
    def `do`(nextOrObserver: (Observer[T] | ( T => Unit)) = ???, error: js.Any => Unit = ???, complete: () => Unit = ???): Observable[T] = js.native
    def elementAt(index: Int, defaultValue: T = ???): Observable[T] = js.native
    def every(predicate: ( T,  Int,  Observable[T]) => Boolean, thisArg: js.Any = ???): Observable[Boolean] = js.native
    def exhaust(): Observable[T] = js.native
    def exhaustMap[ I, R](project: ( T,  Int) => Observable[I], resultSelector: (T,  I,  Int,  Int) => R = ???): Observable[R] = js.native
    def expand[ R](project: ( T, Int) => Observable[R], concurrent: Int = ???, scheduler: Scheduler = ???): Observable[R] = js.native
    def filter(predicate: ( T,  Int) => Boolean, thisArg: js.Any = ???): Observable[T] = js.native
    def _finally(finallySelector: () => Unit): Observable[T] = js.native
    def find(predicate: ( T,  Int,  Observable[T]) => Boolean, thisArg: js.Any = ???): Observable[T] = js.native
    def findIndex(predicate: ( T,  Int,  Observable[T]) => Boolean, thisArg: js.Any = ???): Observable[Int] = js.native
    def first[ R](predicate: ( T,  Int,  Observable[T]) => Boolean = ???, resultSelector: ( T,  Int) => R = ???, defaultValue: R = ???): Observable[T | R] = js.native
    def groupBy[ K, R](keySelector: ( T) => K, elementSelector:  T => R = ???, durationSelector: ( GroupedObservable[K, R]) => Observable[js.Any] = ???): Observable[GroupedObservable[K, R]] = js.native
    def ignoreElements(): Observable[T] = js.native
    def isEmpty(): Observable[Boolean] = js.native
    def last[ R](predicate: ( T,  Int,  Observable[T]) => Boolean = ???, resultSelector: ( T,  Int) => R | Unit = ???, defaultValue: R = ???): Observable[T | R] = js.native
    def last[ R](predicate: ( T,  Int,  Observable[T]) => Boolean = ???, resultSelector: ( T,  Int) => R | Unit = ???, defaultValue: R = ???): Observable[T | R] = js.native
    def let[ R](func:  Observable[T] => Observable[R]): Observable[R] = js.native
    def map[ R](project: ( T,  Int) => R, thisArg: js.Any = ???): Observable[R] = js.native
    def mapTo[ R](value: R): Observable[R] = js.native
    def materialize(): Observable[Notification[T]] = js.native
    def max(comparer: ( T,  T) => T = ???): Observable[T] = js.native
    def merge[ R](observables: (Observable[js.Any] | Scheduler | Int)*): Observable[R] = js.native
    def merge(scheduler: Scheduler = ???): Observable[T] = js.native
    def merge(concurrent: Int = ???, scheduler: Scheduler = ???): Observable[T] = js.native
    def merge[T2](v2: Observable[T2], scheduler: Scheduler = ???): Observable[T | T2] = js.native
    def merge[T2](v2: Observable[T2], concurrent: Int = ???, scheduler: Scheduler = ???): Observable[T | T2] = js.native
    def merge[T2, T3](v2: Observable[T2], v3: Observable[T3], scheduler: Scheduler = ???): Observable[T | T2 | T3] = js.native
    def merge[T2, T3](v2: Observable[T2], v3: Observable[T3], concurrent: Int = ???, scheduler: Scheduler = ???): Observable[T | T2 | T3] = js.native
    def merge[T2, T3, T4](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], scheduler: Scheduler = ???): Observable[T | T2 | T3 | T4] = js.native
    def merge[T2, T3, T4](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], concurrent: Int = ???, scheduler: Scheduler = ???): Observable[T | T2 | T3 | T4] = js.native
    def merge[T2, T3, T4, T5](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], scheduler: Scheduler = ???): Observable[T | T2 | T3 | T4 | T5] = js.native
    def merge[T2, T3, T4, T5](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], concurrent: Int = ???, scheduler: Scheduler = ???): Observable[T | T2 | T3 | T4 | T5] = js.native
    def merge[T2, T3, T4, T5, T6](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], v6: Observable[T6], scheduler: Scheduler = ???): Observable[T | T2 | T3 | T4 | T5 | T6] = js.native
    def merge[T2, T3, T4, T5, T6](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], v6: Observable[T6], concurrent: Int = ???, scheduler: Scheduler = ???): Observable[T | T2 | T3 | T4 | T5 | T6] = js.native
    def merge(observables: (Observable[T] | Scheduler | Int)*): Observable[T] = js.native
    def merge[R](observables: (Observable[js.Any] | Scheduler | Int)*): Observable[R] = js.native
    def mergeAll(concurrent: Int = ???): T = js.native
    def mergeMap[ I, R](project: ( T,  Int) => Observable[I], resultSelector: (( T,  I,  Int,  Int) => R | Int) = ???, concurrent: Int = ???): Observable[R] = js.native
    def mergeMapTo[ I, R](innerObservable: Observable[I], resultSelector: (( T,  I,  Int,  Int) => R | Int) = ???, concurrent: Int = ???): Observable[R] = js.native
    def mergeScan[ R](project: ( R,  T) => Observable[R], seed: R, concurrent: Int = ???): Observable[R] = js.native
    def min(comparer: ( T,  T) => T = ???): Observable[T] = js.native
    def multicast(subjectOrSubjectFactory: Subject[T] | (() => Subject[T])): ConnectableObservable[T] = js.native

    def observeOn(scheduler: Scheduler, delay: Int = ???): Observable[T] = js.native

    def pairwise(): Observable[js.Array[T]] = js.native
    def partition(predicate: ( T) => Boolean, thisArg: js.Any = ???): js.Array[Observable[T]] = js.native
    def pluck[R](properties: String*): Observable[R] = js.native
    def publish(): ConnectableObservable[T] = js.native

    def publishBehavior(value: T): ConnectableObservable[T] = js.native

    def publishLast(): ConnectableObservable[T] = js.native
    def publishReplay(bufferSize: Int = ???, windowTime: Int = ???, scheduler: Scheduler = ???): ConnectableObservable[T] = js.native

    def race(observables: (Observable[T] | js.Array[Observable[T]])*): Observable[T] = js.native
    def reduce[ R](project: ( R,  T) => R, seed: R = ???): Observable[R] = js.native

    def repeat(count: Int = ???): Observable[T] = js.native

    def retry(count: Int = ???): Observable[T] = js.native
    def retryWhen(notifier: Observable[js.Any] => Observable[js.Any]): Observable[T] = js.native
    def sample(notifier: Observable[js.Any]): Observable[T] = js.native

    def sampleTime(delay: Int, scheduler: Scheduler = ???): Observable[T] = js.native
    def scan[ R](accumulator: ( R,  T) => R, seed: T | R = ???): Observable[R] = js.native
    def share(): Observable[T] = js.native
    def single(predicate: ( T,  Int,  Observable[T]) => Boolean = ???): Observable[T] = js.native
    def skip(total: Int): Observable[T] = js.native
    def skipUntil(notifier: Observable[js.Any]): Observable[T] = js.native
    def skipWhile(predicate: ( T,  Int) => Boolean): Observable[T] = js.native
    def startWith(array: (T | Scheduler)*): Observable[T] = js.native
    def subscribeOn(scheduler: Scheduler, delay: Int = ???): Observable[T] = js.native
    def switch(): T = js.native
    def switchMap[ I, R](project: ( T,  Int) => Observable[I], resultSelector: ( T,  I,  Int,  Int) => R = ???): Observable[R] = js.native
    def switchMapTo[ I, R](innerObservable: Observable[I], resultSelector: ( T,  I,  Int,  Int) => R = ???): Observable[R] = js.native
    def take(total: Int): Observable[T] = js.native
    def takeLast(total: Int): Observable[T] = js.native
    def takeUntil(notifier: Observable[js.Any]): Observable[T] = js.native
    def takeWhile(predicate: ( T, Int) => Boolean): Observable[T] = js.native
    def throttle(durationSelector:  T => Subscribable[Int]): Observable[T] = js.native
    def throttleTime(delay: Int, scheduler: Scheduler = ???): Observable[T] = js.native
    def timeInterval(scheduler: Scheduler = ???): Observable[TimeInterval[T]] = js.native
    def timeout(due: Int | Date, errorToSend: js.Any = ???, scheduler: Scheduler = ???): Observable[T] = js.native
    def timeoutWith[ R](due: Int | Date, withObservable: Observable[R], scheduler: Scheduler = ???): Observable[T | R] = js.native
    def timestamp(scheduler: Scheduler = ???): Observable[Timestamp[T]] = js.native
    def toArray(): Observable[js.Array[T]] = js.native
    def window(windowBoundaries: Observable[js.Any]): Observable[Observable[T]] = js.native
    def windowCount(windowSize: Int, startWindowEvery: Int = ???): Observable[Observable[T]] = js.native
    def windowTime(windowTimeSpan: Int, windowCreationInterval: Int = ???, scheduler: Scheduler = ???): Observable[Observable[T]] = js.native
    def windowToggle[ O](openings: Observable[O], closingSelector:  O => Observable[js.Any]): Observable[Observable[T]] = js.native
    def windowWhen(closingSelector: () => Observable[js.Any]): Observable[Observable[T]] = js.native
    def withLatestFrom[ R](args: (Observable[js.Any] | (js.Any*) => R)*): Observable[R] = js.native
    def withLatestFrom[R](project: T => R): Observable[R] = js.native
    def withLatestFrom[T2, R](v2: Observable[T2], project: ( T,  T2) => R): Observable[R] = js.native
    def withLatestFrom[T2, T3, R](v2: Observable[T2], v3: Observable[T3], project: ( T,  T2,  T3) => R): Observable[R] = js.native
    def withLatestFrom[T2, T3, T4, R](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], project: ( T, T2, T3,  T4) => R): Observable[R] = js.native
    def withLatestFrom[T2, T3, T4, T5, R](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], project: ( T,  T2,  T3,  T4,  T5) => R): Observable[R] = js.native
    def withLatestFrom[T2, T3, T4, T5, T6, R](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], v6: Observable[T6], project: ( T,  T2,  T3,  T4,  T5,  T6) => R): Observable[R] = js.native
    def withLatestFrom[T2](v2: Observable[T2]): Observable[js.Array[T | T2]] = js.native
    def withLatestFrom[T2, T3](v2: Observable[T2], v3: Observable[T3]): Observable[js.Array[T| T2| T3]] = js.native
    def withLatestFrom[T2, T3, T4](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4]): Observable[js.Array[T| T2| T3| T4]] = js.native
    def withLatestFrom[T2, T3, T4, T5](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5]): Observable[js.Array[T| T2| T3| T4| T5]] = js.native
    def withLatestFrom[T2, T3, T4, T5, T6](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], v6: Observable[T6]): Observable[js.Array[T| T2| T3|T4| T5| T6]] = js.native
    def withLatestFrom[R](array: js.Array[Observable[js.Any]]): Observable[R] = js.native
    def withLatestFrom[R](array: js.Array[Observable[js.Any]], project: (( js.Any*) => R)): Observable[R] = js.native

    def zip[R](observables: Array[Observable[js.Any] | (( js.Any*) => R)]): Observable[R] = js.native
    def zip[ R](project:  T => R): Observable[R] = js.native
    def zip[ T2, R](v2: Observable[T2], project:  (T,  T2) => R): Observable[R] = js.native
    def zip[ T2, T3, R](v2: Observable[T2], v3: Observable[T3], project: ( T, T2, T3) => R): Observable[R] = js.native
    def zip[ T2, T3, T4, R](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], project: ( T,  T2,  T3,  T4) => R): Observable[R] = js.native
    def zip[ T2, T3, T4, T5, R](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], project: ( T,  T2,  T3,  T4,  T5) => R): Observable[R] = js.native
    def zip[ T2, T3, T4, T5, T6, R](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], v6: Observable[T6], project: ( T,  T2,  T3,  T4,  T5,  T6) => R): Observable[R] = js.native
    def zip[ T2](v2: Observable[T2]): Observable[js.Array[T | T2]] = js.native
    def zip[ T2, T3](v2: Observable[T2], v3: Observable[T3]): Observable[js.Array[T | T2 | T3]] = js.native
    def zip[ T2, T3, T4](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4]): Observable[js.Array[T | T2 | T3 | T4]] = js.native
    def zip[ T2, T3, T4, T5](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5]): Observable[js.Array[T | T2 | T3 | T4 | T5]] = js.native
    def zip[ T2, T3, T4, T5, T6](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], v6: Observable[T6]): Observable[js.Array[T| T2| T3| T4| T5| T6]] = js.native
    def zip[ R](observables: (Observable[T] | (( T*) => R))*): Observable[R] = js.native
    def zip[ R](array: js.Array[Observable[js.Any]]): Observable[R] = js.native
    def zip[ R](array: js.Array[Observable[js.Any]], project: (( js.Any*) => R)): Observable[R] = js.native
    def zipAll[ R](project: (( js.Any*) => R) = ???): Observable[R] = js.native

    
    def lift[R](operator: Operator[T, R]): Observable[R] = js.native

    def subscribe(observerOrNext: PartialObserver[T] | js.Function1[T, Unit] = js.native, error: js.Function1[js.Any, Unit] = js.native, complete: js.Function0[Unit] = js.native): Subscription = js.native

    def forEach(next: js.Function1[T, Unit], PromiseCtor: Promise.type = js.native): Promise[Unit] = js.native
  }

  @js.native
  object Observable extends js.Object {
    def ajax(request: String | js.Object): Observable[js.Any]  = js.native

    def bindCallback[T](callbackFunc: js.Function, selector: js.Function, scheduler: Scheduler): js.Function1[js.Any, Observable[T]]  = js.native

    def bindNodeCallback[T](callbackFunc: js.Function, selector: js.Function, scheduler: Scheduler): js.Function1[js.Any, Observable[T]]  = js.native


    def combineLatest[T ,R] (project: ( T) => R): Observable[R] = js.native
    def combineLatest[T ,T2, R] (v2: Observable[T2], project: ( T,  T2) => R): Observable[R] = js.native
    def combineLatest[T ,T2, T3, R] (v2: Observable[T2], v3: Observable[T3], project: ( T,  T2,  T3) => R): Observable[R] = js.native
    def combineLatest[T ,T2, T3, T4, R] (v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], project: ( T,  T2,  T3, T4) => R): Observable[R] = js.native
    def combineLatest[T ,T2, T3, T4, T5, R] (v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], project: ( T,  T2,  T3,  T4,  T5) => R): Observable[R] = js.native
    def combineLatest[T ,T2, T3, T4, T5, T6, R] (v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], v6: Observable[T6], project: ( T,  T2,  T3,  T4,  T5,  T6) => R): Observable[R] = js.native
    def combineLatest[R] (observables: js.Array[Observable[js.Any]], project: js.Array[js.Any] => R ): Observable[R] = js.native
    def combineLatest[T](v1: Observable[T], scheduler: Scheduler = ???): Observable[js.Array[T]] = js.native
    def combineLatest[T, T2](v1: Observable[T], v2: Observable[T2], scheduler: Scheduler = ???): Observable[js.Array[T | T2]] = js.native
    def combineLatest[T, T2, T3](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], scheduler: Scheduler = ???): Observable[js.Array[T | T2 | T3]] = js.native
    def combineLatest[T, T2, T3, T4](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], scheduler: Scheduler = ???): Observable[js.Array[T | T2 | T3 | T4]] = js.native
    def combineLatest[T, T2, T3, T4, T5](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], scheduler: Scheduler = ???): Observable[js.Array[T | T2 | T3 | T4 | T5]] = js.native
    def combineLatest[T, T2, T3, T4, T5, T6](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], v6: Observable[T6], scheduler: Scheduler = ???): Observable[js.Array[T | T2 | T3 | T4 | T5 | T6]] = js.native


    def concat[T](v1: Observable[T], scheduler: Scheduler = ???): Observable[T] = js.native
    def concat[T, T2](v1: Observable[T], v2: Observable[T2], scheduler: Scheduler = ???): Observable[T | T2] = js.native
    def concat[T, T2, T3](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], scheduler: Scheduler = ???): Observable[T | T2 | T3] = js.native
    def concat[T, T2, T3, T4](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], scheduler: Scheduler = ???): Observable[T | T2 | T3 | T4] = js.native
    def concat[T, T2, T3, T4, T5](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], scheduler: Scheduler = ???): Observable[T | T2 | T3 | T4 | T5] = js.native
    def concat[T, T2, T3, T4, T5, T6](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], v6: Observable[T6], scheduler: Scheduler = ???): Observable[T | T2 | T3 | T4 | T5 | T6] = js.native
    def concat[T, R](observables: js.Array[Observable[T]]): Observable[R] = js.native

    def concatMap[T, I, R](project: ( T,  Int) => Observable[I], resultSelector: ( T,  I,  Int,  Int) => R = ???): js.Any = js.native

    def interval(period: Int = 1000, scheduler: Scheduler = ???): Observable[Int] = js.native

    def merge[T](v1: Observable[T], scheduler: Scheduler = ???): Observable[T] = js.native
    def merge[T](v1: Observable[T], concurrent: Int = ???, scheduler: Scheduler = ???): Observable[T] = js.native
    def merge[T, T2](v1: Observable[T], v2: Observable[T2], scheduler: Scheduler = ???): Observable[T | T2] = js.native
    def merge[T, T2](v1: Observable[T], v2: Observable[T2], concurrent: Int = ???, scheduler: Scheduler = ???): Observable[T | T2] = js.native
    def merge[T, T2, T3](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], scheduler: Scheduler = ???): Observable[T | T2 | T3] = js.native
    def merge[T, T2, T3](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], concurrent: Int = ???, scheduler: Scheduler = ???): Observable[T | T2 | T3] = js.native
    def merge[T, T2, T3, T4](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], scheduler: Scheduler = ???): Observable[T | T2 | T3 | T4] = js.native
    def merge[T, T2, T3, T4](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], concurrent: Int = ???, scheduler: Scheduler = ???): Observable[T | T2 | T3 | T4] = js.native
    def merge[T, T2, T3, T4, T5](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], scheduler: Scheduler = ???): Observable[T | T2 | T3 | T4 | T5] = js.native
    def merge[T, T2, T3, T4, T5](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], concurrent: Int = ???, scheduler: Scheduler = ???): Observable[T | T2 | T3 | T4 | T5] = js.native
    def merge[T, T2, T3, T4, T5, T6](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], v6: Observable[T6], scheduler: Scheduler = ???): Observable[T | T2 | T3 | T4 | T5 | T6] = js.native
    def merge[T, T2, T3, T4, T5, T6](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], v6: Observable[T6], concurrent: Int = ???, scheduler: Scheduler = ???): Observable[T | T2 | T3 | T4 | T5 | T6] = js.native
    def merge[T, R](observables: js.Array[Observable[T]]: Observable[R] = js.native

    def of[T](elements: T*): Observable[T] = js.native
    def race[T](observables: (Observable[T] | Array[Observable[T]])*): Observable[T] = js.native

    def range(start: Int = 0, count: Int = 0, scheduler: Scheduler = ???): Observable[Int] = js.native
    def timer(initialDelay: Int = 0, period: Int = 1000, scheduler: Scheduler = ???):  Observable[Int] = js.native

    def zip[T](v1: Observable[T]): Observable[js.Array[T]] = js.native
    def zip[T, T2](v1: Observable[T], v2: Observable[T2]): Observable[js.Array[T | T2]] = js.native
    def zip[T, T2, T3](v1: Observable[T], v2: Observable[T2], v3: Observable[T3]): Observable[js.Array[T | T2 | T3]] = js.native
    def zip[T, T2, T3, T4](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4]): Observable[js.Array[T | T2 | T3 | T4]] = js.native
    def zip[T, T2, T3, T4, T5](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5]): Observable[js.Array[T | T2 | T3 | T4 | T5]] = js.native
    def zip[T, T2, T3, T4, T5, T6](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], v6: Observable[T6]): Observable[js.Array[T | T2 | T3 | T4 | T5 | T6]] = js.native
    def zip[T, R](v1: Observable[T], project: ( T) => R): Observable[R] = js.native
    def zip[T, T2, R](v1: Observable[T], v2: Observable[T2], project: ( T,  T2) => R): Observable[R] = js.native
    def zip[T, T2, T3, R](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], project: ( T,  T2,  T3) => R): Observable[R] = js.native
    def zip[T, T2, T3, T4, R](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], project: ( T,  T2,  T3,  T4) => R): Observable[R] = js.native
    def zip[T, T2, T3, T4, T5, R](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], project: ( T,  T2,  T3,  T4,  T5) => R): Observable[R] = js.native
    def zip[T, T2, T3, T4, T5, T6, R](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], v6: Observable[T6], project: ( T,  T2,  T3,  T4,  T5,  T6) => R): Observable[R] = js.native
    def zip[R] (observables: js.Array[Observable[js.Any]], project:  js.Array[Array[js.Any] => R] ): Observable[R] = js.native


    var create: js.Function = js.native
    var `if`: IfObservable.create.type = js.native
    var `throw`: ErrorObservable.create.type = js.native
  }

}
