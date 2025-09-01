package com.colemcg.skydiver.platform.android.rendering

import android.graphics.Canvas
import android.graphics.Paint
import com.colemcg.skydiver.core.entities.GameObject
import com.colemcg.skydiver.core.geometry.Vector2
import com.colemcg.skydiver.core.systems.GameRenderer
import com.colemcg.skydiver.platform.android.rendering.SpriteRegistry

/**
 * Android implementation of the GameRenderer.
 * Will implement the various abstract draw methods for the things needed by the game.
 * Android specific, and will be used by the AndroidGameLoop to draw the game.
 *
 * @author Cole McGregor
 * @version 1.0
 * @date 2025-08-31
 */

 //constants
const val TITLE_HEIGHT = 100f

//class implementation
class AndroidGameRenderer(private val canvas: Canvas) : GameRenderer() {

    private val paint = Paint()

    //draw a game object, like a player or an obstacle
    //TODO: test game objects
    override fun drawGameObject(gameObject: GameObject, position: Vector2) {
        println("AndroidGameRenderer: in draw game object")
        val sprite = SpriteRegistry.getSprite(gameObject.spriteName)
        canvas.drawBitmap(sprite, position.x, position.y, paint)
    }

    //draw the background layer
    //TODO: test background layer
    override fun drawBackgroundLayer(position: Vector2) {
        println("AndroidGameRenderer: in draw background layer")
        val background = SpriteRegistry.getSprite("background")
        val offsetX = position.x - (canvas.width / 2)
        val offsetY = position.y - (canvas.height / 2)
        canvas.drawBitmap(background, -offsetX, -offsetY, paint)
    }

    //draw a UI element, like a score or a life
    //TODO: test UI elements
    override fun drawUIElement(name: String, position: Vector2) {
        println("AndroidGameRenderer: in draw ui element")
        val uiSprite = SpriteRegistry.getSprite(name)
        canvas.drawBitmap(uiSprite, position.x, position.y, paint)
    }

    //draw the title screen
    //TODO: test title display
    override fun drawTitle() {
        println("AndroidGameRenderer: in draw title")
        val title = SpriteRegistry.getSprite("title")
        val x = (canvas.width - title.width) / 2f
        val y = TITLE_HEIGHT  // Arbitrary vertical position for now
        canvas.drawBitmap(title, x, y, paint)
    }
    //draw a message overlay, like a paused message or game over message
    //TODO: test message overlayws
    override fun drawMessageOverlay(message: String) {
        println("AndroidGameRenderer: in draw message overlay")
        // Placeholder: draw text centered
        paint.textSize = 64f
        paint.isAntiAlias = true
        val textWidth = paint.measureText(message)
        val x = (canvas.width - textWidth) / 2f
        val y = canvas.height / 2f
        canvas.drawText(message, x, y, paint)
    }

    //draw a visual particle effect, like a sparkle or impact
    //TODO: make particle effects
    override fun drawParticleEffect(position: Vector2, type: String) {
        println("AndroidGameRenderer: in draw particle effect")
        val effectSprite = SpriteRegistry.getSprite(type)
        canvas.drawBitmap(effectSprite, position.x, position.y, paint)
    }

    //draw a full-screen overlay, like a pause menu or game over screen
    //TODO: make overlay screens
    override fun drawOverlay(name: String) {
        println("AndroidGameRenderer: in draw overlay")
        val overlay = SpriteRegistry.getSprite(name)
        canvas.drawBitmap(overlay, 0f, 0f, paint)
    }

    override fun clearScreen() { println("AndroidGameRenderer: in clear screen") }

    override fun flush() { println("AndroidGameRenderer: in flush") }
}
