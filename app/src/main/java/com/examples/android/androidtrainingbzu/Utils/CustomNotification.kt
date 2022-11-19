package com.examples.android.androidtrainingbzu.Utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import com.examples.android.androidtrainingbzu.R
import com.examples.android.androidtrainingbzu.StartNotificationActivity

/**
 * Helper class for showing and canceling custom
 * notifications.
 *
 *
 * This class makes heavy use of the [NotificationCompat.Builder] helper
 * class to create notifications in a backward-compatible way.
 */
object CustomNotification {
    /**
     * The unique identifier for this type of notification.
     */
    private const val NOTIFICATION_TAG = "Custom"

    /**
     * Shows the notification, or updates a previously shown notification of
     * this type, with the given parameters.
     *
     *
     * TODO: Customize this method's arguments to present relevant content in
     * the notification.
     *
     *
     * TODO: Customize the contents of this method to tweak the behavior and
     * presentation of custom notifications. Make
     * sure to follow the
     * [
 * Notification design guidelines](https://developer.android.com/design/patterns/notifications.html) when doing so.
     *
     * @see .cancel
     */
    fun notify(
        context: Context,
        exampleString: String, number: Int,
    ) {
        val res = context.resources

        // This image is used as the notification's large icon (thumbnail) when
        // the notification is collapsed, and as the big picture to show when
        // the notification is expanded.
        val picture = BitmapFactory.decodeResource(res, R.drawable.example_picture)
        val title = res.getString(
            R.string.custom_notification_title_template, exampleString)
        val text = res.getString(
            R.string.custom_notification_placeholder_text_template, exampleString)
        val builder =
            NotificationCompat.Builder(context) // Set appropriate defaults for the notification light, sound,
                // and vibration.
                .setDefaults(Notification.DEFAULT_ALL) // Set required fields, including the small icon, the
                // notification title, and text.
                .setSmallIcon(R.drawable.ic_stat_custom)
                .setContentTitle(title)
                .setContentText(text) // All fields below this line are optional.
                // Use a default priority (recognized on devices running Android
                // 4.1 or later)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Provide a large icon, shown with the notification in the
                // notification drawer on devices running Android 3.0 or later.
                .setLargeIcon(picture) // Set ticker text (preview) information for this notification.
                .setTicker(exampleString) // Show a number. This is useful when stacking notifications of
                // a single type.
                .setNumber(number) // If this notification relates to a past or upcoming event, you
                // should set the relevant time information using the setWhen
                // method below. If this call is omitted, the notification's
                // timestamp will by set to the time at which it was shown.
                // TODO: Call setWhen if this notification relates to a past or
                // upcoming event. The sole argument to this method should be
                // the notification timestamp in milliseconds.
                //.setWhen(...)
                // Set the pending intent to be initiated when the user touches
                // the notification.
                .setContentIntent(
                    PendingIntent.getActivity(
                        context,
                        0,
                        Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com")),
                        PendingIntent.FLAG_UPDATE_CURRENT)) // Show an expanded photo on devices running Android 4.1 or
                // later.
                .setStyle(NotificationCompat.BigPictureStyle().bigPicture(picture)
                    .setBigContentTitle(title)
                    .setSummaryText("Dummy summary text")) // Example additional actions for this notification. These will
                // only show on devices running Android 4.1 or later, so you
                // should ensure that the activity in this notification's
                // content intent provides access to the same actions in
                // another way.
                .addAction(
                    R.drawable.ic_action_stat_share,
                    res.getString(R.string.action_share),
                    PendingIntent.getActivity(
                        context,
                        0,
                        Intent.createChooser(Intent(Intent.ACTION_SEND)
                            .setType("text/plain")
                            .putExtra(Intent.EXTRA_TEXT, "Dummy text"), "Dummy title"),
                        PendingIntent.FLAG_UPDATE_CURRENT))
                .addAction(
                    R.drawable.ic_action_stat_reply,
                    res.getString(R.string.action_reply),
                    null) // Automatically dismiss the notification when it is touched.
                .setAutoCancel(true)
        notify(context, builder)
    }

    private fun notify(context: Context, builder: NotificationCompat.Builder) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val CHANNEL_ID = "my_channel_01"
        val name: CharSequence = "my_channel"
        val Description = "This is my channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = Description
            mChannel.enableLights(true)
            mChannel.lightColor = Color.RED
            mChannel.enableVibration(true)
            mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            mChannel.setShowBadge(false)
            notificationManager.createNotificationChannel(mChannel)
        }
        builder.setChannelId(CHANNEL_ID)
        val resultIntent = Intent(context, StartNotificationActivity::class.java)
        val stackBuilder = TaskStackBuilder.create(context)
        stackBuilder.addParentStack(StartNotificationActivity::class.java)
        stackBuilder.addNextIntent(resultIntent)
        val resultPendingIntent =
            stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(resultPendingIntent)
        notificationManager.notify(10, builder.build())
    }

    /**
     * Cancels any notifications of this type previously shown using
     * [.notify].
     */
    fun cancel(context: Context) {
        val nm = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.cancel(NOTIFICATION_TAG, 0)
        } else {
            nm.cancel(NOTIFICATION_TAG.hashCode())
        }
    }
}