package com.letv.mobile.lebox.heartbeat;

import java.util.Observable;
import java.util.Observer;

public abstract class HeartbeatObserver implements Observer {
    public static final int NOTIFICATION_TYPE_COMPLETED_VERSION = 0;
    public static final int NOTIFICATION_TYPE_DOWNLOADING_TASK = 2;
    public static final int NOTIFICATION_TYPE_HTTP_REQUEST_CHANGE = 5;
    public static final int NOTIFICATION_TYPE_INIT_USER_PERMISSION_SUCCESS = 8;
    public static final int NOTIFICATION_TYPE_LEBOX_CONNECTED = 13;
    public static final int NOTIFICATION_TYPE_LEBOX_CONNECT_SSID_CHANGE = 15;
    public static final int NOTIFICATION_TYPE_LEBOX_INTERNET_STATE_CHANGE = 14;
    public static final int NOTIFICATION_TYPE_NET_STATE_CHANGE = 4;
    public static final int NOTIFICATION_TYPE_P2P_CONNECTIVITY_CHANGE = 6;
    public static final int NOTIFICATION_TYPE_PERMISSION_CHANGE = 9;
    public static final int NOTIFICATION_TYPE_SERVICE_BIND_CHANGE = 7;
    public static final int NOTIFICATION_TYPE_UNFINISHED_VERSION = 1;
    public static final int NOTIFICATION_TYPE_UPDATE_STATE = 3;
    public static final int NOTIFICATION_TYPE_WIFI_P2P_ALREADY_FOUND = 12;
    public static final int NOTIFICATION_TYPE_WIFI_SCAN_RESULTS = 11;
    public static final int NOTIFICATION_TYPE_WIFI_STATE_CHANGED = 10;

    public abstract void change(int i);

    public void update(Observable observable, Object data) {
        change(((Integer) data).intValue());
    }
}
