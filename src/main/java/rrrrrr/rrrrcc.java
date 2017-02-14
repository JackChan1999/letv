package rrrrrr;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.immersion.aws.tvm.Token;

public class rrrrcc extends Token {
    private static final String b04140414ДДД0414 = "AWS_SECURITY_TOKEN";
    private static final String b0414Д0414ДД0414 = "PM_FILE_MD5";
    private static final String b0414ДДДД0414 = "AWS_ACCESS_KEY";
    public static int b043B043B043Bллл = 0;
    private static final String bД0414ДДД0414 = "AWS_SECRET_KEY";
    private static final String bДД0414ДД0414 = "AWS_EXPIRATION_DATE";
    public static int bл043Bлллл = 86;
    public static int bлл043Bллл = 1;
    public static int bллл043Bлл = 2;

    public rrrrcc() {
        if (((bл043Bлллл + bлл043Bллл) * bл043Bлллл) % b043Bл043Bллл() != b043B043Bлллл()) {
            bл043Bлллл = 17;
            bлл043Bллл = bл043B043Bллл();
        }
        try {
        } catch (Exception e) {
            throw e;
        }
    }

    public static int b043B043Bлллл() {
        return 0;
    }

    public static int b043Bл043Bллл() {
        return 2;
    }

    public static int bл043B043Bллл() {
        return 89;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void loadToken(android.content.SharedPreferences r4) {
        /*
        r3 = this;
        r1 = 0;
        r2 = 0;
        r0 = "AWS_ACCESS_KEY";
        r0 = r4.getString(r0, r2);
        r3.mAccessKey = r0;
        r0 = "AWS_SECRET_KEY";
        r0 = r4.getString(r0, r2);
        r3.mSecretKey = r0;
        r0 = "AWS_SECURITY_TOKEN";
        r0 = r4.getString(r0, r2);
        r3.mSecurityToken = r0;
        r0 = "AWS_EXPIRATION_DATE";
        r0 = r4.getString(r0, r2);
        r3.mExpirationDate = r0;
    L_0x0022:
        switch(r1) {
            case 0: goto L_0x0029;
            case 1: goto L_0x0022;
            default: goto L_0x0025;
        };
    L_0x0025:
        switch(r1) {
            case 0: goto L_0x0029;
            case 1: goto L_0x0022;
            default: goto L_0x0028;
        };
    L_0x0028:
        goto L_0x0025;
    L_0x0029:
        r0 = bл043Bлллл;
        r1 = bлл043Bллл;
        r0 = r0 + r1;
        r1 = bл043Bлллл;
        r0 = r0 * r1;
        r1 = bллл043Bлл;
        r0 = r0 % r1;
        r1 = b043B043Bлллл();
        if (r0 == r1) goto L_0x0044;
    L_0x003a:
        r0 = bл043B043Bллл();
        bл043Bлллл = r0;
        r0 = 88;
        b043B043B043Bллл = r0;
    L_0x0044:
        r0 = "PM_FILE_MD5";
        r0 = r4.getString(r0, r2);
        r3.mPolicyFileMD5 = r0;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.rrrrcc.loadToken(android.content.SharedPreferences):void");
    }

    public void storeToken(SharedPreferences sharedPreferences) {
        Editor edit = sharedPreferences.edit();
        edit.putString(b0414ДДДД0414, this.mAccessKey);
        edit.putString(bД0414ДДД0414, this.mSecretKey);
        edit.putString(b04140414ДДД0414, this.mSecurityToken);
        edit.putString(bДД0414ДД0414, this.mExpirationDate);
        edit.putString(b0414Д0414ДД0414, this.mPolicyFileMD5);
        edit.commit();
        if (((bл043Bлллл + bлл043Bллл) * bл043Bлллл) % bллл043Bлл != b043B043B043Bллл) {
            bл043Bлллл = bл043B043Bллл();
            b043B043B043Bллл = bл043B043Bллл();
        }
    }
}
