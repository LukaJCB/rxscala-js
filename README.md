# RxScala.js  [![Build Status](https://travis-ci.org/LukaJCB/rxscala-js.svg?branch=master)](https://travis-ci.org/LukaJCB/rxscala-js) [![Scala.js](https://www.scala-js.org/assets/badges/scalajs-0.6.6.svg)](https://www.scala-js.org)  [![Gitter](https://badges.gitter.im/rxscala-js/Lobby.svg)](https://gitter.im/rxscala-js/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge) [![Maven Central](https://img.shields.io/maven-central/v/com.github.lukajcb/rxscala-js_sjs0.6_2.12.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.lukajcb/rxscala-js_sjs0.6_2.12)
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
  .subscribe(n => println(s"Clicked $n times"))
```

Getting Started
-----

Add the following to your sbt build definition:


    libraryDependencies += "com.github.lukajcb" %%% "rxscala-js" % "0.15.0"


then import the types from the package `rxscalajs`.

### Javascript Dependencies

RxScala.js doesn't actually come bundled with the underlying `rx.js` file, so you'll need to either add them manually or specify them as `jsDependencies`:

    jsDependencies += "org.webjars.npm" % "rxjs" % "5.4.0" / "bundles/Rx.min.js" commonJSName "Rx"

## Differences from RxJS

Similary to RxScala, this wrapper attempts to expose an API which is as Scala-idiomatic as possible. Some examples:

```scala
 // instead of concat:
def ++[U >: T](that: Observable[U]): Observable[U]

// curried in Scala collections, so curry fold also here:
def foldLeft[R](seed: R)(accumulator: (R, T) => R): Observable[R] 

// called skip in RxJS, but drop in Scala
def drop(n: Int): Observable[T] 

// like in the collection API
def zipWithIndex: Observable[(T, Int)] 

// the implicit evidence argument ensures that switch can only be called on Observables of Observables:
def switch[U](implicit evidence: Observable[T] <:< Observable[Observable[U]]): Observable[U]

```


## Documentation

RxScala.js: 

- The API documentation can be found [here](http://lukajcb.github.io/rxscala-js/latest/api/rxscalajs/Observable.html).


RxJs:

- [API Documentation](http://reactivex.io/rxjs)


If you're new to Rx, I suggest starting with [this interactive tutorial.](http://reactivex.io/learnrx/)

## Samples

 - [The basics](https://github.com/LukaJCB/RxScalaJsSamples/blob/master/src/main/scala/samples/main/Samples.scala) - How to use some of the most important operations in RxScala.js 
 - [Spaceship Reactive](https://lukajcb.github.io/RxScalaJsSamples/) - A port of Spaceship Reactive found in Sergi Mansillas awesome book [Reactive Programming with RxJS](https://pragprog.com/book/smreactjs/reactive-programming-with-rxjs). Code can be found [here](https://github.com/LukaJCB/RxScalaJsSamples). 
 - [RxScala.js as a state store](https://github.com/LukaJCB/RxScalaJsSamples/blob/master/src/main/scala/samples/main/StateStore.scala) - A basic example on how to write a simple state store with RxScala.js. (Find the working example [here](https://lukajcb.github.io/RxScalaJsSamples/assets/state-store.html))

 


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

