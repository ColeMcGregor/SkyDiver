/**
 * Abstract interface for persistent key-value storage access.
 *
 * Provides a simple contract for storing and retrieving
 * basic values (e.g., Ints) across game sessions.
 * Intended to abstract platform-specific persistence APIs like
 * Android SharedPreferences or iOS UserDefaults.
 *
 * Responsibilities:
 * - Save and retrieve primitive values by key.
 * - Clear all stored data when requested.
 * - Serve as a backend for systems like StatsManager or OptionsManager.
 *
 * This interface should be implemented per platform and injected
 * where needed to support long-term persistence without tight coupling.
 *
 * Example keys: "highScore", "gamesPlayed", "musicVolume"
 *
 * @author Cole McGregor
 */
package com.colemcg.skydiver.core.systems

/**
 * Abstract interface for persistent key-value storage access.
 *
 * Provides a simple contract for storing and retrieving
 * basic values (e.g., Ints) across game sessions.
 * Intended to abstract platform-specific persistence APIs like
 * Android SharedPreferences or iOS UserDefaults.
 *
 * Responsibilities:
 * - Save and retrieve primitive values by key.
 * - Clear all stored data when requested.
 * - Serve as a backend for systems like StatsManager or OptionsManager.
 *
 * This interface should be implemented per platform and injected
 * where needed to support long-term persistence without tight coupling.
 *
 * Example keys: "highScore", "gamesPlayed", "musicVolume"
 *
 * @author Cole McGregor
 */
interface KeyValueStorage {

    /**
     * Stores an integer value associated with the given key.
     *
     * @param key the identifier string
     * @param value the integer value to store
     */
    fun putInt(key: String, value: Int)

    /**
     * Retrieves an integer value associated with the key.
     *
     * @param key the identifier string
     * @param default the value to return if key not found
     * @return the stored value or default if not present
     */
    fun getInt(key: String, default: Int): Int

    //not as of yet needed and therefore not implemented methods:
    
    // fun putString(key: String, value: String)
    // fun getString(key: String, default: String): String

    // fun putBoolean(key: String, value: Boolean)
    // fun getBoolean(key: String, default: Boolean): Boolean

    // fun putFloat(key: String, value: Float)
    // fun getFloat(key: String, default: Float): Float

    // fun putLong(key: String, value: Long)
    // fun getLong(key: String, default: Long): Long

    // fun putDouble(key: String, value: Double)
    // fun getDouble(key: String, default: Double): Double

    // fun putByteArray(key: String, value: ByteArray)
    // fun getByteArray(key: String, default: ByteArray): ByteArray

    /**
     * Clears all saved key-value data.
     */
    fun clear()
}
