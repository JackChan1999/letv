package com.google.gson.jpush.internal;

final class am extends UnsafeAllocator {
    private static final String z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = "=I\u001f*\\\n\b\u0010(_\u0011K\u00100V^";
        r0 = r0.toCharArray();
        r1 = r0.length;
        r2 = 0;
        r3 = 1;
        if (r1 > r3) goto L_0x0027;
    L_0x000b:
        r3 = r0;
        r4 = r2;
        r7 = r1;
        r1 = r0;
        r0 = r7;
    L_0x0010:
        r6 = r1[r2];
        r5 = r4 % 5;
        switch(r5) {
            case 0: goto L_0x0035;
            case 1: goto L_0x0038;
            case 2: goto L_0x003b;
            case 3: goto L_0x003e;
            default: goto L_0x0017;
        };
    L_0x0017:
        r5 = 51;
    L_0x0019:
        r5 = r5 ^ r6;
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
        r5 = 126; // 0x7e float:1.77E-43 double:6.23E-322;
        goto L_0x0019;
    L_0x0038:
        r5 = 40;
        goto L_0x0019;
    L_0x003b:
        r5 = 113; // 0x71 float:1.58E-43 double:5.6E-322;
        goto L_0x0019;
    L_0x003e:
        r5 = 68;
        goto L_0x0019;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.jpush.internal.am.<clinit>():void");
    }

    am() {
    }

    public final <T> T newInstance(Class<T> cls) {
        throw new UnsupportedOperationException(new StringBuilder(z).append(cls).toString());
    }
}
