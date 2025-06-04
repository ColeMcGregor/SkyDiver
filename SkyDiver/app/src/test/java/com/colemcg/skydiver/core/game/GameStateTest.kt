package com.colemcg.skydiver.core.game

import org.junit.After
import org.junit.Assert.*
import org.junit.Test

class GameStateTest {

    @After
    fun tearDown() {
        // Ensure clean state between tests
        GameState.reset()
    }

    @Test
    fun `initial state is not started, paused, or over`() {
        assertFalse(GameState.gameStarted)
        assertFalse(GameState.gamePaused)
        assertFalse(GameState.gameOver)
    }

    @Test
    fun `startGame sets started true and clears pause and game over`() {
        GameState.startGame()
        assertTrue(GameState.gameStarted)
        assertFalse(GameState.gamePaused)
        assertFalse(GameState.gameOver)
    }

    @Test
    fun `endGame sets gameOver true`() {
        GameState.startGame()
        GameState.endGame()
        assertTrue(GameState.gameOver)
    }

    @Test
    fun `pauseGame sets gamePaused true`() {
        GameState.startGame()
        GameState.pauseGame()
        assertTrue(GameState.gamePaused)
    }

    @Test
    fun `resumeGame sets gamePaused false`() {
        GameState.startGame()
        GameState.pauseGame()
        GameState.resumeGame()
        assertFalse(GameState.gamePaused)
    }

    @Test
    fun `reset clears all flags`() {
        GameState.startGame()
        GameState.endGame()
        GameState.pauseGame()
        GameState.reset()

        assertFalse(GameState.gameStarted)
        assertFalse(GameState.gamePaused)
        assertFalse(GameState.gameOver)
    }
}
