package com.eat.chapter11;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eat.R;


public class BluetoothActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
    }

    public void onStartListening(View v) {
        Intent intent = new Intent(this, BluetoothService.class);
        intent.putExtra(BluetoothService.COMMAND_KEY, BluetoothService.COMMAND_START_LISTENING);
        startService(intent);
    }

    public void onStopListening(View v) {
        Intent intent = new Intent(this, BluetoothService.class);
        stopService(intent);
    }
}