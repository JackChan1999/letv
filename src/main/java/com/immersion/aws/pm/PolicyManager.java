package com.immersion.aws.pm;

import android.content.Context;
import com.immersion.Log;
import com.immersion.aws.Authenticator.Listener;
import com.immersion.aws.tvm.Token;
import java.util.List;
import rrrrrr.cccrcc;
import rrrrrr.crccrr;
import rrrrrr.rcrcrc;

public class PolicyManager implements Listener {
    public static int b04110411ББ04110411 = 94;
    public static int b0411Б0411Б04110411 = 1;
    private static final String b0414ДД04140414Д = PolicyManager.class.getSimpleName();
    public static int bБ04110411Б04110411 = 2;
    public static int bББ0411Б04110411 = 0;
    private static PolicyManager bД0414Д04140414Д = null;
    public static final String bДДДДД0414 = "IMMR_PM_SHARED_PREF";
    public String b04140414041404140414Д = "";
    public rcrcrc b04140414Д04140414Д = rcrcrc.NOT_INITIALIZED;
    public cccrcc b0414Д041404140414Д;
    public List bД0414041404140414Д;
    public NativePolicyManager bДД041404140414Д;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = -1;
        r1 = 0;
    L_0x0002:
        r1.length();	 Catch:{ Exception -> 0x0006 }
        goto L_0x0002;
    L_0x0006:
        r1 = move-exception;
        r1 = 14;
        b04110411ББ04110411 = r1;
    L_0x000b:
        r1 = new int[r0];	 Catch:{ Exception -> 0x0017 }
        goto L_0x000b;
    L_0x000e:
        r0 = com.immersion.aws.pm.PolicyManager.class;
        r0 = r0.getSimpleName();
        b0414ДД04140414Д = r0;
        return;
    L_0x0017:
        r0 = move-exception;
        r0 = b0411ББ041104110411();
        b04110411ББ04110411 = r0;
    L_0x001e:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x000e;
            case 1: goto L_0x001e;
            default: goto L_0x0022;
        };
    L_0x0022:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x001e;
            case 1: goto L_0x000e;
            default: goto L_0x0026;
        };
    L_0x0026:
        goto L_0x0022;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.aws.pm.PolicyManager.<clinit>():void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public PolicyManager() {
        /*
        r4 = this;
        r3 = 58;
        r4.<init>();
        r0 = "";
        r4.b04140414041404140414Д = r0;
        r0 = rrrrrr.rcrcrc.NOT_INITIALIZED;
        r4.b04140414Д04140414Д = r0;
    L_0x000d:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x0016;
            case 1: goto L_0x000d;
            default: goto L_0x0011;
        };
    L_0x0011:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x000d;
            case 1: goto L_0x0016;
            default: goto L_0x0015;
        };
    L_0x0015:
        goto L_0x0011;
    L_0x0016:
        r0 = new java.util.ArrayList;
        r0.<init>();
        r1 = b04110411ББ04110411;
        r2 = b0411Б0411Б04110411;
        r1 = r1 + r2;
        r2 = b04110411ББ04110411;
        r1 = r1 * r2;
        r2 = bБ04110411Б04110411;
        r1 = r1 % r2;
        r2 = bББ0411Б04110411;
        if (r1 == r2) goto L_0x002e;
    L_0x002a:
        b04110411ББ04110411 = r3;
        bББ0411Б04110411 = r3;
    L_0x002e:
        r4.bД0414041404140414Д = r0;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.aws.pm.PolicyManager.<init>():void");
    }

    public static int b041104110411Б04110411() {
        return 0;
    }

    public static int b0411ББ041104110411() {
        return 53;
    }

    public static int bБББ041104110411() {
        return 1;
    }

    public void bйй0439ййй(String str) {
        try {
            List<crccrr> list = this.bД0414041404140414Д;
            if (((b04110411ББ04110411 + b0411Б0411Б04110411) * b04110411ББ04110411) % bБ04110411Б04110411 != bББ0411Б04110411) {
                b04110411ББ04110411 = 81;
                bББ0411Б04110411 = 81;
            }
            for (crccrr onPrepareContent : list) {
                try {
                    onPrepareContent.onPrepareContent(str);
                } catch (Exception e) {
                    throw e;
                }
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public synchronized void dispose() {
        try {
            if (this.bДД041404140414Д != null) {
                this.bДД041404140414Д.dispose();
                this.bД0414041404140414Д.clear();
            }
            if (this.b04140414Д04140414Д == rcrcrc.INITIALIZED) {
                this.b04140414041404140414Д = "";
                this.b0414Д041404140414Д.bй04390439ййй();
                int i = b04110411ББ04110411;
                switch ((i * (b0411Б0411Б04110411 + i)) % bБ04110411Б04110411) {
                    case 0:
                        break;
                    default:
                        b04110411ББ04110411 = 17;
                        bББ0411Б04110411 = b0411ББ041104110411();
                        break;
                }
                this.bДД041404140414Д.dispose();
                this.bД0414041404140414Д.clear();
                this.b04140414Д04140414Д = rcrcrc.NOT_INITIALIZED;
            }
        } catch (Exception e) {
            throw e;
        } catch (Exception e2) {
            throw e2;
        }
        return;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized long getNativeInstance() {
        /*
        r3 = this;
        r2 = 1;
        monitor-enter(r3);
        r0 = b04110411ББ04110411;	 Catch:{ all -> 0x0030 }
        r1 = b0411Б0411Б04110411;	 Catch:{ all -> 0x0030 }
        r0 = r0 + r1;
        r1 = b04110411ББ04110411;	 Catch:{ all -> 0x0030 }
        r0 = r0 * r1;
        r1 = bБ04110411Б04110411;	 Catch:{ all -> 0x0030 }
        r0 = r0 % r1;
        r1 = b041104110411Б04110411();	 Catch:{ all -> 0x0030 }
        if (r0 == r1) goto L_0x001a;
    L_0x0013:
        r0 = 5;
        b04110411ББ04110411 = r0;	 Catch:{ all -> 0x0030 }
        r0 = 84;
        bББ0411Б04110411 = r0;	 Catch:{ all -> 0x0030 }
    L_0x001a:
        r0 = r3.bДД041404140414Д;	 Catch:{ all -> 0x0030 }
        if (r0 != 0) goto L_0x0022;
    L_0x001e:
        r0 = 0;
    L_0x0020:
        monitor-exit(r3);
        return r0;
    L_0x0022:
        switch(r2) {
            case 0: goto L_0x0022;
            case 1: goto L_0x0029;
            default: goto L_0x0025;
        };
    L_0x0025:
        switch(r2) {
            case 0: goto L_0x0022;
            case 1: goto L_0x0029;
            default: goto L_0x0028;
        };
    L_0x0028:
        goto L_0x0025;
    L_0x0029:
        r0 = r3.bДД041404140414Д;	 Catch:{ all -> 0x0030 }
        r0 = r0.b0449щщщ04490449();	 Catch:{ all -> 0x0030 }
        goto L_0x0020;
    L_0x0030:
        r0 = move-exception;
        monitor-exit(r3);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.aws.pm.PolicyManager.getNativeInstance():long");
    }

    public void init(Context context, PolicyPathInfo policyPathInfo, String str) throws OutOfMemoryError, UnsatisfiedLinkError {
        if (this.b04140414Д04140414Д != rcrcrc.INITIALIZED) {
            this.bДД041404140414Д = new NativePolicyManager(str, policyPathInfo, context.getSharedPreferences(bДДДДД0414, 0));
            this.bД0414041404140414Д.add(this.bДД041404140414Д);
            String policyFilePath = policyPathInfo.getPolicyFilePath();
            if (((b04110411ББ04110411 + bБББ041104110411()) * b04110411ББ04110411) % bБ04110411Б04110411 != bББ0411Б04110411) {
                b04110411ББ04110411 = 67;
                bББ0411Б04110411 = b0411ББ041104110411();
            }
            this.b0414Д041404140414Д = new cccrcc(policyFilePath);
            this.b0414Д041404140414Д.b0439й0439ййй(this.bДД041404140414Д);
            this.b04140414Д04140414Д = rcrcrc.INITIALIZED;
        }
    }

    public void onAuthorizationFailure() {
        synchronized (this) {
            if (this.b04140414Д04140414Д == rcrcrc.INITIALIZED) {
                this.b0414Д041404140414Д.notifyAuthorizationFailure();
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onAuthorizationSuccess(com.immersion.aws.tvm.Token r3) {
        /*
        r2 = this;
        monitor-enter(r2);
        r0 = r2.b04140414Д04140414Д;	 Catch:{ all -> 0x0017 }
        r1 = rrrrrr.rcrcrc.INITIALIZED;	 Catch:{ all -> 0x0017 }
        if (r0 != r1) goto L_0x0015;
    L_0x0007:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x0010;
            case 1: goto L_0x0007;
            default: goto L_0x000b;
        };	 Catch:{ all -> 0x0017 }
    L_0x000b:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x0007;
            case 1: goto L_0x0010;
            default: goto L_0x000f;
        };	 Catch:{ all -> 0x0017 }
    L_0x000f:
        goto L_0x000b;
    L_0x0010:
        r0 = r2.b0414Д041404140414Д;	 Catch:{ all -> 0x0017 }
        r0.notifyAuthorizationSuccess(r3);	 Catch:{ all -> 0x0017 }
    L_0x0015:
        monitor-exit(r2);	 Catch:{ all -> 0x0017 }
        return;
    L_0x0017:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0017 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.aws.pm.PolicyManager.onAuthorizationSuccess(com.immersion.aws.tvm.Token):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onAuthorizationTimeout() {
        /*
        r4 = this;
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
        r0 = r4.b04140414Д04140414Д;
        r1 = rrrrrr.rcrcrc.INITIALIZED;
        r2 = b04110411ББ04110411;
        r3 = b0411Б0411Б04110411;
        r3 = r3 + r2;
        r2 = r2 * r3;
        r3 = bБ04110411Б04110411;
        r2 = r2 % r3;
        switch(r2) {
            case 0: goto L_0x0023;
            default: goto L_0x0019;
        };
    L_0x0019:
        r2 = b0411ББ041104110411();
        b04110411ББ04110411 = r2;
        r2 = 65;
        bББ0411Б04110411 = r2;
    L_0x0023:
        if (r0 != r1) goto L_0x002a;
    L_0x0025:
        r0 = r4.b0414Д041404140414Д;
        r0.notifyAuthorizationTimeout();
    L_0x002a:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.aws.pm.PolicyManager.onAuthorizationTimeout():void");
    }

    public void onAuthorizing(Token token) {
        synchronized (this) {
            if (this.b04140414Д04140414Д == rcrcrc.INITIALIZED) {
                this.b0414Д041404140414Д.notifyAuthorizing(token);
            }
        }
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

    public synchronized void prepareContentID(String str) {
        int i = 1;
        try {
            Log.d(b0414ДД04140414Д, "ContentUUID: " + str);
            if (str != null) {
                if (str.compareTo(this.b04140414041404140414Д) == 0) {
                    while (true) {
                        i /= 0;
                    }
                }
            }
            bйй0439ййй(str);
            this.b04140414041404140414Д = str;
        } catch (Exception e) {
            throw e;
        } catch (Exception e2) {
            throw e2;
        } catch (Exception e3) {
            b04110411ББ04110411 = b0411ББ041104110411();
        }
    }
}
