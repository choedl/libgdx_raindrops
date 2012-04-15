package com.me.mygdxgame

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.backends.lwjgl.LwjglApplication

object Main extends App {
  val cfg = new LwjglApplicationConfiguration()
  cfg.title = "Drop"
  cfg.useGL20 = true
  cfg.width = 800
  cfg.height = 480		
  new LwjglApplication(new MyGdxGame(), cfg)
}