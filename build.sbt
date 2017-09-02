lazy val ScalametaVersion = "2.0.0-M2"

lazy val semanticdbSettings = List(
  scalaVersion := "2.12.3", // 2.11.11 is also supported.
  addCompilerPlugin("org.scalameta" % "semanticdb-scalac" % ScalametaVersion cross CrossVersion.full),
  scalacOptions += "-Yrangepos"
)

// Build a semanticdb for this project.
lazy val analyzeme = project.settings(
  semanticdbSettings
)

lazy val analyzer = project
  .settings(
    scalaVersion := "2.12.3", // 2.11.11 is also supported, regardless of scalaVersion in analyzeme.
    libraryDependencies += "org.scalameta" %% "scalameta" % ScalametaVersion,
    // (optional) we need to pass the classpath/sourcepath to our analyzer, one way to do that is with
    // sbt-buildinfo. For a cli analyzer, it makes sense to read --classpath and --sourcepath.
    buildInfoPackage := "myapp",
    buildInfoKeys := Seq[BuildInfoKey](
      "classpath" -> classDirectory.in(analyzeme, Compile).value.getAbsolutePath,
      "sourcepath" -> baseDirectory.in(ThisBuild).value.getAbsolutePath
    )
  )
  .enablePlugins(BuildInfoPlugin) // generate BuildInfo object with sourcepath and classpath.
  .dependsOn(analyzeme) // optional, but convenient to trigger compilation of analyzeme on analyzer/run.
