package rxscalajs

import utest._

import scala.scalajs.js

/**
  * Created by Luka on 28.04.2016.
  */
object ObservableTest extends TestSuite {
  val obs = Observable.of(1,11,21,1211,111221)
  val intervalObs = Observable.interval(100).take(5)
  val hoObs = Observable.of(obs)
  val unit = (n: Any) => ()
  val notiObs = Observable.of(Notification.createNext(3),Notification.createComplete())
  def tests = TestSuite {
    'BufferCount{
      obs.bufferCount(2).subscribe(unit)
      obs.bufferCount(2,1).subscribe(unit)
    }
    'BufferTime{
      intervalObs.bufferTime(1000).subscribe(unit)
      intervalObs.bufferTime(1000,2000).subscribe(unit)
    }
    'CombineAll{
      hoObs.combineAll().subscribe(unit)
      val func = (n: js.Array[js.Any]) => "Hello"
      hoObs.combineAll(func).subscribe(unit)
    }
    'CombineLatest{
      obs.combineLatest(intervalObs).subscribe(unit)
      obs.combineLatest[Int,Int](intervalObs, (n: Int,n2: Int) => n + n2).subscribe(unit)
    }
    'Concat{
      obs.concat(intervalObs).subscribe(unit)
      (obs ++ intervalObs).subscribe(unit)
    }
    'ConcatAll{
      hoObs.concatAll.subscribe(unit)
    }
    'ConcatMap{
      obs.concatMap((n: Int,index: Int) => Observable.range(0,n)).subscribe(unit)
      obs.concatMap[String,Double]((n: Int,index: Int) => Observable.of("Hello", "world"),(n: Int,n2: String,index1: Int,index2: Int) => 0.4).subscribe(unit)
    }
    'ConcatMapTo{
      obs.concatMapTo(Observable.of('H')).subscribe(unit)
      obs.concatMapTo[String,Double](Observable.of("Hello"),(n: Int,n2: String,index1: Int,index2: Int) => 0.4).subscribe(unit)
    }
    'Count{
      obs.count().subscribe(unit)
      obs.count((i: Int,n: Int,ob: Observable[Int]) => i % 2 == 1).subscribe(unit)
    }
    'Debounce{
      obs.debounce((n: Int) => Observable.interval(100).take(6)).subscribe(unit)
    }
    'DebounceTime{
      obs.debounceTime(500).subscribe(unit)
    }
    'DefaultIfEmpty{
      Observable.of().defaultIfEmpty(5).subscribe(unit)
    }
    'Delay{
      obs.delay(50).subscribe(unit)
    }
    'DelayWhen{
      obs.delayWhen((n: Int) => Observable.of(34)).subscribe(unit)
      obs.delayWhen((n: Int) => Observable.of("asd"),Observable.of("as")).subscribe(unit)
    }
    'Dematerialize{
      notiObs.dematerialize().subscribe(unit)
    }
    /*
    'Distinct{
      obs.distinct().subscribe(unit)
      obs.distinct((n: Int,n2: Int) => n > n2).subscribe(unit)
      obs.distinct((n: Int,n2: Int) => n > n2,Observable.of("w")).subscribe(unit)
    }
    'DistinctKey{
      obs.distinctKey("Hello").subscribe(unit)
      obs.distinctKey("A",(n: Int,n2: Int) => n > n2).subscribe(unit)
      obs.distinctKey("A",(n: Int,n2: Int) => n > n2,Observable.of("A")).subscribe(unit)
    }*/
    'DistinctUntilChanged{
      obs.distinctUntilChanged().subscribe(unit)
      obs.distinctUntilChanged((n: Int,n2: Int) => n > n2).subscribe(unit)
      obs.distinctUntilChanged((n: Int,n2: Int) => n > n2,(n: Int) => n).subscribe(unit)
      obs.distinctUntilChanged(keySelector = (n: Int) => n).subscribe(unit)
    }/*
    'DistinctUntilKeyChanged{
      obs.distinctUntilKeyChanged("A").subscribe(unit)
      obs.distinctUntilKeyChanged("A",(n: Int,n2: Int) => n > n2).subscribe(unit)
    }*/
    'Do{
      val intToUnit: js.Function1[Int,Unit] = (n: Int) => ()
      obs.`do`(intToUnit).subscribe(unit)
      obs.`do`(error = (n: Any) => ()).subscribe(unit)
      obs.`do`(intToUnit,(n: Any) => ()).subscribe(unit)
      obs.`do`(intToUnit,complete = () => ()).subscribe(unit)
      obs.`do`(error = (n: Any) => (),complete = () => ()).subscribe(unit)
      obs.`do`(intToUnit,error = (n: Any) => (),complete = () => ()).subscribe(unit)
    }/*
    'ElementAt{
      obs.elementAt(2).subscribe(unit)
      obs.elementAt(20,-3).subscribe(unit)
    }*/
    'Every{
      obs.every((n: Int,n2: Int, o: Observable[Int]) => n > n2).subscribe(unit)
    }/*
    'Exhaust{
      hoObs.exhaust().subscribe(unit)
    }
    'ExhaustMap{
      hoObs.exhaustMap((n: Observable[Int], index: Int) => Observable.range(0,index)).subscribe(unit)
    }*/
    'Expand{
      intervalObs.expand((n: Int,n2: Int) => Observable.of(n)).take(1).subscribe(unit)
    }
    'Filter{
      obs.filter((n: Int, n2: Int) => n % 2 == 0).subscribe(unit)
    }
    'First{
      obs.first().subscribe(unit)
      obs.first(defaultValue = 4).subscribe(unit)
      obs.first(defaultValue = 4, resultSelector = (n: Int, n2: Int) => n).subscribe(unit)
      obs.first((n: Int, n2: Int, src: Observable[Int]) => true).subscribe(unit)
      obs.first((n: Int, n2: Int, src: Observable[Int]) => true,(n: Int, n2: Int) => n).subscribe(unit)
      obs.first((n: Int, n2: Int, src: Observable[Int]) => true,(n: Int, n2: Int) => n,4).subscribe(unit)
      obs.first(resultSelector = (n: Int, n2: Int) => n).subscribe(unit)
    }
    'ForEach{
      obs.forEach(unit).toFuture
    }
    'GroupBy{
      obs.groupBy((n: Int) => n % 2 == 0).subscribe(unit)
      val func: js.Function1[GroupedObservable[Int,Int],Observable[Int]] = (grouped: GroupedObservable[Int,Int]) => Observable.of(-1)
      obs.groupBy((n: Int) => n,(n: Int) => n ,func).subscribe(unit)
      obs.groupBy((n: Int) => n, durationSelector = func).subscribe(unit)
      obs.groupBy((n: Int) => n % 2 == 0,(n: Int) => n).subscribe(unit)
    }
    'IgnoreElements{
      obs.ignoreElements().subscribe(unit)
    }/*
    'IsEmpty{
      obs.isEmpty().subscribe(unit)
    }*/
    'Last{
      obs.last().subscribe(unit)
    }
    'Map{
      obs.map((n:Int, index:Int) => "n: " + n).subscribe(unit)
    }
    'MapTo{
      obs.mapTo("A").subscribe(unit)
    }
    'Materialize{
      obs.materialize().subscribe(unit)
    }
    'Merge{
      obs.merge(intervalObs).subscribe(unit)
      obs.merge(intervalObs,3).subscribe(unit)
    }
    'MergeAll{
      hoObs.mergeAll(3).subscribe(unit)
    }
    'MergeMap{
      obs.mergeMap((n: Int, index:Int) => Observable.of(n)).subscribe(unit)
      obs.mergeMap((n: Int, index:Int) => Observable.of(n),(out: Int, in: Int, index1: Int, index2: Int) => -1).subscribe(unit)
    }
    'MergeMapTo{
      obs.mergeMapTo(Observable.of("34")).subscribe(unit)
      obs.mergeMapTo(Observable.of(34),(out: Int, in: Int, index1: Int, index2: Int) => -1).subscribe(unit)
    }
    'Multicast{
      val func:js.Function0[Subject[Int]] = () => new Subject[Int]()
      obs.multicast(func).subscribe(unit)
    }
    'Partition{
      obs.partition((n: Int) => n > 4)(0).subscribe(unit)
    }
    'Publish{
      obs.publish().subscribe(unit)
    }
    'PublishBehaviour{
      obs.publishBehavior(3).subscribe(unit)
    }
    'PublishLast{
      obs.publishLast().subscribe(unit)
    }
    'PublishReplay{
      obs.publishReplay(5).subscribe(unit)
    }
    'Race{
      intervalObs.race(intervalObs).subscribe(unit)
    }
    'Reduce{
      obs.reduce((n: Int, n2: Int) => n).subscribe(unit)
      obs.reduce((n: Int, n2: Int) => n, -20).subscribe(unit)
    }
    'Repeat{
      intervalObs.repeat().subscribe(unit)
    }
    'Retry{
      obs.retry().subscribe(unit)
      obs.retry(4).subscribe(unit)
    }
    'RetryWhen{
      val func = (o: Observable[Any]) => o
      obs.retryWhen(func).subscribe(unit)
    }
    'Sample{
      obs.sample(intervalObs).subscribe(unit)
    }
    'SampleTime{
      intervalObs.sampleTime(500).subscribe(unit)
    }
    'Scan{
      obs.scan((n: Int, n2: Int) => n + n2).subscribe(unit)
      obs.scan((n: Int, n2: Int) => n + n2,-20).subscribe(unit)
    }
    'Share{
      obs.share().subscribe(unit)
    }
    'Single{
      obs.single((n: Int, n2: Int, o: Observable[Int]) => n == 1).subscribe(unit)
    }
    'Skip{
      obs.skip(2).subscribe(unit)
    }
    'SkipUntil{
      obs.skipUntil(intervalObs).subscribe(unit)
    }
    'SkipWhile{
      obs.skipWhile((n: Int, n2: Int) => n < 5).subscribe(unit)
    }
    'StartWith{
      obs.startWith(0).subscribe(unit)
    }
    'Switch{
      hoObs.switch().subscribe(unit)
    }
    'SwitchMap{
      val func: js.Function2[Observable[Int],Int,Observable[Int]] = (n:Observable[Int],n2:Int) => Observable.of(n2)
      hoObs.switchMap(func).subscribe(unit)
    }
    'SwitchMapTo{
      obs.switchMapTo(intervalObs).subscribe(unit)
    }
    'TakeLast{
      obs.takeLast(2).subscribe(unit)
    }
    'TakeUntil{
      obs.takeUntil(intervalObs).subscribe(unit)
    }
    'TakeWhile{
      obs.takeWhile((n: Int, n2: Int) => n > 1).subscribe(unit)
    }
    'Throttle{
      intervalObs.throttle((ev: Int) => Observable.interval(1000)).subscribe(unit)
    }
    'ThrottleTime{
      intervalObs.throttleTime(200).subscribe(unit)
    }
    'Window{
      obs.window(intervalObs).subscribe(unit)
    }
    'WindowCount{
      obs.windowCount(3).subscribe(unit)
    }
    'WindowTime{
      obs.windowTime(2000).subscribe(unit)
    }
    'WindowToggle{
      obs.window(intervalObs).subscribe(unit)
    }
    'WithLatestFrom{
      obs.withLatestFrom(intervalObs).subscribe(unit)
    }
    'Zip{
      obs.zip(intervalObs).subscribe(unit)
    }









  }

}
