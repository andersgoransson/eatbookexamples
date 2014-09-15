package com.eat.chapter4;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import com.eat.R;


public class LooperActivity extends Activity {

    LooperThread mLooperThread;

    private static class LooperThread extends Thread {

        public Handler mHandler;

        public void run() {
            Looper.prepare();
            mHandler = new Handler() {
                public void handleMessage(Message msg) {
                    if(msg.what == 0) {
                        doLongRunningOperation();
                    }
                }
            };
            Looper.loop();
        }

        private void doLongRunningOperation() {
            // Add long running operation here.
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looper);
        mLooperThread = new LooperThread();
        mLooperThread.start();
    }

    public void onClick(View v) {
        if (mLooperThread.mHandler != null) {
            Message msg = mLooperThread.mHandler.obtainMessage(0);
            mLooperThread.mHandler.sendMessage(msg);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        mLooperThread.mHandler.getLooper().quit();
    }
}
