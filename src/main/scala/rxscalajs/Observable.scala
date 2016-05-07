package rxscalajs

import scala.collection.immutable.Seq
import scala.scalajs.js
import js._
import js.JSConverters._


/**
  * Created by Luka on 29.04.2016.
  */
class Observable[T] private(inner: ObservableFacade[T]) {

  /**
    * Ignores source values for a duration determined by another Observable, then
    * emits the most recent value from the source Observable, then repeats this
    * process.
    *
    * <span class="informal">It's like  auditTime, but the silencing
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
  def audit[I](durationSelector: T => Observable[I]): Observable[T] = new Observable(inner.audit(toReturnFacade(durationSelector)))


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
  def buffer[T2](closingNotifier: Observable[T2]): Observable[List[T]] = new Observable(inner.buffer(closingNotifier).map((n: js.Array[T], index: Int) => n.toList))

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
  def bufferCount(bufferSize: Int, startBufferEvery: Int): Observable[List[T]] = new Observable(inner.bufferCount(bufferSize,startBufferEvery).map((n: js.Array[T], index: Int) => n.toList))
  def bufferCount(bufferSize: Int): Observable[List[T]] = new Observable(inner.bufferCount(bufferSize).map((n: js.Array[T], index: Int) => n.toList))
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
  def bufferTime(bufferTimeSpan: Int, bufferCreationInterval: Int, scheduler: Scheduler): Observable[List[T]] = new Observable(inner.bufferTime(bufferTimeSpan,bufferCreationInterval,scheduler).map((n: js.Array[T], index: Int) => n.toList))
  def bufferTime(bufferTimeSpan: Int, bufferCreationInterval: Int): Observable[List[T]] = new Observable(inner.bufferTime(bufferTimeSpan,bufferCreationInterval).map((n: js.Array[T], index: Int) => n.toList))
  def bufferTime(bufferTimeSpan: Int): Observable[List[T]] = new Observable(inner.bufferTime(bufferTimeSpan).map((n: js.Array[T], index: Int) => n.toList))
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
  def bufferToggle[T2,O](openings: Subscribable[O], closingSelector:  O => Observable[T2]): Observable[List[T]] =
    new Observable(inner.bufferToggle(openings,toReturnFacade(closingSelector)).map((n: js.Array[T], index: Int) => n.toList))
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
  def bufferWhen[T2](closingSelector: () => Observable[T2]): Observable[List[T]] = new Observable(inner.bufferWhen(toReturnFacade(closingSelector)).map((n: js.Array[T], index: Int) => n.toList))

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
  def cache(bufferSize: Int, windowTime: Int, scheduler: Scheduler): Observable[T] = new Observable( inner.cache(bufferSize,windowTime,scheduler))
  def cache(bufferSize: Int, windowTime: Int): Observable[T] = new Observable( inner.cache(bufferSize,windowTime))
  def cache(bufferSize: Int): Observable[T] = new Observable( inner.cache(bufferSize))
  def cache(): Observable[T] = new Observable( inner.cache())

  def combineAll[U,R](implicit evidence: <:<[Observable[T], Observable[Observable[U]]]): Observable[R] = new Observable(inner.combineAll())
  def combineAll[U,T2,R](project: js.Array[T2] => R)(implicit evidence: <:<[Observable[T], Observable[Observable[U]]]): Observable[R] = new Observable(inner.combineAll(project))


  def combineLatest[T2, R](v2: Observable[T2], project: (T,T2) => R): Observable[R] = new Observable(inner.combineLatest(v2,project))
  def combineLatest[T2, R](v2: Observable[T2]): Observable[R] = new Observable(inner.combineLatest(v2))


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

  def concatMap[I, R](project: (T,Int) => Observable[I], resultSelector: (T, I, Int, Int) => R): Observable[R] = new Observable(inner.concatMap(toReturnFacade(project),resultSelector))
  def concatMap[I, R](project: (T,Int) => Observable[I]): Observable[R] = new Observable(inner.concatMap[I,R](toReturnFacade(project)))


  def concatMapTo[I, R](innerObservable: Observable[I], resultSelector: (T, I, Int, Int) => R): Observable[R] = new Observable(inner.concatMapTo(innerObservable,resultSelector))
  def concatMapTo[I, R](innerObservable: Observable[I]): Observable[R] = new Observable(inner.concatMapTo[I,R](innerObservable))

  def count(predicate: (T, Int, Observable[T]) => Boolean): Observable[Int] = new Observable( inner.count(toFacadeFunction(predicate)))

  def count(): Observable[Int] = new Observable(inner.count())


  def debounce(durationSelector:  T => Observable[Int]): Observable[T] = new Observable(inner.debounce(toReturnFacade(durationSelector)))
  def debounceTime(dueTime: Int): Observable[T] = new Observable(inner.debounceTime(dueTime))


  def defaultIfEmpty[R](defaultValue: R): Observable[R] = new Observable(inner.defaultIfEmpty(defaultValue))

  def delay(delay: Int | Date): Observable[T] = new Observable(inner.delay(delay))

  def delayWhen[U,I](delayDurationSelector: T => Observable[U], subscriptionDelay: Observable[I]): Observable[T] = new Observable(inner.delayWhen(toReturnFacade(delayDurationSelector),subscriptionDelay))
  def delayWhen[U,I](delayDurationSelector: T => Observable[U]): Observable[T] = new Observable(inner.delayWhen(toReturnFacade(delayDurationSelector)))

  def dematerialize[T2](): Observable[T2] = new Observable((inner.dematerialize()))

  def distinct[T2](compare: (T,  T) => Boolean, flushes: Observable[T2]): Observable[T] = new Observable(inner.distinct(compare,flushes))
  def distinct[T2]( flushes: Observable[T2]): Observable[T] = new Observable(inner.distinct(flushes = flushes))
  def distinct[T2](compare: (T,  T) => Boolean): Observable[T] = new Observable(inner.distinct(compare))
  def distinct[T2](): Observable[T] = new Observable(inner.distinct())

  def distinctKey[T2](key: String, compare: (T,  T) => Boolean, flushes: Observable[T2]): Observable[T] = new Observable(inner.distinctKey(key,compare,flushes))
  def distinctKey[T2](key: String, compare: (T,  T) => Boolean): Observable[T] = new Observable(inner.distinctKey(key,compare))
  def distinctKey[T2](key: String, flushes: Observable[T2]): Observable[T] = new Observable(inner.distinctKey(key,flushes = flushes))
  def distinctKey[T2](key: String): Observable[T] = new Observable(inner.distinctKey(key))

  def distinctUntilChanged[K](compare: (K,  K) => Boolean, keySelector: T => K): Observable[T] = new Observable(inner.distinctUntilChanged(compare,keySelector))
  def distinctUntilChanged[K](keySelector: T => K): Observable[T] = new Observable(inner.distinctUntilChanged(keySelector = keySelector))
  def distinctUntilChanged[K](compare: (K,  K) => Boolean): Observable[T] = new Observable(inner.distinctUntilChanged(compare))
  def distinctUntilChanged[K](): Observable[T] = new Observable(inner.distinctUntilChanged())

  def distinctUntilKeyChanged(key: String, compare: (T,  T) => Boolean): Observable[T] = new Observable(inner.distinctUntilKeyChanged(key,compare))
  def distinctUntilKeyChanged(key: String): Observable[T] = new Observable(inner.distinctUntilKeyChanged(key))

  def elementAt(index: Int, defaultValue: T): Observable[T] = new Observable(inner.elementAt(index,defaultValue))

  def every[T2](predicate: (T,  Int,  Observable[T]) => Boolean): Observable[Boolean] = new Observable(inner.every(toFacadeFunction(predicate)))

  def exhaust(): Observable[T] = new Observable(inner.exhaust())

  def exhaustMap[I, R](project: (T, Int) => Observable[R], resultSelector: (T, I, Int, Int) => R): Observable[R] = new Observable(inner.exhaustMap(toReturnFacade(project),resultSelector))
  def exhaustMap[I, R](project: (T, Int) => Observable[R]): Observable[R] = new Observable(inner.exhaustMap(toReturnFacade(project)))

  def expand[R](project: ( T, Int) => Observable[R], concurrent: Double = Double.PositiveInfinity, scheduler: Scheduler): Observable[R] =
    new Observable(inner.expand(toReturnFacade(project),concurrent,scheduler))
  def expand[R](project: ( T, Int) => Observable[R]): Observable[R] = new Observable(inner.expand(toReturnFacade(project)))


  def filter[T2](predicate: (T,  Int) => Boolean): Observable[T] = new Observable(inner.filter(predicate))


  def find[T2](predicate: (T,  Int,  Observable[T]) =>Boolean): Observable[T] = new Observable(inner.find(toFacadeFunction(predicate)))

  def findIndex[T2](predicate: (T,  Int,  Observable[T]) =>Boolean): Observable[Int] = new Observable(inner.findIndex(toFacadeFunction(predicate)))


  def first(): Observable[T] = new Observable(inner.take(1))
  def firstOrElse[R >: T](default: => R): Observable[R] = new Observable(inner.first(defaultValue = default))


  def groupBy[K,R,T2](keySelector:  T => K, elementSelector: T => R, durationSelector: GroupedObservableFacade[K, R] => ObservableFacade[T2]): Observable[GroupedObservableFacade[K, R]] =
    new Observable(inner.groupBy(keySelector,elementSelector,durationSelector))

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


  def merge[R >: T](that: Observable[R], concurrent: Double = Double.PositiveInfinity, scheduler: Scheduler): Observable[R] = new Observable(inner.merge(that,concurrent,scheduler))
  def merge[R >: T](that: Observable[R]): Observable[R] = new Observable(inner.merge(that))



  def mergeAll(concurrent: Double = Double.PositiveInfinity): Observable[T] = new Observable(inner.mergeAll[T](concurrent))

  def flatten[U]()(implicit evidence: <:<[Observable[T], Observable[Observable[U]]]): Observable[U] = new Observable(inner.mergeAll())

  def mergeMap[I, R](project: (T, Int) => Observable[I], resultSelector: (T, I, Int, Int) => R, concurrent: Double = Double.PositiveInfinity): Observable[R] =
    new Observable(inner.mergeMap(toReturnFacade(project),resultSelector,concurrent))
  def mergeMap[I, R](project: (T, Int) => Observable[I]): Observable[R] =
    new Observable(inner.mergeMap[I,R](toReturnFacade(project)))


  def mergeMapTo[I, R](innerObservable: Observable[I], resultSelector: (T, I, Int, Int) => R, concurrent: Double = Double.PositiveInfinity): Observable[R] =
    new Observable(inner.mergeMapTo(innerObservable,resultSelector,concurrent))
  def mergeMapTo[I, R](innerObservable: Observable[I]): Observable[R] =
    new Observable(inner.mergeMapTo(innerObservable))


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

  def publishLast(): ConnectableObservableFacade[T] = inner.publishLast()
  def publishReplay(bufferSize: Double = Double.PositiveInfinity, windowTime: Double = Double.PositiveInfinity): ConnectableObservableFacade[T] =
    inner.publishReplay(bufferSize,windowTime)

  def race(observables: Observable[T]*): Observable[T] = new Observable(inner.race(observables.map(_.get).toJSArray))

  def reduce[R](project: (R,T) => R,seed: R): Observable[R] = new Observable(inner.reduce(project,seed))
  def reduce[R](project: (R,T) => R): Observable[R] = new Observable(inner.reduce(project))

  def repeat(count: Int = -1): Observable[T] = new Observable(inner.repeat(count))

  def retry(count: Int = -1): Observable[T] = new Observable(inner.retry(count))

  def retryWhen[T2,T3](notifier: Observable[T2] => Observable[T3]): Observable[T] = new Observable(inner.retryWhen(toFacadeFunction(toReturnFacade(notifier))))
  def sample[I](notifier: Observable[I]): Observable[T] = new Observable(inner.sample(notifier))

  def sampleTime(delay: Int, scheduler: Scheduler): Observable[T] = new Observable(inner.sampleTime(delay,scheduler))
  def sampleTime(delay: Int): Observable[T] = new Observable(inner.sampleTime(delay))


  def scan[R](accumulator: (R, T) => R,seed: R): Observable[R] = new Observable(inner.scan(accumulator,seed))
  def scan[R](accumulator: (R, T) => R): Observable[R] = new Observable(inner.scan(accumulator))


  def share(): Observable[T] = new Observable(inner.share())

  def single(predicate: (T, Int, Observable[T]) => Boolean): Observable[T] = new Observable(inner.single(toFacadeFunction(predicate)))
  def single(): Observable[T] = new Observable(inner.single())

  def drop(total: Int): Observable[T] = skip(total)

  def dropUntil[T2](notifier: Observable[T2]): Observable[T] = skipUntil(notifier)

  def dropWhile(predicate: (T,Int) => Boolean): Observable[T] = skipWhile(predicate)

  def skip(total: Int): Observable[T] = new Observable(inner.skip(total))

  def skipUntil[T2](notifier: Observable[T2]): Observable[T] = new Observable(inner.skipUntil(notifier))

  def skipWhile(predicate: (T,Int) => Boolean): Observable[T] = new Observable(inner.skipWhile(predicate))

  def +:[U >: T](elem: U): Observable[U] = startWith(elem)

  def startWith[U >: T](v1: U, scheduler: Scheduler): Observable[U] = new Observable[U](inner.startWith(v1,scheduler))
  def startWith[U >: T](v1: U): Observable[U] = new Observable[U](inner.startWith(v1))

  def switch[U](implicit evidence: <:<[Observable[T], Observable[Observable[U]]]): Observable[U] = new Observable[U](inner.switch().asInstanceOf[ObservableFacade[U]])

  def switchMap[I, R](project: (T, Int) => Observable[I]): Observable[R] = new Observable(inner.switchMap(toReturnFacade(project)))

  def switchMapTo[I, R](innerObservable: Observable[I]): Observable[R] = new Observable(inner.switchMapTo(innerObservable))

  def take(total: Int): Observable[T] = new Observable(inner.take(total))

  def takeLast(total: Int): Observable[T] = new Observable(inner.takeLast(total))

  def takeUntil[T2](notifier: Observable[T2]): Observable[T] = new Observable(inner.takeUntil(notifier))

  def takeWhile(predicate: (T, Int) => Boolean): Observable[T] = new Observable(inner.takeWhile(predicate))

  def throttle(durationSelector: T =>  Subscribable[Int]): Observable[T] = new Observable(inner.throttle(durationSelector))

  def throttleTime(delay: Int, scheduler: Scheduler): Observable[T] = new Observable(inner.throttleTime(delay,scheduler))
  def throttleTime(delay: Int): Observable[T] = new Observable(inner.throttleTime(delay))


  def timeInterval(): Observable[TimeInterval[T]] = new Observable(inner.timeInterval())

  def timeout[T2](due: Int, errorToSend: T2, scheduler: Scheduler ): Observable[T] = new Observable(inner.timeout(due,errorToSend,scheduler))
  def timeout[T2](due: Int, errorToSend: T2): Observable[T] = new Observable(inner.timeout(due,errorToSend))
  def timeout[T2](due: Int): Observable[T] = new Observable(inner.timeout(due))
  def timeout[T2](due: Int, scheduler: Scheduler ): Observable[T] = new Observable(inner.timeout(due,scheduler = scheduler))

  def timeoutWith[R](due: Int, withObservable: Observable[R]): Observable[R] = new Observable(inner.timeoutWith(due,withObservable))

  def timestamp(): Observable[Timestamp[T]] = new Observable(inner.timestamp())

  def toSeq(): Observable[scala.collection.Seq[T]] = new Observable(inner.toArray().map((arr: js.Array[T], index: Int) => arr.toSeq))


  def window[I](windowBoundaries: Observable[I]): Observable[Observable[T]] = new Observable(inner.window(windowBoundaries).map((o: ObservableFacade[T], n: Int) => new Observable(o)))

  def windowCount(windowSize: Int, startWindowEvery: Int = 0): Observable[Observable[T]] =
    new Observable(inner.windowCount(windowSize,startWindowEvery).map((o: ObservableFacade[T], n: Int) => new Observable(o)))

  def windowTime(windowTimeSpan: Int, windowCreationInterval: Int, scheduler: Scheduler): Observable[Observable[T]] =
    new Observable(inner.windowTime(windowTimeSpan,windowCreationInterval,scheduler).map((o: ObservableFacade[T], n: Int) => new Observable(o)))
  def windowTime(windowTimeSpan: Int, windowCreationInterval: Int): Observable[Observable[T]] =
    new Observable(inner.windowTime(windowTimeSpan,windowCreationInterval).map((o: ObservableFacade[T], n: Int) => new Observable(o)))
  def windowTime(windowTimeSpan: Int, scheduler: Scheduler): Observable[Observable[T]] =
    new Observable(inner.windowTime(windowTimeSpan,scheduler = scheduler).map((o: ObservableFacade[T], n: Int) => new Observable(o)))
  def windowTime(windowTimeSpan: Int): Observable[Observable[T]] =
    new Observable(inner.windowTime(windowTimeSpan).map((o: ObservableFacade[T], n: Int) => new Observable(o)))

  def windowToggle[T2,O](openings: Observable[O], closingSelector: O => ObservableFacade[T2]): Observable[Observable[T]] =
    new Observable(inner.windowToggle(openings,closingSelector).map((o: ObservableFacade[T], n: Int) => new Observable(o)))

  def windowWhen[T2](closingSelector: () => ObservableFacade[T2]): Observable[Observable[T]] =
    new Observable(inner.windowWhen(closingSelector).map((o: ObservableFacade[T], n: Int) => new Observable(o)))

  def withLatestFrom[T2, R](v2: Observable[T2], project: (T, T2) =>  R): Observable[R] = new Observable(inner.withLatestFrom(v2,project))
  def withLatestFrom[T2, R](v2: Observable[T2]): Observable[R] = new Observable(inner.withLatestFrom(v2))

  def zip[T2, R](v2: Observable[T2], project: (T,T2) => R): Observable[R] = new Observable(inner.zip(v2,project))
  def zip[T2, R](v2: Observable[T2]): Observable[R] = new Observable(inner.zip(v2))

  def subscribe(onNext: js.Function1[T,Unit], error: js.Function1[js.Any,Unit] = null, complete: js.Function0[Unit] = null): AnonymousSubscription = inner.subscribe(onNext,error,complete)

  private def get = inner

  implicit def toFacade[A](observable: Observable[A]): ObservableFacade[A] = observable.get

  private def toFacadeFunction[U,I](func: Observable[I] => U): ObservableFacade[I] => U = (facade) => func(new Observable(facade))
  private def toFacadeFunction[U,U2,I,R](func: (U, U2, Observable[I]) => R): (U, U2, ObservableFacade[I]) => R = (arg, arg2, obs) => func(arg,arg2,new Observable(obs))

  private def toReturnFacade[I](func: () => Observable[I]): () => ObservableFacade[I] = () => func()
  private def toReturnFacade[U,I](func: U => Observable[I]): U => ObservableFacade[I] = (arg) => func(arg)
  private def toReturnFacade[U,U2,I](func: (U,U2) => Observable[I]): (U,U2) => ObservableFacade[I] = (arg,arg2) => func(arg,arg2)




}

object Observable {
  def apply[T](values: T*): Observable[T] = new Observable(ObservableFacade.of[T](values: _*))

  def ajax[T](request: String): Observable[T] = new Observable[T](ObservableFacade.ajax(request))

  def bindCallback[T,T2](callbackFunc: js.Function, selector: js.Function, scheduler: Scheduler): js.Function1[T2, ObservableFacade[T]] =
    ObservableFacade.bindCallback(callbackFunc,selector,scheduler)

  def bindNodeCallback[T,T2](callbackFunc: js.Function, selector: js.Function, scheduler: Scheduler): js.Function1[T2, ObservableFacade[T]]  =
    ObservableFacade.bindNodeCallback(callbackFunc,selector,scheduler)



  def combineLatest[T,R] (sources: Seq[ObservableFacade[T]])(combineFunction: Seq[T] => R): Observable[R] = {
    val func = combineFunction.asInstanceOf[js.Array[T] => R]
    _combineLatest(sources.toJSArray,func)
  }


  private def _combineLatest[T, R](sources: js.Array[ObservableFacade[T]],combineFunction: js.Array[T] => R): Observable[R] =
    new Observable(ObservableFacade.combineLatest(sources,combineFunction))


  def concat[T, R](observables: Seq[ObservableFacade[T]], scheduler: Scheduler): Observable[R] = _concat(observables.toJSArray,scheduler)
  def concat[T, R](observables: Seq[ObservableFacade[T]]): Observable[R] = _concat(observables.toJSArray)

  private def _concat[T, R](observables: js.Array[ObservableFacade[T]], scheduler: Scheduler): Observable[R] =
    new Observable[R](ObservableFacade.concat(observables,scheduler))
  private def _concat[T, R](observables: js.Array[ObservableFacade[T]]): Observable[R] =
    new Observable[R](ObservableFacade.concat(observables))


  def interval(period: Int = 0): Observable[Int] = new Observable(ObservableFacade.interval(period))


  def merge[T, R](observables: Seq[ObservableFacade[T]], scheduler: Scheduler): Observable[R] = new Observable(ObservableFacade.merge(observables.toJSArray,scheduler))
  def merge[T, R](observables: Seq[ObservableFacade[T]]): Observable[R] = new Observable(ObservableFacade.merge(observables.toJSArray))

  def of[T](elements: T*): Observable[T] = apply(elements: _*)
  def race[T](observables: ObservableFacade[T]*): Observable[T] = new Observable(ObservableFacade.race(observables: _*))

  def range(start: Int = 0, count: Int = 0): Observable[Int] = new Observable(ObservableFacade.range(start,count))
  def timer(initialDelay: Int = 0, period: Int = -1): Observable[Int] = new Observable(ObservableFacade.timer(initialDelay,period))


  def zip[T,R](observables: Seq[ObservableFacade[T]])(project: js.Array[T] => R): Observable[R] =  {
    val func = project.asInstanceOf[js.Array[T] => R]
    new Observable(ObservableFacade.zip(observables.toJSArray,func))
  }


}
