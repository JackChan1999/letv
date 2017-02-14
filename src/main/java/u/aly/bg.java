package u.aly;

import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: Location */
public class bg implements Serializable, Cloneable, bz<bg, e> {
    public static final Map<e, cl> d;
    private static final dd e = new dd(HttpRequest.HEADER_LOCATION);
    private static final ct f = new ct("lat", (byte) 4, (short) 1);
    private static final ct g = new ct("lng", (byte) 4, (short) 2);
    private static final ct h = new ct("ts", (byte) 10, (short) 3);
    private static final Map<Class<? extends dg>, dh> i = new HashMap();
    private static final int j = 0;
    private static final int k = 1;
    private static final int l = 2;
    public double a;
    public double b;
    public long c;
    private byte m;

    /* compiled from: Location */
    private static class a extends di<bg> {
        private a() {
        }

        public /* synthetic */ void a(cy cyVar, bz bzVar) throws cf {
            b(cyVar, (bg) bzVar);
        }

        public /* synthetic */ void b(cy cyVar, bz bzVar) throws cf {
            a(cyVar, (bg) bzVar);
        }

        public void a(cy cyVar, bg bgVar) throws cf {
            cyVar.j();
            while (true) {
                ct l = cyVar.l();
                if (l.b == (byte) 0) {
                    cyVar.k();
                    if (!bgVar.e()) {
                        throw new cz("Required field 'lat' was not found in serialized data! Struct: " + toString());
                    } else if (!bgVar.i()) {
                        throw new cz("Required field 'lng' was not found in serialized data! Struct: " + toString());
                    } else if (bgVar.l()) {
                        bgVar.m();
                        return;
                    } else {
                        throw new cz("Required field 'ts' was not found in serialized data! Struct: " + toString());
                    }
                }
                switch (l.c) {
                    case (short) 1:
                        if (l.b != (byte) 4) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        bgVar.a = cyVar.y();
                        bgVar.a(true);
                        break;
                    case (short) 2:
                        if (l.b != (byte) 4) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        bgVar.b = cyVar.y();
                        bgVar.b(true);
                        break;
                    case (short) 3:
                        if (l.b != (byte) 10) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        bgVar.c = cyVar.x();
                        bgVar.c(true);
                        break;
                    default:
                        db.a(cyVar, l.b);
                        break;
                }
                cyVar.m();
            }
        }

        public void b(cy cyVar, bg bgVar) throws cf {
            bgVar.m();
            cyVar.a(bg.e);
            cyVar.a(bg.f);
            cyVar.a(bgVar.a);
            cyVar.c();
            cyVar.a(bg.g);
            cyVar.a(bgVar.b);
            cyVar.c();
            cyVar.a(bg.h);
            cyVar.a(bgVar.c);
            cyVar.c();
            cyVar.d();
            cyVar.b();
        }
    }

    /* compiled from: Location */
    private static class b implements dh {
        private b() {
        }

        public /* synthetic */ dg b() {
            return a();
        }

        public a a() {
            return new a();
        }
    }

    /* compiled from: Location */
    private static class c extends dj<bg> {
        private c() {
        }

        public void a(cy cyVar, bg bgVar) throws cf {
            de deVar = (de) cyVar;
            deVar.a(bgVar.a);
            deVar.a(bgVar.b);
            deVar.a(bgVar.c);
        }

        public void b(cy cyVar, bg bgVar) throws cf {
            de deVar = (de) cyVar;
            bgVar.a = deVar.y();
            bgVar.a(true);
            bgVar.b = deVar.y();
            bgVar.b(true);
            bgVar.c = deVar.x();
            bgVar.c(true);
        }
    }

    /* compiled from: Location */
    private static class d implements dh {
        private d() {
        }

        public /* synthetic */ dg b() {
            return a();
        }

        public c a() {
            return new c();
        }
    }

    /* compiled from: Location */
    public enum e implements cg {
        LAT((short) 1, "lat"),
        LNG((short) 2, "lng"),
        TS((short) 3, "ts");
        
        private static final Map<String, e> d = null;
        private final short e;
        private final String f;

