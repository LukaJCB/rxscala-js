lazy val root = project.in(file(".")).
  enablePlugins(ScalaJSPlugin)

name := "RxScala.js"

normalizedName := "rxscala-js"

version := "0.0.1-SNAPSHOT"

organization := "org.ltj"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.9.0",
  "com.lihaoyi" %%% "utest" % "0.4.0" % "test"
)

testFrameworks += new TestFramework("utest.runner.Framework")

jsDependencies += "org.webjars.npm" % "rxjs" % "5.0.0-beta.10" % "test" / "Rx.umd.js" commonJSName "Rx"

scalaJSUseRhino := false
