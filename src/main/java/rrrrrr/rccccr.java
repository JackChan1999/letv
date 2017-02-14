package rrrrrr;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import com.immersion.aws.analytics.AnalyticsDataCollector;
import com.immersion.aws.analytics.AnalyticsDataPair;
import com.immersion.aws.analytics.DefaultAnalyticsCollectors;
import org.json.JSONObject;

public class rccccr extends AnalyticsDataCollector {
    public static int b04170417ЗЗЗЗ = 2;
    public static int b0417З0417ЗЗЗ = 0;
    public static int b0417ЗЗЗЗЗ = 49;
    public static int bЗ0417ЗЗЗЗ = 1;
    public final /* synthetic */ DefaultAnalyticsCollectors b043D043Dнннн;
    private String b043Dннннн;
    private Context bн043Dнннн;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public rccccr(com.immersion.aws.analytics.DefaultAnalyticsCollectors r4, android.content.Context r5) {
        /*
        r3 = this;
        r2 = 0;
        r0 = b0417ЗЗЗЗЗ;
        r1 = bЗ0417ЗЗЗЗ;
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = b04170417ЗЗЗЗ;
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x0017;
            default: goto L_0x000d;
        };
    L_0x000d:
        r0 = 15;
        b0417ЗЗЗЗЗ = r0;
        r0 = bЗЗ0417ЗЗЗ();
        bЗ0417ЗЗЗЗ = r0;
    L_0x0017:
        switch(r2) {
            case 0: goto L_0x001e;
            case 1: goto L_0x0017;
            default: goto L_0x001a;
        };
    L_0x001a:
        switch(r2) {
            case 0: goto L_0x001e;
            case 1: goto L_0x0017;
            default: goto L_0x001d;
        };
    L_0x001d:
        goto L_0x001a;
    L_0x001e:
        r3.b043D043Dнннн = r4;
        r0 = "application_name";
        r3.<init>(r0);
        r3.bн043Dнннн = r5;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.rccccr.<init>(com.immersion.aws.analytics.DefaultAnalyticsCollectors, android.content.Context):void");
    }

    private void b0449щ0449щ0449щ() {
        PackageManager packageManager = this.bн043Dнннн.getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(this.bн043Dнннн.getPackageName(), 0);
            int bЗЗ0417ЗЗЗ = bЗЗ0417ЗЗЗ();
            switch ((bЗЗ0417ЗЗЗ * (bЗ0417ЗЗЗЗ + bЗЗ0417ЗЗЗ)) % b04170417ЗЗЗЗ) {
                case 0:
                    break;
                default:
                    b0417ЗЗЗЗЗ = 73;
                    bЗ0417ЗЗЗЗ = 43;
                    break;
            }
            this.b043Dннннн = applicationInfo.loadLabel(packageManager).toString();
        } catch (NameNotFoundException e) {
            this.b043Dннннн = "NOT_FOUND";
        }
    }

    public static int bЗЗ0417ЗЗЗ() {
        return 75;
    }

    public JSONObject getData() {
        try {
            b0449щ0449щ0449щ();
            String columnName = super.getColumnName();
            if (((b0417ЗЗЗЗЗ + bЗ0417ЗЗЗЗ) * b0417ЗЗЗЗЗ) % b04170417ЗЗЗЗ != b0417З0417ЗЗЗ) {
                b0417ЗЗЗЗЗ = 90;
                b0417З0417ЗЗЗ = bЗЗ0417ЗЗЗ();
            }
            try {
                return new AnalyticsDataPair(columnName, this.b043Dннннн).getJson();
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }
}
