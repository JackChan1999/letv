package cn.jpush.android.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import cn.jpush.android.util.z;

final class g extends SQLiteOpenHelper {
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
        r12 = 3;
        r2 = 1;
        r1 = 0;
        r4 = new java.lang.String[r12];
        r3 = "\u0004t\u0016f#\u0014g\u001bzF`o\u001f\u0016F\u0018o\nbP`R\u0006Zl#G5Xl4O?_`!R0Ym";
        r0 = -1;
        r5 = r4;
        r6 = r4;
        r4 = r1;
    L_0x000b:
        r3 = r3.toCharArray();
        r7 = r3.length;
        if (r7 > r2) goto L_0x0060;
    L_0x0012:
        r8 = r1;
    L_0x0013:
        r9 = r3;
        r10 = r8;
        r14 = r7;
        r7 = r3;
        r3 = r14;
    L_0x0018:
        r13 = r7[r8];
        r11 = r10 % 5;
        switch(r11) {
            case 0: goto L_0x0054;
            case 1: goto L_0x0057;
            case 2: goto L_0x005a;
            case 3: goto L_0x005d;
            default: goto L_0x001f;
        };
    L_0x001f:
        r11 = r12;
    L_0x0020:
        r11 = r11 ^ r13;
        r11 = (char) r11;
        r7[r8] = r11;
        r8 = r10 + 1;
        if (r3 != 0) goto L_0x002c;
    L_0x0028:
        r7 = r9;
        r10 = r8;
        r8 = r3;
        goto L_0x0018;
    L_0x002c:
        r7 = r3;
        r3 = r9;
    L_0x002e:
        if (r7 > r8) goto L_0x0013;
    L_0x0030:
        r7 = new java.lang.String;
        r7.<init>(r3);
        r3 = r7.intern();
        switch(r0) {
            case 0: goto L_0x0045;
            case 1: goto L_0x004f;
            default: goto L_0x003c;
        };
    L_0x003c:
        r5[r4] = r3;
        r0 = "*V,Ek\u001fJ6Ub,y7Yw)@0Ub4O6X-$D";
        r3 = r0;
        r4 = r2;
        r5 = r6;
        r0 = r1;
        goto L_0x000b;
    L_0x0045:
        r5[r4] = r3;
        r3 = 2;
        r0 = "\u0003t\u001cwW\u0005\u0006\rwA\fcyB\\,I:Wo.I-_e)E8Bj/Hy\u001e\\)ByM\u0014c\u001esQ`v\u000bN\u0001t\u0000\u0016H\u0005ywV\u0014i\u0010x@\u0012c\u0014sM\u0014\u0006uZm\u001fO=\u0016o/H>\u0016m/RyXv,JuZm\u001fE6Cm4\u00060Xw%A<D#.I-\u0016m5J5\u001ao.y+Sn/P<\u0016j.R<Qf2\u00067Yw`H,ZolJ7iw9V<\u0016j.R<Qf2\u00067Yw`H,ZolJ7if8R+W#4C!B#lJ7iw2O>Qf2y-_n%\u00065Ym'\u0006uZm\u001fG=R\\4O4S#,I7Q#i\u001d";
        r4 = r3;
        r5 = r6;
        r3 = r0;
        r0 = r2;
        goto L_0x000b;
    L_0x004f:
        r5[r4] = r3;
        z = r6;
        return;
    L_0x0054:
        r11 = 64;
        goto L_0x0020;
    L_0x0057:
        r11 = 38;
        goto L_0x0020;
    L_0x005a:
        r11 = 89;
        goto L_0x0020;
    L_0x005d:
        r11 = 54;
        goto L_0x0020;
    L_0x0060:
        r8 = r1;
        goto L_0x002e;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.data.g.<clinit>():void");
    }

    public g(Context context) {
        this(context, z[1], null, 1);
    }

    private g(Context context, String str, CursorFactory cursorFactory, int i) {
        super(context, str, null, 1);
    }

    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL(z[2]);
        } catch (Exception e) {
            z.a();
        }
    }

    public final void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL(z[0]);
        onCreate(sQLiteDatabase);
    }
}
