package com.letv.mobile.lebox.net;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.text.TextUtils;
import com.letv.mobile.lebox.LeBoxApp;
import com.letv.mobile.lebox.R;
import com.letv.mobile.lebox.ui.qrcode.LeboxQrCodeBean;
import com.letv.mobile.lebox.utils.Logger;
import com.letv.mobile.lebox.utils.Util;

public class LeBoxNetworkManager {
    public static final String INVALID_LEBOX_IP = "0.0.0.0";
    private static final String LEBOX_HEAD = "LEHE";
    private static final String TAG = LeBoxNetworkManager.class.getSimpleName();
    private static LeBoxNetworkManager mNetworkManager;
    public static int needScanNum = 3;
    private boolean isConnectionWifiDirect;
    private final ConnectivityManager mConnectivityManager = ((ConnectivityManager) this.mContext.getSystemService("connectivity"));
    private final Context mContext = LeBoxApp.getApplication();

    private LeBoxNetworkManager() {
    }

    public static synchronized LeBoxNetworkManager getInstance() {
        LeBoxNetworkManager leBoxNetworkManager;
        synchronized (LeBoxNetworkManager.class) {
            if (mNetworkManager == null) {
                mNetworkManager = new LeBoxNetworkManager();
            }
            leBoxNetworkManager = mNetworkManager;
        }
        return leBoxNetworkManager;
    }

    public void release() {
        synchronized (LeBoxNetworkManager.class) {
            mNetworkManager = null;
        }
    }

