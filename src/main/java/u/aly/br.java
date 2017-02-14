package u.aly;

import com.letv.download.db.Download.DownloadVideoTable;
import com.tencent.open.GameAppOperation;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: UMEnvelope */
public class br implements Serializable, Cloneable, bz<br, e> {
    private static final int A = 3;
    public static final Map<e, cl> k;
    private static final dd l = new dd("UMEnvelope");
    private static final ct m = new ct("version", (byte) 11, (short) 1);
    private static final ct n = new ct("address", (byte) 11, (short) 2);
    private static final ct o = new ct(GameAppOperation.GAME_SIGNATURE, (byte) 11, (short) 3);
    private static final ct p = new ct("serial_num", (byte) 8, (short) 4);
    private static final ct q = new ct("ts_secs", (byte) 8, (short) 5);
    private static final ct r = new ct(DownloadVideoTable.COLUMN_LENGTH, (byte) 8, (short) 6);
    private static final ct s = new ct("entity", (byte) 11, (short) 7);
    private static final ct t = new ct("guid", (byte) 11, (short) 8);
    private static final ct u = new ct("checksum", (byte) 11, (short) 9);
    private static final ct v = new ct("codex", (byte) 8, (short) 10);
    private static final Map<Class<? extends dg>, dh> w = new HashMap();
    private static final int x = 0;
    private static final int y = 1;
    private static final int z = 2;
    private byte B;
    private e[] C;
    public String a;
    public String b;
    public String c;
    public int d;
    public int e;
    public int f;
    public ByteBuffer g;
    public String h;
    public String i;
    public int j;

    /* compiled from: UMEnvelope */
    private static class a extends di<br> {
        private a() {
        }

        public /* synthetic */ void a(cy cyVar, bz bzVar) throws cf {
            b(cyVar, (br) bzVar);
        }

        public /* synthetic */ void b(cy cyVar, bz bzVar) throws cf {
            a(cyVar, (br) bzVar);
        }

        public void a(cy cyVar, br brVar) throws cf {
            cyVar.j();
            while (true) {
                ct l = cyVar.l();
                if (l.b == (byte) 0) {
                    cyVar.k();
                    if (!brVar.o()) {
                        throw new cz("Required field 'serial_num' was not found in serialized data! Struct: " + toString());
                    } else if (!brVar.r()) {
                        throw new cz("Required field 'ts_secs' was not found in serialized data! Struct: " + toString());
                    } else if (brVar.u()) {
                        brVar.I();
                        return;
                    } else {
                        throw new cz("Required field 'length' was not found in serialized data! Struct: " + toString());
                    }
                }
                switch (l.c) {
                    case (short) 1:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        brVar.a = cyVar.z();
                        brVar.a(true);
                        break;
                    case (short) 2:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        brVar.b = cyVar.z();
                        brVar.b(true);
                        break;
                    case (short) 3:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        brVar.c = cyVar.z();
                        brVar.c(true);
                        break;
                    case (short) 4:
                        if (l.b != (byte) 8) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        brVar.d = cyVar.w();
                        brVar.d(true);
                        break;
                    case (short) 5:
                        if (l.b != (byte) 8) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        brVar.e = cyVar.w();
                        brVar.e(true);
                        break;
                    case (short) 6:
                        if (l.b != (byte) 8) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        brVar.f = cyVar.w();
                        brVar.f(true);
                        break;
                    case (short) 7:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        brVar.g = cyVar.A();
                        brVar.g(true);
                        break;
                    case (short) 8:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        brVar.h = cyVar.z();
                        brVar.h(true);
                        break;
                    case (short) 9:
                        if (l.b != (byte) 11) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        brVar.i = cyVar.z();
                        brVar.i(true);
                        break;
                    case (short) 10:
                        if (l.b != (byte) 8) {
                            db.a(cyVar, l.b);
                            break;
                        }
                        brVar.j = cyVar.w();
                        brVar.j(true);
                        break;
                    default:
                        db.a(cyVar, l.b);
                        break;
                }
                cyVar.m();
            }
        }

