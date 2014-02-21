package com.eat.chapter9;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.eat.R;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


public class ECSImageDownloaderActivity extends Activity {

    private static final String TAG = "ECSImageDownloaderActivity";

    private LinearLayout layoutImages;


    private class ImageDownloadTask implements Callable<Bitmap> {

        @Override
        public Bitmap call() throws Exception {
            return downloadRemoteImage();
        }

        private Bitmap downloadRemoteImage() {
            SystemClock.sleep((int) (5000f - new Random().nextFloat() * 5000f));
            return BitmapFactory.decodeResource(ECSImageDownloaderActivity.this.getResources(), R.drawable.ic_launcher);
        }
    }

    private class DownloadCompletionService extends ExecutorCompletionService {

        private ExecutorService mExecutor;

        public DownloadCompletionService(ExecutorService executor) {
            super(executor);
            mExecutor = executor;
        }

        public void shutdown() {
            mExecutor.shutdown();
        }

        public boolean isTerminated() {
            return mExecutor.isTerminated();
        }
    }

    private class ConsumerThread extends Thread {

        private DownloadCompletionService mEcs;

        private ConsumerThread(DownloadCompletionService ecs) {
            this.mEcs = ecs;
        }

        @Override
        public void run() {
            super.run();
            try {
                while(!mEcs.isTerminated()) {
                    Future<Bitmap> future = mEcs.poll(1, TimeUnit.SECONDS);
                    if (future != null) {
                        addImage(future.get());
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecs_image_downloader);
        layoutImages = (LinearLayout) findViewById(R.id.layout_images);

        DownloadCompletionService ecs = new DownloadCompletionService(Executors.newCachedThreadPool());
        new ConsumerThread(ecs).start();

        for (int i = 0; i < 5; i++) {
            ecs.submit(new ImageDownloadTask());
        }

        ecs.shutdown();
    }


    private void addImage(final Bitmap image) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ImageView iv = new ImageView(ECSImageDownloaderActivity.this);
                iv.setImageBitmap(image);
                layoutImages.addView(iv);
            }
        });
    }
}