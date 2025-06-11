package com.colemcg.skydiver.core.ui

import org.junit.Assert.*
import org.junit.Test
import com.colemcg.skydiver.core.ui.overlays.StartOverlay
import com.colemcg.skydiver.core.events.InputEvent
import com.colemcg.skydiver.core.events.InputType
import com.colemcg.skydiver.core.geometry.Vector2



class StartOverlayTest {

    private val buttonWidth = 200f
    private val buttonHeight = 80f
    private val x = (1280 - buttonWidth) / 2f
    private val yStart = 350f
    private val spacing = 100f

    private fun overlayWithCallbacks(
        onStart: () -> Unit = {},
        onOptions: () -> Unit = {},
        onStats: () -> Unit = {},
        onCredits: () -> Unit = {}
    ): StartOverlay = StartOverlay(
        OnStart = onStart,
        OnStats = onStats,
        OnOptions = onOptions,
        OnCredits = onCredits
    )

    @Test
    fun startButtonTapTriggersOnStart() {
        var called = false
        val overlay = overlayWithCallbacks(onStart = { called = true })
        overlay.show() // make overlay visible

        // Simulate tap in the middle of the Start button
        val tap = InputEvent(
            InputType.Tap,
            Vector2(x + buttonWidth / 2, yStart + buttonHeight / 2),
            System.currentTimeMillis()
        )
        overlay.handleInput(tap)
        assertTrue("Start callback should be triggered", called)
    }

    @Test
    fun optionsButtonTapTriggersOnOptions() {
        var called = false
        val overlay = overlayWithCallbacks(onOptions = { called = true })
        overlay.show()
        val tap = InputEvent(
            InputType.Tap,
            Vector2(x + buttonWidth / 2, yStart + spacing + buttonHeight / 2),
            System.currentTimeMillis()
        )
        overlay.handleInput(tap)
        assertTrue("Options callback should be triggered", called)
    }

    @Test
    fun statsButtonTapTriggersOnStats() {
        var called = false
        val overlay = overlayWithCallbacks(onStats = { called = true })
        overlay.show()
        val tap = InputEvent(
            InputType.Tap,
            Vector2(x + buttonWidth / 2, yStart + spacing * 2 + buttonHeight / 2),
            System.currentTimeMillis()
        )
        overlay.handleInput(tap)
        assertTrue("Stats callback should be triggered", called)
    }

    @Test
    fun creditsButtonTapTriggersOnCredits() {
        var called = false
        val overlay = overlayWithCallbacks(onCredits = { called = true })
        overlay.show()
        val tap = InputEvent(
            InputType.Tap,
            Vector2(x + buttonWidth / 2, yStart + spacing * 3 + buttonHeight / 2),
            System.currentTimeMillis()
        )
        overlay.handleInput(tap)
        assertTrue("Credits callback should be triggered", called)
    }

    @Test
    fun tapOutsideButtonsTriggersNothing() {
        var start = false; var options = false; var stats = false; var credits = false
        val overlay = overlayWithCallbacks(
            onStart = { start = true },
            onOptions = { options = true },
            onStats = { stats = true },
            onCredits = { credits = true }
        )
        overlay.show()
        // Tap outside any button
        val tap = InputEvent(
            InputType.Tap,
            Vector2(50f, 50f),
            System.currentTimeMillis()
        )
        overlay.handleInput(tap)
        assertFalse(start || options || stats || credits)
    }

    @Test
    fun hiddenOverlayDoesNotTriggerCallback() {
        var called = false
        val overlay = overlayWithCallbacks(onStart = { called = true })
        overlay.hide() // overlay not visible
        val tap = InputEvent(
            InputType.Tap,
            Vector2(x + buttonWidth / 2, yStart + buttonHeight / 2),
            System.currentTimeMillis()
        )
        overlay.handleInput(tap)
        assertFalse("Callback should NOT be triggered if overlay is hidden", called)
    }
}