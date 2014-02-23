package com.eat.chapter14;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eat.R;
import com.eat.chapter13.ExpandableContactListActivity;

public class Chapter14Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter14);
    }

    public void onBasicActivity(View v) {
        startActivity(new Intent(this, BasicActivity.class));
    }

    public void onChromeBookmarkActivity(View v) {
        startActivity(new Intent(this, ChromeBookmarkActivity.class));
    }

    public void onContactActivity(View v) {
        startActivity(new Intent(this, ContactActivity.class));
    }

    public void onFileActivity(View v) {
        startActivity(new Intent(this, FileActivity.class));
    }
}
