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

/* compiled from: DeviceInfo */
public class at implements Serializable, Cloneable, bz<at, e> {
    private static final ct A = new ct("os", (byte) 11, (short) 7);
    private static final ct B = new ct("os_version", (byte) 11, (short) 8);
    private static final ct C = new ct("resolution", (byte) 12, (short) 9);
    private static final ct D = new ct("is_jailbroken", (byte) 2, (short) 10);
    private static final ct E = new ct("is_pirated", (byte) 2, (short) 11);
    private static final ct F = new ct("device_board", (byte) 11, (short) 12);
    private static final ct G = new ct("device_brand", (byte) 11, (short) 13);
    private static final ct H = new ct("device_manutime", (byte) 10, (short) 14);
    private static final ct I = new ct("device_manufacturer", (byte) 11, (short) 15);
    private static final ct J = new ct("device_manuid", (byte) 11, (short) 16);
    private static final ct K = new ct("device_name", (byte) 11, (short) 17);
    private static final ct L = new ct("wp_device", (byte) 11, (short) 18);
    private static final Map<Class<? extends dg>, dh> M = new HashMap();
    private static final int N = 0;
    private static final int O = 1;
    private static final int P = 2;
    public static final Map<e, cl> s;
    private static final dd t = new dd("DeviceInfo");
    private static final ct u = new ct("device_id", (byte) 11, (short) 1);
    private static final ct v = new ct("idmd5", (byte) 11, (short) 2);
    private static final ct w = new ct("mac_address", (byte) 11, (short) 3);
    private static final ct x = new ct("open_udid", (byte) 11, (short) 4);
    private static final ct y = new ct("model", (byte) 11, (short) 5);
    private static final ct z = new ct("cpu", (byte) 11, (short) 6);
    private byte Q;
    private e[] R;
    public String a;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;
    public String h;
    public bk i;
    public boolean j;
    public boolean k;
    public String l;
    public String m;
    public long n;
    public String o;
    public String p;
    public String q;
    public String r;

    /* compiled from: DeviceInfo */
    private static class a extends di<at> {
        private a() {
        }

        public /* synthetic */ void a(cy cyVar, bz bzVar) throws cf {
            b(cyVar, (at) bzVar);
        }

        public /* synthetic */ void b(cy cyVar, bz bzVar) throws cf {
            a(cyVar, (at) bzVar);
        }

        public void a(cy cyVar, at atVar) throws cf {
            cyVar.j();
            while (true) {
                ct l = cyVar.l();
                if (l.b == (byte) 0) {
                    cyVar.k();
                    atVar.af();
                    return;
                }
                switch (l.c) {
                    case (short) 1:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        atVar.a = cyVar.z();
                        atVar.a(true);
                        break;
                    case (short) 2:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        atVar.b = cyVar.z();
                        atVar.b(true);
                        break;
                    case (short) 3:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        atVar.c = cyVar.z();
                        atVar.c(true);
                        break;
                    case (short) 4:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        atVar.d = cyVar.z();
                        atVar.d(true);
                        break;
                    case (short) 5:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        atVar.e = cyVar.z();
                        atVar.e(true);
                        break;
                    case (short) 6:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        atVar.f = cyVar.z();
                        atVar.f(true);
                        break;
                    case (short) 7:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        atVar.g = cyVar.z();
                        atVar.g(true);
                        break;
                    case (short) 8:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        atVar.h = cyVar.z();
                        atVar.h(true);
                        break;
                    case (short) 9:
                        if (l.b != (byte) 12) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        atVar.i = new bk();
                        atVar.i.a(cyVar);
                        atVar.i(true);
                        break;
                    case (short) 10:
                        if (l.b != (byte) 2) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        atVar.j = cyVar.t();
                        atVar.k(true);
                        break;
                    case (short) 11:
                        if (l.b != (byte) 2) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        atVar.k = cyVar.t();
                        atVar.m(true);
                        break;
                    case (short) 12:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        atVar.l = cyVar.z();
                        atVar.n(true);
                        break;
                    case (short) 13:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        atVar.m = cyVar.z();
                        atVar.o(true);
                        break;
                    case (short) 14:
                        if (l.b != (byte) 10) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        atVar.n = cyVar.x();
                        atVar.p(true);
                        break;
                    case (short) 15:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        atVar.o = cyVar.z();
                        atVar.q(true);
                        break;
                    case (short) 16:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        atVar.p = cyVar.z();
                        atVar.r(true);
                        break;
                    case (short) 17:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        atVar.q = cyVar.z();
                        atVar.s(true);
                        break;
                    case (short) 18:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        atVar.r = cyVar.z();
                        atVar.t(true);
                        break;
                    default:
                        db.a(cyVar, l.b);
                        break;
                }
                cyVar.m();
            }
        }

