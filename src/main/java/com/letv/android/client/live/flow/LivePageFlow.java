package com.letv.android.client.live.flow;

import android.text.TextUtils;
import com.letv.android.client.live.bean.LivePageBean;
import com.letv.android.client.live.bean.LiveWatchNumBean;
import com.letv.android.client.live.parser.LivePageDataParser;
import com.letv.android.client.live.parser.LiveWatchNumParser;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.LetvUrlMaker;
import com.letv.core.bean.LiveBeanLeChannel;
import com.letv.core.bean.LiveBeanLeChannelProgramList;
import com.letv.core.network.volley.Volley;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.LiveLunboChannelProgramListParser;
import com.letv.core.utils.LetvUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.ArrayList;

public class LivePageFlow {
    private static final String TAG = "LivePageFlow";
    private String mRequestTag;

    public LivePageFlow() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mRequestTag = TAG;
    }

    public void start(boolean autoRefresh) {
        String url = LetvUrlMaker.getLivePageDataUrl();
        new LetvRequest(LivePageBean.class).setUrl(url).setParser(new LivePageDataParser()).setTag(this.mRequestTag).setRequestType(RequestManner.NETWORK_ONLY).setCallback(new 1(this, autoRefresh)).add();
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
                new LetvRequest(LiveBeanLeChannelProgramList.class).setTag(this.mRequestTag).setUrl(LetvUrlMaker.getLunboListUrl(i2, channelIds)).setParser(new LiveLunboChannelProgramListParser()).setCache(new VolleyNoCache()).setCallback(new 2(this, isLunboData)).add();
            }
        }
    }

    public void requestLiveWatchNum(String id) {
        if (!TextUtils.isEmpty(id)) {
            new LetvRequest(LiveWatchNumBean.class).setTag(this.mRequestTag).setUrl(LetvUrlMaker.getLiveWatchNumUrl(id)).setParser(new LiveWatchNumParser()).setCallback(new 3(this)).add();
        }
    }

    public void destroy() {
        Volley.getQueue().cancelWithTag(this.mRequestTag);
    }
}
