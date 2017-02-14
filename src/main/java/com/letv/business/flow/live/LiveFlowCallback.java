package com.letv.business.flow.live;

import android.os.Bundle;
import com.letv.ads.ex.ui.AdPlayFragmentProxy;
import com.letv.core.bean.CurrentProgram;
import com.letv.core.bean.LetvBaseBean;
import com.letv.core.bean.LiveBeanLeChannelList;
import com.letv.core.bean.LiveBeanLeChannelProgramList;
import com.letv.core.bean.LiveBeanLeChannelProgramList.LiveLunboProgramListBean;
import com.letv.core.bean.LivePriceBean;
import com.letv.core.bean.LiveRemenListBean;
import com.letv.core.bean.LiveRemenListBean.LiveRemenBaseBean;
import com.letv.core.bean.LiveStreamBean;
import com.letv.core.bean.LiveUrlInfo;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import java.util.List;
import org.cybergarage.upnp.Device;

public interface LiveFlowCallback {

    public interface LivePlayCallback {
        void addAdsFragment(AdPlayFragmentProxy adPlayFragmentProxy);

        void back();

        void bookLiveProgram(String str, String str2, String str3, String str4, String str5);

        void buyWo3G();

        void cannotPlayError();

        void changeLaunchMode(LiveRemenBaseBean liveRemenBaseBean);

        void dismisNetChangeDialog();

        void full();

        int getCurrentPlayPosition();

        void half();

        void hide3gLayout();

        void hidePayLoading();

        boolean isEnforcementPause();

        boolean isFirstPlay();

        boolean isPlaying();

        void jumpToVipProducts(long j, long j2);

        void loading(boolean z);

        void notPlay(String str);

        void notifyControllBarNetChange();

        void notifyHalfLivePlayFragment(int i);

        void onLiveUrlPost(LiveStreamBean liveStreamBean, boolean z);

        void onPlayNetNull();

        void onProgramChange(CurrentProgram currentProgram);

        void pause();

        void pauseVideo();

        void play(String str);

        void playNetWhileNotWait(String str);

        void playOnAdsFinish();

        void removeLivePayLayout();

        void requestError(String str);

        void resumeVideo();

        void setDownStreamTipVisible(boolean z);

        void setEnforcementWait(boolean z);

        void setmProgramName(String str);

        boolean showNetChangeDialog();

        void showPayLayout(int i);

        void showPayLoading();

        void showPayPrice(LivePriceBean livePriceBean);

        void star();

        void stopPlayback();

        void toastNoMore();

        void updateHdButton(LiveStreamBean liveStreamBean);

        void updatePartId(String str);
    }

    public interface AsyncCallback {
        void onNetworkResponse(NetworkResponseState networkResponseState, LetvBaseBean letvBaseBean, long j);
    }

    public interface CheckPayCallback {
        void freeCallback();

        void payCallback();
    }

    public interface LiveChannelCallback {
        void onAllChannelData(List<LiveLunboProgramListBean> list);

        void onChannelData(LiveBeanLeChannelProgramList liveBeanLeChannelProgramList);
    }

    public interface LiveExceptionCallback {
        void onNetworkError();

        void onNetworkNotAvailable();

        void onPreFail();

        void onResultError();

        void onResultNoUpdate();
    }

    public interface LiveFlowProgress {
        void onLoading(boolean z);

        void onLoading(boolean z, boolean z2);
    }

    public interface LiveLunboCallback {
        void onCacheData(LiveBeanLeChannelList liveBeanLeChannelList);

        void onNetworkData(LiveBeanLeChannelList liveBeanLeChannelList);
    }

    public interface LiveProgramCallback {
        void onProgramData(int i, LiveLunboProgramListBean liveLunboProgramListBean);
    }

    public interface LiveRoomCallback {
        void onRoomData(LiveRemenListBean liveRemenListBean);
    }

    public interface LiveRoomPlayerCallback {
        String getChannelId();

        String getChannelType();

        void playHd(int i);

        void start3G(boolean z);

        String syncGetPlayUrl(Device device);
    }

    public interface PlayAlbumControllerCallBack {
        public static final int STATE_DATA_NULL = 4;
        public static final int STATE_DIRECTION_CHANGE = 6;
        public static final int STATE_FINISH = 1;
        public static final int STATE_FIRST_PLAYING = 8;
        public static final int STATE_NET_ERR = 3;
        public static final int STATE_NET_NULL = 2;
        public static final int STATE_NEXT = 7;
        public static final int STATE_OTHER = 5;
        public static final int STATE_REFRESH_DATA = 9;
        public static final int STATE_RUNNING = 0;

        void notify(int i);

        void notify(Bundle bundle);
    }

    public interface RequestUrlByChannelIdCallback {
        void onNetworkResponse(NetworkResponseState networkResponseState, LiveUrlInfo liveUrlInfo, boolean z, boolean z2);
    }
}
