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

/* compiled from: AppInfo */
public class aq implements Serializable, Cloneable, bz<aq, e> {
    private static final int A = 1;
    public static final Map<e, cl> l;
    private static final dd m = new dd("AppInfo");
    private static final ct n = new ct("key", (byte) 11, (short) 1);
    private static final ct o = new ct("version", (byte) 11, (short) 2);
    private static final ct p = new ct("version_index", (byte) 8, (short) 3);
    private static final ct q = new ct("package_name", (byte) 11, (short) 4);
    private static final ct r = new ct("sdk_type", (byte) 8, (short) 5);
    private static final ct s = new ct("sdk_version", (byte) 11, (short) 6);
    private static final ct t = new ct("channel", (byte) 11, (short) 7);
    private static final ct u = new ct("wrapper_type", (byte) 11, (short) 8);
    private static final ct v = new ct("wrapper_version", (byte) 11, (short) 9);
    private static final ct w = new ct("vertical_type", (byte) 8, (short) 10);
    private static final ct x = new ct("app_signature", (byte) 11, (short) 11);
    private static final Map<Class<? extends dg>, dh> y = new HashMap();
    private static final int z = 0;
    private byte B;
    private e[] C;
    public String a;
    public String b;
    public int c;
    public String d;
    public bm e;
    public String f;
    public String g;
    public String h;
    public String i;
    public int j;
    public String k;

    /* compiled from: AppInfo */
    private static class a extends di<aq> {
        private a() {
        }

        public /* synthetic */ void a(cy cyVar, bz bzVar) throws cf {
            b(cyVar, (aq) bzVar);
        }

        public /* synthetic */ void b(cy cyVar, bz bzVar) throws cf {
            a(cyVar, (aq) bzVar);
        }

        public void a(cy cyVar, aq aqVar) throws cf {
            cyVar.j();
            while (true) {
                ct l = cyVar.l();
                if (l.b == (byte) 0) {
                    cyVar.k();
                    aqVar.K();
                    return;
                }
                switch (l.c) {
                    case (short) 1:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        aqVar.a = cyVar.z();
                        aqVar.a(true);
                        break;
                    case (short) 2:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        aqVar.b = cyVar.z();
                        aqVar.b(true);
                        break;
                    case (short) 3:
                        if (l.b != (byte) 8) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        aqVar.c = cyVar.w();
                        aqVar.c(true);
                        break;
                    case (short) 4:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        aqVar.d = cyVar.z();
                        aqVar.d(true);
                        break;
                    case (short) 5:
                        if (l.b != (byte) 8) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        aqVar.e = bm.a(cyVar.w());
                        aqVar.e(true);
                        break;
                    case (short) 6:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        aqVar.f = cyVar.z();
                        aqVar.f(true);
                        break;
                    case (short) 7:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        aqVar.g = cyVar.z();
                        aqVar.g(true);
                        break;
                    case (short) 8:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        aqVar.h = cyVar.z();
                        aqVar.h(true);
                        break;
                    case (short) 9:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        aqVar.i = cyVar.z();
                        aqVar.i(true);
                        break;
                    case (short) 10:
                        if (l.b != (byte) 8) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        aqVar.j = cyVar.w();
                        aqVar.j(true);
                        break;
                    case (short) 11:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        aqVar.k = cyVar.z();
                        aqVar.k(true);
                        break;
                    default:
                        db.a(cyVar, l.b);
                        break;
                }
                cyVar.m();
            }
        }

        public void b(cy cyVar, aq aqVar) throws cf {
            aqVar.K();
            cyVar.a(aq.m);
            if (aqVar.a != null) {
                cyVar.a(aq.n);
                cyVar.a(aqVar.a);
                cyVar.c();
            }
            if (aqVar.b != null && aqVar.i()) {
                cyVar.a(aq.o);
                cyVar.a(aqVar.b);
                cyVar.c();
            }
            if (aqVar.l()) {
                cyVar.a(aq.p);
                cyVar.a(aqVar.c);
                cyVar.c();
            }
            if (aqVar.d != null && aqVar.o()) {
                cyVar.a(aq.q);
                cyVar.a(aqVar.d);
                cyVar.c();
            }
            if (aqVar.e != null) {
                cyVar.a(aq.r);
                cyVar.a(aqVar.e.a());
                cyVar.c();
            }
            if (aqVar.f != null) {
                cyVar.a(aq.s);
                cyVar.a(aqVar.f);
                cyVar.c();
            }
            if (aqVar.g != null) {
                cyVar.a(aq.t);
                cyVar.a(aqVar.g);
                cyVar.c();
            }
            if (aqVar.h != null && aqVar.A()) {
                cyVar.a(aq.u);
                cyVar.a(aqVar.h);
                cyVar.c();
            }
            if (aqVar.i != null && aqVar.D()) {
                cyVar.a(aq.v);
                cyVar.a(aqVar.i);
                cyVar.c();
            }
            if (aqVar.G()) {
                cyVar.a(aq.w);
                cyVar.a(aqVar.j);
                cyVar.c();
            }
            if (aqVar.k != null && aqVar.J()) {
                cyVar.a(aq.x);
                cyVar.a(aqVar.k);
                cyVar.c();
            }
            cyVar.d();
            cyVar.b();
        }
    }

