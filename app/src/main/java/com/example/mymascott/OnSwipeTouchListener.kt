package com.example.mymascott

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import kotlin.math.abs


//Helper class to detect different gestures and customise them according to our use case.
open class OnSwipeTouchListener(private val ctx: Context) : View.OnTouchListener {

    private val gestureDetector: GestureDetectorCompat

    companion object {

        private const val SWIPE_THRESHOLD = 100
        private const val SWIPE_VELOCITY_THRESHOLD = 100
    }

    init {
        gestureDetector = GestureDetectorCompat(ctx, GestureListener())
    }


    override fun onTouch(v: View, event: MotionEvent): Boolean {
        v.performClick()
        return gestureDetector.onTouchEvent(event)
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {


        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            var result = false
            try {
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                if (abs(diffX) > abs(diffY)) {
                    if (abs(diffX) > SWIPE_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight()
                        } else {
                            onSwipeLeft()
                        }
                        result = true
                    }
                } else if (abs(diffY) > SWIPE_THRESHOLD && abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom()
                    } else {
                        onSwipeTop()
                    }
                    result = true
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }

            return result
        }


        override fun onDoubleTap(event: MotionEvent?): Boolean {
            onDoubleTapPressed()
            return true
        }

        override fun onSingleTapConfirmed(event: MotionEvent?): Boolean {
            onSingleTapConfirm()
            return true
        }

        override fun onLongPress(event: MotionEvent) {
            onLongPressed()
        }


    }

    open fun onDoubleTapPressed(){}

    open fun onSingleTapConfirm(){}

    open fun onLongPressed(){}

    open fun onSwipeRight() {}

    open fun onSwipeLeft() {}

    open fun onSwipeTop() {}

    open fun onSwipeBottom() {}
}