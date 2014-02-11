package com.eat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eat.chapter10.Chapter10Activity;
import com.eat.chapter11.Chapter11Activity;
import com.eat.chapter12.Chapter12Activity;
import com.eat.chapter13.Chapter13Activity;
import com.eat.chapter14.Chapter14Activity;
import com.eat.chapter4.Chapter4Activity;
import com.eat.chapter5.Chapter5Activity;
import com.eat.chapter6.Chapter6Activity;
import com.eat.chapter7.Chapter7Activity;
import com.eat.chapter8.Chapter8Activity;
import com.eat.chapter9.Chapter9Activity;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void onChapter1(View v) {
        // Empty, no examples in this chapter.
    }

    public void onChapter2(View v) {
        // Empty, no examples in this chapter.
    }

    public void onChapter3(View v) {
        // Empty, no examples in this chapter.
    }

    public void onChapter4(View v) {
        startActivity(new Intent(this, Chapter4Activity.class));
    }

    public void onChapter5(View v) {
        startActivity(new Intent(this, Chapter5Activity.class));
    }

    public void onChapter6(View v) {
        startActivity(new Intent(this, Chapter6Activity.class));
    }

    public void onChapter7(View v) {
        startActivity(new Intent(this, Chapter7Activity.class));
    }

    public void onChapter8(View v) {
        startActivity(new Intent(this, Chapter8Activity.class));
    }

    public void onChapter9(View v) {
        startActivity(new Intent(this, Chapter9Activity.class));
    }

    public void onChapter10(View v) {
        startActivity(new Intent(this, Chapter10Activity.class));
    }

    public void onChapter11(View v) {
        startActivity(new Intent(this, Chapter11Activity.class));
    }

    public void onChapter12(View v) {
        startActivity(new Intent(this, Chapter12Activity.class));
    }

    public void onChapter13(View v) {
        startActivity(new Intent(this, Chapter13Activity.class));
    }

    public void onChapter14(View v) {
        startActivity(new Intent(this, Chapter14Activity.class));
    }
}
