package com.colemcg.skydiver.core.entities.Obstacles

import com.colemcg.skydiver.core.geometry.Vector2
import com.colemcg.skydiver.core.entities.Obstacle
import com.colemcg.skydiver.core.systems.ScoreManager
import com.colemcg.skydiver.core.systems.SoundManager
import com.colemcg.skydiver.core.systems.SpeedManager
import com.colemcg.skydiver.core.game.GameState
import com.colemcg.skydiver.core.entities.Player

/**
 * A slow-impact obstacle that penalizes speed when collided with.
 *
 * Balloons do not cause immediate game over but reduce the
 * player's speed via SpeedManager. This makes the player
 * more vulnerable to other hazards.
 *
 * Can appear alone or in clusters to control pacing.
 *
 * @see Obstacle
 * @author Cole McGregor
 */

const val BALLOON_HORIZONTAL_SPEED = 2.0f
const val BALLOON_SLOW_PENALTY = 0.5f
const val BALLOON_MULTIPLIER_PENALTY = -1.0f

class Balloon(
    position: Vector2 = Vector2(),
    velocity: Vector2 = Vector2(0f, -1.5f)
) : Obstacle(
    position = position,
    velocity = velocity,
    slowPenalty = BALLOON_SLOW_PENALTY,       
    isLethal = false         
) {
    override val spriteName = "balloon"
    override val spriteSize = Vector2(80f, 30f)

    //the balloon will slow the player down,
    //this will risk the player fallong below the speed threshhold
    //this will also decrease the players max speed and multiplier
    //it of course also makes a collision sound play
    override fun onCollision(
        player: Player,
        scoreManager: ScoreManager,
        speedManager: SpeedManager,
        soundManager: SoundManager
    ) {
        //apply the slow penalty
        speedManager.applySlowdown(BALLOON_SLOW_PENALTY)
        //reduce multiplier by one
        scoreManager.applyMultiplierBoost(BALLOON_MULTIPLIER_PENALTY)
        //play the sound
        soundManager.playSFX("collision")

    }

    //the balloon will rise according to game speed,  
    //but also drift very slightly in either direction as long as it isnt off the screen
    override fun update(
        deltaTime: Float, 
        player: Player, 
        gameSpeed: Float
    ) {
            val riseSpeed = gameSpeed
            val horizontalMeander= if (velocity.x > 0) BALLOON_HORIZONTAL_SPEED else -BALLOON_HORIZONTAL_SPEED

            velocity.x = horizontalMeander
            velocity.y = riseSpeed

            position += velocity * deltaTime
            
    }
}
