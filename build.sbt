name := "todo-playframework-slick-vuejs"

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play"    % "1.5.1" % Test,
  "com.typesafe.play"      %% "play-slick"            % "2.0.0",
  "com.typesafe.play"      %% "play-slick-evolutions" % "2.0.0",
  "com.h2database"         % "h2"                     % "1.4.187",
  "org.webjars"            %% "webjars-play"          % "2.5.0",
  "org.webjars"            %  "vue"                   % "2.1.3",
  "org.webjars.bower"      %  "vue-router"            % "2.2.1",
  "mysql"                  %  "mysql-connector-java"  % "5.1.36"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

