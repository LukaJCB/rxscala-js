package rxscalajs

import scala.collection.mutable.Seq
import scala.scalajs.js
import js._

/**
  * Created by Luka on 29.04.2016.
  */
class Observable[T] private(inner: ObservableFacade[T]) {

  /**
    * Ignores source values for a duration determined by another Observable, then
    * emits the most recent value from the source Observable, then repeats this
    * process.
    *
    * <span class="informal">It's like {@link auditTime}, but the silencing
    * duration is determined by a second Observable.</span>
    *
    * <img src="./img/audit.png" width="100%">
    *
    * `audit` is similar to `throttle`, but emits the last value from the silenced
    * time window, instead of the first value. `audit` emits the most recent value
    * from the source Observable on the output Observable as soon as its internal
    * timer becomes disabled, and ignores source values while the timer is enabled.
    * Initially, the timer is disabled. As soon as the first source value arrives,
    * the timer is enabled by calling the `durationSelector` function with the
    * source value, which returns the "duration" Observable. When the duration
    * Observable emits a value or completes, the timer is disabled, then the most
    * recent source value is emitted on the output Observable, and this process
    * repeats for the next source value.
    *
    * @example <caption>Emit clicks at a rate of at most one click per second</caption>
    * val result = clickStream.audit(ev => Observable.interval(1000));
    * result.subscribe(x => println(x));
    *
    *
    * @param durationSelector A function
    * that receives a value from the source Observable, for computing the silencing
    * duration, returned as an Observable or a Promise.
    * @return {Observable[T]} An Observable that performs rate-limiting of
    * emissions from the source Observable.
    */
  def audit[I](durationSelector: T => ObservableFacade[I]): Observable[T] = new Observable(inner.audit(durationSelector))

  /**
    * Ignores source values for `duration` milliseconds, then emits the most recent
    * value from the source Observable, then repeats this process.
    *
    * <span class="informal">When it sees a source values, it ignores that plus
    * the next ones for `duration` milliseconds, and then it emits the most recent
    * value from the source.</span>
    *
    * <img src="./img/auditTime.png" width="100%">
    *
    * `auditTime` is similar to `throttleTime`, but emits the last value from the
    * silenced time window, instead of the first value. `auditTime` emits the most
    * recent value from the source Observable on the output Observable as soon as
    * its internal timer becomes disabled, and ignores source values while the
    * timer is enabled. Initially, the timer is disabled. As soon as the first
    * source value arrives, the timer is enabled. After `duration` milliseconds (or
    * the time unit determined internally by the optional `scheduler`) has passed,
    * the timer is disabled, then the most recent source value is emitted on the
    * output Observable, and this process repeats for the next source value.
    * Optionally takes a Scheduler for managing timers.
    *
    * @example <caption>Emit clicks at a rate of at most one click per second</caption>
    * var clicks = Rx.Observable.fromEvent(document, 'click');
    * var result = clicks.auditTime(1000);
    * result.subscribe(x => console.log(x));
    *
    *
    * @param delay Time to wait before emitting the most recent source
    * value, measured in milliseconds or the time unit determined internally
    * by the optional `scheduler`.
    * @param scheduler The Scheduler to use for
    * managing the timers that handle the rate-limiting behavior.
    * @return An Observable that performs rate-limiting of
    * emissions from the source Observable.
    */
  def auditTime(delay: Int, scheduler: Scheduler): Observable[T] = new Observable(inner.auditTime(delay,scheduler))
  def auditTime(delay: Int): Observable[T] = new Observable(inner.auditTime(delay))

