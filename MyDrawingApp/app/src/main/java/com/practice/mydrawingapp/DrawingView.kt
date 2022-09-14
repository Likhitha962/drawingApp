package com.practice.mydrawingapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import java.nio.file.Path
import java.util.jar.Attributes

//if you need to draw something then definately we need view
class DrawingView(context: Context,attrs:Attributes) : View(context, attrs) {
    private  var mDrawPath :CustomPath? = null
    private  var mCanvasBitmap : Bitmap? = null
    private var mDrawPaint : Paint? = null
    private  var mCanvasPaint : Paint? = null
    private  var mBrushSize: Float = 0.toFloat()
    private  var color = Color.BLACK




//this is nested class only used inside the drawing view
   internal inner class  CustomPath (var color:Int, var brushThickness:Float) : Path(){

    }

}
}

