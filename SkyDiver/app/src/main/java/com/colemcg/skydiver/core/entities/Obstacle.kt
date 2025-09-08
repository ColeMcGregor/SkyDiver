package com.colemcg.skydiver.core.entities

import com.colemcg.skydiver.core.geometry.Vector2
import com.colemcg.skydiver.core.geometry.Rect
import com.colemcg.skydiver.core.systems.GameRenderer
import com.colemcg.skydiver.core.systems.ScoreManager
import com.colemcg.skydiver.core.systems.SpeedManager
import com.colemcg.skydiver.core.systems.SoundManager

/**
 * Abstract class for all obstacles in the game.
 * Obstacles are interactive objects that negatively affect the player on collision.
 *
 * Responsibilities:
 * - Maintain position, velocity, and size.
 * - Update position and hitbox each frame.
 * - Respond to collisions with player.
 * - Render themselves using the GameRenderer.
 *
 * @param position The starting position of the obstacle.
 * @param velocity The velocity of the obstacle.
 * @param slowPenalty Speed penalty applied to player upon collision.
 * @param isLethal Whether the obstacle instantly ends the game on collision.
 *
 * @author Cole McGregor
 */
abstract class Obstacle(
    position: Vector2,
    velocity: Vector2,
    val slowPenalty: Float,
    val isLethal: Boolean
) : GameObject(position, velocity) {

    /**
     * Dynamic hitbox that moves with the object.
     * uses a backing field to avoid re-calculating the hitbox every time it is accessed
     * this is a performance optimization
     */
    //a backing field is a private variable that is used to store the value of a property
    private val _hitbox = Rect(position.x, position.y, spriteSize.x, spriteSize.y)

    //changing vector2 for some obstacles to use for the player position
    var playerGoalPosition: Vector2 = Vector2(0f, 0f)

    //this is a property that is used to get the hitbox, overrides the hitbox property in GameObject
    override val hitbox: Rect
        get() {
            //update the hitbox to the current position and size
            _hitbox.x = position.x
            _hitbox.y = position.y
            _hitbox.width = spriteSize.x
            _hitbox.height = spriteSize.y
            //return the updated hitbox
            return _hitbox
        }
    /**
     * Updates obstacle position based on velocity.
     * Hitbox is kept in sync dynamically.
     *  
     * SHOULD BE OVERRIDDEN BY SUBCLASSES, NOT A SUFFICIENT MOVEMENT FUNCTION
     */
    override fun update(deltaTime: Float) {
        position += velocity * deltaTime * gameSpeed //classic update function
    }

    fun update(deltaTime: Float, playerPosition: Vector2, currentSpeed: Float) {
        playerGoalPosition = playerPosition //used by the obstacles that track player location
        gameSpeed = currentSpeed        // normal game speed update
        update(deltaTime)
    }   //end of update function

    /**
     * Triggered when player collides with this obstacle.
     * Must be implemented by concrete subclasses.
     */
    abstract fun onCollision(
        player: Player,
        scoreManager: ScoreManager,
        speedManager: SpeedManager,
        soundManager: SoundManager
    )

    /**
     * Draws the obstacle using the GameRenderer.
     */
    override fun onDraw(renderer: GameRenderer) {
        renderer.drawGameObject(this, position)
    }
}
