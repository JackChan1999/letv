package com.immersion.aws.analytics;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import rrrrrr.ccrccc;
import rrrrrr.ccrrrc;
import rrrrrr.rccccr;
import rrrrrr.rcrrcc;
import rrrrrr.rrccrr;
import rrrrrr.rrrcrc;

public class DefaultAnalyticsCollectors {
    private static final String b043D043D043Dнн043D = "DefaultAnalyticsCollectors";
    public static int b04440444ф04440444ф = 0;
    public static int b0444ф044404440444ф = 2;
    private static final String bн043D043Dнн043D = "NOT_FOUND";
    public static int bф0444ф04440444ф = 98;
    public static int bфф044404440444ф = 1;

    public private class ActuatorTypeCollector extends AnalyticsDataCollector {
        public static int b04150415Е0415ЕЕ = 18;
        public static int b0415Е04150415ЕЕ = 1;
        public static int bЕ041504150415ЕЕ = 2;
        public static int bЕЕ04150415ЕЕ;
        public final /* synthetic */ DefaultAnalyticsCollectors b041E041EО041E041E041E;
        private String bО041EО041E041E041E;

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public ActuatorTypeCollector(com.immersion.aws.analytics.DefaultAnalyticsCollectors r4) {
            /*
            r3 = this;
            r2 = 1;
            r3.b041E041EО041E041E041E = r4;
            r0 = "actuator_type";
            r3.<init>(r0);
            r0 = b04150415Е0415ЕЕ;
            r1 = b0415Е04150415ЕЕ;
            r0 = r0 + r1;
            r1 = b04150415Е0415ЕЕ;
            r0 = r0 * r1;
            r1 = bЕ041504150415ЕЕ;
            r0 = r0 % r1;
            r1 = bЕЕ04150415ЕЕ;
            if (r0 == r1) goto L_0x001f;
        L_0x0017:
            r0 = 46;
            b04150415Е0415ЕЕ = r0;
            r0 = 44;
            bЕЕ04150415ЕЕ = r0;
        L_0x001f:
            switch(r2) {
                case 0: goto L_0x001f;
                case 1: goto L_0x0026;
                default: goto L_0x0022;
            };
        L_0x0022:
            switch(r2) {
                case 0: goto L_0x001f;
                case 1: goto L_0x0026;
                default: goto L_0x0025;
            };
        L_0x0025:
            goto L_0x0022;
        L_0x0026:
            r0 = r3.getActuatorTypeNative();
            r3.bО041EО041E041E041E = r0;
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.immersion.aws.analytics.DefaultAnalyticsCollectors.ActuatorTypeCollector.<init>(com.immersion.aws.analytics.DefaultAnalyticsCollectors):void");
        }

        public static int b0415041504150415ЕЕ() {
            return 28;
        }

        private native String getActuatorTypeNative();

        public JSONObject getData() {
            try {
                try {
                    AnalyticsDataPair analyticsDataPair = new AnalyticsDataPair(super.getColumnName(), this.bО041EО041E041E041E);
                    int i = b04150415Е0415ЕЕ;
                    switch ((i * (b0415Е04150415ЕЕ + i)) % bЕ041504150415ЕЕ) {
                        case 0:
                            break;
                        default:
                            b04150415Е0415ЕЕ = 61;
                            bЕЕ04150415ЕЕ = b0415041504150415ЕЕ();
                            break;
                    }
                    return analyticsDataPair.getJson();
                } catch (Exception e) {
                    throw e;
                }
            } catch (Exception e2) {
                throw e2;
            }
        }
    }

    public private class TsEditionLevelCollector extends AnalyticsDataCollector {
        public static int b04170417З04170417З = 0;
        public static int b0417З041704170417З = 2;
        public static int bЗ0417З04170417З = 38;
        public static int bЗЗ041704170417З = 1;
        private String b043Dннн043Dн;
        public final /* synthetic */ DefaultAnalyticsCollectors bн043Dнн043Dн;

        public TsEditionLevelCollector(DefaultAnalyticsCollectors defaultAnalyticsCollectors) {
            this.bн043Dнн043Dн = defaultAnalyticsCollectors;
            if (((bЗ0417З04170417З + bЗЗ041704170417З) * bЗ0417З04170417З) % b0417З041704170417З != b04170417З04170417З) {
                bЗ0417З04170417З = 4;
                b04170417З04170417З = bЗ0417041704170417З();
            }
            super("ts_edition_level");
            this.b043Dннн043Dн = getTsEditionLevelNative();
        }

        public static int bЗ0417041704170417З() {
            return 23;
        }

        private native String getTsEditionLevelNative();

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public org.json.JSONObject getData() {
            /*
            r3 = this;
            r1 = 1;
            r0 = new com.immersion.aws.analytics.AnalyticsDataPair;
        L_0x0003:
            switch(r1) {
                case 0: goto L_0x0003;
                case 1: goto L_0x000a;
                default: goto L_0x0006;
            };
        L_0x0006:
            switch(r1) {
                case 0: goto L_0x0003;
                case 1: goto L_0x000a;
                default: goto L_0x0009;
            };
        L_0x0009:
            goto L_0x0006;
        L_0x000a:
            r1 = super.getColumnName();
            r2 = r3.b043Dннн043Dн;
            r0.<init>(r1, r2);
            r1 = bЗ0417041704170417З();
            r2 = bЗЗ041704170417З;
            r2 = r2 + r1;
            r1 = r1 * r2;
            r2 = b0417З041704170417З;
            r1 = r1 % r2;
            switch(r1) {
                case 0: goto L_0x0029;
                default: goto L_0x0021;
            };
        L_0x0021:
            r1 = 99;
            bЗ0417З04170417З = r1;
            r1 = 70;
            b04170417З04170417З = r1;
        L_0x0029:
            r0 = r0.getJson();
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.immersion.aws.analytics.DefaultAnalyticsCollectors.TsEditionLevelCollector.getData():org.json.JSONObject");
        }
    }

    public private class TsStaticWatermarkCollector extends AnalyticsDataCollector {
        public static int b043B043Bл043Bл043B = 98;
        public static int b043Bл043B043Bл043B = 1;
        public static int bл043B043B043Bл043B = 2;
        public static int bлл043B043Bл043B;
        public final /* synthetic */ DefaultAnalyticsCollectors b04140414Д041404140414;
        private String bД0414Д041404140414 = getTsStaticWatermarkNative();

        public TsStaticWatermarkCollector(DefaultAnalyticsCollectors defaultAnalyticsCollectors) {
            this.b04140414Д041404140414 = defaultAnalyticsCollectors;
            super("ts_static_watermark_oem");
        }

        public static int b043B043B043B043Bл043B() {
            return 11;
        }

        private native String getTsStaticWatermarkNative();

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public org.json.JSONObject getData() {
            /*
            r3 = this;
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
            r0 = new com.immersion.aws.analytics.AnalyticsDataPair;
            r1 = b043B043Bл043Bл043B;
            r2 = b043Bл043B043Bл043B;
            r1 = r1 + r2;
            r2 = b043B043Bл043Bл043B;
            r1 = r1 * r2;
            r2 = bл043B043B043Bл043B;
            r1 = r1 % r2;
            r2 = bлл043B043Bл043B;
            if (r1 == r2) goto L_0x0021;
        L_0x0019:
            r1 = 15;
            b043B043Bл043Bл043B = r1;
            r1 = 67;
            bлл043B043Bл043B = r1;
        L_0x0021:
            r1 = super.getColumnName();
            r2 = r3.bД0414Д041404140414;
            r0.<init>(r1, r2);
            r0 = r0.getJson();
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.immersion.aws.analytics.DefaultAnalyticsCollectors.TsStaticWatermarkCollector.getData():org.json.JSONObject");
        }
    }

    public private class TsVersionCollector extends AnalyticsDataCollector {
        public static int b044404440444ффф = 0;
        public static int b0444фф0444фф = 2;
        public static int bффф0444фф = 1;
        public final /* synthetic */ DefaultAnalyticsCollectors b043D043Dннн043D;
        private String bн043Dннн043D = getTsVersionNative();

        public TsVersionCollector(DefaultAnalyticsCollectors defaultAnalyticsCollectors) {
            this.b043D043Dннн043D = defaultAnalyticsCollectors;
            super("ts_version");
        }

        public static int bф04440444ффф() {
            return 43;
        }

        private native String getTsVersionNative();

        public JSONObject getData() {
            String columnName = super.getColumnName();
            int bф04440444ффф = bф04440444ффф();
            switch ((bф04440444ффф * (bффф0444фф + bф04440444ффф)) % b0444фф0444фф) {
                case 0:
                    break;
                default:
                    b044404440444ффф = bф04440444ффф();
                    break;
            }
            return new AnalyticsDataPair(columnName, this.bн043Dннн043D).getJson();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public DefaultAnalyticsCollectors() {
        /*
        r2 = this;
    L_0x0000:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x0000;
            case 1: goto L_0x0009;
            default: goto L_0x0004;
        };
    L_0x0004:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x0009;
            case 1: goto L_0x0000;
            default: goto L_0x0008;
        };
    L_0x0008:
        goto L_0x0004;
    L_0x0009:
        r0 = bф0444ф04440444ф;
        r1 = bфф044404440444ф;
        r0 = r0 + r1;
        r1 = bф0444ф04440444ф;
        r0 = r0 * r1;
        r1 = b0444ф044404440444ф;
        r0 = r0 % r1;
        r1 = b04440444ф04440444ф;
        if (r0 == r1) goto L_0x0022;
    L_0x0018:
        r0 = 91;
        bф0444ф04440444ф = r0;
        r0 = bф0444044404440444ф();
        b04440444ф04440444ф = r0;
    L_0x0022:
        r2.<init>();
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.aws.analytics.DefaultAnalyticsCollectors.<init>():void");
    }

    public static int b04440444044404440444ф() {
        return 2;
    }

    public static int bф0444044404440444ф() {
        return 91;
    }

    public List getDefaultAnalytics(Context context) {
        List arrayList = new ArrayList();
        TsVersionCollector tsVersionCollector = new TsVersionCollector(this);
        ActuatorTypeCollector actuatorTypeCollector = new ActuatorTypeCollector(this);
        TsEditionLevelCollector tsEditionLevelCollector = new TsEditionLevelCollector(this);
        TsStaticWatermarkCollector tsStaticWatermarkCollector = new TsStaticWatermarkCollector(this);
        rccccr rrrrrr_rccccr = new rccccr(this, context);
        ccrrrc rrrrrr_ccrrrc = new ccrrrc(this);
        rcrrcc rrrrrr_rcrrcc = new rcrrcc(this);
        rrrcrc rrrrrr_rrrcrc = new rrrcrc(this);
        rrccrr rrrrrr_rrccrr = new rrccrr(this);
        ccrccc rrrrrr_ccrccc = new ccrccc(this, context);
        arrayList.add(tsVersionCollector);
        arrayList.add(actuatorTypeCollector);
        arrayList.add(tsEditionLevelCollector);
        arrayList.add(tsStaticWatermarkCollector);
        arrayList.add(rrrrrr_rccccr);
        arrayList.add(rrrrrr_ccrrrc);
        if (((bф0444ф04440444ф + bфф044404440444ф) * bф0444ф04440444ф) % b04440444044404440444ф() != b04440444ф04440444ф) {
            bф0444ф04440444ф = bф0444044404440444ф();
            b04440444ф04440444ф = bф0444044404440444ф();
        }
        arrayList.add(rrrrrr_rcrrcc);
        arrayList.add(rrrrrr_rrrcrc);
        arrayList.add(rrrrrr_rrccrr);
        arrayList.add(rrrrrr_ccrccc);
        return arrayList;
    }
}
