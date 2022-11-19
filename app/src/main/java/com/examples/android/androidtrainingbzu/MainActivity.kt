package com.examples.android.androidtrainingbzu

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.examples.android.androidtrainingbzu.ActivityLifcycle
import com.examples.android.androidtrainingbzu.AlarmActivity
import com.examples.android.androidtrainingbzu.BroadcastActivity
import com.examples.android.androidtrainingbzu.ForResultsActivity
import com.examples.android.androidtrainingbzu.FormCheckActivity
import com.examples.android.androidtrainingbzu.ImplicitActivity
import com.examples.android.androidtrainingbzu.JobServiceActivity
import com.examples.android.androidtrainingbzu.LanguageAndSharedPreferencesActivity
import com.examples.android.androidtrainingbzu.StartNotificationActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startNewActivity(view: View) {
        when (view.id) {
            R.id.btn_startLongActivity -> startActivity(Intent(this, LongTextActivity::class.java))
            R.id.btn_startLifecycleActivity -> startActivity(Intent(this,
                ActivityLifcycle::class.java))
            R.id.btn_startActivityResul -> startActivity(Intent(this,
                ForResultsActivity::class.java))
            R.id.btn_startimplicitActivity -> startActivity(Intent(this,
                ImplicitActivity::class.java))
            R.id.btn_startView1Activity -> startActivity(Intent(this, ViewsActivity_1::class.java))
            R.id.btn_startWebviewActivity -> startActivity(Intent(this,
                WebviewActivity::class.java))
            R.id.btn_startDialogsActivity -> startActivity(Intent(this,
                DialogsActivity::class.java))
            R.id.btn_startScaleGestureDetectorActivity -> startActivity(Intent(this,
                ScaleGestureDetectorActivity::class.java))
            R.id.btn_startMotionEventActivity -> startActivity(Intent(this,
                MotionEventActivity::class.java))
            R.id.btn_startMenuActivity -> startActivity(Intent(this, MenusActivity::class.java))
            R.id.btn_startTabsActivity -> startActivity(Intent(this, TabsActivity::class.java))
            R.id.btn_startLoadImagesActivity -> startActivity(Intent(this,
                LoadImagesAsyncAndLoaderActivity::class.java))
            R.id.btn_startChecFormkActivity -> startActivity(Intent(this,
                FormCheckActivity::class.java))
            R.id.btn_startCustomBrodcastActivity -> startActivity(Intent(this,
                BroadcastActivity::class.java))
            R.id.btn_startCustomDownloadManagerActivity -> startActivity(Intent(this,
                CustomDownloadManagerActivity::class.java))
            R.id.btn_downloadFileUsingService -> startActivity(Intent(this,
                StartIntentServiceActivity::class.java))
            R.id.btn_JobServiceActivity -> startActivity(Intent(this,
                JobServiceActivity::class.java))
            R.id.btn_openNotification -> startActivity(Intent(this,
                StartNotificationActivity::class.java))
            R.id.btn_AlarmActivity -> startActivity(Intent(this, AlarmActivity::class.java))
            R.id.btn_languageAndSharedPreferencesActivity -> startActivity(Intent(this,
                LanguageAndSharedPreferencesActivity::class.java))
            R.id.btn_startRecyclerViewActivity -> startActivity(Intent(this,
                HR_MainActivity::class.java))
        }
    }
}