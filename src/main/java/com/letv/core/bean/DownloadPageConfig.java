package com.letv.core.bean;

import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;

public class DownloadPageConfig {
    public static final int EXPECT_NUM_STYPLE = 3;
    public static final int GRID_STYPLE = 1;
    public static final int LIST_STYPLE = 2;
    public static final int PAGE_SIZE = 50;
    private static final String TAG = "DownloadPageConfig";
    public static DownloadPageConfig sConfig = new DownloadPageConfig();
    public boolean isDataEmpty;
    public int mCurrentStyple;
    public int pageNum = 1;
    public int total;

    public static void initDownloadPageConfig(VideoListBean videoListBean, boolean isPaging) {
        if (videoListBean != null) {
            sConfig.isDataEmpty = videoListBean.size() <= 0;
            if (videoListBean.episodeNum != 0) {
                sConfig.total = videoListBean.episodeNum;
            }
            if (videoListBean.totalNum != 0) {
                sConfig.total = videoListBean.totalNum;
            } else {
                sConfig.total = videoListBean.size();
            }
            sConfig.mCurrentStyple = videoListBean.style;
            if (isPaging) {
                sConfig.pageNum = LetvUtils.calculateRows(sConfig.total, 50);
            } else {
                sConfig.pageNum = 1;
            }
            LogInfo.log(TAG, "initDownloadPageConfig total : " + sConfig.total + " pageNum : " + sConfig.pageNum + " episodeNum : " + videoListBean.episodeNum + " totalNum : " + videoListBean.totalNum);
        }
    }
}
