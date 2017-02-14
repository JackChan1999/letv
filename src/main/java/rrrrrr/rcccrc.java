package rrrrrr;

import com.immersion.Log;
import com.letv.core.messagebus.config.LeMessageIds;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.util.Date;

public class rcccrc extends rcrccr {
    public static int b04110411Б04110411Б = 2;
    public static int b0411ББ04110411Б = 0;
    private static final String b0414Д0414Д0414Д = "RemotePolicyConnection";
    public static int bБ0411Б04110411Б = 1;
    public static int bБББ04110411Б = 25;

    public rcccrc(File file, URLConnection uRLConnection) {
        try {
            super(file);
            try {
                this.mConnection = uRLConnection;
                if (((bБББ04110411Б + bБ0411Б04110411Б) * bБББ04110411Б) % b04110411Б04110411Б != b0411ББ04110411Б) {
                    bБББ04110411Б = bББ041104110411Б();
                    b0411ББ04110411Б = bББ041104110411Б();
                }
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public static int b0411Б041104110411Б() {
        return 2;
    }

    public static int bББ041104110411Б() {
        return 69;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b04490449щщ0449щ() {
        /*
        r2 = this;
        r0 = r2.mConnection;
        if (r0 == 0) goto L_0x0034;
    L_0x0004:
        r0 = r2.mConnection;
        r0 = (java.net.HttpURLConnection) r0;
    L_0x0008:
        r1 = 1;
        switch(r1) {
            case 0: goto L_0x0008;
            case 1: goto L_0x0011;
            default: goto L_0x000c;
        };
    L_0x000c:
        r1 = 0;
        switch(r1) {
            case 0: goto L_0x0011;
            case 1: goto L_0x0008;
            default: goto L_0x0010;
        };
    L_0x0010:
        goto L_0x000c;
    L_0x0011:
        r0.disconnect();
        r0 = bБББ04110411Б;
        r1 = bБ0411Б04110411Б;
        r0 = r0 + r1;
        r1 = bБББ04110411Б;
        r0 = r0 * r1;
        r1 = b0411Б041104110411Б();
        r0 = r0 % r1;
        r1 = b0411ББ04110411Б;
        if (r0 == r1) goto L_0x002d;
    L_0x0025:
        r0 = 74;
        bБББ04110411Б = r0;
        r0 = 43;
        b0411ББ04110411Б = r0;
    L_0x002d:
        r0 = "RemotePolicyConnection";
        r1 = "Connection Terminated";
        com.immersion.Log.d(r0, r1);
    L_0x0034:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.rcccrc.b04490449щщ0449щ():void");
    }

    public boolean bщ0449щщ0449щ() {
        int i = LeMessageIds.MSG_FLOAT_BALL_REQUEST_DATA;
        boolean z = false;
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) this.mConnection;
            try {
                i = httpURLConnection.getResponseCode();
            } catch (IOException e) {
                Log.e(b0414Д0414Д0414Д, "Failed to get response code");
            }
            if (i == 200) {
                try {
                    boolean after = new Date(httpURLConnection.getLastModified()).after(new Date(this.mTargetLocation.lastModified()));
                    Log.d(b0414Д0414Д0414Д, "Connection Established.");
                    z = after;
                } catch (Exception e2) {
                    throw e2;
                }
            }
            String str = b0414Д0414Д0414Д;
            if (((bБББ04110411Б + bБ0411Б04110411Б) * bБББ04110411Б) % b04110411Б04110411Б != b0411ББ04110411Б) {
                bБББ04110411Б = 26;
                b0411ББ04110411Б = 90;
            }
            Log.d(str, "Do we need new pm file? " + (z ? "Yes!" : "No."));
            return z;
        } catch (Exception e22) {
            throw e22;
        }
    }
}
