package u.aly;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: UALogEntry */
public class bp implements Serializable, Cloneable, bz<bp, e> {
    private static final Map<Class<? extends dg>, dh> A = new HashMap();
    public static final Map<e, cl> m;
    private static final dd n = new dd("UALogEntry");
    private static final ct o = new ct("client_stats", (byte) 12, (short) 1);
    private static final ct p = new ct("app_info", (byte) 12, (short) 2);
    private static final ct q = new ct("device_info", (byte) 12, (short) 3);
    private static final ct r = new ct("misc_info", (byte) 12, (short) 4);
    private static final ct s = new ct("activate_msg", (byte) 12, (short) 5);
    private static final ct t = new ct("instant_msgs", df.m, (short) 6);
    private static final ct u = new ct("sessions", df.m, (short) 7);
    private static final ct v = new ct("imprint", (byte) 12, (short) 8);
    private static final ct w = new ct("id_tracking", (byte) 12, (short) 9);
    private static final ct x = new ct("active_user", (byte) 12, (short) 10);
    private static final ct y = new ct("control_policy", (byte) 12, (short) 11);
    private static final ct z = new ct("group_info", df.k, (short) 12);
    private e[] B;
    public ar a;
    public aq b;
    public at c;
    public bh d;
    public ao e;
    public List<be> f;
    public List<bn> g;
    public bc h;
    public bb i;
    public ap j;
    public as k;
    public Map<String, Integer> l;

    /* compiled from: UALogEntry */
    private static class a extends di<bp> {
        private a() {
        }

        public /* synthetic */ void a(cy cyVar, bz bzVar) throws cf {
            b(cyVar, (bp) bzVar);
        }

        public /* synthetic */ void b(cy cyVar, bz bzVar) throws cf {
            a(cyVar, (bp) bzVar);
        }

        public void a(cy cyVar, bp bpVar) throws cf {
            cyVar.j();
            while (true) {
                ct l = cyVar.l();
                if (l.b == (byte) 0) {
                    cyVar.k();
                    bpVar.S();
                    return;
                }
                cu p;
                int i;
                switch (l.c) {
                    case (short) 1:
                        if (l.b != (byte) 12) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        bpVar.a = new ar();
                        bpVar.a.a(cyVar);
                        bpVar.a(true);
                        break;
                    case (short) 2:
                        if (l.b != (byte) 12) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        bpVar.b = new aq();
                        bpVar.b.a(cyVar);
                        bpVar.b(true);
                        break;
                    case (short) 3:
                        if (l.b != (byte) 12) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        bpVar.c = new at();
                        bpVar.c.a(cyVar);
                        bpVar.c(true);
                        break;
                    case (short) 4:
                        if (l.b != (byte) 12) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        bpVar.d = new bh();
                        bpVar.d.a(cyVar);
                        bpVar.d(true);
                        break;
                    case (short) 5:
                        if (l.b != (byte) 12) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        bpVar.e = new ao();
                        bpVar.e.a(cyVar);
                        bpVar.e(true);
                        break;
                    case (short) 6:
                        if (l.b != df.m) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        p = cyVar.p();
                        bpVar.f = new ArrayList(p.b);
                        for (i = 0; i < p.b; i++) {
                            be beVar = new be();
                            beVar.a(cyVar);
                            bpVar.f.add(beVar);
                        }
                        cyVar.q();
                        bpVar.f(true);
                        break;
                    case (short) 7:
                        if (l.b != df.m) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        p = cyVar.p();
                        bpVar.g = new ArrayList(p.b);
                        for (i = 0; i < p.b; i++) {
                            bn bnVar = new bn();
                            bnVar.a(cyVar);
                            bpVar.g.add(bnVar);
                        }
                        cyVar.q();
                        bpVar.g(true);
                        break;
                    case (short) 8:
                        if (l.b != (byte) 12) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        bpVar.h = new bc();
                        bpVar.h.a(cyVar);
                        bpVar.h(true);
                        break;
                    case (short) 9:
                        if (l.b != (byte) 12) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        bpVar.i = new bb();
                        bpVar.i.a(cyVar);
                        bpVar.i(true);
                        break;
                    case (short) 10:
                        if (l.b != (byte) 12) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        bpVar.j = new ap();
                        bpVar.j.a(cyVar);
                        bpVar.j(true);
                        break;
                    case (short) 11:
                        if (l.b != (byte) 12) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        bpVar.k = new as();
                        bpVar.k.a(cyVar);
                        bpVar.k(true);
                        break;
                    case (short) 12:
                        if (l.b != df.k) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        cv n = cyVar.n();
                        bpVar.l = new HashMap(n.c * 2);
                        for (i = 0; i < n.c; i++) {
                            bpVar.l.put(cyVar.z(), Integer.valueOf(cyVar.w()));
                        }
                        cyVar.o();
                        bpVar.l(true);
                        break;
                    default:
                        db.a(cyVar, l.b);
                        break;
                }
                cyVar.m();
            }
        }

