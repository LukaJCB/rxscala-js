package rxscalajs

import rxscalajs.facade._

import scala.collection.immutable.Seq
import scala.scalajs.js
import js._
import js.JSConverters._



/**
  * Created by Luka on 29.04.2016.
  */
class Observable[T] protected(val inner: ObservableFacade[T]){

  /**
    * Ignores source values for a duration determined by another Observable, then
    * emits the most recent value from the source Observable, then repeats this
    * process.
    *
    * <span class="informal">It's like  auditTime, but the silencing
    * duration is determined by a second Observable.</span>
    *
    * <img src="http://reactivex.io/rxjs/img/audit.png"  width="640" height="315">
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
    * <img src="http://reactivex.io/rxjs/img/auditTime.png"  width="640" height="315">
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
    * <img src="http://reactivex.io/rxjs/img/buffer.png"  width="640" height="315">
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
    * <img src="http://reactivex.io/rxjs/img/bufferCount.png"  width="640" height="315">
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
    * <img src="http://reactivex.io/rxjs/img/bufferTime.png"  width="640" height="315">
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
    * <img src="http://reactivex.io/rxjs/img/bufferToggle.png"  width="640" height="315">
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
  def bufferToggle[T2,O](openings: Observable[O], closingSelector:  O => Observable[T2]): Observable[List[T]] =
    new Observable(inner.bufferToggle(openings,toReturnFacade(closingSelector)).map((n: js.Array[T], index: Int) => n.toList))
  /**
    * Buffers the source Observable values, using a factory function of closing
    * Observables to determine when to close, emit, and reset the buffer.
    *
    * <span class="informal">Collects values from the past as an array. When it
    * starts collecting values, it calls a function that returns an Observable that
    * tells when to close the buffer and restart collecting.</span>
    *
    * <img src="http://reactivex.io/rxjs/img/bufferWhen.png"  width="640" height="315">
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

  def combineAll[U](implicit evidence: <:<[Observable[T], Observable[Observable[U]]]): Observable[Seq[U]] =
    new Observable(inner.asInstanceOf[ObservableFacade[Observable[U]]].map((n: Observable[U]) => n.get).combineAll())

  def combineAll[U,R](project: js.Array[U] => R)(implicit evidence: <:<[Observable[T], Observable[Observable[U]]]): Observable[R] =
    new Observable(inner.asInstanceOf[ObservableFacade[Observable[U]]].map((n: Observable[U]) => n.get).combineAll(project))

 
    /**
    * Combines two observables, emitting a pair of the latest values of each of
    * the source observables each time an event is received from one of the source observables.
    *
    * @param that
    *            The second source observable.
    * @param selector
    *            The function that is used combine the emissions of the two observables.
    * @return An Observable that combines the source Observables according to the function selector.
    */
  def combineLatestWith[U, R](that: Observable[U])(selector: (T, U) => R): Observable[R] = new Observable(inner.combineLatest(that,selector))
   
    /**
    * Combines two observables, emitting a pair of the latest values of each of
    * the source observables each time an event is received from one of the source observables.
    *
    * @param that
    *            The second source observable.
    * @return An Observable that combines the source Observables
    */
  def combineLatest[U](that: Observable[U]): Observable[(T,U)] = combineLatestWith(that)((t,u) => (t,u))


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

  def concatAll[U](implicit evidence: <:<[Observable[T], Observable[Observable[U]]]): Observable[U] =
    new Observable(inner.asInstanceOf[ObservableFacade[Observable[U]]].map((n: Observable[U]) => n.get).concatAll())


  /**
  * Returns a new Observable that emits items resulting from applying a function that you supply to each item
  * emitted by the source Observable, where that function returns an Observable, and then emitting the items
  * that result from concatinating those resulting Observables.
  *
  * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/concatMap.png" alt="" />
  *
  * @param project a function that, when applied to an item emitted by the source Observable, returns an Observable
  * @return an Observable that emits the result of applying the transformation function to each item emitted
  *         by the source Observable and concatinating the Observables obtained from this transformation
  */
  def concatMap[I, R](project: (T,Int) => Observable[I], resultSelector: (T, I, Int, Int) => R): Observable[R] = new Observable(inner.concatMap(toReturnFacade(project),resultSelector))
  def concatMap[I, R](project: (T,Int) => Observable[I]): Observable[R] = new Observable(inner.concatMap[I,R](toReturnFacade(project)))


  def concatMapTo[I, R](innerObservable: Observable[I], resultSelector: (T, I, Int, Int) => R): Observable[R] = new Observable(inner.concatMapTo(innerObservable,resultSelector))
  def concatMapTo[I, R](innerObservable: Observable[I]): Observable[R] = new Observable(inner.concatMapTo[I,R](innerObservable))

  /**
    * Return an [[Observable]] which emits the number of elements in the source [[Observable]] which satisfy a predicate.
    *
    * @param predicate the predicate used to test elements.
    * @return an [[Observable]] which emits the number of elements in the source [[Observable]] which satisfy a predicate.
    */
  def count(predicate: (T, Int, Observable[T]) => Boolean): Observable[Int] = new Observable( inner.count(toFacadeFunction(predicate)))

  def count(): Observable[Int] = new Observable(inner.count())

  /**
    * Return an Observable that mirrors the source Observable, except that it drops items emitted by the source
    * Observable that are followed by another item within a computed debounce duration.
    *
    * <img width="640" height="425" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/debounce.f.png" alt="" />
    *
    * @param debounceSelector function to retrieve a sequence that indicates the throttle duration for each item
    * @return an Observable that omits items emitted by the source Observable that are followed by another item
    *         within a computed debounce duration
    */
  def debounce(debounceSelector:  T => Observable[Int]): Observable[T] = new Observable(inner.debounce(toReturnFacade(debounceSelector)))
  /**
    * Debounces by dropping all values that are followed by newer values before the timeout value expires. The timer resets on each `onNext` call.
    *
    * NOTE: If events keep firing faster than the timeout then no data will be emitted.
    *
    * <img width="640" height="310" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/debounce.png" alt="" />
    *
    * $debounceVsThrottle
    *
    * @param timeout
    *            The time each value has to be 'the most recent' of the Observable to ensure that it's not dropped.
    *
    * @return An Observable which filters out values which are too quickly followed up with newer values.
    * @see `Observable.throttleWithTimeout`
    */
  def debounceTime(timeout: Int): Observable[T] = new Observable(inner.debounceTime(timeout))


