package com.colemcg.skydiver.platform.android.input

import android.view.MotionEvent
import com.colemcg.skydiver.core.events.InputEvent
import com.colemcg.skydiver.core.events.InputType
import com.colemcg.skydiver.core.geometry.Vector2

/**
 * Converts Android MotionEvents to core InputEvents.
 * Used by MainActivity to abstract input handling.
 *
 * @author Jardina Gomez
 */
class MotionEventAdapter{
    /**
     * Adapts a MotionEvent from Android into a game-specific InputEvent.
     *
     * @param event The Android touch event
     * @return A new InputEvent object used by the game
     */
    fun adapt(event: MotionEvent): InputEvent {
        // Convert touch coordinates (x, y) into a Vector2 (used in game geometry)
        val position = Vector2(event.x, event.y)

        // Map Android motion types to your InputType enum
        val type = when (event.action) {
            MotionEvent.ACTION_DOWN -> InputType.Tap         // Finger touches the screen
            MotionEvent.ACTION_MOVE -> InputType.Drag        // Finger moves while touching
            MotionEvent.ACTION_UP   -> InputType.Hold        // Finger lifts up
            else -> InputType.Tap                             // Fallback
        }

        // Build and return a new InputEvent for the game to use
        return InputEvent(
            type = type,                       // Tap, Drag, or Hold
            position = position,               // Where on the screen it happened
            timestamp = System.currentTimeMillis(), // When it happened
            direction = Vector2(0f, 0f)        //  can calculate this later for swipes
        )
    }

}