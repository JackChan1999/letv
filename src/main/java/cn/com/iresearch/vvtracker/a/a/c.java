package cn.com.iresearch.vvtracker.a.a;

import java.util.Arrays;

final class c {
    int a;
    byte[] b;
    int c;
    int d;
    boolean e;
    int f;
    int g;

    c() {
    }

    public final String toString() {
        return String.format("%s[buffer=%s, currentLinePos=%s, eof=%s, ibitWorkArea=%s, lbitWorkArea=%s, modulus=%s, pos=%s, readPos=%s]", new Object[]{getClass().getSimpleName(), Arrays.toString(this.b), Integer.valueOf(this.f), Boolean.valueOf(this.e), Integer.valueOf(this.a), Long.valueOf(0), Integer.valueOf(this.g), Integer.valueOf(this.c), Integer.valueOf(this.d)});
    }
}
