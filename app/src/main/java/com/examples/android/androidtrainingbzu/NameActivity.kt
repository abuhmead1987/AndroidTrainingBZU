package com.examples.android.androidtrainingbzu

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class NameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name)
    }

    fun finishActivity(view: View?) {
        val i = Intent()
        i.putExtra("user_name", (findViewById<View>(R.id.edt_name) as EditText).text.toString())
        //finishActivity(RESULT_OK);
        setResult(RESULT_OK, i)
        finish()
    }

    fun cancelActivity(view: View?) {
        val i = Intent()
        setResult(RESULT_CANCELED, i)
        finish()
    }
}