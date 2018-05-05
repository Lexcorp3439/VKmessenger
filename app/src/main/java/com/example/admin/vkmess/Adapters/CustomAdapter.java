package com.example.admin.vkmess.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.media.Image;
import android.net.Uri;
import android.os.Parcelable;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.vkmess.BodyMess;
import com.example.admin.vkmess.Dialogs;
import com.example.admin.vkmess.ObjectParameters.HistoryParam;
import com.example.admin.vkmess.Parser.Name;
import com.example.admin.vkmess.Parser.UserMessage;
import com.example.admin.vkmess.R;
import com.example.admin.vkmess.VKLib.DownloadImage;
import com.example.admin.vkmess.VKLib.VKLib;
import com.example.admin.vkmess.VKLib.VKrequest;

import java.io.IOException;
import java.util.ArrayList;


public class CustomAdapter extends BaseAdapter {
    private ArrayList<String>  messages;
    private ArrayList<String>  users;
    private ArrayList<String> images;
    private ArrayList<Integer> user_id;
    private Context context;

    public CustomAdapter(Context context, ArrayList<String> users, ArrayList<Integer> user_id,
                         ArrayList<String> messages,ArrayList<String> images) {
        this.context = context;
        this.messages = messages;
        this.users = users;
        this.user_id = user_id;
        this.images = images;
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

            setData.user_name = view.findViewById(R.id.user);
            setData.msg = view.findViewById(R.id.msg);
            setData.image = view.findViewById(R.id.imageView2);

            setData.user_name.setText(users.get(position));
            setData.msg.setText(messages.get(position));

            new DownloadImage(setData.image).execute(images.get(position));

            view.setOnClickListener(v -> {
                final int id = user_id.get(position);
                System.out.println(id);
                VKLib.getDialogHist(id, context);
            });

        return view;
    }

    private class SetData {
        TextView user_name, msg;
        ImageView image;
    }
}