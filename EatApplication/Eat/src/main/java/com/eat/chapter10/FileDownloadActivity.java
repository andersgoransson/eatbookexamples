package com.eat.chapter10;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.eat.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


public class FileDownloadActivity extends Activity {

    private static final String[] DOWNLOAD_URLS = {
            "http://developer.android.com/design/media/devices_displays_density@2x.png",
            "http://developer.android.com/design/media/iconography_launcher_example2.png",
            "http://developer.android.com/design/media/iconography_actionbar_focal.png",
            "http://developer.android.com/design/media/iconography_actionbar_colors.png"
    };

    DownloadTask mFileDownloaderTask;

    // Views from layout file
    private ProgressBar mProgressBar;
    private LinearLayout mLayoutImages;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_file_download);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBar.setMax(DOWNLOAD_URLS.length);
        mLayoutImages = (LinearLayout) findViewById(R.id.layout_images);

        mFileDownloaderTask = new DownloadTask(this);
        mFileDownloaderTask.execute(DOWNLOAD_URLS);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFileDownloaderTask.setActivity(null);
        mFileDownloaderTask.cancel(true);
    }

    private static class DownloadTask extends AsyncTask<String, Bitmap, Void> {

        private FileDownloadActivity mActivity;
        private int mCount = 0;

        public DownloadTask(FileDownloadActivity activity) {
            mActivity = activity;
        }

        public void setActivity(FileDownloadActivity activity) {
            mActivity = activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mActivity.mProgressBar.setVisibility(View.VISIBLE);
            mActivity.mProgressBar.setProgress(0);
        }

        @Override
        protected Void doInBackground(String... urls) {
            for (String url : urls) {
                if (!isCancelled()) {
                    Bitmap bitmap = downloadFile(url);
                    publishProgress(bitmap);
                }
            }
            return null;
        }


        @Override
        protected void onProgressUpdate(Bitmap... bitmaps) {
            super.onProgressUpdate(bitmaps);
            if (mActivity != null)  {
                mActivity.mProgressBar.setProgress(++mCount);
                ImageView iv = new ImageView(mActivity);
                iv.setImageBitmap(bitmaps[0]);
                mActivity.mLayoutImages.addView(iv);
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (mActivity != null) {
                mActivity.mProgressBar.setVisibility(View.GONE);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            if (mActivity != null) {
                mActivity.mProgressBar.setVisibility(View.GONE);
            }
        }


        private Bitmap downloadFile(String url) {
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory
                        .decodeStream((InputStream) new URL(url)
                                .getContent());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

    }
}