        public void b(cy cyVar, bp bpVar) throws cf {
            bpVar.S();
            cyVar.a(bp.n);
            if (bpVar.a != null) {
                cyVar.a(bp.o);
                bpVar.a.b(cyVar);
                cyVar.c();
            }
            if (bpVar.b != null) {
                cyVar.a(bp.p);
                bpVar.b.b(cyVar);
                cyVar.c();
            }
            if (bpVar.c != null) {
                cyVar.a(bp.q);
                bpVar.c.b(cyVar);
                cyVar.c();
            }
            if (bpVar.d != null) {
                cyVar.a(bp.r);
                bpVar.d.b(cyVar);
                cyVar.c();
            }
            if (bpVar.e != null && bpVar.r()) {
                cyVar.a(bp.s);
                bpVar.e.b(cyVar);
                cyVar.c();
            }
            if (bpVar.f != null && bpVar.w()) {
                cyVar.a(bp.t);
                cyVar.a(new cu((byte) 12, bpVar.f.size()));
                for (be b : bpVar.f) {
                    b.b(cyVar);
                }
                cyVar.f();
                cyVar.c();
            }
            if (bpVar.g != null && bpVar.B()) {
                cyVar.a(bp.u);
                cyVar.a(new cu((byte) 12, bpVar.g.size()));
                for (bn b2 : bpVar.g) {
                    b2.b(cyVar);
                }
                cyVar.f();
                cyVar.c();
            }
            if (bpVar.h != null && bpVar.E()) {
                cyVar.a(bp.v);
                bpVar.h.b(cyVar);
                cyVar.c();
            }
            if (bpVar.i != null && bpVar.H()) {
                cyVar.a(bp.w);
                bpVar.i.b(cyVar);
                cyVar.c();
            }
            if (bpVar.j != null && bpVar.K()) {
                cyVar.a(bp.x);
                bpVar.j.b(cyVar);
                cyVar.c();
            }
            if (bpVar.k != null && bpVar.N()) {
                cyVar.a(bp.y);
                bpVar.k.b(cyVar);
                cyVar.c();
            }
            if (bpVar.l != null && bpVar.R()) {
                cyVar.a(bp.z);
                cyVar.a(new cv((byte) 11, (byte) 8, bpVar.l.size()));
                for (Entry entry : bpVar.l.entrySet()) {
                    cyVar.a((String) entry.getKey());
                    cyVar.a(((Integer) entry.getValue()).intValue());
                }
                cyVar.e();
                cyVar.c();
            }
            cyVar.d();
            cyVar.b();
        }
    }

    /* compiled from: UALogEntry */
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

    /* compiled from: UALogEntry */
    private static class c extends dj<bp> {
        private c() {
        }