        public void b(cy cyVar, at atVar) throws cf {
            atVar.af();
            cyVar.a(at.t);
            if (atVar.a != null && atVar.e()) {
                cyVar.a(at.u);
                cyVar.a(atVar.a);
                cyVar.c();
            }
            if (atVar.b != null && atVar.i()) {
                cyVar.a(at.v);
                cyVar.a(atVar.b);
                cyVar.c();
            }
            if (atVar.c != null && atVar.l()) {
                cyVar.a(at.w);
                cyVar.a(atVar.c);
                cyVar.c();
            }
            if (atVar.d != null && atVar.o()) {
                cyVar.a(at.x);
                cyVar.a(atVar.d);
                cyVar.c();
            }
            if (atVar.e != null && atVar.r()) {
                cyVar.a(at.y);
                cyVar.a(atVar.e);
                cyVar.c();
            }
            if (atVar.f != null && atVar.u()) {
                cyVar.a(at.z);
                cyVar.a(atVar.f);
                cyVar.c();
            }
            if (atVar.g != null && atVar.x()) {
                cyVar.a(at.A);
                cyVar.a(atVar.g);
                cyVar.c();
            }
            if (atVar.h != null && atVar.A()) {
                cyVar.a(at.B);
                cyVar.a(atVar.h);
                cyVar.c();
            }
            if (atVar.i != null && atVar.D()) {
                cyVar.a(at.C);
                atVar.i.b(cyVar);
                cyVar.c();
            }
            if (atVar.G()) {
                cyVar.a(at.D);
                cyVar.a(atVar.j);
                cyVar.c();
            }
            if (atVar.J()) {
                cyVar.a(at.E);
                cyVar.a(atVar.k);
                cyVar.c();
            }
            if (atVar.l != null && atVar.M()) {
                cyVar.a(at.F);
                cyVar.a(atVar.l);
                cyVar.c();
            }
            if (atVar.m != null && atVar.P()) {
                cyVar.a(at.G);
                cyVar.a(atVar.m);
                cyVar.c();
            }
            if (atVar.S()) {
                cyVar.a(at.H);
                cyVar.a(atVar.n);
                cyVar.c();
            }
            if (atVar.o != null && atVar.V()) {
                cyVar.a(at.I);
                cyVar.a(atVar.o);
                cyVar.c();
            }
            if (atVar.p != null && atVar.Y()) {
                cyVar.a(at.J);
                cyVar.a(atVar.p);
                cyVar.c();
            }
            if (atVar.q != null && atVar.ab()) {
                cyVar.a(at.K);
                cyVar.a(atVar.q);
                cyVar.c();
            }
            if (atVar.r != null && atVar.ae()) {
                cyVar.a(at.L);
                cyVar.a(atVar.r);
                cyVar.c();
            }
            cyVar.d();
            cyVar.b();
        }
    }

    /* compiled from: DeviceInfo */
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

    /* compiled from: DeviceInfo */
    private static class c extends dj<at> {
        private c() {
        }

