package rxscalajs

import cats.{Applicative, Functor, Monad}
import rxscalajs.facade.{GroupedObservableFacade, ObservableFacade}
import rxscalajs.subjects.{AsyncSubject, BehaviorSubject, ReplaySubject}
import rxscalajs.subscription._
import utest._

import scala.collection.mutable
import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._
import scala.scalajs.js
import scala.scalajs.js.|


object ObservableTest extends TestSuite {

  type Creator = Unit | js.Function0[Unit]

  def tests = TestSuite {
    val unit = (n: Any) => ()

    def toList[A](o: Observable[A]): List[A] = {
      val buffer = mutable.ArrayBuffer[A]()

      o(x => buffer.append(x))

      buffer.toList
    }

    'FacadeTests {
      val obs = ObservableFacade.of(1,11,21,1211,111221)
      val intervalObs = ObservableFacade.interval(100).take(5)
      val hoObs = ObservableFacade.of(obs).take(2)
      val notiObs = ObservableFacade.of(Notification.createNext(3),Notification.createComplete())
      'BufferCount {
        obs.bufferCount(2).subscribe(unit)
        obs.bufferCount(2, 1).subscribe(unit)
      }
      'BufferTime {
        intervalObs.bufferTime(1000).subscribe(unit)
        intervalObs.bufferTime(1000, 1200).subscribe(unit)
      }
      'CombineAll {
        val func = (n: js.Array[js.Any]) => "Hello"
        hoObs.combineAll(func).subscribe(unit)
      }
      'CombineLatest {
        obs.combineLatest(intervalObs).subscribe(unit)
        obs.combineLatest[Int, Int](intervalObs, (n: Int, n2: Int) => n + n2).subscribe(unit)
      }
      'Concat {
        obs.concat(intervalObs).subscribe(unit)
      }
      'ConcatAll {
        hoObs.concatAll().subscribe(unit)
      }
      'ConcatMap {
        obs.concatMap((n: Int, index: Int) => ObservableFacade.range(0, n)).subscribe(unit)
        obs.concatMap[String, Double]((n: Int, index: Int) => ObservableFacade.of("Hello", "world"), (n: Int, n2: String, index1: Int, index2: Int) => 0.4).subscribe(unit)
      }
      'ConcatMapTo {
        obs.concatMapTo(ObservableFacade.of('H')).subscribe(unit)
        obs.concatMapTo[String, Double](ObservableFacade.of("Hello"), (n: Int, n2: String, index1: Int, index2: Int) => 0.4).subscribe(unit)
      }
      'Count {
        obs.count().subscribe(unit)
        obs.count((i: Int, n: Int, ob: ObservableFacade[Int]) => i % 2 == 1).subscribe(unit)
      }
      'Debounce {
        obs.debounce((n: Int) => ObservableFacade.interval(100).take(6)).subscribe(unit)
      }
      'DebounceTime {
        obs.debounceTime(500).subscribe(unit)
      }
      'DefaultIfEmpty {
        ObservableFacade.of().defaultIfEmpty(5).subscribe(unit)
      }
      'Delay {
        obs.delay(50).subscribe(unit)
      }
      'DelayWhen {
        obs.delayWhen((n: Int) => ObservableFacade.of(34)).subscribe(unit)
        obs.delayWhen((n: Int) => ObservableFacade.of("asd"), ObservableFacade.of("as")).subscribe(unit)
      }
      'Dematerialize {
        notiObs.dematerialize().subscribe(unit)
      }

      'Distinct{
       obs.distinct().subscribe(unit)
      }
      'DistinctUntilChanged {
        obs.distinctUntilChanged().subscribe(unit)
        obs.distinctUntilChanged((n: Int, n2: Int) => n > n2).subscribe(unit)
        obs.distinctUntilChanged((n: Int, n2: Int) => n > n2, (n: Int) => n).subscribe(unit)
      }
      'Empty {
        ObservableFacade.empty().subscribe(unit)
        ObservableFacade.empty(Scheduler.async).subscribe(unit)
      }
      'Never {
        ObservableFacade.never().subscribe(unit)
      }
      'Every {
        obs.every((n: Int, n2: Int) => n > n2).subscribe(unit)
      }
      'ExhaustMap{
       hoObs.exhaustMap((n: ObservableFacade[Int], index: Int) => ObservableFacade.range(0,index)).subscribe(unit)
      }
      'Expand {
        intervalObs.expand((n: Int, n2: Int) => ObservableFacade.of(n)).take(1).subscribe(unit)
      }
      'Filter {
        obs.filter((n: Int, n2: Int) => n % 2 == 0).subscribe(unit)
      }
      'First {
        obs.first().subscribe(unit)
        obs.first(defaultValue = 4).subscribe(unit)
        obs.first(defaultValue = 4, resultSelector = (n: Int, n2: Int) => n).subscribe(unit)
        obs.first((n: Int, n2: Int) => true).subscribe(unit)
        obs.first((n: Int, n2: Int) => true, (n: Int, n2: Int) => n).subscribe(unit)
        obs.first((n: Int, n2: Int) => true, (n: Int, n2: Int) => n, 4).subscribe(unit)
        obs.first(resultSelector = (n: Int, n2: Int) => n).subscribe(unit)
      }

      'GroupBy {
        obs.groupBy((n: Int) => n % 2 == 0).subscribe(unit)
        val func: js.Function1[GroupedObservableFacade[Int, Int], ObservableFacade[Int]] = (grouped: GroupedObservableFacade[Int, Int]) => ObservableFacade.of(-1)
        obs.groupBy((n: Int) => n, (n: Int) => n, func).subscribe(unit)
        obs.groupBy((n: Int) => n, durationSelector = func).subscribe(unit)
        obs.groupBy((n: Int) => n % 2 == 0, (n: Int) => n).subscribe(unit)
      }
      'IgnoreElements {
        obs.ignoreElements().subscribe(unit)
      }
      'IsEmpty{
        obs.isEmpty().subscribe(unit)
      }
      'Last {
        obs.last().subscribe(unit)
      }
      'Map {
        obs.map((n: Int) => "n: " + n).subscribe(unit)
      }
      'MapWithIndex {
        obs.mapWithIndex((n: Int, index: Int) => "n: " + n).subscribe(unit)
      }
      'MapTo {
        obs.mapTo("A").subscribe(unit)
      }
      'Materialize {
        obs.materialize().subscribe(unit)
      }
      'Merge {
        obs.merge(intervalObs).subscribe(unit)
        obs.merge(intervalObs, 3).subscribe(unit)
      }
      'MergeAll {
        hoObs.mergeAll(3).subscribe(unit)
      }
      'MergeMap {
        obs.mergeMap((n: Int, index: Int) => ObservableFacade.of(n)).subscribe(unit)
        obs.mergeMap((n: Int, index: Int) => ObservableFacade.of(n), (out: Int, in: Int, index1: Int, index2: Int) => -1).subscribe(unit)
      }
      'MergeMapTo {
        obs.mergeMapTo(ObservableFacade.of("34")).subscribe(unit)
        obs.mergeMapTo(ObservableFacade.of(34), (out: Int, in: Int, index1: Int, index2: Int) => -1).subscribe(unit)
      }
      'Partition {
        obs.partition((n: Int) => n > 4)(0).subscribe(unit)
      }
      'Publish {
        obs.publish().subscribe(unit)
      }
      'PublishBehaviour {
        obs.publishBehavior(3).subscribe(unit)
      }
      'PublishLast {
        obs.publishLast().subscribe(unit)
      }
      'PublishReplay {
        obs.publishReplay(5).subscribe(unit)
      }
      'Race {
        intervalObs.race(js.Array(intervalObs)).subscribe(unit)
      }
      'Reduce {
        obs.reduce((n: Int, n2: Int) => n).subscribe(unit)
        obs.reduce((n: Int, n2: Int) => n, -20).subscribe(unit)
      }
      'Repeat {
        obs.repeat().take(3).subscribe(unit)
        obs.repeat(count = 2).take(7).subscribe(unit)
      }
      'Retry {
        obs.retry().subscribe(unit)
        obs.retry(4).subscribe(unit)
      }
      'RetryWhen {
        val func = (o: ObservableFacade[Any]) => o
        obs.retryWhen(func).subscribe(unit)
      }
      'Sample {
        obs.sample(intervalObs).subscribe(unit)
      }
      'SampleTime {
        intervalObs.sampleTime(500).subscribe(unit)
      }
      'Scan {
        obs.scan((n: Int, n2: Int) => n + n2).subscribe(unit)
        obs.scan((n: Int, n2: Int) => n + n2, -20).subscribe(unit)
      }
      'Share {
        obs.share().subscribe(unit)
      }
      'Single {
        obs.single((n: Int, n2: Int, o: ObservableFacade[Int]) => n == 1).subscribe(unit)
      }
      'Skip {
        obs.skip(2).subscribe(unit)
      }
      'SkipUntil {
        obs.skipUntil(intervalObs).subscribe(unit)
      }
      'SkipWhile {
        obs.skipWhile((n: Int, n2: Int) => n < 5).subscribe(unit)
      }
      'StartWith {
        obs.startWith(0).subscribe(unit)
      }
      'Switch {
        hoObs.switch().subscribe(unit)
      }
      'SwitchMap {
        val func: js.Function2[ObservableFacade[Int], Int, ObservableFacade[Int]] = (n: ObservableFacade[Int], n2: Int) => ObservableFacade.of(n2)
        hoObs.switchMap(func).subscribe(unit)
      }
      'SwitchMapTo {
        obs.switchMapTo(intervalObs).subscribe(unit)
      }
      'TakeLast {
        obs.takeLast(2).subscribe(unit)
      }
      'TakeUntil {
        obs.takeUntil(intervalObs).subscribe(unit)
      }
      'TakeWhile {
        obs.takeWhile((n: Int, n2: Int) => n > 1).subscribe(unit)
      }
      'Throttle {
        intervalObs.throttle((ev: Int) => ObservableFacade.interval(1000).take(2)).subscribe(unit)
      }
      'ThrottleTime {
        intervalObs.throttleTime(200).subscribe(unit)
      }
      'Window {
        obs.window(intervalObs).subscribe(unit)
      }
      'WindowCount {
        obs.windowCount(3).subscribe(unit)
      }
      'WindowTime {
        obs.windowTime(1200).subscribe(unit)
      }
      'WindowToggle {
        obs.window(intervalObs).subscribe(unit)
      }
      'WithLatestFrom {
        obs.withLatestFrom(intervalObs).subscribe(unit)
      }
      'Zip {
        obs.zip(intervalObs).subscribe(unit)
      }
      'Create {
        val func: js.Function1[ObserverFacade[Double],ObservableFacade.CreatorFacade] = (subscriber: ObserverFacade[Double]) => {
          subscriber.next(Math.random())
          subscriber.next(Math.random())
          subscriber.next(Math.random())
          subscriber.complete(): ObservableFacade.CreatorFacade
        }
        val result = ObservableFacade.create(func)
        result.subscribe(unit)
      }
      'CreateDisposeFunction {
        var x = false
        val func: js.Function1[ObserverFacade[Double],ObservableFacade.CreatorFacade] = (subscriber: ObserverFacade[Double]) => {
          subscriber.next(Math.random())
          subscriber.next(Math.random())
          subscriber.next(Math.random())
          subscriber.complete()
          val disposer = () => {
            x = true
          }
          (disposer: js.Function0[Unit]): ObservableFacade.CreatorFacade
        }
        val result = ObservableFacade.create(func)
        result.subscribe(unit)
        assert(x)
      }
    }
    'FactoryTests {
      'CombineLatest {
        val xs = Vector(1,2,3,4).map(x => Observable.just(x))
        val obs = Observable.combineLatest(xs)
        obs(unit)
      }
      'CombineLatestWith {
        val xs = List(1,2,3,4).map(x => Observable.just(x))
        val obs = Observable.combineLatestWith(xs)(_.sum)
        obs(unit)
      }
    }
    'WrapperTests{

      val obs = Observable.just(1,11,21,1211,111221)
      val intervalObs = Observable.interval(100.millis).take(5)
      val hoObs = Observable.just(obs).take(2)
      val notiObs = Observable.just(Notification.createNext(3),Notification.createComplete())
      'BufferCount {
        obs.bufferCount(2).subscribe(unit)
        obs.bufferCount(2, 1).subscribe(unit)
      }
      'BufferTime {
        intervalObs.bufferTime(1000.millis).subscribe(unit)
        intervalObs.bufferTime(1000.millis, 1200.millis).subscribe(unit)
        intervalObs.bufferTime(1000.millis, 1200.millis,Scheduler.queue).subscribe(unit)
        intervalObs.bufferTime(1000.millis, 1200.millis,Scheduler.async).subscribe(unit)
        intervalObs.bufferTime(1000.millis, 1200.millis,Scheduler.asap).subscribe(unit)
      }
      'CombineAll {
        val combined = hoObs.combineAll.take(3)
          combined.subscribe(unit)
      }
      'CombineLatest {
        obs.combineLatest(intervalObs).subscribe(unit)
        obs.combineLatestWith(intervalObs)((n, n2) => n + n2).subscribe(unit)
      }
      'CombineLatestMultiple {
        obs.combineLatest(intervalObs, hoObs).subscribe(unit)
        obs.combineLatestWith(intervalObs, hoObs)((n, n2, n3) => n + n2).subscribe(unit)
        obs.combineLatest(intervalObs, hoObs, notiObs).subscribe(unit)
        obs.combineLatestWith(intervalObs, hoObs, notiObs)((n, n2, n3, n4) => n + n2).subscribe(unit)
      }
      'Concat {
        obs.concat(intervalObs).subscribe(unit)
      }
      'ConcatAll {
        hoObs.concatAll.subscribe(unit)
      }
      'ConcatMap {
        obs.concatMap(n=> Observable.range(0, n)).subscribe(unit)
        obs.concatMap[String](n=> Observable.of("Hello", "world")).subscribe(unit)
      }
      'ConcatMapTo {
        obs.concatMapTo(Observable.just('H')).subscribe(unit)
        obs.concatMapTo(Observable.of("Hello")).subscribe(unit)
      }
      'Count {
        obs.count.subscribe(unit)
        obs.count((i: Int, n: Int, ob: Observable[Int]) => i % 2 == 1).subscribe(unit)
      }
      'Collect {
        obs.collect { case i if i > 50 => i * i }.subscribe(unit)
      }
      'Debounce {
        obs.debounce((n: Int) => Observable.interval(100.millis).take(6)).subscribe(unit)
      }
      'DebounceTime {
        obs.debounceTime(500.millis).subscribe(unit)
      }
      'DefaultIfEmpty {
        ObservableFacade.of().defaultIfEmpty(5).subscribe(unit)
      }
      'Delay {
        obs.delay(50.millis).subscribe(unit)
      }
      'DelayWhen {
        obs.delayWhen((n: Int) => Observable.of(34)).subscribe(unit)
        obs.delayWhen((n: Int) => Observable.of("asd"), Observable.of("as")).subscribe(unit)
      }
      'Dematerialize {
        notiObs.dematerialize.subscribe(unit)
      }

      'Distinct{
        obs.distinct.subscribe(unit)
      }
      'DistinctUntilChanged {
        obs.distinctUntilChanged.subscribe(unit)
        obs.distinctUntilChanged((n: Int, n2: Int) => n > n2).subscribe(unit)
        obs.distinctUntilChanged((n: Int, n2: Int) => n > n2, (n: Int) => n).subscribe(unit)
      }

      'Every {
        obs.every((n, n2) => n > n2).subscribe(unit)
      }
      'Exhaust{
        hoObs.exhaust.subscribe(unit)
       }
      'ExhaustMap{
        hoObs.exhaustMap((n: Observable[Int], index: Int) => Observable.range(0,index)).subscribe(unit)
      }
      'Expand {
        intervalObs.expand((n: Int, n2: Int) => Observable.just(n)).take(1).subscribe(unit)
      }
      'Filter {
        obs.filter((n: Int, n2: Int) => n % 2 == 0).subscribe(unit)
      }
      'First {
        obs.first.subscribe(unit)
      }
      'FirstOrElse {
        obs.firstOrElse(2342).subscribe(unit)
      }

      'GroupBy {
        obs.groupBy(n => n % 2 == 0).subscribe(unit)
        obs.groupBy(n => n % 2 == 0, n => n+1).subscribe(unit)
      }
      'IgnoreElements {
        obs.ignoreElements.subscribe(unit)
      }
      'IsEmpty{
        obs.isEmpty.subscribe(unit)
      }
      'Last {
        obs.last.subscribe(unit)
      }
      'Map {
        obs.map(_.toString).subscribe(unit)
      }
      'MapWithIndex {
        obs.mapWithIndex((n: Int, index: Int) => "n: " + n).subscribe(unit)
      }
      'MapTo {
        obs.mapTo("A").subscribe(unit)
      }
      'Materialize {
        obs.materialize.subscribe(unit)
      }
      'Merge {
        obs.merge(intervalObs).subscribe(unit)
      }
      'MergeAll {
        hoObs.mergeAll(3).subscribe(unit)
      }
      'MergeMap {
        obs.mergeMap((n: Int) => Observable.of(n)).subscribe(unit)
        obs.mergeMap(n => Observable.just(n)).subscribe(unit)
      }
      'MergeMapTo {
        obs.mergeMapTo(Observable.of("34")).subscribe(unit)
        obs.mergeMapTo(Observable.of(34), (out: Int, in: Int, index1: Int, index2: Int) => -1).subscribe(unit)
      }
      'Partition {
        obs.partition((n: Int) => n > 4)._1.subscribe(unit)
      }
      'Publish {
        obs.publish.refCount.subscribe(unit)
      }
      'PublishLast {
        obs.publishLast.refCount.subscribe(unit)
      }
      'PublishReplay {
        obs.publishReplay(5).refCount.subscribe(unit)
      }
      'Race {
        intervalObs.race(intervalObs).subscribe(unit)
      }
      'Reduce {
        obs.reduce(_ + _).subscribe(unit)
      }
      'Fold {
        obs.foldLeft( -20)((n: Int, n2: Int) => n).subscribe(unit)
      }
      'Repeat {
        intervalObs.repeat().take(5).subscribe(unit)
        intervalObs.repeat(2).take(5).subscribe(unit)
      }
      'Retry {
        obs.retry().subscribe(unit)
        obs.retry(4).subscribe(unit)
      }
      'RetryWhen {
        val func = (o: Observable[Any]) => o
        obs.retryWhen(func).subscribe(unit)
      }
      'Sample {
        obs.sample(intervalObs).subscribe(unit)
      }
      'SampleTime {
        intervalObs.sampleTime(500.millis).subscribe(unit)
      }
      'Scan {
        obs.scan((n: Int, n2: Int) => n + n2).subscribe(unit)
        obs.scan(-20)((n: Int, n2: Int) => n + n2).subscribe(unit)
      }
      'ScanMap {
        import cats.implicits._

        val o = Observable.just(1, 2, 3, 4)
        val scanned = o.scanMap(identity)

        val list = toList(o.scan(0)(_ + _))

        assert(toList(scanned) == list)
      }
      'ScanM {
        import cats.implicits._

        val o = Observable.just(1, 2, 3, 4)
        val scanned = o.scanM(0)((acc, cur) => Option(acc + cur))

        val list = toList(o.scan(0)(_ + _).map(Option.apply))

        assert(toList(scanned) == list)
      }
      'Share {
        obs.share.subscribe(unit)
      }
      'Single {
        obs.single((n: Int, n2: Int, o: Observable[Int]) => n == 1).subscribe(unit)
      }
      'Skip {
        obs.skip(2).subscribe(unit)
      }
      'SkipUntil {
        obs.skipUntil(intervalObs).subscribe(unit)
      }
      'SkipWhile {
        obs.skipWhile((n: Int, n2: Int) => n < 5).subscribe(unit)
      }
      'StartWith {
        obs.startWith(0).subscribe(unit)
      }
      'Switch {
        hoObs.switch.subscribe(unit)
      }
      'SwitchMap {
        val func = (n: Observable[Int]) => Observable.of(n)
        hoObs.switchMap(func).subscribe(unit)
      }
      'SwitchMapTo {
        obs.switchMapTo(intervalObs).subscribe(unit)
      }
      'TakeLast {
        obs.takeLast(2).subscribe(unit)
      }
      'TakeUntil {
        obs.takeUntil(intervalObs).subscribe(unit)
      }
      'TakeWhile {
        obs.takeWhile((n: Int, n2: Int) => n > 1).subscribe(unit)
      }
      'Throttle {
        intervalObs.throttle((ev: Int) => Observable.interval(1000.millis)).subscribe(unit)
      }
      'ThrottleTime {
        intervalObs.throttleTime(200.millis).subscribe(unit)
      }
      'Timestamp {
        intervalObs.timestamp.map(tmp => (tmp.value, tmp.timestamp)).subscribe(unit)
      }
      'Window {
        obs.window(intervalObs).subscribe(unit)
      }
      'WindowCount {
        obs.windowCount(3).subscribe(unit)
      }
      'WindowTime {
        obs.windowTime(1200.millis).subscribe(unit)
      }
      'WindowToggle {
        obs.window(intervalObs).subscribe(unit)
      }
      'WithLatestFrom {
        obs.withLatestFrom(intervalObs).subscribe(unit)
        obs.withLatestFromWith(intervalObs)(_ + _).subscribe(unit)
      }
      'Zip {
        val first = Observable.of(10, 11, 12)
        val second = Observable.of(10, 11, 12)
        first zip second subscribe(unit)
        obs zip intervalObs subscribe(unit)

        first.zipWith(second)(_ + _).subscribe(unit)
      }
      'ZipWithIndex {
        obs.zipWithIndex.subscribe(unit)
      }
      'CatchError {
        val errorObs = Observable.create[Int](observer => {
          observer.next(0)
          observer.error("Error!")
        })
        errorObs.catchError(s => Observable.of(s.toString)).subscribe(unit)
      }
      'OnErrorResumeNext {
        val errorObs = Observable.create[Int](observer => {
          observer.next(0)
          observer.error("Error!")
        })
        errorObs.onErrorResumeNext(s => Observable.of(s.toString)).subscribe(unit)
      }
      'OnErrorReturn {
        val errorObs = Observable.create[Int](observer => {
          observer.next(0)
          observer.error("Error!")
        })
        errorObs.onErrorReturn(_.toString).subscribe(unit)
      }

      'Ajax {
        Observable.ajax("https://api.github.com/orgs/reactivex")
          .map(_.response.public_repos)
          .subscribe(unit)
      }
      'Create {
        val o = Observable.create[String](observer => {
          observer.next("Str")
          observer.next("Hello")
          observer.complete()
        })
        o.subscribe(unit)
      }
      'CreateDisposeFunction {
        var x = false
        val o = Observable.create[String](observer => {
          observer.next("Str")
          observer.next("Hello")
          observer.complete()
          val disposer = () => {
            x = true
          }
          disposer
        })
        o.subscribe(unit)
        assert(x)
      }
      'Empty {
        Observable.empty.subscribe(unit)
      }
      'Never {
        Observable.never.subscribe(unit)
      }
      'FromIterable {
        val iterable = List(1,24,3,35,5,34)
        val o = Observable.just(iterable: _*)
        o.subscribe(unit)
      }
      'FromFuture {
        import ExecutionContext.Implicits.global
        val future = Future {
          "Hello" * 5 * 5 + 123 * 23
        }
        val o = Observable.from(future)
        o.subscribe(unit)
      }
      'ForkJoin {
        val o = Observable.interval(300.millis).take(2)
        val o2 = Observable.interval(400.millis).take(1)

        Observable.forkJoin(o,o2).subscribe(unit)
      }
      'StartWithMany {
        val o = Observable.never.startWithMany(-3,-2,-1)
        o.bufferCount(3).subscribe(list => {
          assert(list == List(-3,-2,-1))
          assert(list != List(-3,-1,-2))
        })
      }

      'ForComprehensions {
        val forObs =
          for {
            o <- hoObs
            (i1,i2) <- o zip obs
          } yield i1 > i2
        forObs.subscribe(unit)
      }
      'SubscriptionTests {
        val sub = intervalObs.subscribe(unit)
        assert(!sub.isUnsubscribed)
        sub.unsubscribe()
        assert(sub.isUnsubscribed)
      }
      'SubscriptionObjectTests {
        val empty = Subscription.EMPTY
        assert(empty.isUnsubscribed)
      }
      'ObserverTests {
        val o = new Observer[Int] {
          override def next(n: Int) = unit(n)
          override def error(a: js.Any) = unit(a)
          override def complete() = unit()
        }

        intervalObs.subscribe(o)
      }
      'SubjectTest {
        val s = Subject[Int]()
        s.scan(0)(_ + _).startWith(0)
        s.next(10)
        s.subscribe(unit)
        s.next(10)

        intervalObs.subscribe(s)

      }
      'BehaviorSubjectTest {
        val s = BehaviorSubject[Int](12)
        s.scan(0)(_ + _).startWith(0)
        s.next(10)
        s.subscribe(unit)
        s.next(10)

        intervalObs.subscribe(s)
      }
      'AsyncSubjectTest {
        val s = AsyncSubject[Int]()
        s.scan(0)(_ + _).startWith(0)
        s.next(10)
        s.subscribe(unit)
        s.next(10)

        intervalObs.subscribe(s)
      }
      'ReplaySubjectTest {
        val s = ReplaySubject.withSize[Int](5)
        s.scan(0)(_ + _).startWith(0)
        s.next(10)
        s.subscribe(unit)
        s.next(10)

        intervalObs.subscribe(s)
      }
    }

    'InstanceTests {
      import cats.implicits._



      'Functor {
        val o = Observable.of(1,2,3)
        val mapped = Functor[Observable].map(o)(_ + 1)

        assert(toList(mapped) == List(2,3,4))

      }

      'Applicative  {
        val o = Observable.of(1,2)
        val t = Applicative[Observable].replicateA(2, o)

        assert(toList(t) == List(List(1,1), List(1,2), List(2,1), List(2,2)))

      }

      'Monad {
        val o = Observable.just(1, 2)

        val t = Monad[Observable].flatMap(o)(n => Observable.of(0 to n: _*))

        assert(toList(t) == List(0, 1, 0, 1, 2))

        val o2 = Observable.of(Observable.of(1,2), Observable.of(3))
        val flat = Monad[Observable].flatten(o2)

        assert(toList(flat) == List(1,2,3))
      }
    }


  }

}
