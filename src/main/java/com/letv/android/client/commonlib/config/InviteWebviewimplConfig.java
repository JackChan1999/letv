package com.letv.android.client.commonlib.config;

import android.content.Context;
import android.content.Intent;
import com.letv.core.messagebus.config.LeIntentConfig;

public class InviteWebviewimplConfig extends LeIntentConfig {
    public static final String FLOATBALLACTIVEURL = "_floatBallActiveUrl";
    public static final String INVITE_FLAG = "_inviteflag";

    public InviteWebviewimplConfig(Context context) {
        super(context);
    }

    public InviteWebviewimplConfig create(String flag) {
        getIntent().putExtra(INVITE_FLAG, flag);
        return this;
    }

    public InviteWebviewimplConfig create(String flag, String url) {
        Intent intent = getIntent();
        intent.putExtra(INVITE_FLAG, flag);
        intent.putExtra(FLOATBALLACTIVEURL, url);
        return this;
    }
}
