package com.colemcg.skydiver.core.ui

import com.colemcg.skydiver.core.events.InputEvent
import com.colemcg.skydiver.core.systems.GameRenderer

/**
 * Interface for UI overlays such as the start screen, options menu, or game over overlay.
 * Each overlay is responsible for drawing itself and handling input events.
 * 
 * @author Cole McGregor and Jardina Gomez(added last two methods)
 */
interface UIOverlay {

    /**
     * Called when the screen becomes visible.
     * Initialize animations or reset internal state here.
     */
    fun show()

    /**
     * Called when the screen is hidden.
     * Cleanup or pause activities here if necessary.
     */
    fun hide()

    /**
     * Called when an input event (e.g., tap, drag) is dispatched to this screen.
     * Use this to handle button presses, menu navigation, etc.
     *
     * @param event the input event to respond to
     */
    fun handleInput(event: InputEvent)

    /**
     * Updates the screen state based on the time passed since the last update.
     * This is useful for animations or time-based logic.
     *
     * @param deltaTime the time in seconds since the last update
     */
    fun update(deltaTime: Float)

    /**
     * Draws the overlay to the screen.
     * This method should handle all rendering logic for the overlay.
     *
     * @param renderer the renderer used to draw the overlay
     */
    fun draw(renderer: GameRenderer)
}
