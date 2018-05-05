package com.example.admin.vkmess.Parser;

import android.util.JsonReader;

import com.example.admin.vkmess.ObjectParameters.Parameters;

import java.io.IOException;
import java.util.ArrayList;

public class Message {
    private boolean unread = false;
    private int user_id;
    public String title = "";
    private String body = "";
    public ArrayList<Parameters> param = new ArrayList<>();

    public Message(JsonReader json) throws IOException {
        json.beginObject();
        json.nextName();
        json.beginObject();
        while (json.hasNext()) {
            switch (json.nextName()) {
                case "unread_dialogs":
                    json.skipValue();
                    break;
                case "count":
                    json.skipValue();
                    break;
                case "items":
                    items(json);
                    break;
                default:
                    json.skipValue();
                    break;
            }
        }
        json.endObject();
        json.endObject();
    }

    private void items(JsonReader json) throws IOException {
        json.beginArray();
        while (json.hasNext()) {
            json.beginObject();
            while (json.hasNext()) {
                switch (json.nextName()) {
                    case "unread":
                        json.skipValue();
                        break;
                    case "message":
                        param.add(message(json));
                        break;
                    default:
                        json.skipValue();
                        break;
                }
            }
            json.endObject();
        }
        json.endArray();
    }

    private Parameters message(JsonReader json) throws IOException {
        json.beginObject();
        while (json.hasNext()) {
            switch (json.nextName()) {
                case "unread":
                    json.skipValue();
                    unread = true;
                    break;
                case "date":
                    json.skipValue();
                    break;
                case "user_id":
                    user_id = json.nextInt();
                    break;
                case "title":
                    title = json.nextString();
                    break;
                case "body":
                    body = json.nextString();
                    break;
                case "action":                //chat_title_update    "chat_kick_user"
                    String chat = json.nextString();
                    if (chat.equals("chat_kick_user"))
                        body = "Пользователь исключен из беседы";
                    break;
                case "action_text":
                    body = json.nextString();
                    break;
                case "chat_id":
                    user_id = json.nextInt();
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
        String type = "";
        json.beginArray();
        while (json.hasNext()) {
            json.beginObject();
            while (json.hasNext()) {
                switch (json.nextName()) {
                    case "type":
                        type = json.nextString();
                        break;
                    case "photo":
                        type = photo(json, type);
                        break;
                    case "doc":
                        type = doc(json, type);
                        break;
                    default:
                        json.skipValue();
                        break;
                }
            }
            json.endObject();
        }
        json.endArray();
        return type;
    }

    private String doc (JsonReader json, String type) throws IOException {
        json.beginObject();
        while (json.hasNext()) {
            switch (json.nextName()) {
                case "title":
                    String text = json.nextString();
                    if (!text.equals("")) {
                        if (text.equals("voice_message.webm"))
                            type = "Голосовое сообщение";
                        else type = text;
                    }
                    break;
                default:
                    json.skipValue();
                    break;
            }
        }
        json.endObject();
        return type;
    }

    private String photo (JsonReader json, String type) throws IOException {
        json.beginObject();
        while (json.hasNext()) {
            switch (json.nextName()) {
                case "text":
                    String text = json.nextString();
                    if (!text.equals(""))
                        type = text;
                    break;
                default:
                    json.skipValue();
                    break;
            }
        }
        json.endObject();
        return type;
    }
}
