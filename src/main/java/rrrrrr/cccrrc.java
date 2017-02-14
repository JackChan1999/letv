package rrrrrr;

import com.immersion.aws.analytics.AnalyticsDataCollector;
import com.immersion.aws.analytics.AnalyticsDataPair;
import com.immersion.hapticmediasdk.MediaPlaybackSDK;
import org.json.JSONObject;

public class cccrrc extends AnalyticsDataCollector {
    public static int b0444044404440444ф0444 = 20;
    public static int b0444ф04440444ф0444 = 1;
    public static int bф044404440444ф0444 = 2;
    public static int bфф04440444ф0444;
    public final /* synthetic */ MediaPlaybackSDK b043D043Dн043Dн043D;
    private boolean bн043Dн043Dн043D;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public cccrrc(com.immersion.hapticmediasdk.MediaPlaybackSDK r4) {
        /*
        r3 = this;
        r2 = 0;
    L_0x0001:
        switch(r2) {
            case 0: goto L_0x0008;
            case 1: goto L_0x0001;
            default: goto L_0x0004;
        };
    L_0x0004:
        switch(r2) {
            case 0: goto L_0x0008;
            case 1: goto L_0x0001;
            default: goto L_0x0007;
        };
    L_0x0007:
        goto L_0x0004;
    L_0x0008:
        r0 = b04440444ф0444ф0444();
        r1 = b0444ф04440444ф0444;
        r0 = r0 + r1;
        r1 = b04440444ф0444ф0444();
        r0 = r0 * r1;
        r1 = bф044404440444ф0444;
        r0 = r0 % r1;
        r1 = bфф04440444ф0444;
        if (r0 == r1) goto L_0x001f;
    L_0x001b:
        r0 = 89;
        bфф04440444ф0444 = r0;
    L_0x001f:
        r3.b043D043Dн043Dн043D = r4;
        r0 = "is_Streaming";
        r3.<init>(r0);
        r3.bн043Dн043Dн043D = r2;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.cccrrc.<init>(com.immersion.hapticmediasdk.MediaPlaybackSDK):void");
    }

    public static int b04440444ф0444ф0444() {
        return 53;
    }

    public JSONObject getData() {
        try {
            try {
                AnalyticsDataPair analyticsDataPair = new AnalyticsDataPair(super.getColumnName(), Boolean.toString(this.bн043Dн043Dн043D));
                if (((b0444044404440444ф0444 + b0444ф04440444ф0444) * b0444044404440444ф0444) % bф044404440444ф0444 != bфф04440444ф0444) {
                    b0444044404440444ф0444 = 44;
                    bфф04440444ф0444 = 97;
                }
                return analyticsDataPair.getJson();
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public void setIsPlayingLocally() {
        int i = b0444044404440444ф0444;
        switch ((i * (b0444ф04440444ф0444 + i)) % bф044404440444ф0444) {
            case 0:
                break;
            default:
                b0444044404440444ф0444 = 95;
                bфф04440444ф0444 = 35;
                break;
        }
        try {
            this.bн043Dн043Dн043D = false;
        } catch (Exception e) {
            throw e;
        }
    }

    public void setIsStreaming() {
        this.bн043Dн043Dн043D = true;
        int i = b0444044404440444ф0444;
        switch ((i * (b0444ф04440444ф0444 + i)) % bф044404440444ф0444) {
            case 0:
                return;
            default:
                b0444044404440444ф0444 = b04440444ф0444ф0444();
                bфф04440444ф0444 = b04440444ф0444ф0444();
                return;
        }
    }
}
