package com.colemcg.skydiver.core.events

import com.colemcg.skydiver.core.geometry.Vector2

/**
 * Represents a user input event, abstracted from platform-specific details.
 * Meant to be used for input events from the player, such as taps, drags, and holds.
 * will be used so that we can implement input events for both iOS and Android in the future.
 *
 * @property type The type of input (Tap, Drag, Hold)
 * @property position The location of the input event in screen or world space
 * @property timestamp The time the input occurred, in milliseconds
 * 
 * @author Cole McGregor
 */
data class InputEvent(
    val type: InputType,
    val position: Vector2,
    val timestamp: Long
)
