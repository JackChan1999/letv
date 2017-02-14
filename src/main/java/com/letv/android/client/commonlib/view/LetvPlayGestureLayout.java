package com.letv.android.client.commonlib.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import com.letv.core.BaseApplication;
import com.letv.core.constant.LiveRoomConstant;
import com.letv.core.messagebus.config.LeMessageIds;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.messagebus.message.LeResponseMessage;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.UIsUtils;

public class LetvPlayGestureLayout extends RelativeLayout implements OnGestureListener {
    public static final int DOUBLE_FINGERS_DOWN = 16;
    public static final int DOUBLE_FINGERS_UP = 17;
    public static final int LANDSCAPE_SCROLL_FINISH = 18;
    public static final int NONE = 0;
    public static final int SINGLE_FINGERS_DOWN = 19;
    public static final int SINGLE_FINGERS_UP = 20;
    private float bothSidesCuttingValue = 0.5f;
    private Context context;
    int currentB = 0;
    private int directionalLock = 0;
    private float doubleFingersDownCuttingValue = 0.3f;
    private float doubleFingersUpCuttingValue = 0.3f;
    public int event;
    private float landscapeLimitSlope = 0.25f;
    private float landscapeProgress = 0.0f;
    private float leftProgress = 0.0f;
    protected LetvPlayGestureCallBack mCallbacks;
    private long mDownTime;
    private GestureDetector mGestureDetector = null;
    private boolean mHasNavigationbar;
    private boolean mIsPanorama = false;
    private boolean mIsVr = false;
    private LiveRoomGestureListener mModel;
    private int mNavigation_bar_height;
    private int mOldBrightness;
    private LetvPanoramaGestureLayout mPanoramaController;
    private float mX;
    private float mY;
    private float middleCuttingValue = 0.5f;
    private float offsetX = 0.0f;
    private float offsetY = 0.0f;
    private float portraitLimitSlope = 4.0f;
    private float rightProgerss = 0.0f;

    public interface LetvPlayGestureCallBack {
        void onDoubleFingersDown();

        void onDoubleFingersUp();

        void onDoubleTap();

        void onDown();

        void onLandscapeScroll(float f);

        void onLandscapeScrollFinish(float f);

        void onLeftScroll(float f);

        void onLongPress();

        void onMiddleSingleFingerDown();

        void onMiddleSingleFingerUp();

        void onRightScroll(float f);

        void onSingleTapUp();

        void onTouch();

        void onTouchEventUp();

        int setOneFingertouchInfomation(float f, float f2, float f3, float f4);

        int setTwoScale(float f);
    }

    public LetvPlayGestureLayout(Context activity, AttributeSet attrs) {
        super(activity, attrs);
        this.context = activity;
        init();
    }

    public LetvPlayGestureLayout(Context activity) {
        super(activity);
        this.context = activity;
        init();
    }

    public LetvPlayGestureLayout(Context activity, AttributeSet attrs, int defStyle) {
        super(activity, attrs, defStyle);
        this.context = activity;
        init();
    }

    public void setLetvPlayGestureCallBack(LetvPlayGestureCallBack callback, boolean isLive) {
        this.mCallbacks = callback;
        this.mPanoramaController = new LetvPanoramaGestureLayout(this.context, this.mCallbacks);
        if (isLive) {
            this.bothSidesCuttingValue = 0.2f;
        }
    }

    public void setIsPanorama(boolean isPanorama) {
        this.mIsPanorama = isPanorama;
        if (this.mPanoramaController != null) {
            this.mPanoramaController.reset();
        }
    }

    public void setIsVr(boolean isVr) {
        this.mIsVr = isVr;
    }

    public void setModel(LiveRoomGestureListener model) {
        this.mModel = model;
        this.bothSidesCuttingValue = 0.2f;
    }

    public boolean useCustomClick() {
        return true;
    }

    protected void init() {
        this.mNavigation_bar_height = UIsUtils.getNavigationBarLandscapeWidth(this.context);
        this.mHasNavigationbar = UIsUtils.hasNavigationBar(this.context);
        this.mGestureDetector = new GestureDetector(this);
        this.mGestureDetector.setOnDoubleTapListener(new 1(this));
    }

