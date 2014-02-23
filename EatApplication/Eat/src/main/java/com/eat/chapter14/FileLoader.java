package com.eat.chapter14;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.FileObserver;
import android.util.Log;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * 1. Data observation
 * 2. Load data on background thread when the content has changed.
 * 3. Cache data
 * 4. Only deliver data when started.
 * 5. Release resources on reset.
 * 6. Cancel load.
 * 7. Handle deliverResult
 */
public class FileLoader extends AsyncTaskLoader<List<String>> {
    // Cache the list of file names.
    private List<String> mFileNames;


    // 1. Data observation
    private class SdCardObserver extends FileObserver {

        public SdCardObserver(String path) {
            super(path, FileObserver.CREATE|FileObserver.DELETE);
        }

        @Override
        public void onEvent(int event, String path) {
            // Report that a content change has occurred.
            // This call will force a new asynchronous data load if the loader is started
            // otherwise it will keep a reference that the data has changed for future loads.
            onContentChanged();
        }
    }

    private SdCardObserver mSdCardObserver;

    public FileLoader(Context context) {
        super(context);
        String path = context.getFilesDir().getPath();
        mSdCardObserver = new SdCardObserver(path);
    }



    /**
     * Decide whether a load should be initiated or not.
     */
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        mSdCardObserver.startWatching();

        if (mFileNames != null) {
            // Return the cache
            deliverResult(mFileNames);
        }

        // Force a data load if there are no previous data
        // or if the content has been marked as changed earlier but not delivered.
        if (takeContentChanged() || mFileNames == null) {
            forceLoad();
        }
    }

    @Override
    public List<String> loadInBackground() {
        File directory = getContext().getFilesDir();
        return Arrays.asList(directory.list());
    }

    @Override
    public void deliverResult(List<String> data) {
        if (isReset()) {
            return;
        }

        // Cache the data
        mFileNames = data;

        // Only deliver result if the loader is started.
        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        mSdCardObserver.stopWatching();
        clearResources();
    }

    private void clearResources() {
       mFileNames = null;
    }
}
