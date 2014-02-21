package com.eat.chapter9;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eat.R;

public class Chapter9Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter9);
    }

    public void onInvokeActivity(View v) {
        startActivity(new Intent(this, InvokeActivity.class));
    }

    public void onECSImageDownloaderActivity(View v) {
        startActivity(new Intent(this, ECSImageDownloaderActivity.class));
    }
}
