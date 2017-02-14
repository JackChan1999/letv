package com.letv.android.client.commonlib.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.letv.android.client.commonlib.R;
import com.letv.business.flow.album.listener.LoadLayoutFragmentListener.IpErrorArea;
import com.letv.business.flow.live.LiveWatchAndBuyFlow.AddToCartResultStatus;
import com.letv.core.bean.TipMapBean.TipBean;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.LetvErrorCode;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.pp.utils.NetworkUtils;

public class PlayLoadLayout extends FrameLayout implements OnClickListener {
    private PlayLoadLayoutCallBack callBack;
    private View cannot_play;
    private TextView cannot_play_btn;
    private View complaint_success;
    private TextView complaint_success_button;
    private View demand_error;
    private TextView demand_error_btn;
    private View dlnaClose;
    private TextView dlnaTitle;
    private int errState = 0;
    private View ip_error;
    private TextView ip_error_call_text;
    private TextView ip_error_text;
    private View jump_error;
    private TextView jump_error_btn;
    private TextView jump_error_button_disable;
    private TextView jump_error_text;
    private View loading;
    private TextView loadingTxt;
    private View local_error;
    private Context mContext;
    private boolean mIsNonCopyright;
    private RelativeLayout mLayoutNetChange;
    private TextView mNetChangeContinue;
    private TextView mNetChangeTipTextView;
    private TextView mTxtSubmitInfo;
    private TextView no_play_tx;
    private View not_play;
    private View request_error;
    private TextView request_error_btn;
    private TextView request_error_text;
    private View vip_login_error;
    private TextView vip_login_error_btn;
    private View vip_not_login_error;
    private TextView vip_not_login_error_btn;

    public interface PlayLoadLayoutCallBack {
        void calledInError();

        void calledInFinish();

        void closeDlna(boolean z);

        void commitErrorInfo();

        void onDemandErr();

        void onJumpErr();

        void onNetChangeErr();

        void onPlayFailed();

        void onRequestErr();

        void onVipErr(boolean z);
    }

    public PlayLoadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayLoadLayout(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        inflate(context, R.layout.play_loading_layout, this);
        findView();
    }

    public int getErrState() {
        return this.errState;
    }

    public void setErrState(int errState) {
        this.errState = errState;
    }

    private void findView() {
        this.loading = findViewById(R.id.loading);
        this.not_play = findViewById(R.id.no_play_error);
        this.no_play_tx = (TextView) findViewById(R.id.no_play_error_tx);
        this.request_error = findViewById(R.id.request_error);
        this.request_error_text = (TextView) findViewById(R.id.request_error_text);
        this.request_error_btn = (TextView) findViewById(R.id.request_error_btn);
        this.cannot_play = findViewById(R.id.cannot_play);
        this.cannot_play_btn = (TextView) findViewById(R.id.cannot_play_btn);
        this.vip_not_login_error = findViewById(R.id.vip_not_login_error);
        this.vip_login_error = findViewById(R.id.vip_login_error);
        this.jump_error = findViewById(R.id.jump_error);
        this.ip_error = findViewById(R.id.ip_error);
        this.ip_error_text = (TextView) findViewById(R.id.ip_error_text);
        this.ip_error_call_text = (TextView) findViewById(R.id.ip_error_call_text);
        this.vip_not_login_error_btn = (TextView) findViewById(R.id.vip_not_login_error_button);
        this.vip_login_error_btn = (TextView) findViewById(R.id.vip_login_error_button);
        this.jump_error_text = (TextView) findViewById(R.id.jump_error_text);
        this.jump_error_btn = (TextView) findViewById(R.id.jump_error_button);
        this.jump_error_button_disable = (TextView) findViewById(R.id.jump_error_button_disable);
        this.demand_error = findViewById(R.id.demand_error);
        this.demand_error_btn = (TextView) findViewById(R.id.demand_error_button);
        this.local_error = findViewById(R.id.local_error_view);
        this.loadingTxt = (TextView) findViewById(R.id.loadingTxt);
        this.mTxtSubmitInfo = (TextView) findViewById(R.id.txt_commit_info);
        this.complaint_success = findViewById(R.id.complaint_success);
        this.complaint_success_button = (TextView) findViewById(R.id.complaint_success_button);
        this.mLayoutNetChange = (RelativeLayout) findViewById(R.id.album_net_frame);
        this.mNetChangeTipTextView = (TextView) findViewById(R.id.album_net_change_text1);
        this.mNetChangeContinue = (TextView) findViewById(R.id.album_net_change_continue);
        this.mTxtSubmitInfo.setOnClickListener(this);
        this.request_error_btn.setOnClickListener(this);
        this.cannot_play_btn.setOnClickListener(this);
        this.vip_not_login_error_btn.setOnClickListener(this);
        this.vip_login_error_btn.setOnClickListener(this);
        this.jump_error_btn.setOnClickListener(this);
        this.demand_error_btn.setOnClickListener(this);
        this.ip_error_call_text.setOnClickListener(this);
        this.complaint_success_button.setOnClickListener(this);
        this.mNetChangeContinue.setOnClickListener(this);
        resizeLoadingText(false);
        this.dlnaTitle = (TextView) findViewById(R.id.dlna_title);
        this.dlnaClose = findViewById(R.id.dlna_close);
        this.dlnaClose.setOnClickListener(this);
        ((TextView) findViewById(R.id.complaint_success_title)).setText(TipUtils.getTipMessage("100050", R.string.complaint_success));
    }

