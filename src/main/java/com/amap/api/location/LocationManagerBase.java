package com.amap.api.location;

import android.app.PendingIntent;

public interface LocationManagerBase {
    public static final int ADD_GEOFENCE = 6;
    public static final int DESTROY = 11;
    public static final int REMOVE_GEOFENCE = 7;
    public static final int REMOVE_GEOFENCE_ONE = 10;
    public static final int REMOVE_LISTENER = 5;
    public static final int SET_LISTENER = 2;
    public static final int SET_OPTION = 1;
    public static final int START_LOCATION = 3;
    public static final int START_SOCKET = 8;
    public static final int STOP_LOCATION = 4;
    public static final int STOP_SOCKET = 9;

    void addGeoFenceAlert(String str, double d, double d2, float f, long j, PendingIntent pendingIntent);

    AMapLocation getLastKnownLocation();

    String getVersion();

    boolean isStarted();

    void onDestroy();

    void removeGeoFenceAlert(PendingIntent pendingIntent);

    void removeGeoFenceAlert(PendingIntent pendingIntent, String str);

    void setLocationListener(AMapLocationListener aMapLocationListener);

    void setLocationOption(AMapLocationClientOption aMapLocationClientOption);

    void startAssistantLocation();

    void startLocation();

    void stopAssistantLocation();

    void stopLocation();

    void unRegisterLocationListener(AMapLocationListener aMapLocationListener);
}
