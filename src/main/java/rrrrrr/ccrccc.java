package rrrrrr;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import com.immersion.Log;
import com.immersion.aws.analytics.AnalyticsDataCollector;
import com.immersion.aws.analytics.DefaultAnalyticsCollectors;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ccrccc extends AnalyticsDataCollector {
    public static int b041E041EОО041EО = 1;
    public static int b041EООО041EО = 6;
    public static int bО041EОО041EО = 0;
    public static int bОО041EО041EО = 2;
    private String b044C044C044Cььь;
    private String b044Cь044Cььь;
    public final /* synthetic */ DefaultAnalyticsCollectors b044Cьь044Cьь;
    private String bь044C044Cььь;
    private String bьь044Cььь;
    private Context bььь044Cьь;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ccrccc(com.immersion.aws.analytics.DefaultAnalyticsCollectors r4, android.content.Context r5) {
        /*
        r3 = this;
        r2 = 0;
        r0 = b041EООО041EО;
        r1 = b041E041EОО041EО;
        r0 = r0 + r1;
        r1 = b041EООО041EО;
        r0 = r0 * r1;
        r1 = bОО041EО041EО;
        r0 = r0 % r1;
        r1 = bО041EОО041EО;
        if (r0 == r1) goto L_0x001a;
    L_0x0010:
        r0 = b041EО041EО041EО();
        b041EООО041EО = r0;
        r0 = 58;
        bО041EОО041EО = r0;
    L_0x001a:
        r3.b044Cьь044Cьь = r4;
    L_0x001c:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x0025;
            case 1: goto L_0x001c;
            default: goto L_0x0020;
        };
    L_0x0020:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x001c;
            case 1: goto L_0x0025;
            default: goto L_0x0024;
        };
    L_0x0024:
        goto L_0x0020;
    L_0x0025:
        r0 = "unique_device_id";
        r3.<init>(r0);
        r3.bьь044Cььь = r2;
        r3.b044Cь044Cььь = r2;
        r3.bь044C044Cььь = r2;
        r3.b044C044C044Cььь = r2;
        r3.bььь044Cьь = r5;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.ccrccc.<init>(com.immersion.aws.analytics.DefaultAnalyticsCollectors, android.content.Context):void");
    }

    public static int b041E041E041EО041EО() {
        return 2;
    }

    public static int b041EО041EО041EО() {
        return 24;
    }

    private void b04390439й043904390439() {
        try {
            ContentResolver contentResolver = this.bььь044Cьь.getContentResolver();
            if (((b041EО041EО041EО() + b041E041EОО041EО) * b041EО041EО041EО()) % bОО041EО041EО != bО041EОО041EО) {
                b041EООО041EО = b041EО041EО041EО();
                bО041EОО041EО = b041EО041EО041EО();
            }
            try {
                this.bьь044Cььь = Secure.getString(contentResolver, "android_id");
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    private void b0439й0439043904390439() {
        String str = Build.SERIAL;
        if (((b041EООО041EО + b041E041EОО041EО) * b041EООО041EО) % bОО041EО041EО != bО041EОО041EО) {
            b041EООО041EО = b041EО041EО041EО();
            bО041EОО041EО = b041EО041EО041EО();
        }
        this.bь044C044Cььь = str;
    }

    public static int bО041E041EО041EО() {
        return 0;
    }

    public static int bООО041E041EО() {
        return 1;
    }

    private void bй04390439043904390439() {
        String str = "SHA-256";
        String str2 = "UTF-8";
        StringBuilder stringBuilder = new StringBuilder();
        try {
            MessageDigest instance = MessageDigest.getInstance(str);
            if (this.bьь044Cььь != null) {
                stringBuilder.append(this.bьь044Cььь);
            }
            if (this.b044Cь044Cььь != null) {
                stringBuilder.append(this.b044Cь044Cььь);
            }
            if (this.bь044C044Cььь != null) {
                stringBuilder.append(this.bь044C044Cььь);
            }
            byte[] bytes = stringBuilder.toString().getBytes(Charset.forName(str2));
            instance.update(bytes, 0, bytes.length);
            byte[] digest = instance.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                stringBuffer.append(Integer.toString((b & 255) + 256, 16).substring(1));
            }
            this.b044C044C044Cььь = stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            Log.e("DefaultAnalyticsCollectors", "Failed to compute uuid: " + e.toString());
        }
    }

    private void bйй0439043904390439() {
        if (this.bььь044Cьь.getPackageManager().checkPermission("android.permission.READ_PHONE_STATE", this.bььь044Cьь.getPackageName()) == 0) {
            TelephonyManager telephonyManager = (TelephonyManager) this.bььь044Cьь.getSystemService("phone");
            if (((b041EО041EО041EО() + b041E041EОО041EО) * b041EО041EО041EО()) % bОО041EО041EО != bО041EОО041EО) {
                b041EООО041EО = 81;
                bО041EОО041EО = b041EО041EО041EО();
            }
            this.b044Cь044Cььь = telephonyManager.getDeviceId();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.json.JSONObject getData() {
        /*
        r5 = this;
        r5.b04390439й043904390439();
        r5.bйй0439043904390439();
        r5.b0439й0439043904390439();
        r5.bй04390439043904390439();
        r0 = new com.immersion.aws.analytics.AnalyticsDataPair;
        r1 = super.getColumnName();
        r2 = r5.b044C044C044Cььь;
        r3 = b041EООО041EО;
        r4 = bООО041E041EО();
        r3 = r3 + r4;
        r4 = b041EООО041EО;
        r3 = r3 * r4;
        r4 = b041E041E041EО041EО();
        r3 = r3 % r4;
        r4 = bО041E041EО041EО();
        if (r3 == r4) goto L_0x0033;
    L_0x0029:
        r3 = 91;
        b041EООО041EО = r3;
        r3 = b041EО041EО041EО();
        bО041EОО041EО = r3;
    L_0x0033:
        r3 = 1;
        switch(r3) {
            case 0: goto L_0x0033;
            case 1: goto L_0x003c;
            default: goto L_0x0037;
        };
    L_0x0037:
        r3 = 0;
        switch(r3) {
            case 0: goto L_0x003c;
            case 1: goto L_0x0033;
            default: goto L_0x003b;
        };
    L_0x003b:
        goto L_0x0037;
    L_0x003c:
        r0.<init>(r1, r2);
        r0 = r0.getJson();
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.ccrccc.getData():org.json.JSONObject");
    }
}
