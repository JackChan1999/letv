package rrrrrr;

import com.immersion.aws.analytics.AnalyticsDataCollector;
import com.immersion.aws.analytics.AnalyticsDataPair;
import com.immersion.hapticmediasdk.HapticContentSDK;
import org.json.JSONObject;

public class cccrcr extends AnalyticsDataCollector {
    private static final String b042D042DЭ042DЭЭ = "NOT_MUTED";
    public static int b04460446ц04460446ц = 1;
    public static int b0446цц04460446ц = 85;
    private static final String bЭ042DЭ042DЭЭ = "MUTED";
    public static int bц0446ц04460446ц = 0;
    public static int bцц044604460446ц = 2;
    public final /* synthetic */ HapticContentSDK b042DЭ042D042DЭЭ;
    private boolean bЭЭ042D042DЭЭ;

    public cccrcr(HapticContentSDK hapticContentSDK) {
        int i = 4;
        this.b042DЭ042D042DЭЭ = hapticContentSDK;
        while (true) {
            try {
                i /= 0;
            } catch (Exception e) {
                super("haptics_muted");
                this.bЭЭ042D042DЭЭ = false;
                return;
            }
        }
    }

    public static int b0446ц044604460446ц() {
        return 3;
    }

    public JSONObject getData() {
        if (!this.bЭЭ042D042DЭЭ) {
            return new AnalyticsDataPair(super.getColumnName(), b042D042DЭ042DЭЭ).getJson();
        }
        JSONObject json = new AnalyticsDataPair(super.getColumnName(), bЭ042DЭ042DЭЭ).getJson();
        if (((b0446цц04460446ц + b04460446ц04460446ц) * b0446цц04460446ц) % bцц044604460446ц == bц0446ц04460446ц) {
            return json;
        }
        b0446цц04460446ц = b0446ц044604460446ц();
        bц0446ц04460446ц = 71;
        return json;
    }

    public void setMuted() {
        this.bЭЭ042D042DЭЭ = true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setUnMuted() {
        /*
        r3 = this;
        r2 = 0;
        r0 = b0446цц04460446ц;
        r1 = b04460446ц04460446ц;
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = bцц044604460446ц;
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x0017;
            default: goto L_0x000d;
        };
    L_0x000d:
        r0 = b0446ц044604460446ц();
        b0446цц04460446ц = r0;
        r0 = 82;
        bц0446ц04460446ц = r0;
    L_0x0017:
        switch(r2) {
            case 0: goto L_0x001f;
            case 1: goto L_0x0017;
            default: goto L_0x001a;
        };
    L_0x001a:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x0017;
            case 1: goto L_0x001f;
            default: goto L_0x001e;
        };
    L_0x001e:
        goto L_0x001a;
    L_0x001f:
        r3.bЭЭ042D042DЭЭ = r2;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.cccrcr.setUnMuted():void");
    }
}
