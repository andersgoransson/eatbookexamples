package com.eat.chapter12;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.TextView;

import com.eat.R;


public class AlarmBroadcastActivity extends Activity {

    private static final long ONE_HOUR = 60 * 60 * 1000;
    TextView tvStatus;

    AlarmManager am;

    AlarmReceiver alarmReceiver;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_broadcast);
        tvStatus = (TextView) findViewById(R.id.text_alarm_status);

        alarmReceiver = new AlarmReceiver();
        registerReceiver(alarmReceiver, new IntentFilter("com.eat.alarmreceiver"));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent("com.eat.alarmreceiver"), PendingIntent.FLAG_UPDATE_CURRENT);
        am = (AlarmManager)(this.getSystemService( Context.ALARM_SERVICE ));
        am.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime()
         + ONE_HOUR, ONE_HOUR, pendingIntent);
        tvStatus.setText("Alarm is set");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(alarmReceiver);
    }
}