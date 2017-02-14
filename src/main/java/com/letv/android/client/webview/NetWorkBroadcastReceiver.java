package com.letv.android.client.webview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NetWorkBroadcastReceiver extends BroadcastReceiver {
    private static List<NetWorkChangeObserver> netWorkChangeObservers = new ArrayList();

    public static synchronized void registerObserver(NetWorkChangeObserver observer) {
        synchronized (NetWorkBroadcastReceiver.class) {
            if (!netWorkChangeObservers.contains(observer)) {
                netWorkChangeObservers.add(observer);
            }
        }
    }

    public static synchronized void unRegisterObserver(NetWorkChangeObserver observer) {
        synchronized (NetWorkBroadcastReceiver.class) {
            if (netWorkChangeObservers.contains(observer)) {
                netWorkChangeObservers.remove(observer);
            }
        }
    }

    public void notifyAllObservers(HashMap<String, Object> netMap) {
        for (NetWorkChangeObserver observer : netWorkChangeObservers) {
            observer.onNetTypeChange(netMap);
        }
    }

    public void onReceive(Context context, Intent intent) {
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            LogInfo.log("lxx", "NetWorkBroadcastReceiver onReceive CONNECTIVITY_ACTION");
            int oldNetType = LetvBaseWebViewActivity.netType;
            int currentNetType = NetworkUtils.getNetworkType();
            if (oldNetType == -1) {
                LogInfo.log("lxx", "oldNetType为空！BaseWebView初始化有问题！");
            } else if (oldNetType != currentNetType) {
                HashMap<String, Object> netMap = new HashMap();
                netMap.put("pre", NetworkUtils.type2String(oldNetType));
                netMap.put("now", NetworkUtils.type2String(currentNetType));
                netMap.put("result", Integer.valueOf(200));
                notifyAllObservers(netMap);
                LetvBaseWebViewActivity.netType = currentNetType;
            }
        }
    }
}