        public void b(cy cyVar, br brVar) throws cf {
            brVar.I();
            cyVar.a(br.l);
            if (brVar.a != null) {
                cyVar.a(br.m);
                cyVar.a(brVar.a);
                cyVar.c();
            }
            if (brVar.b != null) {
                cyVar.a(br.n);
                cyVar.a(brVar.b);
                cyVar.c();
            }
            if (brVar.c != null) {
                cyVar.a(br.o);
                cyVar.a(brVar.c);
                cyVar.c();
            }
            cyVar.a(br.p);
            cyVar.a(brVar.d);
            cyVar.c();
            cyVar.a(br.q);
            cyVar.a(brVar.e);
            cyVar.c();
            cyVar.a(br.r);
            cyVar.a(brVar.f);
            cyVar.c();
            if (brVar.g != null) {
                cyVar.a(br.s);
                cyVar.a(brVar.g);
                cyVar.c();
            }
            if (brVar.h != null) {
                cyVar.a(br.t);
                cyVar.a(brVar.h);
                cyVar.c();
            }
            if (brVar.i != null) {
                cyVar.a(br.u);
                cyVar.a(brVar.i);
                cyVar.c();
            }
            if (brVar.H()) {
                cyVar.a(br.v);
                cyVar.a(brVar.j);
                cyVar.c();
            }
            cyVar.d();
            cyVar.b();
        }
    }

    /* compiled from: UMEnvelope */
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

    /* compiled from: UMEnvelope */
    private static class c extends dj<br> {
        private c() {
        }

        public void a(cy cyVar, br brVar) throws cf {
            de deVar = (de) cyVar;
            deVar.a(brVar.a);
            deVar.a(brVar.b);
            deVar.a(brVar.c);
            deVar.a(brVar.d);
            deVar.a(brVar.e);
            deVar.a(brVar.f);
            deVar.a(brVar.g);
            deVar.a(brVar.h);
            deVar.a(brVar.i);
            BitSet bitSet = new BitSet();
            if (brVar.H()) {
                bitSet.set(0);
            }
            deVar.a(bitSet, 1);
            if (brVar.H()) {
                deVar.a(brVar.j);
            }
        }

        public void b(cy cyVar, br brVar) throws cf {
            de deVar = (de) cyVar;
            brVar.a = deVar.z();
            brVar.a(true);
            brVar.b = deVar.z();
            brVar.b(true);
            brVar.c = deVar.z();
            brVar.c(true);
            brVar.d = deVar.w();
            brVar.d(true);
            brVar.e = deVar.w();
            brVar.e(true);
            brVar.f = deVar.w();
            brVar.f(true);
            brVar.g = deVar.A();
            brVar.g(true);
            brVar.h = deVar.z();
            brVar.h(true);
            brVar.i = deVar.z();
            brVar.i(true);
            if (deVar.b(1).get(0)) {
                brVar.j = deVar.w();
                brVar.j(true);
            }
        }
    }

    /* compiled from: UMEnvelope */
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

    /* compiled from: UMEnvelope */
    public enum e implements cg {
        VERSION((short) 1, "version"),
        ADDRESS((short) 2, "address"),
        SIGNATURE((short) 3, GameAppOperation.GAME_SIGNATURE),
        SERIAL_NUM((short) 4, "serial_num"),
        TS_SECS((short) 5, "ts_secs"),
        LENGTH((short) 6, DownloadVideoTable.COLUMN_LENGTH),
        ENTITY((short) 7, "entity"),
        GUID((short) 8, "guid"),
        CHECKSUM((short) 9, "checksum"),
        CODEX((short) 10, "codex");
        
        private static final Map<String, e> k = null;
        private final short l;
        private final String m;

        static {
            k = new HashMap();
            Iterator it = EnumSet.allOf(e.class).iterator();
            while (it.hasNext()) {
                e eVar = (e) it.next();
                k.put(eVar.b(), eVar);
            }
        }