        public void a(cy cyVar, bp bpVar) throws cf {
            cyVar = (de) cyVar;
            bpVar.a.b(cyVar);
            bpVar.b.b(cyVar);
            bpVar.c.b(cyVar);
            bpVar.d.b(cyVar);
            BitSet bitSet = new BitSet();
            if (bpVar.r()) {
                bitSet.set(0);
            }
            if (bpVar.w()) {
                bitSet.set(1);
            }
            if (bpVar.B()) {
                bitSet.set(2);
            }
            if (bpVar.E()) {
                bitSet.set(3);
            }
            if (bpVar.H()) {
                bitSet.set(4);
            }
            if (bpVar.K()) {
                bitSet.set(5);
            }
            if (bpVar.N()) {
                bitSet.set(6);
            }
            if (bpVar.R()) {
                bitSet.set(7);
            }
            cyVar.a(bitSet, 8);
            if (bpVar.r()) {
                bpVar.e.b(cyVar);
            }
            if (bpVar.w()) {
                cyVar.a(bpVar.f.size());
                for (be b : bpVar.f) {
                    b.b(cyVar);
                }
            }
            if (bpVar.B()) {
                cyVar.a(bpVar.g.size());
                for (bn b2 : bpVar.g) {
                    b2.b(cyVar);
                }
            }
            if (bpVar.E()) {
                bpVar.h.b(cyVar);
            }
            if (bpVar.H()) {
                bpVar.i.b(cyVar);
            }
            if (bpVar.K()) {
                bpVar.j.b(cyVar);
            }
            if (bpVar.N()) {
                bpVar.k.b(cyVar);
            }
            if (bpVar.R()) {
                cyVar.a(bpVar.l.size());
                for (Entry entry : bpVar.l.entrySet()) {
                    cyVar.a((String) entry.getKey());
                    cyVar.a(((Integer) entry.getValue()).intValue());
                }
            }
        }

        public void b(cy cyVar, bp bpVar) throws cf {
            int i;
            int i2 = 0;
            cyVar = (de) cyVar;
            bpVar.a = new ar();
            bpVar.a.a(cyVar);
            bpVar.a(true);
            bpVar.b = new aq();
            bpVar.b.a(cyVar);
            bpVar.b(true);
            bpVar.c = new at();
            bpVar.c.a(cyVar);
            bpVar.c(true);
            bpVar.d = new bh();
            bpVar.d.a(cyVar);
            bpVar.d(true);
            BitSet b = cyVar.b(8);
            if (b.get(0)) {
                bpVar.e = new ao();
                bpVar.e.a(cyVar);
                bpVar.e(true);
            }
            if (b.get(1)) {
                cu cuVar;
                cuVar = new cu((byte) 12, cyVar.w());
                bpVar.f = new ArrayList(cuVar.b);
                for (i = 0; i < cuVar.b; i++) {
                    be beVar = new be();
                    beVar.a(cyVar);
                    bpVar.f.add(beVar);
                }
                bpVar.f(true);
            }
            if (b.get(2)) {
                cuVar = new cu((byte) 12, cyVar.w());
                bpVar.g = new ArrayList(cuVar.b);
                for (i = 0; i < cuVar.b; i++) {
                    bn bnVar = new bn();
                    bnVar.a(cyVar);
                    bpVar.g.add(bnVar);
                }
                bpVar.g(true);
            }
            if (b.get(3)) {
                bpVar.h = new bc();
                bpVar.h.a(cyVar);
                bpVar.h(true);
            }
            if (b.get(4)) {
                bpVar.i = new bb();
                bpVar.i.a(cyVar);
                bpVar.i(true);
            }
            if (b.get(5)) {
                bpVar.j = new ap();
                bpVar.j.a(cyVar);
                bpVar.j(true);
            }
            if (b.get(6)) {
                bpVar.k = new as();
                bpVar.k.a(cyVar);
                bpVar.k(true);
            }
            if (b.get(7)) {
                cv cvVar = new cv((byte) 11, (byte) 8, cyVar.w());
                bpVar.l = new HashMap(cvVar.c * 2);
                while (i2 < cvVar.c) {
                    bpVar.l.put(cyVar.z(), Integer.valueOf(cyVar.w()));
                    i2++;
                }
                bpVar.l(true);
            }
        }
    }

    /* compiled from: UALogEntry */
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

    /* compiled from: UALogEntry */
    public enum e implements cg {
        CLIENT_STATS((short) 1, "client_stats"),
        APP_INFO((short) 2, "app_info"),
        DEVICE_INFO((short) 3, "device_info"),
        MISC_INFO((short) 4, "misc_info"),
        ACTIVATE_MSG((short) 5, "activate_msg"),
        INSTANT_MSGS((short) 6, "instant_msgs"),
        SESSIONS((short) 7, "sessions"),
        IMPRINT((short) 8, "imprint"),
        ID_TRACKING((short) 9, "id_tracking"),
        ACTIVE_USER((short) 10, "active_user"),
        CONTROL_POLICY((short) 11, "control_policy"),
        GROUP_INFO((short) 12, "group_info");
        
