package com.me.mygdxgame

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.backends.lwjgl.LwjglApplication

object Main extends App {
  val cfg = new LwjglApplicationConfiguration()
  cfg.title = "my-gdx-game"
  cfg.useGL20 = false
  cfg.width = 480
  cfg.height = 320		
  new LwjglApplication(new MyGdxGame(), cfg)
}