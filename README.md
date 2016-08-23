# RxScala.js  [![Build Status](https://travis-ci.org/LukaJCB/rxscala-js.svg?branch=master)](https://travis-ci.org/LukaJCB/rxscala-js) [![Scala.js](https://www.scala-js.org/assets/badges/scalajs-0.6.6.svg)](https://www.scala-js.org) 

This is a Scala adapter to [RxJs](http://github.com/ReactiveX/RxJs).

Example usage:

```scala
val o = Observable.interval(200).take(5)
o.subscribe(n => println("n = " + n))
Observable.just(1, 2, 3, 4).reduce(_ + _)
```

Example usage in Browser:

```scala
Observable.fromEvent(document.getElementById("btn"),"click")
  .mapTo(1)
  .scan(0)(_ + _)
  .subscribe(println)
```

Getting Started
-----

Add the following to your sbt build definition:

    resolvers +=
     "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

    libraryDependencies += "com.github.lukajcb" %%% "rxscala-js" % "0.1.0-SNAPSHOT"

then import the types from the package `rxscalajs`.

### Javascript Dependencies

RxScala.js doesn't actually come bundled with the underlying `rx.js` file, so you'll need to either add them manually or specify them as `jsDependencies`:

    jsDependencies += "org.webjars.npm" % "rxjs" % "5.0.0-beta.10" / "Rx.umd.js" commonJSName "Rx"


## Documentation

RxScala.js: 

- The API documentation can be found [here](http://lukajcb.github.io/rxscala-js/latest/api/#rxscalajs.Observable).


RxJs:

- [API Documentation](http://reactivex.io/rxjs)


If you're new to Rx, I suggest starting with [this interactive tutorial.](http://reactivex.io/learnrx/)

## Samples

 - [Spaceship Reactive](https://lukajcb.github.io/RxScalaJsSamples/) - A port of Spaceship Reactive found in Sergi Mansillas awesome book [Reactive Programming with RxJS](https://pragprog.com/book/smreactjs/reactive-programming-with-rxjs). Code can be found [here](https://github.com/LukaJCB/RxScalaJsSamples). 


## Bugs and Feedback

For bugs, questions and discussions please use the [Github Issues](https://github.com/LukaJCB/rxscala-js/issues).

## LICENSE

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

<https://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

