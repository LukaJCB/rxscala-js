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
      obs.dematerialize().subscribe(unit)
    }
    'Distinct{
      obs.distinct().subscribe(unit)
      obs.distinct((n: Int,n2: Int) => n > n2).subscribe(unit)
      obs.distinct((n: Int,n2: Int) => n > n2,Observable.of("w")).subscribe(unit)
    }



  }

}
