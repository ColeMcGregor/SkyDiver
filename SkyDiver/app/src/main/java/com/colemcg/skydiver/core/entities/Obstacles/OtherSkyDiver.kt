package com.colemcg.skydiver.core.entities

import com.colemcg.skydiver.core.geometry.Vector2
import com.colemcg.skydiver.core.geometry.Rect
import com.colemcg.skydiver.core.systems.ScoreManager
import com.colemcg.skydiver.core.systems.SoundManager
import com.colemcg.skydiver.core.systems.SpeedManager
import com.colemcg.skydiver.core.game.GameState



/**
 * A *LETHAL* obstacle that instantly ends the game on contact.
 *
    * Hang gliders serve as high-risk hazards that the player
    * must avoid completely. Triggers a transition to GameOverOverlay.
 *
 * Represents a hard fail state and adds intensity to gameplay.
 * 
 * will track toward the player, making a different type of danger than the hang glider
 *
 * @see Obstacle
 * @author Cole McGregor
 */
//TODO: This needs to be adjusted to be more realistic
 const val CHASE_SPEED = 1.5f
 // used to decrease overall y value changes of motion to make it harder to dodge
 const val RISE_SPEED_MULTIPLIER = 0.5f

class OtherSkyDiver(
    position: Vector2 = Vector2(),
    velocity: Vector2 = Vector2(0f, -1.5f)
) : Obstacle(
    position = position,
    velocity = velocity,
    slowPenalty = 0f,       // Not applicable — it's lethal
    isLethal = true         // Causes game over on collision
) {

    override val spriteName = "otherSkyDiver"

    // TODO: Determine if sprites need scaling
    override val spriteSize = Vector2(32f, 19f)

    override fun onCollision(
        player: Player,
        scoreManager: ScoreManager,
        speedManager: SpeedManager,
        soundManager: SoundManager
    ) {
        //play the collision sound
        soundManager.playSFX("collision")
        //wait for a short moment to separate the collision sound from the game over sound, but without freezing the game
        //this freezes the game for 30ms, but it is necessary to separate the collision sound from the game over sound
        Thread.sleep(30)
        //play the game over sound
        soundManager.playSFX("game_over")
        //end the game
        GameState.endGame()

    }
    /**
     * This is the update function for the other sky diver.
     * It will rise continuously, inline with game speed, but slower like the player, as well as drift towards
     * the player, using a basic chase pathfinding algorithm to track the player, but if the skydiver misses, it continues to rise
     * and despawn like the rest of the objects
     * @param deltaTime The time passed since the last update (in seconds).
     * @param player The player object, used for tracking the player's position
     * @param gameSpeed The current game speed, used for adjusting the object's velocity
     */
    override fun update(deltaTime: Float, player: Player, gameSpeed: Float) {
        // Always move upward with the game, like other obstacles
        val riseSpeed = gameSpeed * RISE_SPEED_MULTIPLIER
    
        // Drift horizontally toward the player's current X position
        val horizontalDelta = player.position.x - position.x
        val horizontalDirection = when {
            horizontalDelta > 1f -> 1f     // drift right
            horizontalDelta < -1f -> -1f   // drift left
            else -> 0f                     // close enough, no adjustment
        }
    
        // Update velocity based on calculated directions
        //remember the top of the sceen is 0, so the object will rise up to the top of the screen if you set the velocity to negative
        velocity = Vector2(horizontalDirection * CHASE_SPEED, -riseSpeed)
    
        // Apply movement
        position += velocity * deltaTime
    }
}