    /* compiled from: AppInfo */
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

    /* compiled from: AppInfo */
    private static class c extends dj<aq> {
        private c() {
        }

        public void a(cy cyVar, aq aqVar) throws cf {
            de deVar = (de) cyVar;
            deVar.a(aqVar.a);
            deVar.a(aqVar.e.a());
            deVar.a(aqVar.f);
            deVar.a(aqVar.g);
            BitSet bitSet = new BitSet();
            if (aqVar.i()) {
                bitSet.set(0);
            }
            if (aqVar.l()) {
                bitSet.set(1);
            }
            if (aqVar.o()) {
                bitSet.set(2);
            }
            if (aqVar.A()) {
                bitSet.set(3);
            }
            if (aqVar.D()) {
                bitSet.set(4);
            }
            if (aqVar.G()) {
                bitSet.set(5);
            }
            if (aqVar.J()) {
                bitSet.set(6);
            }
            deVar.a(bitSet, 7);
            if (aqVar.i()) {
                deVar.a(aqVar.b);
            }
            if (aqVar.l()) {
                deVar.a(aqVar.c);
            }
            if (aqVar.o()) {
                deVar.a(aqVar.d);
            }
            if (aqVar.A()) {
                deVar.a(aqVar.h);
            }
            if (aqVar.D()) {
                deVar.a(aqVar.i);
            }
            if (aqVar.G()) {
                deVar.a(aqVar.j);
            }
            if (aqVar.J()) {
                deVar.a(aqVar.k);
            }
        }

        public void b(cy cyVar, aq aqVar) throws cf {
            de deVar = (de) cyVar;
            aqVar.a = deVar.z();
            aqVar.a(true);
            aqVar.e = bm.a(deVar.w());
            aqVar.e(true);
            aqVar.f = deVar.z();
            aqVar.f(true);
            aqVar.g = deVar.z();
            aqVar.g(true);
            BitSet b = deVar.b(7);
            if (b.get(0)) {
                aqVar.b = deVar.z();
                aqVar.b(true);
            }
            if (b.get(1)) {
                aqVar.c = deVar.w();
                aqVar.c(true);
            }
            if (b.get(2)) {
                aqVar.d = deVar.z();
                aqVar.d(true);
            }
            if (b.get(3)) {
                aqVar.h = deVar.z();
                aqVar.h(true);
            }
            if (b.get(4)) {
                aqVar.i = deVar.z();
                aqVar.i(true);
            }
            if (b.get(5)) {
                aqVar.j = deVar.w();
                aqVar.j(true);
            }
            if (b.get(6)) {
                aqVar.k = deVar.z();
                aqVar.k(true);
            }
        }
    }

    /* compiled from: AppInfo */
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

    /* compiled from: AppInfo */
    public enum e implements cg {
        KEY((short) 1, "key"),
        VERSION((short) 2, "version"),
        VERSION_INDEX((short) 3, "version_index"),
        PACKAGE_NAME((short) 4, "package_name"),
        SDK_TYPE((short) 5, "sdk_type"),
        SDK_VERSION((short) 6, "sdk_version"),
        CHANNEL((short) 7, "channel"),
        WRAPPER_TYPE((short) 8, "wrapper_type"),
        WRAPPER_VERSION((short) 9, "wrapper_version"),
        VERTICAL_TYPE((short) 10, "vertical_type"),
        APP_SIGNATURE((short) 11, "app_signature");
        
        private static final Map<String, e> l = null;
        private final short m;
        private final String n;

        static {
            l = new HashMap();
            Iterator it = EnumSet.allOf(e.class).iterator();
            while (it.hasNext()) {
                e eVar = (e) it.next();
                l.put(eVar.b(), eVar);
            }
        }

        public static e a(int i) {
            switch (i) {
                case 1:
                    return KEY;
                case 2:
                    return VERSION;
                case 3:
                    return VERSION_INDEX;
                case 4:
                    return PACKAGE_NAME;
                case 5:
                    return SDK_TYPE;
                case 6:
                    return SDK_VERSION;
                case 7:
                    return CHANNEL;
                case 8:
                    return WRAPPER_TYPE;
                case 9:
                    return WRAPPER_VERSION;
                case 10:
                    return VERTICAL_TYPE;
                case 11:
                    return APP_SIGNATURE;
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
            return (e) l.get(str);
        }

        private e(short s, String str) {
            this.m = s;
            this.n = str;
        }

        public short a() {
            return this.m;
        }

        public String b() {
            return this.n;
        }
    }

