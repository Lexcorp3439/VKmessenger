package com.example.admin.vkmess.Parser;

import android.util.JsonReader;

import java.io.IOException;

public class LongPollRequest {

    public int pts;
    public int count;

    public LongPollRequest(JsonReader json) throws IOException {
        json.beginObject();
        json.nextName();
        json.beginObject();
        json.skipValue();
        json.nextName();
        json.beginObject();
        json.nextName();
        count = json.nextInt();
        json.skipValue();
        json.endObject();
        json.skipValue();
        json.skipValue();
        json.nextName();
        pts = json.nextInt();
    }
}
