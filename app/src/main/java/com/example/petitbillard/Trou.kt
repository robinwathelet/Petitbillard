package com.example.petitbillard

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class Trou (x1: Float, y1: Float, diametre: Float) {
    val r = RectF(x1, y1, x1 + diametre, y1 + diametre)
    val paint = Paint()

    fun draw (canvas: Canvas) {
        paint.color = Color.BLACK
        canvas.drawOval(r, paint)
    }
}