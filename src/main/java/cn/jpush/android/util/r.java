package cn.jpush.android.util;

import android.content.Context;
import android.database.Cursor;
import cn.jpush.android.a;
import cn.jpush.android.data.o;
import cn.jpush.android.data.q;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class r {
    private static final String[] z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 23;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "Z\u000bp";
        r0 = -1;
        r4 = r3;
    L_0x0009:
        r1 = r1.toCharArray();
        r5 = r1.length;
        r6 = 0;
        r7 = 1;
        if (r5 > r7) goto L_0x002e;
    L_0x0012:
        r7 = r1;
        r8 = r6;
        r11 = r5;
        r5 = r1;
        r1 = r11;
    L_0x0017:
        r10 = r5[r6];
        r9 = r8 % 5;
        switch(r9) {
            case 0: goto L_0x011b;
            case 1: goto L_0x011f;
            case 2: goto L_0x0123;
            case 3: goto L_0x0126;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 40;
    L_0x0020:
        r9 = r9 ^ r10;
        r9 = (char) r9;
        r5[r6] = r9;
        r6 = r8 + 1;
        if (r1 != 0) goto L_0x002c;
    L_0x0028:
        r5 = r7;
        r8 = r6;
        r6 = r1;
        goto L_0x0017;
    L_0x002c:
        r5 = r1;
        r1 = r7;
    L_0x002e:
        if (r5 > r6) goto L_0x0012;
    L_0x0030:
        r5 = new java.lang.String;
        r5.<init>(r1);
        r1 = r5.intern();
        switch(r0) {
            case 0: goto L_0x0044;
            case 1: goto L_0x004c;
            case 2: goto L_0x0054;
            case 3: goto L_0x005c;
            case 4: goto L_0x0064;
            case 5: goto L_0x006c;
            case 6: goto L_0x0074;
            case 7: goto L_0x007d;
            case 8: goto L_0x0087;
            case 9: goto L_0x0092;
            case 10: goto L_0x009d;
            case 11: goto L_0x00a8;
            case 12: goto L_0x00b3;
            case 13: goto L_0x00be;
            case 14: goto L_0x00c9;
            case 15: goto L_0x00d4;
            case 16: goto L_0x00df;
            case 17: goto L_0x00ea;
            case 18: goto L_0x00f5;
            case 19: goto L_0x0100;
            case 20: goto L_0x010b;
            case 21: goto L_0x0116;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "X\u0001gKDk\njY";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "W\u0001jDw]\u001e";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "@\u0007iO[";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "G\u0001qXKQ";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "R\u000fmFMP1pEX";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "X\u0001cCFk\beCDQ\n";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006c:
        r3[r2] = r1;
        r2 = 7;
        r1 = "G\nouAZ\naR";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0074:
        r3[r2] = r1;
        r2 = 8;
        r1 = "G\u001bgIMQ\n[^GD";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007d:
        r3[r2] = r1;
        r2 = 9;
        r1 = "X\u0001cCFk\u001ak^IX";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0087:
        r3[r2] = r1;
        r2 = 10;
        r1 = "M\u0017}S\u0005y#)NL";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0092:
        r3[r2] = r1;
        r2 = 11;
        r1 = "P\u000fpO";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x009d:
        r3[r2] = r1;
        r2 = 12;
        r1 = "@\u0017tO";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00a8:
        r3[r2] = r1;
        r2 = 13;
        r1 = "]\u001amGM";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00b3:
        r3[r2] = r1;
        r2 = 14;
        r1 = "\u0014\u0000k^\bQ\u0016mY\\GN>\nAZ\u001daX\\";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00be:
        r3[r2] = r1;
        r2 = 15;
        r1 = "\u0014\u0007w\nML\u0007w^[\u0014T$_XP\u000fpO";
        r0 = 14;
        r3 = r4;
        goto L_0x0009;
    L_0x00c9:
        r3[r2] = r1;
        r2 = 16;
        r1 = "G\u0001v^w_\u000b}\u0010";
        r0 = 15;
        r3 = r4;
        goto L_0x0009;
    L_0x00d4:
        r3[r2] = r1;
        r2 = 17;
        r1 = "\u0014N$\nD[\tmDwW\u0001w^AY\u000b>\n";
        r0 = 16;
        r3 = r4;
        goto L_0x0009;
    L_0x00df:
        r3[r2] = r1;
        r2 = 18;
        r1 = "G\u0001v^w_\u000b}\n\u0012\u0014";
        r0 = 17;
        r3 = r4;
        goto L_0x0009;
    L_0x00ea:
        r3[r2] = r1;
        r2 = 19;
        r1 = "W\u0001qD\\k_[\u0019";
        r0 = 18;
        r3 = r4;
        goto L_0x0009;
    L_0x00f5:
        r3[r2] = r1;
        r2 = 20;
        r1 = "W\u0001qD\\k][";
        r0 = 19;
        r3 = r4;
        goto L_0x0009;
    L_0x0100:
        r3[r2] = r1;
        r2 = 21;
        r1 = "G\u001bgIMG\u001d[NM@\u000fmF[";
        r0 = 20;
        r3 = r4;
        goto L_0x0009;
    L_0x010b:
        r3[r2] = r1;
        r2 = 22;
        r1 = "W\u0001qD\\k^[\u001b";
        r0 = 21;
        r3 = r4;
        goto L_0x0009;
    L_0x0116:
        r3[r2] = r1;
        z = r4;
        return;
    L_0x011b:
        r9 = 52;
        goto L_0x0020;
    L_0x011f:
        r9 = 110; // 0x6e float:1.54E-43 double:5.43E-322;
        goto L_0x0020;
    L_0x0123:
        r9 = 4;
        goto L_0x0020;
    L_0x0126:
        r9 = 42;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.util.r.<clinit>():void");
    }

    private static JSONObject a(q qVar) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(z[0], qVar.b());
            jSONObject.put(z[2], qVar.c());
            jSONObject.put(z[1], qVar.d());
            jSONObject.put(z[4], qVar.e());
            jSONObject.put(z[3], qVar.g());
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(z[22], qVar.h());
            jSONObject2.put(z[19], qVar.i());
            jSONObject2.put(z[20], qVar.j());
            jSONObject.put(z[21], jSONObject2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public static void a(Context context) {
        if (context != null) {
            b(context);
            c(context);
        }
    }

    public static synchronized void a(Context context, int i, long j, int i2) {
        Cursor b;
        Cursor cursor;
        Throwable th;
        synchronized (r.class) {
            z.c();
            String r = a.r(context);
            String e = a.e() == null ? "" : a.e();
            String str = r + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + i + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + e + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + a.d();
            new StringBuilder(z[18]).append(str).append(z[17]).append(j);
            z.b();
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            if (i2 == 0) {
                if (j <= 1000) {
                    i3 = 1;
                }
                if (j > 1000 && j <= 3000) {
                    i4 = 1;
                }
                if (j > 3000) {
                    i5 = 1;
                }
            }
            o a = o.a(context);
            if (a != null) {
                if (a.a(true)) {
                    if (a.a(str)) {
                        new StringBuilder(z[16]).append(str).append(z[15]);
                        z.c();
                        try {
                            b = a.b(str);
                            if (b == null) {
                                try {
                                    z.d();
                                } catch (Exception e2) {
                                    cursor = b;
                                    if (cursor != null) {
                                        cursor.close();
                                    }
                                    a.a();
                                } catch (Throwable th2) {
                                    th = th2;
                                    if (b != null) {
                                        b.close();
                                    }
                                    throw th;
                                }
                            }
                            q a2 = a.a(b);
                            if (a2 != null) {
                                a.b(str, r, e, a.d(), i, a2.f() + i2, a2.g() + 1, i3 + a2.h(), i4 + a2.i(), i5 + a2.j(), a2.k() + 0);
                            } else {
                                z.d();
                            }
                            if (b != null) {
                                b.close();
                            }
                        } catch (Exception e3) {
                            cursor = null;
                            if (cursor != null) {
                                cursor.close();
                            }
                            a.a();
                        } catch (Throwable th3) {
                            th = th3;
                            b = null;
                            if (b != null) {
                                b.close();
                            }
                            throw th;
                        }
                    }
                    new StringBuilder(z[16]).append(str).append(z[14]);
                    z.c();
                    a.a(str, r, e, a.d(), i, i2, 1, i3, i4, i5, 0);
                    a.a();
                } else {
                    z.e();
                }
            }
        }
    }

    private static JSONObject b(q qVar) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(z[0], qVar.b());
            jSONObject.put(z[2], qVar.c());
            jSONObject.put(z[1], qVar.d());
            jSONObject.put(z[4], qVar.e());
            jSONObject.put(z[3], qVar.g());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    private static synchronized void b(Context context) {
        o a;
        JSONException e;
        Exception e2;
        Cursor cursor = null;
        synchronized (r.class) {
            if (a.u()) {
                JSONObject jSONObject = new JSONObject();
                a = o.a(context);
                try {
                    jSONObject.put(z[13], a.j());
                    jSONObject.put(z[12], z[7]);
                    jSONObject.put(z[11], new SimpleDateFormat(z[10]).format(new Date(a.d())));
                    if (a.a(false)) {
                        jSONObject.put(z[9], a.b(true));
                        jSONObject.put(z[6], a.b(false));
                        JSONArray jSONArray = new JSONArray();
                        Cursor c = a.c();
                        if (c != null) {
                            do {
                                try {
                                    q a2 = a.a(c);
                                    if (a2 == null || ai.a(a2.a())) {
                                        z.b();
                                    } else {
                                        jSONArray.put(b(a2));
                                    }
                                } catch (JSONException e3) {
                                    e = e3;
                                    cursor = c;
                                } catch (Exception e4) {
                                    e2 = e4;
                                    cursor = c;
                                } catch (Throwable th) {
                                    Throwable th2 = th;
                                    cursor = c;
                                }
                            } while (c.moveToNext());
                            c.close();
                        }
                        jSONObject.put(z[5], jSONArray);
                        JSONArray jSONArray2 = new JSONArray();
                        cursor = a.d();
                        if (cursor != null) {
                            do {
                                q a3 = a.a(cursor);
                                if (a3 == null || ai.a(a3.a())) {
                                    z.b();
                                } else {
                                    jSONArray2.put(a(a3));
                                }
                            } while (cursor.moveToNext());
                            cursor.close();
                        }
                        jSONObject.put(z[8], jSONArray2);
                        a.a();
                        ac.a(context, jSONObject);
                        if (cursor != null) {
                            cursor.close();
                        }
                        if (a != null) {
                            a.a();
                        }
                    } else {
                        z.e();
                        if (a != null) {
                            a.a();
                        }
                    }
                } catch (JSONException e5) {
                    e = e5;
                } catch (Exception e6) {
                    e2 = e6;
                }
            }
        }
        z.e();
        e2.printStackTrace();
        if (cursor != null) {
            cursor.close();
        }
        if (a != null) {
            a.a();
        }
        try {
            z.e();
            e.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
            if (a != null) {
                a.a();
            }
        } catch (Throwable th3) {
            th2 = th3;
            if (cursor != null) {
                cursor.close();
            }
            if (a != null) {
                a.a();
            }
            throw th2;
        }
    }

    private static synchronized void c(Context context) {
        synchronized (r.class) {
            o a = o.a(context);
            if (a != null) {
                a.a(true);
                a.b();
                a.a();
            }
        }
    }
}
