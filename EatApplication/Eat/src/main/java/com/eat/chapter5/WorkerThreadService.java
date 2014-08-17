package com.eat.chapter5;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;


public class WorkerThreadService extends Service {

    private static final String TAG = "WorkerThreadService";
    WorkerThread mWorkerThread;
    Messenger mWorkerMessenger;

    @Override
    public void onCreate() {
        super.onCreate();
        mWorkerThread = new WorkerThread();
        mWorkerThread.start();
    }

    // Worker thread has prepared a looper and handler.
    private void onWorkerPrepared() {
        Log.d(TAG, "onWorkerPrepared");
        mWorkerMessenger = new Messenger(mWorkerThread.mWorkerHandler);
        synchronized(this) {
            notifyAll();
        }
    }

    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        synchronized (this) {
            while (mWorkerMessenger == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    // Empty
                }
            }
        }
        return mWorkerMessenger.getBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWorkerThread.quit();
    }

    private class WorkerThread extends Thread {

        Handler mWorkerHandler;

        @Override
        public void run() {
            Looper.prepare();
            mWorkerHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case 1:
                            try {
                                msg.replyTo.send(Message.obtain(null, msg.what, 0, 0));
                            } catch (RemoteException e) {
                                Log.e(TAG, e.getMessage());
                            }
                            break;
                        case 2:
                            Log.d(TAG, "Message received");
                            break;
                    }

                }
            };
            onWorkerPrepared();
            Looper.loop();
        }

        public void quit() {
            mWorkerHandler.getLooper().quit();
        }
    }
}
