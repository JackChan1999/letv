package cn.com.iresearch.vvtracker.db.d;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class b {
    private static SimpleDateFormat c = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String a;
    private Object b;

    public b(String str, Object obj) {
        this.a = str;
        this.b = obj;
    }

    public final String a() {
        return this.a;
    }

    public final Object b() {
        if ((this.b instanceof Date) || (this.b instanceof java.sql.Date)) {
            return c.format(this.b);
        }
        return this.b;
    }
}
