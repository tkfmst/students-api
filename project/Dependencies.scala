import sbt._

object Versions {
  val Scala = "2.13.7"

  val Cats           = "2.6.1"
  val CatsEffect     = "3.2.9"
  val Circe          = "0.14.1"
  val Doobie         = "1.0.0-RC1"
  val H2DB           = "1.4.200"
  val Http4s         = "0.23.6"
  val NewType        = "0.4.4"
  val Refined        = "0.9.27"
  val Shapeless      = "2.3.3"
  val ScalaLogging   = "3.9.4"
  val LogbackClassic = "1.2.3"

  val Munit = "0.7.29"
}

object Dependencies {
  import Libraries._

  val commonDependencies = Seq(
    catsCore,
    catsEffect,
    circeCore,
    circeGeneric,
    circeLiteral,
    circeParser,
    circeRefined,
    logbackClassic,
    newType,
    scalaLogging,
    shapeless,
    refined,
    refinedCats,
  )

  val entityDependencies = Seq(
  )

  val appDependencies = Seq(
  )

  val interfaceDependencies = Seq(
  )

  val infraDependencies = Seq(
    doobie,
    doobieRefined,
    doobieH2,
    doobieHikari,
    h2db,
  )

  val apiDependencies = Seq(
    http4sDsl,
    https4sBlazeServer,
    https4sCirce,
  )

  val testDependencies = Seq(
    Libraries.munit,
  )
}

object Libraries {
  lazy val catsCore   = "org.typelevel" %% "cats-core"   % Versions.Cats
  lazy val catsEffect = "org.typelevel" %% "cats-effect" % Versions.CatsEffect

  lazy val circeCore    = "io.circe" %% "circe-core"    % Versions.Circe
  lazy val circeGeneric = "io.circe" %% "circe-generic" % Versions.Circe
  lazy val circeLiteral = "io.circe" %% "circe-literal" % Versions.Circe
  lazy val circeParser  = "io.circe" %% "circe-parser"  % Versions.Circe
  lazy val circeRefined = "io.circe" %% "circe-refined" % Versions.Circe

  lazy val doobie        = "org.tpolecat" %% "doobie-core"    % Versions.Doobie
  lazy val doobieRefined = "org.tpolecat" %% "doobie-refined" % Versions.Doobie
  lazy val doobieH2      = "org.tpolecat" %% "doobie-h2"      % Versions.Doobie
  lazy val doobieHikari  = "org.tpolecat" %% "doobie-hikari"  % Versions.Doobie

  lazy val h2db = "com.h2database" % "h2" % Versions.H2DB

  lazy val http4sDsl          = "org.http4s" %% "http4s-dsl"          % Versions.Http4s
  lazy val https4sBlazeServer = "org.http4s" %% "http4s-blaze-server" % Versions.Http4s
  lazy val https4sCirce       = "org.http4s" %% "http4s-circe"        % Versions.Http4s

  lazy val logbackClassic = "ch.qos.logback" % "logback-classic" % Versions.LogbackClassic % Runtime

  lazy val newType = "io.estatico" %% "newtype" % Versions.NewType

  lazy val refined     = "eu.timepit" %% "refined"      % Versions.Refined
  lazy val refinedCats = "eu.timepit" %% "refined-cats" % Versions.Refined

  lazy val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % Versions.ScalaLogging

  lazy val shapeless = "com.chuusai" %% "shapeless" % Versions.Shapeless

  // Test
  lazy val munit = "org.scalameta" %% "munit" % Versions.Munit % Test
}
