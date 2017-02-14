package com.letv.android.client.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;
import com.letv.android.client.activity.BasePlayActivity;
import com.letv.android.client.barrage.BarrageUtil;
import com.letv.android.client.utils.MainLaunchUtils;
import com.letv.core.bean.BarrageBean;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class LiveInterpretBarrageView extends TextView implements Runnable {
    private static final int AFTER_TEXT_WIDTH_CALCUTED = 1403;
    private static final int HIDE_TEXT = 1400;
    private static final int SHOW_BARRAGE = 1401;
    private static final int UPDATE_BARRAGE = 1402;
    public static boolean isOpenedRedpacket = false;
    public static boolean isOpenedWebView = false;
    String content;
    private int currentScrollX;
    private int endX;
    private int firstScrollX;
    private boolean isFirstDraw;
    private boolean isMeasure;
    private boolean isShowing;
    private boolean isStop;
    private int mDelayed;
    private InterpretBean mInterpretBean;
    private int mSpeed;
    private Handler mTextHandler;
    private int mTextWidth;
    private int mTotalTime;
    private int mWidth;
    SpannableStringBuilder spannableStringBuilder;
    String textBitmap;

    public LiveInterpretBarrageView(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context);
        this.isShowing = false;
        this.content = "";
        this.textBitmap = "bitmap";
        this.spannableStringBuilder = null;
        this.mTextHandler = new 1(this);
        this.isMeasure = false;
        this.currentScrollX = 0;
        this.firstScrollX = 0;
        this.isStop = true;
        this.mWidth = 0;
        this.mSpeed = 2;
        this.mDelayed = 10;
        this.endX = 0;
        this.isFirstDraw = true;
        this.mTotalTime = 8;
    }

    public LiveInterpretBarrageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isShowing = false;
        this.content = "";
        this.textBitmap = "bitmap";
        this.spannableStringBuilder = null;
        this.mTextHandler = new 1(this);
        this.isMeasure = false;
        this.currentScrollX = 0;
        this.firstScrollX = 0;
        this.isStop = true;
        this.mWidth = 0;
        this.mSpeed = 2;
        this.mDelayed = 10;
        this.endX = 0;
        this.isFirstDraw = true;
        this.mTotalTime = 8;
    }

    public boolean isFocused() {
        return true;
    }

    public void showBarrage(BarrageBean barrage) {
        if (barrage == null) {
            LogInfo.log("fornia", "barrage == null");
            return;
        }
        int i;
        LogInfo.log("fornia", "直播弹幕信息 showBarrage(BarrageBean barrage) barrage.txt:" + barrage.txt);
        this.mInterpretBean = new InterpretBean(this, null);
        this.mInterpretBean.text = barrage.txt;
        this.mInterpretBean.textSize = BarrageUtil.convertFontSize(barrage.font);
        this.mInterpretBean.showTime = barrage.showtime;
        InterpretBean interpretBean = this.mInterpretBean;
        if (barrage.vip == 0) {
            i = 0;
        } else {
            i = 1;
        }
        interpretBean.isVip = i;
        this.mInterpretBean.messageType = convertChat2DanmakuType(barrage.danmakuType);
        this.mInterpretBean.bgopacity = barrage.bgopacity;
        this.mInterpretBean.link_txt = barrage.link_txt;
        this.mInterpretBean.link_url = barrage.link_url;
        if (TextUtils.isEmpty(barrage.color)) {
            this.mInterpretBean.bgcolor = 0;
        } else {
            try {
                this.mInterpretBean.bgcolor = Color.parseColor(barrage.bgcolor.startsWith("#") ? barrage.bgcolor : "#" + barrage.bgcolor);
            } catch (Exception e) {
                this.mInterpretBean.bgcolor = 0;
            }
        }
        if (TextUtils.isEmpty(barrage.color)) {
            this.mInterpretBean.textColor = -1;
        } else {
            try {
                this.mInterpretBean.textColor = Color.parseColor(barrage.color.startsWith("#") ? barrage.color : "#" + barrage.color);
            } catch (Exception e2) {
                this.mInterpretBean.textColor = -1;
            }
        }
        this.mInterpretBean.userHash = barrage.uid;
        if (barrage.extend != null) {
            this.mInterpretBean.nickName = barrage.extend.nickname;
            this.mInterpretBean.picture = barrage.extend.picture;
            LogInfo.log("fornia", "获取用户头像 danmaku.picture：" + this.mInterpretBean.picture);
        }
        interpretBean = this.mInterpretBean;
        if (this.mInterpretBean.textColor <= -16777216) {
            i = -1;
        } else {
            i = -16777216;
        }
        interpretBean.textShadowColor = i;
        this.content = this.mInterpretBean.nickName + ": " + this.mInterpretBean.text;
        if (barrage.extend != null) {
            if (barrage.extend.role == 1 || barrage.extend.role == 2) {
                this.textBitmap = "bitmap";
                this.spannableStringBuilder = new SpannableStringBuilder(this.textBitmap);
                BarrageUtil.createStarImageSpan(this.mInterpretBean.picture, this.spannableStringBuilder, 0, this.textBitmap.length());
                if ("redpaper".equalsIgnoreCase(this.mInterpretBean.messageType) && TextUtils.isEmpty(this.mInterpretBean.link_url) && !LetvUtils.isInHongKong()) {
                    Bitmap bitmap = BarrageUtil.createRedPackageBitmap();
                    this.spannableStringBuilder.append(" ");
                    int start = this.spannableStringBuilder.length();
                    this.spannableStringBuilder.append("bitmap");
                    BarrageUtil.createImageSpan(this.spannableStringBuilder, bitmap, start, this.spannableStringBuilder.length());
                    this.mInterpretBean.messageType = "star_redpackage";
                } else {
                    this.mInterpretBean.messageType = "star";
                }
                this.spannableStringBuilder.append("  ").append(this.content);
                this.mInterpretBean.text = this.spannableStringBuilder;
                LogInfo.log("fornia", "bean.text:" + this.mInterpretBean.text);
                this.mInterpretBean.textShadowColor = 0;
            } else {
                this.textBitmap = "bitmap";
                if ("redpaper".equalsIgnoreCase(this.mInterpretBean.messageType) && TextUtils.isEmpty(this.mInterpretBean.link_url) && !LetvUtils.isInHongKong()) {
                    this.spannableStringBuilder = new SpannableStringBuilder(this.textBitmap);
                    BarrageUtil.createImageSpan(this.spannableStringBuilder, BarrageUtil.createRedPackageBitmap(), 0, this.spannableStringBuilder.length());
                    this.mInterpretBean.messageType = "star_redpackage";
                } else {
                    this.spannableStringBuilder = new SpannableStringBuilder("");
                    this.mInterpretBean.messageType = "star";
                }
                this.spannableStringBuilder.append("  ").append(this.mInterpretBean.text);
                this.mInterpretBean.text = this.spannableStringBuilder;
                LogInfo.log("fornia", "bean.text:" + this.mInterpretBean.text);
                this.mInterpretBean.textShadowColor = 0;
            }
            Message msg = new Message();
            msg.what = SHOW_BARRAGE;
            msg.obj = this.mInterpretBean;
            this.mTextHandler.sendMessage(msg);
            return;
        }
        if ("redpaper".equalsIgnoreCase(this.mInterpretBean.messageType) && TextUtils.isEmpty(this.mInterpretBean.link_url)) {
            this.textBitmap = "bitmap";
            this.spannableStringBuilder = new SpannableStringBuilder(this.textBitmap);
            bitmap = BarrageUtil.createRedPackageBitmap();
            this.spannableStringBuilder.append(" ");
            start = this.spannableStringBuilder.length();
            this.spannableStringBuilder.append("bitmap");
            BarrageUtil.createImageSpan(this.spannableStringBuilder, bitmap, start, this.spannableStringBuilder.length());
            this.mInterpretBean.messageType = "star_redpackage";
            this.spannableStringBuilder.append("  ").append(this.mInterpretBean.text);
            this.mInterpretBean.text = this.spannableStringBuilder;
            LogInfo.log("fornia", "bean.text:" + this.mInterpretBean.text);
            this.mInterpretBean.textShadowColor = 0;
        }
        msg = new Message();
        msg.what = SHOW_BARRAGE;
        msg.obj = this.mInterpretBean;
        this.mTextHandler.sendMessage(msg);
    }

    private String convertChat2DanmakuType(String chatType) {
        if (TextUtils.isEmpty(chatType)) {
            return "";
        }
        if (chatType.equals("3")) {
            return "redpaper";
        }
        return "";
    }

    private void showBarrage(InterpretBean bean) {
        if (bean != null) {
            isOpenedRedpacket = false;
            LogInfo.log("fornia", "interpret 直播弹幕信息 showBarrage(InterpretBean bean)" + bean.text + "|" + bean.textColor + "|" + bean.bgcolor + "bean.bgopacity" + bean.bgopacity);
            this.isShowing = true;
            setBarrageVisibility(0);
            setBarrageTextColor(bean.textColor);
            setBarrageTextSize(bean.textSize);
            setBarrageBackgroundColor((bean.bgopacity * 255) / 100, bean.bgcolor);
            setTransparency(BarrageUtil.getDanmakuTransparency());
            setBarrageText(bean.text, bean.link_txt, bean.showTime);
        }
    }

    public void setTransparency(int alpha) {
        if (alpha >= 0) {
            setTransparency((((float) alpha) * 1.0f) / 100.0f);
        }
    }

    public void setTransparency(float alpha) {
        if (alpha >= 0.0f) {
            setAlpha(alpha);
        }
    }

    public void setBarrageText(CharSequence text, String linkText, int time) {
        if (TextUtils.isEmpty(text)) {
            text = "";
        }
        if (TextUtils.isEmpty(linkText)) {
            linkText = "";
        }
        if (TextUtils.isEmpty(text) && TextUtils.isEmpty(linkText)) {
            setText(text);
        } else if (TextUtils.isEmpty(linkText)) {
            LogInfo.log("fornia", "interpret nolink text.toString().length():" + text.toString().length() + "|text.toString():" + text.toString());
            if ((!isIconBarrage(text.toString()) || text.toString().length() - 9 < 20) && (isIconBarrage(text.toString()) || text.toString().length() < 20)) {
                setGravity(17);
                setText(text);
                LogInfo.log("fornia", "interpret text.toString().length() < 20");
                if (this.mTextHandler.hasMessages(HIDE_TEXT)) {
                    this.mTextHandler.removeMessages(HIDE_TEXT);
                }
                this.mTextHandler.sendEmptyMessageDelayed(HIDE_TEXT, (long) (time * 1000));
                return;
            }
            setGravity(5);
            this.isStop = true;
            this.isFirstDraw = true;
            setText(text);
            msg = new Message();
            msg.what = AFTER_TEXT_WIDTH_CALCUTED;
            this.mTextHandler.sendMessageDelayed(msg, 100);
        } else {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
            spannableStringBuilder.append("  ").append(Html.fromHtml("<u>" + linkText + "</u>"));
            LogInfo.log("fornia", "interpret link spannableStringBuilder.toString().length():" + spannableStringBuilder.toString().length());
            if ((!isIconBarrage(spannableStringBuilder.toString()) || spannableStringBuilder.toString().length() - 9 < 20) && (isIconBarrage(spannableStringBuilder.toString()) || spannableStringBuilder.toString().length() < 20)) {
                setGravity(17);
                LogInfo.log("fornia", "interpret spannableStringBuilder.toString().length() < 20");
                setText(spannableStringBuilder);
                if (this.mTextHandler.hasMessages(HIDE_TEXT)) {
                    this.mTextHandler.removeMessages(HIDE_TEXT);
                }
                this.mTextHandler.sendEmptyMessageDelayed(HIDE_TEXT, (long) (time * 1000));
                return;
            }
            setGravity(5);
            this.isStop = true;
            this.isFirstDraw = true;
            setText(spannableStringBuilder);
            msg = new Message();
            msg.what = AFTER_TEXT_WIDTH_CALCUTED;
            this.mTextHandler.sendMessageDelayed(msg, 100);
        }
    }

    private boolean isIconBarrage(String str) {
        if (!TextUtils.isEmpty(str) && str.contains("bitmap")) {
            return true;
        }
        return false;
    }

    public void setBarrageTextColor(int alpha, int color) {
        if (alpha < 0) {
            alpha = 0;
        }
        int r = (16711680 & color) >> 16;
        setTextColor(Color.argb(alpha, r, (MotionEventCompat.ACTION_POINTER_INDEX_MASK & color) >> 8, color & 255));
    }

    public void setBarrageTextColor(int color) {
        setTextColor(color);
    }

    public void setBarrageBackgroundColor(int alpha, int color) {
        if (alpha < 0) {
            alpha = 0;
        }
        int b = color & 255;
        int g = (MotionEventCompat.ACTION_POINTER_INDEX_MASK & color) >> 8;
        int r = (16711680 & color) >> 16;
        LogInfo.log("fornia", "直播弹幕信息 r" + r + "|g" + g + "|b" + b);
        setBackgroundColor(Color.argb(alpha, r, g, b));
    }

    public void setBarrageTextSize(float size) {
        if (size >= 0.0f) {
            setTextSize(size);
        }
    }

    public void setBarrageVisibility(int visibility) {
        if (this.isShowing && visibility == 0) {
            setVisibility(visibility);
        } else {
            setVisibility(8);
        }
    }

    public void onInterpretClicked(Activity activity) {
        LogInfo.log("fornia", "ok onInterpretClicked");
        if (activity != null && this.mInterpretBean != null) {
            LogInfo.log("fornia", "ok onInterpretClicked link_url:" + this.mInterpretBean.link_url + "mInterpretBean.messageType:" + this.mInterpretBean.messageType + "activity:" + activity.getClass());
            if (TextUtils.isEmpty(this.mInterpretBean.link_url)) {
                if (TextUtils.isEmpty(this.mInterpretBean.link_url) && "star_redpackage".equalsIgnoreCase(this.mInterpretBean.messageType)) {
                    LogInfo.log("fornia", "0handleRedPackageClick interpret live redpackage click");
                    if (!isOpenedRedpacket) {
                        isOpenedRedpacket = true;
                        if ((activity instanceof BasePlayActivity) && ((BasePlayActivity) activity).getBaseRedPacket() != null) {
                            ((BasePlayActivity) activity).getBaseRedPacket().openRedPacket();
                        }
                    }
                }
            } else if (!isOpenedWebView) {
                isOpenedWebView = true;
                MainLaunchUtils.jump2H5(activity, this.mInterpretBean.link_url, false);
            }
        }
    }

    public boolean isShow() {
        return this.isShowing;
    }

    public void updateStatus() {
        this.mTextHandler.sendEmptyMessage(UPDATE_BARRAGE);
    }

    public void updateBarrage() {
        setTransparency(BarrageUtil.getDanmakuTransparency());
        setBarrageVisibility(BarrageUtil.getDanmukuOfficial() ? 0 : 8);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.isFirstDraw) {
            getTextWidth();
            this.firstScrollX = getScrollX();
            this.mWidth = getWidth();
            this.endX = this.firstScrollX + UIsUtils.getScreenWidth();
            this.currentScrollX = this.firstScrollX;
            this.isFirstDraw = false;
        }
    }

    public void setSpeed(int sp) {
        LogInfo.log("fornia", "interpret text.toString().length() >= 20 sp:" + sp);
        this.mSpeed = (this.mDelayed * sp) / 1000;
        LogInfo.log("fornia", "interpret text.toString().length() >= 20 mSpeed:" + this.mSpeed);
    }

    public void setDelayed(int delay) {
        this.mDelayed = delay;
    }

    private void getTextWidth() {
        this.mTextWidth = (int) getPaint().measureText(getText().toString());
        LogInfo.log("fornia", "interpret text初始化mTextWidth:" + this.mTextWidth);
    }

    public void run() {
        if (!this.isStop && this.mSpeed != 0) {
            this.currentScrollX += this.mSpeed;
            scrollTo(this.currentScrollX, 0);
            if (this.currentScrollX > this.endX) {
                stopScroll();
            } else {
                postDelayed(this, (long) this.mDelayed);
            }
        }
    }

    public void startScroll() {
        LogInfo.log("fornia", "interpret run() startScroll ");
        this.isStop = false;
        removeCallbacks(this);
        postDelayed(this, (long) this.mDelayed);
    }

    public void stopScroll() {
        this.isStop = true;
        if (this.mTextHandler.hasMessages(HIDE_TEXT)) {
            this.mTextHandler.removeMessages(HIDE_TEXT);
        }
        LogInfo.log("fornia", "interpret run() handleMessage HIDE_TEXT stopScroll");
        this.mTextHandler.sendEmptyMessage(HIDE_TEXT);
    }

    public void startFor0() {
        startScroll();
    }
}
