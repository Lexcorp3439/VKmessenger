package com.example.admin.vkmess.Parser;

import android.util.JsonReader;

import com.example.admin.vkmess.ObjectParameters.Parameters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Message {
    private boolean unread = false;
    private int user_id;
    public String title = "";
    private String body = "";
    private JsonReader json;
    public ArrayList<Parameters> param = new ArrayList<>();

    public Message(JsonReader json,int count) throws IOException {
        this.json = json;

        json.beginObject();
        json.nextName();
        json.beginObject();
        json.nextName();
        json.skipValue();
        if (Objects.equals(json.nextName(), "unread_dialogs")) {
            json.skipValue();
            json.nextName();
        }
        json.beginArray();
        for (int i = 0; i < count; i++) {
            json.beginObject();
            if (Objects.equals(json.nextName(), "unread")){
                json.skipValue();
                json.nextName();
            }
            param.add(messageObject());

            json.nextName();
            json.skipValue();
            json.nextName();
            json.skipValue();
            json.endObject();
        }
        json.endArray();
        json.endObject();
        json.endObject();
    }

    private Parameters messageObject() throws IOException {
        json.beginObject();
        while (json.hasNext()){
            switch (json.nextName()){
                case "unread":
                    json.skipValue();
                    unread = true;
                case "user_id":
                    user_id = json.nextInt();
                    break;
                case "title":
                    title = json.nextString();
                    break;
                case "body":
                    body = json.nextString();
                    break;
                case "attachments":
                    body = attachment(json);
                    break;
                default:
                    json.skipValue();
                    break;
            }
        }
        json.endObject();
        return new Parameters(body, user_id, title, unread);
    }

    private String attachment(JsonReader json) throws IOException {
        String type;
        json.beginArray();
        json.beginObject();
        json.nextName();
        type = json.nextString();
        if (Objects.equals(type, "doc"))
            type = "Документ";
        if (Objects.equals(type, "wall"))
            type = "Запись со стены";
        if (Objects.equals(type, "sticker"))
            type = "Стикер";
        while (json.hasNext())
            json.skipValue();
        json.endObject();
        json.endArray();
        return type;
    }
}
