package com.eat.chapter4;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.eat.R;


public class HandlerCallbackActivity extends Activity implements Handler.Callback {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_callback);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 1:
                msg.what = 11;
                return true;
            default:
                msg.what = 22;
                return false;
        }
    }

    public void onHandlerCallback(View v) {
        Handler handler = new Handler(this) {
            @Override
            public void handleMessage(Message msg) {
                // Process message
            }
        };
        handler.sendEmptyMessage(1);
        handler.sendEmptyMessage(2);
    }
}
