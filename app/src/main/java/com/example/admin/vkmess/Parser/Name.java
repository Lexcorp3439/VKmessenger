package com.example.admin.vkmess.Parser;

import android.util.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Name implements Parser{
    private List<String> name = new ArrayList<>();
    private List<String> images = new ArrayList<>();

    public List<String> getName() {
        return name;
    }

    public List<String> getImages() {
        return images;
    }

    @Override
    public void parse(JsonReader json) throws IOException {
        json.beginObject();
        json.nextName();
        json.beginArray();
        while (json.hasNext()) {
            json.beginObject();
            StringBuilder nameObj = new StringBuilder();
            while (json.hasNext()) {
                String next = json.nextName();
                switch (next){
                    case "first_name":
                        nameObj.append(json.nextString());
                        nameObj.append(" ");
                        break;
                    case "last_name":
                        nameObj.append(json.nextString());
                        break;
                    case "photo_50":
                        images.add(json.nextString());
                        break;
                    default:
                        json.skipValue();
                        break;
                }
            }
            name.add(nameObj.toString());
            json.endObject();
        }
        json.endArray();
        json.endObject();
        images.add("https://vk.com/images/camera_50.png");
    }
}
