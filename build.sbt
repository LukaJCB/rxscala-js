lazy val root = project.in(file(".")).
  enablePlugins(ScalaJSPlugin, SiteScaladocPlugin, GhpagesPlugin)

name := "RxScala.js"

normalizedName := "rxscala-js"

version := "0.15.2"

organization := "com.github.lukajcb"

scalaVersion := "2.12.4"

crossScalaVersions := Seq("2.10.6", "2.11.11", "2.12.4")


libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.9.1",
  "org.typelevel" %%% "cats-core" % "1.0.0-RC1",
  "com.lihaoyi" %%% "utest" % "0.4.5" % Test,
  "org.typelevel" %%% "cats-testkit" % "1.0.0-RC1" % Test
)

testFrameworks += new TestFramework("utest.runner.Framework")

jsDependencies += "org.webjars.npm" % "rxjs" % "5.4.3" % Test / "bundles/Rx.min.js" commonJSName "Rx"

git.remoteRepo := "git@github.com:LukaJCB/rxscala-js.git"

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
