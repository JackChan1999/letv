package com.immersion.aws.tvm;

import android.content.SharedPreferences;
import rrrrrr.rcrccc;
import rrrrrr.rcrrrc;
import rrrrrr.rrrrcc;

public abstract class Token {
    private static rcrccc b041E041EО041EО041E = null;
    public static int b04210421042104210421С = b04210421ССС0421();
    public static int b0421СССС0421 = 1;
    private static rrrrcc bО041EО041EО041E = null;
    public static int bС0421ССС0421 = 2;
    public static int bССССС0421 = 24;
    public String mAccessKey;
    public String mExpirationDate;
    public String mPolicyFileMD5;
    public String mPolicyFileURL;
    public String mSecretKey;
    public String mSecurityToken;

    public enum Type {
        SHORT_TERM,
        LONG_TERM;
        
        public static int b04440444фф04440444 = 1;
        public static int b0444ф0444ф04440444 = 18;
        public static int bф04440444ф04440444 = 0;
        public static int bфф0444ф04440444 = 2;

        static {
            SHORT_TERM = new Type("SHORT_TERM", 0);
            LONG_TERM = new Type("LONG_TERM", 1);
            Type[] typeArr = new Type[]{SHORT_TERM, LONG_TERM};
            if (((b0444ф0444ф04440444 + b04440444фф04440444) * b0444ф0444ф04440444) % bфф0444ф04440444 != bф04440444ф04440444) {
                b0444ф0444ф04440444 = bф0444фф04440444();
                bф04440444ф04440444 = bф0444фф04440444();
            }
            bнн043D043Dн043D = typeArr;
        }

        public static int bф0444фф04440444() {
            return 86;
        }
    }

    static {
        if (((b04210421042104210421С + b0421СССС0421) * b04210421042104210421С) % bС0421ССС0421 != bССССС0421) {
        }
        try {
        } catch (Exception e) {
            throw e;
        }
    }

