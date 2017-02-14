package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.af;
import com.google.gson.jpush.al;
import com.google.gson.jpush.am;
import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.c;
import com.google.gson.jpush.b.d;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class e extends al<Date> {
    public static final am a = new f();
    private static final String[] z;
    private final DateFormat b = DateFormat.getDateTimeInstance(2, 2, Locale.US);
    private final DateFormat c = DateFormat.getDateTimeInstance(2, 2);
    private final DateFormat d;

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
        r2 = "O<9\r\u000f{\bm\u0010F\u0011\u0011g<j\f(-NQEb\u001aS";
        r0 = -1;
        r5 = r3;
        r6 = r3;
        r3 = r1;
    L_0x000b:
        r2 = r2.toCharArray();
        r7 = r2.length;
        if (r7 > r4) goto L_0x005e;
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
            case 0: goto L_0x0052;
            case 1: goto L_0x0055;
            case 2: goto L_0x0058;
            case 3: goto L_0x005b;
            default: goto L_0x001f;
        };
    L_0x001f:
        r11 = 34;
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
        r0 = "c\u0011\u0003";
        r2 = r0;
        r3 = r4;
        r5 = r6;
        r0 = r1;
        goto L_0x000b;
    L_0x0046:
        r5[r3] = r2;
        z = r6;
        r0 = new com.google.gson.jpush.internal.a.f;
        r0.<init>();
        a = r0;
        return;
    L_0x0052:
        r11 = 54;
        goto L_0x0021;
    L_0x0055:
        r11 = 69;
        goto L_0x0021;
    L_0x0058:
        r11 = 64;
        goto L_0x0021;
    L_0x005b:
        r11 = 116; // 0x74 float:1.63E-43 double:5.73E-322;
        goto L_0x0021;
    L_0x005e:
        r8 = r1;
        goto L_0x002f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.jpush.internal.a.e.<clinit>():void");
    }

    public e() {
        DateFormat simpleDateFormat = new SimpleDateFormat(z[0], Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(z[1]));
        this.d = simpleDateFormat;
    }

    private synchronized Date a(String str) {
        Date parse;
        try {
            parse = this.c.parse(str);
        } catch (ParseException e) {
            try {
                parse = this.b.parse(str);
            } catch (ParseException e2) {
                try {
                    parse = this.d.parse(str);
                } catch (Throwable e3) {
                    throw new af(str, e3);
                }
            }
        }
        return parse;
    }

    private synchronized void a(d dVar, Date date) {
        if (date == null) {
            dVar.f();
        } else {
            dVar.b(this.b.format(date));
        }
    }

    public final /* synthetic */ Object a(a aVar) {
        if (aVar.f() != c.i) {
            return a(aVar.h());
        }
        aVar.j();
        return null;
    }
}
