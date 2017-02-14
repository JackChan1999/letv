package u.aly;

import com.letv.download.db.Download.DownloadVideoTable;
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
import java.util.Map.Entry;

/* compiled from: Event */
public class ax implements Serializable, Cloneable, bz<ax, e> {
    public static final Map<e, cl> f;
    private static final dd g = new dd("Event");
    private static final ct h = new ct("name", (byte) 11, (short) 1);
    private static final ct i = new ct("properties", df.k, (short) 2);
    private static final ct j = new ct(DownloadVideoTable.COLUMN_DURATION, (byte) 10, (short) 3);
    private static final ct k = new ct("acc", (byte) 8, (short) 4);
    private static final ct l = new ct("ts", (byte) 10, (short) 5);
    private static final Map<Class<? extends dg>, dh> m = new HashMap();
    private static final int n = 0;
    private static final int o = 1;
    private static final int p = 2;
    public String a;
    public Map<String, bj> b;
    public long c;
    public int d;
    public long e;
    private byte q;
    private e[] r;

    /* compiled from: Event */
    private static class a extends di<ax> {
        private a() {
        }

        public /* synthetic */ void a(cy cyVar, bz bzVar) throws cf {
            b(cyVar, (ax) bzVar);
        }

        public /* synthetic */ void b(cy cyVar, bz bzVar) throws cf {
            a(cyVar, (ax) bzVar);
        }

        public void a(cy cyVar, ax axVar) throws cf {
            cyVar.j();
            while (true) {
                ct l = cyVar.l();
                if (l.b == (byte) 0) {
                    cyVar.k();
                    if (axVar.s()) {
                        axVar.t();
                        return;
                    }
                    throw new cz("Required field 'ts' was not found in serialized data! Struct: " + toString());
                }
                switch (l.c) {
                    case (short) 1:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        axVar.a = cyVar.z();
                        axVar.a(true);
                        break;
                    case (short) 2:
                        if (l.b != df.k) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        cv n = cyVar.n();
                        axVar.b = new HashMap(n.c * 2);
                        for (int i = 0; i < n.c; i++) {
                            String z = cyVar.z();
                            bj bjVar = new bj();
                            bjVar.a(cyVar);
                            axVar.b.put(z, bjVar);
                        }
                        cyVar.o();
                        axVar.b(true);
                        break;
                    case (short) 3:
                        if (l.b != (byte) 10) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        axVar.c = cyVar.x();
                        axVar.c(true);
                        break;
                    case (short) 4:
                        if (l.b != (byte) 8) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        axVar.d = cyVar.w();
                        axVar.d(true);
                        break;
                    case (short) 5:
                        if (l.b != (byte) 10) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        axVar.e = cyVar.x();
                        axVar.e(true);
                        break;
                    default:
                        db.a(cyVar, l.b);
                        break;
                }
                cyVar.m();
            }
        }

        public void b(cy cyVar, ax axVar) throws cf {
            axVar.t();
            cyVar.a(ax.g);
            if (axVar.a != null) {
                cyVar.a(ax.h);
                cyVar.a(axVar.a);
                cyVar.c();
            }
            if (axVar.b != null) {
                cyVar.a(ax.i);
                cyVar.a(new cv((byte) 11, (byte) 12, axVar.b.size()));
                for (Entry entry : axVar.b.entrySet()) {
                    cyVar.a((String) entry.getKey());
                    ((bj) entry.getValue()).b(cyVar);
                }
                cyVar.e();
                cyVar.c();
            }
            if (axVar.m()) {
                cyVar.a(ax.j);
                cyVar.a(axVar.c);
                cyVar.c();
            }
            if (axVar.p()) {
                cyVar.a(ax.k);
                cyVar.a(axVar.d);
                cyVar.c();
            }
            cyVar.a(ax.l);
            cyVar.a(axVar.e);
            cyVar.c();
            cyVar.d();
            cyVar.b();
        }
    }

