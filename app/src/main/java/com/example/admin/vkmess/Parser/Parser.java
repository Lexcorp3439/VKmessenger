package com.example.admin.vkmess.Parser;

import android.util.JsonReader;

import java.io.IOException;

public interface Parser {

    void parse(JsonReader json) throws IOException;
}
