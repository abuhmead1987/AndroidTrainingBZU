package com.examples.android.androidtrainingbzu

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ActivityLifcycle : AppCompatActivity() {
    private var txt_counter: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifcycle)
        txt_counter = findViewById(R.id.txt_counter)
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume ")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart ")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy ")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart ")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("count", txt_counter!!.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        txt_counter!!.text = savedInstanceState.getString("count")
    }

    fun incrementNumber(view: View?) {
        var count = txt_counter!!.text.toString().toInt()
        txt_counter!!.text = "" + ++count
    }

    companion object {
        private const val TAG = "ActivityLifcycle"
    }
}