    /* compiled from: Event */
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

    /* compiled from: Event */
    private static class c extends dj<ax> {
        private c() {
        }

        public void a(cy cyVar, ax axVar) throws cf {
            de deVar = (de) cyVar;
            deVar.a(axVar.a);
            deVar.a(axVar.b.size());
            for (Entry entry : axVar.b.entrySet()) {
                deVar.a((String) entry.getKey());
                ((bj) entry.getValue()).b((cy) deVar);
            }
            deVar.a(axVar.e);
            BitSet bitSet = new BitSet();
            if (axVar.m()) {
                bitSet.set(0);
            }
            if (axVar.p()) {
                bitSet.set(1);
            }
            deVar.a(bitSet, 2);
            if (axVar.m()) {
                deVar.a(axVar.c);
            }
            if (axVar.p()) {
                deVar.a(axVar.d);
            }
        }

        public void b(cy cyVar, ax axVar) throws cf {
            de deVar = (de) cyVar;
            axVar.a = deVar.z();
            axVar.a(true);
            cv cvVar = new cv((byte) 11, (byte) 12, deVar.w());
            axVar.b = new HashMap(cvVar.c * 2);
            for (int i = 0; i < cvVar.c; i++) {
                String z = deVar.z();
                bj bjVar = new bj();
                bjVar.a((cy) deVar);
                axVar.b.put(z, bjVar);
            }
            axVar.b(true);
            axVar.e = deVar.x();
            axVar.e(true);
            BitSet b = deVar.b(2);
            if (b.get(0)) {
                axVar.c = deVar.x();
                axVar.c(true);
            }
            if (b.get(1)) {
                axVar.d = deVar.w();
                axVar.d(true);
            }
        }
    }

    /* compiled from: Event */
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

    /* compiled from: Event */
    public enum e implements cg {
        NAME((short) 1, "name"),
        PROPERTIES((short) 2, "properties"),
        DURATION((short) 3, DownloadVideoTable.COLUMN_DURATION),
        ACC((short) 4, "acc"),
        TS((short) 5, "ts");
        
        private static final Map<String, e> f = null;
        private final short g;
        private final String h;

        static {
            f = new HashMap();
            Iterator it = EnumSet.allOf(e.class).iterator();
            while (it.hasNext()) {
                e eVar = (e) it.next();
                f.put(eVar.b(), eVar);
            }
        }

        public static e a(int i) {
            switch (i) {
                case 1:
                    return NAME;
                case 2:
                    return PROPERTIES;
                case 3:
                    return DURATION;
                case 4:
                    return ACC;
                case 5:
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
            return (e) f.get(str);
        }

        private e(short s, String str) {
            this.g = s;
            this.h = str;
        }

        public short a() {
            return this.g;
        }

        public String b() {
            return this.h;
        }
    }

    public /* synthetic */ cg b(int i) {
        return c(i);
    }

    public /* synthetic */ bz g() {
        return a();
    }

    static {
        m.put(di.class, new b());
        m.put(dj.class, new d());
        Map enumMap = new EnumMap(e.class);
        enumMap.put(e.NAME, new cl("name", (byte) 1, new cm((byte) 11)));
        enumMap.put(e.PROPERTIES, new cl("properties", (byte) 1, new co(df.k, new cm((byte) 11), new cq((byte) 12, bj.class))));
        enumMap.put(e.DURATION, new cl(DownloadVideoTable.COLUMN_DURATION, (byte) 2, new cm((byte) 10)));
        enumMap.put(e.ACC, new cl("acc", (byte) 2, new cm((byte) 8)));
        enumMap.put(e.TS, new cl("ts", (byte) 1, new cm((byte) 10)));
        f = Collections.unmodifiableMap(enumMap);
        cl.a(ax.class, f);
    }

    public ax() {
        this.q = (byte) 0;
        this.r = new e[]{e.DURATION, e.ACC};
    }