        public static e a(int i) {
            switch (i) {
                case 1:
                    return VERSION;
                case 2:
                    return ADDRESS;
                case 3:
                    return SIGNATURE;
                case 4:
                    return SERIAL_NUM;
                case 5:
                    return TS_SECS;
                case 6:
                    return LENGTH;
                case 7:
                    return ENTITY;
                case 8:
                    return GUID;
                case 9:
                    return CHECKSUM;
                case 10:
                    return CODEX;
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
            return (e) k.get(str);
        }

        private e(short s, String str) {
            this.l = s;
            this.m = str;
        }

        public short a() {
            return this.l;
        }

        public String b() {
            return this.m;
        }
    }

    public /* synthetic */ cg b(int i) {
        return f(i);
    }

    public /* synthetic */ bz g() {
        return a();
    }

    static {
        w.put(di.class, new b());
        w.put(dj.class, new d());
        Map enumMap = new EnumMap(e.class);
        enumMap.put(e.VERSION, new cl("version", (byte) 1, new cm((byte) 11)));
        enumMap.put(e.ADDRESS, new cl("address", (byte) 1, new cm((byte) 11)));
        enumMap.put(e.SIGNATURE, new cl(GameAppOperation.GAME_SIGNATURE, (byte) 1, new cm((byte) 11)));
        enumMap.put(e.SERIAL_NUM, new cl("serial_num", (byte) 1, new cm((byte) 8)));
        enumMap.put(e.TS_SECS, new cl("ts_secs", (byte) 1, new cm((byte) 8)));
        enumMap.put(e.LENGTH, new cl(DownloadVideoTable.COLUMN_LENGTH, (byte) 1, new cm((byte) 8)));
        enumMap.put(e.ENTITY, new cl("entity", (byte) 1, new cm((byte) 11, true)));
        enumMap.put(e.GUID, new cl("guid", (byte) 1, new cm((byte) 11)));
        enumMap.put(e.CHECKSUM, new cl("checksum", (byte) 1, new cm((byte) 11)));
        enumMap.put(e.CODEX, new cl("codex", (byte) 2, new cm((byte) 8)));
        k = Collections.unmodifiableMap(enumMap);
        cl.a(br.class, k);
    }

    public br() {
        this.B = (byte) 0;
        this.C = new e[]{e.CODEX};
    }

    public br(String str, String str2, String str3, int i, int i2, int i3, ByteBuffer byteBuffer, String str4, String str5) {
        this();
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = i;
        d(true);
        this.e = i2;
        e(true);
        this.f = i3;
        f(true);
        this.g = byteBuffer;
        this.h = str4;
        this.i = str5;
    }

    public br(br brVar) {
        this.B = (byte) 0;
        this.C = new e[]{e.CODEX};
        this.B = brVar.B;
        if (brVar.e()) {
            this.a = brVar.a;
        }
        if (brVar.i()) {
            this.b = brVar.b;
        }
        if (brVar.l()) {
            this.c = brVar.c;
        }
        this.d = brVar.d;
        this.e = brVar.e;
        this.f = brVar.f;
        if (brVar.y()) {
            this.g = ca.d(brVar.g);
        }
        if (brVar.B()) {
            this.h = brVar.h;
        }
        if (brVar.E()) {
            this.i = brVar.i;
        }
        this.j = brVar.j;
    }

    public br a() {
        return new br(this);
    }

    public void b() {
        this.a = null;
        this.b = null;
        this.c = null;
        d(false);
        this.d = 0;
        e(false);
        this.e = 0;
        f(false);
        this.f = 0;
        this.g = null;
        this.h = null;
        this.i = null;
        j(false);
        this.j = 0;
    }

    public String c() {
        return this.a;
    }

