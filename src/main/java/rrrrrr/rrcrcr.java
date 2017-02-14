package rrrrrr;

import com.immersion.hapticmediasdk.MediaPlaybackSDK;
import java.net.MalformedURLException;
import java.net.URL;

public class rrcrcr implements Runnable {
    private static final String b041B041B041BЛ041B041B = "ValidateURL";
    public static int b042704270427Ч04270427 = 2;
    public static int b0427Ч0427Ч04270427 = 83;
    public static int b0427ЧЧ042704270427 = 1;
    private static final int bЛЛЛ041B041B041B = 5000;
    private URL b041BЛЛ041B041B041B;
    public final /* synthetic */ MediaPlaybackSDK bЛ041BЛ041B041B041B;

    public rrcrcr(MediaPlaybackSDK mediaPlaybackSDK, String str) throws MalformedURLException {
        try {
            this.bЛ041BЛ041B041B041B = mediaPlaybackSDK;
            int i = b0427Ч0427Ч04270427;
            switch ((i * (bЧ04270427Ч04270427() + i)) % b042704270427Ч04270427) {
                case 0:
                    break;
                default:
                    b0427Ч0427Ч04270427 = 5;
                    b042704270427Ч04270427 = bЧЧЧ042704270427();
                    break;
            }
            try {
                this.b041BЛЛ041B041B041B = new URL(str);
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public static int bЧ04270427Ч04270427() {
        return 1;
    }

    public static int bЧЧЧ042704270427() {
        return 27;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
        r4 = this;
        r3 = 1;
        r1 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        r0 = b0427Ч0427Ч04270427;
        r2 = b0427ЧЧ042704270427;
        r2 = r2 + r0;
        r0 = r0 * r2;
        r2 = b042704270427Ч04270427;
        r0 = r0 % r2;
        switch(r0) {
            case 0: goto L_0x001b;
            default: goto L_0x000f;
        };
    L_0x000f:
        r0 = bЧЧЧ042704270427();
        b0427Ч0427Ч04270427 = r0;
        r0 = bЧЧЧ042704270427();
        b0427ЧЧ042704270427 = r0;
    L_0x001b:
        r0 = r4.b041BЛЛ041B041B041B;	 Catch:{ Exception -> 0x0051 }
        r0 = r0.openConnection();	 Catch:{ Exception -> 0x0051 }
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ Exception -> 0x0051 }
        r2 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
        r0.setConnectTimeout(r2);	 Catch:{ Exception -> 0x0051 }
        r2 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
        r0.setReadTimeout(r2);	 Catch:{ Exception -> 0x0051 }
        r2 = 0;
        r0.setUseCaches(r2);	 Catch:{ Exception -> 0x0051 }
        r2 = "HEAD";
        r0.setRequestMethod(r2);	 Catch:{ Exception -> 0x0051 }
        r0 = r0.getResponseCode();	 Catch:{ Exception -> 0x0051 }
    L_0x003a:
        r1 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r0 >= r1) goto L_0x0050;
    L_0x003e:
        r1 = 399; // 0x18f float:5.59E-43 double:1.97E-321;
    L_0x0040:
        switch(r3) {
            case 0: goto L_0x0040;
            case 1: goto L_0x0047;
            default: goto L_0x0043;
        };
    L_0x0043:
        switch(r3) {
            case 0: goto L_0x0040;
            case 1: goto L_0x0047;
            default: goto L_0x0046;
        };
    L_0x0046:
        goto L_0x0043;
    L_0x0047:
        if (r0 <= r1) goto L_0x0050;
    L_0x0049:
        r0 = "ValidateURL";
        r1 = "Error: Could not access hapt file";
        com.immersion.hapticmediasdk.utils.Log.e(r0, r1);
    L_0x0050:
        return;
    L_0x0051:
        r0 = move-exception;
        r0 = "ValidateURL";
        r2 = "Warning: Could not access hapt file url in a timely manner.";
        com.immersion.hapticmediasdk.utils.Log.w(r0, r2);
        r0 = r1;
        goto L_0x003a;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.rrcrcr.run():void");
    }
}
