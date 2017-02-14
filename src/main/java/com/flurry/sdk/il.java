package com.flurry.sdk;

public abstract class il {
    private long a;
    private boolean b;
    private int c;
    private String d;
    private String e;

    protected il() {
    }

    public long d() {
        return this.a;
    }

    public boolean e() {
        return this.b;
    }

    public int f() {
        return this.c;
    }

    public String g() {
        return this.d;
    }

    public String h() {
        return this.e;
    }

    public void a(long j) {
        this.a = j;
    }

    public void a(boolean z) {
        this.b = z;
    }

    public void a(int i) {
        this.c = i;
    }

    public void i() {
        this.c++;
    }

    public void a(String str) {
        this.d = str;
        this.e = str;
    }

    public void b(String str) {
        this.d = str;
    }

    public void c(String str) {
        this.e = str;
    }
}
