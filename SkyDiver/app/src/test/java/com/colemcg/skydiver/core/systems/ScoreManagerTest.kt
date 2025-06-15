package com.colemcg.skydiver.tests

import com.colemcg.skydiver.core.systems.ScoreManager
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ScoreManagerTest {

    private lateinit var scoreManager: ScoreManager

    @BeforeEach
    fun setup() {
        scoreManager = ScoreManager(baseMultiplier = 1.0f, maxMultiplier = 5.0f, streakThreshold = 3)
    }

    @Test
    fun `addPoints should apply combo multiplier to base points`() {
        scoreManager.addPoints(10)
        assertEquals(10, scoreManager.getScore())

        scoreManager.applyMultiplierBoost(1.0f) // multiplier = 2.0
        scoreManager.addPoints(10)
        assertEquals(10 + 20, scoreManager.getScore()) // total = 30
    }

    @Test
    fun `incrementStreak should increase maxStreak and multiplier at threshold`() {
        repeat(2) { scoreManager.incrementStreak() } // 2 streaks, not yet at threshold
        assertEquals(2, scoreManager.getMaxStreak())
        assertEquals(1.0f, scoreManager.getComboMultiplier())

        scoreManager.incrementStreak() // 3rd = threshold reached
        assertEquals(3, scoreManager.getMaxStreak())
        assertEquals(1.25f, scoreManager.getComboMultiplier()) // +0.25
    }

    @Test
    fun `comboMultiplier should not exceed maxMultiplier`() {
        repeat(30) { scoreManager.incrementStreak() }
        assertEquals(5.0f, scoreManager.getComboMultiplier()) // capped
    }

    @Test
    fun `applyMultiplierBoost should add bonus without exceeding cap`() {
        scoreManager.applyMultiplierBoost(3.0f)
        assertEquals(4.0f, scoreManager.getComboMultiplier())

        scoreManager.applyMultiplierBoost(2.0f) // would be 6.0, but max is 5.0
        assertEquals(5.0f, scoreManager.getComboMultiplier())
    }

    @Test
    fun `resetStreak should zero streak and reset multiplier`() {
        repeat(5) { scoreManager.incrementStreak() }
        scoreManager.resetStreak()
        assertEquals(0f, scoreManager.getComboMultiplier())
        assertEquals(1.0f, scoreManager.getComboMultiplier())
    }

    @Test
    fun `resetScore should zero score and reset streak`() {
        scoreManager.addPoints(50)
        scoreManager.incrementStreak()
        scoreManager.resetScore()
        assertEquals(0, scoreManager.getScore())
        assertEquals(1.0f, scoreManager.getComboMultiplier())
        assertEquals(0, scoreManager.getMaxStreak())
    }

    @Test
    fun `incrementCoinsCollected should increase coins count`() {
        repeat(3) { scoreManager.incrementCoinsCollected() }
        assertEquals(3, scoreManager.getCoinsCollected())
    }

    @Test
    fun `incrementMultipliersCollected should increase multiplier count`() {
        repeat(2) { scoreManager.incrementMultipliersCollected() }
        assertEquals(2, scoreManager.getMultipliersCollected())
    }

    @Test
    fun `incrementObstaclesHit should increase hit count`() {
        repeat(4) { scoreManager.incrementObstaclesHit() }
        assertEquals(4, scoreManager.getObstaclesHit())
    }
}
