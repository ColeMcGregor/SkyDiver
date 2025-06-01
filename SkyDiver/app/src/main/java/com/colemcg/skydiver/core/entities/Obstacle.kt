package com.colemcg.skydiver.core.entities

import com.colemcg.skydiver.core.geometry.Vector2
import com.colemcg.skydiver.core.geometry.Rect
import com.colemcg.skydiver.platform.GameRenderer
import com.colemcg.skydiver.core.entities.GameObject

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
