package com.letv.android.client.album.controller;

import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.letv.android.client.album.AlbumPlayActivity;
import com.letv.android.client.album.R;
import com.letv.android.client.album.controller.AlbumErrorTopController.AlbumErrorFlag;
import com.letv.android.client.album.half.AlbumHalfFragment;
import com.letv.android.client.commonlib.config.LetvLoginActivityConfig;
import com.letv.android.client.commonlib.config.LetvVipDialogActivityConfig;
import com.letv.business.flow.album.AlbumPlayFlow;
import com.letv.business.flow.album.listener.AlbumVipTrailListener;
import com.letv.business.flow.album.listener.AlbumVipTrailListener.VipTrailErrorState;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.LetvUrlMaker;
import com.letv.core.bean.AlbumPayInfoBean;
import com.letv.core.constant.PlayConstant.VideoType;
import com.letv.core.db.PreferencesManager;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.TicketUseParser;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.LetvErrorCode;
import com.letv.datastatistics.constant.PageIdConstant;

public class AlbumPlayVipTrailController implements OnClickListener, AlbumVipTrailListener {
    protected static final int START_VIEW_DURATION = 7000;
    protected AlbumPlayActivity mActivity;
    protected boolean mCanPlay = true;
    protected Button mEndBuyBtn;
    protected View mEndBuyVipView;
    protected View mEndLoginBtn;
    protected View mEndTicketNoneView;
    protected TextView mEndTipTextView;
    protected TextView mEndUseTicketBtn;
    protected View mEndUseTicketByVipFrame;
    protected TextView mEndUseTicketTextView;
    protected TextView mEndUseTicketTipView;
    protected TextView mEndUseTicketTitleView;
    protected View mEndUseTicketView;
    protected View mEndView;
    protected VipTrailErrorState mErrState = VipTrailErrorState.INIT;
    protected TextView mForbiddenMsgView;
    protected TextView mForbiddenTitleView;
    protected View mForbiddenView;
    protected View mFrame;
    protected Handler mHandler = new Handler();
    protected boolean mIsControllerVisible = true;
    protected boolean mIsVip;
    protected boolean mIsVipTrailEnd = false;
    protected AlbumPayInfoBean mPayInfo;
    protected ShowState mStartBuyViewShowState = ShowState.NOT_SHOWED;
    protected View mStartBuyVipBtn;
    protected View mStartBuyVipBtnsView;
    protected View mStartBuyVipView;
    protected View mStartLoginBtn;
    protected ShowState mStartNoTicketViewShowState = ShowState.NOT_SHOWED;
    protected View mStartTicketNoneView;
    protected ShowState mStartTicketViewShowState = ShowState.NOT_SHOWED;
    protected View mStartUseGeneralTicketView;
    protected View mStartUseTicketBtn;
    protected View mStartUseTicketBtnsView;
    protected View mStartUseTicketView;
    protected View mStartUserTicketBtn;
    protected View mStartView;
    protected View mUseTicketReultView;
    protected TextView mUseTicketSuccessTipView;
    private TextView mVipTipStartHeader;
    private int mVipTrailTime;

    public AlbumPlayVipTrailController(AlbumPlayActivity activity) {
        this.mActivity = activity;
        init();
    }

