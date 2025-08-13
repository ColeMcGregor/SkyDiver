package com.colemcg.skydiver.core.entities

import com.colemcg.skydiver.core.geometry.Vector2
import com.colemcg.skydiver.core.geometry.Rect
import com.colemcg.skydiver.core.systems.GameRenderer
import com.colemcg.skydiver.core.systems.ScoreManager
import com.colemcg.skydiver.core.systems.SoundManager

/**
 * Abstract base class for collectible items (e.g., coins, multipliers).
 * 
 * Concrete subclasses must define:
 * - Their own point/multiplier logic
 * - spriteName
 * - onCollect behavior
 * 
 * Handles motion and hitbox calculation based on position and spriteSize.
 *
 * @author
 */
abstract class Collectible(
    override var position: Vector2,
    override var velocity: Vector2 = Vector2(0f, -2f)
) : GameObject(position, velocity) {

    /**
     * Used by GameRenderer to fetch the correct image.
     */
    abstract override val spriteName: String

    /**
     * Size of the sprite (used for rendering and hitbox).
     */
    override open val spriteSize: Vector2 = Vector2(32f, 32f)

    /**
     * Dynamic hitbox that moves with the object.
     * uses a backing field to avoid re-calculating the hitbox every time it is accessed
     * this is a performance optimization
     */
    //a backing field is a private variable that is used to store the value of a property
    private val _hitbox = Rect(position.x, position.y, spriteSize.x, spriteSize.y)

    //this is a property that is used to get the hitbox, overrides the hitbox property in GameObject
    override val hitbox: Rect
        get() {
            //update the hitbox to the current position and size
            _hitbox.x = position.x
            _hitbox.y = position.y
            _hitbox.width = spriteSize.x
            _hitbox.height = spriteSize.y
            //return the updated hitbox
            return _hitbox
        }

    /**
     * Called when the player collects this object.
     */
    abstract fun onCollect(player: Player, scoreManager: ScoreManager, soundManager: SoundManager)

    /**
     * Moves the collectible based on its velocity.
     */
    override fun update(deltaTime: Float, player: Player) {
        position += velocity * deltaTime
    }

    /**
     * Delegates rendering to platform-specific renderer.
     */
    override fun onDraw(renderer: GameRenderer) {
        renderer.drawGameObject(this, position)
    }
}
