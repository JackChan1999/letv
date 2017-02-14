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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.activity.PimBaseActivity;
import com.letv.android.client.commonlib.config.LetvLoginActivityConfig;
import com.letv.android.client.commonlib.share.ShareResultObserver;
import com.letv.android.client.tencentlogin.TencentInstance;
import com.letv.android.client.utils.UIs;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.constant.PlayConstant;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.share.sina.ex.BSsoHandler;
import com.letv.share.tencent.weibo.ex.ITWeiboNew.TWeiboListener;
import com.sina.weibo.sdk.component.WidgetRequestParam;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class SharePageEditActivity extends PimBaseActivity implements OnClickListener {
    public static final int ALBUM_LAUNCH_MODE = 2;
    public static final int SHARE_MODE_BRAND = 20;
    public static final int SHARE_MODE_CLICKPLAY_VOTE = 13;
    public static final int SHARE_MODE_COMMENT = 12;
    public static final int SHARE_MODE_ENT = 4;
    public static final int SHARE_MODE_FINANCE = 19;
    public static final int SHARE_MODE_GAME = 17;
    public static final int SHARE_MODE_HOT = 16;
    public static final int SHARE_MODE_INFORMATION = 18;
    public static final int SHARE_MODE_LIVE_VOTE = 14;
    public static final int SHARE_MODE_LUNBO = 1;
    public static final int SHARE_MODE_MUSIC = 5;
    public static final int SHARE_MODE_OTHER = 21;
    public static final int SHARE_MODE_RED_PACKET_SPRING = 22;
    public static final int SHARE_MODE_SPORT = 3;
    public static final int SHARE_MODE_THIRD = 10;
    public static final int SHARE_MODE_VARIETY = 23;
    public static final int SHARE_MODE_VIDEOSHOT = 11;
    public static final int SHARE_MODE_WEISHI = 15;
    private static Context mActivity;
    public static int mLaunchMode = -1;
    public static List<ShareResultObserver> observers = new ArrayList();
    private String actor;
    private int cid;
    private String comment;
    private String content;
    private String director;
    private int from;
    private String icon;
    private int id;
    private TextView lastLength;
    private int launchMode;
    private boolean letvStarIsLogin;
    private boolean letvStarIsShare;
    private String liveUrl;
    private String mFragId;
    private Handler mHandler;
    private String mLiveId;
    private String mLiveType;
    private String mPlayMark;
    private int mShareType;
    private BSsoHandler mSsoHandler;
    private String mStaticsId;
    private int maxLength;
    private int order;
    private long pid;
    private int pos_cursor;
    private boolean qqIsShare;
    private boolean qzoomIsShare;
    private String role;
    private int shareMode;
    private String shareUrl;
    private boolean sinaIsLogin;
    private boolean sinaIsShare;
    private boolean tencentIsShare;
    private boolean tencentQzoneIsLogin;
    private boolean tencentWeiboIsLogin;
    private long timeLength;
    private String title;
    private TextView top_title;
    private int type;
    private EditText userContent;
    private int vid;
    private int videoType;
    private String voice;
    private String year;

    private class RequestTask {
        private Context context;
        final /* synthetic */ SharePageEditActivity this$0;

        public RequestTask(SharePageEditActivity sharePageEditActivity, Context context) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this.this$0 = sharePageEditActivity;
            this.context = context;
        }

        public void start() {
            this.this$0.mHandler.post(new 1(this));
        }

        public Void doInBackground() {
            if (this.this$0.sinaIsLogin && this.this$0.sinaIsShare) {
                LetvSinaShareSSO.share(this.this$0, this.this$0.userContent.getText().toString().trim(), this.this$0.icon, this.this$0.shareMode, new 2(this));
            }
            if (this.this$0.tencentWeiboIsLogin && this.this$0.tencentIsShare) {
                TWeiboListener listener = new 3(this);
                if (this.this$0.launchMode == 12) {
                    LetvTencentWeiboShare.share(this.this$0, this.this$0.userContent.getText().toString(), this.this$0.icon, false, listener);
                } else {
                    LetvTencentWeiboShare.share(this.this$0, this.this$0.userContent.getText().toString(), this.this$0.icon, false, listener);
                }
            }
            if (this.this$0.qzoomIsShare && this.this$0.from == 3) {
                if (this.this$0.launchMode == 4 || this.this$0.launchMode == 5 || this.this$0.launchMode == 3 || this.this$0.launchMode == 1 || this.this$0.launchMode == 15 || this.this$0.launchMode == 17 || this.this$0.launchMode == 18 || this.this$0.launchMode == 19 || this.this$0.launchMode == 20 || this.this$0.launchMode == 21) {
                    LetvQZoneShare.getInstance(this.this$0).shareLiveToQzone(this.this$0.icon, this.this$0, this.this$0.title, this.this$0.userContent.getText().toString(), this.this$0.liveUrl, this.this$0.mStaticsId, this.this$0.mFragId);
                } else if (!(this.this$0.launchMode != 2 || LetvShareControl.getInstance().getShare() == null || LetvShareControl.getInstance().getShare().video_url == null)) {
                    LetvQZoneShare.getInstance(this.this$0).shareToQzone(this.this$0, this.this$0.title, this.this$0.userContent.getText().toString(), -1, this.this$0.mStaticsId, this.this$0.mFragId);
                }
            }
            if (this.this$0.qqIsShare && this.this$0.from == 6) {
                if (this.this$0.launchMode == 4 || this.this$0.launchMode == 5 || this.this$0.launchMode == 3 || this.this$0.launchMode == 1 || this.this$0.launchMode == 15 || this.this$0.launchMode == 17 || this.this$0.launchMode == 18 || this.this$0.launchMode == 19 || this.this$0.launchMode == 20 || this.this$0.launchMode == 21) {
                    letvTencentShare.getInstance(this.this$0).shareLiveToTencent(this.this$0, this.this$0.title, this.this$0.userContent.getText().toString(), "", this.this$0.liveUrl, this.this$0.mStaticsId, this.this$0.mFragId);
                } else if (this.this$0.launchMode != 2) {
                    letvTencentShare.getInstance(this.this$0).shareLiveToTencent(this.this$0, this.this$0.title, this.this$0.shareUrl, this.this$0.icon, this.this$0.liveUrl, this.this$0.mStaticsId, this.this$0.mFragId);
                } else if (!(LetvShareControl.getInstance().getShare() == null || LetvShareControl.getInstance().getShare().video_url == null)) {
                    letvTencentShare.getInstance(this.this$0).shareToTencent(this.this$0, this.this$0.title, this.this$0.userContent.getText().toString(), this.this$0.mStaticsId, this.this$0.mFragId);
                }
            }
            return null;
        }
    }

    public SharePageEditActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.from = 0;
        this.maxLength = 140;
        this.sinaIsLogin = true;
        this.tencentWeiboIsLogin = true;
        this.sinaIsShare = false;
        this.tencentIsShare = false;
        this.qzoomIsShare = false;
        this.qqIsShare = false;
        this.letvStarIsShare = false;
        this.role = "";
        this.voice = "";
        this.pos_cursor = 0;
        this.mStaticsId = "";
        this.mFragId = "";
        this.mLiveType = "";
        this.videoType = 2;
        this.mShareType = -1;
        this.mHandler = new Handler(Looper.myLooper());
        this.shareMode = -1;
    }

    public static void launch(Context activity, int from, String title, String icon, int id, int type, int cid, String year, String director, String actor, long timeLength, int order, int vid, long pid, String voice, String playMark, int mode, String staticsId, String fragId) {
        mActivity = null;
        LogInfo.log("fornia", "album id:" + id + "cid" + cid + "vid" + vid + "pid:" + pid);
        Intent intent = new Intent(activity, SharePageEditActivity.class);
        intent.putExtra("from", from);
        if (mode == -1) {
            intent.putExtra("launchMode", 2);
        } else if (mode == 5) {
            intent.putExtra("launchMode", 12);
        } else if (mode == 4) {
            intent.putExtra("launchMode", 11);
        } else if (mode == 9) {
            intent.putExtra("launchMode", 16);
        } else if (mode == 1) {
            intent.putExtra("launchMode", 2);
        }
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
        intent.putExtra("pid", pid);
        intent.putExtra("voice", voice);
        intent.putExtra(PlayConstant.LIVE_MODE, mode);
        intent.putExtra("playMark", playMark);
        intent.putExtra("staticsId", staticsId);
        intent.putExtra("fragId", fragId);
        activity.startActivity(intent);
    }

    public static void launch(Context activity, int from, String title, String icon, int id, int type, int cid, String year, String director, String actor, long timeLength, int order, int vid, int mode, String comment, String staticsId, String fragId) {
        LogInfo.log("fornia", "标题 title:" + title + "评论或者角色 mode:" + mode + "comment:" + comment);
        mActivity = null;
        Intent intent = new Intent(activity, SharePageEditActivity.class);
        intent.putExtra("from", from);
        if (mode == -1) {
            intent.putExtra("launchMode", 2);
            intent.putExtra("comment", comment);
        } else if (mode == 5) {
            intent.putExtra("launchMode", 12);
            intent.putExtra("comment", comment);
        } else if (mode == 4) {
            intent.putExtra("launchMode", 11);
            intent.putExtra("comment", comment);
        } else if (mode == 6) {
            intent.putExtra("launchMode", 13);
            intent.putExtra("role", comment);
        }
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
        intent.putExtra("staticsId", staticsId);
        intent.putExtra("fragId", fragId);
        activity.startActivity(intent);
    }

    public static void launch(Context activity, int from, String content, String liveUrl, String liveType, String liveId, int launchMode, String staticsId, String fragId) {
        mActivity = null;
        Intent intent = new Intent(activity, SharePageEditActivity.class);
        intent.putExtra("from", from);
        intent.putExtra("launchMode", launchMode);
        intent.putExtra("title", content);
        intent.putExtra("liveUrl", liveUrl);
        intent.putExtra("liveType", liveType);
        intent.putExtra("liveId", liveId);
        intent.putExtra("staticsId", staticsId);
        intent.putExtra("fragId", fragId);
        activity.startActivity(intent);
    }

    public static void launch(Context activity, int from, String content, String liveUrl, String liveId, int launchMode, String staticsId, String fragId) {
        mActivity = null;
        Intent intent = new Intent(activity, SharePageEditActivity.class);
        intent.putExtra("from", from);
        intent.putExtra("launchMode", launchMode);
        intent.putExtra("title", content);
        intent.putExtra("liveUrl", liveUrl);
        intent.putExtra("liveId", liveId);
        intent.putExtra("staticsId", staticsId);
        intent.putExtra("fragId", fragId);
        activity.startActivity(intent);
    }

    public static void launch(Context activity, int from, String content, String liveUrl, int launchMode, String role, String staticsId, String fragId) {
        mActivity = null;
        Intent intent = new Intent(activity, SharePageEditActivity.class);
        intent.putExtra("from", from);
        intent.putExtra("launchMode", launchMode);
        intent.putExtra("title", content);
        intent.putExtra("liveUrl", liveUrl);
        intent.putExtra("role", role);
        intent.putExtra("staticsId", staticsId);
        intent.putExtra("fragId", fragId);
        activity.startActivity(intent);
    }

    public static void launch(Context activity, int from, String shareTitle, String content, String shareUrl, String awardUrl, String imgUrl, int launchMode, String staticsId, String fragId) {
        mActivity = activity;
        Intent intent = new Intent(activity, SharePageEditActivity.class);
        intent.putExtra("from", from);
        intent.putExtra("launchMode", launchMode);
        intent.putExtra("title", shareTitle);
        intent.putExtra(WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT, content);
        intent.putExtra("shareUrl", shareUrl);
        intent.putExtra(LetvLoginActivityConfig.AWARDURL, awardUrl);
        intent.putExtra(SettingsJsonConstants.APP_ICON_KEY, imgUrl);
        intent.putExtra("staticsId", staticsId);
        intent.putExtra("fragId", fragId);
        activity.startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        this.pid = intent.getLongExtra("pid", 0);
        this.shareMode = intent.getIntExtra(PlayConstant.LIVE_MODE, -1);
        this.comment = intent.getStringExtra("comment");
        this.voice = intent.getStringExtra("voice");
        this.role = intent.getStringExtra("role");
        this.mLiveType = intent.getStringExtra("liveType");
        this.mLiveId = intent.getStringExtra("liveId");
        this.mPlayMark = intent.getStringExtra("playMark");
        this.videoType = ShareUtils.getShareVideoType(this.cid, this.mPlayMark);
        this.mStaticsId = intent.getStringExtra("staticsId");
        this.mFragId = intent.getStringExtra("fragId");
        this.shareUrl = intent.getStringExtra("shareUrl");
        findView();
        this.pos_cursor = this.userContent.getEditableText().length();
    }

    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void findView() {
        this.userContent = (EditText) findViewById(R.id.ShareText);
        this.lastLength = (TextView) findViewById(R.id.maxlength);
        this.top_title = (TextView) findViewById(R.id.top_title);
        findViewById(R.id.top_button_Share).setOnClickListener(this);
        findViewById(R.id.top_button).setOnClickListener(this);
        if (this.launchMode == 2) {
            this.shareUrl = ShareUtils.getShareHint(this.title, this.type, this.id, this.order, this.vid, this.pid, (long) this.cid, this.videoType, this.from, 0);
        } else if (this.launchMode == 1) {
            this.shareUrl = ShareUtils.getShareHitFroLive(this.title, ShareUtils.getAnalysisLiveShareUrl(this.launchMode, this.mLiveType, this.mLiveId, this.from, 0));
        } else if (this.launchMode == 15) {
            this.shareUrl = ShareUtils.getShareHitFroLive(this.title, ShareUtils.getAnalysisLiveShareUrl(this.launchMode, this.mLiveType, this.mLiveId, this.from, 0));
        } else if (this.launchMode == 3) {
            this.shareUrl = ShareUtils.getShareHitFroLive(this.title, ShareUtils.getAnalysisLiveShareUrl(this.launchMode, this.mLiveType, this.mLiveId, this.from, 0));
        } else if (this.launchMode == 4) {
            this.shareUrl = ShareUtils.getShareHitFroLive(this.title, ShareUtils.getAnalysisLiveShareUrl(this.launchMode, this.mLiveType, this.mLiveId, this.from, 0));
        } else if (this.launchMode == 5) {
            this.shareUrl = ShareUtils.getShareHitFroLive(this.title, ShareUtils.getAnalysisLiveShareUrl(this.launchMode, this.mLiveType, this.mLiveId, this.from, 0));
        } else if (this.launchMode == 17) {
            this.shareUrl = ShareUtils.getShareHitFroLive(this.title, ShareUtils.getAnalysisLiveShareUrl(this.launchMode, this.mLiveType, this.mLiveId, this.from, 0));
        } else if (this.launchMode == 18) {
            this.shareUrl = ShareUtils.getShareHitFroLive(this.title, ShareUtils.getAnalysisLiveShareUrl(this.launchMode, this.mLiveType, this.mLiveId, this.from, 0));
        } else if (this.launchMode == 19) {
            this.shareUrl = ShareUtils.getShareHitFroLive(this.title, ShareUtils.getAnalysisLiveShareUrl(this.launchMode, this.mLiveType, this.mLiveId, this.from, 0));
        } else if (this.launchMode == 20) {
            this.shareUrl = ShareUtils.getShareHitFroLive(this.title, ShareUtils.getAnalysisLiveShareUrl(this.launchMode, this.mLiveType, this.mLiveId, this.from, 0));
        } else if (this.launchMode == 21) {
            this.shareUrl = ShareUtils.getShareHitFroLive(this.title, ShareUtils.getAnalysisLiveShareUrl(this.launchMode, this.mLiveType, this.mLiveId, this.from, 0));
        } else if (this.launchMode == 23) {
            this.shareUrl = ShareUtils.getShareHitFroLive(this.title, ShareUtils.getAnalysisLiveShareUrl(this.launchMode, this.mLiveType, this.mLiveId, this.from, 0));
        } else if (this.launchMode == 10) {
            this.shareUrl = this.liveUrl;
        } else if (this.launchMode == 10) {
            this.shareUrl = this.title + getResources().getString(2131100815) + this.liveUrl;
        } else if (this.launchMode == 11) {
            LogInfo.log("fornia", "launchMode == SharePageEditActivity.SHARE_MODE_VIDEOSHOT:");
            this.shareUrl = ShareUtils.getVideoShareHint(this.title, this.type, this.id, this.order, this.vid, this.pid, (long) this.cid, "", this.voice, getShareTypeChannel(this.from), this.from, 0);
        } else if (this.launchMode == 12) {
            this.shareUrl = this.comment;
        } else if (this.launchMode == 13) {
            this.shareUrl = ShareUtils.getVoteShareHint(this.title, this.type, (long) this.id, this.vid, this.role);
            this.title = "";
            LogInfo.log("fornia", "SHARE_MODE_CLICKPLAY_VOTE shareUrl:" + this.shareUrl);
        } else if (this.launchMode == 14) {
            this.shareUrl = ShareUtils.getLiveVoteShareHint(this.title, this.liveUrl, this.role);
            this.title = "";
            LogInfo.log("fornia", "SHARE_MODE_LIVE_VOTE shareUrl:" + this.shareUrl);
        } else if (this.launchMode == 16) {
            this.shareUrl = ShareUtils.getHotShareHint(this.title, this.type, this.id, this.order, this.vid, this.pid, (long) this.cid, 2, this.from, 0);
        } else if (this.launchMode == 22) {
            this.shareUrl = (TextUtils.isEmpty(this.title) ? "" : this.title) + " " + (TextUtils.isEmpty(this.content) ? "" : this.content) + " " + (TextUtils.isEmpty(this.shareUrl) ? "" : this.shareUrl);
        }
        this.userContent.addTextChangedListener(new TextWatcher(this) {
            final /* synthetic */ SharePageEditActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                String ss = s.toString();
                this.this$0.maxLength = 140 - this.this$0.counts(ss);
                if (this.this$0.maxLength < 0) {
                    this.this$0.lastLength.setTextColor(this.this$0.getResources().getColor(2131493369));
                } else {
                    this.this$0.lastLength.setTextColor(this.this$0.getResources().getColor(2131493233));
                }
                LogInfo.log("LM", "maxlength  " + this.this$0.maxLength + "  count  " + this.this$0.counts(ss));
                this.this$0.lastLength.setText(this.this$0.maxLength + "");
                this.this$0.pos_cursor = this.this$0.userContent.getSelectionStart();
            }
        });
        this.userContent.setText(this.shareUrl);
        this.userContent.setSelection(this.userContent.getEditableText().length());
    }

    public void setTopTitle() {
        switch (this.from) {
            case 1:
                this.top_title.setText(2131100839);
                return;
            case 2:
                this.top_title.setText(2131100835);
                return;
            case 3:
                this.top_title.setText(2131100836);
                return;
            case 4:
                return;
            case 5:
                this.top_title.setText(2131100818);
                return;
            case 6:
                this.top_title.setText(2131100841);
                return;
            default:
                this.top_title.setText(2131100840);
                return;
        }
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
        PreferencesManager.getInstance().setSinaIsShare(false);
        PreferencesManager.getInstance().setTencentIsShare(false);
        PreferencesManager.getInstance().setQzoneIsShare(false);
        PreferencesManager.getInstance().setLestarIsShare(false);
        PreferencesManager.getInstance().setQQIsShare(false);
        setTopTitle();
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
        if (this.mSsoHandler != null) {
            this.mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.top_button /*2131364326*/:
                Iterator it = observers.iterator();
                if (it.hasNext()) {
                    ((ShareResultObserver) it.next()).onCanneled();
                }
                mLaunchMode = -1;
                UIsUtils.hideSoftkeyboard(this);
                finish();
                return;
            case R.id.top_button_Share /*2131364328*/:
                if (!NetworkUtils.isNetworkAvailable()) {
                    ToastUtils.showToast(this.mContext, 2131101012);
                    return;
                } else if (this.maxLength < 0) {
                    UIs.callDialogMsgPositiveButton(this, DialogMsgConstantId.SEVEN_ZERO_TWO_CONSTANT, null);
                    return;
                } else if ((!this.sinaIsLogin || !this.sinaIsShare) && ((!this.tencentWeiboIsLogin || !this.tencentIsShare) && ((!this.qzoomIsShare || this.from != 3) && ((!this.qqIsShare || this.from != 6) && (!this.letvStarIsLogin || !this.letvStarIsShare))))) {
                    return;
                } else {
                    if (this.qqIsShare && this.from == 6 && !TencentInstance.getInstance(this).isSupportSSOLogin(this)) {
                        ToastUtils.showToast((Context) this, getString(2131100834));
                        return;
                    } else if (this.qzoomIsShare && this.from == 3 && !TencentInstance.getInstance(this).isSupportSSOLogin(this)) {
                        ToastUtils.showToast((Context) this, getString(2131100834));
                        return;
                    } else {
                        new RequestTask(this, this).start();
                        UIsUtils.hideSoftkeyboard(this);
                        return;
                    }
                }
            default:
                return;
        }
    }

    public int getContentView() {
        return R.layout.share_page;
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

    public String getActivityName() {
        return SharePageEditActivity.class.getSimpleName();
    }

    public Activity getActivity() {
        return this;
    }
}
