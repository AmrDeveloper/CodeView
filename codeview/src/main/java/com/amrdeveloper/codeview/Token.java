package com.amrdeveloper.codeview;

public class Token {

    private final int start;
    private final int end;

    public Token(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
