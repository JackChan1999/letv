package com.google.gson.jpush;

public class aa extends RuntimeException {
    public aa(String str) {
        super(str);
    }

    public aa(String str, Throwable th) {
        super(str, th);
    }

    public aa(Throwable th) {
        super(th);
    }
}
