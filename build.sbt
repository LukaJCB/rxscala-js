lazy val root = project.in(file(".")).
  enablePlugins(ScalaJSPlugin)

name := "RxScala.js"

normalizedName := "rxscala-js"

version := "0.0.1-SNAPSHOT"

organization := "org.ltj"

scalaVersion := "2.11.8"

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.0"

jsDependencies += "org.webjars.npm" % "rxjs" % "5.0.0-beta.5" / "Rx.umd.js" commonJSName "Rx"



