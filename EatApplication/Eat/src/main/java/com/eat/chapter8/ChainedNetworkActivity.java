package com.eat.chapter8;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.*;


public class ChainedNetworkActivity extends Activity {

    private static final int DIALOG_LOADING = 0;

    private static final int SHOW_LOADING = 1;
    private static final int DISMISS_LOADING = 2;

    Handler dialogHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHOW_LOADING:
                    showDialog(DIALOG_LOADING);
                    break;
                case DISMISS_LOADING:
                    dismissDialog(DIALOG_LOADING);
            }
        }
    };

    private class BackgroundHandler extends Handler {
        public static final int STATE_A = 1;

        public static final int STATE_B = 2;

        public BackgroundHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case STATE_A:
                    dialogHandler.sendEmptyMessage(SHOW_LOADING);
                    String result = networkOperation1();
                    if (result != null) {
                        sendMessage(obtainMessage(STATE_B, result));
                    } else {
                        dialogHandler.sendEmptyMessage(DISMISS_LOADING);
                    }
                    break;
                case STATE_B:
                    networkOperation2((String) msg.obj);
                    dialogHandler.sendEmptyMessage(DISMISS_LOADING);
                    break;
            }
        }
    }

    private BackgroundHandler stateHandler;
    private HandlerThread handlerThread;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handlerThread = new HandlerThread("state-handlerthread");
        handlerThread.start();
        stateHandler = new BackgroundHandler(handlerThread.getLooper());
        stateHandler.sendEmptyMessage(BackgroundHandler.STATE_A);

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case DIALOG_LOADING:
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Loading...");
            dialog = builder.create();
            break;
        }
        return dialog;
    }

    private String networkOperation1() {
        SystemClock.sleep(2000);
        return "A string";
    }

    private void networkOperation2(String input) {
        SystemClock.sleep(2000);
    }
}