  /**
    * Buffers the source Observable values until `closingNotifier` emits.
    *
    * <span class="informal">Collects values from the past as an array, and emits
    * that array only when another Observable emits.</span>
    *
    * <img src="./img/buffer.png" width="100%">
    *
    * Buffers the incoming Observable values until the given `closingNotifier`
    * Observable emits a value, at which point it emits the buffer on the output
    * Observable and starts a new buffer internally, awaiting the next time
    * `closingNotifier` emits.
    *
    * @example <caption>On every click, emit array of most recent interval events</caption>
    * var clicks = Rx.Observable.fromEvent(document, 'click');
    * var interval = Rx.Observable.interval(1000);
    * var buffered = interval.buffer(clicks);
    * buffered.subscribe(x => console.log(x));
    *
    *
    * @param closingNotifier An Observable that signals the
    * buffer to be emitted on the output Observable.
    * @return An Observable of buffers, which are arrays of
    * values.
    */
  def buffer[T2](closingNotifier: Observable[T2]): Observable[js.Array[T]] = new Observable(inner.buffer(closingNotifier))

  /**
    * Buffers the source Observable values until the size hits the maximum
    * `bufferSize` given.
    *
    * <span class="informal">Collects values from the past as an array, and emits
    * that array only when its size reaches `bufferSize`.</span>
    *
    * <img src="./img/bufferCount.png" width="100%">
    *
    * Buffers a number of values from the source Observable by `bufferSize` then
    * emits the buffer and clears it, and starts a new buffer each
    * `startBufferEvery` values. If `startBufferEvery` is not provided or is
    * `null`, then new buffers are started immediately at the start of the source
    * and when each buffer closes and is emitted.
    *
    * @example <caption>Emit the last two click events as an array</caption>
    * var clicks = Rx.Observable.fromEvent(document, 'click');
    * var buffered = clicks.bufferCount(2);
    * buffered.subscribe(x => console.log(x));
    *
    * @example <caption>On every click, emit the last two click events as an array</caption>
    * var clicks = Rx.Observable.fromEvent(document, 'click');
    * var buffered = clicks.bufferCount(2, 1);
    * buffered.subscribe(x => console.log(x));
    *
    *
    * @param  bufferSize The maximum size of the buffer emitted.
    * @param startBufferEvery Interval at which to start a new buffer.
    * For example if `startBufferEvery` is `2`, then a new buffer will be started
    * on every other value from the source. A new buffer is started at the
    * beginning of the source by default.
    * @return  An Observable of arrays of buffered values.
    */
  def bufferCount(bufferSize: Int, startBufferEvery: Int): Observable[js.Array[T]] = new Observable(inner.bufferCount(bufferSize,startBufferEvery))
  def bufferCount(bufferSize: Int): Observable[js.Array[T]] = new Observable(inner.bufferCount(bufferSize))
  /**
    * Buffers the source Observable values for a specific time period.
    *
    * <span class="informal">Collects values from the past as an array, and emits
    * those arrays periodically in time.</span>
    *
    * <img src="./img/bufferTime.png" width="100%">
    *
    * Buffers values from the source for a specific time duration `bufferTimeSpan`.
    * Unless the optional argument `bufferCreationInterval` is given, it emits and
    * resets the buffer every `bufferTimeSpan` milliseconds. If
    * `bufferCreationInterval` is given, this operator opens the buffer every
    * `bufferCreationInterval` milliseconds and closes (emits and resets) the
    * buffer every `bufferTimeSpan` milliseconds.
    *
    * @example <caption>Every second, emit an array of the recent click events</caption>
    * var clicks = Rx.Observable.fromEvent(document, 'click');
    * var buffered = clicks.bufferTime(1000);
    * buffered.subscribe(x => console.log(x));
    *
    * @example <caption>Every 5 seconds, emit the click events from the next 2 seconds</caption>
    * var clicks = Rx.Observable.fromEvent(document, 'click');
    * var buffered = clicks.bufferTime(2000, 5000);
    * buffered.subscribe(x => console.log(x));
    *
    *
    * @param bufferTimeSpan The amount of time to fill each buffer array.
    * @param bufferCreationInterval The interval at which to start new
    * buffers.
    * @param  scheduler The scheduler on which to schedule the
    * intervals that determine buffer boundaries.
    * @return An observable of arrays of buffered values.
    */
  def bufferTime(bufferTimeSpan: Int, bufferCreationInterval: Int, scheduler: Scheduler): Observable[js.Array[T]] = new Observable(inner.bufferTime(bufferTimeSpan,bufferCreationInterval,scheduler))
  def bufferTime(bufferTimeSpan: Int, bufferCreationInterval: Int): Observable[js.Array[T]] = new Observable(inner.bufferTime(bufferTimeSpan,bufferCreationInterval))
  def bufferTime(bufferTimeSpan: Int): Observable[js.Array[T]] = new Observable(inner.bufferTime(bufferTimeSpan))
  /**
    * Buffers the source Observable values starting from an emission from
    * `openings` and ending when the output of `closingSelector` emits.
    *
    * <span class="informal">Collects values from the past as an array. Starts
    * collecting only when `opening` emits, and calls the `closingSelector`
    * function to get an Observable that tells when to close the buffer.</span>
    *
    * <img src="./img/bufferToggle.png" width="100%">
    *
    * Buffers values from the source by opening the buffer via signals from an
    * Observable provided to `openings`, and closing and sending the buffers when
    * a Subscribable or Promise returned by the `closingSelector` function emits.
    *
    * @example <caption>Every other second, emit the click events from the next 500ms</caption>
    * var clicks = Rx.Observable.fromEvent(document, 'click');
    * var openings = Rx.Observable.interval(1000);
    * var buffered = clicks.bufferToggle(openings, i =>
    *   i % 2 ? Rx.Observable.interval(500) : Rx.Observable.empty()
    * );
    * buffered.subscribe(x => console.log(x));
    *
    * @param  openings A Subscribable or Promise of notifications to start new
    * buffers.
    * @param closingSelector A function that takes
    * the value emitted by the `openings` observable and returns a Subscribable or Promise,
    * which, when it emits, signals that the associated buffer should be emitted
    * and cleared.
    * @return  An observable of arrays of buffered values.
    */
  def bufferToggle[T2,O](openings: Subscribable[O], closingSelector:  O => ObservableFacade[T2]): Observable[js.Array[T]] = new Observable(inner.bufferToggle(openings,closingSelector))
  /**
    * Buffers the source Observable values, using a factory function of closing
    * Observables to determine when to close, emit, and reset the buffer.
    *
    * <span class="informal">Collects values from the past as an array. When it
    * starts collecting values, it calls a function that returns an Observable that
    * tells when to close the buffer and restart collecting.</span>
    *
    * <img src="./img/bufferWhen.png" width="100%">
    *
    * Opens a buffer immediately, then closes the buffer when the observable
    * returned by calling `closingSelector` function emits a value. When it closes
    * the buffer, it immediately opens a new buffer and repeats the process.
    *
    * @example <caption>Emit an array of the last clicks every [1-5] random seconds</caption>
    * var clicks = Rx.Observable.fromEvent(document, 'click');
    * var buffered = clicks.bufferWhen(() =>
    *   Rx.Observable.interval(1000 + Math.random() * 4000)
    * );
    * buffered.subscribe(x => console.log(x));
    *
    *
    * @param  closingSelector A function that takes no
    * arguments and returns an Observable that signals buffer closure.
    * @return  An observable of arrays of buffered values.
    */
  def bufferWhen[T2](closingSelector: () => ObservableFacade[T2]): Observable[js.Array[T]] = new Observable(inner.bufferWhen(closingSelector))

