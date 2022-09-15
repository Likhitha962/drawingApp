package com.practice.mydrawingapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import java.util.jar.Attributes

//if you need to draw something then definately we need view
class DrawingView(context: Context,attrs:AttributeSet) : View(context, attrs) {
    private  var mDrawPath :CustomPath? = null
    private  var mCanvasBitmap : Bitmap? = null
    private var mDrawPaint : Paint? = null
    private  var mCanvasPaint : Paint? = null
    private  var mBrushSize: Float = 0.toFloat()
    private  var color = Color.BLACK
    private var canvas : Canvas? = null
    private  var mPaths = ArrayList<CustomPath>()


    init {
        setUpDrawing()
    }

    private fun setUpDrawing(){
        mDrawPaint = Paint()
        mDrawPath= CustomPath(color,mBrushSize)
        mDrawPaint!!.color = color
        mDrawPaint!!.style = Paint.Style.STROKE
        mDrawPaint!!.strokeJoin =Paint.Join.ROUND
        mDrawPaint!!.strokeCap =Paint.Cap.ROUND
        mCanvasPaint = Paint(Paint.DITHER_FLAG)
        //mBrushSize = 20.toFloat()
    }
//just give onSizeChanged it will be giving a pop up override
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mCanvasBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888)
    //since mcanvasbitmap is a nullable so use !!
        canvas = Canvas(mCanvasBitmap!!)
    }
//this onDraw method usually requires canvas in order to write or draw something
    //change canvas to canvas? if it fails
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

    canvas.drawBitmap(mCanvasBitmap!!,0f,0f,mCanvasPaint)
    for (path in mPaths){
        mDrawPaint!!.strokeWidth = path.brushThickness
        mDrawPaint!!.color =path.color
        canvas.drawPath(path,mDrawPaint!!)
    }

if (!mDrawPath!!.isEmpty){
    mDrawPaint!!.strokeWidth = mDrawPath!!.brushThickness
    mDrawPaint!!.color = mDrawPaint!!.color
    canvas.drawPath(mDrawPath!!,mDrawPaint!!)
}
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var touchX = event?.x
        var touchY = event?.y

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                mDrawPath!!.color = color
                mDrawPath!!.brushThickness = mBrushSize
                mDrawPath!!.reset()
                        mDrawPath!!.moveTo(touchX!!, touchY!!)
            }
            MotionEvent.ACTION_MOVE -> {

                        mDrawPath!!.lineTo(touchX!!, touchY!!)
            }
            MotionEvent.ACTION_UP -> {
                mPaths.add(mDrawPath!!)
                mDrawPath = CustomPath(color, mBrushSize)
            }
            else -> return false
        }
        invalidate()

        return true
       // return super.onTouchEvent(event)
    }
    fun setSizeForBrush(newsize : Float){
        mBrushSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
        newsize, resources.displayMetrics)
        mDrawPaint!!.strokeWidth = mBrushSize
    }



//this is nested class only used inside the drawing view
 internal inner class CustomPath(var color:Int,var brushThickness: Float) : Path(){

}

}

