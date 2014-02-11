package com.eat.chapter4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eat.R;

public class Chapter4Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter4);
    }

    public void onPipeActivity(View v) {
        startActivity(new Intent(this, PipeExampleActivity.class));
    }

    public void onLooperActivity(View v) {
        startActivity(new Intent(this, LooperActivity.class));
    }

    public void onConsumeAndQuitThreadActivity(View v) {
        startActivity(new Intent(this, ConsumeAndQuitThreadActivity.class));
    }

    public void onHandlerExampleActivity(View v) {
        startActivity(new Intent(this, HandlerExampleActivity.class));
    }

    public void onHandlerCallbackActivity(View v) {
        startActivity(new Intent(this, HandlerCallbackActivity.class));
    }

    public void onMQDebugActivity(View v) {
        startActivity(new Intent(this, MQDebugActivity.class));
    }
}
