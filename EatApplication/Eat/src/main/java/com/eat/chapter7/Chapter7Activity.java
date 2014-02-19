package com.eat.chapter7;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eat.R;
import com.eat.chapter5.MessengerOnewayActivity;
import com.eat.chapter5.MessengerTwowayActivity;

public class Chapter7Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter7);
    }

    public void onThreadRetainActivity(View v) {
        startActivity(new Intent(this, ThreadRetainActivity.class));
    }

    public void onThreadRetainFragment(View v) {
        startActivity(new Intent(this, ThreadRetainWithFragmentActivity.class));
    }
}
