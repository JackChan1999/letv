package com.letv.android.client.barrage.engine;

import android.app.Activity;
import android.view.MotionEvent;
import com.letv.android.client.barrage.BarragePlayControl;
import com.letv.android.client.barrage.BarrageUtil;
import com.letv.android.client.barrage.engine.DanmakuDanmakuEngine.Builder;
import com.letv.android.client.barrage.engine.DanmakuDanmakuEngine.DanmakuBarrageEngineCallBack;
import com.letv.core.bean.BarrageBean;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.UIsUtils;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;

public class DanmakuEngineManager implements IDanmakuEngineManager {
    public static final int DOUBLE_DANMAKU_ENGINE = 3;
    public static final int FULL_SCREEN_DANMAKU_ENGINE = 2;
    public static final int HALF_SCREEN_DANMAKU_ENGINE = 1;
    private static final String TAG = "barrage";
    private Activity mActivity;
    private int mDanmakuEngineType = 3;
    private BarragePlayControl mFullScreenEngine;
    private BarragePlayControl mHalfScreenEngine;
    private boolean mSupportHalf;

    public DanmakuEngineManager(Activity activity, BarragePlayControl halfScreenEngine, BarragePlayControl fullScreenEngine, int danmakuEngineType) {
        this.mActivity = activity;
        this.mHalfScreenEngine = halfScreenEngine;
        this.mFullScreenEngine = fullScreenEngine;
        this.mDanmakuEngineType = danmakuEngineType;
        this.mSupportHalf = BarrageUtil.isSupportHalfBarrage(activity);
    }

    public void invalidateDanmaku(BaseDanmaku baseDanmaku) {
        if (this.mFullScreenEngine != null && (this.mFullScreenEngine instanceof DanmakuDanmakuEngine)) {
            ((DanmakuDanmakuEngine) this.mFullScreenEngine).invalidateDanmaku(baseDanmaku);
        }
    }

    public void setDanmakuViewHeight(int fullScreenHeight, int halfScreenHeight) {
        if (this.mHalfScreenEngine != null) {
            ((DanmakuDanmakuEngine) this.mHalfScreenEngine).setDanmakuViewHeight(halfScreenHeight);
        }
        if (this.mFullScreenEngine != null) {
            ((DanmakuDanmakuEngine) this.mFullScreenEngine).setDanmakuViewHeight(fullScreenHeight);
        }
    }

    public boolean isFullScreen() {
        return UIsUtils.isLandscape(this.mActivity);
    }

    public void setMaximumVisibleSizeInScreen(int maxSize) {
        if (this.mFullScreenEngine != null) {
            this.mFullScreenEngine.setMaximumVisibleSizeInScreen(maxSize);
        }
        if (this.mHalfScreenEngine != null) {
            this.mHalfScreenEngine.setMaximumVisibleSizeInScreen(maxSize);
        }
    }

    public long getCurrentTime() {
        return this.mHalfScreenEngine != null ? ((DanmakuDanmakuEngine) this.mHalfScreenEngine).getCurrentTime() : 0;
    }

    public DanmakuContext getDanmakuContext(boolean isHalfScreen) {
        if (isHalfScreen) {
            if (this.mHalfScreenEngine != null) {
                return ((DanmakuDanmakuEngine) this.mHalfScreenEngine).getDanmakuContext();
            }
        } else if (this.mFullScreenEngine != null) {
            return ((DanmakuDanmakuEngine) this.mFullScreenEngine).getDanmakuContext();
        }
        return null;
    }

    public void sendDanmaku(boolean isHalfScreen, BarrageBean barrageBean) {
        if (isHalfScreen && this.mHalfScreenEngine != null) {
            this.mHalfScreenEngine.sendBarrage(barrageBean);
        } else if (this.mFullScreenEngine != null) {
            this.mFullScreenEngine.sendBarrage(barrageBean);
        }
    }

