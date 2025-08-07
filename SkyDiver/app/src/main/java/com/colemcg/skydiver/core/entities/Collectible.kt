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
     */
    override val hitbox: Rect
        get() = Rect(position.x, position.y, spriteSize.x, spriteSize.y)

    /**
     * Called when the player collects this object.
     */
    abstract fun onCollect(player: Player, scoreManager: ScoreManager, soundManager: SoundManager)

    /**
     * Moves the collectible based on its velocity.
     */
    override fun update(deltaTime: Float) {
        position += velocity * deltaTime
    }

    /**
     * Delegates rendering to platform-specific renderer.
     */
    override fun onDraw(renderer: GameRenderer) {
        renderer.drawGameObject(this)
    }
}
