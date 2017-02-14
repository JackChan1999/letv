package cn.com.iresearch.vvtracker.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import cn.com.iresearch.vvtracker.db.d.c;
import cn.com.iresearch.vvtracker.db.d.e;
import cn.com.iresearch.vvtracker.db.d.f;
import java.util.HashMap;
import java.util.List;

public final class a {
    private static HashMap<String, a> a = new HashMap();
    private SQLiteDatabase b;
    private b c;

    private <T> java.util.List<T> a(java.lang.Class<T> r5, java.lang.String r6) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x001d in list [B:7:0x001a]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r4 = this;
        r1 = 0;
        r4.b(r5);
        r4.a(r6);
        r0 = r4.b;
        r2 = r0.rawQuery(r6, r1);
        r0 = new java.util.ArrayList;	 Catch:{ Exception -> 0x0026, all -> 0x0031 }
        r0.<init>();	 Catch:{ Exception -> 0x0026, all -> 0x0031 }
    L_0x0012:
        r3 = r2.moveToNext();	 Catch:{ Exception -> 0x0026, all -> 0x0031 }
        if (r3 != 0) goto L_0x001e;
    L_0x0018:
        if (r2 == 0) goto L_0x001d;
    L_0x001a:
        r2.close();
    L_0x001d:
        return r0;
    L_0x001e:
        r3 = cn.com.iresearch.vvtracker.dao.a.getEntity(r2, r5);	 Catch:{ Exception -> 0x0026, all -> 0x0031 }
        r0.add(r3);	 Catch:{ Exception -> 0x0026, all -> 0x0031 }
        goto L_0x0012;
    L_0x0026:
        r0 = move-exception;
        r0.printStackTrace();	 Catch:{ Exception -> 0x0026, all -> 0x0031 }
        if (r2 == 0) goto L_0x002f;
    L_0x002c:
        r2.close();
    L_0x002f:
        r0 = r1;
        goto L_0x001d;
    L_0x0031:
        r0 = move-exception;
        if (r2 == 0) goto L_0x0037;
    L_0x0034:
        r2.close();
    L_0x0037:
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.com.iresearch.vvtracker.db.a.a(java.lang.Class, java.lang.String):java.util.List<T>");
    }

    private boolean a(cn.com.iresearch.vvtracker.db.d.f r7) {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r6 = this;
        r2 = 0;
        r0 = 1;
        r1 = 0;
        r3 = r7.c();
        if (r3 == 0) goto L_0x000a;
    L_0x0009:
        return r0;
    L_0x000a:
        r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0045, all -> 0x0050 }
        r4 = "SELECT COUNT(*) AS c FROM sqlite_master WHERE type ='table' AND name ='";	 Catch:{ Exception -> 0x0045, all -> 0x0050 }
        r3.<init>(r4);	 Catch:{ Exception -> 0x0045, all -> 0x0050 }
        r4 = r7.a();	 Catch:{ Exception -> 0x0045, all -> 0x0050 }
        r3 = r3.append(r4);	 Catch:{ Exception -> 0x0045, all -> 0x0050 }
        r4 = "' ";	 Catch:{ Exception -> 0x0045, all -> 0x0050 }
        r3 = r3.append(r4);	 Catch:{ Exception -> 0x0045, all -> 0x0050 }
        r3 = r3.toString();	 Catch:{ Exception -> 0x0045, all -> 0x0050 }
        r6.a(r3);	 Catch:{ Exception -> 0x0045, all -> 0x0050 }
        r4 = r6.b;	 Catch:{ Exception -> 0x0045, all -> 0x0050 }
        r5 = 0;	 Catch:{ Exception -> 0x0045, all -> 0x0050 }
        r2 = r4.rawQuery(r3, r5);	 Catch:{ Exception -> 0x0045, all -> 0x0050 }
        if (r2 == 0) goto L_0x0057;	 Catch:{ Exception -> 0x0045, all -> 0x0050 }
    L_0x002f:
        r3 = r2.moveToNext();	 Catch:{ Exception -> 0x0045, all -> 0x0050 }
        if (r3 == 0) goto L_0x0057;	 Catch:{ Exception -> 0x0045, all -> 0x0050 }
    L_0x0035:
        r3 = 0;	 Catch:{ Exception -> 0x0045, all -> 0x0050 }
        r3 = r2.getInt(r3);	 Catch:{ Exception -> 0x0045, all -> 0x0050 }
        if (r3 <= 0) goto L_0x0057;	 Catch:{ Exception -> 0x0045, all -> 0x0050 }
    L_0x003c:
        r7.d();	 Catch:{ Exception -> 0x0045, all -> 0x0050 }
        if (r2 == 0) goto L_0x0009;
    L_0x0041:
        r2.close();
        goto L_0x0009;
    L_0x0045:
        r0 = move-exception;
        r0.printStackTrace();	 Catch:{ Exception -> 0x0045, all -> 0x0050 }
        if (r2 == 0) goto L_0x004e;
    L_0x004b:
        r2.close();
    L_0x004e:
        r0 = r1;
        goto L_0x0009;
    L_0x0050:
        r0 = move-exception;
        if (r2 == 0) goto L_0x0056;
    L_0x0053:
        r2.close();
    L_0x0056:
        throw r0;
    L_0x0057:
        if (r2 == 0) goto L_0x004e;
    L_0x0059:
        r2.close();
        goto L_0x004e;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.com.iresearch.vvtracker.db.a.a(cn.com.iresearch.vvtracker.db.d.f):boolean");
    }

    private a(b bVar) {
        if (bVar == null) {
            throw new RuntimeException("daoConfig is null");
        } else if (bVar.a() == null) {
            throw new RuntimeException("android context is null");
        } else {
            this.b = new c(bVar.a().getApplicationContext(), bVar.b(), bVar.c()).getWritableDatabase();
            this.c = bVar;
        }
    }

    private static synchronized a a(b bVar) {
        a aVar;
        synchronized (a.class) {
            aVar = (a) a.get(bVar.b());
            if (aVar == null) {
                aVar = new a(bVar);
                a.put(bVar.b(), aVar);
            }
        }
        return aVar;
    }

    public static a a(Context context, String str) {
        b bVar = new b();
        bVar.a(context);
        bVar.a(str);
        bVar.e();
        return a(bVar);
    }

    public final void a(Object obj) {
        b(obj.getClass());
        a(cn.com.iresearch.vvtracker.dao.a.buildInsertSql(obj));
    }

    public final void b(Object obj) {
        b(obj.getClass());
        a(cn.com.iresearch.vvtracker.dao.a.buildDeleteSql(obj));
    }

    private void a(cn.com.iresearch.vvtracker.db.c.a aVar) {
        if (aVar != null) {
            a(aVar.a());
            this.b.execSQL(aVar.a(), aVar.b());
            return;
        }
        Log.e("FinalDb", "sava error:sqlInfo is null");
    }

    public final <T> List<T> a(Class<T> cls) {
        b((Class) cls);
        return a((Class) cls, cn.com.iresearch.vvtracker.dao.a.getSelectSQL(cls));
    }

    private void b(Class<?> cls) {
        try {
            if (!a(f.a(cls))) {
                f a = f.a(cls);
                cn.com.iresearch.vvtracker.db.d.a b = a.b();
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("CREATE TABLE IF NOT EXISTS ");
                stringBuffer.append(a.a());
                stringBuffer.append(" ( ");
                Class e = b.e();
                if (e == Integer.TYPE || e == Integer.class) {
                    stringBuffer.append("\"").append(b.c()).append("\"    INTEGER PRIMARY KEY AUTOINCREMENT,");
                } else {
                    stringBuffer.append("\"").append(b.c()).append("\"    TEXT PRIMARY KEY,");
                }
                for (e c : a.a.values()) {
                    stringBuffer.append("\"").append(c.c());
                    stringBuffer.append("\",");
                }
                for (c c2 : a.b.values()) {
                    stringBuffer.append("\"").append(c2.c()).append("\",");
                }
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                stringBuffer.append(" )");
                String stringBuffer2 = stringBuffer.toString();
                a(stringBuffer2);
                this.b.execSQL(stringBuffer2);
            }
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
    }

    private void a(String str) {
        if (this.c != null && this.c.d()) {
            Log.d("Debug SQL", ">>>>>>  " + str);
        }
    }
}