        public void a(cy cyVar, at atVar) throws cf {
            cyVar = (de) cyVar;
            BitSet bitSet = new BitSet();
            if (atVar.e()) {
                bitSet.set(0);
            }
            if (atVar.i()) {
                bitSet.set(1);
            }
            if (atVar.l()) {
                bitSet.set(2);
            }
            if (atVar.o()) {
                bitSet.set(3);
            }
            if (atVar.r()) {
                bitSet.set(4);
            }
            if (atVar.u()) {
                bitSet.set(5);
            }
            if (atVar.x()) {
                bitSet.set(6);
            }
            if (atVar.A()) {
                bitSet.set(7);
            }
            if (atVar.D()) {
                bitSet.set(8);
            }
            if (atVar.G()) {
                bitSet.set(9);
            }
            if (atVar.J()) {
                bitSet.set(10);
            }
            if (atVar.M()) {
                bitSet.set(11);
            }
            if (atVar.P()) {
                bitSet.set(12);
            }
            if (atVar.S()) {
                bitSet.set(13);
            }
            if (atVar.V()) {
                bitSet.set(14);
            }
            if (atVar.Y()) {
                bitSet.set(15);
            }
            if (atVar.ab()) {
                bitSet.set(16);
            }
            if (atVar.ae()) {
                bitSet.set(17);
            }
            cyVar.a(bitSet, 18);
            if (atVar.e()) {
                cyVar.a(atVar.a);
            }
            if (atVar.i()) {
                cyVar.a(atVar.b);
            }
            if (atVar.l()) {
                cyVar.a(atVar.c);
            }
            if (atVar.o()) {
                cyVar.a(atVar.d);
            }
            if (atVar.r()) {
                cyVar.a(atVar.e);
            }
            if (atVar.u()) {
                cyVar.a(atVar.f);
            }
            if (atVar.x()) {
                cyVar.a(atVar.g);
            }
            if (atVar.A()) {
                cyVar.a(atVar.h);
            }
            if (atVar.D()) {
                atVar.i.b(cyVar);
            }
            if (atVar.G()) {
                cyVar.a(atVar.j);
            }
            if (atVar.J()) {
                cyVar.a(atVar.k);
            }
            if (atVar.M()) {
                cyVar.a(atVar.l);
            }
            if (atVar.P()) {
                cyVar.a(atVar.m);
            }
            if (atVar.S()) {
                cyVar.a(atVar.n);
            }
            if (atVar.V()) {
                cyVar.a(atVar.o);
            }
            if (atVar.Y()) {
                cyVar.a(atVar.p);
            }
            if (atVar.ab()) {
                cyVar.a(atVar.q);
            }
            if (atVar.ae()) {
                cyVar.a(atVar.r);
            }
        }

        public void b(cy cyVar, at atVar) throws cf {
            cyVar = (de) cyVar;
            BitSet b = cyVar.b(18);
            if (b.get(0)) {
                atVar.a = cyVar.z();
                atVar.a(true);
            }
            if (b.get(1)) {
                atVar.b = cyVar.z();
                atVar.b(true);
            }
            if (b.get(2)) {
                atVar.c = cyVar.z();
                atVar.c(true);
            }
            if (b.get(3)) {
                atVar.d = cyVar.z();
                atVar.d(true);
            }
            if (b.get(4)) {
                atVar.e = cyVar.z();
                atVar.e(true);
            }
            if (b.get(5)) {
                atVar.f = cyVar.z();
                atVar.f(true);
            }
            if (b.get(6)) {
                atVar.g = cyVar.z();
                atVar.g(true);
            }
            if (b.get(7)) {
                atVar.h = cyVar.z();
                atVar.h(true);
            }
            if (b.get(8)) {
                atVar.i = new bk();
                atVar.i.a(cyVar);
                atVar.i(true);
            }
            if (b.get(9)) {
                atVar.j = cyVar.t();
                atVar.k(true);
            }
            if (b.get(10)) {
                atVar.k = cyVar.t();
                atVar.m(true);
            }
            if (b.get(11)) {
                atVar.l = cyVar.z();
                atVar.n(true);
            }
            if (b.get(12)) {
                atVar.m = cyVar.z();
                atVar.o(true);
            }
            if (b.get(13)) {
                atVar.n = cyVar.x();
                atVar.p(true);
            }
            if (b.get(14)) {
                atVar.o = cyVar.z();
                atVar.q(true);
            }
            if (b.get(15)) {
                atVar.p = cyVar.z();
                atVar.r(true);
            }
            if (b.get(16)) {
                atVar.q = cyVar.z();
                atVar.s(true);
            }
            if (b.get(17)) {
                atVar.r = cyVar.z();
                atVar.t(true);
            }
        }
    }

