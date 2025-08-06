package com.colemcg.skydiver.tests

import com.colemcg.skydiver.core.systems.DifficultyManager
import com.colemcg.skydiver.core.systems.ScoreManager
import com.colemcg.skydiver.core.game.Spawner
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import kotlin.test.assertEquals

class DifficultyManagerTest {

    private lateinit var spawner: Spawner
    private lateinit var scoreManager: ScoreManager
    private lateinit var difficultyManager: DifficultyManager

    @BeforeEach
    fun setup() {
        spawner = mock()
        scoreManager = mock()
        difficultyManager = DifficultyManager(
            spawner = spawner,
            scoreManager = scoreManager,
            baseSpawnInterval = 1.5f,
            penaltyWeight = 1.5f,
            sensitivity = 0.1f
        )
    }

    @Test
    fun `reset should set spawn interval to base and reset snapshot`() {
        difficultyManager.reset()
        verify(spawner).setSpawnInterval(1.5f)
    }

    @Test
    fun `update should reduce interval when performance improves`() {
        whenever(scoreManager.getCoinsCollected()).thenReturn(5)
        whenever(scoreManager.getMultipliersCollected()).thenReturn(5)
        whenever(scoreManager.getObstaclesHit()).thenReturn(0)

        difficultyManager.update()

        // performance = (5 + 5) - (0 * 1.5) = 10
        // delta = 10 - 0 = 10
        // factor = 1 - (10 * 0.1) = 0.0
        // interval = 1.5 * 0.0 = 0.0
        verify(spawner).setSpawnInterval(0.0f)
    }

    @Test
    fun `update should increase interval when performance worsens`() {
        // First, a high score to set snapshot
        whenever(scoreManager.getCoinsCollected()).thenReturn(5)
        whenever(scoreManager.getMultipliersCollected()).thenReturn(5)
        whenever(scoreManager.getObstaclesHit()).thenReturn(0)
        difficultyManager.update()

        // Now simulate worsening performance
        whenever(scoreManager.getCoinsCollected()).thenReturn(2)
        whenever(scoreManager.getMultipliersCollected()).thenReturn(1)
        whenever(scoreManager.getObstaclesHit()).thenReturn(10)
        difficultyManager.update()

        // newScore = (2 + 1) - (10 * 1.5) = -11
        // delta = -11 - 10 = -21
        // factor = 1 - (-21 * 0.1) = 3.1
        // interval = 1.5 * 3.1 = 4.65
        verify(spawner).setSpawnInterval(4.65f)
    }
}