    public ax(String str, Map<String, bj> map, long j) {
        this();
        this.a = str;
        this.b = map;
        this.e = j;
        e(true);
    }

    public ax(ax axVar) {
        this.q = (byte) 0;
        this.r = new e[]{e.DURATION, e.ACC};
        this.q = axVar.q;
        if (axVar.e()) {
            this.a = axVar.a;
        }
        if (axVar.j()) {
            Map hashMap = new HashMap();
            for (Entry entry : axVar.b.entrySet()) {
                hashMap.put((String) entry.getKey(), new bj((bj) entry.getValue()));
            }
            this.b = hashMap;
        }
        this.c = axVar.c;
        this.d = axVar.d;
        this.e = axVar.e;
    }

    public ax a() {
        return new ax(this);
    }

    public void b() {
        this.a = null;
        this.b = null;
        c(false);
        this.c = 0;
        d(false);
        this.d = 0;
        e(false);
        this.e = 0;
    }

    public String c() {
        return this.a;
    }

    public ax a(String str) {
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

    public int f() {
        return this.b == null ? 0 : this.b.size();
    }

    public void a(String str, bj bjVar) {
        if (this.b == null) {
            this.b = new HashMap();
        }
        this.b.put(str, bjVar);
    }

    public Map<String, bj> h() {
        return this.b;
    }

    public ax a(Map<String, bj> map) {
        this.b = map;
        return this;
    }

    public void i() {
        this.b = null;
    }

    public boolean j() {
        return this.b != null;
    }

    public void b(boolean z) {
        if (!z) {
            this.b = null;
        }
    }

    public long k() {
        return this.c;
    }

    public ax a(long j) {
        this.c = j;
        c(true);
        return this;
    }

    public void l() {
        this.q = bw.b(this.q, 0);
    }

    public boolean m() {
        return bw.a(this.q, 0);
    }

    public void c(boolean z) {
        this.q = bw.a(this.q, 0, z);
    }

    public int n() {
        return this.d;
    }

    public ax a(int i) {
        this.d = i;
        d(true);
        return this;
    }

    public void o() {
        this.q = bw.b(this.q, 1);
    }

    public boolean p() {
        return bw.a(this.q, 1);
    }

    public void d(boolean z) {
        this.q = bw.a(this.q, 1, z);
    }

    public long q() {
        return this.e;
    }

    public ax b(long j) {
        this.e = j;
        e(true);
        return this;
    }

    public void r() {
        this.q = bw.b(this.q, 2);
    }

    public boolean s() {
        return bw.a(this.q, 2);
    }

    public void e(boolean z) {
        this.q = bw.a(this.q, 2, z);
    }

    public e c(int i) {
        return e.a(i);
    }

    public void a(cy cyVar) throws cf {
        ((dh) m.get(cyVar.D())).b().b(cyVar, this);
    }

    public void b(cy cyVar) throws cf {
        ((dh) m.get(cyVar.D())).b().a(cyVar, this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Event(");
        stringBuilder.append("name:");
        if (this.a == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.a);
        }
        stringBuilder.append(", ");
        stringBuilder.append("properties:");
        if (this.b == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.b);
        }
        if (m()) {
            stringBuilder.append(", ");
            stringBuilder.append("duration:");
            stringBuilder.append(this.c);
        }
        if (p()) {
            stringBuilder.append(", ");
            stringBuilder.append("acc:");
            stringBuilder.append(this.d);
        }
        stringBuilder.append(", ");
        stringBuilder.append("ts:");
        stringBuilder.append(this.e);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public void t() throws cf {
        if (this.a == null) {
            throw new cz("Required field 'name' was not present! Struct: " + toString());
        } else if (this.b == null) {
            throw new cz("Required field 'properties' was not present! Struct: " + toString());
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
            this.q = (byte) 0;
            a(new cs(new dk((InputStream) objectInputStream)));
        } catch (cf e) {
            throw new IOException(e.getMessage());
        }
    }
}