  def defaultIfEmpty[R](defaultValue: R): Observable[R] = new Observable(inner.defaultIfEmpty(defaultValue))

  /**
    * Returns an Observable that emits the items emitted by the source Observable shifted forward in time by a
    * specified delay. Error notifications from the source Observable are not delayed.
    *
    * <img width="640" height="310" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/delay.png" alt="" />
    *
    * @param delay the delay to shift the source by
    * @return the source Observable shifted in time by the specified delay
    */
  def delay(delay: Int | Date): Observable[T] = new Observable(inner.delay(delay))

  /**
    * Returns an Observable that delays the emissions of the source Observable via another Observable on a
    * per-item basis.
    * <p>
    * <img width="640" height="450" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/delay.o.png" alt="" />
    * <p>
    * Note: the resulting Observable will immediately propagate any `onError` notification
    * from the source Observable.
    *
    * @param delayDurationSelector a function that returns an Observable for each item emitted by the source Observable, which is
    *                  then used to delay the emission of that item by the resulting Observable until the Observable
    *                  returned from `itemDelay` emits an item
    * @return an Observable that delays the emissions of the source Observable via another Observable on a per-item basis
    */
  def delayWhen[U,I](delayDurationSelector: T => Observable[U], subscriptionDelay: Observable[I]): Observable[T] = new Observable(inner.delayWhen(toReturnFacade(delayDurationSelector),subscriptionDelay))
  def delayWhen[U,I](delayDurationSelector: T => Observable[U]): Observable[T] = new Observable(inner.delayWhen(toReturnFacade(delayDurationSelector)))

  /**
    * Returns an Observable that reverses the effect of [[rxscalajs.Observable.materialize]] by
    * transforming the [[rxscalajs.Notification]] objects emitted by the source Observable into the items
    * or notifications they represent.
    *
    * This operation is only available if `this` is of type `Observable[Notification[U]]` for some `U`,
    * otherwise you will get a compilation error.
    *
    * <img width="640" height="335" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/dematerialize.png" alt="" />
    *
    * @return an Observable that emits the items and notifications embedded in the [[rxscalajs.Notification]] objects emitted by the source Observable
    *
    * @usecase def dematerialize[U]: Observable[U]
    *   @inheritdoc
    *
    */
  def dematerialize[T2]: Observable[T2] = new Observable(inner.dematerialize())

  /**
    * Returns an Observable that forwards all items emitted from the source Observable that are distinct according
    * to a key selector function.
    *
    * <img width="640" height="310" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/distinct.key.png" alt="" />
    *
    * @param compare
    *            a function that projects an emitted item to a key value which is used for deciding whether an item is
    *            distinct from another one or not
    * @return an Observable of distinct items
    */
  def distinct[T2](compare: (T,  T) => Boolean, flushes: Observable[T2]): Observable[T] = new Observable(inner.distinct(compare,flushes))
  def distinct[T2]( flushes: Observable[T2]): Observable[T] = new Observable(inner.distinct(flushes = flushes))
  def distinct[T2](compare: (T,  T) => Boolean): Observable[T] = new Observable(inner.distinct(compare))
  def distinct[T2](): Observable[T] = new Observable(inner.distinct())

  def distinctKey[T2](key: String, compare: (T,  T) => Boolean, flushes: Observable[T2]): Observable[T] = new Observable(inner.distinctKey(key,compare,flushes))
  def distinctKey[T2](key: String, compare: (T,  T) => Boolean): Observable[T] = new Observable(inner.distinctKey(key,compare))
  def distinctKey[T2](key: String, flushes: Observable[T2]): Observable[T] = new Observable(inner.distinctKey(key,flushes = flushes))
  def distinctKey[T2](key: String): Observable[T] = new Observable(inner.distinctKey(key))


  /**
  * Returns an Observable that forwards all items emitted from the source Observable that are sequentially
    * distinct according to a key selector function.
  *
  * <img width="640" height="310" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/distinctUntilChanged.key.png" alt="" />
  *
  * @param keySelector
    *            a function that projects an emitted item to a key value which is used for deciding whether an item is sequentially
    *            distinct from another one or not
  * @return an Observable of sequentially distinct items
    */
  def distinctUntilChanged[K](compare: (K,  K) => Boolean, keySelector: T => K): Observable[T] = new Observable(inner.distinctUntilChanged(compare,keySelector))
  def distinctUntilChanged(compare: (T,  T) => Boolean): Observable[T] = new Observable(inner.distinctUntilChanged(compare))
  def distinctUntilChanged: Observable[T] = new Observable(inner.distinctUntilChanged())

  def distinctUntilKeyChanged(key: String, compare: (T,  T) => Boolean): Observable[T] = new Observable(inner.distinctUntilKeyChanged(key,compare))
  def distinctUntilKeyChanged(key: String): Observable[T] = new Observable(inner.distinctUntilKeyChanged(key))

  /**
    * Returns an Observable that emits the single item at a specified index in a sequence of emissions from a
    * source Observbable.
    *
    * <img width="640" height="310" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/elementAt.png" alt="" />
    *
    * @param index
    *            the zero-based index of the item to retrieve
    * @return an Observable that emits a single item: the item at the specified position in the sequence of
    *         those emitted by the source Observable
    * @throws java.lang.IndexOutOfBoundsException
    *             if index is greater than or equal to the number of items emitted by the source
    *             Observable, or index is less than 0
    */
  def elementAt(index: Int, defaultValue: T): Observable[T] = new Observable(inner.elementAt(index,defaultValue))

  def every[T2](predicate: (T,  Int,  Observable[T]) => Boolean): Observable[Boolean] = new Observable(inner.every(toFacadeFunction(predicate)))

  def exhaust[U]()(implicit evidence: <:<[Observable[T], Observable[Observable[U]]]): Observable[U] =
    new Observable[U](inner.asInstanceOf[ObservableFacade[Observable[U]]].map((n: Observable[U]) => n.get).exhaust())

  def exhaustMap[I, R](project: (T, Int) => Observable[R], resultSelector: (T, I, Int, Int) => R): Observable[R] = new Observable(inner.exhaustMap(toReturnFacade(project),resultSelector))
  def exhaustMap[I, R](project: (T, Int) => Observable[R]): Observable[R] = new Observable(inner.exhaustMap(toReturnFacade(project)))