    public /* synthetic */ cg b(int i) {
        return d(i);
    }

    public /* synthetic */ bz g() {
        return a();
    }

    static {
        y.put(di.class, new b());
        y.put(dj.class, new d());
        Map enumMap = new EnumMap(e.class);
        enumMap.put(e.KEY, new cl("key", (byte) 1, new cm((byte) 11)));
        enumMap.put(e.VERSION, new cl("version", (byte) 2, new cm((byte) 11)));
        enumMap.put(e.VERSION_INDEX, new cl("version_index", (byte) 2, new cm((byte) 8)));
        enumMap.put(e.PACKAGE_NAME, new cl("package_name", (byte) 2, new cm((byte) 11)));
        enumMap.put(e.SDK_TYPE, new cl("sdk_type", (byte) 1, new ck(df.n, bm.class)));
        enumMap.put(e.SDK_VERSION, new cl("sdk_version", (byte) 1, new cm((byte) 11)));
        enumMap.put(e.CHANNEL, new cl("channel", (byte) 1, new cm((byte) 11)));
        enumMap.put(e.WRAPPER_TYPE, new cl("wrapper_type", (byte) 2, new cm((byte) 11)));
        enumMap.put(e.WRAPPER_VERSION, new cl("wrapper_version", (byte) 2, new cm((byte) 11)));
        enumMap.put(e.VERTICAL_TYPE, new cl("vertical_type", (byte) 2, new cm((byte) 8)));
        enumMap.put(e.APP_SIGNATURE, new cl("app_signature", (byte) 2, new cm((byte) 11)));
        l = Collections.unmodifiableMap(enumMap);
        cl.a(aq.class, l);
    }

    public aq() {
        this.B = (byte) 0;
        this.C = new e[]{e.VERSION, e.VERSION_INDEX, e.PACKAGE_NAME, e.WRAPPER_TYPE, e.WRAPPER_VERSION, e.VERTICAL_TYPE, e.APP_SIGNATURE};
    }

    public aq(String str, bm bmVar, String str2, String str3) {
        this();
        this.a = str;
        this.e = bmVar;
        this.f = str2;
        this.g = str3;
    }

    public aq(aq aqVar) {
        this.B = (byte) 0;
        this.C = new e[]{e.VERSION, e.VERSION_INDEX, e.PACKAGE_NAME, e.WRAPPER_TYPE, e.WRAPPER_VERSION, e.VERTICAL_TYPE, e.APP_SIGNATURE};
        this.B = aqVar.B;
        if (aqVar.e()) {
            this.a = aqVar.a;
        }
        if (aqVar.i()) {
            this.b = aqVar.b;
        }
        this.c = aqVar.c;
        if (aqVar.o()) {
            this.d = aqVar.d;
        }
        if (aqVar.r()) {
            this.e = aqVar.e;
        }
        if (aqVar.u()) {
            this.f = aqVar.f;
        }
        if (aqVar.x()) {
            this.g = aqVar.g;
        }
        if (aqVar.A()) {
            this.h = aqVar.h;
        }
        if (aqVar.D()) {
            this.i = aqVar.i;
        }
        this.j = aqVar.j;
        if (aqVar.J()) {
            this.k = aqVar.k;
        }
    }

    public aq a() {
        return new aq(this);
    }

    public void b() {
        this.a = null;
        this.b = null;
        c(false);
        this.c = 0;
        this.d = null;
        this.e = null;
        this.f = null;
        this.g = null;
        this.h = null;
        this.i = null;
        j(false);
        this.j = 0;
        this.k = null;
    }

    public String c() {
        return this.a;
    }

