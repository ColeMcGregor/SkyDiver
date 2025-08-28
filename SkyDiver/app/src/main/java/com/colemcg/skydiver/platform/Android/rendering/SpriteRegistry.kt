package com.colemcg.skydiver.platform.android.rendering

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix

/**
 * This class is used to load and cache sprites from the assets/sprites folder.
 * It is used to get the original sprite bitmap by name, and to get the sprite scaled by a power-of-two factor.
 * 
 * The sprite registry is initialized to load the sprites from the assets/sprites folder.
 * it can give original and scaled sprites by name.
 * 
 * used for the game view to draw the sprites.
 * 
 * used by GameRenderer to draw the sprites.
 * 
 * @author Cole McGregor
 * @version 1.0
 * @date 2025-08-28
 * 
 */

object SpriteRegistry {
    private lateinit var assetManager: android.content.res.AssetManager
    private val spriteCache: MutableMap<String, Bitmap> = mutableMapOf()

    private val allowedScales = setOf(0.5f, 1f, 2f, 4f, 8f, 16f)
    //the folder where the sprites are stored (MUST MATCH THE FOLDER IN THE ASSETS FOLDER)
    private const val SPRITE_FOLDER = "sprites"

    /**
     * Initializes the sprite registry by loading all PNG sprites from the main/assets/sprites folder.
     */
    fun init(context: Context) {
        //get the asset manager, which is an android class that manages the assets of the app
        assetManager = context.assets
        //get the list of files in the sprites folder
        val spriteNames = assetManager.list(SPRITE_FOLDER) ?: return
        //loop through the files and add them to the sprite cache
        for (fileName in spriteNames) {
            //check if the file is a png
            if (fileName.endsWith(".png")) {
                //remove the .png extension from the file name
                val key = fileName.removeSuffix(".png")
                //open the file as a stream
                val inputStream = assetManager.open("$SPRITE_FOLDER/$fileName")
                //decode the stream into a bitmap
                val bitmap = BitmapFactory.decodeStream(inputStream)
                //add the bitmap to the sprite cache, using the key as the name of the sprite
                spriteCache[key] = bitmap
                //close the stream
                inputStream.close()
            }
        }
    }

    /**
     * Returns the original sprite bitmap by name.
     * @throws IllegalArgumentException if the sprite name is not found.
     */
    fun getSprite(name: String): Bitmap {
        //check if the sprite is in the cache, return the bitmap if it is, throw an exception if it is not
        return spriteCache[name]
            ?: throw IllegalArgumentException("Sprite '$name' not found in SpriteRegistry.")
    }

    /**
     * Returns the sprite scaled by a power-of-two factor.
     * Only accepts 0.5x, 1x, 2x, 4x, 8x, or 16x.
     * 
     * @param name Name of the sprite.
     * @param scaleFactor Scale factor to apply (must be in allowedScales).
     * @throws IllegalArgumentException if the scale factor is invalid or sprite is missing.
     */
    fun getSpriteScaled(name: String, scaleFactor: Float): Bitmap {
        if (scaleFactor !in allowedScales) {
            throw IllegalArgumentException("Invalid scale factor: $scaleFactor. Allowed: $allowedScales")
        }

        val original = getSprite(name)
        val width = (original.width * scaleFactor).toInt()
        val height = (original.height * scaleFactor).toInt()

        return Bitmap.createScaledBitmap(original, width, height, true)
    }

    //DEBUGGING METHODS

    /**
     * Returns whether the sprite is in the cache under the given key
     */
    fun isPresent(name: String): Boolean {
        return spriteCache.containsKey(name)
    }

    /**
     * returns the number of sprites in the cache
     */
    fun getSpriteCount(): Int {
        return spriteCache.size
    }

    /**
     * returns the names of the sprites in the cache
     */
    fun printSpriteNames() {
        for (name in spriteCache.keys) {
            println(name)
        }
    }
}
