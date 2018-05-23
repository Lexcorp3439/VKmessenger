package com.example.admin.vkmess.Parser;

import android.util.JsonReader;

import java.io.IOException;
import java.util.Objects;

public class User implements Parser{
    private String name;
    private String image50;
    private String image200;
    private String status;

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getImage50() {
        return image50;
    }

    public String getImage200() {
        return image200;
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
                        image50 = json.nextString();
                        break;
                    case "photo_200":
                        image200 = json.nextString();
                        break;
                    case "status":
                        status = json.nextString();
                        break;
                    default:
                        json.skipValue();
                        break;
                }
            }
            name = nameObj.toString();
            json.endObject();
        }
        json.endArray();
        json.endObject();
    }
}
