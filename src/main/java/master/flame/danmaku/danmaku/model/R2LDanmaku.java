package master.flame.danmaku.danmaku.model;

public class R2LDanmaku extends BaseDanmaku {
    protected static final long CORDON_RENDERING_TIME = 40;
    protected static final long MAX_RENDERING_TIME = 100;
    protected float[] RECT = null;
    protected int mDistance;
    protected long mLastTime;
    protected float mStepX;
    protected float x = 0.0f;
    protected float y = -1.0f;

    public R2LDanmaku(Duration duration) {
        this.duration = duration;
    }

    public void layout(IDisplayer displayer, float x, float y) {
        if (this.mTimer != null) {
            long currMS = this.mTimer.currMillisecond;
            long deltaDuration = currMS - this.time;
            if (deltaDuration <= 0 || deltaDuration >= this.duration.value) {
                this.mLastTime = currMS;
            } else {
                this.x = getAccurateLeft(displayer, currMS);
                if (!isShown()) {
                    this.y = y;
                    setVisibility(true);
                }
                this.mLastTime = currMS;
                return;
            }
        }
        setVisibility(false);
    }

    protected float getAccurateLeft(IDisplayer displayer, long currTime) {
        long elapsedTime = currTime - this.time;
        if (elapsedTime >= this.duration.value) {
            return -this.paintWidth;
        }
        return ((float) displayer.getWidth()) - (((float) elapsedTime) * this.mStepX);
    }

    public float[] getRectAtTime(IDisplayer displayer, long time) {
        if (!isMeasured()) {
            return null;
        }
        float left = getAccurateLeft(displayer, time);
        if (this.RECT == null) {
            this.RECT = new float[4];
        }
        this.RECT[0] = left;
        this.RECT[1] = this.y;
        this.RECT[2] = this.paintWidth + left;
        this.RECT[3] = this.y + this.paintHeight;
        return this.RECT;
    }

    public float getLeft() {
        return this.x;
    }

    public float getTop() {
        return this.y;
    }

    public float getRight() {
        return this.x + this.paintWidth;
    }

    public float getBottom() {
        return this.y + this.paintHeight;
    }

    public int getType() {
        return 1;
    }

    public void measure(IDisplayer displayer, boolean fromWorkerThread) {
        super.measure(displayer, fromWorkerThread);
        this.mDistance = (int) (((float) displayer.getWidth()) + this.paintWidth);
        this.mStepX = ((float) this.mDistance) / ((float) this.duration.value);
    }
}
