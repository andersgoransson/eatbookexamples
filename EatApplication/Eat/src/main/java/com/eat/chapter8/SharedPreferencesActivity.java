package com.eat.chapter8;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.view.View;

import com.eat.R;


public class SharedPreferencesActivity extends Activity {

    private Handler mUiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                Integer i = (Integer)msg.obj;
                // Use the result
            }
        }
    };

    private class SharedPreferenceHandler extends Handler {
        private static final String KEY = "key";
        private SharedPreferences mPrefs;
        static final int READ = 1;
        static final int WRITE = 2;

        private SharedPreferenceHandler(Looper looper, SharedPreferences prefs) {
            super(looper);
            this.mPrefs = prefs;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what) {
                case READ:
                    mUiHandler.sendMessage(mUiHandler.obtainMessage(0, mPrefs.getInt(KEY, 0)));
                    break;
                case WRITE:
                    SharedPreferences.Editor editor = mPrefs.edit();
                    editor.putInt(KEY, msg.arg1);
                    editor.commit();
                    break;
            }
        }
    }

    private int i;
    private HandlerThread mHandlerThread;
    private SharedPreferenceHandler mSharedPreferencesHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences);
        mHandlerThread = new HandlerThread("BgThread", Process.THREAD_PRIORITY_BACKGROUND);
        mHandlerThread.start();
        mSharedPreferencesHandler = new SharedPreferenceHandler(mHandlerThread.getLooper(), getSharedPreferences("LocalPrefs", MODE_PRIVATE));
    }

    public void onButtonClickWrite(View v) {
        mSharedPreferencesHandler.sendMessage(mSharedPreferencesHandler.obtainMessage(SharedPreferenceHandler.WRITE, i++));
    }

    public void onButtonClickRead(View v) {
        mSharedPreferencesHandler.sendMessage(mSharedPreferencesHandler.obtainMessage(SharedPreferenceHandler.READ));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandlerThread.quit();
    }
}
