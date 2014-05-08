package com.eat.chapter8;

import android.os.*;
import android.os.Process;

public class MyHandlerThread extends HandlerThread {

    private Handler mHandler;

    public MyHandlerThread() {
        super("MyHandlerThread", Process.THREAD_PRIORITY_BACKGROUND);
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        mHandler = new Handler(getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case 1:
                        // Handle message
                        break;
                    case 2:
                        // Handle message
                        break;
                }
            }
        };
    }

    public void publishedMethod1() {
        mHandler.sendEmptyMessage(1);
    }
    public void publishedMethod2() {
        mHandler.sendEmptyMessage(2);
    }
}
