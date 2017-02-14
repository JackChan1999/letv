package com.letv.android.client.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.config.LetvWebViewActivityConfig;
import com.letv.android.client.utils.WatchAndBuyAnimationUtils;
import com.letv.android.client.view.BaseFloatViewLayout.CallBackListener;
import com.letv.core.bean.WatchAndBuyGoodsBean;
import com.letv.core.bean.WatchAndBuyGoodsListBean;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.RxBus;
import com.letv.core.utils.StatisticsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.pp.utils.NetworkUtils;
import com.media.ffmpeg.FFMpegPlayer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

public class WatchAndBuyView extends BaseFloatViewLayout implements OnClickListener {
    private static final int MSG_ADDTOCART = 2;
    private static final int MSG_TIMING = 1;
    public static final String TAG = "watchAndBuyView";
    public static WatchAndBuyGoodsBean sAddToCartGoods = null;
    private WatchAndBuyViewCallback callback;
    private WatchAndBuyGoodsBean forceCloseGoods;
    private boolean mAddToCartFromChild;
    private Button mBtnAdd;
    private ImageView mBtnClose;
    private View mCart;
    private boolean mCartListViewShowing;
    private boolean mClickEnable;
    private Context mContext;
    private int mCount;
    private WatchAndBuyGoodsBean mCurrentGoods;
    private WatchAndBuyDetailView mDetailView;
    private boolean mDetailViewShowing;
    private DisplayMetrics mDisplayMetrics;
    private Handler mHandler;
    private ImageView mIvBag;
    private ImageView mIvHandle;
    private ImageView mIvLogo;
    private RelativeLayout mLayoutAttention;
    private RelativeLayout mLayoutDetail;
    private RelativeLayout mLayoutInfo;
    private RelativeLayout mLayoutPackage;
    private RelativeLayout mLayoutPackageHorizon;
    private RelativeLayout mLayoutPackageVertical;
    private WatchAndBuyGoodsListBean mListBean;
    private long mLiveEndTime;
    private WatchAndBuyLoopView mLoopView;
    private Message mMessage;
    private RxBus mRxBus;
    private long mServerTime;
    private boolean mShowing;
    private CompositeSubscription mSubscription;
    private TextView mTvAttention;
    private TextView mTvDesc;
    private TextView mTvOldPrice;
    private TextView mTvPrice;
    private TextView mTvTitle;
    private RelativeLayout mlayoutIcon;
    private RelativeLayout mlayoutIconTop;
    private SimpleDateFormat sdf;

