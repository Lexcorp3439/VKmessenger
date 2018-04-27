package com.example.admin.vkmess.ObjectParameters;

import java.util.ArrayList;

public class HistoryParam {
    public ArrayList<String> messages;
    public ArrayList<Integer> out;
    public ArrayList<Integer> read_state;

    public HistoryParam(ArrayList<String> messages,
                        ArrayList<Integer> out, ArrayList<Integer> read_state){
        this.messages = messages;
        this.out = out;
        this.read_state = read_state;
    }
}
