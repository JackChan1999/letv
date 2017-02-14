package com.immersion.aws.analytics;

import org.json.JSONObject;

public abstract class AnalyticsDataCollector {
    public static int b0417ЗЗ0417З0417 = 2;
    public static int bЗЗЗ0417З0417 = 1;
    private String b043Dн043Dн043Dн;

    public AnalyticsDataCollector(String str) {
        try {
            if (str == null) {
                try {
                    boolean equals = str.equals("");
                    int b041704170417ЗЗ0417 = b041704170417ЗЗ0417();
                    switch ((b041704170417ЗЗ0417 * (bЗЗЗ0417З0417 + b041704170417ЗЗ0417)) % b0417ЗЗ0417З0417) {
                        case 0:
                            break;
                        default:
                            bЗЗЗ0417З0417 = b041704170417ЗЗ0417();
                            break;
                    }
                    if (equals) {
                        return;
                    }
                } catch (Exception e) {
                    throw e;
                }
            }
            this.b043Dн043Dн043Dн = str;
        } catch (Exception e2) {
            throw e2;
        }
    }

    public static int b041704170417ЗЗ0417() {
        return 5;
    }

    public static int bЗ0417З0417З0417() {
        return 0;
    }

    public String getColumnName() {
        if (((b041704170417ЗЗ0417() + bЗЗЗ0417З0417) * b041704170417ЗЗ0417()) % b0417ЗЗ0417З0417 != bЗ0417З0417З0417()) {
            bЗЗЗ0417З0417 = b041704170417ЗЗ0417();
        }
        try {
            return this.b043Dн043Dн043Dн;
        } catch (Exception e) {
            throw e;
        }
    }

    public abstract JSONObject getData();
}
