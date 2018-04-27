package com.example.admin.vkmess.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.vkmess.R;

import java.util.ArrayList;

public class MessagesAdapter extends BaseAdapter{
    private ArrayList<String>  messages;
    private ArrayList<String>  users;
    private ArrayList<Integer>  read_state;
    private Context context;

    public MessagesAdapter(Context context, ArrayList<String> messages,
                           ArrayList<String> users, ArrayList<Integer>   read_state){
        this.users = users;
        this.messages = messages;
        this.context = context;
        this.read_state = read_state;
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

        System.out.println(messages.get(0));

        assert inflater != null;
        @SuppressLint({"ViewHolder", "InflateParams"}) View view = inflater.inflate(R.layout.user_mess, null);

        setData.user_name = view.findViewById(R.id.user1);
        setData.user_name.setText(users.get(position));

        setData.msg = view.findViewById(R.id.user2);
        setData.msg.setText(messages.get(position));

        return view;
    }

    public class SetData {
        TextView user_name, msg;
    }
}
