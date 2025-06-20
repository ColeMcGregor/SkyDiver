package com.colemcg.skydiver

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import core.game.GameManager
import core.game.GameState
import core.ui.UIManager
import core.events.InputEvent
import platform.android.AndroidGameLoop
import platform.android.MotionEventAdapter

/**
 * MainActivity is the Android entry point of the SkiDiver app.
 * It wires the game loop into the Android lifecycle.
 *  - onCreate () : sets everything up
 *  - onResume() : starts the game loop
 *  - onPause() : stops the game loop
 *
 *  Touch events are adapted and passed to the game loop.
 *
 *  @author Jardina Gomez
 *
 */
class MainActivity : AppCompatActivity{
    // Declaring game components
    private lateinit var gameManager: GameManager // Manages game logic
    private lateinit var uiManager: UIManager // Manages UI overlays
    private lateinit var gameState: GameState // Tracks game state (paused, started, etc.)
    private lateinit var gameLoop: AndroidGameLoop // The game loop that updates and draws the game
    private lateinit var motionEventAdapter: MotionEventAdapter // Adapts Android MotionEvents to InputEvents


    /**
     * Called once when the Activity starts.
     * This is where we instantiate game systems and connect the loop.
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Set the layout for this activity

        // Initialize game components
        gameManager = GameManager() // Create the game manager
        uiManager = UIManager() // Create the UI manager
        gameState = GameState() // Create the game state tracker
        motionEventAdapter = MotionEventAdapter() // Create the motion event adapter

        // Connect them into the game loop
        gameLoop = AndroidGameLoop(
            gameManager = gameManager,
            uiManager = uiManager,
            gameState = gameState
        )
    }

    /**
     * Called  everyt time the app comes to the foreground.
     * We start the game loop thread here.
     *
     */
    override fun onResume() {
        super.onResume()
        gameLoop.start() // Start the game loop when the activity resumes
    }

    /**
     * onPause() is called when the app goes to the background (e.g. user switches apps).
     * Stop the game loop to avoid CPU usage while not visible.
     */
    override fun onPause() {
        super.onPause()
        gameLoop.stop()
    }

    /**
     * onTouchEvent() is called whenever the user touches the screen.
     * We convert Android's MotionEvent to our own InputEvent, and pass it to the game loop.
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val inputEvent: InputEvent = inputAdapter.adapt(event)
        gameLoop.handleInput(inputEvent)
        return true // Let Android know we handled the touch
    }

}