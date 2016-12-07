package rxscalajs

import org.scalajs.dom.Element
import org.scalajs.dom.raw.Event
import rxscalajs.facade._
import rxscalajs.subscription._

import scala.collection.immutable.Seq
import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import scala.scalajs.js
import scala.scalajs.js.JSConverters._
import scala.scalajs.js._
import scala.util.{Failure, Success}



/**
  * The Observable interface that implements the Reactive Pattern.
  *
  * @define subscribeObserverMain
  * Call this method to subscribe an [[ObserverFacade]] for receiving
  * items and notifications from the Observable.
  *
  * A typical implementation of `subscribe` does the following:
  *
  * It stores a reference to the Observer in a collection object, such as a `List[T]` object.
  *
  * It returns a reference to the [[Subscription]] interface. This enables Observers to
  * unsubscribe, that is, to stop receiving items and notifications before the Observable stops
  * sending them, which also invokes the Observer's onCompleted method.
  *
  * An `Observable[T]` instance is responsible for accepting all subscriptions
  * and notifying all Observers. Unless the documentation for a particular
  * `Observable[T]` implementation indicates otherwise, Observers should make no
  * assumptions about the order in which multiple Observers will receive their notifications.
  * @define subscribeObserverParamObserver
  * the observer
  * @define subscribeObserverParamScheduler
  * the [[rxscalajs.Scheduler]] on which Observers subscribe to the Observable
  * @define subscribeSubscriberParamObserver
  * the [[rxscalajs.subscription.Subscriber]]
  * @define subscribeSubscriberParamScheduler
  * the [[rxscalajs.Scheduler]] on which [[rxscalajs.subscription.Subscriber]]s subscribe to the Observable
  * @define subscribeAllReturn
  * a [[rxscalajs.subscription.Subscription]] reference whose `unsubscribe` method can be called to  stop receiving items
  * before the Observable has finished sending them
  * @define subscribeCallbacksMainWithNotifications
  * Call this method to receive items and notifications from this observable.
  * @define subscribeCallbacksMainNoNotifications
  * Call this method to receive items from this observable.
  * @define subscribeCallbacksParamOnNext
  * this function will be called whenever the Observable emits an item
  * @define subscribeCallbacksParamOnError
  * this function will be called if an error occurs
  * @define subscribeCallbacksParamOnComplete
  * this function will be called when this Observable has finished emitting items
  * @define subscribeCallbacksParamScheduler
  * the scheduler to use
  * @define noDefaultScheduler
  * ===Scheduler:===
  * This method does not operate by default on a particular [[Scheduler]].
  * @define supportBackpressure
  * ===Backpressure:===
  * Fully supports backpressure.
  * @define debounceVsThrottle
  * Information on debounce vs throttle:
  * - [[http://drupalmotion.com/article/debounce-and-throttle-visual-explanation]]
  * - [[http://unscriptable.com/2009/03/20/debouncing-javascript-methods/]]
  * - [[http://www.illyriad.co.uk/blog/index.php/2011/09/javascript-dont-spam-your-server-debounce-and-throttle/]]
  *
  *
  *
  */

class Observable[+T] protected[rxscalajs](val inner: ObservableFacade[T]) {

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
    *          {{{ val result = clickStream.audit(ev => Observable.interval(1000))
    *                     result.subscribe(x => println(x)) }}}
    *
    * @param durationSelector A function
    * that receives a value from the source Observable, for computing the silencing
    * duration, returned as an Observable or a Promise.
    *
    * @return {Observable[T]} An Observable that performs rate-limiting of
    *         emissions from the source Observable.
    */
  def audit[I](durationSelector: T => Observable[I]): Observable[T] = {
    new Observable(inner
      .audit(toReturnFacade(durationSelector)))
  }

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
    *          {{{ val clicks = Observable.fromEvent(document, "click")
    *                     val result = clicks.auditTime(1000)
    *                     result.subscribe(x => println(x))  }}}
    *
    * @param delay Time to wait before emitting the most recent source
    * value, measured in milliseconds or the time unit determined internally
    * by the optional `scheduler`.
    * @param scheduler The Scheduler to use for
    * managing the timers that handle the rate-limiting behavior.
    *
    * @return An Observable that performs rate-limiting of
    *         emissions from the source Observable.
    */
  def auditTime(delay: Int, scheduler: Scheduler): Observable[T] = {
    new Observable(inner.auditTime(delay, scheduler))
  }
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
    *          {{{ val clicks = Observable.fromEvent(document, "click")
    *                     val result = clicks.auditTime(1000)
    *                     result.subscribe(x => println(x)) }}}
    *
    * @param delay Time to wait before emitting the most recent source
    * value, measured in milliseconds or the time unit determined internally
    * by the optional `scheduler`.
    *
    * @return An Observable that performs rate-limiting of
    *         emissions from the source Observable.
    */
  def auditTime(delay: Int): Observable[T] = {
    new Observable(inner.auditTime(delay))
  }

  /**
    * Creates an Observable which produces buffers of collected values.
    *
    * Buffers the incoming Observable values until the given `closingNotifier`
    * Observable emits a value, at which point it emits the buffer on the output
    * Observable and starts a new buffer internally, awaiting the next time
    * `closingNotifier` emits.
    *
    * <img src="http://reactivex.io/documentation/operators/images/buffer1.png"  width="640" height="315">
    *
    * @param closingNotifier
    * An Observable that signals the buffer to be emitted on the output Observable.
    *
    * @return
    * An Observable of buffers, which are arrays of values.
    **/
  def buffer[T2](closingNotifier: Observable[T2]): Observable[List[T]] = {
    new Observable(inner.buffer(closingNotifier)
      .map((n: js.Array[_ <: T]) => n.toList))
  }

  /**
    * Creates an Observable which produces windows of collected values. This Observable produces windows every
    * `skip` values, each containing `count` elements. When the source Observable completes or encounters an error,
    * the current window is emitted and the event is propagated.
    *
    * <img src="http://reactivex.io/documentation/operators/images/bufferWithCount4.png"  width="640" height="315">
    *
    * @param count
    * The maximum size of each window before it should be emitted.
    * @param skip
    * How many produced values need to be skipped before starting a new window. Note that when `skip` and
    * `count` are equal that this is the same operation as `window(int)`.
    *
    * @return
    * An [[rxscalajs.Observable]] which produces windows every `skip` values containing at most
    * `bufferSize` produced values.
    */

  def bufferCount(count: Int, skip: Int): Observable[List[T]] = {
    new Observable(inner.bufferCount(count, skip)
      .map((n: js.Array[_ <: T]) => n.toList))
  }

  /**
    * Creates an Observable which produces buffers of collected values.
    *
    * This Observable produces connected non-overlapping buffers, each containing `count`
    * elements. When the source Observable completes or encounters an error, the current
    * buffer is emitted, and the event is propagated.
    *
    *
    * <img src="http://reactivex.io/documentation/operators/images/bufferWithCount3.png"  width="640" height="315">
    *
    * @param count
    * The maximum size of each buffer before it should be emitted.
    *
    * @return
    * An [[rxscalajs.Observable]] which produces connected non-overlapping buffers containing at most
    * `count` produced values.
    */
  def bufferCount(count: Int): Observable[List[T]] = {
    new Observable(inner.bufferCount(count)
      .map((n: js.Array[_ <: T]) => n.toList))
  }

  /**
    * Creates an Observable which produces buffers of collected values. This Observable starts a new buffer
    * periodically, which is determined by the `timeshift` argument. Each buffer is emitted after a fixed timespan
    * specified by the `timespan` argument. When the source Observable completes or encounters an error, the
    * current buffer is emitted and the event is propagated.
    *
    * @param bufferTimeSpan
    * The period of time each buffer is collecting values before it should be emitted.
    * @param bufferCreationInterval
    * The period of time after which a new buffer will be created.
    * @param scheduler
    * The [[rxscalajs.Scheduler]] to use when determining the end and start of a buffer.
    *
    * @return
    * An [[rxscalajs.Observable]] which produces new buffers periodically, and these are emitted after
    * a fixed timespan has elapsed.
    */
  def bufferTime(
    bufferTimeSpan: FiniteDuration,
    bufferCreationInterval: FiniteDuration,
    scheduler: Scheduler
  ): Observable[List[T]] = {
    new Observable(inner.bufferTime(bufferTimeSpan.toMillis.toInt, bufferCreationInterval.toMillis.toInt, scheduler)
      .map((n: js.Array[_ <: T]) => n.toList))
  }

  /**
    * Creates an Observable which produces buffers of collected values. This Observable starts a new buffer
    * periodically, which is determined by the `timeshift` argument. Each buffer is emitted after a fixed timespan
    * specified by the `timespan` argument. When the source Observable completes or encounters an error, the
    * current buffer is emitted and the event is propagated.
    *
    * @param bufferTimeSpan
    * The period of time each buffer is collecting values before it should be emitted.
    * @param bufferCreationInterval
    * The period of time after which a new buffer will be created.
    *
    * @return
    * An [[rxscalajs.Observable]] which produces new buffers periodically, and these are emitted after
    * a fixed timespan has elapsed.
    */
  def bufferTime(bufferTimeSpan: FiniteDuration, bufferCreationInterval: FiniteDuration): Observable[List[T]] = {
    new Observable(inner
      .bufferTime(bufferTimeSpan.toMillis.toInt, bufferCreationInterval.toMillis.toInt).map((n: js.Array[_ <: T]) => n.toList))
  }

  /**
    * Creates an Observable which produces buffers of collected values.
    *
    * This Observable produces connected non-overlapping buffers, each of a fixed duration
    * specified by the `timespan` argument. When the source Observable completes or encounters
    * an error, the current buffer is emitted and the event is propagated.
    *
    * @param bufferTimeSpan
    * The period of time each buffer is collecting values before it should be emitted, and
    * replaced with a new buffer.
    *
    * @return
    * An [[rxscalajs.Observable]] which produces connected non-overlapping buffers with a fixed duration.
    */
  def bufferTime(bufferTimeSpan: FiniteDuration): Observable[List[T]] = {
    new Observable(inner.bufferTime(bufferTimeSpan.toMillis.toInt)
      .map((n: js.Array[_ <: T]) => n.toList))
  }
  /**
    * Buffers the source Observable values starting from an emission from
    * `openings` and ending when the output of `closingSelector` emits.
    *
    * <span class="informal">Collects values from the past as an array. Starts
    * collecting only when `opening` emits, and calls the `closingSelector`
    * function to get an Observable that tells when to close the buffer.</span>
    *
    * <img src="http://reactivex.io/documentation/operators/images/buffer2.png"  width="640" height="315">
    *
    * Buffers values from the source by opening the buffer via signals from an
    * Observable provided to `openings`, and closing and sending the buffers when
    * a Subscribable or Promise returned by the `closingSelector` function emits.
    *
    * @param  openings An Observable or Promise of notifications to start new
    * buffers.
    * @param closingSelector A function that takes
    * the value emitted by the `openings` observable and returns a Subscribable or Promise,
    * which, when it emits, signals that the associated buffer should be emitted
    * and cleared.
    *
    * @return An observable of arrays of buffered values.
    */
  def bufferToggle[T2, O](openings: Observable[O])(closingSelector: O => Observable[T2]): Observable[List[T]] = {
    new Observable(inner.bufferToggle(openings, toReturnFacade(closingSelector))
      .map((n: js.Array[_ <: T]) => n.toList))
  }
  /**
    * Buffers the source Observable values, using a factory function of closing
    * Observables to determine when to close, emit, and reset the buffer.
    *
    * <span class="informal">Collects values from the past as an array. When it
    * starts collecting values, it calls a function that returns an Observable that
    * tells when to close the buffer and restart collecting.</span>
    *
    * <img src="http://reactivex.io/documentation/operators/images/buffer1.png"  width="640" height="315">
    *
    * Opens a buffer immediately, then closes the buffer when the observable
    * returned by calling `closingSelector` function emits a value. When it closes
    * the buffer, it immediately opens a new buffer and repeats the process.
    *
    * @example <caption>Emit an array of the last clicks every [1-5] random seconds</caption>
    *          {{{ val clicks = Observable.fromEvent(document, "click")
    *                     val buffered = clicks.bufferWhen(() =>
    *                       Observable.interval(1000 + Math.random() * 4000)
    *                     )
    *                     buffered.subscribe(x => println(x)) }}}
    *
    * @param  closingSelector A function that takes no
    * arguments and returns an Observable that signals buffer closure.
    *
    * @return An observable of arrays of buffered values.
    */
  def bufferWhen[T2](closingSelector: () => Observable[T2]): Observable[List[T]] = {
    new Observable(inner
      .bufferWhen(toReturnFacade(closingSelector)).map((n: js.Array[_ <: T]) => n.toList))
  }

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
    * @param bufferSize the buffer size that limits the number of items that can be replayed
    * @param windowTime the duration of the window in which the replayed items must have been emitted
    * @param scheduler the scheduler that is used as a time source for the window
    *
    * @return an Observable that when first subscribed to, caches all of its notifications for
    *         the benefit of subsequent subscribers.
    */
  def cache(bufferSize: Int, windowTime: FiniteDuration, scheduler: Scheduler): Observable[T] = {
    new Observable(inner
      .cache(bufferSize, windowTime.toMillis.toInt, scheduler))
  }
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
    * @param bufferSize the buffer size that limits the number of items that can be replayed
    * @param windowTime the duration of the window in which the replayed items must have been emitted
    *
    * @return an Observable that when first subscribed to, caches all of its notifications for
    *         the benefit of subsequent subscribers.
    */
  def cache(bufferSize: Int, windowTime: FiniteDuration): Observable[T] = {
    new Observable(inner.cache(bufferSize, windowTime.toMillis.toInt))
  }
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
    * @param bufferSize the buffer size that limits the number of items that can be replayed
    *
    * @return an Observable that when first subscribed to, caches all of its notifications for
    *         the benefit of subsequent subscribers.
    */
  def cache(bufferSize: Int): Observable[T] = {
    new Observable(inner.cache(bufferSize))
  }
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
  def cache: Observable[T] = {
    new Observable(inner.cache())
  }

