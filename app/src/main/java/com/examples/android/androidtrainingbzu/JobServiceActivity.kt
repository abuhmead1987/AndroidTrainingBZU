package com.examples.android.androidtrainingbzu

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.examples.android.androidtrainingbzu.Services.NotificationJobService

class JobServiceActivity : AppCompatActivity() {
    private var mScheduler: JobScheduler? = null

    // Switches for setting job options.
    private var mDeviceIdleSwitch: Switch? = null
    private var mDeviceChargingSwitch: Switch? = null

    // Override deadline seekbar.
    private lateinit var mSeekBar: SeekBar

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_service)
        mDeviceIdleSwitch = findViewById(R.id.idleSwitch)
        mDeviceChargingSwitch = findViewById(R.id.chargingSwitch)
        mSeekBar = findViewById(R.id.seekBar)
        val seekBarProgress = findViewById<TextView>(R.id.seekBarProgress)
        mScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler

        // Updates the TextView with the value from the seekbar.
        mSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                if (i > 0) {
                    seekBarProgress.text = getString(R.string.seconds, i)
                } else {
                    seekBarProgress.setText(R.string.not_set)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

    /**
     * onClick method that schedules the jobs based on the parameters set.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun scheduleJob(view: View?) {
        val networkOptions = findViewById<RadioGroup>(R.id.networkOptions)
        val selectedNetworkID = networkOptions.checkedRadioButtonId
        var selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE
        val seekBarInteger = mSeekBar!!.progress
        val seekBarSet = seekBarInteger > 0
        when (selectedNetworkID) {
            R.id.noNetwork -> selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE
            R.id.anyNetwork -> selectedNetworkOption = JobInfo.NETWORK_TYPE_ANY
            R.id.wifiNetwork -> selectedNetworkOption = JobInfo.NETWORK_TYPE_UNMETERED
        }
        val serviceName = ComponentName(packageName,
            NotificationJobService::class.java.name)
        val builder = JobInfo.Builder(JOB_ID, serviceName)
            .setRequiredNetworkType(selectedNetworkOption)
            .setRequiresDeviceIdle(mDeviceIdleSwitch!!.isChecked)
            .setRequiresCharging(mDeviceChargingSwitch!!.isChecked)
        if (seekBarSet) {
            builder.setOverrideDeadline((seekBarInteger * 1000).toLong())
        }
        val constraintSet = ((selectedNetworkOption
                != JobInfo.NETWORK_TYPE_NONE) || mDeviceChargingSwitch!!.isChecked
                || mDeviceIdleSwitch!!.isChecked
                || seekBarSet)
        if (constraintSet) {
            val myJobInfo = builder.build()
            mScheduler!!.schedule(myJobInfo)
            Toast.makeText(this, R.string.job_scheduled, Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(this, R.string.no_constraint_toast,
                Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * onClick method for cancelling all existing jobs.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun cancelJobs(view: View?) {
        if (mScheduler != null) {
            mScheduler!!.cancelAll()
            mScheduler = null
            Toast.makeText(this, R.string.jobs_canceled, Toast.LENGTH_SHORT)
                .show()
        }
    }

    companion object {
        private const val JOB_ID = 0
    }
}