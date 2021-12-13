import Dependencies._

lazy val root = (project in file("."))
  .aggregate(types, entity, app, interface, infra, api)
  .settings(
    name := "students-api",
  )
  .aggregate(api)

lazy val types = project
  .in(file("./modules/types"))
  .settings(
    name := "types",
  )
  .settings(commonSettings)
  .settings(typesSettings)

lazy val entity = project
  .in(file("./modules/entity"))
  .dependsOn(types % "compile->compile; test->test")
  .settings(
    name := "entity",
  )
  .settings(commonSettings)
  .settings(entitySettings)

lazy val app = project
  .in(file("./modules/app"))
  .dependsOn(entity % "compile->compile; test->test")
  .settings(
    name := "app",
  )
  .settings(commonSettings)
  .settings(appSettings)

lazy val interface = project
  .in(file("./modules/interface"))
  .dependsOn(app % "compile->compile; test->test")
  .settings(
    name := "interface",
  )
  .settings(commonSettings)
  .settings(interfaceSettings)

lazy val infra = project
  .in(file("./modules/infra"))
  .dependsOn(interface % "compile->compile; test->test")
  .settings(
    name := "infra",
  )
  .settings(commonSettings)
  .settings(infraSettings)

lazy val api = project
  .in(file("./modules/api"))
  .dependsOn(infra % "compile->compile; test->test")
  .settings(
    name := "api",
  )
  .settings(commonSettings)
  .settings(apiSettings)

/**
 * Common Settings
 */
lazy val commonSettings = Seq(
  scalaVersion := Versions.Scala,

  scalacOptions ++= Seq(
    /**
      * @see https://docs.scala-lang.org/overviews/compiler-options/index.html
      */
    "-encoding",
    "utf-8", // Specify character encoding used by source files.
    "-Ymacro-annotations",
    "-deprecation", // Emit warning and location for usages of deprecated APIs.
    "-unchecked", // Enable additional warnings where generated code depends on assumptions.
    "-Xlint",
    "-explaintypes", // Explain type errors in more detail.
    "-language:experimental.macros", // Allow macro definition (besides implementation and application)
    "-language:higherKinds", // Allow higher-kinded types
    "-language:implicitConversions", // Allow definition of implicit functions called views
    "-Xfatal-warnings", // Fail the compilation if there are any warnings.
    "-Ywarn-unused:imports", // Warn when imports are unused.
    "-Ywarn-numeric-widen", // Warn when numerics are widened.
    "-Xlint:-byname-implicit" // https://docs.scala-lang.org/sips/byname-implicits.html
  ),
  Compile /console / scalacOptions ~= (_.filterNot(Set("-Xlint", "-Ywarn-unused:imports"))),
  Test / testOptions += Tests.Argument("-oD"),
  Test / fork := true,
  Compile / compile / wartremoverErrors := Warts.unsafe
    .filterNot(Set(Wart.Any)) ++ Seq(
    Wart.ExplicitImplicitTypes,
    Wart.FinalCaseClass,
    Wart.FinalVal,
    Wart.LeakingSealed,
    Wart.While
  ),
  // scaladoc: Create inheritance diagrams for classes, traits and packages.
  Compile / doc / scalacOptions := Seq("-diagrams"),

  /**
   * @see https://github.com/sbt/sbt-assembly#merge-strategy
   */
  assembly / assemblyMergeStrategy:= {
    case PathList(ps @ _*) if ps.last endsWith ".properties" => MergeStrategy.first
    case PathList(ps @ _*) if ps.last endsWith ".class" => MergeStrategy.first
    case x =>
      val oldStrategy = (assembly / assemblyMergeStrategy).value
      oldStrategy(x)
  },
  assembly / test:= {},
)

/**
 * Settings
 */
lazy val typesSettings = Seq(
  libraryDependencies ++= commonDependencies ++ testDependencies
)
lazy val entitySettings = Seq(
  libraryDependencies ++= commonDependencies ++ entityDependencies ++ testDependencies
)
lazy val appSettings = Seq(
  libraryDependencies ++= commonDependencies ++ appDependencies ++ testDependencies
)
lazy val interfaceSettings = Seq(
  libraryDependencies ++= commonDependencies ++ interfaceDependencies ++ testDependencies
)
lazy val infraSettings = Seq(
  libraryDependencies ++= commonDependencies ++ infraDependencies ++ testDependencies
)

lazy val apiSettings = Seq(
  libraryDependencies ++= commonDependencies ++ apiDependencies ++ testDependencies
)
