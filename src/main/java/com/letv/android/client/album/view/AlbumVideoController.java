package com.letv.android.client.album.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.TextView;
import com.letv.ads.ex.utils.PlayConstantUtils.SPConstant;
import com.letv.android.client.album.AlbumPlayActivity;
import com.letv.android.client.album.R;
import com.letv.android.client.album.controller.AlbumGestureController.VolumnChangeStyle;
import com.letv.android.client.album.controller.ScreenProjectionController;
import com.letv.android.client.album.controller.ScreenProjectionController.OnPushToTvResponseCallback;
import com.letv.android.client.album.half.AlbumHalfFragment;
import com.letv.android.client.album.half.fragment.AlbumHalfExpandFragment;
import com.letv.android.client.album.observable.AlbumGestureObservable;
import com.letv.android.client.album.observable.AlbumGestureObservable.VolumeChangeNotify;
import com.letv.android.client.album.observable.ScreenObservable;
import com.letv.android.client.commonlib.config.AlbumPlayVRActivityConfig;
import com.letv.android.client.commonlib.config.LetvWebViewActivityConfig;
import com.letv.android.client.commonlib.config.VideoShotEditActivityConfig;
import com.letv.android.client.commonlib.messagemodel.ShareFloatProtocol;
import com.letv.android.client.commonlib.view.LetvSeekBar;
import com.letv.android.client.commonlib.view.ScrollTextView;
import com.letv.business.flow.album.AlbumPlayFlow;
import com.letv.business.flow.album.AlbumPlayFlowObservable;
import com.letv.business.flow.album.AlbumPlayTopicFlow;
import com.letv.business.flow.album.PlayObservable;
import com.letv.business.flow.album.PlayObservable.BatteryStatusNotify;
import com.letv.business.flow.album.model.AlbumStreamSupporter;
import com.letv.core.BaseApplication;
import com.letv.core.api.LetvRequest;
import com.letv.core.audiotrack.AudioTrackManager;
import com.letv.core.bean.AlbumCardList;
import com.letv.core.bean.AlbumInfo;
import com.letv.core.bean.AlbumPageCard;
import com.letv.core.bean.LanguageSettings;
import com.letv.core.bean.VideoBean;
import com.letv.core.bean.VideoShotShareInfoBean;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.constant.PlayConstant.VideoType;
import com.letv.core.db.DBManager;
import com.letv.core.db.PreferencesManager;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.messagebus.config.LeMessageIds;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.messagebus.message.LeResponseMessage;
import com.letv.core.messagebus.task.LeMessageTask;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.RealPlayUrlInfoParser;
import com.letv.core.subtitle.manager.SubtitleInfoManager;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.FileUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.PlayUtils;
import com.letv.core.utils.RxBus;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.StoreUtils;
import com.letv.core.utils.StringUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.LetvErrorCode;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.pp.func.CdeHelper;
import com.media.ffmpeg.FFMpegPlayer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Observable;
import master.flame.danmaku.danmaku.parser.DanmakuFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

public class AlbumVideoController extends AlbumBaseControllerFragment {
    public static boolean mIsFirstInitLayout = true;
    private RadioButton m1080Button;
    private RadioButton m2KButton;
    private RadioButton m4KButton;
    private RadioButton m720Button;
    private TextView mBarrageButton;
    private ImageView mBarrageInputBtn;
    private ImageView mBatteryImageView;
    private View mBottomBtnsFrame;
    private int mColorGrey;
    private int mColorSelect;
    private int mColorWhite;
    private TextView mEpisodeButton;
    private boolean mEpisodeVisibility = false;
    private ImageView mGuideImage;
    private TextView mGuideText;
    private boolean mHasShowInteract;
    private RadioButton mHighButton;
    private TextView mInteractButton;
    private View mInteractTipLayout;
    private TextView mInteractTipTextView;
    private boolean mIsSensorSelected = true;
    private boolean mIsShowingBlockSuggest;
    private View mJumpToVRView;
    private TextView mLanguageButton;
    public LanguageView mLanguageView;
    public RelativeLayout mLanguageViewLayout;
    private RadioButton mLowButton;
    private TextView mMoreButton;
    private AlbumMoreView mMoreView;
    private ImageView mNetImageView;
    private String mNoNetworkTip;
    private TextView mPanoShareButton;
    private RelativeLayout mRLVideoShot;
    private TextView mSelectSensorBox;
    private RadioButton mStandardButton;
    private TextView mStreamIntroTextView;
    private ImageView mStreamLogoImageView;
    private TextView mStreamSelectBox;
    private View mStreamSelectLayout;
    private TextView mStreamSpreadMoreView;
    private RadioButton mSuperHighButton;
    private View mSwitchView;
    private TextView mTimeTextView;
    private View mTvSpreadView;
    private String mUriLinkString2K = "http://m.shop.letv.com/?cps_id=le_app_apprx_other_appbfycjds_brand_h_qr0610";
    private String mUriLinkString4K = "http://m.shop.letv.com/?cps_id=le_app_apprx_other_appbfy1080p_brand_h_msqgcjds0701";
    private String mUriLinkStringOther = "http://m.shop.letv.com/?cps_id=le_app_apprx_other_appbfycjds_brand_h_qr06101";
    private ImageView mVideoShotBorder;
    private ImageView mVideoShotNotice;
    private View mVideoShotProgress;
    private WatchingFocusRelativeLayout mWatchingFocusLayout;
    private long vidForVideoShotNew = -1;
    private long vidForVideoShotOld = -1;
    Animation visibleAnimation;

    public AlbumVideoController(Context context, View view) {
        super(context, view);
        initView();
    }

    public void setLaunchMode(int launchMode) {
        this.mLaunchMode = launchMode;
    }

    protected void initView() {
        this.mColorGrey = this.mActivity.getResources().getColor(R.color.letv_color_ffa1a1a1);
        this.mColorWhite = this.mActivity.getResources().getColor(R.color.letv_color_ffffff);
        this.mColorSelect = this.mActivity.getResources().getColor(R.color.letv_color_5895ed);
        this.mTitleTextView = (ScrollTextView) this.mContentView.findViewById(R.id.full_controller_title);
        this.mBarrageButton = (TextView) this.mContentView.findViewById(R.id.full_controller_barrage);
        this.mMoreButton = (TextView) this.mContentView.findViewById(R.id.full_controller_more);
        this.mPanoShareButton = (TextView) this.mContentView.findViewById(R.id.full_controller_pano_share);
        this.mEpisodeButton = (TextView) this.mContentView.findViewById(R.id.full_controller_select_episode);
        this.mRLVideoShot = (RelativeLayout) this.mContentView.findViewById(R.id.rl_full_controller_videoshot);
        this.mVideoShotButton = (TextView) this.mContentView.findViewById(R.id.full_controller_videoshot);
        this.mGuideRelativeLayout = (RelativeLayout) this.mContentView.findViewById(R.id.rl_videoshot_guide);
        this.mGuideRelativeActionLayout = (RelativeLayout) this.mContentView.findViewById(R.id.rl_videoshot_guide_layout);
        this.mGuideImage = (ImageView) this.mContentView.findViewById(R.id.iv_videoshot_guide);
        this.mGuideText = (TextView) this.mContentView.findViewById(R.id.tv_videoshot_guide);
        this.mVideoShotGuideArrow = (ImageView) this.mContentView.findViewById(R.id.iv_videoshot_guide_pointer);
        this.mVideoShotNotice = (ImageView) this.mContentView.findViewById(R.id.full_controller_videoshot_notice);
        this.mVideoShotBorder = (ImageView) this.mContentView.findViewById(R.id.full_controller_photo_border);
        this.mVideoShotProgress = this.mContentView.findViewById(R.id.inc_videoshot_progress_noice);
        this.mScreenOrientationLock = (ImageView) this.mContentView.findViewById(R.id.full_controller_lock);
        this.mVideoShot = (ImageView) this.mContentView.findViewById(R.id.full_controller_cut);
        this.mPlayImageView = (ImageView) this.mContentView.findViewById(R.id.full_controller_play);
        this.mSeekBar = (LetvSeekBar) this.mContentView.findViewById(R.id.full_controller_seekbar);
        this.mStreamSelectBox = (TextView) this.mContentView.findViewById(R.id.full_controller_hd);
        this.mStreamSelectLayout = this.mContentView.findViewById(R.id.full_controller_select_stream_frame);
        this.mSelectSensorBox = (TextView) this.mContentView.findViewById(R.id.full_controller_select_sensor);
        this.mInteractButton = (TextView) this.mContentView.findViewById(R.id.full_controller_interact);
        this.mInteractTipLayout = this.mContentView.findViewById(R.id.play_interact_tip_layout);
        this.mInteractTipTextView = (TextView) this.mContentView.findViewById(R.id.interact_tip_title);
        this.mLowButton = (RadioButton) this.mContentView.findViewById(R.id.full_low_text);
        this.mStandardButton = (RadioButton) this.mContentView.findViewById(R.id.full_standard_text);
        this.mHighButton = (RadioButton) this.mContentView.findViewById(R.id.full_high_text);
        this.mSuperHighButton = (RadioButton) this.mContentView.findViewById(R.id.full_super_high_text);
        this.m720Button = (RadioButton) this.mContentView.findViewById(R.id.full_720_text);
        this.m1080Button = (RadioButton) this.mContentView.findViewById(R.id.full_1080_text);
        this.m2KButton = (RadioButton) this.mContentView.findViewById(R.id.full_2k_text);
        this.m4KButton = (RadioButton) this.mContentView.findViewById(R.id.full_4K_text);
        this.mStreamSpreadMoreView = (TextView) this.mContentView.findViewById(R.id.tv_spread_click);
        this.mStreamIntroTextView = (TextView) this.mContentView.findViewById(R.id.full_controller_stream_introduce);
        this.mStreamLogoImageView = (ImageView) this.mContentView.findViewById(R.id.full_controller_stream_logo);
        this.mLanguageButton = (TextView) this.mContentView.findViewById(R.id.full_controller_button_language);
        this.mLanguageView = (LanguageView) this.mActivity.findViewById(R.id.full_controller_language_list_view);
        this.mLanguageViewLayout = (RelativeLayout) this.mContentView.findViewById(R.id.layout_full_controller_language);
        this.mLanguageView.init(this);
        this.mTitleDot = this.mContentView.findViewById(R.id.full_controller_skip_begin);
        this.mTrailerDot = this.mContentView.findViewById(R.id.full_controller_skip_end);
        this.mNetImageView = (ImageView) this.mContentView.findViewById(R.id.full_net);
        this.mBatteryImageView = (ImageView) this.mContentView.findViewById(R.id.full_battery);
        this.mTimeTextView = (TextView) this.mContentView.findViewById(R.id.full_time);
        this.mTvSpreadView = this.mContentView.findViewById(R.id.full_controller_tv_spread);
        this.mMoreView = (AlbumMoreView) this.mActivity.findViewById(R.id.full_controller_more_view);
        this.mMoreView.getLayoutParams().width = AlbumPlayActivity.LANDSCAPE_EXPAND_WIDTH;
        this.mTopRadioGroup = this.mContentView.findViewById(R.id.full_controller_top_rb);
        this.mBottomFrame = this.mContentView.findViewById(R.id.full_controller_bottom_frame);
        this.mTopRightView = this.mContentView.findViewById(R.id.full_controller_top_right_frame);
        this.mRightCenterView = this.mContentView.findViewById(R.id.full_cut_barrage_frame);
        this.mWatchingFocusLayout = (WatchingFocusRelativeLayout) this.mContentView.findViewById(R.id.layout_watchFocus);
        this.mBarrageInputBtn = (ImageView) this.mContentView.findViewById(R.id.barrage_input_btn_id);
        this.mBottomBtnsFrame = this.mContentView.findViewById(R.id.full_controller_bottom_btns);
        this.mSwitchView = this.mContentView.findViewById(R.id.player_half_controller_full);
        this.mJumpToVRView = this.mContentView.findViewById(R.id.full_goto_vr);
        initBarrage();
        LeResponseMessage response = LeMessageManager.getInstance().dispatchMessage(this.mActivity, new LeMessage(LeMessageIds.MSG_SHARE_FLOAT_INIT));
        if (LeResponseMessage.checkResponseMessageValidity(response, ShareFloatProtocol.class)) {
            this.mShareProtocol = (ShareFloatProtocol) response.getData();
            LayoutParams lp = new LayoutParams(UIsUtils.dipToPx(283.0f), -1);
            lp.addRule(11);
            ((RelativeLayout) this.mContentView).addView(this.mShareProtocol.getView(), lp);
        }
        LeResponseMessage responseInteract = LeMessageManager.getInstance().dispatchMessage(this.mActivity, new LeMessage(900));
        if (LeResponseMessage.checkResponseMessageValidity(responseInteract, View.class)) {
            ((ViewGroup) this.mContentView).addView((View) responseInteract.getData(), new LayoutParams(UIsUtils.dipToPx(283.0f), -1));
        }
        if (!NetworkUtils.isNetworkAvailable()) {
            this.mMoreView.setButtonEnable(this.mStreamSelectBox, false);
        }
        super.initView();
        initClick();
        initUriLink();
        onTimeChange();
        onNetChange();
        this.mNoNetworkTip = TipUtils.getTipMessage("100008", R.string.load_data_no_net);
        String[] streamNames = PlayUtils.getStreamLevelName();
        this.mLowButton.setText(streamNames[0]);
        this.mStandardButton.setText(streamNames[1]);
        this.mHighButton.setText(streamNames[2]);
        this.mSuperHighButton.setText(streamNames[3]);
        this.m720Button.setText(streamNames[4]);
        this.m1080Button.setText(streamNames[5]);
        if (!PreferencesManager.getInstance().getHasVideoShotBreath()) {
            PreferencesManager.getInstance().saveHasVideoShotBreath(Boolean.valueOf(true));
            startBreath();
        }
        initNonCopyright();
        this.mSubscription = new CompositeSubscription();
        this.mSubscription.add(RxBus.getInstance().toObserverable().observeOn(AndroidSchedulers.mainThread()).subscribe(new 1(this)));
        LeMessageManager.getInstance().registerTask(new LeMessageTask(113, new 2(this)));
    }

