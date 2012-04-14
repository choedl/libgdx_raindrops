package com.me.mygdxgame

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL10

class MyGdxGame extends ApplicationListener {

  override def create() {}

  override def dispose() {}

  override def render() {
    Gdx.gl.glClearColor(1, 0, 1, 1);
	Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
  }

  override def resize(width: Int, height: Int) {}

  override def pause() {}

  override def resume() {}
}