package com.example.admin.vkmess.Parser;

import android.util.JsonReader;

import java.io.IOException;

public class LongPollRequest {

    public int ts;
    public boolean reload;

    public LongPollRequest(JsonReader json) throws IOException {

        json.beginObject();
        while (json.hasNext()){
            switch (json.nextName()){
                case "ts":
                    ts = json.nextInt();
                    break;
                case "updates":
                    json.beginArray();
                    parseArray(json);
//                    reload = parseArray(json);
                    json.endArray();
                    break;
                default:
                    json.skipValue();
                    break;
            }
        }
        json.endObject();
    }

    private void parseArray(JsonReader json) throws IOException {
        while (json.hasNext())
        json.beginArray();
        int i = json.nextInt();
        if (i == 4 || i == 5)
            reload = true;
        else
            reload = false;
        while (json.hasNext())
            json.skipValue();
        json.endArray();
    }
}