  /**
    * Continues an observable sequence that is terminated by an exception with the next observable sequence.
    *
    * <img width="640" height="310" src="http://reactivex.io/documentation/operators/images/catch.js.png" alt="" />
    *
    * @param resumeFunction
    * Exception handler function that returns an observable sequence given the error that occurred in the first sequence
    *
    * @return An observable sequence containing the first sequence's elements, followed by the elements of the handler sequence in case an exception occurred.
    */
  def catchError[U >: T](resumeFunction: (js.Any) => Observable[U]): Observable[U] = {
    new Observable(inner.`catch`(toReturnFacade(resumeFunction)))
  }

  /**
    * Scala API
    * Same as map, but with PartialFunction, where observable emits elements only for defined values
    *
    * @param partialProject
    * Partial function that will be applied to elements for which it is defined
    *
    * @return An observable sequence containing the first sequence elements projected by `partialProject` for which it was defined.
    */
  def collect[B](partialProject: PartialFunction[T, B]): Observable[B] = Observable.create[B] { obs =>
    val internal = subscribe((x: T) => if (partialProject.isDefinedAt(x)) obs.next(partialProject(x)) else (),
      e => obs.error(e),
      () => obs.complete)
    () => internal.unsubscribe()
  }

  /**
    * Instruct an Observable to pass control to another Observable rather than invoking `onError` if it encounters an error.
    *
    * <img width="640" height="310" src="http://reactivex.io/documentation/operators/images/onErrorResumeNext.png" alt="" />
    *
    * By default, when an Observable encounters an error that prevents it from emitting the
    * expected item to its Observer, the Observable invokes its Observer's
    * `onError` method, and then quits without invoking any more of its Observer's
    * methods. The `onErrorResumeNext` method changes this behavior. If you pass a
    * function that returns an Observable (`resumeFunction`) to
    * `onErrorResumeNext`, if the original Observable encounters an error, instead of
    * invoking its Observer's `onError` method, it will instead relinquish control to
    * the Observable returned from `resumeFunction`, which will invoke the Observer's
    * `onNext` method if it is able to do so. In such a case, because no
    * Observable necessarily invokes `onError`, the Observer may never know that an
    * error happened.
    *
    * You can use this to prevent errors from propagating or to supply fallback data should errors
    * be encountered.
    *
    * @param resumeFunction
    * a function that returns an Observable that will take over if the source Observable
    * encounters an error
    *
    * @return the original Observable, with appropriately modified behavior
    */
  def onErrorResumeNext[U >: T](resumeFunction: (js.Any) => Observable[U]): Observable[U] =
    this.catchError(resumeFunction)


  /**
    * Instruct an Observable to emit an item (returned by a specified function) rather than
    * invoking `onError` if it encounters an error.
    *
    * <img width="640" height="310" src="http://reactivex.io/documentation/operators/images/onErrorReturn.png" alt="" />
    *
    * By default, when an Observable encounters an error that prevents it from emitting the
    * expected item to its `Observer`, the Observable invokes its Observer's
    * `onError` method, and then quits without invoking any more of its Observer's
    * methods. The `onErrorReturn` method changes this behavior. If you pass a function
    * (`resumeFunction`) to an Observable's `onErrorReturn` method, if the
    * original Observable encounters an error, instead of invoking its Observer's
    * `onError` method, it will instead pass the return value of
    * `resumeFunction` to the Observer's `onNext` method.
    *
    * You can use this to prevent errors from propagating or to supply fallback data should errors
    * be encountered.
    *
    * @param resumeFunction
    * a function that returns an item that the new Observable will emit if the source
    * Observable encounters an error
    *
    * @return the original Observable with appropriately modified behavior
    */
  def onErrorReturn[U >: T](resumeFunction: (js.Any) => U): Observable[U] = {
    val toFacade = (any: js.Any) => ObservableFacade.of(resumeFunction(any))
    new Observable(inner.onErrorResumeNext(toFacade))
  }
  /**
    * Converts a higher-order Observable into a first-order Observable by waiting
    * for the outer Observable to complete, then applying {@link combineLatest}.
    *
    * <span class="informal">Flattens an Observable-of-Observables by applying
    * {@link combineLatest} when the Observable-of-Observables completes.</span>
    *
    * <img src="http://reactivex.io/rxjs/img/combineAll.png" width="640" height="195">
    *
    * Takes an Observable of Observables, and collects all Observables from it.
    * Once the outer Observable completes, it subscribes to all collected
    * Observables and combines their values using the {@link combineLatest}
    * strategy, such that:
    * - Every time an inner Observable emits, the output Observable emits.
    * - When the returned observable emits, it emits all of the latest values by:
    *   - If a `project` function is provided, it is called with each recent value
    * from each inner Observable in whatever order they arrived, and the result
    * of the `project` function is what is emitted by the output Observable.
    *   - If there is no `project` function, an array of all of the most recent
    * values is emitted by the output Observable.
    *
    * @example <caption>Map two click events to a finite interval Observable, then apply combineAll</caption>
    *          {{{ var clicks = Observable.fromEvent(document, "click")
    *                     var higherOrder = clicks.map(ev =>
    *                       Observable.interval(Math.random()*2000).take(3)
    *                     ).take(2)
    *                     var result = higherOrder.combineAll
    *                     result.subscribe(x =>  println(x)) }}}
    * @return {Observable} An Observable of projected results or arrays of recent
    *         values.
    */
  def combineAll[U](implicit evidence: <:<[Observable[T], Observable[Observable[U]]]): Observable[Seq[U]] = {
    new Observable(inner.asInstanceOf[ObservableFacade[Observable[U]]].map((n: Observable[U]) => n.get).combineAll())
  }
  /**
    * Converts a higher-order Observable into a first-order Observable by waiting
    * for the outer Observable to complete, then applying {@link combineLatest}.
    *
    * <span class="informal">Flattens an Observable-of-Observables by applying
    * {@link combineLatest} when the Observable-of-Observables completes.</span>
    *
    * <img src="http://reactivex.io/rxjs/img/combineAll.png" width="640" height="195">
    *
    * Takes an Observable of Observables, and collects all Observables from it.
    * Once the outer Observable completes, it subscribes to all collected
    * Observables and combines their values using the {@link combineLatest}
    * strategy, such that:
    * - Every time an inner Observable emits, the output Observable emits.
    * - When the returned observable emits, it emits all of the latest values by:
    *   - If a `project` function is provided, it is called with each recent value
    * from each inner Observable in whatever order they arrived, and the result
    * of the `project` function is what is emitted by the output Observable.
    *   - If there is no `project` function, an array of all of the most recent
    * values is emitted by the output Observable.
    *
    * @example <caption>Map two click events to a finite interval Observable, then apply combineAll</caption>
    *          {{{ var clicks = Observable.fromEvent(document, "click")
    *                     var higherOrder = clicks.map(ev =>
    *                       Observable.interval(Math.random()*2000).take(3)
    *                     ).take(2)
    *                     var result = higherOrder.combineAll()
    *                     result.subscribe(x =>  println(x))  }}}
    *
    * @param project An optional function to map the most recent
    * values from each inner Observable into a new result. Takes each of the most
    * recent values from each collected inner Observable as arguments, in order.
    *
    * @return {Observable} An Observable of projected results or arrays of recent
    *         values.
    */
  def combineAll[U, R](project: js.Array[U] => R)
    (implicit evidence: <:<[Observable[T], Observable[Observable[U]]]): Observable[R] = {
    new Observable(inner.asInstanceOf[ObservableFacade[Observable[U]]].map((n: Observable[U]) => n.get)
      .combineAll(project))
  }

  /**
    * Combines two observables, emitting some type `R` specified in the function `selector`,
    * each time an event is received from one of the source observables, where the aggregation
    * is defined by the given function.
    *
    *
    * <img width="640" height="410" src="http://reactivex.io/documentation/operators/images/withLatestFrom.png" alt="" />
    *
    * @param that
    * The second source observable.
    * @param selector
    * The function that is used combine the emissions of the two observables.
    *
    * @return An Observable that combines the source Observables according to the function selector.
    */
  def combineLatestWith[U, R](that: Observable[U])(selector: (T, U) => R): Observable[R] = {
    new Observable(inner
      .combineLatest(that, selector))
  }

  /**
    * Combines three observables, emitting some type `R` specified in the function `selector`,
    * each time an event is received from one of the source observables, where the aggregation
    * is defined by the given function.
    *
    * <img width="640" height="410" src="http://reactivex.io/documentation/operators/images/withLatestFrom.png" alt="" />
    *
    * @param first
    * an Observable to be combined
    * @param second
    * an Observable to be combined
    * @param selector
    * The function that is used combine the emissions of the three observables.
    *
    * @return An Observable that combines the source Observables according to the function selector.
    */
  def combineLatestWith[U,V,R](first: Observable[U], second: Observable[V])
                                (selector: (T, U, V) => R): Observable[R] = {
    combineLatest(first)
      .combineLatestWith(second)((tu, v) => selector(tu._1, tu._2, v))

  }


  /**
    * Combines four observables, emitting some type `R` specified in the function `selector`,
    * each time an event is received from one of the source observables, where the aggregation
    * is defined by the given function.
    *
    * <img width="640" height="410" src="http://reactivex.io/documentation/operators/images/withLatestFrom.png" alt="" />
    *
    * @param first
    * an Observable to be combined
    * @param second
    * an Observable to be combined
    * @param third
    * an Observable to be combined
    * @param selector
    * The function that is used combine the emissions of the four observables.
    *
    * @return An Observable that combines the source Observables according to the function selector.
    */
  def combineLatestWith[U,V,W,R](first: Observable[U], second: Observable[V], third: Observable[W])
                                (selector: (T, U, V, W) => R): Observable[R] = {
    combineLatest(first)
      .combineLatestWith(second)((tu, v) => (tu._1, tu._2, v))
      .combineLatestWith(third)((tuv, w) => selector(tuv._1, tuv._2, tuv._3, w))

  }

  /**
    * Combines two observables, emitting a combination of the latest values of each of
    * the source observables each time an event is received from one of the source observables.
    *
    * <img width="640" height="410" src="http://reactivex.io/documentation/operators/images/combineLatest.png" alt="" />
    *
    * @param that
    * The second source observable.
    *
    * @return An Observable that combines the source Observables
    */
  def combineLatest[U](that: Observable[U]): Observable[(T, U)] = {
    combineLatestWith(that)((t, u) => (t, u))
  }

  /**
    * Combines four observables, emitting a tuple of the latest values of each of
    * the source observables each time an event is received from one of the source observables.
    *
    * <img width="640" height="410" src="http://reactivex.io/documentation/operators/images/combineLatest.png" alt="" />
    *
    * @param first
    * an Observable to be combined
    * @param second
    * an Observable to be combined
    *
    * @return An Observable that combines the source Observables
    */
  def combineLatest[U,V](first: Observable[U], second: Observable[V]): Observable[(T, U, V)] = {
    combineLatestWith(first, second)((t, u, v) => (t, u, v))
  }

  /**
    * Combines four observables, emitting a tuple of the latest values of each of
    * the source observables each time an event is received from one of the source observables.
    *
    * <img width="640" height="410" src="http://reactivex.io/documentation/operators/images/combineLatest.png" alt="" />
    *
    * @param first
    * an Observable to be combined
    * @param second
    * an Observable to be combined
    * @param third
    * an Observable to be combined
    *
    * @return An Observable that combines the source Observables
    */
  def combineLatest[U,V,W](first: Observable[U], second: Observable[V], third: Observable[W]): Observable[(T, U, V, W)] = {
    combineLatestWith(first, second, third)((t, u, v, w) => (t, u, v, w))
  }



  /**
    * Returns an Observable that first emits the items emitted by `this`, and then the items emitted
    * by `other`.
    *
    * <img width="640" height="380" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/concat.png" alt="" />
    *
    * @param other
    * an Observable to be appended
    *
    * @return an Observable that emits items that are the result of combining the items emitted by
    *         this and that, one after the other
    */
  def ++[U >: T](other: Observable[U]): Observable[U] = {
    new Observable(inner concat other)
  }

  /**
    * Returns an Observable that first emits the items emitted by `this`, and then the items emitted
    * by `other`.
    *
    * <img width="640" height="380" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/concat.png" alt="" />
    *
    * @param other
    * an Observable to be appended
    *
    * @return an Observable that emits items that are the result of combining the items emitted by
    *         this and that, one after the other
    */
  def concat[U](other: Observable[U]): Observable[U] = {
    new Observable(inner concat other)
  }

  /**
    * Returns an Observable that emits the items emitted by several Observables, one after the
    * other.
    *
    * <img width="640" height="380" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/concat.png" alt="" />
    *
    * This operation is only available if `this` is of type `Observable[Observable[U]]` for some `U`,
    * otherwise you'll get a compilation error.
    *
    */
  def concatAll[U](implicit evidence: <:<[Observable[T], Observable[Observable[U]]]): Observable[U] = {
    new Observable(inner.asInstanceOf[ObservableFacade[Observable[U]]].map((n: Observable[U]) => n.get).concatAll())
  }

  /**
    * Returns a new Observable that emits items resulting from applying a function that you supply to each item
    * emitted by the source Observable, where that function returns an Observable, and then emitting the items
    * that result from concatinating those resulting Observables.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/concatMap.png" alt="" />
    *
    * @param project a function that, when applied to an item emitted by the source Observable, returns an Observable
    *
    * @return an Observable that emits the result of applying the transformation function to each item emitted
    *         by the source Observable and concatinating the Observables obtained from this transformation
    */
  def concatMap[R](project: (T, Int) => Observable[R]): Observable[R] = {
    new Observable(inner.concatMap(toReturnFacade(project)))
  }

