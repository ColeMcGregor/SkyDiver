package com.colemcg.skydiver.core.game

import com.colemcg.skydiver.core.events.InputEvent

/**
 * Interface for the game loop, which is responsible for updating the game state and rendering the game.
 * This interface will be implemented by the platform-specific game loop (e.g., Android or iOS).
 *
 * @author Jardina Gomez
 *
 */
interface GameLoop {

    /**
     * Starts the game loop.
     * This method should be called to begin the game loop and start updating the game state.
     */
    fun start()

    /**
     * Stops the game loop.
     * This method should be called to stop the game loop and freeze the game state.
     */
    fun stop()

    /**
     * Updates the game state based on the elapsed time since the last update.
     *
     * @param deltaTime The time in seconds since the last update.
     */
    fun update(deltaTime: Float)

    /**
     * Handles input events from the user.
     *
     * @param event The input event to handle.
     */
    fun handleInput(event: InputEvent)

    /**
     * Draws the game state to the screen.
     */
    fun updateAndDraw()

}

