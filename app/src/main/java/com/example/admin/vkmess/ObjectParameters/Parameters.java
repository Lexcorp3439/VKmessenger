package com.example.admin.vkmess.ObjectParameters;


public class Parameters {
    private String body;
    private int userId;
    private String title;
    private boolean unread;

    public Parameters(String body, int userId, String title, boolean unread) {
        this.body = body;
        this.title = title;
        this.userId = userId;
        this.unread = unread;
    }

    public String getBody() {
        return body;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public boolean isUnread() {
        return unread;
    }
}
