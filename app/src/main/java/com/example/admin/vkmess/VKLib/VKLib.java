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
            new VKrequest().execute(new RequestObject(url, sendMess)).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void getFriends(Context context, Activity activity, ListView listView) {
        String url = "https://api.vk.com/method/friends.get?user_id=" + ID + "&order=hints&fields=photo_50&v=5.74&access_token=" + TOKEN;
        Friends friends = new Friends();

        try {
            new VKrequest().execute(new RequestObject(url, friends)).get();

            activity.runOnUiThread(() ->
                    listView.setAdapter(new FriendsAdapter(context, friends.getName(),
                            friends.getImage(), friends.getId())));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void getDialogHist(int id, String image, Context context) {
        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> images = new ArrayList<>();

        String url = "https://api.vk.com/method/messages.getHistory?offset=0&user_id=" + id + "&count=200&v=5.74&access_token=" + TOKEN;
        String usersGet = "https://api.vk.com/method/users.get?fields=photo_50&v=5.74&access_token=" + TOKEN;
        String friendsGet = "https://api.vk.com/method/users.get?user_ids=" + id + "&v=5.74&access_token=" + TOKEN;

        UserMessage message = new UserMessage();
        Name userName = new Name();
        Name friendName = new Name();

        try {
            new VKrequest().execute(new RequestObject(url, message)).get();
            new VKrequest().execute(new RequestObject(usersGet, userName)).get();
            new VKrequest().execute(new RequestObject(friendsGet, friendName)).get();

            HistoryParam history = message.getHistory();
            usersGet = userName.getName().get(0);
            friendsGet = friendName.getName().get(0);
            String userImg = userName.getImages().get(0);

            for (int out : history.getOut()) {
                if (out == 1) {
                    name.add(usersGet);
                    images.add(userImg);
                }else {
                    name.add(friendsGet);
                    images.add(image);
                }
            }

            context.startActivity(
                    new Intent(context, Dialogs.class).putExtra("id", id)
                            .putExtra("messages", history.getMessages())
                            .putExtra("out", name)
                            .putExtra("readState", history.getReadState())
                            .putExtra("userImg", images));

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void getDialogs(int count, Activity activity, ListView listView) {
        List<Parameters> param;
        List<String> messages = new ArrayList<>();
        List<String> users = new ArrayList<>();
        List<String> images = new ArrayList<>();
        List<Integer> usersId = new ArrayList<>();

        Message message = new Message();
        String request = "https://api.vk.com/method/messages.getDialogs?count=" + count + "&v=5.74&access_token=" + TOKEN;

        try {
            new VKrequest().execute(new RequestObject(request, message)).get();
            param = message.getParam();
            StringBuilder allID = new StringBuilder();

            for (Parameters elem : param) {
                if (Objects.equals(elem.getTitle(), ""))
                    allID.append(elem.getUserId()).append(",");
                usersId.add(elem.getUserId());

                if (elem.getBody().length() < 20) {
                    messages.add(elem.getBody());
                }else {
                    messages.add(elem.getBody().substring(0, 17) + "...");
                }
            }

            String nameReq = "https://api.vk.com/method/users.get?user_ids=" +
                    allID.toString().substring(0, allID.length() - 2) + "&fields=photo_50&v=5.74&access_token=" + TOKEN;
            Name name = new Name();
            new VKrequest().execute(new RequestObject(nameReq, name)).get();

            List<String> allUsers = name.getName();
            List<String> allImages = name.getImages();
            String defaultImg = allImages.get(allImages.size() - 1);
            int i = 0;

            for (Parameters elem : param) {
                if (Objects.equals(elem.getTitle(), "") && elem.getUserId() > 0) {
                    users.add(allUsers.get(i));
                    images.add(allImages.get(i));
                    i++;
                } else {
                    users.add(elem.getTitle());
                    images.add(defaultImg);
                }
            }

            activity.runOnUiThread(() ->
                    listView.setAdapter(new CustomAdapter(activity.getApplicationContext(),
                            users, usersId, messages, images, activity
                    )));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}

// Нужна для LongPoll запросов
//    String url = "https://api.vk.com/method/messages.getLongPollHistory/pts=" + pts +"v=5.74&access_token=" + BodyMess.TOKEN;