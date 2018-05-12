package com.example.admin.vkmess;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.admin.vkmess.ObjectParameters.LPElem;
import com.example.admin.vkmess.Parser.LongPoll;
import com.example.admin.vkmess.VKLib.VKLib;

import static android.content.ContentValues.TAG;

class AutenWeb extends WebViewClient {

    private Context context;

    private WebView webView;

    AutenWeb(Context context, WebView webView) {
        this.context = context;
        this.webView = webView;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        return shouldOverrideUrlLoading(url);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request) {
        Uri uri = request.getUrl();
        return shouldOverrideUrlLoading(uri.toString());
    }

    private boolean shouldOverrideUrlLoading(final String url) {
        Log.i(TAG, "shouldOverrideUrlLoading() URL : " + url);

        if (url.contains("access_token")) {
//            String urlLog = "https://api.vk.com/method/messages.getLongPollServer?lp_version=3" +
//                    "&need_pts=0&need_pts=1&v=5.74&access_token=" + url.substring(45, 130);

            Uri uri = Uri.parse(url.replace("#", "?"));

            System.out.println();

            String ACCES_TOKEN = uri.getQueryParameter("access_token");
            String ID = uri.getQueryParameter("user_id");

            VKLib vkLib = new VKLib();
            vkLib.setTOKEN(ACCES_TOKEN);
            vkLib.setID(ID);
            webView.setVisibility(View.INVISIBLE);

            Intent intent = new Intent(context, BodyMess.class);
            context.startActivity(intent);

        }

        return false;
    }
}
