package rrrrrr;

import com.immersion.aws.analytics.AnalyticsDataCollector;
import com.immersion.aws.analytics.AnalyticsDataPair;
import com.immersion.aws.analytics.DefaultAnalyticsCollectors;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import org.json.JSONObject;

public class rrccrr extends AnalyticsDataCollector {
    public static int b04270427Ч0427ЧЧ = 2;
    public static int bЧ0427Ч0427ЧЧ = 1;
    public static int bЧЧ04270427ЧЧ = 32;
    private Calendar b041B041B041B041B041BЛ;
    private String b041B041BЛ041B041BЛ;
    private String b041BЛ041B041B041BЛ;
    private SimpleDateFormat bЛ041B041B041B041BЛ;
    private String bЛЛ041B041B041BЛ;
    public final /* synthetic */ DefaultAnalyticsCollectors bЛЛЛЛЛ041B;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public rrccrr(com.immersion.aws.analytics.DefaultAnalyticsCollectors r3) {
        /*
        r2 = this;
        r2.bЛЛЛЛЛ041B = r3;
        r0 = b0427ЧЧ0427ЧЧ();
        r1 = bЧ0427Ч0427ЧЧ;
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = b04270427Ч0427ЧЧ;
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x0014;
            default: goto L_0x0010;
        };
    L_0x0010:
        r0 = 28;
        bЧ0427Ч0427ЧЧ = r0;
    L_0x0014:
        r0 = "date";
        r2.<init>(r0);
        r0 = "yyyyMMdd HH:mm:ss";
        r2.b041B041BЛ041B041BЛ = r0;
        r0 = "UTC";
        r2.bЛЛ041B041B041BЛ = r0;
    L_0x0022:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x0022;
            case 1: goto L_0x002b;
            default: goto L_0x0026;
        };
    L_0x0026:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x002b;
            case 1: goto L_0x0022;
            default: goto L_0x002a;
        };
    L_0x002a:
        goto L_0x0026;
    L_0x002b:
        r0 = java.util.Calendar.getInstance();
        r2.b041B041B041B041B041BЛ = r0;
        r0 = new java.text.SimpleDateFormat;
        r1 = r2.b041B041BЛ041B041BЛ;
        r0.<init>(r1);
        r2.bЛ041B041B041B041BЛ = r0;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.rrccrr.<init>(com.immersion.aws.analytics.DefaultAnalyticsCollectors):void");
    }

    public static int b0427ЧЧ0427ЧЧ() {
        return 79;
    }

    public JSONObject getData() {
        this.bЛ041B041B041B041BЛ.setTimeZone(TimeZone.getTimeZone(this.bЛЛ041B041B041BЛ));
        this.b041BЛ041B041B041BЛ = this.bЛ041B041B041B041BЛ.format(this.b041B041B041B041B041BЛ.getTime());
        String columnName = super.getColumnName();
        String str = this.b041BЛ041B041B041BЛ;
        int i = bЧЧ04270427ЧЧ;
        switch ((i * (bЧ0427Ч0427ЧЧ + i)) % b04270427Ч0427ЧЧ) {
            case 0:
                break;
            default:
                bЧЧ04270427ЧЧ = b0427ЧЧ0427ЧЧ();
                bЧ0427Ч0427ЧЧ = b0427ЧЧ0427ЧЧ();
                break;
        }
        return new AnalyticsDataPair(columnName, str).getJson();
    }
}