    public void initializeData(float rightProgerss, float leftProgress) {
        this.rightProgerss = rightProgerss;
        this.leftProgress = leftProgress;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (this.mIsPanorama && this.mPanoramaController != null) {
            this.mPanoramaController.onInterceptTouchEvent(ev);
        }
        if (ev.getAction() == 0) {
            this.mDownTime = System.currentTimeMillis();
            this.mX = ev.getX();
            this.mY = ev.getY();
        }
        return super.onInterceptTouchEvent(ev);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (this.mIsVr) {
            return this.mGestureDetector.onTouchEvent(event);
        }
        LogInfo.log("Gesture", " onTouchEvent  getAction " + event.getAction());
        if (event.getAction() == 0) {
            LeResponseMessage response = LeMessageManager.getInstance().dispatchMessage(new LeMessage(LeMessageIds.MSG_BARRAGE_ON_TOUCH, event));
            if (LeResponseMessage.checkResponseMessageValidity(response, Boolean.class) && ((Boolean) response.getData()).booleanValue()) {
                return false;
            }
        }
        if (this.mIsPanorama && this.mPanoramaController != null) {
            return this.mPanoramaController.onTouchEvent(event);
        }
        if (event.getAction() == 0) {
            this.mDownTime = System.currentTimeMillis();
        } else if (1 == event.getAction()) {
            switch (this.event) {
                case 16:
                    if (this.mCallbacks != null) {
                        this.mCallbacks.onDoubleFingersDown();
                        break;
                    }
                    break;
                case 17:
                    if (this.mCallbacks != null) {
                        this.mCallbacks.onDoubleFingersUp();
                        break;
                    }
                    break;
                case 18:
                    if (this.mCallbacks != null) {
                        this.mCallbacks.onLandscapeScrollFinish(this.landscapeProgress);
                    }
                    if (this.mModel != null) {
                        this.mModel.onLandscapeScrollFinish(this.landscapeProgress);
                        break;
                    }
                    break;
                case 19:
                    if (this.mCallbacks != null) {
                        this.mCallbacks.onMiddleSingleFingerDown();
                        break;
                    }
                    break;
                case 20:
                    if (this.mCallbacks != null) {
                        this.mCallbacks.onMiddleSingleFingerUp();
                    }
                    if (this.mModel != null) {
                        this.mModel.onMiddleSingleFingerUp();
                        break;
                    }
                    break;
            }
            this.directionalLock = 0;
            this.offsetY = 0.0f;
            this.offsetX = 0.0f;
            this.landscapeProgress = 0.0f;
            this.event = 0;
            if (this.mCallbacks != null) {
                this.mCallbacks.onTouchEventUp();
            }
            onTouchEventUp();
        }
        return this.mGestureDetector.onTouchEvent(event);
    }

    public boolean onDown(MotionEvent e) {
        this.leftProgress = 0.0f;
        this.rightProgerss = 0.0f;
        this.mOldBrightness = getBrightness();
        if (this.mCallbacks != null) {
            this.mCallbacks.onDown();
        }
        if (this.mModel != null) {
            this.mModel.setOldVolume(this.mModel.getCurSoundVolume());
        }
        return true;
    }

    public int getBrightness() {
        float br = ((Activity) this.context).getWindow().getAttributes().screenBrightness;
        LogInfo.log("clf", "getBrightness....br=" + br);
        if (br < 0.0f) {
            br = 0.1f;
        }
        return (int) (255.0f * br);
    }

    private void setVolume(float incremental) {
        if (this.mModel != null) {
            int volume = this.mModel.getOldVolume() + ((int) (((float) this.mModel.getMaxSoundVolume()) * incremental));
            if (volume < 0) {
                volume = 0;
            }
            if (volume > this.mModel.getMaxSoundVolume()) {
                volume = this.mModel.getMaxSoundVolume();
            }
            this.mModel.setCurSoundVolume(volume, true);
        }
    }

