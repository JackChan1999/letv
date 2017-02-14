package cn.jpush.android.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import cn.jpush.android.util.z;

final class p extends SQLiteOpenHelper {
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
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r2 = 1;
        r1 = 0;
        r0 = 3;
        r4 = new java.lang.String[r0];
        r3 = "\u0010GLBO%DMPS\u0013DMXD\t\u0019]S";
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
        r11 = 39;
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
        r0 = "9e|ps?\u0017mpe6r\u0019[W\u000fDQnT\u000eVMXT\u000e^ZB\u0007RhPU\u00073ymt`?e\u0019au3zxc~Z||h\u0007;bm~n4tktj?ym\u0011\u000b\tCfBH\bCfZB\u0003\u0017MT_\u000e\u0017W^SZYL]KVDMnI\u001fC\u0019EB\u0002C\u0019_H\u000e\u0017WDK\u0016\u001bJEx\u0019XW_x\u0013G\u0019EB\u0002C\u0019_H\u000e\u0017WDK\u0016\u001bJEx\u0016XZPK%SWB\u0007\u000eRAE\u000b\tCfBH\u000fEZT\u0007\u0013YMT@\u001fE\u0019_H\u000e\u0017WDK\u0016\u001bJEx\u001cVP]B\u001e\u0017P_S\u001fP\\C\u0007\u0014XM\u0011I\u000f[U\u001dT\u000ehM^S\u001b[\u0019XI\u000eR^TUZYVE\u0007\u0014BU]\u000b\tCfRH\u000fYMn\u0016Z^WEB\u001dRK\u001dT\u000ehZ^R\u0014Cf\u0000xI\u0017P_S\u001fP\\C\u000b\tCfRH\u000fYMn\u0014%\u0006\t\u0011N\u0014C\\VB\b\u001bJEx\u0019XL_S%\u0006\t\u0011N\u0014C\\VB\b\u001e\u0002";
        r3 = r0;
        r4 = r2;
        r5 = r6;
        r0 = r1;
        goto L_0x000b;
    L_0x0046:
        r5[r4] = r3;
        r3 = 2;
        r0 = ">eva\u0007.v{}bZ~\u0011b\"~jetZ]IDT\u0012hJEF\u000e^JEN\u0019D";
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
        r11 = 122; // 0x7a float:1.71E-43 double:6.03E-322;
        goto L_0x0021;
    L_0x0058:
        r11 = 55;
        goto L_0x0021;
    L_0x005b:
        r11 = 57;
        goto L_0x0021;
    L_0x005e:
        r11 = 49;
        goto L_0x0021;
    L_0x0061:
        r8 = r1;
        goto L_0x002f;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.data.p.<clinit>():void");
    }

    public p(Context context) {
        this(context, z[0], null, 1);
    }

    private p(Context context, String str, CursorFactory cursorFactory, int i) {
        super(context, str, null, 1);
    }

    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL(z[1]);
        } catch (Exception e) {
            z.a();
        }
    }

    public final void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL(z[2]);
        onCreate(sQLiteDatabase);
    }
}