  /**
    * This method has similar behavior to `Observable.replay` except that this auto-subscribes to
    * the source Observable rather than returning a start function and an Observable.
    *
    * <img width="640" height="410" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/cache.png" alt="" />
    *
    * This is useful when you want an Observable to cache responses and you can't control the
    * subscribe/unsubscribe behavior of all the Observers.
    *
    * When you call `cache`, it does not yet subscribe to the
    * source Observable. This only happens when `subscribe` is called
    * the first time on the Observable returned by `cache`.
    *
    * Note: You sacrifice the ability to unsubscribe from the origin when you use the
    * `cache()` operator so be careful not to use this operator on Observables that
    * emit an infinite or very large number of items that will use up memory.
    *
    * @return an Observable that when first subscribed to, caches all of its notifications for
    *         the benefit of subsequent subscribers.
    */
  def cache(bufferSize: Int, windowTime: Int, scheduler: Scheduler): ObservableFacade[T] = new Observable( inner.cache(bufferSize,windowTime,scheduler))
  def cache(bufferSize: Int, windowTime: Int): ObservableFacade[T] = new Observable( inner.cache(bufferSize,windowTime))
  def cache(bufferSize: Int): ObservableFacade[T] = new Observable( inner.cache(bufferSize))
  def cache(): ObservableFacade[T] = new Observable( inner.cache())

