package com.eat.chapter9;

import android.util.Log;

import java.util.concurrent.ThreadFactory;

class LowPriorityThreadFactory implements ThreadFactory {

    private static final String TAG = "LowPriorityThreadFactory";
    private static int count = 1;

    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setName("LowPrio " + count++);
        t.setPriority(4);
        t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
        {
            @Override
            public void uncaughtException(Thread t, Throwable e)
            {
                Log.d(TAG, "Thread = " + t.getName() + ", error = " + e.getMessage());
            }
        });
        return t;
    }
}