    public void loading() {
        loading(false);
    }

    public void loading(boolean showText) {
        this.loading.setVisibility(0);
        this.mLayoutNetChange.setVisibility(8);
        if (showText) {
            this.loadingTxt.setText(TipUtils.getTipMessage(DialogMsgConstantId.ON_LOADING, R.string.player_loading));
            this.loadingTxt.setVisibility(0);
        } else {
            this.loadingTxt.setVisibility(8);
        }
        setLoadingViewGone();
    }

    public void loadingVideo(String videoName) {
        this.loading.setVisibility(0);
        this.loadingTxt.setVisibility(0);
        if (TextUtils.isEmpty(videoName)) {
            this.loadingTxt.setText(TipUtils.getTipMessage(DialogMsgConstantId.ON_LOADING, R.string.player_loading));
        } else {
            this.loadingTxt.setText(TipUtils.getTipMessage("100071", R.string.will_play));
        }
        setLoadingViewGone();
    }

    public void loading(boolean showText, String loadingMsg, boolean showWillPlay) {
        this.loading.setVisibility(0);
        this.loadingTxt.setVisibility(0);
        if (!showText) {
            this.loadingTxt.setVisibility(8);
        } else if (!TextUtils.isEmpty(loadingMsg)) {
            this.loadingTxt.setText(loadingMsg);
        } else if (showWillPlay) {
            this.loadingTxt.setText(TipUtils.getTipMessage("100071", R.string.will_play));
        } else {
            this.loadingTxt.setText(TipUtils.getTipMessage(DialogMsgConstantId.ON_LOADING, R.string.player_loading));
        }
        setLoadingViewGone();
    }

    private void setLoadingViewGone() {
        this.not_play.setVisibility(8);
        this.request_error.setVisibility(8);
        this.mTxtSubmitInfo.setVisibility(8);
        this.cannot_play.setVisibility(8);
        this.vip_not_login_error.setVisibility(8);
        this.vip_login_error.setVisibility(8);
        this.jump_error.setVisibility(8);
        this.ip_error.setVisibility(8);
        this.demand_error.setVisibility(8);
        this.local_error.setVisibility(8);
        this.complaint_success.setVisibility(8);
    }

    public void resizeLoadingText(boolean isLandscape) {
        ((LayoutParams) this.loadingTxt.getLayoutParams()).width = (int) (((float) UIsUtils.getScreenWidth()) * 0.6f);
        this.loadingTxt.requestLayout();
    }

