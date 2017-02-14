package com.google.protobuf.jpush;

public class Internal {
    private static final String[] z;

    static {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:37)
	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:61)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r2 = 1;
        r1 = 0;
        r0 = 3;
        r4 = new java.lang.String[r0];
        r3 = "Y\u0017I\u0013+E;\u001f\u0016dv\u0005\u001f\u001cdgVL\u0007{c\u0019M\u0006+rVL\u0006j}\u0012^\u0000o3\u0015W\u0013yr\u0015K\u0017y3\u0005Z\u0006%";
        r0 = -1;
        r5 = r4;
        r6 = r4;
        r4 = r1;
    L_0x000b:
        r3 = r3.toCharArray();
        r7 = r3.length;
        if (r7 > r2) goto L_0x0061;
    L_0x0012:
        r8 = r1;
    L_0x0013:
        r9 = r3;
        r10 = r8;
        r13 = r7;
        r7 = r3;
        r3 = r13;
    L_0x0018:
        r12 = r7[r8];
        r11 = r10 % 5;
        switch(r11) {
            case 0: goto L_0x0055;
            case 1: goto L_0x0058;
            case 2: goto L_0x005b;
            case 3: goto L_0x005e;
            default: goto L_0x001f;
        };
    L_0x001f:
        r11 = 11;
    L_0x0021:
        r11 = r11 ^ r12;
        r11 = (char) r11;
        r7[r8] = r11;
        r8 = r10 + 1;
        if (r3 != 0) goto L_0x002d;
    L_0x0029:
        r7 = r9;
        r10 = r8;
        r8 = r3;
        goto L_0x0018;
    L_0x002d:
        r7 = r3;
        r3 = r9;
    L_0x002f:
        if (r7 > r8) goto L_0x0013;
    L_0x0031:
        r7 = new java.lang.String;
        r7.<init>(r3);
        r3 = r7.intern();
        switch(r0) {
            case 0: goto L_0x0046;
            case 1: goto L_0x0050;
            default: goto L_0x003d;
        };
    L_0x003d:
        r5[r4] = r3;
        r0 = "Z%p_3+C\u0006_:";
        r3 = r0;
        r4 = r2;
        r5 = r6;
        r0 = r1;
        goto L_0x000b;
    L_0x0046:
        r5[r4] = r3;
        r3 = 2;
        r0 = "F\"y_3";
        r4 = r3;
        r5 = r6;
        r3 = r0;
        r0 = r2;
        goto L_0x000b;
    L_0x0050:
        r5[r4] = r3;
        z = r6;
        return;
    L_0x0055:
        r11 = 19;
        goto L_0x0021;
    L_0x0058:
        r11 = 118; // 0x76 float:1.65E-43 double:5.83E-322;
        goto L_0x0021;
    L_0x005b:
        r11 = 63;
        goto L_0x0021;
    L_0x005e:
        r11 = 114; // 0x72 float:1.6E-43 double:5.63E-322;
        goto L_0x0021;
    L_0x0061:
        r8 = r1;
        goto L_0x002f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.jpush.Internal.<clinit>():void");
    }

    public static c bytesDefaultValue(String str) {
        try {
            return c.a(str.getBytes(z[1]));
        } catch (Throwable e) {
            throw new IllegalStateException(z[0], e);
        }
    }

    public static boolean isValidUtf8(c cVar) {
        int a = cVar.a();
        int i = 0;
        while (i < a) {
            int i2 = i + 1;
            int a2 = cVar.a(i) & 255;
            if (a2 < 128) {
                i = i2;
            } else if (a2 < 194 || a2 > 244) {
                return false;
            } else {
                if (i2 >= a) {
                    return false;
                }
                int i3 = i2 + 1;
                i2 = cVar.a(i2) & 255;
                if (i2 < 128 || i2 > 191) {
                    return false;
                }
                if (a2 > 223) {
                    if (i3 >= a) {
                        return false;
                    }
                    i = i3 + 1;
                    i3 = cVar.a(i3) & 255;
                    if (i3 < 128 || i3 > 191) {
                        return false;
                    }
                    if (a2 <= 239) {
                        if ((a2 == 224 && i2 < 160) || (a2 == 237 && i2 > 159)) {
                            return false;
                        }
                    } else if (i >= a) {
                        return false;
                    } else {
                        i3 = i + 1;
                        i = cVar.a(i) & 255;
                        if (i < 128 || i > 191) {
                            return false;
                        }
                        if ((a2 == 240 && i2 < 144) || (a2 == 244 && i2 > 143)) {
                            return false;
                        }
                    }
                }
                i = i3;
            }
        }
        return true;
    }

    public static String stringDefaultValue(String str) {
        try {
            return new String(str.getBytes(z[1]), z[2]);
        } catch (Throwable e) {
            throw new IllegalStateException(z[0], e);
        }
    }
}
