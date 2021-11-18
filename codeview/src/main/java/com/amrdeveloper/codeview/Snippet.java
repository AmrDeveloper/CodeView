package com.amrdeveloper.codeview;


public class Snippet implements Code {

    private final String title;
    private final String prefix;
    private final String body;

    public Snippet(String title, String body) {
        this.title = title;
        this.prefix = title;
        this.body = body;
    }

    public Snippet(String title, String prefix, String body) {
        this.title = title;
        this.prefix = prefix;
        this.body = body;
    }

    @Override
    public String getCodeTitle() {
        return title;
    }

    @Override
    public String getCodePrefix() {
        return prefix;
    }

    @Override
    public String getCodeBody() {
        return body;
    }
}
