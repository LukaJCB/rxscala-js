package rxscalajs.facade
import rxscalajs._
import rxscalajs.subscription.{AnonymousSubscription, ObserverFacade, Subscription}

import scala.scalajs.js
import scala.scalajs.js._
import org.scalajs.dom._

import scala.scalajs.js.annotation.JSName

  @js.native
  trait Subscribable[+T] extends js.Object {
    def subscribe(onNext: js.Function1[T, Unit], error: js.Function1[js.Any, Unit] = ???, complete: js.Function0[Unit] = ???): AnonymousSubscription = js.native
    def subscribe(observer: ObserverFacade[_ >: T]): Subscription = js.native
  }

  @js.native
  class GroupedObservableFacade[K,T] protected() extends ObservableFacade[T] {
    def this(key: K, groupSubject: SubjectFacade[T], refCountSubscription: Subscription) = this()
    val key: K = js.native
  }
  @js.native
  trait TimeInterval[+T] extends js.Object { def value: T; def interval: Int }

  @js.native
  trait Timestamp[+T] extends js.Object { def value: T; def timestamp: Double }

  @js.native
  class ErrorObservableFacade protected() extends ObservableFacade[js.Any] {
    def this(error: js.Any,scheduler: Scheduler = ???) = this()
  }


  @js.native
  @JSName("Rx.Observable")
  class ObservableFacade[+T] protected() extends Subscribable[T] {
    def this(subscribe: js.Function = js.native) = this()

    var source: ObservableFacade[js.Any] = js.native



    def audit[T2](durationSelector:  js.Function1[T,Subscribable[T2]]): ObservableFacade[T] = js.native
    def auditTime(delay: Int, scheduler: Scheduler = ???): ObservableFacade[T] = js.native
    def buffer[T2](closingNotifier: ObservableFacade[T2]): ObservableFacade[js.Array[_ <: T]] = js.native
    def bufferCount(bufferSize: Int, startBufferEvery: Int = ???): ObservableFacade[js.Array[_ <: T]] = js.native
    def bufferTime(bufferTimeSpan: Int, bufferCreationInterval: Int = ???, scheduler: Scheduler = ???): ObservableFacade[js.Array[_ <: T]] = js.native
    def bufferToggle[T2,O](openings: Subscribable[O], closingSelector:  js.Function1[O, Subscribable[T2]]): ObservableFacade[js.Array[_ <: T]] = js.native
    def bufferWhen[T2](closingSelector: js.Function0[ObservableFacade[T2]]): ObservableFacade[js.Array[_ <: T]] = js.native

    def cache(bufferSize: Int = ???, windowTime: Int = ???, scheduler: Scheduler = ???): ObservableFacade[T] = js.native
    def `catch`[U](selector:js.Function1[js.Any, ObservableFacade[U]]): ObservableFacade[U] = js.native
    def onErrorResumeNext[U >: T](resumeFunction: js.Function1[js.Any, ObservableFacade[U]]): ObservableFacade[U] = js.native
    def combineAll[T2,R](project:  js.Function1[js.Array[T2],R] = ???): ObservableFacade[R] = js.native


    def combineLatest[T2, R](v2: ObservableFacade[T2], project:  js.Function2[T,T2,R] = ???): ObservableFacade[R] = js.native

    def concat[U](that: ObservableFacade[U], scheduler: Scheduler = ???): ObservableFacade[U] = js.native

    def concatAll[U](): ObservableFacade[U] = js.native

    def concatMap[I, R](project: js.Function2[T,Int,ObservableFacade[I]], resultSelector: js.Function4[T, I, Int, Int, R] = ???): ObservableFacade[R] = js.native


    def concatMapTo[I, R](innerObservable: ObservableFacade[I], resultSelector: js.Function4[T, I, Int, Int, R] = ???): ObservableFacade[R] = js.native


    def count(predicate: js.Function3[T, Int, ObservableFacade[T],Boolean]  = ???): ObservableFacade[Int] = js.native

    def debounce(durationSelector:  js.Function1[T, Subscribable[Int]]): ObservableFacade[T] = js.native
    def debounceTime(dueTime: Int, scheduler: Scheduler = ???): ObservableFacade[T] = js.native


    def defaultIfEmpty[R](defaultValue: R): ObservableFacade[R] = js.native
    def delay(delay: Int | Date, scheduler: Scheduler = ???): ObservableFacade[T] = js.native
    def delayWhen[U,I](delayDurationSelector:  js.Function1[T, ObservableFacade[U]], subscriptionDelay: ObservableFacade[I] = ???): ObservableFacade[T] = js.native
    def dematerialize[T2](): ObservableFacade[T2] = js.native
    def distinct[T2](compare: js.Function2[T,  T, Boolean] = ???, flushes: ObservableFacade[T2] = ???): ObservableFacade[T] = js.native
    def distinctKey[T2](key: String, compare: js.Function2[T,  T,Boolean] = ???, flushes: ObservableFacade[T2] = ???): ObservableFacade[T] = js.native
    def distinctUntilChanged[K](compare: js.Function2[K,  K,Boolean], keySelector: js.Function1[T,K]): ObservableFacade[T] = js.native
    def distinctUntilChanged(compare: js.Function2[T,  T,Boolean] = ???): ObservableFacade[T] = js.native
    def distinctUntilKeyChanged(key: String, compare: js.Function2[ T,  T, Boolean] = ???): ObservableFacade[T] = js.native

    def every[T2](predicate: js.Function2[T,  Int,Boolean], thisArg: T2 = ???): ObservableFacade[Boolean] = js.native
    def exhaust[U](): ObservableFacade[U] = js.native
    def exhaustMap[I, R](project: js.Function2[T, Int, ObservableFacade[R]], resultSelector: js.Function4[T, I, Int, Int, R] = ???): ObservableFacade[R] = js.native
    def expand[R](project: js.Function2[ T, Int, ObservableFacade[R]], concurrent: Double = ???, scheduler: Scheduler = ???): ObservableFacade[R] = js.native
    def filter[T2](predicate: js.Function2[ T,  Int, Boolean], thisArg: T2 = ???): ObservableFacade[T] = js.native
    def filter[T2](predicate: js.Function1[ T, Boolean]): ObservableFacade[T] = js.native
    def _finally(finallySelector: js.Function0[Unit]): ObservableFacade[T] = js.native
    def find[T2](predicate: js.Function2[T,  Int,Boolean], thisArg: T2 = ???): ObservableFacade[T] = js.native
    def findIndex[T2](predicate: js.Function2[T,  Int, Boolean], thisArg: T2 = ???): ObservableFacade[Int] = js.native
    def first[ R](predicate: js.Function2[T,  Int, Boolean] = ???, resultSelector: js.Function2[T,Int,R] = ???, defaultValue: R = ???): ObservableFacade[R] = js.native
    def groupBy[K, R,T2](keySelector: js.Function1[T,K], elementSelector:  js.Function1[T,R]= ???, durationSelector:  js.Function1[GroupedObservableFacade[K, R],ObservableFacade[T2]] = ???): ObservableFacade[GroupedObservableFacade[K, R]] = js.native
    def ignoreElements(): ObservableFacade[T] = js.native
    def isEmpty(): ObservableFacade[Boolean] = js.native
    def last[R](predicate:  js.Function3[T,  Int,  ObservableFacade[T],Boolean] = ???, resultSelector: js.Function2[T,Int,R] = ???, defaultValue: R = ???): ObservableFacade[R] = js.native
    def let[ R](func:  js.Function1[ObservableFacade[T],ObservableFacade[R]]): ObservableFacade[R] = js.native
    def map[R](project: js.Function2[T,Int,R]): ObservableFacade[R] = js.native
    def map[R](project: js.Function1[T,R]): ObservableFacade[R] = js.native
    def mapTo[ R](value: R): ObservableFacade[R] = js.native
    def materialize(): ObservableFacade[Notification[_ <: T]] = js.native
    def merge[R >: T](that: ObservableFacade[R], concurrent: Double = ???, scheduler: Scheduler = ???): ObservableFacade[R] = js.native

    def mergeAll[U](concurrent: Double = ???): ObservableFacade[U] = js.native
    def mergeMap[R](project: js.Function2[T, Int,ObservableFacade[R]], resultSelector: js.Function4[T, R, Int, Int, R] = ???, concurrent: Double = ???): ObservableFacade[R] = js.native

    def mergeMap[ R](project: js.Function1[T,ObservableFacade[R]]): ObservableFacade[R] = js.native
    def mergeMapTo[I, R](innerObservable: ObservableFacade[I], resultSelector: js.Function4[T, I, Int, Int, R] = ???, concurrent: Double = ???): ObservableFacade[R] = js.native
    def mergeScan[ R](project: js.Function2[R,T,ObservableFacade[R]], seed: R, concurrent: Int = ???): ObservableFacade[R] = js.native

    def multicast(subject: SubjectFacade[_ >: T]): ConnectableObservableFacade[T] = js.native

    def observeOn(scheduler: Scheduler, delay: Int = ???): ObservableFacade[T] = js.native

    def pairwise(): ObservableFacade[js.Array[_ <: T]] = js.native
    def partition[T2](predicate: js.Function1[T,Boolean], thisArg: T2 = ???): js.Array[_ <: ObservableFacade[T]] = js.native
    def pluck[R](properties: String*): ObservableFacade[R] = js.native
    def publish(): ConnectableObservableFacade[T] = js.native

    def publishBehavior(value: Any): ConnectableObservableFacade[T] = js.native

    def publishLast(): ConnectableObservableFacade[T] = js.native
    def publishReplay(bufferSize: Double = ???, windowTime: Double = ???, scheduler: Scheduler = ???): ConnectableObservableFacade[T] = js.native

    def race(observables: js.Array[_ >: ObservableFacade[T]]): ObservableFacade[T] = js.native
    def reduce[R](project: js.Function2[R,T,R],seed: R = ???): ObservableFacade[R] = js.native

    def repeat(scheduler: Scheduler = ???, count: Int = ???): ObservableFacade[T] = js.native

    def retry(count: Int = ???): ObservableFacade[T] = js.native
    def retryWhen[T2,T3](notifier: js.Function1[ObservableFacade[T2], ObservableFacade[T3]]): ObservableFacade[T] = js.native
    def sample[I](notifier: ObservableFacade[I]): ObservableFacade[T] = js.native

    def sampleTime(delay: Int, scheduler: Scheduler = ???): ObservableFacade[T] = js.native
    def scan[R](accumulator: js.Function2[R, T, R],seed: R = ???): ObservableFacade[R] = js.native
    def share(): ObservableFacade[T] = js.native
    def single(predicate: js.Function3[T, Int, ObservableFacade[T],Boolean] = ???): ObservableFacade[T] = js.native


    def skip(total: Int): ObservableFacade[T] = js.native
    def skipUntil[T2](notifier: ObservableFacade[T2]): ObservableFacade[T] = js.native
    def skipWhile(predicate: js.Function2[T,Int,Boolean]): ObservableFacade[T] = js.native


    def startWith[U >: T](v1: U, scheduler: Scheduler = ???): ObservableFacade[U] = js.native
    def subscribeOn(scheduler: Scheduler, delay: Int = ???): ObservableFacade[T] = js.native
    def switch(): T = js.native
    def switchMap[I, R](project: js.Function2[T, Int,ObservableFacade[I]], resultSelector: js.Function4[T, I, Int, Int, R] = ???): ObservableFacade[R] = js.native
    def switchMap[I, R](project: js.Function1[T,ObservableFacade[I]]): ObservableFacade[R] = js.native
    def switchMapTo[ I, R](innerObservable: ObservableFacade[I], resultSelector: js.Function4[T, I, Int, Int, R] = ???): ObservableFacade[R] = js.native
    def take(total: Int): ObservableFacade[T] = js.native
    def takeLast(total: Int): ObservableFacade[T] = js.native
    def takeUntil[T2](notifier: ObservableFacade[T2]): ObservableFacade[T] = js.native
    def takeWhile(predicate: js.Function2[T,Int,Boolean]): ObservableFacade[T] = js.native
    def throttle(durationSelector:  js.Function1[T, Subscribable[Int]]): ObservableFacade[T] = js.native
    def throttleTime(delay: Int, scheduler: Scheduler = ???): ObservableFacade[T] = js.native


    def timeInterval(scheduler: Scheduler = ???): ObservableFacade[TimeInterval[T]] = js.native
    def timeout[T2](due: Int | Date, errorToSend: T2 = ???, scheduler: Scheduler = ???): ObservableFacade[T] = js.native
    def timeoutWith[ R](due: Int | Date, withObservable: ObservableFacade[R], scheduler: Scheduler = ???): ObservableFacade[R] = js.native
    def timestamp(scheduler: Scheduler = ???): ObservableFacade[Timestamp[T]] = js.native
    def toArray(): ObservableFacade[js.Array[_ <: T]] = js.native
    def window[I](windowBoundaries: ObservableFacade[I]): ObservableFacade[ObservableFacade[T]] = js.native
    def windowCount(windowSize: Int, startWindowEvery: Int = ???): ObservableFacade[ObservableFacade[T]] = js.native
    def windowTime(windowTimeSpan: Int, windowCreationInterval: Int = ???, scheduler: Scheduler = ???): ObservableFacade[ObservableFacade[T]] = js.native
    def windowToggle[T2,O](openings: ObservableFacade[O], closingSelector:  js.Function1[O, ObservableFacade[T2]]): ObservableFacade[ObservableFacade[T]] = js.native
    def windowWhen[T2](closingSelector: js.Function0[ObservableFacade[T2]]): ObservableFacade[ObservableFacade[T]] = js.native

    def withLatestFrom[T2, R](v2: ObservableFacade[T2], project: js.Function2[T, T2, R] = ???): ObservableFacade[R] = js.native

    def zip[T2, R](v2: ObservableFacade[T2], project: js.Function2[T,T2,R] = ???): ObservableFacade[R] = js.native

    def zipAll[T2,R](project: (js.Function1[js.Array[T2],R]) = ???): ObservableFacade[R] = js.native




    def forEach(next: js.Function1[T, Unit], PromiseCtor: Promise.type = js.native): Promise[Unit] = js.native

  }

  @js.native
  @JSName("Rx.Observable")
  object ObservableFacade extends js.Object {
    type CreatorFacade = Unit | js.Function0[Unit]

    def ajax[T2](request: String | js.Object): ObservableFacade[T2]  = js.native

    def bindCallback[T,T2](callbackFunc: js.Function, selector: js.Function, scheduler: Scheduler): js.Function1[T2, ObservableFacade[T]]  = js.native

    def bindNodeCallback[T,T2](callbackFunc: js.Function, selector: js.Function, scheduler: Scheduler): js.Function1[T2, ObservableFacade[T]]  = js.native


    def fromEvent(element: Element, eventName: String): ObservableFacade[Event] = js.native

    def forkJoin[T](sources: ObservableFacade[T]*): ObservableFacade[js.Array[_ <: T]] = js.native

    def combineLatest[T, R](sources: js.Array[ObservableFacade[T]],combineFunction: js.Function1[js.Array[_ <: T], R] = ???): ObservableFacade[R] = js.native

    def create[T](subscribe: js.Function1[ObserverFacade[T],CreatorFacade]): ObservableFacade[T] = js.native

    def concat[T, R](observables: js.Array[ObservableFacade[T]], scheduler: Scheduler = ???): ObservableFacade[R] = js.native

    def concatMap[T,T2, I, R](project: js.Function2[T,Int, ObservableFacade[I]], resultSelector: js.Function4[T, I, Int, Int, R] = ???): T2 = js.native

    def interval(period: Int = 0, scheduler: Scheduler = ???): ObservableFacade[Int] = js.native


    def merge[T, R](observables: js.Array[ObservableFacade[T]], scheduler: Scheduler = ???): ObservableFacade[R] = js.native

    def of[T](elements: T*): ObservableFacade[T] = js.native
    def race[T](observables: ObservableFacade[T]*): ObservableFacade[T] = js.native

    def range(start: Int = 0, count: Int = 0, scheduler: Scheduler = ???): ObservableFacade[Int] = js.native
    def timer(initialDelay: Int = 0, period: Int = 1000, scheduler: Scheduler = ???):  ObservableFacade[Int] = js.native



    def zip[T,R](observables: js.Array[ObservableFacade[T]], project: js.Function1[js.Array[_ <: T], R] = ??? ): ObservableFacade[R] = js.native


    var create: js.Function = js.native
  }
