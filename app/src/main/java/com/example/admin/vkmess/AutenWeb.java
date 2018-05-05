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

import com.example.admin.vkmess.ObjectParameters.LPElem;
import com.example.admin.vkmess.Parser.LongPoll;
import com.example.admin.vkmess.VKLib.VKrequest;

import java.io.IOException;
import java.util.Objects;

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
            String urlLog = "https://api.vk.com/method/messages.getLongPollServer?lp_version=3" +
                    "&need_pts=0&need_pts=1&v=5.74&access_token=" + url.substring(45, 130);
            VKrequest.lamda(() -> {
                try {
                    LPElem elem = new LongPoll(Objects.requireNonNull(VKrequest.getJSON(urlLog))).elem;

                    String ACCES_TOKEN = url.substring(45, 130);
                    String ID = url.substring(156, 165);
//                    String server = elem.server;
//                    String key = elem.key;
                    int ts = elem.ts;
                    int pts = elem.pts;
                    System.out.println(pts);
                    Intent intent = new Intent(context, BodyMess.class);
                    intent.putExtra("token", ACCES_TOKEN).putExtra("id", ID)
                           // .putExtra("server", server).putExtra("key", key)
                            .putExtra("ts", ts).putExtra("pts", pts);
                    context.startActivity(intent);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        }

        return false;
    }
}
