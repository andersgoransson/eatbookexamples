package com.eat.chapter12;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eat.R;
import com.eat.chapter11.BoundLocalActivity;
import com.eat.chapter11.BoundLocalActivity2;

public class Chapter12Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter12);
    }

    public void onAlarmBroadcastActivity(View v) {
        startActivity(new Intent(this, AlarmBroadcastActivity.class));
    }

    public void onWebServiceActivity(View v) {
        startActivity(new Intent(this, WebServiceActivity.class));
    }
}
