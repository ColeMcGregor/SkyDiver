package com.colemcg.skydiver.core.ui.overlays

import com.colemcg.skydiver.core.ui.UIOverlay
import com.colemcg.skydiver.core.systems.GameRenderer
import com.colemcg.skydiver.core.events.InputEvent
import com.colemcg.skydiver.core.events.InputType
import com.colemcg.skydiver.core.geometry.Rect
import com.colemcg.skydiver.core.geometry.Vector2
import com.colemcg.skydiver.core.game.SCREEN_WIDTH
import com.colemcg.skydiver.core.game.GameState
import com.colemcg.skydiver.core.systems.SoundManager
import com.colemcg.skydiver.core.systems.KeyValueStorage

/**
 * Overlay for modifying game settings and preferences.
 *
 * Used to change settings like Sound, Haptics, or Accessibility.
 * Also used to change the difficulty of the game.
 *
 * Should allow safe exit back to StartOverlay or PauseOverlay.
 *
 * @author Jardina Gomez
 */

class OptionsOverlay (
    private val soundManager: SoundManager,     // Used to toggle sound settings
    private val storage: KeyValueStorage,       // Used to clear saved preferences/stats
    private val onBack:() -> Unit          // Callbacks so UI is controlled by whoever shows this overlay(UIManager)
) : UIOverlay {

    // --- Button Layout Constants ---
    val buttonWidth = 200f // Width of each button
    val buttonHeight = 80f // Height of each button
    val x = (SCREEN_WIDTH - buttonWidth) / 2f // centers the buttons

    val yTitle = 200f // position of the title text
    val yStart = 350f // top of the first button, 350 pixels from the top of the screen
    val spacing = 100f // space between buttons

    // --- Layout ---
    // [toggle Sound]
    // |SFX -| |SFX +|
    // |Music -| |Music +|
    // [toggle Haptics]
    // |Reset Stats|
    // |Back|

    // split horizontally for buttons that go side by side(SFX + Music)
    private val halfGap = 20f // gap between two buttons on the same row
    private val halfButtonWidth = (buttonWidth - halfGap) / 2f // width of each button when there are two on the same row

    // Helpers for single button rows
    private fun singleRow(y: Float) = Vector2(x, y) // position for single button rows
   // Helpers for double button rows
    private fun leftPos(y: Float) = Vector2(x, y) // position for left button in a pair
    private fun rightPos(y: Float) = Vector2(x + halfButtonWidth + halfGap, y) // position for right button in a pair

    // --- Button positions ---
    private val soundButtonPos = singleRow(yStart + 0 * spacing) // Position of the Sound button
    private val sfxMinusPos = leftPos(yStart + 1 * spacing) // Position of the SFX - button
    private val sfxPlusPos = rightPos(yStart + 1 * spacing) // Position of the SFX + button
    private val musicMinusPos = leftPos(yStart + 2 * spacing) // Position of the Music - button
    private val musicPlusPos = rightPos(yStart + 2 * spacing) // Position of the Music + button
    private val resetStatsPos = singleRow(yStart + 3 * spacing) // Position of the Reset Stats button
    private val backPos = singleRow(yStart + 4 * spacing) // Position of the Back button

    // --- Button hitboxes ---
    private val soundButtonRect = Rect(soundButtonPos.x, soundButtonPos.y, buttonWidth, buttonHeight)
    private val sfxMinusRect = Rect(sfxMinusPos.x, sfxMinusPos.y, halfButtonWidth, buttonHeight)
    private val sfxPlusRect = Rect(sfxPlusPos.x, sfxPlusPos.y, halfButtonWidth, buttonHeight)
    private val musicMinusRect = Rect(musicMinusPos.x, musicMinusPos.y, halfButtonWidth, buttonHeight)
    private val musicPlusRect = Rect(musicPlusPos.x, musicPlusPos.y, halfButtonWidth, buttonHeight)
    private val resetStatsButtonRect = Rect(resetStatsPos.x, resetStatsPos.y, buttonWidth, buttonHeight)
    private val backRect = Rect(backPos.x, backPos.y, buttonWidth, buttonHeight)


    // Keeps track of whether the overlay is currently visible
    private var isVisible = false

    private val VOL_STEP = 0.1f // amount to change volume by when pressing + or -

    /**
     * Called when the screen becomes visible.
     * Initialize animations or reset internal state here.
     */
    override fun show() {
        // mark overlay as visible
        isVisible = true
        // pause game
        GameState.pauseGame()
    }

    /**
     * Called when the screen is hidden.
     * Cleanup or pause activities here if necessary.
     */
    override fun hide() {
        // mark overlay as not visible
        isVisible = false
        // not calling resume, caller decides
    }

    /**
     * Called when an input event (e.g., tap, drag) is dispatched to this screen.
     * Use this to handle button presses, menu navigation, etc.
     *
     * @param event the input event to respond to
     */
    override fun handleInput(event: InputEvent) {
       // ignore if hidden or not a tap
        if (!isVisible || event.type != InputType.Tap) return
        val tap = event.position // get the position of the tap

        when {
            // Toggle mute
            soundButtonRect.contains(tap) -> {
                soundManager.toggleSound()
            }
            // SFX volume
            sfxMinusRect.contains(tap) -> {
                val newLevel = (soundManager.getSFXVolume() - VOL_STEP).coerceIn(0f, 1f) // setting volume between 0 and 1
                soundManager.setSFXVolume(newLevel) // set new volume
            }
            sfxPlusRect.contains(tap) -> {
                val newLevel = (soundManager.getSFXVolume() + VOL_STEP).coerceIn(0f, 1f) // setting volume between 0 and 1
                soundManager.setSFXVolume(newLevel) // set new volume
            }
            // Music volume
            musicMinusRect.contains(tap) -> {
                val newLevel = (soundManager.getMusicVolume() - VOL_STEP).coerceIn(0f, 1f) // setting volume between 0 and 1
                soundManager.setMusicVolume(newLevel) // set new volume
            }
            musicPlusRect.contains(tap) -> {
                val newLevel = (soundManager.getMusicVolume() + VOL_STEP).coerceIn(0f, 1f) // setting volume between 0 and 1
                soundManager.setMusicVolume(newLevel) // set new volume
            }
            // Clear saved stats and preferences
            resetStatsButtonRect.contains(tap) -> {
                storage.clear() // clear all saved data
            }

            // Back out to previous overlay
            backRect.contains(tap) -> {
                onBack() // run the back callback
                hide() // hide this overlay
            }
        }
    }

    /**
     * Updates the screen state based on the time passed since the last update.
     * This is useful for animations or time-based logic.
     *
     * @param deltaTime the time in seconds since the last update
     */
    override fun update(deltaTime: Float) {
        // No animation or state change logic needed for now
    }

    /**
     * Draws the overlay to the screen.
     * This method should handle all rendering logic for the overlay.
     *
     * @param renderer the renderer used to draw the overlay
     */
    override fun draw(renderer: GameRenderer) {
        if(isVisible) return

        renderer.drawMessageOverlay("Options")
        // Some helpful status labels to show current settings
        val muteStatus = if (soundManager.isMuted()) "Muted" else "Sound On"
        val sfxVol = (soundManager.sfxVolume * 100).toInt() // convert to percentage
        val musicVol = (soundManager.musicVolume * 100).toInt() // convert to percentage

        // Labels (Not buttons) to explain current values
        renderer.drawUIElement("Audio: $muteStatus", Vector2(x, yTitle))

        //Buttons
        renderer.drawUIElement("Toggle Sound", soundButtonPos)
        renderer.drawUIElement("SFX - ($sfxVol%)", sfxMinusPos)
        renderer.drawUIElement("SFX + ($sfxVol%)", sfxPlusPos)
        renderer.drawUIElement("Music - ($musicVol%)", musicMinusPos)
        renderer.drawUIElement("Music + ($musicVol%)", musicPlusPos)
        renderer.drawUIElement("Reset Stats", resetStatsPos)
        renderer.drawUIElement("Back", backPos)

    }

}