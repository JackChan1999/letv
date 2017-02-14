package rrrrrr;

import com.immersion.Log;
import com.letv.pp.utils.NetworkUtils;
import io.fabric.sdk.android.services.network.UrlUtils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.http.Header;

public class crrcrc {
    public static int b04250425ХХХХ = bХ04250425ХХХ();
    private static final String b043D043Dнн043D043D = "POST";
    private static final String b043Dннн043D043D;
    public static int bХ042504250425ХХ = 2;
    public static int bХ0425ХХХХ = 71;
    public static int bХХ0425ХХХ = 1;
    private static final String bн043Dнн043D043D = "SHA-256";

    static {
        try {
            try {
                String simpleName = crrcrc.class.getSimpleName();
                int i = bХ0425ХХХХ;
                switch ((i * (bХХ0425ХХХ + i)) % bХ042504250425ХХ) {
                    case 0:
                        break;
                    default:
                        break;
                }
                b043Dннн043D043D = simpleName;
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public crrcrc() {
        /*
        r2 = this;
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
        r0 = bХ0425ХХХХ;
        r1 = bХХ0425ХХХ;
        r0 = r0 + r1;
        r1 = bХ0425ХХХХ;
        r0 = r0 * r1;
        r1 = b0425Х0425ХХХ();
        r0 = r0 % r1;
        r1 = b04250425ХХХХ;
        if (r0 == r1) goto L_0x0025;
    L_0x0019:
        r0 = bХ04250425ХХХ();
        bХ0425ХХХХ = r0;
        r0 = bХ04250425ХХХ();
        b04250425ХХХХ = r0;
    L_0x0025:
        r2.<init>();
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.crrcrc.<init>():void");
    }

    public static int b0425Х04250425ХХ() {
        return 1;
    }

    public static int b0425Х0425ХХХ() {
        return 2;
    }

    private String b044904490449щ04490449(Header[] headerArr) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Header header : headerArr) {
            int i = bХ0425ХХХХ;
            switch ((i * (bХХ0425ХХХ + i)) % bХ042504250425ХХ) {
                case 0:
                    break;
                default:
                    bХ0425ХХХХ = 43;
                    b04250425ХХХХ = bХ04250425ХХХ();
                    break;
            }
            stringBuilder.append(header.getName().toLowerCase()).append(NetworkUtils.DELIMITER_COLON).append(header.getValue()).append("\n");
        }
        return stringBuilder.toString();
    }

    private String b04490449щщ04490449(Header[] headerArr, String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AWS4-HMAC-SHA256").append("\n");
        stringBuilder.append(rcccrr.getYYYYMMDDTHHMMSSZ()).append("\n");
        stringBuilder.append(rcccrr.getYYYYMMDD()).append("/").append(rcccrr.b041B041BЛЛЛ041B).append("/").append(rcccrr.bЛЛ041BЛЛ041B).append("/aws4_request").append("\n");
        byte[] b0449щщ044904490449 = b0449щщ044904490449(bщщ0449щ04490449(headerArr, str));
        if (((bХ0425ХХХХ + bХХ0425ХХХ) * bХ0425ХХХХ) % b0425Х0425ХХХ() != b04250425ХХХХ) {
            bХ0425ХХХХ = bХ04250425ХХХ();
            b04250425ХХХХ = 55;
        }
        stringBuilder.append(bщщщ044904490449(b0449щщ044904490449));
        Log.d(b043Dннн043D043D, "StringToSign:\n" + stringBuilder.toString());
        return stringBuilder.toString();
    }

    private byte[] b0449щ0449щ04490449(String str, byte[] bArr) throws Exception {
        try {
            String str2 = "HmacSHA256";
            if (((bХ0425ХХХХ + bХХ0425ХХХ) * bХ0425ХХХХ) % bХ042504250425ХХ != b04250425ХХХХ) {
                bХ0425ХХХХ = 57;
                b04250425ХХХХ = bХ04250425ХХХ();
            }
            try {
                Mac instance = Mac.getInstance(str2);
                instance.init(new SecretKeySpec(bArr, str2));
                return instance.doFinal(str.getBytes(UrlUtils.UTF8));
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    private byte[] b0449щщ044904490449(String str) {
        try {
            byte[] digest = MessageDigest.getInstance(bн043Dнн043D043D).digest(str.getBytes());
            int i = bХ0425ХХХХ;
            switch ((i * (bХХ0425ХХХ + i)) % bХ042504250425ХХ) {
                case 0:
                    return digest;
                default:
                    bХ0425ХХХХ = 47;
                    b04250425ХХХХ = bХ04250425ХХХ();
                    return digest;
            }
        } catch (NoSuchAlgorithmException e) {
            try {
                e.printStackTrace();
                return null;
            } catch (Exception e2) {
                throw e2;
            }
        }
    }

    public static int bХ04250425ХХХ() {
        return 58;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String bщ04490449щ04490449() {
        /*
        r3 = this;
        r2 = 1;
        r0 = bХ0425ХХХХ;
        r1 = bХХ0425ХХХ;
        r0 = r0 + r1;
        r1 = bХ0425ХХХХ;
        r0 = r0 * r1;
        r1 = bХ042504250425ХХ;
        r0 = r0 % r1;
        r1 = b04250425ХХХХ;
        if (r0 == r1) goto L_0x001c;
    L_0x0010:
        r0 = bХ04250425ХХХ();
        bХ0425ХХХХ = r0;
        r0 = bХ04250425ХХХ();
        b04250425ХХХХ = r0;
    L_0x001c:
        switch(r2) {
            case 0: goto L_0x001c;
            case 1: goto L_0x0023;
            default: goto L_0x001f;
        };
    L_0x001f:
        switch(r2) {
            case 0: goto L_0x001c;
            case 1: goto L_0x0023;
            default: goto L_0x0022;
        };
    L_0x0022:
        goto L_0x001f;
    L_0x0023:
        r0 = "";
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.crrcrc.bщ04490449щ04490449():java.lang.String");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String bщщ0449щ04490449(org.apache.http.Header[] r5, java.lang.String r6) {
        /*
        r4 = this;
        r3 = 0;
        r0 = bХ04250425ХХХ();
        r1 = b0425Х04250425ХХ();
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = bХ042504250425ХХ;
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x001b;
            default: goto L_0x0011;
        };
    L_0x0011:
        r0 = 65;
        bХ0425ХХХХ = r0;
        r0 = bХ04250425ХХХ();
        b04250425ХХХХ = r0;
    L_0x001b:
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = "POST";
        r1 = r0.append(r1);
        r2 = "\n";
        r1.append(r2);
        r1 = "/";
        r1 = r0.append(r1);
        r2 = "\n";
        r1.append(r2);
        r1 = r4.bщ04490449щ04490449();
        r1 = r0.append(r1);
        r2 = "\n";
        r1.append(r2);
        r1 = r4.b044904490449щ04490449(r5);
        r1 = r0.append(r1);
        r2 = "\n";
        r1.append(r2);
        r1 = r4.getSignedHeaders(r5);
        r1 = r0.append(r1);
        r2 = "\n";
        r1.append(r2);
        r1 = r4.b0449щщ044904490449(r6);
        r1 = r4.bщщщ044904490449(r1);
        r0.append(r1);
        r1 = b043Dннн043D043D;
        r2 = new java.lang.StringBuilder;
    L_0x006c:
        switch(r3) {
            case 0: goto L_0x0073;
            case 1: goto L_0x006c;
            default: goto L_0x006f;
        };
    L_0x006f:
        switch(r3) {
            case 0: goto L_0x0073;
            case 1: goto L_0x006c;
            default: goto L_0x0072;
        };
    L_0x0072:
        goto L_0x006f;
    L_0x0073:
        r2.<init>();
        r3 = "CanonicalRequest string:\n";
        r2 = r2.append(r3);
        r3 = r0.toString();
        r2 = r2.append(r3);
        r2 = r2.toString();
        com.immersion.Log.d(r1, r2);
        r0 = r0.toString();
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.crrcrc.bщщ0449щ04490449(org.apache.http.Header[], java.lang.String):java.lang.String");
    }

    private String bщщщ044904490449(byte[] bArr) {
        int i = 0;
        int i2 = 3;
        String str = null;
        try {
            StringBuilder stringBuilder = new StringBuilder(bArr.length * 2);
            int length = bArr.length;
            while (i < length) {
                byte b = bArr[i];
                while (true) {
                    try {
                        str.length();
                    } catch (Exception e) {
                        bХ0425ХХХХ = bХ04250425ХХХ();
                        while (true) {
                            try {
                                i2 /= 0;
                            } catch (Exception e2) {
                                bХ0425ХХХХ = 65;
                                while (true) {
                                    try {
                                        str.length();
                                    } catch (Exception e3) {
                                        bХ0425ХХХХ = bХ04250425ХХХ();
                                        try {
                                            String str2 = "%02x";
                                            Object[] objArr = new Object[1];
                                            objArr[0] = Byte.valueOf(b);
                                            stringBuilder.append(String.format(str2, objArr));
                                            i++;
                                        } catch (Exception e4) {
                                            throw e4;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return stringBuilder.toString();
        } catch (Exception e42) {
            throw e42;
        }
    }

    public String generateSignature(String str, Header[] headerArr, String str2) {
        String str3 = null;
        try {
            String signatureKey = getSignatureKey(str, rcccrr.getYYYYMMDD(), rcccrr.b041B041BЛЛЛ041B, rcccrr.bЛЛ041BЛЛ041B, b04490449щщ04490449(headerArr, str2));
            Log.d(b043Dннн043D043D, "Signature:\n" + signatureKey);
            return signatureKey;
        } catch (Exception e) {
            while (true) {
                try {
                    str3.length();
                } catch (Exception e2) {
                    bХ0425ХХХХ = bХ04250425ХХХ();
                    while (true) {
                        try {
                            int[] iArr = new int[-1];
                        } catch (Exception e3) {
                            bХ0425ХХХХ = bХ04250425ХХХ();
                            int i = 2;
                            while (true) {
                                try {
                                    i /= 0;
                                } catch (Exception e4) {
                                    bХ0425ХХХХ = bХ04250425ХХХ();
                                    return null;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getSignatureKey(java.lang.String r4, java.lang.String r5, java.lang.String r6, java.lang.String r7, java.lang.String r8) throws java.lang.Exception {
        /*
        r3 = this;
        r1 = 0;
        r0 = new java.lang.StringBuilder;
    L_0x0003:
        switch(r1) {
            case 0: goto L_0x000a;
            case 1: goto L_0x0003;
            default: goto L_0x0006;
        };
    L_0x0006:
        switch(r1) {
            case 0: goto L_0x000a;
            case 1: goto L_0x0003;
            default: goto L_0x0009;
        };
    L_0x0009:
        goto L_0x0006;
    L_0x000a:
        r0.<init>();
        r1 = "AWS4";
        r0 = r0.append(r1);
        r0 = r0.append(r4);
        r0 = r0.toString();
        r1 = "UTF8";
        r0 = r0.getBytes(r1);
        r0 = r3.b0449щ0449щ04490449(r5, r0);
        r0 = r3.b0449щ0449щ04490449(r6, r0);
        r1 = bХ0425ХХХХ;
        r2 = bХХ0425ХХХ;
        r1 = r1 + r2;
        r2 = bХ0425ХХХХ;
        r1 = r1 * r2;
        r2 = bХ042504250425ХХ;
        r1 = r1 % r2;
        r2 = b04250425ХХХХ;
        if (r1 == r2) goto L_0x003f;
    L_0x0038:
        r1 = 6;
        bХ0425ХХХХ = r1;
        r1 = 74;
        b04250425ХХХХ = r1;
    L_0x003f:
        r0 = r3.b0449щ0449щ04490449(r7, r0);
        r1 = "aws4_request";
        r0 = r3.b0449щ0449щ04490449(r1, r0);
        r0 = r3.b0449щ0449щ04490449(r8, r0);
        r0 = r3.bщщщ044904490449(r0);
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.crrcrc.getSignatureKey(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String):java.lang.String");
    }

    public String getSignedHeaders(Header[] headerArr) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Header header : headerArr) {
            int i = bХ0425ХХХХ;
            switch ((i * (bХХ0425ХХХ + i)) % bХ042504250425ХХ) {
                case 0:
                    break;
                default:
                    bХ0425ХХХХ = bХ04250425ХХХ();
                    b04250425ХХХХ = 30;
                    break;
            }
            stringBuilder.append(header.getName().toLowerCase()).append(";");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
}
