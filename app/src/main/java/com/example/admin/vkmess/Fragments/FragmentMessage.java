package com.example.admin.vkmess.Fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.admin.vkmess.BodyMess;
import com.example.admin.vkmess.ObjectParameters.LPElem;
import com.example.admin.vkmess.Parser.LongPoll;
import com.example.admin.vkmess.Parser.LongPollRequest;
import com.example.admin.vkmess.R;
import com.example.admin.vkmess.VKLib.VKLib;
import com.example.admin.vkmess.VKLib.VKrequest;

import java.io.IOException;
import java.util.Objects;

import static com.example.admin.vkmess.VKLib.VKLib.getDialogs;


@SuppressLint("ValidFragment")
public class FragmentMessage extends Fragment implements NavigationView.OnNavigationItemSelectedListener{

    private ListView listView;
    private int msgCount = 20;
    int ts = BodyMess.ts;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_message, container, false);
        listView = view.findViewById(R.id.listView);



        getDialogs(msgCount, getActivity(), listView);
        ImageButton showMore = view.findViewById(R.id.showMore);
        showMore.setOnClickListener(v -> {
            msgCount += 10;
            getDialogs(msgCount, getActivity(), listView);
        });

//        VKrequest.lamda(() -> {
//            String urlLog = "https://api.vk.com/method/messages.getLongPollServer?lp_version=3" +
//                    "&need_pts=0&v=5.74&access_token=" + BodyMess.TOKEN;
//
//            try {
//                LPElem elem = new LongPoll(Objects.requireNonNull(VKrequest.getJSON(urlLog))).elem;
//                ts = elem.ts;
//
//                while (true){
//
//                    String longPoll = "https://" + elem.server + "?act=a_check&key=" +
//                            elem.key + "&ts=" + ts + "&wait=90&mode=2&version=3";
//                    JsonReader json = VKrequest.getJSON(longPoll);
//                    LongPollRequest request = new LongPollRequest(json);
//                    ts = request.ts;
//                    if (request.reload)
//                        VKLib.getDialogs(msgCount, getActivity(), listView);
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            });

        ImageButton showMessage = view.findViewById(R.id.showText);
        showMessage.setOnClickListener(v -> getDialogs(msgCount, getActivity(), listView));
        return view;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