    public boolean isErrorTagShow() {
        return this.request_error.getVisibility() == 0 || this.not_play.getVisibility() == 0 || this.cannot_play.getVisibility() == 0 || this.vip_not_login_error.getVisibility() == 0 || this.vip_login_error.getVisibility() == 0 || this.jump_error.getVisibility() == 0 || this.ip_error.getVisibility() == 0 || this.demand_error.getVisibility() == 0 || this.local_error.getVisibility() == 0;
    }

    public void finish() {
        if (this.callBack != null) {
            this.callBack.calledInFinish();
        }
        this.errState = 0;
        this.loading.setVisibility(8);
        this.loadingTxt.setVisibility(8);
        this.not_play.setVisibility(8);
        this.request_error.setVisibility(8);
        this.mTxtSubmitInfo.setVisibility(8);
        this.cannot_play.setVisibility(8);
        this.vip_not_login_error.setVisibility(8);
        this.vip_login_error.setVisibility(8);
        this.jump_error.setVisibility(8);
        this.ip_error.setVisibility(8);
        this.demand_error.setVisibility(8);
        this.local_error.setVisibility(8);
        this.complaint_success.setVisibility(8);
        this.mLayoutNetChange.setVisibility(8);
        UIsUtils.enableScreenAlwaysOn(getContext());
    }

    public boolean isLoadingShow() {
        return this.loading.getVisibility() == 0 || this.request_error.getVisibility() == 0;
    }

    public void notPlay() {
        this.errState = 1;
        this.loading.setVisibility(8);
        this.loadingTxt.setVisibility(8);
        this.not_play.setVisibility(0);
        this.request_error.setVisibility(8);
        this.mTxtSubmitInfo.setVisibility(8);
        this.cannot_play.setVisibility(8);
        this.vip_not_login_error.setVisibility(8);
        this.vip_login_error.setVisibility(8);
        this.jump_error.setVisibility(8);
        this.ip_error.setVisibility(8);
        this.demand_error.setVisibility(8);
        this.local_error.setVisibility(8);
        this.complaint_success.setVisibility(8);
    }

    public void notPlay(String tx) {
        this.errState = 1;
        this.loading.setVisibility(8);
        this.loadingTxt.setVisibility(8);
        this.not_play.setVisibility(0);
        if (!TextUtils.isEmpty(tx)) {
            this.no_play_tx.setText(tx);
        }
        this.request_error.setVisibility(8);
        this.mTxtSubmitInfo.setVisibility(8);
        this.cannot_play.setVisibility(8);
        this.vip_not_login_error.setVisibility(8);
        this.vip_login_error.setVisibility(8);
        this.jump_error.setVisibility(8);
        this.ip_error.setVisibility(8);
        this.demand_error.setVisibility(8);
        this.local_error.setVisibility(8);
    }

    public void requestNotWifi() {
        this.mLayoutNetChange.setVisibility(0);
        this.mNetChangeTipTextView.setText(TipUtils.getTipMessage("100073", R.string.play_view_text_top));
        this.mNetChangeContinue.setText(TipUtils.getTipMessage("100074", R.string.play_view_text_migu_bottom));
    }

    public void requestNotWifi(boolean isThirdParty) {
        finish();
        this.request_error.setVisibility(8);
        this.request_error_btn.setVisibility(8);
        this.loading.setVisibility(8);
        this.mLayoutNetChange.setVisibility(0);
        if (isThirdParty) {
            this.mNetChangeTipTextView.setText(R.string.play_view_text_migu_top);
        } else {
            this.mNetChangeTipTextView.setText(TipUtils.getTipMessage("100073", R.string.play_view_text_top));
        }
        this.mNetChangeContinue.setText(TipUtils.getTipMessage("100074", R.string.play_view_text_migu_bottom));
    }

