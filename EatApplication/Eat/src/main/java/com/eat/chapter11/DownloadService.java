package com.eat.chapter11;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class DownloadService extends Service {

    private static final String TAG = "DownloadService";

    private ExecutorService mDownloadExecutor;
    private int mCommandCount;

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        mDownloadExecutor = Executors.newFixedThreadPool(5);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        synchronized(this.getClass()) {
            mDownloadExecutor.shutdownNow();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand - intent = " + intent + ", flags = " + flags + ", startId = " + startId);
        synchronized (this) {
            mCommandCount++;
        }
        if (intent != null) {
            downloadFile(intent.getData());
        }
        return START_REDELIVER_INTENT;
    }

    private void downloadFile(final Uri uri) {
        mDownloadExecutor.submit(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Thread started");
                SystemClock.sleep(10000);
                Log.d(TAG, "Thread finished");

                synchronized (this) {
                    if (--mCommandCount <= 0) {
                        stopSelf();
                    }
                }
            }
        });
    }
}