  /**
    * Returns a new Observable that emits items resulting from applying a function that you supply to each item
    * emitted by the source Observable, where that function returns an Observable, and then emitting the items
    * that result from concatinating those resulting Observables.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/concatMap.png" alt="" />
    *
    * @param project a function that, when applied to an item emitted by the source Observable, returns an Observable
    *
    * @return an Observable that emits the result of applying the transformation function to each item emitted
    *         by the source Observable and concatinating the Observables obtained from this transformation
    */
  def concatMap[R](project: T => Observable[R]): Observable[R] = {
    new Observable(inner.concatMap(toReturnFacade(project)))
  }

  /**
    * Projects each source value to the same Observable which is merged multiple times in a serialized fashion on the output Observable.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/concatMap.png" alt="" />
    *
    * @param innerObservable a function that, when applied to an item emitted by the source Observable, returns an Observable
    *
    * @return an Observable that emits the result of applying the transformation function to each item emitted
    *         by the source Observable and concatinating the Observables obtained from this transformation
    */
  def concatMapTo[R](innerObservable: Observable[R]): Observable[R] = {
    new Observable(inner.concatMapTo[R, Nothing](innerObservable))
  }

  /**
    * Return an [[Observable]] which emits the number of elements in the source [[Observable]] which satisfy a predicate.
    *
    * @param predicate the predicate used to test elements.
    *
    * @return an [[Observable]] which emits the number of elements in the source [[Observable]] which satisfy a predicate.
    */
  def count(predicate: (T, Int, Observable[T]) => Boolean): Observable[Int] = {
    new Observable(inner.count(toFacadeFunction(predicate)))
  }

  /**
    * Return an [[Observable]] which emits the number of elements in the source.
    *
    * @return an [[Observable]] which emits the number of elements in the source.
    */
  def count: Observable[Int] = {
    new Observable(inner.count())
  }

  /**
    * Return an Observable that mirrors the source Observable, except that it drops items emitted by the source
    * Observable that are followed by another item within a computed debounce duration.
    *
    * <img width="640" height="425" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/debounce.f.png" alt="" />
    *
    * @param debounceSelector function to retrieve a sequence that indicates the throttle duration for each item
    *
    * @return an Observable that omits items emitted by the source Observable that are followed by another item
    *         within a computed debounce duration
    */
  def debounce(debounceSelector: T => Observable[Int]): Observable[T] = {
    new Observable(inner
      .debounce(toReturnFacade(debounceSelector)))
  }
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
    * The time each value has to be 'the most recent' of the Observable to ensure that it's not dropped.
    *
    * @return An Observable which filters out values which are too quickly followed up with newer values.
    * @see `Observable.throttleWithTimeout`
    */
  def debounceTime(timeout: FiniteDuration): Observable[T] = {
    new Observable(inner.debounceTime(timeout.toMillis.toInt))
  }

  /**
    * Returns an Observable that emits the items emitted by the source Observable or a specified default item
    * if the source Observable is empty.
    *
    * <img width="640" height="305" src="http://reactivex.io/documentation/operators/images/defaultIfEmpty.c.png" alt="" />
    *
    * @param defaultValue the item to emit if the source Observable emits no items. This is a by-name parameter, so it is
    * only evaluated if the source Observable doesn't emit anything.
    *
    * @return an Observable that emits either the specified default item if the source Observable emits no
    *         items, or the items emitted by the source Observable
    */
  def defaultIfEmpty[R](defaultValue: => R): Observable[R] = {
    new Observable(inner.defaultIfEmpty(defaultValue))
  }

  /**
    * Returns an Observable that emits the items emitted by the source Observable or a specified default item
    * if the source Observable is empty.
    *
    * <img width="640" height="305" src="http://reactivex.io/documentation/operators/images/defaultIfEmpty.c.png" alt="" />
    *
    * @param default the item to emit if the source Observable emits no items. This is a by-name parameter, so it is
    * only evaluated if the source Observable doesn't emit anything.
    *
    * @return an Observable that emits either the specified default item if the source Observable emits no
    *         items, or the items emitted by the source Observable
    */
  def orElse[U >: T](default: => U): Observable[U] = {
    defaultIfEmpty(default)
  }

  /**
    * Returns an Observable that emits the items emitted by the source Observable shifted forward in time by a
    * specified delay. Error notifications from the source Observable are not delayed.
    *
    * <img width="640" height="310" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/delay.png" alt="" />
    *
    * @param delay the delay to shift the source by
    *
    * @return the source Observable shifted in time by the specified delay
    */
  def delay(delay: FiniteDuration): Observable[T] = {
    new Observable(inner.delay(delay.toMillis.toInt))
  }

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
    * then used to delay the emission of that item by the resulting Observable until the Observable
    * returned from `itemDelay` emits an item
    * @param subscriptionDelay a function that returns an Observable that triggers the subscription to the source Observable
    * once it emits any item
    *
    * @return an Observable that delays the emissions of the source Observable via another Observable on a per-item basis
    */
  def delayWhen[U, I](
    delayDurationSelector: T => Observable[U],
    subscriptionDelay: Observable[I]
  ): Observable[T] = {
    new Observable(inner.delayWhen(toReturnFacade(delayDurationSelector), subscriptionDelay))
  }

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
    * then used to delay the emission of that item by the resulting Observable until the Observable
    * returned from `itemDelay` emits an item
    *
    * @return an Observable that delays the emissions of the source Observable via another Observable on a per-item basis
    */
  def delayWhen[U, I](delayDurationSelector: T => Observable[U]): Observable[T] = {
    new Observable(inner
      .delayWhen(toReturnFacade(delayDurationSelector)))
  }

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
    * @usecase def dematerialize[U]: Observable[U]
    * @inheritdoc
    *
    */
  def dematerialize[T2]: Observable[T2] = {
    new Observable(inner.dematerialize())
  }

  /**
    * Returns an Observable that forwards all items emitted from the source Observable that are distinct according
    * to a key selector function.
    *
    * <img width="640" height="310" src="http://reactivex.io/documentation/operators/images/distinct.png" alt="" />
    *
    * @param keySelector
    * function to select which value you want to check as distinct.
    * @param flushes
    * Observable for flushing the internal HashSet of the operator.
    *
    * @return an Observable of distinct items
    */
  def distinct[K,T2](keySelector: T => K, flushes: Observable[T2]): Observable[T] = {
    new Observable(inner
      .distinct(keySelector, flushes))
  }
  /**
    * Returns an Observable that forwards all items emitted from the source Observable that are distinct according
    * to a key selector function.
    *
    * <img width="640" height="310" src="http://reactivex.io/documentation/operators/images/distinct.png" alt="" />
    *
    * @param flushes
    * Observable for flushing the internal HashSet of the operator.
    *
    * @return an Observable of distinct items
    */
  def distinct[T2](flushes: Observable[T2]): Observable[T] = {
    new Observable(inner.distinct(flushes = flushes))
  }
  /**
    * Returns an Observable that forwards all items emitted from the source Observable that are distinct according
    * to a key selector function.
    *
    * <img width="640" height="310" src="http://reactivex.io/documentation/operators/images/distinct.png" alt="" />
    *
    * @param keySelectior
    * function to select which value you want to check as distinct.
    *
    * @return an Observable of distinct items
    */
  def distinct[K](keySelector: T => K): Observable[T] = {
    new Observable(inner.distinct(keySelector))
  }
  /**
    * Returns an Observable that forwards all items emitted from the source Observable that are distinct according
    * to a key selector function.
    *
    * <img width="640" height="310" src="http://reactivex.io/documentation/operators/images/distinct.png" alt="" />
    *
    * @return an Observable of distinct items
    */
  def distinct[T2]: Observable[T] = {
    new Observable(inner.distinct())
  }

  /**
    * Returns an Observable that forwards all items emitted from the source Observable that are sequentially
    * distinct according to a key selector function.
    *
    * <img width="640" height="310" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/distinctUntilChanged.key.png" alt="" />
    *
    * @param compare
    * a function that compares the two items
    * @param keySelector
    * a function that projects an emitted item to a key value which is used for deciding whether an item is sequentially
    * distinct from another one or not
    *
    * @return an Observable of sequentially distinct items
    */
  def distinctUntilChanged[K](compare: (K, K) => Boolean, keySelector: T => K): Observable[T] = {
    new Observable(inner
      .distinctUntilChanged(compare, keySelector))
  }
  /**
    * Returns an Observable that forwards all items emitted from the source Observable that are sequentially
    * distinct according to a key selector function.
    *
    * <img width="640" height="310" src="http://reactivex.io/documentation/operators/images/distinctUntilChanged.png" alt="" />
    *
    * @param compare
    * a function that compares the two items
    *
    * @return an Observable of sequentially distinct items
    */
  def distinctUntilChanged(compare: (T, T) => Boolean): Observable[T] = {
    new Observable(inner
      .distinctUntilChanged(compare))
  }
  /**
    * Returns an Observable that forwards all items emitted from the source Observable that are sequentially
    * distinct according to a key selector function.
    *
    * <img width="640" height="310" src="http://reactivex.io/documentation/operators/images/distinctUntilChanged.png" alt="" />
    *
    * @return an Observable of sequentially distinct items
    */
  def distinctUntilChanged: Observable[T] = {
    new Observable(inner.distinctUntilChanged())
  }

  /**
    * Determines whether all elements of an observable sequence satisfy a condition.
    *
    * @param predicate
    * A function to test each element for a condition.
    *
    * @return An observable sequence containing a single element determining whether all elements in the source sequence pass the test in the specified predicate.
    */
  def every(predicate: (T, Int) => Boolean): Observable[Boolean] = {
    new Observable(inner.every(predicate))
  }

  /**
    * Projects each source value to an Observable which is merged in the output Observable only if the previous projected Observable has completed.
    * Maps each value to an Observable, then flattens all of these inner Observables using exhaust.
    *
    * <img width="640" height="310" src="http://reactivex.io/rxjs/img/exhaust.png" alt="" />
    *
    * @return Returns an Observable that takes a source of Observables and propagates the first observable exclusively until it completes before subscribing to the next.
    */
  def exhaust[U](implicit evidence: <:<[Observable[T], Observable[Observable[U]]]): Observable[U] = {
    new Observable[U](inner.asInstanceOf[ObservableFacade[Observable[U]]].map((n: Observable[U]) => n.get).exhaust())
  }

  /**
    * Converts a higher-order Observable into a first-order Observable by dropping inner Observables while the previous inner Observable has not yet completed.
    * Flattens an Observable-of-Observables by dropping the next inner Observables while the current inner is still executing.
    *
    * <img width="640" height="310" src="http://reactivex.io/rxjs/img/exhaustMap.png" alt="" />
    *
    * @param project
    * A function that, when applied to an item emitted by the source Observable, returns an Observable.
    * @param resultSelector
    * A function to produce the value on the output Observable based on the values and the indices of the source (outer) emission and the inner Observable emission. The arguments passed to this function are:
    * outerValue: the value that came from the source
    * innerValue: the value that came from the projected Observable
    * outerIndex: the "index" of the value that came from the source
    * innerIndex: the "index" of the value from the projected Observable
    *
    * @return An Observable containing projected Observables of each item of the source, ignoring projected Observables that start before their preceding Observable has completed.
    **/
  def exhaustMap[I, R](
    project: (T, Int) => Observable[R],
    resultSelector: (T, I, Int, Int) => R
  ): Observable[R] = {
    new Observable(inner.exhaustMap(toReturnFacade(project), resultSelector))
  }
  /**
    * Converts a higher-order Observable into a first-order Observable by dropping inner Observables while the previous inner Observable has not yet completed.
    * Flattens an Observable-of-Observables by dropping the next inner Observables while the current inner is still executing.
    *
    * <img width="640" height="310" src="http://reactivex.io/rxjs/img/exhaustMap.png" alt="" />
    *
    * @param project
    * A function that, when applied to an item emitted by the source Observable, returns an Observable.
    *
    * @return An Observable containing projected Observables of each item of the source, ignoring projected Observables that start before their preceding Observable has completed.
    */
  def exhaustMap[R](project: (T, Int) => Observable[R]): Observable[R] = {
    new Observable(inner.exhaustMap(toReturnFacade(project)))
  }
  /**
    * Returns an Observable where for each item in the source Observable, the supplied function is applied to each item, resulting in a new value to then be applied again with the function.
    *
    * @param project
    * the function for projecting the next emitted item of the Observable.
    * @param concurrent
    * the max number of observables that can be created concurrently. defaults to infinity.
    * @param scheduler
    * The Scheduler to use for managing the expansions.
    *
    * @return An observable sequence containing a single element determining whether all elements in the source sequence pass the test in the specified predicate.
    */
  def expand[R](
    project: (T, Int) => Observable[R],
    scheduler: Scheduler,
    concurrent: Int = Int.MaxValue
  ): Observable[R] = {
    new Observable(inner.expand(toReturnFacade(project), concurrent, scheduler))
  }
  /**
    * Returns an Observable where for each item in the source Observable, the supplied function is applied to each item, resulting in a new value to then be applied again with the function.
    *
    * @param project
    * the function for projecting the next emitted item of the Observable.
    *
    * @return An observable sequence containing a single element determining whether all elements in the source sequence pass the test in the specified predicate.
    */
  def expand[R](project: (T, Int) => Observable[R]): Observable[R] = {
    new Observable(inner
      .expand(toReturnFacade(project)))
  }

