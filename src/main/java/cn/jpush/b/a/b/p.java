package cn.jpush.b.a.b;

import cn.jpush.a.a.aa;
import cn.jpush.a.a.ab;
import cn.jpush.a.a.ac;
import cn.jpush.a.a.ad;
import cn.jpush.a.a.ae;
import cn.jpush.a.a.af;
import cn.jpush.a.a.ai;
import cn.jpush.a.a.ak;
import cn.jpush.a.a.am;
import cn.jpush.a.a.aq;
import cn.jpush.a.a.as;
import cn.jpush.a.a.au;
import cn.jpush.a.a.aw;
import cn.jpush.a.a.ay;
import cn.jpush.a.a.b;
import cn.jpush.a.a.ba;
import cn.jpush.a.a.bc;
import cn.jpush.a.a.be;
import cn.jpush.a.a.bi;
import cn.jpush.a.a.bl;
import cn.jpush.a.a.bn;
import cn.jpush.a.a.bp;
import cn.jpush.a.a.d;
import cn.jpush.a.a.f;
import cn.jpush.a.a.h;
import cn.jpush.a.a.j;
import cn.jpush.a.a.m;
import cn.jpush.a.a.o;
import cn.jpush.a.a.q;
import cn.jpush.a.a.s;
import cn.jpush.a.a.u;
import cn.jpush.a.a.z;
import com.google.protobuf.jpush.c;

