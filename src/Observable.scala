
import scala.scalajs.js
import js.annotation._
import js.|

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

    def audit[T](durationSelector: (value: T) => SubscribableOrPromise[js.Any]): Observable[T] = js.native
    def auditTime[T](delay: Int, scheduler: Scheduler = js.undefined): Observable[T] = js.native
    def buffer[T](closingNotifier: Observable[js.Any]): Observable[T[]] = js.native
    def bufferCount[T](bufferSize: Int, startBufferEvery: Int = ???): Observable[T[]] = js.native
    def bufferTime[T](bufferTimeSpan: Int, bufferCreationInterval: Int = ???, scheduler: Scheduler = js.undefined): Observable[T[]] = js.native
    def bufferToggle[T, O](openings: SubscribableOrPromise[O], closingSelector: (value: O) => SubscribableOrPromise[js.Any]): Observable[T[]] = js.native
    def bufferWhen[T](closingSelector: () => Observable[js.Any]): Observable[T[]] = js.native
    def cache[T](bufferSize: Int = ???, windowTime: Int = ???, scheduler: Scheduler = js.undefined): Observable[T] = js.native
    def `catch`[T, R](selector: (err: js.Any, caught: Observable[T]) => Observable[R]): Observable[R] = js.native
    def combineAll[R](project: (values: js.Any*) => R = ???): Observable[R] = js.native

    def combineLatest[T, R](observables: (Observable[js.Any] | js.Array[Observable[js.Any]] | ((values: js.Any*) => R))*): Observable[R] = js.native

    def combineLatest[R](project: (v1: T) => R): Observable[R] = js.native
    def combineLatest[T2, R](v2: Observable[T2], project: (v1: T, v2: T2) => R): Observable[R] = js.native
    def combineLatest[T2, T3, R](v2: Observable[T2], v3: Observable[T3], project: (v1: T, v2: T2, v3: T3) => R): Observable[R] = js.native
    def combineLatest[T2, T3, T4, R](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], project: (v1: T, v2: T2, v3: T3, v4: T4) => R): Observable[R] = js.native
    def combineLatest [T2, T3, T4, T5, R](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], project: (v1: T, v2: T2, v3: T3, v4: T4, v5: T5) => R): Observable[R] = js.native
    def combineLatest[T2, T3, T4, T5, T6, R](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], v6: Observable[T6], project: (v1: T, v2: T2, v3: T3, v4: T4, v5: T5, v6: T6) => R): Observable[R] = js.native
    def combineLatest[T2](v2: Observable[T2]): Observable[[T, T2]] = js.native
    def combineLatest [T2, T3](v2: Observable[T2], v3: Observable[T3]): Observable[[T, T2, T3]] = js.native
    def combineLatest [T2, T3, T4](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4]): Observable[[T, T2, T3, T4]] = js.native
    def combineLatest [T2, T3, T4, T5](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5]): Observable[[T, T2, T3, T4, T5]] = js.native
    def combineLatest[T2, T3, T4, T5, T6](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], v6: Observable[T6]): Observable[[T, T2, T3, T4, T5, T6]] = js.native
    def combineLatest [R](observables: (Observable[js.Any] | ((values: js.Any*) => R))*): Observable[R] = js.native
    def combineLatest[R](array: js.Array[Observable[js.Any]]): Observable[R] = js.native
    def combineLatest [R](array: js.Array[Observable[js.Any]], project: ((values: js.Any*) => R): Observable[R] = js.native



    def combineLatest(scheduler: Scheduler = js.undefined): Observable[T] = js.native
    def combineLatest[T2](v2: Observable[T2], scheduler: Scheduler = js.undefined): Observable[T | T2] = js.native
    def combineLatest [T2, T3](v2: Observable[T2], v3: Observable[T3], scheduler: Scheduler = js.undefined): Observable[T | T2 | T3] = js.native
    def combineLatest [T2, T3, T4](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], scheduler: Scheduler = js.undefined): Observable[T | T2 | T3 | T4] = js.native
    def combineLatest [T2, T3, T4, T5](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], scheduler: Scheduler = js.undefined): Observable[T | T2 | T3 | T4 | T5] = js.native
    def combineLatest[T2, T3, T4, T5, T6](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], v6: Observable[T6], scheduler: Scheduler = js.undefined): Observable[T | T2 | T3 | T4 | T5 | T6] = js.native
    def combineLatest(observables: (Observable[T] | Scheduler)*): Observable[T] = js.native
    def combineLatest[R](observables: (Observable[js.Any] | Scheduler)*): Observable[R] = js.native


    def concatAll[T](): T = js.native
    def concatMap[T, I, R](project: (value: T, index: Int) => Observable[I], resultSelector: (outerValue: T, innerValue: I, outerIndex: Int, innerIndex: Int = ???) => R): js.Any = js.native

    def combineLatest[R](project: (value: T, index: Int) => Observable[R]): Observable[R] = js.native
    def combineLatest[I, R](project: (value: T, index: Int) => Observable[I], resultSelector: (outerValue: T, innerValue: I, outerIndex: Int, innerIndex: Int) => R): Observable[R] = js.native

    def concatMapTo[T, I, R](innerObservable: Observable[I], resultSelector: (outerValue: T, innerValue: I, outerIndex: Int, innerIndex: Int) => R = ???): Observable[R] = js.native

    def combineLatest[R](observable: Observable[R]): Observable[R] = js.native
    def combineLatest[I, R](observable: Observable[I], resultSelector: (outerValue: T, innerValue: I, outerIndex: Int, innerIndex: Int) => R): Observable[R] = js.native

    def count[T](predicate: (value: T, index: Int, source: Observable[T]) => Boolean  = ???): Observable[Int] = js.native

    def debounce[T](durationSelector: (value: T) => SubscribableOrPromise[Int]): Observable[T] = js.native
    def debounceTime[T](dueTime: Int, scheduler: Scheduler = js.undefined): Observable[T] = js.native

    def defaultIfEmpty[T, R](defaultValue: R = ???): Observable[T | R] = js.native
    def defaultIfEmpty[T](defaultValue: T = ???): Observable[T] = js.native
    def delay[T](delay: Int | Date, scheduler: Scheduler = js.undefined): Observable[T] = js.native
    def delayWhen[T](delayDurationSelector: (value: T) => Observable[js.Any], subscriptionDelay: Observable[js.Any] = ???): Observable[T] = js.native
    def dematerialize[T](): Observable[js.Any] = js.native
    def distinct[T](compare: (x: T, y: T) => Boolean = ???, flushes: Observable[js.Any] = ???): Observable[T] = js.native
    def distinctKey[T](key: String, compare: (x: T, y: T) => Boolean = ???, flushes: Observable[js.Any] = ???): Observable[T] = js.native
    def distinctKey[T,K](key: String, compare: (x: K, y: K) => Boolean, flushes: Observable[js.Any] = ???): Observable[T] = js.native
    def distinctKey[T](key: String): Observable[T] = js.native
    def distinctUntilChanged[T, K](compare: (x: K, y: K) => Boolean = ???, keySelector: (x: T) => K = ???): Observable[T] = js.native
    def distinctUntilChanged[T, K](compare: (x: K, y: K) => Boolean = ???, keySelector: (x: T) => K = ???): Observable[T] = js.native
    def distinctUntilKeyChanged[T](key: String, compare: (x: T, y: T) => Boolean = ???): Observable[T] = js.native
    def `do`[T](nextOrObserver: (PartialObserver[T] | ((x: T) => Unit)) = ???, error: (e: js.Any) => Unit = ???, complete: () => Unit = ???): Observable[T] = js.native
    def elementAt[T](index: Int, defaultValue: T = ???): Observable[T] = js.native
    def every[T](predicate: (value: T, index: Int, source: Observable[T]) => Boolean, thisArg: js.Any = ???): Observable[Boolean] = js.native
    def exhaust[T](): Observable[T] = js.native
    def exhaustMap[T, I, R](project: (value: T, index: Int) => Observable[I], resultSelector: (outerValue: T, innerValue: I, outerIndex: Int, innerIndex: Int) => R = ???): Observable[R] = js.native
    def expand[T, R](project: (value: T, index: Int) => Observable[R], concurrent: Int = ???, scheduler: Scheduler = js.undefined): Observable[R] = js.native
    def filter[T](predicate: (value: T, index: Int) => Boolean, thisArg: js.Any = ???): Observable[T] = js.native
    def _finally[T](finallySelector: () => Unit): Observable[T] = js.native
    def find[T](predicate: (value: T, index: Int, source: Observable[T]) => Boolean, thisArg: js.Any = ???): Observable[T] = js.native
    def findIndex[T](predicate: (value: T, index: Int, source: Observable[T]) => Boolean, thisArg: js.Any = ???): Observable[Int] = js.native
    def first[T, R](predicate: (value: T, index: Int, source: Observable[T]) => Boolean = ???, resultSelector: (value: T, index: Int) => R = ???, defaultValue: R = ???): Observable[T | R] = js.native
    def groupBy[T, K, R](keySelector: (value: T) => K, elementSelector: (value: T) => R = ???, durationSelector: (grouped: GroupedObservable[K, R]) => Observable[js.Any] = ???): Observable[GroupedObservable[K, R]] = js.native
    def ignoreElements[T](): Observable[T] = js.native
    def isEmpty(): Observable[Boolean] = js.native
    def last[T, R](predicate: (value: T, index: Int, source: Observable[T]) => Boolean = ???, resultSelector: (value: T, index: Int) => R | Unit = ???, defaultValue: R = ???): Observable[T | R] = js.native
    def last[T, R](predicate: (value: T, index: Int, source: Observable[T]) => Boolean = ???, resultSelector: (value: T, index: Int) => R | Unit = ???, defaultValue: R = ???): Observable[T | R] = js.native
    def let[T, R](func: (selector: Observable[T]) => Observable[R]): Observable[R] = js.native
    def map[T, R](project: (value: T, index: Int) => R, thisArg: js.Any = ???): Observable[R] = js.native
    def mapTo[T, R](value: R): Observable[R] = js.native
    def materialize[T](): Observable[Notification[T]] = js.native
    def max[T](comparer: (x: T, y: T) => T = ???): Observable[T] = js.native
    def merge[T, R](observables: (Observable[js.Any] | Scheduler | Int)*): Observable[R] = js.native
    def merge[T](scheduler: Scheduler = js.undefined): Observable[T] = js.native
    def merge[T](concurrent: Int = ???, scheduler: Scheduler = js.undefined): Observable[T] = js.native
    def merge[T,T2](v2: Observable[T2], scheduler: Scheduler = js.undefined): Observable[T | T2] = js.native
    def merge[T,T2](v2: Observable[T2], concurrent: Int = ???, scheduler: Scheduler = js.undefined): Observable[T | T2] = js.native
    def merge[T,T2, T3](v2: Observable[T2], v3: Observable[T3], scheduler: Scheduler = js.undefined): Observable[T | T2 | T3] = js.native
    def merge[T,T2, T3](v2: Observable[T2], v3: Observable[T3], concurrent: Int = ???, scheduler: Scheduler = js.undefined): Observable[T | T2 | T3] = js.native
    def merge[T,T2, T3, T4](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], scheduler: Scheduler = js.undefined): Observable[T | T2 | T3 | T4] = js.native
    def merge[T,T2, T3, T4](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], concurrent: Int = ???, scheduler: Scheduler = js.undefined): Observable[T | T2 | T3 | T4] = js.native
    def merge[T,T2, T3, T4, T5](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], scheduler: Scheduler = js.undefined): Observable[T | T2 | T3 | T4 | T5] = js.native
    def merge[T,T2, T3, T4, T5](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], concurrent: Int = ???, scheduler: Scheduler = js.undefined): Observable[T | T2 | T3 | T4 | T5] = js.native
    def merge[T,T2, T3, T4, T5, T6](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], v6: Observable[T6], scheduler: Scheduler = js.undefined): Observable[T | T2 | T3 | T4 | T5 | T6] = js.native
    def merge[T,T2, T3, T4, T5, T6](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], v6: Observable[T6], concurrent: Int = ???, scheduler: Scheduler = js.undefined): Observable[T | T2 | T3 | T4 | T5 | T6] = js.native
    def merge[T](observables: (Observable[T] | Scheduler | Int)*): Observable[T] = js.native
    def merge[T,R](observables: (Observable[js.Any] | Scheduler | Int)*): Observable[R] = js.native
    def mergeAll[T](concurrent: Int = ???): T = js.native
    def mergeMap[T, I, R](project: (value: T, index: Int) => Observable[I], resultSelector: ((outerValue: T, innerValue: I, outerIndex: Int, innerIndex: Int) => R = ???) | Int, concurrent: Int = ???): Observable[R] = js.native
    def mergeMapTo[T, I, R](innerObservable: Observable[I], resultSelector: ((outerValue: T, innerValue: I, outerIndex: Int, innerIndex: Int) => R = ???) | Int, concurrent: Int = ???): Observable[R] = js.native
    def mergeScan[T, R](project: (acc: R, value: T) => Observable[R], seed: R, concurrent: Int = ???): Observable[R] = js.native
    def min[T](comparer: (x: T, y: T) => T = ???): Observable[T] = js.native
    def multicast[T](subjectOrSubjectFactory: Subject[T] | (() => Subject[T])): ConnectableObservable[T] = js.native

    def observeOn[T](scheduler: Scheduler, delay: Int = ???): Observable[T] = js.native

    def pairwise[T](): Observable[[T, T]] = js.native
    def partition[T](predicate: (value: T) => Boolean, thisArg: js.Any = ???): [Observable[T], Observable[T]] = js.native
    def pluck[R](properties: String*): Observable[R] = js.native
    def publish[T](): ConnectableObservable[T] = js.native

    def publishBehavior[T](value: T): ConnectableObservable[T] = js.native

    def publishLast[T](): ConnectableObservable[T] = js.native
    def publishReplay[T](bufferSize: Int = ???, windowTime: Int = ???, scheduler: Scheduler = js.undefined): ConnectableObservable[T] = js.native

    def race[T](observables: (Observable[T] | js.Array[Observable[T]])*): Observable[T] = js.native
    def reduce[T, R](project: (acc: R, value: T) => R, seed: R = ???): Observable[R] = js.native

    def repeat[T](count: Int = ???): Observable[T] = js.native

    def retry[T](count: Int = ???): Observable[T] = js.native
    def retryWhen[T](notifier: (errors: Observable[js.Any]) => Observable[js.Any]): Observable[T] = js.native
    def sample[T](notifier: Observable[js.Any]): Observable[T] = js.native

    def sampleTime[T](delay: Int, scheduler: Scheduler = js.undefined): Observable[T] = js.native
    def scan[T, R](accumulator: (acc: R, value: T) => R, seed: T | R = ???): Observable[R] = js.native
    def share[T](): Observable[T] = js.native
    def single[T](predicate: (value: T, index: Int, source: Observable[T]) => Boolean = ???): Observable[T] = js.native
    def skip[T](total: Int): Observable[T] = js.native
    def skipUntil[T](notifier: Observable[js.Any]): Observable[T] = js.native
    def skipWhile[T](predicate: (value: T, index: Int) => Boolean): Observable[T] = js.native
    def startWith[T](array: (T | Scheduler)*): Observable[T] = js.native
    def subscribeOn[T](scheduler: Scheduler, delay: Int = ???): Observable[T] = js.native
    def switch[T](): T = js.native
    def switchMap[T, I, R](project: (value: T, index: Int) => Observable[I], resultSelector: (outerValue: T, innerValue: I, outerIndex: Int, innerIndex: Int) => R = ???): Observable[R] = js.native
    def switchMapTo[T, I, R](innerObservable: Observable[I], resultSelector: (outerValue: T, innerValue: I, outerIndex: Int, innerIndex: Int) => R = ???): Observable[R] = js.native
    def take[T](total: Int): Observable[T] = js.native
    def takeLast[T](total: Int): Observable[T] = js.native
    def takeUntil[T](notifier: Observable[js.Any]): Observable[T] = js.native
    def takeWhile[T](predicate: (value: T, index: Int) => Boolean): Observable[T] = js.native
    def throttle[T](durationSelector: (value: T) => SubscribableOrPromise[Int]): Observable[T] = js.native
    def throttleTime[T](delay: Int, scheduler: Scheduler = js.undefined): Observable[T] = js.native
    def timeInterval[T](scheduler: Scheduler = js.undefined): Observable[TimeInterval[T]] = js.native
    def timeout[T](due: Int | Date, errorToSend: js.Any = ???, scheduler: Scheduler = js.undefined): Observable[T] = js.native
    def timeoutWith[T, R](due: Int | Date, withObservable: Observable[R], scheduler: Scheduler = js.undefined): Observable[T | R] = js.native
    def timestamp[T](scheduler: Scheduler = js.undefined): Observable[Timestamp[T]] = js.native
    def toArray[T](): Observable[js.Array[T]] = js.native
    def window[T](windowBoundaries: Observable[js.Any]): Observable[Observable[T]] = js.native
    def windowCount[T](windowSize: Int, startWindowEvery: Int = ???): Observable[Observable[T]] = js.native
    def windowTime[T](windowTimeSpan: Int, windowCreationInterval: Int = ???, scheduler: Scheduler = js.undefined): Observable[Observable[T]] = js.native
    def windowToggle[T, O](openings: Observable[O], closingSelector: (openValue: O) => Observable[js.Any]): Observable[Observable[T]] = js.native
    def windowWhen[T](closingSelector: () => Observable[js.Any]): Observable[Observable[T]] = js.native
    def withLatestFrom[T, R](args: (Observable[js.Any] | ((values: js.Any*) => R))*): Observable[R] = js.native
    def withLatestFrom[T,R](project: (v1: T) => R): Observable[R] = js.native
    def withLatestFrom[T,T2, R](v2: Observable[T2], project: (v1: T, v2: T2) => R): Observable[R] = js.native
    def withLatestFrom[T,T2, T3, R](v2: Observable[T2], v3: Observable[T3], project: (v1: T, v2: T2, v3: T3) => R): Observable[R] = js.native
    def withLatestFrom[T,T2, T3, T4, R](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], project: (v1: T, v2: T2, v3: T3, v4: T4) => R): Observable[R] = js.native
    def withLatestFrom[T,T2, T3, T4, T5, R](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], project: (v1: T, v2: T2, v3: T3, v4: T4, v5: T5) => R): Observable[R] = js.native
    def withLatestFrom[T,T2, T3, T4, T5, T6, R](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], v6: Observable[T6], project: (v1: T, v2: T2, v3: T3, v4: T4, v5: T5, v6: T6) => R): Observable[R] = js.native
    def withLatestFrom[T,T2](v2: Observable[T2]): Observable[[T, T2]] = js.native
    def withLatestFrom[T,T2, T3](v2: Observable[T2], v3: Observable[T3]): Observable[[T, T2, T3]] = js.native
    def withLatestFrom[T,T2, T3, T4](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4]): Observable[[T, T2, T3, T4]] = js.native
    def withLatestFrom[T,T2, T3, T4, T5](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5]): Observable[[T, T2, T3, T4, T5]] = js.native
    def withLatestFrom[T,T2, T3, T4, T5, T6](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], v6: Observable[T6]): Observable[[T, T2, T3, T4, T5, T6]] = js.native
    def withLatestFrom[T,R](array: js.Array[Observable[js.Any]]): Observable[R] = js.native
    def withLatestFrom[T,R](array: js.Array[Observable[js.Any]], project: ((values: js.Any*) => R)): Observable[R] = js.native

    def zip[R](observables: Array[Observable[js.Any] | ((values: js.Any*) => R)]): Observable[R] = js.native
    def zip[T, R](project: (v1: T) => R): Observable[R] = js.native
    def zip[T, T2, R](v2: Observable[T2], project: (v1: T, v2: T2) => R): Observable[R] = js.native
    def zip[T, T2, T3, R](v2: Observable[T2], v3: Observable[T3], project: (v1: T, v2: T2, v3: T3) => R): Observable[R] = js.native
    def zip[T, T2, T3, T4, R](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], project: (v1: T, v2: T2, v3: T3, v4: T4) => R): Observable[R] = js.native
    def zip[T, T2, T3, T4, T5, R](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], project: (v1: T, v2: T2, v3: T3, v4: T4, v5: T5) => R): Observable[R] = js.native
    def zip[T, T2, T3, T4, T5, T6, R](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], v6: Observable[T6], project: (v1: T, v2: T2, v3: T3, v4: T4, v5: T5, v6: T6) => R): Observable[R] = js.native
    def zip[T, T2](v2: Observable[T2]): Observable[[T, T2]] = js.native
    def zip[T, T2, T3](v2: Observable[T2], v3: Observable[T3]): Observable[[T, T2, T3]] = js.native
    def zip[T, T2, T3, T4](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4]): Observable[[T, T2, T3, T4]] = js.native
    def zip[T, T2, T3, T4, T5](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5]): Observable[[T, T2, T3, T4, T5]] = js.native
    def zip[T, T2, T3, T4, T5, T6](v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], v6: Observable[T6]): Observable[[T, T2, T3, T4, T5, T6]] = js.native
    def zip[T, R](observables: (Observable[T] | ((values: T*) => R))*): Observable[R] = js.native
    def zip[T, R](array: js.Array[Observable[js.Any]]): Observable[R] = js.native
    def zip[T, R](array: js.Array[Observable[js.Any]], project: ((values: js.Any*) => R)): Observable[R] = js.native
    def zipAll[T, R](project: ((values: js.Any*) => R) = js.undefined): Observable[R] = js.native

    
    def lift[R](operator: Operator[T, R]): Observable[R] = js.native

    def subscribe(observerOrNext: PartialObserver[T] | js.Function1[T, Unit] = js.native, error: js.Function1[js.Any, Unit] = js.native, complete: js.Function0[Unit] = js.native): Subscription = js.native

    def forEach(next: js.Function1[T, Unit], PromiseCtor: Promise.type = js.native): Promise[Unit] = js.native
  }

  @js.native
  object Observable extends js.Object {
    def ajax(request: String | js.Object): Observable[js.Any]  = js.native

    def bindCallback[T](callbackFunc: js.Function, selector: js.Function, scheduler: Scheduler): js.Function1[js.Any, Observable[T]]  = js.native

    def bindNodeCallback[T](callbackFunc: js.Function, selector: js.Function, scheduler: Scheduler): js.Function1[js.Any, Observable[T]]  = js.native


    def combineLatest[T ,R] (project: (v1: T) => R): Observable[R] = js.native
    def combineLatest[T ,T2, R] (v2: Observable[T2], project: (v1: T, v2: T2) => R): Observable[R] = js.native
    def combineLatest[T ,T2, T3, R] (v2: Observable[T2], v3: Observable[T3], project: (v1: T, v2: T2, v3: T3) => R): Observable[R] = js.native
    def combineLatest[T ,T2, T3, T4, R] (v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], project: (v1: T, v2: T2, v3: T3, v4: T4) => R): Observable[R] = js.native
    def combineLatest[T ,T2, T3, T4, T5, R] (v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], project: (v1: T, v2: T2, v3: T3, v4: T4, v5: T5) => R): Observable[R] = js.native
    def combineLatest[T ,T2, T3, T4, T5, T6, R] (v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], v6: Observable[T6], project: (v1: T, v2: T2, v3: T3, v4: T4, v5: T5, v6: T6) => R): Observable[R] = js.native
    def combineLatest[R] (observables: js.Array[Observable[js.Any], project:  js.Array[Array[js.Any] => R] ): Observable[R] = js.native
    def combineLatest[T](v1: Observable[T], scheduler: Scheduler = js.undefined): Observable[js.Array[T]] = js.native
    def combineLatest[T, T2](v1: Observable[T], v2: Observable[T2], scheduler: Scheduler = js.undefined): Observable[js.Array[T | T2]] = js.native
    def combineLatest[T, T2, T3](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], scheduler: Scheduler = js.undefined): Observable[js.Array[T | T2 | T3]] = js.native
    def combineLatest[T, T2, T3, T4](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], scheduler: Scheduler = js.undefined): Observable[js.Array[T | T2 | T3 | T4]] = js.native
    def combineLatest[T, T2, T3, T4, T5](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], scheduler: Scheduler = js.undefined): Observable[js.Array[T | T2 | T3 | T4 | T5]] = js.native
    def combineLatest[T, T2, T3, T4, T5, T6](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], v6: Observable[T6], scheduler: Scheduler = js.undefined): Observable[js.Array[T | T2 | T3 | T4 | T5 | T6]] = js.native


    def concat[T](v1: Observable[T], scheduler: Scheduler = js.undefined): Observable[T] = js.native
    def concat[T, T2](v1: Observable[T], v2: Observable[T2], scheduler: Scheduler = js.undefined): Observable[T | T2] = js.native
    def concat[T, T2, T3](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], scheduler: Scheduler = js.undefined): Observable[T | T2 | T3] = js.native
    def concat[T, T2, T3, T4](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], scheduler: Scheduler = js.undefined): Observable[T | T2 | T3 | T4] = js.native
    def concat[T, T2, T3, T4, T5](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], scheduler: Scheduler = js.undefined): Observable[T | T2 | T3 | T4 | T5] = js.native
    def concat[T, T2, T3, T4, T5, T6](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], v6: Observable[T6], scheduler: Scheduler = js.undefined): Observable[T | T2 | T3 | T4 | T5 | T6] = js.native
    def concat[T, R](observables: js.Array[Observable[T]]: Observable[R] = js.native

    def concatMap[T, I, R](project: (value: T, index: Int) => Observable[I], resultSelector: (outerValue: T, innerValue: I, outerIndex: Int, innerIndex: Int) => R = js.undefined): js.Any = js.native

    def interval(period: Int = 1000, scheduler: Scheduler = js.undefined): Observable[Int] = js.native

    def merge[T](v1: Observable[T], scheduler: Scheduler = js.undefined): Observable[T] = js.native
    def merge[T](v1: Observable[T], concurrent: Int = ???, scheduler: Scheduler = js.undefined): Observable[T] = js.native
    def merge[T, T2](v1: Observable[T], v2: Observable[T2], scheduler: Scheduler = js.undefined): Observable[T | T2] = js.native
    def merge[T, T2](v1: Observable[T], v2: Observable[T2], concurrent: Int = ???, scheduler: Scheduler = js.undefined): Observable[T | T2] = js.native
    def merge[T, T2, T3](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], scheduler: Scheduler = js.undefined): Observable[T | T2 | T3] = js.native
    def merge[T, T2, T3](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], concurrent: Int = ???, scheduler: Scheduler = js.undefined): Observable[T | T2 | T3] = js.native
    def merge[T, T2, T3, T4](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], scheduler: Scheduler = js.undefined): Observable[T | T2 | T3 | T4] = js.native
    def merge[T, T2, T3, T4](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], concurrent: Int = ???, scheduler: Scheduler = js.undefined): Observable[T | T2 | T3 | T4] = js.native
    def merge[T, T2, T3, T4, T5](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], scheduler: Scheduler = js.undefined): Observable[T | T2 | T3 | T4 | T5] = js.native
    def merge[T, T2, T3, T4, T5](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], concurrent: Int = ???, scheduler: Scheduler = js.undefined): Observable[T | T2 | T3 | T4 | T5] = js.native
    def merge[T, T2, T3, T4, T5, T6](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], v6: Observable[T6], scheduler: Scheduler = js.undefined): Observable[T | T2 | T3 | T4 | T5 | T6] = js.native
    def merge[T, T2, T3, T4, T5, T6](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], v6: Observable[T6], concurrent: Int = ???, scheduler: Scheduler = js.undefined): Observable[T | T2 | T3 | T4 | T5 | T6] = js.native
    def merge[T, R](observables: js.Array[Observable[T]]: Observable[R] = js.native

    def of[T](T*): Observable[T] = js.native
    def race[T](observables: (Observable[T] | Array[Observable[T]])*): Observable[T] = js.native

    def range(start: Int = 0, count: Int = 0, scheduler: Scheduler = js.undefined): Observable[Int] = js.native
    def timer(initialDelay: Int = 0, period: Int = 1000, scheduler: Scheduler = js.undefined):  Observable[Int] = js.native

    def zip[T](v1: Observable[T]): Observable[[T]] = js.native
    def zip[T, T2](v1: Observable[T], v2: Observable[T2]): Observable[js.Array[T | T2]] = js.native
    def zip[T, T2, T3](v1: Observable[T], v2: Observable[T2], v3: Observable[T3]): Observable[js.Array[T | T2 | T3]] = js.native
    def zip[T, T2, T3, T4](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4]): Observable[js.Array[T | T2 | T3 | T4]] = js.native
    def zip[T, T2, T3, T4, T5](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5]): Observable[js.Array[T | T2 | T3 | T4 | T5]] = js.native
    def zip[T, T2, T3, T4, T5, T6](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], v6: Observable[T6]): Observable[js.Array[T | T2 | T3 | T4 | T5 | T6]] = js.native
    def zip[T, R](v1: Observable[T], project: (v1: T) => R): Observable[R] = js.native
    def zip[T, T2, R](v1: Observable[T], v2: Observable[T2], project: (v1: T, v2: T2) => R): Observable[R] = js.native
    def zip[T, T2, T3, R](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], project: (v1: T, v2: T2, v3: T3) => R): Observable[R] = js.native
    def zip[T, T2, T3, T4, R](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], project: (v1: T, v2: T2, v3: T3, v4: T4) => R): Observable[R] = js.native
    def zip[T, T2, T3, T4, T5, R](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], project: (v1: T, v2: T2, v3: T3, v4: T4, v5: T5) => R): Observable[R] = js.native
    def zip[T, T2, T3, T4, T5, T6, R](v1: Observable[T], v2: Observable[T2], v3: Observable[T3], v4: Observable[T4], v5: Observable[T5], v6: Observable[T6], project: (v1: T, v2: T2, v3: T3, v4: T4, v5: T5, v6: T6) => R): Observable[R] = js.native
    def zip[R] (observables: js.Array[Observable[js.Any], project:  js.Array[Array[js.Any] => R] ): Observable[R] = js.native


    var create: js.Function = js.native
    var `if`: IfObservable.create.type = js.native
    var `throw`: ErrorObservable.create.type = js.native
  }

}
