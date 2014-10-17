package com.eat.chapter12;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.View;

import com.eat.R;

public class WebServiceActivity extends Activity {

    private final static String TAG = WebServiceActivity.class.getSimpleName();
    private final static String getUrl = "http://dn.se"; // Dummy
    private final static String postUrl = "http://dn.se"; // Dummy

    private ResultReceiver mReceiver;

    public WebServiceActivity() {
        mReceiver = new ResultReceiver(new Handler()) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                int httpStatus = resultCode;
                String jsonResult = null;
                if (httpStatus == 200) { // OK
                    if (resultData != null) {
                        jsonResult= resultData.getString(WebService.BUNDLE_KEY_REQUEST_RESULT);
                        // Omitted: Handle response
                    }
                }
                else {
                    // Handle error
                }
            }

        };
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service);
    }

    public void doPost(View v) {
        Intent intent = new Intent(this, WebService.class);
        intent.setData(Uri.parse(postUrl));
        intent.putExtra(WebService.INTENT_KEY_REQUEST_TYPE, WebService.POST);
        intent.putExtra(WebService.INTENT_KEY_JSON, "{\"foo\":\"bar\"}");
        intent.putExtra(WebService.INTENT_KEY_RECEIVER, mReceiver);
        startService(intent);
    }

    public void doGet(View v) {
        Intent intent = new Intent(this, WebService.class);
        intent.setData(Uri.parse(getUrl));
        intent.putExtra(WebService.INTENT_KEY_REQUEST_TYPE, WebService.GET);
        intent.putExtra(WebService.INTENT_KEY_RECEIVER, mReceiver);
        startService(intent);
    }

}