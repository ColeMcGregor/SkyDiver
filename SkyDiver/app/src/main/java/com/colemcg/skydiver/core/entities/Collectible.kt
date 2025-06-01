package com.colemcg.skydiver.core.entities

import com.colemcg.skydiver.core.geometry.Vector2
import com.colemcg.skydiver.core.geometry.Rect
import com.colemcg.skydiver.platform.GameRenderer
import com.colemcg.skydiver.core.game.Player

/**
 * Represents a collectible object (e.g., coin, token) that the player can collect for points.
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
