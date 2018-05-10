package com.example.admin.vkmess;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class InitActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        proceed();
    }

    private void proceed() {
        Intent i = new Intent(InitActivity.this, MainActivity.class);
        startActivity(i);
    }
}