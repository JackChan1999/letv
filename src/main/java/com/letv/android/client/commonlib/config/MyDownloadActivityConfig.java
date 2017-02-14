package com.letv.android.client.commonlib.config;

import android.content.Context;
import android.content.Intent;
import com.letv.core.messagebus.config.LeIntentConfig;

public class MyDownloadActivityConfig extends LeIntentConfig {
    public static final String FROM_PUSH = "fromPush";
    public static final String PAGE = "page";
    public static final String TO_DOWNLOAD = "toDownload";
    public static final String TO_HOME_PAGE = "toHomePage";

    public MyDownloadActivityConfig(Context context) {
        super(context);
    }

    public MyDownloadActivityConfig create(boolean fromPush, boolean toHomePage, boolean toDownload) {
        Intent intent = getIntent();
        intent.putExtra(FROM_PUSH, fromPush);
        intent.putExtra(TO_HOME_PAGE, toHomePage);
        intent.putExtra(TO_DOWNLOAD, toDownload);
        return this;
    }

    public MyDownloadActivityConfig create(int page) {
        Intent intent = getIntent();
        intent.putExtra(PAGE, page);
        intent.setFlags(268435456);
        return this;
    }
}
