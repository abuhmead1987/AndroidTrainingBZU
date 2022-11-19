package com.examples.android.androidtrainingbzu

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiveTextActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive_text)
        val textView = findViewById<TextView>(R.id.txt_sharedText)
        val intent = intent
        if (intent != null && intent.data != null && intent.data!!.scheme != null) {
            when (intent.data!!.scheme) {
                "http" -> {
                    val uri = intent.data
                    if (uri != null) {
                        val uri_string = uri.toString()
                        textView.text = uri_string
                    }
                }
            }
        } else {
            textView.text = intent!!.getStringExtra(Intent.EXTRA_TEXT)
        }
    }
}