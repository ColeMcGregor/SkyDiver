package com.colemcg.skydiver.core.game

import com.colemcg.skydiver.core.levels.LevelManager
import com.colemcg.skydiver.core.entities.GameObject
import com.colemcg.skydiver.core.entities.Obstacle
import com.colemcg.skydiver.core.entities.Collectible
import com.colemcg.skydiver.core.geometry.Vector2
import kotlin.random.Random

/**
 * Handles timed spawning of obstacles and collectibles
 * based on the currently active Level configuration.
 */
class Spawner(private val levelManager: LevelManager) {

    private var spawnTimer = 0f
    private var spawnInterval = 1.5f // Default, modifiable later

    /**
     * Updates the spawn timer and adds new objects to the provided list
     * if it's time to spawn something.
     */
    fun update(deltaTime: Float, objects: MutableList<GameObject>) {
        spawnTimer += deltaTime

        if (spawnTimer >= spawnInterval) {
            spawnTimer = 0f

            val level = levelManager.getCurrentLevel()

            // Decide randomly whether to spawn an obstacle or collectible
            if (Random.nextBoolean()) {
                val obstacle = spawnObstacle(level.obstacleTypes)
                if (obstacle != null) objects.add(obstacle)
            } else {
                val collectible = spawnCollectible(level.collectibleTypes)
                if (collectible != null) objects.add(collectible)
            }

            // TODO: Adjust spawnInterval using DifficultyManager
        }
    }

    /**
     * Resets the spawn timer to 0.
     */
    fun reset() {
        spawnTimer = 0f
    }

    private fun spawnObstacle(factories: List<() -> Obstacle>): Obstacle? {
        val factory = factories.randomOrNull() ?: return null
        return factory().apply {
            position = randomSpawnPosition()
        }
    }

    private fun spawnCollectible(factories: List<() -> Collectible>): Collectible? {
        val factory = factories.randomOrNull() ?: return null
        return factory().apply {
            position = randomSpawnPosition()
        }
    }

    private fun randomSpawnPosition(): Vector2 {
        val x = Random.nextFloat() * 720f   // needs to changed to get the screen width
        val y = -100f                       // Spawns just above the screen
        return Vector2(x, y)
        // TODO: bind spawn to breadth of x value depending on typical subset or 
    }
}
