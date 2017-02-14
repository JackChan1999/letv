package rrrrrr;

import com.immersion.aws.analytics.AnalyticsDataCollector;
import com.immersion.aws.analytics.AnalyticsDataPair;
import com.immersion.hapticmediasdk.HapticContentSDK;
import com.immersion.hapticmediasdk.HapticContentSDKFactory;
import org.json.JSONObject;

public class ccccrc extends AnalyticsDataCollector {
    public static int b0411Б04110411Б0411 = 89;
    public static int b0411БББ04110411 = 0;
    public static int bБ041104110411Б0411 = 1;
    public static int bБ0411ББ04110411 = 2;
    private String b041404140414Д0414Д;
    public final /* synthetic */ HapticContentSDK bДДД04140414Д;

    public ccccrc(HapticContentSDK hapticContentSDK) {
        int i = b0411Б04110411Б0411;
        switch ((i * (bБ041104110411Б0411 + i)) % b0411041104110411Б0411()) {
            case 0:
                break;
            default:
                b0411Б04110411Б0411 = bББББ04110411();
                bБ041104110411Б0411 = bББББ04110411();
                break;
        }
        this.bДДД04140414Д = hapticContentSDK;
        super("calling_package_name");
    }

    public static int b0411041104110411Б0411() {
        return 2;
    }

    public static int bББББ04110411() {
        return 21;
    }

    public void determineCallingPackage() {
        try {
            int length = Thread.currentThread().getStackTrace().length;
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            int i = 0;
            while (i < length) {
                try {
                    boolean equals = stackTrace[i].getClassName().equals(HapticContentSDKFactory.class.getName());
                    if (((b0411Б04110411Б0411 + bБ041104110411Б0411) * b0411Б04110411Б0411) % b0411041104110411Б0411() != b0411БББ04110411) {
                        b0411Б04110411Б0411 = 74;
                        b0411БББ04110411 = 21;
                    }
                    if (equals) {
                        i++;
                        break;
                    }
                    i++;
                } catch (Exception e) {
                    throw e;
                }
            }
            this.b041404140414Д0414Д = stackTrace[i].getClassName();
        } catch (Exception e2) {
            throw e2;
        }
    }

    public JSONObject getData() {
        return new AnalyticsDataPair(super.getColumnName(), this.b041404140414Д0414Д).getJson();
    }
}
