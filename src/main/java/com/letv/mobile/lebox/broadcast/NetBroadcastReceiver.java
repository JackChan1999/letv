package com.letv.mobile.lebox.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.text.TextUtils;
import com.letv.mobile.async.TaskCallBack;
import com.letv.mobile.http.bean.CommonResponse;
import com.letv.mobile.http.utils.StringUtils;
import com.letv.mobile.lebox.LeboxApiManager;
import com.letv.mobile.lebox.config.LeBoxAppConfig;
import com.letv.mobile.lebox.heartbeat.HeartbeatManager;
import com.letv.mobile.lebox.http.lebox.bean.KeyLoginBean;
import com.letv.mobile.lebox.http.lebox.bean.KeyLoginInfoBean;
import com.letv.mobile.lebox.http.lebox.request.KeyLoginHttpRequest;
import com.letv.mobile.lebox.init.DataInitManager;
import com.letv.mobile.lebox.net.LeBoxNetworkManager;
import com.letv.mobile.lebox.ui.qrcode.LeboxQrCodeBean;
import com.letv.mobile.lebox.utils.Logger;
import java.util.ArrayList;
import java.util.List;

public class NetBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = NetBroadcastReceiver.class.getSimpleName();

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        NetworkInfo networkInfo;
        if (action.equals("android.net.wifi.STATE_CHANGE")) {
            Logger.d(TAG, "---BroadcastReceiver---wifi network  change---------");
            networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
            if (networkInfo.getState().equals(State.CONNECTED)) {
                Logger.d(TAG, "-BroadcastReceiver--connection---ssid=" + ((WifiInfo) intent.getParcelableExtra("wifiInfo")).getSSID());
                String gateway;
                String domain;
                if (LeBoxNetworkManager.getInstance().isLeboxWifi()) {
                    gateway = LeBoxNetworkManager.getInstance().getWifiGateway();
                    Logger.d(TAG, "-----gateway:" + gateway);
                    if (!TextUtils.isEmpty(gateway)) {
                        LeBoxAppConfig.setDynamicDomain(gateway);
                    }
                    domain = LeBoxAppConfig.getDynamicDomain();
                    Logger.d(TAG, "---Domain=" + domain);
                    if (!TextUtils.isEmpty(domain)) {
                        Logger.d(TAG, "-BroadcastReceiver-wifi network  change is lebox Wifi connect-isRunHeartbeat=" + HeartbeatManager.getInstance().isRunHeartbeat());
                        if (!HeartbeatManager.getInstance().isRunHeartbeat()) {
                            HeartbeatManager.getInstance().onStartHeartbeat();
                        }
                        DataInitManager.getInstance().startInit();
                    }
                } else if (LeBoxNetworkManager.getInstance().isFristConnectLeboxFromSettings()) {
                    gateway = LeBoxNetworkManager.getInstance().getWifiGateway();
                    Logger.d(TAG, "--from settings connect---gateway:" + gateway);
                    if (!TextUtils.isEmpty(gateway)) {
                        LeBoxAppConfig.setDynamicDomain(gateway);
                    }
                    domain = LeBoxAppConfig.getDynamicDomain();
                    Logger.d(TAG, "--from settings connect-Domain=" + domain);
                    if (!TextUtils.isEmpty(domain)) {
                        KeyLoginHttpRequest.getLoginRequest(context, new TaskCallBack() {
                            public void callback(int code, String msg, String errorCode, Object object) {
                                Logger.d(NetBroadcastReceiver.TAG, "-getLoginRequest--because frist from settings--code=" + code + "-msg=" + msg + "-errorCode=" + errorCode + "--object=" + object);
                                if (code == 0 && object != null && (object instanceof CommonResponse)) {
                                    KeyLoginBean bean = (KeyLoginBean) ((CommonResponse) object).getData();
                                    Logger.d(NetBroadcastReceiver.TAG, "KeyLoginBean :" + bean);
                                    KeyLoginInfoBean loginInfo = bean.getInfo();
                                    if ("1".equals(bean.getIsAdmin()) && loginInfo != null && !StringUtils.equalsNull(loginInfo.getSCHEME()) && !StringUtils.equalsNull(loginInfo.getVERSION()) && !StringUtils.equalsNull(loginInfo.getMAC()) && !StringUtils.equalsNull(loginInfo.getSSID()) && !StringUtils.equalsNull(loginInfo.getPASSWORD())) {
                                        LeboxQrCodeBean.setScheme(loginInfo.getSCHEME());
                                        LeboxQrCodeBean.setVersion(loginInfo.getVERSION());
                                        LeboxQrCodeBean.setMac(loginInfo.getMAC());
                                        LeboxQrCodeBean.setSsid(loginInfo.getSSID());
                                        LeboxQrCodeBean.setPassword(loginInfo.getPASSWORD());
                                        LeboxQrCodeBean.setCode(LeboxQrCodeBean.getCode());
                                        Logger.d(NetBroadcastReceiver.TAG, "-BroadcastReceiver-wifi network  change is lebox Wifi connect--from settings ---isRunHeartbeat=" + HeartbeatManager.getInstance().isRunHeartbeat());
                                        if (!HeartbeatManager.getInstance().isRunHeartbeat()) {
                                            HeartbeatManager.getInstance().onStartHeartbeat();
                                        }
                                        DataInitManager.getInstance().startInit();
                                    }
                                }
                            }
                        }).execute(KeyLoginHttpRequest.getKeyLoginParameter().combineParams());
                    }
                } else {
                    Logger.d(TAG, "-BroadcastReceiver-wifi network  change connect but not lebox wifi-isWifiAPAutoConnect=" + HeartbeatManager.isWifiAPAutoConnect + "-isRunHeartbeat=" + HeartbeatManager.getInstance().isRunHeartbeat());
                    if (HeartbeatManager.isWifiAPAutoConnect) {
                        if (LeBoxNetworkManager.getInstance().isHasLeboxAp()) {
                            LeBoxNetworkManager.getInstance().switchLeboxWifi();
                        } else {
                            Logger.d(TAG, "---BroadcastReceiver--connectivity change--no lebox ap around");
                        }
                    }
                    if (!LeBoxNetworkManager.getInstance().isConnectionWifiDirect()) {
                        HeartbeatManager.getInstance().onStopHeartbeat();
                    }
                }
            } else if (!LeBoxNetworkManager.getInstance().isConnectionWifiDirect()) {
                HeartbeatManager.getInstance().onStopHeartbeat();
            }
            if (!(networkInfo.getState().equals(State.CONNECTING) || networkInfo.getState().equals(State.DISCONNECTING))) {
                HeartbeatManager.getInstance().notifyStateChange(4);
            }
            LeboxApiManager.getInstance().setLeboxConnectChanged();
        } else if (action.equals("android.net.wifi.WIFI_STATE_CHANGED")) {
            int wifiStatus = intent.getIntExtra("wifi_state", 4);
            Logger.d(TAG, "---BroadcastReceiver-----wifi status change---------wifiStatus=" + wifiStatus + "--isNeedWatcher=" + HeartbeatManager.getInstance().isNeedWatcher());
            switch (wifiStatus) {
                case 0:
                case 1:
                case 4:
                    Logger.d(TAG, "---BroadcastReceiver-----wifi status change---------sWifiEnable=" + LeBoxNetworkManager.getInstance().isWifiEnable());
                    HeartbeatManager.getInstance().onStopNetworkConnectionWatcher();
                    HeartbeatManager.getInstance().onStopHeartbeat();
                    return;
                case 2:
                    return;
                case 3:
                    if (!HeartbeatManager.getInstance().isNeedWatcher()) {
                        HeartbeatManager.getInstance().onStartNetworkConnectionWatcher();
                        return;
                    }
                    return;
                default:
                    return;
            }
        } else if (action.equals("android.net.wifi.SCAN_RESULTS")) {
            Logger.d(TAG, "---BroadcastReceiver-----scan results---------");
            if (!HeartbeatManager.isWifiAPAutoConnect) {
                return;
            }
            if (LeBoxNetworkManager.getInstance().isHasLeboxAp()) {
                LeBoxNetworkManager.needScanNum = 0;
                boolean isLeboxConnected = LeBoxNetworkManager.getInstance().isLeboxConnected();
                Logger.d(TAG, "---BroadcastReceiver--scan resultes--has lebox ap--isLeboxConnected=" + isLeboxConnected);
                if (!isLeboxConnected) {
                    LeBoxNetworkManager.getInstance().switchLeboxWifi();
                    return;
                }
                return;
            }
            Logger.d(TAG, "---BroadcastReceiver--scan resultes--no lebox ap around");
        } else if (action.equals("android.net.wifi.p2p.PEERS_CHANGED")) {
            Logger.d(TAG, "---BroadcastReceiver-----p2p peers change---------");
            if (!TextUtils.isEmpty(LeboxQrCodeBean.getDirectSsid())) {
                LeBoxNetworkManager.getInstance().requestP2pPeers(new PeerListListener() {
                    public void onPeersAvailable(WifiP2pDeviceList peers) {
                        if (peers.getDeviceList() != null) {
                            List<WifiP2pDevice> wifiP2pDeviceList = new ArrayList();
                            wifiP2pDeviceList.addAll(peers.getDeviceList());
                            Logger.d(NetBroadcastReceiver.TAG, "--BroadcastReceiver--p2p request peers DeviceList:" + wifiP2pDeviceList);
                            for (WifiP2pDevice device : wifiP2pDeviceList) {
                                if (!TextUtils.isEmpty(device.deviceName) && device.deviceName.equals(LeboxQrCodeBean.getDirectSsid())) {
                                    LeBoxNetworkManager.needScanNum = 0;
                                    switch (device.status) {
                                        case 0:
                                            LeBoxNetworkManager.getInstance().setConnectionWifiDirect(true);
                                            if (!TextUtils.isEmpty(LeBoxNetworkManager.getInstance().getP2pDeviceGateway())) {
                                                LeBoxAppConfig.setDynamicDomain(LeBoxNetworkManager.getInstance().getP2pDeviceGateway());
                                                return;
                                            }
                                            return;
                                        case 1:
                                        case 2:
                                        case 4:
                                            LeBoxNetworkManager.getInstance().setConnectionWifiDirect(false);
                                            return;
                                        case 3:
                                            LeBoxNetworkManager.getInstance().setConnectionWifiDirect(false);
                                            LeBoxNetworkManager.getInstance().connectP2p(device, new 1(this));
                                            return;
                                        default:
                                            return;
                                    }
                                }
                            }
                        }
                    }
                });
            }
        } else if (action.equals("android.net.wifi.p2p.CONNECTION_STATE_CHANGE")) {
            Logger.d(TAG, "---BroadcastReceiver-----p2p connection change---------");
            networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
            Logger.d(TAG, "-BroadcastReceiver-p2p--typeName=" + networkInfo.getTypeName() + " --isConnected=" + networkInfo.isConnected());
            checkP2pPeers(intent);
            LeboxApiManager.getInstance().setLeboxConnectChanged();
        }
    }

    private void checkP2pPeers(final Intent intent) {
        LeBoxNetworkManager.getInstance().requestP2pPeers(new PeerListListener() {
            public void onPeersAvailable(WifiP2pDeviceList peers) {
                if (peers.getDeviceList() != null && !TextUtils.isEmpty(LeboxQrCodeBean.getDirectSsid())) {
                    NetworkInfo networkInfo;
                    List<WifiP2pDevice> wifiP2pDeviceList = new ArrayList();
                    wifiP2pDeviceList.addAll(peers.getDeviceList());
                    Logger.d(NetBroadcastReceiver.TAG, "--BroadcastReceiver--connect change so  request p2p peers DeviceList:" + wifiP2pDeviceList);
                    boolean isHasLebox = false;
                    for (WifiP2pDevice device : wifiP2pDeviceList) {
                        if (!TextUtils.isEmpty(device.deviceName) && device.deviceName.equals(LeboxQrCodeBean.getDirectSsid())) {
                            isHasLebox = true;
                            switch (device.status) {
                                case 0:
                                    LeBoxNetworkManager.getInstance().setConnectionWifiDirect(true);
                                    WifiP2pInfo wifiP2pInfo = (WifiP2pInfo) intent.getParcelableExtra("wifiP2pInfo");
                                    Logger.d(NetBroadcastReceiver.TAG, "-BroadcastReceiver---groupFormed=" + wifiP2pInfo.groupFormed + "--isGroupOwner=" + wifiP2pInfo.isGroupOwner + "---Owner-HostAddress=" + wifiP2pInfo.groupOwnerAddress.getHostAddress());
                                    LeBoxNetworkManager.getInstance().saveWifiP2pInfo(wifiP2pInfo);
                                    if (!TextUtils.isEmpty(LeBoxNetworkManager.getInstance().getP2pDeviceGateway())) {
                                        LeBoxAppConfig.setDynamicDomain(LeBoxNetworkManager.getInstance().getP2pDeviceGateway());
                                    }
                                    String domain = LeBoxAppConfig.getDynamicDomain();
                                    Logger.d(NetBroadcastReceiver.TAG, "---Domain=" + domain);
                                    if (!TextUtils.isEmpty(domain)) {
                                        if (!HeartbeatManager.getInstance().isRunHeartbeat()) {
                                            HeartbeatManager.getInstance().onStartHeartbeat();
                                        }
                                        DataInitManager.getInstance().startInit();
                                        break;
                                    }
                                    break;
                                case 1:
                                case 2:
                                case 3:
                                case 4:
                                    LeBoxNetworkManager.getInstance().setConnectionWifiDirect(false);
                                    if (!LeBoxNetworkManager.getInstance().isLeboxWifi()) {
                                        HeartbeatManager.getInstance().onStopHeartbeat();
                                        break;
                                    }
                                    break;
                            }
                            Logger.d(NetBroadcastReceiver.TAG, "--BroadcastReceiver--connect change so  request p2p peers isHasLebox:" + isHasLebox);
                            if (!isHasLebox) {
                                LeBoxNetworkManager.getInstance().setConnectionWifiDirect(false);
                                if (!LeBoxNetworkManager.getInstance().isLeboxWifi()) {
                                    HeartbeatManager.getInstance().onStopHeartbeat();
                                }
                            }
                            networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
                            if (!networkInfo.getState().equals(State.CONNECTING) && !networkInfo.getState().equals(State.DISCONNECTING)) {
                                HeartbeatManager.getInstance().notifyStateChange(6);
                                return;
                            }
                        }
                    }
                    Logger.d(NetBroadcastReceiver.TAG, "--BroadcastReceiver--connect change so  request p2p peers isHasLebox:" + isHasLebox);
                    if (isHasLebox) {
                        LeBoxNetworkManager.getInstance().setConnectionWifiDirect(false);
                        if (LeBoxNetworkManager.getInstance().isLeboxWifi()) {
                            HeartbeatManager.getInstance().onStopHeartbeat();
                        }
                    }
                    networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
                    if (!networkInfo.getState().equals(State.CONNECTING)) {
                    }
                }
            }
        });
    }
}
