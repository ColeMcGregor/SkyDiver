package  com.colemcg.skydiver.core.systems

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
 * @author Jardina Gomez(main body), Cole McGregor (comments, small edits for abstraction)
 *
 */
interface SoundManager {
    // play a sound effect by name
    fun playSFX(name: String) 
    // stop a sound effect by name
    fun stopSFX(name: String) 
    // play background music by name
    fun playMusic(name: String, loop: Boolean) 
    // stop background music by name
    fun stopMusic(name: String) 
    // toggle sound on or off
    fun toggleSound() 
    // check if sound is muted
    fun isMuted(): Boolean = false
    // set the volume of sound effects in total
    fun setSFXVolume(volume: Float) 
    // get the volume of sound effects in total
    fun getSFXVolume(): Float = 1.0f // will be implemented in each platform
    // set the volume of background music in total
    fun setMusicVolume(volume: Float) 
    // get the volume of background music in total
    fun getMusicVolume(): Float = 1.0f // will be implemented in each platform
    // get the volume of all sound in total
    fun getTotalVolume(): Float = 1.0f // will be implemented in each platform
}