  /**
    * Returns an Observable which only emits those items for which a given predicate holds.
    *
    * <img width="640" height="310" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/filter.png" alt="" />
    *
    * @param predicate
    * a function that evaluates the items emitted by the source Observable, returning `true` if they pass the filter
    *
    * @return an Observable that emits only those items in the original Observable that the filter
    *         evaluates as `true`
    */
  def filter[T2](predicate: (T, Int) => Boolean): Observable[T] = {
    new Observable(inner.filter(predicate))
  }
  /**
    * Returns an Observable which only emits those items for which a given predicate holds.
    *
    * <img width="640" height="310" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/filter.png" alt="" />
    *
    * @param predicate
    * a function that evaluates the items emitted by the source Observable, returning `true` if they pass the filter
    *
    * @return an Observable that emits only those items in the original Observable that the filter
    *         evaluates as `true`
    */
  def filter[T2](predicate: T => Boolean): Observable[T] = {
    new Observable(inner.filter(predicate))
  }

  /**
    * Emits only the first value emitted by the source Observable that meets some condition.
    *
    * <img width="640" height="310" src="http://reactivex.io/rxjs/img/find.png" alt="" />
    *
    * @param predicate
    * A function called with each item to test for condition matching.
    *
    * @return An Observable of the first item that matches the condition.
    */
  def find[T2](predicate: (T, Int) => Boolean): Observable[T] = {
    new Observable(inner.find(predicate))
  }

  /**
    * Emits only the index of the first value emitted by the source Observable that meets some condition.
    *
    * <img width="640" height="310" src="http://reactivex.io/rxjs/img/findIndex.png" alt="" />
    *
    * @param predicate
    * A function called with each item to test for condition matching.
    *
    * @return An Observable of the index of the first item that matches the condition.
    */
  def findIndex[T2](predicate: (T, Int) => Boolean): Observable[Int] = {
    new Observable(inner.findIndex(predicate))
  }

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
  def first: Observable[T] = {
    new Observable(inner.take(1))
  }
  /**
    * Returns an Observable that emits only the very first item emitted by the source Observable, or
    * a default value if the source Observable is empty.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/firstOrDefault.png" alt="" />
    *
    * @param default
    * The default value to emit if the source Observable doesn't emit anything.
    * This is a by-name parameter, so it is only evaluated if the source Observable doesn't emit anything.
    *
    * @return an Observable that emits only the very first item from the source, or a default value
    *         if the source Observable completes without emitting any item.
    */
  def firstOrElse[R >: T](default: => R): Observable[R] = {
    new Observable(inner.first(defaultValue = default))
  }

  /**
    * Groups the items emitted by an [[Observable]] according to a specified criterion, and emits these
    * grouped items as `(key, observable)` pairs.
    *
    * <img width="640" height="360" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/groupBy.png" alt="" />
    *
    * Note: A `(key, observable)` will cache the items it is to emit until such time as it
    * is subscribed to. For this reason, in order to avoid memory leaks, you should not simply ignore those
    * `(key, observable)` pairs that do not concern you. Instead, you can signal to them that they may
    * discard their buffers by applying an operator like `take(0)` to them.
    *
    * @param keySelector a function that extracts the key for each item
    * @param valueSelector a function that extracts the return element for each item
    * @tparam K the key type
    * @tparam V the value type
    *
    * @return an [[Observable]] that emits `(key, observable)` pairs, each of which corresponds to a
    *         unique key value and each of which emits those items from the source Observable that share that
    *         key value
    * @see <a href="https://github.com/ReactiveX/RxJava/wiki/Transforming-Observables#groupby-and-groupbyuntil">RxJava wiki: groupBy</a>
    * @see <a href="http://msdn.microsoft.com/en-us/library/system.reactive.linq.observable.groupby.aspx">MSDN: Observable.GroupBy</a>
    */
  def groupBy[K, V](keySelector: T => K, valueSelector: T => V): Observable[(K, Observable[V])] = {
    val outerFacade: ObservableFacade[GroupedObservableFacade[K, V]] = inner.groupBy(keySelector, valueSelector)
    new Observable(outerFacade
      .map((groupFacade: GroupedObservableFacade[K, V]) => (groupFacade.key, new Observable(groupFacade))))
  }
  /**
    * Groups the items emitted by an [[Observable]] according to a specified criterion, and emits these
    * grouped items as `(key, observable)` pairs.
    *
    * <img width="640" height="360" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/groupBy.png" alt="" />
    *
    * Note: A `(key, observable)` will cache the items it is to emit until such time as it
    * is subscribed to. For this reason, in order to avoid memory leaks, you should not simply ignore those
    * `(key, observable)` pairs that do not concern you. Instead, you can signal to them that they may
    * discard their buffers by applying an operator like `take(0)` to them.
    *
    * @param keySelector a function that extracts the key for each item
    * @tparam K the key type
    *
    * @return an [[Observable]] that emits `(key, observable)` pairs, each of which corresponds to a
    *         unique key value and each of which emits those items from the source Observable that share that
    *         key value
    * @see <a href="https://github.com/ReactiveX/RxJava/wiki/Transforming-Observables#groupby-and-groupbyuntil">RxJava wiki: groupBy</a>
    * @see <a href="http://msdn.microsoft.com/en-us/library/system.reactive.linq.observable.groupby.aspx">MSDN: Observable.GroupBy</a>
    */
  def groupBy[K](keySelector: T => K): Observable[(K, Observable[T])] = {
    val outerFacade: ObservableFacade[GroupedObservableFacade[K, T]] = inner.groupBy(keySelector)
    new Observable(outerFacade
      .map((groupFacade: GroupedObservableFacade[K, T]) => (groupFacade.key, new Observable(groupFacade))))
  }

  /**
    * Ignores all items emitted by the source Observable and only passes calls of complete or error.
    *
    * <img width="640" height="310" src="http://reactivex.io/rxjs/img/ignoreElements.png" alt="" />
    *
    * @return an empty Observable that only calls complete or error, based on which one is called by the source Observable.
    */
  def ignoreElements: Observable[T] = {
    new Observable(inner.ignoreElements())
  }

  /** Tests whether this `Observable` emits no elements.
    *
    * @return an Observable emitting one single Boolean, which is `true` if this `Observable`
    *         emits no elements, and `false` otherwise.
    */
  def isEmpty: Observable[Boolean] = {
    new Observable(inner.isEmpty())
  }

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
  def last: Observable[T] = {
    new Observable(inner.last[T]())
  }
  /**
    * Returns an Observable that emits only the last item emitted by the source Observable, or a default item
    * if the source Observable completes without emitting any items.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/lastOrDefault.png" alt="" />
    *
    * @param default the default item to emit if the source Observable is empty.
    * This is a by-name parameter, so it is only evaluated if the source Observable doesn't emit anything.
    *
    * @return an Observable that emits only the last item emitted by the source Observable, or a default item
    *         if the source Observable is empty
    */
  def lastOrElse[R >: T](default: => R): Observable[R] = {
    new Observable(inner.last(defaultValue = default))
  }

  /**
    * Returns an Observable that applies the given function to each item emitted by an
    * Observable and emits the result.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/map.png" alt="" />
    *
    * @param project
    * a function to apply to each item emitted by the Observable
    *
    * @return an Observable that emits the items from the source Observable, transformed by the
    *         given function
    */
  def mapWithIndex[R](project: (T, Int) => R): Observable[R] = {
    new Observable[R](inner.mapWithIndex(project))
  }
  /**
    * Returns an Observable that applies the given function to each item emitted by an
    * Observable and emits the result.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/map.png" alt="" />
    *
    * @param project
    * a function to apply to each item emitted by the Observable
    *
    * @return an Observable that emits the items from the source Observable, transformed by the
    *         given function
    */
  def map[R](project: T => R): Observable[R] = {
    new Observable[R](inner.map(project))
  }
  /**
    * Returns an Observable that maps each element to a specific value.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/map.png" alt="" />
    *
    * @param value
    * the value to map to.
    *
    * @return an Observable that emits the items from the source Observable, transformed by the
    *         given function
    */
  def mapTo[R](value: R): Observable[R] = {
    new Observable(inner.mapTo(value))
  }

  /**
    * Turns all of the notifications from a source Observable into onNext  emissions,
    * and marks them with their original notification types within [[rxscalajs.Notification]] objects.
    *
    * <img width="640" height="315" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/materialize.png" alt="" />
    *
    * @return an Observable whose items are the result of materializing the items and
    *         notifications of the source Observable
    */
  def materialize: Observable[Notification[_ <: T]] = {
    new Observable(inner.materialize())
  }

  /**
    * Flattens two Observables into one Observable, without any transformation.
    *
    * <img width="640" height="380" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/merge.png" alt="" />
    *
    * You can combine items emitted by two Observables so that they act like a single
    * Observable by using the `merge` method.
    *
    * @param that
    * an Observable to be merged
    * @param concurrent the maximum number of Observables that may be subscribed to concurrently
    *
    * @return an Observable that emits items from `this` and `that` until
    *         `this` or `that` emits `onError` or both Observables emit `onCompleted`.
    */
  def merge[R >: T](
    that: Observable[R],
    concurrent: Int = Int.MaxValue,
    scheduler: Scheduler
  ): Observable[R] = {
    new Observable(inner.merge(that, concurrent, scheduler))
  }
  /**
    * Flattens two Observables into one Observable, without any transformation.
    *
    * <img width="640" height="380" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/merge.png" alt="" />
    *
    * You can combine items emitted by two Observables so that they act like a single
    * Observable by using the `merge` method.
    *
    * @param that
    * an Observable to be merged
    *
    * @return an Observable that emits items from `this` and `that` until
    *         `this` or `that` emits `onError` or both Observables emit `onCompleted`.
    */
  def merge[R >: T](that: Observable[R]): Observable[R] = {
    new Observable(inner.merge(that))
  }

  /**
    * Flattens three Observables into one Observable, without any transformation.
    *
    * <img width="640" height="380" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/merge.png" alt="" />
    *
    * You can combine items emitted by three Observables so that they act like a single
    * Observable by using the `merge` method.
    *
    * @param first
    * an Observable to be merged
    * @param second
    * an Observable to be merged
    *
    * @return an Observable that emits items from all Observables until
    *         one emits `onError` or all Observables emit `onCompleted`.
    */
  def merge[R >: T](first: Observable[R], second: Observable[R]): Observable[R] = {
    new Observable(inner.merge(first).merge(second))
  }

  /**
    * Flattens four Observables into one Observable, without any transformation.
    *
    * <img width="640" height="380" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/merge.png" alt="" />
    *
    * You can combine items emitted by four Observables so that they act like a single
    * Observable by using the `merge` method.
    *
    * @param first
    * an Observable to be merged
    * @param second
    * an Observable to be merged
    * @param third
    * an Observable to be merged
    *
    * @return an Observable that emits items from all Observables until
    *         one emits `onError` or all Observables emit `onCompleted`.
    */
  def merge[R >: T](first: Observable[R], second: Observable[R], third: Observable[R]): Observable[R] = {
    new Observable(inner.merge(first).merge(second).merge(third))
  }

  /**
    * Flattens the sequence of Observables emitted by `this` into one Observable, without any
    * transformation.
    *
    * <img width="640" height="380" src="http://reactivex.io/documentation/operators/images/mergeAll.png" alt="" />
    *
    * You can combine the items emitted by multiple Observables so that they act like a single
    * Observable by using this method.
    *
    * This operation is only available if `this` is of type `Observable[Observable[U]]` for some `U`,
    * otherwise you'll get a compilation error.
    *
    * @param concurrent the maximum number of Observables that may be subscribed to concurrently
    *
    * @return an Observable that emits items that are the result of flattening the items emitted
    *         by the Observables emitted by `this`
    *
    */
  def mergeAll[U](concurrent: Int = Int.MaxValue)
    (implicit evidence: <:<[Observable[T], Observable[Observable[U]]]): Observable[U] = {
    new Observable(inner.asInstanceOf[ObservableFacade[Observable[U]]].map((n: Observable[U]) => n.get)
      .mergeAll(concurrent))
  }

  /**
    * Flattens the sequence of Observables emitted by `this` into one Observable, without any
    * transformation.
    *
    * <img width="640" height="380" src="http://reactivex.io/documentation/operators/images/mergeAll.png" alt="" />
    *
    * You can combine the items emitted by multiple Observables so that they act like a single
    * Observable by using this method.
    *
    * This operation is only available if `this` is of type `Observable[Observable[U]]` for some `U`,
    * otherwise you'll get a compilation error.
    *
    * @return an Observable that emits items that are the result of flattening the items emitted
    *         by the Observables emitted by `this`
    *
    */
  def flatten[U](implicit evidence: <:<[Observable[T], Observable[Observable[U]]]): Observable[U] = {
    mergeAll()
  }

  /**
    * Returns an [[Observable]] that emits items based on applying a function that you supply to each item emitted
    * by the source [[Observable]] , where that function returns an [[Observable]] , and then merging those resulting
    * [[Observable]]s and emitting the results of this merger, while limiting the maximum number of concurrent
    * subscriptions to these [[Observable]]s.
    *
    * <img width="640" height="380" src="http://reactivex.io/documentation/operators/images/flatMap.c.png" alt="" />
    *
    * @param project a function that, when applied to an item emitted by the source [[Observable]], returns an [[Observable]]
    *
    * @return an [[Observable]] that emits the result of applying the transformation function to each item emitted
    *         by the source [[Observable]] and merging the results of the [[Observable]]s obtained from this transformation
    */
  def mergeMap[R](project: (T, Int) => Observable[R]): Observable[R] = {
    new Observable(inner.mergeMap[R](toReturnFacade(project)))
  }
  /**
    * Returns an [[Observable]] that emits items based on applying a function that you supply to each item emitted
    * by the source [[Observable]] , where that function returns an [[Observable]] , and then merging those resulting
    * [[Observable]]s and emitting the results of this merger, while limiting the maximum number of concurrent
    * subscriptions to these [[Observable]]s.
    *
    *
    * <img width="640" height="380" src="http://reactivex.io/documentation/operators/images/flatMap.c.png" alt="" />
    *
    * @param project a function that, when applied to an item emitted by the source [[Observable]], returns an [[Observable]]
    *
    * @return an [[Observable]] that emits the result of applying the transformation function to each item emitted
    *         by the source [[Observable]] and merging the results of the [[Observable]]s obtained from this transformation
    */
  def mergeMap[R](project: T => Observable[R]): Observable[R] = {
    new Observable(inner.mergeMap[R](toReturnFacade(project)))
  }

