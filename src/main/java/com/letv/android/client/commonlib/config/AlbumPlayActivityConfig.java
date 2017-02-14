package com.letv.android.client.commonlib.config;

import android.content.Context;
import android.content.Intent;
import com.letv.core.BaseApplication;
import com.letv.core.constant.PlayConstant;
import com.letv.core.constant.PlayConstant.VideoType;
import com.letv.core.messagebus.config.LeIntentConfig;
import com.letv.core.utils.StatisticsUtils;
import com.letv.mobile.lebox.LeboxApiManager.LeboxVideoBean;

public class AlbumPlayActivityConfig extends LeIntentConfig {
    public static final String FLOAT_TOPIC_HALF_NINE = "9";
    public static final String HALF_TAG = "half_tag_";

    public AlbumPlayActivityConfig(Context context) {
        super(context);
        StatisticsUtils.mClickImageForPlayTime = System.currentTimeMillis();
    }

    public void run() {
        if (getIntent() != null) {
            getIntent().addFlags(536870912);
        }
        super.run();
    }

    public AlbumPlayActivityConfig create(long aid, long vid, int from) {
        return create(aid, vid, from, false, "");
    }

    public AlbumPlayActivityConfig create(long aid, long vid, int from, boolean noCopyright, String noCopyrightUrl) {
        int launchMode;
        if (20 != from) {
            launchMode = 2;
        } else {
            launchMode = 3;
        }
        Intent intent = getIntent();
        intent.putExtra("launchMode", launchMode);
        intent.putExtra("aid", aid);
        intent.putExtra("vid", vid);
        intent.putExtra("from", from);
        intent.putExtra(PlayConstant.NO_COPYRIGHT, noCopyright);
        intent.putExtra(PlayConstant.NO_COPYRIGHT_URL, noCopyrightUrl);
        intent.addFlags(67108864);
        return this;
    }

    public AlbumPlayActivityConfig create(long aid, long vid, VideoType videoType, int from, boolean nonCopyright, String nonCopyrightUrl) {
        int launchMode;
        if (videoType == VideoType.Normal) {
            launchMode = 2;
        } else {
            launchMode = 3;
        }
        Intent intent = getIntent();
        intent.putExtra("launchMode", launchMode);
        intent.putExtra("aid", aid);
        intent.putExtra("vid", vid);
        intent.putExtra(PlayConstant.VIDEO_TYPE, videoType);
        intent.putExtra("from", from);
        intent.putExtra(PlayConstant.NO_COPYRIGHT, nonCopyright);
        intent.putExtra(PlayConstant.NO_COPYRIGHT_URL, nonCopyrightUrl);
        return this;
    }

    public AlbumPlayActivityConfig create(long aid, long vid, int from, long seek) {
        Intent intent = getIntent();
        intent.putExtra("launchMode", 2);
        intent.putExtra("aid", aid);
        intent.putExtra("vid", vid);
        intent.putExtra("from", from);
        intent.putExtra("seek", seek);
        return this;
    }

    public AlbumPlayActivityConfig createTopic(long zid, int from) {
        Intent intent = getIntent();
        intent.putExtra("launchMode", 6);
        intent.putExtra(PlayConstant.ZID, zid);
        intent.putExtra("from", from);
        intent.putExtra(PlayConstant.FLOATPAGEINDEX, "9");
        return this;
    }

    public AlbumPlayActivityConfig createTopic(long zid, int from, boolean nonCopyright, String nonCopyrightUrl) {
        Intent intent = getIntent();
        intent.putExtra("launchMode", 6);
        intent.putExtra(PlayConstant.ZID, zid);
        intent.putExtra("from", from);
        intent.putExtra(PlayConstant.FLOATPAGEINDEX, "9");
        intent.putExtra(PlayConstant.NO_COPYRIGHT, nonCopyright);
        intent.putExtra(PlayConstant.NO_COPYRIGHT_URL, nonCopyrightUrl);
        return this;
    }

    public AlbumPlayActivityConfig createTopic(long zid, long pid, long pvid, int from) {
        return createTopic(zid, pid, pvid, from, false, "");
    }

    public AlbumPlayActivityConfig createTopic(long zid, long pid, long pvid, int from, boolean nonCopyright, String nonCopyrightUrl) {
        Intent intent = getIntent();
        intent.putExtra("launchMode", 6);
        intent.putExtra(PlayConstant.ZID, zid);
        intent.putExtra("pid", pid);
        intent.putExtra("vid", pvid);
        intent.putExtra("from", from);
        intent.putExtra(PlayConstant.FLOATPAGEINDEX, "9");
        intent.putExtra(PlayConstant.NO_COPYRIGHT, nonCopyright);
        intent.putExtra(PlayConstant.NO_COPYRIGHT_URL, nonCopyrightUrl);
        return this;
    }

    public AlbumPlayActivityConfig create4D(String videoUrl, String haptUrl, int playMode) {
        Intent intent = getIntent();
        intent.putExtra("launchMode", 1);
        intent.putExtra(PlayConstant.URI, videoUrl);
        intent.putExtra(PlayConstant.HAPT_URL, haptUrl);
        intent.putExtra(PlayConstant.PLAY_MODE, playMode);
        return this;
    }

    public AlbumPlayActivityConfig createLebox(LeboxVideoBean videoBean) {
        Intent intent = getIntent();
        intent.putExtra("launchMode", 7);
        intent.putExtra(PlayConstant.LEBOX_VIDEO, videoBean);
        intent.putExtra("from", 23);
        return this;
    }

    public AlbumPlayActivityConfig createLocal(String uriString, int playMode) {
        Intent intent = getIntent();
        intent.putExtra("launchMode", 1);
        intent.putExtra(PlayConstant.URI, uriString);
        intent.putExtra(PlayConstant.PLAY_MODE, playMode);
        return this;
    }

    public AlbumPlayActivityConfig createLocal(String uriString, int playMode, long seek) {
        Intent intent = getIntent();
        intent.putExtra("launchMode", 1);
        intent.putExtra(PlayConstant.URI, uriString);
        intent.putExtra("seek", seek);
        intent.putExtra(PlayConstant.PLAY_MODE, playMode);
        return this;
    }

    public AlbumPlayActivityConfig createDownload(long aid, long vid, int from, String pageId) {
        if (from == 11) {
            BaseApplication.getInstance().setFromHalf(true);
        } else {
            BaseApplication.getInstance().setFromHalf(false);
        }
        Intent intent = getIntent();
        intent.putExtra("launchMode", 4);
        intent.putExtra("aid", aid);
        intent.putExtra("vid", vid);
        intent.putExtra("from", 11);
        intent.putExtra(PlayConstant.PAGE_ID, pageId);
        return this;
    }

    public AlbumPlayActivityConfig createForWebPay(long aid, long vid, int from, boolean isPay) {
        int launchMode;
        if (20 != from) {
            launchMode = 2;
        } else {
            launchMode = 3;
        }
        Intent intent = getIntent();
        intent.putExtra("launchMode", launchMode);
        intent.putExtra("aid", aid);
        intent.putExtra("vid", vid);
        intent.putExtra("from", from);
        intent.putExtra(PlayConstant.IS_PAY, isPay);
        return this;
    }

    public AlbumPlayActivityConfig createQRCode(long aid, long vid, long htime, String ref, int from) {
        Intent intent = getIntent();
        intent.putExtra("launchMode", 2);
        intent.putExtra("aid", aid);
        intent.putExtra("vid", vid);
        intent.putExtra(PlayConstant.HTIME, htime);
        intent.putExtra(PlayConstant.REF, ref);
        intent.putExtra("from", from);
        return this;
    }
}
