package cn.com.iresearch.vvtracker.a;

import java.util.HashMap;

public final class c {
    private static d a = null;
    private static Object b = new Object();
    private static d c = null;
    private static Object d = new Object();

    static {
        Object obj = new Object();
        HashMap hashMap = new HashMap();
        obj = new Object();
    }

    public static d a() {
        d dVar;
        synchronized (b) {
            if (a == null) {
                a = new d(5, 5);
            }
            dVar = a;
        }
        return dVar;
    }

    public static d b() {
        d dVar;
        synchronized (d) {
            if (c == null) {
                c = new d(2, 2);
            }
            dVar = c;
        }
        return dVar;
    }
}
