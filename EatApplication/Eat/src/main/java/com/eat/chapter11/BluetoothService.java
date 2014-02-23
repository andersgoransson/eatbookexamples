package com.eat.chapter11;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;


public class BluetoothService extends Service {

    private static final String TAG = "BluetoothService";
    public static final String COMMAND_KEY = "command_key";
    public static final String COMMAND_START_LISTENING = "command_start_discovery";

    private static final UUID MY_UUID = new UUID(323476234, 34587387);
    private static final String SDP_NAME = "custom_sdp_name";


    private BluetoothAdapter mAdapter;
    private BluetoothServerSocket mServerSocket;
    private boolean mListening = false;
    private Thread listeningThread;


    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mAdapter != null) {
            if (intent.getStringExtra(COMMAND_KEY).equals(COMMAND_START_LISTENING) && mListening == false) {
                startListening();
            }
        }
        return START_REDELIVER_INTENT;
    }

    private void startListening() {
        mListening = true;
        listeningThread = new Thread(new Runnable() {

            @Override
            public void run() {
                BluetoothSocket socket = null;
                try {
                    mServerSocket = mAdapter.listenUsingInsecureRfcommWithServiceRecord(SDP_NAME, MY_UUID);
                    socket = mServerSocket.accept();

                    if (socket != null) {
                        // Handle BT connection
                    }

                } catch (IOException e) {
                    Log.d(TAG, "Server socket closed");
                }
            }
        });
        listeningThread.start();
    }

    private void stopListening() {
        mListening = false;
        try {
            if (mServerSocket != null) {
                mServerSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopListening();
    }
}
