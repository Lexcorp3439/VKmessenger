package com.example.admin.vkmess.ObjectParameters;

public class LPElem {
    private int ts;
    private String key;
    private String server;
    private int pts;

    public LPElem(int ts, String key, String server, int pts) {
        this.ts = ts;
        this.key = key;
        this.server = server;
        this.pts = pts;
    }

    public int getTs() {
        return ts;
    }

    public String getKey() {
        return key;
    }

    public String getServer() {
        return server;
    }

    public int getPts() {
        return pts;
    }
}
