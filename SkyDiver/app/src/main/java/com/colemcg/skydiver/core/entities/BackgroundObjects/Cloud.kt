import com.colemcg.skydiver.core.entities.BackgroundObject
import com.colemcg.skydiver.core.entities.Player
import com.colemcg.skydiver.core.geometry.Rect
import com.colemcg.skydiver.core.geometry.Vector2
import com.colemcg.skydiver.core.systems.GameRenderer

/**
 * Passive background object that drifts across the screen.
 *
 * Clouds provide visual depth and motion to the game world
 * without affecting gameplay. They use a parallax factor
 * to create the illusion of layered backgrounds.
 *
 * Continuously loops when moving off-screen to maintain
 * environmental continuity.
 *
 * Inherits from BackgroundObject and participates in the draw
 * and update loop, even during paused or game over states.
 *
 * @see BackgroundObject
 * @author Jardina Gomez
 */

class Cloud(
    startPos: Vector2, // initial position of the cloud in the game world
    // width and height of the cloud sprite in pixels
    private val width: Float,
    private val height: Float,
    // base drift speed in Y direction, pixels/sec (positive = down)
    private val speedY: Float,
    // adding this but not sure if it will be used yet, for horizontal drift to simulate some wind
    private val windX: Float = 0f,
    // parallax depth: .3f(far) - 1.0f(near)
    override val parallaxFactor: Float,
    private val getLoopHeight: () -> Float // function to get the height of the background loop

    ): BackgroundObject(
        position = startPos.copy(), // copy to avoid mutating the original Vector2
        velocity = Vector2(windX, speedY),
        parallaxFactor = parallaxFactor

    ){
        /** Sprite metadata used by renderer*/
        override val spriteName = "cloud"
        override val spriteSize = Vector2(width, height)

        /** computed hitbox from current position & sprite size so it stays in sync */
        override val hitbox: Rect
            get() = Rect(position.x, position.y, spriteSize.x, spriteSize.y)

        /** Update cloud motion. Ignoring gameSpeed so clouds keep drifting during pauses/game over.*/
        override fun update(deltaTime: Float, player: Player, gameSpeed: Float) {
            // drift using only parallax scaling
            position += Vector2(velocity.x, velocity.y) * deltaTime * parallaxFactor


            // wrap vertically to create continuous looping background
            checkAndLoopIfNeeded(getLoopHeight())
        }

        override fun onDraw(renderer: GameRenderer) {
            renderer.drawGameObject(this, position)
        }
    }
