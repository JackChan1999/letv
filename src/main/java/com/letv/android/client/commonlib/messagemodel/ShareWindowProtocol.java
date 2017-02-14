package com.letv.android.client.commonlib.messagemodel;

import android.view.View;
import com.letv.core.bean.AlbumCardList;
import com.letv.core.bean.VideoBean;

public interface ShareWindowProtocol {
    void hideShareDialog();

    void share(int i, String str, View view, VideoBean videoBean, AlbumCardList albumCardList);

    void share(View view, VideoBean videoBean, AlbumCardList albumCardList);
}