    protected void init() {
        this.mFrame = this.mActivity.findViewById(R.id.vip_trail_frame);
        this.mStartView = this.mActivity.findViewById(R.id.vip_trail_start);
        this.mEndView = this.mActivity.findViewById(R.id.vip_trail_end);
        this.mUseTicketReultView = this.mActivity.findViewById(R.id.vip_trail_use_ticket_result);
        this.mStartBuyVipView = this.mActivity.findViewById(R.id.vip_trail_start_buy_vip);
        this.mStartBuyVipBtnsView = this.mActivity.findViewById(R.id.vip_trail_start_buy_vip_btns);
        this.mStartUseTicketView = this.mActivity.findViewById(R.id.vip_trail_start_use_ticket);
        this.mStartUseGeneralTicketView = this.mActivity.findViewById(R.id.vip_trail_start_use_general_ticket);
        this.mStartUseTicketBtnsView = this.mActivity.findViewById(R.id.vip_trail_start_use_ticket_btns);
        this.mStartTicketNoneView = this.mActivity.findViewById(R.id.vip_trail_start_ticket_none);
        this.mStartUserTicketBtn = this.mActivity.findViewById(R.id.vip_trail_start_use_ticket_btns_btn);
        this.mEndBuyVipView = this.mActivity.findViewById(R.id.vip_trail_end_buy_vip);
        this.mEndUseTicketTitleView = (TextView) this.mActivity.findViewById(R.id.vip_trail_end_use_ticket_text1);
        this.mEndUseTicketView = this.mActivity.findViewById(R.id.vip_trail_end_use_ticket);
        this.mEndTicketNoneView = this.mActivity.findViewById(R.id.vip_trail_end_ticket_none);
        this.mEndUseTicketTextView = (TextView) this.mActivity.findViewById(R.id.vip_trail_end_use_ticket_text2);
        this.mEndUseTicketTipView = (TextView) this.mActivity.findViewById(R.id.vip_trail_end_use_ticket_text3);
        this.mUseTicketSuccessTipView = (TextView) this.mActivity.findViewById(R.id.vip_trail_use_ticket_result_success_msg);
        this.mStartBuyVipBtn = this.mActivity.findViewById(R.id.vip_trail_start_buy_vip_button1);
        this.mStartLoginBtn = this.mActivity.findViewById(R.id.vip_trail_start_buy_vip_button2);
        this.mEndBuyBtn = (Button) this.mActivity.findViewById(R.id.vip_trail_end_buy_vip_btn);
        this.mEndLoginBtn = this.mActivity.findViewById(R.id.vip_trail_end_buy_vip_login_btn);
        this.mStartUseTicketBtn = this.mActivity.findViewById(R.id.vip_trail_start_use_ticket_btn);
        this.mEndUseTicketBtn = (TextView) this.mActivity.findViewById(R.id.vip_trail_end_use_ticket_btn);
        this.mEndUseTicketByVipFrame = this.mActivity.findViewById(R.id.vip_trail_end_use_ticket_frame);
        this.mForbiddenView = this.mActivity.findViewById(R.id.vip_trail_forbidden);
        this.mForbiddenTitleView = (TextView) this.mActivity.findViewById(R.id.vip_trail_forbidden_title);
        this.mForbiddenMsgView = (TextView) this.mActivity.findViewById(R.id.vip_trail_forbidden_msg);
        this.mEndTipTextView = (TextView) this.mActivity.findViewById(R.id.vip_trail_end_buy_vip_text1);
        this.mVipTipStartHeader = (TextView) this.mActivity.findViewById(R.id.vip_trail_start_text_header);
        this.mForbiddenTitleView.setText(TipUtils.getTipMessage("100078", R.string.vip_trail_forbidden_title));
        this.mForbiddenMsgView.setText(TipUtils.getTipMessage("100079", R.string.vip_trail_forbidden_desc));
        ((TextView) this.mActivity.findViewById(R.id.vip_trail_end_ticket_none_text1)).setText(TipUtils.getTipMessage("90053", R.string.vip_trail_end_has_no_ticket));
        ((TextView) this.mActivity.findViewById(R.id.vip_trail_end_ticket_none_text2)).setText(TipUtils.getTipMessage("90054", R.string.vip_trail_by_from_pc));
        ((TextView) this.mActivity.findViewById(R.id.vip_trail_end_ticket_none_text3)).setText(TipUtils.getTipMessage("90055", R.string.vip_trail_vip_tip));
        initClick();
    }

