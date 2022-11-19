package com.examples.android.androidtrainingbzu

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.examples.android.androidtrainingbzu.Brodcasts.AlarmReceiver

class AlarmActivity : AppCompatActivity() {
    private var mNotificationManager: NotificationManager? = null
    var alarmManager: AlarmManager? = null
    var notifyPendingIntent: PendingIntent? = null

    /**
     * Initializes the activity.
     *
     * @param savedInstanceState The current state data.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)
        mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val alarmToggle = findViewById<CheckBox>(R.id.alarmToggle)

        // Set up the Notification Broadcast Intent.
        val notifyIntent = Intent(this, AlarmReceiver::class.java)
        val alarmUp = PendingIntent.getBroadcast(this, NOTIFICATION_ID,
            notifyIntent, PendingIntent.FLAG_NO_CREATE) != null
        alarmToggle.isChecked = alarmUp
        notifyPendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID, notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        // Set the click listener for the toggle button.
        alarmToggle.setOnCheckedChangeListener { buttonView, isChecked ->
            val toastMessage: String
            toastMessage = if (isChecked) {
                val repeatInterval: Long = 1 // AlarmManager.INTERVAL_FIFTEEN_MINUTES;
                val triggerTime = (SystemClock.elapsedRealtime()
                        + repeatInterval)

                // If the Toggle is turned on, set the repeating alarm with
                // a 1 minute interval.
                if (alarmManager != null) {
                    alarmManager!!.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                        triggerTime, repeatInterval,
                        notifyPendingIntent)
                }
                // Set the toast message for the "on" case.
                getString(R.string.alarm_on_toast)
            } else {
                // Cancel notification if the alarm is turned off.
                mNotificationManager!!.cancelAll()
                if (alarmManager != null) {
                    alarmManager!!.cancel(notifyPendingIntent)
                }
                // Set the toast message for the "off" case.
                getString(R.string.alarm_off_toast)
            }

            // Show a toast to say the alarm is turned on or off.
            Toast.makeText(this@AlarmActivity, toastMessage,
                Toast.LENGTH_SHORT).show()
        }

        // Create the notification channel.
        createNotificationChannel()
    }

    /**
     * Creates a Notification channel, for OREO and higher.
     */
    fun createNotificationChannel() {

        // Create a notification manager object.
        mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.O
        ) {

            // Create the NotificationChannel with all the parameters.
            val notificationChannel = NotificationChannel(PRIMARY_CHANNEL_ID,
                "Stand up notification",
                NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Notifies every 15 minutes to " +
                    "stand up and walk"
            mNotificationManager!!.createNotificationChannel(notificationChannel)
        }
    }

    companion object {
        // Notification ID.
        private const val NOTIFICATION_ID = 0

        // Notification channel ID.
        private const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
    }
}