package com.eat.chapter9;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.eat.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;


public class InvokeActivity extends Activity {


    private static final String TAG = "InvokeActivity";

    private TextView textStatus;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoke);
        textStatus = (TextView) findViewById(R.id.text_status);
    }


    public void onButtonClick(View v) {

        SimpleExecutor simpleExecutor = new SimpleExecutor();
        simpleExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<Callable<String>> tasks = new ArrayList<Callable<String>>();
                tasks.add(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return getFirstPartialDataFromNetwork();
                    }
                });
                tasks.add(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return getSecondPartialDataFromNetwork();
                    }
                });

                ThreadPoolExecutor executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(2);
                try {
                    Log.d(TAG, "invokeAll");
                    List<Future<String>> futures = executor.invokeAll(tasks);
                    Log.d(TAG, "invokeAll after");

                    final String mashedData = mashupResult(futures);

                    textStatus.post(new Runnable() {
                        @Override
                        public void run() {
                            textStatus.setText(mashedData);
                        }
                    });
                    Log.d(TAG, "mashedData = " + mashedData);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                executor.shutdown();
            }
        });
    }

    private String getFirstPartialDataFromNetwork() {
        Log.d(TAG, "ProgressReportingTask 1 started");
        SystemClock.sleep(10000);
        Log.d(TAG, "ProgressReportingTask 1 done");
        return "MockA";
    }

    private String getSecondPartialDataFromNetwork() {
        Log.d(TAG, "ProgressReportingTask 2 started");
        SystemClock.sleep(2000);
        Log.d(TAG, "ProgressReportingTask 2 done");
        return "MockB";
    }

    private String mashupResult(List<Future<String>> futures) throws ExecutionException, InterruptedException {
        StringBuilder builder = new StringBuilder();
        for (Future<String> future : futures) {
            builder.append(future.get());
        }
        return builder.toString();
    }
}