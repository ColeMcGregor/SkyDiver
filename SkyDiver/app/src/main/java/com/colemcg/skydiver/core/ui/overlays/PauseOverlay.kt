package com.colemcg.skydiver.core.ui.overlays


import com.colemcg.skydiver.core.events.InputEvent
import com.colemcg.skydiver.core.events.InputType
import com.colemcg.skydiver.core.game.SCREEN_WIDTH
import com.colemcg.skydiver.core.geometry.Rect
import com.colemcg.skydiver.core.geometry.Vector2
import com.colemcg.skydiver.core.systems.GameRenderer
import com.colemcg.skydiver.core.ui.UIOverlay
import com.colemcg.skydiver.core.game.GameState

/**
 * UI overlay displayed when the game is paused.
 *
 * Prevents gameplay from progressing and gives options
 * to resume, return to main menu, or open the OptionsOverlay.
 *
 * Updates GameState by setting gamePaused = true.
 * Resumes gameplay by calling GameState.resumeGame().
 *
 * @author Cole McGregor
 */

class PauseOverlay(
    private val OnResume:() -> Unit, // called when player taps "Resume" button
    private val OnOptions:() -> Unit, // called when player taps "Options" button
    private val OnMainMenu:() -> Unit // called when player taps "Main Menu" button
    private val OnExit:() -> Unit // called when player taps "Exit" button
) : UIOverlay {
    val buttonWidth = 200f // Width of each button
    val buttonHeight = 80f // Height of each button
    val x = (SCREEN_WIDTH - buttonWidth) / 2f // centers the buttons

    val yStart = 350f // top of the first button, 350 pixels from the top of the screen
    val spacing = 100f // space between buttons

    // --- Button positions ---
    // stack buttons vertically with some padding
    val resumeButtonPos = Vector2(x, yStart) // Position of the Start button
    val optionsButtonPos = Vector2(x, yStart + spacing) // Position of the Options button
    val mainMenuButtonPos = Vector2(x, yStart + 2 * spacing) // Position of the Main Menu button
    val exitButtonPos = Vector2(x, yStart + 3 * spacing) // Position of the Exit button

    // --- Button hitboxes ---
    val resumeButtonRect = Rect(resumeButtonPos.x, resumeButtonPos.y, buttonWidth, buttonHeight)
    val optionsButtonRect = Rect(optionsButtonPos.x, optionsButtonPos.y, buttonWidth, buttonHeight)
    val mainMenuButtonRect = Rect(mainMenuButtonPos.x, mainMenuButtonPos.y, buttonWidth, buttonHeight)
    val exitButtonRect = Rect(exitButtonPos.x, exitButtonPos.y, buttonWidth, buttonHeight)


    // Keeps track of whether the overlay is currently visible
    private var isVisible = false

    /**
     * Called when the screen becomes visible.
     * Initialize animations or reset internal state here.
     */
    override fun show() {
        // pause game
        GameState.pauseGame()
        // set visible to true
        isVisible = true
    }

    /**
     * Called when the screen is hidden.
     * Cleanup or pause activities here if necessary.
     */
    override fun hide() {
        // resume game
        GameState.resumeGame()
        // set visible to false
        isVisible = false
    }

    /**
     * Called when an input event (e.g., tap, drag) is dispatched to this screen.
     * Use this to handle button presses, menu navigation, etc.
     *
     * @param event the input event to respond to
     */
    override fun handleInput(event: InputEvent){
        // if not visible, ignore input, if input is not a tap, ignore it
        if (!isVisible || event.type != InputType.Tap) return

        // get the position of the tap
        val tap = event.position

        // check which button was tapped
        when {
            resumeButtonRect.contains(tap) -> {
                // run the resume callback
                OnResume()
                // hide the overlay
                hide()
            }

            optionsButtonRect.contains(tap) -> {
                // run the options callback
                OnOptions()
                //dont hide because options will come back
            }

            mainMenuButtonRect.contains(tap) -> {
                // run the main menu callback
                OnMainMenu()
                // hide the overlay
                hide()
            }
        }
    }

    /**
     * Updates the screen state based on the time passed since the last update.
     * This is useful for animations or time-based logic.
     *
     * @param deltaTime the time in seconds since the last update
     */
    override fun update(deltaTime: Float){
        //Add any logic to update animations or state based on deltaTime
    }

    /**
     * Draws the overlay to the screen.
     * This method should handle all rendering logic for the overlay.
     *
     * @param renderer the renderer used to draw the overlay
     */
    override fun draw(renderer: GameRenderer){
        if (!isVisible) return

        renderer.drawMessageOverlay("Paused")
        renderer.drawUIElement("Resume", resumeButtonPos)
        renderer.drawUIElement("Options", optionsButtonPos)
        renderer.drawUIElement("Main Menu", mainMenuButtonPos)
    }
    
    
}
