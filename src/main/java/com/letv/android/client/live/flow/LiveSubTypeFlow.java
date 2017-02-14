package com.letv.android.client.live.flow;

import com.letv.core.api.LetvRequest;
import com.letv.core.api.LetvUrlMaker;
import com.letv.core.bean.LiveBeanLeChannel;
import com.letv.core.bean.LiveBeanLeChannelList;
import com.letv.core.bean.LiveBeanLeChannelProgramList;
import com.letv.core.bean.LiveBeanLeChannelProgramList.LiveLunboProgramListBean;
import com.letv.core.bean.LiveRemenListBean;
import com.letv.core.constant.DatabaseConstant.ChannelListTrace.ChannelStatus;
import com.letv.core.constant.LetvConstant.SortType;
import com.letv.core.constant.LiveRoomConstant;
import com.letv.core.db.ChannelHisListHandler;
import com.letv.core.db.ChannelListHandler;
import com.letv.core.db.DBManager;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.Volley;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.LiveBeanLeChannelListParser;
import com.letv.core.parser.LiveLunboChannelProgramListParser;
import com.letv.core.parser.LiveRemenListParser;
import com.letv.core.parser.LiveRemenParser;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LiveLunboUtils;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.pp.utils.NetworkUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscription;

public class LiveSubTypeFlow {
    private static final String TAG = "LiveSubTypeFlow";
    private List<LiveLunboProgramListBean> mAllPrograms;
    private LiveBeanLeChannelList mLiveLunboList;
    private Subscription mRefreshSubscription;
    private String mRequestTag;
    private int pageIndex;

