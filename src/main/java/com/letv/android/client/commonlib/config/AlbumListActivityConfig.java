package com.letv.android.client.commonlib.config;

import android.content.Context;
import android.content.Intent;
import com.letv.core.bean.AlbumInfo;
import com.letv.core.messagebus.config.LeIntentConfig;
import java.util.ArrayList;

public class AlbumListActivityConfig extends LeIntentConfig {
    public static final String INTENT_KEY_ALBUM_LIST = "album_list";
    public static final String INTENT_KEY_TITLE = "title";

    public AlbumListActivityConfig(Context context) {
        super(context);
    }

    public AlbumListActivityConfig create(ArrayList<AlbumInfo> list, String title) {
        Intent intent = getIntent();
        intent.putExtra(INTENT_KEY_ALBUM_LIST, list);
        intent.putExtra("title", title);
        return this;
    }
}
