package com.colemcg.skydiver.core.entities.Collectibles

import com.colemcg.skydiver.core.geometry.Vector2
import kotlin.math.sin
import kotlin.math.cos
import com.colemcg.skydiver.core.entities.Collectible
import com.colemcg.skydiver.core.entities.Player
import com.colemcg.skydiver.core.systems.ScoreManager
import com.colemcg.skydiver.core.systems.SoundManager

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

//const for coin point value
const val COIN_POINT_VALUE = 100

class Coin(position: Vector2 = Vector2()) : Collectible(position) {

    override val spriteName = "coin"
    override val spriteSize = Vector2(6f, 10f)

    private var time = 0f
    private val angularSpeed = 2f         // radians per second
    private val radius = 40f              // horizontal circle radius
    private val upwardSpeed = -40f        // upward drift speed (pixels/second)

    override fun onCollect(player: Player, scoreManager: ScoreManager, soundManager: SoundManager) {
        println("Coin collected")
        //add points to the score manager
        scoreManager.addPoints(COIN_POINT_VALUE)
        //increment the number of coins collected by the player this run
        scoreManager.incrementCoinsCollected()
        //play the coin sound
        soundManager.playSFX("coin")
    }

    /*
    * Updates the coin's position based on its position, and the elapsed time.
    * DOESNT USE VELOCITY, as it is a circular motion, and drifts upward
    * @param deltaTime The time passed since the last update (in seconds).
    * @author Cole McGregor
    */
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