        private static final Map<String, e> m = null;
        private final short n;
        private final String o;

        static {
            m = new HashMap();
            Iterator it = EnumSet.allOf(e.class).iterator();
            while (it.hasNext()) {
                e eVar = (e) it.next();
                m.put(eVar.b(), eVar);
            }
        }

        public static e a(int i) {
            switch (i) {
                case 1:
                    return CLIENT_STATS;
                case 2:
                    return APP_INFO;
                case 3:
                    return DEVICE_INFO;
                case 4:
                    return MISC_INFO;
                case 5:
                    return ACTIVATE_MSG;
                case 6:
                    return INSTANT_MSGS;
                case 7:
                    return SESSIONS;
                case 8:
                    return IMPRINT;
                case 9:
                    return ID_TRACKING;
                case 10:
                    return ACTIVE_USER;
                case 11:
                    return CONTROL_POLICY;
                case 12:
                    return GROUP_INFO;
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
            return (e) m.get(str);
        }

        private e(short s, String str) {
            this.n = s;
            this.o = str;
        }

        public short a() {
            return this.n;
        }

        public String b() {
            return this.o;
        }
    }

    public /* synthetic */ cg b(int i) {
        return a(i);
    }

    public /* synthetic */ bz g() {
        return a();
    }

    static {
        A.put(di.class, new b());
        A.put(dj.class, new d());
        Map enumMap = new EnumMap(e.class);
        enumMap.put(e.CLIENT_STATS, new cl("client_stats", (byte) 1, new cq((byte) 12, ar.class)));
        enumMap.put(e.APP_INFO, new cl("app_info", (byte) 1, new cq((byte) 12, aq.class)));
        enumMap.put(e.DEVICE_INFO, new cl("device_info", (byte) 1, new cq((byte) 12, at.class)));
        enumMap.put(e.MISC_INFO, new cl("misc_info", (byte) 1, new cq((byte) 12, bh.class)));
        enumMap.put(e.ACTIVATE_MSG, new cl("activate_msg", (byte) 2, new cq((byte) 12, ao.class)));
        enumMap.put(e.INSTANT_MSGS, new cl("instant_msgs", (byte) 2, new cn(df.m, new cq((byte) 12, be.class))));
        enumMap.put(e.SESSIONS, new cl("sessions", (byte) 2, new cn(df.m, new cq((byte) 12, bn.class))));
        enumMap.put(e.IMPRINT, new cl("imprint", (byte) 2, new cq((byte) 12, bc.class)));
        enumMap.put(e.ID_TRACKING, new cl("id_tracking", (byte) 2, new cq((byte) 12, bb.class)));
        enumMap.put(e.ACTIVE_USER, new cl("active_user", (byte) 2, new cq((byte) 12, ap.class)));
        enumMap.put(e.CONTROL_POLICY, new cl("control_policy", (byte) 2, new cq((byte) 12, as.class)));
        enumMap.put(e.GROUP_INFO, new cl("group_info", (byte) 2, new co(df.k, new cm((byte) 11), new cm((byte) 8))));
        m = Collections.unmodifiableMap(enumMap);
        cl.a(bp.class, m);
    }

    public bp() {
        this.B = new e[]{e.ACTIVATE_MSG, e.INSTANT_MSGS, e.SESSIONS, e.IMPRINT, e.ID_TRACKING, e.ACTIVE_USER, e.CONTROL_POLICY, e.GROUP_INFO};
    }

    public bp(ar arVar, aq aqVar, at atVar, bh bhVar) {
        this();
        this.a = arVar;
        this.b = aqVar;
        this.c = atVar;
        this.d = bhVar;
    }

