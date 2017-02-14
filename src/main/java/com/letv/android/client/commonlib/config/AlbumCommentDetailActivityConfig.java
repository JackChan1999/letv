package com.letv.android.client.commonlib.config;

import android.content.Context;
import android.content.Intent;
import com.letv.core.bean.AlbumCardList;
import com.letv.core.bean.VideoBean;
import com.letv.core.messagebus.config.LeIntentConfig;

public class AlbumCommentDetailActivityConfig extends LeIntentConfig {
    public static final String FROM = "from";
    public static final String ID = "id";
    public static final int INITIAL_FROM_ALBUM_HALF = 1;
    public static final int INITIAL_FROM_MY_MESSAGE = 2;
    public static final String LEVEL = "level";
    public static AlbumCardList sAlbum;
    public static VideoBean sVideo;

    public AlbumCommentDetailActivityConfig(Context context) {
        super(context);
    }

    public AlbumCommentDetailActivityConfig create(String commentId, int level, VideoBean video, AlbumCardList album) {
        getIntent().putExtra("id", commentId);
        getIntent().putExtra(LEVEL, level);
        getIntent().putExtra("from", 1);
        sVideo = video;
        sAlbum = album;
        return this;
    }

    public AlbumCommentDetailActivityConfig create(String type, String commentId) {
        Intent intent = getIntent();
        intent.putExtra("type", type);
        intent.putExtra("id", commentId);
        getIntent().putExtra("from", 2);
        return this;
    }
}
