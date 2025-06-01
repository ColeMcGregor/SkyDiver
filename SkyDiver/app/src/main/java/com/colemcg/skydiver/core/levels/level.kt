package com.colemcg.skydiver.core.levels

import com.colemcg.skydiver.core.entities.GameObject
import com.colemcg.skydiver.core.entities.Obstacle
import com.colemcg.skydiver.core.entities.Collectible

/**
 * Abstract class for all levels in the game.
 * 
 * @author Cole McGregor
 */

abstract class Level {

    abstract val name: String                    // e.g., "Volcano Tube"
    abstract val backgroundImage: String         // PNG or resource ID
    open val initialOffset: Float = 0f           // Optional vertical scroll start

    /**
     * Called once to populate background visuals (e.g., clouds, volcano jets)
     */
    abstract fun createBackgroundObjects(): List<GameObject>

    /**
     * Called when difficulty manager decides it's time to spawn an obstacle
     */
    abstract fun generateObstacle(): Obstacle?

    /**
     * Called when a collectible should appear
     */
    abstract fun generateCollectible(): Collectible?
}
