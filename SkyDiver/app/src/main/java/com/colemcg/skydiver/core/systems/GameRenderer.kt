package com.colemcg.skydiver.core.systems

import com.colemcg.skydiver.core.geometry.Vector2
import com.colemcg.skydiver.core.entities.GameObject

/**
 * Abstract class for all game renderers (e.g., Android, iOS).
 * Subclasses implement platform-specific drawing logic.
 * 
 * @author Cole McGregor
 */
abstract class GameRenderer {


    // Draw any sprite (e.g., kite, hang glider, player,etc.)
    abstract fun drawGameObject(gameObject: GameObject, position: Vector2)

    // Draw the static background layer (e.g., PNG image or gradient)
    //this receives the player's position to draw the background layer at the correct position
    //needs to be used to center the background layer on the screen, as the background layer is a static image
    abstract fun drawBackgroundLayer(position: Vector2)

    // Draw a UI element (e.g., score label or icon) at a specific position
    abstract fun drawUIElement(name: String, position: Vector2)

    // Draw title, which just loads the title png from the assets
    abstract fun drawTitle()

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
