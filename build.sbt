// Scala.meta supports two most recent major versions of Scala.
// Scalahost, being a compiler plugin, only supports the most recent minor versions.
lazy val ScalaVersion = ScalaVersions.head
lazy val ScalaVersions = Seq("2.11.8", "2.12.1")

// The latest stable version can be found at:
//   http://scalameta.org/
// The latest prerelease version can be found at:
//   https://bintray.com/scalameta/maven/scalameta/_latestVersion
lazy val MetaVersion = "1.6.0-346-6ce2620f"

// Thanks to plugins.sbt, scala.meta will generate a semantic database for this project.
// See https://github.com/scalameta/scalameta/issues/605 for details.
lazy val library = project.settings(
  scalaVersion := ScalaVersion,
  crossScalaVersions := ScalaVersions
)

// Thanks to Mirror from scalahost, this project can load the semantic database created from library.
// Check out /app/src/main/scala/Main.scala to see what can be done on top of that.
lazy val app = project.settings(
  scalaVersion := ScalaVersion,
  crossScalaVersions := ScalaVersions,

  // This custom resolver is only necessary if you're using prerelease builds of scala.meta.
  // Stable releases are synced to Maven Central and available without any custom resolvers.
  resolvers += Resolver.bintrayIvyRepo("scalameta", "maven"),
  libraryDependencies += "org.scalameta" %% "scalameta" % MetaVersion,
  libraryDependencies += "org.scalameta" % "scalahost" % MetaVersion cross CrossVersion.full,

  // Semantic databases are dumped next to classfiles of their corresponding projects.
  // Inside, they are referring to source files from those projects.
  // As a result, semantic databases are loaded from a classpath and a sourcepath,
  // which we set up here to simplify the demonstration.
  fork in run := true,
  javaOptions := {
    val sourcepath = (sourceDirectory in Compile in library).value.getAbsolutePath
    val sourcepathOption = s"-Dlibrary.sourcepath=$sourcepath"
    val classpath = (fullClasspath in Compile in library).value.files.map(_.getAbsolutePath)
    val classpathOption = s"-Dlibrary.classpath=${classpath.mkString(java.io.File.pathSeparator)}"
    Seq(sourcepathOption, classpathOption)
  }
)
