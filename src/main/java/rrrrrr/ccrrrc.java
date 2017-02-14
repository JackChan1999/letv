package rrrrrr;

import com.immersion.aws.analytics.AnalyticsDataCollector;
import com.immersion.aws.analytics.AnalyticsDataPair;
import com.immersion.aws.analytics.DefaultAnalyticsCollectors;
import org.json.JSONObject;

public class ccrrrc extends AnalyticsDataCollector {
    public static int b04440444фффф = 1;
    public static int bф0444фффф = 58;
    public static int bфф0444ффф = 2;
    public final /* synthetic */ DefaultAnalyticsCollectors b043Dнннн043D;
    private String bннннн043D;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ccrrrc(com.immersion.aws.analytics.DefaultAnalyticsCollectors r4) {
        /*
        r3 = this;
        r2 = 1;
        r0 = bф0444фффф;
        r1 = b04440444фффф;
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = bфф0444ффф;
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x0019;
            default: goto L_0x000d;
        };
    L_0x000d:
        r0 = b0444ф0444ффф();
        bф0444фффф = r0;
        r0 = b0444ф0444ффф();
        b04440444фффф = r0;
    L_0x0019:
        switch(r2) {
            case 0: goto L_0x0019;
            case 1: goto L_0x0020;
            default: goto L_0x001c;
        };
    L_0x001c:
        switch(r2) {
            case 0: goto L_0x0019;
            case 1: goto L_0x0020;
            default: goto L_0x001f;
        };
    L_0x001f:
        goto L_0x001c;
    L_0x0020:
        r3.b043Dнннн043D = r4;
        r0 = "manufacturer";
        r3.<init>(r0);
        r0 = android.os.Build.MANUFACTURER;
        r3.bннннн043D = r0;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.ccrrrc.<init>(com.immersion.aws.analytics.DefaultAnalyticsCollectors):void");
    }

    public static int b0444ф0444ффф() {
        return 56;
    }

    public JSONObject getData() {
        try {
            String columnName = super.getColumnName();
            try {
                String str = this.bннннн043D;
                int i = bф0444фффф;
                switch ((i * (b04440444фффф + i)) % bфф0444ффф) {
                    case 0:
                        break;
                    default:
                        bф0444фффф = b0444ф0444ффф();
                        b04440444фффф = b0444ф0444ффф();
                        break;
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
