package com.example.admin.vkmess.Parser;

import android.graphics.Bitmap;
import android.util.JsonReader;

import com.example.admin.vkmess.VKLib.VKrequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Name {
    public ArrayList<String> name = new ArrayList<>();

    public ArrayList<Bitmap> images = new ArrayList<>();

    public Name(JsonReader json) throws IOException {
        ArrayList<String> image = new ArrayList<>();
        json.beginObject();
        json.nextName();
        json.beginArray();
        while (json.hasNext()) {
            json.beginObject();
            StringBuilder nameObj = new StringBuilder();
            while (json.hasNext()) {
                String next = json.nextName();
                if (Objects.equals(next, "first_name")) {
                    nameObj.append(json.nextString());
                    nameObj.append(" ");
                } else if (Objects.equals(next, "last_name"))
                    nameObj.append(json.nextString());
                else if (Objects.equals(next, "photo_50"))
                    image.add(json.nextString());
                else
                    json.skipValue();

            }
            name.add(nameObj.toString());
            json.endObject();
        }
        json.endArray();
        json.endObject();

        image.add("https://vk.com/images/camera_50.png");
        images = (VKrequest.getImageBitmap(image));
    }
}
