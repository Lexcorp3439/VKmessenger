package com.example.admin.vkmess.VKLib;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.admin.vkmess.Parser.Parser;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


class VKrequest extends AsyncTask<RequestObject, Void, Void> {

    Runnable run;

    public VKrequest(Runnable run) {
        this.run = run;
    }

    public VKrequest() {
        run = ()->{};
    }

    @Override
    protected Void doInBackground(RequestObject... request) {
        HttpURLConnection connection = null;
        RequestObject rq = request[0];

        // этот циел нужен для того, чтоб в том случае, если ловится ошибка timeout
        // - программа не закрывается, а совершает повторное подключение
        while (true) {
            try {
                connection = (HttpURLConnection) new URL(rq.getUrl()).openConnection();

                connection.setRequestMethod("GET");
                connection.setUseCaches(false);
                connection.setConnectTimeout(250);
                connection.setReadTimeout(250);
                connection.connect();

                Parser clasS = rq.getParserClass();

                if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                    clasS.parse(new JsonReader(new InputStreamReader(connection.getInputStream())));
                } else {
                    Log.d("VKrequest", "Fail: " + connection.getResponseCode() + ", " + connection.getResponseMessage());
                }
                break;
            } catch (Exception cause) {
                Log.d("VKrequest", cause.getMessage());
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        run.run();
    }
}