public final class p {
    private static final String[] z;
    int a;
    int b;
    long c;
    String d;
    Object e;
    af f;
    int g = -1;
    String h;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 10;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "{\u000eBVYJ\fOS\u0017M\rN\u0017\u001a\u000e";
        r0 = -1;
        r4 = r3;
    L_0x000a:
        r1 = r1.toCharArray();
        r5 = r1.length;
        r6 = 0;
        r7 = 1;
        if (r5 > r7) goto L_0x002f;
    L_0x0013:
        r7 = r1;
        r8 = r6;
        r11 = r5;
        r5 = r1;
        r1 = r11;
    L_0x0018:
        r10 = r5[r6];
        r9 = r8 % 5;
        switch(r9) {
            case 0: goto L_0x0090;
            case 1: goto L_0x0093;
            case 2: goto L_0x0096;
            case 3: goto L_0x0099;
            default: goto L_0x001f;
        };
    L_0x001f:
        r9 = 55;
    L_0x0021:
        r9 = r9 ^ r10;
        r9 = (char) r9;
        r5[r6] = r9;
        r6 = r8 + 1;
        if (r1 != 0) goto L_0x002d;
    L_0x0029:
        r5 = r7;
        r8 = r6;
        r6 = r1;
        goto L_0x0018;
    L_0x002d:
        r5 = r1;
        r1 = r7;
    L_0x002f:
        if (r5 > r6) goto L_0x0013;
    L_0x0031:
        r5 = new java.lang.String;
        r5.<init>(r1);
        r1 = r5.intern();
        switch(r0) {
            case 0: goto L_0x0045;
            case 1: goto L_0x004d;
            case 2: goto L_0x0055;
            case 3: goto L_0x005d;
            case 4: goto L_0x0066;
            case 5: goto L_0x006e;
            case 6: goto L_0x0076;
            case 7: goto L_0x0080;
            case 8: goto L_0x008b;
            default: goto L_0x003d;
        };
    L_0x003d:
        r3[r2] = r1;
        r2 = 1;
        r1 = "\u0002@OYCG\u0014S\r";
        r0 = 0;
        r3 = r4;
        goto L_0x000a;
    L_0x0045:
        r3[r2] = r1;
        r2 = 2;
        r1 = "\u0002@KGGE\u0005S\r";
        r0 = 1;
        r3 = r4;
        goto L_0x000a;
    L_0x004d:
        r3[r2] = r1;
        r2 = 3;
        r1 = "\u0002@XRD^\u000fDDRm\u000fNR\r";
        r0 = 2;
        r3 = r4;
        goto L_0x000a;
    L_0x0055:
        r3[r2] = r1;
        r2 = 4;
        r1 = "\u0002@XRD^\u000fDDRc\u0005YDVI\u0005\u0010";
        r0 = 3;
        r3 = r4;
        goto L_0x000a;
    L_0x005d:
        r3[r2] = r1;
        r2 = 5;
        r1 = "u)ggEA\u0014ETXB=\n\u001a\u0017M\u000fGZV@\u0004\u0010";
        r0 = 4;
        r3 = r4;
        goto L_0x000a;
    L_0x0066:
        r3[r2] = r1;
        r2 = 6;
        r1 = "\u0002@\\RE]\tEY\r";
        r0 = 5;
        r3 = r4;
        goto L_0x000a;
    L_0x006e:
        r3[r2] = r1;
        r2 = 7;
        r1 = "\u0002@_^S\u0014";
        r0 = 6;
        r3 = r4;
        goto L_0x000a;
    L_0x0076:
        r3[r2] = r1;
        r2 = 8;
        r1 = "{\u000eBVYJ\fOS\u0017g-\nTZJ@SRC\u000eM\n";
        r0 = 7;
        r3 = r4;
        goto L_0x000a;
    L_0x0080:
        r3[r2] = r1;
        r2 = 9;
        r1 = "u*zBDF=\n\u001a\u0017";
        r0 = 8;
        r3 = r4;
        goto L_0x000a;
    L_0x008b:
        r3[r2] = r1;
        z = r4;
        return;
    L_0x0090:
        r9 = 46;
        goto L_0x0021;
    L_0x0093:
        r9 = 96;
        goto L_0x0021;
    L_0x0096:
        r9 = 42;
        goto L_0x0021;
    L_0x0099:
        r9 = 55;
        goto L_0x0021;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.b.a.b.p.<clinit>():void");
    }

    public p(int i, int i2, long j, String str, Object obj) {
        this.a = i;
        this.b = 1;
        this.c = j;
        this.d = str;
        this.e = obj;
    }

    public p(byte[] bArr) {
        z a = z.a(bArr);
        ad d = a.d();
        ab f = a.f();
        this.a = d.d();
        this.b = d.f();
        this.c = d.h();
        if (d.j() != null) {
            this.d = d.j().b();
        }
        this.f = f.N();
        switch (this.a) {
            case 1:
                this.e = f.d();
                return;
            case 2:
                this.e = f.f();
                return;
            case 3:
                this.e = f.h();
                return;
            case 4:
                this.e = f.j();
                return;
            case 5:
                this.e = f.l();
                return;
            case 6:
                this.e = f.n();
                return;
            case 7:
                this.e = f.p();
                return;
            case 8:
                this.e = f.r();
                return;
            case 9:
                this.e = f.t();
                return;
            case 10:
                this.e = f.v();
                return;
            case 11:
                this.e = f.x();
                return;
            case 12:
                this.e = f.z();
                return;
            case 13:
                this.e = f.B();
                return;
            case 14:
                this.e = f.D();
                return;
            case 15:
                this.e = f.F();
                return;
            case 16:
                this.e = f.H();
                return;
            case 18:
                this.e = f.J();
                return;
            case 19:
                this.e = f.L();
                return;
            case 23:
                this.e = f.P();
                return;
            case 31:
                this.e = f.R();
                return;
            case 32:
                this.e = f.T();
                return;
            case 33:
                this.e = f.V();
                return;
            case 34:
                this.e = f.X();
                return;
            case 35:
                this.e = f.Z();
                return;
            case 36:
                this.e = f.ab();
                return;
            default:
                System.out.println(new StringBuilder(z[9]).append(new StringBuilder(z[8]).append(this.a).toString()).toString());
                this.e = null;
                return;
        }
    }

    public final z a() {
        ae a = ad.p().a(this.a).b(this.b).a(this.c);
        if (!(this.d == null || "".equalsIgnoreCase(this.d.trim()))) {
            a.a(c.a(this.d));
        }
        aa a2 = z.h().a(a.a());
        int i = this.a;
        Object obj = this.e;
        af afVar = this.f;
        ac ad = ab.ad();
        if (afVar != null) {
            ad.a(afVar);
        }
        if (obj != null) {
            switch (i) {
                case 1:
                    ad.a((bl) obj);
                    break;
                case 2:
                    ad.a((bn) obj);
                    break;
                case 3:
                    ad.a((bi) obj);
                    break;
                case 4:
                    ad.a((be) obj);
                    break;
                case 5:
                    ad.a((d) obj);
                    break;
                case 6:
                    ad.a((h) obj);
                    break;
                case 7:
                    ad.a((j) obj);
                    break;
                case 8:
                    ad.a((o) obj);
                    break;
                case 9:
                    ad.a((s) obj);
                    break;
                case 10:
                    ad.a((m) obj);
                    break;
                case 11:
                    ad.a((q) obj);
                    break;
                case 12:
                    ad.a((u) obj);
                    break;
                case 13:
                    ad.a((ba) obj);
                    break;
                case 14:
                    ad.a((aq) obj);
                    break;
                case 15:
                    ad.a((bc) obj);
                    break;
                case 16:
                    ad.a((ay) obj);
                    break;
                case 18:
                    ad.a((b) obj);
                    break;
                case 19:
                    ad.a((f) obj);
                    break;
                case 23:
                    ad.a((bp) obj);
                    break;
                case 31:
                    ad.a((am) obj);
                    break;
                case 32:
                    ad.a((aw) obj);
                    break;
                case 33:
                    ad.a((ak) obj);
                    break;
                case 34:
                    ad.a((au) obj);
                    break;
                case 35:
                    ad.a((ai) obj);
                    break;
                case 36:
                    ad.a((as) obj);
                    break;
                default:
                    cn.jpush.b.a.c.b.a(new StringBuilder(z[0]).append(i).toString());
                    break;
            }
        }
        return a2.a(ad.a()).a();
    }

    public final int b() {
        return this.a;
    }

    public final String toString() {
        StringBuilder append = new StringBuilder(z[5]).append(this.a).append(z[6]).append(this.b).append(z[7]).append(this.c).append(z[2]).append(this.d).append(this.g >= 0 ? new StringBuilder(z[3]).append(this.g).toString() : "");
        String stringBuilder = (this.g < 0 || this.h == null) ? "" : new StringBuilder(z[4]).append(this.h).toString();
        return append.append(stringBuilder).append(this.e == null ? "" : new StringBuilder(z[1]).append(this.e.toString()).toString()).toString();
    }
}
