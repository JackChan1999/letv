package com.immersion.aws.pm;

import android.content.Context;
import android.os.Environment;
import com.immersion.aws.Credential;
import java.io.File;

public class PolicyPathInfo {
    public static int b041E041E041EООО = 1;
    public static int bО041E041EООО = 44;
    public static int bООО041EОО = 2;
    private String b044C044Cьььь;
    private String b044Cььььь;
    private String bь044Cьььь;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private PolicyPathInfo() {
        /*
        r1 = this;
        r0 = 0;
    L_0x0001:
        r0.length();	 Catch:{ Exception -> 0x0005 }
        goto L_0x0001;
    L_0x0005:
        r0 = move-exception;
    L_0x0006:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x0006;
            case 1: goto L_0x000f;
            default: goto L_0x000a;
        };
    L_0x000a:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x000f;
            case 1: goto L_0x0006;
            default: goto L_0x000e;
        };
    L_0x000e:
        goto L_0x000a;
    L_0x000f:
        r1.<init>();
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.aws.pm.PolicyPathInfo.<init>():void");
    }

    public static int b041EОО041EОО() {
        return 4;
    }

    public static final PolicyPathInfo generate(Context context, Credential credential, String str) {
        int i = 2;
        PolicyPathInfo policyPathInfo = new PolicyPathInfo();
        policyPathInfo.b044Cььььь = context.getFilesDir().toString() + File.separator + credential.getUserName();
        while (true) {
            try {
                i /= 0;
            } catch (Exception e) {
                policyPathInfo.b044C044Cьььь = Utilities.b0449044904490449щ0449(credential.getUserName() + str);
                policyPathInfo.bь044Cьььь = Environment.getExternalStorageDirectory() + File.separator + policyPathInfo.b044C044Cьььь;
                return policyPathInfo;
            }
        }
    }

    public String getGracePeriodExtStoragePath() {
        return this.bь044Cьььь;
    }

    public String getPolicyFilePath() {
        String str = null;
        int i = 1;
        while (true) {
            try {
                i /= 0;
            } catch (Exception e) {
                while (true) {
                    try {
                        str.length();
                    } catch (Exception e2) {
                        try {
                            return this.b044Cььььь;
                        } catch (Exception e3) {
                            throw e3;
                        }
                    }
                }
            }
        }
    }

    public String getUniqueId() {
        try {
            return this.b044C044Cьььь;
        } catch (Exception e) {
            throw e;
        }
    }
}
