package com.colemcg.skydiver.core.ui.overlays

import com.colemcg.skydiver.core.events.InputEvent
import com.colemcg.skydiver.core.events.InputType
import com.colemcg.skydiver.core.game.GameState
import com.colemcg.skydiver.core.game.SCREEN_WIDTH
import com.colemcg.skydiver.core.geometry.Rect
import com.colemcg.skydiver.core.geometry.Vector2
import com.colemcg.skydiver.core.systems.GameRenderer
import com.colemcg.skydiver.core.ui.UIOverlay
import com.colemcg.skydiver.core.systems.ScoreManager

/**
 * Overlay shown after the player loses the game.
 *
 * Displays final score and relevant stats from the completed session.
 * Prevents gameplay interaction until user returns to menu or restarts.
 *
 * Triggers when GameState transitions to gameOver = true.
 * 
 * @author Cole McGregor
 */
class GameOverOverlay(
    //TODO: SCOREMANAGER NEEDS TO BE FED IN BY GAMELOOP
    private val scoreManager: ScoreManager,     // Used to display score and stats
    private val onRestart: () -> Unit,          // Callback for restarting the game
    private val onMainMenu: () -> Unit,         // Callback for returning to the main menu
    private val onExit: () -> Unit              // Callback for exiting the game
) : UIOverlay {

    private val buttonWidth = 220f
    private val buttonHeight = 80f
    private val x = (SCREEN_WIDTH - buttonWidth) / 2f

    private val yStart = 500f
    private val spacing = 100f

    // Positions
    private val restartPos = Vector2(x, yStart)
    private val menuPos = Vector2(x, yStart + spacing)
    private val exitPos = Vector2(x, yStart + 2 * spacing)

    // Hitboxes
    private val restartRect = Rect(restartPos.x, restartPos.y, buttonWidth, buttonHeight)
    private val menuRect = Rect(menuPos.x, menuPos.y, buttonWidth, buttonHeight)
    private val exitRect = Rect(exitPos.x, exitPos.y, buttonWidth, buttonHeight)

    // Keeps track of whether the overlay is currently visible
    private var isVisible = false

    override fun show() {
        isVisible = true
        GameState.pauseGame() // ensures input doesn't affect the world
    }

    override fun hide() {
        isVisible = false
    }

    override fun handleInput(event: InputEvent) {
        // if not visible, ignore input, if input is not a tap, ignore it
        if (!isVisible || event.type != InputType.Tap) return
        // get the position of the tap
        val tap = event.position

        // check which button was tapped
        when {
            restartRect.contains(tap) -> {
                // run the restart callback
                onRestart()
                hide()
            }
            menuRect.contains(tap) -> {
                // run the main menu callback
                onMainMenu()
                hide()
            }
            exitRect.contains(tap) -> {
                // run the exit callback
                onExit()
                hide()
            }
        }
    }

    override fun update(deltaTime: Float) {
        // No animation or state change logic needed for now
    }

    override fun draw(renderer: GameRenderer) {
        if (!isVisible) return

        renderer.drawMessageOverlay("Game Over")
        //TODO: make these stats dynamic and correct
        //------------------------------------------
        renderer.drawUIElement("Final Score: ${scoreManager.getScore()}", Vector2(x, 250f))
        renderer.drawUIElement("Highest Multiplier: ${scoreManager.getMaxStreak()}", Vector2(x, 320f))
        //------------------------------------------
        renderer.drawUIElement("Restart", restartPos)
        renderer.drawUIElement("Main Menu", menuPos)
        renderer.drawUIElement("Exit", exitPos)
    }
}
