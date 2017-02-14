package cn.jpush.android.service;

import cn.jpush.android.data.i;
import cn.jpush.android.data.s;
import cn.jpush.android.helpers.d;
import cn.jpush.android.helpers.g;
import cn.jpush.android.util.a;
import cn.jpush.android.util.z;
import com.letv.android.client.share.videoshot.VideoShotEditActivity;
import com.letv.core.constant.LiveRoomConstant;

final class e implements d {
    private static final String[] z;
    final /* synthetic */ boolean a;
    final /* synthetic */ int b;
    final /* synthetic */ DownloadService c;

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
        r1 = 0;
        r2 = 1;
        r0 = 3;
        r4 = new java.lang.String[r0];
        r3 = "-|,\u001emo0'\u0010~d8r";
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
            case 0: goto L_0x0056;
            case 1: goto L_0x0058;
            case 2: goto L_0x005b;
            case 3: goto L_0x005e;
            default: goto L_0x001f;
        };
    L_0x001f:
        r11 = 26;
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
            case 0: goto L_0x0047;
            case 1: goto L_0x0051;
            default: goto L_0x003d;
        };
    L_0x003d:
        r5[r4] = r3;
        r0 = "q9+\u0014tuf";
        r3 = r0;
        r4 = r2;
        r5 = r6;
        r0 = r1;
        goto L_0x000b;
    L_0x0047:
        r5[r4] = r3;
        r3 = 2;
        r0 = "-|<\u001en`0r";
        r4 = r3;
        r5 = r6;
        r3 = r0;
        r0 = r2;
        goto L_0x000b;
    L_0x0051:
        r5[r4] = r3;
        z = r6;
        return;
    L_0x0056:
        r11 = r2;
        goto L_0x0021;
    L_0x0058:
        r11 = 92;
        goto L_0x0021;
    L_0x005b:
        r11 = 72;
        goto L_0x0021;
    L_0x005e:
        r11 = 113; // 0x71 float:1.58E-43 double:5.6E-322;
        goto L_0x0021;
    L_0x0061:
        r8 = r1;
        goto L_0x002f;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.service.e.<clinit>():void");
    }

    e(DownloadService downloadService, boolean z, int i) {
        this.c = downloadService;
        this.a = z;
        this.b = i;
    }

    public final void a(int i) {
        this.c.c.cancel(this.b);
        if (b.a(i)) {
            String str;
            this.c.d.v = true;
            g.a(this.c.d.c, VideoShotEditActivity.VIDEOSHOT_VIEWPAGER_FLIP, this.c);
            String str2 = "";
            try {
                str = ((i) this.c.d).K;
            } catch (Exception e) {
                str = str2;
            }
            g.a(this.c.d.c, 1022, a.b(this.c, str), this.c);
        }
        this.c.d.w = true;
        DownloadService.a(this.c, this.b, this.c.d, i);
    }

    public final void a(long j, long j2) {
        new StringBuilder(z[1]).append((int) ((((float) j) / ((float) j2)) * 100.0f)).append(z[0]).append(j).append(z[2]).append(j2);
        z.b();
        if (!this.a) {
            this.c.a(this.c.d, this.b, j, j2);
        }
    }

    public final void a(String str, boolean z) {
        int i;
        this.c.d.x = true;
        DownloadService.a.remove(this.c.d);
        int i2 = 1001;
        if (this.c.d.a()) {
            i iVar = (i) this.c.d;
            iVar.P = str;
            iVar.Q = false;
            if (d.a(iVar.G, iVar.H, this.c)) {
                i2 = LiveRoomConstant.LIVE_ROOM_LOADER_BOOK_ID;
                iVar.Q = true;
            }
        } else if (this.c.d.b()) {
            ((s) this.c.d).I = str;
            i = 1004;
            if (z) {
                i = VideoShotEditActivity.VIDEOSHOT_VIEWPAGER_DESTORY;
            }
            g.a(this.c.d.c, i, this.c);
            if (!this.c.d.e()) {
                this.c.n.sendEmptyMessageDelayed(this.b, 500);
            }
            this.c.d.w = true;
            DownloadService.a(this.c, this.c.d);
        } else if (this.c.d.e()) {
            this.c.d.C = str;
        }
        i = i2;
        if (z) {
            i = VideoShotEditActivity.VIDEOSHOT_VIEWPAGER_DESTORY;
        }
        g.a(this.c.d.c, i, this.c);
        if (this.c.d.e()) {
            this.c.n.sendEmptyMessageDelayed(this.b, 500);
        }
        this.c.d.w = true;
        DownloadService.a(this.c, this.c.d);
    }
}