    public void requestError() {
        requestError(null, AddToCartResultStatus.NO_NETWORK);
    }

    public void requestError(String msg) {
        requestError(msg, AddToCartResultStatus.NO_NETWORK);
    }

    public void requestError(String msg, String errorCode) {
        requestError(msg, errorCode, null);
    }

    public void requestError(String msg, String errorCode, String btnMsg) {
        boolean show;
        int i;
        if (TextUtils.equals(LetvErrorCode.REQUEST_CANPLAY_ERROR, errorCode) || TextUtils.equals(LetvErrorCode.VIDEO_FOREIGN_SHIELD, errorCode) || TextUtils.equals("0302", errorCode) || TextUtils.equals(DialogMsgConstantId.FIFTEEN_ZERO_FIVE_CONSTANT, errorCode) || TextUtils.equals("1506", errorCode) || TextUtils.equals(LetvErrorCode.DOWNLOAD_VIDEOFILE_FAILED, errorCode) || TextUtils.equals(LetvErrorCode.VIDEO_NOTPLAY_STREAM, errorCode) || TextUtils.equals(LetvErrorCode.LOCALFILE_PLAY_ERROR, errorCode) || TextUtils.equals("0204", errorCode) || TextUtils.equals(AddToCartResultStatus.NO_NETWORK, errorCode)) {
            show = true;
        } else {
            show = false;
        }
        finish();
        TextView textView = this.mTxtSubmitInfo;
        if (!show || this.mIsNonCopyright) {
            i = 8;
        } else {
            i = 0;
        }
        textView.setVisibility(i);
        if (TextUtils.equals(errorCode, "-1") || TextUtils.equals(errorCode, "0208") || TextUtils.equals(errorCode, "01000")) {
            this.request_error_btn.setVisibility(8);
        } else {
            this.request_error_btn.setVisibility(0);
        }
        if (this.callBack != null) {
            this.callBack.calledInError();
        }
        this.errState = 2;
        this.loading.setVisibility(8);
        this.loadingTxt.setVisibility(8);
        this.not_play.setVisibility(8);
        this.request_error.setVisibility(0);
        this.mTxtSubmitInfo.setVisibility(8);
        if (TextUtils.isEmpty(msg)) {
            TipBean dialogMsgByMsg = TipUtils.getTipBean("100075");
            if (!(dialogMsgByMsg == null || TextUtils.isEmpty(dialogMsgByMsg.message))) {
                this.request_error_text.setText(dialogMsgByMsg.message);
            }
        } else {
            this.request_error_text.setText(msg);
        }
        if (TextUtils.isEmpty(btnMsg)) {
            this.request_error_btn.setText(TipUtils.getTipMessage("100076", R.string.try_again));
        } else {
            this.request_error_btn.setText(btnMsg);
        }
        this.cannot_play.setVisibility(8);
        this.vip_not_login_error.setVisibility(8);
        this.vip_login_error.setVisibility(8);
        this.jump_error.setVisibility(8);
        this.ip_error.setVisibility(8);
        this.demand_error.setVisibility(8);
        this.local_error.setVisibility(8);
        this.complaint_success.setVisibility(8);
        UIsUtils.disableScreenAlwaysOn(getContext());
    }

    public void leboxError(String msg) {
        if (this.callBack != null) {
            this.callBack.calledInError();
        }
        this.errState = 2;
        this.loading.setVisibility(8);
        this.loadingTxt.setVisibility(8);
        this.not_play.setVisibility(8);
        this.request_error.setVisibility(0);
        this.mTxtSubmitInfo.setVisibility(8);
        if (TextUtils.isEmpty(msg)) {
            TipBean dialogMsgByMsg = TipUtils.getTipBean("100075");
            if (!(dialogMsgByMsg == null || TextUtils.isEmpty(dialogMsgByMsg.message))) {
                this.request_error_text.setText(dialogMsgByMsg.message);
            }
        } else {
            this.request_error_text.setText(msg);
        }
        this.cannot_play.setVisibility(8);
        this.vip_not_login_error.setVisibility(8);
        this.vip_login_error.setVisibility(8);
        this.jump_error.setVisibility(8);
        this.ip_error.setVisibility(8);
        this.demand_error.setVisibility(8);
        this.local_error.setVisibility(8);
        this.complaint_success.setVisibility(8);
        this.request_error_btn.setVisibility(8);
        UIsUtils.disableScreenAlwaysOn(getContext());
    }

