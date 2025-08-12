package platform.android

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
//import android.app.GameState 
import android.util.Log
import com.colemcg.skydiver.core.events.InputEvent
import com.colemcg.skydiver.core.game.GameLoop


import com.colemcg.skydiver.core.game.GameManager
import com.colemcg.skydiver.core.game.GameState
import com.colemcg.skydiver.core.ui.UIManager

/**
 * Android-specific implementation of the GameLoop interface.
 *
 * This class implements a variable time-step game loop with interpolation and CPU friendly timing.
 * It follows the Game Loop Pattern and ensures the Android system remains responsive by yeilding/sleeping.
 *
 * 
 * @author Jardina Gomez
 */

class AndroidGameLoop(
   private val gameManager: GameManager, // Game logic controller
    private val uiManager: UIManager, // UI manager for handling overlays
    private val gameState: GameState, // Tracks if game is paused, started , etc

) :GameLoop, Runnable{

    var gameView: GameView? = null // gameview reference for rendering

    // -- Configuration --
    private val targetFPS: Int = 60 // Target frames per second
    // how many nanoseconds each frame should take at the target FPS
    private val frameDurationNanos = 1_000_000_000L / targetFPS // Convert updates per second to nanoseconds

    // -- Control Flags --
    @Volatile // Volatile: ensures isRunning is thread-safe
    private var isRunning = false // Flag to control the game loop execution

    // -- Timing --
    private var lastUpdateTime = System.nanoTime() // Time marker for last frame


    /**
     *    Starts the game loop in a background thread.
     *    Only run if it's not already running.
     */
    override fun start() {
        if (!isRunning) {
            isRunning = true
            Thread(this).start() // launch run() in a new thread
            Log.d("AndroidGameLoop", "Game loop started") // Log start of the game loop
        }
    }

    /**
     * Stops the game loop by setting isRunning to false.
     */
    override fun stop() {
        isRunning = false // Set the running flag to false
        Log.d("AndroidGameLoop", "Game loop stopped") // Log stop of the game loop
    }



    /**
     * Main loop: runs in background thread
     * Updates game state and IU, then renders.
     * Uses variable delta time and interpolated rendering.
     */
    override fun run() {
        lastUpdateTime = System.nanoTime()

        while(isRunning){
            val now = System.nanoTime()

            // Calculate time since last frame in seconds
            val deltaTime = (now - lastUpdateTime) / 1_000_000_000f // Convert nanoseconds to seconds
            lastUpdateTime = now // Update last frame time

            // -- GAME LOGIC--
            // only update gameplay if started and not paused or over
            if(GameState.gameStarted && !GameState.gamePaused && !GameState.gameOver) {
                gameManager.updateAll(deltaTime) // Update game logic with delta time
            }

            // Always update UI overlays like pause menu
            uiManager.update(deltaTime) // Update UI state

            // -- INTERPOLATION --
            // calculate interpolation factor between updates (used for smooth rendering)
            val inter = ((System.nanoTime() - lastUpdateTime).toFloat() / frameDurationNanos)
                .coerceIn(0f, 1f) // Clamp between 0 and 1

            // -- RENDERING --
            gameManager.drawAll(gameManager.speedManager) // draw gameplay
            uiManager.draw() // draw UI overlays

            // -- TESTING WITH RED CIRCLE --
            gameView?.lockAndDraw { canvas:Canvas->
                val paint = Paint().apply {
                    color = Color.RED
                    style = Paint.Style.FILL
                }
                canvas.drawColor(Color.BLACK) // clear screen
                canvas.drawCircle(300f, 600f, 100f, paint) // draw red circle
            }


            // -- FRAME PACE CONTROL --
            val frameTime = System.nanoTime() - now // Total frame processing time
            val sleepTime = (frameDurationNanos - frameTime) // Remaining time until next frame

            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime / 1_000_000) // Convert to milliseconds
                } catch (e: InterruptedException) {
                    Log.e("AndroidGameLoop",  "Interrupted: ${e.message}") // Log any interruptions
                }
            } else {
                // if behind schedule, let other system processes run
                Thread.yield()
            }
        }
}
    /**
     * Handles input events (like touches or gestures).
     * Sends input to either gameplay or overlay screen depending on game state.
     */
    override fun handleInput(event: InputEvent) {
        if (gameState.gameStarted && !gameState.gamePaused && !gameState.gameOver) {
            // Forward input to the player (e.g., for movement)
            gameManager.player.movePlayer(event)
        } else {
            // Send input to overlay screen (e.g., menu buttons)
            uiManager.handleInput(event)
        }
    }
    /**
     * Required by the GameLoop interface.
     * Separately updates game and UI logic for a given deltaTime.
     */
    override fun update(deltaTime: Float) {
        if (gameState.gameStarted && !gameState.gamePaused && !gameState.gameOver) {
            gameManager.updateAll(deltaTime)
        }
        uiManager.update(deltaTime)
    }


    /**
     * Optional method for calling update and draw once manually (for tests or single-step execution).
     */
    override fun updateAndDraw() {
        val now = System.nanoTime()
        val deltaTime = (now - lastUpdateTime) / 1_000_000_000f
        lastUpdateTime = now

        if (gameState.gameStarted && !gameState.gamePaused && !gameState.gameOver) {
            gameManager.updateAll(deltaTime)
        }

        uiManager.update(deltaTime)
        gameManager.drawAll(gameManager.speedManager)
        uiManager.draw()
    }
}

