package com.colemcg.skydiver.core.game

import com.colemcg.skydiver.core.geometry.Vector2
import com.colemcg.skydiver.core.systems.*
import com.colemcg.skydiver.core.levels.LevelManager
import com.colemcg.skydiver.core.entities.GameObject
import com.colemcg.skydiver.core.entities.Player
import com.colemcg.skydiver.core.entities.Spawner
import com.colemcg.skydiver.core.game.GameState

class GameManager(
    val levelManager: LevelManager,
    val speedManager: SpeedManager,
    val difficultyManager: DifficultyManager,
    val scoreManager: ScoreManager,
    val statsTracker: StatsTracker,
    val soundManager: SoundManager
) {
    val player = Player(Vector2(0f, 0f))
    private val objects = mutableListOf<GameObject>()
    private val spawner = Spawner(levelManager)

    fun updateAll(deltaTime: Float) {
        if (!GameState.gameStarted || GameState.gameOver) return

        speedManager.update(deltaTime)
        difficultyManager.update(deltaTime)
        spawner.update(deltaTime, objects)

        player.update(deltaTime)
        objects.forEach { it.update(deltaTime) }

        checkCollisions()
        removeDeadObjects()
    }

    fun drawAll(renderer: GameRenderer) {
        val playerPosition = player.position

        renderer.drawBackgroundLayer(playerPosition)
        objects.forEach { it.onDraw(renderer) }
        player.onDraw(renderer)
    }

    private fun checkCollisions() {
        // TODO: Implement spatial checking between player and each GameObject
        //       - Apply onPlayerCollision() if Obstacle
        //       - Apply onCollect() if Collectible
    }

    private fun removeDeadObjects() {
        // TODO: Remove objects that have left the screen or are marked inactive
    }

    fun reset() {
        objects.clear()
        player.reset()
        speedManager.reset()
        difficultyManager.reset()
        scoreManager.resetScore()
        spawner.reset()
    }
}
