package cn.jpush.android.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.RemoteViews;
import cn.jpush.android.api.m;
import cn.jpush.android.data.b;
import cn.jpush.android.data.c;
import cn.jpush.android.data.i;
import cn.jpush.android.data.s;
import cn.jpush.android.e;
import cn.jpush.android.helpers.d;
import cn.jpush.android.helpers.g;
import cn.jpush.android.util.a;
import cn.jpush.android.util.z;
import com.letv.android.client.share.videoshot.VideoShotEditActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DownloadService extends IntentService {
    public static ConcurrentLinkedQueue<c> a = new ConcurrentLinkedQueue();
    private static Bundle b;
    private static final String[] z;
    private NotificationManager c;
    private c d;
    private g e;
    private Notification f;
    private Builder g;
    private RemoteViews h;
    private Integer i = Integer.valueOf(0);
    private Integer j = Integer.valueOf(0);
    private Integer k = Integer.valueOf(0);
    private Integer l = Integer.valueOf(0);
    private Integer m = Integer.valueOf(0);
    private Handler n = new f(this);

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 22;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "G-l\u0002UR,p:EV-";
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
            case 0: goto L_0x0120;
            case 1: goto L_0x0124;
            case 2: goto L_0x0128;
            case 3: goto L_0x012b;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 39;
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
            case 7: goto L_0x007e;
            case 8: goto L_0x0088;
            case 9: goto L_0x0094;
            case 10: goto L_0x009f;
            case 11: goto L_0x00ab;
            case 12: goto L_0x00b7;
            case 13: goto L_0x00c3;
            case 14: goto L_0x00ce;
            case 15: goto L_0x00da;
            case 16: goto L_0x00e5;
            case 17: goto L_0x00f1;
            case 18: goto L_0x00fd;
            case 19: goto L_0x0109;
            case 20: goto L_0x0114;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "C:{\u0011xG-l\u0002UR,p";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "e{o\u0004^X*w";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "Y0w\fA^<b\u0011NX1";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "^<l\u000b";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "e{j\u0001";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "C6w\tB";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006c:
        r3[r2] = r1;
        r2 = 7;
        r1 = "Y0w\fA^<b\u0011NX1\\\tFN0v\u0011";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0074:
        r3[r2] = r1;
        r2 = 8;
        r1 = "丼輢丮K\t\u0019";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007e:
        r3[r2] = r1;
        r2 = 9;
        r1 = "d+b\u0017S^1dESXg\nPY3l\u0004C\u0017r#\bBD,b\u0002B~;9";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0088:
        r3[r2] = r1;
        r2 = 10;
        r1 = "r'f\u0006RC:#\nKSg\nPY3l\u0004C\u0017+b\u0016L\u0017r#\u0016NM:9";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0094:
        r3[r2] = r1;
        r2 = 11;
        r1 = "Y0w:FB+l\u0017RY";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x009f:
        r3[r2] = r1;
        r2 = 12;
        r1 = "y0#\u0000ISm\nS^9j\u0006FC6l\u000b\t\u00176pEA^3f5FC7#\u0000JG+zE\u0018\u0017r#";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00ab:
        r3[r2] = r1;
        r2 = 13;
        r1 = "s0t\u000bKX>g6BE)j\u0006B";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00b7:
        r3[r2] = r1;
        r2 = 14;
        r1 = "曃旯牋杉巕丼輢宏殰Ｋ诀烦凸寬裢〵";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00c3:
        r3[r2] = r1;
        r2 = 15;
        r1 = "T1-\u000fWB,kKFY;q\nNSqj\u000bSR1wKix\u000bJ#nt\u001eW,hy\u0000J+tc\u001eO)xt\u0013J&lr\u001b";
        r0 = 14;
        r3 = r4;
        goto L_0x0009;
    L_0x00ce:
        r3[r2] = r1;
        r2 = 16;
        r1 = "宾袚匆嶗丬轊寓毖ｩ诐炎冤宊袠〥";
        r0 = 15;
        r3 = r4;
        goto L_0x0009;
    L_0x00da:
        r3[r2] = r1;
        r2 = 17;
        r1 = "U0g\u001c";
        r0 = 16;
        r3 = r4;
        goto L_0x0009;
    L_0x00e5:
        r3[r2] = r1;
        r2 = 18;
        r1 = "丼輢赇滵外敿そ说穨吩炎冤野旕丬轊～";
        r0 = 17;
        r3 = r4;
        goto L_0x0009;
    L_0x00f1:
        r3[r2] = r1;
        r2 = 19;
        r1 = "彤划罒纹个变畷、穨吩伭纸绮乮轚Ｖ";
        r0 = 18;
        r3 = r4;
        goto L_0x0009;
    L_0x00fd:
        r3[r2] = r1;
        r2 = 20;
        r1 = "丼輢夲赀〥诀穒名烜凜釺旯丈輘Ｆ";
        r0 = 19;
        r3 = r4;
        goto L_0x0009;
    L_0x0109:
        r3[r2] = r1;
        r2 = 21;
        r1 = "Z0v\u000bSR;";
        r0 = 20;
        r3 = r4;
        goto L_0x0009;
    L_0x0114:
        r3[r2] = r1;
        z = r4;
        r0 = new java.util.concurrent.ConcurrentLinkedQueue;
        r0.<init>();
        a = r0;
        return;
    L_0x0120:
        r9 = 55;
        goto L_0x0020;
    L_0x0124:
        r9 = 95;
        goto L_0x0020;
    L_0x0128:
        r9 = 3;
        goto L_0x0020;
    L_0x012b:
        r9 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.service.DownloadService.<clinit>():void");
    }

    public DownloadService() {
        super(z[13]);
    }

    public static void a(Context context) {
        new StringBuilder(z[10]).append(a.size());
        z.b();
        ArrayList arrayList = new ArrayList();
        while (true) {
            c cVar = (c) a.poll();
            if (cVar == null) {
                break;
            } else if (cVar.w) {
                new StringBuilder(z[9]).append(cVar.c);
                z.b();
                ServiceInterface.a(context, cVar);
            } else {
                z.a();
                arrayList.add(cVar);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            a.offer((c) it.next());
        }
    }

    private void a(c cVar, int i, long j, long j2) {
        int i2;
        if (VERSION.SDK_INT >= 11) {
            if (this.g == null) {
                this.g = new Builder(getApplicationContext()).setSmallIcon(17301633).setWhen(System.currentTimeMillis()).setDefaults(4).setOngoing(true);
                this.g.setContentIntent(PendingIntent.getActivity(getApplicationContext(), i, new Intent(), 134217728));
            }
            CharSequence charSequence = cVar.s;
            CharSequence charSequence2 = z[8];
            i2 = (int) ((((float) j) / ((float) j2)) * 100.0f);
            if (j2 > 0) {
                charSequence2 = charSequence2 + i2 + "%";
            }
            if (this.m == null || this.m.intValue() <= 0) {
                this.g.setContentTitle(charSequence).setContentText(charSequence2).setContentIntent(PendingIntent.getActivity(getApplicationContext(), i, new Intent(), 134217728));
            } else {
                if (this.h == null) {
                    this.h = new RemoteViews(getPackageName(), this.m.intValue());
                    this.h.setTextViewText(this.i.intValue(), charSequence);
                    this.h.setImageViewResource(this.k.intValue(), e.b);
                }
                this.h.setTextViewText(this.l.intValue(), i2 + "%");
                this.h.setProgressBar(this.j.intValue(), 100, i2, false);
                this.g.setContent(this.h);
            }
            this.c.notify(i, this.g.getNotification());
            return;
        }
        if (this.f == null) {
            this.f = new Notification();
            this.f.icon = 17301633;
            this.f.when = System.currentTimeMillis();
            this.f.flags = 2;
            this.f.defaults = 4;
            this.f.contentIntent = PendingIntent.getActivity(getApplicationContext(), i, new Intent(), 134217728);
        }
        Object obj = cVar.s;
        String str = z[8];
        i2 = (int) ((((float) j) / ((float) j2)) * 100.0f);
        if (j2 > 0) {
            str = str + i2 + "%";
        }
        if (this.m == null || this.m.intValue() <= 0) {
            m.a(this.f, this, obj, str, PendingIntent.getActivity(getApplicationContext(), i, new Intent(), 134217728));
        } else {
            if (this.h == null) {
                this.h = new RemoteViews(getPackageName(), this.m.intValue());
                this.h.setTextViewText(this.i.intValue(), obj);
                this.h.setImageViewResource(this.k.intValue(), e.b);
            }
            this.h.setTextViewText(this.l.intValue(), i2 + "%");
            this.h.setProgressBar(this.j.intValue(), 100, i2, false);
            this.f.contentView = this.h;
        }
        if (this.f != null) {
            this.c.notify(i, this.f);
        }
    }

    private void a(c cVar, boolean z) {
        Intent a;
        boolean z2;
        boolean c = cVar.c();
        if (!cVar.a() || z) {
            a = a.a(getApplicationContext(), cVar, false);
            z2 = false;
        } else {
            z2 = true;
            a = new Intent();
            a.putExtra(z[17], cVar);
            a.setClass(getApplicationContext(), PushReceiver.class);
            a.setAction(z[15]);
            if (cVar.c()) {
                cVar.t = z[14];
            } else {
                cVar.t = z[16];
            }
        }
        int a2 = m.a(cVar, 0);
        Notification a3 = m.a(getApplicationContext(), a2, a, cVar, c, z2);
        if (a3 != null) {
            this.c.notify(a2, a3);
        } else {
            z.e();
        }
    }

    static /* synthetic */ void a(DownloadService downloadService, int i, c cVar, int i2) {
        if (i2 != 0 && !cVar.e()) {
            Object obj;
            int i3 = 4;
            if (2 == i2) {
                obj = z[20];
            } else if (3 == i2) {
                obj = z[18];
            } else if (1 == i2) {
                String str = z[19];
                i3 = 2;
            } else {
                return;
            }
            Object obj2 = cVar.s;
            Intent intent = new Intent();
            if (b.a(i2)) {
                intent.setClass(downloadService.getApplicationContext(), DownloadService.class);
                cVar.z = -1;
                intent.putExtra(z[17], cVar);
            }
            PendingIntent service = PendingIntent.getService(downloadService, i, intent, 134217728);
            if (VERSION.SDK_INT >= 11) {
                new Builder(downloadService.getApplicationContext()).setContentTitle(obj2).setContentText(obj).setContentIntent(service).setWhen(System.currentTimeMillis()).setSmallIcon(17301634).getNotification().flags = i3;
            } else {
                Notification notification = new Notification();
                notification.icon = 17301634;
                notification.when = System.currentTimeMillis();
                notification.flags = i3;
                m.a(notification, downloadService.getApplicationContext(), obj2, obj, service);
            }
            if (downloadService.f != null) {
                downloadService.c.notify(i, downloadService.f);
            }
        }
    }

    static /* synthetic */ void a(DownloadService downloadService, c cVar) {
        if (cVar.e()) {
            a.b(downloadService.getApplicationContext(), cVar);
            return;
        }
        if (cVar.a()) {
            Object obj = ((i) cVar).P;
        } else {
            String str = cVar.b() ? ((s) cVar).I : "";
        }
        if (cVar.a() && !TextUtils.isEmpty(obj)) {
            i iVar = (i) cVar;
            String str2 = iVar.I ? iVar.E : z[11];
            PackageInfo packageInfo = null;
            try {
                packageInfo = downloadService.getPackageManager().getPackageArchiveInfo(obj, 16384);
            } catch (Exception e) {
                z.h();
            }
            String str3 = packageInfo != null ? packageInfo.packageName : "";
            if (TextUtils.isEmpty(str3) && !TextUtils.isEmpty(iVar.a)) {
                str3 = iVar.a;
            }
            b.a(downloadService, cVar, str3, str2);
            if (d.a(iVar.G, iVar.H, downloadService.getApplicationContext())) {
                downloadService.a(cVar, true);
                return;
            }
            if (iVar.N) {
                downloadService.a(cVar, false);
            }
            if (iVar.O) {
                a.e(downloadService.getApplicationContext(), obj);
            }
            if (!iVar.N && !iVar.O) {
                z.b();
            }
        } else if (!cVar.b() || TextUtils.isEmpty(obj)) {
            new StringBuilder(z[12]).append(obj);
            z.b();
        } else {
            downloadService.a(cVar, false);
        }
    }

    public static boolean a() {
        return a.size() > 0;
    }

    public void onCreate() {
        z.b();
        super.onCreate();
        this.e = new g(this, getApplicationContext());
        this.c = (NotificationManager) getSystemService(z[3]);
        if (b == null) {
            b = new Bundle();
        }
        try {
            if (this.m.intValue() == 0) {
                HashMap a = m.a(z[2], new String[]{z[7]});
                if (a.size() > 0) {
                    this.m = (Integer) a.get(z[7]);
                }
                HashMap a2 = m.a(z[5], new String[]{z[6], z[0], z[4], z[1]});
                if (a2.size() > 0) {
                    this.i = (Integer) a2.get(z[6]);
                    this.j = (Integer) a2.get(z[0]);
                    this.k = (Integer) a2.get(z[4]);
                    this.l = (Integer) a2.get(z[1]);
                }
            }
        } catch (Exception e) {
            z.h();
        }
    }

    public void onDestroy() {
        z.b();
        super.onDestroy();
    }

    protected void onHandleIntent(Intent intent) {
        z.b();
        this.d = (c) intent.getSerializableExtra(z[17]);
        if (this.d == null) {
            z.d();
        } else if (!Environment.getExternalStorageState().equals(z[21])) {
            z.d();
            this.e.sendEmptyMessage(0);
        } else if (this.d.x) {
            z.b();
        } else {
            boolean z;
            if (this.d.v) {
                g.a(this.d.c, VideoShotEditActivity.VIDEOSHOT_VIEWPAGER_DELETE, this);
            }
            if (!a.contains(this.d)) {
                a.offer(this.d);
            }
            int a = m.a(this.d, 1);
            c cVar = this.d;
            if (cVar.e()) {
                z = true;
            } else {
                int i;
                if (cVar.a()) {
                    i iVar = (i) cVar;
                    if (d.a(iVar.G, iVar.H, (Context) this)) {
                        i = 1;
                    } else {
                        a(cVar, a, 0, 0);
                        i = 0;
                    }
                } else {
                    i = 0;
                }
                z = i != 0 || cVar.b();
            }
            Thread.currentThread().setPriority(1);
            b bVar = new b(this, this.d, b, new e(this, z, a), 3000);
        }
    }
}