  def combineAll[T2,R](project: js.Array[T2] => R): Observable[R] = new Observable(inner.combineAll(project))


  def combineLatest[T2, R](v2: ObservableFacade[T2], project: (T,T2) => R): Observable[R] = new Observable(inner.combineLatest(v2,project))
  def combineLatest[T2, R](v2: ObservableFacade[T2]): Observable[R] = new Observable(inner.combineLatest(v2))


  /**
    * Returns an Observable that first emits the items emitted by `this`, and then the items emitted
    * by `that`.
    *
    * <img width="640" height="380" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/concat.png" alt="" />
    *
    * @param other
    *            an Observable to be appended
    * @return an Observable that emits items that are the result of combining the items emitted by
    *         this and that, one after the other
    */
  def ++ [U >: T](other: Observable[U]): Observable[U] = new Observable(inner concat other)

  /**
    * Returns an Observable that emits the items emitted by several Observables, one after the
    * other.
    *
    * This operation is only available if `this` is of type `Observable[Observable[U]]` for some `U`,
    * otherwise you'll get a compilation error.
    *
    * @usecase def concat[U]: Observable[U]
    */
  def concat[U](other: Observable[U]): Observable[U] = new Observable(inner concat other)

  def concatAll[U](implicit evidence: <:<[Observable[T], Observable[Observable[U]]]): Observable[U] = new Observable[U](inner.concatAll())

  def concatMap[I, R](project: (T,Int) => ObservableFacade[I], resultSelector: (T, I, Int, Int) => R): Observable[R] = new Observable(inner.concatMap(project,resultSelector))
  def concatMap[I, R](project: (T,Int) => ObservableFacade[I]): Observable[R] = new Observable(inner.concatMap[I,R](project))


  def concatMapTo[I, R](innerObservable: Observable[I], resultSelector: (T, I, Int, Int) => R): Observable[R] = new Observable(inner.concatMapTo(innerObservable,resultSelector))
  def concatMapTo[I, R](innerObservable: Observable[I]): Observable[R] = new Observable(inner.concatMapTo[I,R](innerObservable))

  def count(predicate: (T, Int, ObservableFacade[T]) => Boolean): Observable[Int] = new Observable( inner.count(predicate))

  def count(): ObservableFacade[Int] = new Observable(inner.count())


  def debounce(durationSelector:  T => ObservableFacade[Int]): Observable[T] = new Observable(inner.debounce(durationSelector))
  def debounceTime(dueTime: Int): Observable[T] = new Observable(inner.debounceTime(dueTime))


  def defaultIfEmpty[R](defaultValue: R): Observable[R] = new Observable(inner.defaultIfEmpty(defaultValue))

  def delay(delay: Int | Date): Observable[T] = new Observable(inner.delay(delay))

  def delayWhen[U,I](delayDurationSelector: T => ObservableFacade[U], subscriptionDelay: Observable[I]): Observable[T] = new Observable(inner.delayWhen(delayDurationSelector,subscriptionDelay))
  def delayWhen[U,I](delayDurationSelector: T => ObservableFacade[U]): Observable[T] = new Observable(inner.delayWhen(delayDurationSelector))