    public void cannotPlayError() {
        if (this.callBack != null) {
            this.callBack.calledInError();
        }
        this.errState = 2;
        this.loading.setVisibility(8);
        this.loadingTxt.setVisibility(8);
        this.not_play.setVisibility(8);
        this.cannot_play.setVisibility(0);
        this.request_error.setVisibility(8);
        this.mTxtSubmitInfo.setVisibility(8);
        this.vip_not_login_error.setVisibility(8);
        this.vip_login_error.setVisibility(8);
        this.jump_error.setVisibility(8);
        this.ip_error.setVisibility(8);
        this.demand_error.setVisibility(8);
        this.local_error.setVisibility(8);
        this.complaint_success.setVisibility(8);
    }

    public void vipNotLoginError() {
        if (this.callBack != null) {
            this.callBack.calledInError();
        }
        this.loading.setVisibility(8);
        this.loadingTxt.setVisibility(8);
        this.not_play.setVisibility(8);
        this.request_error.setVisibility(8);
        this.mTxtSubmitInfo.setVisibility(8);
        this.cannot_play.setVisibility(8);
        this.vip_not_login_error.setVisibility(0);
        this.vip_login_error.setVisibility(8);
        this.jump_error.setVisibility(8);
        this.ip_error.setVisibility(8);
        this.demand_error.setVisibility(8);
        this.local_error.setVisibility(8);
        this.complaint_success.setVisibility(8);
    }

    public void jumpError() {
        if (this.callBack != null) {
            this.callBack.calledInError();
        }
        this.errState = 6;
        this.loading.setVisibility(8);
        this.loadingTxt.setVisibility(8);
        this.not_play.setVisibility(8);
        this.request_error.setVisibility(8);
        this.mTxtSubmitInfo.setVisibility(8);
        this.cannot_play.setVisibility(8);
        this.vip_not_login_error.setVisibility(8);
        this.vip_login_error.setVisibility(8);
        this.jump_error.setVisibility(0);
        this.jump_error_text.setText(TipUtils.getTipMessage("100051", R.string.dialog_jump_error_title));
        this.jump_error_button_disable.setVisibility(8);
        this.ip_error.setVisibility(8);
        this.demand_error.setVisibility(8);
        this.local_error.setVisibility(8);
        this.complaint_success.setVisibility(8);
    }

    public void jumpError(int value) {
        if (this.callBack != null) {
            this.callBack.calledInError();
        }
        this.errState = 6;
        this.loading.setVisibility(8);
        this.loadingTxt.setVisibility(8);
        this.not_play.setVisibility(8);
        this.request_error.setVisibility(8);
        this.mTxtSubmitInfo.setVisibility(8);
        this.cannot_play.setVisibility(8);
        this.vip_not_login_error.setVisibility(8);
        this.vip_login_error.setVisibility(8);
        this.jump_error.setVisibility(0);
        errorExposure();
        this.jump_error_text.setText(TipUtils.getTipMessage(value == 0 ? "100053" : "100051", R.string.dialog_jump_error_title));
        if (value == 1) {
            this.jump_error_btn.setVisibility(0);
            this.jump_error_btn.setText(TipUtils.getTipMessage("100054", R.string.jump_to_page_play));
            this.jump_error_button_disable.setVisibility(8);
        } else if (value == 2) {
            this.jump_error_btn.setVisibility(8);
            this.jump_error_button_disable.setText(TipUtils.getTipMessage("100056", R.string.dialog_jump_error_web));
            this.jump_error_button_disable.setVisibility(0);
        } else {
            this.jump_error_btn.setVisibility(8);
            this.jump_error_button_disable.setVisibility(8);
        }
        this.ip_error.setVisibility(8);
        this.demand_error.setVisibility(8);
        this.local_error.setVisibility(8);
        this.complaint_success.setVisibility(8);
    }

