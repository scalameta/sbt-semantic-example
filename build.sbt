// Scala.meta semantic API is available for two most recent minor versions of Scala.
// At the time of writing, that's 2.11.8 and 2.12.1.
scalaVersion in ThisBuild := "2.11.8"

// Thanks to sbt-scalahost, scala.meta will generate a semantic database for this project on compilation.
// See https://github.com/scalameta/scalameta/issues/605 for details about the semantic database.
lazy val library = project

// To load and analyze the semantic database of `library`, add a dependency on library in the Scalameta configuration.
// sbt-scalahost will set up the correct dependencies and environment for you.
// We use the semantic database to load a "scala.meta.Mirror",
// check out /app/src/main/scala/Main.scala how that is done.
lazy val app = project.dependsOn(library % Scalameta)