  /**
    * Returns an [[Observable]] that emits items based on applying a function that you supply to each item emitted
    * by the source [[Observable]] , where that function returns an [[Observable]] , and then merging those resulting
    * [[Observable]]s and emitting the results of this merger, while limiting the maximum number of concurrent
    * subscriptions to these [[Observable]]s.
    *
    *
    * <img width="640" height="380" src="http://reactivex.io/documentation/operators/images/flatMap.c.png" alt="" />
    *
    * @param project a function that, when applied to an item emitted by the source [[Observable]], returns an [[Observable]]
    *
    * @return an [[Observable]] that emits the result of applying the transformation function to each item emitted
    *         by the source [[Observable]] and merging the results of the [[Observable]]s obtained from this transformation
    */
  def flatMap[R](project: T => Observable[R]): Observable[R] = {
    new Observable(inner.mergeMap[R](toReturnFacade(project)))
  }

  def mergeMapTo[I, R](
    innerObservable: Observable[I],
    resultSelector: (T, I, Int, Int) => R,
    concurrent: Int = Int.MaxValue
  ): Observable[R] = {
    new Observable(inner.mergeMapTo(innerObservable, resultSelector, concurrent))
  }
  def mergeMapTo[R](innerObservable: Observable[R]): Observable[R] = {
    new Observable(inner.mergeMapTo(innerObservable))
  }

  def multicast(subjectOrSubjectFactory: SubjectFacade[_ >: T]): ConnectableObservable[T] = {
    new ConnectableObservable(inner.multicast(subjectOrSubjectFactory))
  }

  /**
    * Groups pairs of consecutive emissions together and emits them as a tuple of two values.
    *
    * <img width="640" height="510" src="http://reactivex.io/rxjs/img/pairwise.png" alt="" />
    *
    * @return an Observable of pairs (as tuples) of consecutive values from the source Observable.
    */
  def pairwise: Observable[(T, T)] = {
    new Observable[(T, T)](inner.pairwise()
      .map((arr: js.Array[_ <: T]) => (arr(0), arr(1))))
  }

