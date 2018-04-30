package com.example.admin.vkmess.VKLib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.JsonReader;
import android.widget.ListView;

import com.example.admin.vkmess.Adapters.CustomAdapter;
import com.example.admin.vkmess.Adapters.FriendsAdapter;
import com.example.admin.vkmess.Dialogs;
import com.example.admin.vkmess.ObjectParameters.HistoryParam;
import com.example.admin.vkmess.ObjectParameters.Parameters;
import com.example.admin.vkmess.Parser.Friends;
import com.example.admin.vkmess.Parser.Message;
import com.example.admin.vkmess.Parser.Name;
import com.example.admin.vkmess.Parser.UserMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static com.example.admin.vkmess.BodyMess.TOKEN;
import static com.example.admin.vkmess.BodyMess.ID;

public class VKLib {

    public static void getFriends(Context context, Activity activity, ListView listView){
        VKrequest.lamda(() -> {
            String url = "https://api.vk.com/method/friends.get?user_id=" + ID + "&order=hints&fields=photo_50&v=5.74&access_token=" + TOKEN;

            JsonReader json = VKrequest.getJSON(url);
            try {
                Friends friends = new Friends(json);
                activity.runOnUiThread(() ->
                        listView.setAdapter(new FriendsAdapter(context, friends.name, friends.images, friends.id)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void getDialogHist(int id, Context context) {
        VKrequest.lamda(() -> {
            ArrayList<String> name = new ArrayList<>();

            String url = "https://api.vk.com/method/messages.getHistory?offset=0&user_id=" + id + "&count=20&v=5.74&access_token=" + TOKEN;
            String user_name = "https://api.vk.com/method/users.get?user_id=" + ID + "&v=5.74";
            String friend_name = "https://api.vk.com/method/users.get?user_id=" + id + "&v=5.74";

            JsonReader json = VKrequest.getJSON(url);

            JsonReader userJson = VKrequest.getJSON(user_name);
            JsonReader friendJson = VKrequest.getJSON(friend_name);

            try {

                HistoryParam history = new UserMessage(json).history;

                user_name = new Name(userJson).name.get(0);
                friend_name = new Name(friendJson).name.get(0);

                for (int i : history.out) {
                    if (i == 1)
                        name.add(user_name);
                    else
                        name.add(friend_name);
                }

                context.startActivity(
                        new Intent(context, Dialogs.class).putExtra("id", id)
                                .putExtra("messages", history.messages)
                                .putExtra("out", name)
                                .putExtra("read_state", history.read_state));

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    public static void getDialogs(int count, Activity activity, ListView listView) {

        VKrequest.lamda(() -> {
            ArrayList<Parameters> param;
            ArrayList<String> messages = new ArrayList<>();
            ArrayList<String> users = new ArrayList<>();
            ArrayList<Bitmap> images = new ArrayList<>();
            ArrayList<Integer> users_id = new ArrayList<>();
            String request = "https://api.vk.com/method/messages.getDialogs?count=" + count + "&v=5.74&access_token=" + TOKEN;

            JsonReader json = VKrequest.getJSON(request);

            if (json == null)
                System.out.println("LexaLOH");

            try {
                param = new Message(json, count).param;

                StringBuilder allID = new StringBuilder();

                for (Parameters elem : param) {

                    if (Objects.equals(elem.title, "")) {
                        allID.append(elem.user_id).append(",");
                    }
                    users_id.add(elem.user_id);

                    if (elem.body.length() < 20)
                        messages.add(elem.body);
                    else {
                        messages.add(elem.body.substring(0, 17) + "...");
                    }

                }

                String nameReq = "https://api.vk.com/method/users.get?user_ids=" +
                        allID.toString().substring(0, allID.length() - 2) + "&fields=photo_50&v=5.74&access_token=" + TOKEN;
                json = VKrequest.getJSON(nameReq);
                Name name = new Name(json);
                ArrayList<String> users_All = name.name;
                ArrayList<Bitmap> image_All = name.images;
                Bitmap defaultImg = image_All.get(image_All.size() - 1);
                int i = 0;

                for (Parameters elem : param) {
                    if (Objects.equals(elem.title, "" ) && elem.user_id > 0) {
                        users.add(users_All.get(i));
                        images.add(image_All.get(i));
                        i++;
                    } else {
                        users.add(elem.title);
                        images.add(defaultImg);
                    }

                }


                activity.runOnUiThread(() ->
                        listView.setAdapter(new CustomAdapter(activity.getApplicationContext(),
                                users, users_id, messages, images
                        )));

            } catch (IOException e) {
                e.printStackTrace();
            }

        });


    }
}

