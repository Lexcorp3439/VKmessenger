package com.example.admin.vkmess;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.admin.vkmess.Adapters.MessagesAdapter;
import com.example.admin.vkmess.VKLib.VKLib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import static com.example.admin.vkmess.R.layout.dialogs;

public class Dialogs extends Activity {
//    ArrayList<String> messages;
//    ArrayList<String> users;
//    ArrayList<Integer> readState;
//    ArrayList<String> usrImg;
    int id = 0;

    EditText text;
    ImageButton send;
    ListView listView;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(dialogs);

        id = getIntent().getIntExtra("id", 0);
        String name = getIntent().getStringExtra("userName");
        String image = getIntent().getStringExtra("userImg");

        send = findViewById(R.id.sendMess);
        text = findViewById(R.id.takeMess);
        listView = findViewById(R.id.dialogsMes);

        VKLib.getDialogHist(id, name, image, getApplicationContext(), listView);

        send.setOnClickListener(v -> {
            if (!Objects.equals(String.valueOf(text.getText()), "")) {
                VKLib.sendMess(id, String.valueOf(text.getText()));
                text.setText("");
                //               VKLib.getDialogHist(id, getApplicationContext());
            }
        });
    }
}