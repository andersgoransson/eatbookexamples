package com.eat.chapter11;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;


public class BoundLocalService extends Service {

    private static final String TAG = "BoundLocalService";
    private final ServiceBinder mBinder = new ServiceBinder();
    private final ServiceImpl mServiceImpl = new ServiceImpl();

    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class ServiceBinder extends Binder {
        ServiceImpl getServiceImpl() {
            return mServiceImpl;
        }
    }

    public class ServiceImpl {
        public int method1() {
            Log.d(TAG, "method1");
            return 99;
        }
    }
}
