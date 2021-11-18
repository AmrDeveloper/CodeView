package com.amrdeveloper.codeview;

public class Keyword implements Code {

    private final String title;
    private final String prefix;

    public Keyword(String title) {
        this.title = title;
        this.prefix = title;
    }

    public Keyword(String title, String prefix) {
        this.title = title;
        this.prefix = prefix;
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
        return prefix;
    }
}
