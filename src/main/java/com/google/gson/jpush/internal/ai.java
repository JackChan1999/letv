package com.google.gson.jpush.internal;

final class ai implements CharSequence {
    char[] a;

    ai() {
    }

    public final char charAt(int i) {
        return this.a[i];
    }

    public final int length() {
        return this.a.length;
    }

    public final CharSequence subSequence(int i, int i2) {
        return new String(this.a, i, i2 - i);
    }
}
