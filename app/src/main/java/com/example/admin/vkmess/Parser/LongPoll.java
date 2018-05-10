package com.example.admin.vkmess.Parser;

import android.util.JsonReader;

import com.example.admin.vkmess.ObjectParameters.LPElem;

import java.io.IOException;

public class LongPoll {

    private LPElem elem;

    public LPElem getElem() {
        return elem;
    }

    public LongPoll(JsonReader json) throws IOException {

        int ts = 0;
        int pts = 0;
        String key = "";
        String server = "";

        json.beginObject();
        json.nextName();
        json.beginObject();
        while (json.hasNext()){
            switch (json.nextName()){
                case "key":
                    key = json.nextString();
                    break;
                case "ts":
                    ts = json.nextInt();
                    break;
                case "server":
                    server = json.nextString();
                    break;
                case "pts":
                    pts = json.nextInt();
                    break;
                default:
                    json.skipValue();
                    break;
            }
        }
        json.endObject();
        json.endObject();
        elem = new LPElem(ts, server, key, pts);
    }
}