    /* compiled from: DeviceInfo */
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

    /* compiled from: DeviceInfo */
    public enum e implements cg {
        DEVICE_ID((short) 1, "device_id"),
        IDMD5((short) 2, "idmd5"),
        MAC_ADDRESS((short) 3, "mac_address"),
        OPEN_UDID((short) 4, "open_udid"),
        MODEL((short) 5, "model"),
        CPU((short) 6, "cpu"),
        OS((short) 7, "os"),
        OS_VERSION((short) 8, "os_version"),
        RESOLUTION((short) 9, "resolution"),
        IS_JAILBROKEN((short) 10, "is_jailbroken"),
        IS_PIRATED((short) 11, "is_pirated"),
        DEVICE_BOARD((short) 12, "device_board"),
        DEVICE_BRAND((short) 13, "device_brand"),
        DEVICE_MANUTIME((short) 14, "device_manutime"),
        DEVICE_MANUFACTURER((short) 15, "device_manufacturer"),
        DEVICE_MANUID((short) 16, "device_manuid"),
        DEVICE_NAME((short) 17, "device_name"),
        WP_DEVICE((short) 18, "wp_device");
        
        private static final Map<String, e> s = null;
        private final short t;
        private final String u;

        static {
            s = new HashMap();
            Iterator it = EnumSet.allOf(e.class).iterator();
            while (it.hasNext()) {
                e eVar = (e) it.next();
                s.put(eVar.b(), eVar);
            }
        }

        public static e a(int i) {
            switch (i) {
                case 1:
                    return DEVICE_ID;
                case 2:
                    return IDMD5;
                case 3:
                    return MAC_ADDRESS;
                case 4:
                    return OPEN_UDID;
                case 5:
                    return MODEL;
                case 6:
                    return CPU;
                case 7:
                    return OS;
                case 8:
                    return OS_VERSION;
                case 9:
                    return RESOLUTION;
                case 10:
                    return IS_JAILBROKEN;
                case 11:
                    return IS_PIRATED;
                case 12:
                    return DEVICE_BOARD;
                case 13:
                    return DEVICE_BRAND;
                case 14:
                    return DEVICE_MANUTIME;
                case 15:
                    return DEVICE_MANUFACTURER;
                case 16:
                    return DEVICE_MANUID;
                case 17:
                    return DEVICE_NAME;
                case 18:
                    return WP_DEVICE;
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
            return (e) s.get(str);
        }

        private e(short s, String str) {
            this.t = s;
            this.u = str;
        }

        public short a() {
            return this.t;
        }

        public String b() {
            return this.u;
        }
    }

    public /* synthetic */ cg b(int i) {
        return a(i);
    }

    public /* synthetic */ bz g() {
        return a();
    }

