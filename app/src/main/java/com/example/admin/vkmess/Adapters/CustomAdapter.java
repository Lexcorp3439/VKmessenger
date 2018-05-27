package com.example.admin.vkmess.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.vkmess.Dialogs;
import com.example.admin.vkmess.R;
import com.example.admin.vkmess.VKLib.DownloadImage;
import com.example.admin.vkmess.VKLib.VKLib;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.admin.vkmess.VKLib.VKLib.getID;


public class CustomAdapter extends BaseAdapter {
    private List<String> messages;
    private List<String> users;
    private List<String> images;
    private List<Integer> userId;
    private Context context;
    private Activity activity;

    private final String LOG = "CustomAdapter: ";
    private boolean can = true;

    public CustomAdapter(Context context, List<String> users, List<Integer> userId,
                         List<String> messages, List<String> images, Activity activity) {
        this.context = context;
        this.messages = messages;
        this.users = users;
        this.userId = userId;
        this.images = images;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        SetData setData = new SetData();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        assert inflater != null;
        @SuppressLint({"ViewHolder", "InflateParams"})
        View view = inflater.inflate(R.layout.item, null);

        setData.userName = view.findViewById(R.id.user);
        setData.msg = view.findViewById(R.id.msg);
        setData.image = view.findViewById(R.id.imageView2);

        setData.userName.setText(users.get(position));
        setData.msg.setText(messages.get(position));
        new DownloadImage(setData.image, context, userId.get(position)).execute(images.get(position));

        Timer timer = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(() -> can = true);
            }
        };
        timer.schedule(tt,0,1000);

        view.setOnClickListener(v -> {
            if (can) {
                activity.runOnUiThread(() -> can = false);
                int id = userId.get(position);
                Log.e(LOG, "userId= " + id);
                context.startActivity(
                        new Intent(context, Dialogs.class).putExtra("id", id)
                                .putExtra("userName", users.get(position))
                                .putExtra("userImg", images.get(position)));
            }
        });
        return view;
    }

    private class SetData {
        TextView userName, msg;
        ImageView image;
    }
}