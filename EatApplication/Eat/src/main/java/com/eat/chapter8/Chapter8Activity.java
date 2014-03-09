package com.eat.chapter8;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eat.R;

public class Chapter8Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter8);
    }

    public void onSharedPreferencesActivity(View v) {
        startActivity(new Intent(this, SharedPreferencesActivity.class));
    }

    public void onChainedNetworkActivity(View v) {
        startActivity(new Intent(this, ChainedNetworkActivity.class));
    }
}
