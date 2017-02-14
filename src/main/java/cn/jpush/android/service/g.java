package cn.jpush.android.service;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

final class g extends Handler {
    private static final String z;
    final /* synthetic */ DownloadService a;
    private Context b;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = "ALo1H%lo-\fk`zc[j}e";
        r0 = r0.toCharArray();
        r1 = r0.length;
        r2 = 0;
        r3 = 1;
        if (r1 > r3) goto L_0x0028;
    L_0x000c:
        r3 = r0;
        r4 = r2;
        r7 = r1;
        r1 = r0;
        r0 = r7;
    L_0x0011:
        r6 = r1[r2];
        r5 = r4 % 5;
        switch(r5) {
            case 0: goto L_0x0036;
            case 1: goto L_0x0039;
            case 2: goto L_0x003b;
            case 3: goto L_0x003e;
            default: goto L_0x0018;
        };
    L_0x0018:
        r5 = 67;
    L_0x001a:
        r5 = r5 ^ r6;
        r5 = (char) r5;
        r1[r2] = r5;
        r2 = r4 + 1;
        if (r0 != 0) goto L_0x0026;
    L_0x0022:
        r1 = r3;
        r4 = r2;
        r2 = r0;
        goto L_0x0011;
    L_0x0026:
        r1 = r0;
        r0 = r3;
    L_0x0028:
        if (r1 > r2) goto L_0x000c;
    L_0x002a:
        r1 = new java.lang.String;
        r1.<init>(r0);
        r0 = r1.intern();
        z = r0;
        return;
    L_0x0036:
        r5 = 44;
        goto L_0x001a;
    L_0x0039:
        r5 = 5;
        goto L_0x001a;
    L_0x003b:
        r5 = 15;
        goto L_0x001a;
    L_0x003e:
        r5 = 14;
        goto L_0x001a;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.service.g.<clinit>():void");
    }

    public g(DownloadService downloadService, Context context) {
        this.a = downloadService;
        super(context.getMainLooper());
        this.b = context;
    }

    public final void handleMessage(Message message) {
        super.handleMessage(message);
        Toast.makeText(this.b, z, 1).show();
    }
}