  def expand[R](project: ( T, Int) => Observable[R], concurrent: Double = Double.PositiveInfinity, scheduler: Scheduler): Observable[R] =
    new Observable(inner.expand(toReturnFacade(project),concurrent,scheduler))
  def expand[R](project: ( T, Int) => Observable[R]): Observable[R] = new Observable(inner.expand(toReturnFacade(project)))

  /**
    * Returns an Observable which only emits those items for which a given predicate holds.
    *
    * <img width="640" height="310" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/filter.png" alt="" />
    *
    * @param predicate
    *            a function that evaluates the items emitted by the source Observable, returning `true` if they pass the filter
    * @return an Observable that emits only those items in the original Observable that the filter
    *         evaluates as `true`
    */
  def filter[T2](predicate: (T,  Int) => Boolean): Observable[T] = new Observable(inner.filter(predicate))


  def find[T2](predicate: (T,  Int,  Observable[T]) =>Boolean): Observable[T] = new Observable(inner.find(toFacadeFunction(predicate)))

  def findIndex[T2](predicate: (T,  Int,  Observable[T]) =>Boolean): Observable[Int] = new Observable(inner.findIndex(toFacadeFunction(predicate)))

  /**
    * Returns an Observable that emits only the very first item emitted by the source Observable, or raises an
    * `NoSuchElementException` if the source Observable is empty.
    * <p>
    * <img width="640" height="310" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/first.png" alt="" />
    *
    * @return an Observable that emits only the very first item emitted by the source Observable, or raises an
    *         `NoSuchElementException` if the source Observable is empty
    * @see <a href="https://github.com/ReactiveX/RxJava/wiki/Filtering-Observables#wiki-first">RxJava Wiki: first()</a>
    * @see "MSDN: Observable.firstAsync()"
    */
  def first: Observable[T] = new Observable(inner.take(1))
  /**
    * Returns an Observable that emits only the very first item emitted by the source Observable, or
    * a default value if the source Observable is empty.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/firstOrDefault.png" alt="" />
    *
    * @param default
    *            The default value to emit if the source Observable doesn't emit anything.
    *            This is a by-name parameter, so it is only evaluated if the source Observable doesn't emit anything.
    * @return an Observable that emits only the very first item from the source, or a default value
    *         if the source Observable completes without emitting any item.
    */
  def firstOrElse[R >: T](default: => R): Observable[R] = new Observable(inner.first(defaultValue = default))


  def groupBy[K,R](keySelector:  T => K, valueSelector: T => R): Observable[(K, Observable[R])] = {
    val outerFacade: ObservableFacade[GroupedObservableFacade[K,R]] = inner.groupBy(keySelector,valueSelector)
    new Observable(outerFacade.map((groupFacade: GroupedObservableFacade[K,R]) => (groupFacade.key, new Observable(groupFacade))))
  }
  def groupBy[K](keySelector:  T => K): Observable[(K, Observable[T])] = {
    val outerFacade: ObservableFacade[GroupedObservableFacade[K,T]] = inner.groupBy(keySelector)
    new Observable(outerFacade.map((groupFacade: GroupedObservableFacade[K,T]) => (groupFacade.key, new Observable(groupFacade))))
  }

  def ignoreElements(): Observable[T] = new Observable(inner.ignoreElements())

  /** Tests whether this `Observable` emits no elements.
    *
    *  @return        an Observable emitting one single Boolean, which is `true` if this `Observable`
    *                 emits no elements, and `false` otherwise.
    */
  def isEmpty: Observable[Boolean] = new Observable(inner.isEmpty())

  /**
    * Returns an Observable that emits the last item emitted by the source Observable or notifies observers of
    * an `NoSuchElementException` if the source Observable is empty.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/last.png" alt="" />
    *
    * @return an Observable that emits the last item from the source Observable or notifies observers of an
    *         error
    * @see <a href="https://github.com/ReactiveX/RxJava/wiki/Filtering-Observable-Operators#wiki-last">RxJava Wiki: last()</a>
    * @see "MSDN: Observable.lastAsync()"
    */
  def last: Observable[T] = new Observable(inner.last[T]())
  /**
    * Returns an Observable that emits only the last item emitted by the source Observable, or a default item
    * if the source Observable completes without emitting any items.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/lastOrDefault.png" alt="" />
    *
    * @param default the default item to emit if the source Observable is empty.
    *                This is a by-name parameter, so it is only evaluated if the source Observable doesn't emit anything.
    * @return an Observable that emits only the last item emitted by the source Observable, or a default item
    *         if the source Observable is empty
    */
  def lastOrElse[R >: T](default: => R): Observable[R] = new Observable(inner.last(defaultValue = default))

  def let[R](func:  Observable[T] => Observable[R]): Observable[R] = new Observable(inner.let(toReturnFacade(toFacadeFunction(func))))

  /**
    * Returns an Observable that applies the given function to each item emitted by an
    * Observable and emits the result.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/map.png" alt="" />
    *
    * @param project
    *            a function to apply to each item emitted by the Observable
    * @return an Observable that emits the items from the source Observable, transformed by the
    *         given function
    */
  def map[R](project: (T,Int) => R): Observable[R] = new Observable[R](inner.map(project))
  def map[R](project: T => R): Observable[R] = new Observable[R](inner.map(project))

  def mapTo[R](value: R): Observable[R] = new Observable(inner.mapTo(value))

  /**
    * Turns all of the notifications from a source Observable into onNext  emissions,
    * and marks them with their original notification types within [[rxscalajs.Notification]] objects.
    *
    * <img width="640" height="315" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/materialize.png" alt="" />
    *
    * @return an Observable whose items are the result of materializing the items and
    *         notifications of the source Observable
    */
  def materialize(): Observable[Notification[T]] = new Observable(inner.materialize())


  def max(comparer: (T,T) => T): Observable[T] = new Observable(inner.max(comparer))
  def max(): Observable[T] = new Observable(inner.max())

  /**
    * Flattens two Observables into one Observable, without any transformation.
    *
    * <img width="640" height="380" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/merge.png" alt="" />
    *
    * You can combine items emitted by two Observables so that they act like a single
    * Observable by using the `merge` method.
    *
    * @param that
    *            an Observable to be merged
    * @return an Observable that emits items from `this` and `that` until
    *            `this` or `that` emits `onError` or both Observables emit `onCompleted`.
    */
  def merge[R >: T](that: Observable[R], concurrent: Double = Double.PositiveInfinity, scheduler: Scheduler): Observable[R] = new Observable(inner.merge(that,concurrent,scheduler))
  def merge[R >: T](that: Observable[R]): Observable[R] = new Observable(inner.merge(that))



