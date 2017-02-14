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

/* compiled from: Ekv */
public class au implements Serializable, Cloneable, bz<au, e> {
    public static final Map<e, cl> f;
    private static final dd g = new dd("Ekv");
    private static final ct h = new ct("ts", (byte) 10, (short) 1);
    private static final ct i = new ct("name", (byte) 11, (short) 2);
    private static final ct j = new ct("ckv", df.k, (short) 3);
    private static final ct k = new ct(DownloadVideoTable.COLUMN_DURATION, (byte) 10, (short) 4);
    private static final ct l = new ct("acc", (byte) 8, (short) 5);
    private static final Map<Class<? extends dg>, dh> m = new HashMap();
    private static final int n = 0;
    private static final int o = 1;
    private static final int p = 2;
    public long a;
    public String b;
    public Map<String, String> c;
    public long d;
    public int e;
    private byte q;
    private e[] r;

    /* compiled from: Ekv */
    private static class a extends di<au> {
        private a() {
        }

        public /* synthetic */ void a(cy cyVar, bz bzVar) throws cf {
            b(cyVar, (au) bzVar);
        }

        public /* synthetic */ void b(cy cyVar, bz bzVar) throws cf {
            a(cyVar, (au) bzVar);
        }

        public void a(cy cyVar, au auVar) throws cf {
            cyVar.j();
            while (true) {
                ct l = cyVar.l();
                if (l.b == (byte) 0) {
                    cyVar.k();
                    if (auVar.e()) {
                        auVar.t();
                        return;
                    }
                    throw new cz("Required field 'ts' was not found in serialized data! Struct: " + toString());
                }
                switch (l.c) {
                    case (short) 1:
                        if (l.b != (byte) 10) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        auVar.a = cyVar.x();
                        auVar.a(true);
                        break;
                    case (short) 2:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        auVar.b = cyVar.z();
                        auVar.b(true);
                        break;
                    case (short) 3:
                        if (l.b != df.k) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        cv n = cyVar.n();
                        auVar.c = new HashMap(n.c * 2);
                        for (int i = 0; i < n.c; i++) {
                            auVar.c.put(cyVar.z(), cyVar.z());
                        }
                        cyVar.o();
                        auVar.c(true);
                        break;
                    case (short) 4:
                        if (l.b != (byte) 10) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        auVar.d = cyVar.x();
                        auVar.d(true);
                        break;
                    case (short) 5:
                        if (l.b != (byte) 8) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        auVar.e = cyVar.w();
                        auVar.e(true);
                        break;
                    default:
                        db.a(cyVar, l.b);
                        break;
                }
                cyVar.m();
            }
        }

        public void b(cy cyVar, au auVar) throws cf {
            auVar.t();
            cyVar.a(au.g);
            cyVar.a(au.h);
            cyVar.a(auVar.a);
            cyVar.c();
            if (auVar.b != null) {
                cyVar.a(au.i);
                cyVar.a(auVar.b);
                cyVar.c();
            }
            if (auVar.c != null) {
                cyVar.a(au.j);
                cyVar.a(new cv((byte) 11, (byte) 11, auVar.c.size()));
                for (Entry entry : auVar.c.entrySet()) {
                    cyVar.a((String) entry.getKey());
                    cyVar.a((String) entry.getValue());
                }
                cyVar.e();
                cyVar.c();
            }
            if (auVar.p()) {
                cyVar.a(au.k);
                cyVar.a(auVar.d);
                cyVar.c();
            }
            if (auVar.s()) {
                cyVar.a(au.l);
                cyVar.a(auVar.e);
                cyVar.c();
            }
            cyVar.d();
            cyVar.b();
        }
    }

    /* compiled from: Ekv */
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

    /* compiled from: Ekv */
    private static class c extends dj<au> {
        private c() {
        }

        public void a(cy cyVar, au auVar) throws cf {
            de deVar = (de) cyVar;
            deVar.a(auVar.a);
            deVar.a(auVar.b);
            deVar.a(auVar.c.size());
            for (Entry entry : auVar.c.entrySet()) {
                deVar.a((String) entry.getKey());
                deVar.a((String) entry.getValue());
            }
            BitSet bitSet = new BitSet();
            if (auVar.p()) {
                bitSet.set(0);
            }
            if (auVar.s()) {
                bitSet.set(1);
            }
            deVar.a(bitSet, 2);
            if (auVar.p()) {
                deVar.a(auVar.d);
            }
            if (auVar.s()) {
                deVar.a(auVar.e);
            }
        }