    static {
        M.put(di.class, new b());
        M.put(dj.class, new d());
        Map enumMap = new EnumMap(e.class);
        enumMap.put(e.DEVICE_ID, new cl("device_id", (byte) 2, new cm((byte) 11)));
        enumMap.put(e.IDMD5, new cl("idmd5", (byte) 2, new cm((byte) 11)));
        enumMap.put(e.MAC_ADDRESS, new cl("mac_address", (byte) 2, new cm((byte) 11)));
        enumMap.put(e.OPEN_UDID, new cl("open_udid", (byte) 2, new cm((byte) 11)));
        enumMap.put(e.MODEL, new cl("model", (byte) 2, new cm((byte) 11)));
        enumMap.put(e.CPU, new cl("cpu", (byte) 2, new cm((byte) 11)));
        enumMap.put(e.OS, new cl("os", (byte) 2, new cm((byte) 11)));
        enumMap.put(e.OS_VERSION, new cl("os_version", (byte) 2, new cm((byte) 11)));
        enumMap.put(e.RESOLUTION, new cl("resolution", (byte) 2, new cq((byte) 12, bk.class)));
        enumMap.put(e.IS_JAILBROKEN, new cl("is_jailbroken", (byte) 2, new cm((byte) 2)));
        enumMap.put(e.IS_PIRATED, new cl("is_pirated", (byte) 2, new cm((byte) 2)));
        enumMap.put(e.DEVICE_BOARD, new cl("device_board", (byte) 2, new cm((byte) 11)));
        enumMap.put(e.DEVICE_BRAND, new cl("device_brand", (byte) 2, new cm((byte) 11)));
        enumMap.put(e.DEVICE_MANUTIME, new cl("device_manutime", (byte) 2, new cm((byte) 10)));
        enumMap.put(e.DEVICE_MANUFACTURER, new cl("device_manufacturer", (byte) 2, new cm((byte) 11)));
        enumMap.put(e.DEVICE_MANUID, new cl("device_manuid", (byte) 2, new cm((byte) 11)));
        enumMap.put(e.DEVICE_NAME, new cl("device_name", (byte) 2, new cm((byte) 11)));
        enumMap.put(e.WP_DEVICE, new cl("wp_device", (byte) 2, new cm((byte) 11)));
        s = Collections.unmodifiableMap(enumMap);
        cl.a(at.class, s);
    }

    public at() {
        this.Q = (byte) 0;
        this.R = new e[]{e.DEVICE_ID, e.IDMD5, e.MAC_ADDRESS, e.OPEN_UDID, e.MODEL, e.CPU, e.OS, e.OS_VERSION, e.RESOLUTION, e.IS_JAILBROKEN, e.IS_PIRATED, e.DEVICE_BOARD, e.DEVICE_BRAND, e.DEVICE_MANUTIME, e.DEVICE_MANUFACTURER, e.DEVICE_MANUID, e.DEVICE_NAME, e.WP_DEVICE};
    }

    public at(at atVar) {
        this.Q = (byte) 0;
        this.R = new e[]{e.DEVICE_ID, e.IDMD5, e.MAC_ADDRESS, e.OPEN_UDID, e.MODEL, e.CPU, e.OS, e.OS_VERSION, e.RESOLUTION, e.IS_JAILBROKEN, e.IS_PIRATED, e.DEVICE_BOARD, e.DEVICE_BRAND, e.DEVICE_MANUTIME, e.DEVICE_MANUFACTURER, e.DEVICE_MANUID, e.DEVICE_NAME, e.WP_DEVICE};
        this.Q = atVar.Q;
        if (atVar.e()) {
            this.a = atVar.a;
        }
        if (atVar.i()) {
            this.b = atVar.b;
        }
        if (atVar.l()) {
            this.c = atVar.c;
        }
        if (atVar.o()) {
            this.d = atVar.d;
        }
        if (atVar.r()) {
            this.e = atVar.e;
        }
        if (atVar.u()) {
            this.f = atVar.f;
        }
        if (atVar.x()) {
            this.g = atVar.g;
        }
        if (atVar.A()) {
            this.h = atVar.h;
        }
        if (atVar.D()) {
            this.i = new bk(atVar.i);
        }
        this.j = atVar.j;
        this.k = atVar.k;
        if (atVar.M()) {
            this.l = atVar.l;
        }
        if (atVar.P()) {
            this.m = atVar.m;
        }
        this.n = atVar.n;
        if (atVar.V()) {
            this.o = atVar.o;
        }
        if (atVar.Y()) {
            this.p = atVar.p;
        }
        if (atVar.ab()) {
            this.q = atVar.q;
        }
        if (atVar.ae()) {
            this.r = atVar.r;
        }
    }

