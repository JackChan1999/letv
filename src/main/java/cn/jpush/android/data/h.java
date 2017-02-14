package cn.jpush.android.data;

public final class h {
    private static final String[] z;
    private long a;
    private int b;
    private int c;
    private int d;
    private String e;
    private long f;
    private long g;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 7;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "\u00036v*\"]sw+\u000bJ+";
        r0 = -1;
        r4 = r3;
    L_0x0008:
        r1 = r1.toCharArray();
        r5 = r1.length;
        r6 = 0;
        r7 = 1;
        if (r5 > r7) goto L_0x002d;
    L_0x0011:
        r7 = r1;
        r8 = r6;
        r11 = r5;
        r5 = r1;
        r1 = r11;
    L_0x0016:
        r10 = r5[r6];
        r9 = r8 % 5;
        switch(r9) {
            case 0: goto L_0x0070;
            case 1: goto L_0x0073;
            case 2: goto L_0x0076;
            case 3: goto L_0x0079;
            default: goto L_0x001d;
        };
    L_0x001d:
        r9 = 125; // 0x7d float:1.75E-43 double:6.2E-322;
    L_0x001f:
        r9 = r9 ^ r10;
        r9 = (char) r9;
        r5[r6] = r9;
        r6 = r8 + 1;
        if (r1 != 0) goto L_0x002b;
    L_0x0027:
        r5 = r7;
        r8 = r6;
        r6 = r1;
        goto L_0x0016;
    L_0x002b:
        r5 = r1;
        r1 = r7;
    L_0x002d:
        if (r5 > r6) goto L_0x0011;
    L_0x002f:
        r5 = new java.lang.String;
        r5.<init>(r1);
        r1 = r5.intern();
        switch(r0) {
            case 0: goto L_0x0043;
            case 1: goto L_0x004b;
            case 2: goto L_0x0053;
            case 3: goto L_0x005b;
            case 4: goto L_0x0063;
            case 5: goto L_0x006b;
            default: goto L_0x003b;
        };
    L_0x003b:
        r3[r2] = r1;
        r2 = 1;
        r1 = "cyy%\u0011ayn-\u001bFu{0\u0014@x^\u00069Nb{d&CxE-\u0019\u0012";
        r0 = 0;
        r3 = r4;
        goto L_0x0008;
    L_0x0043:
        r3[r2] = r1;
        r2 = 2;
        r1 = "\u00036v*\"[oj!@";
        r0 = 1;
        r3 = r4;
        goto L_0x0008;
    L_0x004b:
        r3[r2] = r1;
        r2 = 3;
        r1 = "\u00036v*\"Nr~\u001b\tF{y";
        r0 = 2;
        r3 = r4;
        goto L_0x0008;
    L_0x0053:
        r3[r2] = r1;
        r2 = 4;
        r1 = "\u00036v*\"[ds#\u001aJdE0\u0014Bs'";
        r0 = 3;
        r3 = r4;
        goto L_0x0008;
    L_0x005b:
        r3[r2] = r1;
        r2 = 5;
        r1 = "\u00036v*\"Jnn6\u001c\u0012";
        r0 = 4;
        r3 = r4;
        goto L_0x0008;
    L_0x0063:
        r3[r2] = r1;
        r2 = 6;
        r1 = "\u00036v*\"Lyo*\t\u0012";
        r0 = 5;
        r3 = r4;
        goto L_0x0008;
    L_0x006b:
        r3[r2] = r1;
        z = r4;
        return;
    L_0x0070:
        r9 = 47;
        goto L_0x001f;
    L_0x0073:
        r9 = 22;
        goto L_0x001f;
    L_0x0076:
        r9 = 26;
        goto L_0x001f;
    L_0x0079:
        r9 = 68;
        goto L_0x001f;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.data.h.<clinit>():void");
    }

    public h() {
        this.a = 0;
        this.b = 0;
        this.c = 0;
        this.d = 0;
        this.e = "";
        this.f = 0;
        this.g = 0;
        this.a = 0;
        this.b = 0;
        this.c = 0;
        this.d = 0;
        this.e = "";
        this.f = 0;
        this.g = 0;
    }

    public final long a() {
        return this.a;
    }

    public final void a(int i) {
        this.b = i;
    }

    public final void a(long j) {
        this.a = j;
    }

    public final void a(String str) {
        this.e = str;
    }

    public final int b() {
        return this.b;
    }

    public final void b(int i) {
        this.c = i;
    }

    public final void b(long j) {
        this.g = j;
    }

    public final int c() {
        return this.c;
    }

    public final void c(int i) {
        this.d = i;
    }

    public final void c(long j) {
        this.f = j;
    }

    public final String d() {
        return this.e;
    }

    public final long e() {
        return this.g;
    }

    public final long f() {
        return this.f;
    }

    public final String toString() {
        return new StringBuilder(z[1]).append(this.a).append(z[6]).append(this.b).append(z[0]).append(this.c).append(z[2]).append(this.d).append(z[5]).append(this.e).append(z[4]).append(this.f).append(z[3]).append(this.g).append("]").toString();
    }
}