    private void setBrightness(float incremental) {
        if (this.mModel != null) {
            if (this.currentB == 0) {
                this.mOldBrightness = getBrightness();
                float bright = BaseApplication.getInstance().getBritness();
                this.mOldBrightness = bright == 0.0f ? this.mOldBrightness : (int) (bright * 255.0f);
                this.currentB = this.mOldBrightness;
            }
            float brightness = (((float) this.mOldBrightness) / 255.0f) + incremental;
            LogInfo.log("fornia", "Brightness letvplaygest setBrightness:incremental:" + incremental + " brightness" + brightness);
            if (brightness < 0.0f) {
                brightness = 0.0f;
            }
            if (((double) brightness) > 1.0d) {
                brightness = 1.0f;
            }
            this.mModel.setBrightness(brightness);
        }
    }

    public void onTouchEventUp() {
        if (this.mModel != null) {
            this.mModel.sendEvent(LiveRoomConstant.EVENT_TOUCH_EVENT_UP);
        }
    }

    private void doSingleTapUp() {
        if (this.mModel != null) {
            this.mModel.doSingleTapUp();
        }
    }

    public void onShowPress(MotionEvent e) {
    }

    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (!this.mIsPanorama && (this.mModel == null || this.mModel.isFullScreen())) {
            int s = e2.getPointerCount();
            if (s == 2) {
                this.offsetY += distanceY;
                if (this.offsetY > 0.0f) {
                    if (this.offsetY > this.doubleFingersUpCuttingValue * ((float) getHeight())) {
                        this.event = 17;
                    } else {
                        this.event = 0;
                    }
                } else if (this.offsetY < (-this.doubleFingersDownCuttingValue) * ((float) getHeight())) {
                    this.event = 16;
                } else {
                    this.event = 0;
                }
            } else if (s == 1) {
                if (Math.abs(distanceY) > this.portraitLimitSlope * Math.abs(distanceX) && this.directionalLock != 2) {
                    this.directionalLock = 1;
                    if (e2.getX() > (1.0f - this.bothSidesCuttingValue) * ((float) getWidth())) {
                        this.rightProgerss += distanceY / ((float) getHeight());
                        setVolume(this.rightProgerss);
                        if (this.mCallbacks != null) {
                            this.mCallbacks.onRightScroll(this.rightProgerss);
                        }
                    } else if (e2.getX() < this.bothSidesCuttingValue * ((float) getWidth())) {
                        this.leftProgress += distanceY / ((float) getHeight());
                        setBrightness(this.leftProgress);
                        if (this.mCallbacks != null) {
                            this.mCallbacks.onLeftScroll(this.leftProgress);
                        }
                    } else if (e1.getX() > (this.middleCuttingValue - this.bothSidesCuttingValue) * ((float) getWidth()) && e1.getX() < (this.middleCuttingValue + this.bothSidesCuttingValue) * ((float) getWidth())) {
                        this.offsetY += distanceY;
                        if (!(this.mCallbacks == null && this.mModel == null)) {
                            if (this.offsetY > this.bothSidesCuttingValue * ((float) getHeight())) {
                                this.event = 20;
                            } else if (this.offsetY < (-this.bothSidesCuttingValue) * ((float) getHeight())) {
                                this.event = 19;
                            }
                        }
                    }
                } else if (Math.abs(distanceY) < this.landscapeLimitSlope * Math.abs(distanceX) && this.directionalLock != 1) {
                    this.directionalLock = 2;
                    this.offsetX -= distanceX;
                    this.landscapeProgress = this.offsetX / ((float) getWidth());
                    if (!(this.mCallbacks == null || isInterceptByNavigationBar(e1))) {
                        this.mCallbacks.onLandscapeScroll(this.landscapeProgress);
                    }
                    this.event = 18;
                }
            }
        }
        return false;
    }

    private boolean isInterceptByNavigationBar(MotionEvent downEvent) {
        if (this.mHasNavigationbar && UIsUtils.isLandscape() && ((float) getWidth()) - downEvent.getX() < ((float) this.mNavigation_bar_height)) {
            return true;
        }
        return false;
    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    public void onLongPress(MotionEvent e) {
        if (this.mCallbacks != null) {
            this.mCallbacks.onLongPress();
        }
    }
}
