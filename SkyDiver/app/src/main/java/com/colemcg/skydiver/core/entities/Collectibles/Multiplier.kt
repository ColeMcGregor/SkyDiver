package com.colemcg.skydiver.core.entities.Collectibles

import com.colemcg.skydiver.core.geometry.Vector2
import com.colemcg.skydiver.core.entities.Collectible
import com.colemcg.skydiver.core.entities.Player
import com.colemcg.skydiver.core.systems.ScoreManager
import com.colemcg.skydiver.core.systems.SoundManager

/**
 * Collectible that temporarily boosts the player's score multiplier.
 *
 * When picked up, this collectible increases the multiplier
 * applied to future point-earning events for a limited time.
 *
 * Works in conjunction with ScoreManager.
 * Rarer than standard collectibles like Coin.
 *
 * @see Collectible
 * @author Cole McGregor
 */

 class Multiplier(position: Vector2 = Vector2()) : Collectible(position) {

    override val spriteName = "multiplier"
    override val spriteSize = Vector2(8f, 5f)


    override fun onCollect(player: Player, scoreManager: ScoreManager, soundManager: SoundManager) {

        //TODO this needs to be implemented

    }

    override fun update(deltaTime: Float, player: Player) {

        //TODO this needs to be implemented

    }
}