    private void initBarrage() {
        if (this.mActivity.getBarrageProtocol() != null) {
            this.mActivity.getBarrageProtocol().setBarrageButton(this.mBarrageButton, this.mBarrageInputBtn);
        }
        LinearLayout contain = (LinearLayout) this.mMoreView.findViewById(R.id.album_more_contain);
        LeResponseMessage response = LeMessageManager.getInstance().dispatchMessage(new LeMessage(304, this.mActivity));
        if (LeResponseMessage.checkResponseMessageValidity(response, View.class)) {
            contain.addView((View) response.getData());
            if (this.mActivity.getBarrageProtocol() != null) {
                this.mActivity.getBarrageProtocol().bindSettingView((View) response.getData());
            }
        }
    }

    public void retryPlayByAudioTrackSelected() {
        this.mActivity.getFlow().retryPlay(false, false);
    }

    public void updateLanguageInfo() {
        LogInfo.log("wuxinrong", "刷新语言栏");
        AudioTrackManager atm = AudioTrackManager.getInstance();
        SubtitleInfoManager sim = SubtitleInfoManager.getInstance();
        ArrayList<String> audioTrackList = atm.getCodeList();
        ArrayList<String> subtitleList = sim.getCodeList();
        if (!BaseTypeUtils.isListEmpty(audioTrackList)) {
            this.mLanguageView.setDataList(0, audioTrackList);
            this.mLanguageView.setSelectedItemColor(0, atm.getIndex());
        }
        if (!BaseTypeUtils.isListEmpty(subtitleList)) {
            this.mLanguageView.setDataList(1, subtitleList);
            this.mLanguageView.setSelectedItemColor(1, sim.getIndex());
        }
        updateLanguageView(audioTrackList, subtitleList);
    }

    private void updateLanguageView(ArrayList<String> audioTrackList, ArrayList<String> subtitleList) {
        if (BaseTypeUtils.isListEmpty(audioTrackList) && BaseTypeUtils.isListEmpty(subtitleList)) {
            this.mLanguageButton.setVisibility(8);
            return;
        }
        this.mLanguageButton.setVisibility(0);
        if (BaseTypeUtils.isListEmpty(audioTrackList)) {
            this.mLanguageView.hide(0);
        } else {
            this.mLanguageView.show(0);
        }
        if (BaseTypeUtils.isListEmpty(subtitleList)) {
            this.mLanguageView.hide(1);
        } else {
            this.mLanguageView.show(1);
        }
    }

    private void initShareFloatView(List<String> roles, int shareFrom) {
        if (roles != null) {
            LogInfo.log("fornia", "roles size:" + roles.size() + "roles:" + roles.toString());
        }
        if (this.mShareProtocol != null) {
            this.mShareProtocol.initView(shareFrom, this.mActivity.getHalfFragment().getCurrPlayingVideo(), this.mActivity.getHalfFragment().getAlbumCardList());
            this.mShareProtocol.setVoteShareRoles(roles);
        }
    }

    private void initClick() {
        this.mStreamSelectBox.setOnClickListener(this);
        this.mLowButton.setOnClickListener(this);
        this.mStandardButton.setOnClickListener(this);
        this.mHighButton.setOnClickListener(this);
        this.mSuperHighButton.setOnClickListener(this);
        this.m720Button.setOnClickListener(this);
        this.m1080Button.setOnClickListener(this);
        this.m4KButton.setOnClickListener(this);
        this.m2KButton.setOnClickListener(this);
        this.mStreamSpreadMoreView.setOnClickListener(this);
        this.mEpisodeButton.setOnClickListener(this);
        this.mInteractButton.setOnClickListener(this);
        this.mSelectSensorBox.setOnClickListener(this);
        this.mBarrageButton.setOnClickListener(this);
        this.mMoreButton.setOnClickListener(this);
        this.mBarrageInputBtn.setOnClickListener(this);
        this.mPanoShareButton.setOnClickListener(this);
        this.mVideoShotButton.setOnClickListener(this);
        this.mVideoShotNotice.setOnClickListener(this);
        this.mVideoShot.setOnClickListener(this);
        this.mGuideRelativeLayout.setOnClickListener(this);
        this.mGuideRelativeActionLayout.setOnClickListener(this);
        this.mLanguageButton.setOnClickListener(this);
        this.mJumpToVRView.setOnClickListener(this);
        this.mTvSpreadView.setOnClickListener(new 3(this));
        this.mSwitchView.setOnClickListener(new 4(this));
        this.mBackView.setOnClickListener(new 5(this));
        this.mLanguageView.setOnClickListener(new 6(this));
    }

    private void initNonCopyright() {
        if (this.mActivity.mIsPlayingNonCopyright) {
            this.mBarrageButton.setVisibility(8);
            this.mRLVideoShot.setVisibility(8);
            this.mMoreButton.setVisibility(8);
            this.mWatchingFocusLayout.setVisibility(8);
            this.mTitleDot.setVisibility(8);
            this.mTrailerDot.setVisibility(8);
            this.mLowButton.setBackgroundResource(R.drawable.noncopyright_play_album_full_pop_selector);
            this.mStandardButton.setBackgroundResource(R.drawable.noncopyright_play_album_full_pop_selector);
            this.mHighButton.setBackgroundResource(R.drawable.noncopyright_play_album_full_pop_selector);
            this.mSuperHighButton.setBackgroundResource(R.drawable.noncopyright_play_album_full_pop_selector);
            this.m720Button.setBackgroundResource(R.drawable.noncopyright_play_album_full_pop_selector);
            this.mColorSelect = this.mActivity.getResources().getColor(R.color.letv_color_noncopyright);
            this.mSeekBar.initNonCopyright();
            this.mBottomFrame.setBackgroundColor(Color.parseColor("#bf000000"));
            this.mContentView.findViewById(R.id.full_controller_top_frame).setBackgroundColor(Color.parseColor("#bf000000"));
            this.mContentView.findViewById(R.id.full_controller_timeline).setVisibility(8);
            this.mPlayImageView.setImageResource(R.drawable.noncopyright_btn_play_selector);
            this.mPlayImageView.setBackgroundColor(this.mColorSelect);
            ((LinearLayout.LayoutParams) this.mPlayImageView.getLayoutParams()).leftMargin = 0;
            this.mPlayImageView.getLayoutParams().width = UIsUtils.dipToPx(48.0f);
            initNonCopyrightBtn(this.mStreamSelectBox);
            initNonCopyrightBtn(this.mEpisodeButton);
            View view = this.mContentView.findViewById(R.id.full_controller_bottom_right_frame);
            LayoutParams rlp = (LayoutParams) view.getLayoutParams();
            rlp.rightMargin = 0;
            view.setLayoutParams(rlp);
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.mStreamSelectBox.getLayoutParams();
            lp.rightMargin = UIsUtils.dipToPx(15.0f);
            this.mStreamSelectBox.setLayoutParams(lp);
            this.mContentView.findViewById(R.id.full_controller_play_next).setVisibility(8);
            this.mStreamTipStreamTextView.setTextColor(this.mColorSelect);
        }
    }

