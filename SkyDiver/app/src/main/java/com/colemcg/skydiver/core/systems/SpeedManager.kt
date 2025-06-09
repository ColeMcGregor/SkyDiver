package com.colemcg.skydiver.core.systems

/**
 * Controls global game speed and pacing rules.
 *
 * Handles the dynamic acceleration of game elements over time,
 * ensuring gameplay becomes more intense without breaking the frame rate.
 *
 * Responsibilities:
 * - Track current speed, min/max velocity bounds.
 * - Apply speed increases over time.
 * - Handle speed penalties (e.g., hitting obstacles).
 *
 * Should be called once per frame to adjust simulation pacing.
 *
 * @author Cole McGregor
 */

class SpeedManager(
    // TODO: update values to more accurate values after testing
    private val initialSpeed: Float = 1.0f,              // the starting speed of the game
    private val minSpeed: Float = 0.5f,                  // the starting minimum speed of the game
    private val maxSpeed: Float = 3.5f,                  // the maximum speed of the game
    private val speedIncreaseRate: Float = 0.1f,         // the rate at which the game speed increases
    private val minSpeedIncreaseRate: Float = 0.05f      // the rate at which the minimum speed increases
) {
    private var gameSpeed: Float = initialSpeed          // the current speed of the game, as it stands
    private var currentMinSpeed: Float = minSpeed        // the current minimum speed of the game, to be set to rise

    /**
     * Updates the current speed based on time elapsed.
     * Should be called once per logic update.
     *
     * @param deltaTime Time in seconds since last update.
     */
    fun update(deltaTime: Float) {
        // Increase min speed gradually
        currentMinSpeed = (currentMinSpeed + (minSpeedIncreaseRate * deltaTime))
            .coerceAtMost(maxSpeed)

        // Increase game speed toward max
        gameSpeed += speedIncreaseRate * deltaTime
        gameSpeed = gameSpeed.coerceIn(currentMinSpeed, maxSpeed)
    }

    /**
     * Applies a slowdown penalty to the current game speed.
     *
     * @param amount The amount to reduce from game speed.
     */
    fun applySlowdown(amount: Float) {
        gameSpeed = (gameSpeed - amount).coerceAtLeast(currentMinSpeed)
    }

    /**
     * Applies a manual speed boost to the current game speed.
     *
     * @param amount The amount to increase the game speed by.
     */
    fun applySpeedup(amount: Float) {
        gameSpeed = (gameSpeed + amount).coerceAtMost(maxSpeed)
    }

    /**
     * Returns the current game speed.
     */
    fun getGameSpeed(): Float = gameSpeed

    /**
     * Resets the speed manager to its initial state.
     */
    fun reset() {
        gameSpeed = initialSpeed
        currentMinSpeed = minSpeed
    }
}
