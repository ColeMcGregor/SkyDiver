package com.colemcg.skydiver.core.entities.Collectibles

import com.colemcg.skydiver.core.geometry.Vector2
import kotlin.math.sin
import kotlin.math.cos
import com.colemcg.skydiver.core.entities.Collectible
import com.colemcg.skydiver.core.entities.Player

/**
 * A simple collectible that increases the player's score.
 *
 * When collected, this item awards a fixed number of points
 * to the player's score via ScoreManager.
 *
 * It inherits from the base Collectible class and defines
 * its own point value.
 * 
 * moves in a circular motion, and drifts upward
 *
 * Safe to spawn frequently to encourage player movement.
 *
 * @see Collectible
 * @author Cole McGregor
 */

class Coin(position: Vector2 = Vector2()) : Collectible(position) {

    override val spriteName = "coin"
    override val spriteSize = Vector2(32f, 32f)

    private var time = 0f
    private val angularSpeed = 2f         // radians per second
    private val radius = 40f              // horizontal circle radius
    private val upwardSpeed = -40f        // upward drift speed (pixels/second)

    override fun onCollect(player: Player) {
        // TODO: Implement coin collection logic
    }

    override fun update(deltaTime: Float) {
        time += deltaTime
        val angle = time * angularSpeed

        // Circular horizontal offset
        val dx = cos(angle) * radius * deltaTime
        val dy = upwardSpeed * deltaTime

        position.x += dx
        position.y += dy
    }
}
