package com.colemcg.skydiver.core.levels

import com.colemcg.skydiver.core.entities.GameObject
import com.colemcg.skydiver.core.entities.Obstacle
import com.colemcg.skydiver.core.entities.Collectible
import com.colemcg.skydiver.core.entities.BackgroundObject

/**
 * Abstract class for all levels in the game.
 * 
 * @author Cole McGregor
 */

abstract class Level {

    abstract val name: String                    // e.g., "Volcano Tube"
    abstract val backgroundImage: String         // PNG or resource ID
    open val initialOffset: Float = 0f           // Optional vertical scroll start
    abstract val obstacleTypes: List<() -> Obstacle> // List of functions that return new instances of Obstacle
    abstract val collectibleTypes: List<() -> Collectible> // List of functions that return new instances of Collectible
    abstract val bgObjectTypes: List<() -> BackgroundObject> // List of functions that return new instances of BackgroundObject

}
