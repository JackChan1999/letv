package rrrrrr;

import com.immersion.aws.analytics.AnalyticsDataCollector;
import com.immersion.aws.analytics.AnalyticsDataPair;
import com.immersion.hapticmediasdk.HapticContentSDK;
import org.json.JSONObject;

public class ccrrcc extends AnalyticsDataCollector {
    public static int b043B043Bлл043Bл = 1;
    public static int b043Bллл043Bл = 39;
    public static int bл043Bлл043Bл;
    private String b0414ДДД04140414;
    public final /* synthetic */ HapticContentSDK bД0414ДД04140414;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ccrrcc(com.immersion.hapticmediasdk.HapticContentSDK r3, java.lang.String r4) {
        /*
        r2 = this;
        r0 = 0;
        r2.bД0414ДД04140414 = r3;
    L_0x0003:
        switch(r0) {
            case 0: goto L_0x000a;
            case 1: goto L_0x0003;
            default: goto L_0x0006;
        };
    L_0x0006:
        switch(r0) {
            case 0: goto L_0x000a;
            case 1: goto L_0x0003;
            default: goto L_0x0009;
        };
    L_0x0009:
        goto L_0x0006;
    L_0x000a:
        r0 = "customer_name";
        r2.<init>(r0);
        r0 = b043Bллл043Bл;
        r1 = b043B043Bлл043Bл;
        r0 = r0 + r1;
        r1 = b043Bллл043Bл;
        r0 = r0 * r1;
        r1 = bлл043Bл043Bл();
        r0 = r0 % r1;
        r1 = bл043Bлл043Bл;
        if (r0 == r1) goto L_0x0029;
    L_0x0020:
        r0 = b043Bл043Bл043Bл();
        b043Bллл043Bл = r0;
        r0 = 6;
        bл043Bлл043Bл = r0;
    L_0x0029:
        r2.b0414ДДД04140414 = r4;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.ccrrcc.<init>(com.immersion.hapticmediasdk.HapticContentSDK, java.lang.String):void");
    }

    public static int b043Bл043Bл043Bл() {
        return 44;
    }

    public static int bлл043Bл043Bл() {
        return 2;
    }

    public JSONObject getData() {
        try {
            try {
                String columnName = super.getColumnName();
                String str = this.b0414ДДД04140414;
                if (((b043Bллл043Bл + b043B043Bлл043Bл) * b043Bллл043Bл) % bлл043Bл043Bл() != bл043Bлл043Bл) {
                    b043Bллл043Bл = 70;
                    bл043Bлл043Bл = b043Bл043Bл043Bл();
                }
                return new AnalyticsDataPair(columnName, str).getJson();
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }
}
