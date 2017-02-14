package com.letv.redpacketsdk.ui;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.letv.redpacketsdk.R;
import com.letv.redpacketsdk.RedPacketSdk;
import com.letv.redpacketsdk.RedPacketSdkManager;
import com.letv.redpacketsdk.callback.AsyncTaskImageLoaderCallback;
import com.letv.redpacketsdk.callback.ClickCallBack;
import com.letv.redpacketsdk.callback.RedPacketDialogDisplayCallback;
import com.letv.redpacketsdk.callback.RedPacketViewListener;
import com.letv.redpacketsdk.net.AsyncTaskImageLoader;
import com.letv.redpacketsdk.net.NetworkManager;
import com.letv.redpacketsdk.net.statistics.StatisticsUtil;
import com.letv.redpacketsdk.utils.DensityUtil;

public class RedPacketUI extends ImageView {
    public static int STATUS_ONCREATE = 0;
    public static int STATUS_ONDESTORY = 3;
    public static int STATUS_ONRESUME = 1;
    public static int STATUS_ONSTOP = 2;
    private Activity mActivity;
    private boolean mAnimationPlay = false;
    private ClickCallBack mClickCallback;
    private RedPacketDialogDisplayCallback mDialogDisplayCallback;
    private AsyncTaskImageLoader mEntryUpdateAsyncTask = null;
    private boolean mIsOpenDialog = false;
    private boolean mIsOrientationControl = false;
    private RedPacketViewListener mListener;
    private boolean mOrientatiionHasControl = false;
    private int mOrientation;
    private RedPacketDialog mRedPacketDialog;
    private int mRequestedOrientation;
    private int mStatus = -1;
    private boolean mTimingAnimation = false;
    private Runnable mUIShakeAnimation = new Runnable() {
        public void run() {
            RedPacketUI.this.mTimingAnimation = false;
            RedPacketUI.this.playAnimation();
        }
    };

    public RedPacketUI(Activity activity, RedPacketViewListener redPacketViewListener) {
        super(activity);
        this.mActivity = activity;
        this.mListener = redPacketViewListener;
        this.mStatus = STATUS_ONCREATE;
        initView();
    }

    public void openRedPacket() {
        this.mIsOpenDialog = true;
        initDialog();
        lockRedPacketOrientation();
        this.mRedPacketDialog.show();
        if (this.mDialogDisplayCallback != null) {
            this.mDialogDisplayCallback.onShow();
        }
    }

    private void initView() {
        setImageResource(R.drawable.redpacket_entry);
        LayoutParams params = new LayoutParams(-2, -2);
        params.width = DensityUtil.px2dip(RedPacketSdkManager.getInstance().getApplicationContext(), 87.0f);
        params.height = DensityUtil.px2dip(RedPacketSdkManager.getInstance().getApplicationContext(), 87.0f);
        setLayoutParams(params);
        updateEntryImage();
        setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (RedPacketUI.this.mClickCallback != null) {
                    RedPacketUI.this.mClickCallback.onClick();
                }
                StatisticsUtil.statistics(0);
                RedPacketUI.this.openRedPacket();
            }
        });
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility != 0) {
            stopAnimation();
        } else if (!this.mAnimationPlay) {
            this.mAnimationPlay = true;
            playAnimation();
        }
    }

    public void playAnimation() {
        if (this.mAnimationPlay && !this.mTimingAnimation) {
            postDelayed(this.mUIShakeAnimation, 30000);
            this.mTimingAnimation = true;
            if (this != null && this.mStatus == STATUS_ONRESUME && getVisibility() == 0) {
                startAnimation(AnimationUtils.loadAnimation(RedPacketSdkManager.getInstance().getApplicationContext(), R.anim.entry_shake));
            }
        }
    }

    public void stopAnimation() {
        this.mAnimationPlay = false;
        removeCallbacks(this.mUIShakeAnimation);
    }

    public void updateEntryImage() {
        if (RedPacketSdkManager.getInstance().hasRedPacket() && !TextUtils.isEmpty(RedPacketSdkManager.getInstance().getRedPacketBean().pic1)) {
            this.mEntryUpdateAsyncTask = NetworkManager.loadImage(RedPacketSdkManager.getInstance().getRedPacketBean().pic1, new AsyncTaskImageLoaderCallback() {
                public void imageDownloadResult(Bitmap bitmap) {
                    RedPacketUI.this.setEntryImage(bitmap);
                }
            });
        }
    }

    private void setEntryImage(Bitmap bitmap) {
        setImageBitmap(bitmap);
    }

    private void initDialog() {
        if (this.mRedPacketDialog != null) {
            this.mRedPacketDialog.dismiss();
            this.mRedPacketDialog = null;
        }
        if (this.mActivity != null) {
            this.mRedPacketDialog = new RedPacketDialog(this.mActivity);
        }
    }

    public RedPacketViewListener getListener() {
        return this.mListener;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public void onResume() {
        if (this.mStatus != STATUS_ONCREATE) {
            RedPacketSdk.getInstance().updateRedPacketUI(this);
        }
        this.mStatus = STATUS_ONRESUME;
        if (this.mRedPacketDialog != null) {
            this.mRedPacketDialog.onResume();
        }
    }

    public void stop() {
        this.mStatus = STATUS_ONSTOP;
        if (this.mRedPacketDialog != null) {
            this.mRedPacketDialog.onStop();
        }
    }

    public void destroy() {
        this.mStatus = STATUS_ONDESTORY;
        this.mActivity = null;
        stopAnimation();
        if (this.mEntryUpdateAsyncTask != null) {
            this.mEntryUpdateAsyncTask.cleanCallback();
            this.mEntryUpdateAsyncTask = null;
        }
        RedPacketSdk.getInstance().removeRedPacketUI(this);
    }

    public void setDialogDisplayCallback(RedPacketDialogDisplayCallback callback) {
        this.mDialogDisplayCallback = callback;
    }

    public RedPacketDialogDisplayCallback getDialogDisplayCallback() {
        return this.mDialogDisplayCallback;
    }

    public void setOnClickCallBack(ClickCallBack callBack) {
        this.mClickCallback = callBack;
    }

    public boolean getDialogIsShown() {
        return this.mRedPacketDialog.isShowing();
    }

    public Dialog getResultDialog() {
        if (this.mRedPacketDialog != null) {
            return this.mRedPacketDialog;
        }
        return null;
    }

    public void lockRedPacketOrientation() {
        if (this.mIsOrientationControl) {
            this.mOrientatiionHasControl = true;
            this.mOrientation = this.mActivity.getResources().getConfiguration().orientation;
            this.mRequestedOrientation = this.mActivity.getRequestedOrientation();
            if (this.mOrientation == 2) {
                this.mActivity.setRequestedOrientation(0);
            } else if (this.mOrientation == 1) {
                this.mActivity.setRequestedOrientation(1);
            }
        }
    }

    public void unLockRedPacketOrientation() {
        if (this.mIsOrientationControl && this.mOrientatiionHasControl) {
            this.mActivity.setRequestedOrientation(this.mRequestedOrientation);
        }
        dialogClean();
    }

    private void dialogClean() {
        this.mIsOpenDialog = false;
        this.mRedPacketDialog = null;
        this.mOrientatiionHasControl = false;
    }

    public void setIsOrientationControl(boolean is) {
        this.mIsOrientationControl = is;
    }

    public boolean getIsOpenDialog() {
        return this.mIsOpenDialog;
    }
}