        static {
            d = new HashMap();
            Iterator it = EnumSet.allOf(e.class).iterator();
            while (it.hasNext()) {
                e eVar = (e) it.next();
                d.put(eVar.b(), eVar);
            }
        }

        public static e a(int i) {
            switch (i) {
                case 1:
                    return LAT;
                case 2:
                    return LNG;
                case 3:
                    return TS;
                default:
                    return null;
            }
        }

        public static e b(int i) {
            e a = a(i);
            if (a != null) {
                return a;
            }
            throw new IllegalArgumentException("Field " + i + " doesn't exist!");
        }

        public static e a(String str) {
            return (e) d.get(str);
        }

        private e(short s, String str) {
            this.e = s;
            this.f = str;
        }

        public short a() {
            return this.e;
        }

        public String b() {
            return this.f;
        }
    }

    public /* synthetic */ cg b(int i) {
        return a(i);
    }

    public /* synthetic */ bz g() {
        return a();
    }

    static {
        i.put(di.class, new b());
        i.put(dj.class, new d());
        Map enumMap = new EnumMap(e.class);
        enumMap.put(e.LAT, new cl("lat", (byte) 1, new cm((byte) 4)));
        enumMap.put(e.LNG, new cl("lng", (byte) 1, new cm((byte) 4)));
        enumMap.put(e.TS, new cl("ts", (byte) 1, new cm((byte) 10)));
        d = Collections.unmodifiableMap(enumMap);
        cl.a(bg.class, d);
    }

    public bg() {
        this.m = (byte) 0;
    }

    public bg(double d, double d2, long j) {
        this();
        this.a = d;
        a(true);
        this.b = d2;
        b(true);
        this.c = j;
        c(true);
    }

    public bg(bg bgVar) {
        this.m = (byte) 0;
        this.m = bgVar.m;
        this.a = bgVar.a;
        this.b = bgVar.b;
        this.c = bgVar.c;
    }

    public bg a() {
        return new bg(this);
    }

    public void b() {
        a(false);
        this.a = 0.0d;
        b(false);
        this.b = 0.0d;
        c(false);
        this.c = 0;
    }

    public double c() {
        return this.a;
    }

    public bg a(double d) {
        this.a = d;
        a(true);
        return this;
    }

    public void d() {
        this.m = bw.b(this.m, 0);
    }

    public boolean e() {
        return bw.a(this.m, 0);
    }

    public void a(boolean z) {
        this.m = bw.a(this.m, 0, z);
    }

    public double f() {
        return this.b;
    }

    public bg b(double d) {
        this.b = d;
        b(true);
        return this;
    }

    public void h() {
        this.m = bw.b(this.m, 1);
    }

    public boolean i() {
        return bw.a(this.m, 1);
    }

    public void b(boolean z) {
        this.m = bw.a(this.m, 1, z);
    }

    public long j() {
        return this.c;
    }

    public bg a(long j) {
        this.c = j;
        c(true);
        return this;
    }

    public void k() {
        this.m = bw.b(this.m, 2);
    }

    public boolean l() {
        return bw.a(this.m, 2);
    }

    public void c(boolean z) {
        this.m = bw.a(this.m, 2, z);
    }

    public e a(int i) {
        return e.a(i);
    }

    public void a(cy cyVar) throws cf {
        ((dh) i.get(cyVar.D())).b().b(cyVar, this);
    }

    public void b(cy cyVar) throws cf {
        ((dh) i.get(cyVar.D())).b().a(cyVar, this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Location(");
        stringBuilder.append("lat:");
        stringBuilder.append(this.a);
        stringBuilder.append(", ");
        stringBuilder.append("lng:");
        stringBuilder.append(this.b);
        stringBuilder.append(", ");
        stringBuilder.append("ts:");
        stringBuilder.append(this.c);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public void m() throws cf {
    }

    private void a(ObjectOutputStream objectOutputStream) throws IOException {
        try {
            b(new cs(new dk((OutputStream) objectOutputStream)));
        } catch (cf e) {
            throw new IOException(e.getMessage());
        }
    }

    private void a(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        try {
            this.m = (byte) 0;
            a(new cs(new dk((InputStream) objectInputStream)));
        } catch (cf e) {
            throw new IOException(e.getMessage());
        }
    }
}