    public boolean openWifi() {
        boolean isOpeanSuccee = LeBoxWifiModel.getInstance().openWifi();
        Logger.d(TAG, "-------wifi--打开--直接链接乐盒-----isOpean=" + isOpeanSuccee);
        if (!isOpeanSuccee) {
            Util.showToast(R.string.opean_wifi_fail);
            try {
                Intent wifiSettingsIntent = new Intent("android.settings.WIFI_SETTINGS");
                wifiSettingsIntent.setFlags(268435456);
                this.mContext.startActivity(wifiSettingsIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return isOpeanSuccee;
    }

    public void connectLebox() {
        if (LeBoxWifiModel.getInstance().isWifiEnable() && !TextUtils.isEmpty(LeboxQrCodeBean.getSsid())) {
            if (getInstance().isHasLeboxAp()) {
                LeBoxWifiModel.getInstance().connectWifi(LeboxQrCodeBean.getSsid(), LeboxQrCodeBean.getPassword());
            } else {
                discoverP2pPeers(new 1(this));
            }
        }
    }

    public void switchLeboxWifi() {
        if (LeBoxWifiModel.getInstance().isWifiEnable() && !TextUtils.isEmpty(LeboxQrCodeBean.getSsid()) && !LeboxQrCodeBean.getSsid().equals(LeBoxWifiModel.getInstance().getConnWifiSSID()) && !Util.isBackground(this.mContext)) {
            LeBoxWifiModel.getInstance().connectWifi(LeboxQrCodeBean.getSsid(), LeboxQrCodeBean.getPassword());
        }
    }

    public boolean isLeboxWifi() {
        Logger.d(TAG, "----isLeboxWifi--isCurrentConnectIsWifi=" + isCurrentConnectIsWifi() + "--lebox-ssid" + LeboxQrCodeBean.getSsid() + "--wifi ssid=" + LeBoxWifiModel.getInstance().getConnWifiSSID());
        if (TextUtils.isEmpty(LeboxQrCodeBean.getSsid()) || !LeboxQrCodeBean.getSsid().equals(LeBoxWifiModel.getInstance().getConnWifiSSID())) {
            return false;
        }
        needScanNum = 0;
        return true;
    }

    public boolean isLeboxWifiAvailable() {
        Logger.d(TAG, "----isLeboxWifi--isCurrentConnectIsWifi=" + isCurrentConnectIsWifi() + "--lebox-ssid" + LeboxQrCodeBean.getSsid() + "--wifi ssid=" + LeBoxWifiModel.getInstance().getConnWifiSSID());
        if (!isCurrentConnectIsWifi() || TextUtils.isEmpty(LeboxQrCodeBean.getSsid()) || !LeboxQrCodeBean.getSsid().equals(LeBoxWifiModel.getInstance().getConnWifiSSID())) {
            return false;
        }
        needScanNum = 0;
        return true;
    }

    public boolean isFristConnectLeboxFromSettings() {
        String ssid = LeBoxWifiModel.getInstance().getConnWifiSSID();
        if (TextUtils.isEmpty(LeboxQrCodeBean.getSsid()) && ssid.startsWith(LEBOX_HEAD)) {
            return true;
        }
        return false;
    }

    public ConnectivityManager getConnectivityManager() {
        return this.mConnectivityManager;
    }

    private boolean isCurrentConnectIsWifi() {
        NetworkInfo info = this.mConnectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            Logger.d(TAG, "--getCurrentConnectIsWifi--type name：" + info.getTypeName());
            if (1 == info.getType()) {
                return true;
            }
        }
        return false;
    }

    public boolean isHasLeboxAp() {
        if (TextUtils.isEmpty(LeboxQrCodeBean.getSsid())) {
            return false;
        }
        boolean isHas = LeBoxWifiModel.getInstance().isHasTargetSsid(LeboxQrCodeBean.getSsid());
        if (isHas) {
            needScanNum = 0;
            return isHas;
        } else if (!LeBoxWifiModel.getInstance().isWifiEnable() || needScanNum <= 0) {
            return isHas;
        } else {
            LeBoxWifiModel.getInstance().startScan();
            needScanNum--;
            return isHas;
        }
    }

    public boolean isHasLeboxDirectSsid() {
        if (TextUtils.isEmpty(LeboxQrCodeBean.getDirectSsid())) {
            return false;
        }
        boolean isHas = LeBoxWifiModel.getInstance().isHasTargetSsid(LeboxQrCodeBean.getDirectSsid());
        if (!isHas) {
            return isHas;
        }
        needScanNum = 0;
        return isHas;
    }

    public void requestP2pPeers(PeerListListener peerListListener) {
        LeBoxWifiDirectModel.getInstance().requestPeers(peerListListener);
    }

    public void connectP2p(WifiP2pDevice device, ActionListener listener) {
        LeBoxWifiDirectModel.getInstance().connect(device, listener);
    }

    public void discoverP2pPeers(ActionListener listener) {
        LeBoxWifiDirectModel.getInstance().discoverPeers(listener);
    }

    public void saveWifiP2pInfo(WifiP2pInfo wifiP2pInfo) {
        LeBoxWifiDirectModel.getInstance().setWifiP2pInfo(wifiP2pInfo);
    }

    public String getP2pDeviceGateway() {
        return LeBoxWifiDirectModel.getInstance().getDeviceGateway();
    }

    public boolean isWifiEnable() {
        return LeBoxWifiModel.getInstance().isWifiEnable();
    }

    public String getWifiGateway() {
        return LeBoxWifiModel.getInstance().getWifiGateway();
    }

    public boolean isConnectionWifiDirect() {
        return this.isConnectionWifiDirect;
    }

    public void setConnectionWifiDirect(boolean isConnectionWifiDirect) {
        this.isConnectionWifiDirect = isConnectionWifiDirect;
    }

    public boolean isLeboxConnected() {
        if (isLeboxWifi() || this.isConnectionWifiDirect) {
            return true;
        }
        return false;
    }

    public boolean isLeboxConnectedAvailable() {
        if (isLeboxWifiAvailable() || this.isConnectionWifiDirect) {
            return true;
        }
        return false;
    }

    public State getWifiConnectivityState() {
        return this.mConnectivityManager.getNetworkInfo(1).getState();
    }
}