    public at a() {
        return new at(this);
    }

    public void b() {
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = null;
        this.g = null;
        this.h = null;
        this.i = null;
        k(false);
        this.j = false;
        m(false);
        this.k = false;
        this.l = null;
        this.m = null;
        p(false);
        this.n = 0;
        this.o = null;
        this.p = null;
        this.q = null;
        this.r = null;
    }

    public String c() {
        return this.a;
    }

    public at a(String str) {
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

    public at b(String str) {
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

    public String j() {
        return this.c;
    }

    public at c(String str) {
        this.c = str;
        return this;
    }

    public void k() {
        this.c = null;
    }

    public boolean l() {
        return this.c != null;
    }

    public void c(boolean z) {
        if (!z) {
            this.c = null;
        }
    }

    public String m() {
        return this.d;
    }

    public at d(String str) {
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

    public String p() {
        return this.e;
    }

    public at e(String str) {
        this.e = str;
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

    public at f(String str) {
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

    public at g(String str) {
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

    public at h(String str) {
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

    public bk B() {
        return this.i;
    }

    public at a(bk bkVar) {
        this.i = bkVar;
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

    public boolean E() {
        return this.j;
    }

    public at j(boolean z) {
        this.j = z;
        k(true);
        return this;
    }

    public void F() {
        this.Q = bw.b(this.Q, 0);
    }

    public boolean G() {
        return bw.a(this.Q, 0);
    }

    public void k(boolean z) {
        this.Q = bw.a(this.Q, 0, z);
    }

    public boolean H() {
        return this.k;
    }

    public at l(boolean z) {
        this.k = z;
        m(true);
        return this;
    }

    public void I() {
        this.Q = bw.b(this.Q, 1);
    }

    public boolean J() {
        return bw.a(this.Q, 1);
    }

    public void m(boolean z) {
        this.Q = bw.a(this.Q, 1, z);
    }

    public String K() {
        return this.l;
    }

    public at i(String str) {
        this.l = str;
        return this;
    }

    public void L() {
        this.l = null;
    }

    public boolean M() {
        return this.l != null;
    }

    public void n(boolean z) {
        if (!z) {
            this.l = null;
        }
    }

    public String N() {
        return this.m;
    }

    public at j(String str) {
        this.m = str;
        return this;
    }

    public void O() {
        this.m = null;
    }

    public boolean P() {
        return this.m != null;
    }

    public void o(boolean z) {
        if (!z) {
            this.m = null;
        }
    }

    public long Q() {
        return this.n;
    }

    public at a(long j) {
        this.n = j;
        p(true);
        return this;
    }

    public void R() {
        this.Q = bw.b(this.Q, 2);
    }

    public boolean S() {
        return bw.a(this.Q, 2);
    }

    public void p(boolean z) {
        this.Q = bw.a(this.Q, 2, z);
    }

    public String T() {
        return this.o;
    }

    public at k(String str) {
        this.o = str;
        return this;
    }

    public void U() {
        this.o = null;
    }

    public boolean V() {
        return this.o != null;
    }

    public void q(boolean z) {
        if (!z) {
            this.o = null;
        }
    }

    public String W() {
        return this.p;
    }

    public at l(String str) {
        this.p = str;
        return this;
    }

    public void X() {
        this.p = null;
    }

    public boolean Y() {
        return this.p != null;
    }

    public void r(boolean z) {
        if (!z) {
            this.p = null;
        }
    }

    public String Z() {
        return this.q;
    }

    public at m(String str) {
        this.q = str;
        return this;
    }

    public void aa() {
        this.q = null;
    }

    public boolean ab() {
        return this.q != null;
    }

    public void s(boolean z) {
        if (!z) {
            this.q = null;
        }
    }

    public String ac() {
        return this.r;
    }

    public at n(String str) {
        this.r = str;
        return this;
    }

    public void ad() {
        this.r = null;
    }

    public boolean ae() {
        return this.r != null;
    }

    public void t(boolean z) {
        if (!z) {
            this.r = null;
        }
    }

    public e a(int i) {
        return e.a(i);
    }

    public void a(cy cyVar) throws cf {
        ((dh) M.get(cyVar.D())).b().b(cyVar, this);
    }

    public void b(cy cyVar) throws cf {
        ((dh) M.get(cyVar.D())).b().a(cyVar, this);
    }

    public String toString() {
        Object obj = null;
        StringBuilder stringBuilder = new StringBuilder("DeviceInfo(");
        Object obj2 = 1;
        if (e()) {
            stringBuilder.append("device_id:");
            if (this.a == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.a);
            }
            obj2 = null;
        }
        if (i()) {
            if (obj2 == null) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("idmd5:");
            if (this.b == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.b);
            }
            obj2 = null;
        }
        if (l()) {
            if (obj2 == null) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("mac_address:");
            if (this.c == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.c);
            }
            obj2 = null;
        }
        if (o()) {
            if (obj2 == null) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("open_udid:");
            if (this.d == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.d);
            }
            obj2 = null;
        }
        if (r()) {
            if (obj2 == null) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("model:");
            if (this.e == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.e);
            }
            obj2 = null;
        }
        if (u()) {
            if (obj2 == null) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("cpu:");
            if (this.f == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.f);
            }
            obj2 = null;
        }
        if (x()) {
            if (obj2 == null) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("os:");
            if (this.g == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.g);
            }
            obj2 = null;
        }
        if (A()) {
            if (obj2 == null) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("os_version:");
            if (this.h == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.h);
            }
            obj2 = null;
        }
        if (D()) {
            if (obj2 == null) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("resolution:");
            if (this.i == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.i);
            }
            obj2 = null;
        }
        if (G()) {
            if (obj2 == null) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("is_jailbroken:");
            stringBuilder.append(this.j);
            obj2 = null;
        }
        if (J()) {
            if (obj2 == null) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("is_pirated:");
            stringBuilder.append(this.k);
            obj2 = null;
        }
        if (M()) {
            if (obj2 == null) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("device_board:");
            if (this.l == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.l);
            }
            obj2 = null;
        }
        if (P()) {
            if (obj2 == null) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("device_brand:");
            if (this.m == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.m);
            }
            obj2 = null;
        }
        if (S()) {
            if (obj2 == null) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("device_manutime:");
            stringBuilder.append(this.n);
            obj2 = null;
        }
        if (V()) {
            if (obj2 == null) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("device_manufacturer:");
            if (this.o == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.o);
            }
            obj2 = null;
        }
        if (Y()) {
            if (obj2 == null) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("device_manuid:");
            if (this.p == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.p);
            }
            obj2 = null;
        }
        if (ab()) {
            if (obj2 == null) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("device_name:");
            if (this.q == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.q);
            }
        } else {
            obj = obj2;
        }
        if (ae()) {
            if (obj == null) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("wp_device:");
            if (this.r == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.r);
            }
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public void af() throws cf {
        if (this.i != null) {
            this.i.j();
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
            this.Q = (byte) 0;
            a(new cs(new dk((InputStream) objectInputStream)));
        } catch (cf e) {
            throw new IOException(e.getMessage());
        }
    }
}
