package com.eat.chapter7;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import com.eat.R;


public class ThreadRetainActivity extends Activity {

    private static class MyThread extends Thread {
        private ThreadRetainActivity mActivity;

        public MyThread(ThreadRetainActivity activity) {
            mActivity = activity;
        }

        private void attach(ThreadRetainActivity activity) {
            mActivity = activity;
        }

        @Override
        public void run() {
            final String text = getTextFromNetwork();
            mActivity.setText(text);
        }

        // Long operation
        private String getTextFromNetwork() {
            // Simulate network operation
            SystemClock.sleep(5000);
            return "Text from network";
        }
    }

    private static MyThread t;
    private TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retain_thread);
        textView = (TextView) findViewById(R.id.text_retain);
        Object retainedObject = getLastNonConfigurationInstance();
        if (retainedObject != null) {
            t = (MyThread) retainedObject;
            t.attach(this);
        }
    }

    @Override

    public Object onRetainNonConfigurationInstance() {
        if (t != null && t.isAlive()) {
            return t;
        }
        return null;
    }

    public void onStartThread(View v) {
        t = new MyThread(this);
        t.start();
    }

    private void setText(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(text);
            }
        });
    }
}