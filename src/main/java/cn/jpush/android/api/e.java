package cn.jpush.android.api;

import android.app.Activity;
import android.app.Application;
import android.app.TabActivity;
import android.content.Context;
import cn.jpush.android.util.a;
import cn.jpush.android.util.ac;
import cn.jpush.android.util.ag;
import cn.jpush.android.util.ai;
import cn.jpush.android.util.j;
import cn.jpush.android.util.z;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class e {
    public static boolean a = false;
    public static boolean b = false;
    private static e c = null;
    private static final String[] z;
    private ExecutorService d = Executors.newSingleThreadExecutor();
    private String e = null;
    private String f = null;
    private ArrayList<a> g = new ArrayList();
    private long h = 30;
    private long i = 0;
    private long j = 0;
    private boolean k = false;
    private boolean l = true;
    private boolean m = true;
    private boolean n = false;
    private boolean o = true;
    private long p = 0;
    private WeakReference<JSONObject> q = null;
    private JSONObject r = null;
    private Object s = new Object();

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 24;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "#D&\b";
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
            case 0: goto L_0x012f;
            case 1: goto L_0x0133;
            case 2: goto L_0x0137;
            case 3: goto L_0x013b;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 105; // 0x69 float:1.47E-43 double:5.2E-322;
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
            case 22: goto L_0x0121;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "3L?\b";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "\ru'\u001e\u0001\u0014d";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "(K\u0000\b\u001a2H7";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "\ru'\u001e\u0001\u000eK&\b\u001b!D1\bG(K\u0000\b\u001a2H7E@gH'\u001e\u001dgG7M\n&I>\b\rgD4\u0019\f5\u00051\f\u0005+@6M#\u0017P!\u0005 )Q7\u001f\u000f&F7C\u0006)u3\u0018\u001a\"\r{M\b)Ar'92V:$\u00073@ \u000b\b$@|\u0002\u0007\u0015@!\u0018\u0004\"\u0005!\u0005\u00062I6M\u0007(Qr\u000f\fgF3\u0001\u0005\"Ar\u0000\u00065@r\u0019\u0000*@r\u0004\u0007gI3\u001e\u001dgd1\u0019\u00001L&\u0014I(Wr+\u001b&B?\b\u00073\u0005r";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "\ru'\u001e\u0001\u000eK&\b\u001b!D1\bG(K\u0000\b\u001a2H7E@gH'\u001e\u001dgG7M\n&I>\b\rgD4\u0019\f5\u00051\f\u0005+@6M#\u0017P!\u0005 )Q7\u001f\u000f&F7C\u0006)u3\u0018\u001a\"\r{M\u0000)\u0005>\f\u001a3\u0005\u0013\u000e\u001d.S;\u0019\u0010gJ M/5D5\u0000\f)Q";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "(K\u0002\f\u001c4@";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006c:
        r3[r2] = r1;
        r2 = 7;
        r1 = "\ru'\u001e\u0001\u000eK&\b\u001b!D1\bG(K\u0002\f\u001c4@zDI*P!\u0019I%@r\u000e\b+I7\tI&C&\b\u001bgF3\u0001\u0005\"Ar'92V:$\u00073@ \u000b\b$@|\u0002\u0007\u0015@!\u0018\u0004\"\r{M\b)Ar'92V:$\u00073@ \u000b\b$@|\u0002\u0007\u0017D'\u001e\fgV:\u0002\u001c+Ar\u0003\u00063\u00050\bI$D>\u0001\f#\u0005?\u0002\u001b\"\u0005&\u0004\u0004\"\u0005;\u0003I3M;\u001eI\u0006F&\u0004\u001f.Q+M\u00065\u0005\u0014\u001f\b H7\u0003\u001dg\u001er";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0074:
        r3[r2] = r1;
        r2 = 8;
        r1 = "4@!\u001e\u0000(K\r\u0004\r";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007d:
        r3[r2] = r1;
        r2 = 9;
        r1 = "+D!\u001967D'\u001e\f";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0087:
        r3[r2] = r1;
        r2 = 10;
        r1 = "3\\\"\b";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0092:
        r3[r2] = r1;
        r2 = 11;
        r1 = ".Q;\u0000\f";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x009d:
        r3[r2] = r1;
        r2 = 12;
        r1 = "&F&\u0004\u001f\"z&\b\u001b*L<\f\u001d\"";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00a8:
        r3[r2] = r1;
        r2 = 13;
        r1 = "-U'\u001e\u0001\u0018V&\f\u001d\u0018F3\u000e\u0001\"\u000b8\u001e\u0006)";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00b3:
        r3[r2] = r1;
        r2 = 14;
        r1 = "&F&\u0004\u001f.Q;\b\u001a";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00be:
        r3[r2] = r1;
        r2 = 15;
        r1 = "#P \f\u001d.J<";
        r0 = 14;
        r3 = r4;
        goto L_0x0009;
    L_0x00c9:
        r3[r2] = r1;
        r2 = 16;
        r1 = "$P 2\u001a\"V!\u0004\u0006)z!\u0019\b5Q";
        r0 = 15;
        r3 = r4;
        goto L_0x0009;
    L_0x00d4:
        r3[r2] = r1;
        r2 = 17;
        r1 = "$P 2\u001a\"@!\u0004\u0006)z7\u0003\r";
        r0 = 16;
        r3 = r4;
        goto L_0x0009;
    L_0x00df:
        r3[r2] = r1;
        r2 = 18;
        r1 = "2Q4@Q";
        r0 = 17;
        r3 = r4;
        goto L_0x0009;
    L_0x00ea:
        r3[r2] = r1;
        r2 = 19;
        r1 = "&F&\u0004\u001f\"z>\f\u001c)F:";
        r0 = 18;
        r3 = r4;
        goto L_0x0009;
    L_0x00f5:
        r3[r2] = r1;
        r2 = 20;
        r1 = "\ru'\u001e\u0001\u0014d|\u0002\u0007\u0015@!\u0018\u0004\"\r\u0011\u0002\u00073@*\u0019@gH'\u001e\u001dgG7M\u0000)S=\u0006\f#\u0005;\u0003I\u0006F&\u0004\u001f.Q+C\u0006)w7\u001e\u001c*@zD";
        r0 = 19;
        r3 = r4;
        goto L_0x0009;
    L_0x0100:
        r3[r2] = r1;
        r2 = 21;
        r1 = "\u0004J<\u0019\f?Qr\u001e\u0001(P>\tI%@r\f\u0007gd1\u0019\u00001L&\u0014I(Kr\u0019\u0001.Vr\u0000\f3M=\tI}\u0005";
        r0 = 20;
        r3 = r4;
        goto L_0x0009;
    L_0x010b:
        r3[r2] = r1;
        r2 = 22;
        r1 = "7D5\bI)D?\bI#L6\u0003N3\u0005?\f\u001d$Mr\u0019\u0001\"\u0005>\f\u001a3\u0005=\u0003\fgU3\u001e\u001a\"Ar\u000f\u0010gJ<?\f4P?\b";
        r0 = 21;
        r3 = r4;
        goto L_0x0009;
    L_0x0116:
        r3[r2] = r1;
        r2 = 23;
        r1 = "\ru'\u001e\u0001\u000eK&\b\u001b!D1\bG(K\u0002\f\u001c4@zDI*P!\u0019I%@r\u000e\b+I7\tI&C&\b\u001bgF3\u0001\u0005\"Ar'92V:$\u00073@ \u000b\b$@|\u0002\u0007\u0015@!\u0018\u0004\"\r{M\u0000)\u0005&\u0005\u00004\u0005\u0013\u000e\u001d.S;\u0019\u0010gJ M/5D5\u0000\f)Q";
        r0 = 22;
        r3 = r4;
        goto L_0x0009;
    L_0x0121:
        r3[r2] = r1;
        z = r4;
        r0 = 0;
        c = r0;
        r0 = 0;
        a = r0;
        r0 = 0;
        b = r0;
        return;
    L_0x012f:
        r9 = 71;
        goto L_0x0020;
    L_0x0133:
        r9 = 37;
        goto L_0x0020;
    L_0x0137:
        r9 = 82;
        goto L_0x0020;
    L_0x013b:
        r9 = 109; // 0x6d float:1.53E-43 double:5.4E-322;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.api.e.<clinit>():void");
    }

    private e() {
    }

    private JSONObject a(Context context, long j) {
        ag.a().b(context, z[16], this.i);
        StringBuilder stringBuilder = new StringBuilder();
        String q = a.q(context);
        if (!ai.a(q)) {
            stringBuilder.append(q);
        }
        q = a.j(context);
        if (!ai.a(q)) {
            stringBuilder.append(q);
        }
        stringBuilder.append(j);
        this.f = a.a(stringBuilder.toString());
        ag.a().b(context, z[8], this.f);
        JSONObject jSONObject = new JSONObject();
        try {
            if (cn.jpush.android.a.u()) {
                a(jSONObject);
                jSONObject.put(z[11], cn.jpush.android.a.j());
                jSONObject.put(z[8], this.f);
                jSONObject.put(z[10], z[19]);
                return jSONObject;
            }
            z.d();
            return null;
        } catch (JSONException e) {
            return null;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void a(cn.jpush.android.api.e r12, android.content.Context r13) {
        /*
        r10 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r8 = -1;
        r6 = 0;
        r0 = 0;
        r1 = cn.jpush.android.a.u();
        if (r1 != 0) goto L_0x0010;
    L_0x000c:
        cn.jpush.android.util.z.d();
    L_0x000f:
        return;
    L_0x0010:
        r1 = 1;
        r2 = r12.l;
        if (r2 == 0) goto L_0x009f;
    L_0x0015:
        r12.l = r0;
        cn.jpush.android.util.z.b();
        r2 = cn.jpush.android.util.ag.a();
        r3 = z;
        r4 = 9;
        r3 = r3[r4];
        r2 = r2.a(r13, r3, r8);
        r4 = r12.i;
        r4 = r4 - r2;
        r2 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1));
        if (r2 == 0) goto L_0x00ab;
    L_0x002f:
        r2 = r12.h;
        r2 = r2 * r10;
        r2 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1));
        if (r2 > 0) goto L_0x00ab;
    L_0x0036:
        r12.k = r0;
        r0 = r12.k;
        if (r0 == 0) goto L_0x00b0;
    L_0x003c:
        cn.jpush.android.util.z.b();
        r0 = new org.json.JSONArray;
        r0.<init>();
        r2 = r12.i;
        r1 = r12.a(r13, r2);
        if (r1 == 0) goto L_0x004f;
    L_0x004c:
        r0.put(r1);
    L_0x004f:
        r1 = r12.s;
        monitor-enter(r1);
        r2 = r12.d(r13);	 Catch:{ all -> 0x00ad }
        if (r2 == 0) goto L_0x008e;
    L_0x0058:
        r3 = r2.length();	 Catch:{ all -> 0x00ad }
        if (r3 <= 0) goto L_0x008e;
    L_0x005e:
        r3 = z;	 Catch:{ Exception -> 0x00c2 }
        r4 = 10;
        r3 = r3[r4];	 Catch:{ Exception -> 0x00c2 }
        r4 = z;	 Catch:{ Exception -> 0x00c2 }
        r5 = 12;
        r4 = r4[r5];	 Catch:{ Exception -> 0x00c2 }
        r2.put(r3, r4);	 Catch:{ Exception -> 0x00c2 }
        r3 = z;	 Catch:{ Exception -> 0x00c2 }
        r4 = 11;
        r3 = r3[r4];	 Catch:{ Exception -> 0x00c2 }
        r4 = cn.jpush.android.a.j();	 Catch:{ Exception -> 0x00c2 }
        r2.put(r3, r4);	 Catch:{ Exception -> 0x00c2 }
    L_0x007a:
        r3 = z;	 Catch:{ all -> 0x00ad }
        r4 = 13;
        r3 = r3[r4];	 Catch:{ all -> 0x00ad }
        r4 = 0;
        cn.jpush.android.util.ac.a(r13, r3, r4);	 Catch:{ all -> 0x00ad }
        r3 = 0;
        r12.r = r3;	 Catch:{ all -> 0x00ad }
        r3 = new java.util.ArrayList;	 Catch:{ all -> 0x00ad }
        r3.<init>();	 Catch:{ all -> 0x00ad }
        r12.g = r3;	 Catch:{ all -> 0x00ad }
    L_0x008e:
        monitor-exit(r1);	 Catch:{ all -> 0x00ad }
        if (r2 == 0) goto L_0x009a;
    L_0x0091:
        r1 = r2.length();
        if (r1 <= 0) goto L_0x009a;
    L_0x0097:
        r0.put(r2);
    L_0x009a:
        cn.jpush.android.util.ac.a(r13, r0);
        goto L_0x000f;
    L_0x009f:
        r2 = r12.i;
        r4 = r12.j;
        r2 = r2 - r4;
        r4 = r12.h;
        r4 = r4 * r10;
        r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r2 <= 0) goto L_0x0036;
    L_0x00ab:
        r0 = r1;
        goto L_0x0036;
    L_0x00ad:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x00ad }
        throw r0;
    L_0x00b0:
        r0 = cn.jpush.android.util.ag.a();
        r1 = z;
        r2 = 8;
        r1 = r1[r2];
        r0 = r0.a(r13, r1, r6);
        r12.f = r0;
        goto L_0x000f;
    L_0x00c2:
        r3 = move-exception;
        goto L_0x007a;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.api.e.a(cn.jpush.android.api.e, android.content.Context):void");
    }

    private static void a(JSONObject jSONObject) {
        String a = j.a();
        Object obj = a.split(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR)[0];
        Object obj2 = a.split(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR)[1];
        jSONObject.put(z[0], obj);
        jSONObject.put(z[1], obj2);
    }

    private static boolean a(String str) {
        boolean z = false;
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        if (stackTrace.length >= 2) {
            int i = 0;
            while (i < stackTrace.length) {
                try {
                    StackTraceElement stackTraceElement = stackTrace[i];
                    if (stackTraceElement.getMethodName().equals(str)) {
                        for (Class cls = Class.forName(stackTraceElement.getClassName()); cls.getSuperclass() != null; cls = cls.getSuperclass()) {
                            if (cls.getSuperclass() == Activity.class) {
                                z = true;
                                break;
                            }
                        }
                    }
                    i++;
                } catch (Exception e) {
                }
            }
        }
        return z;
    }

    public static e b() {
        if (c == null) {
            synchronized (e.class) {
                c = new e();
            }
        }
        return c;
    }

    static /* synthetic */ void b(e eVar, Context context) {
        int i = 0;
        if (context != null) {
            synchronized (eVar.s) {
                JSONArray optJSONArray;
                ag.a().b(context, z[9], eVar.j);
                ag.a().b(context, z[17], eVar.j);
                if (eVar.m) {
                    eVar.m = false;
                    if (!(eVar.k || eVar.d(context) == null)) {
                        optJSONArray = eVar.d(context).optJSONArray(z[14]);
                        if (optJSONArray != null) {
                            Collection arrayList = new ArrayList();
                            for (int i2 = 0; i2 < optJSONArray.length(); i2++) {
                                try {
                                    JSONObject optJSONObject = optJSONArray.optJSONObject(i2);
                                    if (optJSONObject != null) {
                                        Iterator keys = optJSONObject.keys();
                                        if (keys.hasNext()) {
                                            String str = (String) keys.next();
                                            arrayList.add(new a(str, optJSONObject.getLong(str)));
                                        }
                                    }
                                } catch (Exception e) {
                                    e.getMessage();
                                    z.e();
                                }
                            }
                            arrayList.addAll(eVar.g);
                            eVar.g = new ArrayList();
                            eVar.g.addAll(arrayList);
                        }
                    }
                }
                JSONObject d = eVar.d(context);
                JSONObject jSONObject = d == null ? new JSONObject() : d;
                optJSONArray = new JSONArray();
                while (i < eVar.g.size()) {
                    a aVar = (a) eVar.g.get(i);
                    JSONObject jSONObject2 = new JSONObject();
                    try {
                        jSONObject2.put(aVar.a, aVar.b);
                        optJSONArray.put(jSONObject2);
                    } catch (JSONException e2) {
                    }
                    i++;
                }
                if (optJSONArray.length() > 0) {
                    try {
                        jSONObject.put(z[14], optJSONArray);
                    } catch (JSONException e3) {
                    }
                }
                if (jSONObject.length() > 0) {
                    try {
                        long a = ag.a().a(context, z[16], 0);
                        long j = 10;
                        if (a == 0) {
                            a = eVar.j - eVar.p;
                            if (a > 0) {
                                j = a / 1000;
                            }
                            ag.a().b(context, z[16], eVar.p);
                        } else {
                            j = (eVar.j - a) / 1000;
                        }
                        jSONObject.put(z[15], j);
                        jSONObject.put(z[11], cn.jpush.android.a.j());
                        jSONObject.put(z[8], eVar.f);
                        a(jSONObject);
                    } catch (Exception e4) {
                    }
                    eVar.r = jSONObject;
                    if (eVar.r != null) {
                        try {
                            ac.a(context, eVar.r.toString().getBytes(z[18]).length);
                        } catch (UnsupportedEncodingException e5) {
                        }
                    }
                    ac.a(context, z[13], jSONObject);
                }
            }
        }
    }

    private boolean c(Context context, String str) {
        if (!this.o) {
            z.c();
            return false;
        } else if (context == null) {
            z.c();
            return false;
        } else if (context instanceof Application) {
            z.e(z[2], new StringBuilder(z[21]).append(str).toString());
            return false;
        } else if (a(str)) {
            return true;
        } else {
            throw new SecurityException(z[20]);
        }
    }

    private JSONObject d(Context context) {
        if (this.r == null) {
            this.r = ac.a(context, z[13]);
        }
        return this.r;
    }

    public final void a(long j) {
        this.h = j;
    }

    public final void a(Context context) {
        if (c(context, z[3])) {
            a = true;
            try {
                if (Class.forName(context.getClass().getName()).newInstance() instanceof TabActivity) {
                    this.n = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (this.n) {
                z.e(z[2], z[4]);
                return;
            }
            this.n = true;
            this.i = System.currentTimeMillis();
            this.e = context.getClass().getName();
            try {
                this.d.execute(new h(this, context));
            } catch (Exception e2) {
            }
        }
    }

    public final void a(Context context, String str) {
        if (this.n) {
            z.e(z[2], z[5]);
            return;
        }
        this.n = true;
        this.e = str;
        this.i = System.currentTimeMillis();
        try {
            this.d.execute(new f(this, context));
        } catch (Exception e) {
        }
    }

    public final void a(boolean z) {
        this.o = z;
    }

    public final boolean a() {
        return this.o;
    }

    public final void b(Context context) {
        if (c(context, z[6])) {
            b = true;
            try {
                if (Class.forName(context.getClass().getName()).newInstance() instanceof TabActivity) {
                    this.n = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (this.n) {
                this.n = false;
                if (this.e == null || !this.e.equals(context.getClass().getName())) {
                    z.c();
                    return;
                }
                this.j = System.currentTimeMillis();
                this.g.add(new a(this.e, (this.j - this.i) / 1000));
                this.p = this.i;
                try {
                    this.d.execute(new i(this, context));
                    return;
                } catch (Exception e2) {
                    return;
                }
            }
            z.e(z[2], z[7]);
        }
    }

    public final void b(Context context, String str) {
        if (this.n) {
            this.n = false;
            if (this.e == null || !this.e.equals(str)) {
                z.e(z[2], z[22]);
                return;
            }
            this.j = System.currentTimeMillis();
            this.g.add(new a(this.e, (this.j - this.i) / 1000));
            try {
                this.d.execute(new g(this, context));
                return;
            } catch (Exception e) {
                return;
            }
        }
        z.e(z[2], z[23]);
    }

    public final void c(Context context) {
        try {
            if (this.e != null && this.n) {
                this.j = System.currentTimeMillis();
                this.g.add(new a(this.e, (this.j - this.i) / 1000));
                try {
                    this.d.execute(new j(this, context));
                } catch (Exception e) {
                }
            }
        } catch (Exception e2) {
        }
    }
}
