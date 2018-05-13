package com.example.admin.vkmess.VKLib;

import com.example.admin.vkmess.Parser.Parser;

public class RequestObject {
    private String url;
    private Parser clasS;

    public RequestObject(String url, Parser clasS) {
        this.url = url;
        this.clasS = clasS;
    }

    public String getUrl() {
        return url;
    }

    public Parser getClasS() {
        return clasS;
    }
}
