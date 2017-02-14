package rrrrrr;

import java.security.AlgorithmParameters;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

public class rrrccc {
    public static final String ENCRYPTION_ALGORITHM = "AES/CBC/PKCS5Padding";
    public static int b04290429ЩЩ0429Щ = 1;
    public static int b0429Щ0429Щ0429Щ = 13;
    public static int bЩ0429ЩЩ0429Щ = 0;
    public static int bЩЩ0429Щ0429Щ = 2;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public rrrccc() {
        /*
        r2 = this;
    L_0x0000:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x0009;
            case 1: goto L_0x0000;
            default: goto L_0x0004;
        };
    L_0x0004:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x0000;
            case 1: goto L_0x0009;
            default: goto L_0x0008;
        };
    L_0x0008:
        goto L_0x0004;
    L_0x0009:
        r0 = b0429ЩЩЩ0429Щ();
        r1 = b04290429ЩЩ0429Щ;
        r0 = r0 + r1;
        r1 = b0429ЩЩЩ0429Щ();
        r0 = r0 * r1;
        r1 = bЩЩ0429Щ0429Щ;
        r0 = r0 % r1;
        r1 = bЩ0429ЩЩ0429Щ;
        if (r0 == r1) goto L_0x0020;
    L_0x001c:
        r0 = 56;
        bЩ0429ЩЩ0429Щ = r0;
    L_0x0020:
        r2.<init>();
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.rrrccc.<init>():void");
    }

    public static int b0429ЩЩЩ0429Щ() {
        return 59;
    }

    private static SecretKeySpec bй0439йй0439й(String str) throws Exception {
        String str2 = null;
        while (true) {
            try {
                str2.length();
            } catch (Exception e) {
                b0429Щ0429Щ0429Щ = b0429ЩЩЩ0429Щ();
                try {
                    return new SecretKeySpec(Hex.decodeHex(str.toCharArray()), "AES");
                } catch (Exception e2) {
                    throw e2;
                }
            }
        }
    }

    public static byte[] decrypt(byte[] bArr, String str, byte[] bArr2) throws Exception {
        Cipher instance = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        AlgorithmParameters instance2 = AlgorithmParameters.getInstance("AES");
        instance2.init(new IvParameterSpec(bArr2));
        Key bй0439йй0439й = bй0439йй0439й(str);
        if (((b0429Щ0429Щ0429Щ + b04290429ЩЩ0429Щ) * b0429Щ0429Щ0429Щ) % bЩЩ0429Щ0429Щ != bЩ0429ЩЩ0429Щ) {
            b0429Щ0429Щ0429Щ = 87;
            bЩ0429ЩЩ0429Щ = 42;
        }
        instance.init(2, bй0439йй0439й, instance2);
        return instance.doFinal(bArr);
    }

    public static String unwrap(String str, String str2) throws Exception {
        try {
            Object decodeBase64 = Base64.decodeBase64(str.getBytes());
            try {
                Object obj = new byte[16];
                Object obj2 = new byte[(decodeBase64.length - 16)];
                if (((b0429Щ0429Щ0429Щ + b04290429ЩЩ0429Щ) * b0429Щ0429Щ0429Щ) % bЩЩ0429Щ0429Щ != bЩ0429ЩЩ0429Щ) {
                    b0429Щ0429Щ0429Щ = b0429ЩЩЩ0429Щ();
                    bЩ0429ЩЩ0429Щ = b0429ЩЩЩ0429Щ();
                }
                System.arraycopy(decodeBase64, 0, obj, 0, 16);
                System.arraycopy(decodeBase64, 16, obj2, 0, decodeBase64.length - 16);
                return new String(decrypt(obj2, str2, obj));
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }
}
