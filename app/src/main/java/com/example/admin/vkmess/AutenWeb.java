package com.example.admin.vkmess;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static android.content.ContentValues.TAG;

class AutenWeb extends WebViewClient{

    Context context;

    AutenWeb(Context context){
        this.context = context;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url)
    {
        return shouldOverrideUrlLoading(url);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request)
    {
        Uri uri = request.getUrl();
        return shouldOverrideUrlLoading(uri.toString());
    }

    private boolean shouldOverrideUrlLoading(final String url)
    {
        Log.i(TAG, "shouldOverrideUrlLoading() URL : " + url);

        if (url.matches(".*access_token=.*")) {
            String ACCES_TOKEN = url.substring(45, 130);
            String ID = url.substring(156, 165);
            Intent intent = new Intent(context, BodyMess.class);
            intent.putExtra("token", ACCES_TOKEN).putExtra("id", ID);
            context.startActivity(intent);
        }

        return false;
    }
}
