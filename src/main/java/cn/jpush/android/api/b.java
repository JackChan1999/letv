package cn.jpush.android.api;

import java.util.Set;

public final class b {
    private static final String[] z;
    public String a;
    public Set<String> b;
    public TagAliasCallback c;

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
        r12 = 2;
        r4 = 1;
        r1 = 0;
        r3 = new java.lang.String[r12];
        r2 = "Fc\u001c_pda\u001bcSwc\u001d@\u0012(\"\u0011_[dqJ";
        r0 = -1;
        r5 = r3;
        r6 = r3;
        r3 = r1;
    L_0x000b:
        r2 = r2.toCharArray();
        r7 = r2.length;
        if (r7 > r4) goto L_0x0055;
    L_0x0012:
        r8 = r1;
    L_0x0013:
        r9 = r2;
        r10 = r8;
        r14 = r7;
        r7 = r2;
        r2 = r14;
    L_0x0018:
        r13 = r7[r8];
        r11 = r10 % 5;
        switch(r11) {
            case 0: goto L_0x004b;
            case 1: goto L_0x004d;
            case 2: goto L_0x004f;
            case 3: goto L_0x0052;
            default: goto L_0x001f;
        };
    L_0x001f:
        r11 = 50;
    L_0x0021:
        r11 = r11 ^ r13;
        r11 = (char) r11;
        r7[r8] = r11;
        r8 = r10 + 1;
        if (r2 != 0) goto L_0x002d;
    L_0x0029:
        r7 = r9;
        r10 = r8;
        r8 = r2;
        goto L_0x0018;
    L_0x002d:
        r7 = r2;
        r2 = r9;
    L_0x002f:
        if (r7 > r8) goto L_0x0013;
    L_0x0031:
        r7 = new java.lang.String;
        r7.<init>(r2);
        r2 = r7.intern();
        switch(r0) {
            case 0: goto L_0x0046;
            default: goto L_0x003d;
        };
    L_0x003d:
        r5[r3] = r2;
        r0 = ")\"\u0004RUv8";
        r2 = r0;
        r3 = r4;
        r5 = r6;
        r0 = r1;
        goto L_0x000b;
    L_0x0046:
        r5[r3] = r2;
        z = r6;
        return;
    L_0x004b:
        r11 = 5;
        goto L_0x0021;
    L_0x004d:
        r11 = r12;
        goto L_0x0021;
    L_0x004f:
        r11 = 112; // 0x70 float:1.57E-43 double:5.53E-322;
        goto L_0x0021;
    L_0x0052:
        r11 = 51;
        goto L_0x0021;
    L_0x0055:
        r8 = r1;
        goto L_0x002f;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.api.b.<clinit>():void");
    }

    public b(String str, Set<String> set, TagAliasCallback tagAliasCallback) {
        this.a = str;
        this.b = set;
        this.c = tagAliasCallback;
    }

    public final String toString() {
        return new StringBuilder(z[0]).append(this.a).append(z[1]).append(this.b).toString();
    }
}
