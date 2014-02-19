package com.eat.chapter7;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.eat.R;


public class ThreadRetainWithFragmentActivity extends Activity {

    private static final String TAG = "ThreadRetainActivity";

    private static final String KEY_TEXT = "key_text";

    private ThreadFragment mThreadFragment;

    private TextView mTextView;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retain_thread);
        mTextView = (TextView) findViewById(R.id.text_retain);

        FragmentManager manager = getFragmentManager();
        mThreadFragment = (ThreadFragment) manager.findFragmentByTag("threadfragment");

        if (mThreadFragment == null) {
            FragmentTransaction transaction = manager.beginTransaction();
            mThreadFragment = new ThreadFragment();
            transaction.add(mThreadFragment, "threadfragment");
            transaction.commit();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mTextView.setText(savedInstanceState.getString(KEY_TEXT));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_TEXT, (String)mTextView.getText());
    }

    // Method called to start a worker thread
    public void onStartThread(View v) {
        mThreadFragment.execute();
    }

    public void setText(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextView.setText(text);
            }
        });
    }
}