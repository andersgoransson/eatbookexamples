package com.eat.chapter12;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;

public class WebService extends IntentService {
    private static final String TAG = WebService.class.getName();
    public static final int GET = 1;
    public static final int POST = 2;

    public static final String INTENT_KEY_REQUEST_TYPE = "com.eat.INTENT_KEY_REQUEST_TYPE";
    public static final String INTENT_KEY_JSON = "com.eat.INTENT_KEY_JSON";
    public static final String INTENT_KEY_RECEIVER = "com.eat.INTENT_KEY_RECEIVER";
    public static final String BUNDLE_KEY_REQUEST_RESULT = "com.eat.BUNDLE_KEY_REQUEST_RESULT";

    public WebService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Uri uri = intent.getData();
        int requestType = intent.getIntExtra(INTENT_KEY_REQUEST_TYPE, 0);
        String json = (String)intent.getSerializableExtra(INTENT_KEY_JSON);
        ResultReceiver receiver = intent.getParcelableExtra(INTENT_KEY_RECEIVER);

        try {
            HttpRequestBase request = null;
            switch (requestType) {
                case GET: {
                    request = new HttpGet();
                    // Request setup omitted
                    break;
                }
                case POST: {
                    request = new HttpPost();
                    if (json != null) {
                        ((HttpPost)request).setEntity(new StringEntity(json));
                    }
                    // Request setup omitted
                    break;
                }
            }

            if (request != null) {
                request.setURI(new URI(uri.toString()));
                HttpResponse response = doRequest(request);
                HttpEntity httpEntity = response.getEntity();
                StatusLine responseStatus = response.getStatusLine();
                int statusCode = responseStatus != null ? responseStatus.getStatusCode() : 0;

                if (httpEntity != null) {
                    Bundle resultBundle = new Bundle();
                    resultBundle.putString(BUNDLE_KEY_REQUEST_RESULT, EntityUtils.toString(httpEntity));
                    receiver.send(statusCode, resultBundle);
                }
                else {
                    receiver.send(statusCode, null);
                }
            }
            else {
                receiver.send(0, null);

            }
        }
        catch (IOException e) {
            receiver.send(0, null);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private HttpResponse doRequest(HttpRequestBase request) throws IOException {
        HttpClient client = new DefaultHttpClient();

        // HttpClient configuration omitted

        return client.execute(request);
    }
}
