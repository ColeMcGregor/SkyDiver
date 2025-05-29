package com.colemcg.skydiver.platform
//brind in custom vector2 class
import com.colemcg.skydiver.core.geometry.Vector2

/*
* Abstract class for all game renderers, Android and IOS
* Subclasses implement all their own logic for the system they are on.
*/
abstract class GameRenderer {

    //draw player sprite
    abstract fun drawPlayer(position: Vector2)

    //draw obstacle sprites(like a hangglider)
    abstract fun drawObstacle(position: Vector2)

    //draw collectible sprites(like coins)
    abstract fun drawCollectible(position: Vector2)

    //draw background objects(like clouds)
    abstract fun drawBackgroundObjects(position: Vector2)

    //clear the screen, for when the game is restarted
    open fun clearScreen() {}

    //flush the screen, for the end of a frame
    open fun flush() {}
}