    public bp(bp bpVar) {
        List arrayList;
        this.B = new e[]{e.ACTIVATE_MSG, e.INSTANT_MSGS, e.SESSIONS, e.IMPRINT, e.ID_TRACKING, e.ACTIVE_USER, e.CONTROL_POLICY, e.GROUP_INFO};
        if (bpVar.e()) {
            this.a = new ar(bpVar.a);
        }
        if (bpVar.i()) {
            this.b = new aq(bpVar.b);
        }
        if (bpVar.l()) {
            this.c = new at(bpVar.c);
        }
        if (bpVar.o()) {
            this.d = new bh(bpVar.d);
        }
        if (bpVar.r()) {
            this.e = new ao(bpVar.e);
        }
        if (bpVar.w()) {
            arrayList = new ArrayList();
            for (be beVar : bpVar.f) {
                arrayList.add(new be(beVar));
            }
            this.f = arrayList;
        }
        if (bpVar.B()) {
            arrayList = new ArrayList();
            for (bn bnVar : bpVar.g) {
                arrayList.add(new bn(bnVar));
            }
            this.g = arrayList;
        }
        if (bpVar.E()) {
            this.h = new bc(bpVar.h);
        }
        if (bpVar.H()) {
            this.i = new bb(bpVar.i);
        }
        if (bpVar.K()) {
            this.j = new ap(bpVar.j);
        }
        if (bpVar.N()) {
            this.k = new as(bpVar.k);
        }
        if (bpVar.R()) {
            Map hashMap = new HashMap();
            for (Entry entry : bpVar.l.entrySet()) {
                hashMap.put((String) entry.getKey(), (Integer) entry.getValue());
            }
            this.l = hashMap;
        }
    }

    public bp a() {
        return new bp(this);
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
        this.j = null;
        this.k = null;
        this.l = null;
    }

    public ar c() {
        return this.a;
    }

