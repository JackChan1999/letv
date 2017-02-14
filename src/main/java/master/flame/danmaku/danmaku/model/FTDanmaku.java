package master.flame.danmaku.danmaku.model;

public class FTDanmaku extends BaseDanmaku {
    private float[] RECT = null;
    private int mLastDispWidth;
    private float mLastLeft;
    private float mLastPaintWidth;
    private float x = 0.0f;
    protected float y = -1.0f;

    public FTDanmaku(Duration duration) {
        this.duration = duration;
    }

    public void layout(IDisplayer displayer, float x, float y) {
        if (this.mTimer != null) {
            long deltaDuration = this.mTimer.currMillisecond - this.time;
            if (deltaDuration <= 0 || deltaDuration >= this.duration.value) {
                setVisibility(false);
                this.y = -1.0f;
                this.x = (float) displayer.getWidth();
            } else if (!isShown()) {
                this.x = getLeft(displayer);
                this.y = y;
                setVisibility(true);
            }
        }
    }

    protected float getLeft(IDisplayer displayer) {
        if (this.mLastDispWidth == displayer.getWidth() && this.mLastPaintWidth == this.paintWidth) {
            return this.mLastLeft;
        }
        float left = (((float) displayer.getWidth()) - this.paintWidth) / 2.0f;
        this.mLastDispWidth = displayer.getWidth();
        this.mLastPaintWidth = this.paintWidth;
        this.mLastLeft = left;
        return left;
    }

    public float[] getRectAtTime(IDisplayer displayer, long time) {
        if (!isMeasured()) {
            return null;
        }
        float left = getLeft(displayer);
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
        return 5;
    }
}