    public void jumpError(String title, String msg, boolean showBtn) {
        if (this.callBack != null) {
            this.callBack.calledInError();
        }
        this.errState = 6;
        this.loading.setVisibility(8);
        this.loadingTxt.setVisibility(8);
        this.not_play.setVisibility(8);
        this.request_error.setVisibility(8);
        this.mTxtSubmitInfo.setVisibility(8);
        this.cannot_play.setVisibility(8);
        this.vip_not_login_error.setVisibility(8);
        this.vip_login_error.setVisibility(8);
        this.jump_error.setVisibility(0);
        errorExposure();
        if (TextUtils.isEmpty(title)) {
            this.jump_error_text.setText(TipUtils.getTipMessage("100051", R.string.dialog_jump_error_title));
        } else {
            this.jump_error_text.setText(title);
        }
        if (showBtn) {
            this.jump_error_btn.setVisibility(0);
            this.jump_error_button_disable.setVisibility(8);
            if (!TextUtils.isEmpty(msg)) {
                this.jump_error_btn.setText(msg);
            }
        } else {
            this.jump_error_btn.setVisibility(8);
            this.jump_error_button_disable.setVisibility(0);
            if (!TextUtils.isEmpty(msg)) {
                this.jump_error_button_disable.setText(msg);
            }
        }
        this.ip_error.setVisibility(8);
        this.demand_error.setVisibility(8);
        this.local_error.setVisibility(8);
        this.complaint_success.setVisibility(8);
    }

