package com.letv.android.client.album.controller;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.android.client.album.AlbumPlayActivity;
import com.letv.android.client.album.R;
import com.letv.android.client.album.observable.ScreenObservable;
import com.letv.business.flow.album.AlbumPlayFlow;
import com.letv.business.flow.album.AlbumPlayFlowObservable.PlayErrorCodeNotify;
import com.letv.core.utils.RxBus;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.DataStatistics;
import com.letv.datastatistics.util.DataUtils;
import java.util.Observable;
import java.util.Observer;

public class AlbumErrorTopController implements OnClickListener, Observer {
    private AlbumPlayActivity mActivity;
    private View mBackView;
    private View mContainView;
    private TextView mErrorCodeText;
    private AlbumErrorFlag mFlag = AlbumErrorFlag.None;
    private ImageView mSwitchView;
    private TextView mTitleTextView;

    public AlbumErrorTopController(AlbumPlayActivity activity) {
        this.mActivity = activity;
        init();
    }

    private void init() {
        this.mContainView = this.mActivity.findViewById(R.id.album_error_top_frame);
        this.mBackView = this.mActivity.findViewById(R.id.album_error_top_back);
        this.mTitleTextView = (TextView) this.mActivity.findViewById(R.id.album_error_top_title);
        this.mSwitchView = (ImageView) this.mActivity.findViewById(R.id.album_error_top_fullhalf_icon);
        this.mErrorCodeText = (TextView) this.mActivity.findViewById(R.id.player_error_code_text);
        this.mBackView.setOnClickListener(this);
        this.mSwitchView.setOnClickListener(this);
    }

    public void show(AlbumErrorFlag flag) {
        this.mFlag = flag;
        this.mContainView.setVisibility(0);
        this.mActivity.coverBlackOnVideoView(true);
        this.mActivity.downloadBackground();
        RxBus.getInstance().send("rx_bus_album_action_update_system_ui");
        if (this.mActivity.getBarrageProtocol() != null) {
            this.mActivity.getBarrageProtocol().pause(false);
        }
        if (this.mActivity != null && this.mActivity.getPlayAdListener() != null) {
            this.mActivity.getPlayAdListener().clearAdFullProcessTimeout();
        }
    }

    public void hide(AlbumErrorFlag flag) {
        if (this.mFlag == flag || this.mFlag == AlbumErrorFlag.None) {
            this.mContainView.setVisibility(8);
            this.mActivity.coverBlackOnVideoView(false);
            RxBus.getInstance().send("rx_bus_album_action_update_system_ui");
        }
    }

    public void setTitle(String title) {
        this.mTitleTextView.setText(title);
    }

    public void setVisibilityForSwitchView(int visibility) {
        this.mSwitchView.setVisibility(visibility);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.album_error_top_back) {
            clickBack();
        } else if (v.getId() == R.id.album_error_top_fullhalf_icon) {
            switchScreen();
        }
    }

    private void clickBack() {
        if (this.mActivity.getController() != null) {
            if (!UIsUtils.isLandscape(this.mActivity)) {
                this.mActivity.getController().back();
            } else if (!this.mActivity.getController().fullBack()) {
                this.mActivity.getController().half();
            }
        }
    }

    private void switchScreen() {
        if (this.mActivity.getController() != null && this.mActivity.getFlow() != null) {
            if (UIsUtils.isLandscape(this.mActivity)) {
                this.mActivity.getController().half();
            } else {
                this.mActivity.getController().full();
            }
        }
    }

    public void setPlayErrorCode(String errorCode, boolean toShow, String subErrorCode) {
        if (!(this.mActivity.getFlow() == null || TextUtils.isEmpty(errorCode))) {
            AlbumPlayFlow flow = this.mActivity.getFlow();
            String str = errorCode;
            String str2 = subErrorCode;
            DataStatistics.getInstance().sendErrorInfo(this.mActivity, "0", "0", str, null, str2, DataUtils.getData(flow.mCid), DataUtils.getData(flow.mAid), DataUtils.getData(flow.mVid), DataUtils.getData(flow.mZid), "pl", flow.mPlayInfo.mUuidTimp);
        }
        if (this.mActivity.mIsPlayingDlna && this.mActivity.getVideoController().mDlnaProtocol != null) {
            this.mActivity.getVideoController().mDlnaProtocol.protocolStop(true, true);
        }
        if (toShow) {
            this.mErrorCodeText.setVisibility(0);
            if (TextUtils.isEmpty(errorCode)) {
                this.mErrorCodeText.setText("");
                return;
            }
            this.mErrorCodeText.setText(this.mActivity.getString(R.string.play_error_code, new Object[]{errorCode}));
            return;
        }
        this.mErrorCodeText.setVisibility(8);
    }

    public void setPlayErrorCode(String errorCode, boolean toShow) {
        setPlayErrorCode(errorCode, toShow, null);
    }

    public boolean isErrorCodeVisible() {
        return this.mErrorCodeText.getVisibility() == 0;
    }

    public boolean isVisible() {
        return this.mContainView.getVisibility() == 0;
    }

    public void update(Observable observable, Object data) {
        if (data instanceof String) {
            if (!TextUtils.equals(ScreenObservable.ON_CONFIG_CHANGE, (String) data)) {
                return;
            }
            if (UIsUtils.isLandscape(this.mActivity)) {
                this.mSwitchView.setImageResource(R.drawable.play_ablum_half_selecter);
                setVisibilityForSwitchView(8);
                return;
            }
            this.mSwitchView.setImageResource(R.drawable.full_selecter);
            setVisibilityForSwitchView(0);
        } else if (data instanceof PlayErrorCodeNotify) {
            PlayErrorCodeNotify notify = (PlayErrorCodeNotify) data;
            setPlayErrorCode(notify.errorCode, notify.shouldShow, notify.subErrorCode);
        }
    }
}
