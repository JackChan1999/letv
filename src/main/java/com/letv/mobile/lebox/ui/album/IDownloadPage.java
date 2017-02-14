package com.letv.mobile.lebox.ui.album;

import android.content.Context;
import android.widget.TextView;
import com.letv.mobile.lebox.utils.Logger;
import com.letv.mobile.letvhttplib.bean.VideoBean;
import com.letv.mobile.letvhttplib.bean.VideoListBean;
import com.letv.mobile.letvhttplib.volley.listener.OnEntryResponse;
import java.util.Map;

public interface IDownloadPage {

    public static class DownloadPageConfig {
        public static final int EXPAND_STYPLE = 3;
        public static final int GRID_STYPLE = 2;
        public static final int LIST_STYPLE = 1;
        public static final int PAGE_SIZE = 50;
        private static final String TAG = DownloadPageConfig.class.getSimpleName();
        public static DownloadPageConfig sConfig = new DownloadPageConfig();
        public long aid;
        public boolean isDataEmpty;
        public boolean isList;
        public boolean isVideoNormal;
        public int mCurrentStyple;
        public int merge;
        public String order;
        public int pageNum;
        public int total;

        public static void initDownloadPageConfig(AlbumInfo album, boolean isVideoNormal, boolean isDataEmpty) {
            if (album != null) {
                Logger.d(TAG, "initDownloadPageConfig isVideoNormal " + isVideoNormal + " isDataEmpty :" + isDataEmpty + " style : " + album.style + " nameCn : " + album.nameCn);
                sConfig.isDataEmpty = isDataEmpty;
                sConfig.isVideoNormal = isVideoNormal;
                sConfig.aid = album.pid;
                sConfig.merge = LetvUtils.getMerge(album.style);
                sConfig.order = LetvUtils.getOrder(album.cid);
                sConfig.total = sConfig.merge == 0 ? album.platformVideoInfo : album.platformVideoNum;
                sConfig.isList = LetvUtils.getIsList(album.style);
                sConfig.pageNum = LetvUtils.calculateRows(sConfig.total, 50);
                if (!isVideoNormal && !isDataEmpty) {
                    sConfig.mCurrentStyple = 1;
                } else if (album.cid == 11) {
                    sConfig.mCurrentStyple = 3;
                } else if (sConfig.isList) {
                    sConfig.mCurrentStyple = 1;
                } else {
                    sConfig.mCurrentStyple = 2;
                }
                Logger.d(TAG, "initPageConfig isList : " + sConfig.isList + " mCurrentStyple : " + sConfig.mCurrentStyple + " cl " + sConfig.pageNum + " totle: " + sConfig.total);
            }
        }
    }

    AlbumInfo getAlbumNew();

    int getCurPage();

    long getCurrentPlayVid();

    DownloadPageConfig getDownloadPageConfig();

    boolean getIsVideoNormal();

    TextView getMoveGridView();

    TextView getMoveListView();

    Map<Integer, VideoListBean> getVideoMap();

    VideoStreamHandler getVideoStreamHandler();

    void requestVideoTask(Context context, long j, int i, OnEntryResponse<VideoListBean> onEntryResponse);

    void setCurPage(int i);

    void startDownLoadinitAnimation(TextView textView, float f, float f2, VideoBean videoBean, int i);
}
