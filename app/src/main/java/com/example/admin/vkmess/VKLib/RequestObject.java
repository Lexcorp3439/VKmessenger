package com.example.admin.vkmess.VKLib;

import com.example.admin.vkmess.Parser.Parser;

public class RequestObject {
    private String url;
    private Parser parserClass;

    RequestObject(String url, Parser parserClass) {
        this.url = url;
        this.parserClass = parserClass;
    }

    public String getUrl() {
        return url;
    }

    public Parser getParserClass() {
        return parserClass;
    }
}
