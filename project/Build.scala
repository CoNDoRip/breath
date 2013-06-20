import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "breath-server"
  val appVersion      = "0.1"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaJpa,
    "org.hibernate" % "hibernate-entitymanager" % "4.2.2.Final"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here
      ebeanEnabled := false
  )

}