    public WatchAndBuyView(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context);
        this.mDisplayMetrics = new DisplayMetrics();
        this.mClickEnable = true;
        this.mAddToCartFromChild = false;
        this.forceCloseGoods = null;
        this.sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.mHandler = new 1(this);
        this.mContext = context;
    }

    public WatchAndBuyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mDisplayMetrics = new DisplayMetrics();
        this.mClickEnable = true;
        this.mAddToCartFromChild = false;
        this.forceCloseGoods = null;
        this.sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.mHandler = new 1(this);
        this.mContext = context;
    }

    public WatchAndBuyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mDisplayMetrics = new DisplayMetrics();
        this.mClickEnable = true;
        this.mAddToCartFromChild = false;
        this.forceCloseGoods = null;
        this.sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.mHandler = new 1(this);
        this.mContext = context;
    }

    public void init() {
        ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay().getMetrics(this.mDisplayMetrics);
        this.mDetailView = (WatchAndBuyDetailView) findViewById(R.id.watchandbuy_detail_view);
        this.mDetailView.init();
        this.mLoopView = (WatchAndBuyLoopView) findViewById(R.id.watchandbuy_loopview);
        this.mlayoutIcon = (RelativeLayout) findViewById(R.id.watchandbuy_layout_icon);
        this.mlayoutIconTop = (RelativeLayout) findViewById(R.id.watchandbuy_layout_looper_top);
        this.mLayoutDetail = (RelativeLayout) findViewById(R.id.watchandbuy_layout_detail);
        this.mLayoutInfo = (RelativeLayout) findViewById(R.id.watchandbuy_layout_info);
        this.mLayoutPackage = (RelativeLayout) findViewById(R.id.watchandbuy_layout_package);
        this.mLayoutPackageVertical = (RelativeLayout) findViewById(R.id.watchandbuy_layout_package_vertical);
        this.mLayoutPackageHorizon = (RelativeLayout) findViewById(R.id.watchandbuy_layout_package_horizon);
        this.mLayoutAttention = (RelativeLayout) findViewById(R.id.watchandbuy_layout_attention);
        this.mIvBag = (ImageView) findViewById(R.id.watchandbuy_iv_bag);
        this.mIvHandle = (ImageView) findViewById(R.id.watchandbuy_iv_handle);
        this.mIvLogo = (ImageView) findViewById(R.id.watchandbuy_iv_logo);
        this.mBtnClose = (ImageView) findViewById(R.id.watchandbuy_btn_close);
        this.mBtnAdd = (Button) findViewById(R.id.watchandbuy_btn_add);
        this.mTvTitle = (TextView) findViewById(R.id.watchandbuy_tv_title);
        this.mTvDesc = (TextView) findViewById(R.id.watchandbuy_tv_desc);
        this.mTvPrice = (TextView) findViewById(R.id.watchandbuy_tv_price);
        this.mTvOldPrice = (TextView) findViewById(R.id.watchandbuy_tv_oldprice);
        this.mTvOldPrice.getPaint().setFlags(16);
        this.mTvAttention = (TextView) findViewById(R.id.watchandbuy_tv_attention);
        this.mBtnClose.setOnClickListener(this);
        this.mBtnAdd.setOnClickListener(this);
        this.mLayoutInfo.setOnClickListener(this);
        this.mlayoutIconTop.setOnClickListener(this);
        this.mRxBus = RxBus.getInstance();
    }

    public void setCallback(WatchAndBuyViewCallback callback) {
        this.callback = callback;
    }

    public void setGoodsListBean(WatchAndBuyGoodsListBean goods) {
        setGoodsListBean(goods, "");
    }

    public void setGoodsListBean(WatchAndBuyGoodsListBean goods, String liveEndTime) {
        LogInfo.log(TAG, "setGoodsListBean");
        this.mListBean = goods;
        this.mLiveEndTime = getFormatTime(liveEndTime);
        LogInfo.log(TAG, "live end time : " + this.mLiveEndTime + "  timeStr : " + liveEndTime);
        LogInfo.log(TAG, "client time : " + (this.mServerTime + ((long) this.mCount)) + "  timeStr : " + getFormatTimeString(this.mServerTime + ((long) this.mCount)));
        LogInfo.log(TAG, "server time : " + getFormatTime(goods.time) + " timeStr : " + goods.time);
        if (Math.abs((this.mServerTime + ((long) this.mCount)) - getFormatTime(goods.time)) > 5) {
            LogInfo.log(TAG, "时间相差较大，时间校对");
            this.mServerTime = getFormatTime(goods.time);
            this.mCount = 0;
            LogInfo.log(TAG, "client time : " + (this.mServerTime + ((long) this.mCount)) + "  timeStr : " + getFormatTimeString(this.mServerTime + ((long) this.mCount)));
        }
        this.mHandler.removeMessages(1);
        if (this.mServerTime != 0) {
            this.mMessage = this.mHandler.obtainMessage();
            this.mMessage.what = 1;
            this.mHandler.sendMessageDelayed(this.mMessage, 1000);
            registerRxBus();
        }
    }

    public void setAttetion(int count) {
        if (this.mCurrentGoods != null) {
            if (count != 0) {
                this.mCurrentGoods.attention = getAttentionString(count);
            }
            if (TextUtils.isEmpty(this.mCurrentGoods.attention)) {
                this.mLayoutAttention.setVisibility(8);
                return;
            }
            this.mTvAttention.setText(this.mCurrentGoods.attention);
            this.mLayoutAttention.setVisibility(0);
        }
    }

    private String getAttentionString(int count) {
        String result = "";
        if (count < 10000) {
            return String.valueOf(count);
        }
        Context context = this.mContext;
        Object[] objArr = new Object[1];
        objArr[0] = String.format("%.1f", new Object[]{Float.valueOf(((float) count) / 10000.0f)});
        return context.getString(2131100935, objArr);
    }

    private void registerRxBus() {
        LogInfo.log(RxBus.TAG, "WatchAndBuyView注册RxBus");
        if (this.mSubscription == null) {
            this.mSubscription = new CompositeSubscription();
        }
        if (!this.mSubscription.hasSubscriptions()) {
            LogInfo.log(RxBus.TAG, "WatchAndBuyView添加RxBus Event");
            this.mSubscription.add(this.mRxBus.toObserverable().observeOn(AndroidSchedulers.mainThread()).subscribe(new 2(this)));
        }
    }

    private void unRegisterRxBus() {
        LogInfo.log(RxBus.TAG, "WatchAndBuyView取消注册RxBus");
        if (this.mSubscription != null && this.mSubscription.hasSubscriptions()) {
            this.mSubscription.unsubscribe();
        }
        this.mSubscription = null;
    }

    private void beginToShow(WatchAndBuyGoodsBean goods) {
        beginToShow(goods, false);
    }

    private void beginToShow(WatchAndBuyGoodsBean goods, boolean animated) {
        if (goods != null) {
            if (getVisibility() == 8) {
                LogInfo.log(TAG, "unvisiable");
                return;
            }
            LogInfo.log(TAG, "want to show : " + goods.name + "  " + goods.startTime + "  " + goods.endTime);
            if (this.forceCloseGoods != null && this.forceCloseGoods.id.equals(goods.id) && this.forceCloseGoods.startTime.equals(goods.startTime) && this.forceCloseGoods.endTime.equals(goods.endTime)) {
                LogInfo.log(TAG, "和上次关闭的一样");
            } else if (sAddToCartGoods != null && sAddToCartGoods.id.equals(goods.id) && sAddToCartGoods.startTime.equals(goods.startTime) && sAddToCartGoods.endTime.equals(goods.endTime)) {
                LogInfo.log(TAG, "和上次关闭的一样");
            } else if (!this.mShowing) {
                if (this.mCurrentGoods == null || !this.mCurrentGoods.id.equals(goods.id) || !this.mCurrentGoods.startTime.equals(goods.startTime) || !this.mCurrentGoods.endTime.equals(goods.endTime)) {
                    String str;
                    if (this.mDetailView != null) {
                        this.mDetailView.close();
                    }
                    ArrayList<String> images = new ArrayList();
                    if (!TextUtils.isEmpty(goods.pic)) {
                        images.add(goods.pic);
                    }
                    if (!TextUtils.isEmpty(goods.pic1)) {
                        images.add(goods.pic1);
                    }
                    if (!TextUtils.isEmpty(goods.pic2)) {
                        images.add(goods.pic2);
                    }
                    this.mLoopView.setData(images, true);
                    this.mTvTitle.setText(goods.name);
                    this.mTvDesc.setText(goods.desc);
                    this.mTvPrice.setText(goods.price);
                    this.mTvOldPrice.setText(goods.oldPrice);
                    if (!TextUtils.isEmpty(goods.btn_txt)) {
                        this.mBtnAdd.setText(goods.btn_txt);
                    }
                    this.mCurrentGoods = goods;
                    if (TextUtils.isEmpty(this.mCurrentGoods.attention)) {
                        this.mLayoutAttention.setVisibility(8);
                    } else {
                        this.mTvAttention.setText(this.mCurrentGoods.attention);
                        this.mLayoutAttention.setVisibility(0);
                    }
                    if (this.callback != null) {
                        this.callback.onStartToShow();
                    }
                    startAnimation();
                    Context context = this.mContext;
                    String str2 = PageIdConstant.fullPlayPage;
                    String str3 = "19";
                    String str4 = "com01";
                    String str5 = goods == null ? NetworkUtils.DELIMITER_LINE : goods.id;
                    if (goods == null) {
                        str = NetworkUtils.DELIMITER_LINE;
                    } else {
                        str = goods.streamId;
                    }
                    StatisticsUtils.statisticsActionInfo(context, str2, str3, str4, str5, -1, null, null, null, null, null, str);
                    this.mShowing = true;
                    LogInfo.log(RxBus.TAG, "WatchAndBuyView 发送showEvent");
                    this.mRxBus.send(new ShowEvent());
                } else if (!this.mCartListViewShowing) {
                    if (this.mDetailView != null) {
                        switch (this.mDetailView.getStatus()) {
                            case 1:
                                this.mDetailView.show();
                                this.mShowing = false;
                                return;
                            case 2:
                                return;
                        }
                    }
                    if (this.callback != null) {
                        this.callback.onStartToShow();
                    }
                    LogInfo.log(TAG, "不再播放动画直接显示");
                    if (!this.mDetailViewShowing) {
                        showHorizonal();
                    }
                    this.mShowing = true;
                }
            }
        }
    }

    private void beginToHide(WatchAndBuyGoodsBean goods) {
        if (goods != null && this.mShowing) {
            this.mShowing = false;
            stopShowing();
            this.mCurrentGoods = null;
            if (this.callback != null) {
                this.callback.onClickClose();
            }
        }
    }

    public WatchAndBuyGoodsBean getCanShowGoods() {
        LogInfo.log(TAG, "getCanShowGoods");
        WatchAndBuyGoodsBean goodsBean = null;
        if (this.mListBean == null) {
            return null;
        }
        LogInfo.log(TAG, "servertime: " + (this.mServerTime + ((long) this.mCount)) + "  timeStr: " + getFormatTimeString(this.mServerTime + ((long) this.mCount)));
        Iterator it = this.mListBean.items.iterator();
        while (it.hasNext()) {
            WatchAndBuyGoodsBean temp = (WatchAndBuyGoodsBean) it.next();
            long startTime = getFormatTime(temp.startTime);
            long endTime = getFormatTime(temp.endTime);
            LogInfo.log(TAG, "getCanShowGoods : " + temp.name + "  startTime: " + startTime + "  timeStr: " + temp.startTime + "  endTime: " + temp.endTime);
            if ((this.mServerTime + ((long) this.mCount)) - startTime > 0 && (this.mServerTime + ((long) this.mCount)) - endTime < 2) {
                goodsBean = temp;
                break;
            }
        }
        return goodsBean;
    }

    private WatchAndBuyGoodsBean getCanHideGoods() {
        long endTime;
        WatchAndBuyGoodsBean goodsBean = null;
        if (this.mListBean == null) {
            goodsBean = null;
        }
        LogInfo.log(TAG, "servertime: " + (this.mServerTime + ((long) this.mCount)) + "  timeStr: " + getFormatTimeString(this.mServerTime + ((long) this.mCount)));
        if (this.mCurrentGoods != null) {
            LogInfo.log(TAG, "比对当前显示的商品是否需要消失");
            endTime = getFormatTime(this.mCurrentGoods.endTime);
            LogInfo.log(TAG, "getCanHideGoods : " + this.mCurrentGoods.name + "  startTime: " + endTime + "  timeStr: " + this.mCurrentGoods.endTime);
            if ((this.mServerTime + ((long) this.mCount)) - endTime > 0 && (this.mServerTime + ((long) this.mCount)) - endTime < 2) {
                goodsBean = this.mCurrentGoods;
            }
        }
        if (goodsBean != null) {
            return goodsBean;
        }
        Iterator it = this.mListBean.items.iterator();
        while (it.hasNext()) {
            WatchAndBuyGoodsBean temp = (WatchAndBuyGoodsBean) it.next();
            endTime = getFormatTime(temp.endTime);
            LogInfo.log(TAG, "getCanHideGoods : " + temp.name + "  startTime: " + endTime + "  timeStr: " + temp.endTime);
            if ((this.mServerTime + ((long) this.mCount)) - endTime > 0 && (this.mServerTime + ((long) this.mCount)) - endTime < 2) {
                return temp;
            }
        }
        return goodsBean;
    }

    private void stopShowing() {
        this.mLayoutPackageHorizon.setVisibility(8);
        this.mLayoutPackage.setVisibility(8);
        this.mlayoutIcon.setVisibility(8);
    }

    public boolean isShowing() {
        return this.mShowing || this.mDetailViewShowing;
    }

    public void destroy() {
        LogInfo.log(TAG, "边看边买View销毁");
        if (this.mShowing) {
            setVisibility(8);
        }
        if (this.mHandler != null) {
            this.mHandler.removeMessages(1);
            this.mHandler.removeMessages(2);
        }
        if (this.mDetailView != null && this.mDetailView.getStatus() == 2) {
            this.mDetailView.hide();
        }
        this.mCount = 0;
        this.forceCloseGoods = null;
        this.mCartListViewShowing = false;
        unRegisterRxBus();
        hide();
    }

    public WatchAndBuyGoodsBean getCurrentGoods() {
        return this.mCurrentGoods;
    }

    public void hide() {
        LogInfo.log(TAG, "hide");
        setVisible(false);
    }

    public void show() {
        LogInfo.log(TAG, "show");
        setVisible(true);
    }

    private void hideHorizonal() {
        this.mLayoutPackageVertical.setVisibility(8);
        this.mLayoutPackageHorizon.setVisibility(8);
        this.mLayoutPackageVertical.setVisibility(8);
        this.mlayoutIcon.setVisibility(8);
        this.mLayoutPackage.setVisibility(8);
        if (this.mLoopView != null) {
            this.mLoopView.destroy();
        }
    }

    private void hideHorizonalAndDetail() {
        hideHorizonal();
        if (this.mDetailView != null && this.mDetailView.getStatus() == 2) {
            this.mDetailView.hide();
        }
        this.mShowing = false;
    }

    private void showHorizonal() {
        this.mLayoutPackage.setVisibility(0);
        this.mLayoutPackageHorizon.setVisibility(0);
        this.mlayoutIcon.setVisibility(0);
        this.mLayoutDetail.setVisibility(0);
        if (TextUtils.isEmpty(this.mCurrentGoods.attention)) {
            this.mLayoutAttention.setVisibility(8);
        } else {
            this.mTvAttention.setText(this.mCurrentGoods.attention);
            this.mLayoutAttention.setVisibility(0);
        }
        this.mLoopView.startLoop();
    }

    private void setVisible(boolean visible) {
        if (visible) {
            setVisibility(0);
            return;
        }
        LogInfo.log("fornia", " setVisibility out_to_right:");
        setVisibility(8);
        hideHorizonalAndDetail();
    }

    public void onAddToCart(View view) {
        sAddToCartGoods = this.mCurrentGoods;
        this.mCart = view;
        if (!this.mAddToCartFromChild) {
            closeAnimation();
        } else if (this.mDetailView != null) {
            this.mDetailView.onAddToCart(view);
        }
    }

    private void close() {
        LogInfo.log(TAG, "click close Button");
        if (this.mCurrentGoods != null) {
            this.forceCloseGoods = this.mCurrentGoods;
        }
        hideHorizonalAndDetail();
    }

    public void setCallBackListener(CallBackListener callBackListener) {
    }

    public void clearVideos() {
    }

    public void onClick(View v) {
        if (v == this.mBtnAdd) {
            if (this.mClickEnable) {
                String str;
                String str2;
                this.mClickEnable = false;
                this.mAddToCartFromChild = false;
                if (!TextUtils.isEmpty(this.mCurrentGoods.btn_url)) {
                    new LetvWebViewActivityConfig(this.mContext).launch(this.mCurrentGoods.btn_url, false, false, 25);
                } else if (this.callback != null) {
                    this.callback.onAddToCartClick();
                }
                Context context = this.mContext;
                String str3 = PageIdConstant.fullPlayPage;
                String str4 = "0";
                String str5 = "com01";
                if (this.mCurrentGoods == null) {
                    str = NetworkUtils.DELIMITER_LINE;
                } else {
                    str = this.mCurrentGoods.id;
                }
                if (this.mCurrentGoods == null) {
                    str2 = NetworkUtils.DELIMITER_LINE;
                } else {
                    str2 = this.mCurrentGoods.streamId;
                }
                StatisticsUtils.statisticsActionInfo(context, str3, str4, str5, str, 2, null, null, null, null, null, str2);
                Observable.timer(1000, TimeUnit.MILLISECONDS).subscribe(new 3(this));
            }
        } else if (v == this.mBtnClose) {
            close();
            if (this.callback != null) {
                this.callback.onClickClose();
            }
            StatisticsUtils.statisticsActionInfo(this.mContext, PageIdConstant.fullPlayPage, "0", "com01", this.mCurrentGoods == null ? NetworkUtils.DELIMITER_LINE : this.mCurrentGoods.id, 3, null, null, null, null, null, this.mCurrentGoods == null ? NetworkUtils.DELIMITER_LINE : this.mCurrentGoods.streamId);
        } else if (v == this.mLayoutInfo || v == this.mlayoutIconTop) {
            LogInfo.log(TAG, "click view");
            if (this.mCurrentGoods != null) {
                ArrayList<String> images = new ArrayList();
                if (!TextUtils.isEmpty(this.mCurrentGoods.pic_text1)) {
                    images.add(this.mCurrentGoods.pic_text1);
                }
                if (!TextUtils.isEmpty(this.mCurrentGoods.pic_text2)) {
                    images.add(this.mCurrentGoods.pic_text2);
                }
                if (!TextUtils.isEmpty(this.mCurrentGoods.pic_text3)) {
                    images.add(this.mCurrentGoods.pic_text3);
                }
                if (!TextUtils.isEmpty(this.mCurrentGoods.pic_text4)) {
                    images.add(this.mCurrentGoods.pic_text4);
                }
                if (!TextUtils.isEmpty(this.mCurrentGoods.pic_text5)) {
                    images.add(this.mCurrentGoods.pic_text5);
                }
                if (images.size() != 0) {
                    beginToCloseViewAnimation();
                }
                StatisticsUtils.statisticsActionInfo(this.mContext, PageIdConstant.fullPlayPage, "0", "com01", this.mCurrentGoods == null ? NetworkUtils.DELIMITER_LINE : this.mCurrentGoods.id, 1, null, null, null, null, null, this.mCurrentGoods == null ? NetworkUtils.DELIMITER_LINE : this.mCurrentGoods.streamId);
            }
        }
    }

    private void isLiveEnd() {
        LogInfo.log("pjf", "查看直播是否结束");
        if (this.mLiveEndTime - (this.mServerTime + ((long) this.mCount)) == 60) {
            LogInfo.log(TAG, "直播即将结束");
            if (this.callback != null) {
                this.callback.onLiveStreamEnd();
            }
        }
    }

    private long getFormatTime(String timeStr) {
        long time = 0;
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeStr).getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
            return time;
        }
    }

    private String getFormatTimeString(long time) {
        return this.sdf.format(new Date(1000 * time));
    }

    private void startAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, (float) (-((int) (40.0f * this.mDisplayMetrics.density))), 0.0f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        AnimationSet animations = new AnimationSet(true);
        animations.addAnimation(translateAnimation);
        animations.addAnimation(alphaAnimation);
        animations.setDuration(500);
        animations.setFillAfter(true);
        this.mIvLogo.setVisibility(0);
        this.mLayoutPackageVertical.setVisibility(0);
        animations.setAnimationListener(new 4(this));
        this.mLayoutPackageVertical.startAnimation(animations);
    }

    private void startRotate() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setDuration(150);
        alphaAnimation.setAnimationListener(new 5(this));
        this.mIvLogo.startAnimation(alphaAnimation);
        int pivotX = this.mLayoutPackageVertical.getWidth() / 2;
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, -90.0f, (float) pivotX, (float) (this.mLayoutPackageVertical.getHeight() - pivotX));
        rotateAnimation.setDuration(300);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setAnimationListener(new 6(this));
        this.mLayoutPackageVertical.startAnimation(rotateAnimation);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.0f, 1.0f, 1.35f, (float) this.mIvBag.getWidth(), (float) this.mIvBag.getHeight());
        scaleAnimation.setDuration(300);
        scaleAnimation.setFillAfter(true);
        this.mIvBag.startAnimation(scaleAnimation);
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, (float) (-((int) (49.0f * this.mDisplayMetrics.density))));
        translateAnimation.setDuration(300);
        translateAnimation.setFillAfter(true);
        this.mIvHandle.startAnimation(translateAnimation);
    }

    private void startTranslateAndShowInfo() {
        this.mlayoutIcon.setVisibility(0);
        WatchAndBuyAnimationUtils.right2Left(this.mlayoutIcon, 400, new 7(this));
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(200);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setAnimationListener(new 8(this));
        this.mLayoutDetail.startAnimation(alphaAnimation);
    }

    private void closeAnimation() {
        WatchAndBuyAnimationUtils.left2Right(this.mlayoutIcon, 300, null, new 9(this));
    }

    private void beginToCloseViewAnimation() {
        WatchAndBuyAnimationUtils.left2Right(this.mlayoutIcon, 170, null, new 10(this));
        Observable.timer(150, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new 11(this));
    }

    private void beginToShowViewAnimation() {
        LogInfo.log("pjf", "beginToShowViewAnimation");
        this.mLayoutPackageHorizon.setVisibility(0);
        this.mLayoutDetail.setVisibility(0);
        this.mLayoutPackage.setVisibility(0);
        WatchAndBuyAnimationUtils.right2Left(this.mLayoutPackage, FFMpegPlayer.PREPARE_VIDEO_NODECODER_ERROR, new 12(this));
        Observable.timer(200, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new 13(this));
    }
}