    public aq a(String str) {
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

    public String f() {
        return this.b;
    }

    public aq b(String str) {
        this.b = str;
        return this;
    }

    public void h() {
        this.b = null;
    }

    public boolean i() {
        return this.b != null;
    }

    public void b(boolean z) {
        if (!z) {
            this.b = null;
        }
    }

    public int j() {
        return this.c;
    }

    public aq a(int i) {
        this.c = i;
        c(true);
        return this;
    }

    public void k() {
        this.B = bw.b(this.B, 0);
    }

    public boolean l() {
        return bw.a(this.B, 0);
    }

    public void c(boolean z) {
        this.B = bw.a(this.B, 0, z);
    }

    public String m() {
        return this.d;
    }

    public aq c(String str) {
        this.d = str;
        return this;
    }

    public void n() {
        this.d = null;
    }

    public boolean o() {
        return this.d != null;
    }

    public void d(boolean z) {
        if (!z) {
            this.d = null;
        }
    }

    public bm p() {
        return this.e;
    }

    public aq a(bm bmVar) {
        this.e = bmVar;
        return this;
    }

    public void q() {
        this.e = null;
    }

    public boolean r() {
        return this.e != null;
    }

    public void e(boolean z) {
        if (!z) {
            this.e = null;
        }
    }

    public String s() {
        return this.f;
    }

    public aq d(String str) {
        this.f = str;
        return this;
    }

    public void t() {
        this.f = null;
    }

    public boolean u() {
        return this.f != null;
    }

    public void f(boolean z) {
        if (!z) {
            this.f = null;
        }
    }

    public String v() {
        return this.g;
    }

    public aq e(String str) {
        this.g = str;
        return this;
    }

    public void w() {
        this.g = null;
    }

    public boolean x() {
        return this.g != null;
    }

    public void g(boolean z) {
        if (!z) {
            this.g = null;
        }
    }

    public String y() {
        return this.h;
    }

    public aq f(String str) {
        this.h = str;
        return this;
    }

    public void z() {
        this.h = null;
    }

    public boolean A() {
        return this.h != null;
    }

    public void h(boolean z) {
        if (!z) {
            this.h = null;
        }
    }

    public String B() {
        return this.i;
    }

    public aq g(String str) {
        this.i = str;
        return this;
    }

    public void C() {
        this.i = null;
    }

    public boolean D() {
        return this.i != null;
    }

    public void i(boolean z) {
        if (!z) {
            this.i = null;
        }
    }

    public int E() {
        return this.j;
    }

    public aq c(int i) {
        this.j = i;
        j(true);
        return this;
    }

    public void F() {
        this.B = bw.b(this.B, 1);
    }

    public boolean G() {
        return bw.a(this.B, 1);
    }

    public void j(boolean z) {
        this.B = bw.a(this.B, 1, z);
    }

    public String H() {
        return this.k;
    }

    public aq h(String str) {
        this.k = str;
        return this;
    }

    public void I() {
        this.k = null;
    }

    public boolean J() {
        return this.k != null;
    }

    public void k(boolean z) {
        if (!z) {
            this.k = null;
        }
    }

    public e d(int i) {
        return e.a(i);
    }

    public void a(cy cyVar) throws cf {
        ((dh) y.get(cyVar.D())).b().b(cyVar, this);
    }

    public void b(cy cyVar) throws cf {
        ((dh) y.get(cyVar.D())).b().a(cyVar, this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("AppInfo(");
        stringBuilder.append("key:");
        if (this.a == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.a);
        }
        if (i()) {
            stringBuilder.append(", ");
            stringBuilder.append("version:");
            if (this.b == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.b);
            }
        }
        if (l()) {
            stringBuilder.append(", ");
            stringBuilder.append("version_index:");
            stringBuilder.append(this.c);
        }
        if (o()) {
            stringBuilder.append(", ");
            stringBuilder.append("package_name:");
            if (this.d == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.d);
            }
        }
        stringBuilder.append(", ");
        stringBuilder.append("sdk_type:");
        if (this.e == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.e);
        }
        stringBuilder.append(", ");
        stringBuilder.append("sdk_version:");
        if (this.f == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.f);
        }
        stringBuilder.append(", ");
        stringBuilder.append("channel:");
        if (this.g == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.g);
        }
        if (A()) {
            stringBuilder.append(", ");
            stringBuilder.append("wrapper_type:");
            if (this.h == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.h);
            }
        }
        if (D()) {
            stringBuilder.append(", ");
            stringBuilder.append("wrapper_version:");
            if (this.i == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.i);
            }
        }
        if (G()) {
            stringBuilder.append(", ");
            stringBuilder.append("vertical_type:");
            stringBuilder.append(this.j);
        }
        if (J()) {
            stringBuilder.append(", ");
            stringBuilder.append("app_signature:");
            if (this.k == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.k);
            }
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public void K() throws cf {
        if (this.a == null) {
            throw new cz("Required field 'key' was not present! Struct: " + toString());
        } else if (this.e == null) {
            throw new cz("Required field 'sdk_type' was not present! Struct: " + toString());
        } else if (this.f == null) {
            throw new cz("Required field 'sdk_version' was not present! Struct: " + toString());
        } else if (this.g == null) {
            throw new cz("Required field 'channel' was not present! Struct: " + toString());
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
            this.B = (byte) 0;
            a(new cs(new dk((InputStream) objectInputStream)));
        } catch (cf e) {
            throw new IOException(e.getMessage());
        }
    }
}
