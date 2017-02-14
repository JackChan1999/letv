package com.letv.android.client.share;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.MotionEvent;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.commonlib.config.LetvLoginActivityConfig;
import com.letv.android.client.commonlib.share.ShareResultObserver;
import com.letv.android.client.tencentlogin.TencentInstance;
import com.letv.core.BaseApplication;
import com.letv.core.constant.PlayConstant;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.ActivityUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.ToastUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.share.sina.ex.BSsoHandler;
import com.letv.share.sina.ex.RequestListener;
import com.letv.share.tencent.weibo.ex.ITWeiboNew.TWeiboListener;
import com.sina.weibo.sdk.component.WidgetRequestParam;
import com.tencent.connect.common.Constants;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SharePageActivity extends LetvBaseActivity {
    public static final int ALBUM_LAUNCH_MODE = 2;
    public static final int NOTIFY_ACTIVITY_DESTROY_RESULTCODE = 1100;
    public static int mLaunchMode;
    public static List<ShareResultObserver> observers = new ArrayList();
    private String actor;
    private int cid;
    private String content;
    private String director;
    private int from;
    private String icon;
    private int id;
    private String imageUrl;
    private int launchMode;
    private boolean letvStarIsLogin;
    private boolean letvStarIsShare;
    private String liveUrl;
    private String mAwardUrl;
    private String mFragId;
    private Handler mHandler;
    private int mShareType;
    private BSsoHandler mSsoHandler;
    private String mStaticsId;
    private int order;
    private boolean qqIsShare;
    private boolean qzoomIsShare;
    private int shareMode;
    private String shareUrl;
    private boolean sinaIsLogin;
    private boolean sinaIsShare;
    private boolean tencentIsShare;
    private boolean tencentQzoneIsLogin;
    private boolean tencentWeiboIsLogin;
    private long timeLength;
    private String title;
    private int type;
    private int vid;
    private String year;

    private class RequestTask {
        private Context context;
        final /* synthetic */ SharePageActivity this$0;

        public RequestTask(SharePageActivity sharePageActivity, Context context) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this.this$0 = sharePageActivity;
            this.context = context;
        }

        public void start() {
            this.this$0.mHandler.post(new 1(this));
        }

        public Void doInBackground() {
            if (this.this$0.sinaIsLogin && this.this$0.sinaIsShare) {
                RequestListener sinaListener = new 2(this);
                this.this$0.finishShareAllChannelActivity();
                if (this.this$0.shareMode != -1) {
                    LetvSinaShareSSO.share(this.this$0, this.this$0.getShareContent(this.this$0.shareUrl), this.this$0.icon, this.this$0.shareMode, sinaListener);
                } else {
                    LetvSinaShareSSO.share(this.this$0, this.this$0.getShareContent(this.this$0.shareUrl), this.this$0.icon, this.this$0.shareMode, sinaListener);
                }
            }
            if (this.this$0.tencentWeiboIsLogin && this.this$0.tencentIsShare) {
                TWeiboListener listener = new 3(this);
                this.this$0.finishShareAllChannelActivity();
                LetvTencentWeiboShare.share(this.this$0, this.this$0.getShareContent(this.this$0.shareUrl), this.this$0.icon, false, listener);
            }
            if (this.this$0.qzoomIsShare && this.this$0.from == 3) {
                if (this.this$0.launchMode == 4 || this.this$0.launchMode == 5 || this.this$0.launchMode == 3 || this.this$0.launchMode == 1 || this.this$0.launchMode == 15 || this.this$0.launchMode == 17 || this.this$0.launchMode == 18 || this.this$0.launchMode == 19 || this.this$0.launchMode == 20 || this.this$0.launchMode == 23 || this.this$0.launchMode == 21) {
                    LogInfo.log("fornia", "share---doInBackground");
                    LetvQZoneShare.getInstance(BaseApplication.getInstance()).shareLiveToQzone(this.this$0.icon, this.this$0, this.this$0.title, this.this$0.shareUrl, this.this$0.liveUrl, this.this$0.mStaticsId, this.this$0.mFragId);
                } else if (this.this$0.launchMode == 2) {
                    if (!(LetvShareControl.getInstance().getShare() == null || LetvShareControl.getInstance().getShare().video_url == null)) {
                        LetvQZoneShare.getInstance(BaseApplication.getInstance()).shareToQzone(this.this$0, this.this$0.title, this.this$0.shareUrl, this.this$0.shareMode, this.this$0.mStaticsId, this.this$0.mFragId);
                    }
                } else if (this.this$0.launchMode == 10) {
                    LetvQZoneShare.getInstance(BaseApplication.getInstance()).shareLiveToQzone(this.this$0, this.this$0.title, TextUtils.isEmpty(this.this$0.content) ? "" : this.this$0.content, "", this.this$0.icon, this.this$0.shareUrl, 10, this.this$0.mStaticsId, this.this$0.mFragId);
                } else if (this.this$0.launchMode == 11) {
                    LetvQZoneShare.getInstance(BaseApplication.getInstance()).shareToQzone(this.this$0, this.this$0.title, this.this$0.shareUrl, this.this$0.shareMode, this.this$0.mStaticsId, this.this$0.mFragId);
                } else if (this.this$0.launchMode == 13) {
                    LetvQZoneShare.getInstance(BaseApplication.getInstance()).shareToQzone(this.this$0, this.this$0.title, this.this$0.shareUrl, this.this$0.shareMode, this.this$0.mStaticsId, this.this$0.mFragId);
                } else if (this.this$0.launchMode == 14) {
                    LetvQZoneShare.getInstance(BaseApplication.getInstance()).shareToQzone(this.this$0, this.this$0.title, this.this$0.shareUrl, this.this$0.shareMode, this.this$0.mStaticsId, this.this$0.mFragId);
                } else if (this.this$0.launchMode == 12) {
                    LogInfo.log("fornia", "commentshare 评论qqzone分享 title：" + this.this$0.title + "|shareUrl:" + this.this$0.shareUrl + "|shareMode:" + this.this$0.shareMode);
                    if (!(LetvShareControl.getInstance().getShare() == null || LetvShareControl.getInstance().getShare().video_url == null)) {
                        LogInfo.log("fornia", "commentshare 评论qqzone分享 video_url不为空");
                        LetvQZoneShare.getInstance(BaseApplication.getInstance()).shareToQzone(this.this$0, this.this$0.title, this.this$0.shareUrl, this.this$0.shareMode, this.this$0.mStaticsId, this.this$0.mFragId);
                    }
                } else if (this.this$0.launchMode == 22) {
                    LogInfo.log("fornia", "SharePageEditActivity.SHARE_MODE_RED_PACKET_SPRING shareLiveToQzone:");
                    LetvQZoneShare.getInstance(BaseApplication.getInstance()).shareLiveToQzone(this.this$0, this.this$0.title, this.this$0.content, this.this$0.shareUrl, this.this$0.imageUrl, this.this$0.mStaticsId, this.this$0.mFragId);
                }
            }
            if (this.this$0.qqIsShare && this.this$0.from == 6) {
                LogInfo.log("fornia", "share---SharePageactivity qq！！！！！！！");
                if (this.this$0.launchMode == 4 || this.this$0.launchMode == 5 || this.this$0.launchMode == 3 || this.this$0.launchMode == 1 || this.this$0.launchMode == 15 || this.this$0.launchMode == 17 || this.this$0.launchMode == 18 || this.this$0.launchMode == 19 || this.this$0.launchMode == 20 || this.this$0.launchMode == 21) {
                    LogInfo.log("fornia", "share---SharePageactivity launchMode！！！！！！！");
                    letvTencentShare.getInstance(this.this$0).shareLiveToTencent(this.this$0, this.this$0.title, this.this$0.shareUrl, this.this$0.icon, this.this$0.liveUrl, this.this$0.mStaticsId, this.this$0.mFragId);
                } else if (this.this$0.launchMode == 2) {
                    if (!(LetvShareControl.getInstance().getShare() == null || LetvShareControl.getInstance().getShare().video_url == null)) {
                        letvTencentShare.getInstance(this.this$0).shareToTencent(this.this$0, this.this$0.title, this.this$0.shareUrl, this.this$0.mStaticsId, this.this$0.mFragId);
                    }
                } else if (this.this$0.launchMode == 10) {
                    LogInfo.log("fornia", "share--- SharePageEditActivity.SHARE_MODE_THIRD！！！！！！！");
                    letvTencentShare.getInstance(this.this$0).shareLiveToTencent(this.this$0, this.this$0.title, TextUtils.isEmpty(this.this$0.content) ? "" : this.this$0.content, "", this.this$0.icon, this.this$0.liveUrl, this.this$0.launchMode, this.this$0.mStaticsId, this.this$0.mFragId);
                } else if (this.this$0.launchMode == 11) {
                    letvTencentShare.getInstance(this.this$0).shareLiveToTencent(this.this$0, this.this$0.title, TextUtils.isEmpty(this.this$0.content) ? "" : this.this$0.content, "", this.this$0.icon, this.this$0.liveUrl, this.this$0.launchMode, this.this$0.mStaticsId, this.this$0.mFragId);
                } else if (this.this$0.launchMode == 13) {
                    LogInfo.log("fornia", "share--- SharePageEditActivity.SHARE_MODE_CLICKPLAY_VOTE！！！！！！！");
                    letvTencentShare.getInstance(this.this$0).shareLiveToTencent(this.this$0, this.this$0.title, TextUtils.isEmpty(this.this$0.content) ? "" : this.this$0.content, "", this.this$0.icon, this.this$0.liveUrl, this.this$0.mStaticsId, this.this$0.mFragId);
                } else if (this.this$0.launchMode == 14) {
                    letvTencentShare.getInstance(this.this$0).shareLiveToTencent(this.this$0, this.this$0.title, TextUtils.isEmpty(this.this$0.content) ? "" : this.this$0.content, "", this.this$0.icon, this.this$0.liveUrl, this.this$0.mStaticsId, this.this$0.mFragId);
                } else if (this.this$0.launchMode == 12) {
                    LogInfo.log("fornia", "commentshare 评论qq分享 title：" + this.this$0.title + "|shareUrl:" + this.this$0.shareUrl);
                    if (!(LetvShareControl.getInstance().getShare() == null || LetvShareControl.getInstance().getShare().video_url == null)) {
                        LogInfo.log("fornia", "commentshare 评论qq分享 video_url不为空");
                        letvTencentShare.getInstance(this.this$0).shareToTencent(this.this$0, this.this$0.title, this.this$0.shareUrl, 12, this.this$0.mStaticsId, this.this$0.mFragId);
                    }
                } else if (this.this$0.launchMode == 22) {
                    LogInfo.log("fornia", "share-SHARE_FROM_RED_PACKET_SPRING shareQ shareLiveToTencent");
                    letvTencentShare.getInstance(this.this$0).shareLiveToTencent(this.this$0, this.this$0.title, TextUtils.isEmpty(this.this$0.content) ? "" : this.this$0.content, this.this$0.icon, this.this$0.liveUrl, this.this$0.mStaticsId, this.this$0.mFragId);
                } else {
                    letvTencentShare.getInstance(this.this$0).shareLiveToTencent(this.this$0, this.this$0.title, TextUtils.isEmpty(this.this$0.content) ? "" : this.this$0.content, "", this.this$0.icon, this.this$0.liveUrl, this.this$0.mStaticsId, this.this$0.mFragId);
                }
            }
            return null;
        }
    }

    public SharePageActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.from = 0;
        this.sinaIsLogin = true;
        this.tencentWeiboIsLogin = true;
        this.sinaIsShare = false;
        this.tencentIsShare = false;
        this.qzoomIsShare = false;
        this.qqIsShare = false;
        this.letvStarIsShare = false;
        this.mStaticsId = "";
        this.mFragId = "";
        this.mHandler = new Handler(Looper.myLooper());
        this.mShareType = -1;
        this.shareMode = -1;
    }

    public static void launch(Context activity, int from, String title, String icon, int id, int type, int cid, String year, String director, String actor, long timeLength, int order, int vid, String staticsId, String fragId) {
        Intent intent = new Intent(activity, SharePageActivity.class);
        intent.putExtra("from", from);
        intent.putExtra("launchMode", 2);
        intent.putExtra("title", title);
        intent.putExtra(SettingsJsonConstants.APP_ICON_KEY, icon);
        intent.putExtra("id", id);
        intent.putExtra("type", type);
        intent.putExtra("cid", cid);
        intent.putExtra("year", year);
        intent.putExtra("director", director);
        intent.putExtra("actor", actor);
        intent.putExtra("timeLength", timeLength);
        intent.putExtra("order", order);
        intent.putExtra("vid", vid);
        intent.putExtra("staticsId", staticsId);
        intent.putExtra("fragId", fragId);
        activity.startActivity(intent);
    }

    public static void launch(Context activity, int from, String title, String icon, int id, int type, int cid, String year, String director, String actor, long timeLength, int order, int vid, String content, String staticsId, String fragId) {
        Intent intent = new Intent(activity, SharePageActivity.class);
        intent.putExtra("from", from);
        intent.putExtra("launchMode", 2);
        intent.putExtra("title", title);
        intent.putExtra(SettingsJsonConstants.APP_ICON_KEY, icon);
        intent.putExtra("id", id);
        intent.putExtra("type", type);
        intent.putExtra("cid", cid);
        intent.putExtra("year", year);
        intent.putExtra("director", director);
        intent.putExtra("actor", actor);
        intent.putExtra("timeLength", timeLength);
        intent.putExtra("order", order);
        intent.putExtra("vid", vid);
        intent.putExtra(WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT, content);
        intent.putExtra("staticsId", staticsId);
        intent.putExtra("fragId", fragId);
        activity.startActivity(intent);
    }

    public static void launch(Context activity, int from, String title, String icon, int id, int type, int cid, String year, String director, String actor, long timeLength, int order, int vid, int mode, String content, String staticsId, String fragId) {
        Intent intent = new Intent(activity, SharePageActivity.class);
        intent.putExtra("from", from);
        intent.putExtra("launchMode", mode);
        intent.putExtra("title", title);
        intent.putExtra(SettingsJsonConstants.APP_ICON_KEY, icon);
        intent.putExtra("id", id);
        intent.putExtra("type", type);
        intent.putExtra("cid", cid);
        intent.putExtra("year", year);
        intent.putExtra("director", director);
        intent.putExtra("actor", actor);
        intent.putExtra("timeLength", timeLength);
        intent.putExtra("order", order);
        intent.putExtra("vid", vid);
        intent.putExtra(PlayConstant.LIVE_MODE, mode);
        intent.putExtra(WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT, content);
        intent.putExtra("staticsId", staticsId);
        intent.putExtra("fragId", fragId);
        activity.startActivity(intent);
    }

    public static void launch(Context activity, int from, String title, String icon, String liveUrl, int launchMode, String staticsId, String fragId) {
        LogInfo.log("fornia", "share forlepai---SharePageactivity launch");
        Intent intent = new Intent(activity, SharePageActivity.class);
        intent.putExtra("from", from);
        intent.putExtra("launchMode", launchMode);
        intent.putExtra("title", title);
        intent.putExtra(SettingsJsonConstants.APP_ICON_KEY, icon);
        intent.putExtra("liveUrl", liveUrl);
        intent.putExtra("staticsId", staticsId);
        intent.putExtra("fragId", fragId);
        activity.startActivity(intent);
    }

    public static void launch(Context activity, int from, String title, String icon, String liveUrl, int launchMode, String staticsId, String fragId, String desc) {
        LogInfo.log("fornia", "share forlepai---SharePageactivity launch");
        Intent intent = new Intent(activity, SharePageActivity.class);
        intent.putExtra("from", from);
        intent.putExtra("launchMode", launchMode);
        intent.putExtra("title", title);
        intent.putExtra(WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT, desc);
        intent.putExtra(SettingsJsonConstants.APP_ICON_KEY, icon);
        intent.putExtra("liveUrl", liveUrl);
        intent.putExtra("staticsId", staticsId);
        intent.putExtra("fragId", fragId);
        activity.startActivity(intent);
    }

    public static void launchToLive(Context activity, int from, String title, String liveUrl, String imgUrl, int launchMode, String content, String staticsId, String fragId) {
        LogInfo.log("fornia", "share---SharePageactivity launch");
        Intent intent = new Intent(activity, SharePageActivity.class);
        intent.putExtra("from", from);
        intent.putExtra("launchMode", launchMode);
        intent.putExtra("title", title);
        intent.putExtra(SettingsJsonConstants.APP_ICON_KEY, imgUrl);
        intent.putExtra("liveUrl", liveUrl);
        intent.putExtra(WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT, content);
        intent.putExtra("staticsId", staticsId);
        intent.putExtra("fragId", fragId);
        activity.startActivity(intent);
    }

    public static void launch(Context activity, int from, String title, String content, String icon, String liveUrl, int launchMode, String staticsId, String fragId) {
        Intent intent = new Intent(activity, SharePageActivity.class);
        intent.putExtra("from", from);
        intent.putExtra("launchMode", launchMode);
        intent.putExtra("title", title);
        intent.putExtra(WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT, content);
        intent.putExtra(SettingsJsonConstants.APP_ICON_KEY, icon);
        intent.putExtra("liveUrl", liveUrl);
        intent.putExtra("staticsId", staticsId);
        intent.putExtra("fragId", fragId);
        intent.setFlags(268435456);
        activity.startActivity(intent);
    }

    public static void launch(Context activity, int from, String title, String content, String webUrl, String imgUrl, String awardUrl, int launchMode, String staticsId, String fragId) {
        Intent intent = new Intent(activity, SharePageActivity.class);
        intent.putExtra("from", from);
        intent.putExtra("launchMode", launchMode);
        intent.putExtra("title", title);
        intent.putExtra(WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT, content);
        intent.putExtra(SettingsJsonConstants.APP_ICON_KEY, imgUrl);
        intent.putExtra("liveUrl", webUrl);
        intent.putExtra(LetvLoginActivityConfig.AWARDURL, awardUrl);
        intent.putExtra("staticsId", staticsId);
        intent.putExtra("fragId", fragId);
        activity.startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogInfo.log("fornia", "share---SharePageactivity onCreate");
        Intent intent = getIntent();
        this.from = intent.getIntExtra("from", 0);
        this.launchMode = intent.getIntExtra("launchMode", 2);
        mLaunchMode = intent.getIntExtra("launchMode", 2);
        this.title = intent.getStringExtra("title");
        this.content = intent.getStringExtra(WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT);
        this.liveUrl = intent.getStringExtra("liveUrl");
        this.icon = intent.getStringExtra(SettingsJsonConstants.APP_ICON_KEY);
        this.year = intent.getStringExtra("year");
        this.director = intent.getStringExtra("director");
        this.actor = intent.getStringExtra("actor");
        this.timeLength = intent.getLongExtra("timeLength", 0);
        this.id = intent.getIntExtra("id", 0);
        this.type = intent.getIntExtra("type", 0);
        this.cid = intent.getIntExtra("cid", 0);
        this.order = intent.getIntExtra("order", 1);
        this.vid = intent.getIntExtra("vid", -1);
        this.shareMode = intent.getIntExtra(PlayConstant.LIVE_MODE, -1);
        this.mStaticsId = intent.getStringExtra("staticsId");
        this.mFragId = intent.getStringExtra("fragId");
        this.mAwardUrl = intent.getStringExtra(LetvLoginActivityConfig.AWARDURL);
        if (this.launchMode == 2) {
            this.shareUrl = this.content;
        } else if (this.launchMode == 1) {
            this.shareUrl = this.content;
        } else if (this.launchMode == 15) {
            this.shareUrl = this.content;
        } else if (this.launchMode == 3) {
            this.shareUrl = this.content;
        } else if (this.launchMode == 4) {
            this.shareUrl = this.content;
        } else if (this.launchMode == 5) {
            this.shareUrl = this.content;
        } else if (this.launchMode == 17) {
            this.shareUrl = this.content;
        } else if (this.launchMode == 18) {
            this.shareUrl = this.content;
        } else if (this.launchMode == 19) {
            this.shareUrl = this.content;
        } else if (this.launchMode == 20) {
            this.shareUrl = this.content;
        } else if (this.launchMode == 23) {
            this.shareUrl = this.content;
        } else if (this.launchMode == 21) {
            this.shareUrl = this.content;
        } else if (this.launchMode == 10) {
            this.shareUrl = this.liveUrl;
        } else if (this.launchMode == 11) {
            this.shareUrl = ShareUtils.getVideoShareHint(this.title, this.type, this.id, this.order, this.vid, (long) this.id, (long) this.cid, "", this.content, getShareTypeChannel(this.from), this.from, 0);
        } else if (this.launchMode == 12) {
            this.shareUrl = this.content;
        } else if (this.launchMode == 13) {
            if (TextUtils.isEmpty(this.liveUrl)) {
                this.liveUrl = ShareUtils.getSharePlayUrl(this.type, this.id, 0, this.vid);
            }
            LogInfo.log("fornia", "SharePageEditActivity.SHARE_MODE_CLICKPLAY_VOTE content:" + this.content + "|liveUrl:" + this.liveUrl);
            this.shareUrl = this.content;
        } else if (this.launchMode == 14) {
            this.shareUrl = this.content;
        } else if (this.launchMode == 22) {
            LogInfo.log("fornia", "SharePageEditActivity.SHARE_MODE_RED_PACKET_SPRING content:");
            this.shareUrl = this.liveUrl;
            this.imageUrl = this.icon;
        } else {
            this.shareUrl = "";
        }
        PreferencesManager.getInstance().setSinaIsShare(false);
        PreferencesManager.getInstance().setTencentIsShare(false);
        PreferencesManager.getInstance().setQzoneIsShare(false);
        PreferencesManager.getInstance().setLestarIsShare(false);
        PreferencesManager.getInstance().setQQIsShare(false);
        switch (this.from) {
            case 1:
                PreferencesManager.getInstance().setSinaIsShare(true);
                break;
            case 2:
                PreferencesManager.getInstance().setTencentIsShare(true);
                break;
            case 3:
                PreferencesManager.getInstance().setQzoneIsShare(true);
                break;
            case 5:
                PreferencesManager.getInstance().setLestarIsShare(true);
                break;
            case 6:
                PreferencesManager.getInstance().setQQIsShare(true);
                break;
        }
        isBind();
        requestData();
    }

    private int getShareTypeChannel(int from) {
        switch (from) {
            case 1:
                this.mShareType = 2;
                break;
            case 2:
                this.mShareType = 5;
                break;
            case 3:
                this.mShareType = 3;
                break;
            case 4:
                this.mShareType = 0;
                break;
            case 5:
                this.mShareType = 1;
                break;
            case 6:
                this.mShareType = 4;
                break;
        }
        return this.mShareType;
    }

    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    private String getShareContent(String test) {
        String content = getString(2131100812, new Object[]{""});
        if (test.length() <= 0) {
            return content;
        }
        return getString(2131100812, new Object[]{userc});
    }

    private String getShareCalculateContent(String text) {
        String content = getString(2131100813, new Object[]{""});
        if (text.length() <= 0) {
            return content;
        }
        return getString(2131100813, new Object[]{userc});
    }

    public int count(String text) {
        int doubleC = 0;
        String str = getShareCalculateContent(text);
        while (Pattern.compile("[^\\x00-\\xff]").matcher(str).find()) {
            doubleC++;
        }
        int singelC = str.length() - doubleC;
        if (singelC % 2 != 0) {
            return doubleC + ((singelC + 1) / 2);
        }
        return doubleC + (singelC / 2);
    }

    public int counts(String text) {
        int doubleC = 0;
        while (Pattern.compile("[^\\x00-\\xff]").matcher(text).find()) {
            doubleC++;
        }
        LogInfo.log("LM", "汉字个数  " + doubleC);
        int singelC = text.length() - doubleC;
        if (singelC % 2 != 0) {
            doubleC += (singelC + 1) / 2;
        } else {
            doubleC += singelC / 2;
        }
        LogInfo.log("LM", "总输入数  " + doubleC);
        return doubleC;
    }

    protected void onResume() {
        super.onResume();
        LogInfo.log("fornia", "share---SharePageactivity onResume！！！！！！！");
    }

    protected void onStart() {
        super.onStart();
        LogInfo.log("fornia", "share---SharePageactivity onStart！！！！！！！");
    }

    protected void onPause() {
        super.onPause();
        LogInfo.log("fornia", "share---SharePageactivity onPause！！！！！！！");
    }

    protected void onStop() {
        super.onStop();
        LogInfo.log("fornia", "share---SharePageactivity onStop！！！！！！！");
    }

    protected void onRestart() {
        super.onRestart();
        LogInfo.log("fornia", "share---SharePageactivity onRestart！！！！！！！");
    }

    public void isBind() {
        boolean z = true;
        boolean z2 = LetvSinaShareSSO.isLogin(this) == 1 || LetvSinaShareSSO.isLogin2(this);
        this.sinaIsLogin = z2;
        if (LetvTencentWeiboShare.isLogin(this) != 1) {
            z = false;
        }
        this.tencentWeiboIsLogin = z;
        this.sinaIsShare = PreferencesManager.getInstance().sinaIsShare();
        this.tencentIsShare = PreferencesManager.getInstance().tencentIsShare();
        this.qzoomIsShare = PreferencesManager.getInstance().qzoneIsShare();
        this.qqIsShare = PreferencesManager.getInstance().qqIsShare();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogInfo.log("fornia", "SharePageActivity onActivityResult");
        if (requestCode == Constants.REQUEST_QQ_SHARE && resultCode == 0) {
            LogInfo.log("fornia", "SharePageActivity 1onActivityResult");
            finish();
        }
        if (requestCode == Constants.REQUEST_QZONE_SHARE && resultCode == 0) {
            LogInfo.log("fornia", "SharePageActivity 2onActivityResult");
            finish();
        }
        if (this.mSsoHandler != null) {
            this.mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
        LogInfo.log("fornia", "SharePageActivity onActivityResult resultCode" + resultCode);
    }

    protected void onDestroy() {
        super.onDestroy();
        LogInfo.log("fornia", "SharePageActivity onDestroy");
    }

    private void requestData() {
        if ((!this.sinaIsLogin || !this.sinaIsShare) && ((!this.tencentWeiboIsLogin || !this.tencentIsShare) && ((!this.qzoomIsShare || this.from != 3) && ((!this.qqIsShare || this.from != 6) && (!this.letvStarIsLogin || !this.letvStarIsShare))))) {
            finishShareAllChannelActivity();
            finish();
        } else if (this.qqIsShare && this.from == 6 && !TencentInstance.getInstance(this).isSupportSSOLogin(this)) {
            ToastUtils.showToast((Context) this, "请安装最新版手机QQ客户端");
            finishShareAllChannelActivity();
            finish();
        } else if (this.qzoomIsShare && this.from == 3 && !TencentInstance.getInstance(this).isSupportSSOLogin(this)) {
            ToastUtils.showToast((Context) this, "请安装最新版手机QQ客户端");
            finishShareAllChannelActivity();
            finish();
        } else {
            finish();
            LogInfo.log("fornia", "share---requestData");
            new RequestTask(this, this).start();
        }
    }

    private void sendNotifycation(int notifyId, int textId, int drawableId) {
        NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
        Notification notification = new Notification();
        PendingIntent contentIntent = PendingIntent.getActivity(this, notifyId, new Intent(), 0);
        notification.icon = drawableId;
        notification.tickerText = ShareUtils.getString(textId);
        notification.defaults |= 1;
        notification.flags = 16;
        notification.setLatestEventInfo(this, null, null, contentIntent);
        notificationManager.notify(notifyId, notification);
        notificationManager.cancel(notifyId);
        if (LetvUtils.getBrandName().toLowerCase().contains("xiaomi")) {
            ToastUtils.showToast((Context) this, textId);
        }
    }

    private void finishShareAllChannelActivity() {
        ActivityUtils.getInstance().removeActivity(ShareAllChannelActivity.class.getSimpleName(), true);
    }

    public String[] getAllFragmentTags() {
        return null;
    }

    public String getActivityName() {
        return SharePageActivity.class.getSimpleName();
    }

    public Activity getActivity() {
        return this;
    }
}
