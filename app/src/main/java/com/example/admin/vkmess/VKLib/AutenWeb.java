package com.example.admin.vkmess.VKLib;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.admin.vkmess.BodyMess;
import com.example.admin.vkmess.Parser.Name;
import com.example.admin.vkmess.Parser.User;

import java.util.concurrent.ExecutionException;

import static android.content.ContentValues.TAG;

public class AutenWeb extends WebViewClient {
    private Context context;
    private WebView webView;

    public AutenWeb(Context context, WebView webView) {
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
            Uri uri = Uri.parse(url.replace("#", "?"));

            String ACCES_TOKEN = uri.getQueryParameter("access_token");
            String ID = uri.getQueryParameter("user_id");

            String usersGet = "https://api.vk.com/method/users.get?fields=photo_50,photo_200,status&v=5.74&access_token=" + ACCES_TOKEN;
            User user = new User();
            try {
                new VKrequest().execute(new RequestObject(usersGet, user)).get();
                VKLib.setTOKEN(ACCES_TOKEN);
                VKLib.setID(ID);
                VKLib.setNameUsr(user.getName());
                VKLib.setImage(user.getImage50());
                VKLib.setImage200(user.getImage200());
                VKLib.setStatus(user.getStatus());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }



            webView.setVisibility(View.INVISIBLE);

            Intent intent = new Intent(context, BodyMess.class);
            context.startActivity(intent);
        }
        return false;
    }
}

//            String urlLog = "https://api.vk.com/method/messages.getLongPollServer?lp_version=3" +
//                    "&need_pts=0&need_pts=1&v=5.74&access_token=" + url.substring(45, 130);
