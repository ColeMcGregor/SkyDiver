package com.colemcg.skydiver.platform.android

import android.content.Context
import android.graphics.Canvas
import android.view.SurfaceHolder
import android.view.SurfaceView
import platform.android.AndroidGameLoop

/**
 * GameView is a custom drawing surface where the game is rendered.
 * It integrates with AndroidGameLoop and allows Canvas-based rendering.
 *
 * @author Jardina Gomez
 */
class GameView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {

    var gameLoop: AndroidGameLoop? = null

    init {
        holder.addCallback(this) // Register lifecycle callbacks
        isFocusable = true       // Needed to receive touch input
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        gameLoop?.start()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        gameLoop?.stop()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        //TODO:  Optional: Handle resolution changes
    }

    /**
     * Call this manually from your GameLoop's drawAll(renderer)
     * to get access to Canvas.
     */
    fun lockAndDraw(drawCallback: (Canvas) -> Unit) {
        val canvas = holder.lockCanvas()
        if (canvas != null) {
            drawCallback(canvas)
            holder.unlockCanvasAndPost(canvas)
        }
    }
}