  def mergeAll[U](concurrent: Double = Double.PositiveInfinity)(implicit evidence: <:<[Observable[T], Observable[Observable[U]]]): Observable[U] =
    new Observable(inner.asInstanceOf[ObservableFacade[Observable[U]]].map((n: Observable[U]) => n.get).mergeAll(concurrent))

  def flatten[U](implicit evidence: <:<[Observable[T], Observable[Observable[U]]]): Observable[U] = new Observable(inner.mergeAll())

  def mergeMap[I, R](project: (T, Int) => Observable[I], resultSelector: (T, I, Int, Int) => R, concurrent: Double = Double.PositiveInfinity): Observable[R] =
    new Observable(inner.mergeMap(toReturnFacade(project),resultSelector,concurrent))
  def mergeMap[I, R](project: (T, Int) => Observable[I]): Observable[R] =
    new Observable(inner.mergeMap[I,R](toReturnFacade(project)))


  def mergeMapTo[I, R](innerObservable: Observable[I], resultSelector: (T, I, Int, Int) => R, concurrent: Double = Double.PositiveInfinity): Observable[R] =
    new Observable(inner.mergeMapTo(innerObservable,resultSelector,concurrent))
  def mergeMapTo[I, R](innerObservable: Observable[I]): Observable[R] =
    new Observable(inner.mergeMapTo(innerObservable))


  def min(comparer: (T,T) => T): Observable[T] = new Observable(inner.min(comparer))
  def min: Observable[T] = new Observable(inner.min())

  def multicast(subjectOrSubjectFactory: () => SubjectFacade[T]): Observable[T] = new Observable(inner.multicast(subjectOrSubjectFactory))

  def pairwis: Observable[(T,T)] = new Observable[(T, T)](inner.pairwise().map((arr: js.Array[T], index: Int) => (arr(0), arr(1))))

  def partition[T2](predicate: T => Boolean): (Observable[T], Observable[T]) = {
    val partitioned = inner.partition(predicate)
    (new Observable(partitioned(0)),new Observable(partitioned(1)))
  }

  /**
    * Returns a ConnectableObservable, which waits until the `connect` function is called
    * before it begins emitting items from `this` [Observable to those Observers that
    * have subscribed to it.
    *
    * <img width="640" height="510" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/publishConnect.png" alt="" />
    *
    * @return an ConnectableObservable
    */
  def publish: Observable[T] = new Observable(inner.publish())

  def publishBehavior(value: T): Observable[T] = new Observable(inner.publishBehavior(value))

  def publishLast: Observable[T] = new Observable(inner.publishLast())
  def publishReplay(bufferSize: Double = Double.PositiveInfinity, windowTime: Double = Double.PositiveInfinity): Observable[T] =
    new Observable(inner.publishReplay(bufferSize,windowTime))

  def race(observables: Observable[T]*): Observable[T] = new Observable(inner.race(observables.map(_.get).toJSArray))
  /**
    * Returns an Observable that applies a function of your choosing to the first item emitted by a
    * source Observable, then feeds the result of that function along with the second item emitted
    * by the source Observable into the same function, and so on until all items have been emitted
    * by the source Observable, and emits the final result from the final call to your function as
    * its sole item.
    *
    * <img width="640" height="320" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/reduce.png" alt="" />
    *
    * This technique, which is called "reduce" or "aggregate" here, is sometimes called "fold,"
    * "accumulate," "compress," or "inject" in other programming contexts. Groovy, for instance,
    * has an `inject` method that does a similar operation on lists.
    *
    * @param accumulator
    *            An accumulator function to be invoked on each item emitted by the source
    *            Observable, whose result will be used in the next accumulator call
    * @return an Observable that emits a single item that is the result of accumulating the
    *         output from the source Observable
    */
  def reduce[R](accumulator: (R,T) => R,seed: R): Observable[R] = new Observable(inner.reduce(accumulator,seed))
  def reduce[R](accumulator: (R,T) => R): Observable[R] = new Observable(inner.reduce(accumulator))

  /**
    * Returns an Observable that repeats the sequence of items emitted by the source Observable at most `count` times.
    * <p>
    * <img width="640" height="310" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/repeat.on.png" alt="" />
    *
    * @param count the number of times the source Observable items are repeated,
    *              a count of 0 will yield an empty sequence
    * @return an Observable that repeats the sequence of items emitted by the source Observable at most `count` times
    * @throws java.lang.IllegalArgumentException if `count` is less than zero
    * @see <a href="https://github.com/ReactiveX/RxJava/wiki/Creating-Observables#wiki-repeat">RxJava Wiki: repeat()</a>
    * @see <a href="http://msdn.microsoft.com/en-us/library/hh229428.aspx">MSDN: Observable.Repeat</a>
    */
  def repeat(count: Int = -1): Observable[T] = new Observable(inner.repeat(count))