  def dematerialize[T2](): Observable[T2] = new Observable((inner.dematerialize()))

  def distinct[T2](compare: (T,  T) => Boolean, flushes: Observable[T2]): Observable[T] = new Observable(inner.distinct(compare,flushes))
  def distinct[T2]( flushes: Observable[T2]): Observable[T] = new Observable(inner.distinct(flushes = flushes))
  def distinct[T2](compare: (T,  T) => Boolean): Observable[T] = new Observable(inner.distinct(compare))
  def distinct[T2](): Observable[T] = new Observable(inner.distinct())

  def distinctKey[T2](key: String, compare: (T,  T) => Boolean = null, flushes: Observable[T2] = null): Observable[T] = new Observable(inner.distinctKey(key,compare,flushes))

  def distinctUntilChanged[K](compare: (K,  K) => Boolean = null, keySelector: T => K = null): Observable[T] = new Observable(inner.distinctUntilChanged(compare,keySelector))

  def distinctUntilKeyChanged(key: String, compare: (T,  T) => Boolean = null): Observable[T] = new Observable(inner.distinctUntilKeyChanged(key,compare))

  def elementAt(index: Int, defaultValue: T): Observable[T] = new Observable(inner.elementAt(index,defaultValue))

  def every[T2](predicate: (T,  Int,  ObservableFacade[T]) => Boolean): Observable[Boolean] = new Observable(inner.every(predicate))

  def exhaust(): Observable[T] = new Observable(inner.exhaust())

  def exhaustMap[I, R](project: (T, Int) => ObservableFacade[R], resultSelector: (T, I, Int, Int) => R = null): Observable[R] = new Observable(inner.exhaustMap(project,resultSelector))

  def expand[R](project: ( T, Int) => ObservableFacade[R], concurrent: Int = 0, scheduler: Scheduler = null): Observable[R] = new Observable(inner.expand(project,concurrent,scheduler))


  def filter[T2](predicate: (T,  Int) => Boolean): Observable[T] = new Observable(inner.filter(predicate))


  def find[T2](predicate: (T,  Int,  ObservableFacade[T]) =>Boolean): Observable[T] = new Observable(inner.find(predicate))

  def findIndex[T2](predicate: (T,  Int,  ObservableFacade[T]) =>Boolean): Observable[Int] = new Observable(inner.findIndex(predicate))


  def first(): Observable[T] = new Observable(inner.take(1))
  def firstOrElse[R >: T](default: => R): Observable[R] = new Observable(inner.first(defaultValue = default))


  def groupBy[K,R,T2](keySelector:  T => K, elementSelector: T => R, durationSelector: GroupedObservableFacade[K, R] => ObservableFacade[T2]): Observable[GroupedObservableFacade[K, R]] =
    new Observable(inner.groupBy(keySelector,elementSelector,durationSelector))
  def groupBy[K,R,T2](keySelector:  T => K, durationSelector: GroupedObservableFacade[K, R] => ObservableFacade[T2] = null): Observable[GroupedObservableFacade[K, R]] =
    new Observable(inner.groupBy(keySelector,durationSelector = durationSelector))

  def groupBy[K,R,T2](keySelector:  T => K, elementSelector: T => R): Observable[GroupedObservableFacade[K, R]] = new Observable(inner.groupBy(keySelector,elementSelector))
  def groupBy[K,R,T2](keySelector:  T => K): Observable[GroupedObservableFacade[K, R]] = new Observable(inner.groupBy(keySelector))

  def ignoreElements(): Observable[T] = new Observable(inner.ignoreElements())

  def isEmpty(): Observable[Boolean] = new Observable(inner.isEmpty())

