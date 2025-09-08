package com.colemcg.skydiver.core.entities

import com.colemcg.skydiver.core.geometry.Vector2
import com.colemcg.skydiver.core.geometry.Rect
import com.colemcg.skydiver.core.systems.GameRenderer
import com.colemcg.skydiver.core.systems.ScoreManager
import com.colemcg.skydiver.core.systems.SpeedManager
import com.colemcg.skydiver.core.systems.SoundManager
import com.colemcg.skydiver.core.entities.GameObject
import com.colemcg.skydiver.core.entities.Collectible
import com.colemcg.skydiver.core.entities.Obstacle
import com.colemcg.skydiver.core.events.InputEvent

//player state enum for switch cases, used in conjunction with sprite name for sprite application in renderering, as well as logic
enum class PlayerState {
    Normal, Slowed, Fast
}

//TODO: adjust these constants to be more accurate to the actual player and sprite size
//constants for player rect size(adjust here)   
//fast player is the player when they are going fast
const val FAST_PLAYER_WIDTH = 25f
const val FAST_PLAYER_HEIGHT = 45f
//normal player is the player when they are not slowed or dead
const val NORMAL_PLAYER_WIDTH = 30f
const val NORMAL_PLAYER_HEIGHT = 45f
//slowed player is the player when they are slowed
const val SLOWED_PLAYER_WIDTH = 45f
const val SLOWED_PLAYER_HEIGHT = 25f


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
    //sprite name (the player of course)
    override val spriteName: String = "player"
    //sprite size
    override val spriteSize: Vector2 = Vector2(NORMAL_PLAYER_WIDTH, NORMAL_PLAYER_HEIGHT)

    //player current speed
    var currentSpeed: Float = 1.0f

    //player hitbox
    //this is a getter for the hitbox, and is used to get the hitbox of the player
    //the hitbox changes size based on the state of the player
    private val _hitbox = Rect(0f, 0f, NORMAL_PLAYER_WIDTH, NORMAL_PLAYER_HEIGHT)

    override val hitbox: Rect
        get() {
            val size = when (state) {
                PlayerState.Normal -> Vector2(NORMAL_PLAYER_WIDTH, NORMAL_PLAYER_HEIGHT)
                PlayerState.Slowed -> Vector2(SLOWED_PLAYER_WIDTH, SLOWED_PLAYER_HEIGHT)
                PlayerState.Fast -> Vector2(FAST_PLAYER_WIDTH, FAST_PLAYER_HEIGHT)
            }

            _hitbox.x = position.x
            _hitbox.y = position.y
            _hitbox.width = size.x
            _hitbox.height = size.y

            return _hitbox
        }


    //increase speed by 0.1f
    fun goFaster() { currentSpeed += 0.1f }

    //increase speed by amount specified
    fun goFaster(amount: Float) { currentSpeed += amount }

    //decrease speed by 0.1f
    fun goSlower() { currentSpeed -= 0.1f }

    //decrease speed by amount specified
    fun goSlower(amount: Float) { currentSpeed -= amount }


    /*The below are still stub functions, and will be implemented in the future */

    //TODO: implement designed slide joystick movement
    fun movePlayer(input: InputEvent) {
        // move according to the pseudo-Joystick type movement
    }

    //reset player
    fun reset() {
        position = Vector2(0f, 0f) // TODO: these need changing to the actual starting position of the player(center of screen, slightly up)
        velocity = Vector2(0f, 0f)
        state = PlayerState.Normal
    }

//override functions from GameObject

    //update player position, overridden from GameObject
    override fun update(deltaTime: Float) {
        position += velocity * deltaTime * gameSpeed //classic update function
    }

    //update player position
    //this is a classic update pattern for a game object, used to allow for smooth movement/actions to occur
    //it helps simulate time passing, and allows for smooth animations
    //called from GameManager to update the player's speed
    fun update(deltaTime: Float, currentSpeed: Float) {
        gameSpeed = currentSpeed //update the game speed
        update(deltaTime) //classic update function
    }

    /**
     * Draws the player using the provided GameRenderer.
     * @param renderer The renderer that handles drawing to the screen.
     * @param position The position of the player.
     * @param this The player itself.
     */
    override fun onDraw(renderer: GameRenderer) {
        renderer.drawGameObject(this, position)
    }
}
