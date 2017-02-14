package com.soundink.lib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class ConnectionChangeReceiver extends BroadcastReceiver {
    private String[] a;

    public final void onReceive(Context context, Intent intent) {
        if (context != null) {
            if (a.a == null || a.a.equals("")) {
                new a(context).execute(new Void[0]);
            }
            if (SoundInkInterface.getReceiveSignalAtNoNetWork()) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                connectivityManager.getNetworkInfo(0);
                if (activeNetworkInfo != null) {
                    e.a(context);
                    this.a = e.c();
                    if (this.a.length != 0) {
                        new Thread(new i(this.a)).start();
                    }
                }
            }
        }
    }
}