        public void b(cy cyVar, au auVar) throws cf {
            de deVar = (de) cyVar;
            auVar.a = deVar.x();
            auVar.a(true);
            auVar.b = deVar.z();
            auVar.b(true);
            cv cvVar = new cv((byte) 11, (byte) 11, deVar.w());
            auVar.c = new HashMap(cvVar.c * 2);
            for (int i = 0; i < cvVar.c; i++) {
                auVar.c.put(deVar.z(), deVar.z());
            }
            auVar.c(true);
            BitSet b = deVar.b(2);
            if (b.get(0)) {
                auVar.d = deVar.x();
                auVar.d(true);
            }
            if (b.get(1)) {
                auVar.e = deVar.w();
                auVar.e(true);
            }
        }
    }

    /* compiled from: Ekv */
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

    /* compiled from: Ekv */
    public enum e implements cg {
        TS((short) 1, "ts"),
        NAME((short) 2, "name"),
        CKV((short) 3, "ckv"),
        DURATION((short) 4, DownloadVideoTable.COLUMN_DURATION),
        ACC((short) 5, "acc");
        
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
                    return TS;
                case 2:
                    return NAME;
                case 3:
                    return CKV;
                case 4:
                    return DURATION;
                case 5:
                    return ACC;
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
        enumMap.put(e.TS, new cl("ts", (byte) 1, new cm((byte) 10)));
        enumMap.put(e.NAME, new cl("name", (byte) 1, new cm((byte) 11)));
        enumMap.put(e.CKV, new cl("ckv", (byte) 1, new co(df.k, new cm((byte) 11), new cm((byte) 11))));
        enumMap.put(e.DURATION, new cl(DownloadVideoTable.COLUMN_DURATION, (byte) 2, new cm((byte) 10)));
        enumMap.put(e.ACC, new cl("acc", (byte) 2, new cm((byte) 8)));
        f = Collections.unmodifiableMap(enumMap);
        cl.a(au.class, f);
    }

    public au() {
        this.q = (byte) 0;
        this.r = new e[]{e.DURATION, e.ACC};
    }

    public au(long j, String str, Map<String, String> map) {
        this();
        this.a = j;
        a(true);
        this.b = str;
        this.c = map;
    }

    public au(au auVar) {
        this.q = (byte) 0;
        this.r = new e[]{e.DURATION, e.ACC};
        this.q = auVar.q;
        this.a = auVar.a;
        if (auVar.i()) {
            this.b = auVar.b;
        }
        if (auVar.m()) {
            Map hashMap = new HashMap();
            for (Entry entry : auVar.c.entrySet()) {
                hashMap.put((String) entry.getKey(), (String) entry.getValue());
            }
            this.c = hashMap;
        }
        this.d = auVar.d;
        this.e = auVar.e;
    }

    public au a() {
        return new au(this);
    }

    public void b() {
        a(false);
        this.a = 0;
        this.b = null;
        this.c = null;
        d(false);
        this.d = 0;
        e(false);
        this.e = 0;
    }

    public long c() {
        return this.a;
    }

    public au a(long j) {
        this.a = j;
        a(true);
        return this;
    }

    public void d() {
        this.q = bw.b(this.q, 0);
    }

    public boolean e() {
        return bw.a(this.q, 0);
    }

    public void a(boolean z) {
        this.q = bw.a(this.q, 0, z);
    }

    public String f() {
        return this.b;
    }

    public au a(String str) {
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
        return this.c == null ? 0 : this.c.size();
    }

    public void a(String str, String str2) {
        if (this.c == null) {
            this.c = new HashMap();
        }
        this.c.put(str, str2);
    }

    public Map<String, String> k() {
        return this.c;
    }

    public au a(Map<String, String> map) {
        this.c = map;
        return this;
    }

    public void l() {
        this.c = null;
    }

    public boolean m() {
        return this.c != null;
    }

    public void c(boolean z) {
        if (!z) {
            this.c = null;
        }
    }

    public long n() {
        return this.d;
    }

    public au b(long j) {
        this.d = j;
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

    public int q() {
        return this.e;
    }

    public au a(int i) {
        this.e = i;
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
        StringBuilder stringBuilder = new StringBuilder("Ekv(");
        stringBuilder.append("ts:");
        stringBuilder.append(this.a);
        stringBuilder.append(", ");
        stringBuilder.append("name:");
        if (this.b == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.b);
        }
        stringBuilder.append(", ");
        stringBuilder.append("ckv:");
        if (this.c == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.c);
        }
        if (p()) {
            stringBuilder.append(", ");
            stringBuilder.append("duration:");
            stringBuilder.append(this.d);
        }
        if (s()) {
            stringBuilder.append(", ");
            stringBuilder.append("acc:");
            stringBuilder.append(this.e);
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public void t() throws cf {
        if (this.b == null) {
            throw new cz("Required field 'name' was not present! Struct: " + toString());
        } else if (this.c == null) {
            throw new cz("Required field 'ckv' was not present! Struct: " + toString());
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
