package com.colemcg.skydiver.core.entities

import com.colemcg.skydiver.core.geometry.Vector2
import com.colemcg.skydiver.core.geometry.Rect
import com.colemcg.skydiver.core.systems.GameRenderer

/**
 * Abstract base class for all interactive game objects, this includes players, obstacles, clouds, and collectibles.
 * Subclasses implement all their own logic for the type of object they are.
 * 
 * @param position The position of the game object.
 * @param velocity The velocity of the game object.
 * 
 * @author Cole McGregor
 */
abstract class GameObject(
    var position: Vector2,
    var velocity: Vector2 = Vector2(0f, 0f)
    var isMarkedForRemoval: Boolean = false
    
) {
    abstract val hitbox: Rect

    /**
     * Updates the object's internal state.
     * @param deltaTime The time passed since the last update (in seconds).
     */
    abstract fun update(deltaTime: Float)

    /**
     * Draws the object using the provided GameRenderer.
     * @param renderer The renderer that handles drawing to the screen.
     */
    abstract fun onDraw(renderer: GameRenderer)
}
