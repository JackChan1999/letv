package u.aly;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: ClientStats */
public class ar implements Serializable, Cloneable, bz<ar, e> {
    public static final Map<e, cl> d;
    private static final dd e = new dd("ClientStats");
    private static final ct f = new ct("successful_requests", (byte) 8, (short) 1);
    private static final ct g = new ct("failed_requests", (byte) 8, (short) 2);
    private static final ct h = new ct("last_request_spent_ms", (byte) 8, (short) 3);
    private static final Map<Class<? extends dg>, dh> i = new HashMap();
    private static final int j = 0;
    private static final int k = 1;
    private static final int l = 2;
    public int a;
    public int b;
    public int c;
    private byte m;
    private e[] n;

    /* compiled from: ClientStats */
    private static class a extends di<ar> {
        private a() {
        }

        public /* synthetic */ void a(cy cyVar, bz bzVar) throws cf {
            b(cyVar, (ar) bzVar);
        }

        public /* synthetic */ void b(cy cyVar, bz bzVar) throws cf {
            a(cyVar, (ar) bzVar);
        }

        public void a(cy cyVar, ar arVar) throws cf {
            cyVar.j();
            while (true) {
                ct l = cyVar.l();
                if (l.b == (byte) 0) {
                    cyVar.k();
                    if (!arVar.e()) {
                        throw new cz("Required field 'successful_requests' was not found in serialized data! Struct: " + toString());
                    } else if (arVar.i()) {
                        arVar.m();
                        return;
                    } else {
                        throw new cz("Required field 'failed_requests' was not found in serialized data! Struct: " + toString());
                    }
                }
                switch (l.c) {
                    case (short) 1:
                        if (l.b != (byte) 8) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        arVar.a = cyVar.w();
                        arVar.a(true);
                        break;
                    case (short) 2:
                        if (l.b != (byte) 8) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        arVar.b = cyVar.w();
                        arVar.b(true);
                        break;
                    case (short) 3:
                        if (l.b != (byte) 8) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        arVar.c = cyVar.w();
                        arVar.c(true);
                        break;
                    default:
                        db.a(cyVar, l.b);
                        break;
                }
                cyVar.m();
            }
        }

        public void b(cy cyVar, ar arVar) throws cf {
            arVar.m();
            cyVar.a(ar.e);
            cyVar.a(ar.f);
            cyVar.a(arVar.a);
            cyVar.c();
            cyVar.a(ar.g);
            cyVar.a(arVar.b);
            cyVar.c();
            if (arVar.l()) {
                cyVar.a(ar.h);
                cyVar.a(arVar.c);
                cyVar.c();
            }
            cyVar.d();
            cyVar.b();
        }
    }

    /* compiled from: ClientStats */
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

    /* compiled from: ClientStats */
    private static class c extends dj<ar> {
        private c() {
        }

        public void a(cy cyVar, ar arVar) throws cf {
            de deVar = (de) cyVar;
            deVar.a(arVar.a);
            deVar.a(arVar.b);
            BitSet bitSet = new BitSet();
            if (arVar.l()) {
                bitSet.set(0);
            }
            deVar.a(bitSet, 1);
            if (arVar.l()) {
                deVar.a(arVar.c);
            }
        }

        public void b(cy cyVar, ar arVar) throws cf {
            de deVar = (de) cyVar;
            arVar.a = deVar.w();
            arVar.a(true);
            arVar.b = deVar.w();
            arVar.b(true);
            if (deVar.b(1).get(0)) {
                arVar.c = deVar.w();
                arVar.c(true);
            }
        }
    }

    /* compiled from: ClientStats */
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

    /* compiled from: ClientStats */
    public enum e implements cg {
        SUCCESSFUL_REQUESTS((short) 1, "successful_requests"),
        FAILED_REQUESTS((short) 2, "failed_requests"),
        LAST_REQUEST_SPENT_MS((short) 3, "last_request_spent_ms");
        
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
                    return SUCCESSFUL_REQUESTS;
                case 2:
                    return FAILED_REQUESTS;
                case 3:
                    return LAST_REQUEST_SPENT_MS;
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
        return e(i);
    }

    public /* synthetic */ bz g() {
        return a();
    }

    static {
        i.put(di.class, new b());
        i.put(dj.class, new d());
        Map enumMap = new EnumMap(e.class);
        enumMap.put(e.SUCCESSFUL_REQUESTS, new cl("successful_requests", (byte) 1, new cm((byte) 8)));
        enumMap.put(e.FAILED_REQUESTS, new cl("failed_requests", (byte) 1, new cm((byte) 8)));
        enumMap.put(e.LAST_REQUEST_SPENT_MS, new cl("last_request_spent_ms", (byte) 2, new cm((byte) 8)));
        d = Collections.unmodifiableMap(enumMap);
        cl.a(ar.class, d);
    }

    public ar() {
        this.m = (byte) 0;
        this.n = new e[]{e.LAST_REQUEST_SPENT_MS};
        this.a = 0;
        this.b = 0;
    }

    public ar(int i, int i2) {
        this();
        this.a = i;
        a(true);
        this.b = i2;
        b(true);
    }

    public ar(ar arVar) {
        this.m = (byte) 0;
        this.n = new e[]{e.LAST_REQUEST_SPENT_MS};
        this.m = arVar.m;
        this.a = arVar.a;
        this.b = arVar.b;
        this.c = arVar.c;
    }

    public ar a() {
        return new ar(this);
    }

    public void b() {
        this.a = 0;
        this.b = 0;
        c(false);
        this.c = 0;
    }

    public int c() {
        return this.a;
    }

    public ar a(int i) {
        this.a = i;
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

    public int f() {
        return this.b;
    }

    public ar c(int i) {
        this.b = i;
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

    public int j() {
        return this.c;
    }

    public ar d(int i) {
        this.c = i;
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

    public e e(int i) {
        return e.a(i);
    }

    public void a(cy cyVar) throws cf {
        ((dh) i.get(cyVar.D())).b().b(cyVar, this);
    }

    public void b(cy cyVar) throws cf {
        ((dh) i.get(cyVar.D())).b().a(cyVar, this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("ClientStats(");
        stringBuilder.append("successful_requests:");
        stringBuilder.append(this.a);
        stringBuilder.append(", ");
        stringBuilder.append("failed_requests:");
        stringBuilder.append(this.b);
        if (l()) {
            stringBuilder.append(", ");
            stringBuilder.append("last_request_spent_ms:");
            stringBuilder.append(this.c);
        }
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
