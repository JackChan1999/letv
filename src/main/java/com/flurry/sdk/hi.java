package com.flurry.sdk;

import android.telephony.TelephonyManager;

public class hi {
    private static hi a;
    private static final String b = hi.class.getSimpleName();

    public static synchronized hi a() {
        hi hiVar;
        synchronized (hi.class) {
            if (a == null) {
                a = new hi();
            }
            hiVar = a;
        }
        return hiVar;
    }

    public static void b() {
        a = null;
    }

    private hi() {
    }

    public String c() {
        TelephonyManager telephonyManager = (TelephonyManager) hn.a().c().getSystemService("phone");
        if (telephonyManager == null) {
            return null;
        }
        return telephonyManager.getNetworkOperatorName();
    }

    public String d() {
        TelephonyManager telephonyManager = (TelephonyManager) hn.a().c().getSystemService("phone");
        if (telephonyManager == null) {
            return null;
        }
        return telephonyManager.getNetworkOperator();
    }
}
