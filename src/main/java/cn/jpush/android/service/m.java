package cn.jpush.android.service;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import cn.jpush.android.helpers.ConnectingHelper;
import cn.jpush.android.helpers.g;
import cn.jpush.android.util.z;
import com.letv.android.client.share.videoshot.VideoShotEditActivity;
import com.letv.core.constant.LiveRoomConstant;
import java.lang.ref.WeakReference;

final class m extends Handler {
    private static final String[] z;
    private final WeakReference<PushService> a;

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
        r3 = "\u0004=d(t\t\u0011o?k\r;ov";
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
        r11 = 24;
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
        r0 = "92o4h\t?~)|V|\"p\r2n }\b|g?Lq*";
        r3 = r0;
        r4 = r2;
        r5 = r6;
        r0 = r1;
        goto L_0x000b;
    L_0x0046:
        r5[r4] = r3;
        r3 = 2;
        r0 = "\u000f3d\"}\u000f(c#v";
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
        r11 = 108; // 0x6c float:1.51E-43 double:5.34E-322;
        goto L_0x0021;
    L_0x0058:
        r11 = 92;
        goto L_0x0021;
    L_0x005b:
        r11 = 10;
        goto L_0x0021;
    L_0x005e:
        r11 = 76;
        goto L_0x0021;
    L_0x0061:
        r8 = r1;
        goto L_0x002f;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.service.m.<clinit>():void");
    }

    public m(PushService pushService) {
        this.a = new WeakReference(pushService);
    }

    public final void handleMessage(Message message) {
        super.handleMessage(message);
        new StringBuilder(z[0]).append(message.toString());
        z.a();
        PushService pushService = (PushService) this.a.get();
        if (pushService == null || pushService.f == null || !pushService.f.isAlive()) {
            z.d();
            return;
        }
        Context applicationContext = pushService.getApplicationContext();
        switch (message.what) {
            case 1001:
                z.b();
                pushService.stopSelf();
                ConnectingHelper.sendConnectionChanged(applicationContext, a.b);
                return;
            case 1002:
                g.a(applicationContext, true, PushService.b, PushService.d, PushService.c);
                return;
            case LiveRoomConstant.LIVE_ROOM_LOADER_BOOK_ID /*1003*/:
                pushService.stopSelf();
                return;
            case 1004:
                PushService.a(pushService, true);
                return;
            case LiveRoomConstant.LIVE_ROOM_LOADER_HK_TVSERIES_ID /*1005*/:
                PushService.a(pushService, false);
                return;
            case LiveRoomConstant.LIVE_ROOM_LOADER_HK_VARIETY_ID /*1006*/:
                removeMessages(VideoShotEditActivity.VIDEOSHOT_VIEWPAGER_FLIP);
                removeMessages(1010);
                sendEmptyMessageDelayed(VideoShotEditActivity.VIDEOSHOT_VIEWPAGER_FLIP, 3000);
                return;
            case LiveRoomConstant.LIVE_ROOM_LOADER_HK_MUSIC_ID /*1007*/:
                sendEmptyMessageDelayed(1010, 200);
                return;
            case 1009:
                pushService.f.a(k.a.get(), message.obj);
                return;
            case 1010:
                PushService.a(pushService.g);
                return;
            case VideoShotEditActivity.VIDEOSHOT_VIEWPAGER_FLIP /*1011*/:
                pushService.a();
                return;
            case 1022:
                PushService.d(pushService);
                return;
            case 7301:
                PushService.b(pushService, message.getData().getLong(z[2]));
                return;
            case 7302:
                pushService.f.a(message.getData().getLong(z[2]), message.obj);
                return;
            case 7303:
                PushService.c(pushService, message.getData().getLong(z[2]));
                return;
            case 7304:
                PushService.a(pushService, message.getData().getLong(z[2]));
                return;
            case 7306:
                z.b(PushService.z[1], new StringBuilder(PushService.z[54]).append(message.getData().getLong(z[2])).append(PushService.z[53]).append(message.arg2).toString());
                return;
            case 7307:
                k.b.set(false);
                return;
            case 7501:
                pushService.f.a((cn.jpush.b.a.a.g) message.obj, 0);
                return;
            case 7502:
                pushService.f.a((cn.jpush.b.a.a.g) message.obj, 0);
                return;
            default:
                new StringBuilder(z[1]).append(message.what);
                z.a();
                return;
        }
    }
}
