package cn.jpush.b.a.b;

import cn.jpush.b.a.a.b;
import com.google.gson.jpush.annotations.a;
import com.google.gson.jpush.k;
import com.google.gson.jpush.r;

public abstract class q {
    protected static k f = new r().a().b();
    private static final String z;
    @a
    protected long g = 0;
    @a
    protected long h = 0;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = "2@\u0019^\u0019\u0003B\u0014[W\u0004A\u001cR\u0016\tJQ\u0012W";
        r0 = r0.toCharArray();
        r1 = r0.length;
        r2 = 0;
        r3 = 1;
        if (r1 > r3) goto L_0x0027;
    L_0x000b:
        r3 = r0;
        r4 = r2;
        r7 = r1;
        r1 = r0;
        r0 = r7;
    L_0x0010:
        r6 = r1[r2];
        r5 = r4 % 5;
        switch(r5) {
            case 0: goto L_0x0044;
            case 1: goto L_0x0047;
            case 2: goto L_0x004a;
            case 3: goto L_0x004d;
            default: goto L_0x0017;
        };
    L_0x0017:
        r5 = 119; // 0x77 float:1.67E-43 double:5.9E-322;
    L_0x0019:
        r5 = r5 ^ r6;
        r5 = (char) r5;
        r1[r2] = r5;
        r2 = r4 + 1;
        if (r0 != 0) goto L_0x0025;
    L_0x0021:
        r1 = r3;
        r4 = r2;
        r2 = r0;
        goto L_0x0010;
    L_0x0025:
        r1 = r0;
        r0 = r3;
    L_0x0027:
        if (r1 > r2) goto L_0x000b;
    L_0x0029:
        r1 = new java.lang.String;
        r1.<init>(r0);
        r0 = r1.intern();
        z = r0;
        r0 = new com.google.gson.jpush.r;
        r0.<init>();
        r0 = r0.a();
        r0 = r0.b();
        f = r0;
        return;
    L_0x0044:
        r5 = 103; // 0x67 float:1.44E-43 double:5.1E-322;
        goto L_0x0019;
    L_0x0047:
        r5 = 46;
        goto L_0x0019;
    L_0x004a:
        r5 = 113; // 0x71 float:1.58E-43 double:5.6E-322;
        goto L_0x0019;
    L_0x004d:
        r5 = 63;
        goto L_0x0019;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.b.a.b.q.<clinit>():void");
    }

    public static q a(String str, int i) {
        Class cls;
        switch (i) {
            case 1:
                cls = r.class;
                break;
            case 2:
                cls = s.class;
                break;
            case 3:
                cls = v.class;
                break;
            case 4:
                cls = o.class;
                break;
            case 5:
                cls = b.class;
                break;
            case 6:
                cls = i.class;
                break;
            case 7:
                cls = x.class;
                break;
            case 8:
                cls = g.class;
                break;
            case 9:
                cls = n.class;
                break;
            case 10:
                cls = c.class;
                break;
            case 11:
                cls = j.class;
                break;
            case 12:
                cls = w.class;
                break;
            case 18:
                cls = a.class;
                break;
            case 19:
                cls = h.class;
                break;
            case 23:
                cls = u.class;
                break;
            case 31:
                cls = f.class;
                break;
            case 32:
                cls = m.class;
                break;
            case 33:
                cls = e.class;
                break;
            case 34:
                cls = l.class;
                break;
            case 35:
                cls = d.class;
                break;
            case 36:
                cls = k.class;
                break;
            default:
                System.out.println(new StringBuilder(z).append(i).toString());
                return null;
        }
        return (q) f.a(str, cls);
    }

    public final b a(String str) {
        return new b(this.g, a(this.h, str));
    }

    abstract p a(long j, String str);
}
