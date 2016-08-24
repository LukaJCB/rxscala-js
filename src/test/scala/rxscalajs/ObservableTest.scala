package rxscalajs

import rxscalajs.facade.{GroupedObservableFacade, ObservableFacade, SubjectFacade}
import rxscalajs.subscription._
import utest._

import scala.scalajs.js


object ObservableTest extends TestSuite {



  def tests = TestSuite {
    val unit = (n: Any) => ()
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
       obs.distinct((n: Int,n2: Int) => n > n2).subscribe(unit)
      }
      'DistinctUntilChanged {
        obs.distinctUntilChanged().subscribe(unit)
        obs.distinctUntilChanged((n: Int, n2: Int) => n > n2).subscribe(unit)
        obs.distinctUntilChanged((n: Int, n2: Int) => n > n2, (n: Int) => n).subscribe(unit)
      }
      'Do {
        val intToUnit: js.Function1[Int, Unit] = (n: Int) => ()
        obs.`do`(intToUnit).subscribe(unit)
        obs.`do`(error = (n: Any) => ()).subscribe(unit)
        obs.`do`(intToUnit, (n: Any) => ()).subscribe(unit)
        obs.`do`(intToUnit, complete = () => ()).subscribe(unit)
        obs.`do`(error = (n: Any) => (), complete = () => ()).subscribe(unit)
        obs.`do`(intToUnit, error = (n: Any) => (), complete = () => ()).subscribe(unit)
      }  
      'ElementAt{
      obs.elementAt(2).subscribe(unit)
      obs.elementAt(20,-3).subscribe(unit)
      }
      'Every {
        obs.every((n: Int, n2: Int, o: ObservableFacade[Int]) => n > n2).subscribe(unit)
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
        obs.first((n: Int, n2: Int, src: ObservableFacade[Int]) => true).subscribe(unit)
        obs.first((n: Int, n2: Int, src: ObservableFacade[Int]) => true, (n: Int, n2: Int) => n).subscribe(unit)
        obs.first((n: Int, n2: Int, src: ObservableFacade[Int]) => true, (n: Int, n2: Int) => n, 4).subscribe(unit)
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
        obs.map((n: Int, index: Int) => "n: " + n).subscribe(unit)
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
      'Multicast {
        val func: js.Function0[SubjectFacade[Int]] = () => new SubjectFacade[Int]()
        obs.multicast(func).subscribe(unit)
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
        val func: js.Function1[ObserverFacade[Double],Unit] = (subscriber: ObserverFacade[Double]) => {
          subscriber.next(Math.random())
          subscriber.next(Math.random())
          subscriber.next(Math.random())
          subscriber.complete()
        }
        val result = ObservableFacade.create(func)
        result.subscribe(unit)
      }
    }
    'WrapperTests{

      val obs = Observable(1,11,21,1211,111221)
      val intervalObs = Observable.interval(100).take(5)
      val hoObs = Observable(obs).take(2)
      val notiObs = Observable(Notification.createNext(3),Notification.createComplete())
      'BufferCount {
        obs.bufferCount(2).subscribe(unit)
        obs.bufferCount(2, 1).subscribe(unit)
      }
      'BufferTime {
        intervalObs.bufferTime(1000).subscribe(unit)
        intervalObs.bufferTime(1000, 1200).subscribe(unit)
      }
      'CombineAll {
        val combined = hoObs.combineAll.take(3)
          combined.subscribe(unit)
      }
      'CombineLatest {
        obs.combineLatest(intervalObs).subscribe(unit)
        obs.combineLatestWith(intervalObs)((n, n2) => n + n2).subscribe(unit)
      }
      'Concat {
        obs.concat(intervalObs).subscribe(unit)
      }
      'ConcatAll {
        hoObs.concatAll.subscribe(unit)
      }
      'ConcatMap {
        obs.concatMap((n: Int, index: Int) => Observable.range(0, n)).subscribe(unit)
        obs.concatMap[String, Double]((n: Int, index: Int) => Observable.of("Hello", "world")).subscribe(unit)
      }
      'ConcatMapTo {
        obs.concatMapTo(Observable('H')).subscribe(unit)
        obs.concatMapTo(Observable("Hello")).subscribe(unit)
      }
      'Count {
        obs.count.subscribe(unit)
        obs.count((i: Int, n: Int, ob: Observable[Int]) => i % 2 == 1).subscribe(unit)
      }
      'Debounce {
        obs.debounce((n: Int) => Observable.interval(100).take(6)).subscribe(unit)
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
        obs.delayWhen((n: Int) => Observable(34)).subscribe(unit)
        obs.delayWhen((n: Int) => Observable("asd"), Observable("as")).subscribe(unit)
      }
      'Dematerialize {
        notiObs.dematerialize.subscribe(unit)
      }
       
      'Distinct{
        obs.distinct.subscribe(unit)
        obs.distinct((n: Int,n2: Int) => n > n2).subscribe(unit)
        obs.distinct((n: Int,n2: Int) => n > n2,Observable.of("w")).subscribe(unit)
      }
      'DistinctUntilChanged {
        obs.distinctUntilChanged.subscribe(unit)
        obs.distinctUntilChanged((n: Int, n2: Int) => n > n2).subscribe(unit)
        obs.distinctUntilChanged((n: Int, n2: Int) => n > n2, (n: Int) => n).subscribe(unit)
      }

      'ElementAt{
        obs.elementAt(20,-3).subscribe(unit)
      }
      'Every {
        obs.every((n: Int, n2: Int, o: Observable[Int]) => n > n2).subscribe(unit)
      }  
      'Exhaust{
        hoObs.exhaust().subscribe(unit)
       }
      'ExhaustMap{
        hoObs.exhaustMap((n: Observable[Int], index: Int) => Observable.range(0,index)).subscribe(unit)
      }
      'Expand {
        intervalObs.expand((n: Int, n2: Int) => Observable(n)).take(1).subscribe(unit)
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
        obs.ignoreElements().subscribe(unit)
      }
      'IsEmpty{
        obs.isEmpty.subscribe(unit)
      }
      'Last {
        obs.last.subscribe(unit)
      }
      'Map {
        obs.map((n: Int, index: Int) => "n: " + n).subscribe(unit)
        obs.map(_.toString).subscribe(unit)
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
        obs.mergeMap((n: Int, index: Int) => Observable(n)).subscribe(unit)
        obs.mergeMap(n => Observable.just(n)).subscribe(unit)
      }
      'MergeMapTo {
        obs.mergeMapTo(Observable("34")).subscribe(unit)
        obs.mergeMapTo(Observable(34), (out: Int, in: Int, index1: Int, index2: Int) => -1).subscribe(unit)
      }
      'Multicast {
        val func: js.Function0[SubjectFacade[Int]] = () => new SubjectFacade[Int]()
        obs.multicast(func).subscribe(unit)
      }
      'Partition {
        obs.partition((n: Int) => n > 4)._1.subscribe(unit)
      }
      'Publish {
        obs.publish.subscribe(unit)
      }
      'PublishBehaviour {
        obs.publishBehavior(3).subscribe(unit)
      }
      'PublishLast {
        obs.publishLast.subscribe(unit)
      }
      'PublishReplay {
        obs.publishReplay(5).subscribe(unit)
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
        intervalObs.sampleTime(500).subscribe(unit)
      }
      'Scan {
        obs.scan((n: Int, n2: Int) => n + n2).subscribe(unit)
        obs.scan(-20)((n: Int, n2: Int) => n + n2).subscribe(unit)
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
        val func = (n: Observable[Int], n2: Int) => Observable.of(n2)
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
        intervalObs.throttle((ev: Int) => Observable.interval(1000)).subscribe(unit)
      }
      'ThrottleTime {
        intervalObs.throttleTime(200).subscribe(unit)
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
        obs.windowTime(1200).subscribe(unit)
      }
      'WindowToggle {
        obs.window(intervalObs).subscribe(unit)
      }
      'WithLatestFrom {
        obs.withLatestFrom(intervalObs).subscribe(unit)
      }
      'Zip {
        val first = Observable(10, 11, 12)
        val second = Observable(10, 11, 12)
        first zip second subscribe(unit)
        obs zip intervalObs subscribe(unit)
      }
      'ZipWithIndex {
        obs.zipWithIndex.subscribe(unit)
      }
      'Create {
        val o = Observable.create[String](observer => {
          observer.next("Str")
          observer.next("Hello")
          observer.complete()
        })
        o.subscribe(unit)
      }

      'ForComprehensions {
          for {
            o <- hoObs
            n <- o
          } yield (n > 100)
      }
      'SubscriptionTests {
        val sub = intervalObs.subscribe(unit)
        assert(!sub.isUnsubscribed)
        sub.unsubscribe()
        assert(sub.isUnsubscribed)
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
    }







  }

}
