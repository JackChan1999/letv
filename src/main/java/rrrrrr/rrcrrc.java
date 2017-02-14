package rrrrrr;

import android.os.Handler.Callback;

public class rrcrrc implements Callback {
    public static int b04440444ф0444фф = 1;
    public static int b0444ф04440444фф = 0;
    public static int bф0444ф0444фф = 33;
    public static int bфф04440444фф = 2;
    public final /* synthetic */ cccrcc bнн043Dнн043D;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public rrcrrc(rrrrrr.cccrcc r4) {
        /*
        r3 = this;
        r2 = 0;
        r0 = bф0444ф0444фф;
        r1 = b04440444ф0444фф;
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = bфф04440444фф;
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x0015;
            default: goto L_0x000d;
        };
    L_0x000d:
        r0 = 29;
        bф0444ф0444фф = r0;
        r0 = 61;
        b04440444ф0444фф = r0;
    L_0x0015:
        r3.bнн043Dнн043D = r4;
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
        r3.<init>();
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.rrcrrc.<init>(rrrrrr.cccrcc):void");
    }

    public static int bф044404440444фф() {
        return 70;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean handleMessage(android.os.Message r5) {
        /*
        r4 = this;
        r3 = 1;
        r0 = r5.what;
        switch(r0) {
            case -2: goto L_0x0007;
            case -1: goto L_0x014b;
            case 0: goto L_0x006c;
            case 1: goto L_0x00ad;
            case 2: goto L_0x016b;
            case 3: goto L_0x00ee;
            case 4: goto L_0x017b;
            case 5: goto L_0x00fc;
            default: goto L_0x0006;
        };
    L_0x0006:
        return r3;
    L_0x0007:
        r0 = rrrrrr.cccrcc.bйй04390439йй();
        r1 = "Authentication Timed Out";
        com.immersion.Log.d(r0, r1);
        r0 = r4.bнн043Dнн043D;
        r0 = rrrrrr.cccrcc.b0439й04390439йй(r0);
        r0 = r0.exists();
        if (r0 == 0) goto L_0x0006;
    L_0x001c:
        r0 = r4.bнн043Dнн043D;
        r0 = rrrrrr.cccrcc.bй043904390439йй(r0);
        r0 = r0.bщщщ0449щ0449();
        if (r0 == 0) goto L_0x0006;
    L_0x0028:
        r0 = r4.bнн043Dнн043D;
        r0 = rrrrrr.cccrcc.b0439й04390439йй(r0);
        r0.delete();
        r0 = bф0444ф0444фф;
        r1 = b04440444ф0444фф;
        r0 = r0 + r1;
        r1 = bф0444ф0444фф;
        r0 = r0 * r1;
        r1 = bфф04440444фф;
        r0 = r0 % r1;
        r1 = b0444ф04440444фф;
        if (r0 == r1) goto L_0x0006;
    L_0x0040:
        r0 = bф044404440444фф();
        bф0444ф0444фф = r0;
        r0 = bф044404440444фф();
        b0444ф04440444фф = r0;
        goto L_0x0006;
    L_0x004d:
        r0 = r4.bнн043Dнн043D;
        r0 = rrrrrr.cccrcc.b0439й04390439йй(r0);
        r0 = r0.exists();
        if (r0 == 0) goto L_0x0006;
    L_0x0059:
        r0 = rrrrrr.cccrcc.bйй04390439йй();
        r1 = "Checksums do not match!";
        com.immersion.Log.d(r0, r1);
        r0 = r4.bнн043Dнн043D;
        r0 = rrrrrr.cccrcc.b0439й04390439йй(r0);
        r0.delete();
        goto L_0x0006;
    L_0x006c:
        r0 = rrrrrr.cccrcc.bйй04390439йй();
        r1 = "Authentication Started";
        com.immersion.Log.d(r0, r1);
        r0 = r5.getData();
        r1 = "cksm";
        r0 = r0.getString(r1);
        r1 = r4.bнн043Dнн043D;
        r1 = rrrrrr.cccrcc.b0439й04390439йй(r1);
        r1 = com.immersion.aws.pm.Utilities.bщщщщ04490449(r1);
        if (r0 == 0) goto L_0x004d;
    L_0x008b:
        r2 = r4.bнн043Dнн043D;
        r2 = rrrrrr.cccrcc.b0439й04390439йй(r2);
        r2 = r2.exists();
        if (r2 == 0) goto L_0x004d;
    L_0x0097:
        r0 = r1.equalsIgnoreCase(r0);
        if (r0 == 0) goto L_0x004d;
    L_0x009d:
        r0 = rrrrrr.cccrcc.bйй04390439йй();
        r1 = "Checksums Matched!";
        com.immersion.Log.d(r0, r1);
        r0 = r4.bнн043Dнн043D;
        r0.b0439йй0439йй();
        goto L_0x0006;
    L_0x00ad:
        r0 = rrrrrr.cccrcc.bйй04390439йй();
    L_0x00b1:
        switch(r3) {
            case 0: goto L_0x00b1;
            case 1: goto L_0x00b8;
            default: goto L_0x00b4;
        };
    L_0x00b4:
        switch(r3) {
            case 0: goto L_0x00b1;
            case 1: goto L_0x00b8;
            default: goto L_0x00b7;
        };
    L_0x00b7:
        goto L_0x00b4;
    L_0x00b8:
        r1 = "Authentication Succeeded";
        com.immersion.Log.d(r0, r1);
        r0 = r5.getData();
        r1 = "url";
        r0 = r0.getString(r1);
        r1 = r5.getData();
        r2 = "cksm";
        r1 = r1.getString(r2);
        r2 = r4.bнн043Dнн043D;
        r2 = rrrrrr.cccrcc.bй043904390439йй(r2);
        r2.bщ04490449щщ0449(r0, r1);
        r0 = r4.bнн043Dнн043D;
        r0 = rrrrrr.cccrcc.bй043904390439йй(r0);
        r0 = r0.b0449щщ0449щ0449();
        if (r0 == 0) goto L_0x011e;
    L_0x00e7:
        r0 = r4.bнн043Dнн043D;
        r0.bййй0439йй();
        goto L_0x0006;
    L_0x00ee:
        r0 = rrrrrr.cccrcc.bйй04390439йй();
        r1 = "PolicyFileDownload Failed";
        com.immersion.Log.d(r0, r1);
        r0 = r4.bнн043Dнн043D;
        r0.bй0439й0439йй();
    L_0x00fc:
        r0 = r4.bнн043Dнн043D;
        r0 = rrrrrr.cccrcc.b0439043904390439йй(r0);
        if (r0 == 0) goto L_0x0006;
    L_0x0104:
        r0 = r4.bнн043Dнн043D;
        r0 = rrrrrr.cccrcc.bй043904390439йй(r0);
        r0.bщщ04490449щ0449();
        r0 = r4.bнн043Dнн043D;
        r0 = rrrrrr.cccrcc.bйййй0439й(r0);
        r0.quit();
        r0 = r4.bнн043Dнн043D;
        r1 = 0;
        rrrrrr.cccrcc.b0439ййй0439й(r0, r1);
        goto L_0x0006;
    L_0x011e:
        r0 = r4.bнн043Dнн043D;
        r0 = rrrrrr.cccrcc.b0439й04390439йй(r0);
        r0 = r0.exists();
        if (r0 == 0) goto L_0x0006;
    L_0x012a:
        r0 = new java.util.Date;
        r0.<init>();
        r1 = r4.bнн043Dнн043D;
        r1 = rrrrrr.cccrcc.bй043904390439йй(r1);
        r0 = r1.bщ0449щ0449щ0449(r0);
        if (r0 == 0) goto L_0x0144;
    L_0x013b:
        r0 = rrrrrr.cccrcc.bйй04390439йй();
        r1 = "Local PolicyFile Updated";
        com.immersion.Log.d(r0, r1);
    L_0x0144:
        r0 = r4.bнн043Dнн043D;
        r0.bй04390439ййй();
        goto L_0x0006;
    L_0x014b:
        r0 = rrrrrr.cccrcc.bйй04390439йй();
        r1 = "Authentication Failed";
        com.immersion.Log.d(r0, r1);
        r0 = r4.bнн043Dнн043D;
        r0 = rrrrrr.cccrcc.b0439й04390439йй(r0);
        r0 = r0.exists();
        if (r0 == 0) goto L_0x0006;
    L_0x0160:
        r0 = r4.bнн043Dнн043D;
        r0 = rrrrrr.cccrcc.b0439й04390439йй(r0);
        r0.delete();
        goto L_0x0006;
    L_0x016b:
        r0 = rrrrrr.cccrcc.bйй04390439йй();
        r1 = "Attemp to download policy file";
        com.immersion.Log.d(r0, r1);
        r0 = r4.bнн043Dнн043D;
        r0.b04390439й0439йй();
        goto L_0x0006;
    L_0x017b:
        r0 = rrrrrr.cccrcc.bйй04390439йй();
        r1 = "PolicyFileDownload Succeeded";
        com.immersion.Log.d(r0, r1);
        r0 = r4.bнн043Dнн043D;
        r0.b0439йй0439йй();
        goto L_0x0006;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.rrcrrc.handleMessage(android.os.Message):boolean");
    }
}