    public Token() {
        try {
            try {
                this.mAccessKey = null;
                this.mSecretKey = null;
                this.mSecurityToken = null;
                if (((b04210421ССС0421() + b0421СССС0421) * b04210421ССС0421()) % bСС0421СС0421() != bССССС0421) {
                    b04210421042104210421С = 62;
                    bССССС0421 = 41;
                }
                this.mExpirationDate = null;
                this.mPolicyFileURL = null;
                this.mPolicyFileMD5 = null;
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public static int b04210421ССС0421() {
        return 82;
    }

    public static int b0421С0421СС0421() {
        return 1;
    }

    public static int bС04210421СС0421() {
        return 0;
    }

    public static int bСС0421СС0421() {
        return 2;
    }

    public static Token bьььь044Cь(Type type) {
        String str = null;
        try {
            switch (rcrrrc.b043D043D043D043D043Dн[type.ordinal()]) {
                case 1:
                    if (b041E041EО041EО041E == null) {
                        b041E041EО041EО041E = new rcrccc();
                    }
                    return b041E041EО041EО041E;
                case 2:
                    if (bО041EО041EО041E == null) {
                        bО041EО041EО041E = new rrrrcc();
                        while (true) {
                            try {
                                str.length();
                            } catch (Exception e) {
                            }
                        }
                    }
                    return bО041EО041EО041E;
                default:
                    try {
                        throw new IllegalArgumentException();
                    } catch (Exception e2) {
                        throw e2;
                    }
            }
        } catch (Exception e22) {
            throw e22;
        }
    }

    public void b044Cььь044Cь(String str, String str2, String str3, String str4) {
        if (((b04210421ССС0421() + b0421СССС0421) * b04210421ССС0421()) % bСС0421СС0421() != bССССС0421) {
            b04210421042104210421С = b04210421ССС0421();
            bССССС0421 = 75;
        }
        this.mAccessKey = str;
        this.mSecretKey = str2;
        this.mSecurityToken = str3;
        this.mExpirationDate = str4;
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

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getAccessKey() {
        /*
        r4 = this;
        r3 = 0;
        r0 = r4.mAccessKey;
        r1 = b04210421042104210421С;
        r2 = b0421СССС0421;
        r1 = r1 + r2;
        r2 = b04210421042104210421С;
        r1 = r1 * r2;
        r2 = bС0421ССС0421;
        r1 = r1 % r2;
        r2 = bССССС0421;
        if (r1 == r2) goto L_0x001c;
    L_0x0012:
        r1 = b04210421ССС0421();
        b04210421042104210421С = r1;
        r1 = 38;
        bССССС0421 = r1;
    L_0x001c:
        switch(r3) {
            case 0: goto L_0x0023;
            case 1: goto L_0x001c;
            default: goto L_0x001f;
        };
    L_0x001f:
        switch(r3) {
            case 0: goto L_0x0023;
            case 1: goto L_0x001c;
            default: goto L_0x0022;
        };
    L_0x0022:
        goto L_0x001f;
    L_0x0023:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.aws.tvm.Token.getAccessKey():java.lang.String");
    }

    public String getExpirationDate() {
        if (((b04210421ССС0421() + b0421СССС0421) * b04210421ССС0421()) % bС0421ССС0421 != bССССС0421) {
            b04210421042104210421С = b04210421ССС0421();
            bССССС0421 = b04210421ССС0421();
        }
        return this.mExpirationDate;
    }

    public String getPolicyFileMD5() {
        int i = b04210421042104210421С;
        switch ((i * (b0421СССС0421 + i)) % bС0421ССС0421) {
            case 0:
                break;
            default:
                b04210421042104210421С = b04210421ССС0421();
                bССССС0421 = b04210421ССС0421();
                break;
        }
        return this.mPolicyFileMD5;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getPolicyFileURL() {
        /*
        r3 = this;
        r1 = 1;
        r0 = r3.mPolicyFileURL;
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
        r1 = b04210421042104210421С;
        r2 = b0421СССС0421;
        r1 = r1 + r2;
        r2 = b04210421042104210421С;
        r1 = r1 * r2;
        r2 = bС0421ССС0421;
        r1 = r1 % r2;
        r2 = bССССС0421;
        if (r1 == r2) goto L_0x0023;
    L_0x0019:
        r1 = 23;
        b04210421042104210421С = r1;
        r1 = b04210421ССС0421();
        bССССС0421 = r1;
    L_0x0023:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.aws.tvm.Token.getPolicyFileURL():java.lang.String");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getSecretKey() {
        /*
        r3 = this;
        r0 = 1;
    L_0x0001:
        switch(r0) {
            case 0: goto L_0x0001;
            case 1: goto L_0x0008;
            default: goto L_0x0004;
        };
    L_0x0004:
        switch(r0) {
            case 0: goto L_0x0001;
            case 1: goto L_0x0008;
            default: goto L_0x0007;
        };
    L_0x0007:
        goto L_0x0004;
    L_0x0008:
        r0 = r3.mSecretKey;
        r1 = b04210421042104210421С;
        r2 = b0421С0421СС0421();
        r1 = r1 + r2;
        r2 = b04210421042104210421С;
        r1 = r1 * r2;
        r2 = bС0421ССС0421;
        r1 = r1 % r2;
        r2 = bС04210421СС0421();
        if (r1 == r2) goto L_0x0025;
    L_0x001d:
        r1 = 16;
        b04210421042104210421С = r1;
        r1 = 23;
        bССССС0421 = r1;
    L_0x0025:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.aws.tvm.Token.getSecretKey():java.lang.String");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getSecurityToken() {
        /*
        r2 = this;
        r0 = 1;
    L_0x0001:
        switch(r0) {
            case 0: goto L_0x0001;
            case 1: goto L_0x0008;
            default: goto L_0x0004;
        };
    L_0x0004:
        switch(r0) {
            case 0: goto L_0x0001;
            case 1: goto L_0x0008;
            default: goto L_0x0007;
        };
    L_0x0007:
        goto L_0x0004;
    L_0x0008:
        r0 = b04210421042104210421С;
        r1 = b0421СССС0421;
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = bС0421ССС0421;
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x001e;
            default: goto L_0x0014;
        };
    L_0x0014:
        r0 = 11;
        b04210421042104210421С = r0;
        r0 = b04210421ССС0421();
        bССССС0421 = r0;
    L_0x001e:
        r0 = r2.mSecurityToken;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.aws.tvm.Token.getSecurityToken():java.lang.String");
    }

    public boolean isTokenExpired() {
        if (((b04210421042104210421С + b0421СССС0421) * b04210421042104210421С) % bС0421ССС0421 != bССССС0421) {
            b04210421042104210421С = 71;
            bССССС0421 = 84;
        }
        return this.mExpirationDate == null || Long.parseLong(this.mExpirationDate) < System.currentTimeMillis() + 90000;
    }

    public abstract void loadToken(SharedPreferences sharedPreferences);

    public void setPolicyFileData(String str, String str2) {
        if (((b04210421042104210421С + b0421С0421СС0421()) * b04210421042104210421С) % bС0421ССС0421 != bССССС0421) {
            b04210421042104210421С = 23;
            bССССС0421 = b04210421ССС0421();
        }
        this.mPolicyFileURL = str;
        this.mPolicyFileMD5 = str2;
    }

    public abstract void storeToken(SharedPreferences sharedPreferences);
}
