package com.example.admin.vkmess.Fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.admin.vkmess.R;

import static com.example.admin.vkmess.VKLib.VKLib.getDialogs;


@SuppressLint("ValidFragment")
public class FragmentMessage extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    private ListView listView;
    private int msgCount = 20;

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

        ImageButton showMessage = view.findViewById(R.id.showText);
        showMessage.setOnClickListener(v -> getDialogs(msgCount, getActivity(), listView));
        return view;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