    private void initNonCopyrightBtn(TextView textView) {
        textView.setBackgroundResource(R.drawable.noncopyright_album_play_btn);
        textView.getLayoutParams().width = UIsUtils.dipToPx(40.0f);
        textView.getLayoutParams().height = UIsUtils.dipToPx(19.0f);
        textView.setTextColor(Color.parseColor("#ccfcfcfc"));
    }

    protected void onSeekBarTouchListener(MotionEvent event) {
        super.onSeekBarTouchListener(event);
        if (event.getAction() == 1) {
            StatisticsUtils.staticticsInfoPost(this.mActivity, "0", "c67", "1001", 4, null, PageIdConstant.fullPlayPage, null, null, null, null, null);
        } else if (event.getAction() == 0) {
            hidePopup(0);
        }
    }

    protected void onSeekProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        super.onSeekProgressChanged(seekBar, progress, fromUser);
        if (fromUser) {
            this.mWatchingFocusLayout.onProgressChanged(seekBar, progress, fromUser);
        }
    }

    private void initUriLink() {
        String urlLink2K = TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_100022);
        if (TextUtils.isEmpty(urlLink2K) || this.mActivity.mIsPlayingNonCopyright || LetvUtils.isInHongKong()) {
            this.m2KButton.setVisibility(8);
        } else {
            this.m2KButton.setVisibility(0);
            this.mUriLinkString2K = urlLink2K;
        }
        String uriLink4k = TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_100023);
        if (TextUtils.isEmpty(uriLink4k) || this.mActivity.mIsPlayingNonCopyright || LetvUtils.isInHongKong()) {
            this.m4KButton.setVisibility(8);
            return;
        }
        this.m4KButton.setVisibility(0);
        this.mUriLinkString4K = uriLink4k;
    }

    public void initFullState() {
        if (this.mActivity.isForceFull()) {
            this.mScreenOrientationLock.setVisibility(8);
            if (!(this.mActivity.mIsPanoramaVideo || this.mActivity.mIsDolbyVideo || this.mActivity.mIsLebox || this.mActivity.mIsPlayingNonCopyright)) {
                this.mStreamSelectBox.setVisibility(8);
            }
            if (!(this.mActivity.mIsLebox || this.mActivity.mIsPlayingNonCopyright)) {
                this.mEpisodeButton.setVisibility(8);
            }
        }
        this.mSelectSensorBox.setVisibility(this.mActivity.mIsPanoramaVideo ? 0 : 8);
        setVisibilityForVRBtn(true);
        setButtonSelect(this.mSelectSensorBox, true);
        if (this.mActivity.mIs4dVideo || this.mActivity.mIsPanoramaVideo || this.mActivity.mIsLebox) {
            this.mMoreButton.setVisibility(8);
        }
        if (!this.mActivity.mIsPanoramaVideo || LetvUtils.isInHongKong() || this.mActivity.mIsVR) {
            this.mPanoShareButton.setVisibility(8);
        } else {
            this.mPanoShareButton.setVisibility(0);
        }
        this.mMoreView.initFullState();
    }

    private void setVisibilityForVRBtn(boolean show) {
        if (!this.mActivity.mIsPanoramaVideo || this.mActivity.mIsVR || LetvUtils.isInHongKong() || !show) {
            this.mJumpToVRView.setVisibility(8);
        } else {
            this.mJumpToVRView.setVisibility(0);
        }
    }

    private void setVisibilityForVideoShot() {
        if (this.mActivity.getFlow() == null || this.mActivity.getFlow().mIsDownloadFile || this.mActivity.getFlow().isLocalFile() || this.mActivity.mIsPanoramaVideo || this.mActivity.mIs4dVideo || this.mActivity.mIsDolbyVideo || PreferencesManager.getInstance().getShareWords().equalsIgnoreCase("0") || this.mActivity.mIsPlayingNonCopyright || this.mActivity.mIsLebox || !this.mActivity.mIsLandspace) {
            this.mRLVideoShot.setVisibility(8);
            this.mVideoShot.setVisibility(8);
            LogInfo.log("fornia", "setVisibilityForVideoShot false");
            return;
        }
        if (!FileUtils.hasValidFile(this.mActivity, StoreUtils.VIDEOSHOT_PIC_TEMP_PATH)) {
            this.mVideoShotNotice.setVisibility(8);
        }
        this.mRLVideoShot.setVisibility(0);
        this.mVideoShot.setVisibility(0);
        LogInfo.log("fornia", "setVisibilityForVideoShot show:true");
    }

    private void initStream() {
        if (this.mActivity.getFlow() != null) {
            AlbumPlayFlow flow = this.mActivity.getFlow();
            if (flow.isLebox()) {
                if (TextUtils.equals(flow.mBoxBean.stream, "21")) {
                    this.mStreamSelectBox.setText(R.string.stream_smooth);
                } else if (TextUtils.equals(flow.mBoxBean.stream, "22")) {
                    this.mStreamSelectBox.setText(R.string.stream_hd);
                } else {
                    this.mStreamSelectBox.setText(R.string.stream_standard);
                }
                this.mStreamSelectBox.setEnabled(false);
                this.mMoreView.setButtonEnable(this.mStreamSelectBox, false);
                return;
            }
            this.vidForVideoShotNew = flow.mVid;
            if (this.vidForVideoShotOld == -1) {
                this.vidForVideoShotOld = this.vidForVideoShotNew;
            } else if (!(this.vidForVideoShotOld == -1 || this.vidForVideoShotOld == this.vidForVideoShotNew)) {
                FileUtils.clearPicsAfterChangeVideo(this.mActivity);
            }
            setVisibilityForVideoShot();
            flow.setCurrenStreamFromFullController();
            int playLevel = flow.mPlayLevel;
            boolean isLocalFile = flow.mIsDownloadFile;
            int downloadStreamLevel = flow.getDownloadStreamLevel();
            String[] streamLevels = PlayUtils.getStreamLevelName();
            String superHdName = streamLevels[3];
            String hdName = streamLevels[2];
            String standardName = streamLevels[1];
            String lowName = streamLevels[0];
            if (isLocalFile) {
                String downloadStreamName = hdName;
                if (downloadStreamLevel == 1) {
                    downloadStreamName = superHdName;
                } else if (downloadStreamLevel == 0) {
                    downloadStreamName = standardName;
                }
                this.mStreamSelectBox.setText(downloadStreamName);
            } else {
                this.mStreamSelectBox.setText(PlayUtils.getPlayLevelZh(superHdName, hdName, standardName, lowName, playLevel));
            }
            if (flow.mStreamSupporter.getStreamCount(flow.mLevelList) >= 1) {
                this.mStreamSelectBox.setEnabled(true);
                recoverFullPlayControllerHd();
                if (flow.mIsDownloadFile) {
                    initStreamWhenIsLocal();
                } else {
                    initStreamWhenOnline();
                }
            }
            if (flow.mLaunchMode == 0) {
                this.mStreamSelectBox.setVisibility(8);
            }
            if (mIsFirstInitLayout) {
                mIsFirstInitLayout = false;
            }
            setStreamButtonEnableByLevel(playLevel);
            if (!flow.mIsDownloadFile) {
                setStreamButtonSelectColor(playLevel);
            }
            if (playLevel == 5) {
                checkIfCanPlay1080p(false);
            }
        }
    }

    private void recoverFullPlayControllerHd() {
        if (this.mActivity.getFlow() != null) {
            AlbumPlayFlow flow = this.mActivity.getFlow();
            AlbumStreamSupporter supporter = flow.mStreamSupporter;
            if (!flow.mIsDownloadFile) {
                if (supporter.has1080p) {
                    this.m1080Button.setTextColor(this.mColorWhite);
                    this.m1080Button.setEnabled(true);
                } else {
                    this.m1080Button.setTextColor(this.mColorGrey);
                    this.m1080Button.setEnabled(false);
                }
                if (supporter.has720p) {
                    this.m720Button.setTextColor(this.mColorWhite);
                    this.m720Button.setEnabled(true);
                    this.m720Button.setVisibility(0);
                } else {
                    this.m720Button.setTextColor(this.mColorGrey);
                    this.m720Button.setEnabled(false);
                    this.m720Button.setVisibility(8);
                }
                if (supporter.hasSuperHd) {
                    this.mSuperHighButton.setTextColor(this.mColorWhite);
                    this.mSuperHighButton.setEnabled(true);
                } else {
                    this.mSuperHighButton.setTextColor(this.mColorGrey);
                    this.mSuperHighButton.setEnabled(false);
                }
                if (supporter.hasHd) {
                    this.mHighButton.setTextColor(this.mColorWhite);
                    this.mHighButton.setEnabled(true);
                } else {
                    this.mHighButton.setTextColor(this.mColorGrey);
                    this.mHighButton.setEnabled(false);
                }
                if (supporter.hasStandard) {
                    this.mStandardButton.setTextColor(this.mColorWhite);
                    this.mStandardButton.setEnabled(true);
                } else {
                    this.mStandardButton.setTextColor(this.mColorGrey);
                    this.mStandardButton.setEnabled(false);
                }
                if (supporter.hasLow) {
                    this.mLowButton.setTextColor(this.mColorWhite);
                    this.mLowButton.setEnabled(true);
                } else {
                    this.mLowButton.setTextColor(this.mColorGrey);
                    this.mLowButton.setEnabled(false);
                }
                if (this.mTvSpreadView.getVisibility() != 0) {
                    switch (flow.mPlayLevel) {
                        case 0:
                            this.mLowButton.setChecked(true);
                            return;
                        case 1:
                            this.mStandardButton.setChecked(true);
                            return;
                        case 2:
                            this.mHighButton.setChecked(true);
                            return;
                        case 3:
                            this.mSuperHighButton.setChecked(true);
                            return;
                        case 5:
                            this.m1080Button.setChecked(true);
                            return;
                        default:
                            return;
                    }
                }
            }
        }
    }

    private void initStreamWhenIsLocal() {
        if (this.mActivity.getFlow() != null) {
            AlbumPlayFlow flow = this.mActivity.getFlow();
            this.m720Button.setVisibility(8);
            this.m1080Button.setVisibility(8);
            switch (flow.getDownloadStreamLevel()) {
                case 0:
                    this.mHighButton.setVisibility(8);
                    this.mSuperHighButton.setVisibility(8);
                    this.mLowButton.setVisibility(8);
                    this.mStandardButton.setVisibility(0);
                    flow.mSelectStream = 2;
                    return;
                case 1:
                    this.mHighButton.setVisibility(8);
                    this.mStandardButton.setVisibility(8);
                    this.mLowButton.setVisibility(8);
                    this.mSuperHighButton.setVisibility(0);
                    this.mSuperHighButton.setEnabled(true);
                    this.mSuperHighButton.setSelected(true);
                    flow.mSelectStream = 4;
                    return;
                case 2:
                    this.mLowButton.setVisibility(8);
                    this.mSuperHighButton.setVisibility(8);
                    this.mStandardButton.setVisibility(8);
                    this.mHighButton.setVisibility(0);
                    this.mHighButton.setEnabled(true);
                    this.mHighButton.setSelected(true);
                    flow.mSelectStream = 3;
                    return;
                default:
                    return;
            }
        }
    }

    private void initStreamWhenOnline() {
        if (this.mActivity.getFlow() != null) {
            AlbumStreamSupporter supporter = this.mActivity.getFlow().mStreamSupporter;
            this.m1080Button.setEnabled(false);
            this.m1080Button.setTextColor(this.mActivity.getResources().getColor(R.color.letv_color_ff969696));
            this.m1080Button.setVisibility(8);
            if (supporter.has720p) {
                this.m720Button.setVisibility(0);
                this.m720Button.setEnabled(true);
            } else {
                this.m720Button.setEnabled(false);
                this.m720Button.setTextColor(this.mActivity.getResources().getColor(R.color.letv_color_ff969696));
                this.m720Button.setVisibility(8);
            }
            if (supporter.hasSuperHd) {
                this.mSuperHighButton.setVisibility(0);
                this.mSuperHighButton.setEnabled(true);
            } else {
                this.mSuperHighButton.setEnabled(false);
                this.mSuperHighButton.setTextColor(this.mActivity.getResources().getColor(R.color.letv_color_ff969696));
                this.mSuperHighButton.setVisibility(8);
            }
            if (supporter.hasHd) {
                this.mHighButton.setVisibility(0);
                this.mHighButton.setEnabled(true);
            } else {
                this.mHighButton.setEnabled(false);
                this.mHighButton.setVisibility(8);
            }
            if (supporter.hasStandard) {
                this.mStandardButton.setVisibility(0);
                this.mStandardButton.setEnabled(true);
            } else {
                this.mStandardButton.setEnabled(false);
                this.mStandardButton.setVisibility(8);
            }
            if (supporter.hasLow) {
                this.mLowButton.setVisibility(0);
                this.mLowButton.setEnabled(true);
                return;
            }
            this.mLowButton.setEnabled(false);
            this.mLowButton.setTextColor(this.mActivity.getResources().getColor(R.color.letv_color_ff969696));
            this.mLowButton.setVisibility(8);
        }
    }

    private void setStreamButtonEnableByLevel(int level) {
        boolean z;
        boolean z2 = true;
        this.mLowButton.setClickable(level != 0);
        RadioButton radioButton = this.mStandardButton;
        if (level != 1) {
            z = true;
        } else {
            z = false;
        }
        radioButton.setClickable(z);
        radioButton = this.mHighButton;
        if (level != 2) {
            z = true;
        } else {
            z = false;
        }
        radioButton.setClickable(z);
        radioButton = this.mSuperHighButton;
        if (level != 3) {
            z = true;
        } else {
            z = false;
        }
        radioButton.setClickable(z);
        radioButton = this.m720Button;
        if (level != 4) {
            z = true;
        } else {
            z = false;
        }
        radioButton.setClickable(z);
        RadioButton radioButton2 = this.m1080Button;
        if (level == 5) {
            z2 = false;
        }
        radioButton2.setClickable(z2);
    }

    private void setStreamButtonSelectColor(int selectedStream) {
        LogInfo.log("zhuqiao", "selectedStream:" + selectedStream);
        this.mLowButton.setTextColor(this.mColorWhite);
        this.mStandardButton.setTextColor(this.mColorWhite);
        this.mHighButton.setTextColor(this.mColorWhite);
        this.mSuperHighButton.setTextColor(this.mColorWhite);
        this.m720Button.setTextColor(this.mColorWhite);
        this.m1080Button.setTextColor(this.mColorWhite);
        String[] streamLevels = PlayUtils.getStreamLevelName();
        if (selectedStream == 0) {
            this.mLowButton.setTextColor(this.mColorSelect);
            this.mStreamSelectBox.setText(streamLevels[0]);
        } else if (selectedStream == 1) {
            this.mStandardButton.setTextColor(this.mColorSelect);
            this.mStreamSelectBox.setText(streamLevels[1]);
        } else if (selectedStream == 2) {
            this.mHighButton.setTextColor(this.mColorSelect);
            this.mStreamSelectBox.setText(streamLevels[2]);
        } else if (selectedStream == 3) {
            this.mSuperHighButton.setTextColor(this.mColorSelect);
            this.mStreamSelectBox.setText(streamLevels[3]);
        } else if (selectedStream == 4) {
            this.m720Button.setTextColor(this.mColorSelect);
            this.mStreamSelectBox.setText(streamLevels[4]);
        } else if (selectedStream == 5) {
            this.m1080Button.setTextColor(this.mColorSelect);
            this.mStreamSelectBox.setText(streamLevels[5]);
        }
        this.m4KButton.setTextColor(this.mColorGrey);
        this.m2KButton.setTextColor(this.mColorGrey);
    }

    public void checkVideoType(AlbumCardList cardList) {
        if (!AlbumPageCard.isPostiveVideo(cardList, null) && cardList.mIsAlbum) {
            this.mEpisodeButton.setText(R.string.list);
        } else if (cardList.videoList.style == 1) {
            this.mEpisodeButton.setText(R.string.episode);
        } else if (cardList.videoList.style == 2) {
            this.mEpisodeButton.setText(R.string.list);
        } else {
            this.mEpisodeButton.setText(R.string.periods);
        }
    }

    private void videoShot(boolean is2Edit) {
        this.mIsVideoShotting = true;
        AlbumPlayFlow flow = this.mActivity.getFlow();
        String dispatchUrl = flow.mAlbumUrl.dispatchUrl;
        if (flow.mVideoType == VideoType.Drm) {
            String[] arr = PlayUtils.getDDUrls(flow.mVideoFile, flow.mPlayLevel, flow.mCurrentPlayingVideo.pay == 1, VideoType.Normal).poll();
            if (arr != null) {
                dispatchUrl = arr[1];
            } else {
                return;
            }
        }
        new LetvRequest().setUrl(dispatchUrl).setParser(new RealPlayUrlInfoParser()).setCache(new VolleyNoCache()).setMaxRetries(2).setRequestType(RequestManner.NETWORK_ONLY).setTag(AlbumPlayFlow.REQUEST_REAL_URL).setCallback(new 7(this, is2Edit)).add();
        LetvUtils.Vibrate(this.mActivity, 100);
        changeVisibleByAnim(false);
    }

    private void showVideoShotGuide(Bitmap bitmap) {
        this.mGuideImage.setImageBitmap(bitmap);
        int[] locations = new int[2];
        this.mVideoShotButton.getLocationOnScreen(locations);
        int x = locations[0];
        int y = locations[1];
        LayoutParams guideParams = (LayoutParams) this.mGuideRelativeLayout.getLayoutParams();
        guideParams.leftMargin = x - UIsUtils.dipToPx(120.0f);
        guideParams.topMargin = (this.mVideoShotButton.getHeight() + y) + UIsUtils.dipToPx(5.0f);
        this.mGuideRelativeLayout.setLayoutParams(guideParams);
        LayoutParams arrowParams = (LayoutParams) this.mVideoShotGuideArrow.getLayoutParams();
        arrowParams.leftMargin = (UIsUtils.dipToPx(120.0f) + (this.mVideoShotButton.getWidth() / 2)) - UIsUtils.dipToPx(8.0f);
        arrowParams.topMargin = 0;
        this.mVideoShotGuideArrow.setLayoutParams(arrowParams);
    }

    public void showVideoShotActionGuide() {
        if (this.mActivity.getBarrageProtocol() != null && this.mActivity.getBarrageProtocol().showGuide()) {
            this.mHandler.postDelayed(new 8(this), 500);
        }
    }

    private void changeVisibleByAnim(boolean isShow) {
        if (isShow) {
            this.mVideoShotBorder.setVisibility(8);
            this.visibleAnimation = AnimationUtils.loadAnimation(this.mActivity, R.anim.fade_in);
            this.mVideoShotBorder.startAnimation(this.visibleAnimation);
            this.visibleAnimation.setAnimationListener(new 9(this));
            return;
        }
        this.mVideoShotBorder.setVisibility(0);
        this.visibleAnimation = AnimationUtils.loadAnimation(this.mActivity, R.anim.fade_out_videoshot);
        this.mVideoShotBorder.startAnimation(this.visibleAnimation);
        this.visibleAnimation.setAnimationListener(new 10(this));
    }

    private void go2VideoshotEdit() {
        LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new VideoShotEditActivityConfig(this.mActivity).create(setValues(new VideoShotShareInfoBean()))));
        this.mIsVideoShotting = false;
        this.mIsClickVideoShotButton = false;
    }

    public void onClick(View v) {
        LogInfo.log("fornia", "onclick v" + v.getContentDescription());
        super.onClick(v);
        if (this.mActivity.getFlow() != null) {
            AlbumPlayFlow flow = this.mActivity.getFlow();
            AlbumStreamSupporter supporter = flow.mStreamSupporter;
            int id = v.getId();
            if (id == R.id.full_controller_hd) {
                clickSelectDH();
            } else if (id == R.id.full_low_text) {
                clickStreamBtn(0, supporter.hasLow, flow.mPlayLevel != 0, this.mLowButton.getText().toString());
            } else if (id == R.id.full_standard_text) {
                clickStreamBtn(1, supporter.hasStandard, flow.mPlayLevel != 1, this.mStandardButton.getText().toString());
            } else if (id == R.id.full_high_text) {
                clickStreamBtn(2, supporter.hasHd, flow.mPlayLevel != 2, this.mHighButton.getText().toString());
            } else if (id == R.id.full_super_high_text) {
                clickStreamBtn(3, supporter.hasSuperHd, flow.mPlayLevel != 3, this.mSuperHighButton.getText().toString());
            } else if (id == R.id.full_720_text) {
                clickStreamBtn(4, supporter.has720p, flow.mPlayLevel != 4, this.m720Button.getText().toString());
            } else if (id == R.id.full_1080_text) {
                clickStreamBtn(5, supporter.has1080p, flow.mPlayLevel != 5, this.m1080Button.getText().toString());
            } else if (id == R.id.full_2k_text) {
                click2K();
            } else if (id == R.id.full_4K_text) {
                click4k();
            } else if (id == R.id.tv_spread_click) {
                clickLeanMore();
            } else if (id == R.id.full_controller_select_episode) {
                setVisibilityForEpisode(true);
                StatisticsUtils.staticticsInfoPost(this.mActivity, "0", "c65", LetvErrorCode.SUB_ERROR_CODE_DATA_ERROR, 6, null, PageIdConstant.fullPlayPage, null, null, null, null, null);
            } else if (id == R.id.full_controller_pano_share) {
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(LeMessageIds.MSG_SHARE_REQUEST_LINK));
                shareClick(null, 1);
            } else if (id == R.id.full_controller_select_sensor) {
                if (!this.mActivity.mIsVR) {
                    clickSensor();
                }
            } else if (id == R.id.full_controller_barrage) {
                if (this.mActivity.getBarrageProtocol() != null) {
                    this.mActivity.getBarrageProtocol().clickBarrageBtn();
                }
            } else if (id == R.id.full_controller_more) {
                setVisibilityForMore(true);
            } else if (id == R.id.barrage_input_btn_id) {
                if (NetworkUtils.isNetworkAvailable()) {
                    this.mActivity.getController().pause(false);
                    if (this.mActivity.getBarrageProtocol() != null) {
                        this.mActivity.getBarrageProtocol().showBarrageInputView();
                        return;
                    }
                    return;
                }
                ToastUtils.showToast(R.string.net_error);
            } else if (id == R.id.full_controller_cut) {
                StatisticsUtils.staticticsInfoPost(this.mActivity, "0", "c68", null, 9, null, PageIdConstant.fullPlayPage, null, null, null, PageIdConstant.fullPlayPage, null);
                if (NetworkUtils.isNetworkAvailable()) {
                    delayHide();
                    if (this.mIsVideoShotting) {
                        ToastUtils.showToast(R.string.videoshot_action_doing_wait);
                        return;
                    } else if (FileUtils.getFileCount(this.mActivity, StoreUtils.VIDEOSHOT_PIC_TEMP_PATH) >= 99) {
                        ToastUtils.showToast(R.string.videoshot_advise_delete);
                        return;
                    } else {
                        if (this.mVideoShotProgress != null) {
                            this.mVideoShotProgress.setVisibility(0);
                        }
                        videoShot(false);
                        return;
                    }
                }
                ToastUtils.showToast(R.string.net_error);
            } else if (id == R.id.full_controller_videoshot) {
                StatisticsUtils.staticticsInfoPost(this.mActivity, "0", "c65", null, 10, null, PageIdConstant.fullPlayPage, null, null, null, PageIdConstant.fullPlayPage, null);
                stopBreath();
                if (!NetworkUtils.isNetworkAvailable()) {
                    ToastUtils.showToast(R.string.net_error);
                } else if (FileUtils.hasValidFile(this.mActivity, StoreUtils.VIDEOSHOT_PIC_TEMP_PATH)) {
                    this.mVideoShotNotice.setVisibility(8);
                    go2VideoshotEdit();
                } else if (this.mIsClickVideoShotButton) {
                    ToastUtils.showToast(R.string.videoshot_action_doing_wait);
                } else {
                    this.mIsClickVideoShotButton = true;
                    videoShot(true);
                }
            } else if (id == R.id.rl_videoshot_guide) {
                this.mGuideRelativeLayout.setVisibility(8);
                this.mVideoShotNotice.setVisibility(8);
                this.mVideoShotBorder.setVisibility(8);
                go2VideoshotEdit();
            } else if (id == R.id.rl_videoshot_guide_layout) {
                this.mGuideRelativeActionLayout.setVisibility(8);
            } else if (id == R.id.full_controller_interact) {
                setVisibilityForInteract(true);
            } else if (id == R.id.full_controller_button_language) {
                onLanguageButtonClicked();
            } else if (id == R.id.full_goto_vr) {
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayVRActivityConfig(this.mActivity).create(flow.mAid, flow.mVid, flow.mFrom, this.mActivity.mIsPlayingNonCopyright, this.mActivity.mNonCopyrightUrl)));
                this.mActivity.finish();
            }
        }
    }

    public void onShareActivityResult(int requestCode, int resultCode, Intent data) {
        if (this.mShareProtocol != null) {
            this.mShareProtocol.onShareActivityResult(requestCode, resultCode, data);
        }
    }

    public void shareClick(List<String> roles, int shareFrom) {
        if (shareFrom == 6) {
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(LeMessageIds.MSG_INTERACT_HIDE));
            if (LetvUtils.isInHongKong()) {
                LogInfo.log("sx", "shareClick 点播投票屏蔽");
                UIsUtils.showToast(R.string.share_copyright_disable);
                return;
            }
        }
        initShareFloatView(roles, shareFrom);
        setVisibilityForShare(true);
    }

    private void clickSensor() {
        boolean z = true;
        if (this.mIsSensorSelected) {
            setButtonSelect(this.mSelectSensorBox, false);
            this.mActivity.getController().closeSensor();
        } else {
            setButtonSelect(this.mSelectSensorBox, true);
            this.mActivity.getController().openSensor();
        }
        if (this.mIsSensorSelected) {
            z = false;
        }
        this.mIsSensorSelected = z;
    }

    public void setVisibilityForMore(boolean show) {
        if (show) {
            this.mMoreView.show();
        } else {
            this.mMoreView.hide();
        }
        onRightPopHiddenChanged(!show);
        if (show) {
            StatisticsUtils.statisticsActionInfo(this.mActivity, PageIdConstant.fullPlayPage, "19", "c655", null, -1, null);
        }
    }

    private void setVisibilityForShare(boolean show) {
        LogInfo.log("fornia", "setVisibilityForShare show:" + show);
        if (this.mShareProtocol == null) {
            return;
        }
        if (show) {
            this.mShareProtocol.show();
            this.mMoreView.hide();
            onRightPopHiddenChanged(false);
            StatisticsUtils.statisticsActionInfo(this.mActivity, PageIdConstant.fullPlayPage, "19", "c655", null, -1, null);
            return;
        }
        this.mShareProtocol.hide();
    }

    private void setVisibilityForInteract(boolean show) {
        if (this.mActivity.getFlow() != null) {
            if (show) {
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(LeMessageIds.MSG_INTERACT_SHOW, String.valueOf(this.mActivity.getFlow().mVid)));
            } else {
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(LeMessageIds.MSG_INTERACT_HIDE));
            }
            onRightPopHiddenChanged(!show);
        }
    }

    public void initProcess(int max, int progress, int buffer) {
        super.initProcess(max, progress, buffer);
        initStream();
        drawWatchFocus();
        initInteract();
        updateWindowPlayerButtonState();
        if (!NetworkUtils.isNetworkAvailable()) {
            this.mActivity.getHalfController().findNextVideo();
        }
        if (this.mActivity.mIsLebox && this.mActivity.getFlow().mBoxBean != null) {
            this.mTitleTextView.setText(this.mActivity.getFlow().mBoxBean.videoName);
        }
        if (mIsSwitch && this.mActivity.getFlow().enableDoublePlayer() && !isPlayingCombineAd()) {
            onStreamSwitchFinish(true);
        }
    }

    private void drawWatchFocus() {
        if (!this.mActivity.mIsPlayingNonCopyright) {
            this.mHandler.postDelayed(new 11(this), SPConstant.DELAY_BUFFER_DURATION);
        }
    }

    public void initInteract() {
        int i = 0;
        if (!isPlayingCombineAd() && this.mActivity.getFlow() != null && this.mActivity.getFlow().mCurrentPlayingVideo != null) {
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(LeMessageIds.MSG_INTERACT_RESET));
            VideoBean video = this.mActivity.getFlow().mCurrentPlayingVideo;
            int allowVote = video.allowVote;
            this.mInteractButton.setVisibility(allowVote == 1 ? 0 : 8);
            if (!this.mHasShowInteract) {
                this.mHasShowInteract = true;
                View view = this.mInteractTipLayout;
                if (allowVote != 1 || TextUtils.isEmpty(video.votePoptext)) {
                    i = 8;
                }
                view.setVisibility(i);
            }
            this.mInteractTipTextView.setText(BaseTypeUtils.ensureStringValidate(video.votePoptext));
            this.mInteractTipLayout.setOnClickListener(new 12(this));
            ((LayoutParams) this.mInteractTipLayout.getLayoutParams()).topMargin = Math.min(UIsUtils.getScreenWidth(), UIsUtils.getScreenHeight()) - UIsUtils.dipToPx(74.0f);
            if (allowVote == 1) {
                this.mHandler.postDelayed(new 13(this), 5000);
            }
        }
    }

    private void occurBlockPostBtn() {
        removeHideHandler();
        delayHide();
    }

    public void refreshEpisodeView() {
        if (this.mActivity.getHalfFragment().getExpendFragment().isOpened()) {
            setVisibilityForEpisode(true);
        }
    }

    public void setBlockBtnVisibile(boolean show) {
        if (show) {
            LogInfo.log("zhuqiao", "卡顿曝光");
            StatisticsUtils.staticticsInfoPost(this.mActivity, "19", "c65", null, 2, null, PageIdConstant.fullPlayPage, null, null, null, null, null);
        }
    }

    public void onVolumeChange(int max, int progress, VolumnChangeStyle style, boolean show) {
        if (progress <= max) {
            delayHide();
        }
    }

    public void update(Observable observable, Object data) {
        super.update(observable, data);
        if (data instanceof String) {
            String notify = (String) data;
            if (TextUtils.equals(ScreenObservable.ON_CONFIG_CHANGE, notify)) {
                LogInfo.log("jc666", "albumplay on config change");
                if (this.mActivity.mIsLandspace) {
                    changeToFull();
                } else {
                    changeToHalf(true);
                }
                if (this.mActivity.mIsLandspace && this.mFullSeekBarWidth > 0) {
                    updateSkipState(this.mFullSeekBarWidth);
                } else if (this.mActivity.mIsLandspace || this.mHalfSeekBarWidth <= 0) {
                    this.mHandler.post(new 14(this));
                } else {
                    updateSkipState(this.mHalfSeekBarWidth);
                }
            } else if (TextUtils.equals(AlbumGestureObservable.ON_CLICK, notify)) {
                if (isVisible()) {
                    if (this.mBottomFrame.getVisibility() == 8) {
                        setControllerVisibility(8, true);
                    } else {
                        doVisibilityChangeAnim(false);
                    }
                    changeToHalf(false);
                    return;
                }
                doVisibilityChangeAnim(true);
            } else if (TextUtils.equals(AlbumGestureObservable.DOUBLE_CLICK, notify)) {
                LogInfo.log("zhaosumin", "双击后的操作");
                closePauseAd();
                this.mController.clickPauseOrPlay();
            } else if (TextUtils.equals(AlbumGestureObservable.ON_GESTURE_CHANGE, notify)) {
                hidePopup(0);
            } else if (TextUtils.equals(AlbumPlayFlowObservable.ON_STREAM_INIT, notify)) {
                initStream();
            } else if (TextUtils.equals(PlayObservable.ON_TIME_CHANGE, notify)) {
                onTimeChange();
            } else if (TextUtils.equals(PlayObservable.ON_NET_CHANGE, notify)) {
                onNetChange();
            } else if (TextUtils.equals(AlbumPlayFlowObservable.ON_START_FETCHING, notify)) {
                if (UIsUtils.isLandscape(this.mActivity)) {
                    setVisibilityForEpisode(false);
                }
                AlbumPlayFlow flow = this.mActivity.getFlow();
                if (flow.mIsDownloadFile) {
                    setStreamButtonSelectColor(flow.mSelectStream);
                } else {
                    setStreamButtonSelectColor(flow.mPlayLevel);
                }
                this.mWatchingFocusLayout.clearFocus();
            }
        } else if (data instanceof BatteryStatusNotify) {
            BatteryStatusNotify notify2 = (BatteryStatusNotify) data;
            onBatteryChange(notify2.batteryStatus, notify2.batterycurPower);
        }
    }

    public void updateVolume(VolumeChangeNotify volume) {
        onVolumeChange(volume.max, volume.progress, volume.style, volume.showSeekbar);
    }

    protected void changeToHalf(boolean isDirectionChange) {
        if (isDirectionChange) {
            this.mTopRightView.setVisibility(8);
            this.mTopRadioGroup.setVisibility(8);
            this.mBottomBtnsFrame.setVisibility(8);
            this.mWatchingFocusLayout.setVisibility(8);
            this.mSwitchView.setVisibility(0);
            this.mVideoShot.setVisibility(8);
        } else {
            setVisibilityForEpisode(false);
        }
        setVisibilityForMore(false);
        setVisibilityForInteract(false);
        setVisibilityForShare(false);
        this.mGuideRelativeActionLayout.setVisibility(8);
        this.mGuideRelativeLayout.setVisibility(8);
    }

    protected void changeToFull() {
        int i;
        int i2 = 0;
        this.mTopRightView.setVisibility(0);
        this.mTopRadioGroup.setVisibility(this.mActivity.mIsPlayingDlna ? 8 : 0);
        View view = this.mRightCenterView;
        if (this.mActivity.mIsPlayingNonCopyright || this.mActivity.mIsPlayingDlna) {
            i = 8;
        } else {
            i = 0;
        }
        view.setVisibility(i);
        WatchingFocusRelativeLayout watchingFocusRelativeLayout = this.mWatchingFocusLayout;
        if (this.mActivity.mIsPlayingNonCopyright) {
            i = 8;
        } else {
            i = 0;
        }
        watchingFocusRelativeLayout.setVisibility(i);
        View view2 = this.mBottomBtnsFrame;
        if (this.mActivity.mIsPlayingDlna) {
            i2 = 8;
        }
        view2.setVisibility(i2);
        this.mSwitchView.setVisibility(8);
        setVisibilityForVideoShot();
    }

    private void onTimeChange() {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        this.mTimeTextView.setText(StringUtils.getStringTwo(String.valueOf(mCalendar.get(11))) + com.letv.pp.utils.NetworkUtils.DELIMITER_COLON + StringUtils.getStringTwo(String.valueOf(mCalendar.get(12))));
    }

    private void onNetChange() {
        this.mMoreView.setButtonEnable(this.mStreamSelectBox, true);
        this.mMoreView.setButtonEnable(this.mLanguageButton, true);
        switch (NetworkUtils.getNetworkType()) {
            case 0:
                this.mMoreView.setButtonEnable(this.mStreamSelectBox, false);
                this.mMoreView.setButtonEnable(this.mLanguageButton, false);
                this.mNetImageView.setImageResource(R.drawable.net_no);
                this.mVideoShotBorder.clearAnimation();
                this.mIsVideoShotting = false;
                this.mIsClickVideoShotButton = false;
                if (this.mVideoShotProgress != null) {
                    this.mVideoShotProgress.setVisibility(8);
                    return;
                }
                return;
            case 1:
                this.mNetImageView.setImageResource(R.drawable.net_wifi);
                return;
            case 2:
                this.mNetImageView.setImageResource(R.drawable.net_2g);
                return;
            case 3:
                this.mNetImageView.setImageResource(R.drawable.net_3g);
                return;
            default:
                return;
        }
    }

    private void onBatteryChange(int curStatus, int curPower) {
        boolean isCharging = false;
        switch (curStatus) {
            case 2:
                isCharging = true;
                break;
        }
        if (isCharging) {
            this.mBatteryImageView.setImageResource(R.drawable.battery_charge);
        } else if (curPower >= 80) {
            this.mBatteryImageView.setImageResource(R.drawable.battery5);
        } else if (curPower >= 60) {
            this.mBatteryImageView.setImageResource(R.drawable.battery4);
        } else if (curPower >= 40) {
            this.mBatteryImageView.setImageResource(R.drawable.battery3);
        } else if (curPower >= 20) {
            this.mBatteryImageView.setImageResource(R.drawable.battery2);
        } else if (curPower >= 0) {
            this.mBatteryImageView.setImageResource(R.drawable.battery1);
        }
    }

    private boolean processFirstUse(Context context, int type) {
        boolean isFirst = context.getSharedPreferences("gesture", 0).getBoolean(type == 0 ? "isFirstPush" : "isFirstDownload", true);
        if (isFirst) {
            DlnaFirstPushDialog dlg = new DlnaFirstPushDialog(context, R.style.first_push_style, type == 0 ? R.string.double_finger_up : R.string.double_finger_down, type == 0 ? R.drawable.double_finger_up : R.drawable.double_finger_down);
            dlg.setOnDismissListener(new 15(this, context, type));
            dlg.show();
            if (!this.mIsLocked) {
                if (this.mActivity.getController() != null) {
                    this.mActivity.getController().setLock(true);
                }
                this.mIsLocked = true;
            }
        }
        return isFirst;
    }

    public void processScreenProjection(Context context, int type, OnPushToTvResponseCallback callback) {
        hidePopup(0);
        if (isVisible()) {
            setVisibility(8);
        }
        createScreenProjectionController(callback);
        if (!processFirstUse(context, type)) {
            this.mActivity.getScreenProjectionController().performScreenProject(type);
        }
    }

    private void createScreenProjectionController(OnPushToTvResponseCallback callback) {
        if (this.mActivity.getScreenProjectionController() == null) {
            this.mActivity.setScreenProjectionController(new ScreenProjectionController(this.mActivity));
        }
        this.mActivity.getScreenProjectionController().setOnPushToTvResponseCallback(callback);
    }

    private void setPreferenceValue(Context context, int type, boolean value) {
        Editor editor = context.getSharedPreferences("gesture", 0).edit();
        editor.putBoolean(type == 0 ? "isFirstPush" : "isFirstDownload", false);
        editor.commit();
    }

    private void setButtonSelect(TextView textView, boolean select) {
        if (!this.mActivity.mIsPlayingNonCopyright) {
            if (select) {
                textView.setBackgroundResource(R.drawable.bg_btn_play_full_selected);
                return;
            }
            textView.setTextAppearance(this.mActivity, R.style.play_controller_btn_text);
            textView.setBackgroundResource(R.drawable.btn_play_live_full_func_bg_selector);
        }
    }

    public void updateWindowPlayerButtonState() {
        boolean enable = true;
        boolean dlnaEnable = true;
        if (this.mActivity.mIsPanoramaVideo || this.mActivity.mIsDolbyVideo || this.mActivity.getVipTrailListener().isVipVideo()) {
            enable = false;
            dlnaEnable = false;
        } else if (this.mActivity.getFlow() instanceof AlbumPlayTopicFlow) {
            enable = false;
        } else if (LetvUtils.getBrandName().toLowerCase().contains("xiaomi")) {
            String version = LetvUtils.getMIUIVersion();
            if (!TextUtils.isEmpty(version) && version.compareTo("V6") >= 0) {
                enable = false;
            }
        }
        if (this.mActivity.getFlow() != null && this.mActivity.getFlow().isLocalFile()) {
            dlnaEnable = false;
        }
        this.mMoreView.updateWindowPlayerButtonState(enable, dlnaEnable);
    }

    private void clickSelectDH() {
        delayHide();
        if (NetworkUtils.isNetworkAvailable()) {
            hidePopup(2);
            hideTvSpreadView();
            if (!(this.mActivity.getFlow() == null || this.mActivity.getFlow().mIsDownloadFile)) {
                setStreamButtonSelectColor(this.mActivity.getFlow().mPlayLevel);
            }
            if (this.mStreamSelectLayout.getVisibility() == 0) {
                this.mStreamSelectLayout.setVisibility(8);
                setButtonSelect(this.mStreamSelectBox, false);
                this.mVideoShot.setClickable(true);
                this.mBarrageInputBtn.setClickable(true);
                return;
            }
            this.mStreamSelectLayout.setVisibility(0);
            this.mStreamSelectLayout.measure(0, 0);
            setButtonSelect(this.mStreamSelectBox, true);
            StatisticsUtils.staticticsInfoPost(this.mActivity, "0", "c67", "1005", 5, null, PageIdConstant.fullPlayPage, null, null, null, null, null);
            int[] locations = new int[2];
            this.mStreamSelectBox.getLocationInWindow(locations);
            int x = locations[0];
            int y = locations[1];
            LogInfo.log("zhuqiao", "mStreamSelectLayout.getMeasuredHeight():" + this.mStreamSelectLayout.getMeasuredHeight());
            LayoutParams selectStreamParams = (LayoutParams) this.mStreamSelectLayout.getLayoutParams();
            selectStreamParams.leftMargin = (((this.mStreamSelectBox.getWidth() / 2) + x) - (UIsUtils.dipToPx(59.0f) / 2)) - UIsUtils.dipToPx(240.0f);
            selectStreamParams.topMargin = (y - this.mStreamSelectLayout.getMeasuredHeight()) - UIsUtils.dipToPx(8.0f);
            this.mStreamSelectLayout.setLayoutParams(selectStreamParams);
            this.mStreamSelectLayout.requestLayout();
            this.mVideoShot.setClickable(false);
            this.mBarrageInputBtn.setClickable(false);
            return;
        }
        UIsUtils.showToast(this.mNoNetworkTip);
    }

    private void onLanguageButtonClicked() {
        StatisticsUtils.statisticsActionInfo(this.mActivity, PageIdConstant.fullPlayPage, "0", "c67", null, 8, null);
        if (NetworkUtils.isNetworkAvailable()) {
            delayHide();
            hidePopup(4);
            if (this.mLanguageView.getVisibility() == 0) {
                this.mLanguageView.setVisibility(8);
                this.mLanguageViewLayout.setVisibility(8);
                setButtonSelect(this.mLanguageButton, false);
                this.mVideoShot.setClickable(true);
                this.mBarrageInputBtn.setClickable(true);
                return;
            }
            this.mVideoShot.setClickable(false);
            this.mBarrageInputBtn.setClickable(false);
            this.mLanguageView.setVisibility(0);
            this.mLanguageViewLayout.setVisibility(0);
            LayoutParams languageLayout = (LayoutParams) this.mLanguageView.getLayoutParams();
            languageLayout.width = this.mLanguageView.mOriginalWidth;
            this.mLanguageView.measure(0, 0);
            setButtonSelect(this.mLanguageButton, true);
            int[] locations = new int[2];
            this.mLanguageButton.getLocationOnScreen(locations);
            int x = locations[0];
            int y = locations[1];
            boolean isSingleList = false;
            if (BaseTypeUtils.isListEmpty(AudioTrackManager.getInstance().getCodeList()) || BaseTypeUtils.isListEmpty(SubtitleInfoManager.getInstance().getCodeList())) {
                isSingleList = true;
            }
            if (isSingleList) {
                languageLayout.width = this.mLanguageView.mOriginalWidth / 2;
                this.mLanguageViewLayout.setBackgroundResource(R.drawable.language_background_half);
            } else {
                this.mLanguageViewLayout.setBackgroundResource(R.drawable.language_background);
            }
            languageLayout.leftMargin = (x + (this.mLanguageButton.getWidth() / 2)) - (UIsUtils.dipToPx(isSingleList ? 70.0f : 140.0f) / 2);
            languageLayout.topMargin = (y - this.mLanguageView.getMeasuredHeight()) - UIsUtils.dipToPx(8.0f);
            this.mLanguageView.setLayoutParams(languageLayout);
            this.mLanguageView.requestLayout();
            this.mLanguageViewLayout.requestLayout();
            return;
        }
        UIsUtils.showToast(this.mNoNetworkTip);
    }

    private void clickStreamBtn(int playLevel, boolean hasLevel, boolean shouldChange, String name) {
        if (!hasLevel) {
            return;
        }
        if ((playLevel != 5 || checkIfCanPlay1080p(true)) && !this.mActivity.getFlow().mIsDownloadFile) {
            changeStreamByUser();
            this.mTvSpreadView.setVisibility(4);
            hidePopup(0);
            this.m2KButton.setSelected(false);
            this.m4KButton.setSelected(false);
            setButtonSelect(this.mStreamSelectBox, false);
            if (shouldChange && this.mActivity.getFlow() != null) {
                this.mWaitingSwitchStreamName = name;
                this.mActivity.getFlow().mPlayLevel = playLevel;
                PreferencesManager.getInstance().setPlayLevel(playLevel);
                if (this.mActivity.getFlow().mShouldDeclineStream) {
                    BaseApplication.getInstance().setMemoryPlayLevel(playLevel);
                }
                setStreamButtonSelectColor(playLevel);
                showStreamTip();
                this.mActivity.getFlow().mSelectStream = playLevel;
                if (this.mActivity.getAlbumPlayFragment().isPlaying() || !this.mActivity.getFlow().enableDoublePlayer()) {
                    this.mActivity.getFlow().addPlayInfo("重走播放流程", "切换码流");
                    this.mActivity.getFlow().retryPlay(false, true);
                } else {
                    this.mShouldDoChangeStreamWhenPlayed = true;
                }
            }
            switch (playLevel) {
                case 0:
                    StatisticsUtils.staticticsInfoPost(this.mActivity, "0", "c675", "2006", 6, null, PageIdConstant.fullPlayPage, null, null, null, null, null);
                    return;
                case 1:
                    StatisticsUtils.staticticsInfoPost(this.mActivity, "0", "c675", "2005", 5, null, PageIdConstant.fullPlayPage, null, null, null, null, null);
                    return;
                case 2:
                    StatisticsUtils.staticticsInfoPost(this.mActivity, "0", "c675", "2004", 4, null, PageIdConstant.fullPlayPage, null, null, null, null, null);
                    return;
                case 3:
                    StatisticsUtils.staticticsInfoPost(this.mActivity, "0", "c675", "2003", 3, null, PageIdConstant.fullPlayPage, null, null, null, null, null);
                    return;
                case 5:
                    return;
                default:
                    return;
            }
        }
    }

    private void changeStreamByUser() {
        int currentState = NetworkUtils.getNetworkType();
        if (currentState == 2 || currentState == 3) {
            BaseApplication.sIsChangeStream = false;
        }
    }

    private void click4k() {
        this.m2KButton.setSelected(false);
        if (this.m4KButton.isSelected()) {
            this.mTvSpreadView.setVisibility(4);
            this.m4KButton.setSelected(false);
            delayHide();
        } else {
            this.mTvSpreadView.setVisibility(0);
            this.m4KButton.setSelected(true);
            setStreamTvSpread(DialogMsgConstantId.CONSTANT_100027, R.drawable.tv_spread_4k);
            this.mStreamIntroTextView.setText(TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_100094, R.string.spread_4k));
            this.mStreamSpreadMoreView.setText(TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_100096, R.string.know_more));
            removeHideHandler();
        }
        if (this.mActivity.getFlow() != null) {
            this.mActivity.getFlow().mSelectStream = R.id.full_4K_text;
        }
        StatisticsUtils.staticticsInfoPost(this.mActivity, "0", "c675", "2001", 1, null, PageIdConstant.fullPlayPage, null, null, null, null, null);
    }

    private void click2K() {
        this.m4KButton.setSelected(false);
        if (this.m2KButton.isSelected()) {
            this.mTvSpreadView.setVisibility(4);
            this.m2KButton.setSelected(false);
            delayHide();
        } else {
            this.mTvSpreadView.setVisibility(0);
            this.mStreamLogoImageView.setImageResource(R.drawable.tv_spread_1080p);
            setStreamTvSpread(DialogMsgConstantId.CONSTANT_100026, R.drawable.tv_spread_1080p);
            this.mStreamIntroTextView.setText(TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_100093, R.string.spread_1080p));
            this.mStreamSpreadMoreView.setText(TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_100095, R.string.know_more));
            this.m2KButton.setSelected(true);
            removeHideHandler();
        }
        if (this.mActivity.getFlow() != null) {
            this.mActivity.getFlow().mSelectStream = R.id.full_1080_text;
        }
        StatisticsUtils.staticticsInfoPost(this.mActivity, "0", "c675", "2002", 2, null, PageIdConstant.fullPlayPage, null, null, null, null, null);
    }

    private void setStreamTvSpread(String messageId, int imageResId) {
        String url = TipUtils.getTipMessage(messageId);
        if (TextUtils.isEmpty(url)) {
            ImageDownloader.getInstance().download(this.mStreamLogoImageView, url);
        } else {
            this.mStreamLogoImageView.setImageResource(imageResId);
        }
    }

    private void clickLeanMore() {
        hidePopup(0);
        String uri = "";
        String fl = "";
        if (this.m2KButton.isSelected()) {
            uri = this.mUriLinkString2K;
            fl = "c6752";
        } else if (this.m4KButton.isSelected()) {
            uri = this.mUriLinkString4K;
            fl = "c6751";
        } else {
            uri = this.mUriLinkStringOther;
        }
        StatisticsUtils.staticticsInfoPost(this.mActivity, "0", fl, null, 1, null, PageIdConstant.fullPlayPage, null, null, null, null, null);
        new LetvWebViewActivityConfig(this.mActivity).launch(uri, this.mActivity.getString(R.string.letv_shop));
    }

    public boolean clickBack() {
        if (!this.mActivity.mIsLandspace) {
            this.mActivity.getController().back();
            StatisticsUtils.staticticsInfoPost(this.mActivity, "0", "h22", "0005", 1, null, PageIdConstant.halpPlayPage, null, null, null, null, null);
        } else if (this.mMoreView.getVisibility() == 0) {
            this.mMoreView.hide();
            return true;
        } else {
            AlbumHalfExpandFragment expendFragment = this.mActivity.getHalfFragment().getExpendFragment();
            if (expendFragment != null && expendFragment.isOpened()) {
                expendFragment.close();
                return true;
            } else if (this.mActivity.getController().fullBack()) {
                return false;
            } else {
                changeToHalf(true);
                delayHide();
                this.mActivity.getController().half();
                StatisticsUtils.staticticsInfoPost(this.mActivity, "0", "c65", "0005", 1, null, PageIdConstant.fullPlayPage, null, null, null, null, null);
            }
        }
        this.mUserClickBackTime = StringUtils.timeClockString("yyyyMMdd_HH:mm:ss");
        this.mUserClickBackStartTime = System.currentTimeMillis();
        return false;
    }

    private void clickBlockComplain() {
        String cdeCode = CdeHelper.getInstance().getHelpNumber("");
        String content = TipUtils.getTipMessage("100017", R.string.complaintSuccess);
        if (!TextUtils.isEmpty(cdeCode)) {
            int length = cdeCode.length();
            if (length > 6) {
                content = "[" + cdeCode.substring(length - 6, length) + "]" + content;
            } else {
                content = "[" + cdeCode + "]" + content;
            }
        }
        content = content.replace("#", "\n");
        LogInfo.log("Emerson", "content = " + content);
        UIsUtils.showToast(content);
        setBlockBtnVisibile(false);
        LogInfo.log("zhuqiao", "卡顿投诉");
        StatisticsUtils.staticticsInfoPost(this.mActivity, "0", "c65", LetvErrorCode.DOWNLOAD_VIDEOFILE_FAILED, 2, null, PageIdConstant.fullPlayPage, null, null, null, null, null);
    }

    public void hidePopup(int exceptFor) {
        if (exceptFor != 2) {
            setButtonSelect(this.mStreamSelectBox, false);
            this.mStreamSelectLayout.setVisibility(8);
            this.mVideoShot.setClickable(true);
            this.mBarrageInputBtn.setClickable(true);
        }
        if (exceptFor != 4) {
            setButtonSelect(this.mLanguageButton, false);
            this.mLanguageView.setVisibility(8);
            this.mVideoShot.setClickable(true);
            this.mBarrageInputBtn.setClickable(true);
        }
        this.mInteractTipLayout.setVisibility(8);
    }

    private void hideTvSpreadView() {
        if (this.mTvSpreadView.getVisibility() == 0) {
            this.mTvSpreadView.setVisibility(4);
            this.m2KButton.setSelected(false);
            this.m4KButton.setSelected(false);
        }
        recoverFullPlayControllerHd();
    }

    private VideoShotShareInfoBean setValues(VideoShotShareInfoBean bean) {
        if (this.mActivity.getFlow() == null) {
            return null;
        }
        AlbumPlayFlow flow = this.mActivity.getFlow();
        AlbumInfo album = flow.mVideoBelongedAlbum;
        VideoBean vb = flow.mCurrentPlayingVideo;
        bean.mShotSource = 2;
        bean.mLiveTitle = "";
        bean.mVideoName = album != null ? album.nameCn : "";
        bean.aid = album != null ? album.pid : 0;
        bean.vid = vb.vid;
        bean.mAlbumInfo = album;
        bean.mVideoBean = vb;
        bean.mid = vb.mid;
        bean.mLiveLaunchMode = flow.mLaunchMode;
        return bean;
    }

    private void setVisibilityForEpisode(boolean show) {
        if (this.mActivity.getHalfFragment() != null) {
            boolean z;
            AlbumHalfFragment fragment = this.mActivity.getHalfFragment();
            if (show) {
                LogInfo.log("zhaosumin", " 剧集列表显示");
                if (!(this.mEpisodeVisibility || this.mActivity == null || this.mActivity.getPlayAdListener().getAdFragment().getIVideoStatusInformer() == null)) {
                    this.mEpisodeVisibility = true;
                    this.mActivity.getPlayAdListener().getAdFragment().getIVideoStatusInformer().onEpisodeVisible(true);
                }
                if (this.mActivity.mIsLebox) {
                    fragment.getLeboxBeanListAndConvertVideoBeanList();
                    if (((ArrayList) fragment.leboxPairBean.first).size() > 0) {
                        int cid = fragment.getChannelId();
                        if (cid == 5 || cid == 2) {
                            fragment.expandMore(fragment.mGridController);
                        } else {
                            fragment.expandMore(fragment.mListController);
                        }
                    } else {
                        fragment.openExpandError(TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_LEBOX_DATA_ERROR, R.string.lebox_data_error));
                    }
                } else if (fragment.getAlbumCardList() != null) {
                    AlbumCardList cardList = fragment.getAlbumCardList();
                    if (!AlbumPageCard.isPostiveVideo(cardList, null) && cardList.mIsAlbum) {
                        fragment.expandMore(fragment.mRelateController);
                    } else if (cardList.videoList.style == 1) {
                        fragment.expandMore(fragment.mGridController);
                    } else if (cardList.videoList.style == 2) {
                        fragment.expandMore(fragment.mListController);
                    } else {
                        fragment.expandMore(fragment.mPeriodsController);
                    }
                } else {
                    fragment.openExpandError();
                }
                this.mMoreView.hide();
            } else {
                if (!(!this.mEpisodeVisibility || this.mActivity == null || this.mActivity.getPlayAdListener().getAdFragment().getIVideoStatusInformer() == null)) {
                    LogInfo.log("zhaosumin", " 剧集列表隐藏");
                    this.mActivity.getPlayAdListener().getAdFragment().getIVideoStatusInformer().onEpisodeVisible(false);
                    this.mEpisodeVisibility = false;
                }
                fragment.closeExpand();
            }
            if (show) {
                z = false;
            } else {
                z = true;
            }
            onRightPopHiddenChanged(z);
        }
    }

    private void onRightPopHiddenChanged(boolean isHidden) {
        int i = 8;
        LogInfo.log("ZSM onRightPopHiddenChanged isHidden == " + isHidden + "  mActivity.mIsPlayingDlna == " + this.mActivity.mIsPlayingDlna);
        if (!(this.mActivity.getGestureController() == null || this.mActivity.mIsPlayingDlna)) {
            this.mActivity.getGestureController().setGestureUseful(isHidden);
        }
        if (this.mActivity.mIsPlayingDlna) {
            LogInfo.log("ZSM 投屏显示底部栏");
            setVisibileForBottomFrame(true);
            this.mTopRadioGroup.setVisibility(8);
            this.mTopRightView.setVisibility(8);
        } else if (this.mPlayVideoSuccess || mIsSwitch) {
            int i2;
            if (this.mIsPlayNext) {
                setVisibileForBottomFrame(false);
            } else if (mIsSwitch && this.mActivity.getFlow().enableDoublePlayer()) {
                setVisibileForBottomFrame(true);
            } else {
                setVisibileForBottomFrame(isHidden);
            }
            View view = this.mTopRadioGroup;
            if (this.mActivity.mIsLandspace && isHidden) {
                i2 = 0;
            } else {
                i2 = 8;
            }
            view.setVisibility(i2);
            View view2 = this.mTopRightView;
            if (this.mActivity.mIsLandspace && isHidden) {
                i = 0;
            }
            view2.setVisibility(i);
        } else {
            setVisibileForBottomFrame(false);
            this.mTopRadioGroup.setVisibility(8);
            this.mTopRightView.setVisibility(8);
        }
        LogInfo.log("jc666", "button is show=" + isHidden);
        hidePopup(0);
        if (isHidden) {
            delayHide();
            return;
        }
        this.mWatchingFocusLayout.clearAnimationAndHide();
        removeHideHandler();
    }

    protected void setVisibileForBottomFrame(boolean show) {
        super.setVisibileForBottomFrame(show);
        setVisibilityForVRBtn(show);
    }

    public void playAnotherVideo(boolean isRetry) {
        super.playAnotherVideo(isRetry);
        this.mHasShowInteract = false;
    }

    private void blockSuggest(String msg) {
        LogInfo.log("zhuqiao", "卡顿提示:" + msg);
        if (!this.mActivity.mIsPlayingNonCopyright && !this.mIsShowingBlockSuggest && System.currentTimeMillis() - this.mLastProgressTime >= DanmakuFactory.MIN_DANMAKU_DURATION) {
            if (this.mActivity.getLoadListener() == null || !this.mActivity.getLoadListener().isDownLoading()) {
                this.mIsShowingBlockSuggest = true;
                if (UIsUtils.isLandscape(this.mActivity)) {
                    setVisibility(0);
                }
                checkStreamTipMargin();
                this.mActivity.getViewById(R.id.change_stream_name).setVisibility(8);
                this.mSwitchingTipTextView.setText(msg);
                this.mSwitchingTipTextView.setVisibility(0);
                this.mStreamTipStreamTextView.setVisibility(0);
                this.mStreamTipStreamTextView.setText("  " + this.mActivity.getString(R.string.caton_complaint));
                this.mStreamCancelView.setVisibility(0);
                this.mSwitchingTipView.setVisibility(0);
                this.mStream1080pTipView.setVisibility(8);
                this.mHandler.removeMessages(FFMpegPlayer.PREPARE_VIDEO_NODECODER_ERROR);
                this.mStreamTipStreamTextView.setOnClickListener(new 16(this));
                this.mHandler.postDelayed(new 17(this), 5000);
            }
        }
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility != 0) {
            this.mWatchingFocusLayout.clearAnimationAndHide();
            hidePopup(0);
        } else if (this.mSeekBar.getVisibility() == 0) {
            drawWatchFocus();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.mShareProtocol != null) {
            this.mShareProtocol.onDestory();
        }
        this.mShareProtocol = null;
        if (this.mDlnaProtocol != null) {
            this.mDlnaProtocol.protocolDestory();
        }
        this.mDlnaProtocol = null;
    }

    public void saveLanguageSettings() {
        LogInfo.log("wuxinrong", "保存<语言>参数...");
        AudioTrackManager audioTrackManager = AudioTrackManager.getInstance();
        SubtitleInfoManager subtitleInfoManager = SubtitleInfoManager.getInstance();
        if (audioTrackManager.getCodeList() == null && subtitleInfoManager.getCodeList() == null) {
            LogInfo.log("wuxinrong", "不需要保存<语言>参数");
            return;
        }
        LanguageSettings settings = this.mActivity.getFlow().mLanguageSettings;
        if (settings == null) {
            settings = new LanguageSettings();
        }
        settings.pid = this.mActivity.getFlow().mCurrentPlayingVideo.pid > 0 ? this.mActivity.getFlow().mCurrentPlayingVideo.pid : this.mActivity.getFlow().mCurrentPlayingVideo.vid;
        settings.audioTrackCode = AudioTrackManager.getInstance().getCode();
        settings.subtitleCode = SubtitleInfoManager.getInstance().getCode();
        LogInfo.log("wuxinrong", "保存<语言>参数 pid = " + settings.pid + " 音轨code = " + settings.audioTrackCode + " 字幕code = " + settings.subtitleCode);
        DBManager.getInstance().getLanguageSettingsTrace().save(settings);
    }
}
