name := "mail-example"
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.2"

libraryDependencies ++= Seq(
  guice,
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
  "com.typesafe.play"      %% "play-mailer"        % "8.0.0",
  "com.typesafe.play"      %% "play-mailer-guice"  % "8.0.0",
  "org.apache.poi"          % "poi"                % "4.1.2",
  "org.apache.poi"          % "poi-ooxml"          % "4.1.2"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
