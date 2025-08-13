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
    open var position: Vector2,
    open var velocity: Vector2 = Vector2(0f, 0f),
    open var isMarkedForRemoval: Boolean = false
    
) {
    open val spriteName: String = "" // to be overridden in concrete classes (e.g. "player", "obstacle", "cloud", "collectible")
    open val spriteSize: Vector2 = Vector2(64f, 64f) // default size, will be overridden in concrete classes
    abstract val hitbox: Rect // will be overridden in concrete classes, used for collision detection

    /**
     * Updates the object's internal state.
     * @param deltaTime The time passed since the last update (in seconds).
     * @param player The player object, used for tracking the player's position
     */
    abstract fun update(deltaTime: Float, player: Player)

    /**
     * Draws the object using the provided GameRenderer.
     * @param renderer The renderer that handles drawing to the screen.
     */
    abstract fun onDraw(renderer: GameRenderer)
}
