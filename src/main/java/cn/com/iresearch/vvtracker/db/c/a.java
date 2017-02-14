package cn.com.iresearch.vvtracker.db.c;

import java.util.LinkedList;

public final class a {
    private String a;
    private LinkedList<Object> b;

    public final String a() {
        return this.a;
    }

    public final void a(String str) {
        this.a = str;
    }

    public final Object[] b() {
        if (this.b != null) {
            return this.b.toArray();
        }
        return null;
    }

    public final void a(Object obj) {
        if (this.b == null) {
            this.b = new LinkedList();
        }
        this.b.add(obj);
    }
}
