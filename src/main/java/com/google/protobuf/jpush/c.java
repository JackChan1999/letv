package com.google.protobuf.jpush;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public final class c {
    public static final c a = new c(new byte[0]);
    private static final String[] z;
    private final byte[] b;
    private volatile int c = 0;

    static {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:37)
	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:61)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r4 = 1;
        r1 = 0;
        r0 = 2;
        r3 = new java.lang.String[r0];
        r2 = "y0;\u0002W\f\n\u0012[O_\u0011\r_\u0000^\u0010\u0018KP";
        r0 = -1;
        r5 = r3;
        r6 = r3;
        r3 = r1;
    L_0x000c:
        r2 = r2.toCharArray();
        r7 = r2.length;
        if (r7 > r4) goto L_0x0062;
    L_0x0013:
        r8 = r1;
    L_0x0014:
        r9 = r2;
        r10 = r8;
        r13 = r7;
        r7 = r2;
        r2 = r13;
    L_0x0019:
        r12 = r7[r8];
        r11 = r10 % 5;
        switch(r11) {
            case 0: goto L_0x0056;
            case 1: goto L_0x0059;
            case 2: goto L_0x005c;
            case 3: goto L_0x005f;
            default: goto L_0x0020;
        };
    L_0x0020:
        r11 = 111; // 0x6f float:1.56E-43 double:5.5E-322;
    L_0x0022:
        r11 = r11 ^ r12;
        r11 = (char) r11;
        r7[r8] = r11;
        r8 = r10 + 1;
        if (r2 != 0) goto L_0x002e;
    L_0x002a:
        r7 = r9;
        r10 = r8;
        r8 = r2;
        goto L_0x0019;
    L_0x002e:
        r7 = r2;
        r2 = r9;
    L_0x0030:
        if (r7 > r8) goto L_0x0014;
    L_0x0032:
        r7 = new java.lang.String;
        r7.<init>(r2);
        r2 = r7.intern();
        switch(r0) {
            case 0: goto L_0x0048;
            default: goto L_0x003e;
        };
    L_0x003e:
        r5[r3] = r2;
        r0 = "y0;\u0002W";
        r2 = r0;
        r3 = r4;
        r5 = r6;
        r0 = r1;
        goto L_0x000c;
    L_0x0048:
        r5[r3] = r2;
        z = r6;
        r0 = new com.google.protobuf.jpush.c;
        r1 = new byte[r1];
        r0.<init>(r1);
        a = r0;
        return;
    L_0x0056:
        r11 = 44;
        goto L_0x0022;
    L_0x0059:
        r11 = 100;
        goto L_0x0022;
    L_0x005c:
        r11 = 125; // 0x7d float:1.75E-43 double:6.2E-322;
        goto L_0x0022;
    L_0x005f:
        r11 = 47;
        goto L_0x0022;
    L_0x0062:
        r8 = r1;
        goto L_0x0030;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.jpush.c.<clinit>():void");
    }

    private c(byte[] bArr) {
        this.b = bArr;
    }

    public static c a(String str) {
        try {
            return new c(str.getBytes(z[1]));
        } catch (Throwable e) {
            throw new RuntimeException(z[0], e);
        }
    }

    public static c a(byte[] bArr) {
        return a(bArr, 0, bArr.length);
    }

    public static c a(byte[] bArr, int i, int i2) {
        Object obj = new byte[i2];
        System.arraycopy(bArr, i, obj, 0, i2);
        return new c(obj);
    }

    public final byte a(int i) {
        return this.b[i];
    }

    public final int a() {
        return this.b.length;
    }

    public final void a(byte[] bArr, int i, int i2, int i3) {
        System.arraycopy(this.b, i, bArr, i2, i3);
    }

    public final String b() {
        try {
            return new String(this.b, z[1]);
        } catch (Throwable e) {
            throw new RuntimeException(z[0], e);
        }
    }

    public final InputStream c() {
        return new ByteArrayInputStream(this.b);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof c)) {
            return false;
        }
        c cVar = (c) obj;
        int length = this.b.length;
        if (length != cVar.b.length) {
            return false;
        }
        byte[] bArr = this.b;
        byte[] bArr2 = cVar.b;
        for (int i = 0; i < length; i++) {
            if (bArr[i] != bArr2[i]) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = this.c;
        if (i == 0) {
            byte[] bArr = this.b;
            int length = this.b.length;
            int i2 = 0;
            i = length;
            while (i2 < length) {
                int i3 = bArr[i2] + (i * 31);
                i2++;
                i = i3;
            }
            if (i == 0) {
                i = 1;
            }
            this.c = i;
        }
        return i;
    }
}
