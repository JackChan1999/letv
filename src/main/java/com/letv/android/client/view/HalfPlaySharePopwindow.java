package com.letv.android.client.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.facebook.CallbackManager;
import com.facebook.CallbackManager.Factory;
import com.facebook.share.widget.ShareDialog;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.activity.BasePlayActivity;
import com.letv.android.client.activity.PaySucceedActivity;
import com.letv.android.client.commonlib.listener.GiftShareAwardCallback;
import com.letv.android.client.controller.PlayLiveController;
import com.letv.android.client.share.LetvFacebookShare;
import com.letv.android.client.share.LetvQZoneShare;
import com.letv.android.client.share.LetvShareControl;
import com.letv.android.client.share.LetvSinaShareSSO;
import com.letv.android.client.share.LetvTencentWeiboShare;
import com.letv.android.client.share.LetvWeixinShare;
import com.letv.android.client.share.ShareUtils;
import com.letv.android.client.share.letvTencentShare;
import com.letv.android.client.utils.UIs;
import com.letv.core.BaseApplication;
import com.letv.core.bean.AlbumCardList;
import com.letv.core.bean.LetvBaseBean;
import com.letv.core.bean.LiveLunboProgramListBean;
import com.letv.core.bean.LiveRemenListBean.LiveRemenBaseBean;
import com.letv.core.bean.ProgramEntity;
import com.letv.core.bean.ShareAlbumBean;
import com.letv.core.bean.TipMapBean.TipBean;
import com.letv.core.bean.VideoBean;
import com.letv.core.bean.VideoShotShareInfoBean;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.download.image.ImageDownloadStateListener;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.utils.FileUtils;
import com.letv.core.utils.LetvDateUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.StoreUtils;
import com.letv.core.utils.StringUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.pp.utils.NetworkUtils;
import com.letv.share.sina.ex.BSsoHandler;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class HalfPlaySharePopwindow extends PopupWindow implements OnClickListener {
    public static final int SHARE_FROM_ALBUM_PANO = 12;
    public static final int SHARE_FROM_CLICK_PLAY = 1;
    public static final int SHARE_FROM_CLICK_PLAY_VOTE = 6;
    public static final int SHARE_FROM_COMMENT = 5;
    public static final int SHARE_FROM_COMP_OLD_VALUE = -1;
    public static final int SHARE_FROM_GIFT_MONEY_HALFPAGE = 10;
    public static final int SHARE_FROM_GIFT_MONEY_MAINPAGE = 8;
    public static final int SHARE_FROM_HOT = 9;
    public static final int SHARE_FROM_LIVE = 3;
    public static final int SHARE_FROM_LIVE_PANO = 13;
    public static final int SHARE_FROM_LIVE_VOTE = 7;
    public static final int SHARE_FROM_RED_PACKET_SPRING = 11;
    public static final int SHARE_FROM_TOPIC = 2;
    public static final int SHARE_FROM_VIDEOSHOT = 4;
    public static CallbackManager mFacebookCallbackManager;
    public static onFragmentResult onFragmentResult;
    private ShareAlbumBean album;
    private String channel;
    String cid;
    private String mActivityTitle;
    private String mActivityUrl;
    private AlbumCardList mAlbum;
    String mAppUrl;
    private String mAwardUrl;
    private TextView mCancleTextView;
    private String mComment;
    private Context mContext;
    private String mDescription;
    String mDialog;
    String mEdit;
    private String mExpandProperty;
    private ImageView mFacebookImageView;
    private String mGiftContent;
    private GiftShareAwardCallback mGiftShareAwardCallback;
    private BaseApplication mLetvApplication;
    private String mLiveId;
    String mLiveImgUrl;
    String mLiveTitle;
    private String mLiveType;
    String mMSite;
    String mPhotoPath;
    private String mPhotoUrl;
    private ImageView mQQImageView;
    private ImageView mQzoneImageView;
    String mRandText;
    private View mRootView;
    ShareDialog mShareDialog;
    private int mShareFrom;
    String mShotSource;
    String mShotTime;
    private ImageView mSinaImageView;
    private BSsoHandler mSsoHandler;
    private ImageView mTencentWeiboImageView;
    private View mTransparentView;
    private String mType;
    private VideoBean mVideo;
    String mVideoCaption;
    String mVideoName;
    VideoShotShareInfoBean mVideoShotShareInfoBean;
    private ImageView mWxFriendsImageView;
    private ImageView mWxImageView;
    String pid;
    private PlayLiveController playLiveController;
    private ImageView qq_icon;
    private TextView qq_status;
    private ImageView qzone_icon;
    private TextView qzone_status;
    private String secondShareDesc;
    int shareMode;
    private ImageView sina_icon;
    private TextView sina_status;
    private ImageView tencent_icon;
    String vid;
    private ImageView weixin_icon;
    private TextView weixin_status;
    private ImageView wx_timeline_icon;

    public interface onFragmentResult {
        void onFragmentResult_back(int i, int i2, Intent intent);
    }

    public HalfPlaySharePopwindow(Context context, int shareFrom) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this(context, shareFrom, null, null);
    }

    public HalfPlaySharePopwindow(Context context, int shareFrom, VideoBean video, AlbumCardList album) {
        this.mTransparentView = null;
        this.secondShareDesc = "";
        this.mDescription = "";
        this.mComment = "";
        this.mLiveId = "";
        this.mLiveType = "";
        this.mVideo = null;
        this.album = LetvShareControl.mShareAlbum;
        this.mShareFrom = 1;
        this.vid = NetworkUtils.DELIMITER_LINE;
        this.cid = NetworkUtils.DELIMITER_LINE;
        this.pid = NetworkUtils.DELIMITER_LINE;
        this.mLiveImgUrl = "";
        this.mVideoShotShareInfoBean = null;
        this.mContext = context;
        this.mShareFrom = shareFrom;
        this.mVideo = video;
        this.mAlbum = album;
        this.mLetvApplication = BaseApplication.getInstance();
        setPlayController();
        initContentView(this.mContext);
    }

    public HalfPlaySharePopwindow(Context context, int shareFrom, String activityTitle, String activityUrl, String awardUrl, String photoUrl, String giftContent, GiftShareAwardCallback giftShareAwardCallback) {
        this.mTransparentView = null;
        this.secondShareDesc = "";
        this.mDescription = "";
        this.mComment = "";
        this.mLiveId = "";
        this.mLiveType = "";
        this.mVideo = null;
        this.album = LetvShareControl.mShareAlbum;
        this.mShareFrom = 1;
        this.vid = NetworkUtils.DELIMITER_LINE;
        this.cid = NetworkUtils.DELIMITER_LINE;
        this.pid = NetworkUtils.DELIMITER_LINE;
        this.mLiveImgUrl = "";
        this.mVideoShotShareInfoBean = null;
        LogInfo.log("oncreia", "gift 调用带参数的share（）activityTitle:" + activityTitle + "|shareFrom:" + shareFrom + "|activityUrl:" + activityUrl + "|awardUrl:" + awardUrl + "|photoUrl:" + photoUrl + "|giftContent:" + giftContent);
        this.mContext = context;
        this.mShareFrom = shareFrom;
        this.mActivityTitle = activityTitle;
        this.mActivityUrl = activityUrl;
        this.mAwardUrl = awardUrl;
        this.mPhotoUrl = photoUrl;
        this.mGiftContent = giftContent;
        this.mGiftShareAwardCallback = giftShareAwardCallback;
        if (this.mLetvApplication == null) {
            this.mLetvApplication = (BaseApplication) this.mContext.getApplicationContext();
        }
        initContentView(this.mContext);
    }

    public HalfPlaySharePopwindow(Context context, int shareFrom, boolean isSDKRedPacket, String activityTitle, String activityUrl, String awardUrl, String photoUrl, String giftContent, GiftShareAwardCallback giftShareAwardCallback) {
        this.mTransparentView = null;
        this.secondShareDesc = "";
        this.mDescription = "";
        this.mComment = "";
        this.mLiveId = "";
        this.mLiveType = "";
        this.mVideo = null;
        this.album = LetvShareControl.mShareAlbum;
        this.mShareFrom = 1;
        this.vid = NetworkUtils.DELIMITER_LINE;
        this.cid = NetworkUtils.DELIMITER_LINE;
        this.pid = NetworkUtils.DELIMITER_LINE;
        this.mLiveImgUrl = "";
        this.mVideoShotShareInfoBean = null;
        LogInfo.log("fornia", "gift 调用带参数的share（）activityTitle:" + activityTitle + "|shareFrom:" + shareFrom + "|activityUrl:" + activityUrl + "|awardUrl:" + awardUrl + "|photoUrl:" + photoUrl + "|giftContent:" + giftContent);
        this.mContext = context;
        this.mShareFrom = shareFrom;
        this.mActivityTitle = activityTitle;
        this.mActivityUrl = activityUrl;
        this.mAwardUrl = awardUrl;
        this.mPhotoUrl = photoUrl;
        this.mGiftContent = giftContent;
        this.mGiftShareAwardCallback = giftShareAwardCallback;
        if (this.mLetvApplication == null) {
            this.mLetvApplication = (BaseApplication) this.mContext.getApplicationContext();
        }
        initContentView(this.mContext, isSDKRedPacket);
    }

    public void setShareCommment(String comment) {
        this.mComment = comment;
    }

    public void setVideoShotData(VideoShotShareInfoBean videoShotShareInfoBean) {
        this.mVideoShotShareInfoBean = videoShotShareInfoBean;
    }

    public void setStatisticsInfo(String type, String expandProperty) {
        this.mType = type;
        this.mExpandProperty = expandProperty;
    }

    private void setPlayController() {
        LogInfo.log("fornia", "SHARE_FROM_CLICK_PLAY setPlayController");
        switch (this.mShareFrom) {
            case 3:
                if (this.mContext instanceof BasePlayActivity) {
                    LogInfo.log("sContext instanceof BasePlayActivity");
                    this.playLiveController = (PlayLiveController) ((BasePlayActivity) this.mContext).mPlayController;
                    if (this.playLiveController != null) {
                        this.mLiveType = this.playLiveController.getLiveType();
                        this.mLiveId = this.playLiveController.getChannelId();
                        LogInfo.log("fornia", "SHARE_FROM_LIVE playLiveController.getChannelId() 1mLiveId:" + this.mLiveId + "mLiveType:" + this.mLiveType);
                        if (TextUtils.isEmpty(this.mLiveId)) {
                            this.mLiveId = this.playLiveController.getUniqueId();
                        }
                        LogInfo.log("fornia", "SHARE_FROM_LIVE playLiveController.getChannelId() 3mLiveId:" + this.mLiveId + "mLiveType:" + "|" + this.mLiveType + this.playLiveController.getLiveFlow());
                        if (this.playLiveController.getLiveFlow() != null) {
                            LetvBaseBean liveBean = this.playLiveController.getCurrentLiveData();
                            if (liveBean == null) {
                                return;
                            }
                            if (liveBean instanceof LiveRemenBaseBean) {
                                this.mLiveImgUrl = ((LiveRemenBaseBean) liveBean).typeICON;
                                LogInfo.log("fornia", "SHARE_FROM_LIVE 111111111playLiveController.getChannelId() mLiveIconUrl:" + this.mLiveImgUrl + ((LiveRemenBaseBean) liveBean).homeImgUrl + ((LiveRemenBaseBean) liveBean).guestImgUrl + ((LiveRemenBaseBean) liveBean).mobilePic + ((LiveRemenBaseBean) liveBean).typeICON);
                                return;
                            }
                            if ((liveBean instanceof LiveLunboProgramListBean) && ((LiveLunboProgramListBean) liveBean).programs != null) {
                                Iterator it = ((LiveLunboProgramListBean) liveBean).programs.iterator();
                                while (it.hasNext()) {
                                    ProgramEntity pe = (ProgramEntity) it.next();
                                    if (pe != null) {
                                        LogInfo.log("fornia", "SHARE_FROM_LIVE  mLiveIconUrl:" + pe.viewPic + pe.title);
                                        if (pe.title.equals(this.playLiveController.getProgramName())) {
                                            this.mLiveImgUrl = pe.viewPic;
                                        }
                                    }
                                }
                            }
                            if (TextUtils.isEmpty(this.mLiveImgUrl)) {
                                ProgramEntity programEntity = this.playLiveController.getCurrentLiveProgram();
                                if (programEntity != null) {
                                    this.mLiveImgUrl = programEntity.viewPic;
                                    String fileName = LetvDateUtils.getFormatPhotoName(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())) + ".jpg";
                                    LogInfo.log("fornia", "SHARE_FROM_LIVE 66666666loadSuccess mLiveImgUrl:" + this.mLiveImgUrl);
                                    if (!TextUtils.isEmpty(this.mLiveImgUrl)) {
                                        ImageDownloader.getInstance().download(this.mLiveImgUrl, new ImageDownloadStateListener(this) {
                                            final /* synthetic */ HalfPlaySharePopwindow this$0;

                                            {
                                                if (HotFix.PREVENT_VERIFY) {
                                                    System.out.println(VerifyLoad.class);
                                                }
                                                this.this$0 = this$0;
                                            }

                                            public void loading() {
                                            }

                                            public void loadSuccess(Bitmap bitmap) {
                                            }

                                            public void loadSuccess(Bitmap bitmap, String localPath) {
                                                LogInfo.log("fornia", "SHARE_FROM_LIVE 11111loadSuccess bitmap:" + bitmap + localPath);
                                                if (FileUtils.saveBitmap(BaseApplication.getInstance(), bitmap, localPath)) {
                                                    this.this$0.mLiveImgUrl = localPath;
                                                    bitmap.recycle();
                                                    return;
                                                }
                                                bitmap = BitmapFactory.decodeResource(this.this$0.mContext.getResources(), 2130838248);
                                                localPath = StoreUtils.getLocalRestorePath(BaseApplication.getInstance(), StoreUtils.SHARE_LIVE_PIC_SAVE_PATH) + "letv.jpg";
                                                if (new File(localPath).exists()) {
                                                    this.this$0.mLiveImgUrl = localPath;
                                                } else if (FileUtils.saveBitmap(BaseApplication.getInstance(), bitmap, localPath)) {
                                                    this.this$0.mLiveImgUrl = localPath;
                                                    bitmap.recycle();
                                                }
                                            }

                                            public void loadSuccess(View view, Bitmap bitmap, String localPath) {
                                                LogInfo.log("fornia", "SHARE_FROM_LIVE 2222loadSuccess bitmap:" + bitmap + localPath);
                                            }

                                            public void loadFailed() {
                                            }
                                        }, StoreUtils.getLocalRestorePath(BaseApplication.getInstance(), StoreUtils.SHARE_LIVE_PIC_SAVE_PATH) + fileName);
                                    }
                                    LogInfo.log("fornia", "SHARE_FROM_LIVE7777 222222222playLiveController.getChannelId() mLiveImgUrl:" + this.mLiveImgUrl + programEntity.title);
                                }
                            }
                            LogInfo.log("fornia", "SHARE_FROM_LIVE 222222222playLiveController.getChannelId() mLiveIconUrl:" + this.mLiveImgUrl);
                            return;
                        }
                        return;
                    }
                    return;
                }
                return;
            default:
                return;
        }
    }

    private void initContentView(Context context) {
        this.mRootView = UIs.inflate(this.mContext, 2130903140, null);
        setContentView(this.mRootView);
        setWidth(-1);
        setHeight(-1);
        setFocusable(true);
        setOutsideTouchable(true);
        update();
        setBackgroundDrawable(new ColorDrawable(0));
        initUI();
    }

    private void initContentView(Context context, boolean isSDKRedPacket) {
        this.mRootView = UIs.inflate(this.mContext, 2130903140, null);
        setContentView(this.mRootView);
        if (isSDKRedPacket) {
            LogInfo.log("fornia", "UIsUtils.getScreenWidth():" + UIsUtils.getScreenWidth() + "UIsUtils.getScreenHeight():" + UIsUtils.getScreenHeight());
            if (context.getResources().getConfiguration().orientation == 2) {
                setWidth(UIsUtils.getScreenWidth());
                setHeight(UIsUtils.getScreenHeight());
            } else if (context.getResources().getConfiguration().orientation == 1) {
                setWidth(UIsUtils.getScreenWidth());
                setHeight(UIsUtils.getScreenHeight() - UIsUtils.getStatusBarHeight(context));
            }
        } else {
            setWidth(-1);
            setHeight(-1);
        }
        setFocusable(true);
        setOutsideTouchable(true);
        update();
        setBackgroundDrawable(new ColorDrawable(0));
        initUI();
    }

    public void updateUI() {
        if (this.mContext.getResources().getConfiguration().orientation == 2) {
            setWidth(UIsUtils.getScreenHeight());
            setHeight(UIsUtils.getScreenWidth());
        } else if (this.mContext.getResources().getConfiguration().orientation == 1) {
            setWidth(UIsUtils.getScreenWidth());
            setHeight(UIsUtils.getScreenHeight() - UIsUtils.getStatusBarHeight(this.mContext));
        }
        update();
    }

    private void initUI() {
        onFragmentResult = new onFragmentResult(this) {
            final /* synthetic */ HalfPlaySharePopwindow this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onFragmentResult_back(int requestCode, int resultCode, Intent data) {
                if (this.this$0.mSsoHandler != null) {
                    this.this$0.mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
                }
            }
        };
        mFacebookCallbackManager = Factory.create();
        ((HorizontalScrollView) this.mRootView.findViewById(2131362442)).setVisibility(8);
        this.mRootView.findViewById(2131362443).setVisibility(8);
        this.mTransparentView = this.mRootView.findViewById(2131362426);
        this.mTransparentView.setOnClickListener(this);
        LinearLayout wx_timeline_invite_layout = (LinearLayout) this.mRootView.findViewById(2131362428);
        LinearLayout weixin_invite_layout = (LinearLayout) this.mRootView.findViewById(2131362430);
        LinearLayout sina_invite_layout = (LinearLayout) this.mRootView.findViewById(2131362432);
        LinearLayout qzone_invite_layout = (LinearLayout) this.mRootView.findViewById(2131362434);
        LinearLayout tencent_invite_layout = (LinearLayout) this.mRootView.findViewById(2131362438);
        LinearLayout qq_invite_layout = (LinearLayout) this.mRootView.findViewById(2131362436);
        LinearLayout facebook_invite_layout = (LinearLayout) this.mRootView.findViewById(2131362440);
        if (LetvUtils.isInHongKong()) {
            wx_timeline_invite_layout.setVisibility(8);
            weixin_invite_layout.setVisibility(8);
            sina_invite_layout.setVisibility(8);
            qzone_invite_layout.setVisibility(8);
            tencent_invite_layout.setVisibility(8);
            qq_invite_layout.setVisibility(8);
            facebook_invite_layout.setVisibility(0);
        } else {
            wx_timeline_invite_layout.setVisibility(0);
            weixin_invite_layout.setVisibility(0);
            sina_invite_layout.setVisibility(0);
            qzone_invite_layout.setVisibility(0);
            tencent_invite_layout.setVisibility(0);
            qq_invite_layout.setVisibility(0);
            facebook_invite_layout.setVisibility(8);
        }
        this.mWxFriendsImageView = (ImageView) this.mRootView.findViewById(2131362429);
        this.mWxFriendsImageView.setOnClickListener(this);
        this.mWxImageView = (ImageView) this.mRootView.findViewById(2131362431);
        this.mWxImageView.setOnClickListener(this);
        this.mSinaImageView = (ImageView) this.mRootView.findViewById(2131362433);
        this.mSinaImageView.setOnClickListener(this);
        this.mQzoneImageView = (ImageView) this.mRootView.findViewById(2131362435);
        this.mQzoneImageView.setOnClickListener(this);
        this.mQQImageView = (ImageView) this.mRootView.findViewById(2131362437);
        this.mQQImageView.setOnClickListener(this);
        this.mFacebookImageView = (ImageView) this.mRootView.findViewById(2131362441);
        this.mFacebookImageView.setOnClickListener(this);
        this.mTencentWeiboImageView = (ImageView) this.mRootView.findViewById(2131362439);
        this.mTencentWeiboImageView.setOnClickListener(this);
        this.mCancleTextView = (TextView) this.mRootView.findViewById(2131362444);
        this.mCancleTextView.setOnClickListener(this);
        if (this.mShareFrom == 8 || this.mShareFrom == 10) {
            sina_invite_layout.setVisibility(8);
            qzone_invite_layout.setVisibility(8);
            qq_invite_layout.setVisibility(8);
            tencent_invite_layout.setVisibility(8);
        } else if (this.mShareFrom == 11) {
            tencent_invite_layout.setVisibility(8);
        }
    }

    public void onClick(View v) {
        if (v.getId() == 2131362426 || v.getId() == 2131362444) {
            LogInfo.log("YDD", "===WX=半屏=取消分享==mShowRedPackageIcon=" + PaySucceedActivity.mShowRedPackage);
            if (PaySucceedActivity.mShowRedPackage && (this.mContext instanceof PaySucceedActivity)) {
                ((PaySucceedActivity) this.mContext).showRedPackage(true);
            }
            dismissPopupWindow();
            return;
        }
        this.channel = "0";
        String time = StringUtils.timeClockString("yyyyMMdd_HH:mm:ss");
        if (com.letv.core.utils.NetworkUtils.isNetworkAvailable() || v.getId() == 2131362444) {
            if (this.mShareFrom == 1) {
                if (this.mVideo == null || this.mAlbum == null) {
                    ToastUtils.showToast(this.mContext, 2131100831);
                    dismiss();
                    return;
                }
                this.vid = this.mVideo.vid + "";
                this.cid = this.mVideo.cid + "";
                this.pid = this.mVideo.pid + "";
                LetvShareControl.getInstance().setAblum_att(this.mVideo, this.mAlbum.albumInfo);
                if (this.mAlbum.intro != null) {
                    this.mDescription = this.mAlbum.intro.getDescription();
                }
            } else if (this.mShareFrom == 2) {
                if (this.mVideo == null || this.mAlbum == null) {
                    ToastUtils.showToast(this.mContext, 2131100831);
                    dismiss();
                    return;
                }
                this.vid = this.mVideo.vid + "";
                this.cid = this.mVideo.cid + "";
                this.pid = this.mVideo.pid + "";
                LetvShareControl.getInstance().setAblum_att(this.mVideo, this.mAlbum.albumInfo);
                if (this.mAlbum.intro != null) {
                    this.mDescription = this.mAlbum.intro.getDescription();
                }
            } else if (this.mShareFrom == 3) {
                if (this.playLiveController == null) {
                    ToastUtils.showToast(this.mContext, 2131100831);
                    dismiss();
                    return;
                }
                this.cid = this.playLiveController.getChannelId();
            } else if (this.mShareFrom == 4) {
                this.vid = this.mVideoShotShareInfoBean.vid + "";
                this.cid = this.mVideoShotShareInfoBean.mAlbumInfo.cid + "";
                this.pid = this.mVideoShotShareInfoBean.aid + "";
                LetvShareControl.getInstance().setAblum_att(this.mVideoShotShareInfoBean.mVideoBean, this.mVideoShotShareInfoBean.mAlbumInfo);
            } else if (this.mShareFrom == 5) {
                if (this.mVideo == null || this.mAlbum == null) {
                    ToastUtils.showToast(this.mContext, 2131100831);
                    dismiss();
                    return;
                }
                this.vid = this.mVideo.vid + "";
                this.cid = this.mVideo.cid + "";
                this.pid = this.mVideo.pid + "";
                LetvShareControl.getInstance().setAblum_att(this.mVideo, this.mAlbum.albumInfo);
                if (this.mAlbum.intro != null) {
                    this.mDescription = this.mAlbum.intro.getDescription();
                }
            }
            createSecondShareDesc();
            LetvShareControl.mShareAlbum.type = 2;
            switch (v.getId()) {
                case 2131362429:
                    onShareWxTimeLine(true);
                    dismissPopupWindow();
                    return;
                case 2131362431:
                    onShareWxTimeLine(false);
                    dismissPopupWindow();
                    return;
                case 2131362433:
                    LetvShareControl.mShareAlbum.type = 1;
                    onShareSina();
                    dismissPopupWindow();
                    return;
                case 2131362435:
                    onShareQzone();
                    dismissPopupWindow();
                    return;
                case 2131362437:
                    onShareTencent();
                    dismissPopupWindow();
                    return;
                case 2131362439:
                    onShareQQWeibo();
                    dismissPopupWindow();
                    return;
                case 2131362441:
                    onShareFacebook();
                    dismissPopupWindow();
                    return;
                default:
                    return;
            }
        }
        ToastUtils.showToast(v.getContext(), 2131100332);
    }

    public void showPopupWindow(View parent) {
        LogInfo.log("ZSM", "POPWINDOW IS SHOW ==" + isShowing());
        if (isShowing()) {
            dismiss();
        } else {
            showAtLocation(parent, 80, 0, 0);
        }
    }

    public void onShareQQWeibo() {
        if (LetvTencentWeiboShare.isLogin(this.mContext) == 1) {
            qqWeiboShareLogin();
        } else if (com.letv.core.utils.NetworkUtils.isNetworkAvailable()) {
            qqWeiboShareLogin();
        } else {
            ToastUtils.showToast(this.mContext, 2131101012);
        }
        this.channel = "2";
    }

    public void onShareFacebook() {
        LogInfo.log("fornia", "SHARE_FROM_CLICK_PLAY onShareFacebook");
        LetvFacebookShare instance;
        Activity activity;
        String linkcardTitleText;
        String descContent;
        if (this.mShareFrom == 1 && this.mVideo != null) {
            instance = LetvFacebookShare.getInstance();
            activity = (FragmentActivity) this.mContext;
            linkcardTitleText = ShareUtils.getLinkcardTitleText(this.album.cid, 6, this.album.isFeature, this.mVideo.playCount, this.album.Share_PidName, this.mVideo.episode, this.secondShareDesc);
            descContent = getDescContent();
            instance.shareToFacebook(activity, linkcardTitleText, descContent, this.album.facebookIcon, LetvFacebookShare.getFacebookClickShareUrl(10, this.album.Share_vid, this.album.Share_id, this.album.cid, 0, "facebook"), PageIdConstant.halpPlayPage, "s10");
            StatisticsUtils.staticticsInfoPost(this.mContext, "4", "h223", "5007", 7, null, PageIdConstant.halpPlayPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
        } else if (this.mShareFrom == 3) {
            LetvFacebookShare.getInstance().shareToFacebook((FragmentActivity) this.mContext, this.playLiveController.getShareProgramName(), this.secondShareDesc, this.album.facebookIcon, LetvFacebookShare.getFacebookLiveShareUrl(3, this.mLiveType, this.mLiveId, "facebook"), PageIdConstant.pictureSharePage, "sh21");
        } else if (this.mShareFrom == 4) {
            if (ShareUtils.checkPackageInstalled(LetvApplication.getInstance(), "com.facebook.katana")) {
                LetvFacebookShare.getInstance().shareToFacebookPic((FragmentActivity) this.mContext, this.album.Share_AlbumName, this.mVideoShotShareInfoBean.mPhotoPath, PageIdConstant.pictureSharePage, "sh21");
                StatisticsUtils.staticticsInfoPost(this.mContext, "4", "sh20", "5007", 7, null, PageIdConstant.pictureSharePage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
                return;
            }
            TipBean dialogMsgByMsg = TipUtils.getTipBean(DialogMsgConstantId.CONSTANT_20091);
            String toastString = "";
            if (dialogMsgByMsg == null) {
                toastString = ShareUtils.getString(2131100849);
            } else {
                toastString = dialogMsgByMsg.message;
            }
            UIsUtils.showToast(toastString);
        } else if (this.mShareFrom == 5 && this.mVideo != null) {
            instance = LetvFacebookShare.getInstance();
            activity = (FragmentActivity) this.mContext;
            linkcardTitleText = ShareUtils.getLinkcardTitleText(this.album.cid, 6, this.album.isFeature, this.mVideo.playCount, this.album.Share_PidName, this.mVideo.episode, this.secondShareDesc);
            descContent = getCommentDesc(6);
            instance.shareToFacebook(activity, linkcardTitleText, descContent, this.album.facebookIcon, LetvFacebookShare.getFacebookClickShareUrl(10, this.album.Share_vid, this.album.Share_id, this.album.cid, 0, "facebook"), PageIdConstant.halpPlayPage, "s10");
            StatisticsUtils.staticticsInfoPost(this.mContext, "4", "sh22", "5007", 7, null, PageIdConstant.halpPlayPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
        }
    }

    public void onShareWxTimeLine(boolean isWxTimeLine) {
        if (ShareUtils.checkPackageInstalled(this.mContext, "com.tencent.mm")) {
            LogInfo.log("wx", "album.Share_AlbumName = " + this.album.Share_AlbumName + "mShareFrom:" + this.mShareFrom);
            this.mLetvApplication.setWxisShare(true);
            if (this.mShareFrom == 3) {
                if (isWxTimeLine) {
                    LetvWeixinShare.share((FragmentActivity) this.mContext, this.playLiveController.getShareProgramName(), this.secondShareDesc, ShareUtils.getAnalysisLiveShareUrl(getShareMode(), this.mLiveType, this.mLiveId, 0, 0), isWxTimeLine, PageIdConstant.halpPlayPage, "s10");
                } else {
                    LetvWeixinShare.share((FragmentActivity) this.mContext, this.playLiveController.getShareProgramName(), this.secondShareDesc, ShareUtils.getAnalysisLiveShareUrl(getShareMode(), this.mLiveType, this.mLiveId, 1, 0), isWxTimeLine, PageIdConstant.halpPlayPage, "s10");
                }
                if (isWxTimeLine) {
                    StatisticsUtils.staticticsInfoPost(this.mContext, "4", "h223", "5001", 1, null, PageIdConstant.halpPlayPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
                } else {
                    StatisticsUtils.staticticsInfoPost(this.mContext, "4", "h223", "5002", 2, null, PageIdConstant.halpPlayPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
                }
            } else if (this.mShareFrom == 4) {
                if (isWxTimeLine) {
                    LetvWeixinShare.sharePic((FragmentActivity) this.mContext, this.album.Share_AlbumName, ShareUtils.getVideoShareHint(this.album.Share_AlbumName, this.album.type, this.album.Share_id, this.album.order, this.album.Share_vid, this.album.sharedPid, (long) this.album.cid, "", this.mVideoShotShareInfoBean.mRandText, 0, 4, 0), null, this.mVideoShotShareInfoBean.mPhotoPath, isWxTimeLine, PageIdConstant.pictureSharePage, "sh21");
                } else {
                    LetvWeixinShare.sharePic((FragmentActivity) this.mContext, this.album.Share_AlbumName, ShareUtils.getVideoShareHint(this.album.Share_AlbumName, this.album.type, this.album.Share_id, this.album.order, this.album.Share_vid, this.album.sharedPid, (long) this.album.cid, "", this.mVideoShotShareInfoBean.mRandText, 1, 5, 0), null, this.mVideoShotShareInfoBean.mPhotoPath, isWxTimeLine, PageIdConstant.pictureSharePage, "sh21");
                }
                if (isWxTimeLine) {
                    StatisticsUtils.staticticsInfoPost(this.mContext, "4", "sh20", "5001", 1, null, PageIdConstant.pictureSharePage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
                } else {
                    StatisticsUtils.staticticsInfoPost(this.mContext, "4", "sh20", "5002", 2, null, PageIdConstant.pictureSharePage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
                }
            } else if (this.mShareFrom == 5) {
                if (isWxTimeLine) {
                    LetvWeixinShare.share((FragmentActivity) this.mContext, getCommentDesc(0), "", this.album.icon, ShareUtils.getSharePlayUrl(this.album.type, this.album.Share_id, this.album.order, this.album.Share_vid), this.album.cid, true, isWxTimeLine, PageIdConstant.halpPlayPage, "sh23");
                } else {
                    LetvWeixinShare.share((FragmentActivity) this.mContext, this.album.Share_AlbumName, getCommentDesc(1), this.album.icon, ShareUtils.getSharePlayUrl(this.album.type, this.album.Share_id, this.album.order, this.album.Share_vid), this.album.cid, true, isWxTimeLine, PageIdConstant.halpPlayPage, "sh23");
                }
                if (isWxTimeLine) {
                    StatisticsUtils.staticticsInfoPost(this.mContext, "4", "sh22", "5001", 1, null, PageIdConstant.halpPlayPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
                } else {
                    StatisticsUtils.staticticsInfoPost(this.mContext, "4", "sh22", "5002", 2, null, PageIdConstant.halpPlayPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
                }
            } else if (this.mShareFrom == 8) {
                LogInfo.log("fornia", "onShareWxTimeLine SHARE_FROM_GIFT_MONEY:");
                LetvWeixinShare.share((FragmentActivity) this.mContext, this.mActivityTitle, this.mGiftContent, this.mPhotoUrl, this.mActivityUrl, this.mAwardUrl, this.mGiftShareAwardCallback, isWxTimeLine, 8, PageIdConstant.index, "hb02");
                if (isWxTimeLine) {
                    StatisticsUtils.staticticsInfoPost(this.mContext, "4", "hb02", "5001", 1, null, PageIdConstant.index, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
                } else {
                    StatisticsUtils.staticticsInfoPost(this.mContext, "4", "hb02", "5002", 2, null, PageIdConstant.index, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
                }
            } else if (this.mShareFrom == 10) {
                LogInfo.log("fornia", "onShareWxTimeLine SHARE_FROM_GIFT_MONEY:");
                LetvWeixinShare.share((FragmentActivity) this.mContext, this.mActivityTitle, this.mGiftContent, this.mPhotoUrl, this.mActivityUrl, this.mAwardUrl, this.mGiftShareAwardCallback, isWxTimeLine, 10, PageIdConstant.halpPlayPage, "hb02");
                if (isWxTimeLine) {
                    StatisticsUtils.staticticsInfoPost(this.mContext, "4", "hb02", "5001", 1, null, PageIdConstant.halpPlayPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
                } else {
                    StatisticsUtils.staticticsInfoPost(this.mContext, "4", "hb02", "5002", 2, null, PageIdConstant.halpPlayPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
                }
            } else if (this.mShareFrom == 11) {
                LogInfo.log("fornia", "onShareWxTimeLine SHARE_FROM_GIFT_MONEY:");
                LetvWeixinShare.share((FragmentActivity) this.mContext, this.mActivityTitle, this.mGiftContent, this.mPhotoUrl, this.mActivityUrl, this.mAwardUrl, this.mGiftShareAwardCallback, isWxTimeLine, 22, PageIdConstant.halpPlayPage, "hb02");
                if (isWxTimeLine) {
                    StatisticsUtils.staticticsInfoPost(this.mContext, "4", "hb02", "5001", 1, null, PageIdConstant.halpPlayPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
                } else {
                    StatisticsUtils.staticticsInfoPost(this.mContext, "4", "hb02", "5002", 2, null, PageIdConstant.halpPlayPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
                }
            } else if (this.mShareFrom != 1 || this.mVideo == null) {
                if (isWxTimeLine) {
                    LetvWeixinShare.share((FragmentActivity) this.mContext, this.album.Share_AlbumName, "", this.album.icon, ShareUtils.getSharePlayUrl(this.album.type, this.album.Share_id, this.album.order, this.album.Share_vid), this.album.cid, isWxTimeLine, PageIdConstant.halpPlayPage, "s10");
                } else {
                    LetvWeixinShare.share((FragmentActivity) this.mContext, this.album.Share_AlbumName, this.secondShareDesc, this.album.icon, ShareUtils.getSharePlayUrl(this.album.type, this.album.Share_id, this.album.order, this.album.Share_vid), this.album.cid, isWxTimeLine, PageIdConstant.halpPlayPage, "s10");
                }
                if (isWxTimeLine) {
                    StatisticsUtils.staticticsInfoPost(this.mContext, "4", "h223", "5001", 1, null, PageIdConstant.halpPlayPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
                } else {
                    StatisticsUtils.staticticsInfoPost(this.mContext, "4", "h223", "5002", 2, null, PageIdConstant.halpPlayPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
                }
            } else {
                if (isWxTimeLine) {
                    LogInfo.log("fornia", "6.4文案调整 secondShareDesc:" + this.secondShareDesc);
                    LetvWeixinShare.share((FragmentActivity) this.mContext, ShareUtils.getLinkcardTitleText(this.album.cid, 0, this.album.isFeature, this.mVideo.playCount, this.album.Share_PidName, this.mVideo.episode, this.secondShareDesc), "", this.album.icon, ShareUtils.getSharePlayUrl(this.album.type, this.album.Share_id, this.album.order, this.album.Share_vid), this.album.cid, isWxTimeLine, PageIdConstant.halpPlayPage, "s10");
                } else {
                    String linkcardTitleText;
                    LogInfo.log("fornia", "6.4文案调整 secondShareDesc:" + this.secondShareDesc);
                    FragmentActivity fragmentActivity = (FragmentActivity) this.mContext;
                    String linkcardTitleText2 = TextUtils.isEmpty(getDescContent()) ? "" : ShareUtils.getLinkcardTitleText(this.album.cid, 1, this.album.isFeature, this.mVideo.playCount, this.album.Share_PidName, this.mVideo.episode, this.secondShareDesc);
                    if (TextUtils.isEmpty(getDescContent())) {
                        linkcardTitleText = ShareUtils.getLinkcardTitleText(this.album.cid, 1, this.album.isFeature, this.mVideo.playCount, this.album.Share_PidName, this.mVideo.episode, this.secondShareDesc);
                    } else {
                        linkcardTitleText = getDescContent();
                    }
                    LetvWeixinShare.share(fragmentActivity, linkcardTitleText2, linkcardTitleText, this.album.icon, ShareUtils.getSharePlayUrl(this.album.type, this.album.Share_id, this.album.order, this.album.Share_vid), this.album.cid, isWxTimeLine, PageIdConstant.halpPlayPage, "s10");
                }
                if (isWxTimeLine) {
                    StatisticsUtils.staticticsInfoPost(this.mContext, "4", "h223", "5001", 1, null, PageIdConstant.halpPlayPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
                } else {
                    StatisticsUtils.staticticsInfoPost(this.mContext, "4", "h223", "5002", 2, null, PageIdConstant.halpPlayPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
                }
            }
        } else {
            UIs.callDialogMsgPositiveButton((FragmentActivity) this.mContext, DialogMsgConstantId.SEVEN_ZERO_SEVEN_CONSTANT, new DialogInterface.OnClickListener(this) {
                final /* synthetic */ HalfPlaySharePopwindow this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void onClick(DialogInterface dialog, int which) {
                    LogInfo.log("YDD", "===WX=半屏=无客户端==mShowRedPackageIcon=" + PaySucceedActivity.mShowRedPackage);
                    if (PaySucceedActivity.mShowRedPackage && (this.this$0.mContext instanceof PaySucceedActivity)) {
                        ((PaySucceedActivity) this.this$0.mContext).showRedPackage(true);
                    }
                }
            });
        }
        this.channel = "4";
    }

    private void qqWeiboShareLogin() {
        if (this.mShareFrom == 3) {
            LetvTencentWeiboShare.login((FragmentActivity) this.mContext, this.playLiveController.getShareProgramName(), 2, this.playLiveController.getShareLiveUrl(), this.mLiveType, this.mLiveId, getShareMode(), PageIdConstant.halpPlayPage, "s10");
            StatisticsUtils.staticticsInfoPost(this.mContext, "4", "h223", "5006", 6, null, PageIdConstant.halpPlayPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
        } else if (this.mShareFrom == 4 && this.mVideo != null) {
            LetvTencentWeiboShare.login((FragmentActivity) this.mContext, LetvShareControl.mShareAlbum, 2, LetvShareControl.mShareAlbum.Share_id, LetvShareControl.mShareAlbum.Share_vid, this.mVideo == null ? "" : this.mVideo.playMark, 4, PageIdConstant.pictureSharePage, "sh21");
            StatisticsUtils.staticticsInfoPost(this.mContext, "4", "sh20", "5006", 6, null, PageIdConstant.pictureSharePage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
        } else if (this.mShareFrom == 5) {
            LetvTencentWeiboShare.login((FragmentActivity) this.mContext, LetvShareControl.mShareAlbum, 2, LetvShareControl.mShareAlbum.Share_id, LetvShareControl.mShareAlbum.Share_vid, 5, getCommentDesc(5), PageIdConstant.halpPlayPage, "sh23");
            StatisticsUtils.staticticsInfoPost(this.mContext, "4", "sh22", "5006", 6, null, PageIdConstant.halpPlayPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
        } else if (this.mShareFrom == 1 && this.mVideo != null) {
            LetvTencentWeiboShare.login((FragmentActivity) this.mContext, LetvShareControl.mShareAlbum, 2, LetvShareControl.mShareAlbum.Share_id, LetvShareControl.mShareAlbum.Share_vid, this.mVideo == null ? "" : this.mVideo.playMark, 1, PageIdConstant.halpPlayPage, "s10");
            StatisticsUtils.staticticsInfoPost(this.mContext, "4", "h223", "5006", 6, null, PageIdConstant.halpPlayPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
        } else if (this.mVideo != null) {
            LetvTencentWeiboShare.login((FragmentActivity) this.mContext, LetvShareControl.mShareAlbum, 2, LetvShareControl.mShareAlbum.Share_id, LetvShareControl.mShareAlbum.Share_vid, this.mVideo == null ? "" : this.mVideo.playMark, 1, PageIdConstant.halpPlayPage, "s10");
            StatisticsUtils.staticticsInfoPost(this.mContext, "4", "h223", "5006", 6, null, PageIdConstant.halpPlayPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
        }
    }

    public void onShareSina() {
        if (LetvSinaShareSSO.isLogin(this.mContext) == 1 && !LetvSinaShareSSO.isLogin2(this.mContext)) {
            sinaShareLogin();
        } else if (com.letv.core.utils.NetworkUtils.isNetworkAvailable()) {
            sinaShareLogin();
        } else {
            ToastUtils.showToast(this.mContext, 2131101012);
        }
        this.channel = "1";
    }

    private void sinaShareLogin() {
        if (this.mShareFrom == 3) {
            this.mSsoHandler = LetvSinaShareSSO.login((FragmentActivity) this.mContext, this.playLiveController.getShareProgramName(), this.playLiveController.getShareLiveUrl(), this.mLiveType, this.mLiveId, getShareMode(), PageIdConstant.halpPlayPage, "s10");
            StatisticsUtils.staticticsInfoPost(this.mContext, "4", "h223", "5003", 3, null, PageIdConstant.halpPlayPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
        } else if (this.mShareFrom == 4) {
            switch (this.mVideoShotShareInfoBean.mShotSource) {
                case 1:
                    this.mSsoHandler = LetvSinaShareSSO.login((FragmentActivity) this.mContext, this.album, this.mVideoShotShareInfoBean, this.album.order, this.album.Share_vid, "", 4, PageIdConstant.pictureSharePage, "sh21");
                    StatisticsUtils.staticticsInfoPost(this.mContext, "4", "sh20", "5003", 3, null, PageIdConstant.pictureSharePage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
                    return;
                case 2:
                    this.mSsoHandler = LetvSinaShareSSO.login((FragmentActivity) this.mContext, this.album, this.mVideoShotShareInfoBean, this.album.order, this.album.Share_vid, "", 4, PageIdConstant.pictureSharePage, "sh21");
                    StatisticsUtils.staticticsInfoPost(this.mContext, "4", "sh20", "5003", 3, null, PageIdConstant.pictureSharePage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
                    return;
                case 3:
                    this.mSsoHandler = LetvSinaShareSSO.login((FragmentActivity) this.mContext, this.album, this.mVideoShotShareInfoBean, this.album.order, this.album.Share_vid, "", 4, PageIdConstant.pictureSharePage, "sh21");
                    StatisticsUtils.staticticsInfoPost(this.mContext, "4", "sh20", "5003", 3, null, PageIdConstant.pictureSharePage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
                    return;
                default:
                    return;
            }
        } else if (this.mShareFrom == 5) {
            this.mSsoHandler = LetvSinaShareSSO.login((FragmentActivity) this.mContext, this.album, null, this.album.order, this.album.Share_vid, 5, getCommentDesc(2), PageIdConstant.halpPlayPage, "sh23");
            StatisticsUtils.staticticsInfoPost(this.mContext, "4", "sh22", "5003", 3, null, PageIdConstant.halpPlayPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
        } else if (this.mShareFrom == 1) {
            this.mSsoHandler = LetvSinaShareSSO.login((FragmentActivity) this.mContext, this.album, null, this.album.order, this.album.Share_vid, this.mVideo == null ? "" : this.mVideo.playMark, 1, PageIdConstant.halpPlayPage, "s10");
            StatisticsUtils.staticticsInfoPost(this.mContext, "4", "h223", "5003", 3, null, PageIdConstant.halpPlayPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
        } else if (this.mShareFrom == 11) {
            this.mSsoHandler = LetvSinaShareSSO.login((Activity) this.mContext, this.mActivityTitle, this.mGiftContent, this.mPhotoUrl, this.mActivityUrl, this.mAwardUrl, 22, PageIdConstant.halpPlayPage, "hb02");
        } else {
            String str;
            Activity activity = (FragmentActivity) this.mContext;
            ShareAlbumBean shareAlbumBean = this.album;
            int i = this.album.order;
            int i2 = this.album.Share_vid;
            if (this.mVideo == null) {
                str = "";
            } else {
                str = this.mVideo.playMark;
            }
            this.mSsoHandler = LetvSinaShareSSO.login(activity, shareAlbumBean, null, i, i2, str, 1, PageIdConstant.halpPlayPage, "s10");
            StatisticsUtils.staticticsInfoPost(this.mContext, "4", "h223", "5003", 3, null, PageIdConstant.halpPlayPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
        }
    }

    public void onShareQzone() {
        if (this.mShareFrom == 3) {
            LetvQZoneShare.getInstance(this.mContext).gotoSharePage((FragmentActivity) this.mContext, this.playLiveController.getShareProgramName(), ShareUtils.getAnalysisLiveShareUrl(getShareMode(), this.mLiveType, this.mLiveId, 3, 0), this.mLiveImgUrl, getShareMode(), this.secondShareDesc, PageIdConstant.halpPlayPage, "s10");
            StatisticsUtils.staticticsInfoPost(this.mContext, "4", "h223", "5004", 4, null, PageIdConstant.halpPlayPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
        } else if (this.mShareFrom == 4) {
            LetvQZoneShare.getInstance(this.mContext).gotoSharePage((FragmentActivity) this.mContext, this.album, this.mVideoShotShareInfoBean, this.album.order, this.album.Share_vid, 4, PageIdConstant.pictureSharePage, "sh21");
            StatisticsUtils.staticticsInfoPost(this.mContext, "4", "sh20", "5004", 4, null, PageIdConstant.pictureSharePage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
        } else if (this.mShareFrom == 5) {
            LetvQZoneShare.getInstance(this.mContext).gotoSharePage((FragmentActivity) this.mContext, this.album, this.album.order, this.album.Share_vid, 5, getCommentDesc(3), PageIdConstant.halpPlayPage, "sh23");
            StatisticsUtils.staticticsInfoPost(this.mContext, "4", "sh22", "5004", 4, null, PageIdConstant.halpPlayPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
        } else if (this.mShareFrom == 1) {
            LetvQZoneShare.getInstance(this.mContext).gotoSharePage((FragmentActivity) this.mContext, this.album, this.album.order, this.album.Share_vid, this.secondShareDesc, PageIdConstant.halpPlayPage, "s10");
            StatisticsUtils.staticticsInfoPost(this.mContext, "4", "h223", "5004", 4, null, PageIdConstant.halpPlayPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
        } else if (this.mShareFrom == 11) {
            LetvQZoneShare.getInstance(this.mContext).gotoSharePage((FragmentActivity) this.mContext, this.mActivityTitle, this.mActivityUrl, this.mPhotoUrl, this.mAwardUrl, 22, this.mGiftContent, PageIdConstant.halpPlayPage, "s10");
            StatisticsUtils.staticticsInfoPost(this.mContext, "4", "h223", "5004", 4, null, PageIdConstant.halpPlayPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
        } else {
            LetvQZoneShare.getInstance(this.mContext).gotoSharePage((FragmentActivity) this.mContext, this.album, this.album.order, this.album.Share_vid, this.secondShareDesc, "s10");
            StatisticsUtils.staticticsInfoPost(this.mContext, "4", "h223", "5004", 4, null, PageIdConstant.halpPlayPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
        }
        this.channel = "3";
    }

    public void onShareTencent() {
        if (this.mShareFrom == 3) {
            letvTencentShare.getInstance(this.mContext).gotoSharePage((FragmentActivity) this.mContext, this.playLiveController.getShareProgramName(), ShareUtils.getAnalysisLiveShareUrl(getShareMode(), this.mLiveType, this.mLiveId, 6, 0), this.mLiveImgUrl, getShareMode(), this.secondShareDesc, PageIdConstant.halpPlayPage, "s10");
            StatisticsUtils.staticticsInfoPost(this.mContext, "4", "h223", "5005", 5, null, PageIdConstant.halpPlayPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
        } else if (this.mShareFrom == 4) {
            letvTencentShare.getInstance(this.mContext).gotoSharePage((FragmentActivity) this.mContext, this.album, this.mVideoShotShareInfoBean, this.album.order, this.album.Share_vid, 4, this.mVideoShotShareInfoBean.mRandText, PageIdConstant.pictureSharePage, "sh21");
            StatisticsUtils.staticticsInfoPost(this.mContext, "4", "sh20", "5005", 5, null, PageIdConstant.pictureSharePage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
        } else if (this.mShareFrom == 5) {
            letvTencentShare.getInstance(this.mContext).gotoSharePage((FragmentActivity) this.mContext, this.album, null, this.album.order, this.album.Share_vid, 5, getCommentDesc(4), PageIdConstant.halpPlayPage, "sh23");
            StatisticsUtils.staticticsInfoPost(this.mContext, "4", "sh22", "5005", 5, null, PageIdConstant.halpPlayPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
        } else if (this.mShareFrom == 1) {
            letvTencentShare.getInstance(this.mContext).gotoSharePage((FragmentActivity) this.mContext, this.album, null, this.album.order, this.album.Share_vid, 1, this.secondShareDesc, PageIdConstant.halpPlayPage, "s10");
            StatisticsUtils.staticticsInfoPost(this.mContext, "4", "h223", "5005", 5, null, PageIdConstant.halpPlayPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
        } else if (this.mShareFrom == 11) {
            LogInfo.log("fornia", "share-SHARE_FROM_RED_PACKET_SPRING shareQ");
            letvTencentShare.getInstance(this.mContext).gotoSharePage((FragmentActivity) this.mContext, this.mActivityTitle, this.mActivityUrl, this.mPhotoUrl, this.mAwardUrl, 22, this.mGiftContent, PageIdConstant.halpPlayPage, "s10");
            StatisticsUtils.staticticsInfoPost(this.mContext, "4", "h223", "5005", 5, null, PageIdConstant.halpPlayPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
        } else {
            letvTencentShare.getInstance(this.mContext).gotoSharePage((FragmentActivity) this.mContext, this.album, null, this.album.order, this.album.Share_vid, 1, this.secondShareDesc, PageIdConstant.halpPlayPage, "s10");
            StatisticsUtils.staticticsInfoPost(this.mContext, "4", "h223", "5005", 5, null, PageIdConstant.halpPlayPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
        }
    }

    private void createSecondShareDesc() {
        if (this.mShareFrom != 3) {
            if (!(this.mShareFrom == 4 || this.mShareFrom == 8 || this.mShareFrom == 10)) {
                switch (this.album.cid) {
                    case 1:
                        String str = this.album.isFeature ? TextUtils.isEmpty(this.album.actor) ? "" : this.mContext.getResources().getString(2131100827) + this.album.actor : this.album.subTitle;
                        this.secondShareDesc = str;
                        LogInfo.log("fornia", "secondShareDesc TYPE_MOVIE:" + this.secondShareDesc);
                        break;
                    case 2:
                    case 5:
                    case 11:
                    case 16:
                        if (this.mVideo != null) {
                            this.secondShareDesc = this.mVideo.subTitle;
                        }
                        LogInfo.log("fornia", "secondShareDesc TYPE_CARTOON:" + this.secondShareDesc);
                        break;
                    case 9:
                        if (this.mVideo != null) {
                            this.secondShareDesc = this.mVideo.singer;
                        }
                        LogInfo.log("fornia", "secondShareDesc TYPE_MUSIC:" + this.secondShareDesc);
                        break;
                    default:
                        this.secondShareDesc = "";
                        break;
                }
            }
        } else if (this.playLiveController != null) {
            this.secondShareDesc = this.playLiveController.getShareProgramName();
            LogInfo.log("fornia", "secondShareDesc SHARE_FROM_LIVE:" + this.secondShareDesc);
        }
        LogInfo.log("fornia", "secondShareDesc :" + this.secondShareDesc);
    }

    private String getDescContent() {
        switch (this.album.cid) {
            case 9:
                return this.secondShareDesc;
            default:
                if (this.album.isFeature) {
                    return this.secondShareDesc;
                }
                return "";
        }
    }

    private String getCommentDesc(int shareType) {
        String desc = "";
        switch (shareType) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                if (this.album != null) {
                    return this.mContext.getResources().getString(2131100820, new Object[]{this.mComment, this.album.Share_AlbumName});
                }
                return this.mContext.getResources().getString(2131100819, new Object[]{this.mComment});
            case 6:
                if (this.album != null) {
                    return this.mComment;
                }
                return desc;
            default:
                return desc;
        }
    }

    public int getShareMode() {
        switch (this.playLiveController.getLaunchMode()) {
            case 101:
                this.shareMode = 1;
                break;
            case 102:
                this.shareMode = 15;
                break;
            case 103:
                this.shareMode = 3;
                break;
            case 104:
                this.shareMode = 4;
                break;
            case 105:
                this.shareMode = 5;
                break;
            case 106:
                this.shareMode = 17;
                break;
            case 107:
                this.shareMode = 18;
                break;
            case 108:
                this.shareMode = 19;
                break;
            case 109:
                this.shareMode = 20;
                break;
            case 111:
                this.shareMode = 23;
                break;
            default:
                this.shareMode = 21;
                break;
        }
        return this.shareMode;
    }

    private void dismissPopupWindow() {
        dismiss();
    }
}
