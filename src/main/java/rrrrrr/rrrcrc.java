package rrrrrr;

import com.immersion.aws.analytics.AnalyticsDataCollector;
import com.immersion.aws.analytics.AnalyticsDataPair;
import com.immersion.aws.analytics.DefaultAnalyticsCollectors;
import org.json.JSONObject;

public class rrrcrc extends AnalyticsDataCollector {
    public static int b0425ХХХХХ = 0;
    public static int b0444ф0444044404440444 = 1;
    public static int bф04440444044404440444 = 2;
    private int b043D043D043D043Dн043D;
    public final /* synthetic */ DefaultAnalyticsCollectors bнннн043D043D;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public rrrcrc(com.immersion.aws.analytics.DefaultAnalyticsCollectors r3) {
        /*
        r2 = this;
        r0 = 0;
    L_0x0001:
        switch(r0) {
            case 0: goto L_0x0008;
            case 1: goto L_0x0001;
            default: goto L_0x0004;
        };
    L_0x0004:
        switch(r0) {
            case 0: goto L_0x0008;
            case 1: goto L_0x0001;
            default: goto L_0x0007;
        };
    L_0x0007:
        goto L_0x0004;
    L_0x0008:
        r2.bнннн043D043D = r3;
        r0 = "android_sdk_version";
        r2.<init>(r0);
        r0 = android.os.Build.VERSION.SDK_INT;
        r2.b043D043D043D043Dн043D = r0;
        r0 = bфф0444044404440444();
        r1 = b0444ф0444044404440444;
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = bф04440444044404440444;
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x0025;
            default: goto L_0x0021;
        };
    L_0x0021:
        r0 = 43;
        b0444ф0444044404440444 = r0;
    L_0x0025:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.rrrcrc.<init>(com.immersion.aws.analytics.DefaultAnalyticsCollectors):void");
    }

    public static int bфф0444044404440444() {
        return 76;
    }

    public JSONObject getData() {
        String columnName = super.getColumnName();
        int i = this.b043D043D043D043Dн043D;
        if (((bфф0444044404440444() + b0444ф0444044404440444) * bфф0444044404440444()) % bф04440444044404440444 != b0425ХХХХХ) {
            b0425ХХХХХ = 87;
        }
        return new AnalyticsDataPair(columnName, String.valueOf(i)).getJson();
    }
}
