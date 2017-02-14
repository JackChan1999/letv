package com.letv.android.client.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.umeng.analytics.a;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class PullToRefreshHeaderView extends FrameLayout {
    static final int DEFAULT_ROTATION_ANIMATION_DURATION = 150;
    private static String time = null;
    private Context context;
    private final ImageView mHeaderImage;
    private final ProgressBar mHeaderProgress;
    private final TextView mHeaderText;
    private boolean mIsFromMessagePage;
    private boolean mIsHideTime;
    private final ImageView mNoneRotateImage;
    private String mPullLabel;
    private String mRefreshingLabel;
    private String mReleaseLabel;
    private final Animation mResetRotateAnimation;
    private final Animation mRotateAnimation;
    private Object[] objs;
    public boolean refreshFlag;
    private final View refresh_root;
    private final TextView refresh_time;
    private long step;
    private String title;

    public PullToRefreshHeaderView(Context context, int mode, String releaseLabel, String pullLabel, String refreshingLabel, Object... objs) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context);
        this.step = 0;
        this.refreshFlag = true;
        this.mIsFromMessagePage = false;
        this.context = context;
        ViewGroup header = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_header, this);
        this.mNoneRotateImage = (ImageView) header.findViewById(R.id.none_rotate_progress);
        this.mHeaderText = (TextView) header.findViewById(2131364108);
        this.mHeaderImage = (ImageView) header.findViewById(2131364106);
        this.mHeaderProgress = (ProgressBar) header.findViewById(2131364109);
        this.refresh_time = (TextView) header.findViewById(R.id.pull_to_refresh_text_time);
        this.refresh_root = findViewById(R.id.linearLayout_pull);
        this.refresh_root.setVisibility(8);
        Interpolator interpolator = new LinearInterpolator();
        this.mRotateAnimation = new RotateAnimation(0.0f, -180.0f, 1, 0.5f, 1, 0.5f);
        this.mRotateAnimation.setInterpolator(interpolator);
        this.mRotateAnimation.setDuration(150);
        this.mRotateAnimation.setFillAfter(true);
        this.mResetRotateAnimation = new RotateAnimation(-180.0f, 0.0f, 1, 0.5f, 1, 0.5f);
        this.mResetRotateAnimation.setInterpolator(interpolator);
        this.mResetRotateAnimation.setDuration(150);
        this.mResetRotateAnimation.setFillAfter(true);
        this.mReleaseLabel = releaseLabel;
        this.mPullLabel = pullLabel;
        this.mRefreshingLabel = refreshingLabel;
    }

    public void setParams(Object[] objs) {
        if (objs != null && objs.length > 1) {
            this.refreshFlag = ((Boolean) objs[0]).booleanValue();
            LogInfo.log("pullDown", "setParams_refreshFlag =" + this.refreshFlag);
            this.title = (String) objs[1];
        }
    }

    private void updateTimeValues() {
        long now = System.currentTimeMillis();
        long last = Long.parseLong(PreferencesManager.getInstance().getLastRefreshTime(this.title));
        this.step = now - last;
        LogInfo.log("pullDown", "time =" + time + "  step =" + this.step + "---- last= " + last);
        String a1;
        if (last == 1) {
            a1 = this.context.getString(2131100697);
            time = String.format(this.context.getString(2131100698), new Object[]{a1});
            LogInfo.log("pullDown", "time =" + time);
        } else if (this.step < 60000) {
            time = String.format(this.context.getString(2131100698), new Object[]{this.context.getString(2131100694)});
        } else if (60000 <= this.step && this.step < a.h) {
            String t = ((this.step / 1000) / 60) + this.context.getString(2131100696);
            time = String.format(this.context.getString(2131100698), new Object[]{t});
        } else if (a.h <= this.step) {
            a1 = "";
            a1 = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(last));
            time = String.format(this.context.getString(2131100698), new Object[]{a1});
        } else {
            time = String.format(this.context.getString(2131100698), new Object[]{this.context.getString(2131100694)});
        }
    }

    public void updateTime() {
        if (this.refreshFlag) {
            updateTimeValues();
            setTextTime();
        }
    }

    public void updateTime(boolean flag) {
        if (this.refreshFlag) {
            updateTimeValues();
            setTextTime();
            PreferencesManager.getInstance().saveLastRefreshTime(this.title, System.currentTimeMillis() + "");
        }
    }

    public void reset() {
        this.mHeaderText.setText(this.mPullLabel);
    }

    public void reset2() {
        updateTime(true);
        if (this.objs != null && this.objs.length > 2) {
            ((RefreshOver) this.objs[2]).refreshOver();
        }
    }

    public void reset3() {
        this.mHeaderText.setText(this.mPullLabel);
    }

    public void releaseToRefresh() {
        if (this.mIsFromMessagePage) {
            this.mHeaderText.setVisibility(0);
        } else {
            this.mHeaderText.setVisibility(8);
        }
        this.refresh_root.setVisibility(0);
        setTextTime();
        this.mNoneRotateImage.setVisibility(0);
        this.mHeaderProgress.setVisibility(8);
        this.mHeaderText.setText(this.mReleaseLabel);
        this.mHeaderImage.clearAnimation();
        this.mHeaderImage.startAnimation(this.mRotateAnimation);
    }

    public void setPullLabel(String pullLabel) {
        this.mPullLabel = pullLabel;
    }

    public void refreshing() {
        if (this.mIsFromMessagePage) {
            this.mHeaderText.setVisibility(0);
        } else {
            this.mHeaderText.setVisibility(8);
        }
        this.refresh_root.setVisibility(0);
        this.mNoneRotateImage.setVisibility(8);
        this.mHeaderProgress.setVisibility(0);
        this.mHeaderText.setText(2131099841);
        this.mHeaderImage.clearAnimation();
    }

    public void setRefreshingLabel(String refreshingLabel) {
        this.mRefreshingLabel = refreshingLabel;
        setTextTime();
    }

    public void setReleaseLabel(String releaseLabel) {
        this.mReleaseLabel = releaseLabel;
        setTextTime();
    }

    public void pullToRefresh() {
        updateTime();
        this.refresh_root.setVisibility(0);
        this.mHeaderText.setText(this.mPullLabel);
        this.mHeaderImage.clearAnimation();
        this.mHeaderImage.startAnimation(this.mResetRotateAnimation);
    }

    public void setTextTime() {
        this.refresh_root.setVisibility(0);
        if (this.mIsHideTime) {
            this.refresh_time.setVisibility(8);
        } else if (this.refreshFlag) {
            if (this.refresh_time.getVisibility() == 8) {
                this.refresh_time.setVisibility(0);
            }
            if (TextUtils.isEmpty(time)) {
                updateTimeValues();
            }
            this.refresh_time.setText(time);
        }
    }

    public void setHideTime(boolean isHideTime) {
        this.mIsHideTime = isHideTime;
    }

    public void setTextColor(int color) {
        this.mHeaderText.setTextColor(color);
    }

    protected void onDetachedFromWindow() {
        LogInfo.log("pullDown", "onDetachedFromWindow");
        super.onDetachedFromWindow();
        this.refreshFlag = false;
    }

    public boolean getMessagePageFlag() {
        return this.mIsFromMessagePage;
    }

    public void setMessagePageFlag(boolean mIsFromMessagePage) {
        this.mIsFromMessagePage = mIsFromMessagePage;
    }
}
