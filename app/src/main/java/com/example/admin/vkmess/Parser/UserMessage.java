package com.example.admin.vkmess.Parser;

import android.util.JsonReader;

import com.example.admin.vkmess.ObjectParameters.HistoryParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class UserMessage {

    public HistoryParam history;

    public UserMessage(JsonReader json) throws IOException {
        json.beginObject();                                                                         // {
        json.nextName();                                                                          // response
        json.beginObject();                                                                         // {
        json.nextName();                                                                           // count
        json.skipValue();                                                                           // 37
        if (Objects.equals(json.nextName(), "unread")) {
            json.skipValue();
            json.nextName();
        }
        history = parseObj(json);
        json.nextName();
        json.skipValue();
        json.nextName();
        json.skipValue();
        json.endObject();
        json.endObject();
    }

    private HistoryParam parseObj(JsonReader json) throws IOException {
        ArrayList<String> messages = new ArrayList<>();
        ArrayList<Integer> out = new ArrayList<>();
        ArrayList<Integer> read_state = new ArrayList<>();


        json.beginArray();
        while (json.hasNext()) {
            json.beginObject();
            StringBuilder text = new StringBuilder();
            while (json.hasNext()) {
                switch (json.nextName()) {
                    case "body":
                        text.append(json.nextString());
                        break;
                    case "read_state":
                        read_state.add(json.nextInt());
                        break;
                    case "out":
                        out.add(json.nextInt());
                        break;
                    case "attachments":
                        if (!Objects.equals(text, ""))
                            text.append("\n").append(attachments(json));
                        else text.append(attachments(json));
                        break;
                    case "fwd_messages":
                        text.append("Пересланное сообщение");
                        json.beginArray();
                        while (json.hasNext()) {
                            json.beginObject();
                            while (json.hasNext())
                                json.skipValue();
                            json.endObject();
                        }
                        json.endArray();

                        break;
                    default:
                        json.skipValue();
                        break;
                }
            }
            messages.add(text.toString());
            json.endObject();
        }
        json.endArray();
        return new HistoryParam(messages, out, read_state);
    }

    private String attachments(JsonReader json) throws IOException {
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
        if (Objects.equals(type, "photo"))
            type = "Фотография";
        if (Objects.equals(type, "audio"))
            type = "Аудио";
        if (Objects.equals(type, "video"))
            type = "Видео";
        while (json.hasNext())
            json.skipValue();
        json.endObject();
        while (json.hasNext()){
            json.skipValue();
        }

        json.endArray();
        return type;
    }
}

