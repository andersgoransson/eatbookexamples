package com.eat.chapter5;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;

import com.eat.R;


/**
 * Activity passing messages to remote service via Messenger.
 */
public class MessengerOnewayActivity extends Activity {

    private boolean mBound = false;
    private Messenger mRemoteService = null;

    private ServiceConnection mRemoteConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder service) {
            mRemoteService = new Messenger(service);
            mBound = true;
        }

        public void onServiceDisconnected(ComponentName className) {
            mRemoteService = null;
            mBound = false;
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger_client);
    }

    public void onBindClick(View v) {
        Intent intent = new Intent("com.eat.chapter5.ACTION_BIND");
        bindService(intent, mRemoteConnection, Context.BIND_AUTO_CREATE);

    }

    public void onUnbindClick(View v) {
        if (mBound) {
            unbindService(mRemoteConnection);
            mBound = false;
        }

    }

    public void onSendClick(View v) {
        if (mBound) {
            try {
                mRemoteService.send(Message.obtain(null, 2, 0, 0));
            } catch (RemoteException e) {
                // Empty
            }
        }
    }
}