name := "goals"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "org.webjars"                         %%      "webjars-play"                          % "2.3.0",
  "org.webjars"                         %       "bootstrap"                             % "3.1.1-1"
)     

play.Project.playJavaSettings
