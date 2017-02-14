package com.letv.android.client.ui.downloadpage;

import android.widget.TextView;
import com.letv.core.bean.AlbumInfo;
import com.letv.core.bean.DownloadPageConfig;
import com.letv.core.bean.VideoBean;
import com.letv.core.bean.VideoListBean;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.utils.VideoStreamHandler;
import java.util.Map;

public interface IDownloadPage {
    AlbumInfo getAlbumNew();

    int getCurPage();

    long getCurrentPlayVid();

    DownloadPageConfig getDownloadPageConfig();

    boolean getIsVideoNormal();

    TextView getMoveGridView();

    TextView getMoveListView();

    Map<String, VideoListBean> getVideoMap();

    VideoStreamHandler getVideoStreamHandler();

    void requestEpisodeVideolist(int i, SimpleResponse simpleResponse);

    void requestPeriodsVideolist(String str, SimpleResponse simpleResponse);

    void setCurPage(int i);

    void startDownLoadinitAnimation(TextView textView, float f, float f2, VideoBean videoBean, int i);
}
