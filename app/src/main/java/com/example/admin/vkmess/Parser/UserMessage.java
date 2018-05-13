package com.example.admin.vkmess.Parser;

import android.util.JsonReader;

import com.example.admin.vkmess.ObjectParameters.HistoryParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class UserMessage implements Parser{
    private HistoryParam history;

    public HistoryParam getHistory() {
        return history;
    }

    public void parse(JsonReader json) throws IOException {
        json.beginObject();
        json.nextName();
        json.beginObject();
        while (json.hasNext()) {
            switch (json.nextName()) {
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
        ArrayList<String> messages = new ArrayList<>();
        ArrayList<Integer> out = new ArrayList<>();
        ArrayList<Integer> readState = new ArrayList<>();


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
                        readState.add(json.nextInt());
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
                        text.append("\nПересланное сообщение");
                        json.skipValue();
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
        history = new HistoryParam(messages, out, readState);
    }

    private String attachments(JsonReader json) throws IOException {
        String type = "";
        json.beginArray();
        while (json.hasNext()) {
            json.beginObject();
            while (json.hasNext()) {
                switch (json.nextName()) {
                    case "type":
                        type = type(json.nextString());
                        break;
                    case "doc":
                        json.skipValue();
                        break;
                    case "photo":
                        json.skipValue();
                        break;
                    case "wall":
                        json.skipValue();
                        break;
                    case "sticker":
                        json.skipValue();
                        break;
                    case "audio":
                        json.skipValue();
                        break;
                    case "video":
                        json.skipValue();
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

    private String type(String tp) {
        switch (tp) {
            case "doc":
                return "Документ";
            case "wall":
                return "Запись со стены";
            case "sticker":
                return "Стикер";
            case "photo":
                return "Фотография";
            case "audio":
                return "Аудио";
            case "video":
                return "Видео";
            default:
                return tp;
        }
    }
}

