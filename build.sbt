name := """play-java"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.11"

libraryDependencies += javaJdbc
libraryDependencies += cache
libraryDependencies += javaWs
libraryDependencies += "org.xerial" % "sqlite-jdbc" % "3.18.0"
libraryDependencies += "com.google.guava" % "guava" % "12.0"
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.0.1"
libraryDependencies += "org.mockito" % "mockito-all" % "1.9.5" % "test"