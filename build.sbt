name := "readers"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies += "org.typelevel" %% "cats-core"        % "2.1.1"
libraryDependencies += "org.typelevel" %% "cats-effect"      % "2.1.1"
libraryDependencies += "com.olegpy"    %% "meow-mtl-core"    % "0.4.0"
libraryDependencies += "com.olegpy"    %% "meow-mtl-effects" % "0.4.0"

libraryDependencies += "org.specs2" %% "specs2-core" % "4.8.3" % "test"

addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full)

scalacOptions in Test ++= Seq("-Yrangepos")
