package com.eat.chapter14;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.eat.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class FileActivity extends Activity {

    private static final String TAG = "FileActivity";

    private static int mCount; // A count to append to file names

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
    }

    public void onAddFile(View v) {
        String filename = "testfile" + mCount++ + ".txt";
        File file = new File(this.getFilesDir(), filename);
        OutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(file));
            out.write(new String("Test").getBytes("UTF-8"));
            out.close();
        } catch (Exception e){

        }
        Log.d(TAG, "onAddFile - path = " + file.getAbsolutePath());

    }

    public void onRemoveFiles(View v) {
        removeAllFiles();
    }

    private void removeAllFiles() {
        File dir = getFilesDir();
        File[] files = dir.listFiles();
        for (File file : files) {
            file.delete();
        }
    }
}