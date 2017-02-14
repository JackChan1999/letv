package com.letv.redpacketsdk.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.redpacketsdk.R;
import com.letv.redpacketsdk.RedPacketSdk;
import com.letv.redpacketsdk.RedPacketSdkManager;
import com.letv.redpacketsdk.RedPacketSharePreferences;
import com.letv.redpacketsdk.bean.GiftBean;
import com.letv.redpacketsdk.bean.RedPacketBean;
import com.letv.redpacketsdk.callback.AsyncTaskImageLoaderCallback;
import com.letv.redpacketsdk.callback.GiftBeanCallback;
import com.letv.redpacketsdk.callback.LetvSensorEventListenerCallback;
import com.letv.redpacketsdk.net.AsyncTaskImageLoader;
import com.letv.redpacketsdk.net.NetworkManager;
import com.letv.redpacketsdk.net.statistics.StatisticsUtil;
import com.letv.redpacketsdk.utils.DensityUtil;
import com.letv.redpacketsdk.utils.LetvSensorEventListener;
import com.letv.redpacketsdk.utils.LogInfo;

public class RedPacketDialog extends Dialog {
    private final int RESULT_CASH = 1;
    private final int RESULT_DEFAULT = -1;
    private final int RESULT_FAIL = 0;
    private final int RESULT_REAL_GIFT = 2;
    private final int WHAT_ERROR = 3;
    private final int WHAT_LOADING = 2;
    private final int WHAT_SHAKE = 1;
    private Handler handler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    RedPacketDialog.this.showGetGiftLoading();
                    return;
                case 2:
                    RedPacketDialog.this.mLoadingTimeok = true;
                    RedPacketDialog.this.showResult();
                    return;
                case 3:
                    if (RedPacketDialog.this.mLoadingTimeok && !RedPacketDialog.this.mLoadingGetResultok) {
                        RedPacketDialog.this.mLoadingGetResultok = true;
                        RedPacketDialog.this.mResultType = 0;
                        RedPacketDialog.this.showResult();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    };
    public boolean isClicked = false;
    public boolean isShaked = false;
    private AnimationDrawable mAnimation;
    private Context mContext;
    private GiftBean mGiftBean;
    private String mGiftBeanString;
    private ImageView mGiftImage;
    private ImageView mGiftLoading;
    private ImageView mGiftTitleImage;
    private TextView mGiftTitleTextView;
    private AsyncTaskImageLoader mImageUpdateAsyncTask = null;
    private boolean mIsLoading = false;
    private boolean mIsSensorUsed = false;
    private Button mJumpToListButton;
    private LetvSensorEventListener mLetvSensorEventListener;
    private boolean mLoadingGetResultok = false;
    private RelativeLayout mLoadingLayout;
    private boolean mLoadingTimeok = false;
    private ImageView mLogoImage;
    private int mOrientation = 0;
    private MediaPlayer mPlayer;
    public int mRedPacketState;
    private TextView mRedPacketTitleTextView;
    private RelativeLayout mResultActivityDescribeLayout;
    private View mResultCloseView;
    private RelativeLayout mResultLayout;
    private int mResultType = -1;
    private RelativeLayout mRootView;
    private RelativeLayout mShakeActivityDescribeLayout;
    private ImageView mShakeButton;
    private View mShakeCloseView;
    private RelativeLayout mShakeLayout;
    private ImageView mShakeView;
    private ImageView mShareButton;
    private LinearLayout mTimeAndShareLayout;
    private LinearLayout mTimeLayout;
    private TextView mTimeTextView;
    private long sErrorTime = 30000;
    private long sLoadingTime = 1200;
    private long sShakeTime = 1000;

