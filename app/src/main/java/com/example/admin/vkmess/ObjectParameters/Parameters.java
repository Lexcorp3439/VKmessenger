package com.example.admin.vkmess.ObjectParameters;


public class Parameters {
    private String body;
    private int user_id;
    private String title;
    private boolean unread;

    public Parameters(String body, int user_id, String title, boolean unread) {
        this.body = body;
        this.title = title;
        this.user_id = user_id;
        this.unread = unread;
    }

    public String getBody() {
        return body;
    }

    public int getUserId() {
        return user_id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isUnread() {
        return unread;
    }
}
