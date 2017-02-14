package com.letv.core.bean;

import com.letv.core.api.LetvRequest;
import com.letv.core.api.LetvUrlMaker;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.ServerTimestampParser;
import com.letv.core.utils.MD5;

public class TimestampBean implements LetvBaseBean {
    private static final long serialVersionUID = 1;
    private static final TimestampBean tm = new TimestampBean();
    public boolean mHasRecodeServerTime;
    private int offset;

    public interface FetchServerTimeListener {
        void afterFetch();
    }

    private TimestampBean() {
    }

    public static TimestampBean getTm() {
        return tm;
    }

    public void updateTimestamp(int newTimestamp) {
        this.mHasRecodeServerTime = true;
        this.offset = (int) ((System.currentTimeMillis() / 1000) - ((long) newTimestamp));
    }

    public int getCurServerTime() {
        return (int) ((System.currentTimeMillis() / 1000) - ((long) this.offset));
    }

    public static String generateVideoFileKey(String mid, String tm) {
        StringBuilder builder = new StringBuilder();
        builder.append(mid);
        builder.append(",");
        builder.append(tm);
        builder.append(",");
        builder.append("bh65OzqYYYmHRQ");
        return MD5.toMd5(builder.toString());
    }

    public void getServerTimestamp(FetchServerTimeListener listener) {
        new LetvRequest().setRequestType(RequestManner.NETWORK_ONLY).setCache(new VolleyNoCache()).setUrl(LetvUrlMaker.getTimestamp()).setParser(new ServerTimestampParser()).setCallback(new 1(this, listener)).add();
    }
}
