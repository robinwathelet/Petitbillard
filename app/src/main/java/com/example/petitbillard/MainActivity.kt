package com.example.petitbillard

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.AttributeSet
import android.view.SurfaceView
import android.view.View
import android.widget.Button
import android.widget.Toast
import android.graphics.Paint





class MainActivity : Activity() {

    lateinit var drawingView: DrawingView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawingView = findViewById<DrawingView>(R.id.DrawingView)
    }

    override fun onPause() {
        super.onPause()
        drawingView.pause()
    }

    override fun onResume() {
        super.onResume()
        drawingView.resume()
    }

    fun onClick(v: View){
        if (drawingView.drawing) drawingView.pause()
        else drawingView.resume()
    }

}