    public LiveSubTypeFlow(String requestTag) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mAllPrograms = new ArrayList();
        this.mRequestTag = requestTag;
    }

    public void start(int pageIndex) {
        this.pageIndex = pageIndex;
        String url = null;
        LiveRemenParser parser = null;
        switch (pageIndex) {
            case 0:
            case 12:
                url = LetvUrlMaker.getTopVideoHomeUrl();
                parser = new LiveRemenListParser();
                break;
            case 1:
            case 2:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
                requestLunboWeishi(pageIndex);
                return;
            case 3:
                url = LetvUrlMaker.getLiveUrlByChannelType("sports");
                parser = new LiveRemenParser();
                break;
            case 4:
                url = LetvUrlMaker.getLiveUrlByChannelType("music");
                parser = new LiveRemenParser();
                break;
            case 5:
                url = LetvUrlMaker.getLiveUrlByChannelType(LiveRoomConstant.CHANNEL_TYPE_ENTERTAINMENT);
                parser = new LiveRemenParser();
                break;
            case 6:
                url = LetvUrlMaker.getLiveUrlByChannelType("brand");
                parser = new LiveRemenParser();
                break;
            case 7:
                url = LetvUrlMaker.getLiveUrlByChannelType("game");
                parser = new LiveRemenParser();
                break;
            case 8:
                url = LetvUrlMaker.getLiveUrlByChannelType("information");
                parser = new LiveRemenParser();
                break;
            case 9:
                url = LetvUrlMaker.getLiveUrlByChannelType("finance");
                parser = new LiveRemenParser();
                break;
            case 10:
                url = LetvUrlMaker.getLiveUrlByChannelType("other");
                parser = new LiveRemenParser();
                break;
            case 11:
                url = LetvUrlMaker.getLiveUrlByChannelType("variety");
                parser = new LiveRemenParser();
                break;
        }
        requestRemenListBean(parser, url);
        if (!LiveLunboUtils.isLunBoWeiShiType(pageIndex) && !PreferencesManager.getInstance().isRequestLunboData()) {
            requestLunboWeishi(1);
        }
    }

    public void destroy() {
        Volley.getQueue().cancelWithTag(this.mRequestTag);
        if (this.mRefreshSubscription != null && !this.mRefreshSubscription.isUnsubscribed()) {
            this.mRefreshSubscription.unsubscribe();
        }
    }

    public void requestRemenListBean(LiveRemenParser parser, String url) {
        LogInfo.log(TAG, "request LiveRemenListBean: " + url);
        new LetvRequest(LiveRemenListBean.class).setUrl(url).setParser(parser).setTag(this.mRequestTag).setCache(new VolleyNoCache()).setCallback(new 1(this)).add();
    }

    public void requestLunboWeishi(int pageIndex) {
        String url = null;
        LogInfo.log(TAG, "requestLunboWieshi: " + pageIndex);
        if (pageIndex == 1) {
            url = LetvUrlMaker.getLunboListBeanUrl();
        } else if (pageIndex == 2) {
            url = LetvUrlMaker.getWeishilListBeanUrl();
        } else if (pageIndex == 13) {
            url = LetvUrlMaker.getHKLunboListBeanUrl(LiveRoomConstant.CHANNEL_TYPE_SUBTYPE_MOVIE);
        } else if (pageIndex == 14) {
            url = LetvUrlMaker.getHKLunboListBeanUrl(LiveRoomConstant.CHANNEL_TYPE_SUBTYPE_TVSERIES);
        } else if (pageIndex == 15) {
            url = LetvUrlMaker.getHKLunboListBeanUrl(LiveRoomConstant.CHANNEL_TYPE_SUBTYPE_VAREITY);
        } else if (pageIndex == 16) {
            url = LetvUrlMaker.getHKLunboListBeanUrl("music");
        } else if (pageIndex == 17) {
            url = LetvUrlMaker.getHKLunboListBeanUrl("sports");
        }
        new LetvRequest(LiveBeanLeChannelList.class).setUrl(url).setTag(this.mRequestTag).setParser(new LiveBeanLeChannelListParser()).setCache(new 3(this, LiveLunboUtils.getChannelDBType(pageIndex), pageIndex)).setCallback(new 2(this, pageIndex)).add();
    }

    public void requestAllPrograms(int channelType, int firstIndex) {
        if (firstIndex == 0) {
            this.mAllPrograms.clear();
            if (!(this.mRefreshSubscription == null || this.mRefreshSubscription.isUnsubscribed())) {
                this.mRefreshSubscription.unsubscribe();
            }
            this.mRefreshSubscription = Observable.timer(120, TimeUnit.SECONDS).subscribe(new 4(this, channelType));
        }
        LogInfo.log(TAG, "requestAllPrograms: " + channelType + NetworkUtils.DELIMITER_COLON + firstIndex);
        StringBuilder sb = new StringBuilder();
        if (this.mLiveLunboList == null) {
            LogInfo.log(TAG, "no lunbo list");
            return;
        }
        List<LiveBeanLeChannel> channelList = this.mLiveLunboList.mLiveBeanLeChannelList;
        if (firstIndex >= 0) {
            int i = firstIndex;
            while (i < channelList.size() && i < firstIndex + 10) {
                sb.append(((LiveBeanLeChannel) channelList.get(i)).channelId).append(",");
                i++;
            }
            LogInfo.log(TAG, sb.toString());
            int lastComma = sb.lastIndexOf(",");
            if (lastComma != -1) {
                new LetvRequest(LiveBeanLeChannelProgramList.class).setTag(this.mRequestTag).setUrl(LetvUrlMaker.getLunboListUrl(channelType, sb.substring(0, lastComma))).setParser(new LiveLunboChannelProgramListParser()).setCache(new VolleyNoCache()).setCallback(new 5(this, firstIndex, channelList, channelType)).add();
            }
        }
    }

    public void requestProgram(ArrayList<LiveBeanLeChannel> data, boolean isLunboData) {
        if (data != null && data.size() != 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.size(); i++) {
                sb.append(((LiveBeanLeChannel) data.get(i)).channelId).append(",");
            }
            int lastComma = sb.lastIndexOf(",");
            if (lastComma != -1) {
                String channelIds = sb.substring(0, lastComma);
                int i2 = (isLunboData || LetvUtils.isInHongKong()) ? 1 : 2;
                new LetvRequest(LiveBeanLeChannelProgramList.class).setTag(this.mRequestTag).setUrl(LetvUrlMaker.getLunboListUrl(i2, channelIds)).setParser(new LiveLunboChannelProgramListParser()).setCache(new VolleyNoCache()).setCallback(new 6(this)).add();
            }
        }
    }

    private void saveLiveBeanLeChannelListToDB(LiveBeanLeChannelList response, int channelType) {
        saveToLocalDataBase(response, channelType);
        saveToSaveDataBase(response, channelType);
    }

    private void saveToLocalDataBase(LiveBeanLeChannelList result, int channelIndex) {
        String type = LiveLunboUtils.getChannelDBType(channelIndex);
        List<LiveBeanLeChannel> newList = LetvUtils.sortChannelList(result.mLiveBeanLeChannelList, SortType.SORT_BYNO);
        ChannelHisListHandler mChannelListTrace = DBManager.getInstance().getChannelHisListTrace();
        List<LiveBeanLeChannel> localList = mChannelListTrace.getAllChannelList(type).mLiveBeanLeChannelList;
        LiveBeanLeChannelList hisChannelList = mChannelListTrace.getHisChannelList(type);
        HashSet<String> hisListChanenelIDs = new HashSet();
        if (hisChannelList != null) {
            List<LiveBeanLeChannel> hisList = hisChannelList.mLiveBeanLeChannelList;
            for (int a = 0; a < hisList.size(); a++) {
                hisListChanenelIDs.add(((LiveBeanLeChannel) hisList.get(a)).channelId);
            }
        }
        int j = newList.size();
        int i;
        if (localList.isEmpty()) {
            for (i = 0; i < j; i++) {
                mChannelListTrace.addToChannelList((LiveBeanLeChannel) newList.get(i), type);
            }
            return;
        }
        for (LiveBeanLeChannel localChannel : localList) {
            mChannelListTrace.delete(localChannel, type);
        }
        for (i = 0; i < j; i++) {
            LiveBeanLeChannel channel = (LiveBeanLeChannel) newList.get(i);
            if (hisListChanenelIDs.contains(channel.channelId)) {
                channel.isRecord = 1;
            }
            mChannelListTrace.addToChannelList(channel, type);
            mChannelListTrace.updateChannelStatus(channel, ChannelStatus.NORMAL, type);
        }
    }

    private void saveToSaveDataBase(LiveBeanLeChannelList result, int channelIndex) {
        String key = LiveLunboUtils.getChannelDBType(channelIndex);
        List<LiveBeanLeChannel> newList = LetvUtils.sortChannelList(result.mLiveBeanLeChannelList, SortType.SORT_BYNO);
        ChannelListHandler mChannelListTrace = DBManager.getInstance().getChannelListTrace();
        int j = newList.size();
        List<LiveBeanLeChannel> saveList = mChannelListTrace.getAllChannelList(key).mLiveBeanLeChannelList;
        int i;
        if (saveList.isEmpty()) {
            for (i = 0; i < j; i++) {
                mChannelListTrace.addToChannelList((LiveBeanLeChannel) newList.get(i), key);
            }
            return;
        }
        HashMap<String, LiveBeanLeChannel> keysMap = new HashMap();
        for (i = 0; i < j; i++) {
            keysMap.put(((LiveBeanLeChannel) newList.get(i)).channelId, newList.get(i));
        }
        for (LiveBeanLeChannel saveChannel : saveList) {
            if (!keysMap.containsKey(saveChannel.channelId)) {
                mChannelListTrace.delete(saveChannel, key);
            }
        }
        for (i = 0; i < j; i++) {
            if (mChannelListTrace.isExistByNumberKeysAndStatus(((LiveBeanLeChannel) newList.get(i)).numericKeys, key)) {
                mChannelListTrace.updateByNumberkeys((LiveBeanLeChannel) newList.get(i), ChannelStatus.NORMAL, "0", key);
            } else {
                mChannelListTrace.addToChannelList((LiveBeanLeChannel) newList.get(i), key);
                mChannelListTrace.updateChannelStatus((LiveBeanLeChannel) newList.get(i), ChannelStatus.NORMAL, key);
            }
        }
    }
}
