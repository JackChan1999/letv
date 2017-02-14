package rrrrrr;

import com.immersion.aws.analytics.AnalyticsDataCollector;
import com.immersion.hapticmediasdk.controllers.MemoryAlignedFileReader;

public class ccrrrr extends AnalyticsDataCollector {
    public static int b0421СС042104210421 = 2;
    public static int bС04210421С04210421 = 36;
    public static int bС0421С042104210421 = 1;
    public static int bСС0421042104210421;
    public final /* synthetic */ MemoryAlignedFileReader b041E041E041EО041E041E;
    public int b041EО041EО041E041E = 0;
    public int bО041E041EО041E041E;
    public int bОО041EО041E041E = 0;

    public ccrrrr(MemoryAlignedFileReader memoryAlignedFileReader) {
        this.b041E041E041EО041E041E = memoryAlignedFileReader;
        super("percentage_played_back");
        if (((bС04210421С04210421 + bССС042104210421()) * bС04210421С04210421) % b0421СС042104210421 != b042104210421С04210421()) {
            bС04210421С04210421 = 86;
            b0421СС042104210421 = 10;
        }
        this.bО041E041EО041E041E = 0;
    }

    public static int b042104210421С04210421() {
        return 0;
    }

    public static int b04210421С042104210421() {
        return 22;
    }

    public static int bССС042104210421() {
        return 1;
    }

    public int calculatePercentagePlayedBack() {
        if (this.b041EО041EО041E041E == 0) {
            return 0;
        }
        int i = this.bОО041EО041E041E + this.bО041E041EО041E041E;
        if (((bС04210421С04210421 + bС0421С042104210421) * bС04210421С04210421) % b0421СС042104210421 != bСС0421042104210421) {
            bС04210421С04210421 = 67;
            bСС0421042104210421 = 64;
        }
        return (int) ((((double) i) / ((double) this.b041EО041EО041E041E)) * 100.0d);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.json.JSONObject getData() {
        /*
        r3 = this;
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
        r0 = r3.calculatePercentagePlayedBack();
        r1 = new com.immersion.aws.analytics.AnalyticsDataPair;
        r2 = super.getColumnName();
        r0 = java.lang.Integer.toString(r0);
        r1.<init>(r2, r0);
        r0 = r1.getJson();
        r1 = bС04210421С04210421;
        r2 = bС0421С042104210421;
        r2 = r2 + r1;
        r1 = r1 * r2;
        r2 = b0421СС042104210421;
        r1 = r1 % r2;
        switch(r1) {
            case 0: goto L_0x0034;
            default: goto L_0x002a;
        };
    L_0x002a:
        r1 = b04210421С042104210421();
        bС04210421С04210421 = r1;
        r1 = 54;
        bСС0421042104210421 = r1;
    L_0x0034:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.ccrrrr.getData():org.json.JSONObject");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setHapticBlockSize(int r3) {
        /*
        r2 = this;
        r0 = r2.bО041E041EО041E041E;
        if (r0 != 0) goto L_0x0025;
    L_0x0004:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x0004;
            case 1: goto L_0x000d;
            default: goto L_0x0008;
        };
    L_0x0008:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x000d;
            case 1: goto L_0x0004;
            default: goto L_0x000c;
        };
    L_0x000c:
        goto L_0x0008;
    L_0x000d:
        r0 = bС04210421С04210421;
        r1 = bС0421С042104210421;
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = b0421СС042104210421;
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x0023;
            default: goto L_0x0019;
        };
    L_0x0019:
        r0 = 94;
        bС04210421С04210421 = r0;
        r0 = b04210421С042104210421();
        bС0421С042104210421 = r0;
    L_0x0023:
        r2.bО041E041EО041E041E = r3;
    L_0x0025:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.ccrrrr.setHapticBlockSize(int):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setHapticDataSize(int r3) {
        /*
        r2 = this;
        r0 = r2.b041EО041EО041E041E;
        if (r0 != 0) goto L_0x0027;
    L_0x0004:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x000d;
            case 1: goto L_0x0004;
            default: goto L_0x0008;
        };
    L_0x0008:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x0004;
            case 1: goto L_0x000d;
            default: goto L_0x000c;
        };
    L_0x000c:
        goto L_0x0008;
    L_0x000d:
        r0 = bС04210421С04210421;
        r1 = bС0421С042104210421;
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = b0421СС042104210421;
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x0025;
            default: goto L_0x0019;
        };
    L_0x0019:
        r0 = b04210421С042104210421();
        bС04210421С04210421 = r0;
        r0 = b04210421С042104210421();
        bС0421С042104210421 = r0;
    L_0x0025:
        r2.b041EО041EО041E041E = r3;
    L_0x0027:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.ccrrrr.setHapticDataSize(int):void");
    }

    public void setLastPlayedBlockPosition(int i) {
        int i2 = this.b041EО041EО041E041E;
        if (((bС04210421С04210421 + bССС042104210421()) * bС04210421С04210421) % b0421СС042104210421 != bСС0421042104210421) {
            bС04210421С04210421 = b04210421С042104210421();
            bСС0421042104210421 = b04210421С042104210421();
        }
        if (i <= i2 && i > 0) {
            this.bОО041EО041E041E = i;
        }
    }
}
