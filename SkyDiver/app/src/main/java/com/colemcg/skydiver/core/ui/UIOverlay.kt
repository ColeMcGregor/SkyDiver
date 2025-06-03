package com.colemcg.skydiver.core.ui

import com.colemcg.skydiver.core.events.InputEvent

/**
 * Interface for UI overlays such as the start screen, options menu, or game over overlay.
 * Each overlay is responsible for drawing itself and handling input events.
 * 
 * @author Cole McGregor
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
}
