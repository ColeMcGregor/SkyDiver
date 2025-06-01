package com.colemcg.skydiver.core.entities

import com.colemcg.skydiver.core.geometry.Vector2
import com.colemcg.skydiver.core.geometry.Rect
import com.colemcg.skydiver.platform.GameRenderer
import com.colemcg.skydiver.core.entities.GameObject

/**
 * Abstract class for all obstacles in the game.
 * will be used to create anything that the player can collide with in a bad way
 * 
 * @param position The position of the obstacle.
 * @param velocity The velocity of the obstacle.
 * @param hitbox The hitbox of the obstacle.
 * @param slowPenalty The penalty for the player when they hit the obstacle.
 * @param isLethal Whether the obstacle is lethal to the player.
 * 
 * @author Cole McGregor
 */

abstract class Obstacle(
    position: Vector2,
    velocity: Vector2,
    override val hitbox: Rect,  
    val slowPenalty: Float,
    val isLethal: Boolean
) : GameObject(position, velocity) {

    abstract fun onPlayerCollision(player: Player)

    override fun update(deltaTime: Float) {
        position += velocity * deltaTime
        hitbox.x = position.x
        hitbox.y = position.y
    }

    override fun onDraw(renderer: GameRenderer) {
        renderer.drawObstacle(position)
    }
}
