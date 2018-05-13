package com.example.admin.vkmess.Parser;

import android.util.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Friends implements Parser{
    private List<String> name = new ArrayList<>();
    private List<String> image = new ArrayList<>();
    private List<Integer> id = new ArrayList<>();

    public List<String> getName() {
        return name;
    }

    public List<String> getImage() {
        return image;
    }

    public List<Integer> getId() {
        return id;
    }

    @Override
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
        json.beginArray();
        while (json.hasNext()) {
            json.beginObject();
            StringBuilder nameObj = new StringBuilder();
            while (json.hasNext()) {
                switch (json.nextName()) {
                    case "id":
                        id.add(json.nextInt());
                        break;
                    case "first_name":
                        nameObj.append(json.nextString());
                        nameObj.append(" ");
                        break;
                    case "last_name":
                        nameObj.append(json.nextString());
                        break;
                    case "photo_50":
                        image.add(json.nextString());
                        break;
                    default:
                        json.skipValue();
                        break;
                }
            }
            json.endObject();

            name.add(nameObj.toString());
        }
        json.endArray();
    }
}
