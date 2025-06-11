package com.colemcg.skydiver.core.systems

import com.colemcg.skydiver.core.game.Spawner

/**
 * Dynamically adjusts spawn difficulty based on real-time player performance.
 *
 * - Increases difficulty by decreasing spawn interval when the player performs well.
 * - Decreases difficulty (eases game) when the player struggles (e.g., hits obstacles).
 *
 * Should be updated regularly (e.g., every frame or few frames) and reset on session start.
 *
 * @see ScoreManager for performance metrics
 * @see Spawner for applying difficulty
 *
 * @author Cole McGregor
 */
class DifficultyManager(
    private val spawner: Spawner,                   // used to adjust the spawn interval
    private val scoreManager: ScoreManager,         // used to determine the performance score
    private val baseSpawnInterval: Float = 1.5f,    // Initial/default interval
    private val penaltyWeight: Float = 1.5f,        // How harshly obstacle hits are penalized (larger than 1 is more severe than collectibles help)
    private val sensitivity: Float = 0.05f          // How strongly performance affects difficulty
) {

    private var lastScoreSnapshot: Float = 0f      //used to determine the difference between the current performance and the last performance

    /**
     * Resets difficulty back to default. Call this on session start or when the player dies
     */
    fun reset() {
        lastScoreSnapshot = 0f
        spawner.setSpawnInterval(baseSpawnInterval)
    }

    /**
     * Updates the spawn interval based on current performance metrics.
     * Called regularly in the game loop to adjust the spawn interval
     */
    fun update() {
        val performance = computePerformanceScore()                 //our algorithm for determining the performance score
        val delta = performance - lastScoreSnapshot                 //the difference between the current performance and the last performance
        lastScoreSnapshot = performance

        val difficultyFactor = 1f - (delta * sensitivity) //the difficulty factor is positive for improvements, negative for regressions
        val adjustedInterval = baseSpawnInterval * difficultyFactor //the adjusted interval is the base spawn interval multiplied by the difficulty factor

        spawner.setSpawnInterval(adjustedInterval) //update the spawn interval
    }

    /**
     * ALGORITHM FUNCTION
     * Calculates the player's performance score from ScoreManager data.
     * Good actions increase score, bad actions (like hits) decrease it.
     */
    private fun computePerformanceScore(): Float {
        //get the performance scores from the ScoreManager
        val coins = scoreManager.getCoinsCollected()
        val multipliers = scoreManager.getMultipliersCollected()
        val hits = scoreManager.getObstaclesHit()

        // HERE is the algorithm for determining the performance score
        return (coins + multipliers) - (hits * penaltyWeight)
    }

    //DEBUGGING
    /**
     * Logs the current spawn interval for debugging or tuning.
     */
    fun logDifficulty() {
        println("Spawn Interval: ${spawner.getSpawnInterval()}")
    }
}
