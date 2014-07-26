name := """izan-pirata"""

version := "0.1"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

libraryDependencies ++= Seq(
    javaJdbc,
    javaEbean,
    cache,
    "org.json" % "json" % "20090211",
    "com.typesafe" %% "play-plugins-redis" % "2.1.1"
)
