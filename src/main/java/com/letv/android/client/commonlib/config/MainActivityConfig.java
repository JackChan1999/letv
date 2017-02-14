package com.letv.android.client.commonlib.config;

import android.content.Context;
import android.content.Intent;
import com.letv.core.bean.ChannelListBean.Channel;
import com.letv.core.constant.FragmentConstant;
import com.letv.core.messagebus.config.LeIntentConfig;
import com.letv.core.messagebus.config.LeIntentConfig.IntentFlag;

public class MainActivityConfig extends LeIntentConfig {
    public static final String CHILD_LIVE_ID = "childLiveId";
    public static final String IS_FACEBOOK_INTO_HOMEPAGE = "isFacebookIntoHomePage";
    public static final String TAG = "tag";

    public MainActivityConfig(Context context) {
        super(context);
    }

    public MainActivityConfig fromFaceBook() {
        getIntent().putExtra(IS_FACEBOOK_INTO_HOMEPAGE, "true");
        return this;
    }

    public MainActivityConfig create(String tag, String childLiveId) {
        Intent intent = getIntent();
        intent.putExtra("tag", tag);
        intent.putExtra(CHILD_LIVE_ID, childLiveId);
        return this;
    }

    public MainActivityConfig createForTab(String tag) {
        Intent intent = getIntent();
        intent.putExtra("tag", tag);
        intent.putExtra("from_M", true);
        intent.setFlags(268435456);
        setIntentFlag(IntentFlag.START_ACTIVITY_FOR_RESULT);
        setRequestCode(10001);
        return this;
    }

    public MainActivityConfig createForChannel(int cid, int pageId, String cname, int type) {
        Intent intent = getIntent();
        Channel channel = new Channel();
        channel.id = cid;
        channel.pageid = pageId + "";
        channel.type = type;
        channel.name = cname;
        intent.putExtra("tag", FragmentConstant.TAG_FRAGMENT_CHANNEL);
        intent.putExtra("channel", channel);
        intent.putExtra("from_M", true);
        setIntentFlag(IntentFlag.START_ACTIVITY_FOR_RESULT);
        setRequestCode(10001);
        return this;
    }
}
