package com.colemcg.skydiver.core.systems
/**
 * Tracks the player's score and combo streak during gameplay.
 *
 * applies point changes based on collision actions (e.g., collecting items, hitting obstacles),
 * and applies combo multipliers for consecutive successes.
 *
 * Responsibilities:
 * - Maintain current score and streak.
 * - Apply scoring logic and multipliers.
 * - Expose public getters for score-related overlays.
 *
 * Does not persist data across sessions; see StatsManager for history.
 *
 * @author 
 */

 //a constant for how much the multiplier increases by each streak
const val STREAK_INCREASE_RATE = 0.25f

class ScoreManager(
    private val baseMultiplier: Float = 1.0f, // the base multiplier, which is the multiplier when the streak is 0
    private val maxMultiplier: Float = 100.0f, // the maximum multiplier, which is the multiplier when the streak is at its highest
    private val streakThreshold: Int = 5 // Increase multiplier every X amount of collections
) {
    private var score: Int = 0 // the current score of the player
    private var currentStreak: Int = 0 // the current streak of the player
    private var maxStreak: Int = 0 // the highest streak the player has had this round, not historically
    private var comboMultiplier: Float = baseMultiplier // the current combo multiplier

    /**
     * Adds points to the score, applying the current combo multiplier.
     * Call this when the player collects a score-bearing object.
     */
    fun addPoints(basePoints: Int) {
        val adjusted = (basePoints * comboMultiplier).toInt()
        score += adjusted
    }

    /**
     * Increments the current streak. May increase the combo multiplier.
     */
    fun incrementStreak() {
        currentStreak++
        if (currentStreak > maxStreak) {
            maxStreak = currentStreak
        }
        //if the streak is a multiple of the streak threshold, increase the combo multiplier by the streak increase rate
        if (currentStreak % streakThreshold == 0) {
            comboMultiplier = (comboMultiplier + STREAK_INCREASE_RATE).coerceAtMost(maxMultiplier)
        }
    }

     /**
     * Applies a bonus multiplier to the current combo multiplier.
     *
     * This stacks on top of the existing multiplier temporarily (e.g., from a collectible).
     *
     * @param multiplierBoost The amount to add to the current multiplier.
     */
    fun applyMultiplierBoost(multiplierBoost: Float) {
        comboMultiplier = (comboMultiplier + multiplierBoost).coerceAtMost(maxMultiplier)
    }


    /**
     * Resets the current streak and combo multiplier.
     * Call this when the player hits an obstacle or breaks the streak.
     */
    fun resetStreak() {
        currentStreak = 0
        comboMultiplier = baseMultiplier
    }

    //reset the score and streak
    fun resetScore() {
        score = 0
        resetStreak()
    }

    //getters
    //get the current score
    fun getScore(): Int = score
    //get the highest streak the player has had this round, not historically
    fun getMaxStreak(): Int = maxStreak
    //get the current combo multiplier
    fun getComboMultiplier(): Float = comboMultiplier
}