  /**
    * Splits the source Observable into two, one with values that satisfy a predicate, and another with values that don't satisfy the predicate.
    * It's like filter, but returns two Observables: one like the output of filter, and the other with values that did not pass the condition.
    *
    * <img width="640" height="325" src="http://reactivex.io/rxjs/img/partition.png" alt="" />
    *
    * @param predicate
    * A function that evaluates each value emitted by the source Observable.
    * If it returns true, the value is emitted on the first Observable in the returned array, if false the value is emitted on the second Observable in the tuple.
    *
    * @return an Observable that emits a single item that is the result of accumulating the output
    *         from the items emitted by the source Observable
    */
  def partition[T2](predicate: T => Boolean): (Observable[T], Observable[T]) = {
    val partitioned = inner.partition(predicate)
    (new Observable(partitioned(0)), new Observable(partitioned(1)))
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
  def publish: ConnectableObservable[T] = {
    new ConnectableObservable[T](inner.publish())
  }

  def publishLast: ConnectableObservable[T] = new ConnectableObservable[T](inner.publishLast())
  def publishReplay(
    bufferSize: Int = Int.MaxValue,
    windowTime: FiniteDuration = Int.MaxValue.millis
  ): ConnectableObservable[T] = {
    new ConnectableObservable(inner.publishReplay(bufferSize, windowTime.toMillis.toInt))
  }

  /**
    * Returns an Observable that mirrors the first source Observable to emit an item from the combination of this Observable and supplied Observables
    *
    * @param observables
    * sources used to race for which Observable emits first.
    *
    * @return an Observable that mirrors the output of the first Observable to emit an item.
    */
  def race(observables: Observable[_ >: T]*): Observable[T] = {
    new Observable(inner
      .race(observables.map(_.get).toJSArray))
  }

  /**
    * Returns an Observable that applies a function of your choosing to the first item emitted by a
    * source Observable, then feeds the result of that function along with the second item emitted
    * by an Observable into the same function, and so on until all items have been emitted by the
    * source Observable, emitting the final result from the final call to your function as its sole
    * item.
    *
    * <img width="640" height="325" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/reduceSeed.png" alt="" />
    *
    * This technique, which is called "fold" or "reduce" here, is sometimes called "aggregate,"
    * "accumulate," "compress," or "inject" in other programming contexts. Groovy, for instance,
    * has an `inject` method that does a similar operation on lists.
    *
    * @param seed
    * the initial (seed) accumulator value
    * @param accumulator
    * an accumulator function to be invoked on each item emitted by the source
    * Observable, the result of which will be used in the next accumulator call
    *
    * @return an Observable that emits a single item that is the result of accumulating the output
    *         from the items emitted by the source Observable
    */
  def foldLeft[R](seed: R)(accumulator: (R, T) => R): Observable[R] = {
    new Observable(inner.reduce(accumulator, seed))
  }
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
    * An accumulator function to be invoked on each item emitted by the source
    * Observable, whose result will be used in the next accumulator call
    *
    * @return an Observable that emits a single item that is the result of accumulating the
    *         output from the source Observable
    */
  def reduce[U >: T](accumulator: (U, U) => U): Observable[U] = {
    new Observable(inner.reduce(accumulator))
  }

  /**
    * Returns an Observable that repeats the sequence of items emitted by the source Observable at most `count` times.
    * <p>
    * <img width="640" height="310" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/repeat.on.png" alt="" />
    *
    * @param count the number of times the source Observable items are repeated,
    * a count of 0 will yield an empty sequence
    *
    * @return an Observable that repeats the sequence of items emitted by the source Observable at most `count` times
    * @see <a href="https://github.com/ReactiveX/RxJava/wiki/Creating-Observables#wiki-repeat">RxJava Wiki: repeat()</a>
    * @see <a href="http://msdn.microsoft.com/en-us/library/hh229428.aspx">MSDN: Observable.Repeat</a>
    */
  def repeat(count: Int = -1): Observable[T] = {
    new Observable(inner.repeat(count = count))
  }

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
    * Number of retry attempts before failing.
    *
    * @return Observable with retry logic.
    */
  def retry(count: Int = -1): Observable[T] = {
    new Observable(inner.retry(count))
  }
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
    * <dt><b>Scheduler:</b></dt>
    * <dd>`retryWhen` operates by default on the `trampoline` [[Scheduler]].</dd>
    * </dl>
    *
    * @param notifier receives an Observable of a Throwable with which a user can complete or error, aborting the
    * retry
    *
    * @return the source Observable modified with retry logic
    * @see <a href="https://github.com/ReactiveX/RxJava/wiki/Error-Handling-Operators#retrywhen">RxJava Wiki: retryWhen()</a>
    * @see RxScalaDemo.retryWhenDifferentExceptionsExample for a more intricate example
    * @since 0.20
    */
  def retryWhen[U, S](notifier: Observable[U] => Observable[S]): Observable[T] = {
    new Observable(inner
      .retryWhen(toFacadeFunction(toReturnFacade(notifier))))
  }
  /**
    * Return an Observable that emits the results of sampling the items emitted by the source Observable
    * whenever the specified sampler Observable emits an item or completes.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/sample.o.png" alt="" />
    *
    * @param sampler
    * the Observable to use for sampling the source Observable
    *
    * @return an Observable that emits the results of sampling the items emitted by this Observable whenever
    *         the sampler Observable emits an item or completes
    */
  def sample[I](sampler: Observable[I]): Observable[T] = {
    new Observable(inner.sample(sampler))
  }
  /**
    * Returns an Observable that emits the results of sampling the items emitted by the source
    * Observable at a specified time interval.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/sample.png" alt="" />
    *
    * @param delay the sampling rate
    * @param scheduler
    * the [[rxscalajs.Scheduler]] to use when sampling
    *
    * @return an Observable that emits the results of sampling the items emitted by the source
    *         Observable at the specified time interval
    */
  def sampleTime(delay: FiniteDuration, scheduler: Scheduler): Observable[T] = {
    new Observable(inner.sampleTime(delay.toMillis.toInt, scheduler))
  }
  /**
    * Returns an Observable that emits the results of sampling the items emitted by the source
    * Observable at a specified time interval.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/sample.png" alt="" />
    *
    * @param delay the sampling rate
    *
    * @return an Observable that emits the results of sampling the items emitted by the source
    *         Observable at the specified time interval
    */
  def sampleTime(delay: FiniteDuration): Observable[T] = {
    new Observable(inner.sampleTime(delay.toMillis.toInt))
  }

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
    * the initial (seed) accumulator value
    * @param accumulator
    * an accumulator function to be invoked on each item emitted by the source
    * Observable, whose result will be emitted to [[rxscalajs.subscription.ObserverFacade]]s via
    * onNext and used in the next accumulator call.
    *
    * @return an Observable that emits the results of each call to the accumulator function
    */
  def scan[R](seed: R)(accumulator: (R, T) => R): Observable[R] = {
    new Observable(inner.scan(accumulator, seed))
  }
  /**
    * Returns an Observable that applies a function of your choosing to the first item emitted by a
    * source Observable, then feeds the result of that function along with the second item emitted
    * by an Observable into the same function, and so on until all items have been emitted by the
    * source Observable, emitting the result of each of these iterations.
    *
    * <img width="640" height="320" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/scan.png" alt="" />
    *
    * This sort of function is sometimes called an accumulator.
    *
    * Note that when you pass a seed to `scan()` the resulting Observable will emit
    * that seed as its first emitted item.
    *
    * @param accumulator
    * an accumulator function to be invoked on each item emitted by the source
    * Observable, whose result will be emitted to [[rxscalajs.subscription.ObserverFacade]]s via
    * onNext and used in the next accumulator call.
    *
    * @return an Observable that emits the results of each call to the accumulator function
    */
  def scan[U >: T](accumulator: (U, U) => U): Observable[U] = {
    new Observable(inner.scan(accumulator))
  }

  /**
    * Returns a new [[Observable]] that multicasts (shares) the original [[Observable]]. As long a
    * there is more than 1 [[rxscalajs.subscription.Subscriber]], this [[Observable]] will be subscribed and emitting data.
    * When all subscribers have unsubscribed it will unsubscribe from the source [[Observable]].
    *
    * This is an alias for `publish().refCount()`
    *
    * <img width="640" height="510" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/publishRefCount.png" alt="" />
    *
    * @return a [[Observable]] that upon connection causes the source Observable to emit items to its [[rxscalajs.subscription.Subscriber]]s
    * @since 0.19
    */
  def share: Observable[T] = {
    new Observable(inner.share())
  }
  /**
    * If the source Observable completes after emitting a single item, return an Observable that emits that
    * item. If the source Observable emits more than one item or no items, notify of an `IllegalArgumentException`
    * or `NoSuchElementException` respectively.
    *
    * <img width="640" height="315" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/single.png" alt="" />
    *
    * @return an Observable that emits the single item emitted by the source Observable
    * @see <a href="https://github.com/ReactiveX/RxJava/wiki/Observable-Utility-Operators#wiki-single-and-singleordefault">RxJava Wiki: single()</a>
    * @see "MSDN: Observable.singleAsync()"
    */
  def single(predicate: (T, Int, Observable[T]) => Boolean): Observable[T] = {
    new Observable(inner
      .single(toFacadeFunction(predicate)))
  }
  /**
    * If the source Observable completes after emitting a single item, return an Observable that emits that
    * item. If the source Observable emits more than one item or no items, notify of an `IllegalArgumentException`
    * or `NoSuchElementException` respectively.
    *
    * <img width="640" height="315" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/single.png" alt="" />
    *
    * @return an Observable that emits the single item emitted by the source Observable
    * @see <a href="https://github.com/ReactiveX/RxJava/wiki/Observable-Utility-Operators#wiki-single-and-singleordefault">RxJava Wiki: single()</a>
    * @see "MSDN: Observable.singleAsync()"
    */
  def single: Observable[T] = {
    new Observable(inner.single())
  }
  /**
    * Returns an Observable that skips the first `num` items emitted by the source
    * Observable and emits the remainder.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/skip.png" alt="" />
    *
    * @param total
    * the number of items to skip
    *
    * @return an Observable that is identical to the source Observable except that it does not
    *         emit the first `num` items that the source emits
    */
  def drop(total: Int): Observable[T] = {
    skip(total)
  }
  /**
    * Returns an Observable that skips items emitted by the source Observable until a second Observable emits an item.
    * <p>
    * <img width="640" height="375" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/skipUntil.png" alt="" />
    *
    * @param notifier the second Observable that has to emit an item before the source Observable's elements begin
    * to be mirrored by the resulting Observable
    *
    * @return an Observable that skips items from the source Observable until the second Observable emits an
    *         item, then emits the remaining items
    * @see <a href="https://github.com/ReactiveX/RxJava/wiki/Filtering-Observables#wiki-skipuntil">RxJava Wiki: skipUntil()</a>
    * @see <a href="http://msdn.microsoft.com/en-us/library/hh229358.aspx">MSDN: Observable.SkipUntil</a>
    */
  def dropUntil[U](notifier: Observable[U]): Observable[T] = {
    skipUntil(notifier)
  }

  /**
    * Returns an Observable that bypasses all items from the source Observable as long as the specified
    * condition holds true. Emits all further source items as soon as the condition becomes false.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/skipWhile.png" alt="" />
    *
    * @param predicate
    * A function to test each item emitted from the source Observable for a condition.
    *
    * @return an Observable that emits all items from the source Observable as soon as the condition
    *         becomes false.
    */
  def dropWhile(predicate: (T, Int) => Boolean): Observable[T] = {
    skipWhile(predicate)
  }
  /**
    * Returns an Observable that skips the first `num` items emitted by the source
    * Observable and emits the remainder.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/skip.png" alt="" />
    *
    * @param total
    * the number of items to skip
    *
    * @return an Observable that is identical to the source Observable except that it does not
    *         emit the first `num` items that the source emits
    */
  def skip(total: Int): Observable[T] = {
    new Observable(inner.skip(total))
  }
  /**
    * Returns an Observable that skips items emitted by the source Observable until a second Observable emits an item.
    * <p>
    * <img width="640" height="375" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/skipUntil.png" alt="" />
    *
    * @param notifier the second Observable that has to emit an item before the source Observable's elements begin
    * to be mirrored by the resulting Observable
    *
    * @return an Observable that skips items from the source Observable until the second Observable emits an
    *         item, then emits the remaining items
    * @see <a href="https://github.com/ReactiveX/RxJava/wiki/Filtering-Observables#wiki-skipuntil">RxJava Wiki: skipUntil()</a>
    * @see <a href="http://msdn.microsoft.com/en-us/library/hh229358.aspx">MSDN: Observable.SkipUntil</a>
    */
  def skipUntil[U](notifier: Observable[U]): Observable[T] = {
    new Observable(inner.skipUntil(notifier))
  }
  /**
    * Returns an Observable that bypasses all items from the source Observable as long as the specified
    * condition holds true. Emits all further source items as soon as the condition becomes false.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/skipWhile.png" alt="" />
    *
    * @param predicate
    * A function to test each item emitted from the source Observable for a condition.
    *
    * @return an Observable that emits all items from the source Observable as soon as the condition
    *         becomes false.
    */
  def skipWhile(predicate: (T, Int) => Boolean): Observable[T] = {
    new Observable(inner.skipWhile(predicate))
  }
  /**
    * Returns an Observable that emits a specified item before it begins to emit items emitted by the source Observable.
    * <p>
    * <img width="640" height="315" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/startWith.png" alt="" />
    *
    * @param elem the item to emit
    *
    * @return an Observable that emits the specified item before it begins to emit items emitted by the source Observable
    */
  def +:[U >: T](elem: U): Observable[U] = {
    startWith(elem)
  }
  /**
    * Returns an Observable that emits a specified item before it begins to emit items emitted by the source Observable.
    * <p>
    * <img width="640" height="315" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/startWith.png" alt="" />
    *
    * @param elem the item to emit
    * @param scheduler
    * The [[rxscalajs.Scheduler]] to use internally to manage the timers which handle timeout for each event.
    *
    * @return an Observable that emits the specified item before it begins to emit items emitted by the source Observable
    */
  def startWith[U >: T](elem: U, scheduler: Scheduler): Observable[U] = {
    new Observable[U](inner
      .startWith(elem, scheduler))
  }
  /**
    * Returns an Observable that emits a specified item before it begins to emit items emitted by the source Observable.
    * <p>
    * <img width="640" height="315" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/startWith.png" alt="" />
    *
    * @param elem the item to emit
    *
    * @return an Observable that emits the specified item before it begins to emit items emitted by the source Observable
    */
  def startWith[U >: T](elem: U): Observable[U] = {
    new Observable[U](inner.startWith(elem))
  }
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
    * @usecase def switch[U]: Observable[U]
    * @inheritdoc
    */
  def switch[U](implicit evidence: <:<[Observable[T], Observable[Observable[U]]]): Observable[U] = {
    new Observable[U](inner.asInstanceOf[ObservableFacade[Observable[U]]].map((n: Observable[U]) => n.get).switch())
  }

  /**
    * Returns a new Observable by applying a function that you supply to each item emitted by the source
    * Observable that returns an Observable, and then emitting the items emitted by the most recently emitted
    * of these Observables.
    *
    * <img width="640" height="350" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/switchMap.png" alt="" />
    *
    * @param project a function that, when applied to an item emitted by the source Observable, returns an Observable
    *
    * @return an Observable that emits the items emitted by the Observable returned from applying a function to
    *         the most recently emitted item emitted by the source Observable
    */
  def switchMap[R](project: (T, Int) => Observable[R]): Observable[R] = {
    new Observable(inner.switchMap(toReturnFacade(project)))
  }
  /**
    * Returns a new Observable by applying a function that you supply to each item emitted by the source
    * Observable that returns an Observable, and then emitting the items emitted by the most recently emitted
    * of these Observables.
    *
    * <img width="640" height="350" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/switchMap.png" alt="" />
    *
    * @param project a function that, when applied to an item emitted by the source Observable, returns an Observable
    *
    * @return an Observable that emits the items emitted by the Observable returned from applying a function to
    *         the most recently emitted item emitted by the source Observable
    */
  def switchMap[R](project: T => Observable[R]): Observable[R] = {
    new Observable(inner.switchMap(toReturnFacade(project)))
  }

  def switchMapTo[R](innerObservable: Observable[R]): Observable[R] = {
    new Observable(inner.switchMapTo(innerObservable))
  }

  /**
    * Returns an Observable that emits only the first `num` items emitted by the source
    * Observable.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/take.png" alt="" />
    *
    * This method returns an Observable that will invoke a subscribing [[rxscalajs.subscription.ObserverFacade]]'s
    * onNext function a maximum of `num` times before invoking
    * onCompleted.
    *
    * @param total
    * the number of items to take
    *
    * @return an Observable that emits only the first `num` items from the source
    *         Observable, or all of the items from the source Observable if that Observable emits
    *         fewer than `num` items
    */
  def take(total: Int): Observable[T] = {
    new Observable(inner.take(total))
  }
  /**
    * Returns an Observable that emits only the last `count` items emitted by the source
    * Observable.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/last.png" alt="" />
    *
    * @param total
    * the number of items to emit from the end of the sequence emitted by the source
    * Observable
    *
    * @return an Observable that emits only the last `count` items emitted by the source
    *         Observable
    */
  def takeLast(total: Int): Observable[T] = {
    new Observable(inner.takeLast(total))
  }

  /**
    * Returns an Observable that emits the items from the source Observable only until the
    * `other` Observable emits an item.
    *
    * <img width="640" height="380" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/takeUntil.png" alt="" />
    *
    * @param notifier
    * the Observable whose first emitted item will cause `takeUntil` to stop
    * emitting items from the source Observable
    *
    * @return an Observable that emits the items of the source Observable until such time as
    *         `other` emits its first item
    */
  def takeUntil[U](notifier: Observable[U]): Observable[T] = {
    new Observable(inner.takeUntil(notifier))
  }
  /**
    * Returns an Observable that emits items emitted by the source Observable so long as a
    * specified condition is true.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/takeWhile.png" alt="" />
    *
    * @param predicate
    * a function that evaluates an item emitted by the source Observable and returns a
    * Boolean
    *
    * @return an Observable that emits the items from the source Observable so long as each item
    *         satisfies the condition defined by `predicate`
    */
  def takeWhile(predicate: (T, Int) => Boolean): Observable[T] = {
    new Observable(inner.takeWhile(predicate))
  }

  /**
    * Emits a value from the source Observable, then ignores subsequent source
    * values for a duration determined by another Observable, then repeats this
    * process.
    *
    * <span class="informal">It's like throttleTime, but the silencing
    * duration is determined by a second Observable.</span>
    *
    * <img src="http://reactivex.io/rxjs/img/throttle.png" width="640" height="195">
    *
    * `throttle` emits the source Observable values on the output Observable
    * when its internal timer is disabled, and ignores source values when the timer
    * is enabled. Initially, the timer is disabled. As soon as the first source
    * value arrives, it is forwarded to the output Observable, and then the timer
    * is enabled by calling the `durationSelector` function with the source value,
    * which returns the "duration" Observable. When the duration Observable emits a
    * value or completes, the timer is disabled, and this process repeats for the
    * next source value.
    *
    * @param durationSelector A function
    * that receives a value from the source Observable, for computing the silencing
    * duration for each source value, returned as an Observable or a Promise.
    *
    * @return An Observable that performs the throttle operation to
    *         limit the rate of emissions from the source.
    */
  def throttle(durationSelector: T => Observable[Int]): Observable[T] = {
    new Observable(inner.throttle(toReturnFacade(durationSelector)))
  }
  /**
    * Debounces by dropping all values that are followed by newer values before the timeout value expires. The timer resets on each `onNext` call.
    *
    * NOTE: If events keep firing faster than the timeout then no data will be emitted.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/throttleWithTimeout.png" alt="" />
    *
    * @param delay
    * The time each value has to be 'the most recent' of the [[rxscalajs.Observable]] to ensure that it's not dropped.
    * @param scheduler
    * The [[rxscalajs.Scheduler]] to use internally to manage the timers which handle timeout for each event.
    *
    * @return Observable which performs the throttle operation.
    * @see `Observable.debounce`
    */
  def throttleTime(delay: FiniteDuration, scheduler: Scheduler): Observable[T] = {
    new Observable(inner
      .throttleTime(delay.toMillis.toInt, scheduler))
  }
  /**
    * Debounces by dropping all values that are followed by newer values before the timeout value expires. The timer resets on each `onNext` call.
    *
    * NOTE: If events keep firing faster than the timeout then no data will be emitted.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/throttleWithTimeout.png" alt="" />
    *
    * @param delay
    * The time each value has to be 'the most recent' of the [[rxscalajs.Observable]] to ensure that it's not dropped.
    *
    * @return Observable which performs the throttle operation.
    * @see `Observable.debounce`
    */
  def throttleTime(delay: FiniteDuration): Observable[T] = {
    new Observable(inner.throttleTime(delay.toMillis.toInt))
  }

  /**
    * Returns an Observable that emits records of the time interval between consecutive items emitted by the
    * source Observable.
    * <p>
    * <img width="640" height="310" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/timeInterval.png" alt="" />
    *
    * @return an Observable that emits time interval information items
    */
  def timeInterval: Observable[TimeInterval[T]] = {
    new Observable(inner.timeInterval())
  }
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
    * @param scheduler Scheduler to run the timeout timers on
    *
    * @return the source Observable modified so that it will switch to the
    *         fallback Observable in case of a timeout
    */
  def timeout[U](due: Int, scheduler: Scheduler): Observable[T] = {
    new Observable(inner.timeout(due, scheduler))
  }

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
    *
    * @return the source Observable modified so that it will switch to the
    *         fallback Observable in case of a timeout
    */
  def timeout(due: Int): Observable[T] = {
    new Observable(inner.timeout(due))
  }

  /**
    * Applies a timeout policy for each item emitted by the Observable, using
    * the specified scheduler to run timeout timers. If the next item isn't
    * observed within the specified timeout duration starting from its
    * predecessor, a specified fallback Observable produces future items and
    * notifications from that point on.
    * <p>
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/timeout.2.png" alt="" />
    *
    * @param due maximum duration between items before a timeout occurs
    * @param withObservable fallback Observable to use in case of a timeout
    *
    * @return the source Observable modified to switch to the fallback
    *         Observable in case of a timeout
    */
  def timeoutWith[R](due: Int, withObservable: Observable[R]): Observable[R] = {
    new Observable(inner
      .timeoutWith(due, withObservable))
  }
  /**
    * Wraps each item emitted by a source Observable in a timestamped tuple.
    *
    * <img width="640" height="310" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/timestamp.png" alt="" />
    *
    * @return an Observable that emits timestamped items from the source Observable
    */
  def timestamp: Observable[Timestamp[T]] = {
    new Observable(inner.timestamp())
  }
  /**
    * Returns an Observable that emits a single item, a list composed of all the items emitted by
    * the source Observable.
    *
    * <img width="640" height="305" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/toList.png" alt="" />
    *
    * Normally, an Observable that returns multiple items will do so by invoking its [[rxscalajs.subscription.ObserverFacade]]'s
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
  def toSeq: Observable[scala.collection.Seq[T]] = {
    new Observable(inner.toArray()
      .map((arr: js.Array[_ <: T]) => arr.toSeq))
  }

  /**
    * Creates an Observable which produces windows of collected values. This Observable produces connected
    * non-overlapping windows. The boundary of each window is determined by the items emitted from a specified
    * boundary-governing Observable.
    *
    * <img width="640" height="475" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/window8.png" alt="" />
    *
    * @param windowBoundaries an Observable whose emitted items close and open windows. Note: This is a by-name parameter,
    * so it is only evaluated when someone subscribes to the returned Observable.
    *
    * @return An Observable which produces connected non-overlapping windows. The boundary of each window is
    *         determined by the items emitted from a specified boundary-governing Observable.
    */
  def window[I](windowBoundaries: Observable[I]): Observable[Observable[T]] = {
    new Observable(inner.window(windowBoundaries).map((o: ObservableFacade[T]) => new Observable(o)))
  }
  /**
    * Creates an Observable which produces windows of collected values. This Observable produces connected
    * non-overlapping windows. The boundary of each window is determined by the items emitted from a specified
    * boundary-governing Observable.
    *
    * <img width="640" height="475" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/window8.png" alt="" />
    *
    * @param boundaries an Observable whose emitted items close and open windows. Note: This is a by-name parameter,
    * so it is only evaluated when someone subscribes to the returned Observable.
    *
    * @return An Observable which produces connected non-overlapping windows. The boundary of each window is
    *         determined by the items emitted from a specified boundary-governing Observable.
    */
  def tumbling[I](boundaries: Observable[I]): Observable[Observable[T]] = {
    window(boundaries)
  }

  /**
    * Creates an Observable which produces windows of collected values. This Observable produces windows every
    * `skip` values, each containing `count` elements. When the source Observable completes or encounters an error,
    * the current window is emitted and the event is propagated.
    *
    * @param windowSize
    * The maximum size of each window before it should be emitted.
    * @param startWindowEvery
    * How many produced values need to be skipped before starting a new window. Note that when `skip` and
    * `count` are equal that this is the same operation as `window(int)`.
    *
    * @return
    * An [[rxscalajs.Observable]] which produces windows every `skip` values containing at most
    * `count` produced values.
    */
  def windowCount(windowSize: Int, startWindowEvery: Int = 0): Observable[Observable[T]] = {
    new Observable(inner.windowCount(windowSize, startWindowEvery)
      .map((o: ObservableFacade[T]) => new Observable(o)))
  }

  /**
    * Creates an Observable which produces windows of collected values. This Observable produces windows every
    * `skip` values, each containing `count` elements. When the source Observable completes or encounters an error,
    * the current window is emitted and the event is propagated.
    *
    * @param count
    * The maximum size of each window before it should be emitted.
    * @param skip
    * How many produced values need to be skipped before starting a new window. Note that when `skip` and
    * `count` are equal that this is the same operation as `window(int)`.
    *
    * @return
    * An [[rxscalajs.Observable]] which produces windows every `skip` values containing at most
    * `count` produced values.
    */
  def sliding(count: Int, skip: Int): Observable[Observable[T]] = {
    windowCount(count, skip)
  }

  /**
    * Creates an Observable which produces windows of collected values. This Observable produces connected
    * non-overlapping windows, each of a fixed duration specified by the `timespan` argument or a maximum size
    * specified by the `count` argument (which ever is reached first). When the source Observable completes
    * or encounters an error, the current window is emitted and the event is propagated.
    *
    * @param timespan
    * The period of time each window is collecting values before it should be emitted, and
    * replaced with a new window.
    * @param timeshift the period of time after which a new window will be created
    * @param scheduler
    * The [[rxscalajs.Scheduler]] to use when determining the end and start of a window.
    *
    * @return
    * An [[rxscalajs.Observable]] which produces connected non-overlapping windows which are emitted after
    * a fixed duration or when the window has reached maximum capacity (which ever occurs first).
    */
  def windowTime(timespan: FiniteDuration, timeshift: FiniteDuration, scheduler: Scheduler): Observable[Observable[T]] = {
    new Observable(inner.windowTime(timespan.toMillis.toInt, timeshift.toMillis.toInt, scheduler)
      .map((o: ObservableFacade[T]) => new Observable(o)))
  }
  /**
    * Creates an Observable which produces windows of collected values. This Observable produces connected
    * non-overlapping windows, each of a fixed duration specified by the `timespan` argument or a maximum size
    * specified by the `count` argument (which ever is reached first). When the source Observable completes
    * or encounters an error, the current window is emitted and the event is propagated.
    *
    * @param timespan
    * The period of time each window is collecting values before it should be emitted, and
    * replaced with a new window.
    * @param timeshift the period of time after which a new window will be created
    *
    * @return
    * An [[rxscalajs.Observable]] which produces connected non-overlapping windows which are emitted after
    * a fixed duration or when the window has reached maximum capacity (which ever occurs first).
    */
  def windowTime(timespan: FiniteDuration, timeshift: FiniteDuration): Observable[Observable[T]] = {
    new Observable(inner.windowTime(timespan.toMillis.toInt, timeshift.toMillis.toInt).map((o: ObservableFacade[T]) => new Observable(o)))
  }
  /**
    * Creates an Observable which produces windows of collected values. This Observable produces connected
    * non-overlapping windows, each of a fixed duration specified by the `timespan` argument or a maximum size
    * specified by the `count` argument (which ever is reached first). When the source Observable completes
    * or encounters an error, the current window is emitted and the event is propagated.
    *
    * @param timespan
    * The period of time each window is collecting values before it should be emitted, and
    * replaced with a new window.
    * @param scheduler
    * The [[rxscalajs.Scheduler]] to use when determining the end and start of a window.
    *
    * @return
    * An [[rxscalajs.Observable]] which produces connected non-overlapping windows which are emitted after
    * a fixed duration or when the window has reached maximum capacity (which ever occurs first).
    */
  def windowTime(timespan: FiniteDuration, scheduler: Scheduler): Observable[Observable[T]] = {
    new Observable(inner.windowTime(timespan.toMillis.toInt, scheduler = scheduler)
      .map((o: ObservableFacade[T]) => new Observable(o)))
  }
  /**
    * Creates an Observable which produces windows of collected values. This Observable produces connected
    * non-overlapping windows, each of a fixed duration specified by the `timespan` argument or a maximum size
    * specified by the `count` argument (which ever is reached first). When the source Observable completes
    * or encounters an error, the current window is emitted and the event is propagated.
    *
    * @param timespan
    * The period of time each window is collecting values before it should be emitted, and
    * replaced with a new window.
    *
    * @return
    * An [[rxscalajs.Observable]] which produces connected non-overlapping windows which are emitted after
    * a fixed duration or when the window has reached maximum capacity (which ever occurs first).
    */
  def windowTime(timespan: FiniteDuration): Observable[Observable[T]] = {
    new Observable(inner.windowTime(timespan.toMillis.toInt).map((o: ObservableFacade[T]) => new Observable(o)))
  }

  /**
    * Creates an Observable which produces windows of collected values. Chunks are created when the specified `openings`
    * Observable produces an object. That object is used to construct an Observable to emit windows, feeding it into `closings` function.
    * Windows are emitted when the created Observable produces an object.
    *
    * @param openings
    * The [[rxscalajs.Observable]] which when it produces an object, will cause
    * another window to be created.
    * @param closingSelector
    * The function which is used to produce an [[rxscalajs.Observable]] for every window created.
    * When this [[rxscalajs.Observable]] produces an object, the associated window
    * is emitted.
    *
    * @return
    * An [[rxscalajs.Observable]] which produces windows which are created and emitted when the specified [[rxscalajs.Observable]]s publish certain objects.
    */
  def windowToggle[U, O](openings: Observable[O])(closingSelector: O => Observable[U]): Observable[Observable[T]] = {
    new Observable(inner.windowToggle(openings, toReturnFacade(closingSelector))
      .map((o: ObservableFacade[T]) => new Observable(o)))
  }

  /**
    * Creates an Observable which produces windows of collected values. This Observable produces connected
    * non-overlapping windows. The boundary of each window is determined by the items emitted from a specified
    * boundary-governing Observable.
    *
    * <img width="640" height="475" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/window8.png" alt="" />
    *
    * @param closingSelector an Observable whose emitted items close and open windows. Note: This is a by-name parameter,
    * so it is only evaluated when someone subscribes to the returned Observable.
    *
    * @return An Observable which produces connected non-overlapping windows. The boundary of each window is
    *         determined by the items emitted from a specified boundary-governing Observable.
    */
  def windowWhen[U](closingSelector: () => Observable[U]): Observable[Observable[T]] = {
    new Observable(inner.windowWhen(toReturnFacade(closingSelector))
      .map((o: ObservableFacade[T]) => new Observable(o)))
  }
  /**
    * Merges the specified [[Observable]] into this [[Observable]] sequence by using the `resultSelector`
    * function only when the source [[Observable]] (this instance) emits an item.
    *
    * <img width="640" height="380" src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/withLatestFrom.png" alt="">
    *
    * @param other the other [[Observable]]
    * @param project the function to call when this [[Observable]] emits an item and the other [[Observable]] has already
    * emitted an item, to generate the item to be emitted by the resulting [[Observable]]
    *
    * @return an [[Observable]] that merges the specified [[Observable]] into this [[Observable]] by using the
    *         `resultSelector` function only when the source [[Observable]] sequence (this instance) emits an item
    * @see <a href="http://reactivex.io/documentation/operators/combinelatest.html">ReactiveX operators documentation: CombineLatest</a>
    * @since (if this graduates from Experimental/Beta to supported, replace this parenthetical with the release number)
    */
  def withLatestFromWith[U, R](other: Observable[U])(project: (T, U) => R): Observable[R] = {
    new Observable(inner.withLatestFrom(other, project))
  }
  /**
    * Merges the specified [[Observable]] into this [[Observable]] sequence by using the `resultSelector`
    * function only when the source [[Observable]] (this instance) emits an item.
    *
    * <img width="640" height="380" src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/withLatestFrom.png" alt="">
    *
    * @param other the other [[Observable]]
    *
    * @return an [[Observable]] that merges the specified [[Observable]] into this [[Observable]] by combining
    *         the elements into a tuple only when the source [[Observable]] sequence (this instance) emits an item
    * @see <a href="http://reactivex.io/documentation/operators/combinelatest.html">ReactiveX operators documentation: CombineLatest</a>
    * @since (if this graduates from Experimental/Beta to supported, replace this parenthetical with the release number)
    */
  def withLatestFrom[U](other: Observable[U]): Observable[(T, U)] = {
    new Observable(inner.withLatestFrom(other, (t: T, u: U) => (t, u)))
  }

  /**
    * Returns an Observable that emits items that are the result of applying a specified function to pairs of
    * values, one each from the source Observable and a specified Iterable sequence.
    * <p>
    * <img width="640" height="380" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/zip.i.png" alt="" />
    * <p>
    * Note that the `other` Iterable is evaluated as items are observed from the source Observable  it is
    * not pre-consumed. This allows you to zip infinite streams on either side.
    *
    * @param that the Iterable sequence
    * @param project a function that combines the pairs of items from the Observable and the Iterable to generate
    * the items to be emitted by the resulting Observable
    *
    * @return an Observable that pairs up values from the source Observable and the `other` Iterable
    *         sequence and emits the results of `selector` applied to these pairs
    */
  def zipWith[U, R](that: Observable[U])(project: (T, U) => R): Observable[R] = {
    new Observable(inner.zip(that, project))
  }

  /**
    * Returns an Observable formed from this Observable and another Observable by combining
    * corresponding elements in pairs.
    * The number of `onNext` invocations of the resulting `Observable[(T, U)]`
    * is the minumum of the number of `onNext` invocations of `this` and `that`.
    *
    * @param that the Observable to zip with
    *
    * @return an Observable that pairs up values from `this` and `that` Observables.
    */
  def zip[U](that: Observable[U]): Observable[(T, U)] = {
    new Observable(inner.zip(that, (a: T, b: U) => (a, b)))
  }

  /**
    * Zips this Observable with its indices.
    *
    * @return An Observable emitting pairs consisting of all elements of this Observable paired with
    *         their index. Indices start at 0.
    */
  def zipWithIndex: Observable[(T, Int)] = {
    this.mapWithIndex((e, n) => (e, n))
  }

  /**
    *
    * Call this method to subscribe an [[Observer]] for receiving
    * items and notifications from the Observable.
    *
    * A typical implementation of `subscribe` does the following:
    *
    * It stores a reference to the Observer in a collection object, such as a `List[T]` object.
    *
    * It returns a reference to the [[subscription.Subscription]] interface. This enables Observers to
    * unsubscribe, that is, to stop receiving items and notifications before the Observable stops
    * sending them, which also invokes the Observer's [[Observer.complete() complete]] method.
    *
    * An `Observable[T]` instance is responsible for accepting all subscriptions
    * and notifying all Observers. Unless the documentation for a particular
    * `Observable[T]` implementation indicates otherwise, Observers should make no
    * assumptions about the order in which multiple Observers will receive their notifications.
    *
    * @return a [[subscription.Subscription]] reference whose `unsubscribe` method can be called to  stop receiving items
    *         before the Observable has finished sending them
    * @see <a href="http://reactivex.io/documentation/operators/subscribe.html">ReactiveX operators documentation: Subscribe</a>
    */
  def subscribe(
    onNext: T => Unit,
    error: js.Any => Unit = e => (),
    complete: () => Unit = () => ()
  ): Subscription = {
    inner.subscribe(onNext, error, complete)
  }

  /**
    *
    * Call this method to subscribe an [[Observer]] for receiving
    * items and notifications from the Observable.
    *
    * A typical implementation of `subscribe` does the following:
    *
    * It stores a reference to the Observer in a collection object, such as a `List[T]` object.
    *
    * It returns a reference to the [[subscription.Subscription]] interface. This enables Observers to
    * unsubscribe, that is, to stop receiving items and notifications before the Observable stops
    * sending them, which also invokes the Observer's [[Observer.complete()]] method.
    *
    * An `Observable[T]` instance is responsible for accepting all subscriptions
    * and notifying all Observers. Unless the documentation for a particular
    * `Observable[T]` implementation indicates otherwise, Observers should make no
    * assumptions about the order in which multiple Observers will receive their notifications.
    *
    * @return a [[subscription.Subscription]] reference whose `unsubscribe` method can be called to  stop receiving items
    *         before the Observable has finished sending them
    * @see <a href="http://reactivex.io/documentation/operators/subscribe.html">ReactiveX operators documentation: Subscribe</a>
    */
  def subscribe(observer: Observer[T]): AnonymousSubscription = {
    val complete: () => Unit = observer.complete _
    inner
      .subscribe(observer.next _: js.Function1[T, Unit],
        observer.error _: js.Function1[js.Any, Unit],
        complete)
  }

  /**
    * Call this method to receive items from this observable.
    *
    * @param onNext this function will be called whenever the Observable emits an item
    *
    * @return a [[subscription.Subscription]] reference whose `unsubscribe` method can be called to  stop receiving items
    *         before the Observable has finished sending them
    * @see <a href="http://reactivex.io/documentation/operators/subscribe.html">ReactiveX operators documentation: Subscribe</a>
    */
  def apply(onNext: T => Unit) = {
    subscribe(onNext)
  }

  private def get = inner

  implicit def toFacade[A](observable: Observable[A]): ObservableFacade[A] = observable.get

  private def toFacadeFunction[U, I](func: Observable[I] => U): ObservableFacade[I] => U = {
    (facade) => {
      func(new Observable(
        facade))
    }
  }
  private def toFacadeFunction[U, U2, I, R](func: (U, U2, Observable[I]) => R): (U, U2, ObservableFacade[I]) => R = {
    (
      arg,
      arg2,
      obs
    ) => {
      func(arg, arg2, new Observable(obs))
    }
  }

  private def toReturnFacade[I](func: () => Observable[I]): () => ObservableFacade[I] = () => func()
  private def toReturnFacade[U, I](func: U => Observable[I]): U => ObservableFacade[I] = (arg) => func(arg)
  private def toReturnFacade[U, U2, I](func: (U, U2) => Observable[I]): (U, U2) => ObservableFacade[I] = {
    (
      arg,
      arg2
    ) => {
      func(arg, arg2)
    }
  }

}

object Observable {

