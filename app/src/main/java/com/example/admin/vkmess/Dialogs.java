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


public class Dialogs extends Activity {

    public ArrayList<String> messages;
    public ArrayList<String> users;
    public ArrayList<Integer> read_state;
    int id = 0;

    EditText text;
    ImageButton send;
    ListView listView;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.dialogs);

        messages = getIntent().getStringArrayListExtra("messages");
        users = getIntent().getStringArrayListExtra("out");
        read_state = getIntent().getIntegerArrayListExtra("read_state");
        Collections.reverse(messages);
        Collections.reverse(users);
        Collections.reverse(read_state);

        id = getIntent().getIntExtra("id", 0);

        send = findViewById(R.id.sendMess);
        text = findViewById(R.id.takeMess);
        listView = findViewById(R.id.dialogsMes);

        listView.setAdapter(new MessagesAdapter(this, messages, users, read_state));
        listView.setSelection(users.size() - 1);

        send.setOnClickListener(v -> {
            if (!Objects.equals(String.valueOf(text.getText()), "")) {
                VKLib.sendMess(id, String.valueOf(text.getText()));
                text.setText("");
                //               VKLib.getDialogHist(id, getApplicationContext());
            }
        });
    }
}