package com.immersion.aws.tvm;

import android.content.SharedPreferences;
import com.immersion.Log;
import com.immersion.aws.Authenticator;
import com.immersion.aws.Authenticator.Listener;
import com.immersion.aws.Credential;
import com.immersion.aws.tvm.Token.Type;
import java.util.List;
import org.apache.http.auth.AuthenticationException;
import rrrrrr.ccccrr;
import rrrrrr.cccrrr;
import rrrrrr.rrcrcc;

public class TVMAuthenticator implements Authenticator {
    public static int b04250425Х042504250425 = 1;
    private static final String bДД04140414ДД;
    public static int bХ04250425042504250425 = b0425Х0425042504250425();
    public static int bХ0425Х042504250425 = b0425Х0425042504250425();
    public static int bХХ0425042504250425 = 2;
    private SharedPreferences b04140414Д0414ДД;
    private List b0414Д04140414ДД;
    private String bД041404140414ДД;
    private Credential bД0414Д0414ДД;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
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
        r0 = com.immersion.aws.tvm.TVMAuthenticator.class;
        r0 = r0.getSimpleName();
        r1 = bХ0425Х042504250425;
        r2 = b04250425Х042504250425;
        r1 = r1 + r2;
        r2 = bХ0425Х042504250425;
        r1 = r1 * r2;
        r2 = bХХ0425042504250425;
        r1 = r1 % r2;
        r2 = bХ04250425042504250425;
        if (r1 == r2) goto L_0x002a;
    L_0x001e:
        r1 = b0425Х0425042504250425();
        bХ0425Х042504250425 = r1;
        r1 = b0425Х0425042504250425();
        bХ04250425042504250425 = r1;
    L_0x002a:
        bДД04140414ДД = r0;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.aws.tvm.TVMAuthenticator.<clinit>():void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public TVMAuthenticator(android.content.Context r3, com.immersion.aws.Credential r4, java.lang.String r5) {
        /*
        r2 = this;
        r1 = 1;
        r2.<init>();
        r0 = r4.getUserName();
    L_0x0008:
        switch(r1) {
            case 0: goto L_0x0008;
            case 1: goto L_0x000f;
            default: goto L_0x000b;
        };
    L_0x000b:
        switch(r1) {
            case 0: goto L_0x0008;
            case 1: goto L_0x000f;
            default: goto L_0x000e;
        };
    L_0x000e:
        goto L_0x000b;
    L_0x000f:
        r1 = r4.getPassword();
        r0 = rrrrrr.rcrrrr.bьь044Cь044Cь(r0, r1);
        r1 = 0;
        r0 = r3.getSharedPreferences(r0, r1);
        r2.b04140414Д0414ДД = r0;
        r2.bД0414Д0414ДД = r4;
        r0 = bХ0425Х042504250425;
        r1 = b04250425Х042504250425;
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = bХХ0425042504250425;
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x0036;
            default: goto L_0x002c;
        };
    L_0x002c:
        r0 = b0425Х0425042504250425();
        bХ0425Х042504250425 = r0;
        r0 = 36;
        b04250425Х042504250425 = r0;
    L_0x0036:
        r0 = new java.util.ArrayList;
        r0.<init>();
        r2.b0414Д04140414ДД = r0;
        r2.bД041404140414ДД = r5;
        if (r5 == 0) goto L_0x004f;
    L_0x0041:
        r0 = r5.isEmpty();
        if (r0 != 0) goto L_0x004f;
    L_0x0047:
        r0 = "default";
        r0 = r5.equalsIgnoreCase(r0);
        if (r0 == 0) goto L_0x0055;
    L_0x004f:
        r0 = rrrrrr.ccccrr.getTokenVendingMachineURL();
        r2.bД041404140414ДД = r0;
    L_0x0055:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.aws.tvm.TVMAuthenticator.<init>(android.content.Context, com.immersion.aws.Credential, java.lang.String):void");
    }

    public static int b0411БББББ() {
        return 0;
    }

    public static int b0425Х0425042504250425() {
        return 45;
    }

    private void b0439ййййй(Token token) {
        int b0425Х0425042504250425 = b0425Х0425042504250425();
        switch ((b0425Х0425042504250425 * (b04250425Х042504250425 + b0425Х0425042504250425)) % bХХ0425042504250425) {
            case 0:
                break;
            default:
                bХ0425Х042504250425 = 34;
                bХ04250425042504250425 = 0;
                break;
        }
        try {
            for (Listener onAuthorizing : this.b0414Д04140414ДД) {
                try {
                    onAuthorizing.onAuthorizing(token);
                } catch (Exception e) {
                    throw e;
                }
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    private void b0449щ0449044904490449() {
        for (Listener onAuthorizationFailure : this.b0414Д04140414ДД) {
            onAuthorizationFailure.onAuthorizationFailure();
        }
    }

    private void bщ04490449044904490449() {
        for (Listener onAuthorizationTimeout : this.b0414Д04140414ДД) {
            while (true) {
                try {
                    int[] iArr = new int[-1];
                } catch (Exception e) {
                    bХ0425Х042504250425 = 40;
                    onAuthorizationTimeout.onAuthorizationTimeout();
                }
            }
        }
    }

    private void bщщ0449044904490449(Token token) {
        try {
            for (Object next : this.b0414Д04140414ДД) {
                try {
                    if (((b0425Х0425042504250425() + b04250425Х042504250425) * b0425Х0425042504250425()) % bХХ0425042504250425 != b0411БББББ()) {
                        bХ0425Х042504250425 = b0425Х0425042504250425();
                        bХ04250425042504250425 = b0425Х0425042504250425();
                    }
                    ((Listener) next).onAuthorizationSuccess(token);
                } catch (Exception e) {
                    throw e;
                }
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void addListener(com.immersion.aws.Authenticator.Listener r4) {
        /*
        r3 = this;
        monitor-enter(r3);
        r0 = r3.b0414Д04140414ДД;	 Catch:{ all -> 0x0028 }
    L_0x0003:
        r1 = 1;
        switch(r1) {
            case 0: goto L_0x0003;
            case 1: goto L_0x000c;
            default: goto L_0x0007;
        };	 Catch:{ all -> 0x0028 }
    L_0x0007:
        r1 = 0;
        switch(r1) {
            case 0: goto L_0x000c;
            case 1: goto L_0x0003;
            default: goto L_0x000b;
        };	 Catch:{ all -> 0x0028 }
    L_0x000b:
        goto L_0x0007;
    L_0x000c:
        r1 = bХ0425Х042504250425;	 Catch:{ all -> 0x0028 }
        r2 = b04250425Х042504250425;	 Catch:{ all -> 0x0028 }
        r1 = r1 + r2;
        r2 = bХ0425Х042504250425;	 Catch:{ all -> 0x0028 }
        r1 = r1 * r2;
        r2 = bХХ0425042504250425;	 Catch:{ all -> 0x0028 }
        r1 = r1 % r2;
        r2 = bХ04250425042504250425;	 Catch:{ all -> 0x0028 }
        if (r1 == r2) goto L_0x0023;
    L_0x001b:
        r1 = 23;
        bХ0425Х042504250425 = r1;	 Catch:{ all -> 0x0028 }
        r1 = 98;
        bХ04250425042504250425 = r1;	 Catch:{ all -> 0x0028 }
    L_0x0023:
        r0.add(r4);	 Catch:{ all -> 0x0028 }
        monitor-exit(r3);
        return;
    L_0x0028:
        r0 = move-exception;
        monitor-exit(r3);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.aws.tvm.TVMAuthenticator.addListener(com.immersion.aws.Authenticator$Listener):void");
    }

    public Token authenticate() throws AuthenticationException {
        Token bьььь044Cь = Token.bьььь044Cь(Type.SHORT_TERM);
        bьььь044Cь.loadToken(this.b04140414Д0414ДД);
        b0439ййййй(bьььь044Cь);
        if (bьььь044Cь.isTokenExpired()) {
            Log.d(bДД04140414ДД, "Credentials expired or do not exist. Requesting new token.");
            rrcrcc login = new cccrrr(this.b04140414Д0414ДД, this.bД041404140414ДД, ccccrr.getAppName(), ccccrr.useSSL()).login(this.bД0414Д0414ДД.getUserName(), this.bД0414Д0414ДД.getPassword());
            if (login.requestUnauthorized()) {
                b0449щ0449044904490449();
                throw new AuthenticationException();
            } else if (login.requestTimedOut()) {
                bщ04490449044904490449();
                if (((bХ0425Х042504250425 + b04250425Х042504250425) * bХ0425Х042504250425) % bХХ0425042504250425 != bХ04250425042504250425) {
                    bХ0425Х042504250425 = 30;
                    bХ04250425042504250425 = 78;
                }
                return bьььь044Cь;
            }
        }
        bщщ0449044904490449(bьььь044Cь);
        return bьььь044Cь;
    }
}