  /**
    * Retry subscription to origin Observable upto given retry count.
    *
    * <img width="640" height="315" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/retry.png" alt="" />
    *
    * If Observer.onError is invoked the source Observable will be re-subscribed to as many times as defined by retryCount.
    *
    * Any Observer.onNext calls received on each attempt will be emitted and concatenated together.
    *
    * For example, if an Observable fails on first time but emits [1, 2] then succeeds the second time and
    * emits [1, 2, 3, 4, 5] then the complete output would be [1, 2, 1, 2, 3, 4, 5, onCompleted].
    *
    * @param count
    *            Number of retry attempts before failing.
    * @return Observable with retry logic.
    */
  def retry(count: Int = -1): Observable[T] = new Observable(inner.retry(count))
  /**
    * Returns an Observable that emits the same values as the source observable with the exception of an
    * `onError`. An `onError` notification from the source will result in the emission of a
    * `Throwable` to the Observable provided as an argument to the `notificationHandler`
    *  function. If the Observable returned `onCompletes` or `onErrors` then `retry` will call
    * `onCompleted` or `onError` on the child subscription. Otherwise, this Observable will
    * resubscribe to the source Observable.
    * <p>
    * <img width="640" height="430" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/retryWhen.f.png" alt="" />
    *
    * Example:
    *
    * This retries 3 times, each time incrementing the number of seconds it waits.
    *
    * @example
    *
    * This retries 3 times, each time incrementing the number of seconds it waits.
    *
    * {{{
    * Observable[String]({ subscriber =>
    *   println("subscribing")
    *   subscriber.onError(new RuntimeException("always fails"))
    * }).retryWhen({ throwableObservable =>
    *   throwableObservable.zipWith(Observable.from(1 to 3))((t, i) => i).flatMap(i => {
    *     println("delay retry by " + i + " second(s)")
    *     Observable.timer(Duration(i, TimeUnit.SECONDS))
    *   })
    * }).toBlocking.foreach(s => println(s))
    * }}}
    *
    * Output is:
    *
    * {{{
    * subscribing
    * delay retry by 1 second(s)
    * subscribing
    * delay retry by 2 second(s)
    * subscribing
    * delay retry by 3 second(s)
    * subscribing
    * }}}
    *
    * <dl>
    *  <dt><b>Scheduler:</b></dt>
    *  <dd>`retryWhen` operates by default on the `trampoline` [[Scheduler]].</dd>
    * </dl>
    *
    * @param notifier receives an Observable of a Throwable with which a user can complete or error, aborting the
    *            retry
    * @return the source Observable modified with retry logic
    * @see <a href="https://github.com/ReactiveX/RxJava/wiki/Error-Handling-Operators#retrywhen">RxJava Wiki: retryWhen()</a>
    * @see RxScalaDemo.retryWhenDifferentExceptionsExample for a more intricate example
    * @since 0.20
    */
  def retryWhen[U,S](notifier: Observable[U] => Observable[S]): Observable[T] = new Observable(inner.retryWhen(toFacadeFunction(toReturnFacade(notifier))))
  /**
    * Return an Observable that emits the results of sampling the items emitted by the source Observable
    * whenever the specified sampler Observable emits an item or completes.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/sample.o.png" alt="" />
    *
    * @param sampler
    *            the Observable to use for sampling the source Observable
    * @return an Observable that emits the results of sampling the items emitted by this Observable whenever
    *         the sampler Observable emits an item or completes
    */
  def sample[I](sampler: Observable[I]): Observable[T] = new Observable(inner.sample(sampler))
  /**
    * Returns an Observable that emits the results of sampling the items emitted by the source
    * Observable at a specified time interval.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/sample.png" alt="" />
    *
    * @param delay the sampling rate
    * @param scheduler
    *            the [[rxscalajs.Scheduler]] to use when sampling
    * @return an Observable that emits the results of sampling the items emitted by the source
    *         Observable at the specified time interval
    */
  def sampleTime(delay: Int, scheduler: Scheduler): Observable[T] = new Observable(inner.sampleTime(delay,scheduler))
  def sampleTime(delay: Int): Observable[T] = new Observable(inner.sampleTime(delay))

  /**
  * Returns an Observable that applies a function of your choosing to the first item emitted by a
    * source Observable, then feeds the result of that function along with the second item emitted
    * by an Observable into the same function, and so on until all items have been emitted by the
  * source Observable, emitting the result of each of these iterations.
  *
  * <img width="640" height="320" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/scanSeed.png" alt="" />
  *
  * This sort of function is sometimes called an accumulator.
  *
  * Note that when you pass a seed to `scan()` the resulting Observable will emit
  * that seed as its first emitted item.
  *
  * @param seed
    *            the initial (seed) accumulator value
  * @param accumulator
    *            an accumulator function to be invoked on each item emitted by the source
    *            Observable, whose result will be emitted to [[rxscalajs.Observer]]s via
    *            onNext and used in the next accumulator call.
    * @return an Observable that emits the results of each call to the accumulator function
  */
  def scan[R](accumulator: (R, T) => R,seed: R): Observable[R] = new Observable(inner.scan(accumulator,seed))
  def scan[R](accumulator: (R, T) => R): Observable[R] = new Observable(inner.scan(accumulator))

  /**
    * Returns a new [[Observable]] that multicasts (shares) the original [[Observable]]. As long a
    * there is more than 1 [[Subscriber]], this [[Observable]] will be subscribed and emitting data.
    * When all subscribers have unsubscribed it will unsubscribe from the source [[Observable]].
    *
    * This is an alias for `publish().refCount()`
    *
    * <img width="640" height="510" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/publishRefCount.png" alt="" />
    *
    * @return a [[Observable]] that upon connection causes the source Observable to emit items to its [[Subscriber]]s
    * @since 0.19
    */
  def share: Observable[T] = new Observable(inner.share())
  /**
    * If the source Observable completes after emitting a single item, return an Observable that emits that
    * item. If the source Observable emits more than one item or no items, notify of an `IllegalArgumentException`
    * or `NoSuchElementException` respectively.
    *
    * <img width="640" height="315" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/single.png" alt="" />
    *
    * @return an Observable that emits the single item emitted by the source Observable
    * @throws java.lang.IllegalArgumentException if the source emits more than one item
    * @throws java.util.NoSuchElementException if the source emits no items
    * @see <a href="https://github.com/ReactiveX/RxJava/wiki/Observable-Utility-Operators#wiki-single-and-singleordefault">RxJava Wiki: single()</a>
    * @see "MSDN: Observable.singleAsync()"
    */
  def single(predicate: (T, Int, Observable[T]) => Boolean): Observable[T] = new Observable(inner.single(toFacadeFunction(predicate)))
  def single: Observable[T] = new Observable(inner.single())
  /**
    * Returns an Observable that skips the first `num` items emitted by the source
    * Observable and emits the remainder.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/skip.png" alt="" />
    *
    * @param total
    *            the number of items to skip
    * @return an Observable that is identical to the source Observable except that it does not
    *         emit the first `num` items that the source emits
    */
  def drop(total: Int): Observable[T] = skip(total)
  /**
    * Returns an Observable that skips items emitted by the source Observable until a second Observable emits an item.
    * <p>
    * <img width="640" height="375" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/skipUntil.png" alt="" />
    *
    * @param notifier the second Observable that has to emit an item before the source Observable's elements begin
    *              to be mirrored by the resulting Observable
    * @return an Observable that skips items from the source Observable until the second Observable emits an
    *         item, then emits the remaining items
    * @see <a href="https://github.com/ReactiveX/RxJava/wiki/Filtering-Observables#wiki-skipuntil">RxJava Wiki: skipUntil()</a>
    * @see <a href="http://msdn.microsoft.com/en-us/library/hh229358.aspx">MSDN: Observable.SkipUntil</a>
    */
  def dropUntil[U](notifier: Observable[U]): Observable[T] = skipUntil(notifier)