  type Creator = Unit | (() => Unit)

  /**
    * Returns an Observable that will execute the specified function when someone subscribes to it.
    *
    * <img width="640" height="200" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/create.png" alt="" />
    *
    * Write the function you pass so that it behaves as an Observable: It should invoke the
    * Subscriber's `next`, `error`, and `completed` methods appropriately.
    *
    *
    * See <a href="http://go.microsoft.com/fwlink/?LinkID=205219">Rx Design Guidelines (PDF)</a> for detailed
    * information.
    *
    * See `<a href="https://github.com/ReactiveX/RxScala/blob/0.x/examples/src/test/scala/examples/RxScalaDemo.scala">RxScalaDemo</a>.createExampleGood`
    * and `<a href="https://github.com/ReactiveX/RxScala/blob/0.x/examples/src/test/scala/examples/RxScalaDemo.scala">RxScalaDemo</a>.createExampleGood2`.
    *
    * @tparam T
    * the type of the items that this Observable emits
    * @param f
    * a function that accepts a `Observer[T]`, and invokes its `next`,
    * `onError`, and `onCompleted` methods as appropriate
    * @return an Observable that, when someone subscribes to it, will execute the specified
    *         function
    */
  def apply[T](f: Observer[T] => Creator): Observable[T] = {
    Observable.create(f)
  }

