package com.colemcg.skydiver.core.game

/**
 * Central authority on high-level game session state.
 * 
 * Used to gate input, spawning, scoring, and rendering logic
 * based on the current status of the simulation.
 * 
 * NOTE: This is a shared global and should be mutated carefully.
 * 
 * @author Cole McGregor
 */
object GameState {
    var gameStarted: Boolean = false
        private set //this is a fancy way of saying that the variable can only be set within this file

    var gamePaused: Boolean = false
        private set //here too

    var gameOver: Boolean = false
        private set //here too

    /**
     * Begins the game session. Enables input and spawning.
     * says the game has started, so we should not be paused or over
     */
    fun startGame() {
        gameStarted = true
        gamePaused = false
        gameOver = false
    }

    /**
     * Ends the current session and freezes game logic.
     */
    fun endGame() {
        gameOver = true
    }

    /**
     * Temporarily pauses the game (e.g., on overlay open).
     */
    fun pauseGame() {
        gamePaused = true
    }

    /**
     * Resumes from paused state.
     */
    fun resumeGame() {
        gamePaused = false
    }

    /**
     * Resets all state flags. Called before a new session. will be initiatilized to non-false values when Game is started
     */
    fun reset() {
        gameStarted = false
        gamePaused = false
        gameOver = false
    }
}
