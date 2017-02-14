package com.letv.android.client.commonlib.config;

import android.content.Context;
import com.letv.core.messagebus.config.LeIntentConfig;
import com.letv.core.messagebus.config.LeIntentConfig.IntentFlag;

public class LivePayWebViewActivityConfig extends LeIntentConfig {
    public static final String URL = "url";

    public LivePayWebViewActivityConfig(Context context) {
        super(context);
    }

    public LivePayWebViewActivityConfig create(int requestCode, String url) {
        getIntent().putExtra("url", url);
        setIntentFlag(IntentFlag.START_ACTIVITY_FOR_RESULT);
        setRequestCode(requestCode);
        return this;
    }
}
