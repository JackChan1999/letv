package com.letv.redpacketsdk;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import com.letv.redpacketsdk.bean.ShareBean;
import com.letv.redpacketsdk.callback.RedPacketForecastCallback;
import com.letv.redpacketsdk.callback.RedPacketPollingCallback;
import com.letv.redpacketsdk.callback.RedPacketViewListener;
import com.letv.redpacketsdk.net.HTTPURL;
import com.letv.redpacketsdk.net.NetworkManager;
import com.letv.redpacketsdk.ui.RedPacketUI;
import com.letv.redpacketsdk.utils.LogInfo;
import java.util.ArrayList;

public class RedPacketSdk {
    private static RedPacketSdk instance = new RedPacketSdk();
    private ArrayList<RedPacketUI> mRedPacketUIList = new ArrayList();

    private RedPacketSdk() {
    }

    public static RedPacketSdk getInstance() {
        return instance;
    }

    public View getRedPacketView(Activity activity, RedPacketViewListener redPacketViewListener) {
        RedPacketUI view = new RedPacketUI(activity, redPacketViewListener);
        this.mRedPacketUIList.add(view);
        return view;
    }

    public void getForecastView(Context context, RedPacketForecastCallback callback) {
        NetworkManager.getRedPacketForecast(context, callback);
    }

    public void updateRedPacketUI(RedPacketUI redPacketUI) {
        if (RedPacketSdkManager.getInstance().hasRedPacket()) {
            redPacketUI.updateEntryImage();
            redPacketUI.getListener().show();
            return;
        }
        redPacketUI.getListener().hide();
    }

    public void getRedPacketInfo(RedPacketPollingCallback callback) {
        NetworkManager.getRedpacketBean(callback);
    }

    public void cleanPollingCallback() {
        NetworkManager.cleanGetRpBeanAsyncTaskCallback();
    }

    public ArrayList<RedPacketViewListener> getRedPacketViewListener() {
        ArrayList<RedPacketViewListener> listenerList = new ArrayList();
        ArrayList<RedPacketUI> viewList = getVisbleRedPacketView();
        for (int i = 0; i < viewList.size(); i++) {
            RedPacketUI view = (RedPacketUI) viewList.get(i);
            if (view != null) {
                listenerList.add(view.getListener());
            }
        }
        return listenerList;
    }

    public ArrayList<RedPacketUI> getVisbleRedPacketView() {
        ArrayList<RedPacketUI> viewList = new ArrayList();
        for (int i = 0; i < this.mRedPacketUIList.size(); i++) {
            RedPacketUI ui = (RedPacketUI) this.mRedPacketUIList.get(i);
            if (ui.getStatus() == RedPacketUI.STATUS_ONRESUME) {
                viewList.add(ui);
            }
        }
        return viewList;
    }

    protected void notifyListener(boolean isShow) {
        LogInfo.log("RedPacketSdk", "notifyListener + isShow =" + isShow + "  ;mRedPacketUIList.size = " + this.mRedPacketUIList.size());
        if (this.mRedPacketUIList.size() > 0) {
            ArrayList<RedPacketUI> mRpEntryList = getVisbleRedPacketView();
            for (int i = 0; i < mRpEntryList.size(); i++) {
                RedPacketUI mRpEntry = (RedPacketUI) mRpEntryList.get(i);
                if (isShow) {
                    if (mRpEntry != null) {
                        mRpEntry.updateEntryImage();
                    }
                    mRpEntry.getListener().show();
                } else {
                    mRpEntry.getListener().hide();
                }
            }
        }
    }

    public void notifyGotoGiftList() {
        ArrayList<RedPacketViewListener> listenerList = getRedPacketViewListener();
        for (int i = 0; i < listenerList.size(); i++) {
            ((RedPacketViewListener) listenerList.get(i)).gotoGiftPage(HTTPURL.REDPACKET_LIST_BASE);
        }
    }

    public void notifyGotoWeb(String url) {
        if (!TextUtils.isEmpty(url)) {
            ArrayList<RedPacketViewListener> listenerList = getRedPacketViewListener();
            for (int i = 0; i < listenerList.size(); i++) {
                ((RedPacketViewListener) listenerList.get(i)).gotoWeb(url);
            }
        }
    }

    public void notifyShare() {
        ArrayList<RedPacketViewListener> listenerList = getRedPacketViewListener();
        ShareBean bean = RedPacketSdkManager.getInstance().getShareInfo();
        if (!bean.isEmpty()) {
            for (int i = 0; i < listenerList.size(); i++) {
                ((RedPacketViewListener) listenerList.get(i)).share(bean.shareUrl, bean.sharePic, bean.shareTitle);
            }
        }
    }

    public void notifyToast() {
        ArrayList<RedPacketViewListener> listenerList = getRedPacketViewListener();
        for (int i = 0; i < listenerList.size(); i++) {
            ((RedPacketViewListener) listenerList.get(i)).showToast();
        }
    }

    public void notifyDialogDismiss() {
        ArrayList<RedPacketUI> viewList = getVisbleRedPacketView();
        for (int i = 0; i < viewList.size(); i++) {
            ((RedPacketUI) viewList.get(i)).unLockRedPacketOrientation();
            if (((RedPacketUI) viewList.get(i)).getDialogDisplayCallback() != null) {
                ((RedPacketUI) viewList.get(i)).getDialogDisplayCallback().onDismiss();
            }
        }
    }

    public void removeRedPacketUI(RedPacketUI redPacketUI) {
        if (this.mRedPacketUIList.contains(redPacketUI)) {
            this.mRedPacketUIList.remove(redPacketUI);
        }
    }
}
