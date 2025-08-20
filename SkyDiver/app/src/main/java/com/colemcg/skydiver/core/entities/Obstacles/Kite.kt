package com.colemcg.skydiver.core.entities.Obstacles

import com.colemcg.skydiver.core.geometry.Vector2
import com.colemcg.skydiver.core.entities.Obstacle
import com.colemcg.skydiver.core.systems.ScoreManager
import com.colemcg.skydiver.core.systems.SoundManager
import com.colemcg.skydiver.core.systems.SpeedManager
import com.colemcg.skydiver.core.game.GameState
import com.colemcg.skydiver.core.entities.Player


/**
 * A mid-level obstacle that slows the player more than a balloon.
 *
 * Kites apply a stronger penalty to downward momentum, increasing
 * the risk of falling below minimum speed. Not lethal, but dangerous
 * in combination with other obstacles.
 *
 * Often used to create vertical tension or delay progress.
 *
 * @see Obstacle
 * @author Cole McGregor
 */

const val KITE_HORIZONTAL_SPEED = 2.0f
const val KITE_SLOW_PENALTY = 0.5f
const val KITE_MULTIPLIER_PENALTY = -1.0f

class Kite(
    position: Vector2 = Vector2(),
    velocity: Vector2 = Vector2(0f, -1.5f)
) : Obstacle(
    position = position,
    velocity = velocity,
    slowPenalty = KITE_SLOW_PENALTY,       
    isLethal = false        
) {
    override val spriteName = "kite"
    override val spriteSize = Vector2(80f, 30f)

    //the kite will slow the player down,
    //this will risk the player falling below the speed threshhold
    //this will also decrease the players current multiplier
    //it of course also makes a collision sound play
    override fun onCollision(
        player: Player,
        scoreManager: ScoreManager,
        speedManager: SpeedManager,
        soundManager: SoundManager
    ) {
        //apply the slow penalty
        speedManager.applySlowdown(KITE_SLOW_PENALTY)
        //reduce multiplier by one
        scoreManager.applyMultiplierBoost(KITE_MULTIPLIER_PENALTY)
        //play the sound
        soundManager.playSFX("collision")

    }

    //the kite will rise according to game speed,  
    //but also drift a moderate speed to either side, simulating a kite in the wind
    //might spontaneously move in the opposite x direction, after a random delay
    override fun update(
        deltaTime: Float, 
        player: Player, 
        gameSpeed: Float
    ) {
            //get the rise speed and drift speed
            val riseSpeed = gameSpeed
            //if the kite is moving to the right, it will drift to the right, and vice versa
            val driftSpeed = if (velocity.x > 0) KITE_HORIZONTAL_SPEED else -KITE_HORIZONTAL_SPEED

            //update the velocity
            velocity.x = driftSpeed
            velocity.y = riseSpeed

            //update the position
            position += velocity * deltaTime

           


            
    }
}
