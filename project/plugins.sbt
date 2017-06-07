// sbt-scalahost enables generation of a semantic database,
// i.e. a storage for semantic information about Scala code.
// See https://github.com/scalameta/scalameta/issues/605 for details.
// The latest stable and pre-release version numbers can be found at http://scalameta.org/.
addSbtPlugin("org.scalameta" % "sbt-scalahost" % "1.8.0")

// sbt-coursier parallelizes downloads of dependencies.
// If you haven't tried it yet, do so - the productivity boost it provides is insane.
// It's especially useful for scala.meta, which contains more than a dozen of modules.
addSbtPlugin("io.get-coursier" % "sbt-coursier" % "1.0.0-RC3")
