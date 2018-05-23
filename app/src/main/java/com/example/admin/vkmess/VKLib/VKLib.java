package com.example.admin.vkmess.VKLib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.ListView;

import com.example.admin.vkmess.Adapters.CustomAdapter;
import com.example.admin.vkmess.Adapters.FriendsAdapter;
import com.example.admin.vkmess.Adapters.MessagesAdapter;
import com.example.admin.vkmess.Dialogs;
import com.example.admin.vkmess.ObjectParameters.HistoryParam;
import com.example.admin.vkmess.ObjectParameters.Parameters;
import com.example.admin.vkmess.Parser.Friends;
import com.example.admin.vkmess.Parser.Message;
import com.example.admin.vkmess.Parser.Name;
import com.example.admin.vkmess.Parser.SendMess;
import com.example.admin.vkmess.Parser.UserMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class VKLib {
//    private static String LOG = "VKLin: ";
    private static String TOKEN;
    private static String ID;
    private static String nameUsr;
    private static String imageUsr;
    private static String imageUsr200;
    private static String status;


    public static String getStatus() {
        return status;
    }

    public static String getNameUsr() {
        return nameUsr;
    }

    public static String getImageUsr() {
        return imageUsr;
    }

    public static String getImage200() {
        return imageUsr200;
    }

    public static void setStatus(String status) {
        VKLib.status = status;
    }

    public static void setImage200(String imageUsr200) {
        VKLib.imageUsr200 = imageUsr200;
    }

    public static void setTOKEN(String TOKEN) {
        VKLib.TOKEN = TOKEN;
    }

    public static void setID(String ID) {
        VKLib.ID = ID;
    }

    public static void setNameUsr(String nameUsr) {
        VKLib.nameUsr = nameUsr;
    }

    public static void setImage(String imageUsr) {
        VKLib.imageUsr = imageUsr;
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
                    listView.setAdapter(new FriendsAdapter(friends.getImage(),
                            friends.getName(), friends.getId(), context, activity)));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void getDialogHist(int id, String frName, String image, Context context, ListView listView) {
        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> images = new ArrayList<>();

        String url = "https://api.vk.com/method/messages.getHistory?offset=0&user_id=" + id + "&count=200&v=5.74&access_token=" + TOKEN;

        UserMessage message = new UserMessage();

        try {
            new VKrequest().execute(new RequestObject(url, message)).get();

            HistoryParam history = message.getHistory();

            for (int out : history.getOut()) {
                if (out == 1) {
                    name.add(nameUsr);
                    images.add(imageUsr);
                }else {
                    name.add(frName);
                    images.add(image);
                }
            }
            ArrayList<String> messages = history.getMessages();
            ArrayList<Integer> readState = history.getReadState();

            Collections.reverse(messages);
            Collections.reverse(name);
            Collections.reverse(readState);
            Collections.reverse(images);

            listView.setAdapter(new MessagesAdapter(messages, name,
                    readState, context, images));
            listView.setSelection(name.size() - 1);

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
                    allID.toString().substring(0, allID.length() - 1) + "&fields=photo_50&v=5.74&access_token=" + TOKEN;
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