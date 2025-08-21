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
 * @see Obstacle
 * @author Cole McGregor
 */

 //TODO: Update this to be appropriately fast for the game
//constants for gliders horizontal speed (UPDATE HERE)
const val GLIDER_HORIZONTAL_SPEED = 2.0f


class HangGlider(
    position: Vector2 = Vector2(),
    velocity: Vector2 = Vector2(0f, -1.5f)
) : Obstacle(
    position = position,
    velocity = velocity,
    slowPenalty = 0f,       // Not applicable — it's lethal
    isLethal = true         // Causes game over on collision
) {

    override val spriteName = "hangGlider"

    // TODO: Adjust to match actual sprite dimensions
    override val spriteSize = Vector2(80f, 30f)

    //variable to tell which direction the glider is facing, depending on objects velocity
    //if the object is moving to the right, it is facing right, if it is moving to the left, it is facing left
    //returns a boolean value(true or false)
    private var facingRight = velocity.x > 0

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
    override fun update(deltaTime: Float, player: Player, gameSpeed: Float) {
        // Glider moves up (against falling direction) and slightly sideways
        // a diagonal path: up + sideways
    
        // Determine base vertical rise rate (opposite to game flow)
        val verticalRise = 1.0f * gameSpeed  // Glider rises faster as game speed increases
    
        // Horizontal drift - right to left (negative X)
        val horizontalDrift = if (facingRight) -GLIDER_HORIZONTAL_SPEED else GLIDER_HORIZONTAL_SPEED
    
        // Combine into velocity vector
        velocity = Vector2(horizontalDrift, verticalRise)
    
        // Apply movement
        position += velocity * deltaTime
