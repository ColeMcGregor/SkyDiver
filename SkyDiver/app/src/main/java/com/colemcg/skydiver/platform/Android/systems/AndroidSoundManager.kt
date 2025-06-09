package com.colemcg.skydiver.platform.Android

import com.colemcg.skydiver.core.systems.SoundManager

/**
 * Android implementation of the SoundManager.
 * This class will handle sound effects and music playback on Android devices.
 * It will use platform-specific APIs to manage audio resources.
 *
 * @author Jardina Gomez
 */

class AndroidSoundManager : SoundManager {
    private var isMuted: Boolean = false
    private var sfxVolume: Float = 1.0f
    private var musicVolume: Float = 1.0f

    override fun playSFX(name: String) {
        // TODO: research what SoundPool is and how to use it to play sound effects on Android
    }

    override fun stopSFX(name: String) {
        //TODO: Implementation for stopping sound effects on Android
    }

    override fun playMusic(name: String, loop: Boolean) {
        // TODO: Implementation for playing music on Android
    }

    override fun stopMusic(name: String) {
        // TODO: Implementation for stopping music on Android
    }

    override fun toggleSound() {
        // TODO: Implementation for toggling sound on Android
    }

    override fun isMuted(): Boolean = false // Default implementation

    override fun setSFXVolume(volume: Float) {
        // TODO: Implementation for setting SFX volume on Android
    }

    override fun getSFXVolume(): Float = 1.0f // Default implementation

    override fun setMusicVolume(volume: Float) {
        musicVolume = volume
        // TODO: Adjust music volume
    }

    override fun getMusicVolume(): Float = 1.0f // Default implementation

}