package com.colemcg.skydiver.tests

import com.colemcg.skydiver.core.systems.SpeedManager
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.math.round

class SpeedManagerTest {

    private lateinit var speedManager: SpeedManager

    @BeforeEach
    fun setup() {
        speedManager = SpeedManager(
            initialSpeed = 1.0f,
            minSpeed = 0.5f,
            maxSpeed = 3.0f,
            speedIncreaseRate = 1.0f,         // makes deltas easy to test
            minSpeedIncreaseRate = 0.5f       // same
        )
    }

    @Test
    fun `update should increase game speed and current min speed`() {
        speedManager.update(1.0f) // simulate 1 second

        // expected: gameSpeed += 1.0 * 1.0 = 2.0, minSpeed += 0.5 * 1.0 = 1.0
        assertEquals(2.0f, round(speedManager.getGameSpeed() * 100) / 100)
    }

    @Test
    fun `update should clamp game speed to max speed`() {
        // Simulate a huge delta time to exceed max
        speedManager.update(10.0f) // would bring speed > 10

        assertEquals(3.0f, speedManager.getGameSpeed()) // should be clamped to maxSpeed
    }

    @Test
    fun `update should clamp game speed to currentMinSpeed`() {
        // Force slow down below currentMinSpeed after 1 update
        speedManager.update(1.0f) // currentMinSpeed becomes 1.0
        speedManager.applySlowdown(2.0f) // drops gameSpeed to 0.0 → should clamp to 1.0

        assertEquals(1.0f, speedManager.getGameSpeed())
    }

    @Test
    fun `applySlowdown should reduce speed but not go below min`() {
        speedManager.applySlowdown(0.6f) // from 1.0 down to 0.4 → clamp to 0.5

        assertEquals(0.5f, speedManager.getGameSpeed())
    }

    @Test
    fun `applySpeedup should increase speed but not exceed max`() {
        speedManager.applySpeedup(5.0f) // 1.0 + 5.0 = 6.0 → clamp to 3.0

        assertEquals(3.0f, speedManager.getGameSpeed())
    }

    @Test
    fun `reset should return speeds to initial values`() {
        speedManager.update(1.0f)
        speedManager.applySpeedup(1.0f)
        speedManager.reset()

        assertEquals(1.0f, speedManager.getGameSpeed()) // initial
        speedManager.update(0f) // to advance currentMinSpeed if needed
        speedManager.reset()
        // indirect way to verify internal currentMinSpeed is back to 0.5
        speedManager.applySlowdown(10f)
        assertEquals(0.5f, speedManager.getGameSpeed())
    }
}
