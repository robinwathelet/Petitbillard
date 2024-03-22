package com.example.petitbillard


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.util.*
import kotlinx.coroutines.*



class DrawingView @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0): SurfaceView(context, attributes,defStyleAttr), SurfaceHolder.Callback,Runnable {
    lateinit var canvas: Canvas
    val backgroundPaint = Paint()
    lateinit var thread: Thread
    var drawing: Boolean = true
    val lesBalles = ArrayList<Balle>()
    lateinit var lesParois: Array<Parois>

    init {
        backgroundPaint.color = Color.WHITE
    }

    override fun onSizeChanged(w: Int,h: Int,oldw: Int,oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val canvasH = (h - 500).toFloat()
        val canvasW = (w - 25).toFloat()
        lesParois = arrayOf(
            Parois(5f, 5f, 25f, canvasH),
            Parois(5f, 5f, canvasW, 25f),
            Parois(5f, canvasH, canvasW, canvasH + 25),
            Parois(canvasW, 5f, canvasW + 25, canvasH + 25))
    }

    fun pause() {
        drawing = false
        thread.join()
    }

    fun resume() {
        drawing = true
        thread = Thread(this)
        thread.start()
    }

    fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            canvas.drawRect(0F, 0F, canvas.getWidth()*1F,
                canvas.getHeight()*1F, backgroundPaint)
            for (b in lesBalles) {
                b.draw(canvas)
            }
            for (p in lesParois) {
                p.draw(canvas)
            }
            holder.unlockCanvasAndPost(canvas)
        }
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                val x = e.rawX.toInt() - 100
                val y = e.rawY.toInt() - 300
                lesBalles.add(Balle(x.toFloat(), y.toFloat(), 30f))
            }
        }
        return true
    }

    override fun run() {
        while (drawing) {
            for (b in lesBalles) {
                b.bouge(lesParois, lesBalles)
            }
            draw()
        }
    }

    override fun surfaceChanged(
        holder: SurfaceHolder, format: Int,
        width: Int, height: Int) {
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        thread = Thread(this)
        thread.start()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        thread.join()
    }
}