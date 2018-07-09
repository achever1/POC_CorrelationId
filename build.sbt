name := """traceId"""
organization := "org.talend"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.6"

libraryDependencies += guice
libraryDependencies += ws
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.13"
libraryDependencies += "net.logstash.logback" % "logstash-logback-encoder" % "5.1"
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "org.talend.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "org.talend.binders._"