    public RedPacketDialog(Context context) {
        super(context, R.style.redpacket_dialog);
        this.mContext = context;
        this.mOrientation = this.mContext.getResources().getConfiguration().orientation;
        getWindow().setGravity(17);
        setCanceledOnTouchOutside(false);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.red_packet_layout);
        initView();
        initAction();
    }

    private void initView() {
        this.mRootView = (RelativeLayout) findViewById(R.id.root_view);
        LayoutParams root = this.mRootView.getLayoutParams();
        root.width = this.mContext.getResources().getDisplayMetrics().widthPixels;
        root.height = this.mContext.getResources().getDisplayMetrics().heightPixels;
        this.mRootView.setLayoutParams(root);
        this.mShakeLayout = (RelativeLayout) findViewById(R.id.shake_layout);
        this.mShakeCloseView = findViewById(R.id.shake_close_view);
        this.mResultCloseView = findViewById(R.id.result_close_view);
        this.mLogoImage = (ImageView) findViewById(R.id.logo_image);
        this.mRedPacketTitleTextView = (TextView) findViewById(R.id.redpacket_title);
        this.mShakeView = (ImageView) findViewById(R.id.shake_image);
        this.mShakeButton = (ImageView) findViewById(R.id.shake_button);
        this.mLoadingLayout = (RelativeLayout) findViewById(R.id.loading_layout);
        this.mGiftLoading = (ImageView) findViewById(R.id.gift_loading);
        this.mResultLayout = (RelativeLayout) findViewById(R.id.result_layout);
        this.mGiftImage = (ImageView) findViewById(R.id.gift_imageview);
        this.mJumpToListButton = (Button) findViewById(R.id.go_to_list_button);
        this.mShareButton = (ImageView) findViewById(R.id.share_button);
        this.mGiftTitleTextView = (TextView) findViewById(R.id.result_gift_title);
        this.mGiftTitleImage = (ImageView) findViewById(R.id.result_title_image);
        this.mTimeAndShareLayout = (LinearLayout) findViewById(R.id.time_and_share_layout);
        this.mTimeLayout = (LinearLayout) findViewById(R.id.time_layout);
        this.mTimeTextView = (TextView) findViewById(R.id.time_textview);
        this.mResultActivityDescribeLayout = (RelativeLayout) findViewById(R.id.result_activity_describe);
        this.mShakeActivityDescribeLayout = (RelativeLayout) findViewById(R.id.shacke_activity_describe);
    }

    private void initAction() {
        this.mResultActivityDescribeLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                RedPacketSdk.getInstance().notifyGotoWeb(RedPacketSdkManager.getInstance().getActivityDescribeUrl());
                StatisticsUtil.statistics(19);
            }
        });
        this.mShakeActivityDescribeLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                RedPacketSdk.getInstance().notifyGotoWeb(RedPacketSdkManager.getInstance().getActivityDescribeUrl());
                StatisticsUtil.statistics(15);
            }
        });
        this.mShakeCloseView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                StatisticsUtil.statistics(3);
                RedPacketDialog.this.stopShakeSenser();
                RedPacketDialog.this.dismiss();
            }
        });
        this.mShakeButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                RedPacketDialog.this.isClicked = true;
                StatisticsUtil.statistics(2);
                RedPacketDialog.this.shakeView();
            }
        });
        this.mResultCloseView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                StatisticsUtil.statistics(7);
                StatisticsUtil.statistics(10);
                RedPacketSdk.getInstance().notifyToast();
                RedPacketDialog.this.dismiss();
            }
        });
        this.mJumpToListButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (RedPacketDialog.this.mResultType == 0) {
                    StatisticsUtil.statistics(8);
                } else {
                    StatisticsUtil.statistics(16);
                }
                RedPacketSdk.getInstance().notifyGotoGiftList();
                RedPacketDialog.this.dismiss();
            }
        });
        this.mShareButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                StatisticsUtil.statistics(9);
                RedPacketSdk.getInstance().notifyShare();
            }
        });
    }

    public void onBackPressed() {
        if (this.mResultType != -1) {
            RedPacketSdk.getInstance().notifyDialogDismiss();
            RedPacketSdk.getInstance().notifyToast();
            StatisticsUtil.statistics(10);
            dismiss();
        }
        super.onBackPressed();
    }

    protected void shakeView() {
        stopShakeSenser();
        this.mIsSensorUsed = false;
        if (this.mImageUpdateAsyncTask != null) {
            this.mImageUpdateAsyncTask.cleanCallback();
            this.mImageUpdateAsyncTask = null;
        }
        if (this.mShakeView != null) {
            this.mShakeView.startAnimation(AnimationUtils.loadAnimation(RedPacketSdkManager.getInstance().getApplicationContext(), R.anim.shake));
        }
        timeDelay(this.sShakeTime, 1);
        soundShake();
    }

    private void soundShake() {
        try {
            this.mPlayer = MediaPlayer.create(RedPacketSdkManager.getInstance().getApplicationContext(), R.raw.shake_sound);
            if (this.mPlayer != null) {
                this.mPlayer.start();
                this.mPlayer.setOnCompletionListener(new OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        RedPacketDialog.this.mPlayer.release();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (this.mPlayer != null) {
                this.mPlayer.release();
            }
        }
    }

    public void show() {
        try {
            super.show();
            RedPacketSharePreferences sp = RedPacketSharePreferences.getInstance();
            if (sp.isPassRedPacket() || (sp.hasOpenedThisRedPacket() && !sp.getHasShared())) {
                this.mGiftBean = sp.getGiftBean();
                this.mGiftBeanString = sp.getGiftJson();
                this.mResultType = this.mGiftBean.big_type;
                this.mLoadingGetResultok = true;
                this.mLoadingTimeok = true;
                showResult();
                return;
            }
            showShakeDialog();
        } catch (Exception e) {
        }
    }

    public void onResume() {
        if (this.mIsSensorUsed) {
            startShakeSenser();
        }
    }

    public void onStop() {
        if (this.mIsSensorUsed) {
            stopShakeSenser();
        }
    }

    private void showShakeDialog() {
        setCancelable(false);
        this.mShakeLayout.setVisibility(0);
        this.mLoadingLayout.setVisibility(8);
        this.mResultLayout.setVisibility(8);
        if (RedPacketSdkManager.getInstance().hasRedPacket()) {
            RedPacketBean bean = RedPacketSdkManager.getInstance().getRedPacketBean();
            if (!TextUtils.isEmpty(bean.title)) {
                this.mRedPacketTitleTextView.setText(bean.title);
            }
            if (!TextUtils.isEmpty(bean.mobilePic)) {
                this.mImageUpdateAsyncTask = NetworkManager.loadImage(bean.mobilePic, new AsyncTaskImageLoaderCallback() {
                    public void imageDownloadResult(Bitmap bitmap) {
                        RedPacketDialog.this.mLogoImage.setImageBitmap(bitmap);
                        StatisticsUtil.statistics(13);
                    }
                });
            }
            if (!TextUtils.isEmpty(bean.actionUrl)) {
                this.mShakeActivityDescribeLayout.setVisibility(0);
            }
        }
        StatisticsUtil.statistics(1);
        startShakeSenser();
        this.mIsSensorUsed = true;
        if (this.mOrientation == 1) {
            StatisticsUtil.statistics(14);
        }
    }

    private void showGetGiftLoading() {
        if (!this.mIsLoading) {
            try {
                setCancelable(false);
                this.mIsLoading = true;
                this.mShakeLayout.setVisibility(8);
                this.mLoadingLayout.setVisibility(0);
                this.mResultLayout.setVisibility(8);
                this.mGiftLoading.setImageResource(R.anim.animation_redpacket_coming);
                this.mAnimation = (AnimationDrawable) this.mGiftLoading.getDrawable();
                this.mAnimation.start();
                StatisticsUtil.statistics(5);
                timeDelay(this.sLoadingTime, 2);
                timeDelay(this.sErrorTime, 3);
                this.mGiftBeanString = "";
                this.mResultType = -1;
                this.mGiftBean = null;
                NetworkManager.getGiftBean(new GiftBeanCallback() {
                    public void getGiftBeanCallBack(GiftBean giftBean, String json) {
                        RedPacketDialog.this.mLoadingGetResultok = true;
                        if (giftBean != null) {
                            RedPacketDialog.this.mGiftBean = giftBean;
                            RedPacketDialog.this.mResultType = RedPacketDialog.this.mGiftBean.big_type;
                            RedPacketDialog.this.mGiftBeanString = json;
                        } else {
                            RedPacketDialog.this.mResultType = 0;
                            LogInfo.log("PacketReceiveDialog", "showSecondPacketReceiveDialog+getGift+giftBean=null");
                        }
                        RedPacketDialog.this.showResult();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                this.mLoadingGetResultok = true;
                this.mLoadingTimeok = true;
                this.mResultType = 0;
                showResult();
            } catch (OutOfMemoryError e2) {
                e2.printStackTrace();
                this.mLoadingGetResultok = true;
                this.mLoadingTimeok = true;
                this.mResultType = 0;
                showResult();
            }
        }
    }

    private synchronized void showResult() {
        if (this.mLoadingGetResultok && this.mLoadingTimeok) {
            this.mIsLoading = false;
            this.mLoadingGetResultok = false;
            this.mLoadingTimeok = false;
            if (this.mAnimation != null && this.mAnimation.isRunning()) {
                this.mAnimation.stop();
                this.mAnimation = null;
            }
            setCancelable(true);
            this.mShakeLayout.setVisibility(8);
            this.mLoadingLayout.setVisibility(8);
            this.mResultLayout.setVisibility(0);
            if (this.mResultType == 0 || this.mResultType == -1) {
                this.mGiftTitleImage.setImageResource(R.drawable.yihan_icon);
                this.mJumpToListButton.setText("查看奖品");
            } else if (!RedPacketSdkManager.getInstance().hasRedPacket() || TextUtils.isEmpty(RedPacketSdkManager.getInstance().getRedPacketBean().getGiftButtonString)) {
                this.mJumpToListButton.setText("领取奖品");
            } else {
                this.mJumpToListButton.setText(RedPacketSdkManager.getInstance().getRedPacketBean().getGiftButtonString);
            }
            setRealGiftData();
            chackResultShowLayout();
            RedPacketSharePreferences.getInstance().setGiftJson(this.mGiftBeanString);
            StatisticsUtil.statistics(6);
        }
    }

    private void setRealGiftData() {
        if (this.mGiftBean == null) {
            this.mGiftImage.setImageResource(R.drawable.logo);
            this.mGiftTitleTextView.setText("");
            return;
        }
        if (TextUtils.isEmpty(this.mGiftBean.title) || "null".equals(this.mGiftBean.title)) {
            this.mGiftTitleTextView.setText("");
        } else {
            this.mGiftTitleTextView.setText(this.mGiftBean.title);
        }
        if (TextUtils.isEmpty(this.mGiftBean.price_image)) {
            this.mGiftImage.setImageResource(R.drawable.logo);
        } else {
            LogInfo.log("RedPacketDialog", "设置实物红包的图片");
            this.mImageUpdateAsyncTask = NetworkManager.loadImage(this.mGiftBean.price_image, new AsyncTaskImageLoaderCallback() {
                public void imageDownloadResult(Bitmap bitmap) {
                    if (bitmap != null) {
                        RedPacketDialog.this.mGiftImage.setImageBitmap(bitmap);
                    }
                }
            });
        }
        if (!RedPacketSdkManager.getInstance().hasRedPacket() || TextUtils.isEmpty(RedPacketSdkManager.getInstance().getRedPacketBean().actionUrl)) {
            this.mResultActivityDescribeLayout.setVisibility(8);
        } else {
            this.mResultActivityDescribeLayout.setVisibility(0);
        }
    }

    private void chackResultShowLayout() {
        LogInfo.log("RedPacketDialog", "chackResultShowLayout");
        if (RedPacketSharePreferences.getInstance().isPassRedPacket() || RedPacketSdkManager.getInstance().getShareInfo().isEmpty()) {
            this.mShareButton.setVisibility(8);
        } else {
            this.mTimeAndShareLayout.setVisibility(0);
            this.mShakeButton.setVisibility(0);
            StatisticsUtil.statistics(20);
        }
        if (TextUtils.isEmpty(RedPacketSdkManager.getInstance().getNextRedPacketTime())) {
            this.mTimeLayout.setVisibility(8);
        } else {
            this.mTimeAndShareLayout.setVisibility(0);
            this.mTimeLayout.setVisibility(0);
            this.mTimeTextView.setText(RedPacketSdkManager.getInstance().getNextRedPacketTime());
            StatisticsUtil.statistics(17);
        }
        if (this.mShareButton.getVisibility() == 8 && this.mTimeLayout.getVisibility() == 8) {
            this.mTimeAndShareLayout.setVisibility(8);
            this.mResultLayout.setBackgroundResource(R.drawable.result_bg_mini);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) this.mResultLayout.getLayoutParams();
            if (this.mOrientation == 2) {
                lp.height = DensityUtil.dip2px(RedPacketSdkManager.getInstance().getApplicationContext(), 280.0f);
            } else {
                lp.height = DensityUtil.dip2px(RedPacketSdkManager.getInstance().getApplicationContext(), 350.0f);
            }
            this.mResultLayout.setLayoutParams(lp);
            if (this.mOrientation == 2) {
                this.mResultLayout.setPadding(0, DensityUtil.dip2px(RedPacketSdkManager.getInstance().getApplicationContext(), 15.0f), 0, 0);
            }
        }
        if (this.mOrientation == 1) {
            StatisticsUtil.statistics(18);
        }
    }

    private void timeDelay(final long sShakeTime2, final int what) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(sShakeTime2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                RedPacketDialog.this.handler.sendEmptyMessage(what);
            }
        }).start();
    }

    private void startShakeSenser() {
        if (this.mLetvSensorEventListener == null) {
            this.mLetvSensorEventListener = new LetvSensorEventListener(RedPacketSdkManager.getInstance().getApplicationContext());
        }
        this.mLetvSensorEventListener.setCallBack(new LetvSensorEventListenerCallback() {
            public void getShakeAction() {
                LogInfo.log("RedPacketUI", "Get a shaking action");
                RedPacketDialog.this.isShaked = true;
                StatisticsUtil.statistics(4);
                RedPacketDialog.this.shakeView();
            }
        });
        this.mLetvSensorEventListener.start();
    }

    private void stopShakeSenser() {
        if (this.mLetvSensorEventListener != null) {
            this.mLetvSensorEventListener.stop();
            this.mLetvSensorEventListener = null;
        }
    }

    public void dismiss() {
        super.dismiss();
        this.mContext = null;
        this.mIsSensorUsed = false;
        stopShakeSenser();
        if (this.mImageUpdateAsyncTask != null) {
            this.mImageUpdateAsyncTask.cleanCallback();
            this.mImageUpdateAsyncTask = null;
        }
        RedPacketSdk.getInstance().notifyDialogDismiss();
    }
}
