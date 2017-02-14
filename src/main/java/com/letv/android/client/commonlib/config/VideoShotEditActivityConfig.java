package com.letv.android.client.commonlib.config;

import android.content.Context;
import android.content.Intent;
import com.letv.core.bean.VideoShotShareInfoBean;
import com.letv.core.messagebus.config.LeIntentConfig;

public class VideoShotEditActivityConfig extends LeIntentConfig {
    public static final String SHARE_VIDEOSHOT_DATA_BEAN = "SHARE_VIDEOSHOT_DATA_BEAN";

    public VideoShotEditActivityConfig(Context context) {
        super(context);
    }

    public VideoShotEditActivityConfig create(VideoShotShareInfoBean bean) {
        Intent intent = getIntent();
        if (bean != null) {
            intent.putExtra(SHARE_VIDEOSHOT_DATA_BEAN, bean);
        }
        return this;
    }
}
