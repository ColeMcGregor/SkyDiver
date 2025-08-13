package com.colemcg.skydiver.core.game

import com.colemcg.skydiver.core.geometry.Vector2
import com.colemcg.skydiver.core.systems.*
import com.colemcg.skydiver.core.levels.LevelManager
import com.colemcg.skydiver.core.entities.*


/**
 * Manages the overall game state, including player, objects, and game systems.
 * Also manages the Spawner, SpeedManager, DifficultyManager, ScoreManager, StatsTracker, and SoundManager.
 * Calls the update and draw methods for all objects and game systems.
 * Slave to the GameLoop
 * 
 * @author Cole McGregor
 */

const val SCREEN_HEIGHT: Int = 800  //this is the height of the screen (the value is the bottom pixel of the screen)
const val SCREEN_WIDTH: Int = 1280 //this is the width of the screen (the value is the right pixel of the screen)
const val REPEAT_AMOUNT: Int = 10 //this is the amount of background objects to spawn

class GameManager(
    val levelManager: LevelManager,
    val speedManager: SpeedManager,
    val difficultyManager: DifficultyManager,
    val scoreManager: ScoreManager,
    val statsManager: StatsManager,
    val soundManager: SoundManager
) {
    //GAMESTATE EXISTS AS A GLOBAL OBJECT, SO WE CAN ACCESS IT FROM ANYWHERE
    //make a player object
    val player = Player(Vector2(0f, 0f))
    //make a list of objects to hold all the objects in the game
    private val objects = mutableListOf<GameObject>()
    //make a list of background objects to hold all the background objects in the game
    private val backgroundObjects = mutableListOf<BackgroundObject>()
    //make a spawner object to handle the spawning of objects
    private val spawner = Spawner(levelManager)

    // Loop height is about 5 screens worth of vertical space
    private val bgLoopHeight = SCREEN_HEIGHT * 5 

    // Initialize the game manager
    init {



        // Initialize the background objects
        spawnInitialBackgroundObjects()

        //TODO: this  might need changing to start at a trigger somehwere, like via a button on a start overlay
        //start a new game state
        GameState.startGame()
    }

    // Update all objects and game systems
    fun updateAll(deltaTime: Float) {
        // If the game hasn't started or is over, don't update anything
        if (!GameState.gameStarted || GameState.gameOver) return

        // Update the speed manager (game speed adjustment)
        speedManager.update(deltaTime)

        // Update the difficulty manager(spawn interval adjustment, via performance score computation)
        difficultyManager.update(deltaTime)

        // Update the score manager (staying alive point giving, multiplier decay)
        scoreManager.update(deltaTime)

        // Update the spawner
        spawner.update(deltaTime, objects)

        // Update the background objects
        backgroundObjects.forEach {
            it.update(deltaTime, player)
            it.checkAndLoopIfNeeded(bgLoopHeight.toFloat())
        }

        // Update the player
        player.update(deltaTime, player)

        // Update the objects, some of which will need to know the player's position
        objects.forEach { it.update(deltaTime, player, speedManager.getGameSpeed()) }

        // Check for collisions between player and objects
        checkCollisions()

        // Remove dead objects
        removeDeadObjects()
    }

    // Draw all objects and game systems
    fun drawAll(renderer: GameRenderer) {
        val playerPosition = player.position

        renderer.drawBackgroundLayer(playerPosition)
        backgroundObjects.forEach { it.onDraw(renderer) }
        objects.forEach { it.onDraw(renderer) }
        player.onDraw(renderer)
    }

        /*
         * Check for collisions between player and objects
         * @param objects The list of objects to check for collisions with the player.
         * @param player The player to check for collisions with.
         * @param scoreManager The score manager to update the score.
         * @author Cole McGregor
         * @author Cole McGregor
         */
        private fun checkCollisions() {
            val playerHitbox = player.hitbox
        
            //check all objects
            for (obj in objects) {            
                //if the objects hitbox intersects with the player's hitbox                                                       //for each object in the objects list       
                if (playerHitbox.intersects(obj.hitbox)) {      
                    //when the object is a...                                     //if the player's hitbox intersects with the object's hitbox
                    when (obj) {
                        //...collectible, call the onCollect function and mark the object for removal
                        is Collectible -> {                                                          //if the object is a collectible
                            obj.onCollect(player, scoreManager, soundManager)                        //call the onCollect function
                            obj.isMarkedForRemoval = true                                            //mark the object for removal
                        }
                        //...obstacle, call the onCollision function and mark the object for removal
                        is Obstacle -> {                                                             //if the object is an obstacle
                            obj.onCollision(player, scoreManager, speedManager, soundManager)        //call the onCollision function
                            obj.isMarkedForRemoval = true                                            //mark the object for removal
                        }
                    }
                }
            }
        }


    // Remove dead objects(not background objects) which means they have passed the screen height
    private fun removeDeadObjects() {
        objects.removeIf { it.position.y > SCREEN_HEIGHT || it.isMarkedForRemoval }
    }

    // Reset the game, calling all necessary reset methods, and then spawns the initial background objects
    fun reset() {
        objects.clear()
        backgroundObjects.clear()
        player.reset()
        speedManager.reset()
        difficultyManager.reset()
        scoreManager.resetScore()
        spawner.reset()
        spawnInitialBackgroundObjects() //normally done by init(), but the game is being reset, not initialized
    }

    // Spawns the initial background objects
    private fun spawnInitialBackgroundObjects() {
        val factories = levelManager.getCurrentLevel().bgObjectTypes
        repeat(REPEAT_AMOUNT) { //this is essentially a for loop, it will repeat the code REPEAT_AMOUNT times
            factories.randomOrNull()?.let { factory ->
                val obj = factory().apply {
                    position = randomInitialBackgroundPosition()
                }
                backgroundObjects.add(obj)
            }
        }
    }

    // Generates a random initial background position
    private fun randomInitialBackgroundPosition(): Vector2 {
        val x = (0..SCREEN_WIDTH).random().toFloat() //will need adjusting so cloud are at least some way on screen
        val y = (0..(bgLoopHeight.toInt())).random().toFloat() - bgLoopHeight
        return Vector2(x, y)
    }
}
