package com.colemcg.skydiver.core.systems

import com.colemcg.skydiver.core.geometry.Vector2

/**
 * Abstract class for all game renderers (e.g., Android, iOS).
 * Subclasses implement platform-specific drawing logic.
 * 
 * @author Cole McGregor
 */
abstract class GameRenderer {

    // Draw the player sprite at the given position
    abstract fun drawPlayer(position: Vector2)

    // Draw an obstacle sprite (e.g., kite, hang glider)
    abstract fun drawObstacle(position: Vector2)

    // Draw a collectible sprite (e.g., coin, token)
    abstract fun drawCollectible(position: Vector2)

    // Draw non-interactive background objects (e.g., clouds, birds)
    abstract fun drawBackgroundObjects(position: Vector2)

    // Draw the static background layer (e.g., PNG image or gradient)
    abstract fun drawBackgroundLayer(position: Vector2)

    // Draw a UI element (e.g., score label or icon) at a specific position
    abstract fun drawUIElement(name: String, position: Vector2)

    // Draw the title text (e.g., level name or game name)
    abstract fun drawTitle(title: String)

    // Draw a message overlay (e.g., "Paused", "Game Over")
    abstract fun drawMessageOverlay(message: String)

    // Draw a visual particle effect (e.g., sparkle, impact) at a position
    abstract fun drawParticleEffect(position: Vector2, type: String)

    // Draw full-screen overlays (e.g., pause menu, options menu)
    abstract fun drawOverlay(name: String)

    // Optional: Clear the screen before rendering a new frame
    open fun clearScreen() {}

    // Optional: Flush and present the current frame
    open fun flush() {}
}
