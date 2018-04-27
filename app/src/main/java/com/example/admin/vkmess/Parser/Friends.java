package com.example.admin.vkmess.Parser;

import android.graphics.Bitmap;
import android.util.JsonReader;

import com.example.admin.vkmess.VKLib.VKrequest;

import java.io.IOException;
import java.util.ArrayList;

public class Friends {

    public ArrayList<String> name = new ArrayList<>();

    public ArrayList<Bitmap> images = new ArrayList<>();

    public ArrayList<Integer> id = new ArrayList<>();

    public Friends (JsonReader json) throws IOException {
        ArrayList<String> image = new ArrayList<>();

        json.beginObject();
        json.nextName();
        json.beginObject();
        json.nextName();
        System.out.println(json.nextInt());
        System.out.println(json.nextName());
        json.beginArray();
        while (json.hasNext()){
            json.beginObject();
            StringBuilder nameObj = new StringBuilder();
            while (json.hasNext()) {
                switch (json.nextName()){
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
                }
            }
            System.out.println(nameObj.toString());
            name.add(nameObj.toString());
            json.endObject();
        }
        json.endArray();
        json.endObject();
        json.endObject();

        images = (VKrequest.getImageBitmap(image));
    }
}
