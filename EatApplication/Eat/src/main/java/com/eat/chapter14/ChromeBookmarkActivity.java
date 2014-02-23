package com.eat.chapter14;

import android.app.*;
import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.eat.R;

public class ChromeBookmarkActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

    // Definition of bookmark access information.
    public interface ChromeBookmark {
        final static int ID = 1;
        final static Uri URI= Uri.parse("content://com.android.chrome.browser/bookmarks");
        final static String[] PROJECTION = {
                Browser.BookmarkColumns._ID,
                Browser.BookmarkColumns.TITLE,
                Browser.BookmarkColumns.URL
        };
    }

    // AsyncQueryHandler with convenience methods for insertion and deletion of bookmarks.
    public static class ChromeBookmarkAsyncHandler extends AsyncQueryHandler {

        public ChromeBookmarkAsyncHandler(ContentResolver cr) {
            super(cr);
        }

        public void insert(String name, String url) {
            ContentValues cv = new ContentValues();
            cv.put(Browser.BookmarkColumns.BOOKMARK, 1);
            cv.put(Browser.BookmarkColumns.TITLE, name);
            cv.put(Browser.BookmarkColumns.URL, url);
            startInsert(0, null, ChromeBookmark.URI, cv);
        }

        public void delete(String name) {
            String where = Browser.BookmarkColumns.TITLE + "=?";
            String[] args = new String[] { name };
            startDelete(0, null, ChromeBookmark.URI, where, args);
        }
    }


    ListView mListBookmarks;
    SimpleCursorAdapter mAdapter;
    ChromeBookmarkAsyncHandler mChromeBookmarkAsyncHandler;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);
        mListBookmarks = (ListView) findViewById(R.id.list_bookmarks);

        mChromeBookmarkAsyncHandler = new ChromeBookmarkAsyncHandler(getContentResolver());

        initAdapter();

        getLoaderManager().initLoader(ChromeBookmark.ID, null, this);
    }

    private void initAdapter() {
        mAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1, null,
                new String[] { Browser.BookmarkColumns.TITLE },
                new int[] { android.R.id.text1}, 0);
        mListBookmarks.setAdapter(mAdapter);
        mListBookmarks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id) {
                Cursor c = ((SimpleCursorAdapter) adapterView.getAdapter()).getCursor();
                c.moveToPosition(pos);
                int i = c.getColumnIndex(Browser.BookmarkColumns.TITLE);
                mChromeBookmarkAsyncHandler.delete(c.getString(i));
                return true;
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this, ChromeBookmark.URI,
                ChromeBookmark.PROJECTION, null, null,
                Browser.BookmarkColumns.TITLE + " ASC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor newCursor) {
        mAdapter.swapCursor(newCursor);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        mAdapter.swapCursor(null);
    }


    public void onAddBookmark(View v) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        // Remove previous dialogs
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        // Create and show the dialog.
        DialogFragment newFragment = EditBookmarkDialog.newInstance(mChromeBookmarkAsyncHandler);
        newFragment.show(ft, "dialog");
    }
}