package cn.jpush.android.b.a;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;
import cn.jpush.android.api.m;
import cn.jpush.android.data.c;
import cn.jpush.android.data.i;
import cn.jpush.android.data.s;
import cn.jpush.android.e;
import cn.jpush.android.helpers.d;
import cn.jpush.android.helpers.g;
import cn.jpush.android.service.ServiceInterface;
import cn.jpush.android.ui.PopWinActivity;
import cn.jpush.android.ui.PushActivity;
import cn.jpush.android.util.a;
import cn.jpush.android.util.ai;
import cn.jpush.android.util.z;
import java.lang.ref.WeakReference;

public final class f {
    private static final String[] z;
    private final WeakReference<Activity> a;
    private final c b;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 20;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "g\u000fE9BP\u0013IoJG\u001e\u0000wB^\u0002\u0000pP\u0013\tUuO\u0013\bR9F^\u0017T`\u000f\u0013 IoF\u0013\u0012P7\r";
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
            case 0: goto L_0x00fc;
            case 1: goto L_0x0100;
            case 2: goto L_0x0104;
            case 3: goto L_0x0108;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 35;
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
            case 14: goto L_0x00ca;
            case 15: goto L_0x00d5;
            case 16: goto L_0x00e0;
            case 17: goto L_0x00eb;
            case 18: goto L_0x00f7;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "g\u000fE9BP\u0013IoJG\u001e\u0000wB^\u0002\u0000pP\u0013\u000eNoB_\u000eD5\u0003t\u000eV|\u0003F\u0017\u000e7";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "P\t\u000esSF\u0014H7B]\u0003RvJWIaZwz1iMzl7aKb~";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "d\u0002BOJV\u0010h|OC\u0002R";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "R\u0004TpUZ\u0013YWB^\u0002\u0000$\u0003";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "\u001fGSqLF\u000bDZB]\u0004Eum\\\u0013IJP\u0006TpL]]";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "\u001fGSqLF\u000bDZO\\\u0014E#";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006c:
        r3[r2] = r1;
        r2 = 7;
        r1 = "d\u0002B9@R\u000bL{BP\f\u001azOZ\u0004K9\u000e\u0013\u0006CmJ\\\ti}\u0019";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0074:
        r3[r2] = r1;
        r2 = 8;
        r1 = "d\u0002B9@R\u000bL{BP\f\u001azQV\u0006T|p[\bRm@F\u0013\u00004\u0003]\u0006M|\u0019";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007d:
        r3[r2] = r1;
        r2 = 9;
        r1 = "\u001fGUkO\t";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0087:
        r3[r2] = r1;
        r2 = 10;
        r1 = "Z\u0004OwjWGSqLF\u000bD9AVGIwW\u0013J\u0000";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0092:
        r3[r2] = r1;
        r2 = 11;
        r1 = "d\u0002B9@R\u000bL{BP\f\u001ajK\\\u0010tvB@\u0013\u00004\u0003";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x009d:
        r3[r2] = r1;
        r2 = 12;
        r1 = "P\t\u000esSF\u0014H7B]\u0003RvJWIIwWV\tT7bp3iVml5iZkc2sQ|p&lUar$k";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00a8:
        r3[r2] = r1;
        r2 = 13;
        r1 = "f\tHxMW\u000bE9J]\u0013EwW\u0013]\u0000";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00b3:
        r3[r2] = r1;
        r2 = 14;
        r1 = "P\t\u000esSF\u0014H7B]\u0003RvJWIeAwa&";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00be:
        r3[r2] = r1;
        r2 = 15;
        r1 = "z\tVxOZ\u0003\u0000x@G\u000eOwjWGFkL^Gw|A\u0013J\u0000";
        r0 = 14;
        r3 = r4;
        goto L_0x0009;
    L_0x00ca:
        r3[r2] = r1;
        r2 = 16;
        r1 = "d\u0002B9@R\u000bL{BP\f\u001a|[V\u0004UmF~\u0014GTF@\u0014A~F\u0013J\u0000";
        r0 = 15;
        r3 = r4;
        goto L_0x0009;
    L_0x00d5:
        r3[r2] = r1;
        r2 = 17;
        r1 = "f\tHxMW\u000bE9J]\u0013EwW\u0013]\u0000zM\u001d\rPlP[IAwGA\bI}\rZ\tT|MGIaZwz(nFbp3iOjg>Vsv)d\\g";
        r0 = 16;
        r3 = r4;
        goto L_0x0009;
    L_0x00e0:
        r3[r2] = r1;
        r2 = 18;
        r1 = "d\u0002B9@R\u000bL{BP\f\u001a}LD\tLvBWG\r9";
        r0 = 17;
        r3 = r4;
        goto L_0x0009;
    L_0x00eb:
        r3[r2] = r1;
        r2 = 19;
        r1 = "z\tVxOZ\u0003\u0000tF@\u0014A~Fg\u001eP|\u0003U\bR9G\\\u0010NuLR\u0003\u00004\u0003";
        r0 = 18;
        r3 = r4;
        goto L_0x0009;
    L_0x00f7:
        r3[r2] = r1;
        z = r4;
        return;
    L_0x00fc:
        r9 = 51;
        goto L_0x0020;
    L_0x0100:
        r9 = 103; // 0x67 float:1.44E-43 double:5.1E-322;
        goto L_0x0020;
    L_0x0104:
        r9 = 32;
        goto L_0x0020;
    L_0x0108:
        r9 = 25;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.b.a.f.<clinit>():void");
    }

    public f(Context context, c cVar) {
        this.a = new WeakReference((Activity) context);
        this.b = cVar;
    }

    private void g(String str) {
        int parseInt;
        int i = 1100;
        try {
            parseInt = Integer.parseInt(str);
        } catch (Exception e) {
            new StringBuilder(z[15]).append(str);
            parseInt = i;
            z.e();
        }
        g.a(this.b.c, parseInt, (Context) this.a.get());
    }

    public final void a() {
        if (this.a.get() != null) {
            z.b();
            ((Activity) this.a.get()).finish();
        }
    }

    public final void a(String str) {
        Context context = (Context) this.a.get();
        if (context != null) {
            a.b(context, z[12], str);
        }
    }

    public final void a(String str, String str2) {
        new StringBuilder(z[4]).append(str);
        z.b();
        if (ai.a(str)) {
            z.e(z[3], z[0]);
        }
        Context context = (Context) this.a.get();
        if (context != null) {
            try {
                Class cls = Class.forName(str);
                if (cls != null) {
                    Intent intent = new Intent(context, cls);
                    intent.putExtra(z[2], str2);
                    intent.setFlags(268435456);
                    context.startActivity(intent);
                }
            } catch (Exception e) {
                z.e(z[3], z[1]);
            }
        }
    }

    public final void a(String str, String str2, String str3) {
        int parseInt;
        int i = 0;
        try {
            parseInt = Integer.parseInt(str3);
        } catch (Exception e) {
            new StringBuilder(z[10]).append(str3);
            parseInt = i;
            z.b();
        }
        if (this.a.get() != null) {
            new StringBuilder(z[8]).append(str).append(z[9]).append(str2);
            z.b();
            a.a((Context) this.a.get(), str, str2, m.a(parseInt));
        }
    }

    public final void b() {
        if (this.a.get() != null && (this.a.get() instanceof PushActivity)) {
            ((PushActivity) this.a.get()).a();
        }
    }

    public final void b(String str) {
        Context context = (Context) this.a.get();
        if (context != null) {
            try {
                a.h(context, str);
                ((Activity) context).finish();
            } catch (Exception e) {
                z.e(z[3], z[17]);
            }
        }
    }

    public final void b(String str, String str2) {
        Context context = (Context) this.a.get();
        if (context != null) {
            try {
                Intent intent = new Intent(str);
                intent.addCategory(context.getPackageName());
                intent.putExtra(z[14], str2);
                intent.setFlags(268435456);
                context.startActivity(intent);
            } catch (Exception e) {
                z.e(z[3], new StringBuilder(z[13]).append(str).toString());
            }
        }
    }

    public final void b(String str, String str2, String str3) {
        if (this.a.get() != null) {
            boolean parseBoolean;
            boolean z;
            new StringBuilder(z[7]).append(str).append(z[6]).append(str2).append(z[5]).append(str3);
            z.b();
            g(str);
            try {
                parseBoolean = Boolean.parseBoolean(str2);
                try {
                    z = parseBoolean;
                    parseBoolean = Boolean.parseBoolean(str3);
                } catch (Exception e) {
                    z = parseBoolean;
                    parseBoolean = false;
                    if (parseBoolean) {
                        m.a((Context) this.a.get(), this.b, 0);
                    }
                    if (!z) {
                        ((Activity) this.a.get()).finish();
                    }
                }
            } catch (Exception e2) {
                parseBoolean = false;
                z = parseBoolean;
                parseBoolean = false;
                if (parseBoolean) {
                    m.a((Context) this.a.get(), this.b, 0);
                }
                if (!z) {
                    ((Activity) this.a.get()).finish();
                }
            }
            if (parseBoolean) {
                m.a((Context) this.a.get(), this.b, 0);
            }
            if (!z) {
                ((Activity) this.a.get()).finish();
            }
        }
    }

    public final void c(String str) {
        if (this.a.get() != null) {
            new StringBuilder(z[18]).append(str);
            z.b();
            Context context = (Context) this.a.get();
            c cVar = this.b;
            if (cVar.a()) {
                c cVar2 = (i) cVar;
                if (TextUtils.isEmpty(cVar2.K)) {
                    cVar2.K = str;
                }
                if (!TextUtils.isEmpty(cVar2.P)) {
                    a.e(context, cVar2.P);
                    m.a(context, cVar2, 0);
                    m.a(context, cVar2, 1);
                    return;
                }
            } else if (cVar.b()) {
                s sVar = (s) cVar;
                if (TextUtils.isEmpty(sVar.E)) {
                    sVar.E = str;
                }
                if (!TextUtils.isEmpty(sVar.I)) {
                    context.startActivity(a.a(context, cVar, false));
                    return;
                }
            } else {
                new StringBuilder(z[19]).append(cVar.o);
                z.d();
                return;
            }
            ServiceInterface.a(context, cVar);
        }
    }

    public final void c(String str, String str2) {
        if (this.a.get() != null) {
            g(str);
            c(str2);
            m.a((Context) this.a.get(), this.b, 0);
            ((Activity) this.a.get()).finish();
        }
    }

    public final void d(String str) {
        if (this.a.get() != null) {
            new StringBuilder(z[11]).append(str);
            z.b();
            Toast.makeText((Context) this.a.get(), str, 0).show();
        }
    }

    public final void e(String str) {
        if (e.a) {
            new StringBuilder(z[16]).append(str);
            z.b();
            if (this.a.get() != null) {
                d.a((Context) this.a.get(), str);
            }
        }
    }

    public final void f(String str) {
        if (this.a.get() != null && (this.a.get() instanceof PopWinActivity)) {
            ((PopWinActivity) this.a.get()).a(str);
        }
    }
}