  /**
    * Returns an Observable that bypasses all items from the source Observable as long as the specified
    * condition holds true. Emits all further source items as soon as the condition becomes false.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/skipWhile.png" alt="" />
    *
    * @param predicate
    *            A function to test each item emitted from the source Observable for a condition.
    * @return an Observable that emits all items from the source Observable as soon as the condition
    *         becomes false.
    */
  def dropWhile(predicate: (T,Int) => Boolean): Observable[T] = skipWhile(predicate)
  /**
    * Returns an Observable that skips the first `num` items emitted by the source
    * Observable and emits the remainder.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/skip.png" alt="" />
    *
    * @param total
    *            the number of items to skip
    * @return an Observable that is identical to the source Observable except that it does not
    *         emit the first `num` items that the source emits
    */
  def skip(total: Int): Observable[T] = new Observable(inner.skip(total))
  /**
    * Returns an Observable that skips items emitted by the source Observable until a second Observable emits an item.
    * <p>
    * <img width="640" height="375" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/skipUntil.png" alt="" />
    *
    * @param notifier the second Observable that has to emit an item before the source Observable's elements begin
    *              to be mirrored by the resulting Observable
    * @return an Observable that skips items from the source Observable until the second Observable emits an
    *         item, then emits the remaining items
    * @see <a href="https://github.com/ReactiveX/RxJava/wiki/Filtering-Observables#wiki-skipuntil">RxJava Wiki: skipUntil()</a>
    * @see <a href="http://msdn.microsoft.com/en-us/library/hh229358.aspx">MSDN: Observable.SkipUntil</a>
    */
  def skipUntil[U](notifier: Observable[U]): Observable[T] = new Observable(inner.skipUntil(notifier))
  /**
    * Returns an Observable that bypasses all items from the source Observable as long as the specified
    * condition holds true. Emits all further source items as soon as the condition becomes false.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/skipWhile.png" alt="" />
    *
    * @param predicate
    *            A function to test each item emitted from the source Observable for a condition.
    * @return an Observable that emits all items from the source Observable as soon as the condition
    *         becomes false.
    */
  def skipWhile(predicate: (T,Int) => Boolean): Observable[T] = new Observable(inner.skipWhile(predicate))
  /**
    * Returns an Observable that emits a specified item before it begins to emit items emitted by the source Observable.
    * <p>
    * <img width="640" height="315" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/startWith.png" alt="" />
    *
    * @param elem the item to emit
    * @return an Observable that emits the specified item before it begins to emit items emitted by the source Observable
    */
  def +:[U >: T](elem: U): Observable[U] = startWith(elem)
  /**
    * Returns an Observable that emits a specified item before it begins to emit items emitted by the source Observable.
    * <p>
    * <img width="640" height="315" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/startWith.png" alt="" />
    *
    * @param elem the item to emit
    * @return an Observable that emits the specified item before it begins to emit items emitted by the source Observable
    */
  def startWith[U >: T](elem: U, scheduler: Scheduler): Observable[U] = new Observable[U](inner.startWith(elem,scheduler))
  def startWith[U >: T](elem: U): Observable[U] = new Observable[U](inner.startWith(elem))
  /**
    * Given an Observable that emits Observables, creates a single Observable that
    * emits the items emitted by the most recently published of those Observables.
    *
    * <img width="640" height="370" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/switchDo.png" alt="" />
    *
    * This operation is only available if `this` is of type `Observable[Observable[U]]` for some `U`,
    * otherwise you'll get a compilation error.
    *
    * @return an Observable that emits only the items emitted by the most recently published
    *         Observable
    *
    * @usecase def switch[U]: Observable[U]
    *   @inheritdoc
    */
  def switch[U](implicit evidence: <:<[Observable[T], Observable[Observable[U]]]): Observable[U] = new Observable[U](inner.switch().asInstanceOf[ObservableFacade[U]])

  /**
    * Returns a new Observable by applying a function that you supply to each item emitted by the source
    * Observable that returns an Observable, and then emitting the items emitted by the most recently emitted
    * of these Observables.
    *
    * <img width="640" height="350" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/switchMap.png" alt="" />
    *
    * @param project a function that, when applied to an item emitted by the source Observable, returns an Observable
    * @return an Observable that emits the items emitted by the Observable returned from applying a function to
    *         the most recently emitted item emitted by the source Observable
    */
  def switchMap[I, R](project: (T, Int) => Observable[I]): Observable[R] = new Observable(inner.switchMap(toReturnFacade(project)))
  def switchMap[I, R](project: T => Observable[I]): Observable[R] = new Observable(inner.switchMap(toReturnFacade(project)))

  def switchMapTo[I, R](innerObservable: Observable[I]): Observable[R] = new Observable(inner.switchMapTo(innerObservable))

  def flatMap[I,R](project: T => Observable[I]): Observable[R] = switchMap(project)


  /**
    * Returns an Observable that emits only the first `num` items emitted by the source
    * Observable.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/take.png" alt="" />
    *
    * This method returns an Observable that will invoke a subscribing [[rxscalajs.Observer]]'s
    * onNext function a maximum of `num` times before invoking
    * onCompleted.
    *
    * @param total
    *            the number of items to take
    * @return an Observable that emits only the first `num` items from the source
    *         Observable, or all of the items from the source Observable if that Observable emits
    *         fewer than `num` items
    */
  def take(total: Int): Observable[T] = new Observable(inner.take(total))
  /**
    * Returns an Observable that emits only the last `count` items emitted by the source
    * Observable.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/last.png" alt="" />
    *
    * @param total
    *            the number of items to emit from the end of the sequence emitted by the source
    *            Observable
    * @return an Observable that emits only the last `count` items emitted by the source
    *         Observable
    */
  def takeLast(total: Int): Observable[T] = new Observable(inner.takeLast(total))


  def takeUntil[U](notifier: Observable[U]): Observable[T] = new Observable(inner.takeUntil(notifier))
  /**
    * Returns an Observable that emits items emitted by the source Observable so long as a
    * specified condition is true.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/takeWhile.png" alt="" />
    *
    * @param predicate
    *            a function that evaluates an item emitted by the source Observable and returns a
    *            Boolean
    * @return an Observable that emits the items from the source Observable so long as each item
    *         satisfies the condition defined by `predicate`
    */
  def takeWhile(predicate: (T, Int) => Boolean): Observable[T] = new Observable(inner.takeWhile(predicate))

