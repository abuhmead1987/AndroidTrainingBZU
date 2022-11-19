package com.examples.android.androidtrainingbzu

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.examples.android.androidtrainingbzu.Brodcasts.DownloadFileFinishedReceiver
import com.examples.android.androidtrainingbzu.Services.DownloadService

class StartIntentServiceActivity : AppCompatActivity() {
    private var textView: TextView? = null
    private var receiver: DownloadFileFinishedReceiver? = null
    private val permisionsList = arrayOf(Manifest.permission.INTERNET,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_NETWORK_STATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_intent_service)
        textView = findViewById(R.id.status)
        receiver = DownloadFileFinishedReceiver(this, textView)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!hasPermissions(this, *permisionsList)) {
                ActivityCompat.requestPermissions(this, permisionsList, 1)
            }
        }
    }

    //    @Override
    //    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    //        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    //        switch (requestCode){
    //
    //        }
    //    }
    override fun onResume() {
        super.onResume()
        registerReceiver(receiver, IntentFilter(
            DownloadService.Companion.NOTIFICATION))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

    fun onClick(view: View?) {
        if (hasPermissions(this, *permisionsList)) {
            startDownloadingService()
        }
    }

    private fun startDownloadingService() {
        val intent = Intent(this, DownloadService::class.java)
        // add infos for the service which file to download and where to store
        intent.putExtra(DownloadService.Companion.FILENAME, "downloaded_image.jpg")
        intent.putExtra(DownloadService.Companion.URL,
            "https://images.idgesg.net/images/article/2017/08/android_robot_logo_by_ornecolorada_cc0_via_pixabay1904852_wide-100732483-large.jpg")
        startService(intent)
        textView!!.text = "Service started"
    }

    fun hasPermissions(context: Context?, vararg permissions: String?): Boolean {
        return if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
            ) {
                true
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1)
                false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            true
        }
    }
}