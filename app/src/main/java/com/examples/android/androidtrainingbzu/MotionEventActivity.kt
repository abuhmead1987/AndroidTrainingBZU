package com.examples.android.androidtrainingbzu

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MotionEventActivity : AppCompatActivity(), OnTouchListener {
    var clickCount = 0
    var startTime: Long = 0
    private var RootLayout: ViewGroup? = null
    private var Position_X = 0
    private var Position_Y = 0
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion_event)
        RootLayout = findViewById<View>(R.id.rootLayout) as ViewGroup


        //new image
        val NewImage = findViewById<View>(R.id.new_image_button) as Button
        NewImage.setOnClickListener { Add_Image() }
        clickCount = 0
    }

    private fun Add_Image() {
        val iv = ImageView(this)
        iv.setImageResource(R.drawable.bar_chart)
        val layoutParams = RelativeLayout.LayoutParams(150, 150)
        iv.layoutParams = layoutParams
        RootLayout!!.addView(iv, layoutParams)
        iv.setOnTouchListener(this)
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        val X = event.rawX.toInt()
        val Y = event.rawY.toInt()
        val pointerCount = event.pointerCount
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                val layoutParams = view.layoutParams as RelativeLayout.LayoutParams
                Position_X = X - layoutParams.leftMargin
                Position_Y = Y - layoutParams.topMargin
            }
            MotionEvent.ACTION_UP -> if (startTime == 0L) {
                startTime = System.currentTimeMillis()
            } else {
                if (System.currentTimeMillis() - startTime < 200) {
                    val builder = AlertDialog.Builder(this@MotionEventActivity)
                    builder.setMessage("Are you sure you want to delete this?")
                    builder.setPositiveButton("Yes") { dialog, which ->
                        view.visibility = View.GONE
                    }
                    builder.setNegativeButton("No") { dialog, which -> dialog.dismiss() }
                    val alertDialog = builder.create()
                    alertDialog.show()
                }
                startTime = System.currentTimeMillis()
            }
            MotionEvent.ACTION_POINTER_DOWN -> {}
            MotionEvent.ACTION_POINTER_UP -> {}
            MotionEvent.ACTION_MOVE -> {
                if (pointerCount == 1) {
                    val Params = view.layoutParams as RelativeLayout.LayoutParams
                    Params.leftMargin = X - Position_X
                    Params.topMargin = Y - Position_Y
                    Params.rightMargin = -500
                    Params.bottomMargin = -500
                    view.layoutParams = Params
                }
                if (pointerCount == 2) {
                    val layoutParams1 = view.layoutParams as RelativeLayout.LayoutParams
                    layoutParams1.width = Position_X + event.x.toInt()
                    layoutParams1.height = Position_Y + event.y.toInt()
                    view.layoutParams = layoutParams1
                }

                //Rotation
                if (pointerCount == 3) {
                    //Rotate the ImageView
                    view.rotation = view.rotation + 10.0f
                }
            }
        }

// Schedules a repaint for the root Layout.
        RootLayout!!.invalidate()
        return true
    }
}