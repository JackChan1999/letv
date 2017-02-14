package com.letv.mobile.http.utils;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import java.util.ArrayList;
import java.util.Iterator;

public final class NetworkUtil {
    public static final int NETWORK_OPERATOR_CMCC = 1;
    public static final int NETWORK_OPERATOR_CTCC = 3;
    public static final int NETWORK_OPERATOR_CUCC = 2;
    public static final int NETWORK_OPERATOR_NONE = 0;
    public static final int NETWORK_OPERATOR_UNKOWN = 4;
    public static final int NETWORK_TYPE_2G = 2;
    public static final int NETWORK_TYPE_3G = 3;
    public static final int NETWORK_TYPE_4G = 4;
    public static final int NETWORK_TYPE_MOBILE = 5;
    public static final int NETWORK_TYPE_NONE = 0;
    public static final int NETWORK_TYPE_WIFI = 1;
    private static final BroadcastReceiver sBroadcastReceiver = new 1();
    private static final ArrayList<OnNetworkChangeListener> sNetworkChangeListeners = new ArrayList();

    public interface OnNetworkChangeListener {
        void onNetworkChanged();
    }

    private NetworkUtil() {
    }

    public static void init() {
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            ContextProvider.getApplicationContext().registerReceiver(sBroadcastReceiver, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void notifyListeners() {
        Iterator it = ((ArrayList) sNetworkChangeListeners.clone()).iterator();
        while (it.hasNext()) {
            OnNetworkChangeListener listener = (OnNetworkChangeListener) it.next();
            if (listener != null) {
                listener.onNetworkChanged();
            }
        }
    }

    public static synchronized void registerNetworkChangeListener(OnNetworkChangeListener listener) {
        synchronized (NetworkUtil.class) {
            if (listener != null) {
                if (!sNetworkChangeListeners.contains(listener)) {
                    sNetworkChangeListeners.add(listener);
                }
            }
        }
    }

    public static synchronized void unregisterNetworkChangeListener(OnNetworkChangeListener listener) {
        synchronized (NetworkUtil.class) {
            if (listener != null) {
                if (sNetworkChangeListeners.contains(listener)) {
                    sNetworkChangeListeners.remove(listener);
                }
            }
        }
    }

    public static boolean isNetAvailable() {
        return getNetworkType() != 0;
    }

    public static int getNetworkType() {
        NetworkInfo networkInfo = ((ConnectivityManager) ContextProvider.getApplicationContext().getSystemService("connectivity")).getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isAvailable()) {
            return 4;
        }
        if (1 == networkInfo.getType()) {
            return 1;
        }
        switch (((TelephonyManager) ContextProvider.getApplicationContext().getSystemService("phone")).getNetworkType()) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return 2;
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                return 3;
            case 13:
                return 4;
            default:
                return 5;
        }
    }

    public static boolean isWIFI() {
        if (getNetworkType() == 1) {
            return true;
        }
        return false;
    }

    public static boolean isMobileNet() {
        switch (getNetworkType()) {
            case 2:
            case 3:
            case 4:
            case 5:
                return true;
            default:
                return false;
        }
    }
}
