package com.eat.chapter11;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import java.util.concurrent.Executor;


public class BoundLocalService2 extends Service {


    private static final String TAG = "BoundLocalService";
    private final ServiceBinder mBinder = new ServiceBinder();
    private final TaskExecutor executor = new TaskExecutor();

    public interface OperationListener {
        public void onOperationDone(int i);
    }

    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind - this = " + this);
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate - this = " + this);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged  - this = " + this);
    }

    public class ServiceBinder extends Binder {
        public BoundLocalService2 getService() {
            return BoundLocalService2.this;
        }
    }


    public int doLongSyncOperation() {
        Log.d(TAG, "onConfigurationChanged  - this = " + this);
        return longOperation();
    }

    public void doLongAsyncOperation(final OperationListener listener) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                int result = longOperation();
                listener.onOperationDone(result);
            }
        });
    }

    private int longOperation() {
        SystemClock.sleep(10000);
        return 42;
    }

    public class TaskExecutor implements Executor {
        @Override
        public void execute(Runnable runnable) {
            new Thread(runnable).start();
        }
    }
}
