package com.eat.chapter11;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

import com.eat.R;

import java.lang.ref.WeakReference;


public class BoundLocalActivity2 extends Activity {

    private TextView tvStatus;

    private LocalServiceConnection mLocalServiceConnection = new LocalServiceConnection();
    private boolean mIsBound;
    private BoundLocalService2 mBoundLocalService;

    private static class ServiceListener implements BoundLocalService2.OperationListener {

        private WeakReference<BoundLocalActivity2> mWeakActivity;

        public ServiceListener(BoundLocalActivity2 activity) {
            this.mWeakActivity = new WeakReference<BoundLocalActivity2>(activity);
        }

        @Override
        public void onOperationDone(final int someResult) {
            final BoundLocalActivity2 localReferenceActivity = mWeakActivity.get();
            if (localReferenceActivity != null) {
                localReferenceActivity.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                         localReferenceActivity.tvStatus.setText(Integer.toString(someResult));
                    }
                });
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bound_local_service_sync_client);
        tvStatus = (TextView) findViewById(R.id.text_status);

        bindService(new Intent(BoundLocalActivity2.this, BoundLocalService2.class), mLocalServiceConnection, Service.BIND_AUTO_CREATE);
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
            mBoundLocalService.doLongAsyncOperation(new ServiceListener(this));
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