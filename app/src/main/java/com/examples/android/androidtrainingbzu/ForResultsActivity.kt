package com.examples.android.androidtrainingbzu

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ForResultsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_for_results)
    }

    fun callActivityForResult(view: View?) {
        val intent = Intent(this, NameActivity::class.java)
        startActivityForResult(intent, NAME_ACTIVITY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            (findViewById<View>(R.id.txt_name) as TextView).text =
                data!!.getStringExtra("user_name")
        } else {
            Toast.makeText(this, "HUM!!!, you canceled the request", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        private const val NAME_ACTIVITY = 50
    }
}