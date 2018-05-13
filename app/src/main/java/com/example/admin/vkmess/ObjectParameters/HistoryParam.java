package com.example.admin.vkmess.ObjectParameters;

import java.util.ArrayList;

public class HistoryParam {
    private ArrayList<String> messages;
    private ArrayList<Integer> out;
    private ArrayList<Integer> readState;

    public HistoryParam(ArrayList<String> messages,
                        ArrayList<Integer> out, ArrayList<Integer> readState) {
        this.messages = messages;
        this.out = out;
        this.readState = readState;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public ArrayList<Integer> getOut() {
        return out;
    }

    public ArrayList<Integer> getReadState() {
        return readState;
    }
}
