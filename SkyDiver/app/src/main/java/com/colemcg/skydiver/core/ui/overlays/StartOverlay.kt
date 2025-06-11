package com.colemcg.skydiver.core.ui.overlays

import com.colemcg.skydiver.core.events.InputEvent
import com.colemcg.skydiver.core.events.InputType
import com.colemcg.skydiver.core.game.SCREEN_WIDTH
import com.colemcg.skydiver.core.geometry.Rect
import com.colemcg.skydiver.core.geometry.Vector2
import com.colemcg.skydiver.core.systems.GameRenderer
import com.colemcg.skydiver.core.ui.UIOverlay



/**
 * Main menu overlay shown at app launch.
 *
 * Provides navigation to start a game, view stats, open options,
 * or view credits.It implements the UIOverlay interface.
 *
 * Typically the first screen users see after splash.
 *
 * @author Jardina Gomez
 */
class StartOverlay(
    // callbacks that will run when button is pressed
    private val OnStart:() -> Unit, // called when player taps "Start" button
    private val OnStats:() -> Unit, // called when player taps "Stats" button
    private val OnOptions:() -> Unit, // called when player taps "Options" button
    private val OnCredits:() -> Unit // called when player taps "Credits" button
) : UIOverlay { // UIOverlay is the base interface for all UI screens

    // ---BUTTON LAYOUT CONSTANTS---
    // defining button positons and hitboxes: hitboxes are Rects that define the area of the button
    val buttonWidth = 200f // Width of each button
    val buttonHeight = 80f // Height of each button
    val x = (SCREEN_WIDTH - buttonWidth) / 2f // centers the buttons

    val yStart = 350f // top of the first button, 350 pixels from the top of the screen
    val spacing = 100f // space between buttons

    // --- Button positions ---
    // stack buttons vertically with some padding
    val startButtonPos = Vector2(x, yStart) // Position of the Start button
    val optionsButtonPos = Vector2(x, yStart + spacing) // Position of the Options button
    val statsButtonPos = Vector2(x, yStart + 2 * spacing) // Position of the Stats button
    val creditsButtonPos = Vector2(x, yStart + 3 * spacing) // Position of the Credits button

    // --- Button hitboxes ---
    val startButtonRect = Rect(startButtonPos.x, startButtonPos.y, buttonWidth, buttonHeight)
    val optionsButtonRect = Rect(optionsButtonPos.x, optionsButtonPos.y, buttonWidth, buttonHeight)
    val statsButtonRect = Rect(statsButtonPos.x, statsButtonPos.y, buttonWidth, buttonHeight)
    val creditsButtonRect = Rect(creditsButtonPos.x, creditsButtonPos.y, buttonWidth, buttonHeight)


    // Keeps track of whether the overlay is currently visible
    private var isVisible = false

    /**
     * Called when overlay chould become visible.
     * Used to reset any state or start animations.
     *
     */
    override fun show() {
        isVisible = true
        // TODO: Add any animations or state resets needed when showing the overlaY
    }

    /**
     * Called when overlay is no longer visible.
     * Used to pause animations or cleanup resources.
     *
     */
    override fun hide() {
        isVisible = false
        // TODO: Add any cleanup or pause logic needed when hiding the overlay

    }

    /**
     * Handles input events like taps or drags.
     * Where to check if user tapped a button and run matching callback.
     *
     * @param event The InputEven describing what the user did.
     */
    override fun handleInput(event: InputEvent) {
        // if not visible, ignore input
        if (!isVisible) return
        // only respond to tap events
        if (event.type != InputType.Tap) {
            val pos = event.position // get the position of the tap

            // check which button was tapped
            when {
                startButtonRect.contains(pos) -> OnStart() // Start button tapped
                optionsButtonRect.contains(pos) -> OnOptions() // Options button tapped
                statsButtonRect.contains(pos) -> OnStats() // Stats button tapped
                creditsButtonRect.contains(pos) -> OnCredits() // Credits button tapped
            }
        }

    }

    /**
     * Updates the overlay with the time passed since the last update.
     * This is where animations or state changes can be updated.
     *
     * @param deltaTime The time passed since the last update.
     */
    override fun update(deltaTime: Float) {
        // TODO: Add any logic to update animations or state based on deltaTime

    }

    /**
     * Draws the overlay using the provided GameRenderer.
     * This is where the UI elements are rendered to the screen.
     *
     * @param renderer The GameRenderer used to draw the overlay.
     */
    override fun draw(renderer: GameRenderer) {
        if (!isVisible) return // Don't draw if not visible

        // draw gae title at top
        renderer.drawText("Skydiver", 100f, 50f, size = 48f) // Example title

        // draw main menu buttons - replace nulls with actaul Vector2 postions when screen size has been figured out
        // drawUIElement?
        renderer.drawButton("Start", /* Vector2(x1,y1) */ null) // Start button
        renderer.drawButton("Options", /* Vector2(x2,y2) */ null) // Options button
        renderer.drawButton("Stats", /* Vector2(x3,y3) */ null) // Stats button
        renderer.drawButton("Credits", /* Vector2(x4,y4) */ null) // Credits button
    }

}




