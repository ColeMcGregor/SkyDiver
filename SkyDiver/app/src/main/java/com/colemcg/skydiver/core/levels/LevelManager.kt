package com.colemcg.skydiver.core.levels

// NOTE: we do not need to import level here because we will be using the Level class the package we are inside of.

/**
 * Manages level registration and switching.
 *
 * Holds a map of all available levels keyed by name and tracks the currently active level.
 * Designed for runtime querying by systems like Spawner, GameManager, and ScoreManager.
 *
 * @author Cole McGregor
 */
class LevelManager {

    private val levels: MutableMap<String, Level> = mutableMapOf()
    private var currentLevel: Level? = null

    /**
     * Registers a level with its name as the key.
     *
     * If the level's name already exists, it will be overwritten.
     */
    fun registerLevel(level: Level) {
        levels[level.name] = level
        if (currentLevel == null) {
            currentLevel = level // Set the first registered level as the default
        }
    }

    /**
     * Attempts to switch to a level by name.
     *
     * @return true if successful, false if no level with that name exists
     */
    fun switchTo(levelName: String): Boolean {
        val newLevel = levels[levelName] ?: return false //this is a fancy way of saying if the level is not found, return false
        currentLevel = newLevel
        return true
    }

    /**
     * Returns a list of all registered levels, of type Level.
     */
    fun getAvailableLevels(): List<Level> = levels.values.toList()

    /**
     * Gets the currently active level.
     *
     * @throws IllegalStateException if no level is currently active
     */
    fun getCurrentLevel(): Level {
        return currentLevel
            ?: throw IllegalStateException("No level has been set or found. You must register at least one level.")
    }

    /**
     * Clears all registered levels and resets the current level.
     * Useful for reinitialization in test or reset scenarios.
     */
    fun clear() {
        levels.clear()
        currentLevel = null
    }
}
