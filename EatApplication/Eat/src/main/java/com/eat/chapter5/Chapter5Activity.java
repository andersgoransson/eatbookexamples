package com.eat.chapter5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eat.R;

public class Chapter5Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter5);
    }

    public void onMessengerOnewayActivity(View v) {
        startActivity(new Intent(this, MessengerOnewayActivity.class));
    }

    public void onMessengerTwowayActivity(View v) {
        startActivity(new Intent(this, MessengerTwowayActivity.class));
    }
}
