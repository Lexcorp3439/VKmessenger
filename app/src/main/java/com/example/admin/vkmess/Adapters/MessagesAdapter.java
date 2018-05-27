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

import static com.example.admin.vkmess.VKLib.VKLib.getID;
import static com.example.admin.vkmess.VKLib.VKLib.getImageUsr;
import static com.example.admin.vkmess.VKLib.VKLib.getNameUsr;

public class MessagesAdapter extends BaseAdapter {
    private List<String> messages;
    private String frName;
    private List<Integer> readState;  //Пока не используется, но вижу в перспективе
    private Context context;
    private String frImage;
    private Integer frId;
    private List<Integer> out;

    public MessagesAdapter(List<String> messages, String frName, List<Integer> readState,
                           Context context, String frImage, Integer frId, List<Integer> out) {
        this.messages = messages;
        this.frName = frName;
        this.readState = readState;
        this.context = context;
        this.frImage = frImage;
        this.frId = frId;
        this.out = out;
    }

    @Override
    public int getCount() {
        return out.size();
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
        setData.msg.setText(messages.get(position));

        if (out.get(position) == 1) {
            setData.userName.setText(frName);
            new DownloadImage(setData.img, context, frId).execute(frImage);
        } else {
            setData.userName.setText(getNameUsr());
            new DownloadImage(setData.img, context, getID()).execute(getImageUsr());
        }
        return view;
    }

    public class SetData {
        TextView userName, msg;
        ImageView img;
    }
}
