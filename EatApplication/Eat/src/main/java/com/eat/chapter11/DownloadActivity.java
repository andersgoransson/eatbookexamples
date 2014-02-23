package com.eat.chapter11;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.eat.R;


public class DownloadActivity extends Activity {

    private static final String DOWNLOAD_URL = "http://developer.android.com/design/media/devices_displays_density@2x.png";


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_service);
    }

    public void onStartDownload(View v) {
        Intent intent = new Intent("com.eat.ACTION_DOWNLOAD");
        intent.setData(Uri.parse(DOWNLOAD_URL));
        startService(intent);
    }

    public void onStopService(View v) {
        Intent intent = new Intent(this, DownloadService.class);
        stopService(intent);
    }
}