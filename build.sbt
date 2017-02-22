inThisBuild(List(
  scalaVersion := "2.11.8" // 2.12.1 is also supported
))

// Thanks to sbt-scalahost, scala.meta will generate a semantic database for this project on compilation.
// See https://github.com/scalameta/scalameta/issues/605 for details about the semantic database.
lazy val library = project

// To load and analyze the semantic database of `library`, add a dependency on library in the Scalameta configuration.
// sbt-scalahost will set up the correct dependencies and environment for you.
// We use the semantic database to load a "scala.meta.Mirror",
// check out /app/src/main/scala/Main.scala how that is done.
lazy val app = project.dependsOn(library % Scalameta)

