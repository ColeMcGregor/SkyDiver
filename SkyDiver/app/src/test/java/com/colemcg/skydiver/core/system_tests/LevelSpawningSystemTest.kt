package com.colemcg.skydiver.core.levels

import com.colemcg.skydiver.core.entities.*
import com.colemcg.skydiver.core.game.Spawner
import com.colemcg.skydiver.core.systems.DifficultyManager
import com.colemcg.skydiver.core.game.MINIMUM_SPAWN_INTERVAL
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue
import kotlin.test.assertEquals

/**
 * A System Level Test
 * Tests the interaction between the following classes:
 * LevelManager
 * Level (abstract + specific implementations)
 * Spawner
 * Obstacle subclasses (Kite, Balloon, HangGlider)
 * Collectible subclasses (Coin, Multiplier)
 * DifficultyManager
* 
 * This test suite verifies that:
 * - The Spawner creates only valid entity types as defined by the currently active Level
 * - The DifficultyManager influences spawn interval over time
 * - Spawn interval never drops below the defined minimum threshold
 * - Switching levels dynamically changes the types of spawnable entities
 * - The Spawner correctly populates a shared object list for the GameManager
 * 
 * These tests serve to validate the cohesion and runtime behavior of the Level + Spawning System,
 * ensuring proper integration and dynamic response under real gameplay-like conditions.
 */

class LevelSpawningSystemTest {

    private lateinit var spawner: Spawner
    private lateinit var levelManager: LevelManager
    private lateinit var difficultyManager: DifficultyManager
    private val objects = mutableListOf<GameObject>()

    @BeforeEach
    fun setup() {
        levelManager = LevelManager()
        difficultyManager = DifficultyManager()
        spawner = Spawner(levelManager)

        // Register a fake level for testing
        levelManager.registerLevel(object : Level() {
            override val name = "TestLevel"
            override val backgroundImage = "test.png"
            override val obstacleTypes = listOf(::Kite, ::Balloon)
            override val collectibleTypes = listOf(::Coin)
            override val bgObjectTypes = emptyList<() -> BackgroundObject>()
        })
        levelManager.switchTo("TestLevel")
    }

    @Test
    fun `spawner only spawns valid level-defined types`() {
        repeat(100) {
            spawner.update(1f, objects)
        }

        assertTrue(objects.all {
            it is Obstacle && (it is Kite || it is Balloon) ||
            it is Collectible && it is Coin
        })
    }

    @Test
    fun `spawn interval decreases as difficulty increases`() {
        val intervals = mutableListOf<Float>()
        repeat(10) {
            difficultyManager.update(10f)
            intervals.add(difficultyManager.adjustSpawnRate())
        }

        assertTrue(intervals.zipWithNext().all { (a, b) -> b >= MINIMUM_SPAWN_INTERVAL && b <= a })
    }

    @Test
    fun `spawner does not exceed minimum interval`() {
        repeat(100) {
            difficultyManager.update(999f) // simulate extreme difficulty
            val rate = difficultyManager.adjustSpawnRate()
            assertTrue(rate >= MINIMUM_SPAWN_INTERVAL)
        }
    }

    @Test
    fun `switching levels updates spawnable types`() {
        // Register second level with only HangGlider
        levelManager.registerLevel(object : Level() {
            override val name = "HangLevel"
            override val backgroundImage = "hang.png"
            override val obstacleTypes = listOf(::HangGlider)
            override val collectibleTypes = emptyList<() -> Collectible>()
            override val bgObjectTypes = emptyList<() -> BackgroundObject>()
        })
        levelManager.switchTo("HangLevel")
        objects.clear()

        repeat(100) {
            spawner.update(1f, objects)
        }

        assertTrue(objects.all { it is HangGlider })
    }

    @Test
    fun `spawner populates the object list`() {
        val initialSize = objects.size
        spawner.update(1f, objects)
        assertTrue(objects.size > initialSize, "Spawner should have added objects to the list.")
    }
}
