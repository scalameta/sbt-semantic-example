lazy val MetaVersion = {
  val buildSbt = scala.io.Source.fromFile("build.sbt").getLines.toList
  val MetaVersionRx = "lazy val MetaVersion = \"(.*?)\"".r
  buildSbt.flatMap(line => MetaVersionRx.unapplySeq(line).map(_.head)) match {
    case List(metaVersion) => metaVersion
    case _ => sys.error("cannot load MetaVersion from build.sbt")
  }
}

// sbt-scalahost enables generation of a semantic database,
// i.e. a storage for semantic information about Scala code.
// See https://github.com/scalameta/scalameta/issues/605 for details.
addSbtPlugin("org.scalameta" % "sbt-scalahost" % MetaVersion)

// sbt-coursier parallelizes downloads of dependencies.
// If you haven't tried it yet, do so - the productivity boost it provides is insane.
// It's especially useful for scala.meta, which contains more than a dozen of modules.
addSbtPlugin("io.get-coursier" % "sbt-coursier" % "1.0.0-M15")
