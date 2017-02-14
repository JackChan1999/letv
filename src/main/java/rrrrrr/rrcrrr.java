package rrrrrr;

import com.immersion.aws.analytics.AnalyticsDataCollector;
import com.immersion.aws.analytics.AnalyticsDataPair;
import com.immersion.hapticmediasdk.controllers.HapticPlaybackThread;
import org.json.JSONObject;

public class rrcrrr extends AnalyticsDataCollector {
    public static int b04150415ЕЕЕЕ = 0;
    public static int b0415ЕЕЕЕЕ = 2;
    public static int b0421С0421042104210421 = 5;
    public static int bС04210421042104210421 = 1;
    public final /* synthetic */ HapticPlaybackThread bООО041E041E041E;

    public rrcrrr(HapticPlaybackThread hapticPlaybackThread) {
        try {
            this.bООО041E041E041E = hapticPlaybackThread;
            try {
                String str = "percentage_played_back";
                int i = b0421С0421042104210421;
                switch ((i * (bС04210421042104210421 + i)) % b0415ЕЕЕЕЕ) {
                    case 0:
                        break;
                    default:
                        b0421С0421042104210421 = 22;
                        bС04210421042104210421 = bЕ0415ЕЕЕЕ();
                        break;
                }
                super(str);
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public static int bЕ0415ЕЕЕЕ() {
        return 49;
    }

    public JSONObject getData() {
        return new AnalyticsDataPair(super.getColumnName(), "INVALID HAPT File").getJson();
    }
}
