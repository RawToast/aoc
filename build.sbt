val scala3Version = "3.3.1"

val MonocleVersion    = "3.2.0"
val MunitVersion           = "0.7.29"

lazy val root = project
  .in(file("."))
  .settings(
    name := "aoc",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      // Monocle lenses
      "dev.optics" %% "monocle-core"  % MonocleVersion,
      "dev.optics" %% "monocle-macro" % MonocleVersion,

      // Test
      "org.scalameta" %% "munit"               % MunitVersion           % Test,
    ),
    testFrameworks += new TestFramework("munit.Framework"),
    run / fork := true,
    scalacOptions ++= Seq(
      "-deprecation", // emit warning and location for usages of deprecated APIs
      // "-explain", // explain errors in more detail
      // "-explain-types", // explain type errors in more detail
      "-feature", // emit warning and location for usages of features that should be imported explicitly
      "-indent", // allow significant indentation.
      "-new-syntax", // require `then` and `do` in control expressions.
      "-print-lines", // show source code line numbers.
      "-unchecked", // enable additional warnings where generated code depends on assumptions
      "-Ykind-projector", // allow `*` as wildcard to be compatible with kind projector
      "-Xmigration" // warn about constructs whose behavior may have changed since version
    )
  )
addCommandAlias(
  "codeCoverage",
  "clean; coverage ; test ; coverageReport"
)