    public br a(String str) {
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

    public br b(String str) {
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

    public br c(String str) {
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

    public int m() {
        return this.d;
    }

    public br a(int i) {
        this.d = i;
        d(true);
        return this;
    }

    public void n() {
        this.B = bw.b(this.B, 0);
    }

    public boolean o() {
        return bw.a(this.B, 0);
    }

    public void d(boolean z) {
        this.B = bw.a(this.B, 0, z);
    }

    public int p() {
        return this.e;
    }

    public br c(int i) {
        this.e = i;
        e(true);
        return this;
    }

    public void q() {
        this.B = bw.b(this.B, 1);
    }

    public boolean r() {
        return bw.a(this.B, 1);
    }

    public void e(boolean z) {
        this.B = bw.a(this.B, 1, z);
    }

    public int s() {
        return this.f;
    }

    public br d(int i) {
        this.f = i;
        f(true);
        return this;
    }

    public void t() {
        this.B = bw.b(this.B, 2);
    }

    public boolean u() {
        return bw.a(this.B, 2);
    }

    public void f(boolean z) {
        this.B = bw.a(this.B, 2, z);
    }

    public byte[] v() {
        a(ca.c(this.g));
        return this.g == null ? null : this.g.array();
    }

    public ByteBuffer w() {
        return this.g;
    }

    public br a(byte[] bArr) {
        a(bArr == null ? (ByteBuffer) null : ByteBuffer.wrap(bArr));
        return this;
    }

    public br a(ByteBuffer byteBuffer) {
        this.g = byteBuffer;
        return this;
    }

    public void x() {
        this.g = null;
    }

    public boolean y() {
        return this.g != null;
    }

    public void g(boolean z) {
        if (!z) {
            this.g = null;
        }
    }

    public String z() {
        return this.h;
    }

    public br d(String str) {
        this.h = str;
        return this;
    }

    public void A() {
        this.h = null;
    }

    public boolean B() {
        return this.h != null;
    }

    public void h(boolean z) {
        if (!z) {
            this.h = null;
        }
    }

    public String C() {
        return this.i;
    }

    public br e(String str) {
        this.i = str;
        return this;
    }

    public void D() {
        this.i = null;
    }

    public boolean E() {
        return this.i != null;
    }

    public void i(boolean z) {
        if (!z) {
            this.i = null;
        }
    }

    public int F() {
        return this.j;
    }

    public br e(int i) {
        this.j = i;
        j(true);
        return this;
    }

    public void G() {
        this.B = bw.b(this.B, 3);
    }

    public boolean H() {
        return bw.a(this.B, 3);
    }

    public void j(boolean z) {
        this.B = bw.a(this.B, 3, z);
    }

    public e f(int i) {
        return e.a(i);
    }

    public void a(cy cyVar) throws cf {
        ((dh) w.get(cyVar.D())).b().b(cyVar, this);
    }

    public void b(cy cyVar) throws cf {
        ((dh) w.get(cyVar.D())).b().a(cyVar, this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("UMEnvelope(");
        stringBuilder.append("version:");
        if (this.a == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.a);
        }
        stringBuilder.append(", ");
        stringBuilder.append("address:");
        if (this.b == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.b);
        }
        stringBuilder.append(", ");
        stringBuilder.append("signature:");
        if (this.c == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.c);
        }
        stringBuilder.append(", ");
        stringBuilder.append("serial_num:");
        stringBuilder.append(this.d);
        stringBuilder.append(", ");
        stringBuilder.append("ts_secs:");
        stringBuilder.append(this.e);
        stringBuilder.append(", ");
        stringBuilder.append("length:");
        stringBuilder.append(this.f);
        stringBuilder.append(", ");
        stringBuilder.append("entity:");
        if (this.g == null) {
            stringBuilder.append("null");
        } else {
            ca.a(this.g, stringBuilder);
        }
        stringBuilder.append(", ");
        stringBuilder.append("guid:");
        if (this.h == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.h);
        }
        stringBuilder.append(", ");
        stringBuilder.append("checksum:");
        if (this.i == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.i);
        }
        if (H()) {
            stringBuilder.append(", ");
            stringBuilder.append("codex:");
            stringBuilder.append(this.j);
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public void I() throws cf {
        if (this.a == null) {
            throw new cz("Required field 'version' was not present! Struct: " + toString());
        } else if (this.b == null) {
            throw new cz("Required field 'address' was not present! Struct: " + toString());
        } else if (this.c == null) {
            throw new cz("Required field 'signature' was not present! Struct: " + toString());
        } else if (this.g == null) {
            throw new cz("Required field 'entity' was not present! Struct: " + toString());
        } else if (this.h == null) {
            throw new cz("Required field 'guid' was not present! Struct: " + toString());
        } else if (this.i == null) {
            throw new cz("Required field 'checksum' was not present! Struct: " + toString());
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
