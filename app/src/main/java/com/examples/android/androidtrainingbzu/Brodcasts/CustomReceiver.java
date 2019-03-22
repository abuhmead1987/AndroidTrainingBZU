package com.examples.android.androidtrainingbzu.Brodcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.examples.android.androidtrainingbzu.BuildConfig;
import com.examples.android.androidtrainingbzu.R;

public class CustomReceiver extends BroadcastReceiver {

    // String constant that defines the custom broadcast Action.
    private static final String ACTION_CUSTOM_BROADCAST =
            BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";

    /**
     * This callback method gets called when the Broadcast Receiver receives a
     * broadcast that it is registered for.
     *
     * @param context The context in which broadcast receiver is running.
     * @param intent The broadcast is delivered in the form of an intent which
     *               contains the broadcast action.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();

        if (intentAction != null) {
            String toastMessage = context.getString(R.string.unknown_action);
            switch (intentAction){
                case Intent.ACTION_POWER_CONNECTED:
                    toastMessage = context.getString(R.string.power_connected);
                    MediaPlayer.create(context, R.raw.s3_charger_connect).start();
                    break;
                case Intent.ACTION_POWER_DISCONNECTED:
                    toastMessage =
                            context.getString(R.string.power_disconnected);
                    break;
                case ACTION_CUSTOM_BROADCAST:
                    toastMessage =
                            context.getString(R.string.custom_broadcast_toast);
                    break;
                default:
                    toastMessage = "Other Actions  "+intentAction;
                    MediaPlayer.create(context, R.raw.s3_charger_connect).start();
                    break;
            }
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
        }
    }
}
