import sbt._
import Keys._

object MyGdxBuild extends Build {
  lazy val game = Project(id = "game", base = file("game"))

  lazy val desktopGame = Project(id = "game-desktop", base = file("game-desktop")) dependsOn(game)
}
