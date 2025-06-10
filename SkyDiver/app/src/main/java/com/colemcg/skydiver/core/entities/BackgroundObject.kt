package com.colemcg.skydiver.core.entities

import com.colemcg.skydiver.core.geometry.Vector2
import com.colemcg.skydiver.core.geometry.Rect
import com.colemcg.skydiver.core.systems.GameRenderer

/**
 * Abstract class for all background objects, such as clouds or scenery elements.
 * These are primarily visual and may have parallax behavior.
 * 
 * @param position The position of the background object.
 * @param velocity The velocity of the background object.
 * @param parallaxFactor The parallax factor of the background object.
 * 
 * @author Cole McGregor
 */
abstract class BackgroundObject(
    position: Vector2,
    velocity: Vector2 = Vector2(0f, 0f),
    val parallaxFactor: Float = 1.0f
) : GameObject(position, velocity) {

    /**
     * Each background object defines its own hitbox for visual bounding.
     */
    abstract override val hitbox: Rect

    /**
     * Checks if the background object has moved off the screen and loops it if needed.
     * This is used to create the illusion of a looping background.
     * @param bgLoopHeight The height of the background loop.
     */
    fun checkAndLoopIfNeeded(bgLoopHeight: Float) {
        if (position.y >= bgLoopHeight) {
            position.y = -hitbox.height //the hitbox height is how tall the object is, start exactly that much above the screen
        }
    }

    /**
     * Updates the background object’s position based on its velocity and parallax.
     */
    override fun update(deltaTime: Float) {
        position += velocity * deltaTime * parallaxFactor
    }

    /**
     * Draws the background object using the renderer.
     */
    override fun onDraw(renderer: GameRenderer) {
        renderer.drawBackgroundObjects(position)
    }
}