    public bp a(ar arVar) {
        this.a = arVar;
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

    public aq f() {
        return this.b;
    }

    public bp a(aq aqVar) {
        this.b = aqVar;
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

    public at j() {
        return this.c;
    }

    public bp a(at atVar) {
        this.c = atVar;
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

    public bh m() {
        return this.d;
    }

    public bp a(bh bhVar) {
        this.d = bhVar;
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

    public ao p() {
        return this.e;
    }

    public bp a(ao aoVar) {
        this.e = aoVar;
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

    public int s() {
        return this.f == null ? 0 : this.f.size();
    }

    public Iterator<be> t() {
        return this.f == null ? null : this.f.iterator();
    }

    public void a(be beVar) {
        if (this.f == null) {
            this.f = new ArrayList();
        }
        this.f.add(beVar);
    }

    public List<be> u() {
        return this.f;
    }

    public bp a(List<be> list) {
        this.f = list;
        return this;
    }

    public void v() {
        this.f = null;
    }

    public boolean w() {
        return this.f != null;
    }

    public void f(boolean z) {
        if (!z) {
            this.f = null;
        }
    }

    public int x() {
        return this.g == null ? 0 : this.g.size();
    }

    public Iterator<bn> y() {
        return this.g == null ? null : this.g.iterator();
    }

    public void a(bn bnVar) {
        if (this.g == null) {
            this.g = new ArrayList();
        }
        this.g.add(bnVar);
    }

    public List<bn> z() {
        return this.g;
    }

    public bp b(List<bn> list) {
        this.g = list;
        return this;
    }

    public void A() {
        this.g = null;
    }

    public boolean B() {
        return this.g != null;
    }

    public void g(boolean z) {
        if (!z) {
            this.g = null;
        }
    }

    public bc C() {
        return this.h;
    }

    public bp a(bc bcVar) {
        this.h = bcVar;
        return this;
    }

    public void D() {
        this.h = null;
    }

    public boolean E() {
        return this.h != null;
    }

    public void h(boolean z) {
        if (!z) {
            this.h = null;
        }
    }

    public bb F() {
        return this.i;
    }

    public bp a(bb bbVar) {
        this.i = bbVar;
        return this;
    }

    public void G() {
        this.i = null;
    }

    public boolean H() {
        return this.i != null;
    }

    public void i(boolean z) {
        if (!z) {
            this.i = null;
        }
    }

    public ap I() {
        return this.j;
    }

    public bp a(ap apVar) {
        this.j = apVar;
        return this;
    }

    public void J() {
        this.j = null;
    }

    public boolean K() {
        return this.j != null;
    }

    public void j(boolean z) {
        if (!z) {
            this.j = null;
        }
    }

    public as L() {
        return this.k;
    }

    public bp a(as asVar) {
        this.k = asVar;
        return this;
    }

    public void M() {
        this.k = null;
    }

    public boolean N() {
        return this.k != null;
    }

    public void k(boolean z) {
        if (!z) {
            this.k = null;
        }
    }

    public int O() {
        return this.l == null ? 0 : this.l.size();
    }

    public void a(String str, int i) {
        if (this.l == null) {
            this.l = new HashMap();
        }
        this.l.put(str, Integer.valueOf(i));
    }

    public Map<String, Integer> P() {
        return this.l;
    }

    public bp a(Map<String, Integer> map) {
        this.l = map;
        return this;
    }

    public void Q() {
        this.l = null;
    }

    public boolean R() {
        return this.l != null;
    }

    public void l(boolean z) {
        if (!z) {
            this.l = null;
        }
    }

    public e a(int i) {
        return e.a(i);
    }

    public void a(cy cyVar) throws cf {
        ((dh) A.get(cyVar.D())).b().b(cyVar, this);
    }

    public void b(cy cyVar) throws cf {
        ((dh) A.get(cyVar.D())).b().a(cyVar, this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("UALogEntry(");
        stringBuilder.append("client_stats:");
        if (this.a == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.a);
        }
        stringBuilder.append(", ");
        stringBuilder.append("app_info:");
        if (this.b == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.b);
        }
        stringBuilder.append(", ");
        stringBuilder.append("device_info:");
        if (this.c == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.c);
        }
        stringBuilder.append(", ");
        stringBuilder.append("misc_info:");
        if (this.d == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.d);
        }
        if (r()) {
            stringBuilder.append(", ");
            stringBuilder.append("activate_msg:");
            if (this.e == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.e);
            }
        }
        if (w()) {
            stringBuilder.append(", ");
            stringBuilder.append("instant_msgs:");
            if (this.f == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.f);
            }
        }
        if (B()) {
            stringBuilder.append(", ");
            stringBuilder.append("sessions:");
            if (this.g == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.g);
            }
        }
        if (E()) {
            stringBuilder.append(", ");
            stringBuilder.append("imprint:");
            if (this.h == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.h);
            }
        }
        if (H()) {
            stringBuilder.append(", ");
            stringBuilder.append("id_tracking:");
            if (this.i == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.i);
            }
        }
        if (K()) {
            stringBuilder.append(", ");
            stringBuilder.append("active_user:");
            if (this.j == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.j);
            }
        }
        if (N()) {
            stringBuilder.append(", ");
            stringBuilder.append("control_policy:");
            if (this.k == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.k);
            }
        }
        if (R()) {
            stringBuilder.append(", ");
            stringBuilder.append("group_info:");
            if (this.l == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.l);
            }
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public void S() throws cf {
        if (this.a == null) {
            throw new cz("Required field 'client_stats' was not present! Struct: " + toString());
        } else if (this.b == null) {
            throw new cz("Required field 'app_info' was not present! Struct: " + toString());
        } else if (this.c == null) {
            throw new cz("Required field 'device_info' was not present! Struct: " + toString());
        } else if (this.d == null) {
            throw new cz("Required field 'misc_info' was not present! Struct: " + toString());
        } else {
            if (this.a != null) {
                this.a.m();
            }
            if (this.b != null) {
                this.b.K();
            }
            if (this.c != null) {
                this.c.af();
            }
            if (this.d != null) {
                this.d.H();
            }
            if (this.e != null) {
                this.e.f();
            }
            if (this.h != null) {
                this.h.n();
            }
            if (this.i != null) {
                this.i.p();
            }
            if (this.j != null) {
                this.j.j();
            }
            if (this.k != null) {
                this.k.f();
            }
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
            a(new cs(new dk((InputStream) objectInputStream)));
        } catch (cf e) {
            throw new IOException(e.getMessage());
        }
    }
}
