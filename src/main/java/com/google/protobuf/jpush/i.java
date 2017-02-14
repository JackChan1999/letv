package com.google.protobuf.jpush;

public abstract class i<MessageType extends h, BuilderType extends i> extends b<BuilderType> {
    private static final String z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r6 = 1;
        r0 = "\\iS9Far\u001a9\u0013xqU9\u0003l!N%Fjd\u001a%\u0010msH#\u0002ldTj\u0004q!I?\u0004km[9\u0015mr\u0014";
        r0 = r0.toCharArray();
        r1 = r0.length;
        r2 = 0;
        if (r1 > r6) goto L_0x0027;
    L_0x000b:
        r3 = r0;
        r4 = r2;
        r8 = r1;
        r1 = r0;
        r0 = r8;
    L_0x0010:
        r7 = r1[r2];
        r5 = r4 % 5;
        switch(r5) {
            case 0: goto L_0x0035;
            case 1: goto L_0x0038;
            case 2: goto L_0x003a;
            case 3: goto L_0x003d;
            default: goto L_0x0017;
        };
    L_0x0017:
        r5 = 102; // 0x66 float:1.43E-43 double:5.04E-322;
    L_0x0019:
        r5 = r5 ^ r7;
        r5 = (char) r5;
        r1[r2] = r5;
        r2 = r4 + 1;
        if (r0 != 0) goto L_0x0025;
    L_0x0021:
        r1 = r3;
        r4 = r2;
        r2 = r0;
        goto L_0x0010;
    L_0x0025:
        r1 = r0;
        r0 = r3;
    L_0x0027:
        if (r1 > r2) goto L_0x000b;
    L_0x0029:
        r1 = new java.lang.String;
        r1.<init>(r0);
        r0 = r1.intern();
        z = r0;
        return;
    L_0x0035:
        r5 = 8;
        goto L_0x0019;
    L_0x0038:
        r5 = r6;
        goto L_0x0019;
    L_0x003a:
        r5 = 58;
        goto L_0x0019;
    L_0x003d:
        r5 = 74;
        goto L_0x0019;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.jpush.i.<clinit>():void");
    }

    protected i() {
    }

    public BuilderType c() {
        throw new UnsupportedOperationException(z);
    }

    public /* synthetic */ Object clone() {
        return c();
    }

    public /* synthetic */ b d() {
        return c();
    }
}
