name := """izan-pirata"""

version := "0.1"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

resolvers += "pk11" at "http://pk11-scratch.googlecode.com/svn/trunk"

libraryDependencies ++= Seq(
    javaJdbc,
    javaEbean,
    cache,
    "org.json" % "json" % "20140107",
    "com.typesafe" %% "play-plugins-redis" % "2.2.1"
)
