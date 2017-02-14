package com.google.gson.jpush.internal;

import java.io.Writer;

final class ah extends Writer {
    private final Appendable a;
    private final ai b;

    private ah(Appendable appendable) {
        this.b = new ai();
        this.a = appendable;
    }

    public final void close() {
    }

    public final void flush() {
    }

    public final void write(int i) {
        this.a.append((char) i);
    }

    public final void write(char[] cArr, int i, int i2) {
        this.b.a = cArr;
        this.a.append(this.b, i, i + i2);
    }
}
