package com.colemcg.skydiver.core.ui

import com.colemcg.skydiver.core.systems.GameRenderer
import com.colemcg.skydiver.core.events.InputEvent

/**
 * Responsible for controlling which UI overlay is currently visible. Routing
 * input, update, and draw calls to the active overlay.
 *
 * @property activeScreen The currently active UI overlay, or null if none is active.
 *
 * @author Jardina Gomez
 */
class UIManager (

    var activeScreen: UIOverlay? = null
){
    /**
     * Shows the specified UI overlay, hiding any currently active screen.
     *
     * @param screen The UI overlay to show.
     */
    fun show(screen: UIOverlay){
        activeScreen?.hide() // if theres a curr overlay it gets hidden
        activeScreen = screen // setting new overlay as active
        screen.show()
    }

    /**
     * Hides specified overlay, only if it is currently active.
     * Setting activeScreen to null if it was the active one.
     *
     * @param screen The UI overlay to hide.
     */
    fun hide(screen: UIOverlay){
        if(activeScreen == screen){
            activeScreen?.hide()
            activeScreen = null // No overlay is active now
        }

    }

    /**
     * Handles input events (like taps or drags) by delegating them to the currently active screen.
     *
     * @param event The input event to handle, such as a tap or drag.
     */
    fun handleInput(event: InputEvent){
        activeScreen?.handleInput(event)
    }

    /**
     * Updates the currently active screen with the time passed since the last update.
     *
     * @param deltaTime The time passed since the last update, in seconds.
     */
    fun update(deltaTime: Float){
        activeScreen?.update(deltaTime)
    }

    /**
     * Tells active overlay to draw itself using the provided renderer.
     *
     * @param renderer The renderer to use for drawing UI elements.
     */
    fun draw(renderer: GameRenderer){
        actvieScreen?.draw(renderer)
    }
}