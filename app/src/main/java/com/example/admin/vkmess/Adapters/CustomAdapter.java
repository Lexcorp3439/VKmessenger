package com.example.admin.vkmess.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.vkmess.R;
import com.example.admin.vkmess.VKLib.DownloadImage;
import com.example.admin.vkmess.VKLib.VKLib;

import java.util.List;


public class CustomAdapter extends BaseAdapter {
    private List<String> messages;
    private List<String> users;
    private List<String> images;
    private List<Integer> user_id;
    private Context context;

    private String LOG = "CustomAdapter: ";

    public CustomAdapter(Context context, List<String> users, List<Integer> user_id,
                         List<String> messages, List<String> images) {
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
            Log.e(LOG, "user_id= " + id);
            VKLib.getDialogHist(id, context);
        });

        return view;
    }

    private class SetData {
        TextView user_name, msg;
        ImageView image;
    }
}