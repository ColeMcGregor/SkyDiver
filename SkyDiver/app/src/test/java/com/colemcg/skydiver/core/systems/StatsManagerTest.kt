package com.colemcg.skydiver.tests

import com.colemcg.skydiver.core.systems.StatsManager
import com.colemcg.skydiver.core.systems.KeyValueStorage
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import kotlin.test.assertEquals

class StatsManagerTest {

    private lateinit var storage: KeyValueStorage
    private lateinit var statsManager: StatsManager

    @BeforeEach
    fun setup() {
        storage = mock()
        statsManager = StatsManager(storage)
    }

    @Test
    fun `recordRun should update and store all relevant stats`() {
        // Initial mock return values
        whenever(storage.getInt("gamesPlayed", 0)).thenReturn(2)
        whenever(storage.getInt("totalScore", 0)).thenReturn(150)
        whenever(storage.getInt("highestScore", 0)).thenReturn(80)
        whenever(storage.getInt("coinsCollected", 0)).thenReturn(10)
        whenever(storage.getInt("multipliersCollected", 0)).thenReturn(5)
        whenever(storage.getInt("obstaclesHit", 0)).thenReturn(3)

        // Run the method
        statsManager.recordRun(score = 100, coins = 4, multipliers = 2, obstacles = 1)

        // Expected results
        verify(storage).putInt("gamesPlayed", 3)
        verify(storage).putInt("totalScore", 250)
        verify(storage).putInt("highestScore", 100)
        verify(storage).putInt("lastScore", 100)
        verify(storage).putInt("coinsCollected", 14)
        verify(storage).putInt("multipliersCollected", 7)
        verify(storage).putInt("obstaclesHit", 4)
    }

    @Test
    fun `get methods should return correct values from storage`() {
        whenever(storage.getInt("gamesPlayed", 0)).thenReturn(3)
        whenever(storage.getInt("highestScore", 0)).thenReturn(120)
        whenever(storage.getInt("totalScore", 0)).thenReturn(300)
        whenever(storage.getInt("lastScore", 0)).thenReturn(75)
        whenever(storage.getInt("coinsCollected", 0)).thenReturn(20)
        whenever(storage.getInt("multipliersCollected", 0)).thenReturn(10)
        whenever(storage.getInt("obstaclesHit", 0)).thenReturn(5)

        assertEquals(3, statsManager.getGamesPlayed())
        assertEquals(120, statsManager.getHighestScore())
        assertEquals(300, statsManager.getTotalScore())
        assertEquals(75, statsManager.getLastScore())
        assertEquals(20, statsManager.getCoinsCollected())
        assertEquals(10, statsManager.getMultipliersCollected())
        assertEquals(5, statsManager.getObstaclesHit())
    }

    @Test
    fun `getAverageScore should return correct average`() {
        whenever(storage.getInt("gamesPlayed", 0)).thenReturn(4)
        whenever(storage.getInt("totalScore", 0)).thenReturn(200)

        assertEquals(50f, statsManager.getAverageScore())
    }

    @Test
    fun `getAverageScore should return 0 if no games played`() {
        whenever(storage.getInt("gamesPlayed", 0)).thenReturn(0)

        assertEquals(0f, statsManager.getAverageScore())
    }

    @Test
    fun `resetStats should zero out all tracked fields`() {
        statsManager.resetStats()

        verify(storage).putInt("gamesPlayed", 0)
        verify(storage).putInt("totalScore", 0)
        verify(storage).putInt("highestScore", 0)
        verify(storage).putInt("lastScore", 0)
        verify(storage).putInt("obstaclesHit", 0)
        verify(storage).putInt("coinsCollected", 0)
        verify(storage).putInt("multipliersCollected", 0)
    }
}
