package com.example.admin.vkmess.VKLib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.ListView;

import com.example.admin.vkmess.Adapters.CustomAdapter;
import com.example.admin.vkmess.Adapters.FriendsAdapter;
import com.example.admin.vkmess.Dialogs;
import com.example.admin.vkmess.ObjectParameters.HistoryParam;
import com.example.admin.vkmess.ObjectParameters.Parameters;
import com.example.admin.vkmess.Parser.Friends;
import com.example.admin.vkmess.Parser.Message;
import com.example.admin.vkmess.Parser.Name;
import com.example.admin.vkmess.Parser.SendMess;
import com.example.admin.vkmess.Parser.UserMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class VKLib {

//    private static String LOG = "VKLin: ";

    private static String TOKEN;
    private static String ID;

    public void setTOKEN(String TOKEN) {
        VKLib.TOKEN = TOKEN;
    }

    public void setID(String ID) {
        VKLib.ID = ID;
    }

    public static void sendMess(int id, String msg) {

        try {

            String url = "https://api.vk.com/method/messages.send?user_id=" + id + "&message="
                    + msg + "&v=5.74&access_token=" + TOKEN;
            SendMess sendMess = new SendMess();
            RequestObject rb = new RequestObject(url, sendMess);

            new VKrequest().execute(rb).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void getFriends(Context context, Activity activity, ListView listView) {

        Friends friends = new Friends();
        String url = "https://api.vk.com/method/friends.get?user_id=" + ID + "&order=hints&fields=photo_50&v=5.74&access_token=" + TOKEN;
        RequestObject rb = new RequestObject(url, friends);

        try {
            new VKrequest().execute(rb).get();

            activity.runOnUiThread(() ->
                    listView.setAdapter(new FriendsAdapter(context, friends.getName(),
                            friends.getImage(), friends.getId())));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void getDialogHist(int id, Context context) {

        ArrayList<String> name = new ArrayList<>();

        String url = "https://api.vk.com/method/messages.getHistory?offset=0&user_id=" + id + "&count=200&v=5.74&access_token=" + TOKEN;
        String user_name = "https://api.vk.com/method/users.get?&v=5.74&access_token=" + TOKEN;
        String friend_name = "https://api.vk.com/method/users.get?user_ids=" + id + "&v=5.74&access_token=" + TOKEN;
        UserMessage message = new UserMessage();
        Name userName = new Name();
        Name friendName = new Name();

        try {
            new VKrequest().execute(new RequestObject(url, message)).get();
            new VKrequest().execute(new RequestObject(user_name, userName)).get();
            new VKrequest().execute(new RequestObject(friend_name, friendName)).get();

            HistoryParam history = message.getHistory();
            user_name = userName.getName().get(0);
            friend_name = friendName.getName().get(0);

            for (int i : history.getOut()) {
                if (i == 1)
                    name.add(user_name);
                else
                    name.add(friend_name);
            }

            context.startActivity(
                    new Intent(context, Dialogs.class).putExtra("id", id)
                            .putExtra("messages", history.getMessages())
                            .putExtra("out", name)
                            .putExtra("read_state", history.getReadState()));

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void getDialogs(int count, Activity activity, ListView listView) {

        List<Parameters> param;
        List<String> messages = new ArrayList<>();
        List<String> users = new ArrayList<>();
        List<String> images = new ArrayList<>();
        List<Integer> users_id = new ArrayList<>();

        Message message = new Message();
        String request = "https://api.vk.com/method/messages.getDialogs?count=" + count + "&v=5.74&access_token=" + TOKEN;
        RequestObject rb = new RequestObject(request, message);

        try {
            new VKrequest().execute(rb).get();

            param = message.getParam();

            StringBuilder allID = new StringBuilder();

            for (Parameters elem : param) {

                if (Objects.equals(elem.getTitle(), "")) {
                    allID.append(elem.getUserId()).append(",");
                }
                users_id.add(elem.getUserId());

                if (elem.getBody().length() < 20)
                    messages.add(elem.getBody());
                else {
                    messages.add(elem.getBody().substring(0, 17) + "...");
                }

            }

            String nameReq = "https://api.vk.com/method/users.get?user_ids=" +
                    allID.toString().substring(0, allID.length() - 2) + "&fields=photo_50&v=5.74&access_token=" + TOKEN;
            Name name = new Name();

            new VKrequest().execute(new RequestObject(nameReq, name)).get();

            List<String> users_All = name.getName();
            List<String> image_All = name.getImages();
            String defaultImg = image_All.get(image_All.size() - 1);
            int i = 0;

            for (Parameters elem : param) {
                if (Objects.equals(elem.getTitle(), "") && elem.getUserId() > 0) {
                    users.add(users_All.get(i));
                    images.add(image_All.get(i));
                    i++;
                } else {
                    users.add(elem.getTitle());
                    images.add(defaultImg);
                }

            }


            activity.runOnUiThread(() ->
                    listView.setAdapter(new CustomAdapter(activity.getApplicationContext(),
                            users, users_id, messages, images
                    )));

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}

//            new VKrequest(() ->
//                    new VKrequest(()->
//                    new VKrequest(()->{
//                    HistoryParam history = message.getHistory();
//                    String userN = userName.getName().get(0);
//                    String friendN = friendName.getName().get(0);
//
//                    for (int i : history.getOut()) {
//                    if (i == 1)
//                    name.add(userN);
//                    else
//                    name.add(friendN);
//                    }
//
//                    context.startActivity(
//                    new Intent(context, Dialogs.class).putExtra("id", id)
//        .putExtra("messages", history.getMessages())
//        .putExtra("out", name)
//        .putExtra("read_state", history.getReadState()));
//        }
//        ).execute(new RequestObject(friend_name, friendName)).get())
//        .execute(new RequestObject(user_name, userName)).get())
//        .execute(new RequestObject(url, message)).get();