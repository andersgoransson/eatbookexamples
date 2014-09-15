package com.eat.chapter4;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.LogPrinter;
import android.view.View;

import com.eat.R;


public class MQDebugActivity extends Activity {

    private static final String TAG = "EAT";
    Handler mWorkerHandler;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mq_debug);

        Thread t = new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                mWorkerHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        Log.d(TAG, "handleMessage - what = " + msg.what);
                    }
                };
                Looper.loop();
            }
        };
        t.start();
    }

    // Called on button click, i.e. from the UI thread.
    public void onClick(View v) {
        mWorkerHandler.sendEmptyMessageDelayed(1, 2000);
        mWorkerHandler.sendEmptyMessage(2);
        mWorkerHandler.obtainMessage(3, 0, 0, new Object()).sendToTarget();
        mWorkerHandler.sendEmptyMessageDelayed(4, 300);
        mWorkerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Execute");
            }
        }, 400);
        mWorkerHandler.sendEmptyMessage(5);

        mWorkerHandler.dump(new LogPrinter(Log.DEBUG, TAG), "");
    }
}