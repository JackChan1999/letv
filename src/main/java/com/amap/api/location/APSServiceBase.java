package com.amap.api.location;

import android.content.Intent;
import android.os.Handler;

public interface APSServiceBase {
    public static final int GPSFUSION = 4;
    public static final int INIT = 0;
    public static final int LOCATION = 1;
    public static final int OFFLOCFUSION = 5;
    public static final int STARTCOLL = 6;
    public static final int STARTSCOKET = 2;
    public static final int STOPSCOKET = 3;

    Handler getHandler();

    void onCreate();

    void onDestroy();

    int onStartCommand(Intent intent, int i, int i2);
}
