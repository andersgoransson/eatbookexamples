package com.eat.chapter14;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import java.util.Random;

public class BasicLoader extends AsyncTaskLoader<Integer>{

    private static final String TAG = "BasicLoader";

    public BasicLoader(Context context) {
        super(context);
    }

    @Override
    protected boolean onCancelLoad() {
        Log.d(TAG, "onCancelLoad");
        return super.onCancelLoad();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public Integer loadInBackground() {
       return loadData();
    }

    private int loadData() {
        SystemClock.sleep(3000);
        Random rand = new Random();
        int data = rand.nextInt(50);
        Log.d(TAG, "loadData - data = " + data);
        return data;
    }
}