    public void setDanmakuTransparency(int t) {
        switch (this.mDanmakuEngineType) {
            case 1:
                if (this.mHalfScreenEngine != null) {
                    ((DanmakuDanmakuEngine) this.mHalfScreenEngine).getDanmakuContext().setDanmakuTransparency((((float) t) * 1.0f) / 100.0f);
                    return;
                }
                return;
            case 2:
                if (this.mFullScreenEngine != null) {
                    ((DanmakuDanmakuEngine) this.mFullScreenEngine).getDanmakuContext().setDanmakuTransparency((((float) t) * 1.0f) / 100.0f);
                    return;
                }
                return;
            case 3:
                if (this.mFullScreenEngine != null && this.mHalfScreenEngine != null) {
                    ((DanmakuDanmakuEngine) this.mFullScreenEngine).getDanmakuContext().setDanmakuTransparency((((float) t) * 1.0f) / 100.0f);
                    ((DanmakuDanmakuEngine) this.mHalfScreenEngine).getDanmakuContext().setDanmakuTransparency((((float) t) * 1.0f) / 100.0f);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public int getMaximumVisibleSizeInScreen() {
        if (this.mFullScreenEngine == null) {
            return 0;
        }
        return this.mFullScreenEngine.getMaximumVisibleSizeInScreen();
    }

    public void setR2LDanmakuVisibility(boolean visible) {
        if (this.mFullScreenEngine != null && this.mHalfScreenEngine != null) {
            this.mFullScreenEngine.setR2LDanmakuVisibility(visible);
            this.mHalfScreenEngine.setR2LDanmakuVisibility(visible);
        }
    }

    public void setFTDanmakuVisibility(boolean visible) {
        if (this.mFullScreenEngine != null && this.mHalfScreenEngine != null) {
            this.mFullScreenEngine.setFTDanmakuVisibility(visible);
            this.mHalfScreenEngine.setFTDanmakuVisibility(visible);
        }
    }

    public void setFBDanmakuVisibility(boolean visible) {
        if (this.mFullScreenEngine != null && this.mHalfScreenEngine != null) {
            this.mFullScreenEngine.setFBDanmakuVisibility(visible);
            this.mHalfScreenEngine.setFBDanmakuVisibility(visible);
        }
    }

    public boolean isR2LDanmakuVisibility() {
        if (this.mFullScreenEngine == null) {
            return false;
        }
        return this.mFullScreenEngine.isR2LDanmakuVisibility();
    }

    public boolean isFBDanmakuVisibility() {
        if (this.mFullScreenEngine == null) {
            return false;
        }
        return this.mFullScreenEngine.isFBDanmakuVisibility();
    }

    public boolean isFTDanmakuVisibility() {
        if (this.mFullScreenEngine == null) {
            return false;
        }
        return this.mFullScreenEngine.isFTDanmakuVisibility();
    }

    public void pauseBarrage() {
        switch (this.mDanmakuEngineType) {
            case 1:
                if (this.mHalfScreenEngine != null && !this.mHalfScreenEngine.isPause()) {
                    this.mHalfScreenEngine.pauseBarrage();
                    return;
                }
                return;
            case 2:
                if (this.mFullScreenEngine != null && !this.mFullScreenEngine.isPause()) {
                    this.mFullScreenEngine.pauseBarrage();
                    return;
                }
                return;
            case 3:
                if (!(this.mFullScreenEngine == null || this.mFullScreenEngine.isPause())) {
                    this.mFullScreenEngine.pauseBarrage();
                }
                if (this.mHalfScreenEngine != null && !this.mHalfScreenEngine.isPause()) {
                    this.mHalfScreenEngine.pauseBarrage();
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void resumeBarrage() {
        switch (this.mDanmakuEngineType) {
            case 1:
                if (this.mHalfScreenEngine != null && this.mHalfScreenEngine.isPause()) {
                    this.mHalfScreenEngine.resumeBarrage();
                    return;
                }
                return;
            case 2:
                if (this.mFullScreenEngine != null && this.mFullScreenEngine.isPause()) {
                    this.mFullScreenEngine.resumeBarrage();
                    return;
                }
                return;
            case 3:
                if (this.mFullScreenEngine != null && this.mFullScreenEngine.isPause()) {
                    this.mFullScreenEngine.resumeBarrage();
                }
                if (this.mHalfScreenEngine != null && this.mHalfScreenEngine.isPause()) {
                    this.mHalfScreenEngine.resumeBarrage();
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void stopBarrage() {
        switch (this.mDanmakuEngineType) {
            case 1:
                if (this.mHalfScreenEngine != null) {
                    this.mHalfScreenEngine.stopBarrage();
                    return;
                }
                return;
            case 2:
                if (this.mFullScreenEngine != null) {
                    this.mFullScreenEngine.stopBarrage();
                    return;
                }
                return;
            case 3:
                if (this.mFullScreenEngine != null) {
                    this.mFullScreenEngine.stopBarrage();
                }
                if (this.mHalfScreenEngine != null) {
                    this.mHalfScreenEngine.stopBarrage();
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void startBarrage(Runnable runnable) {
        switch (this.mDanmakuEngineType) {
            case 1:
                if (this.mHalfScreenEngine != null) {
                    ((DanmakuDanmakuEngine) this.mHalfScreenEngine).setDanmakuParser(new 3(this));
                    this.mHalfScreenEngine.startBarrage(runnable);
                    return;
                }
                return;
            case 2:
                if (this.mFullScreenEngine != null) {
                    ((DanmakuDanmakuEngine) this.mFullScreenEngine).setDanmakuParser(new 4(this));
                    this.mFullScreenEngine.startBarrage(runnable);
                    return;
                }
                return;
            case 3:
                if (this.mHalfScreenEngine != null) {
                    ((DanmakuDanmakuEngine) this.mHalfScreenEngine).setDanmakuParser(new 1(this));
                    this.mHalfScreenEngine.startBarrage(null);
                }
                if (this.mFullScreenEngine != null) {
                    ((DanmakuDanmakuEngine) this.mFullScreenEngine).setDanmakuParser(new 2(this));
                    this.mFullScreenEngine.startBarrage(runnable);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void initBarrage(Builder builder) {
    }

    public void sendBarrage(BarrageBean barrage) {
    }

    public void hideBarrage() {
        LogInfo.log(TAG, " hideBarrage isFullScreen : " + isFullScreen());
        switch (this.mDanmakuEngineType) {
            case 1:
                if (this.mHalfScreenEngine != null) {
                    this.mHalfScreenEngine.hideBarrage();
                    return;
                }
                return;
            case 2:
                if (this.mFullScreenEngine != null) {
                    this.mFullScreenEngine.hideBarrage();
                    return;
                }
                return;
            case 3:
                if (isFullScreen()) {
                    if (this.mHalfScreenEngine != null) {
                        this.mHalfScreenEngine.hideBarrage();
                        return;
                    }
                    return;
                } else if (this.mFullScreenEngine != null) {
                    this.mFullScreenEngine.hideBarrage();
                    return;
                } else {
                    return;
                }
            default:
                return;
        }
    }

    public void showBarrage() {
        LogInfo.log(TAG, " showBarrage isFullScreen : " + isFullScreen());
        switch (this.mDanmakuEngineType) {
            case 1:
                if (this.mHalfScreenEngine != null && !isFullScreen()) {
                    this.mHalfScreenEngine.showBarrage();
                    return;
                }
                return;
            case 2:
                if (this.mFullScreenEngine != null && isFullScreen()) {
                    this.mFullScreenEngine.showBarrage();
                    return;
                }
                return;
            case 3:
                if (isFullScreen()) {
                    if (this.mFullScreenEngine != null) {
                        this.mFullScreenEngine.showBarrage();
                        return;
                    }
                    return;
                } else if (this.mHalfScreenEngine != null && this.mSupportHalf) {
                    this.mHalfScreenEngine.showBarrage();
                    return;
                } else {
                    return;
                }
            default:
                return;
        }
    }

    public boolean isShow() {
        boolean isShow = false;
        switch (this.mDanmakuEngineType) {
            case 1:
                if (this.mHalfScreenEngine == null || !this.mHalfScreenEngine.isShow()) {
                    isShow = false;
                } else {
                    isShow = true;
                }
                break;
            case 2:
                isShow = this.mFullScreenEngine != null && this.mFullScreenEngine.isShow();
                break;
            case 3:
                if (!isFullScreen()) {
                    if (this.mHalfScreenEngine == null || !this.mHalfScreenEngine.isShow()) {
                        isShow = false;
                    } else {
                        isShow = true;
                    }
                    break;
                }
                isShow = this.mFullScreenEngine != null && this.mFullScreenEngine.isShow();
                break;
                break;
        }
        return isShow;
    }

    public boolean isPause() {
        boolean isPause = false;
        switch (this.mDanmakuEngineType) {
            case 1:
                if (this.mHalfScreenEngine == null || !this.mHalfScreenEngine.isPause()) {
                    isPause = false;
                } else {
                    isPause = true;
                }
                break;
            case 2:
                isPause = this.mFullScreenEngine != null && this.mFullScreenEngine.isPause();
                break;
            case 3:
                if (!isFullScreen()) {
                    if (this.mHalfScreenEngine == null || !this.mHalfScreenEngine.isPause()) {
                        isPause = false;
                    } else {
                        isPause = true;
                    }
                    break;
                }
                isPause = this.mFullScreenEngine != null && this.mFullScreenEngine.isPause();
                break;
                break;
        }
        return isPause;
    }

    public boolean isPrepare() {
        boolean isPrepare = false;
        switch (this.mDanmakuEngineType) {
            case 1:
                if (this.mHalfScreenEngine == null || !this.mHalfScreenEngine.isPrepare()) {
                    isPrepare = false;
                } else {
                    isPrepare = true;
                }
                break;
            case 2:
                isPrepare = this.mFullScreenEngine != null && this.mFullScreenEngine.isPrepare();
                break;
            case 3:
                if (!isFullScreen()) {
                    if (this.mHalfScreenEngine == null || !this.mHalfScreenEngine.isPrepare()) {
                        isPrepare = false;
                    } else {
                        isPrepare = true;
                    }
                    break;
                }
                isPrepare = this.mFullScreenEngine != null && this.mFullScreenEngine.isPrepare();
                break;
                break;
        }
        return isPrepare;
    }

    public boolean isClickDanmuku(MotionEvent event) {
        boolean isClickDanmuku = false;
        switch (this.mDanmakuEngineType) {
            case 2:
            case 3:
                if (isFullScreen()) {
                    isClickDanmuku = this.mFullScreenEngine != null && this.mFullScreenEngine.isClickDanmuku(event);
                    break;
                }
                break;
        }
        return isClickDanmuku;
    }

    public void addDanmaku(boolean isHalfScreen, BaseDanmaku baseDanmaku) {
        if (isHalfScreen) {
            if (this.mHalfScreenEngine != null && this.mSupportHalf) {
                this.mHalfScreenEngine.addDanmaku(baseDanmaku);
            }
        } else if (this.mFullScreenEngine != null) {
            this.mFullScreenEngine.addDanmaku(baseDanmaku);
        }
    }

    public void addDanmaku(BaseDanmaku baseDanmaku) {
    }

    public void removeAllDanmaku() {
        switch (this.mDanmakuEngineType) {
            case 1:
                if (this.mHalfScreenEngine != null) {
                    this.mHalfScreenEngine.removeAllDanmaku();
                    return;
                }
                return;
            case 2:
                if (this.mFullScreenEngine != null) {
                    this.mFullScreenEngine.removeAllDanmaku();
                    return;
                }
                return;
            case 3:
                if (this.mFullScreenEngine != null) {
                    this.mFullScreenEngine.removeAllDanmaku();
                }
                if (this.mHalfScreenEngine != null) {
                    this.mHalfScreenEngine.removeAllDanmaku();
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void setCallBack(DanmakuBarrageEngineCallBack callBack) {
        switch (this.mDanmakuEngineType) {
            case 1:
            case 2:
            case 3:
                if (this.mHalfScreenEngine != null && this.mSupportHalf) {
                    ((DanmakuDanmakuEngine) this.mHalfScreenEngine).setCallBack(callBack);
                    return;
                } else if (this.mFullScreenEngine != null) {
                    ((DanmakuDanmakuEngine) this.mFullScreenEngine).setCallBack(callBack);
                    return;
                } else {
                    return;
                }
            default:
                return;
        }
    }

    public void hideAllDanmaku() {
        if (this.mHalfScreenEngine != null) {
            this.mHalfScreenEngine.hideBarrage();
        }
        if (this.mFullScreenEngine != null) {
            this.mFullScreenEngine.hideBarrage();
        }
    }

    public void showAllDanmaku() {
        if (this.mHalfScreenEngine != null && this.mSupportHalf) {
            this.mHalfScreenEngine.showBarrage();
        }
        if (this.mFullScreenEngine != null) {
            this.mFullScreenEngine.showBarrage();
        }
    }

    public void hideDanmaku(boolean isHalfScreen) {
        if (isHalfScreen) {
            if (this.mHalfScreenEngine != null) {
                this.mHalfScreenEngine.hideBarrage();
            }
        } else if (this.mFullScreenEngine != null) {
            this.mFullScreenEngine.hideBarrage();
        }
    }

    public void showDanmaku(boolean isHalfScreen) {
        if (isHalfScreen) {
            if (this.mHalfScreenEngine != null && this.mSupportHalf) {
                this.mHalfScreenEngine.showBarrage();
            }
        } else if (this.mFullScreenEngine != null) {
            this.mFullScreenEngine.showBarrage();
        }
    }

    public void pauseDanmaku(boolean isHalfScreen) {
        if (isHalfScreen) {
            if (this.mHalfScreenEngine != null && this.mSupportHalf) {
                this.mHalfScreenEngine.pauseBarrage();
            }
        } else if (this.mFullScreenEngine != null) {
            this.mFullScreenEngine.pauseBarrage();
        }
    }

    public void resumeDanmaku(boolean isHalfScreen) {
        if (isHalfScreen) {
            if (this.mHalfScreenEngine != null) {
                this.mHalfScreenEngine.resumeBarrage();
            }
        } else if (this.mFullScreenEngine != null) {
            this.mFullScreenEngine.resumeBarrage();
        }
    }
}