  def throttle(durationSelector: T => Observable[Int]): Observable[T] = new Observable(inner.throttle(toReturnFacade(durationSelector)))
  /**
    * Debounces by dropping all values that are followed by newer values before the timeout value expires. The timer resets on each `onNext` call.
    *
    * NOTE: If events keep firing faster than the timeout then no data will be emitted.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/throttleWithTimeout.png" alt="" />
    *
    * @param delay
    *            The time each value has to be 'the most recent' of the [[rxscalajs.Observable]] to ensure that it's not dropped.
    * @param scheduler
    *            The [[rxscalajs.Scheduler]] to use internally to manage the timers which handle timeout for each event.
    * @return Observable which performs the throttle operation.
    * @see `Observable.debounce`
    */
  def throttleTime(delay: Int, scheduler: Scheduler): Observable[T] = new Observable(inner.throttleTime(delay,scheduler))
  def throttleTime(delay: Int): Observable[T] = new Observable(inner.throttleTime(delay))

  /**
    * Returns an Observable that emits records of the time interval between consecutive items emitted by the
    * source Observable.
    * <p>
    * <img width="640" height="310" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/timeInterval.png" alt="" />
    *
    * @return an Observable that emits time interval information items
    */
  def timeInterval: Observable[TimeInterval[T]] = new Observable(inner.timeInterval())
  /**
    * Applies a timeout policy for each item emitted by the Observable, using
    * the specified scheduler to run timeout timers. If the next item isn't
    * observed within the specified timeout duration starting from its
    * predecessor, a specified fallback Observable sequence produces future
    * items and notifications from that point on.
    * <p>
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/timeout.2s.png" alt="" />
    *
    * @param due maximum duration between items before a timeout occurs
    * @param errorToSend the error to send.
    * @param scheduler Scheduler to run the timeout timers on
    * @return the source Observable modified so that it will switch to the
    *         fallback Observable in case of a timeout
    */
  def timeout[U](due: Int, errorToSend: U, scheduler: Scheduler ): Observable[T] = new Observable(inner.timeout(due,errorToSend,scheduler))
  def timeout[U](due: Int, errorToSend: U): Observable[T] = new Observable(inner.timeout(due,errorToSend))
  def timeout[U](due: Int): Observable[T] = new Observable(inner.timeout(due))
  def timeout[U](due: Int, scheduler: Scheduler ): Observable[T] = new Observable(inner.timeout(due,scheduler = scheduler))

  def timeoutWith[R](due: Int, withObservable: Observable[R]): Observable[R] = new Observable(inner.timeoutWith(due,withObservable))
  /**
    * Wraps each item emitted by a source Observable in a timestamped tuple.
    *
    * <img width="640" height="310" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/timestamp.png" alt="" />
    *
    * @return an Observable that emits timestamped items from the source Observable
    */
  def timestamp: Observable[Timestamp[T]] = new Observable(inner.timestamp())
  /**
    * Returns an Observable that emits a single item, a list composed of all the items emitted by
    * the source Observable.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/toList.png" alt="" />
    *
    * Normally, an Observable that returns multiple items will do so by invoking its [[rxscalajs.Observer]]'s
    * onNext method for each such item. You can change
    * this behavior, instructing the Observable to compose a list of all of these items and then to
    * invoke the Observer's `onNext` function once, passing it the entire list, by
    * calling the Observable's `toList` method prior to calling its `Observable.subscribe` method.
    *
    * Be careful not to use this operator on Observables that emit infinite or very large numbers
    * of items, as you do not have the option to unsubscribe.
    *
    * @return an Observable that emits a single item: a List containing all of the items emitted by
    *         the source Observable.
    */
  def toSeq: Observable[scala.collection.Seq[T]] = new Observable(inner.toArray().map((arr: js.Array[T], index: Int) => arr.toSeq))


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

  def windowToggle[U,O](openings: Observable[O], closingSelector: O => Observable[U]): Observable[Observable[T]] =
    new Observable(inner.windowToggle(openings,toReturnFacade(closingSelector)).map((o: ObservableFacade[T], n: Int) => new Observable(o)))

  def windowWhen[U](closingSelector: () => Observable[U]): Observable[Observable[T]] =
    new Observable(inner.windowWhen(toReturnFacade(closingSelector)).map((o: ObservableFacade[T], n: Int) => new Observable(o)))
  /**
    * $experimental Merges the specified [[Observable]] into this [[Observable]] sequence by using the `resultSelector`
    * function only when the source [[Observable]] (this instance) emits an item.
    *
    * <img width="640" height="380" src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/withLatestFrom.png" alt="">
    *
    * $noDefaultScheduler
    *
    * @param other the other [[Observable]]
    * @param project the function to call when this [[Observable]] emits an item and the other [[Observable]] has already
    *                       emitted an item, to generate the item to be emitted by the resulting [[Observable]]
    * @return an [[Observable]] that merges the specified [[Observable]] into this [[Observable]] by using the
    *         `resultSelector` function only when the source [[Observable]] sequence (this instance) emits an item
    * @see <a href="http://reactivex.io/documentation/operators/combinelatest.html">ReactiveX operators documentation: CombineLatest</a>
    * @since (if this graduates from Experimental/Beta to supported, replace this parenthetical with the release number)
    */
  def withLatestFrom[U, R](other: Observable[U], project: (T, U) =>  R): Observable[R] = new Observable(inner.withLatestFrom(other,project))
  def withLatestFrom[U, R](other: Observable[U]): Observable[R] = new Observable(inner.withLatestFrom(other))

  /**
    * Returns an Observable that emits items that are the result of applying a specified function to pairs of
    * values, one each from the source Observable and a specified Iterable sequence.
    * <p>
    * <img width="640" height="380" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/zip.i.png" alt="" />
    * <p>
    * Note that the `other` Iterable is evaluated as items are observed from the source Observable; it is
    * not pre-consumed. This allows you to zip infinite streams on either side.
    *
    * @param that the Iterable sequence
    * @param project a function that combines the pairs of items from the Observable and the Iterable to generate
    *                 the items to be emitted by the resulting Observable
    * @return an Observable that pairs up values from the source Observable and the `other` Iterable
    *         sequence and emits the results of `selector` applied to these pairs
    */
  def zip[U, R](that: Observable[U], project: (T,U) => R): Observable[R] = new Observable(inner.zip(that,project))
  /**
    * Returns an Observable formed from this Observable and another Observable by combining
    * corresponding elements in pairs.
    * The number of `onNext` invocations of the resulting `Observable[(T, U)]`
    * is the minumum of the number of `onNext` invocations of `this` and `that`.
    *
    * @param that the Observable to zip with
    * @return an Observable that pairs up values from `this` and `that` Observables.
    */
  def zip[U](that: Observable[U]): Observable[(T,U)] = new Observable(inner.zip(that,(a: T, b: U) => (a,b)))


