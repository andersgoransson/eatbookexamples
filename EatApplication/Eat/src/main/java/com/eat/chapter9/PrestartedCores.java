package com.eat.chapter9;

import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PrestartedCores {


    private static final String TAG = "PrestartedCores";

    public void preloadedQueue() {

        BlockingQueue<Runnable> preloadedQueue = new LinkedBlockingQueue<Runnable>();
        final String[] alphabet = {"Alpha","Beta", "Gamma","Delta","Epsilon","Zeta"};
        for(int i = 0; i < alphabet.length; i++){
            final int j = i;
            preloadedQueue.add(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, alphabet[j]);

                }
            });
        }
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 1, TimeUnit.SECONDS, preloadedQueue);
        executor.prestartAllCoreThreads();
    }
}
