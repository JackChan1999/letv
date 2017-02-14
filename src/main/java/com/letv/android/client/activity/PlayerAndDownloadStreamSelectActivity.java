package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.activity.WrapActivity;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class PlayerAndDownloadStreamSelectActivity extends WrapActivity implements OnClickListener {
    private ImageView mBackView;
    private TextView mHdText;
    private ImageView mHighSelectIv;
    private RelativeLayout mHighStreamSelectLayout;
    private boolean mIsPlay;
    private ImageView mLowSelectIv;
    private RelativeLayout mLowStreamSelectLayout;
    private TextView mSmoothText;
    private TextView mSpeedText;
    private TextView mStandardText;
    private boolean mStreamLevelFlag;
    private TextView mStreamSelectTitleTv;
    private ImageView mSuperSelectIv;
    private RelativeLayout mSuperStreamSelectLayout;
    private ImageView mSupperSpeedIv;
    private RelativeLayout mSupperSpeedStreamSelectLayout;

    public PlayerAndDownloadStreamSelectActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mIsPlay = true;
        this.mStreamLevelFlag = false;
    }

    public static void launch(Activity activity, boolean mIsPlay) {
        Intent intent = new Intent(activity, PlayerAndDownloadStreamSelectActivity.class);
        intent.putExtra("mIsPlay", mIsPlay);
        activity.startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_center_stream_select_layout);
        this.mIsPlay = getIntent().getBooleanExtra("mIsPlay", true);
        findView();
        initData();
    }

    private void initData() {
        boolean z = true;
        if (this.mIsPlay) {
            findViewById(R.id.setting_model_low_container).setVisibility(0);
            findViewById(R.id.super_stream_select_layout).setVisibility(0);
            switch (PreferencesManager.getInstance().getPlayLevel()) {
                case 0:
                    setWhichSelect(this.mSupperSpeedIv);
                    break;
                case 1:
                    setWhichSelect(this.mLowSelectIv);
                    break;
                case 2:
                    setWhichSelect(this.mHighSelectIv);
                    break;
                case 3:
                    setWhichSelect(this.mSuperSelectIv);
                    break;
            }
        }
        findViewById(R.id.setting_model_low_container).setVisibility(8);
        findViewById(R.id.super_stream_select_layout).setVisibility(0);
        if (PreferencesManager.getInstance().getCurrentDownloadStream() == 1) {
            setWhichSelect(this.mSuperSelectIv);
        } else if (PreferencesManager.getInstance().getCurrentDownloadStream() == 2) {
            setWhichSelect(this.mHighSelectIv);
        } else {
            setWhichSelect(this.mLowSelectIv);
        }
        if (LetvApplication.getInstance().getSuppportTssLevel() != 0) {
            z = false;
        }
        this.mStreamLevelFlag = z;
        if (this.mIsPlay && this.mStreamLevelFlag) {
            LogInfo.log("+-->", "---mIsPlay&& LetvApplicationPlayerLibs.getInstance().getSuppportTssLevel()<= NativeInfos.SUPPORT_TS350K_LEVEL---");
            this.mHighStreamSelectLayout.setVisibility(8);
            this.mHighSelectIv.setVisibility(8);
            this.mSuperStreamSelectLayout.setVisibility(8);
            this.mSuperSelectIv.setVisibility(8);
            this.mSupperSpeedStreamSelectLayout.setVisibility(8);
            this.mSupperSpeedIv.setVisibility(8);
        }
    }

    private void findView() {
        this.mBackView = (ImageView) findViewById(2131362035);
        this.mStreamSelectTitleTv = (TextView) findViewById(R.id.stream_select_title);
        if (this.mIsPlay) {
            this.mStreamSelectTitleTv.setText(2131100437);
        } else {
            this.mStreamSelectTitleTv.setText(2131100436);
        }
        this.mSupperSpeedStreamSelectLayout = (RelativeLayout) findViewById(R.id.supper_speed_stream_select_layout);
        this.mLowStreamSelectLayout = (RelativeLayout) findViewById(R.id.low_stream_select_layout);
        this.mHighStreamSelectLayout = (RelativeLayout) findViewById(R.id.high_stream_select_layout);
        this.mSuperStreamSelectLayout = (RelativeLayout) findViewById(R.id.super_stream_select_layout);
        this.mSupperSpeedIv = (ImageView) findViewById(R.id.supper_speed_select_iv);
        this.mLowSelectIv = (ImageView) findViewById(R.id.low_select_iv);
        this.mHighSelectIv = (ImageView) findViewById(R.id.high_select_iv);
        this.mSuperSelectIv = (ImageView) findViewById(R.id.super_select_iv);
        this.mBackView.setOnClickListener(this);
        this.mSupperSpeedStreamSelectLayout.setOnClickListener(this);
        this.mLowStreamSelectLayout.setOnClickListener(this);
        this.mHighStreamSelectLayout.setOnClickListener(this);
        this.mSuperStreamSelectLayout.setOnClickListener(this);
        if (this.mIsPlay) {
            this.mStandardText = (TextView) findViewById(R.id.model_hight_model_text);
            this.mSmoothText = (TextView) findViewById(R.id.model_standard_model_text);
            this.mHdText = (TextView) findViewById(R.id.model_superhight_model_text);
            this.mSpeedText = (TextView) findViewById(R.id.model_low_model_text);
            this.mStandardText.setText(LetvUtils.getStartStreamText());
            this.mSmoothText.setText(LetvUtils.getSmoothStreamText());
            this.mHdText.setText(LetvUtils.getHDstreamText());
            this.mSpeedText.setText(LetvUtils.getSpeedStreamText());
            return;
        }
        this.mStandardText = (TextView) findViewById(R.id.model_hight_model_text);
        this.mSmoothText = (TextView) findViewById(R.id.model_standard_model_text);
        this.mHdText = (TextView) findViewById(R.id.model_superhight_model_text);
        this.mStandardText.setText(LetvUtils.getStartStreamText());
        this.mSmoothText.setText(LetvUtils.getSmoothStreamText());
        this.mHdText.setText(LetvUtils.getHDstreamText());
    }

    public void onClick(View v) {
        int i = 1;
        switch (v.getId()) {
            case 2131362035:
                finish();
                return;
            case R.id.low_stream_select_layout /*2131364257*/:
                setWhichSelect(this.mLowSelectIv);
                if (!this.mStreamLevelFlag) {
                    setPlayLevel(1);
                }
                if (!(this.mIsPlay || this.mStreamLevelFlag)) {
                    PreferencesManager.getInstance().setCurrentDownloadStream(0);
                }
                LogInfo.LogStatistics("流畅模式");
                String str = "0";
                String str2 = this.mIsPlay ? "e52" : "e53";
                String str3 = "流畅模式";
                if (this.mIsPlay) {
                    i = 2;
                }
                StatisticsUtils.staticticsInfoPost(this, str, str2, str3, i, null, PageIdConstant.settingPage, null, null, null, null, null);
                return;
            case R.id.supper_speed_stream_select_layout /*2131364284*/:
                setWhichSelect(this.mSupperSpeedIv);
                setPlayLevel(0);
                LogInfo.LogStatistics("极速模式");
                StatisticsUtils.staticticsInfoPost(this, "0", "e52", "极速模式", 1, null, PageIdConstant.settingPage, null, null, null, null, null);
                return;
            case R.id.high_stream_select_layout /*2131364292*/:
                setWhichSelect(this.mHighSelectIv);
                setPlayLevel(2);
                if (!this.mIsPlay) {
                    PreferencesManager.getInstance().setCurrentDownloadStream(2);
                }
                LogInfo.LogStatistics("高清模式");
                StatisticsUtils.staticticsInfoPost(this, "0", this.mIsPlay ? "e52" : "e53", "高清模式", this.mIsPlay ? 3 : 2, null, PageIdConstant.settingPage, null, null, null, null, null);
                return;
            case R.id.super_stream_select_layout /*2131364295*/:
                setWhichSelect(this.mSuperSelectIv);
                if (!this.mIsPlay) {
                    PreferencesManager.getInstance().setCurrentDownloadStream(1);
                }
                setPlayLevel(3);
                LogInfo.LogStatistics("超清模式");
                StatisticsUtils.staticticsInfoPost(this, "0", "e52", "超清模式", 4, null, PageIdConstant.settingPage, null, null, null, null, null);
                return;
            default:
                return;
        }
    }

    private void setPlayLevel(int playLevel) {
        if (this.mIsPlay) {
            PreferencesManager.getInstance().setPlayLevel(playLevel);
            LetvApplication.getInstance().setSettingPlayLevel(true);
        }
    }

    private void setWhichSelect(View view) {
        this.mSupperSpeedIv.setVisibility(8);
        this.mLowSelectIv.setVisibility(8);
        this.mHighSelectIv.setVisibility(8);
        this.mSuperSelectIv.setVisibility(8);
        view.setVisibility(0);
    }

    public String getActivityName() {
        return PlayerAndDownloadStreamSelectActivity.class.getName();
    }

    public Activity getActivity() {
        return this;
    }
}
