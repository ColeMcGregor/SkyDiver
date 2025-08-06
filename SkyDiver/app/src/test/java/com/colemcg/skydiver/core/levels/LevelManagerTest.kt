package com.colemcg.skydiver.tests

import com.colemcg.skydiver.core.levels.Level
import com.colemcg.skydiver.core.levels.LevelManager
import com.colemcg.skydiver.core.entities.Obstacle
import com.colemcg.skydiver.core.entities.Collectible
import com.colemcg.skydiver.core.entities.BackgroundObject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.*

class LevelManagerTest {

    private lateinit var levelManager: LevelManager

    // Minimal implementation of Level for testing
    private class TestLevel(
        override val name: String,
        override val backgroundImage: String = "test_bg.png"
    ) : Level() {
        override val obstacleTypes: List<() -> Obstacle> = emptyList()
        override val collectibleTypes: List<() -> Collectible> = emptyList()
        override val bgObjectTypes: List<() -> BackgroundObject> = emptyList()
    }

    @BeforeEach
    fun setup() {
        levelManager = LevelManager()
    }

    @Test
    fun `registerLevel should add level and set as current if first`() {
        val level = TestLevel("Test1")
        levelManager.registerLevel(level)

        assertEquals(level, levelManager.getCurrentLevel())
        assertEquals(listOf(level), levelManager.getAvailableLevels())
    }

    @Test
    fun `registerLevel should overwrite if name exists`() {
        val original = TestLevel("Duplicate")
        val updated = TestLevel("Duplicate")

        levelManager.registerLevel(original)
        levelManager.registerLevel(updated)

        assertEquals(1, levelManager.getAvailableLevels().size)
        assertEquals(updated, levelManager.getCurrentLevel())
    }

    @Test
    fun `switchTo should change current level if name exists`() {
        val level1 = TestLevel("Forest")
        val level2 = TestLevel("Cave")

        levelManager.registerLevel(level1)
        levelManager.registerLevel(level2)

        val result = levelManager.switchTo("Cave")

        assertTrue(result)
        assertEquals(level2, levelManager.getCurrentLevel())
    }

    @Test
    fun `switchTo should return false if level name does not exist`() {
        val level = TestLevel("RealLevel")
        levelManager.registerLevel(level)

        val result = levelManager.switchTo("FakeLevel")

        assertFalse(result)
        assertEquals(level, levelManager.getCurrentLevel()) // should still be on original
    }

    @Test
    fun `getCurrentLevel should throw if no level registered`() {
        val ex = assertFailsWith<IllegalStateException> {
            levelManager.getCurrentLevel()
        }
        assertTrue(ex.message!!.contains("No level has been set or found"))
    }

    @Test
    fun `clear should remove all levels and unset current`() {
        levelManager.registerLevel(TestLevel("WipeMe"))
        levelManager.clear()

        assertEquals(emptyList(), levelManager.getAvailableLevels())
        assertFailsWith<IllegalStateException> {
            levelManager.getCurrentLevel()
        }
    }
}
