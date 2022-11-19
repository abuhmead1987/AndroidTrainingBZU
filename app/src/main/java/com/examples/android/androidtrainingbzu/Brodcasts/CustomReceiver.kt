package com.examples.android.androidtrainingbzu.Brodcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.widget.Toast
import com.examples.android.androidtrainingbzu.BuildConfig
import com.examples.android.androidtrainingbzu.R

class CustomReceiver : BroadcastReceiver() {
    /**
     * This callback method gets called when the Broadcast Receiver receives a
     * broadcast that it is registered for.
     *
     * @param context The context in which broadcast receiver is running.
     * @param intent The broadcast is delivered in the form of an intent which
     * contains the broadcast action.
     */
    override fun onReceive(context: Context, intent: Intent) {
        val intentAction = intent.action
        if (intentAction != null) {
            var toastMessage = context.getString(R.string.unknown_action)
            when (intentAction) {
                Intent.ACTION_POWER_CONNECTED -> {
                    toastMessage = context.getString(R.string.power_connected)
                    MediaPlayer.create(context, R.raw.s3_charger_connect).start()
                }
                Intent.ACTION_POWER_DISCONNECTED -> toastMessage =
                    context.getString(R.string.power_disconnected)
                ACTION_CUSTOM_BROADCAST -> toastMessage =
                    context.getString(R.string.custom_broadcast_toast)
                else -> {
                    toastMessage = "Other Actions  $intentAction"
                    MediaPlayer.create(context, R.raw.s3_charger_connect).start()
                }
            }
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        // String constant that defines the custom broadcast Action.
        private const val ACTION_CUSTOM_BROADCAST =
            BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST"
    }
}