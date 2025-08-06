package com.colemcg.skydiver



import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.colemcg.skydiver.platform.android.GameView
import com.colemcg.skydiver.platform.android.input.MotionEventAdapter
import com.colemcg.skydiver.core.events.InputEvent
import com.colemcg.skydiver.core.game.GameManager
import com.colemcg.skydiver.core.game.GameState
import com.colemcg.skydiver.core.ui.UIManager
import platform.android.AndroidGameLoop

import com.colemcg.skydiver.core.levels.LevelManager
import com.colemcg.skydiver.core.systems.SpeedManager
import com.colemcg.skydiver.core.systems.DifficultyManager
import com.colemcg.skydiver.core.systems.ScoreManager
import com.colemcg.skydiver.core.systems.StatsManager
import com.colemcg.skydiver.core.systems.SoundManager



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
class MainActivity : AppCompatActivity() {
    // Declaring game components
    private lateinit var gameManager: GameManager // Manages game logic
    private lateinit var uiManager: UIManager // Manages UI overlays
    private lateinit var gameState: GameState // Tracks game state (paused, started, etc.)
    private lateinit var gameLoop: AndroidGameLoop // The game loop that updates and draws the game
    private lateinit var motionEventAdapter: MotionEventAdapter // Adapts Android MotionEvents to InputEvents
    private lateinit var gameView: GameView

    /**
     * Called once when the Activity starts.
     * This is where we instantiate game systems and connect the loop.
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // -- Instantiate the game systems --
        val levelManager = LevelManager()
        val speedManager = SpeedManager()
        val difficultyManager = DifficultyManager()
        val scoreManager = ScoreManager()
        val statsManager = StatsManager()
        val soundManager = SoundManager()

// -- create the GameManager with those dependencies --
        gameManager = GameManager(
            levelManager = levelManager,
            speedManager = speedManager,
            difficultyManager = difficultyManager,
            scoreManager = scoreManager,
            statsManager = statsManager,
            soundManager = soundManager
        )

        // 3. Now continue as normal:
        uiManager = UIManager()
        gameState = GameState
        motionEventAdapter = MotionEventAdapter()

        gameLoop = AndroidGameLoop(gameManager, uiManager, gameState)

        gameView = GameView(this)
        gameView.gameLoop = gameLoop
        gameLoop.gameView = gameView
        setContentView(gameView)
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
        val inputEvent: InputEvent = motionEventAdapter.adapt(event)
        gameLoop.handleInput(inputEvent)
        return true // Let Android know we handled the touch
    }

}