    private void errorExposure() {
        if (this.jump_error.getVisibility() == 0) {
            String pageid;
            if (UIsUtils.isLandscape(this.mContext)) {
                pageid = PageIdConstant.fullPlayPage;
            } else {
                pageid = PageIdConstant.halpPlayPage;
            }
            StatisticsUtils.staticticsInfoPost(this.mContext, "19", "c76", null, -1, null, pageid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
        }
    }

    public void ipError() {
        if (this.callBack != null) {
            this.callBack.calledInError();
        }
        this.errState = 7;
        this.loading.setVisibility(8);
        this.loadingTxt.setVisibility(8);
        this.not_play.setVisibility(8);
        this.request_error.setVisibility(8);
        this.mTxtSubmitInfo.setVisibility(8);
        this.cannot_play.setVisibility(8);
        this.vip_not_login_error.setVisibility(8);
        this.vip_login_error.setVisibility(8);
        this.jump_error.setVisibility(8);
        this.ip_error.setVisibility(0);
        this.ip_error_text.setText(TipUtils.getTipMessage(DialogMsgConstantId.NO_PLAY_ONLY_CHINA, R.string.no_play_only_china));
        if (!this.mIsNonCopyright) {
            this.ip_error_call_text.setVisibility(0);
        }
        this.demand_error.setVisibility(8);
        this.local_error.setVisibility(8);
        this.complaint_success.setVisibility(8);
    }

    public void ipError(String msg, IpErrorArea area) {
        if (this.callBack != null) {
            this.callBack.calledInError();
        }
        this.errState = 7;
        this.loading.setVisibility(8);
        this.loadingTxt.setVisibility(8);
        this.not_play.setVisibility(8);
        this.request_error.setVisibility(8);
        this.mTxtSubmitInfo.setVisibility(8);
        this.cannot_play.setVisibility(8);
        this.vip_not_login_error.setVisibility(8);
        this.vip_login_error.setVisibility(8);
        this.jump_error.setVisibility(8);
        this.ip_error.setVisibility(0);
        this.ip_error_text.setText(msg.replace("#", "\n"));
        String btnText = TipUtils.getTipMessage("100097");
        String webViewUrl = TipUtils.getTipMessage("100098");
        if (TextUtils.isEmpty(btnText) || TextUtils.isEmpty(webViewUrl) || area != IpErrorArea.CN) {
            this.ip_error_call_text.setVisibility(8);
        } else {
            this.ip_error_call_text.setVisibility(0);
            this.ip_error_call_text.setText(btnText);
            this.ip_error_call_text.setOnClickListener(new 1(this, webViewUrl));
        }
        this.demand_error.setVisibility(8);
        this.local_error.setVisibility(8);
        this.complaint_success.setVisibility(8);
    }

    public void ipError(String msgTip, boolean isHk) {
        if (this.callBack != null) {
            this.callBack.calledInError();
        }
        this.errState = 7;
        this.loading.setVisibility(8);
        this.loadingTxt.setVisibility(8);
        this.not_play.setVisibility(8);
        this.request_error.setVisibility(8);
        this.mTxtSubmitInfo.setVisibility(8);
        this.cannot_play.setVisibility(8);
        this.vip_not_login_error.setVisibility(8);
        this.vip_login_error.setVisibility(8);
        this.jump_error.setVisibility(8);
        this.ip_error.setVisibility(0);
        if (!this.mIsNonCopyright) {
            this.ip_error_call_text.setVisibility(0);
        }
        if (TextUtils.isEmpty(msgTip)) {
            this.ip_error_text.setText(R.string.no_play_only_china);
        } else {
            this.ip_error_text.setText(msgTip);
        }
        this.demand_error.setVisibility(8);
        this.local_error.setVisibility(8);
        this.complaint_success.setVisibility(8);
    }

    public void localError() {
        if (this.callBack != null) {
            this.callBack.calledInError();
        }
        this.errState = 8;
        this.loading.setVisibility(8);
        this.loadingTxt.setVisibility(8);
        this.not_play.setVisibility(8);
        this.request_error.setVisibility(8);
        this.mTxtSubmitInfo.setVisibility(8);
        this.cannot_play.setVisibility(8);
        this.vip_not_login_error.setVisibility(8);
        this.vip_login_error.setVisibility(8);
        this.jump_error.setVisibility(8);
        this.ip_error.setVisibility(8);
        this.demand_error.setVisibility(8);
        this.local_error.setVisibility(0);
        this.complaint_success.setVisibility(8);
    }

    public void complaintSuccess() {
        if (!this.mIsNonCopyright) {
            this.loading.setVisibility(8);
            this.loadingTxt.setVisibility(8);
            this.not_play.setVisibility(8);
            this.request_error.setVisibility(8);
            this.mTxtSubmitInfo.setVisibility(8);
            this.cannot_play.setVisibility(8);
            this.vip_not_login_error.setVisibility(8);
            this.vip_login_error.setVisibility(8);
            this.jump_error.setVisibility(8);
            this.ip_error.setVisibility(8);
            this.demand_error.setVisibility(8);
            this.local_error.setVisibility(8);
            this.complaint_success.setVisibility(0);
        }
    }

    public void onClick(View v) {
        if (v.getId() == R.id.album_net_change_continue) {
            this.mLayoutNetChange.setVisibility(8);
            LogInfo.log("CRL album_net_change_continue click callBack== " + this.callBack);
            if (this.callBack != null) {
                this.callBack.onNetChangeErr();
            }
        } else if (this.request_error_btn == v || this.complaint_success_button == v) {
            if (this.callBack != null) {
                this.callBack.onRequestErr();
            }
            this.errState = 0;
        } else if (this.vip_not_login_error_btn == v) {
            if (this.callBack != null) {
                this.callBack.onVipErr(false);
            }
            this.errState = 0;
        } else if (this.vip_login_error_btn == v) {
            if (this.callBack != null) {
                this.callBack.onVipErr(true);
            }
            this.errState = 0;
        } else if (this.jump_error_btn == v) {
            if (this.callBack != null) {
                this.callBack.onJumpErr();
            }
            this.errState = 0;
            errorClick();
        } else if (this.demand_error_btn == v) {
            if (this.callBack != null) {
                this.callBack.onDemandErr();
            }
            this.errState = 0;
        } else if (this.cannot_play_btn == v) {
            if (this.callBack != null) {
                this.callBack.onPlayFailed();
            }
            this.errState = 0;
        } else if (this.dlnaClose == v) {
            if (this.callBack != null) {
                this.callBack.closeDlna(true);
            }
        } else if (this.mTxtSubmitInfo == v || this.ip_error_call_text == v) {
            if (this.callBack != null) {
                this.callBack.commitErrorInfo();
            }
            complaintSuccess();
            if (UIsUtils.isLandscape(this.mContext)) {
                StatisticsUtils.staticticsInfoPost(this.mContext, "0", "c65", null, 5, null, PageIdConstant.fullPlayPage, null, null, null, null, null);
                return;
            }
            StatisticsUtils.staticticsInfoPost(this.mContext, "0", "c65", null, 5, null, PageIdConstant.halpPlayPage, null, null, null, null, null);
        }
    }

    public void initWithNonCopyright() {
        this.mIsNonCopyright = true;
        r4 = new int[2][];
        r4[0] = new int[]{16842919};
        r4[1] = new int[0];
        ColorStateList color = new ColorStateList(r4, new int[]{-1, this.mContext.getResources().getColor(R.color.letv_color_noncopyright)});
        int bg = R.drawable.noncopyright_btn_selector;
        this.request_error_btn.setTextColor(color);
        this.complaint_success_button.setTextColor(color);
        this.vip_not_login_error_btn.setTextColor(color);
        this.vip_login_error_btn.setTextColor(color);
        this.jump_error_btn.setTextColor(color);
        this.demand_error_btn.setTextColor(color);
        this.cannot_play_btn.setTextColor(color);
        this.mTxtSubmitInfo.setTextColor(color);
        this.ip_error_call_text.setTextColor(color);
        this.request_error_btn.setBackgroundResource(bg);
        this.complaint_success_button.setBackgroundResource(bg);
        this.vip_not_login_error_btn.setBackgroundResource(bg);
        this.vip_login_error_btn.setBackgroundResource(bg);
        this.jump_error_btn.setBackgroundResource(bg);
        this.demand_error_btn.setBackgroundResource(bg);
        this.cannot_play_btn.setBackgroundResource(bg);
        this.ip_error_call_text.setBackgroundResource(bg);
        int visibile = this.loading.getVisibility();
        findViewById(R.id.loading).setVisibility(8);
        this.loading = findViewById(R.id.noncopyright_loading);
        this.loading.setVisibility(visibile);
        LayoutParams lp = (LayoutParams) this.loadingTxt.getLayoutParams();
        lp.addRule(3, R.id.noncopyright_loading);
        this.loadingTxt.setLayoutParams(lp);
        this.mTxtSubmitInfo.setVisibility(8);
    }

    private void errorClick() {
        String pageid;
        if (UIsUtils.isLandscape(this.mContext)) {
            pageid = PageIdConstant.fullPlayPage;
        } else {
            pageid = PageIdConstant.halpPlayPage;
        }
        StatisticsUtils.staticticsInfoPost(this.mContext, "0", "c76", null, 1, null, pageid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
    }

    public PlayLoadLayoutCallBack getCallBack() {
        return this.callBack;
    }

    public void setCallBack(PlayLoadLayoutCallBack callBack) {
        this.callBack = callBack;
    }

    public boolean isShowLoading() {
        return this.loading.getVisibility() == 0;
    }

    public String getJumpBtnText() {
        if (this.jump_error_btn.getVisibility() == 0) {
            return this.jump_error_btn.getText().toString();
        }
        return "";
    }
}
