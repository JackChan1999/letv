package u.aly;

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

/* compiled from: IdSnapshot */
public class ba implements Serializable, Cloneable, bz<ba, e> {
    public static final Map<e, cl> d;
    private static final dd e = new dd("IdSnapshot");
    private static final ct f = new ct("identity", (byte) 11, (short) 1);
    private static final ct g = new ct("ts", (byte) 10, (short) 2);
    private static final ct h = new ct("version", (byte) 8, (short) 3);
    private static final Map<Class<? extends dg>, dh> i = new HashMap();
    private static final int j = 0;
    private static final int k = 1;
    public String a;
    public long b;
    public int c;
    private byte l;

    /* compiled from: IdSnapshot */
    private static class a extends di<ba> {
        private a() {
        }

        public /* synthetic */ void a(cy cyVar, bz bzVar) throws cf {
            b(cyVar, (ba) bzVar);
        }

        public /* synthetic */ void b(cy cyVar, bz bzVar) throws cf {
            a(cyVar, (ba) bzVar);
        }

        public void a(cy cyVar, ba baVar) throws cf {
            cyVar.j();
            while (true) {
                ct l = cyVar.l();
                if (l.b == (byte) 0) {
                    cyVar.k();
                    if (!baVar.i()) {
                        throw new cz("Required field 'ts' was not found in serialized data! Struct: " + toString());
                    } else if (baVar.l()) {
                        baVar.m();
                        return;
                    } else {
                        throw new cz("Required field 'version' was not found in serialized data! Struct: " + toString());
                    }
                }
                switch (l.c) {
                    case (short) 1:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        baVar.a = cyVar.z();
                        baVar.a(true);
                        break;
                    case (short) 2:
                        if (l.b != (byte) 10) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        baVar.b = cyVar.x();
                        baVar.b(true);
                        break;
                    case (short) 3:
                        if (l.b != (byte) 8) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        baVar.c = cyVar.w();
                        baVar.c(true);
                        break;
                    default:
                        db.a(cyVar, l.b);
                        break;
                }
                cyVar.m();
            }
        }

        public void b(cy cyVar, ba baVar) throws cf {
            baVar.m();
            cyVar.a(ba.e);
            if (baVar.a != null) {
                cyVar.a(ba.f);
                cyVar.a(baVar.a);
                cyVar.c();
            }
            cyVar.a(ba.g);
            cyVar.a(baVar.b);
            cyVar.c();
            cyVar.a(ba.h);
            cyVar.a(baVar.c);
            cyVar.c();
            cyVar.d();
            cyVar.b();
        }
    }

    /* compiled from: IdSnapshot */
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

    /* compiled from: IdSnapshot */
    private static class c extends dj<ba> {
        private c() {
        }

        public void a(cy cyVar, ba baVar) throws cf {
            de deVar = (de) cyVar;
            deVar.a(baVar.a);
            deVar.a(baVar.b);
            deVar.a(baVar.c);
        }

        public void b(cy cyVar, ba baVar) throws cf {
            de deVar = (de) cyVar;
            baVar.a = deVar.z();
            baVar.a(true);
            baVar.b = deVar.x();
            baVar.b(true);
            baVar.c = deVar.w();
            baVar.c(true);
        }
    }

    /* compiled from: IdSnapshot */
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

    /* compiled from: IdSnapshot */
    public enum e implements cg {
        IDENTITY((short) 1, "identity"),
        TS((short) 2, "ts"),
        VERSION((short) 3, "version");
        
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
                    return IDENTITY;
                case 2:
                    return TS;
                case 3:
                    return VERSION;
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
        return c(i);
    }

    public /* synthetic */ bz g() {
        return a();
    }

    static {
        i.put(di.class, new b());
        i.put(dj.class, new d());
        Map enumMap = new EnumMap(e.class);
        enumMap.put(e.IDENTITY, new cl("identity", (byte) 1, new cm((byte) 11)));
        enumMap.put(e.TS, new cl("ts", (byte) 1, new cm((byte) 10)));
        enumMap.put(e.VERSION, new cl("version", (byte) 1, new cm((byte) 8)));
        d = Collections.unmodifiableMap(enumMap);
        cl.a(ba.class, d);
    }

    public ba() {
        this.l = (byte) 0;
    }

    public ba(String str, long j, int i) {
        this();
        this.a = str;
        this.b = j;
        b(true);
        this.c = i;
        c(true);
    }

    public ba(ba baVar) {
        this.l = (byte) 0;
        this.l = baVar.l;
        if (baVar.e()) {
            this.a = baVar.a;
        }
        this.b = baVar.b;
        this.c = baVar.c;
    }

    public ba a() {
        return new ba(this);
    }

    public void b() {
        this.a = null;
        b(false);
        this.b = 0;
        c(false);
        this.c = 0;
    }

    public String c() {
        return this.a;
    }

    public ba a(String str) {
        this.a = str;
        return this;
    }

    public void d() {
        this.a = null;
    }

    public boolean e() {
        return this.a != null;
    }

    public void a(boolean z) {
        if (!z) {
            this.a = null;
        }
    }

    public long f() {
        return this.b;
    }

    public ba a(long j) {
        this.b = j;
        b(true);
        return this;
    }

    public void h() {
        this.l = bw.b(this.l, 0);
    }

    public boolean i() {
        return bw.a(this.l, 0);
    }

    public void b(boolean z) {
        this.l = bw.a(this.l, 0, z);
    }

    public int j() {
        return this.c;
    }

    public ba a(int i) {
        this.c = i;
        c(true);
        return this;
    }

    public void k() {
        this.l = bw.b(this.l, 1);
    }

    public boolean l() {
        return bw.a(this.l, 1);
    }

    public void c(boolean z) {
        this.l = bw.a(this.l, 1, z);
    }

    public e c(int i) {
        return e.a(i);
    }

    public void a(cy cyVar) throws cf {
        ((dh) i.get(cyVar.D())).b().b(cyVar, this);
    }

    public void b(cy cyVar) throws cf {
        ((dh) i.get(cyVar.D())).b().a(cyVar, this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("IdSnapshot(");
        stringBuilder.append("identity:");
        if (this.a == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.a);
        }
        stringBuilder.append(", ");
        stringBuilder.append("ts:");
        stringBuilder.append(this.b);
        stringBuilder.append(", ");
        stringBuilder.append("version:");
        stringBuilder.append(this.c);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public void m() throws cf {
        if (this.a == null) {
            throw new cz("Required field 'identity' was not present! Struct: " + toString());
        }
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
            this.l = (byte) 0;
            a(new cs(new dk((InputStream) objectInputStream)));
        } catch (cf e) {
            throw new IOException(e.getMessage());
        }
    }
}
