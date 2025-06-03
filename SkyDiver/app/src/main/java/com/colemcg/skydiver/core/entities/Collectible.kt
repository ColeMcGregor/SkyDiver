package com.colemcg.skydiver.core.entities

import com.colemcg.skydiver.core.geometry.Vector2
import com.colemcg.skydiver.core.geometry.Rect
import com.colemcg.skydiver.core.systems.GameRenderer
import com.colemcg.skydiver.core.entities.Player

/**
 * Represents a collectible object (e.g., coin, token) that the player can collect for points.
 * 
 * @param points The number of points the collectible is worth.
 * @param multiplier The multiplier for the points value.
 * @param position The position of the collectible.
 * @param width The width of the collectible.
 * @param height The height of the collectible.
 * 
 * @author Cole McGregor
 */
class Collectible(
    val points: Int,
    val multiplier: Float,
    position: Vector2,
    width: Float = 20f,
    height: Float = 20f
) : GameObject(position, Vector2(0f, -2f)) { // default upward motion

    override val hitbox: Rect = Rect(position.x, position.y, width, height)

    fun onCollect(player: Player) {
        // Implement scoring logic here
    }

    override fun update(deltaTime: Float) {
        position += velocity * deltaTime
        hitbox.x = position.x
        hitbox.y = position.y
    }

    override fun onDraw(renderer: GameRenderer) {
        renderer.drawCollectible(position)
    }
}