  /**
    * $subscribeSubscriberMain
    *
    * $noDefaultScheduler
    *
    * @return $subscribeAllReturn
    * @see <a href="http://reactivex.io/documentation/operators/subscribe.html">ReactiveX operators documentation: Subscribe</a>
    */
  def subscribe(onNext: T => Unit, error: js.Function1[js.Any,Unit] = null, complete: js.Function0[Unit] = null): AnonymousSubscription = inner.subscribe(onNext,error,complete)

  private def get = inner

  implicit def toFacade[A](observable: Observable[A]): ObservableFacade[A] = observable.get

  private def toFacadeFunction[U,I](func: Observable[I] => U): ObservableFacade[I] => U = (facade) => func(new Observable(facade))
  private def toFacadeFunction[U,U2,I,R](func: (U, U2, Observable[I]) => R): (U, U2, ObservableFacade[I]) => R = (arg, arg2, obs) => func(arg,arg2,new Observable(obs))

  private def toReturnFacade[I](func: () => Observable[I]): () => ObservableFacade[I] = () => func()
  private def toReturnFacade[U,I](func: U => Observable[I]): U => ObservableFacade[I] = (arg) => func(arg)
  private def toReturnFacade[U,U2,I](func: (U,U2) => Observable[I]): (U,U2) => ObservableFacade[I] = (arg,arg2) => func(arg,arg2)



}

object Observable {
  /**
    * Converts a sequence of values into an Observable.
    *
    * <img width="640" height="315" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/from.png" alt="" />
    *
    * Implementation note: the entire array will be immediately emitted each time an [[rxscalajs.Observer]] subscribes.
    * Since this occurs before the [[rxscalajs.Subscription]] is returned,
    * it in not possible to unsubscribe from the sequence before it completes.
    *
    * @param values
    *            the source Array
    * @tparam T
    *            the type of items in the Array, and the type of items to be emitted by the
    *            resulting Observable
    * @return an Observable that emits each item in the source Array
    */
  def apply[T](values: T*): Observable[T] = new Observable(ObservableFacade.of[T](values: _*))

  def ajax[T](request: String): Observable[T] = new Observable[T](ObservableFacade.ajax(request))

  def bindCallback[T,U](callbackFunc: js.Function, selector: js.Function, scheduler: Scheduler): js.Function1[U, ObservableFacade[T]] =
    ObservableFacade.bindCallback(callbackFunc,selector,scheduler)

  def bindNodeCallback[T,U](callbackFunc: js.Function, selector: js.Function, scheduler: Scheduler): js.Function1[U, ObservableFacade[T]]  =
    ObservableFacade.bindNodeCallback(callbackFunc,selector,scheduler)


  /**
    * Combines a list of source Observables by emitting an item that aggregates the latest values of each of
    * the source Observables each time an item is received from any of the source Observables, where this
    * aggregation is defined by a specified function.
    *
    * @tparam T the common base type of source values
    * @tparam R the result type
    * @param sources the list of source Observables
    * @param combineFunction the aggregation function used to combine the items emitted by the source Observables
    * @return an Observable that emits items that are the result of combining the items emitted by the source
    *         Observables by means of the given aggregation function
    */
  def combineLatest[T,R] (sources: Seq[Observable[T]])(combineFunction: Seq[T] => R): Observable[R] = {
    val func = combineFunction.asInstanceOf[js.Array[T] => R]
    _combineLatest(sources.map(_.inner).toJSArray,func)
  }


  private def _combineLatest[T, R](sources: js.Array[ObservableFacade[T]],combineFunction: js.Array[T] => R): Observable[R] =
    new Observable(ObservableFacade.combineLatest(sources,combineFunction))


  /**
    * Emits `0`, `1`, `2`, `...` with a delay of `duration` between consecutive numbers.
    *
    * <img width="640" height="195" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/interval.png" alt="" />
    *
    * @param duration
    *            duration between two consecutive numbers
    * @return An Observable that emits a number each time interval.
    */
  def interval(duration: Int = 0): Observable[Int] = new Observable(ObservableFacade.interval(duration))

  /**
    * Flattens Observables into one Observable, without any transformation.
    *
    * <img width="640" height="380" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/merge.png" alt="" />
    *
    * You can combine items emitted by two Observables so that they act like a single
    * Observable by using the `merge` method.
    *
    * @param observables
    *            Observables to be merged
    * @return an Observable that emits items from `this` and `that` until
    *            `this` or `that` emits `onError` or both Observables emit `onCompleted`.
    */
  def merge[T, R](observables: Seq[ObservableFacade[T]], scheduler: Scheduler): Observable[R] = new Observable(ObservableFacade.merge(observables.toJSArray,scheduler))
  def merge[T, R](observables: Seq[ObservableFacade[T]]): Observable[R] = new Observable(ObservableFacade.merge(observables.toJSArray))

  def of[T](elements: T*): Observable[T] = apply(elements: _*)
  def race[T](observables: ObservableFacade[T]*): Observable[T] = new Observable(ObservableFacade.race(observables: _*))

  /**
    * Returns an Observable that emits `0L` after a specified delay, and then completes.
    *
    * <img width="640" height="200" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/timer.png" alt="" />
    *
    * @param initialDelay the initial delay before emitting a single `0L`
    * @return Observable that emits `0L` after a specified delay, and then completes
    */
  def timer(initialDelay: Int = 0, period: Int = -1): Observable[Int] = new Observable(ObservableFacade.timer(initialDelay,period))
  
  def range(start: Int = 0, count: Int = 0): Observable[Int] = new Observable(ObservableFacade.range(start,count))


  def zip[T,R](observables: Seq[Observable[T]])(project: Seq[T] => R): Observable[R] =  {
    val func = project.asInstanceOf[js.Array[T] => R]
    new Observable(ObservableFacade.zip(observables.map(_.inner).toJSArray,func))
  }


}