    protected void initClick() {
        this.mStartBuyVipBtn.setOnClickListener(this);
        this.mStartLoginBtn.setOnClickListener(this);
        this.mEndBuyBtn.setOnClickListener(this);
        this.mEndLoginBtn.setOnClickListener(this);
        this.mStartUseTicketBtn.setOnClickListener(this);
        this.mEndUseTicketBtn.setOnClickListener(this);
        this.mEndView.setOnClickListener(this);
        this.mForbiddenView.setOnClickListener(this);
        this.mStartUserTicketBtn.setOnClickListener(this);
        this.mActivity.findViewById(R.id.vip_trail_start_buy_vip_btns_button1).setOnClickListener(this);
        this.mActivity.findViewById(R.id.vip_trail_start_buy_vip_btns_button2).setOnClickListener(this);
        this.mActivity.findViewById(R.id.vip_trail_start_use_ticket_btns).setOnClickListener(this);
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.vip_trail_start_buy_vip_button1 || id == R.id.vip_trail_start_buy_vip_btns_button1) {
            onTrailBuyVip(true, false);
        }
        if (id == R.id.vip_trail_start_buy_vip_button2 || id == R.id.vip_trail_start_buy_vip_btns_button2) {
            onTrailBuyVip(false, false);
        }
        if (id == R.id.vip_trail_end_buy_vip_btn) {
            onTrailBuyVip(true, true);
        }
        if (id == R.id.vip_trail_end_buy_vip_login_btn) {
            onTrailBuyVip(false, true);
        }
        if (id == R.id.vip_trail_start_use_ticket_btn || id == R.id.vip_trail_start_use_ticket_btns || id == R.id.vip_trail_start_use_ticket_btns_btn || id == R.id.vip_trail_end_use_ticket_btn) {
            requestUseTicket();
        }
        if (id != R.id.vip_trail_end && id != R.id.vip_trail_forbidden) {
        }
    }

    protected void onTrailBuyVip(boolean isPay, boolean isTrailOver) {
        if (this.mActivity.getFlow() != null) {
            AlbumPlayFlow flow = this.mActivity.getFlow();
            if (isPay) {
                if (this.mActivity.getController() != null) {
                    this.mActivity.getController().setIsOpenVip(true);
                }
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new LetvVipDialogActivityConfig(this.mActivity).create(this.mActivity.getVideoController() == null ? "" : this.mActivity.getVideoController().getTitle())));
            } else {
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new LetvLoginActivityConfig(this.mActivity).create(1, 10001)));
            }
            if (flow.mCurrentPlayingVideo != null) {
                StatisticsUtils.statisticsActionInfo(this.mActivity, UIsUtils.isLandscape(this.mActivity) ? PageIdConstant.fullPlayPage : PageIdConstant.halpPlayPage, "0", "c62", isPay ? "0022" : LetvErrorCode.LOCALFILE_PLAY_ERROR, -1, null, String.valueOf(flow.mCurrentPlayingVideo.cid), String.valueOf(flow.mCurrentPlayingVideo.pid), String.valueOf(flow.mCurrentPlayingVideo.vid), String.valueOf(flow.mCurrentPlayingVideo.zid), null);
            }
        }
    }

    protected void requestUseTicket() {
        if (this.mActivity.getFlow() != null && this.mActivity.getLoadListener() != null && this.mActivity.getHalfFragment() != null) {
            AlbumPlayFlow flow = this.mActivity.getFlow();
            AlbumHalfFragment fragment = this.mActivity.getHalfFragment();
            this.mActivity.getLoadListener().loading();
            String name = "";
            String pid = "";
            String type = "";
            String id = "";
            if (fragment.getAlbumInfo() != null) {
                name = fragment.getAlbumInfo().nameCn;
                pid = fragment.getAlbumInfo().pid + "";
            } else if (fragment.getCurrPlayingVideo() != null) {
                name = fragment.getCurrPlayingVideo().nameCn;
                pid = fragment.getCurrPlayingVideo().pid + "";
            }
            if (this.mPayInfo != null) {
                type = this.mPayInfo.ticketType + "";
                if (this.mPayInfo.ticketType != 0) {
                    id = this.mPayInfo.id + "";
                }
            }
            flow.addPlayInfo("使用观影券", LetvUrlMaker.getUseTicketUrl(name, pid, type, id));
            new LetvRequest().setUrl(LetvUrlMaker.getUseTicketUrl(name, pid, type, id)).setCache(new VolleyNoCache()).setRequestType(RequestManner.NETWORK_ONLY).setParser(new TicketUseParser()).setCallback(new 1(this, flow)).add();
        }
    }

    public void setPayInfo(AlbumPayInfoBean payInfo) {
        this.mPayInfo = payInfo;
        this.mIsVip = PreferencesManager.getInstance().isVip();
        this.mVipTrailTime = payInfo.tryLookTime;
        LogInfo.log("zhaosumin", "setPayInfo mVipTrailTime = " + this.mVipTrailTime);
        if (this.mVipTrailTime >= 60) {
            this.mVipTipStartHeader.setText(this.mActivity.getString(R.string.vip_trail_start_text_header, new Object[]{Integer.valueOf(this.mVipTrailTime / 60)}));
        } else {
            this.mVipTipStartHeader.setText(this.mActivity.getString(R.string.vip_trail_start_text_header_second, new Object[]{Integer.valueOf(this.mVipTrailTime)}));
        }
        ((TextView) this.mActivity.findViewById(R.id.vip_trail_end_buy_vip_text2)).setText(this.mActivity.getString(R.string.vip_trail_ticket_use_success, new Object[]{this.mPayInfo.validDays}));
        this.mEndUseTicketTipView.setText(this.mActivity.getString(R.string.vip_trail_ticket_use_success, new Object[]{this.mPayInfo.validDays}));
        this.mUseTicketSuccessTipView.setText(this.mActivity.getString(R.string.vip_trail_ticket_use_success, new Object[]{this.mPayInfo.validDays}));
    }

    protected void vipTrailStart() {
        this.mIsVipTrailEnd = false;
        if (!this.mActivity.getVideoController().is1080pStreamVisible()) {
            switch (7.$SwitchMap$com$letv$business$flow$album$listener$AlbumVipTrailListener$VipTrailErrorState[this.mErrState.ordinal()]) {
                case 1:
                    showStartBuyVip(true);
                    return;
                case 2:
                    showStartBuyVip(false);
                    return;
                case 3:
                    showStartByTicket(true);
                    return;
                case 4:
                    showStartByTicket(false);
                    return;
                default:
                    return;
            }
        }
    }

    public void setStateForStartByHasLogined(boolean hasLogined) {
        this.mErrState = hasLogined ? VipTrailErrorState.START_WITH_LOGIN : VipTrailErrorState.START_WITH_NOT_LOGIN;
        showStartBuyVip(hasLogined);
    }

    public void setStateForStartByHasNoTicket() {
        this.mErrState = VipTrailErrorState.START_WITH_NOT_TICKET;
        showStartByTicket(false);
    }

    public void setStateForStartByHasTicket(boolean isVip) {
        this.mErrState = VipTrailErrorState.START_WITH_TICKET;
        showStartByTicket(true);
    }

    protected void showStartBuyVip(boolean hasLogined) {
        int i;
        this.mActivity.findViewById(R.id.vip_trail_start_buy_vip_text2).setVisibility(hasLogined ? 8 : 0);
        View view = this.mStartLoginBtn;
        if (hasLogined) {
            i = 8;
        } else {
            i = 0;
        }
        view.setVisibility(i);
        view = this.mActivity.findViewById(R.id.vip_trail_start_buy_vip_btns_button2);
        if (hasLogined) {
            i = 8;
        } else {
            i = 0;
        }
        view.setVisibility(i);
        vipTrailStartInit();
        if (this.mStartBuyViewShowState != ShowState.SHOWING) {
            if (this.mStartBuyViewShowState == ShowState.HAS_SHOWED) {
                this.mStartBuyVipView.setVisibility(8);
                this.mStartBuyVipBtnsView.setVisibility(0);
                return;
            }
            this.mStartBuyVipView.setVisibility(0);
            this.mStartUseTicketView.setVisibility(8);
            this.mStartUseGeneralTicketView.setVisibility(8);
            this.mStartUseTicketBtnsView.setVisibility(8);
            this.mStartTicketNoneView.setVisibility(8);
            if (!this.mIsControllerVisible) {
                this.mStartBuyViewShowState = ShowState.SHOWING;
                this.mHandler.postDelayed(new 2(this), 7000);
            }
        }
    }

    protected void showStartByTicket(boolean hasTicket) {
        if (hasTicket) {
            if (this.mStartTicketViewShowState != ShowState.SHOWING) {
                if (this.mStartTicketViewShowState == ShowState.HAS_SHOWED) {
                    showRightWhenHasTicket();
                    return;
                }
                vipTrailStartInit();
                this.mStartBuyVipView.setVisibility(8);
                this.mStartBuyVipBtnsView.setVisibility(8);
                if (this.mPayInfo != null) {
                    if (this.mPayInfo.ticketType == 1) {
                        this.mStartUseTicketView.setVisibility(8);
                        this.mStartUseGeneralTicketView.setVisibility(0);
                    } else {
                        this.mStartUseTicketView.setVisibility(0);
                        this.mStartUseGeneralTicketView.setVisibility(8);
                    }
                }
                this.mStartTicketNoneView.setVisibility(8);
                if (!this.mIsControllerVisible) {
                    this.mStartTicketViewShowState = ShowState.SHOWING;
                    this.mHandler.postDelayed(new 3(this), 7000);
                }
            }
        } else if (this.mStartNoTicketViewShowState != ShowState.SHOWING && this.mStartNoTicketViewShowState != ShowState.HAS_SHOWED) {
            vipTrailStartInit();
            this.mStartNoTicketViewShowState = ShowState.SHOWING;
            this.mStartBuyVipView.setVisibility(8);
            this.mStartBuyVipBtnsView.setVisibility(8);
            this.mStartUseTicketView.setVisibility(8);
            this.mStartUseGeneralTicketView.setVisibility(8);
            this.mStartTicketNoneView.setVisibility(0);
            this.mHandler.postDelayed(new 4(this), 7000);
        }
    }

    protected void showRightWhenHasTicket() {
        this.mStartUseTicketView.setVisibility(8);
        this.mStartUseTicketBtnsView.setVisibility(0);
        this.mActivity.findViewById(R.id.vip_trail_start_open_vip_btns_btn).setVisibility(8);
    }

    protected void vipTrailEnd() {
        this.mIsVipTrailEnd = true;
        switch (7.$SwitchMap$com$letv$business$flow$album$listener$AlbumVipTrailListener$VipTrailErrorState[this.mErrState.ordinal()]) {
            case 1:
            case 5:
                vipTrailEndByHasLogined(true);
                return;
            case 2:
            case 6:
                vipTrailEndByHasLogined(false);
                return;
            case 3:
            case 7:
                vipTrailEndByHasTicket(true);
                return;
            case 4:
            case 8:
                vipTrailEndByHasTicket(false);
                return;
            default:
                return;
        }
    }

    public void vipTrailEndByHasLogined(boolean hasLogined) {
        int i;
        int i2 = 8;
        this.mErrState = hasLogined ? VipTrailErrorState.END_WITH_LOGIN : VipTrailErrorState.END_WITH_NOT_LOGIN;
        vipTrailEndInit();
        this.mEndBuyVipView.setVisibility(0);
        this.mEndUseTicketView.setVisibility(8);
        this.mEndTicketNoneView.setVisibility(8);
        if (isTv()) {
            this.mEndTipTextView.setText(TipUtils.getTipMessage("700058", R.string.vip_trail_end_tip_tv));
        } else {
            this.mEndTipTextView.setText(TipUtils.getTipMessage("700032", R.string.vip_trail_end_tip));
        }
        View view = this.mEndLoginBtn;
        if (hasLogined) {
            i = 8;
        } else {
            i = 0;
        }
        view.setVisibility(i);
        View findViewById = this.mActivity.findViewById(R.id.vip_trail_end_buy_vip_login_frame);
        if (!hasLogined) {
            i2 = 0;
        }
        findViewById.setVisibility(i2);
        LogInfo.log("zhuqiao", "试看结束提示浮层曝光--" + (hasLogined ? "用户已登陆" : "用户未登陆"));
        StatisticsUtils.staticticsInfoPost(this.mActivity, "19", "c62", null, -1, null, UIsUtils.isLandscape(this.mActivity) ? PageIdConstant.fullPlayPage : PageIdConstant.halpPlayPage, null, null, null, null, null);
    }

    public void vipTrailEndByHasTicket(boolean hasTicket) {
        int i;
        this.mErrState = hasTicket ? VipTrailErrorState.END_WITH_TICKET : VipTrailErrorState.END_WITH_NOT_TICKET;
        vipTrailEndInit();
        this.mEndBuyVipView.setVisibility(8);
        View view = this.mEndUseTicketView;
        if (hasTicket) {
            i = 0;
        } else {
            i = 8;
        }
        view.setVisibility(i);
        view = this.mEndTicketNoneView;
        if (hasTicket) {
            i = 8;
        } else {
            i = 0;
        }
        view.setVisibility(i);
        if (hasTicket && this.mPayInfo != null) {
            this.mEndUseTicketTitleView.setText(R.string.vip_trail_has_be_member);
            SpannableStringBuilder style = new SpannableStringBuilder(String.format(this.mActivity.getString(R.string.vip_trail_over_use_ticket_text2), new Object[]{Integer.valueOf(this.mPayInfo.vipTicketSize)}));
            style.setSpan(new ForegroundColorSpan(this.mActivity.getResources().getColor(R.color.letv_color_ffec8e1f)), 8, String.valueOf(this.mPayInfo.vipTicketSize).length() + 8, 33);
            this.mEndUseTicketTextView.setText(style);
            this.mEndUseTicketTipView.setText(this.mActivity.getString(R.string.vip_trail_ticket_use_success, new Object[]{this.mPayInfo.validDays}));
            this.mUseTicketSuccessTipView.setText(this.mActivity.getString(R.string.vip_trail_ticket_use_success, new Object[]{this.mPayInfo.validDays}));
            this.mEndUseTicketBtn.setText(R.string.vip_trail_use_ticket);
            this.mEndUseTicketTipView.setVisibility(0);
            this.mEndUseTicketByVipFrame.setVisibility(8);
        }
    }

    public void vipTrailBackTitleScreenProcess(String title, boolean isDolby) {
        AlbumErrorTopController topController = this.mActivity.getErrorTopController();
        if (topController != null) {
            topController.show(AlbumErrorFlag.VipError);
            topController.setTitle(title);
            if (isDolby) {
                this.mActivity.getErrorTopController().setVisibilityForSwitchView(8);
            }
        }
    }

    public void vipTrailUseTicketSuccess() {
        this.mErrState = VipTrailErrorState.INIT;
        this.mStartView.setVisibility(8);
        this.mEndView.setVisibility(8);
        this.mForbiddenView.setVisibility(8);
        this.mUseTicketReultView.setVisibility(0);
        this.mHandler.postDelayed(new 5(this), 3000);
    }

    public boolean isError() {
        if (this.mEndUseTicketTextView == null) {
            return false;
        }
        if (this.mEndBuyVipView.getVisibility() == 0 || this.mEndUseTicketTextView.getVisibility() == 0 || this.mEndTicketNoneView.getVisibility() == 0) {
            return true;
        }
        return false;
    }

    public void hide() {
        if (isShow()) {
            this.mStartView.setVisibility(8);
            this.mEndView.setVisibility(8);
            this.mUseTicketReultView.setVisibility(8);
            this.mForbiddenView.setVisibility(8);
        }
    }

    public void finish() {
        this.mIsVipTrailEnd = false;
        this.mErrState = VipTrailErrorState.INIT;
        hide();
        this.mActivity.getErrorTopController().hide(AlbumErrorFlag.VipError);
        this.mStartBuyViewShowState = ShowState.NOT_SHOWED;
        this.mStartTicketViewShowState = ShowState.NOT_SHOWED;
        this.mStartNoTicketViewShowState = ShowState.NOT_SHOWED;
    }

    public VipTrailErrorState getErrState() {
        return this.mErrState;
    }

    public boolean isVipTrailEnd() {
        return this.mIsVipTrailEnd;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean checkVipTrailIsStateEnd() {
        /*
        r10 = this;
        r1 = 1;
        r3 = 0;
        r10.mCanPlay = r1;
        r6 = r10.mActivity;
        r6 = r6.getFlow();
        if (r6 == 0) goto L_0x0024;
    L_0x000c:
        r6 = r10.mActivity;
        r6 = r6.getLoadListener();
        if (r6 == 0) goto L_0x0024;
    L_0x0014:
        r6 = r10.mActivity;
        r6 = r6.getAlbumPlayFragment();
        if (r6 == 0) goto L_0x0024;
    L_0x001c:
        r6 = r10.mActivity;
        r6 = r6.getController();
        if (r6 != 0) goto L_0x0025;
    L_0x0024:
        return r3;
    L_0x0025:
        r6 = r10.mActivity;
        r0 = r6.getFlow();
        r2 = r0.mPlayInfo;
        r4 = r2.currTime;
        r6 = r0.mIsSkip;
        if (r6 == 0) goto L_0x003e;
    L_0x0033:
        r6 = r2.beginTime;
        r8 = 0;
        r6 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
        if (r6 <= 0) goto L_0x003e;
    L_0x003b:
        r6 = r2.beginTime;
        r4 = r4 - r6;
    L_0x003e:
        r6 = r10.isVipVideo();
        if (r6 == 0) goto L_0x0067;
    L_0x0044:
        r6 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r8 = r4 / r6;
        r6 = r10.mVipTrailTime;
        r7 = 60;
        if (r6 < r7) goto L_0x0064;
    L_0x004e:
        r6 = r10.mVipTrailTime;
        r6 = r6 / 60;
        r6 = r6 * 60;
    L_0x0054:
        r6 = (long) r6;
        r6 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1));
        if (r6 <= 0) goto L_0x0067;
    L_0x0059:
        r6 = r10.mHandler;
        r7 = new com.letv.android.client.album.controller.AlbumPlayVipTrailController$6;
        r7.<init>(r10, r1);
        r6.post(r7);
        goto L_0x0024;
    L_0x0064:
        r6 = r10.mVipTrailTime;
        goto L_0x0054;
    L_0x0067:
        r1 = r3;
        goto L_0x0059;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.letv.android.client.album.controller.AlbumPlayVipTrailController.checkVipTrailIsStateEnd():boolean");
    }

    private void doEnd(boolean isEnd) {
        boolean z = true;
        AlbumPlayFlow flow = this.mActivity.getFlow();
        this.mActivity.getVideoController().updateWindowPlayerButtonState();
        this.mActivity.getLoadListener().finish();
        if (isEnd) {
            this.mActivity.getController().pause(false);
            this.mActivity.getAlbumPlayFragment().stopHandlerTime();
            this.mActivity.getVideoController().setVisibility(8);
            if (this.mErrState == VipTrailErrorState.FORBIDDEN) {
                showForbiddenView();
            } else {
                vipTrailEnd();
            }
            setVisibileWithController(true);
            String str = flow.mCurrentPlayingVideo != null ? flow.mCurrentPlayingVideo.nameCn : "";
            if (flow.mVideoType != VideoType.Dolby) {
                z = false;
            }
            vipTrailBackTitleScreenProcess(str, z);
            this.mCanPlay = false;
            return;
        }
        vipTrailStart();
    }

    public boolean isVipVideo() {
        return this.mErrState != VipTrailErrorState.INIT;
    }

    public void setVisibileWithController(boolean show) {
        if (this.mCanPlay) {
            this.mFrame.setVisibility(show ? 0 : 8);
        }
    }

    public void forbidden() {
        this.mErrState = VipTrailErrorState.FORBIDDEN;
    }

    protected void showForbiddenView() {
        this.mStartView.setVisibility(8);
        this.mEndView.setVisibility(8);
        this.mUseTicketReultView.setVisibility(8);
        this.mForbiddenView.setVisibility(0);
    }

    public void setIsControllerVisible(boolean isVisible) {
        this.mIsControllerVisible = isVisible;
        this.mFrame.setVisibility(isVisible ? 8 : 0);
        if (!this.mIsControllerVisible) {
            vipTrailStart();
        }
    }

    protected void vipTrailStartInit() {
        this.mStartView.setVisibility(0);
        this.mEndView.setVisibility(8);
        this.mUseTicketReultView.setVisibility(8);
        this.mForbiddenView.setVisibility(8);
    }

    protected void vipTrailEndInit() {
        this.mStartView.setVisibility(8);
        this.mEndView.setVisibility(0);
        this.mUseTicketReultView.setVisibility(8);
        this.mForbiddenView.setVisibility(8);
    }

    protected boolean isShow() {
        return this.mStartView.getVisibility() == 0 || this.mEndView.getVisibility() == 0 || this.mUseTicketReultView.getVisibility() == 0 || this.mForbiddenView.getVisibility() == 0;
    }

    public boolean isTryLookPause() {
        return this.mErrState == VipTrailErrorState.END_WITH_LOGIN || this.mErrState == VipTrailErrorState.END_WITH_NOT_LOGIN || this.mErrState == VipTrailErrorState.END_WITH_NOT_TICKET || this.mErrState == VipTrailErrorState.END_WITH_TICKET;
    }

    protected boolean isTv() {
        return (this.mActivity.getFlow() == null || this.mActivity.getFlow().mCurrentPlayingVideo == null || this.mActivity.getFlow().mCurrentPlayingVideo.cid != 2) ? false : true;
    }
}
