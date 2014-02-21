package com.eat.chapter9;


import android.util.Log;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskTrackingThreadPool extends ThreadPoolExecutor{

    private static final String TAG = "CustomThreadPool";

    private AtomicInteger mTaskCount = new AtomicInteger(0);

    public TaskTrackingThreadPool() {
        super(3, 3, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        Log.d(TAG, "beforeExecute - thread = " + t.getName());
        mTaskCount.getAndIncrement();
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        Log.d(TAG, "afterExecute - thread = " + Thread.currentThread().getName() + "t = " + t);
        mTaskCount.getAndDecrement();
    }

    @Override
    protected void terminated() {
        super.terminated();
        Log.d(TAG, "terminated - thread = " + Thread.currentThread().getName());
    }

    public int getNbrOfTasks() {
        return mTaskCount.get();
    }
}
