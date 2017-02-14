package com.letv.android.client.view;

import android.content.Context;
import android.graphics.Point;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.view.PlayLoadLayout;
import com.letv.android.client.controller.FloatingWindowPlayerController;
import com.letv.android.client.model.VideoAttributes;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class FloatingWindowPlayerView extends LinearLayout {
    private static final int FLOATING_WINDOW_HEIGHT = ((UIsUtils.getDisplayWidth() * 29) / 48);
    private static final int FLOATING_WINDOW_WIDTH = UIsUtils.getDisplayWidth();
    private static final int MOVE_DISTANCE_THRESHOLD_VALUE = 15;
    private View mBackgroundView;
    private Context mContext;
    private FloatingWindowPlayerController mFloatingWindowPlayerController;
    private ImageView mImageViewWatermark;
    private FloatingWindowPlayerMediaControlView mMediaControlView;
    private FloatingWindowMediaPlayerContainer mMediaPlayerContainer;
    Point mOriginalScreenPoint;
    Point mOriginalViewPoint;
    private PlayLoadLayout mPlayLoadLayout;
    Point mPoint;
    private VideoAttributes mVideoParams;
    private FloatingWindowPlayerVipAuthenticationView mVipAuthView;
    private LayoutParams mWindowLayoutParams;
    private WindowManager mWindowManager;

    public FloatingWindowPlayerView(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this(context, null);
    }

    public FloatingWindowPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mPoint = new Point();
        this.mOriginalScreenPoint = new Point();
        this.mOriginalViewPoint = new Point();
        create(context);
    }

    private void create(Context context) {
        removeAllViews();
        this.mContext = context;
        inflate(context, R.layout.floating_window_player_view, this);
        this.mWindowLayoutParams = createWindowLayoutParams();
        initView();
        setMoveControl();
    }

    private void createMediaPlayerControlView() {
        this.mMediaControlView = (FloatingWindowPlayerMediaControlView) findViewById(R.id.pip_pipMediaController);
        this.mMediaControlView.setVisibility(0);
        this.mMediaControlView.setFloatingWindowPlayerController(this.mFloatingWindowPlayerController);
        this.mMediaControlView.initControlView();
    }

    private void initView() {
        this.mPlayLoadLayout = new PlayLoadLayout(this.mContext);
        this.mBackgroundView = findViewById(R.id.black_bg);
        this.mBackgroundView.setVisibility(0);
        this.mImageViewWatermark = (ImageView) findViewById(R.id.waterMark_imageView);
        this.mPlayLoadLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        ((RelativeLayout) findViewById(R.id.view_container)).addView(this.mPlayLoadLayout);
        this.mPlayLoadLayout.loading(true);
    }

    public FloatingWindowPlayerController getFloatingWindowPlayerController() {
        return this.mFloatingWindowPlayerController;
    }

    public void load() {
        this.mFloatingWindowPlayerController = new FloatingWindowPlayerController(this, this.mVideoParams);
        this.mMediaPlayerContainer = (FloatingWindowMediaPlayerContainer) findViewById(R.id.videoview_container);
        this.mMediaPlayerContainer.setGravity(17);
        this.mMediaPlayerContainer.addView(this.mFloatingWindowPlayerController.getLetvMediaPlayerController().getView(), new LinearLayout.LayoutParams(-1, -1));
        this.mWindowManager.addView(this, this.mWindowLayoutParams);
        createMediaPlayerControlView();
    }

    public FloatingWindowMediaPlayerContainer getMediaPlayerContainer() {
        return this.mMediaPlayerContainer;
    }

    public void setVideoParams(VideoAttributes vp) {
        this.mVideoParams = vp;
    }

    private void setMoveControl() {
        setOnTouchListener(new 1(this));
    }

    public boolean allowMove() {
        if (Math.abs(this.mPoint.x - this.mOriginalScreenPoint.x) >= 15 || Math.abs(this.mPoint.y - this.mOriginalScreenPoint.y) >= 15) {
            return true;
        }
        return false;
    }

    private LayoutParams createWindowLayoutParams() {
        LayoutParams lp = new LayoutParams();
        if (VERSION.SDK_INT >= 19) {
            lp.type = 2005;
        } else {
            lp.type = 2002;
        }
        lp.flags |= 8;
        lp.alpha = 1.0f;
        lp.gravity = 83;
        lp.x = 0;
        lp.y = 0;
        lp.width = FLOATING_WINDOW_WIDTH;
        lp.height = FLOATING_WINDOW_HEIGHT;
        lp.format = 1;
        return lp;
    }

    public boolean shouldShowControlBar() {
        if (Math.abs(this.mPoint.x - this.mOriginalScreenPoint.x) >= 15 || Math.abs(this.mPoint.y - this.mOriginalScreenPoint.y) >= 15) {
            return false;
        }
        if (this.mFloatingWindowPlayerController.getLetvMediaPlayerController() == null) {
            return true;
        }
        if (this.mMediaControlView.isShowing()) {
            this.mMediaControlView.hide();
            return true;
        }
        this.mMediaControlView.show();
        return true;
    }

    public void setWindowManager(WindowManager wm) {
        this.mWindowManager = wm;
    }

    public FloatingWindowPlayerMediaControlView getMediaControlView() {
        return this.mMediaControlView;
    }

    private void updatePosition() {
        this.mWindowLayoutParams.x = this.mPoint.x - this.mOriginalViewPoint.x;
        this.mWindowLayoutParams.y = (UIsUtils.getScreenHeight() - (this.mPoint.y - this.mOriginalViewPoint.y)) - FLOATING_WINDOW_HEIGHT;
        this.mWindowManager.updateViewLayout(this, this.mWindowLayoutParams);
    }

    public PlayLoadLayout getPlayLoadLayout() {
        return this.mPlayLoadLayout;
    }

    public View getBackgroundView() {
        return this.mBackgroundView;
    }

    public void loadVipAuthenticationView() {
        LogInfo.log("FloatingWindowPlayer", "该集为VIP剧集，加载开通VIP会员界面");
        if (this.mVipAuthView == null) {
            this.mVipAuthView = new FloatingWindowPlayerVipAuthenticationView(this.mContext);
        }
        this.mVipAuthView.setController(this.mFloatingWindowPlayerController);
        this.mVipAuthView.setVisibility(0);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.view_container);
        container.removeAllViews();
        container.addView(this.mVipAuthView);
    }

    public void removeVipAuthenticationView() {
        if (this.mVipAuthView != null) {
            ((RelativeLayout) findViewById(R.id.view_container)).removeView(this.mVipAuthView);
        }
    }
}
