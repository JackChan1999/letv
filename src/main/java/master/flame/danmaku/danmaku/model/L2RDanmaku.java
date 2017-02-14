package master.flame.danmaku.danmaku.model;

public class L2RDanmaku extends R2LDanmaku {
    public L2RDanmaku(Duration duration) {
        super(duration);
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

    protected float getAccurateLeft(IDisplayer displayer, long currTime) {
        long elapsedTime = currTime - this.time;
        if (elapsedTime >= this.duration.value) {
            return (float) displayer.getWidth();
        }
        return (this.mStepX * ((float) elapsedTime)) - this.paintWidth;
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
        return 6;
    }
}
