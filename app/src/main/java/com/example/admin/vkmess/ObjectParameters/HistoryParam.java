package com.example.admin.vkmess.ObjectParameters;

import java.util.ArrayList;

public class HistoryParam {
    private ArrayList<String> messages;
    private ArrayList<Integer> out;
    private ArrayList<Integer> read_state;

    public HistoryParam(ArrayList<String> messages,
                        ArrayList<Integer> out, ArrayList<Integer> read_state){
        this.messages = messages;
        this.out = out;
        this.read_state = read_state;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public ArrayList<Integer> getOut() {
        return out;
    }

    public ArrayList<Integer> getReadState() {
        return read_state;
    }
}
