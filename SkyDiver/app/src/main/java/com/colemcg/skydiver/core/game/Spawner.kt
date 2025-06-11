package com.colemcg.skydiver.core.game

import com.colemcg.skydiver.core.levels.*
import com.colemcg.skydiver.core.entities.GameObject
import com.colemcg.skydiver.core.entities.Obstacle
import com.colemcg.skydiver.core.entities.Collectible
import com.colemcg.skydiver.core.geometry.Vector2
import kotlin.random.Random

//a constant for the default spawn interval
const val MINIMUM_SPAWN_INTERVAL = 0.25f

/**
 * Handles timed spawning of obstacles and collectibles
 * based on the currently active Level configuration.
 * 
 * will have the spawn interval modified by the DifficultyManager
 * 
 * @author Cole McGregor
 */
class Spawner(private val levelManager: LevelManager) {

    private var spawnTimer = 0f
    private var spawnInterval = 1.5f // Default, modifiable later
    private var level = levelManager.getCurrentLevel() //may need to be initialized in init



    /**
     * Updates the spawn timer and adds new objects to the provided list
     * if it's time to spawn something.
     */
    fun update(deltaTime: Float, objects: MutableList<GameObject>) {
        spawnTimer += deltaTime

        if (spawnTimer >= spawnInterval) {
            spawnTimer = 0f

            // Decide randomly whether to spawn an obstacle or collectible
            if (Random.nextBoolean()) {
                val obstacle = spawnObstacle(level.obstacleTypes)
                if (obstacle != null) objects.add(obstacle)
            } else {
                val collectible = spawnCollectible(level.collectibleTypes)
                if (collectible != null) objects.add(collectible)
            }
        }
    }

    /**
     * Resets the spawn timer to 0.
     */
    fun reset() {
        spawnTimer = 0f
    }

    /**
     * Spawns an obstacle at a random position.
     */
    private fun spawnObstacle(factories: List<() -> Obstacle>): Obstacle? {
        val factory = factories.randomOrNull() ?: return null
        return factory().apply {
            position = randomSpawnPosition()
        }
    }

    /**
     * Spawns a collectible at a random position.
     */
    private fun spawnCollectible(factories: List<() -> Collectible>): Collectible? {
        val factory = factories.randomOrNull() ?: return null
        return factory().apply {
            position = randomSpawnPosition()
        }
    }

    /**
     * Sets the spawn interval. used by the DifficultyManager to adjust rate of spawning
     */
    fun setSpawnInterval(newInterval: Float) {
        spawnInterval = newInterval.coerceAtLeast(MINIMUM_SPAWN_INTERVAL)
    }

    /**
     * Gets the spawn interval.
     */
    fun getSpawnInterval(): Float = spawnInterval

    /**
     * Generates a random spawn position. bound within the screen width
     */
    private fun randomSpawnPosition(): Vector2 {
        val x = Random.nextFloat() * 720f   // needs to changed to get the screen width
        val y = -100f                       // Spawns just above the screen
        return Vector2(x, y)
        // TODO: bind spawn to breadth of x value depending on typical subset or 
    }
}