  def last(): Observable[T] = new Observable(inner.last[T]())
  def lastOrElse[R >: T](default: => R): Observable[R] = new Observable(inner.last(defaultValue = default))

  def map[R](project: (T,Int) => R): Observable[R] = new Observable[R](inner.map(project))

  def mapTo[R](value: R): Observable[R] = new Observable(inner.mapTo(value))

  def materialize(): Observable[Notification[T]] = new Observable(inner.materialize())


  def max(comparer: (T,T) => T): Observable[T] = new Observable(inner.max(comparer))
  def max(): Observable[T] = new Observable(inner.max())


  def merge[R >: T](that: ObservableFacade[R], concurrent: Double = Double.PositiveInfinity, scheduler: Scheduler): Observable[R] = new Observable(inner.merge(that,concurrent,scheduler))
  def merge[R >: T](that: ObservableFacade[R], concurrent: Double = Double.PositiveInfinity): Observable[R] = new Observable(inner.merge(that,concurrent))
  def merge[R >: T](that: ObservableFacade[R]): Observable[R] = new Observable(inner.merge(that))



  def mergeAll(concurrent: Double = Double.PositiveInfinity): Observable[T] = new Observable(inner.mergeAll(concurrent))

  def flatten[U]()(implicit evidence: <:<[Observable[T], Observable[Observable[U]]]): Observable[U] = new Observable(inner.mergeAll())

  def mergeMap[I, R](project: (T, Int) => ObservableFacade[I], resultSelector: (T, I, Int, Int) => R, concurrent: Double = Double.PositiveInfinity): Observable[R] =
    new Observable(inner.mergeMap(project,resultSelector,concurrent))
  def mergeMap[I, R](project: (T, Int) => ObservableFacade[I], concurrent: Double = Double.PositiveInfinity): ObservableFacade[R] =
    new Observable(inner.mergeMap[I,R](project,concurrent = concurrent))


  def mergeMapTo[I, R](innerObservable: ObservableFacade[I], resultSelector: (T, I, Int, Int) => R, concurrent: Double = Double.PositiveInfinity): Observable[R] =
    new Observable(inner.mergeMapTo(innerObservable,resultSelector,concurrent))
  def mergeMapTo[I, R](innerObservable: ObservableFacade[I], concurrent: Double = Double.PositiveInfinity): Observable[R] =
    new Observable(inner.mergeMapTo(innerObservable,concurrent = concurrent))


  def min(comparer: (T,T) => T): Observable[T] = new Observable(inner.min(comparer))
  def min(): Observable[T] = new Observable(inner.min())

  def multicast(subjectOrSubjectFactory: () => Subject[T]): ConnectableObservableFacade[T] = inner.multicast(subjectOrSubjectFactory)

  def pairwise(): Observable[(T,T)] = new Observable[(T, T)](inner.pairwise().map((arr: js.Array[T], index: Int) => (arr(0), arr(1))))

  def partition[T2](predicate: T => Boolean): (Observable[T], Observable[T]) = {
    val partitioned = inner.partition(predicate)
    (new Observable(partitioned(0)),new Observable(partitioned(1)))
  }

  def publish(): ConnectableObservableFacade[T] = inner.publish()

  def publishBehavior(value: T): ConnectableObservableFacade[T] = inner.publishBehavior(value)

  

  def subscribe(onNext: js.Function1[T,Unit], error: js.Function1[js.Any,Unit] = null, complete: js.Function0[Unit] = null): AnonymousSubscription = inner.subscribe(onNext,error,complete)

  private def get = inner

  implicit def toFacade[A](observable: Observable[A]): ObservableFacade[A] = observable.get
  //implicit def toOuter[A](facade: ObservableFacade[A]): Observable[A] = new Observable[A](facade)
  //implicit def convertParameter[U <% T](observable: Observable[T]): Observable[U] = this

}

object Observable {
  def apply[T](values: T*): Observable[T] = new Observable(ObservableFacade.of[T](values: _*))

}
