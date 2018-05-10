package com.example.admin.vkmess.VKLib;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.JsonReader;
import android.util.Log;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


class VKrequest extends AsyncTask<String, Void, JsonReader> {

    @Override
    protected JsonReader doInBackground(String... urls) {
        HttpURLConnection connection = null;
        String url = urls[0];

        try {
            connection = (HttpURLConnection) new URL(url).openConnection();

            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setConnectTimeout(250);
            connection.setReadTimeout(250);

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
    protected void onPostExecute(JsonReader json) {

    }

}
