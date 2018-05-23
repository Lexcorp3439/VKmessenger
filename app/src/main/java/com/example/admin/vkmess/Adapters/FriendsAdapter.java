package com.example.admin.vkmess.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

public class FriendsAdapter extends BaseAdapter {
    private List<String> image;
    private List<String> users;
    private List<Integer> id;
    private Context context;
    private Activity activity;

    private boolean can = true;


    public FriendsAdapter(List<String> image, List<String> users, List<Integer> id, Context context, Activity activity) {
        this.image = image;
        this.users = users;
        this.id = id;
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        SetData setData = new SetData();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        assert inflater != null;
        @SuppressLint({"ViewHolder", "InflateParams"})
        View view = inflater.inflate(R.layout.friends_list, null);

        setData.userName = view.findViewById(R.id.users);
        setData.image = view.findViewById(R.id.image);

        setData.userName.setText(users.get(position));
        new DownloadImage(setData.image, context).execute(image.get(position));

        Timer timer = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                can = true;
            }
        };
        timer.schedule(tt,0,1000);

        view.setOnClickListener(v -> {
            if (can) {
                activity.runOnUiThread(() -> can = false);
                context.startActivity(
                        new Intent(context, Dialogs.class).putExtra("id", id.get(position))
                                .putExtra("userName", users.get(position))
                                .putExtra("userImg", image.get(position)));
            }
        });
        return view;
    }


    private class SetData {
        TextView userName;
        ImageView image;
    }
}
