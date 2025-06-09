/**
 * Interface for cross-platform sound effect and music playback.
 *
 * Defines a contract for controlling background music, sound effects,
 * volume levels, and muting functionality. Concrete implementations
 * should exist for each supported platform (e.g., Android, iOS).
 *
 * responsibilities:
 * - Play SFX by name.
 * - Toggle sound or music globally.
 * - Adjust volume levels in settings.
 *
 * This interface is platform-agnostic and injected into game components.
 *
 * @author Jardina Gomez
 *
 */

fun playSFX(name: String) {}
fun stopSFX(name: String) {}
fun playMusic(name: String, loop: Boolean) {}
fun stopMusic(name: String) {}
fun toggleSound() {}
fun isMuted(): Boolean = false
fun setSFXVolume(volume: Float) {}
fun getSFXVolume(): Float = 1.0f // for right now
fun setMusicVolume(volume: Float) {}
fun getMusicVolume(): Float = 1.0f // for right now
