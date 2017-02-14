package cn.jpush.android.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import cn.jpush.android.a;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JRecorder {
    private static ExecutorService a = Executors.newSingleThreadExecutor();
    private static Context c;
    private static Handler d = null;
    private static ArrayList<x> e = new ArrayList();
    private static volatile boolean f = false;
    private static final String[] z;
    private ArrayList<y> b;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 12;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "A\fJg!GI_f2Q\u0005_ls\t";
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
            case 0: goto L_0x00b5;
            case 1: goto L_0x00b9;
            case 2: goto L_0x00bd;
            case 3: goto L_0x00c1;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 83;
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
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "A\fJg!GI^a R\u000bVm7\u0013\u000bC(i";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "A\fYg!W\u0019_z:\\\r";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "Z\u0007Yz6R\u0004_f'";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "A\fYg!W6Nq#V";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "W\u001cHi'Z\u0006T";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "A\bTo6";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006c:
        r3[r2] = r1;
        r2 = 7;
        r1 = "\\\u001f_z?R\u0010";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0074:
        r3[r2] = r1;
        r2 = 8;
        r1 = "A\fYg!W\u001cTa'@";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007d:
        r3[r2] = r1;
        r2 = 9;
        r1 = "Z\u001dSe6";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0087:
        r3[r2] = r1;
        r2 = 10;
        r1 = "G\u0010Jm";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0092:
        r3[r2] = r1;
        r2 = 11;
        r1 = "Y\u001b_k<A\r_z";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x009d:
        r3[r2] = r1;
        z = r4;
        r0 = java.util.concurrent.Executors.newSingleThreadExecutor();
        a = r0;
        r0 = 0;
        d = r0;
        r0 = new java.util.ArrayList;
        r0.<init>();
        e = r0;
        r0 = 0;
        f = r0;
        return;
    L_0x00b5:
        r9 = 51;
        goto L_0x0020;
    L_0x00b9:
        r9 = 105; // 0x69 float:1.47E-43 double:5.2E-322;
        goto L_0x0020;
    L_0x00bd:
        r9 = 58;
        goto L_0x0020;
    L_0x00c1:
        r9 = 8;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.util.JRecorder.<clinit>():void");
    }

    private JRecorder() {
        if (d == null) {
            d = new Handler(Looper.getMainLooper());
        }
    }

    private JRecorder(int i, Context context) {
        this();
        c = context.getApplicationContext();
        this.b = new ArrayList();
        x xVar = new x();
        xVar.a = i;
        xVar.b = this.b;
        e.add(xVar);
    }

    private static JSONObject a(ArrayList<y> arrayList) {
        if (arrayList == null) {
            return null;
        }
        int size = arrayList.size();
        if (size <= 0) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        long j = ((y) arrayList.get(size - 1)).a - ((y) arrayList.get(0)).a;
        int i = 0;
        int i2 = 0;
        while (i < size) {
            i++;
            i2 = (int) (((double) i2) + ((y) arrayList.get(i)).b);
        }
        jSONObject.put(z[4], z[3]);
        jSONObject.put(z[5], j);
        jSONObject.put(z[6], ((double) i2) - ((y) arrayList.get(0)).b);
        return jSONObject;
    }

    static /* synthetic */ void a(Context context) {
        try {
            JSONArray jSONArray;
            if (e == null || e.size() <= 0) {
                jSONArray = null;
            } else {
                JSONArray jSONArray2 = new JSONArray();
                Iterator it = e.iterator();
                while (it.hasNext()) {
                    x xVar = (x) it.next();
                    if (xVar.a == 0) {
                        jSONArray2.put(a(xVar.b));
                    } else if (xVar.a == 1) {
                        Object obj;
                        ArrayList arrayList = xVar.b;
                        if (arrayList == null) {
                            obj = null;
                        } else {
                            int size = arrayList.size();
                            if (size <= 0) {
                                obj = null;
                            } else {
                                long j = ((y) arrayList.get(size - 1)).a - ((y) arrayList.get(0)).a;
                                JSONObject jSONObject = new JSONObject();
                                jSONObject.put(z[4], z[7]);
                                jSONObject.put(z[5], j);
                                jSONObject.put(z[6], ((y) arrayList.get(size - 1)).b - ((y) arrayList.get(0)).b);
                                JSONObject jSONObject2 = jSONObject;
                            }
                        }
                        jSONArray2.put(obj);
                    }
                }
                b();
                jSONArray = jSONArray2;
            }
            if (jSONArray != null && jSONArray.length() > 0) {
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put(z[10], z[11]);
                jSONObject3.put(z[9], a.j());
                jSONObject3.put(z[8], jSONArray);
                a.execute(new w(jSONObject3));
            }
        } catch (JSONException e) {
        }
    }

    static /* synthetic */ void a(JRecorder jRecorder, double d) {
        synchronized (jRecorder.b) {
            y yVar = new y(jRecorder);
            yVar.b = d;
            yVar.a = System.currentTimeMillis();
            jRecorder.b.add(yVar);
        }
    }

    private static void b() {
        Iterator it = e.iterator();
        while (it.hasNext()) {
            ((x) it.next()).b.clear();
        }
        e.clear();
    }

    public static JRecorder getIncreamentsRecorder(Context context) {
        return new JRecorder(0, context);
    }

    public static JRecorder getSuperpositionRecorder(Context context) {
        return new JRecorder(1, context);
    }

    public static void parseRecordCommand(String str) {
        if (f) {
            z.b();
            return;
        }
        try {
            int i = new JSONObject(str).getInt(z[2]);
            f = true;
            new StringBuilder(z[0]).append(i).append("s");
            z.b();
            if (d == null) {
                d = new Handler(Looper.getMainLooper());
            }
            d.postDelayed(new v(), (long) (i * 1000));
        } catch (JSONException e) {
            f = false;
            new StringBuilder(z[1]).append(e.getMessage());
            z.b();
        }
    }

    public void record(int i) {
        if (f) {
            a.execute(new u(this, i));
        } else {
            z.b();
        }
    }
}
