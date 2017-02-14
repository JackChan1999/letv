package com.letv.android.client.commonlib.messagemodel;

import com.letv.android.client.commonlib.listener.AlbumFindNextVideoCallback;
import com.letv.core.bean.AlbumCardList;
import com.letv.core.bean.AlbumPageCard;
import com.letv.core.bean.VideoBean;

public interface AlbumTask$AlbumPlayNexProtocol {
    void findNextVideo(AlbumCardList albumCardList, VideoBean videoBean, AlbumPageCard albumPageCard, AlbumFindNextVideoCallback albumFindNextVideoCallback);

    VideoBean getNextVideoBean();
}
