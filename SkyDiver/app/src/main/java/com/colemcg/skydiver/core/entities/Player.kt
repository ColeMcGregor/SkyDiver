package com.colemcg.skydiver.core.entities

import com.colemcg.skydiver.core.geometry.Vector2
import com.colemcg.skydiver.core.geometry.Rect
import com.colemcg.skydiver.core.systems.GameRenderer
import com.colemcg.skydiver.core.entities.GameObject
import com.colemcg.skydiver.core.entities.Collectible
import com.colemcg.skydiver.core.entities.Obstacle

//player state enum for switch cases
enum class PlayerState {
    Normal, Slowed, Dead
}

/**
 * Player class
 * is a subclass of GameObject, and therefore has a position, velocity, and hitbox
 * 
 * @param position The position of the player.
 * @param velocity The velocity of the player.
 * 
 * @author Cole McGregor
 */

class Player(
    position: Vector2,
    velocity: Vector2 = Vector2(0f, 0f)
) : GameObject(position, velocity) {

    //player state at start
    var state: PlayerState = PlayerState.Normal

    //player current speed
    var currentSpeed: Float = 1.0f

    override val hitbox: Rect = Rect(position.x, position.y, width = 30f, height = 50f)

    //increase speed
    fun goFaster() { currentSpeed += 0.1f }

    //increase speed by amount
    fun goFaster(amount: Float) { currentSpeed += amount }

    //decrease speed
    fun goSlower() { currentSpeed -= 0.1f }

    //decrease speed by amount
    fun goSlower(amount: Float) { currentSpeed -= amount }


    /*The below are still stub functions, and will be implemented in the future */

    //move left/right  with slide input
    fun movePlayer(input: InputEvent) {
        // move according to the pseudo-Joystick type movement
    }

    //collect collectible, used for collectible logic
    fun onCollect(collectible: Collectible) {
        // Implement collectible logic here
    }

    //collision with obstacle, this is a typical name for a function that handles collisions
    fun onCollision(obstacle: Obstacle) {
        // Implement collision logic here
    }

    //reset player
    fun reset() {
        position = Vector2(0f, 0f) // TODO: these need changing to the actual starting position of the player
        velocity = Vector2(0f, 0f)
        state = PlayerState.Normal
    }

//override functions from GameObject

    //update player position
    //this is a classic update pattern for a game object, used to allow for smooth movement/actions to occur
    //it helps simulate time passing, and allows for smooth animations
    override fun update(deltaTime: Float) {
        position += velocity * deltaTime
        hitbox.x = position.x
        hitbox.y = position.y
    }

    //draw player
    override fun onDraw(renderer: GameRenderer) {
        renderer.drawPlayer(position)
    }
}
