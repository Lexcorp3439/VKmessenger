package com.example.admin.vkmess.VKLib;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.util.JsonReader;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static android.content.ContentValues.TAG;


public class VKrequest {
    static BlockingQueue<Runnable> request = new ArrayBlockingQueue<Runnable>(100);
    static MyThread secThread = new MyThread(request);



    public static void lamda(Runnable runnable){
        request.add(runnable);
    }

    @Nullable
    public static JsonReader getJSON(String url) {
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) new URL(url).openConnection();

            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            connection.connect();

            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()){

                return new JsonReader(new InputStreamReader(connection.getInputStream()));
            } else{
                Log.d("VKrequest", "Fail: " + connection.getResponseCode() + ", " + connection.getResponseMessage());
            }
        } catch (Exception cause){
            Log.d("VKrequestTEXT", cause.getMessage());
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }
        return null;
    }
}
