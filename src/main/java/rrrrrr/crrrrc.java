package rrrrrr;

import com.immersion.Log;
import com.immersion.aws.pm.Utilities;
import java.io.File;
import java.util.Date;

public class crrrrc {
    public static int b0417041704170417З0417 = 1;
    public static int b0417З04170417З0417 = b0417ЗЗЗ04170417();
    private static final int b043D043D043Dн043Dн = 120000;
    public static int bЗ041704170417З0417 = 71;
    public static int bЗЗЗЗ04170417 = 2;
    private static final String bн043D043Dн043Dн;
    private static final int bннн043D043Dн = 14;
    private String b043D043Dн043D043Dн;
    private rcrccr b043Dн043D043D043Dн;
    private rrrrcr b043Dнн043D043Dн;
    private String bн043Dн043D043Dн;
    private File bнн043D043D043Dн;

    static {
        try {
            try {
                bн043D043Dн043Dн = crrrrc.class.getSimpleName();
                int i = b0417З04170417З0417;
                switch ((i * (b0417041704170417З0417 + i)) % bЗЗЗЗ04170417) {
                    case 0:
                        return;
                    default:
                        return;
                }
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public crrrrc(rrrrcr rrrrrr_rrrrcr, File file) {
        try {
            this.b043Dнн043D043Dн = rrrrrr_rrrrcr;
            this.bнн043D043D043Dн = file;
            while (true) {
                try {
                    int[] iArr = new int[-1];
                } catch (Exception e) {
                    try {
                        this.b043Dн043D043D043Dн = null;
                        return;
                    } catch (Exception e2) {
                        throw e2;
                    }
                }
            }
        } catch (Exception e22) {
            throw e22;
        }
    }

    public static int b0417ЗЗЗ04170417() {
        return 67;
    }

    public static int bЗ0417ЗЗ04170417() {
        return 1;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean b044904490449щщ0449() {
        /*
        r4 = this;
        r0 = 0;
    L_0x0001:
        switch(r0) {
            case 0: goto L_0x0009;
            case 1: goto L_0x0001;
            default: goto L_0x0004;
        };
    L_0x0004:
        r1 = 1;
        switch(r1) {
            case 0: goto L_0x0001;
            case 1: goto L_0x0009;
            default: goto L_0x0008;
        };
    L_0x0008:
        goto L_0x0004;
    L_0x0009:
        r1 = bн043D043Dн043Dн;	 Catch:{ MalformedURLException -> 0x0050, IOException -> 0x0072 }
        r2 = new java.lang.StringBuilder;	 Catch:{ MalformedURLException -> 0x0050, IOException -> 0x0072 }
        r2.<init>();	 Catch:{ MalformedURLException -> 0x0050, IOException -> 0x0072 }
        r3 = "Policy File URL: ";
        r2 = r2.append(r3);	 Catch:{ MalformedURLException -> 0x0050, IOException -> 0x0072 }
        r3 = r4.bн043Dн043D043Dн;	 Catch:{ MalformedURLException -> 0x0050, IOException -> 0x0072 }
        r2 = r2.append(r3);	 Catch:{ MalformedURLException -> 0x0050, IOException -> 0x0072 }
        r2 = r2.toString();	 Catch:{ MalformedURLException -> 0x0050, IOException -> 0x0072 }
        com.immersion.Log.d(r1, r2);	 Catch:{ MalformedURLException -> 0x0050, IOException -> 0x0072 }
        r1 = new java.net.URL;	 Catch:{ MalformedURLException -> 0x0050, IOException -> 0x0072 }
        r2 = r4.bн043Dн043D043Dн;	 Catch:{ MalformedURLException -> 0x0050, IOException -> 0x0072 }
        r1.<init>(r2);	 Catch:{ MalformedURLException -> 0x0050, IOException -> 0x0072 }
        r2 = r1.openConnection();	 Catch:{ MalformedURLException -> 0x0050, IOException -> 0x0072 }
        r3 = 120000; // 0x1d4c0 float:1.68156E-40 double:5.9288E-319;
        r2.setConnectTimeout(r3);	 Catch:{ MalformedURLException -> 0x0050, IOException -> 0x0072 }
        r0 = r1.getProtocol();
        r1 = "file";
        r0 = r0.equalsIgnoreCase(r1);
        if (r0 == 0) goto L_0x007b;
    L_0x0040:
        r0 = new rrrrrr.crrccr;
        r1 = r4.bнн043D043D043Dн;
        r0.<init>(r1, r2);
        r4.b043Dн043D043D043Dн = r0;
    L_0x0049:
        r0 = r4.b043Dн043D043D043Dн;
        r0 = r0.bщ0449щщ0449щ();
    L_0x004f:
        return r0;
    L_0x0050:
        r1 = move-exception;
        r1 = bн043D043Dн043Dн;
        r2 = "Malformed pm file URL";
        com.immersion.Log.e(r1, r2);
        r1 = b0417З04170417З0417;
        r2 = b0417041704170417З0417;
        r1 = r1 + r2;
        r2 = b0417З04170417З0417;
        r1 = r1 * r2;
        r2 = bЗЗЗЗ04170417;
        r1 = r1 % r2;
        r2 = bЗ041704170417З0417;
        if (r1 == r2) goto L_0x004f;
    L_0x0067:
        r1 = b0417ЗЗЗ04170417();
        b0417З04170417З0417 = r1;
        r1 = 84;
        bЗ041704170417З0417 = r1;
        goto L_0x004f;
    L_0x0072:
        r1 = move-exception;
        r1 = bн043D043Dн043Dн;
        r2 = "Failed to open connection to pm";
        com.immersion.Log.e(r1, r2);
        goto L_0x004f;
    L_0x007b:
        r0 = new rrrrrr.rcccrc;
        r1 = r4.bнн043D043D043Dн;
        r0.<init>(r1, r2);
        r4.b043Dн043D043D043Dн = r0;
        goto L_0x0049;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.crrrrc.b044904490449щщ0449():boolean");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean b04490449щ0449щ0449(java.util.Date r7, java.util.Date r8, int r9) {
        /*
        r6 = this;
        r0 = 1;
        r2 = r7.getTime();
        r4 = r8.getTime();
        r2 = r2 - r4;
        r4 = 86400000; // 0x5265c00 float:7.82218E-36 double:4.2687272E-316;
        r2 = r2 / r4;
        r4 = (long) r9;
        r1 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r1 < 0) goto L_0x0038;
    L_0x0013:
        switch(r0) {
            case 0: goto L_0x0013;
            case 1: goto L_0x001a;
            default: goto L_0x0016;
        };
    L_0x0016:
        switch(r0) {
            case 0: goto L_0x0013;
            case 1: goto L_0x001a;
            default: goto L_0x0019;
        };
    L_0x0019:
        goto L_0x0016;
    L_0x001a:
        r1 = b0417ЗЗЗ04170417();
        r2 = b0417041704170417З0417;
        r1 = r1 + r2;
        r2 = b0417ЗЗЗ04170417();
        r1 = r1 * r2;
        r2 = bЗЗЗЗ04170417;
        r1 = r1 % r2;
        r2 = bЗ041704170417З0417;
        if (r1 == r2) goto L_0x0037;
    L_0x002d:
        r1 = 35;
        b0417З04170417З0417 = r1;
        r1 = b0417ЗЗЗ04170417();
        bЗ041704170417З0417 = r1;
    L_0x0037:
        return r0;
    L_0x0038:
        r0 = 0;
        goto L_0x0037;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.crrrrc.b04490449щ0449щ0449(java.util.Date, java.util.Date, int):boolean");
    }

    public void b0449щ04490449щ0449() {
        this.b043Dнн043D043Dн.onDownloadStarted();
        Log.d(bн043D043Dн043Dн, "Download started");
        if (this.b043Dн043D043D043Dн.bщщ0449щ0449щ()) {
            String bщщщщ04490449 = Utilities.bщщщщ04490449(this.bнн043D043D043Dн);
            Log.d(bн043D043Dн043Dн, "PolicyFile MD5: " + bщщщщ04490449 + " Expect: " + this.b043D043Dн043D043Dн);
            String str = this.b043D043Dн043D043Dн;
            int i = b0417З04170417З0417;
            switch ((i * (b0417041704170417З0417 + i)) % bЗЗЗЗ04170417) {
                case 0:
                    break;
                default:
                    b0417З04170417З0417 = 93;
                    bЗ041704170417З0417 = 32;
                    break;
            }
            if (bщщщщ04490449.equalsIgnoreCase(str)) {
                this.b043Dнн043D043Dн.onDownloadSuccess();
                return;
            }
            Log.i(bн043D043Dн043Dн, "File checksum don't match. Retrying...");
        }
        this.b043Dнн043D043Dн.onDownloadFailure();
    }

    public boolean b0449щщ0449щ0449() {
        if (!bщщщ0449щ0449() || !b044904490449щщ0449()) {
            return false;
        }
        if (((b0417З04170417З0417 + b0417041704170417З0417) * b0417З04170417З0417) % bЗЗЗЗ04170417 == bЗ041704170417З0417) {
            return true;
        }
        b0417З04170417З0417 = b0417ЗЗЗ04170417();
        bЗ041704170417З0417 = 29;
        return true;
    }

    public void bщ04490449щщ0449(String str, String str2) {
        if (((b0417З04170417З0417 + b0417041704170417З0417) * b0417З04170417З0417) % bЗЗЗЗ04170417 != bЗ041704170417З0417) {
            b0417З04170417З0417 = 60;
            bЗ041704170417З0417 = 56;
        }
        try {
            this.bн043Dн043D043Dн = str;
            try {
                this.b043D043Dн043D043Dн = str2;
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public boolean bщ0449щ0449щ0449(Date date) {
        if (!bщщщ0449щ0449()) {
            return false;
        }
        boolean lastModified = this.bнн043D043D043Dн.setLastModified(date.getTime());
        Log.d(bн043D043Dн043Dн, "Updated local policy file.");
        return lastModified;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void bщщ04490449щ0449() {
        /*
        r4 = this;
        r3 = 0;
        r0 = 2;
        r1 = 0;
        r2 = r4.b043Dн043D043D043Dн;
        if (r2 == 0) goto L_0x001d;
    L_0x0007:
        switch(r3) {
            case 0: goto L_0x000f;
            case 1: goto L_0x0007;
            default: goto L_0x000a;
        };
    L_0x000a:
        r2 = 1;
        switch(r2) {
            case 0: goto L_0x0007;
            case 1: goto L_0x000f;
            default: goto L_0x000e;
        };
    L_0x000e:
        goto L_0x000a;
    L_0x000f:
        r0 = r0 / r1;
        goto L_0x000f;
    L_0x0011:
        r0 = move-exception;
        r0 = b0417ЗЗЗ04170417();
        b0417З04170417З0417 = r0;
        r0 = r4.b043Dн043D043D043Dн;
        r0.b04490449щщ0449щ();
    L_0x001d:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.crrrrc.bщщ04490449щ0449():void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean bщщщ0449щ0449() {
        /*
        r4 = this;
        r0 = new java.util.Date;
        r0.<init>();
    L_0x0005:
        r1 = 1;
        switch(r1) {
            case 0: goto L_0x0005;
            case 1: goto L_0x000e;
            default: goto L_0x0009;
        };
    L_0x0009:
        r1 = 0;
        switch(r1) {
            case 0: goto L_0x000e;
            case 1: goto L_0x0005;
            default: goto L_0x000d;
        };
    L_0x000d:
        goto L_0x0009;
    L_0x000e:
        r1 = new java.util.Date;
        r2 = r4.bнн043D043D043Dн;
        r2 = r2.lastModified();
        r1.<init>(r2);
        r2 = b0417З04170417З0417;
        r3 = b0417041704170417З0417;
        r2 = r2 + r3;
        r3 = b0417З04170417З0417;
        r2 = r2 * r3;
        r3 = bЗЗЗЗ04170417;
        r2 = r2 % r3;
        r3 = bЗ041704170417З0417;
        if (r2 == r3) goto L_0x0030;
    L_0x0028:
        r2 = 29;
        b0417З04170417З0417 = r2;
        r2 = 33;
        bЗ041704170417З0417 = r2;
    L_0x0030:
        r2 = 14;
        r1 = r4.b04490449щ0449щ0449(r0, r1, r2);
        r2 = bн043D043Dн043Dн;
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r3 = "Is local pm file outdated? ";
        r3 = r0.append(r3);
        if (r1 == 0) goto L_0x0053;
    L_0x0045:
        r0 = "Yes!";
    L_0x0047:
        r0 = r3.append(r0);
        r0 = r0.toString();
        com.immersion.Log.d(r2, r0);
        return r1;
    L_0x0053:
        r0 = "No.";
        goto L_0x0047;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.crrrrc.bщщщ0449щ0449():boolean");
    }
}
