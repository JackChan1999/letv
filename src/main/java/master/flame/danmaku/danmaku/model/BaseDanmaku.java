package master.flame.danmaku.danmaku.model;

import android.graphics.RectF;

public abstract class BaseDanmaku {
    public static final String DANMAKU_BR_CHAR = "/n";
    public static final int FLAG_REQUEST_INVALIDATE = 2;
    public static final int FLAG_REQUEST_REMEASURE = 1;
    public static final int INVISIBLE = 0;
    public static final int TYPE_FIX_BOTTOM = 4;
    public static final int TYPE_FIX_TOP = 5;
    public static final int TYPE_MOVEABLE_XXX = 0;
    public static final int TYPE_SCROLL_LR = 6;
    public static final int TYPE_SCROLL_RL = 1;
    public static final int TYPE_SPECIAL = 7;
    public static final int VISIBLE = 1;
    public String _id;
    protected int alpha = AlphaValue.MAX;
    public int borderColor = 0;
    public IDrawingCache<?> cache;
    public int clickBgAlpha = 50;
    public int clickBgColor = 0;
    public float clickX;
    public float clickY;
    public int danmakuClickZanEffectType;
    public long danmakuTime = 0;
    public String danmakuType;
    public Duration duration;
    public int filterResetFlag = -1;
    public int firstShownFlag = -1;
    public GlobalFlagValues flags = null;
    public int index;
    public boolean isClickZan = false;
    public boolean isDanmakuTypeFiltered = false;
    public boolean isGuest;
    public boolean isLive;
    public int isVip = 0;
    public String[] lines;
    public RectF mDanmakuBounds;
    public int mFilterParam = 0;
    protected DanmakuTimer mTimer;
    private int measureResetFlag = 0;
    public float newPaintHeight;
    public float newPaintWidth;
    public String nickName;
    public Object obj;
    public int padding = 0;
    public int paddingBottom = 0;
    public int paddingLeft = 0;
    public int paddingRight = 0;
    public int paddingTop = 0;
    public float paintHeight = -1.0f;
    public float paintWidth = -1.0f;
    public String picture;
    public byte priority = (byte) 0;
    public int requestFlags = 0;
    public int role;
    public float rotationY;
    public float rotationZ;
    public CharSequence text;
    public int textColor;
    public float textHeight;
    public int textShadowColor;
    public float textSize = -1.0f;
    public float textWidth;
    public long time;
    public int underlineColor = 0;
    public String userHash;
    public int userId = 0;
    public int visibility;
    private int visibleResetFlag = 0;
    public String zanNum;
    public RectF zanRoundRect;

    public abstract float getBottom();

    public abstract float getLeft();

    public abstract float[] getRectAtTime(IDisplayer iDisplayer, long j);

    public abstract float getRight();

    public abstract float getTop();

    public abstract int getType();

    public abstract void layout(IDisplayer iDisplayer, float f, float f2);

    public void setRectF(RectF oldRectF) {
        RectF newRectF = new RectF();
        newRectF.right = oldRectF.right;
        newRectF.bottom = oldRectF.bottom;
        newRectF.top = oldRectF.top;
        newRectF.left = oldRectF.left;
        this.mDanmakuBounds = newRectF;
    }

    public long getDuration() {
        return this.duration.value;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public int draw(IDisplayer displayer) {
        return displayer.draw(this);
    }

    public boolean isMeasured() {
        return this.paintWidth > -1.0f && this.paintHeight > -1.0f && this.measureResetFlag == this.flags.MEASURE_RESET_FLAG;
    }

    public void measure(IDisplayer displayer, boolean fromWorkerThread) {
        displayer.measure(this, fromWorkerThread);
        this.measureResetFlag = this.flags.MEASURE_RESET_FLAG;
    }

    public boolean hasDrawingCache() {
        return (this.cache == null || this.cache.get() == null) ? false : true;
    }

    public boolean isShown() {
        return this.visibility == 1 && this.visibleResetFlag == this.flags.VISIBLE_RESET_FLAG;
    }

    public boolean isTimeOut() {
        return this.mTimer == null || isTimeOut(this.mTimer.currMillisecond);
    }

    public boolean isTimeOut(long ctime) {
        return ctime - this.time >= this.duration.value;
    }

    public boolean isOutside() {
        return this.mTimer == null || isOutside(this.mTimer.currMillisecond);
    }

    public boolean isOutside(long ctime) {
        long dtime = ctime - this.time;
        return dtime <= 0 || dtime >= this.duration.value;
    }

    public boolean isLate() {
        return this.mTimer == null || this.mTimer.currMillisecond < this.time;
    }

    public boolean hasPassedFilter() {
        if (this.filterResetFlag == this.flags.FILTER_RESET_FLAG) {
            return true;
        }
        this.mFilterParam = 0;
        return false;
    }

    public boolean isFiltered() {
        return this.filterResetFlag == this.flags.FILTER_RESET_FLAG && this.mFilterParam != 0;
    }

    public boolean isFilteredBy(int flag) {
        return this.filterResetFlag == this.flags.FILTER_RESET_FLAG && (this.mFilterParam & flag) == flag;
    }

    public void setVisibility(boolean b) {
        if (b) {
            this.visibleResetFlag = this.flags.VISIBLE_RESET_FLAG;
            this.visibility = 1;
            return;
        }
        this.visibility = 0;
    }

    public DanmakuTimer getTimer() {
        return this.mTimer;
    }

    public void setTimer(DanmakuTimer timer) {
        this.mTimer = timer;
    }

    public int getAlpha() {
        return this.alpha;
    }
}
