package com.example.admin.vkmess;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.admin.vkmess.Adapters.MessagesAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;



public class Dialogs extends Activity {

    public ArrayList<String> messages;
    public ArrayList<String> users;
    public ArrayList<Integer>  read_state;
    int id = 0;
    String TOKEN;

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
        id = getIntent().getIntExtra("id", 0);
        TOKEN = BodyMess.TOKEN;

        Arrays.sort(messages.toArray(), Collections.reverseOrder());

        send = findViewById(R.id.sendMess);
        text = findViewById(R.id.takeMess);
        listView = findViewById(R.id.dialogsMes);

        listView.setAdapter(new MessagesAdapter(this, messages, users, read_state));


//        send.setOnClickListener(v -> {
//                VKRequest request = new VKRequest("messages.send", VKParameters.from(VKApiConst.USER_ID,
//                        id, VKApiConst.MESSAGE, text.getText().toString()));
//                request.executeWithListener(new VKRequest.VKRequestListener() {
//                    @Override
//                    public void onComplete(VKResponse response) {
//                        super.onComplete(response);
//                        text.setText("");
//                        Toast.makeText(Dialogs.this, "Complete", Toast.LENGTH_SHORT).show();
//                }
//            });
//        });
    }
}