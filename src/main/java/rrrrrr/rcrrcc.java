package rrrrrr;

import android.os.Build;
import com.immersion.aws.analytics.AnalyticsDataCollector;
import com.immersion.aws.analytics.AnalyticsDataPair;
import com.immersion.aws.analytics.DefaultAnalyticsCollectors;
import org.json.JSONObject;

public class rcrrcc extends AnalyticsDataCollector {
    public static int b043B043B043B043Bлл = 1;
    public static int bл043B043B043Bлл = 0;
    public static int bлллл043Bл = 2;
    private String b0414041404140414Д0414;
    public final /* synthetic */ DefaultAnalyticsCollectors bДДДД04140414;

    public rcrrcc(DefaultAnalyticsCollectors defaultAnalyticsCollectors) {
        this.bДДДД04140414 = defaultAnalyticsCollectors;
        super("model");
        String str = Build.MODEL;
        if (((b043Bл043B043Bлл() + b043B043B043B043Bлл) * b043Bл043B043Bлл()) % bлллл043Bл != bл043B043B043Bлл) {
            bл043B043B043Bлл = 53;
        }
        this.b0414041404140414Д0414 = str;
        while (true) {
            switch (null) {
                case null:
                    return;
                case 1:
                    break;
                default:
                    while (true) {
                        switch (null) {
                            case null:
                                return;
                            case 1:
                                break;
                            default:
                        }
                    }
            }
        }
    }

    public static int b043Bл043B043Bлл() {
        return 53;
    }

    public JSONObject getData() {
        try {
            int b043Bл043B043Bлл = b043Bл043B043Bлл();
            switch ((b043Bл043B043Bлл * (b043B043B043B043Bлл + b043Bл043B043Bлл)) % bлллл043Bл) {
                case 0:
                    break;
                default:
                    bл043B043B043Bлл = b043Bл043B043Bлл();
                    break;
            }
            try {
                return new AnalyticsDataPair(super.getColumnName(), this.b0414041404140414Д0414).getJson();
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }
}