  /**
    * Converts a sequence of values into an Observable.
    *
    * <img width="640" height="315" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/from.png" alt="" />
    *
    * Implementation note: the entire array will be immediately emitted each time an [[rxscalajs.subscription.ObserverFacade]] subscribes.
    * Since this occurs before the [[subscription.Subscription]] is returned,
    * it in not possible to unsubscribe from the sequence before it completes.
    *
    * @param values
    * the source Array
    * @tparam T
    * the type of items in the Array, and the type of items to be emitted by the
    * resulting Observable
    * @return an Observable that emits each item in the source Array
    */
  def just[T](values: T*): Observable[T] = {
    new Observable(ObservableFacade.of[T](values: _*))
  }

  /**
    * Returns an Observable that will execute the specified function when someone subscribes to it.
    *
    * <img width="640" height="200" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/create.png" alt="" />
    *
    * Write the function you pass so that it behaves as an Observable: It should invoke the
    * Subscriber's `next`, `error`, and `completed` methods appropriately.
    *
    *
    * See <a href="http://go.microsoft.com/fwlink/?LinkID=205219">Rx Design Guidelines (PDF)</a> for detailed
    * information.
    *
    * See `<a href="https://github.com/ReactiveX/RxScala/blob/0.x/examples/src/test/scala/examples/RxScalaDemo.scala">RxScalaDemo</a>.createExampleGood`
    * and `<a href="https://github.com/ReactiveX/RxScala/blob/0.x/examples/src/test/scala/examples/RxScalaDemo.scala">RxScalaDemo</a>.createExampleGood2`.
    *
    * @tparam T
    * the type of the items that this Observable emits
    * @param f
    * a function that accepts a `Observer[T]`, and invokes its `next`,
    * `onError`, and `onCompleted` methods as appropriate.
    * It can also return a Teardown function that will be called once the Observable is disposed.
    * @return an Observable that, when someone subscribes to it, will execute the specified
    *         function
    */
  def create[T](f: Observer[T] => Creator): Observable[T] = {
    import ObservableFacade.CreatorFacade

    def toObserver(o: ObserverFacade[T]): Observer[T] = {
      new Observer[T] {
        override def next(t: T) = o.next(t)
        override def error(a: js.Any) = o.error(a)
        override def complete() = o.complete()
      }
    }
    def unitOrFn(in: Creator): CreatorFacade = {
      in match {
        case b: (() => Unit) => b: js.Function0[Unit]
        case _ => (): Unit
      }
    }
    def toFacadeFunction(func: Observer[T] => Creator): ObserverFacade[T] => CreatorFacade = {
      (facade) => {
        val observer = toObserver(facade)
        func.andThen[CreatorFacade](unitOrFn).apply(observer)
      }
    }

    new Observable(ObservableFacade.create(toFacadeFunction(f)))
  }

  def ajax(url: String): Observable[js.Dynamic] = new Observable(ObservableFacade.ajax(url))

  def ajax(settings: js.Object): Observable[js.Dynamic] = new Observable(ObservableFacade.ajax(settings))
  /**
    * Converts a callback API to a function that returns an Observable.
    *
    * <span class="informal">Give it a function `f` of type `f(x, callback)` and
    * it will return a function `g` that when called as `g(x)` will output an
    * Observable.</span>
    *
    * `bindCallback` is not an operator because its input and output are not
    * Observables. The input is a function `func` with some parameters, but the
    * last parameter must be a callback function that `func` calls when it is
    * done. The output of `bindCallback` is a function that takes the same
    * parameters as `func`, except the last one (the callback). When the output
    * function is called with arguments, it will return an Observable where the
    * results will be delivered to.
    *
    * @param callbackFunc Function with a callback as the last parameter.
    * @param selector A function which takes the arguments from the
    * callback and maps those a value to emit on the output Observable.
    * @param scheduler The scheduler on which to schedule the
    * callbacks.
    *
    * @return A function which returns the
    *         Observable that delivers the same values the callback would deliver.
    *
    */
  def bindCallback[T, U](
    callbackFunc: js.Function,
    selector: js.Function,
    scheduler: Scheduler
  ): js.Function1[U, ObservableFacade[T]] = {
    ObservableFacade.bindCallback(callbackFunc, selector, scheduler)
  }

  /**
    * Converts a Node.js-style callback API to a function that returns an
    * Observable.
    *
    * <span class="informal">It's just like `bindCallback`, but the
    * callback is expected to be of type `callback(error, result)`.</span>
    *
    * `bindNodeCallback` is not an operator because its input and output are not
    * Observables. The input is a function `func` with some parameters, but the
    * last parameter must be a callback function that `func` calls when it is
    * done. The callback function is expected to follow Node.js conventions,
    * where the first argument to the callback is an error, while remaining
    * arguments are the callback result. The output of `bindNodeCallback` is a
    * function that takes the same parameters as `func`, except the last one (the
    * callback). When the output function is called with arguments, it will
    * return an Observable where the results will be delivered to.
    *
    * @param callbackFunc Function with a callback as the last parameter.
    * @param  selector A function which takes the arguments from the
    * callback and maps those a value to emit on the output Observable.
    * @param  scheduler The scheduler on which to schedule the
    * callbacks.
    *
    * @return A function which returns the
    *         Observable that delivers the same values the callback would deliver.
    *
    */
  def bindNodeCallback[T, U](
    callbackFunc: js.Function,
    selector: js.Function,
    scheduler: Scheduler
  ): js.Function1[U, ObservableFacade[T]] = {
    ObservableFacade.bindNodeCallback(callbackFunc, selector, scheduler)
  }

  /**
    *
    * @param sources
    * @tparam T
    *
    * @return
    */
  def forkJoin[T](sources: Observable[T]*): Observable[scala.collection.Seq[T]] = {
    val facade = ObservableFacade.forkJoin(sources.map(_.inner): _*)
    val func: js.Function1[js.Array[_ <: T], scala.collection.Seq[T]] = (n: js.Array[_ <: T]) => n.toSeq
    new Observable(facade.map(func))
  }

  /**
    * Creates an Observable that emits events of a specific type coming from the given event target.
    *
    * Creates an Observable by attaching an event listener to an "event target", which may be an object with addEventListener and removeEventListener, a Node.js EventEmitter, a jQuery style EventEmitter, a NodeList from the DOM, or an HTMLCollection from the DOM.
    * The event handler is attached when the output Observable is subscribed, and removed when the Subscription is unsubscribed.
    *
    * @param element
    * @param eventName
    *
    * @return
    */
  def fromEvent(element: Element, eventName: String) = {
    new Observable[Event](ObservableFacade
      .fromEvent(element, eventName))
  }

  /**
    * Converts a `Seq` into an Observable.
    *
    * <img width="640" height="315" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/from.png" alt="" />
    *
    * Note: the entire iterable sequence is immediately emitted each time an
    * Observer subscribes. Since this occurs before the
    * `Subscription` is returned, it is not possible to unsubscribe from
    * the sequence before it completes.
    *
    * @param seq the source `Seq` sequence
    * @tparam T the type of items in the `Seq` sequence and the
    * type of items to be emitted by the resulting Observable
    *
    * @return an Observable that emits each item in the source `Iterable`
    *         sequence
    */
  def from[T](seq: Seq[T]): Observable[T] = {
    Observable.just(seq: _*)
  }

  /** Returns an Observable emitting the value produced by the Future as its single item.
    * If the future fails, the Observable will fail as well.
    *
    * @param future Future whose value ends up in the resulting Observable
    *
    * @return an Observable completed after producing the value of the future, or with an exception
    */
  def from[T](future: Future[T])(implicit execContext: ExecutionContext): Observable[T] = {

    Observable.create[T](observer => {
      future onComplete {
        case Success(value) => observer.next(value); observer.complete()
        case Failure(err) => observer.error(err.asInstanceOf[js.Any]); observer.complete()
      }
    })
  }

  /**
    * Combines a list of source Observables by emitting an item that aggregates the latest values of each of
    * the source Observables each time an item is received from any of the source Observables, where this
    * aggregation is defined by a specified function.
    *
    * @tparam T the common base type of source values
    * @tparam R the result type
    * @param sources the list of source Observables
    * @param combineFunction the aggregation function used to combine the items emitted by the source Observables
    *
    * @return an Observable that emits items that are the result of combining the items emitted by the source
    *         Observables by means of the given aggregation function
    */
  def combineLatest[T, R](sources: Seq[Observable[T]])(combineFunction: Seq[T] => R): Observable[R] = {
    val func = combineFunction.asInstanceOf[js.Array[_ <: T] => R]
    _combineLatest(sources.map(_.inner).toJSArray, func)
  }

  private def _combineLatest[T, R](
    sources: js.Array[ObservableFacade[T]],
    combineFunction: js.Array[_ <: T] => R
  ): Observable[R] = {
    new Observable(ObservableFacade.combineLatest(sources, combineFunction))
  }

  /**
    * Emits `0`, `1`, `2`, `...` with a delay of `duration` between consecutive numbers.
    *
    * <img width="640" height="195" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/interval.png" alt="" />
    *
    * @param duration
    * duration between two consecutive numbers
    *
    * @return An Observable that emits a number each time interval.
    */
  def interval(duration: Duration = Duration.Zero): Observable[Int] = {
    new Observable(ObservableFacade.interval(duration.toMillis.toInt))
  }

  /**
    * Converts a sequence of values into an Observable.
    *
    * <img width="640" height="315" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/from.png" alt="" />
    *
    * Implementation note: the entire array will be immediately emitted each time an [[rxscalajs.subscription.ObserverFacade]] subscribes.
    * Since this occurs before the [[subscription.Subscription]] is returned,
    * it in not possible to unsubscribe from the sequence before it completes.
    *
    * @param elements
    * the source Array
    * @tparam T
    * the type of items in the Array, and the type of items to be emitted by the
    * resulting Observable
    *
    * @return an Observable that emits each item in the source Array
    */
  def of[T](elements: T*): Observable[T] = {
    just(elements: _*)
  }

  /**
    * Returns an Observable that mirrors the first source Observable to emit an item from the combination of this Observable and supplied Observables
    *
    * @param    observables sources used to race for which Observable emits first.
    *
    * @return an Observable that mirrors the output of the first Observable to emit an item.
    *
    */
  def race[T](observables: Observable[T]*): Observable[T] = {
    new Observable(ObservableFacade.race(observables.map(_.inner): _*))
  }

  /**
    * Returns an Observable that emits `0L` after a specified delay, and then completes.
    *
    * <img width="640" height="200" src="https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/timer.png" alt="" />
    *
    * @param initialDelay the initial delay before emitting a single `0L`
    *
    * @return Observable that emits `0L` after a specified delay, and then completes
    */
  def timer(initialDelay: Int = 0, period: Int = -1): Observable[Int] = {
    new Observable(ObservableFacade
      .timer(initialDelay, period))
  }

  /**
    * Creates an Observable that emits a sequence of numbers within a specified
    * range.
    *
    * Emits a sequence of numbers in a range.
    *
    * <img src="http://reactivex.io/rxjs/img/range.png" width="640" height="195">
    *
    * `range` operator emits a range of sequential integers, in order, where you
    * select the `start` of the range and its `length`. By default, uses no
    * Scheduler and just delivers the notifications synchronously, but may use
    * an optional Scheduler to regulate those deliveries.
    *
    * @param start The value of the first integer in the sequence.
    * @param count The number of sequential integers to generate.
    * the emissions of the notifications.
    *
    * @return An Observable of numbers that emits a finite range of
    *         sequential integers.
    */
  def range(start: Int = 0, count: Int = 0): Observable[Int] = {
    new Observable(ObservableFacade.range(start, count))
  }

  /**
    * Given a number of observables, returns an observable that emits a combination of each.
    * The first emitted combination will contain the first element of each source observable,
    * the second combination the second element of each source observable, and so on.
    *
    * @param project
    * a function that combines the items from the Observable and the Iterable to generate
    * the items to be emitted by the resulting Observable
    *
    * @return an Observable that emits the zipped Observables
    */
  def zip[T, R](observables: Seq[Observable[T]])(project: Seq[T] => R): Observable[R] = {
    val func = project.asInstanceOf[js.Array[_ <: T] => R]
    new Observable(ObservableFacade.zip(observables.map(_.inner).toJSArray, func))
  }

}
