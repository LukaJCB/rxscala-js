lazy val root = project.in(file(".")).
  enablePlugins(ScalaJSPlugin)

name := "RxScala.js"

normalizedName := "rxscala-js"

version := "0.0.1-SNAPSHOT"

organization := "org.ltj"

scalaVersion := "2.11.8"


jsDependencies +=  "org.webjars.npm" % "reactivex__rxjs" % "5.0.0-beta.6" / "global/Rx.umd.js"

