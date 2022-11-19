package com.examples.android.androidtrainingbzu

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.examples.android.androidtrainingbzu.Listeners.Android_Gesture_Detector

class ScaleGestureDetectorActivity : AppCompatActivity() {
    private var imageView: ImageView? = null
    private var scale = 1f
    private var detector: ScaleGestureDetector? = null
    var android_gesture_detector: Android_Gesture_Detector? = null
    var mGestureDetector: GestureDetector? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scale_gesture_detector)
        imageView = findViewById<View>(R.id.img_scalimg) as ImageView
        detector = ScaleGestureDetector(this, ScaleListener())
        android_gesture_detector = Android_Gesture_Detector()
        // Create a GestureDetector
        mGestureDetector = GestureDetector(this, android_gesture_detector)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
//  re-route the Touch Events to the ScaleListener class
        detector!!.onTouchEvent(event)
        mGestureDetector!!.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    inner class ScaleListener : SimpleOnScaleGestureListener() {
        var onScaleBegin = 0f
        var onScaleEnd = 0f
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scale *= detector.scaleFactor
            imageView!!.scaleX = scale
            imageView!!.scaleY = scale
            return true
        }

        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            Toast.makeText(applicationContext, "Scale Begin", Toast.LENGTH_SHORT).show()
            onScaleBegin = scale
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector) {
            Toast.makeText(applicationContext, "Scale Ended", Toast.LENGTH_SHORT).show()
            onScaleEnd = scale
            if (onScaleEnd > onScaleBegin) {
                Toast.makeText(applicationContext,
                    "Scaled Up by a factor of  " + (onScaleEnd / onScaleBegin).toString(),
                    Toast.LENGTH_SHORT).show()
            }
            if (onScaleEnd < onScaleBegin) {
                Toast.makeText(applicationContext,
                    "Scaled Down by a factor of  " + (onScaleBegin / onScaleEnd).toString(),
                    Toast.LENGTH_SHORT).show()
            }
            super.onScaleEnd(detector)
        }
    }
}