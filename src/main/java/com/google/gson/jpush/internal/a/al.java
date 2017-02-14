package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.af;
import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.c;
import com.google.gson.jpush.b.d;
import java.util.BitSet;

final class al extends com.google.gson.jpush.al<BitSet> {
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
        r4 = 1;
        r1 = 0;
        r0 = 2;
        r3 = new java.lang.String[r0];
        r2 = "R\tf2D-[Q%Fr\u0018`4XpA4?_c\bq)\u0016y\u000ey?Se[b<Zb\u001e4u\u0007;[$t\u001a7={(XsA4";
        r0 = -1;
        r5 = r3;
        r6 = r3;
        r3 = r1;
    L_0x000b:
        r2 = r2.toCharArray();
        r7 = r2.length;
        if (r7 > r4) goto L_0x0057;
    L_0x0012:
        r8 = r1;
    L_0x0013:
        r9 = r2;
        r10 = r8;
        r13 = r7;
        r7 = r2;
        r2 = r13;
    L_0x0018:
        r12 = r7[r8];
        r11 = r10 % 5;
        switch(r11) {
            case 0: goto L_0x004b;
            case 1: goto L_0x004e;
            case 2: goto L_0x0051;
            case 3: goto L_0x0054;
            default: goto L_0x001f;
        };
    L_0x001f:
        r11 = 54;
    L_0x0021:
        r11 = r11 ^ r12;
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
        r0 = "^\u0015b<Z~\u001f4?_c\bq)\u0016a\u001ax(S7\u000fm-S-[";
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
        r11 = 23;
        goto L_0x0021;
    L_0x004e:
        r11 = 123; // 0x7b float:1.72E-43 double:6.1E-322;
        goto L_0x0021;
    L_0x0051:
        r11 = 20;
        goto L_0x0021;
    L_0x0054:
        r11 = 93;
        goto L_0x0021;
    L_0x0057:
        r8 = r1;
        goto L_0x002f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.jpush.internal.a.al.<clinit>():void");
    }

    al() {
    }

    private static BitSet b(a aVar) {
        if (aVar.f() == c.i) {
            aVar.j();
            return null;
        }
        BitSet bitSet = new BitSet();
        aVar.a();
        c f = aVar.f();
        int i = 0;
        while (f != c.b) {
            boolean z;
            switch (az.a[f.ordinal()]) {
                case 1:
                    if (aVar.m() == 0) {
                        z = false;
                        break;
                    }
                    z = true;
                    break;
                case 2:
                    z = aVar.i();
                    break;
                case 3:
                    String h = aVar.h();
                    try {
                        if (Integer.parseInt(h) == 0) {
                            z = false;
                            break;
                        }
                        z = true;
                        break;
                    } catch (NumberFormatException e) {
                        throw new af(new StringBuilder(z[0]).append(h).toString());
                    }
                default:
                    throw new af(new StringBuilder(z[1]).append(f).toString());
            }
            if (z) {
                bitSet.set(i);
            }
            i++;
            f = aVar.f();
        }
        aVar.b();
        return bitSet;
    }

    public final /* synthetic */ Object a(a aVar) {
        return b(aVar);
    }

    public final /* synthetic */ void a(d dVar, Object obj) {
        BitSet bitSet = (BitSet) obj;
        if (bitSet == null) {
            dVar.f();
            return;
        }
        dVar.b();
        for (int i = 0; i < bitSet.length(); i++) {
            dVar.a((long) (bitSet.get(i) ? 1 : 0));
        }
        dVar.c();
    }
}
