package cn.jpush.android.service;

import com.letv.pp.utils.NetworkUtils;

final class q {
    private static final String[] z;
    String a;
    int b;

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
        r3 = 2;
        r2 = 1;
        r1 = 0;
        r0 = 4;
        r5 = new java.lang.String[r0];
        r4 = "ytJDs@h\u0018^6L]TsOtJ\u00102\tmY\\:M;YT7[~KC\thH\\:];ZIs\u0013";
        r0 = -1;
        r6 = r5;
        r7 = r5;
        r5 = r1;
    L_0x000d:
        r4 = r4.toCharArray();
        r8 = r4.length;
        if (r8 > r2) goto L_0x006c;
    L_0x0014:
        r9 = r1;
    L_0x0015:
        r10 = r4;
        r11 = r9;
        r14 = r8;
        r8 = r4;
        r4 = r14;
    L_0x001a:
        r13 = r8[r9];
        r12 = r11 % 5;
        switch(r12) {
            case 0: goto L_0x0060;
            case 1: goto L_0x0063;
            case 2: goto L_0x0066;
            case 3: goto L_0x0069;
            default: goto L_0x0021;
        };
    L_0x0021:
        r12 = 83;
    L_0x0023:
        r12 = r12 ^ r13;
        r12 = (char) r12;
        r8[r9] = r12;
        r9 = r11 + 1;
        if (r4 != 0) goto L_0x002f;
    L_0x002b:
        r8 = r10;
        r11 = r9;
        r9 = r4;
        goto L_0x001a;
    L_0x002f:
        r8 = r4;
        r4 = r10;
    L_0x0031:
        if (r8 > r9) goto L_0x0015;
    L_0x0033:
        r8 = new java.lang.String;
        r8.<init>(r4);
        r4 = r8.intern();
        switch(r0) {
            case 0: goto L_0x0048;
            case 1: goto L_0x0051;
            case 2: goto L_0x005b;
            default: goto L_0x003f;
        };
    L_0x003f:
        r6[r5] = r4;
        r0 = "`uNQ?@\u0018@<[o\u0018\u001ds\u0019";
        r4 = r0;
        r5 = r2;
        r6 = r7;
        r0 = r1;
        goto L_0x000d;
    L_0x0048:
        r6[r5] = r4;
        r0 = "`uNQ?@\u0018@<[o\u0018\u001ds";
        r4 = r0;
        r5 = r3;
        r6 = r7;
        r0 = r2;
        goto L_0x000d;
    L_0x0051:
        r6[r5] = r4;
        r4 = 3;
        r0 = "`uNQ?@\u0018Y#\t6\u0018";
        r5 = r4;
        r6 = r7;
        r4 = r0;
        r0 = r3;
        goto L_0x000d;
    L_0x005b:
        r6[r5] = r4;
        z = r7;
        return;
    L_0x0060:
        r12 = 41;
        goto L_0x0023;
    L_0x0063:
        r12 = 27;
        goto L_0x0023;
    L_0x0066:
        r12 = 56;
        goto L_0x0023;
    L_0x0069:
        r12 = 48;
        goto L_0x0023;
    L_0x006c:
        r9 = r1;
        goto L_0x0031;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.service.q.<clinit>():void");
    }

    public q(String str) {
        int indexOf = str.indexOf(58);
        if (indexOf == -1) {
            throw new Exception(z[0]);
        }
        this.a = str.substring(0, indexOf);
        if (SisInfo.isValidIPV4(this.a)) {
            String substring = str.substring(indexOf + 1);
            try {
                this.b = Integer.parseInt(substring);
                if (this.b == 0) {
                    throw new Exception(z[1]);
                }
                return;
            } catch (Exception e) {
                throw new Exception(new StringBuilder(z[2]).append(substring).toString());
            }
        }
        throw new Exception(new StringBuilder(z[3]).append(this.a).toString());
    }

    public final String toString() {
        return this.a + NetworkUtils.DELIMITER_COLON + this.b;
    }
}
