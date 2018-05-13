package com.example.admin.vkmess.Parser;

import android.util.JsonReader;

import java.io.IOException;

public class LongPollRequest implements Parser{
    private int pts;
    private int count;

    public int getPts() {
        return pts;
    }

    public int getCount() {
        return count;
    }

    @Override
    public void parse(JsonReader json) throws IOException {
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
