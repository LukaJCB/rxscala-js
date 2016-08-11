lazy val root = project.in(file(".")).
  enablePlugins(ScalaJSPlugin)

name := "RxScala.js"

normalizedName := "rxscala-js"

version := "0.0.2"

organization := "com.github.lukajcb"

scalaVersion := "2.11.8"


libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.9.0",
  "com.lihaoyi" %%% "utest" % "0.4.0" % "test"
)

testFrameworks += new TestFramework("utest.runner.Framework")

jsDependencies += "org.webjars.npm" % "rxjs" % "5.0.0-beta.10" % "test" / "Rx.umd.js" commonJSName "Rx"

scalaJSUseRhino := false

publishMavenStyle := true

licenses += ("Apache 2", url("https://www.apache.org/licenses/LICENSE-2.0.txt"))

homepage := Some(url("https://github.com/LukaJCB/rxscala-js"))

scmInfo := Some(ScmInfo(
  url("https://github.com/LukaJCB/rxscala-js"),
  "scm:git:git@github.com:LukaJCB/rxscala-js.git",
  Some("scm:git:git@github.com:LukaJCB/rxscala-js.git")))

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

pomExtra := (
  <developers>
    <developer>
      <id>ltj</id>
      <name>Luka Jacobowitz</name>
      <url>https://github.com/LukaJCB</url>
    </developer>
  </developers>
  )

pomIncludeRepository := { _ => false }
