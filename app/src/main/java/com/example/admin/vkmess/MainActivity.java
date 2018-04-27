package com.example.admin.vkmess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.vk.sdk.util.VKUtil;

import java.lang.reflect.Array;
import java.util.Arrays;

public class MainActivity extends Activity {

    String url = "https://oauth.vk.com/authorize?client_id=6445024&display=page&redirect_uri=https:" +
            "//oauth.vk.com/blank.html&scope=+4098&response_type=token&v=5.74&state=123456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_in);

        android.webkit.WebView webView = findViewById(R.id.webView);
        webView.setWebViewClient(new AutenWeb(getApplicationContext()));

        webView.loadUrl(url);
    }
}
