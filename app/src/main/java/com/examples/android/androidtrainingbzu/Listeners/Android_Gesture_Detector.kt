package com.examples.android.androidtrainingbzu.Listeners

import android.util.Log
import android.view.GestureDetector
import android.view.GestureDetector.OnDoubleTapListener
import android.view.MotionEvent

class Android_Gesture_Detector : GestureDetector.OnGestureListener, OnDoubleTapListener {
    override fun onDown(e: MotionEvent): Boolean {
        Log.d("Gesture ", " onDown")
        return true
    }

    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        Log.d("Gesture ", " onSingleTapConfirmed")
        return true
    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        Log.d("Gesture ", " onSingleTapUp")
        return true
    }

    override fun onShowPress(e: MotionEvent) {
        Log.d("Gesture ", " onShowPress")
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        Log.d("Gesture ", " onDoubleTap")
        return true
    }

    override fun onDoubleTapEvent(e: MotionEvent): Boolean {
        Log.d("Gesture ", " onDoubleTapEvent")
        return true
    }

    override fun onLongPress(e: MotionEvent) {
        Log.d("Gesture ", " onLongPress")
    }

    override fun onScroll(
        e1: MotionEvent,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float,
    ): Boolean {
        Log.d("Gesture ", " onScroll")
        if (e1.y < e2.y) {
            Log.d("Gesture ", " Scroll Down")
        }
        if (e1.y > e2.y) {
            Log.d("Gesture ", " Scroll Up")
        }
        return true
    }

    override fun onFling(
        e1: MotionEvent,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float,
    ): Boolean {
        if (e1.x < e2.x) {
            Log.d("Gesture ", "Left to Right swipe: " + e1.x + " - " + e2.x)
            Log.d("Speed ", "$velocityX pixels/second")
        }
        if (e1.x > e2.x) {
            Log.d("Gesture ", "Right to Left swipe: " + e1.x + " - " + e2.x)
            Log.d("Speed ", "$velocityX pixels/second")
        }
        if (e1.y < e2.y) {
            Log.d("Gesture ", "Up to Down swipe: " + e1.x + " - " + e2.x)
            Log.d("Speed ", "$velocityY pixels/second")
        }
        if (e1.y > e2.y) {
            Log.d("Gesture ", "Down to Up swipe: " + e1.x + " - " + e2.x)
            Log.d("Speed ", "$velocityY pixels/second")
        }
        return true
    }
}