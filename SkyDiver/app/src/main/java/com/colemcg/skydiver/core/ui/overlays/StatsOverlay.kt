package com.colemcg.skydiver.core.ui.overlays

import com.colemcg.skydiver.core.systems.StatsManager
import com.colemcg.skydiver.core.ui.UIOverlay
import com.colemcg.skydiver.core.events.InputEvent
import com.colemcg.skydiver.core.events.InputType
import com.colemcg.skydiver.core.geometry.Vector2
import com.colemcg.skydiver.core.systems.GameRenderer
import com.colemcg.skydiver.core.game.SCREEN_WIDTH
import com.colemcg.skydiver.core.game.SCREEN_HEIGHT
import com.colemcg.skydiver.core.geometry.Rect


/**
 * Overlay for viewing long-term player statistics.
 *
 * Pulls data from StatsManager to display historical performance
 * like high score, average score, and games played.
 *
 * This is a read-only screen and does not affect gameplay or GameState.
 * Usually accessible from the main menu or after a game ends.
 *
 * @author Jardina Gomez
 */

class StatsOverlay(
    private val stats: StatsManager, // to get stats data
    private val OnBack: () -> Unit,    // callback for back button
    private val showResetButton: Boolean = true // whether to show the reset button or not
) : UIOverlay {

    // ---BUTTON LAYOUT CONSTANTS---
    val buttonWidth = 200f // Width of each button
    val buttonHeight = 80f // Height of each button
    val x = (SCREEN_WIDTH - buttonWidth) / 2f // centers the buttons

    // vertical anchors
    val yStart = 250f // top of the first button,
    val spacing = 50f // space between buttons

    // --- Button positions ---
    // stack buttons vertically with some padding
    val resetY = 600f // y position of the reset button if it is shown
    val backY = 700f // y position of the back button, always shown


    // --- Label positions( one Vectior2 for each stat label) ---
    private val gamesPlayedPos = Vector2(x, yStart + spacing * 0) // Position of the Games Played label
    private val highestScorePos = Vector2(x, yStart + spacing * 1) // Position of the Highest Score label
    private val lastScorePos = Vector2(x, yStart + spacing * 2) // Position of the last Score label
    private val totalScorePos = Vector2(x, yStart + spacing * 3) // Position of the Total Score label
    private val averageScorePos = Vector2(x, yStart + spacing * 4) // Position of the Average Score label
    private val coinsCollectedPos = Vector2(x, yStart + spacing * 5) // Position of the Coins Collected label
    private val multipierPos = Vector2(x, yStart + spacing * 6) // Position of the Multipliers Collected label
    private val obstaclesHitPos = Vector2(x, yStart + spacing * 7) // Position of the Obstacles Hit label

    private val resetButtonPos = Vector2(x, resetY) // Position of the Reset Stats button
    private val backButtonPos = Vector2(x, backY) // Position of the Back button

    // --- Button hitboxes ---
    val backButtonRect = Rect(backButtonPos.x, backButtonPos.y, buttonWidth, buttonHeight)
    val resetButtonRect = Rect(resetButtonPos.x, resetButtonPos.y, buttonWidth, buttonHeight)

    // Keeps track of whether the overlay is currently visible
    private var isVisible = false
    private var resetFlashTime = 0f // time to show "Stats Reset!" message after resetting stats
    private val resetFlashDuration = 1.2f // duration to show "Stats Reset!" message in seconds

    /**
     * Called when the screen becomes visible.
     * Initialize animations or reset internal state here.
     */
    override fun show() {
        // mark overlay as visible
        isVisible = true
    }
    /**
     * Called when the screen is hidden.
     * Cleanup or pause activities here if necessary.
     */
    override fun hide() {
        // mark overlay as not visible
        isVisible = false
        }

    override fun handleInput(event: InputEvent) {
        if(isVisible || event.type != InputType.Tap) return // ignore input if not visible or not a tap
        val tap = event.position // get tap position

        when {
            showResetButton && resetButtonRect.contains(tap) -> { // if reset button is tapped
                stats.resetStats() // reset stats
                resetFlashTime = resetFlashDuration // start flash timer
            }
            backButtonRect.contains(tap) -> { // if back button is tapped
                OnBack() // invoke the back callback
                hide()
            }
        }
    }

    override fun update(deltaTime: Float) {
        // update the reset flash timer
        if (resetFlashTime > 0f) {
            resetFlashTime -= deltaTime
            if (resetFlashTime < 0f) resetFlashTime = 0f // clamp to zero
        }
    }
    override fun draw(renderer: GameRenderer) {
        if (!isVisible) return // don't draw if not visible

        renderer.drawMessageOverlay("Stats") // draw overlay background with title
        // get values from StatsManager
        val games = stats.getGamesPlayed()
        val highScore = stats.getHighestScore()
        val lastScore = stats.getLastScore()
        val totalScore = stats.getTotalScore()
        val avgScoreForm = "%.1f".format(stats.getAverageScore())
        val coins = stats.getCoinsCollected()
        val multipliers = stats.getMultipliersCollected()
        val obstacles = stats.getObstaclesHit()

        // draw each stat row
        renderer.drawUIElement("Games Played: $games", gamesPlayedPos)
        renderer.drawUIElement("Highest Score: $highScore", highestScorePos)
        renderer.drawUIElement("Last Score: $lastScore", lastScorePos)
        renderer.drawUIElement("Total Score: $totalScore", totalScorePos)
        renderer.drawUIElement("Average Score: $avgScoreForm", averageScorePos)
        renderer.drawUIElement("Coins Collected: $coins", coinsCollectedPos)
        renderer.drawUIElement("Multipliers Collected: $multipliers", multipierPos)
        renderer.drawUIElement("Obstacles Hit: $obstacles", obstaclesHitPos)

        // stats cleared confiration flash
        if (resetFlashTime > 0f) {
            renderer.drawMessageOverlay("Stats Cleared!") // draw flash message over everything else
        }

        // action button
        if (showResetButton) {
            renderer.drawUIElement("Reset Stats", resetButtonPos)
        }
        renderer.drawUIElement("Back", backButtonPos)




    }
}