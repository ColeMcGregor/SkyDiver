package com.colemcg.skydiver.tests

import com.colemcg.skydiver.core.game.Spawner
import com.colemcg.skydiver.core.levels.Level
import com.colemcg.skydiver.core.levels.LevelManager
import com.colemcg.skydiver.core.entities.*
import com.colemcg.skydiver.core.geometry.Vector2
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.*
import org.mockito.kotlin.*

class SpawnerTest {

    private lateinit var levelManager: LevelManager
    private lateinit var spawner: Spawner
    private lateinit var testLevel: Level

    private class DummyObstacle : Obstacle() {
        override var position: Vector2 = Vector2(0f, 0f)
    }

    private class DummyCollectible : Collectible() {
        override var position: Vector2 = Vector2(0f, 0f)
    }

    private class DummyLevel : Level() {
        override val name = "TestLevel"
        override val backgroundImage = "test.png"
        override val initialOffset = 0f
        override val obstacleTypes = listOf<() -> Obstacle>({ DummyObstacle() })
        override val collectibleTypes = listOf<() -> Collectible>({ DummyCollectible() })
        override val bgObjectTypes = emptyList<() -> BackgroundObject>()
    }

    @BeforeEach
    fun setup() {
        testLevel = DummyLevel()
        levelManager = mock()
        whenever(levelManager.getCurrentLevel()).thenReturn(testLevel)
        spawner = Spawner(levelManager)
    }

    @Test
    fun `update should spawn object after reaching spawn interval`() {
        val objects = mutableListOf<GameObject>()
        spawner.setSpawnInterval(0.5f)
        spawner.update(0.3f, objects)
        assertTrue(objects.isEmpty())

        spawner.update(0.3f, objects)
        assertEquals(1, objects.size)
    }

    @Test
    fun `update should not spawn if below interval`() {
        val objects = mutableListOf<GameObject>()
        spawner.setSpawnInterval(2.0f)
        spawner.update(0.1f, objects)
        assertTrue(objects.isEmpty())
    }

    @Test
    fun `setSpawnInterval should not allow value below minimum`() {
        spawner.setSpawnInterval(0.1f)
        assertEquals(0.25f, spawner.getSpawnInterval()) // Clamped
    }

    @Test
    fun `reset should clear spawn timer`() {
        val objects = mutableListOf<GameObject>()
        spawner.setSpawnInterval(1.0f)
        spawner.update(1.1f, objects) // causes spawn
        assertEquals(1, objects.size)

        spawner.reset()
        spawner.update(0.5f, objects)
        assertEquals(1, objects.size) // no new spawn yet
    }

    @Test
    fun `spawnObstacle should assign random position`() {
        val method = Level::class.java.getDeclaredMethod("spawnObstacle", List::class.java)
        method.isAccessible = true
        val result = method.invoke(spawner, testLevel.obstacleTypes) as Obstacle
        assertTrue(result.position.y <= 0f) // should be above screen
    }

    @Test
    fun `spawnCollectible should assign random position`() {
        val method = Level::class.java.getDeclaredMethod("spawnCollectible", List::class.java)
        method.isAccessible = true
        val result = method.invoke(spawner, testLevel.collectibleTypes) as Collectible
        assertTrue(result.position.y <= 0f)
    }
}
