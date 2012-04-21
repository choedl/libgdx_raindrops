package com.me.mygdxgame

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL10
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.Input.Keys
import scala.collection._
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.TimeUtils

class MyGdxGame extends ApplicationListener {

  var dropImage: Texture = _
  var bucketImage: Texture = _
  var dropSound: Sound = _
  var rainMusic: Music = _
  var camera: Camera = _
  var batch: SpriteBatch = _
  var bucket: Rectangle = _

  val touchPos = new Vector3()

  val raindrops = mutable.ArrayBuffer[Rectangle]() // TODO or use libgdx's Array  class
  var lastDropTime: Long = _

  override def create() {
    // load the images for the droplet and the bucket, 48x48 pixels each
    dropImage = new Texture(Gdx.files.internal("droplet48x48.png"))
    bucketImage = new Texture(Gdx.files.internal("bucket48x48.png"))

    // load the drop sound effect and the rain background "music"
    dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"))
    rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"))

    // start the playback of the background music immediately
    rainMusic.setLooping(true)
    rainMusic.play()

    camera = new OrthographicCamera(800, 480) // TODO no setToOrtho?

    batch = new SpriteBatch()

    bucket = new Rectangle()
    bucket.x = 0 //800 / 2 - 48 / 2
    bucket.y = -220 //20
    bucket.width = 48
    bucket.height = 48
    
    spawnRaindrop
  }
  
  private def spawnRaindrop = {
    val raindrop = new Rectangle()
    raindrop.x = MathUtils.random(-400, 400 - 48)
    raindrop.y = 240
    raindrop.width = 48
    raindrop.height = 48
    raindrops += raindrop
    lastDropTime = TimeUtils.nanoTime()
  }

  override def dispose() {
    dropImage.dispose()
    bucketImage.dispose()
    dropSound.dispose()
    rainMusic.dispose()
    batch.dispose()
  }

  override def render() {
    Gdx.gl.glClearColor(0, 0, 0.2f, 1)
	Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT)
	
	camera.update()

	batch.setProjectionMatrix(camera.combined)
	batch.begin()
	batch.draw(bucketImage, bucket.x, bucket.y)
	raindrops foreach { r => batch.draw(dropImage, r.x, r.y) }
	batch.end()
	
		
	if (Gdx.input.isTouched()) {
	  touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0)
      camera.unproject(touchPos)
      bucket.x = touchPos.x - 48 / 2
    }
    
    if (Gdx.input.isKeyPressed(Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime()
    if (Gdx.input.isKeyPressed(Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime()

    // if (bucket.x < 0) bucket.x = 0
    // if (bucket.x > 800 - 48) bucket.x = 800 - 48
    if (bucket.x < -400) bucket.x = -400
    if (bucket.x > 400 - 48) bucket.x = 400 - 48

    if (TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop

    raindrops foreach { raindrop => // TODO why can raindrop be null?
      Option.apply(raindrop) map { r =>
        raindrop.y -= 200 * Gdx.graphics.getDeltaTime()
	    if (raindrop.y + 48 < -240) raindrops -= r
	    else if (raindrop.overlaps(bucket)) {
	      dropSound.play()
	      raindrops -= r
	    }
      }
    }
  }

  override def resize(width: Int, height: Int) {}

  override def pause() {}

  override def resume() {}
}