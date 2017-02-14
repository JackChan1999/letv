package com.letv.zxing.ex;

public class ParseResultEntity {
    public static final int TEXT = 2;
    public static final int URI = 1;
    private String displayResult;
    private String text;
    private int type;

    public String getDisplayResult() {
        return this.displayResult;
    }

    public void setDisplayResult(String displayResult) {
        this.displayResult = displayResult;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
