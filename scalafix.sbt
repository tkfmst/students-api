ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.6.0";
ThisBuild / scalafixScalaBinaryVersion := Versions.ScalaBinary;
ThisBuild / semanticdbEnabled := true; // enable scalafix SemanticDB
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision;
