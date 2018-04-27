package com.example.admin.vkmess.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.vkmess.R;
import com.example.admin.vkmess.VKLib.VKLib;

import java.util.ArrayList;

public class FriendsAdapter extends BaseAdapter {

    private ArrayList<Bitmap>  images;
    private ArrayList<String>  users;
    private ArrayList<Integer> id;
    private Context context;


    public FriendsAdapter(Context context, ArrayList<String>  users, ArrayList<Bitmap> images, ArrayList<Integer> id){
        this.users = users;
        this.context = context;
        this.images = images;
        this.id = id;
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
    public View getView(int position, View convertView, ViewGroup parent){
        SetData setData = new SetData();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        assert inflater != null;
        @SuppressLint({"ViewHolder", "InflateParams"})
        View view = inflater.inflate(R.layout.friends_list, null);

        setData.user_name = view.findViewById(R.id.users);
        setData.image = view.findViewById(R.id.image);

        setData.user_name.setText(users.get(position));
        setData.image.setImageBitmap(images.get(position));

        view.setOnClickListener(v -> {
            final int user_id = id.get(position);
            VKLib.getDialogHist(user_id, context);
        });
        return view;
    }


    private class SetData {
        TextView user_name;
        ImageView image;
    }
}
