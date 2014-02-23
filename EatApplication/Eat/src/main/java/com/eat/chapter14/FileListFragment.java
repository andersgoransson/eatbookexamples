package com.eat.chapter14;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class FileListFragment extends ListFragment implements
        LoaderManager.LoaderCallbacks<List<String>>{

    private static final int FILE_LOADER_ID = 1;

    private ArrayAdapter<String> mFileAdapter;
    private List<String> mFileNames = new ArrayList<String>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(FILE_LOADER_ID, null, this);
        setEmptyText("No files in directory");
        setListShown(false);

        mFileAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, mFileNames);
        mFileAdapter.setNotifyOnChange(true);
        setListAdapter(mFileAdapter);
    }

    @Override
    public Loader<List<String>> onCreateLoader(int i, Bundle bundle) {
        return new FileLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<String>> fileLoader,
                               List<String> fileNames) {
        mFileAdapter.clear();
        mFileAdapter.addAll(fileNames);
        setListShown(true);
    }

    @Override
    public void onLoaderReset(Loader<List<String>> fileLoader) {
        mFileNames = null;
        mFileAdapter.clear();
    }
}
