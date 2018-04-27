package com.example.admin.vkmess.ObjectParameters;


public class Parameters {
    public String body;
    public int user_id;
    public String title;
    public boolean unread;

    public Parameters(String body, int user_id, String title, boolean unread) {
        this.body = body;
        this.title = title;
        this.user_id = user_id;
        this.unread = unread;
    }
}
