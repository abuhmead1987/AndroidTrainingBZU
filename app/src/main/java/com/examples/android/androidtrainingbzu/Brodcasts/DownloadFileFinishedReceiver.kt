package com.examples.android.androidtrainingbzu.Brodcasts

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.examples.android.androidtrainingbzu.R
import com.examples.android.androidtrainingbzu.Services.DownloadService
import java.io.File

class DownloadFileFinishedReceiver(
    private val activity: Activity,
    private val textView: TextView?,
) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val bundle = intent.extras
        if (bundle != null) {
            val string = bundle.getString(DownloadService.Companion.FILEPATH)
            val resultCode = bundle.getInt(DownloadService.Companion.RESULT)
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(activity.applicationContext,
                    "Download complete. Download URI: $string",
                    Toast.LENGTH_LONG).show()
                (activity.findViewById<View>(R.id.img_loaded) as ImageView).setImageURI(Uri.fromFile(
                    File(string)))
                textView!!.text = "Download done"
            } else {
                Toast.makeText(activity.applicationContext, "Download failed",
                    Toast.LENGTH_LONG).show()
                textView!!.text = "Download failed"
            }
        }
    }
}