package com.eat.chapter11;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.eat.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class BoundLocalActivity2 extends Activity {

    private TextView tvStatus;

    private final LocalServiceConnection mLocalServiceConnection = new LocalServiceConnection();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private boolean mIsBound;
    private BoundLocalService2 mBoundLocalService;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            tvStatus.setText(Integer.toString(msg.what));
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bound_local_service_async_client);
        tvStatus = (TextView) findViewById(R.id.text_status);


        tvStatus.setText("Bind to Service");
        bindService(new Intent(BoundLocalActivity2.this, BoundLocalService.class), mLocalServiceConnection, Service.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mIsBound) {
            try {
                unbindService(mLocalServiceConnection);
                mIsBound = false;
            } catch (IllegalArgumentException e) {
                // No bound service
            }
        }
    }


    public void onClickExecuteOnClientUIThread(View v) {
        if (mBoundLocalService != null) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    int i = mBoundLocalService.doLongSyncOperation();
                    handler.sendEmptyMessage(i);
                }
            });
        }
    }

    private class LocalServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBoundLocalService = ((BoundLocalService2.ServiceBinder)iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBoundLocalService = null;
        }
    }
}