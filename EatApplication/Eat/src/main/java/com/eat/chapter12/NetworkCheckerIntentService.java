package com.eat.chapter12;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import com.eat.R;


public class NetworkCheckerIntentService extends IntentService {

    public NetworkCheckerIntentService() {
        super("NetworkCheckerThread");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (isNewNetworkDataAvailable()) {
            addStatusBarNotification();
        }
    }

    private boolean isNewNetworkDataAvailable() {
        // Network request code omitted. Returns dummy result.
        return true;
    }

    private void addStatusBarNotification() {
        Notification.Builder mBuilder =
                new Notification.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("New network data")
                        .setContentText("New data can be downloaded.");

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }


}
