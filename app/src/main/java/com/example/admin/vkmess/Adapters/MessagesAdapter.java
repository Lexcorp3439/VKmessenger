package com.example.admin.vkmess.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.vkmess.R;
import com.example.admin.vkmess.VKLib.DownloadImage;

import java.util.List;

public class MessagesAdapter extends BaseAdapter {
    private List<String> messages;
    private List<String> users;
    private List<Integer> readState;  //Пока не используется, но вижу в перспективе
    private Context context;
    private List<String> usrImg;

    public MessagesAdapter(List<String> messages, List<String> users, List<Integer> readState, Context context, List<String> usrImg) {
        this.messages = messages;
        this.users = users;
        this.readState = readState;
        this.context = context;
        this.usrImg = usrImg;
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
        @SuppressLint({"ViewHolder", "InflateParams"}) View view = inflater.inflate(R.layout.item_for_hist, null);

        setData.userName = view.findViewById(R.id.user);
        setData.msg = view.findViewById(R.id.msg);
        setData.img = view.findViewById(R.id.imageView2);

        setData.userName.setText(users.get(position));
        setData.msg.setText(messages.get(position));
        new DownloadImage(setData.img).execute(usrImg.get(position));
        return view;
    }

    public class SetData {
        TextView userName, msg;
        ImageView img;
    }
}
