package com.colemcg.skydiver.core.systems

/**
 * Tracks long-term player performance metrics.
 *
 * Records session statistics like high score, total games played,
 * and average score over time. Works alongside a persistent
 * storage mechanism (KeyValueStorage --> platform-specific storage) to preserve data.
 *
 * Responsibilities:
 * - Update stats at end of session. (read, update, write storage values at end of session)
 * - Provide data for stats screen overlays (getters)
 * - Reset or retrieve aggregate performance data. (read, update, write storage values in general)
 *
 * This is persistent across sessions unless reset explicitly.
 *
 * @author Cole McGregor
 */

class StatsManager(private val storage: KeyValueStorage) {
    /**
     * A companion object is a static class that can be used to store constants
     * that are shared across all instances of the class. it is like a static class in other languages.
     */
    companion object {
        // these are the keys for the storage values
        // they are private because they are only used within the class
        // they are constants because they are not meant to be changed
        private const val KEY_GAMES_PLAYED = "gamesPlayed"                          // the number of games played by this user for all time
        private const val KEY_HIGHEST_SCORE = "highestScore"                        // the highest score from any run that has been played
        private const val KEY_TOTAL_SCORE = "totalScore"                            // the total sum of all scores from all runs, used for average score calculation
        private const val KEY_LAST_SCORE = "lastScore"                              // the score of the last run
        private const val KEY_OBSTACLES_HIT = "obstaclesHit"                            // the number of times the player has hit an obstacle
        private const val KEY_COINS_COLLECTED = "coinsCollected"                        // the number of coins collected by the player
        private const val KEY_MULTIPLIERS_COLLECTED = "multipliersCollected"            // the number of multipliers collected by the player
    }

    /**
     * Records a run and updates the stats.
     * This is called at the end of a run.
     *
     * The score is added to the total score.
     * The number of games played is incremented.
     * The highest score is updated if the current score is higher.
     * The last score is updated to the current score.
     * The number of coins collected is updated.
     * The number of multipliers collected is updated.
     * The number of obstacles hit is updated.
     * 
     * then they are written to storage.
     *
     * @param score the score of the run as an integer
     */
    fun recordRun(score: Int, coins: Int, multipliers: Int, obstacles: Int) {
        val gamesPlayed = storage.getInt(KEY_GAMES_PLAYED, 0) + 1
        val totalScore = storage.getInt(KEY_TOTAL_SCORE, 0) + score
        val highestScore = storage.getInt(KEY_HIGHEST_SCORE, 0).coerceAtLeast(score)
        val coinsCollected = storage.getInt(KEY_COINS_COLLECTED, 0) + coins
        val multipliersCollected = storage.getInt(KEY_MULTIPLIERS_COLLECTED, 0) + multipliers
        val obstaclesHit = storage.getInt(KEY_OBSTACLES_HIT, 0) + obstacles
        
        storage.putInt(KEY_COINS_COLLECTED, coinsCollected)
        storage.putInt(KEY_MULTIPLIERS_COLLECTED, multipliersCollected)
        storage.putInt(KEY_OBSTACLES_HIT, obstaclesHit)
        storage.putInt(KEY_GAMES_PLAYED, gamesPlayed)
        storage.putInt(KEY_TOTAL_SCORE, totalScore)
        storage.putInt(KEY_HIGHEST_SCORE, highestScore)
        storage.putInt(KEY_LAST_SCORE, score)
    }

    /**
     * Gets the number of games played by this user for all time.
     *
     * @return the number of games played
     */
    fun getGamesPlayed(): Int = storage.getInt(KEY_GAMES_PLAYED, 0)

    /**
     * Gets the highest score from any run that has been played.
     *
     * @return the highest score
     */
    fun getHighestScore(): Int = storage.getInt(KEY_HIGHEST_SCORE, 0)

    /**
     * Gets the total sum of all scores from all runs, used for average score calculation
     *
     * @return the total score
     */
    fun getTotalScore(): Int = storage.getInt(KEY_TOTAL_SCORE, 0)

    /**
     * Gets the score of the last run
     *
     * @return the last score
     */
    fun getLastScore(): Int = storage.getInt(KEY_LAST_SCORE, 0)

    /**
     * Gets the number of obstacles hit by the player
     *
     * @return the number of obstacles hit
     */
    fun getObstaclesHit(): Int = storage.getInt(KEY_OBSTACLES_HIT, 0)
    
    /**
     * Gets the number of coins collected by the player
     *
     * @return the number of coins collected
     */
    fun getCoinsCollected(): Int = storage.getInt(KEY_COINS_COLLECTED, 0)
    
    /**
     * Gets the number of multipliers collected by the player
     *
     * @return the number of multipliers collected
     */
    fun getMultipliersCollected(): Int = storage.getInt(KEY_MULTIPLIERS_COLLECTED, 0)

    /**
     * Gets the average score by dividing the total score by the number of games played.
     * if there are no games played, the average score is 0.
     *
     * @return the average score
     */
    fun getAverageScore(): Float {
        val games = getGamesPlayed()
        return if (games > 0) getTotalScore().toFloat() / games else 0f
    }

    /**
     * Resets the stats to 0.
     */
    fun resetStats() {
        storage.putInt(KEY_GAMES_PLAYED, 0)
        storage.putInt(KEY_TOTAL_SCORE, 0)
        storage.putInt(KEY_HIGHEST_SCORE, 0)
        storage.putInt(KEY_LAST_SCORE, 0)
        storage.putInt(KEY_OBSTACLES_HIT, 0)
        storage.putInt(KEY_COINS_COLLECTED, 0)
        storage.putInt(KEY_MULTIPLIERS_COLLECTED, 0)
    }
}
