package com.example.admin.vkmess.Fragments;

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
import com.example.admin.vkmess.VKLib.VKLib;

public class FragmentFriends extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    private ListView listView;


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_friends, container, false);
        listView = view.findViewById(R.id.listFriends);

        VKLib.getFriends(getContext(), getActivity(), listView);

        ImageButton update = view.findViewById(R.id.showFriends);
        update.setOnClickListener(v -> {
            VKLib.getFriends(getContext(), getActivity(), listView);
        });
        return view;
    }
}
