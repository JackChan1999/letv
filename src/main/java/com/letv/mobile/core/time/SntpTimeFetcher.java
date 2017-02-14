package com.letv.mobile.core.time;

import com.letv.mobile.core.utils.HandlerUtils;

@Deprecated
public class SntpTimeFetcher implements RemoteTimeFetcher {
    private static final int SNTP_FETCH_TIMEOUT = 3000;
    private static final String SNTP_HOST = "pool.ntp.org";
    private static final SntpClient sClient = new SntpClient();

    public void getCurrentTime(FetchTimeListener listener) {
        HandlerUtils.getWorkingThreadHandler().post(new 1(this, listener));
    }

    private void callListener(FetchTimeListener listener) {
        if (listener != null) {
            HandlerUtils.getUiThreadHandler().post(new 2(this, listener));
        }
    }
}
